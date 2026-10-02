package com.hollingsworth.arsnouveau.common.entity.goal.drygmy;

import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.common.block.tile.DrygmyTile;
import com.hollingsworth.arsnouveau.common.entity.EntityDrygmy;
import com.hollingsworth.arsnouveau.common.entity.EntityFlyingItem;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.level.pathfinder.Path;

public class CollectEssenceGoal extends Goal {
   public EntityDrygmy drygmy;
   public LivingEntity target;
   public boolean complete;
   public boolean approached;
   public int timeChanneling;
   public int timePathing;

   public CollectEssenceGoal(EntityDrygmy drygmy) {
      this.drygmy = drygmy;
      this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
   }

   public boolean m_8036_() {
      return this.drygmy.channelCooldown <= 0 && this.drygmy.getHome() != null && !this.drygmy.getHome().isOff;
   }

   public boolean m_6767_() {
      return false;
   }

   public void m_8056_() {
      super.m_8056_();
      DrygmyTile tile = this.drygmy.getHome();
      if (tile != null) {
         this.target = tile.getRandomEntity();
         this.complete = false;
         this.approached = false;
         this.timeChanneling = 0;
         this.timePathing = 0;
      }
   }

   public boolean m_8045_() {
      return !this.complete && this.m_8036_() && this.target != null && !this.target.m_213877_() && this.target.m_6084_();
   }

   public void m_8037_() {
      super.m_8037_();
      if (!this.complete && this.target != null) {
         if (this.approached) {
            this.drygmy.setChannelingEntity(this.target.m_19879_());
            this.drygmy.m_21563_().m_24960_(this.target, 10.0F, (float)this.drygmy.m_8132_());
            this.drygmy.m_21573_().m_26573_();
            this.approached = true;
            this.drygmy.setChanneling(true);
            this.timeChanneling++;
            if (this.timeChanneling >= 100) {
               this.drygmy.setChanneling(false);
               this.drygmy.setHoldingEssence(true);
               this.drygmy.setChannelingEntity(-1);
               this.complete = true;
               BlockPos homePos = this.drygmy.getHome().m_58899_();
               BlockPos targetPos = this.target.m_20183_();
               if (homePos.m_123342_() >= targetPos.m_123342_() - 2) {
                  targetPos = targetPos.m_6630_(homePos.m_123342_() - targetPos.m_123342_());
               }

               EntityFlyingItem item = new EntityFlyingItem(this.drygmy.f_19853_, targetPos, homePos, 50, 255, 20);
               this.drygmy.f_19853_.m_7967_(item);
               this.drygmy.channelCooldown = 100;
               this.drygmy.getHome().giveProgress();
            }
         } else {
            if (this.timePathing > 160) {
               this.approached = true;
               this.drygmy.m_21573_().m_26573_();
            }

            if (BlockUtil.distanceFrom(this.drygmy.f_19825_, this.target.f_19825_) <= 2.3) {
               this.approached = true;
            } else {
               this.timePathing++;
               Path path = this.drygmy.m_21573_().m_26524_(this.target.m_20185_(), this.target.m_20186_(), this.target.m_20189_(), 1);
               if (path != null && path.m_77403_()) {
                  this.drygmy.m_21573_().m_26536_(path, 1.0);
               } else {
                  this.approached = true;
                  this.drygmy.m_21573_().m_26573_();
               }
            }
         }
      }
   }

   public void m_8041_() {
      super.m_8041_();
      this.complete = false;
      this.approached = false;
      this.drygmy.setChannelingEntity(-1);
      this.drygmy.setChanneling(false);
      this.timeChanneling = 0;
   }
}
