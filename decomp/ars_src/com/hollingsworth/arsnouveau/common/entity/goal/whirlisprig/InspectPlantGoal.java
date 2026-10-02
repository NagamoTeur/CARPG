package com.hollingsworth.arsnouveau.common.entity.goal.whirlisprig;

import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.block.tile.WhirlisprigTile;
import com.hollingsworth.arsnouveau.common.entity.EntityFlyingItem;
import com.hollingsworth.arsnouveau.common.entity.Whirlisprig;
import com.hollingsworth.arsnouveau.common.entity.goal.DistanceRestrictedGoal;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;

public class InspectPlantGoal extends DistanceRestrictedGoal {
   Whirlisprig entity;
   BlockPos pos;
   int timeLooking;
   int timePerforming;

   public InspectPlantGoal(Whirlisprig entity, Supplier<BlockPos> from, int maxDistanceFrom) {
      super(from, maxDistanceFrom);
      this.m_7021_(EnumSet.of(Flag.LOOK, Flag.MOVE));
      this.entity = entity;
   }

   public boolean hasVisibleSide(BlockPos pos) {
      for (Direction d : Direction.values()) {
         if (this.entity.f_19853_.m_8055_(pos.m_121945_(d)).m_60767_() == Material.f_76296_) {
            return true;
         }
      }

      return false;
   }

   public void m_8037_() {
      if (this.pos != null) {
         this.timePerforming--;
         if (BlockUtil.distanceFrom(this.entity.m_20183_(), this.pos) > 1.5) {
            this.entity.m_21573_().m_26519_((double)this.pos.m_123341_(), (double)this.pos.m_123342_(), (double)this.pos.m_123343_(), 1.2);
         } else {
            ServerLevel world = (ServerLevel)this.entity.f_19853_;
            this.entity.m_7618_(Anchor.EYES, new Vec3((double)this.pos.m_123341_(), (double)this.pos.m_123342_(), (double)this.pos.m_123343_()));
            if (world.f_46441_.m_188503_(20) == 0) {
               world.m_8767_(
                  ParticleTypes.f_123750_,
                  (double)this.pos.m_123341_() + 0.5,
                  (double)this.pos.m_123342_() + 1.1,
                  (double)this.pos.m_123343_() + 0.5,
                  1,
                  ParticleUtil.inRange(-0.2, 0.2),
                  0.0,
                  ParticleUtil.inRange(-0.2, 0.2),
                  0.01
               );
            }

            this.timeLooking--;
         }
      }
   }

   public boolean m_8045_() {
      return this.pos != null && this.timeLooking > 0 && this.timePerforming > 0;
   }

   public boolean m_8036_() {
      WhirlisprigTile tile = this.entity.getTile();
      if (tile != null && !tile.isOff) {
         if (this.entity.timeSinceGen > 300) {
            return true;
         }

         if (this.entity.m_20193_().f_46441_.m_188503_(100) <= 2) {
            return this.entity.f_19853_.m_46467_() % 10L == 0L;
         }
      }

      return false;
   }

   public void m_8056_() {
      int range = 4;
      List<BlockPos> list = new ArrayList<>();
      BlockPos.m_121990_(this.entity.m_20183_().m_7918_(range, range, range), this.entity.m_20183_().m_7918_(-range, -range, -range)).forEach(bp -> {
         if (WhirlisprigTile.getScore(this.entity.f_19853_.m_8055_(bp)) > 0 && this.hasVisibleSide(bp) && this.isInRange(bp)) {
            list.add(bp.m_7949_());
         }
      });
      if (!list.isEmpty()) {
         this.pos = list.get(this.entity.f_19853_.f_46441_.m_188503_(list.size()));
         this.timeLooking = 120;
         this.timePerforming = 240;
         EntityFlyingItem flyingItem = new EntityFlyingItem(this.entity.f_19853_, this.pos, this.entity.flowerPos, 50, 255, 40);
         flyingItem.m_20088_().m_135381_(EntityFlyingItem.HELD_ITEM, this.entity.f_19853_.m_8055_(this.pos).m_60734_().m_5456_().m_7968_());
         this.entity.f_19853_.m_7967_(flyingItem);
         this.entity.timeSinceGen = 0;
         this.entity.getTile().addProgress();
      }
   }
}
