package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.api.enchanting_apparatus.IEnchantingRecipe;
import com.hollingsworth.arsnouveau.api.util.SourceUtil;
import com.hollingsworth.arsnouveau.client.util.ColorPos;
import com.hollingsworth.arsnouveau.common.block.tile.ArcanePedestalTile;
import com.hollingsworth.arsnouveau.common.block.tile.EnchantingApparatusTile;
import com.hollingsworth.arsnouveau.common.network.HighlightAreaPacket;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EnchantingApparatusBlock extends TickableModBlock {
   public EnchantingApparatusBlock() {
      this(TickableModBlock.defaultProperties().m_60955_());
   }

   public EnchantingApparatusBlock(Properties properties) {
      super(properties);
   }

   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new EnchantingApparatusTile(pos, state);
   }

   public InteractionResult m_6227_(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
      if (world.f_46443_ || handIn != InteractionHand.MAIN_HAND || !(world.m_7702_(pos) instanceof EnchantingApparatusTile tile)) {
         return InteractionResult.SUCCESS;
      } else if (tile.isCrafting) {
         return InteractionResult.SUCCESS;
      } else if (!(world.m_8055_(pos.m_7495_()).m_60734_() instanceof ArcaneCore)) {
         PortUtil.sendMessage(player, Component.m_237115_("alert.core"));
         return InteractionResult.SUCCESS;
      } else {
         if (tile.getStack() != null && !tile.getStack().m_41619_()) {
            ItemEntity item = new ItemEntity(world, player.m_20185_(), player.m_20186_(), player.m_20189_(), tile.getStack());
            world.m_7967_(item);
            tile.setStack(ItemStack.f_41583_);
            if (tile.attemptCraft(player.m_21205_(), player)) {
               tile.setStack(player.m_150109_().m_7407_(player.m_150109_().f_35977_, 1));
            }
         } else {
            IEnchantingRecipe recipe = tile.getRecipe(player.m_21205_(), player);
            if (recipe == null) {
               List<ColorPos> colorPos = new ArrayList<>();

               for (BlockPos pedPos : tile.pedestalList()) {
                  if (world.m_7702_(pedPos) instanceof ArcanePedestalTile pedestalTile) {
                     colorPos.add(ColorPos.centeredAbove(pedPos));
                  }
               }

               Networking.sendToNearby(world, tile.m_58899_(), new HighlightAreaPacket(colorPos, 60));
               PortUtil.sendMessage(player, Component.m_237115_("ars_nouveau.apparatus.norecipe"));
            } else if (recipe.consumesSource() && !SourceUtil.hasSourceNearby(tile.m_58899_(), tile.m_58904_(), 10, recipe.getSourceCost())) {
               PortUtil.sendMessage(player, Component.m_237115_("ars_nouveau.apparatus.nomana"));
            } else if (tile.attemptCraft(player.m_21205_(), player)) {
               tile.setStack(player.m_150109_().m_7407_(player.m_150109_().f_35977_, 1));
            }
         }

         world.m_7260_(pos, state, state, 2);
         return InteractionResult.SUCCESS;
      }
   }

   public VoxelShape m_5940_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      return Block.m_49796_(1.0, 1.0, 1.0, 15.0, 16.0, 15.0);
   }

   public void m_5707_(Level worldIn, BlockPos pos, BlockState state, Player player) {
      super.m_5707_(worldIn, pos, state, player);
      if (worldIn.m_7702_(pos) instanceof EnchantingApparatusTile tile && tile.getStack() != null) {
         worldIn.m_7967_(new ItemEntity(worldIn, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), tile.getStack()));
      }
   }

   public RenderShape m_7514_(BlockState p_149645_1_) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   public boolean m_7357_(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
      return false;
   }
}
