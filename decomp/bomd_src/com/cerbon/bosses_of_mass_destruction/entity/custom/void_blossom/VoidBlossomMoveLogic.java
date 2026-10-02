package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.random.WeightedRandom;
import com.cerbon.bosses_of_mass_destruction.entity.ai.TargetSwitcher;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.world.entity.LivingEntity;

public class VoidBlossomMoveLogic implements IActionWithCooldown {
   private final Map<Byte, IActionWithCooldown> actions;
   private final VoidBlossomEntity entity;
   private final Supplier<Boolean> doBlossom;
   private final TargetSwitcher targetSwitcher;

   public VoidBlossomMoveLogic(Map<Byte, IActionWithCooldown> actions, VoidBlossomEntity entity, Supplier<Boolean> doBlossom, TargetSwitcher targetSwitcher) {
      this.actions = actions;
      this.entity = entity;
      this.doBlossom = doBlossom;
      this.targetSwitcher = targetSwitcher;
   }

   @Override
   public int perform() {
      this.targetSwitcher.trySwitchTarget();
      LivingEntity target = this.entity.m_5448_();
      if (target == null) {
         return 20;
      } else {
         float healthPercentage = this.entity.m_21223_() / this.entity.m_21233_();
         WeightedRandom<Byte> random = new WeightedRandom<>();
         double shortDistanceRate = target.m_20270_(this.entity) > 21.0F ? 0.0 : 1.0;
         double spikeWeight = 1.0;
         double sporeWeight = healthPercentage < VoidBlossomEntity.hpMilestones.get(3) ? shortDistanceRate : 0.0;
         double bladeWeight = healthPercentage < VoidBlossomEntity.hpMilestones.get(2) ? 1.0 : 0.0;
         byte moveByte;
         if (this.doBlossom.get()) {
            moveByte = 9;
         } else {
            random.add(spikeWeight, (byte)4);
            random.add(shortDistanceRate, (byte)5);
            random.add(sporeWeight, (byte)7);
            random.add(bladeWeight, (byte)8);
            moveByte = random.next();
         }

         IActionWithCooldown action = this.actions.get(moveByte);
         if (action == null) {
            throw new IllegalArgumentException(moveByte + " action not registered as an attack");
         } else {
            this.entity.f_19853_.m_7605_(this.entity, moveByte);
            return action.perform();
         }
      }
   }
}
