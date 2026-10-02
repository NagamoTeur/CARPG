package com.aizistral.enigmaticlegacy.helpers;

import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.Vec3;

public class AdvancedSpawnLocationHelper {
   public static ResourceKey<Level> getPlayerRespawnDimension(ServerPlayer player) {
      return player.m_8963_();
   }

   public static BlockPos getRespawnLocation(ServerLevel world, int arg1, int arg2, boolean flag) {
      return getOverworldRespawnPos(world, arg1, arg2, flag);
   }

   private static int someNumberLol(int p_205735_1_) {
      return p_205735_1_ <= 16 ? p_205735_1_ - 1 : 17;
   }

   public static void fuckBackToSpawn(ServerLevel worldIn, ServerPlayer playerIn) {
      BlockPos blockpos = worldIn.m_220360_();
      playerIn.m_6021_((double)blockpos.m_123341_() + 0.5, (double)blockpos.m_123342_(), (double)blockpos.m_123343_() + 0.5);
      if (worldIn.m_6042_().f_223549_() && worldIn.m_7654_().m_129910_().m_5464_() != GameType.ADVENTURE) {
         int i = Math.max(0, worldIn.m_7654_().m_129803_(worldIn));
         int j = Mth.m_14107_(worldIn.m_6857_().m_61941_((double)blockpos.m_123341_(), (double)blockpos.m_123343_()));
         if (j < i) {
            i = j;
         }

         if (j <= 1) {
            i = 1;
         }

         long k = (long)(i * 2 + 1);
         long l = k * k;
         int i1 = l > 2147483647L ? Integer.MAX_VALUE : (int)l;
         int j1 = someNumberLol(i1);
         int k1 = new Random().nextInt(i1);

         for (int l1 = 0; l1 < i1; l1++) {
            int i2 = (k1 + j1 * l1) % i1;
            int j2 = i2 % (i * 2 + 1);
            int k2 = i2 / (i * 2 + 1);
            BlockPos blockpos1 = getRespawnLocation(worldIn, blockpos.m_123341_() + j2 - i, blockpos.m_123343_() + k2 - i, false);
            if (blockpos1 != null) {
               playerIn.m_6021_((double)blockpos1.m_123341_() + 0.5, (double)blockpos1.m_123342_(), (double)blockpos1.m_123343_() + 0.5);
               if (worldIn.m_45786_(playerIn)) {
                  break;
               }
            }
         }
      } else {
         while (!worldIn.m_45786_(playerIn) && playerIn.m_20186_() < 255.0) {
            playerIn.m_6021_(playerIn.m_20185_(), playerIn.m_20186_() + 1.0, playerIn.m_20189_());
         }
      }

      while (!worldIn.m_45786_(playerIn) && playerIn.m_20186_() < 256.0) {
         playerIn.m_6021_(playerIn.m_20185_(), playerIn.m_20186_() + 1.0, playerIn.m_20189_());
      }
   }

   public static Optional<Vec3> getValidSpawn(ServerLevel world, ServerPlayer player) {
      BlockPos blockpos = player.m_8961_();
      Optional<Vec3> optional;
      if (world != null && blockpos != null) {
         optional = Player.m_36130_(world, blockpos, player.m_8962_(), player.m_8964_(), false);
      } else {
         optional = Optional.empty();
      }

      return optional;
   }

   @Nullable
   protected static BlockPos getOverworldRespawnPos(ServerLevel p_241092_0_, int p_241092_1_, int p_241092_2_, boolean p_241092_3_) {
      MutableBlockPos blockpos$mutable = new MutableBlockPos(p_241092_1_, 0, p_241092_2_);
      Biome biome = (Biome)p_241092_0_.m_204166_(blockpos$mutable).m_203334_();
      boolean flag = p_241092_0_.m_6042_().f_63856_();
      LevelChunk chunk = p_241092_0_.m_6325_(p_241092_1_ >> 4, p_241092_2_ >> 4);
      int i = flag ? p_241092_0_.m_7726_().m_8481_().m_142051_(p_241092_0_) : chunk.m_5885_(Types.MOTION_BLOCKING, p_241092_1_ & 15, p_241092_2_ & 15);
      if (i < 0) {
         return null;
      } else {
         int j = chunk.m_5885_(Types.WORLD_SURFACE, p_241092_1_ & 15, p_241092_2_ & 15);
         if (j <= i && j > chunk.m_5885_(Types.OCEAN_FLOOR, p_241092_1_ & 15, p_241092_2_ & 15)) {
            return null;
         } else {
            for (int k = i + 1; k >= 0; k--) {
               blockpos$mutable.m_122178_(p_241092_1_, k, p_241092_2_);
               BlockState blockstate = p_241092_0_.m_8055_(blockpos$mutable);
               if (!blockstate.m_60819_().m_76178_()) {
                  break;
               }

               if (Block.m_49918_(blockstate.m_60812_(p_241092_0_, blockpos$mutable), Direction.UP)) {
                  return blockpos$mutable.m_7494_().m_7949_();
               }
            }

            return null;
         }
      }
   }

   @Nullable
   public static BlockPos getSpawnPosInChunk(ServerLevel p_241094_0_, ChunkPos p_241094_1_, boolean p_241094_2_) {
      for (int i = p_241094_1_.m_45604_(); i <= p_241094_1_.m_45608_(); i++) {
         for (int j = p_241094_1_.m_45605_(); j <= p_241094_1_.m_45609_(); j++) {
            BlockPos blockpos = getOverworldRespawnPos(p_241094_0_, i, j, p_241094_2_);
            if (blockpos != null) {
               return blockpos;
            }
         }
      }

      return null;
   }
}
