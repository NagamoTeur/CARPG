package com.rolfmao.upgradednetherite_items.entity;

import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.EntityTeleportEvent.EnderEntity;

public class EnderUpgradedNetheritePearlEntity extends ThrowableItemProjectile {
   public EnderUpgradedNetheritePearlEntity(EntityType<? extends ThrowableItemProjectile> p_37442_, Level p_37443_) {
      super(p_37442_, p_37443_);
   }

   public EnderUpgradedNetheritePearlEntity(Level worldIn, LivingEntity throwerIn) {
      super(EntityType.f_20484_, throwerIn, worldIn);
   }

   @OnlyIn(Dist.CLIENT)
   public EnderUpgradedNetheritePearlEntity(Level worldIn, double x, double y, double z) {
      super(EntityType.f_20484_, x, y, z, worldIn);
   }

   protected Item m_7881_() {
      return Items.f_42584_;
   }

   protected void m_5790_(EntityHitResult p_213868_1_) {
      super.m_5790_(p_213868_1_);
      p_213868_1_.m_82443_().m_6469_(DamageSource.m_19361_(this, this.m_37282_()), 0.0F);
   }

   protected void m_6532_(HitResult hitResult) {
      super.m_6532_(hitResult);

      for (int i = 0; i < 32; i++) {
         this.f_19853_
            .m_7106_(
               ParticleTypes.f_123760_,
               this.m_20185_(),
               this.m_20186_() + this.f_19796_.m_188500_() * 2.0,
               this.m_20189_(),
               this.f_19796_.m_188583_(),
               0.0,
               this.f_19796_.m_188583_()
            );
      }

      if (!this.f_19853_.f_46443_ && !this.m_213877_()) {
         Entity entity = this.f_19853_.m_6815_(this.getPersistentData().m_128451_("EnderUpgradedNetheritePearlTarget"));
         if (entity instanceof LivingEntity livingentity) {
            EnderEntity event = ForgeEventFactory.onEnderTeleport(livingentity, this.m_20185_(), this.m_20186_(), this.m_20189_());
            if (!event.isCanceled()) {
               if (this.f_19796_.m_188501_() < 0.05F && this.f_19853_.m_46469_().m_46207_(GameRules.f_46134_)) {
                  Endermite endermiteentity = (Endermite)EntityType.f_20567_.m_20615_(this.f_19853_);
                  endermiteentity.m_7678_(entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), entity.m_146908_(), entity.m_146909_());
                  this.f_19853_.m_7967_(endermiteentity);
               }

               if (entity.m_20159_()) {
                  entity.m_8127_();
               }

               entity.m_6021_(event.getTargetX(), event.getTargetY(), event.getTargetZ());
               entity.f_19789_ = 0.0F;
            }
         }
      }

      this.m_146870_();
   }

   public void m_8119_() {
      Entity entity = this.f_19853_.m_6815_(this.getPersistentData().m_128451_("EnderUpgradedNetheritePearlTarget"));
      if (!(entity instanceof Player) && entity != null && entity.m_6084_()) {
         super.m_8119_();
      } else {
         this.m_146870_();
      }
   }

   @Nullable
   public Entity changeDimension(ServerLevel p_37506_, ITeleporter teleporter) {
      Entity entity = this.f_19853_.m_6815_(this.getPersistentData().m_128451_("EnderUpgradedNetheritePearlTarget"));
      if (entity != null && entity.f_19853_.m_46472_() != p_37506_.m_46472_()) {
         this.m_5602_((Entity)null);
      }

      return super.changeDimension(p_37506_, teleporter);
   }
}
