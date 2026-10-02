package io.redspace.ironsspellbooks.entity.spells.creeper_head;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import io.redspace.ironsspellbooks.spells.evocation.ChainCreeperSpell;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.network.NetworkHooks;

public class CreeperHeadProjectile extends WitherSkull implements AntiMagicSusceptible {
   protected float damage;
   protected boolean chainOnKill = false;

   public CreeperHeadProjectile(EntityType<? extends WitherSkull> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
   }

   public CreeperHeadProjectile(LivingEntity shooter, Level level, float speed, float damage) {
      this((EntityType<? extends WitherSkull>)EntityRegistry.CREEPER_HEAD_PROJECTILE.get(), level);
      this.m_5602_(shooter);
      Vec3 power = shooter.m_20154_().m_82541_().m_82490_((double)speed);
      this.f_36813_ = power.f_82479_;
      this.f_36814_ = power.f_82480_;
      this.f_36815_ = power.f_82481_;
      this.m_20334_(this.f_36813_, this.f_36814_, this.f_36815_);
      this.damage = damage;
   }

   public CreeperHeadProjectile(LivingEntity shooter, Level level, Vec3 power, float damage) {
      this((EntityType<? extends WitherSkull>)EntityRegistry.CREEPER_HEAD_PROJECTILE.get(), level);
      this.m_5602_(shooter);
      this.f_36813_ = power.f_82479_;
      this.f_36814_ = power.f_82480_;
      this.f_36815_ = power.f_82481_;
      this.m_20334_(this.f_36813_, this.f_36814_, this.f_36815_);
      this.damage = damage;
   }

   public void setChainOnKill(boolean chain) {
      this.chainOnKill = chain;
   }

   protected void m_5790_(EntityHitResult pResult) {
   }

   public void m_8119_() {
      if (!this.f_19853_.f_46443_) {
         HitResult hitresult = ProjectileUtil.m_37294_(this, x$0 -> this.m_5603_(x$0));
         if (hitresult.m_6662_() != Type.MISS) {
            this.m_6532_(hitresult);
         }
      } else {
         this.f_19853_.m_7106_(this.m_5967_(), this.m_20182_().f_82479_, this.m_20182_().f_82480_ + 0.25, this.m_20182_().f_82481_, 0.0, 0.0, 0.0);
      }

      ProjectileUtil.m_37284_(this, 1.0F);
      this.m_146884_(this.m_20182_().m_82549_(this.m_20184_()));
      if (!this.m_20068_()) {
         Vec3 vec34 = this.m_20184_();
         this.m_20334_(vec34.f_82479_, vec34.f_82480_ - 0.05F, vec34.f_82481_);
      }

      this.m_6075_();
   }

   protected void m_6532_(HitResult hitResult) {
      if (!this.f_19853_.f_46443_) {
         float explosionRadius = 3.5F;

         for (Entity entity : this.f_19853_.m_45933_(this, this.m_20191_().m_82400_((double)explosionRadius))) {
            double distance = entity.m_20182_().m_82554_(hitResult.m_82450_());
            if (distance < (double)explosionRadius) {
               if (entity instanceof LivingEntity livingEntity && livingEntity.m_21224_() && !this.m_5603_(entity)) {
                  break;
               }

               float damage = (float)((double)this.damage * (1.0 - Math.pow(distance / (double)explosionRadius, 2.0)));
               DamageSources.applyDamage(entity, damage, ((AbstractSpell)SpellRegistry.LOB_CREEPER_SPELL.get()).getDamageSource(this, this.m_37282_()));
               entity.f_19802_ = 0;
               if (this.chainOnKill && entity instanceof LivingEntity) {
                  LivingEntity livingEntity = (LivingEntity)entity;
                  if (livingEntity.m_21224_()) {
                     ChainCreeperSpell.summonCreeperRing(
                        this.f_19853_,
                        this.m_37282_() instanceof LivingEntity livingOwner ? livingOwner : null,
                        livingEntity.m_146892_(),
                        this.damage * 0.85F,
                        3
                     );
                  }
               }
            }
         }

         this.f_19853_.m_46518_(this, this.m_20185_(), this.m_20186_(), this.m_20189_(), 0.0F, false, BlockInteraction.NONE);
         this.m_146870_();
      }
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   @Override
   public void onAntiMagic(MagicData playerMagicData) {
      this.m_146870_();
   }
}
