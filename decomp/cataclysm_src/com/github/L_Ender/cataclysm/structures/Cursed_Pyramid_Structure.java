package com.github.L_Ender.cataclysm.structures;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.Koboleton_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Kobolediator_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Wadjet_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Ancient_Remnant.Ancient_Remnant_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModStructures;
import com.github.L_Ender.cataclysm.world.structures.Processor.WaterLoggingFixProcessor;
import com.google.common.collect.ImmutableMap;
import com.min01.archaeology.init.ArchaeologyBlockEntityType;
import com.min01.archaeology.init.ArchaeologyBlocks;
import com.mojang.serialization.Codec;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProtectedBlockProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class Cursed_Pyramid_Structure extends CataclysmStructure {
   public static final Codec<Cursed_Pyramid_Structure> CODEC = m_226607_(Cursed_Pyramid_Structure::new);
   private static final ResourceLocation LOWER1 = new ResourceLocation("cataclysm", "cursed_pyramid_lower1");
   private static final ResourceLocation LOWER2 = new ResourceLocation("cataclysm", "cursed_pyramid_lower2");
   private static final ResourceLocation LOWER3 = new ResourceLocation("cataclysm", "cursed_pyramid_lower3");
   private static final ResourceLocation LOWER4 = new ResourceLocation("cataclysm", "cursed_pyramid_lower4");
   private static final ResourceLocation UPPER1 = new ResourceLocation("cataclysm", "cursed_pyramid_upper1");
   private static final ResourceLocation UPPER2 = new ResourceLocation("cataclysm", "cursed_pyramid_upper2");
   private static final ResourceLocation UPPER3 = new ResourceLocation("cataclysm", "cursed_pyramid_upper3");
   private static final ResourceLocation UPPER4 = new ResourceLocation("cataclysm", "cursed_pyramid_upper4");
   private static final ResourceLocation OBELISK1 = new ResourceLocation("cataclysm", "cursed_pyramid_obelisk1");
   private static final ResourceLocation OBELISK2 = new ResourceLocation("cataclysm", "cursed_pyramid_obelisk2");
   private static final Map<ResourceLocation, BlockPos> OFFSET = ImmutableMap.builder()
      .put(LOWER1, new BlockPos(0, 1, 0))
      .put(LOWER2, new BlockPos(0, 1, 0))
      .put(LOWER3, new BlockPos(0, 1, 0))
      .put(LOWER4, new BlockPos(0, 1, 0))
      .put(UPPER1, new BlockPos(0, 1, 0))
      .put(UPPER2, new BlockPos(0, 1, 0))
      .put(UPPER3, new BlockPos(0, 1, 0))
      .put(UPPER4, new BlockPos(0, 1, 0))
      .put(OBELISK1, new BlockPos(0, 1, 0))
      .put(OBELISK2, new BlockPos(0, 1, 0))
      .build();

   public Cursed_Pyramid_Structure(StructureSettings p_227593_) {
      super(p_227593_);
   }

   private static BlockPos posToSurface(ChunkGenerator generator, BlockPos pos, LevelHeightAccessor heightAccessor, RandomState state) {
      int surfaceY = generator.m_214096_(pos.m_123341_(), pos.m_123343_(), Types.WORLD_SURFACE_WG, heightAccessor, state);
      return new BlockPos(pos.m_123341_(), surfaceY - 1, pos.m_123343_());
   }

   @Override
   public void generatePieces(StructurePiecesBuilder builder, GenerationContext context) {
      StructureTemplateManager templateManager = context.f_226625_();
      Rotation rotation = Rotation.values()[context.f_226626_().m_188503_(Rotation.values().length)];
      int x = (context.f_226628_().f_45578_ << 4) + 7;
      int z = (context.f_226628_().f_45579_ << 4) + 7;
      BlockPos centerPos = new BlockPos(x, 1, z);
      ChunkGenerator generator = context.f_226622_();
      LevelHeightAccessor heightLimitView = context.f_226629_();
      int surfaceY = generator.m_214096_(centerPos.m_123341_(), centerPos.m_123343_(), Types.WORLD_SURFACE_WG, heightLimitView, context.f_226624_());
      int oceanFloorY = generator.m_214096_(centerPos.m_123341_(), centerPos.m_123343_(), Types.OCEAN_FLOOR_WG, heightLimitView, context.f_226624_());
      if (oceanFloorY >= surfaceY) {
         BlockPos spawncenterPos = posToSurface(generator, centerPos, heightLimitView, context.f_226624_());
         BlockPos obelisk1Offset = spawncenterPos.m_121955_(new BlockPos(20, -4, 94).m_7954_(rotation));
         BlockPos obelisk2Offset = spawncenterPos.m_121955_(new BlockPos(45, -4, 94).m_7954_(rotation));
         BlockPos lower1Offset = spawncenterPos.m_7918_(0, -39, 0);
         BlockPos lower2Offset = spawncenterPos.m_121955_(new BlockPos(0, -39, 47).m_7954_(rotation));
         BlockPos lower3Offset = spawncenterPos.m_121955_(new BlockPos(47, -39, 0).m_7954_(rotation));
         BlockPos lower4Offset = spawncenterPos.m_121955_(new BlockPos(47, -39, 47).m_7954_(rotation));
         BlockPos upper1Offset = spawncenterPos.m_7918_(0, 9, 0);
         BlockPos upper2Offset = spawncenterPos.m_121955_(new BlockPos(0, 9, 47).m_7954_(rotation));
         BlockPos upper3Offset = spawncenterPos.m_121955_(new BlockPos(47, 9, 0).m_7954_(rotation));
         BlockPos upper4Offset = spawncenterPos.m_121955_(new BlockPos(47, 9, 47).m_7954_(rotation));
         builder.m_142679_(new Cursed_Pyramid_Structure.Piece(templateManager, LOWER1, lower1Offset, rotation));
         builder.m_142679_(new Cursed_Pyramid_Structure.Piece(templateManager, LOWER2, lower2Offset, rotation));
         builder.m_142679_(new Cursed_Pyramid_Structure.Piece(templateManager, LOWER3, lower3Offset, rotation));
         builder.m_142679_(new Cursed_Pyramid_Structure.Piece(templateManager, LOWER4, lower4Offset, rotation));
         builder.m_142679_(new Cursed_Pyramid_Structure.Piece(templateManager, UPPER1, upper1Offset, rotation));
         builder.m_142679_(new Cursed_Pyramid_Structure.Piece(templateManager, UPPER2, upper2Offset, rotation));
         builder.m_142679_(new Cursed_Pyramid_Structure.Piece(templateManager, UPPER3, upper3Offset, rotation));
         builder.m_142679_(new Cursed_Pyramid_Structure.Piece(templateManager, UPPER4, upper4Offset, rotation));
         builder.m_142679_(new Cursed_Pyramid_Structure.Piece(templateManager, OBELISK1, obelisk1Offset, rotation));
         builder.m_142679_(new Cursed_Pyramid_Structure.Piece(templateManager, OBELISK2, obelisk2Offset, rotation));
      }
   }

   public StructureType<?> m_213658_() {
      return (StructureType<?>)ModStructures.CURSED_PYRAMID.get();
   }

   @Override
   public Decoration m_226619_() {
      return Decoration.SURFACE_STRUCTURES;
   }

   public static class Piece extends TemplateStructurePiece {
      public Piece(StructureTemplateManager templateManagerIn, ResourceLocation resourceLocationIn, BlockPos pos, Rotation rotation) {
         super(
            (StructurePieceType)ModStructures.CPD.get(),
            0,
            templateManagerIn,
            resourceLocationIn,
            resourceLocationIn.toString(),
            makeSettings(rotation),
            makecenterPos(resourceLocationIn, pos)
         );
      }

      public Piece(StructureTemplateManager templateManagerIn, CompoundTag tagCompound) {
         super(
            (StructurePieceType)ModStructures.CPD.get(),
            tagCompound,
            templateManagerIn,
            p_162451_ -> makeSettings(Rotation.valueOf(tagCompound.m_128461_("Rot")))
         );
      }

      public Piece(StructurePieceSerializationContext context, CompoundTag tag) {
         this(context.f_226956_(), tag);
      }

      private static StructurePlaceSettings makeSettings(Rotation p_163156_) {
         BlockIgnoreProcessor blockignoreprocessor = BlockIgnoreProcessor.f_74046_;
         return new StructurePlaceSettings()
            .m_74379_(p_163156_)
            .m_74377_(Mirror.NONE)
            .m_74383_(blockignoreprocessor)
            .m_74383_(new WaterLoggingFixProcessor())
            .m_74383_(new ProtectedBlockProcessor(BlockTags.f_144287_));
      }

      private static BlockPos makecenterPos(ResourceLocation p_162453_, BlockPos p_162454_) {
         return p_162454_.m_121955_((Vec3i)Cursed_Pyramid_Structure.OFFSET.get(p_162453_));
      }

      protected void m_183620_(StructurePieceSerializationContext p_162444_, CompoundTag tagCompound) {
         super.m_183620_(p_162444_, tagCompound);
         tagCompound.m_128359_("Rot", this.f_73657_.m_74404_().name());
      }

      protected void m_213704_(String function, BlockPos pos, ServerLevelAccessor worldIn, RandomSource rand, BoundingBox sbb) {
         switch (function) {
            case "necklace":
               worldIn.m_7731_(pos, ((Block)ArchaeologyBlocks.SUSPICIOUS_SAND.get()).m_49966_(), 2);
               worldIn.m_141902_(pos, (BlockEntityType)ArchaeologyBlockEntityType.BRUSHABLE_BLOCK.get()).ifPresent(blockEntity -> {
                  ResourceLocation lootTableLocation = new ResourceLocation("cataclysm", "archaeology/cursed_pyramid_necklace");
                  blockEntity.setLootTable(lootTableLocation, pos.m_121878_());
               });
               break;
            case "sus":
               worldIn.m_7731_(pos, ((Block)ArchaeologyBlocks.SUSPICIOUS_SAND.get()).m_49966_(), 2);
               worldIn.m_141902_(pos, (BlockEntityType)ArchaeologyBlockEntityType.BRUSHABLE_BLOCK.get()).ifPresent(blockEntity -> {
                  ResourceLocation lootTableLocation = new ResourceLocation("cataclysm", "archaeology/cursed_pyramid");
                  blockEntity.setLootTable(lootTableLocation, pos.m_121878_());
               });
               break;
            case "koboleton":
               Koboleton_Entity koboleton = (Koboleton_Entity)((EntityType)ModEntities.KOBOLETON.get()).m_20615_(worldIn.m_6018_());
               if (koboleton != null) {
                  koboleton.m_21530_();
                  koboleton.m_20035_(pos, 0.0F, 0.0F);
                  koboleton.m_6518_(worldIn, worldIn.m_6436_(koboleton.m_20183_()), MobSpawnType.STRUCTURE, (SpawnGroupData)null, (CompoundTag)null);
                  worldIn.m_47205_(koboleton);
                  worldIn.m_7731_(pos, Blocks.f_50016_.m_49966_(), 2);
               }
               break;
            case "wadjet":
               Wadjet_Entity wadjet = (Wadjet_Entity)((EntityType)ModEntities.WADJET.get()).m_20615_(worldIn.m_6018_());
               if (wadjet != null) {
                  wadjet.m_21530_();
                  wadjet.m_20035_(pos, 0.0F, 0.0F);
                  wadjet.m_6518_(worldIn, worldIn.m_6436_(wadjet.m_20183_()), MobSpawnType.STRUCTURE, (SpawnGroupData)null, (CompoundTag)null);
                  worldIn.m_47205_(wadjet);
                  worldIn.m_7731_(pos, Blocks.f_50016_.m_49966_(), 2);
               }
               break;
            case "kobolediator":
               Kobolediator_Entity kobolediator = (Kobolediator_Entity)((EntityType)ModEntities.KOBOLEDIATOR.get()).m_20615_(worldIn.m_6018_());
               if (kobolediator != null) {
                  kobolediator.m_21530_();
                  kobolediator.m_20035_(pos, 0.0F, 0.0F);
                  kobolediator.setSleep(true);
                  kobolediator.m_6518_(worldIn, worldIn.m_6436_(kobolediator.m_20183_()), MobSpawnType.STRUCTURE, (SpawnGroupData)null, (CompoundTag)null);
                  worldIn.m_47205_(kobolediator);
                  worldIn.m_7731_(pos, Blocks.f_50016_.m_49966_(), 2);
               }
               break;
            case "remnant":
               Ancient_Remnant_Entity remnant = (Ancient_Remnant_Entity)((EntityType)ModEntities.ANCIENT_REMNANT.get()).m_20615_(worldIn.m_6018_());
               if (remnant != null) {
                  remnant.setNecklace(false);
                  remnant.m_21530_();
                  remnant.m_20035_(pos, 0.0F, 0.0F);
                  remnant.m_6518_(worldIn, worldIn.m_6436_(remnant.m_20183_()), MobSpawnType.STRUCTURE, (SpawnGroupData)null, (CompoundTag)null);
                  worldIn.m_47205_(remnant);
                  worldIn.m_7731_(pos, Blocks.f_50016_.m_49966_(), 2);
               }
         }
      }
   }
}
