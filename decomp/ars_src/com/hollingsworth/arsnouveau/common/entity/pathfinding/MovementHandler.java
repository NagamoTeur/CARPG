package com.hollingsworth.arsnouveau.common.entity.pathfinding;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MovementHandler extends MoveControl {
   final AttributeInstance speedAtr = this.f_24974_.m_21051_(Attributes.f_22279_);

   public MovementHandler(Mob mob) {
      super(mob);
   }

   public void m_8126_() {
      if (this.f_24981_ == Operation.STRAFE) {
         float speedAtt = (float)this.speedAtr.m_22135_();
         float speed = (float)this.f_24978_ * speedAtt;
         float forward = this.f_24979_;
         float strafe = this.f_24980_;
         float totalMovement = Mth.m_14116_(forward * forward + strafe * strafe);
         if (totalMovement < 1.0F) {
            totalMovement = 1.0F;
         }

         totalMovement = speed / totalMovement;
         forward *= totalMovement;
         strafe *= totalMovement;
         float sinRotation = Mth.m_14031_(this.f_24974_.m_146908_() * (float) (Math.PI / 180.0));
         float cosRotation = Mth.m_14089_(this.f_24974_.m_146908_() * (float) (Math.PI / 180.0));
         float rot1 = forward * cosRotation - strafe * sinRotation;
         float rot2 = strafe * cosRotation + forward * sinRotation;
         PathNavigation pathnavigator = this.f_24974_.m_21573_();
         NodeEvaluator nodeprocessor = pathnavigator.m_26575_();
         if (nodeprocessor.m_8086_(
               this.f_24974_.f_19853_,
               Mth.m_14107_(this.f_24974_.m_20185_() + (double)rot1),
               Mth.m_14107_(this.f_24974_.m_20186_()),
               Mth.m_14107_(this.f_24974_.m_20189_() + (double)rot2)
            )
            != BlockPathTypes.WALKABLE) {
            this.f_24979_ = 1.0F;
            this.f_24980_ = 0.0F;
            speed = speedAtt;
         }

         this.f_24974_.m_7910_(speed);
         this.f_24974_.m_21564_(this.f_24979_);
         this.f_24974_.m_21570_(this.f_24980_);
         this.f_24981_ = Operation.WAIT;
      } else if (this.f_24981_ == Operation.MOVE_TO) {
         this.f_24981_ = Operation.WAIT;
         double xDif = this.f_24975_ - this.f_24974_.m_20185_();
         double zDif = this.f_24977_ - this.f_24974_.m_20189_();
         double yDif = this.f_24976_ - this.f_24974_.m_20186_();
         double dist = xDif * xDif + yDif * yDif + zDif * zDif;
         if (dist < 2.5000003E-7F) {
            this.f_24974_.m_21564_(0.0F);
            return;
         }

         float range = (float)(Mth.m_14136_(zDif, xDif) * 180.0F / (float)Math.PI) - 90.0F;
         this.f_24974_.m_146922_(this.m_24991_(this.f_24974_.m_146908_(), range, 90.0F));
         this.f_24974_.m_7910_((float)(this.f_24978_ * this.speedAtr.m_22135_()));
         BlockPos blockpos = new BlockPos(this.f_24974_.m_20182_());
         BlockState blockstate = this.f_24974_.f_19853_.m_8055_(blockpos);
         Block block = blockstate.m_60734_();
         VoxelShape voxelshape = blockstate.m_60812_(this.f_24974_.f_19853_, blockpos);
         if (yDif > (double)this.f_24974_.getStepHeight() && xDif * xDif + zDif * zDif < (double)Math.max(1.0F, this.f_24974_.m_20205_())
            || !voxelshape.m_83281_()
               && this.f_24974_.m_20186_() < voxelshape.m_83297_(Axis.Y) + (double)blockpos.m_123342_()
               && !blockstate.m_204336_(BlockTags.f_13103_)
               && !blockstate.m_204336_(BlockTags.f_13039_)
               && !blockstate.m_204336_(BlockTags.f_13055_)
               && !block.isLadder(blockstate, this.f_24974_.f_19853_, blockpos, this.f_24974_)) {
            this.f_24974_.m_21569_().m_24901_();
            this.f_24981_ = Operation.JUMPING;
         }
      } else if (this.f_24981_ == Operation.JUMPING) {
         this.f_24974_.m_7910_((float)(this.f_24978_ * this.speedAtr.m_22135_()));
         BlockPos blockpos = new BlockPos(this.f_24974_.m_20182_());
         BlockState blockstate = this.f_24974_.f_19853_.m_8055_(blockpos);
         if (this.f_24974_.m_20096_() || blockstate.m_60767_().m_76332_()) {
            this.f_24981_ = Operation.WAIT;
         }
      } else {
         this.f_24974_.m_21564_(0.0F);
      }
   }

   public void m_6849_(double x, double y, double z, double speedIn) {
      super.m_6849_(x, y, z, speedIn);
      this.f_24981_ = Operation.MOVE_TO;
   }
}
