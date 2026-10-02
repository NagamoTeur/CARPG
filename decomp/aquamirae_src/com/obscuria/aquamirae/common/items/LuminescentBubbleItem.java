package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

public class LuminescentBubbleItem extends Item {
   public LuminescentBubbleItem() {
      super(new Properties().m_41491_(Aquamirae.TAB).m_41487_(16).m_41497_(Rarity.COMMON));
   }

   @NotNull
   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player entity, @NotNull InteractionHand hand) {
      ItemStack stack = entity.m_21120_(hand);
      if (world.m_8055_(new BlockPos(entity.m_146903_(), entity.m_146904_() + 1, entity.m_146907_())).m_60767_().m_76332_()) {
         stack.m_41774_(1);
         world.m_7731_(
            new BlockPos(entity.m_146903_(), entity.m_146904_() + 1, entity.m_146907_()),
            (BlockState)((Block)AquamiraeBlocks.LUMINESCENT_BUBBLE.get()).m_49966_().m_61124_(BlockStateProperties.f_61362_, true),
            3
         );
         if (world instanceof ServerLevel level) {
            level.m_5594_(
               null, new BlockPos(entity.m_20185_(), entity.m_20186_() + 1.0, entity.m_20189_()), SoundEvents.f_11773_, SoundSource.BLOCKS, 2.0F, 1.0F
            );
         }

         return InteractionResultHolder.m_19090_(stack);
      } else {
         return InteractionResultHolder.m_19100_(stack);
      }
   }
}
