package com.github.alexthe666.alexsmobs.entity.ai;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.phys.Vec3;

public class MonsterAIWalkThroughHallsOfStructure extends RandomStrollGoal {
   private TagKey<Structure> structureTagKey;
   private double maximumDistance = 0.0;
   private double maximumYDistance = 3.0;

   public MonsterAIWalkThroughHallsOfStructure(PathfinderMob mob, double speed, int chance, TagKey<Structure> structureTagKey, double maximumDistance) {
      super(mob, speed, chance, false);
      this.structureTagKey = structureTagKey;
      this.maximumDistance = 50.0;
   }

   @Nullable
   protected Vec3 m_7037_() {
      StructureStart start = this.getNearestStructure(this.f_25725_.m_20183_());
      if (start.m_73603_()) {
         List<BlockPos> validPieceCenters = new ArrayList<>();

         for (StructurePiece piece : start.m_73602_()) {
            BoundingBox boundingbox = piece.m_73547_();
            BlockPos blockpos = boundingbox.m_162394_();
            BlockPos blockpos1 = new BlockPos(blockpos.m_123341_(), boundingbox.m_162396_(), blockpos.m_123343_());
            double yDist = (double)Math.abs(blockpos1.m_123342_() - this.f_25725_.m_20183_().m_123342_());
            if (this.f_25725_.m_20238_(Vec3.m_82512_(blockpos1)) <= this.maximumDistance * this.maximumDistance && yDist < this.maximumYDistance) {
               validPieceCenters.add(blockpos1);
            }
         }

         if (!validPieceCenters.isEmpty()) {
            BlockPos randomCenter = validPieceCenters.size() > 1
               ? validPieceCenters.get(this.f_25725_.m_217043_().m_188503_(validPieceCenters.size() - 1))
               : validPieceCenters.get(0);
            return Vec3.m_82512_(randomCenter.m_7918_(this.f_25725_.m_217043_().m_188503_(2) - 1, 0, this.f_25725_.m_217043_().m_188503_(2) - 1));
         }
      }

      return this.getPositionTowardsAnywhere();
   }

   @Nullable
   private Vec3 getPositionTowardsAnywhere() {
      return DefaultRandomPos.m_148403_(this.f_25725_, 10, 7);
   }

   private StructureStart getNearestStructure(BlockPos pos) {
      ServerLevel serverlevel = (ServerLevel)this.f_25725_.f_19853_;
      StructureStart start = serverlevel.m_215010_().m_220491_(pos, this.structureTagKey);
      if (start.m_73603_()) {
         return start;
      } else {
         BlockPos nearestOf = serverlevel.m_215011_(this.structureTagKey, pos, (int)this.maximumDistance, false);
         return nearestOf == null ? StructureStart.f_73561_ : serverlevel.m_215010_().m_220491_(nearestOf, this.structureTagKey);
      }
   }
}
