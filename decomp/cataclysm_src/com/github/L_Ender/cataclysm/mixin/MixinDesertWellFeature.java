package com.github.L_Ender.cataclysm.mixin;

import com.min01.archaeology.init.ArchaeologyBlockEntityType;
import com.min01.archaeology.init.ArchaeologyBlocks;
import com.min01.archaeology.init.ArchaeologyLootTables;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.DesertWellFeature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({DesertWellFeature.class})
public abstract class MixinDesertWellFeature {
   @Inject(
      method = {"place"},
      at = {@At("TAIL")}
   )
   private void place(FeaturePlaceContext<NoneFeatureConfiguration> context, CallbackInfoReturnable<Boolean> callback) {
      if ((Boolean)callback.getReturnValue()) {
         BlockPos origin = context.m_159777_();
         List<BlockPos> positions = List.of(origin, origin.m_122029_(), origin.m_122019_(), origin.m_122024_(), origin.m_122012_());
         archaeology$placeSuspiciousSand(context.m_159774_(), ((BlockPos)Util.m_214621_(positions, context.m_225041_())).m_6625_(1));
         archaeology$placeSuspiciousSand(context.m_159774_(), ((BlockPos)Util.m_214621_(positions, context.m_225041_())).m_6625_(2));
      }
   }

   @Unique
   private static void archaeology$placeSuspiciousSand(WorldGenLevel level, BlockPos position) {
      level.m_7731_(position, ((Block)ArchaeologyBlocks.SUSPICIOUS_SAND.get()).m_49966_(), 3);
      level.m_141902_(position, (BlockEntityType)ArchaeologyBlockEntityType.BRUSHABLE_BLOCK.get())
         .ifPresent(brushableEntity -> brushableEntity.setLootTable(ArchaeologyLootTables.DESERT_WELL_ARCHAEOLOGY, position.m_121878_()));
   }
}
