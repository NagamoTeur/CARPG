package io.redspace.ironsspellbooks.entity.spells.poison_cloud;

import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AoeEntity;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import java.util.Optional;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public class PoisonCloud extends AoeEntity {
   public static final DamageSource DAMAGE_SOURCE = new DamageSource(String.format("%s.%s", "irons_spellbooks", "poison_cloud"));

   public PoisonCloud(EntityType<? extends Projectile> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
   }

   public PoisonCloud(Level level) {
      this((EntityType<? extends Projectile>)EntityRegistry.POISON_CLOUD.get(), level);
   }

   @Override
   public void applyEffect(LivingEntity target) {
      DamageSource damageSource = DamageSources.indirectDamageSource(DAMAGE_SOURCE, this, this.m_37282_());
      DamageSources.ignoreNextKnockback(target);
      target.m_6469_(damageSource, this.getDamage());
      target.m_7292_(new MobEffectInstance(MobEffects.f_19614_, 120, (int)this.getDamage()));
   }

   @Override
   public float getParticleCount() {
      return 0.15F;
   }

   @Override
   public Optional<ParticleOptions> getParticle() {
      return Optional.of(ParticleHelper.POISON_CLOUD);
   }
}
