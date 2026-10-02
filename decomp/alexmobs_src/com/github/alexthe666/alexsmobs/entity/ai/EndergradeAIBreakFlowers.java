package com.github.alexthe666.alexsmobs.entity.ai;

import com.github.alexthe666.alexsmobs.entity.EntityEndergrade;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class EndergradeAIBreakFlowers extends MoveToBlockGoal {
   private EntityEndergrade endergrade;
   private int idleAtFlowerTime = 0;
   private boolean isAboveDestinationBear;

   public EndergradeAIBreakFlowers(EntityEndergrade bird) {
      super(bird, 1.0, 32, 8);
      this.endergrade = bird;
   }

   public boolean m_8036_() {
      return !this.endergrade.m_6162_() && !this.endergrade.hasItemTarget && super.m_8036_();
   }

   public boolean m_8045_() {
      return !this.endergrade.hasItemTarget && super.m_8045_();
   }

   public void m_8041_() {
      this.idleAtFlowerTime = 0;
      this.endergrade.stopWandering = false;
   }

   public double m_8052_() {
      return 2.0;
   }

   public void m_8037_() {
      super.m_8037_();
      this.endergrade.stopWandering = true;
      BlockPos blockpos = this.m_6669_();
      if (!this.isWithinXZDist(blockpos, this.f_25598_.m_20182_(), this.m_8052_())) {
         this.isAboveDestinationBear = false;
         this.f_25601_++;
         this.f_25598_
            .m_21566_()
            .m_6849_((double)((float)blockpos.m_123341_()) + 0.5, (double)blockpos.m_123342_() - 0.5, (double)((float)blockpos.m_123343_()) + 0.5, 1.0);
      } else {
         this.isAboveDestinationBear = true;
         this.f_25601_--;
      }

      if (this.m_25625_() && Math.abs(this.endergrade.m_20186_() - (double)this.f_25602_.m_123342_()) <= 2.0) {
         this.endergrade
            .m_7618_(Anchor.EYES, new Vec3((double)this.f_25602_.m_123341_() + 0.5, (double)this.f_25602_.m_123342_(), (double)this.f_25602_.m_123343_() + 0.5));
         if (this.idleAtFlowerTime >= 20) {
            this.endergrade.bite();
            this.pollinate();
            this.m_8041_();
         } else {
            this.idleAtFlowerTime++;
         }
      }
   }

   private boolean isWithinXZDist(BlockPos blockpos, Vec3 positionVec, double distance) {
      return blockpos.m_123331_(new BlockPos(positionVec.m_7096_(), (double)blockpos.m_123342_(), positionVec.m_7094_())) < distance * distance;
   }

   protected boolean m_25625_() {
      return this.isAboveDestinationBear;
   }

   private void pollinate() {
      this.endergrade.f_19853_.m_46961_(this.f_25602_, true);
      this.m_8041_();
   }

   protected boolean m_6465_(LevelReader worldIn, BlockPos pos) {
      return worldIn.m_8055_(pos).m_60734_() == Blocks.f_50491_;
   }
}
