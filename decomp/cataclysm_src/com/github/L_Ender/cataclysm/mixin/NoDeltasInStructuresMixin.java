package com.github.L_Ender.cataclysm.mixin;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.util.MixinUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.levelgen.feature.DeltaFeature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.DeltaFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({DeltaFeature.class})
public class NoDeltasInStructuresMixin {
   @Inject(
      method = {"place(Lnet/minecraft/world/level/levelgen/feature/FeaturePlaceContext;)Z"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void cataclysm_noDeltasInStructures(FeaturePlaceContext<DeltaFeatureConfiguration> context, CallbackInfoReturnable<Boolean> cir) {
      if (context.m_159774_() instanceof WorldGenRegion) {
         SectionPos sectionPos = SectionPos.m_123199_(context.m_159777_());
         if (context.m_159774_().m_6522_(sectionPos.m_123170_(), sectionPos.m_123222_(), ChunkStatus.f_62316_, false) == null) {
            Cataclysm.LOGGER
               .warn(
                  "Detected a mod with a broken delta configuredfeature that is trying to place blocks outside the 3x3 safe chunk area for features. Find the broken mod and report to them to fix the placement of their magma feature."
               );
         } else {
            Registry<Structure> configuredStructureFeatureRegistry = context.m_159774_().m_5962_().m_175515_(Registry.f_235725_);
            StructureManager structureManager = ((WorldGenRegionAccessor)context.m_159774_()).getStructureManager();

            for (Holder<Structure> configuredStructureFeature : configuredStructureFeatureRegistry.m_203561_(ModTag.BLOCKED_BASALT)) {
               if (MixinUtil.getStructureAt(structureManager, context.m_159777_(), (Structure)configuredStructureFeature.m_203334_()).m_73603_()) {
                  cir.setReturnValue(false);
               }
            }
         }
      }
   }
}
