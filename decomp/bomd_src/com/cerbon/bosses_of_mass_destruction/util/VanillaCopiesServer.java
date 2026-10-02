package com.cerbon.bosses_of_mass_destruction.util;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class VanillaCopiesServer {
   public static void travel(Vec3 relative, LivingEntity entity, float baseFrictionCoefficient) {
      if (entity.m_20069_()) {
         entity.m_19920_(0.02F, relative);
         entity.m_6478_(MoverType.SELF, entity.m_20184_());
         entity.m_20256_(entity.m_20184_().m_82490_(0.8F));
      } else if (entity.m_20077_()) {
         entity.m_19920_(0.02F, relative);
         entity.m_6478_(MoverType.SELF, entity.m_20184_());
         entity.m_20256_(entity.m_20184_().m_82490_(0.5));
      } else {
         float friction = entity.m_20096_()
            ? entity.f_19853_.m_8055_(new BlockPos(entity.m_20185_(), entity.m_20186_() - 1.0, entity.m_20189_())).m_60734_().m_49958_()
               * baseFrictionCoefficient
            : baseFrictionCoefficient;
         float g = 0.16277137F / (friction * friction * friction);
         entity.m_19920_(entity.m_20096_() ? 0.1F * g : 0.02F, relative);
         entity.m_6478_(MoverType.SELF, entity.m_20184_());
         entity.m_20256_(entity.m_20184_().m_82490_((double)friction));
      }

      entity.m_21043_(entity, false);
   }

   public static void lookAtTarget(Mob mobEntity, Vec3 target, float maxYawChange, float maxPitchChange) {
      double d = target.f_82479_ - mobEntity.m_20185_();
      double e = target.f_82481_ - mobEntity.m_20189_();
      double g = target.f_82480_ - mobEntity.m_20188_();
      double h = Math.sqrt(d * d + e * e);
      float i = (float)(Mth.m_14136_(e, d) * 180.0F / (float)Math.PI - 90.0);
      float j = (float)(-(Mth.m_14136_(g, h) * 180.0F / (float)Math.PI));
      mobEntity.m_146926_(changeAngle(mobEntity.m_146909_(), j, maxPitchChange));
      mobEntity.m_146922_(changeAngle(mobEntity.m_146908_(), i, maxYawChange));
   }

   public static float changeAngle(float oldAngle, float newAngle, float maxChangeInAngle) {
      float f = Mth.m_14177_(newAngle - oldAngle);
      if (f > maxChangeInAngle) {
         f = maxChangeInAngle;
      }

      if (f < -maxChangeInAngle) {
         f = -maxChangeInAngle;
      }

      return oldAngle + f;
   }

   public static boolean hasDirectLineOfSight(Vec3 to, Vec3 from, BlockGetter level, Entity entity) {
      ClipContext context = new ClipContext(to, from, Block.COLLIDER, Fluid.NONE, entity);
      return level.m_45547_(context).m_6662_() == Type.MISS;
   }

   public static void awardExperience(int amount, Vec3 pos, Level level) {
      int amt = amount;

      while (amt > 0) {
         int i = ExperienceOrb.m_20782_(amt);
         amt -= i;
         level.m_7967_(new ExperienceOrb(level, pos.f_82479_, pos.f_82480_, pos.f_82481_, i));
      }
   }

   public static int getBlockLight(Entity entity, BlockPos blockPos) {
      return entity.m_6060_() ? 15 : entity.f_19853_.m_45517_(LightLayer.BLOCK, blockPos);
   }

   public static BlockInteraction getEntityDestructionType(Level level) {
      return level.m_46469_().m_46207_(GameRules.f_46132_) ? BlockInteraction.DESTROY : BlockInteraction.NONE;
   }

   public static void destroyBlocks(Entity entity, AABB aabb) {
      int i = Mth.m_14107_(aabb.f_82288_);
      int j = Mth.m_14107_(aabb.f_82289_);
      int k = Mth.m_14107_(aabb.f_82290_);
      int l = Mth.m_14107_(aabb.f_82291_);
      int m = Mth.m_14107_(aabb.f_82292_);
      int n = Mth.m_14107_(aabb.f_82293_);
      boolean bl2 = false;

      for (int o = i; o <= l; o++) {
         for (int p = j; p <= m; p++) {
            for (int q = k; q <= n; q++) {
               BlockPos blockPos = new BlockPos(o, p, q);
               BlockState blockState = entity.f_19853_.m_8055_(blockPos);
               if (!blockState.m_60795_()
                  && blockState.m_60734_() == Blocks.f_50083_
                  && entity.f_19853_.m_46469_().m_46207_(GameRules.f_46132_)
                  && !blockState.m_204336_(BlockTags.f_13070_)) {
                  bl2 = entity.f_19853_.m_46961_(blockPos, false) || bl2;
               }
            }
         }
      }
   }

   public static void onBreakInCreative(Level level, BlockPos pos, BlockState state, Player player) {
      DoubleBlockHalf doubleBlockHalf = (DoubleBlockHalf)state.m_61143_(DoublePlantBlock.f_52858_);
      if (doubleBlockHalf == DoubleBlockHalf.UPPER) {
         BlockPos blockPos = pos.m_7495_();
         BlockState blockState = level.m_8055_(blockPos);
         if (blockState.m_60734_() == state.m_60734_() && blockState.m_61143_(DoublePlantBlock.f_52858_) == DoubleBlockHalf.LOWER) {
            level.m_7731_(blockPos, Blocks.f_50016_.m_49966_(), 35);
            level.m_5898_(player, 2001, blockPos, net.minecraft.world.level.block.Block.m_49956_(blockState));
         }
      }
   }
}
