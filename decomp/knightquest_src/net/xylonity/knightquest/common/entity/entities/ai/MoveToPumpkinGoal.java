package net.xylonity.knightquest.common.entity.entities.ai;

import java.util.EnumSet;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.xylonity.knightquest.common.entity.entities.SamhainEntity;

public class MoveToPumpkinGoal extends Goal {
   private final SamhainEntity entity;
   private final double speed;
   private BlockPos targetPumpkinPos;
   private final int searchRadius = 10;
   private final double circleRadius = 3.0;
   private double angle = 0.0;
   private final double angleIncrement = Math.PI / 8;
   private final Random random = new Random();
   private int ticksCircling = 0;
   private final int maxTicksCircling = 80;

   public MoveToPumpkinGoal(SamhainEntity entity, double speed) {
      this.entity = entity;
      this.speed = speed;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      BlockPos entityPos = this.entity.m_20183_();
      Level level = this.entity.f_19853_;
      this.targetPumpkinPos = this.findNearestPumpkin(level, entityPos, 10);
      return this.targetPumpkinPos != null;
   }

   public boolean m_8045_() {
      return this.targetPumpkinPos != null && this.entity.f_19853_.m_8055_(this.targetPumpkinPos).m_60713_(Blocks.f_50133_);
   }

   public void m_8056_() {
      if (this.targetPumpkinPos != null) {
         this.moveToNextPosition();
         this.ticksCircling = 0;
      }
   }

   public void m_8041_() {
      this.targetPumpkinPos = null;
   }

   public void m_8037_() {
      if (this.targetPumpkinPos != null) {
         if (this.ticksCircling < 80) {
            if (this.entity.m_21573_().m_26571_()) {
               this.angle += Math.PI / 8;
               this.moveToNextPosition();
            }

            this.ticksCircling++;
            if (this.random.nextInt(20) == 0) {
               this.entity.m_21569_().m_24901_();
            }
         } else {
            this.entity
               .m_21573_()
               .m_26519_(
                  (double)this.targetPumpkinPos.m_123341_() + 0.5,
                  (double)this.targetPumpkinPos.m_123342_(),
                  (double)this.targetPumpkinPos.m_123343_() + 0.5,
                  this.speed
               );
         }
      }
   }

   private void moveToNextPosition() {
      double xOffset = (double)this.targetPumpkinPos.m_123341_() + 3.0 * Math.cos(this.angle);
      double zOffset = (double)this.targetPumpkinPos.m_123343_() + 3.0 * Math.sin(this.angle);
      this.entity
         .m_21563_()
         .m_24946_((double)this.targetPumpkinPos.m_123341_(), (double)this.targetPumpkinPos.m_123342_(), (double)this.targetPumpkinPos.m_123343_());
      this.entity.m_21573_().m_26519_(xOffset, (double)this.targetPumpkinPos.m_123342_(), zOffset, this.speed);
   }

   private BlockPos findNearestPumpkin(Level level, BlockPos entityPos, int radius) {
      for (BlockPos pos : BlockPos.m_121940_(entityPos.m_7918_(-radius, -2, -radius), entityPos.m_7918_(radius, 2, radius))) {
         if (level.m_8055_(pos).m_60713_(Blocks.f_50133_)) {
            return pos;
         }
      }

      return null;
   }
}
