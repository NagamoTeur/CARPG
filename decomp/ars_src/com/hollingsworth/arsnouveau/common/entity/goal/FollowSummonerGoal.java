package com.hollingsworth.arsnouveau.common.entity.goal;

import com.hollingsworth.arsnouveau.common.entity.IFollowingSummon;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public class FollowSummonerGoal extends Goal {
   protected final IFollowingSummon summon;
   protected final LevelReader world;
   private final double followSpeed;
   private final PathNavigation navigator;
   private int timeToRecalcPath;
   private final float maxDist;
   private final float minDist;
   private float oldWaterCost;

   public FollowSummonerGoal(IFollowingSummon mobEntity, LivingEntity owner, double followSpeedIn, float minDistIn, float maxDistIn) {
      this.summon = mobEntity;
      this.world = mobEntity.getWorld();
      this.followSpeed = followSpeedIn;
      this.navigator = mobEntity.getPathNav();
      this.minDist = minDistIn;
      this.maxDist = maxDistIn;
      this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
      if (!(mobEntity.getPathNav() instanceof GroundPathNavigation) && !(mobEntity.getPathNav() instanceof FlyingPathNavigation)) {
         throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
      }
   }

   public boolean m_8036_() {
      LivingEntity livingentity = this.summon.getSummoner();
      if (livingentity == null) {
         return false;
      } else if (livingentity instanceof Player && livingentity.m_5833_()) {
         return false;
      } else {
         return this.summon instanceof TamableAnimal && ((TamableAnimal)this.summon).m_21827_()
            ? false
            : !(this.summon.getSelfEntity().m_20280_(livingentity) < (double)(this.minDist * this.minDist));
      }
   }

   public boolean m_8045_() {
      boolean flag = true;
      if (this.summon instanceof TamableAnimal) {
         flag = !((TamableAnimal)this.summon).m_21827_();
      }

      return this.summon.getSummoner() == null
         ? false
         : !this.navigator.m_26571_() && this.summon.getSelfEntity().m_20280_(this.summon.getSummoner()) > (double)(this.maxDist * this.maxDist) && flag;
   }

   public void m_8056_() {
      this.timeToRecalcPath = 0;
      this.oldWaterCost = this.summon.getSelfEntity().m_21439_(BlockPathTypes.WATER);
      this.summon.getSelfEntity().m_21441_(BlockPathTypes.WATER, 0.0F);
   }

   public void m_8041_() {
      this.navigator.m_26573_();
      this.summon.getSelfEntity().m_21441_(BlockPathTypes.WATER, this.oldWaterCost);
   }

   public void m_8037_() {
      if (this.summon.getSummoner() != null) {
         this.summon.getSelfEntity().m_21563_().m_24960_(this.summon.getSummoner(), 10.0F, (float)this.summon.getSelfEntity().m_8132_());
         if (!(this.summon instanceof TamableAnimal) || !((TamableAnimal)this.summon).m_21827_()) {
            if (--this.timeToRecalcPath <= 0) {
               this.timeToRecalcPath = 10;
               if (!this.navigator.m_5624_(this.summon.getSummoner(), this.followSpeed)
                  && !(this.summon.getSelfEntity().m_20280_(this.summon.getSummoner()) < 144.0)) {
                  int i = Mth.m_14107_(this.summon.getSummoner().m_20185_()) - 2;
                  int j = Mth.m_14107_(this.summon.getSummoner().m_20189_()) - 2;
                  int k = Mth.m_14107_(this.summon.getSummoner().m_20191_().f_82289_);

                  for (int l = 0; l <= 4; l++) {
                     for (int i1 = 0; i1 <= 4; i1++) {
                        if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && this.canTeleportToBlock(new BlockPos(i + l, k - 1, j + i1))) {
                           this.summon
                              .getSelfEntity()
                              .m_7678_(
                                 (double)((float)(i + l) + 0.5F),
                                 (double)k,
                                 (double)((float)(j + i1) + 0.5F),
                                 this.summon.getSelfEntity().m_146908_(),
                                 this.summon.getSelfEntity().m_146909_()
                              );
                           this.navigator.m_26573_();
                           return;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   protected boolean canTeleportToBlock(BlockPos pos) {
      BlockState blockstate = this.world.m_8055_(pos);
      return blockstate.m_60643_(this.world, pos, this.summon.getSelfEntity().m_6095_())
         && this.world.m_46859_(pos.m_7494_())
         && this.world.m_46859_(pos.m_6630_(2));
   }
}
