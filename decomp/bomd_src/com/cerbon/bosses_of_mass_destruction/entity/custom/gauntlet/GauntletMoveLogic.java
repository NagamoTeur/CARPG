package com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.data.HistoricalData;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.random.WeightedRandom;
import com.cerbon.bosses_of_mass_destruction.entity.ai.TargetSwitcher;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.entity.damage.DamageMemory;
import java.util.Map;
import net.minecraft.world.entity.LivingEntity;

public class GauntletMoveLogic implements IActionWithCooldown {
   private final Map<Byte, IActionWithCooldown> actions;
   private final GauntletEntity entity;
   private final HistoricalData<Byte> moveHistory = new HistoricalData<>((byte)0, 4);
   private final TargetSwitcher targetSwitcher;
   public static final double laserPercentage = 0.85;
   public static final double swirlPunchPercentage = 0.7;
   public static final double blindnessPercentage = 0.5;

   public GauntletMoveLogic(Map<Byte, IActionWithCooldown> actions, GauntletEntity entity, DamageMemory damageMemory) {
      this.actions = actions;
      this.entity = entity;
      this.targetSwitcher = new TargetSwitcher(entity, damageMemory);
   }

   @Override
   public int perform() {
      this.targetSwitcher.trySwitchTarget();
      byte moveByte = this.chooseMove();
      IActionWithCooldown action = this.actions.get(moveByte);
      if (action == null) {
         throw new IllegalArgumentException(moveByte + " action not registered as an attack");
      } else {
         this.entity.f_19853_.m_7605_(this.entity, moveByte);
         return action.perform();
      }
   }

   private byte chooseMove() {
      LivingEntity target = this.entity.m_5448_();
      if (target == null) {
         return 4;
      } else {
         float healthPercentage = this.entity.m_21223_() / this.entity.m_21233_();
         WeightedRandom<Byte> random = new WeightedRandom<>();
         double punchWeight = 1.0;
         double laserWeight = this.moveHistory.get(0) != 8 && !((double)healthPercentage >= 0.85) ? 0.7 : 0.0;
         double swirlPunchWeight = this.moveHistory.get(0) != 10 && !((double)healthPercentage >= 0.7) ? 0.7 : 0.0;
         double blindnessWeight = !this.moveHistory.getAll().contains((byte)11) && !((double)healthPercentage >= 0.5) ? 1.0 : 0.0;
         random.add(punchWeight, (byte)4);
         random.add(laserWeight, (byte)8);
         random.add(swirlPunchWeight, (byte)10);
         random.add(blindnessWeight, (byte)11);
         byte nextMove = random.next();
         this.moveHistory.set(nextMove);
         return nextMove;
      }
   }
}
