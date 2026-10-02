package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.api.source.AbstractSourceMachine;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

public abstract class SourceBlock extends ModBlock implements EntityBlock {
   public SourceBlock(Properties properties, String registry) {
      super(properties);
   }

   public InteractionResult m_6227_(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
      if (!worldIn.f_46443_ && handIn == InteractionHand.MAIN_HAND && worldIn.m_7702_(pos) instanceof AbstractSourceMachine tile) {
         if (player.m_21120_(handIn).m_41720_() == ItemsRegistry.BUCKET_OF_SOURCE.get()) {
            if (tile.getMaxSource() - tile.getSource() >= 1000) {
               tile.addSource(1000);
               if (!player.m_7500_()) {
                  if (player.m_21120_(handIn).m_41613_() == 1) {
                     player.m_21008_(handIn, new ItemStack(Items.f_42446_));
                  } else {
                     player.m_21120_(handIn).m_41774_(1);
                     if (!player.m_36356_(new ItemStack(Items.f_42446_))) {
                        player.m_7197_(new ItemStack(Items.f_42446_), false, false);
                     }
                  }
               }

               return InteractionResult.SUCCESS;
            }

            return super.m_6227_(state, worldIn, pos, player, handIn, hit);
         }

         if (player.m_21120_(handIn).m_41720_() instanceof BucketItem && ((BucketItem)player.m_21120_(handIn).m_41720_()).getFluid() == Fluids.f_76191_) {
            if (tile.getSource() >= 1000) {
               if (player.m_21120_(handIn).m_41613_() == 1) {
                  player.m_21008_(handIn, new ItemStack((ItemLike)ItemsRegistry.BUCKET_OF_SOURCE.get()));
                  tile.removeSource(1000);
                  return InteractionResult.SUCCESS;
               }

               if (player.m_36356_(new ItemStack((ItemLike)ItemsRegistry.BUCKET_OF_SOURCE.get()))) {
                  player.m_21120_(handIn).m_41774_(1);
                  tile.removeSource(1000);
                  return InteractionResult.SUCCESS;
               }
            } else if (tile.getSource() >= 1000 && player.m_21120_(handIn).m_41613_() == 1) {
               tile.removeSource(1000);
               player.m_21008_(player.m_7655_(), new ItemStack((ItemLike)ItemsRegistry.BUCKET_OF_SOURCE.get()));
               return InteractionResult.SUCCESS;
            }
         }
      }

      return super.m_6227_(state, worldIn, pos, player, handIn, hit);
   }
}
