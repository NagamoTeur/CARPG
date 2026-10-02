package com.aizistral.enigmaticlegacy.helpers;

import com.aizistral.enigmaticlegacy.items.generic.ItemBase;
import com.aizistral.enigmaticlegacy.objects.Vector3;
import com.google.common.collect.Sets;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class AOEMiningHelper {
   public static final Random random = new Random();

   public static void attemptBreakNeighbors(
      Level world, BlockPos pos, Player player, Set<Block> effectiveOn, Set<Material> effectiveMaterials, boolean checkHarvestLevel
   ) {
      HitResult trace = calcRayTrace(world, player, Fluid.ANY);
      if (trace.m_6662_() == Type.BLOCK) {
         BlockHitResult blockTrace = (BlockHitResult)trace;
         Direction face = blockTrace.m_82434_();
         int fortuneLevel = player.m_21205_().getEnchantmentLevel(Enchantments.f_44987_);
         int silkLevel = player.m_21205_().getEnchantmentLevel(Enchantments.f_44985_);

         for (int a = -1; a <= 1; a++) {
            for (int b = -1; b <= 1; b++) {
               if (a != 0 || b != 0) {
                  BlockPos target = null;
                  if (face == Direction.UP || face == Direction.DOWN) {
                     target = pos.m_7918_(a, 0, b);
                  }

                  if (face == Direction.NORTH || face == Direction.SOUTH) {
                     target = pos.m_7918_(a, b, 0);
                  }

                  if (face == Direction.EAST || face == Direction.WEST) {
                     target = pos.m_7918_(0, a, b);
                  }

                  attemptBreak(
                     world, target, player, effectiveOn, effectiveMaterials, fortuneLevel, silkLevel, checkHarvestLevel, null, (objPos, objState) -> {
                     }
                  );
               }
            }
         }
      }
   }

   public static void attemptBreak(
      Level world,
      BlockPos pos,
      Player player,
      Set<Block> effectiveOn,
      Set<Material> effectiveMaterials,
      int fortuneLevel,
      int silkLevel,
      boolean checkHarvestLevel,
      ItemStack tool,
      BiConsumer<BlockPos, BlockState> toolDamageConsumer
   ) {
      BlockState state = world.m_8055_(pos);
      BlockEntity iCertainlyHopeYouHaveATileEntityLicense = world.m_7702_(pos);
      boolean validHarvest = !checkHarvestLevel || player.m_21205_().m_41735_(state);
      boolean isEffective = effectiveOn.contains(state.m_60734_()) || effectiveMaterials.contains(state.m_60767_());
      boolean unbreakable = state.m_204336_(BlockTags.f_13070_) || state.m_60734_() == Blocks.f_50085_ || state.m_60800_(world, pos) < 0.0F;
      if (validHarvest && isEffective && !unbreakable) {
         world.m_46961_(pos, false);
         Block.m_49881_(state, world, pos, iCertainlyHopeYouHaveATileEntityLicense, player, player.m_21205_());
         toolDamageConsumer.accept(pos, state);
         int exp = state.getExpDrop(world, world.f_46441_, pos, fortuneLevel, silkLevel);
         if (exp > 0 && world instanceof ServerLevel) {
            state.m_60734_().m_49805_((ServerLevel)world, pos, exp);
         }
      }
   }

   public static BlockHitResult calcRayTrace(Level worldIn, Player player, Fluid fluidMode) {
      return ItemBase.rayTrace(worldIn, player, fluidMode);
   }

   public static Vector3 calcRayTrace(Level worldIn, Player player, Fluid fluidMode, double distance) {
      float f = player.m_146909_();
      float f1 = player.m_146908_();
      Vec3 vector3d = player.m_20299_(1.0F);
      float f2 = Mth.m_14089_(-f1 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float f3 = Mth.m_14031_(-f1 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float f4 = -Mth.m_14089_(-f * (float) (Math.PI / 180.0));
      float f5 = Mth.m_14031_(-f * (float) (Math.PI / 180.0));
      float f6 = f3 * f4;
      float f7 = f2 * f4;
      Vec3 vector3d1 = vector3d.m_82520_((double)f6 * distance, (double)f5 * distance, (double)f7 * distance);
      HitResult result = worldIn.m_45547_(new ClipContext(vector3d, vector3d1, net.minecraft.world.level.ClipContext.Block.OUTLINE, fluidMode, player));
      return result.m_6662_() == Type.BLOCK
         ? new Vector3(result.m_82450_())
         : new Vector3(player.m_20154_()).multiply(64.0).add(new Vector3(player.m_20182_()));
   }

   public static void harvestPlane(
      Level world,
      Player player,
      Direction dir,
      BlockPos pos,
      Set<Material> effectiveMaterials,
      int radius,
      boolean harvestLevelCheck,
      @Nullable BlockPos excludedBlock,
      ItemStack tool,
      BiConsumer<BlockPos, BlockState> toolDamageConsumer
   ) {
      int fortuneLevel = EnchantmentHelper.m_44843_(Enchantments.f_44987_, player.m_21205_());
      int silkLevel = EnchantmentHelper.m_44843_(Enchantments.f_44985_, player.m_21205_());
      int supRad = (radius - 1) / 2;

      for (int a = -supRad; a <= supRad; a++) {
         for (int b = -supRad; b <= supRad; b++) {
            BlockPos target = null;
            if (dir == Direction.UP || dir == Direction.DOWN) {
               target = pos.m_7918_(a, 0, b);
            }

            if (dir == Direction.NORTH || dir == Direction.SOUTH) {
               target = pos.m_7918_(a, b, 0);
            }

            if (dir == Direction.EAST || dir == Direction.WEST) {
               target = pos.m_7918_(0, a, b);
            }

            if (excludedBlock == null || target == null || !target.equals(excludedBlock)) {
               attemptBreak(world, target, player, Sets.newHashSet(), effectiveMaterials, fortuneLevel, silkLevel, harvestLevelCheck, tool, toolDamageConsumer);
            }
         }
      }
   }

   public static void harvestCube(
      Level world,
      Player player,
      Direction dir,
      BlockPos centralPos,
      Set<Material> effectiveMaterials,
      int planeRadius,
      int depth,
      boolean harvestLevelCheck,
      @Nullable BlockPos excludedBlock,
      ItemStack tool,
      BiConsumer<BlockPos, BlockState> toolDamageConsumer
   ) {
      for (int a = 0; a < depth; a++) {
         int x = 0;
         int y = 0;
         int z = 0;
         if (dir == Direction.UP) {
            y -= a;
         }

         if (dir == Direction.DOWN) {
            y += a;
         }

         if (dir == Direction.SOUTH) {
            z -= a;
         }

         if (dir == Direction.NORTH) {
            z += a;
         }

         if (dir == Direction.EAST) {
            x -= a;
         }

         if (dir == Direction.WEST) {
            x += a;
         }

         harvestPlane(
            world,
            player,
            dir,
            new BlockPos(centralPos).m_7918_(x, y, z),
            effectiveMaterials,
            planeRadius,
            harvestLevelCheck,
            excludedBlock,
            tool,
            toolDamageConsumer
         );
      }
   }
}
