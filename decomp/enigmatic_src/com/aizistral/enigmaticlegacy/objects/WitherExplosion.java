package com.aizistral.enigmaticlegacy.objects;

import java.util.Collections;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class WitherExplosion extends Explosion {
   private final boolean causesFire;
   private final BlockInteraction mode;
   private final Random random = new Random();
   private final Level world;
   private final double x;
   private final double y;
   private final double z;
   @Nullable
   private final Entity exploder;
   private final float size;

   public WitherExplosion(Level worldIn, Entity exploderIn, double xIn, double yIn, double zIn, float sizeIn, boolean causesFireIn, BlockInteraction modeIn) {
      super(worldIn, exploderIn, (DamageSource)null, (ExplosionDamageCalculator)null, xIn, yIn, zIn, sizeIn, causesFireIn, modeIn);
      this.world = worldIn;
      this.exploder = exploderIn;
      this.size = sizeIn;
      this.x = xIn;
      this.y = yIn;
      this.z = zIn;
      this.causesFire = causesFireIn;
      this.mode = modeIn;
      DamageSource.m_19358_(this);
      new Vec3(this.x, this.y, this.z);
   }

   public void m_46075_(boolean spawnParticles) {
      if (this.world.f_46443_) {
         this.world
            .m_7785_(
               this.x,
               this.y,
               this.z,
               SoundEvents.f_11913_,
               SoundSource.BLOCKS,
               4.0F,
               (1.0F + (this.world.f_46441_.m_188501_() - this.world.f_46441_.m_188501_()) * 0.2F) * 0.7F,
               false
            );
      }

      boolean flag = this.mode != BlockInteraction.NONE;
      if (spawnParticles) {
         if (!(this.size < 2.0F) && flag) {
            this.world.m_7106_(ParticleTypes.f_123812_, this.x, this.y, this.z, 1.0, 0.0, 0.0);
         } else {
            this.world.m_7106_(ParticleTypes.f_123813_, this.x, this.y, this.z, 1.0, 0.0, 0.0);
         }
      }

      if (flag) {
         Collections.shuffle(super.m_46081_());

         for (BlockPos blockpos : super.m_46081_()) {
            BlockState blockstate = this.world.m_8055_(blockpos);
            if (!blockstate.m_60795_()) {
               this.world.m_46473_().m_6180_("explosion_blocks");
               blockstate.onBlockExploded(this.world, blockpos, this);
               this.world.m_46473_().m_7238_();
            }
         }
      }

      if (this.causesFire) {
         for (BlockPos blockpos2 : super.m_46081_()) {
            if (this.random.nextInt(3) == 0
               && this.world.m_8055_(blockpos2).m_60795_()
               && this.world.m_8055_(blockpos2.m_7495_()).m_60804_(this.world, blockpos2.m_7495_())) {
               this.world.m_46597_(blockpos2, Blocks.f_50083_.m_49966_());
            }
         }
      }
   }
}
