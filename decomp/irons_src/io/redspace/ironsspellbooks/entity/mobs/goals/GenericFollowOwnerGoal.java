package io.redspace.ironsspellbooks.entity.mobs.goals;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;

public class GenericFollowOwnerGoal extends Goal {
   private final PathfinderMob entity;
   private LivingEntity owner;
   private final LevelReader level;
   private final double speedModifier;
   private final PathNavigation navigation;
   private int timeToRecalcPath;
   private final float stopDistance;
   private final float startDistance;
   private float oldWaterCost;
   private final boolean canFly;
   private final OwnerGetter ownerGetter;
   private final float teleportDistance;

   public GenericFollowOwnerGoal(
      PathfinderMob entity, OwnerGetter ownerGetter, double pSpeedModifier, float pStartDistance, float pStopDistance, boolean pCanFly, float teleportDistance
   ) {
      this.entity = entity;
      this.ownerGetter = ownerGetter;
      this.level = entity.f_19853_;
      this.speedModifier = pSpeedModifier;
      this.navigation = entity.m_21573_();
      this.startDistance = pStartDistance;
      this.stopDistance = pStopDistance;
      this.canFly = pCanFly;
      this.teleportDistance = teleportDistance * teleportDistance;
      this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
      if (!(entity.m_21573_() instanceof GroundPathNavigation) && !(entity.m_21573_() instanceof FlyingPathNavigation)) {
         throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
      }
   }

   public boolean m_8036_() {
      LivingEntity livingentity = this.ownerGetter.get();
      if (livingentity == null) {
         return false;
      } else if (livingentity.m_5833_()) {
         return false;
      } else if (this.entity.m_20280_(livingentity) < (double)(this.startDistance * this.startDistance)) {
         return false;
      } else {
         this.owner = livingentity;
         return true;
      }
   }

   public boolean m_8045_() {
      return this.navigation.m_26571_() ? false : !(this.entity.m_20280_(this.owner) <= (double)(this.stopDistance * this.stopDistance));
   }

   public void m_8056_() {
      this.timeToRecalcPath = 0;
      this.oldWaterCost = this.entity.m_21439_(BlockPathTypes.WATER);
      this.entity.m_21441_(BlockPathTypes.WATER, 0.0F);
   }

   public void m_8041_() {
      this.owner = null;
      this.navigation.m_26573_();
      this.entity.m_21441_(BlockPathTypes.WATER, this.oldWaterCost);
   }

   public void m_8037_() {
      this.entity.m_21563_().m_24960_(this.owner, 10.0F, (float)this.entity.m_8132_());
      if (--this.timeToRecalcPath <= 0) {
         this.timeToRecalcPath = this.m_183277_(10);
         if (!this.entity.m_21523_() && !this.entity.m_20159_()) {
            if (this.entity.m_20280_(this.owner) >= (double)this.teleportDistance) {
               this.teleportToOwner();
            } else if (this.canFly && !this.entity.m_20096_()) {
               Vec3 vec3 = this.owner.m_20182_();
               this.entity.m_21566_().m_6849_(vec3.f_82479_, vec3.f_82480_ + 2.0, vec3.f_82481_, this.speedModifier);
            } else {
               this.navigation.m_5624_(this.owner, this.speedModifier);
            }
         }
      }
   }

   private void teleportToOwner() {
      BlockPos blockpos = this.owner.m_20183_();

      for (int i = 0; i < 10; i++) {
         int j = this.randomIntInclusive(-3, 3);
         int k = this.randomIntInclusive(-1, 1);
         int l = this.randomIntInclusive(-3, 3);
         boolean flag = this.maybeTeleportTo(blockpos.m_123341_() + j, blockpos.m_123342_() + k, blockpos.m_123343_() + l);
         if (flag) {
            return;
         }
      }
   }

   private boolean maybeTeleportTo(int pX, int pY, int pZ) {
      if (Math.abs((double)pX - this.owner.m_20185_()) < 2.0 && Math.abs((double)pZ - this.owner.m_20189_()) < 2.0) {
         return false;
      } else if (!this.canTeleportTo(new BlockPos(pX, pY, pZ))) {
         return false;
      } else {
         this.entity
            .m_7678_(
               (double)pX + 0.5,
               (double)pY + (double)(this.canFly && !this.entity.m_20096_() ? 3 : 0),
               (double)pZ + 0.5,
               this.entity.m_146908_(),
               this.entity.m_146909_()
            );
         this.navigation.m_26573_();
         return true;
      }
   }

   private boolean canTeleportTo(BlockPos pPos) {
      BlockPathTypes blockpathtypes = WalkNodeEvaluator.m_77604_(this.level, pPos.m_122032_());
      if (blockpathtypes != BlockPathTypes.WALKABLE) {
         return false;
      } else {
         BlockState blockstate = this.level.m_8055_(pPos.m_7495_());
         if (!this.canFly && blockstate.m_60734_() instanceof LeavesBlock) {
            return false;
         } else {
            BlockPos blockpos = pPos.m_121996_(this.entity.m_20183_());
            return this.level.m_45756_(this.entity, this.entity.m_20191_().m_82338_(blockpos));
         }
      }
   }

   private int randomIntInclusive(int pMin, int pMax) {
      return this.entity.m_217043_().m_188503_(pMax - pMin + 1) + pMin;
   }
}
