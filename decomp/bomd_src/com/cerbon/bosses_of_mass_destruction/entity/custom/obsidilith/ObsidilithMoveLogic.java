package com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.data.HistoricalData;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.random.WeightedRandom;
import com.cerbon.bosses_of_mass_destruction.entity.ai.TargetSwitcher;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.entity.damage.DamageMemory;
import com.cerbon.bosses_of_mass_destruction.entity.damage.IDamageHandler;
import com.cerbon.bosses_of_mass_destruction.entity.damage.StagedDamageHandler;
import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityStats;
import java.util.Map;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class ObsidilithMoveLogic implements IActionWithCooldown, IDamageHandler {
   private final Map<Byte, IActionWithCooldown> actions;
   private final ObsidilithEntity entity;
   private final HistoricalData<Byte> moveHistory;
   private boolean shouldDoPillarDefense;
   private final StagedDamageHandler damageHandler;
   private final TargetSwitcher targetSwitcher;

   public ObsidilithMoveLogic(Map<Byte, IActionWithCooldown> actions, ObsidilithEntity entity, DamageMemory damageMemory) {
      this.actions = actions;
      this.entity = entity;
      this.moveHistory = new HistoricalData<>((byte)0, 2);
      this.shouldDoPillarDefense = false;
      this.damageHandler = new StagedDamageHandler(ObsidilithUtils.hpPillarShieldMilestones, () -> this.shouldDoPillarDefense = true);
      this.targetSwitcher = new TargetSwitcher(entity, damageMemory);
   }

   @Override
   public int perform() {
      this.targetSwitcher.trySwitchTarget();
      byte moveByte = this.chooseMove();
      if (this.actions.get(moveByte) == null) {
         throw new IllegalArgumentException(moveByte + " action not registered as an attack");
      } else {
         IActionWithCooldown action = this.actions.get(moveByte);
         this.entity.f_19853_.m_7605_(this.entity, moveByte);
         return action.perform();
      }
   }

   private Byte chooseMove() {
      LivingEntity target = this.entity.m_5448_();
      if (target == null) {
         return (byte)5;
      } else {
         byte nextMove;
         if (this.shouldDoPillarDefense) {
            this.shouldDoPillarDefense = false;
            nextMove = 9;
         } else {
            WeightedRandom<Byte> random = new WeightedRandom<>();
            double distanceToTarget = target.m_20280_(this.entity);
            double burstWeight = distanceToTarget < 36.0 ? 1.0 : 0.0;
            double anvilWeight = !(distanceToTarget < 36.0) && !this.moveHistory.getAll().contains((byte)8) ? 1.0 : 0.0;
            double waveWeight = distanceToTarget < 36.0 ? 0.5 : 1.0;
            double spikeWeight = distanceToTarget < 36.0 ? 0.0 : 1.0;
            random.add(burstWeight, (byte)5);
            random.add(anvilWeight, (byte)8);
            random.add(spikeWeight, (byte)7);
            random.add(waveWeight, (byte)6);
            nextMove = random.next();
         }

         this.moveHistory.set(nextMove);
         return nextMove;
      }
   }

   @Override
   public void beforeDamage(IEntityStats stats, DamageSource damageSource, float amount) {
      this.damageHandler.beforeDamage(stats, damageSource, amount);
   }

   @Override
   public void afterDamage(IEntityStats stats, DamageSource damageSource, float amount, boolean result) {
      this.damageHandler.afterDamage(stats, damageSource, amount, result);
   }

   @Override
   public boolean shouldDamage(LivingEntity actor, DamageSource damageSource, float amount) {
      return true;
   }
}
