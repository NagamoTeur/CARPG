package io.redspace.ironsspellbooks.damage;

import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import net.minecraft.world.damagesource.DamageSource;

public interface ISpellDamageSource {
   DamageSource get();

   AbstractSpell spell();

   default SchoolType schoolType() {
      return this.spell().getSchoolType();
   }

   float getLifestealPercent();

   int getFireTime();

   int getFreezeTicks();

   ISpellDamageSource setLifestealPercent(float var1);

   ISpellDamageSource setFireTime(int var1);

   ISpellDamageSource setFreezeTicks(int var1);

   default boolean hasPostHitEffects() {
      return this.getLifestealPercent() > 0.0F || this.getFireTime() > 0 || this.getFreezeTicks() > 0;
   }
}
