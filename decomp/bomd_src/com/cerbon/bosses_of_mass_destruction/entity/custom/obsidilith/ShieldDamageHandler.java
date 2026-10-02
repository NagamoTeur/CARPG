package com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith;

import com.cerbon.bosses_of_mass_destruction.entity.damage.IDamageHandler;
import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityStats;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import java.util.function.Supplier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class ShieldDamageHandler implements IDamageHandler {
   private final Supplier<Boolean> isShielded;

   public ShieldDamageHandler(Supplier<Boolean> isShielded) {
      this.isShielded = isShielded;
   }

   @Override
   public void beforeDamage(IEntityStats stats, DamageSource damageSource, float amount) {
   }

   @Override
   public void afterDamage(IEntityStats stats, DamageSource damageSource, float amount, boolean result) {
   }

   @Override
   public boolean shouldDamage(LivingEntity actor, DamageSource damageSource, float amount) {
      if (this.isShielded.get() && !damageSource.m_19378_()) {
         if (!damageSource.m_19360_() && damageSource.m_7639_() instanceof LivingEntity livingEntity) {
            livingEntity.m_147240_(0.5, actor.m_20185_() - livingEntity.m_20185_(), actor.m_20189_() - livingEntity.m_20189_());
         }

         actor.m_5496_((SoundEvent)BMDSounds.ENERGY_SHIELD.get(), 1.0F, BMDUtils.randomPitch(actor.m_217043_()));
         return false;
      } else {
         return true;
      }
   }
}
