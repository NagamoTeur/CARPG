package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Old_Netherite_Monstrosity_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.NewNetherite_Monstrosity.Netherite_Monstrosity_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.NewNetherite_Monstrosity.Netherite_Monstrosity_Part;
import com.github.L_Ender.cataclysm.entity.partentity.Old_Netherite_Monstrosity_Part;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.network.NetworkHooks;

public class Lava_Bomb_Entity extends ThrowableProjectile {
   public double prevDeltaMovementX;
   public double prevDeltaMovementY;
   public double prevDeltaMovementZ;

   public Lava_Bomb_Entity(EntityType<Lava_Bomb_Entity> type, Level world) {
      super(type, world);
   }

   public Lava_Bomb_Entity(EntityType<Lava_Bomb_Entity> type, Level world, LivingEntity thrower) {
      super(type, thrower, world);
   }

   protected void m_8097_() {
   }

   protected void m_6532_(HitResult ray) {
      Type raytraceresult$type = ray.m_6662_();
      if (raytraceresult$type == Type.ENTITY) {
         this.m_5790_((EntityHitResult)ray);
      } else if (raytraceresult$type == Type.BLOCK) {
         this.m_8060_((BlockHitResult)ray);
      }
   }

   protected void m_5790_(EntityHitResult result) {
      super.m_5790_(result);
      Entity shooter = this.m_37282_();
      if (!this.f_19853_.f_46443_
         && !(result.m_82443_() instanceof Lava_Bomb_Entity)
         && (
            !(shooter instanceof Netherite_Monstrosity_Entity)
               || !(result.m_82443_() instanceof Netherite_Monstrosity_Part) && !(result.m_82443_() instanceof Netherite_Monstrosity_Entity)
         )
         && (
            !(shooter instanceof Old_Netherite_Monstrosity_Entity)
               || !(result.m_82443_() instanceof Old_Netherite_Monstrosity_Part) && !(result.m_82443_() instanceof Old_Netherite_Monstrosity_Entity)
         )) {
         this.m_5496_(SoundEvents.f_11909_, 1.5F, 0.75F);
         this.f_19853_.m_46511_(this, this.m_20185_(), this.m_20186_(), this.m_20189_(), (float)CMConfig.Lavabombradius, BlockInteraction.NONE);
         this.doTerrainEffects();
         this.m_146870_();
      }
   }

   protected void m_8060_(BlockHitResult result) {
      super.m_8060_(result);
      if (!this.f_19853_.m_5776_()) {
         this.m_5496_(SoundEvents.f_11909_, 1.5F, 0.75F);
         this.f_19853_.m_46511_(this, this.m_20185_(), this.m_20186_(), this.m_20189_(), (float)CMConfig.Lavabombradius, BlockInteraction.NONE);
         this.doTerrainEffects();
         this.m_146870_();
      }
   }

   private void doTerrainEffects() {
      int range = 0;
      int ix = Mth.m_14107_(this.f_19790_);
      int iy = Mth.m_14107_(this.f_19791_);
      int iz = Mth.m_14107_(this.f_19792_);

      for (int x = 0; x <= 0; x++) {
         for (int y = 0; y <= 0; y++) {
            for (int z = 0; z <= 0; z++) {
               BlockPos pos = new BlockPos(ix + x, iy + y, iz + z);
               this.doTerrainEffect(pos);
            }
         }
      }
   }

   private void doTerrainEffect(BlockPos pos) {
      BlockState state = this.f_19853_.m_8055_(pos);
      if (state.m_60767_() == Material.f_76305_) {
         this.f_19853_.m_46597_(pos, Blocks.f_50069_.m_49966_());
      }

      if (this.f_19853_.m_46859_(pos) && Blocks.f_49991_.m_49966_().m_60710_(this.f_19853_, pos)) {
         this.f_19853_.m_46597_(pos, Blocks.f_49991_.m_49966_());
      }
   }

   public void m_8119_() {
      super.m_8119_();
      this.prevDeltaMovementX = this.m_20184_().f_82479_;
      this.prevDeltaMovementY = this.m_20184_().f_82480_;
      this.prevDeltaMovementZ = this.m_20184_().f_82481_;
      this.m_146922_(-((float)Mth.m_14136_(this.m_20184_().f_82479_, this.m_20184_().f_82481_)) * (180.0F / (float)Math.PI));
      this.makeTrail();
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
