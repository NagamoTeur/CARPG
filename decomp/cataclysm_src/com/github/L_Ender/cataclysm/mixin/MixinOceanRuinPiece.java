package com.github.L_Ender.cataclysm.mixin;

import com.min01.archaeology.init.ArchaeologyBlocks;
import com.min01.archaeology.init.ArchaeologyLootTables;
import com.min01.archaeology.structure.processor.CappedProcessor;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.structures.OceanRuinPieces.OceanRuinPiece;
import net.minecraft.world.level.levelgen.structure.structures.OceanRuinStructure.Type;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosAlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({OceanRuinPiece.class})
public abstract class MixinOceanRuinPiece extends TemplateStructurePiece {
   @Shadow
   @Final
   private Type f_229014_;

   public MixinOceanRuinPiece(
      StructurePieceType type,
      int genDepth,
      StructureTemplateManager manager,
      ResourceLocation location,
      String name,
      StructurePlaceSettings settings,
      BlockPos position
   ) {
      super(type, genDepth, manager, location, name, settings, position);
   }

   @Unique
   private static StructureProcessor archaeology$archyRuleProcessor(Block inputBlock, Block outputBlock, ResourceLocation lootTable) {
      CompoundTag lootTableTag = new CompoundTag();
      lootTableTag.m_128359_("LootTable", lootTable.toString());
      return new CappedProcessor(
         new RuleProcessor(
            List.of(
               new ProcessorRule(
                  new BlockMatchTest(inputBlock), AlwaysTrueTest.f_73954_, PosAlwaysTrueTest.f_74188_, outputBlock.m_49966_(), Optional.of(lootTableTag)
               )
            )
         ),
         ConstantInt.m_146483_(5),
         lootTable
      );
   }

   @Inject(
      method = {"postProcess"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructurePlaceSettings;clearProcessors()Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructurePlaceSettings;",
         shift = Shift.AFTER
      )}
   )
   private void addProccessors(
      WorldGenLevel level,
      StructureManager manager,
      ChunkGenerator generator,
      RandomSource random,
      BoundingBox boundingBox,
      ChunkPos chunkPosition,
      BlockPos position,
      CallbackInfo callback
   ) {
      StructureProcessor suspiciousProcessor = this.f_229014_ == Type.COLD
         ? archaeology$archyRuleProcessor(Blocks.f_49994_, (Block)ArchaeologyBlocks.SUSPICIOUS_GRAVEL.get(), ArchaeologyLootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY)
         : archaeology$archyRuleProcessor(Blocks.f_49992_, (Block)ArchaeologyBlocks.SUSPICIOUS_SAND.get(), ArchaeologyLootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY);
      this.f_73657_.m_74383_(suspiciousProcessor);
   }
}
