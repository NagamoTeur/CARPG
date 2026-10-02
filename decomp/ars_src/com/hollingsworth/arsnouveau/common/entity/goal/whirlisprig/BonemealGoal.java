package com.hollingsworth.arsnouveau.common.entity.goal.whirlisprig;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.entity.Whirlisprig;
import com.hollingsworth.arsnouveau.common.entity.goal.DistanceRestrictedGoal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.material.Material;

public class BonemealGoal extends DistanceRestrictedGoal {
   private int timeGrowing;
   BlockPos growPos;
   Whirlisprig sylph;
   public final Predicate<BlockState> IS_GRASS = BlockStatePredicate.m_61287_(Blocks.f_50440_);

   public BonemealGoal(Whirlisprig sylph) {
      super(sylph::m_20183_, 0);
      this.sylph = sylph;
      this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
   }

   public BonemealGoal(Whirlisprig sylph, Supplier<BlockPos> from, int distanceFrom) {
      super(from, distanceFrom);
      this.sylph = sylph;
   }

   public void m_8041_() {
      this.timeGrowing = 0;
      this.growPos = null;
   }

   public void m_8037_() {
      if (this.growPos != null) {
         if (BlockUtil.distanceFrom(this.sylph.m_20183_(), this.growPos) > 1.2) {
            this.sylph.m_21573_().m_26519_((double)this.growPos.m_123341_(), (double)this.growPos.m_123342_(), (double)this.growPos.m_123343_(), 1.2);
         } else {
            ServerLevel world = (ServerLevel)this.sylph.f_19853_;
            world.m_8767_(
               ParticleTypes.f_123749_,
               (double)this.growPos.m_123341_() + 0.5,
               (double)this.growPos.m_123342_() + 1.1,
               (double)this.growPos.m_123343_() + 0.5,
               1,
               ParticleUtil.inRange(-0.2, 0.2),
               0.0,
               ParticleUtil.inRange(-0.2, 0.2),
               0.01
            );
            this.timeGrowing--;
            if (this.timeGrowing <= 0) {
               this.sylph.timeSinceBonemeal = 0;
               ItemStack stack = new ItemStack(Items.f_42499_);
               BoneMealItem.applyBonemeal(stack, world, this.growPos, ANFakePlayer.getPlayer(world));
            }
         }
      }
   }

   public boolean m_8045_() {
      return this.timeGrowing > 0 && this.growPos != null && this.sylph.timeSinceBonemeal >= 9600 && this.isInRange(this.growPos);
   }

   public boolean m_8036_() {
      return this.sylph.f_19853_.f_46441_.m_188503_(5) == 0 && this.sylph.timeSinceBonemeal >= 9600 && this.isInRange(this.sylph.m_20183_());
   }

   public void m_8056_() {
      Level world = this.sylph.f_19853_;
      int range = 4;
      if (this.IS_GRASS.test(world.m_8055_(this.sylph.m_20183_().m_7495_())) && world.m_8055_(this.sylph.m_20183_()).m_60767_() == Material.f_76296_) {
         this.growPos = this.sylph.m_20183_().m_7495_();
      } else {
         List<BlockPos> list = new ArrayList<>();
         BlockPos.m_121990_(this.sylph.m_20183_().m_7918_(range, range, range), this.sylph.m_20183_().m_7918_(-range, -range, -range)).forEach(bp -> {
            bp = bp.m_7949_();
            if (this.IS_GRASS.test(world.m_8055_(bp)) && world.m_8055_(bp.m_7494_()).m_60767_() == Material.f_76296_) {
               list.add(bp);
            }
         });
         Collections.shuffle(list);
         if (!list.isEmpty()) {
            this.growPos = list.get(0);
         }
      }

      this.timeGrowing = 60;
   }
}
