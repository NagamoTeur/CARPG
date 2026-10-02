package com.hollingsworth.arsnouveau.common.entity.goal.carbuncle;

import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.common.block.SourceBerryBush;
import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import com.hollingsworth.arsnouveau.common.entity.debug.DebugEvent;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ForageManaBerries extends Goal {
   private final Starbuncle entity;
   private final Level world;
   int timeSpent;
   BlockPos pos;
   StarbyTransportBehavior behavior;

   public ForageManaBerries(Starbuncle starbuncle, StarbyTransportBehavior transportBehavior) {
      this.entity = starbuncle;
      this.world = this.entity.f_19853_;
      this.m_7021_(EnumSet.of(Flag.MOVE));
      this.behavior = transportBehavior;
   }

   public void m_8056_() {
      super.m_8056_();
      this.timeSpent = 0;
      this.entity.goalState = Starbuncle.StarbuncleGoalState.FORAGING;
   }

   public void m_8041_() {
      super.m_8041_();
      this.timeSpent = 0;
      this.entity.goalState = Starbuncle.StarbuncleGoalState.NONE;
   }

   public boolean m_8036_() {
      if (!this.behavior.isPickupDisabled()
         && this.entity.getHeldStack().m_41619_()
         && !(this.world.f_46441_.m_188500_() > 0.05)
         && this.behavior.getValidStorePos(new ItemStack(BlockRegistry.SOURCEBERRY_BUSH)) != null) {
         this.pos = this.getNearbyManaBerry();
         if (this.pos == null) {
            this.entity.addGoalDebug(this, new DebugEvent("NoBerries", "No Berries Nearby"));
            return false;
         } else if (this.behavior.isBedPowered()) {
            this.entity.addGoalDebug(this, new DebugEvent("Bed Powered", "Bed powered, no berry pickin"));
            return false;
         } else {
            return this.pos != null;
         }
      } else {
         return false;
      }
   }

   public boolean m_6767_() {
      return false;
   }

   public void m_8037_() {
      super.m_8037_();
      this.timeSpent++;
      if (this.pos != null) {
         if (BlockUtil.distanceFrom(this.entity.f_19825_, this.pos) >= 2.0) {
            this.entity.getNavigation().tryMoveToBlockPos(this.pos, 1.2);
            this.entity.addGoalDebug(this, new DebugEvent("PathTo", "Moving to berry " + this.pos.toString()));
         } else if (this.world.m_8055_(this.pos).m_60734_() instanceof SourceBerryBush) {
            int i = (Integer)this.world.m_8055_(this.pos).m_61143_(SourceBerryBush.AGE);
            boolean flag = i == 3;
            this.entity.m_7618_(Anchor.EYES, new Vec3((double)this.pos.m_123341_(), (double)this.pos.m_123342_(), (double)this.pos.m_123343_()));
            int j = 1 + this.world.f_46441_.m_188503_(2);
            SourceBerryBush.m_49840_(this.world, this.pos, new ItemStack(BlockRegistry.SOURCEBERRY_BUSH, j + (flag ? 1 : 0)));
            this.world.m_5594_(null, this.pos, SoundEvents.f_12457_, SoundSource.BLOCKS, 1.0F, 0.8F + this.world.f_46441_.m_188501_() * 0.4F);
            this.world.m_7731_(this.pos, (BlockState)this.world.m_8055_(this.pos).m_61124_(SourceBerryBush.AGE, 1), 2);
            this.entity
               .addGoalDebug(
                  this, new DebugEvent("PickedBerry", "Popped berries at " + this.pos.m_123341_() + "," + this.pos.m_123342_() + "," + this.pos.m_123343_())
               );
            this.pos = null;
         }
      }
   }

   public boolean m_8045_() {
      return this.pos != null && !this.behavior.isPickupDisabled()
         ? this.timeSpent <= 300
            && this.world.m_8055_(this.pos).m_60734_() instanceof SourceBerryBush
            && (Integer)this.world.m_8055_(this.pos).m_61143_(SourceBerryBush.AGE) > 1
         : false;
   }

   public BlockPos getNearbyManaBerry() {
      List<BlockPos> posList = new ArrayList<>();

      for (BlockPos blockpos : BlockPos.m_121925_(this.entity.m_20183_(), 10, 3, 10)) {
         if (this.world.m_8055_(blockpos).m_60734_() instanceof SourceBerryBush && (Integer)this.world.m_8055_(blockpos).m_61143_(SourceBerryBush.AGE) > 1) {
            posList.add(blockpos.m_7949_());
         }
      }

      return posList.isEmpty() ? null : posList.get(this.world.f_46441_.m_188503_(posList.size()));
   }
}
