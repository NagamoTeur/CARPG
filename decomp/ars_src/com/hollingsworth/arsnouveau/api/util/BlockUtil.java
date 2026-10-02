package com.hollingsworth.arsnouveau.api.util;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CommandBlock;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.ObserverBlock;
import net.minecraft.world.level.block.StructureBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.level.BlockEvent.BreakEvent;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class BlockUtil {
   public static BlockPos toPos(Vec3 vec) {
      return new BlockPos(vec.f_82479_, vec.f_82480_, vec.f_82481_);
   }

   public static boolean isTreeBlock(BlockState block) {
      return block.m_204336_(BlockTags.f_13035_) || block.m_204336_(BlockTags.f_13106_);
   }

   public static boolean containsStateInRadius(Level world, BlockPos start, int radius, Class clazz) {
      for (double x = (double)(start.m_123341_() - radius); x <= (double)(start.m_123341_() + radius); x++) {
         for (double y = (double)(start.m_123342_() - radius); y <= (double)(start.m_123342_() + radius); y++) {
            for (double z = (double)(start.m_123343_() - radius); z <= (double)(start.m_123343_() + radius); z++) {
               BlockPos pos = new BlockPos(x, y, z);
               if (!pos.equals(start) && world.m_8055_(pos).m_60734_().getClass().equals(clazz)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public static double distanceFrom(BlockPos start, BlockPos end) {
      return start != null && end != null
         ? Math.sqrt(
            Math.pow((double)(start.m_123341_() - end.m_123341_()), 2.0)
               + Math.pow((double)(start.m_123342_() - end.m_123342_()), 2.0)
               + Math.pow((double)(start.m_123343_() - end.m_123343_()), 2.0)
         )
         : 0.0;
   }

   public static double distanceFromCenter(BlockPos start, BlockPos end) {
      return start != null && end != null
         ? distanceFrom(
            new Vec3((double)start.m_123341_() + 0.5, (double)start.m_123342_() + 0.5, (double)start.m_123343_() + 0.5),
            new Vec3((double)end.m_123341_() + 0.5, (double)end.m_123342_() + 0.5, (double)end.m_123343_() + 0.5)
         )
         : 0.0;
   }

   public static double distanceFrom(Vec3 start, BlockPos end) {
      return start != null && end != null
         ? Math.sqrt(
            Math.pow(start.f_82479_ - (double)end.m_123341_(), 2.0)
               + Math.pow(start.f_82480_ - (double)end.m_123342_(), 2.0)
               + Math.pow(start.f_82481_ - (double)end.m_123343_(), 2.0)
         )
         : 0.0;
   }

   public static double distanceFrom(Vec3 start, Vec3 end) {
      return Math.sqrt(
         Math.pow(start.f_82479_ - end.f_82479_, 2.0) + Math.pow(start.f_82480_ - end.f_82480_, 2.0) + Math.pow(start.f_82481_ - end.f_82481_, 2.0)
      );
   }

   public static boolean destroyBlockSafely(Level world, BlockPos pos, boolean dropBlock, LivingEntity caster) {
      if (!(world instanceof ServerLevel)) {
         return false;
      } else {
         Player playerEntity = (Player)(caster instanceof Player ? (Player)caster : ANFakePlayer.getPlayer((ServerLevel)world));
         if (MinecraftForge.EVENT_BUS.post(new BreakEvent(world, pos, world.m_8055_(pos), playerEntity))) {
            return false;
         } else {
            world.m_8055_(pos).m_60734_().m_5707_(world, pos, world.m_8055_(pos), playerEntity);
            return world.m_46961_(pos, dropBlock);
         }
      }
   }

   public static boolean destroyRespectsClaim(LivingEntity caster, Level world, BlockPos pos) {
      Player playerEntity = (Player)(caster instanceof Player ? (Player)caster : ANFakePlayer.getPlayer((ServerLevel)world));
      return !MinecraftForge.EVENT_BUS.post(new BreakEvent(world, pos, world.m_8055_(pos), playerEntity));
   }

   public static void safelyUpdateState(Level world, BlockPos pos, BlockState state) {
      if (!world.m_151570_(pos)) {
         world.m_7260_(pos, state, state, 3);
      }
   }

   public static void safelyUpdateState(Level world, BlockPos pos) {
      safelyUpdateState(world, pos, world.m_8055_(pos));
   }

   public static boolean destroyBlockSafelyWithoutSound(Level world, BlockPos pos, boolean dropBlock) {
      return destroyBlockWithoutSound(world, pos, dropBlock, null);
   }

   public static boolean destroyBlockSafelyWithoutSound(Level world, BlockPos pos, boolean dropBlock, @Nullable LivingEntity caster) {
      if (!(world instanceof ServerLevel)) {
         return false;
      } else {
         Player playerEntity = (Player)(caster instanceof Player ? (Player)caster : ANFakePlayer.getPlayer((ServerLevel)world));
         return MinecraftForge.EVENT_BUS.post(new BreakEvent(world, pos, world.m_8055_(pos), playerEntity))
            ? false
            : destroyBlockWithoutSound(world, pos, dropBlock);
      }
   }

   private static boolean destroyBlockWithoutSound(Level world, BlockPos pos, boolean dropBlock) {
      return destroyBlockWithoutSound(world, pos, dropBlock, null);
   }

   private static boolean destroyBlockWithoutSound(Level world, BlockPos pos, boolean isMoving, @Nullable Entity entityIn) {
      BlockState blockstate = world.m_8055_(pos);
      if (blockstate.m_60795_()) {
         return false;
      } else {
         FluidState ifluidstate = world.m_6425_(pos);
         if (isMoving) {
            BlockEntity tileentity = blockstate.m_155947_() ? world.m_7702_(pos) : null;
            Block.m_49881_(blockstate, world, pos, tileentity, entityIn, ItemStack.f_41583_);
         }

         return world.m_7731_(pos, ifluidstate.m_76188_(), 3);
      }
   }

   public static List<IItemHandler> getAdjacentInventories(Level world, BlockPos pos) {
      if (world != null && pos != null) {
         ArrayList<IItemHandler> iInventories = new ArrayList<>();

         for (Direction d : Direction.values()) {
            BlockEntity tileEntity = world.m_7702_(pos.m_121945_(d));
            if (tileEntity != null) {
               tileEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iInventories::add);
            }
         }

         return iInventories;
      } else {
         return new ArrayList<>();
      }
   }

   public static ItemStack insertItemAdjacent(Level world, BlockPos pos, ItemStack stack) {
      for (IItemHandler i : getAdjacentInventories(world, pos)) {
         if (stack == ItemStack.f_41583_ || stack == null) {
            break;
         }

         stack = ItemHandlerHelper.insertItemStacked(i, stack, false);
      }

      return stack;
   }

   public static ItemStack getItemAdjacent(Level world, BlockPos pos, Predicate<ItemStack> matchPredicate) {
      ItemStack stack = ItemStack.f_41583_;

      for (IItemHandler inv : getAdjacentInventories(world, pos)) {
         for (int i = 0; i < inv.getSlots(); i++) {
            if (matchPredicate.test(inv.getStackInSlot(i))) {
               return inv.getStackInSlot(i);
            }
         }
      }

      return stack;
   }

   public static boolean canBlockBeHarvested(SpellStats stats, Level world, BlockPos pos) {
      return world.m_8055_(pos).m_60800_(world, pos) >= 0.0F && SpellUtil.isCorrectHarvestLevel(getBaseHarvestLevel(stats), world.m_8055_(pos));
   }

   public static int getBaseHarvestLevel(SpellStats stats) {
      return (int)(3.0 + stats.getAmpMultiplier());
   }

   public static List<BlockPos> getLine(int x0, int y0, int x1, int y1, float wd) {
      List<BlockPos> vects = new ArrayList<>();
      int dx = Math.abs(x1 - x0);
      int sx = x0 < x1 ? 1 : -1;
      int dy = Math.abs(y1 - y0);
      int sy = y0 < y1 ? 1 : -1;
      int err = dx - dy;
      float ed = dx + dy == 0 ? 1.0F : Mth.m_14116_((float)dx * (float)dx + (float)dy * (float)dy);
      wd = (wd + 1.0F) / 2.0F;

      while (true) {
         vects.add(new BlockPos(x0, 0, y0));
         int e2 = err;
         int x2 = x0;
         if (2 * err >= -dx) {
            e2 = err + dy;

            for (int y2 = y0; (float)e2 < ed * wd && (y1 != y2 || dx > dy); e2 += dx) {
               vects.add(new BlockPos(x0, 0, y2 += sy));
            }

            if (x0 == x1) {
               break;
            }

            e2 = err;
            err -= dy;
            x0 += sx;
         }

         if (2 * e2 <= dy) {
            for (int var17 = dx - e2; (float)var17 < ed * wd && (x1 != x2 || dx < dy); var17 += dy) {
               vects.add(new BlockPos(x2 += sx, 0, y0));
            }

            if (y0 == y1) {
               break;
            }

            err += dx;
            y0 += sy;
         }
      }

      return vects;
   }

   @Nullable
   public static BlockPos scanForBlockNearPoint(Level world, BlockPos point, int radiusX, int radiusY, int radiusZ, int height) {
      BlockPos closestCoords = null;
      double minDistance = Double.MAX_VALUE;

      for (int j = point.m_123342_(); j <= point.m_123342_() + radiusY; j++) {
         for (int i = point.m_123341_() - radiusX; i <= point.m_123341_() + radiusX; i++) {
            for (int k = point.m_123343_() - radiusZ; k <= point.m_123343_() + radiusZ; k++) {
               if (wontSuffocate(world, i, j, k, height)) {
                  BlockPos tempCoords = new BlockPos(i, j, k);
                  if (world.m_8055_(tempCoords.m_7495_()).m_60767_().m_76333_() || world.m_8055_(tempCoords.m_6625_(2)).m_60767_().m_76333_()) {
                     double distance = (double)getDistanceSquared(tempCoords, point);
                     if (closestCoords == null || distance < minDistance) {
                        closestCoords = tempCoords;
                        minDistance = distance;
                     }
                  }
               }
            }
         }
      }

      return closestCoords;
   }

   private static boolean wontSuffocate(Level world, int x, int y, int z, int height) {
      for (int dy = 0; dy < height; dy++) {
         BlockState state = world.m_8055_(new BlockPos(x, y + dy, z));
         if (state.m_60767_().m_76334_()) {
            return false;
         }
      }

      return true;
   }

   public static long getDistanceSquared(BlockPos block1, BlockPos block2) {
      long xDiff = (long)block1.m_123341_() - (long)block2.m_123341_();
      long yDiff = (long)block1.m_123342_() - (long)block2.m_123342_();
      long zDiff = (long)block1.m_123343_() - (long)block2.m_123343_();
      long result = xDiff * xDiff + yDiff * yDiff + zDiff * zDiff;
      if (result < 0L) {
         throw new IllegalStateException("max-sqrt is to high! Failure to catch overflow with " + xDiff + " | " + yDiff + " | " + zDiff);
      } else {
         return result;
      }
   }

   public static void updateObservers(Level level, BlockPos pos) {
      for (Direction d : Direction.values()) {
         BlockPos adjacentPos = pos.m_121945_(d);
         if (level.m_8055_(adjacentPos).m_60734_() instanceof ObserverBlock) {
            BlockState observer = level.m_8055_(adjacentPos);
            if (adjacentPos.m_121945_((Direction)observer.m_61143_(ObserverBlock.f_52588_)).equals(pos)) {
               level.m_186460_(pos.m_121945_(d), level.m_8055_(pos.m_121945_(d)).m_60734_(), 2);
            }
         }
      }
   }

   public static boolean breakExtraBlock(ServerLevel world, BlockPos pos, ItemStack mainhand, @Nullable UUID source, boolean bypassToolCheck) {
      BlockState blockstate = world.m_8055_(pos);
      FakePlayer player;
      if (source != null) {
         player = FakePlayerFactory.get(world, new GameProfile(source, UsernameCache.getLastKnownUsername(source)));
         Player realPlayer = world.m_46003_(source);
         if (realPlayer != null) {
            player.m_146884_(realPlayer.m_20182_());
         }
      } else {
         player = FakePlayerFactory.getMinecraft(world);
      }

      player.m_150109_().f_35974_.set(player.m_150109_().f_35977_, mainhand);
      if (!(blockstate.m_60800_(world, pos) < 0.0F) && (blockstate.canHarvestBlock(world, pos, player) || bypassToolCheck)) {
         GameType type = player.m_150110_().f_35937_ ? GameType.CREATIVE : GameType.SURVIVAL;
         int exp = ForgeHooks.onBlockBreakEvent(world, type, player, pos);
         if (exp == -1) {
            return false;
         } else {
            BlockEntity tileentity = world.m_7702_(pos);
            Block block = blockstate.m_60734_();
            if ((block instanceof CommandBlock || block instanceof StructureBlock || block instanceof JigsawBlock) && !player.m_36337_()) {
               world.m_7260_(pos, blockstate, blockstate, 3);
               return false;
            } else if (player.m_21205_().onBlockStartBreak(pos, player)) {
               return false;
            } else if (player.m_36187_(world, pos, type)) {
               return false;
            } else if (player.m_150110_().f_35937_) {
               removeBlock(world, player, pos, false);
               return true;
            } else {
               ItemStack copyMain = mainhand.m_41777_();
               boolean canHarvest = blockstate.canHarvestBlock(world, pos, player) || bypassToolCheck;
               mainhand.m_41686_(world, blockstate, pos, player);
               if (mainhand.m_41619_() && !copyMain.m_41619_()) {
                  ForgeEventFactory.onPlayerDestroyItem(player, copyMain, InteractionHand.MAIN_HAND);
               }

               boolean removed = removeBlock(world, player, pos, canHarvest);
               if (removed && canHarvest) {
                  block.m_6240_(world, player, pos, blockstate, tileentity, copyMain);
               }

               if (removed && exp > 0) {
                  blockstate.m_60734_().m_49805_(world, pos, exp);
               }

               return true;
            }
         }
      } else {
         return false;
      }
   }

   public static boolean removeBlock(ServerLevel world, ServerPlayer player, BlockPos pos, boolean canHarvest) {
      BlockState state = world.m_8055_(pos);
      boolean removed = state.onDestroyedByPlayer(world, pos, player, canHarvest, world.m_6425_(pos));
      if (removed) {
         state.m_60734_().m_6786_(world, pos, state);
      }

      return removed;
   }

   private BlockUtil() {
   }
}
