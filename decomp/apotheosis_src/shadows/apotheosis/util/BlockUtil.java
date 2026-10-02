package shadows.apotheosis.util;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CommandBlock;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.StructureBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.ForgeEventFactory;

public class BlockUtil {
   public static boolean breakExtraBlock(ServerLevel world, BlockPos pos, ItemStack mainhand, @Nullable UUID source) {
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
      if (!(blockstate.m_60800_(world, pos) < 0.0F) && blockstate.canHarvestBlock(world, pos, player)) {
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
               ItemStack itemstack = player.m_21205_();
               ItemStack itemstack1 = itemstack.m_41777_();
               boolean canHarvest = blockstate.canHarvestBlock(world, pos, player);
               itemstack.m_41686_(world, blockstate, pos, player);
               if (itemstack.m_41619_() && !itemstack1.m_41619_()) {
                  ForgeEventFactory.onPlayerDestroyItem(player, itemstack1, InteractionHand.MAIN_HAND);
               }

               boolean removed = removeBlock(world, player, pos, canHarvest);
               if (removed && canHarvest) {
                  block.m_6240_(world, player, pos, blockstate, tileentity, itemstack1);
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
}
