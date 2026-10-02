package com.bobmowzie.mowziesmobs.server.item;

import com.bobmowzie.mowziesmobs.server.block.BlockHandler;
import com.bobmowzie.mowziesmobs.server.block.RakedSandBlock;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.Tags.Blocks;

public class ItemSandRake extends Item {
   public ItemSandRake(Properties properties) {
      super(properties);
   }

   public boolean m_6832_(ItemStack thisStack, ItemStack ingredientStack) {
      return ingredientStack.m_204117_(ItemTags.f_13168_) || super.m_6832_(thisStack, ingredientStack);
   }

   public InteractionResult m_6225_(UseOnContext context) {
      Level level = context.m_43725_();
      BlockPos blockpos = context.m_8083_();
      BlockState blockstate = level.m_8055_(blockpos);
      if (context.m_43719_() != Direction.UP) {
         return InteractionResult.PASS;
      } else {
         Player player = context.m_43723_();
         if (player != null) {
            BlockPlaceContext blockPlaceContext = new BlockPlaceContext(
               player,
               context.m_43724_(),
               context.m_43722_(),
               new BlockHitResult(context.m_43720_(), context.m_43719_(), context.m_8083_(), context.m_43721_())
            );
            RakedSandBlock origBlock = null;
            if (blockstate.m_204336_(Blocks.SAND_COLORLESS)) {
               origBlock = (RakedSandBlock)BlockHandler.RAKED_SAND.get();
            } else if (blockstate.m_204336_(Blocks.SAND_RED)) {
               origBlock = (RakedSandBlock)BlockHandler.RED_RAKED_SAND.get();
            }

            if (origBlock != null) {
               BlockState blockState = origBlock.m_5573_(blockPlaceContext);
               if (blockState != null) {
                  level.m_5594_(player, blockpos, (SoundEvent)MMSounds.BLOCK_RAKE_SAND.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
                  if (!level.f_46443_) {
                     level.m_7731_(blockpos, blockState, 11);
                     origBlock.m_6807_(blockState, level, blockpos, blockstate, false);
                     origBlock.updateState(blockState, level, blockpos, false);
                     context.m_43722_().m_41622_(1, player, p_43122_ -> p_43122_.m_21190_(context.m_43724_()));
                  }
               }

               return InteractionResult.m_19078_(level.f_46443_);
            } else {
               return InteractionResult.PASS;
            }
         } else {
            return InteractionResult.PASS;
         }
      }
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      super.m_7373_(stack, worldIn, tooltip, flagIn);
      tooltip.add(Component.m_237115_(this.m_5524_() + ".text.0").m_6270_(ItemHandler.TOOLTIP_STYLE));
   }
}
