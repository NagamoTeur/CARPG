package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

public class Void_Howitzer_Entity extends ThrowableProjectile {
   public Void_Howitzer_Entity(EntityType<Void_Howitzer_Entity> type, Level world) {
      super(type, world);
   }

   public Void_Howitzer_Entity(EntityType<Void_Howitzer_Entity> type, Level world, LivingEntity thrower) {
      super(type, thrower, world);
   }

   protected void m_8097_() {
   }

   protected void m_5790_(EntityHitResult p_37626_) {
      super.m_5790_(p_37626_);
      if (!this.f_19853_.f_46443_) {
         Entity entity = p_37626_.m_82443_();
         if (this.m_37282_() instanceof LivingEntity livingentity) {
            boolean flag = entity.m_6469_(DamageSource.m_19367_(this, livingentity).m_19366_(), 8.0F);
            if (flag) {
               if (entity.m_6084_()) {
                  this.m_19970_(livingentity, entity);
               } else {
                  livingentity.m_5634_(5.0F);
               }
            }
         } else {
            entity.m_6469_(DamageSource.f_19319_, 5.0F);
         }
      }
   }

   protected void m_6532_(HitResult p_37628_) {
      super.m_6532_(p_37628_);
      if (!this.f_19853_.f_46443_) {
         int standingOnY = Mth.m_14107_(this.m_20186_()) - 3;
         this.f_19853_.m_46518_(this, this.m_20185_(), this.m_20186_(), this.m_20189_(), 3.0F, false, BlockInteraction.NONE);

         for (int k = 0; k < 6; k++) {
            float f2 = (float)k * (float) Math.PI * 2.0F / 6.0F + (float) (Math.PI * 2.0 / 5.0);
            this.spawnFangs(
               this.m_20185_() + (double)Mth.m_14089_(f2) * 2.5,
               this.m_20189_() + (double)Mth.m_14031_(f2) * 2.5,
               (double)standingOnY,
               this.m_20186_() + 1.0,
               f2,
               0
            );
         }

         for (int k = 0; k < 11; k++) {
            float f3 = (float)k * (float) Math.PI * 2.0F / 11.0F + (float) (Math.PI / 5);
            this.spawnFangs(
               this.m_20185_() + (double)Mth.m_14089_(f3) * 3.5,
               this.m_20189_() + (double)Mth.m_14031_(f3) * 3.5,
               (double)standingOnY,
               this.m_20186_() + 1.0,
               f3,
               2
            );
         }

         for (int k = 0; k < 14; k++) {
            float f4 = (float)k * (float) Math.PI * 2.0F / 14.0F + (float) (Math.PI / 10);
            this.spawnFangs(
               this.m_20185_() + (double)Mth.m_14089_(f4) * 4.5,
               this.m_20189_() + (double)Mth.m_14031_(f4) * 4.5,
               (double)standingOnY,
               this.m_20186_() + 1.0,
               f4,
               4
            );
         }

         for (int k = 0; k < 19; k++) {
            float f5 = (float)k * (float) Math.PI * 2.0F / 19.0F + 0.25132743F;
            this.spawnFangs(
               this.m_20185_() + (double)Mth.m_14089_(f5) * 5.5,
               this.m_20189_() + (double)Mth.m_14031_(f5) * 5.5,
               (double)standingOnY,
               this.m_20186_() + 1.0,
               f5,
               6
            );
         }

         for (int k = 0; k < 26; k++) {
            float f5 = (float)k * (float) Math.PI * 2.0F / 26.0F + 0.17951958F;
            this.spawnFangs(
               this.m_20185_() + (double)Mth.m_14089_(f5) * 6.5,
               this.m_20189_() + (double)Mth.m_14031_(f5) * 6.5,
               (double)standingOnY,
               this.m_20186_() + 1.0,
               f5,
               8
            );
         }

         ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 40.0F, 0.3F, 0, 20);
         this.m_146870_();
      }
   }

   private void spawnFangs(double x, double z, double minY, double maxY, float rotation, int delay) {
      BlockPos blockpos = new BlockPos(x, maxY, z);
      boolean flag = false;
      double d0 = 0.0;

      do {
         BlockPos blockpos1 = blockpos.m_7495_();
         BlockState blockstate = this.f_19853_.m_8055_(blockpos1);
         if (blockstate.m_60783_(this.f_19853_, blockpos1, Direction.UP)) {
            if (!this.f_19853_.m_46859_(blockpos)) {
               BlockState blockstate1 = this.f_19853_.m_8055_(blockpos);
               VoxelShape voxelshape = blockstate1.m_60812_(this.f_19853_, blockpos);
               if (!voxelshape.m_83281_()) {
                  d0 = voxelshape.m_83297_(Axis.Y);
               }
            }

            flag = true;
            break;
         }

         blockpos = blockpos.m_7495_();
      } while (blockpos.m_123342_() >= Mth.m_14107_(minY) - 1);

      if (flag) {
         LivingEntity entity1 = (LivingEntity)this.m_37282_();
         this.f_19853_
            .m_7967_(new Void_Rune_Entity(this.f_19853_, x, (double)blockpos.m_123342_() + d0, z, rotation, delay, (float)CMConfig.Voidrunedamage, entity1));
      }
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.f_19853_.f_46443_) {
         Vec3 vec3 = this.m_20184_();
         this.f_19853_
            .m_7106_(ParticleTypes.f_123789_, this.m_20185_() - vec3.f_82479_, this.m_20186_() - vec3.f_82480_, this.m_20189_() - vec3.f_82481_, 0.0, 0.0, 0.0);
         this.f_19853_
            .m_7106_(ParticleTypes.f_123762_, this.m_20185_() - vec3.f_82479_, this.m_20186_() - vec3.f_82480_, this.m_20189_() - vec3.f_82481_, 0.0, 0.0, 0.0);
      }
   }

   protected float m_7139_() {
      return 0.03F;
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}
