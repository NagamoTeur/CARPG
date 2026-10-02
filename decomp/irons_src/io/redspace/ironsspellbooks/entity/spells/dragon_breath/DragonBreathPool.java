package io.redspace.ironsspellbooks.entity.spells.dragon_breath;

import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AoeEntity;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import java.util.Optional;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public class DragonBreathPool extends AoeEntity {
   public static final DamageSource DAMAGE_SOURCE = new DamageSource(((AbstractSpell)SpellRegistry.DRAGON_BREATH_SPELL.get()).getDeathMessageId() + "_pool")
      .m_19389_();

   public DragonBreathPool(EntityType<? extends Projectile> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
      this.setCircular();
      this.setRadius(1.8F);
      this.radiusOnUse = -0.15F;
      this.radiusPerTick = -0.02F;
   }

   public DragonBreathPool(Level level) {
      this((EntityType<? extends Projectile>)EntityRegistry.DRAGON_BREATH_POOL.get(), level);
   }

   @Override
   public void applyEffect(LivingEntity target) {
      DamageSource damageSource = DamageSources.indirectDamageSource(DAMAGE_SOURCE, this, this.m_37282_());
      DamageSources.ignoreNextKnockback(target);
      target.m_6469_(damageSource, this.getDamage());
   }

   @Override
   public float getParticleCount() {
      return 3.0F;
   }

   @Override
   public Optional<ParticleOptions> getParticle() {
      return Optional.of(ParticleTypes.f_123799_);
   }
}
