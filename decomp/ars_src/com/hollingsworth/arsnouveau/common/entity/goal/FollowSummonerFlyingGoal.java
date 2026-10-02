package com.hollingsworth.arsnouveau.common.entity.goal;

import com.hollingsworth.arsnouveau.common.entity.IFollowingSummon;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FollowSummonerFlyingGoal extends FollowSummonerGoal {
   public FollowSummonerFlyingGoal(IFollowingSummon mobEntity, LivingEntity owner, double followSpeedIn, float minDistIn, float maxDistIn) {
      super(mobEntity, owner, followSpeedIn, minDistIn, maxDistIn);
   }

   @Override
   protected boolean canTeleportToBlock(BlockPos pos) {
      BlockState blockstate = this.world.m_8055_(pos);
      return (blockstate.isLadder(this.world, pos, this.summon.getSelfEntity()) || blockstate.m_204336_(BlockTags.f_13035_))
         && this.world.m_46859_(pos.m_7494_())
         && this.world.m_46859_(pos.m_6630_(2));
   }
}
