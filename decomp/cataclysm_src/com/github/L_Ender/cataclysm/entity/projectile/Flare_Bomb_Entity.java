package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.client.particle.LightTrailParticle;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Old_Netherite_Monstrosity_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.NewNetherite_Monstrosity.Netherite_Monstrosity_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.NewNetherite_Monstrosity.Netherite_Monstrosity_Part;
import com.github.L_Ender.cataclysm.entity.partentity.Old_Netherite_Monstrosity_Part;
import com.github.L_Ender.cataclysm.init.ModParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

public class Flare_Bomb_Entity extends ThrowableProjectile {
   public double prevDeltaMovementX;
   public double prevDeltaMovementY;
   public double prevDeltaMovementZ;

   public Flare_Bomb_Entity(EntityType<Flare_Bomb_Entity> type, Level world) {
      super(type, world);
   }

   public Flare_Bomb_Entity(EntityType<Flare_Bomb_Entity> type, Level world, LivingEntity thrower) {
      super(type, thrower, world);
   }

   protected void m_8097_() {
   }

   protected void m_5790_(EntityHitResult result) {
      super.m_5790_(result);
      Entity shooter = this.m_37282_();
      if (!this.f_19853_.f_46443_
         && !(result.m_82443_() instanceof Flare_Bomb_Entity)
         && (
            !(shooter instanceof Netherite_Monstrosity_Entity)
               || !(result.m_82443_() instanceof Netherite_Monstrosity_Part) && !(result.m_82443_() instanceof Netherite_Monstrosity_Entity)
         )
         && (
            !(shooter instanceof Old_Netherite_Monstrosity_Entity)
               || !(result.m_82443_() instanceof Old_Netherite_Monstrosity_Part) && !(result.m_82443_() instanceof Old_Netherite_Monstrosity_Entity)
         )) {
         Entity entity = result.m_82443_();
         if (this.m_37282_() instanceof LivingEntity livingentity) {
            boolean flag = entity.m_6469_(DamageSource.m_19340_(this, livingentity), (float)CMConfig.FlareBombDamage);
            if (flag && entity.m_6084_()) {
               entity.m_20254_(5);
               this.m_19970_(livingentity, entity);
            }
         } else {
            entity.m_6469_(DamageSource.f_19319_, 7.0F);
         }
      }
   }

   protected void m_6532_(HitResult p_37628_) {
      super.m_6532_(p_37628_);
      if (!this.f_19853_.f_46443_) {
         this.m_5496_(SoundEvents.f_11909_, 1.5F, 0.75F);
         this.f_19853_.m_7605_(this, (byte)4);
         if (this.f_19796_.m_188499_()) {
            this.XStrikeRune(10, 2.0);
         } else {
            this.PlusStrikeRune(10, 2.0);
         }

         this.m_146870_();
      }
   }

   private void PlusStrikeRune(int rune, double time) {
      for (int i = 0; i < 4; i++) {
         float yawRadians = (float)Math.toRadians((double)(90.0F + this.m_146908_()));
         float throwAngle = yawRadians + (float)i * (float) Math.PI / 2.0F;

         for (int k = 0; k < rune; k++) {
            double d2 = 0.8 * (double)(k + 1);
            int d3 = (int)(time * (double)(k + 1));
            this.spawnFangs(
               this.m_20185_() + (double)Mth.m_14089_(throwAngle) * 1.25 * d2,
               this.m_20189_() + (double)Mth.m_14031_(throwAngle) * 1.25 * d2,
               this.m_20186_() - 2.0,
               this.m_20186_() + 2.0,
               throwAngle,
               d3
            );
         }
      }
   }

   private void XStrikeRune(int rune, double time) {
      for (int i = 0; i < 4; i++) {
         float yawRadians = (float)Math.toRadians((double)(45.0F + this.m_146908_()));
         float throwAngle = yawRadians + (float)i * (float) Math.PI / 2.0F;

         for (int k = 0; k < rune; k++) {
            double d2 = 0.8 * (double)(k + 1);
            int d3 = (int)(time * (double)(k + 1));
            this.spawnFangs(
               this.m_20185_() + (double)Mth.m_14089_(throwAngle) * 1.25 * d2,
               this.m_20189_() + (double)Mth.m_14031_(throwAngle) * 1.25 * d2,
               this.m_20186_() - 2.0,
               this.m_20186_() + 2.0,
               throwAngle,
               d3
            );
         }
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
         if (this.m_37282_() != null && this.m_37282_() instanceof LivingEntity living) {
            this.f_19853_
               .m_7967_(new Flame_Jet_Entity(this.f_19853_, x, (double)blockpos.m_123342_() + d0, z, rotation, delay, (float)CMConfig.FlareBombDamage, living));
         } else {
            this.f_19853_
               .m_7967_(new Flame_Jet_Entity(this.f_19853_, x, (double)blockpos.m_123342_() + d0, z, rotation, delay, (float)CMConfig.FlareBombDamage, null));
         }
      }
   }

   public void m_8119_() {
      super.m_8119_();
      this.prevDeltaMovementX = this.m_20184_().f_82479_;
      this.prevDeltaMovementY = this.m_20184_().f_82480_;
      this.prevDeltaMovementZ = this.m_20184_().f_82481_;
      this.m_146922_(-((float)Mth.m_14136_(this.m_20184_().f_82479_, this.m_20184_().f_82481_)) * (180.0F / (float)Math.PI));
      if (this.f_19853_.f_46443_) {
         double dx = this.m_20185_() + (double)(1.5F * (this.f_19796_.m_188501_() - 0.5F));
         double dy = this.m_20186_() + (double)(1.5F * (this.f_19796_.m_188501_() - 0.5F));
         double dz = this.m_20189_() + (double)(1.5F * (this.f_19796_.m_188501_() - 0.5F));
         float ran = 0.04F;
         float r = 0.7647059F + this.f_19796_.m_188501_() * ran * 1.5F;
         float g = 0.37254903F + this.f_19796_.m_188501_() * ran;
         float b = 0.011764706F + this.f_19796_.m_188501_() * ran;
         this.f_19853_.m_7106_(new LightTrailParticle.OrbData(r, g, b, 0.1F, this.m_20206_() / 2.0F, this.m_19879_()), dx, dy, dz, 0.0, 0.0, 0.0);
      }
   }

   public void makeTrail() {
      if (this.f_19853_.f_46443_) {
         for (int i = 0; i < 5; i++) {
            double dx = this.m_20185_() + (double)(1.5F * (this.f_19796_.m_188501_() - 0.5F));
            double dy = this.m_20186_() + (double)(1.5F * (this.f_19796_.m_188501_() - 0.5F));
            double dz = this.m_20189_() + (double)(1.5F * (this.f_19796_.m_188501_() - 0.5F));
            this.f_19853_.m_7106_(ParticleTypes.f_123744_, dx, dy, dz, -this.m_20184_().m_7096_(), -this.m_20184_().m_7098_(), -this.m_20184_().m_7094_());
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7822_(byte id) {
      super.m_7822_(id);
      if (id == 4) {
         this.f_19853_
            .m_7785_(
               this.m_20185_(),
               this.m_20186_(),
               this.m_20189_(),
               SoundEvents.f_11913_,
               SoundSource.BLOCKS,
               4.0F,
               (1.0F + (this.f_19853_.f_46441_.m_188501_() - this.f_19853_.f_46441_.m_188501_()) * 0.2F) * 0.7F,
               false
            );
         this.f_19853_
            .m_7106_(
               (ParticleOptions)ModParticle.FLARE_EXPLODE.get(),
               this.m_20185_(),
               this.m_20186_(),
               this.m_20189_(),
               this.f_19796_.m_188583_() * 0.05,
               0.005,
               this.f_19796_.m_188583_() * 0.05
            );
      }
   }

   public float m_213856_() {
      return 1.0F;
   }

   protected float m_7139_() {
      return 0.025F;
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}
