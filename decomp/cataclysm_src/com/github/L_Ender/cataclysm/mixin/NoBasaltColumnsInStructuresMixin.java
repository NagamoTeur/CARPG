package com.github.L_Ender.cataclysm.mixin;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.util.MixinUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.levelgen.feature.BasaltColumnsFeature;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({BasaltColumnsFeature.class})
public class NoBasaltColumnsInStructuresMixin {
   @Inject(
      method = {"canPlaceAt(Lnet/minecraft/world/level/LevelAccessor;ILnet/minecraft/core/BlockPos$MutableBlockPos;)Z"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void cataclysm_noBasaltColumnsInStructures(
      LevelAccessor levelAccessor, int seaLevel, MutableBlockPos mutableBlockPos, CallbackInfoReturnable<Boolean> cir
   ) {
      if (levelAccessor instanceof WorldGenRegion) {
         SectionPos sectionPos = SectionPos.m_123199_(mutableBlockPos);
         if (!levelAccessor.m_6325_(sectionPos.m_123170_(), sectionPos.m_123222_()).m_6415_().m_62427_(ChunkStatus.f_62316_)) {
            Cataclysm.LOGGER
               .warn(
                  "Cataclysm: Detected a mod with a broken basalt columns configuredfeature that is trying to place blocks outside the 3x3 safe chunk area for features. Find the broken mod and report to them to fix the placement of their basalt columns feature."
               );
         } else {
            Registry<Structure> configuredStructureFeatureRegistry = levelAccessor.m_5962_().m_175515_(Registry.f_235725_);
            StructureManager structureManager = ((WorldGenRegionAccessor)levelAccessor).getStructureManager();

            for (Holder<Structure> configuredStructureFeature : configuredStructureFeatureRegistry.m_203561_(ModTag.BLOCKED_BASALT)) {
               if (MixinUtil.getStructureAt(structureManager, mutableBlockPos, (Structure)configuredStructureFeature.m_203334_()).m_73603_()) {
                  cir.setReturnValue(false);
               }
            }
         }
      }
   }
}
