package com.bobmowzie.mowziesmobs.server.ability.abilities.mob;

import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.abilities.player.SimpleAnimationAbility;
import com.bobmowzie.mowziesmobs.server.entity.MowzieGeckoEntity;
import net.minecraft.world.entity.LivingEntity;

public class BlockAbility<T extends MowzieGeckoEntity> extends SimpleAnimationAbility<T> {
   public BlockAbility(AbilityType<T, ? extends BlockAbility<T>> abilityType, T user, String animationName, int duration) {
      super(abilityType, user, animationName, duration);
   }

   @Override
   public void tickUsing() {
      super.tickUsing();
      LivingEntity blockingEntity = this.getUser().blockingEntity;
      if (blockingEntity != null) {
         this.getUser().m_21391_(blockingEntity, 100.0F, 100.0F);
         this.getUser().m_21563_().m_24960_(blockingEntity, 200.0F, 30.0F);
      }
   }

   @Override
   public boolean canCancelActiveAbility() {
      return super.canCancelActiveAbility()
         || this.getUser().getActiveAbility() instanceof BlockAbility
         || this.getUser().getActiveAbility() instanceof HurtAbility;
   }

   @Override
   public boolean canCancelSelf() {
      return true;
   }
}
