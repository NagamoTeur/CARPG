package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.api.item.IScribeable;
import com.hollingsworth.arsnouveau.common.block.tile.ScribesTile;
import com.hollingsworth.arsnouveau.common.items.DominionWand;
import com.hollingsworth.arsnouveau.common.items.SpellBook;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketOpenGlyphCraft;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.PacketDistributor;

public class ScribesBlock extends TableBlock {
   public ScribesBlock() {
      MinecraftForge.EVENT_BUS.register(this);
   }

   public InteractionResult m_6227_(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
      if (world.f_46443_ || handIn != InteractionHand.MAIN_HAND || !(world.m_7702_(pos) instanceof ScribesTile tile)) {
         return InteractionResult.PASS;
      } else if (player.m_21120_(handIn).m_41720_() instanceof SpellBook && !player.m_6144_()) {
         Networking.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer)player), new PacketOpenGlyphCraft(pos));
         return InteractionResult.SUCCESS;
      } else {
         if (state.m_61143_(PART) != ThreePartBlock.HEAD) {
            BlockEntity tileEntity = world.m_7702_(pos.m_121945_(getConnectedDirection(state)));
            tile = tileEntity instanceof ScribesTile ? (ScribesTile)tileEntity : null;
            if (tile == null) {
               return InteractionResult.PASS;
            }
         }

         if (!player.m_6144_()) {
            if (tile.consumeStack(player.m_21120_(handIn))) {
               return InteractionResult.SUCCESS;
            }

            if (!tile.getStack().m_41619_() && player.m_21120_(handIn).m_41619_()) {
               ItemEntity item = new ItemEntity(world, player.m_20185_(), player.m_20186_(), player.m_20189_(), tile.getStack());
               world.m_7967_(item);
               tile.setStack(ItemStack.f_41583_);
            } else if (!player.m_150109_().m_36056_().m_41619_()) {
               if (!tile.getStack().m_41619_()) {
                  ItemEntity item = new ItemEntity(world, player.m_20185_(), player.m_20186_(), player.m_20189_(), tile.getStack());
                  world.m_7967_(item);
               }

               tile.setStack(player.m_150109_().m_7407_(player.m_150109_().f_35977_, 1));
            }

            BlockState updateState = world.m_8055_(tile.m_58899_());
            world.m_7260_(tile.m_58899_(), updateState, updateState, 2);
         }

         if (player.m_6144_()) {
            ItemStack stack = tile.getStack();
            if (player.m_21120_(handIn).m_41720_() instanceof DominionWand) {
               return InteractionResult.PASS;
            }

            if (stack == null || stack.m_41619_()) {
               return InteractionResult.SUCCESS;
            }

            if (stack.m_41720_() instanceof IScribeable scribeable) {
               scribeable.onScribe(world, pos, player, handIn, stack);
               BlockState updateState = world.m_8055_(tile.m_58899_());
               world.m_7260_(tile.m_58899_(), updateState, updateState, 2);
            }
         }

         return InteractionResult.SUCCESS;
      }
   }

   public void m_5707_(Level worldIn, BlockPos pos, BlockState state, Player player) {
      super.m_5707_(worldIn, pos, state, player);
      if (worldIn.m_7702_(pos) instanceof ScribesTile tile && tile.getStack() != null) {
         worldIn.m_7967_(new ItemEntity(worldIn, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), tile.getStack()));
         tile.refundConsumed();
      }
   }

   @Override
   public BlockState tearDown(BlockState state, Direction direction, BlockState state2, LevelAccessor world, BlockPos pos, BlockPos pos2) {
      if (!world.m_5776_()) {
         BlockEntity entity = world.m_7702_(pos);
         if (entity instanceof ScribesTile tile && ((ScribesTile)entity).getStack() != null) {
            world.m_7967_(
               new ItemEntity((Level)world, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), ((ScribesTile)entity).getStack())
            );
            tile.refundConsumed();
         }
      }

      return Blocks.f_50016_.m_49966_();
   }

   @SubscribeEvent
   public void rightClick(RightClickBlock event) {
      if (event.getLevel().m_7702_(event.getPos()) instanceof ScribesTile) {
         Level world = event.getLevel();
         BlockPos pos = event.getPos();
         if (world.m_8055_(pos).m_60734_() instanceof ScribesBlock) {
            if (event.getEntity().m_21120_(event.getHand()).m_41720_() instanceof DominionWand) {
               return;
            }

            BlockRegistry.SCRIBES_BLOCK.m_6227_(world.m_8055_(pos), world, pos, event.getEntity(), event.getHand(), null);
            event.setCanceled(true);
         }
      }
   }

   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new ScribesTile(pos, state);
   }

   public boolean m_7357_(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
      return false;
   }
}
