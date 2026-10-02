package io.redspace.ironsspellbooks.entity.spells.magic_arrow;

import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.event.ForgeEventFactory;

public class MagicArrowProjectile extends AbstractMagicProjectile {
   private final List<Entity> victims = new ArrayList<>();
   private int hitsPerTick;
   BlockPos lastHitBlock;

   @Override
   public void trailParticles() {
      Vec3 vec3 = this.m_20182_().m_82546_(this.m_20184_());
      this.f_19853_.m_7106_(ParticleHelper.UNSTABLE_ENDER, vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, 0.0, 0.0, 0.0);
   }

   @Override
   public void impactParticles(double x, double y, double z) {
      MagicManager.spawnParticles(this.f_19853_, ParticleHelper.UNSTABLE_ENDER, x, y, z, 15, 0.1, 0.1, 0.1, 0.5, false);
   }

   @Override
   public float getSpeed() {
      return 2.7F;
   }

   @Override
   public Optional<SoundEvent> getImpactSound() {
      return Optional.empty();
   }

   public MagicArrowProjectile(EntityType<? extends Projectile> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
      this.m_20242_(true);
   }

   public MagicArrowProjectile(Level levelIn, LivingEntity shooter) {
      this((EntityType<? extends Projectile>)EntityRegistry.MAGIC_ARROW_PROJECTILE.get(), levelIn);
      this.m_5602_(shooter);
   }

   protected void m_8060_(BlockHitResult pResult) {
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      this.hitsPerTick = 0;
   }

   @Override
   protected void m_5790_(EntityHitResult entityHitResult) {
      Entity entity = entityHitResult.m_82443_();
      if (!this.victims.contains(entity)) {
         DamageSources.applyDamage(entity, this.damage, ((AbstractSpell)SpellRegistry.MAGIC_ARROW_SPELL.get()).getDamageSource(this, this.m_37282_()));
         this.victims.add(entity);
      }

      if (this.hitsPerTick++ < 5) {
         HitResult hitresult = ProjectileUtil.m_37294_(this, x$0 -> this.m_5603_(x$0));
         if (hitresult.m_6662_() != Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitresult)) {
            this.m_6532_(hitresult);
         }
      }
   }

   @Override
   protected void m_6532_(HitResult result) {
      if (!this.f_19853_.f_46443_) {
         BlockPos blockPos = new BlockPos(result.m_82450_());
         if (result.m_6662_() == Type.BLOCK && !blockPos.equals(this.lastHitBlock)) {
            this.lastHitBlock = blockPos;
         } else if (result.m_6662_() == Type.ENTITY) {
            this.f_19853_.m_5594_(null, new BlockPos(this.m_20182_()), (SoundEvent)SoundRegistry.FORCE_IMPACT.get(), SoundSource.NEUTRAL, 2.0F, 0.65F);
         }
      }

      super.m_6532_(result);
   }

   @Override
   protected boolean shouldPierceShields() {
      return true;
   }
}
