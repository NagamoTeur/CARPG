package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.client.util.ColorPos;
import com.hollingsworth.arsnouveau.common.block.tile.ArcanePedestalTile;
import com.hollingsworth.arsnouveau.common.block.tile.ImbuementTile;
import com.hollingsworth.arsnouveau.common.crafting.recipes.ImbuementRecipe;
import com.hollingsworth.arsnouveau.common.network.HighlightAreaPacket;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.RecipeRegistry;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class ImbuementBlock extends TickableModBlock {
   public ImbuementBlock() {
      super(defaultProperties().m_60955_());
   }

   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new ImbuementTile(pos, state);
   }

   public RenderShape m_7514_(BlockState p_149645_1_) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   public InteractionResult m_6227_(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
      if (!(worldIn.m_7702_(pos) instanceof ImbuementTile tile)) {
         return InteractionResult.SUCCESS;
      } else if (!worldIn.f_46443_ && handIn == InteractionHand.MAIN_HAND) {
         if (tile.stack.m_41619_() && !player.m_21120_(handIn).m_41619_()) {
            tile.stack = player.m_21120_(handIn).m_41777_();
            ImbuementRecipe recipe = worldIn.m_7465_()
               .m_44013_((RecipeType)RecipeRegistry.IMBUEMENT_TYPE.get())
               .stream()
               .filter(f -> f.matches(tile, worldIn))
               .findFirst()
               .orElse(null);
            if (recipe == null) {
               List<ColorPos> colorPos = new ArrayList<>();

               for (BlockPos pedPos : tile.getNearbyPedestals()) {
                  if (worldIn.m_7702_(pedPos) instanceof ArcanePedestalTile pedestalTile) {
                     colorPos.add(ColorPos.centeredAbove(pedPos));
                  }
               }

               Networking.sendToNearby(worldIn, tile.m_58899_(), new HighlightAreaPacket(colorPos, 60));
               PortUtil.sendMessage(player, Component.m_237115_("ars_nouveau.imbuement.norecipe"));
               tile.stack = ItemStack.f_41583_;
            } else {
               tile.stack = player.m_150109_().m_7407_(player.m_150109_().f_35977_, 1);
               PortUtil.sendMessageNoSpam(player, Component.m_237110_("ars_nouveau.imbuement.crafting_started", new Object[]{recipe.output.m_41786_()}));
               tile.updateBlock();
            }
         } else {
            ItemEntity item = new ItemEntity(worldIn, player.m_20185_(), player.m_20186_(), player.m_20189_(), tile.stack.m_41777_());
            worldIn.m_7967_(item);
            tile.stack = ItemStack.f_41583_;
            tile.stack = player.m_150109_().m_36056_().m_41777_();
            ImbuementRecipe recipe = worldIn.m_7465_()
               .m_44013_((RecipeType)RecipeRegistry.IMBUEMENT_TYPE.get())
               .stream()
               .filter(f -> f.matches(tile, worldIn))
               .findFirst()
               .orElse(null);
            if (recipe != null) {
               tile.stack = player.m_150109_().m_7407_(player.m_150109_().f_35977_, 1);
            } else {
               tile.stack = ItemStack.f_41583_;
            }

            tile.draining = false;
            tile.updateBlock();
         }

         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.SUCCESS;
      }
   }

   public void m_5707_(Level worldIn, BlockPos pos, BlockState state, Player player) {
      super.m_5707_(worldIn, pos, state, player);
      if (worldIn.m_7702_(pos) instanceof ImbuementTile) {
         ItemStack stack = ((ImbuementTile)worldIn.m_7702_(pos)).stack;
         worldIn.m_7967_(new ItemEntity(worldIn, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), stack.m_41777_()));
         ((ImbuementTile)worldIn.m_7702_(pos)).stack = ItemStack.f_41583_;
      }
   }
}
