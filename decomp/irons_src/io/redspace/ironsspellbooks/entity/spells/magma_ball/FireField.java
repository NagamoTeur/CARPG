package io.redspace.ironsspellbooks.entity.spells.magma_ball;

import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AoeEntity;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import java.util.Optional;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public class FireField extends AoeEntity {
   public static final DamageSource DAMAGE_SOURCE = new DamageSource(String.format("%s.%s", "irons_spellbooks", "fire_field"));

   public FireField(EntityType<? extends Projectile> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
   }

   public FireField(Level level) {
      this((EntityType<? extends Projectile>)EntityRegistry.FIRE_FIELD.get(), level);
   }

   @Override
   public void applyEffect(LivingEntity target) {
      DamageSource damageSource = DamageSources.indirectDamageSource(DAMAGE_SOURCE, this, this.m_37282_());
      DamageSources.ignoreNextKnockback(target);
      target.m_6469_(damageSource, this.getDamage());
      target.m_20254_(3);
   }

   @Override
   public float getParticleCount() {
      return 0.7F * this.getRadius();
   }

   @Override
   protected float particleYOffset() {
      return 0.25F;
   }

   @Override
   protected float getParticleSpeedModifier() {
      return 1.4F;
   }

   @Override
   public Optional<ParticleOptions> getParticle() {
      return Optional.of(ParticleHelper.FIRE);
   }
}
