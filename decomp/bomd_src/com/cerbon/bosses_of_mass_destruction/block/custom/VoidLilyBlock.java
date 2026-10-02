package com.cerbon.bosses_of_mass_destruction.block.custom;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VoidLilyBlock extends FlowerBlock implements EntityBlock {
   public VoidLilyBlock(Properties properties) {
      super(MobEffects.f_19619_, 0, properties);
   }

   @Nullable
   public BlockEntity m_142194_(@NotNull BlockPos pos, @NotNull BlockState state) {
      return new VoidLilyBlockEntity(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
      return (level1, pos, state1, blockEntity) -> {
         if (blockEntity instanceof VoidLilyBlockEntity) {
            VoidLilyBlockEntity.tick(level1, pos, state1, (VoidLilyBlockEntity)blockEntity);
         }
      };
   }

   public void m_5871_(@NotNull ItemStack stack, @Nullable BlockGetter level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
      tooltip.add(Component.m_237115_("block.bosses_of_mass_destruction.void_lily.tooltip").m_130940_(ChatFormatting.DARK_GRAY));
   }
}
