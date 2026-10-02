package com.github.L_Ender.cataclysm.structures;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ender_Golem_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModStructures;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationStub;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProtectedBlockProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class RuinedCitadelStructure extends Structure {
   public static final Codec<RuinedCitadelStructure> CODEC = m_226607_(RuinedCitadelStructure::new);
   private static final ResourceLocation CITADEL1 = new ResourceLocation("cataclysm", "ruined_citadel1");
   private static final ResourceLocation CITADEL2 = new ResourceLocation("cataclysm", "ruined_citadel2");
   private static final ResourceLocation CITADEL3 = new ResourceLocation("cataclysm", "ruined_citadel3");
   private static final ResourceLocation CITADEL4 = new ResourceLocation("cataclysm", "ruined_citadel4");
   private static final ResourceLocation CITADEL5 = new ResourceLocation("cataclysm", "ruined_citadel5");
   private static final ResourceLocation CITADEL6 = new ResourceLocation("cataclysm", "ruined_citadel6");
   private static final ResourceLocation CITADEL7 = new ResourceLocation("cataclysm", "ruined_citadel7");
   private static final ResourceLocation CITADEL8 = new ResourceLocation("cataclysm", "ruined_citadel8");
   private static final ResourceLocation CITADEL9 = new ResourceLocation("cataclysm", "ruined_citadel9");
   private static final ResourceLocation CITADEL10 = new ResourceLocation("cataclysm", "ruined_citadel10");
   private static final ResourceLocation CITADEL11 = new ResourceLocation("cataclysm", "ruined_citadel11");
   private static final ResourceLocation CITADEL12 = new ResourceLocation("cataclysm", "ruined_citadel12");
   private static final ResourceLocation CITADEL13 = new ResourceLocation("cataclysm", "ruined_citadel13");
   private static final ResourceLocation CITADEL14 = new ResourceLocation("cataclysm", "ruined_citadel14");
   private static final ResourceLocation CITADEL15 = new ResourceLocation("cataclysm", "ruined_citadel15");
   private static final ResourceLocation CITADEL16 = new ResourceLocation("cataclysm", "ruined_citadel16");
   private static final ResourceLocation CITADEL17 = new ResourceLocation("cataclysm", "ruined_citadel17");
   private static final ResourceLocation CITADEL18 = new ResourceLocation("cataclysm", "ruined_citadel18");
   private static final Map<ResourceLocation, BlockPos> OFFSET = ImmutableMap.builder()
      .put(CITADEL1, new BlockPos(0, 1, 0))
      .put(CITADEL2, new BlockPos(0, 1, 0))
      .put(CITADEL3, new BlockPos(0, 1, 0))
      .put(CITADEL4, new BlockPos(0, 1, 0))
      .put(CITADEL5, new BlockPos(0, 1, 0))
      .put(CITADEL6, new BlockPos(0, 1, 0))
      .put(CITADEL7, new BlockPos(0, 1, 0))
      .put(CITADEL8, new BlockPos(0, 1, 0))
      .put(CITADEL9, new BlockPos(0, 1, 0))
      .put(CITADEL10, new BlockPos(0, 1, 0))
      .put(CITADEL11, new BlockPos(0, 1, 0))
      .put(CITADEL12, new BlockPos(0, 1, 0))
      .put(CITADEL13, new BlockPos(0, 1, 0))
      .put(CITADEL14, new BlockPos(0, 1, 0))
      .put(CITADEL15, new BlockPos(0, 1, 0))
      .put(CITADEL16, new BlockPos(0, 1, 0))
      .put(CITADEL17, new BlockPos(0, 1, 0))
      .put(CITADEL18, new BlockPos(0, 1, 0))
      .build();

   public static void start(StructureTemplateManager templateManager, BlockPos pos, Rotation rotation, StructurePieceAccessor pieceList, RandomSource random) {
      int x = pos.m_123341_();
      int z = pos.m_123343_();
      BlockPos rotationOffSet = new BlockPos(0, -45, 0).m_7954_(rotation);
      BlockPos blockpos = rotationOffSet.m_7918_(x, pos.m_123342_(), z);
      pieceList.m_142679_(new RuinedCitadelStructure.Piece(templateManager, CITADEL5, blockpos, rotation));
      rotationOffSet = new BlockPos(0, 0, 0).m_7954_(rotation);
      blockpos = rotationOffSet.m_7918_(x, pos.m_123342_(), z);
      pieceList.m_142679_(new RuinedCitadelStructure.Piece(templateManager, CITADEL14, blockpos, rotation));
      rotationOffSet = new BlockPos(0, -45, 37).m_7954_(rotation);
      blockpos = rotationOffSet.m_7918_(x, pos.m_123342_(), z);
      pieceList.m_142679_(new RuinedCitadelStructure.Piece(templateManager, CITADEL6, blockpos, rotation));
      rotationOffSet = new BlockPos(0, 0, 37).m_7954_(rotation);
      blockpos = rotationOffSet.m_7918_(x, pos.m_123342_(), z);
      pieceList.m_142679_(new RuinedCitadelStructure.Piece(templateManager, CITADEL15, blockpos, rotation));
      rotationOffSet = new BlockPos(0, -45, -37).m_7954_(rotation);
      blockpos = rotationOffSet.m_7918_(x, pos.m_123342_(), z);
      pieceList.m_142679_(new RuinedCitadelStructure.Piece(templateManager, CITADEL4, blockpos, rotation));
      rotationOffSet = new BlockPos(0, 0, -37).m_7954_(rotation);
      blockpos = rotationOffSet.m_7918_(x, pos.m_123342_(), z);
      pieceList.m_142679_(new RuinedCitadelStructure.Piece(templateManager, CITADEL13, blockpos, rotation));
      rotationOffSet = new BlockPos(-36, -45, 0).m_7954_(rotation);
      blockpos = rotationOffSet.m_7918_(x, pos.m_123342_(), z);
      pieceList.m_142679_(new RuinedCitadelStructure.Piece(templateManager, CITADEL2, blockpos, rotation));
      rotationOffSet = new BlockPos(-36, 0, 0).m_7954_(rotation);
      blockpos = rotationOffSet.m_7918_(x, pos.m_123342_(), z);
      pieceList.m_142679_(new RuinedCitadelStructure.Piece(templateManager, CITADEL11, blockpos, rotation));
      rotationOffSet = new BlockPos(36, -45, 0).m_7954_(rotation);
      blockpos = rotationOffSet.m_7918_(x, pos.m_123342_(), z);
      pieceList.m_142679_(new RuinedCitadelStructure.Piece(templateManager, CITADEL8, blockpos, rotation));
      rotationOffSet = new BlockPos(36, 0, 0).m_7954_(rotation);
      blockpos = rotationOffSet.m_7918_(x, pos.m_123342_(), z);
      pieceList.m_142679_(new RuinedCitadelStructure.Piece(templateManager, CITADEL17, blockpos, rotation));
      rotationOffSet = new BlockPos(-36, -45, -37).m_7954_(rotation);
      blockpos = rotationOffSet.m_7918_(x, pos.m_123342_(), z);
      pieceList.m_142679_(new RuinedCitadelStructure.Piece(templateManager, CITADEL1, blockpos, rotation));
      rotationOffSet = new BlockPos(-36, 0, -37).m_7954_(rotation);
      blockpos = rotationOffSet.m_7918_(x, pos.m_123342_(), z);
      pieceList.m_142679_(new RuinedCitadelStructure.Piece(templateManager, CITADEL10, blockpos, rotation));
      rotationOffSet = new BlockPos(-36, -45, 37).m_7954_(rotation);
      blockpos = rotationOffSet.m_7918_(x, pos.m_123342_(), z);
      pieceList.m_142679_(new RuinedCitadelStructure.Piece(templateManager, CITADEL3, blockpos, rotation));
      rotationOffSet = new BlockPos(-36, 0, 37).m_7954_(rotation);
      blockpos = rotationOffSet.m_7918_(x, pos.m_123342_(), z);
      pieceList.m_142679_(new RuinedCitadelStructure.Piece(templateManager, CITADEL12, blockpos, rotation));
      rotationOffSet = new BlockPos(36, -45, 37).m_7954_(rotation);
      blockpos = rotationOffSet.m_7918_(x, pos.m_123342_(), z);
      pieceList.m_142679_(new RuinedCitadelStructure.Piece(templateManager, CITADEL9, blockpos, rotation));
      rotationOffSet = new BlockPos(36, 0, 37).m_7954_(rotation);
      blockpos = rotationOffSet.m_7918_(x, pos.m_123342_(), z);
      pieceList.m_142679_(new RuinedCitadelStructure.Piece(templateManager, CITADEL18, blockpos, rotation));
      rotationOffSet = new BlockPos(36, -45, -37).m_7954_(rotation);
      blockpos = rotationOffSet.m_7918_(x, pos.m_123342_(), z);
      pieceList.m_142679_(new RuinedCitadelStructure.Piece(templateManager, CITADEL7, blockpos, rotation));
      rotationOffSet = new BlockPos(36, 0, -37).m_7954_(rotation);
      blockpos = rotationOffSet.m_7918_(x, pos.m_123342_(), z);
      pieceList.m_142679_(new RuinedCitadelStructure.Piece(templateManager, CITADEL16, blockpos, rotation));
   }

   public RuinedCitadelStructure(StructureSettings p_227593_) {
      super(p_227593_);
   }

   public Optional<GenerationStub> m_214086_(GenerationContext p_228964_) {
      int i = p_228964_.f_226628_().f_45578_ >> 16;
      int j = p_228964_.f_226628_().f_45579_ >> 16;
      WorldgenRandom worldgenrandom = new WorldgenRandom(new LegacyRandomSource(0L));
      worldgenrandom.m_188584_((long)(i ^ j << 9) ^ p_228964_.f_226627_());
      worldgenrandom.m_188502_();
      return m_226585_(p_228964_, Types.WORLD_SURFACE_WG, p_228967_ -> generatePieces(p_228967_, p_228964_));
   }

   private static void generatePieces(StructurePiecesBuilder p_197233_, GenerationContext p_197234_) {
      BlockPos blockpos = new BlockPos(p_197234_.f_226628_().m_45604_(), 53, p_197234_.f_226628_().m_45605_());
      Rotation rotation = Rotation.m_221990_(p_197234_.f_226626_());
      start(p_197234_.f_226625_(), blockpos, rotation, p_197233_, p_197234_.f_226626_());
   }

   public StructureType<?> m_213658_() {
      return (StructureType<?>)ModStructures.RUINED_CITADEL.get();
   }

   public Decoration m_226619_() {
      return Decoration.SURFACE_STRUCTURES;
   }

   public static class Piece extends TemplateStructurePiece {
      public Piece(StructureTemplateManager templateManagerIn, ResourceLocation resourceLocationIn, BlockPos pos, Rotation rotation) {
         super(
            (StructurePieceType)ModStructures.RCP.get(),
            0,
            templateManagerIn,
            resourceLocationIn,
            resourceLocationIn.toString(),
            makeSettings(rotation),
            makePosition(resourceLocationIn, pos)
         );
      }

      public Piece(StructureTemplateManager templateManagerIn, CompoundTag tagCompound) {
         super(
            (StructurePieceType)ModStructures.RCP.get(),
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
            .m_74383_(new ProtectedBlockProcessor(BlockTags.f_144287_));
      }

      private static BlockPos makePosition(ResourceLocation p_162453_, BlockPos p_162454_) {
         return p_162454_.m_121955_((Vec3i)RuinedCitadelStructure.OFFSET.get(p_162453_));
      }

      protected void m_183620_(StructurePieceSerializationContext p_162444_, CompoundTag tagCompound) {
         super.m_183620_(p_162444_, tagCompound);
         tagCompound.m_128359_("Rot", this.f_73657_.m_74404_().name());
      }

      protected void m_213704_(String function, BlockPos pos, ServerLevelAccessor worldIn, RandomSource rand, BoundingBox sbb) {
         if (sbb.m_71051_(pos) && Level.m_46741_(pos)) {
            if (function.startsWith("sentry")) {
               worldIn.m_7731_(pos, Blocks.f_50016_.m_49966_(), 2);
               Shulker shulker = (Shulker)EntityType.f_20521_.m_20615_(worldIn.m_6018_());
               shulker.m_6034_((double)pos.m_123341_() + 0.5, (double)pos.m_123342_(), (double)pos.m_123343_() + 0.5);
               worldIn.m_7967_(shulker);
            } else if (function.startsWith("mimic")) {
               worldIn.m_7731_(pos, Blocks.f_50016_.m_49966_(), 2);
               Shulker Silentshulkerentity = (Shulker)EntityType.f_20521_.m_20615_(worldIn.m_6018_());
               Silentshulkerentity.m_6034_((double)pos.m_123341_() + 0.5, (double)pos.m_123342_(), (double)pos.m_123343_() + 0.5);
               Silentshulkerentity.m_20225_(true);
               worldIn.m_7967_(Silentshulkerentity);
            } else if ("golem".equals(function)) {
               Ender_Golem_Entity golem = (Ender_Golem_Entity)((EntityType)ModEntities.ENDER_GOLEM.get()).m_20615_(worldIn.m_6018_());
               worldIn.m_7731_(pos, Blocks.f_50016_.m_49966_(), 2);
               golem.m_20035_(pos, 180.0F, 180.0F);
               worldIn.m_7967_(golem);
            }
         }
      }
   }
}
