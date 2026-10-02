package com.cerbon.bosses_of_mass_destruction.mixin;

import com.cerbon.bosses_of_mass_destruction.structure.BMDStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({IceBlock.class})
public class IceBlockMixin {
   @Inject(
      method = {"playerDestroy"},
      at = {@At("TAIL")}
   )
   private void onAfterBreak(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack, CallbackInfo ci) {
      if (EnchantmentHelper.m_44843_(Enchantments.f_44985_, stack) == 0
         && level instanceof ServerLevel serverLevel
         && serverLevel.m_215010_()
            .m_220494_(
               pos,
               (Structure)serverLevel.m_215010_()
                  .m_220521_()
                  .m_175515_(Registry.f_235725_)
                  .m_123013_(BMDStructures.LICH_STRUCTURE_REGISTRY.getConfiguredStructureKey())
            )
            .m_73603_()) {
         level.m_46597_(pos, Blocks.f_50016_.m_49966_());
      }
   }
}
