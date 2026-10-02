package com.github.L_Ender.cataclysm.structures;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ignited_Revenant_Entity;
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
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
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

public class Burning_Arena_Structure extends Structure {
   public static final Codec<Burning_Arena_Structure> CODEC = m_226607_(Burning_Arena_Structure::new);
   private static final ResourceLocation ARENA1 = new ResourceLocation("cataclysm", "burning_arena1");
   private static final ResourceLocation ARENA2 = new ResourceLocation("cataclysm", "burning_arena2");
   private static final ResourceLocation ARENA3 = new ResourceLocation("cataclysm", "burning_arena3");
   private static final ResourceLocation ARENA4 = new ResourceLocation("cataclysm", "burning_arena4");
   private static final ResourceLocation ARENA5 = new ResourceLocation("cataclysm", "burning_arena5");
   private static final ResourceLocation ARENA6 = new ResourceLocation("cataclysm", "burning_arena6");
   private static final ResourceLocation ARENA7 = new ResourceLocation("cataclysm", "burning_arena7");
   private static final ResourceLocation ARENA8 = new ResourceLocation("cataclysm", "burning_arena8");
   private static final Map<ResourceLocation, BlockPos> OFFSET = ImmutableMap.builder()
      .put(ARENA1, new BlockPos(0, 1, 0))
      .put(ARENA2, new BlockPos(0, 1, 0))
      .put(ARENA3, new BlockPos(0, 1, 0))
      .put(ARENA4, new BlockPos(0, 1, 0))
      .put(ARENA5, new BlockPos(0, 1, 0))
      .put(ARENA6, new BlockPos(0, 1, 0))
      .put(ARENA7, new BlockPos(0, 1, 0))
      .put(ARENA8, new BlockPos(0, 1, 0))
      .build();

   public static void start(StructureTemplateManager templateManager, BlockPos pos, Rotation rotation, StructurePieceAccessor pieceList, RandomSource random) {
      int x = pos.m_123341_();
      int z = pos.m_123343_();
      BlockPos rotationOffSet = new BlockPos(0, 0, 0).m_7954_(rotation);
      BlockPos blockpos = rotationOffSet.m_7918_(x, pos.m_123342_(), z);
      pieceList.m_142679_(new Burning_Arena_Structure.Piece(templateManager, ARENA1, blockpos, rotation));
      rotationOffSet = new BlockPos(0, 0, 38).m_7954_(rotation);
      blockpos = rotationOffSet.m_7918_(x, pos.m_123342_(), z);
      pieceList.m_142679_(new Burning_Arena_Structure.Piece(templateManager, ARENA2, blockpos, rotation));
      rotationOffSet = new BlockPos(47, 0, 0).m_7954_(rotation);
      blockpos = rotationOffSet.m_7918_(x, pos.m_123342_(), z);
      pieceList.m_142679_(new Burning_Arena_Structure.Piece(templateManager, ARENA3, blockpos, rotation));
      rotationOffSet = new BlockPos(47, 0, 38).m_7954_(rotation);
      blockpos = rotationOffSet.m_7918_(x, pos.m_123342_(), z);
      pieceList.m_142679_(new Burning_Arena_Structure.Piece(templateManager, ARENA4, blockpos, rotation));
      rotationOffSet = new BlockPos(0, 48, 0).m_7954_(rotation);
      blockpos = rotationOffSet.m_7918_(x, pos.m_123342_(), z);
      pieceList.m_142679_(new Burning_Arena_Structure.Piece(templateManager, ARENA5, blockpos, rotation));
      rotationOffSet = new BlockPos(0, 48, 38).m_7954_(rotation);
      blockpos = rotationOffSet.m_7918_(x, pos.m_123342_(), z);
      pieceList.m_142679_(new Burning_Arena_Structure.Piece(templateManager, ARENA6, blockpos, rotation));
      rotationOffSet = new BlockPos(47, 48, 0).m_7954_(rotation);
      blockpos = rotationOffSet.m_7918_(x, pos.m_123342_(), z);
      pieceList.m_142679_(new Burning_Arena_Structure.Piece(templateManager, ARENA7, blockpos, rotation));
      rotationOffSet = new BlockPos(47, 48, 38).m_7954_(rotation);
      blockpos = rotationOffSet.m_7918_(x, pos.m_123342_(), z);
      pieceList.m_142679_(new Burning_Arena_Structure.Piece(templateManager, ARENA8, blockpos, rotation));
   }

   public Burning_Arena_Structure(StructureSettings p_227593_) {
      super(p_227593_);
   }

   public Optional<GenerationStub> m_214086_(GenerationContext p_228964_) {
      int i = p_228964_.f_226628_().f_45578_ >> 16;
      int j = p_228964_.f_226628_().f_45579_ >> 16;
      BlockPos blockpos = new BlockPos(p_228964_.f_226628_().m_45604_(), 21, p_228964_.f_226628_().m_45605_());
      WorldgenRandom worldgenrandom = new WorldgenRandom(new LegacyRandomSource(0L));
      worldgenrandom.m_188584_((long)(i ^ j << 9) ^ p_228964_.f_226627_());
      worldgenrandom.m_188502_();
      return Optional.of(new GenerationStub(blockpos, p_228526_ -> generatePieces(p_228526_, p_228964_)));
   }

   private static void generatePieces(StructurePiecesBuilder p_197233_, GenerationContext p_197234_) {
      BlockPos blockpos = new BlockPos(p_197234_.f_226628_().m_45604_(), 21, p_197234_.f_226628_().m_45605_());
      Rotation rotation = Rotation.m_221990_(p_197234_.f_226626_());
      start(p_197234_.f_226625_(), blockpos, rotation, p_197233_, p_197234_.f_226626_());
   }

   public StructureType<?> m_213658_() {
      return (StructureType<?>)ModStructures.BURNING_ARENA.get();
   }

   public Decoration m_226619_() {
      return Decoration.SURFACE_STRUCTURES;
   }

   public static class Piece extends TemplateStructurePiece {
      public Piece(StructureTemplateManager templateManagerIn, ResourceLocation resourceLocationIn, BlockPos pos, Rotation rotation) {
         super(
            (StructurePieceType)ModStructures.BAP.get(),
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
            (StructurePieceType)ModStructures.BAP.get(),
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
         return p_162454_.m_121955_((Vec3i)Burning_Arena_Structure.OFFSET.get(p_162453_));
      }

      protected void m_183620_(StructurePieceSerializationContext p_162444_, CompoundTag tagCompound) {
         super.m_183620_(p_162444_, tagCompound);
         tagCompound.m_128359_("Rot", this.f_73657_.m_74404_().name());
      }

      protected void m_213704_(String function, BlockPos pos, ServerLevelAccessor worldIn, RandomSource rand, BoundingBox sbb) {
         if ("revenant".equals(function)) {
            worldIn.m_7731_(pos, Blocks.f_50016_.m_49966_(), 2);
            Ignited_Revenant_Entity revenant = (Ignited_Revenant_Entity)((EntityType)ModEntities.IGNITED_REVENANT.get()).m_20615_(worldIn.m_6018_());
            revenant.m_20035_(pos, 180.0F, 180.0F);
            worldIn.m_7967_(revenant);
         }
      }
   }
}
