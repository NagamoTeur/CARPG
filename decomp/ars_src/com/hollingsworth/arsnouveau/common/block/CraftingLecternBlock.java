package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.common.block.tile.CraftingLecternTile;
import com.hollingsworth.arsnouveau.common.block.tile.StorageLecternTile;
import com.hollingsworth.arsnouveau.common.items.DominionWand;
import com.hollingsworth.arsnouveau.common.items.summon_charms.BookwyrmCharm;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;

public class CraftingLecternBlock extends TickableModBlock {
   public CraftingLecternBlock() {
      super(Properties.m_60939_(Material.f_76320_).m_60978_(3.0F).m_60955_());
   }

   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new CraftingLecternTile(pos, state);
   }

   public void m_5871_(ItemStack stack, BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
   }

   public PushReaction m_5537_(BlockState p_149656_1_) {
      return PushReaction.BLOCK;
   }

   public BlockState m_5573_(BlockPlaceContext pContext) {
      return (BlockState)this.m_49966_().m_61124_(HorizontalDirectionalBlock.f_54117_, pContext.m_8125_().m_122424_());
   }

   public InteractionResult m_6227_(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult rtr) {
      ItemStack heldStack = player.m_21120_(hand);
      if (world.f_46443_) {
         return InteractionResult.SUCCESS;
      } else if (!(heldStack.m_41720_() instanceof DominionWand) && hand == InteractionHand.MAIN_HAND && !(heldStack.m_41720_() instanceof BookwyrmCharm)) {
         if (world.m_7702_(pos) instanceof StorageLecternTile term && !term.openMenu(player)) {
            player.m_5661_(Component.m_237115_("ars_nouveau.invalid_lectern"), true);
         }

         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }

   public void m_6810_(BlockState state, Level world, BlockPos pos, BlockState state2, boolean flag) {
      if (!state.m_60713_(state2.m_60734_())) {
         if (world.m_7702_(pos) instanceof CraftingLecternTile te) {
            Containers.m_19002_(world, pos, te.getCraftingInv());
            world.m_46717_(pos, this);
         }

         super.m_6810_(state, world, pos, state2, flag);
      }
   }

   public RenderShape m_7514_(BlockState p_149645_1_) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{HorizontalDirectionalBlock.f_54117_});
   }

   public BlockState m_6843_(BlockState state, Rotation rot) {
      return (BlockState)state.m_61124_(HorizontalDirectionalBlock.f_54117_, rot.m_55954_((Direction)state.m_61143_(HorizontalDirectionalBlock.f_54117_)));
   }

   public BlockState m_6943_(BlockState state, Mirror mirrorIn) {
      return state.m_60717_(mirrorIn.m_54846_((Direction)state.m_61143_(HorizontalDirectionalBlock.f_54117_)));
   }
}
