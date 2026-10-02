package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.api.recipe.MultiRecipeWrapper;
import com.hollingsworth.arsnouveau.common.block.tile.SummoningTile;
import com.hollingsworth.arsnouveau.common.block.tile.WixieCauldronTile;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;

public class WixieCauldron extends SummonBlock {
   public static final BooleanProperty FILLED = BooleanProperty.m_61465_("filled");

   public WixieCauldron() {
      super(defaultProperties().m_60955_());
      this.m_49959_((BlockState)((BlockState)this.m_49966_().m_61124_(SummoningTile.CONVERTED, false)).m_61124_(FILLED, false));
   }

   public InteractionResult m_6227_(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
      if (!worldIn.f_46443_
         && handIn == InteractionHand.MAIN_HAND
         && worldIn.m_7702_(pos) instanceof WixieCauldronTile
         && player.m_21205_().m_41720_() != ItemsRegistry.DOMINION_ROD.get()) {
         if (player.m_21205_().m_41720_() != ItemsRegistry.WIXIE_CHARM.get()
            && !player.m_21205_().m_41619_()
            && worldIn.m_7702_(pos) instanceof WixieCauldronTile cauldronTile) {
            MultiRecipeWrapper wrapper = cauldronTile.getRecipesForStack(player.m_21205_());
            if (wrapper.isEmpty()) {
               PortUtil.sendMessage(player, Component.m_237115_("ars_nouveau.wixie.no_recipe"));
            } else {
               cauldronTile.setSetStack(player.m_21205_().m_41777_());
               PortUtil.sendMessage(player, Component.m_237115_("ars_nouveau.wixie.recipe_set"));
            }

            return InteractionResult.CONSUME;
         } else {
            return InteractionResult.PASS;
         }
      } else {
         return InteractionResult.PASS;
      }
   }

   @Override
   protected void m_7926_(Builder<Block, BlockState> builder) {
      super.m_7926_(builder);
      builder.m_61104_(new Property[]{FILLED});
   }

   public void m_6861_(BlockState state, Level world, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
      super.m_6861_(state, world, pos, blockIn, fromPos, isMoving);
      if (!world.m_5776_() && world.m_7702_(pos) instanceof WixieCauldronTile cauldronTile) {
         cauldronTile.isOff = world.m_46753_(pos);
         cauldronTile.updateBlock();
      }
   }

   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new WixieCauldronTile(pos, state);
   }
}
