package com.hollingsworth.arsnouveau.common.entity.goal.bookwyrm;

import com.hollingsworth.arsnouveau.api.event.EventQueue;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.entity.EntityBookwyrm;
import com.hollingsworth.arsnouveau.common.event.OpenChestEvent;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class TransferGoal extends Goal {
   public int time;
   public TransferTask task;
   public boolean isDone;
   public boolean reachedFrom;
   public EntityBookwyrm bookwyrm;

   public TransferGoal(EntityBookwyrm bookwyrm) {
      this.bookwyrm = bookwyrm;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public void m_8056_() {
      this.isDone = false;
      this.time = 0;
      this.reachedFrom = false;
   }

   public boolean m_8045_() {
      return this.task != null && !this.isDone;
   }

   public boolean m_8036_() {
      if (this.bookwyrm.f_19853_.m_46467_() % 2L != 0L && this.bookwyrm.m_217043_().m_188503_(10) != 0) {
         return false;
      } else {
         this.task = this.bookwyrm.getTransferTask();
         return this.task != null;
      }
   }

   public boolean m_6767_() {
      return false;
   }

   public void m_8037_() {
      this.time++;
      if (this.task != null && !this.isDone && this.time <= 200) {
         if (!this.reachedFrom) {
            if (BlockUtil.distanceFrom(this.bookwyrm.m_20182_(), new Vec3(this.task.from.f_82479_, this.task.from.m_7098_(), this.task.from.m_7094_())) <= 1.5) {
               this.reachedFrom = true;
               if (this.task != null) {
                  this.bookwyrm.setHeldStack(this.task.stack);
                  this.bookwyrm
                     .f_19853_
                     .m_6263_(
                        null,
                        this.bookwyrm.m_20185_(),
                        this.bookwyrm.m_20186_(),
                        this.bookwyrm.m_20189_(),
                        SoundEvents.f_12019_,
                        this.bookwyrm.m_5720_(),
                        0.3F + (float)ParticleUtil.inRange(-0.1, 0.1),
                        1.0F + (float)ParticleUtil.inRange(-0.1, 0.1)
                     );
                  if (this.bookwyrm.f_19853_ instanceof ServerLevel serverLevel) {
                     OpenChestEvent event = new OpenChestEvent(serverLevel, new BlockPos(this.task.from.m_82492_(0.0, 1.0, 0.0)), 20);
                     event.open();
                     EventQueue.getServerInstance().addEvent(event);
                  }
               }
            } else {
               this.bookwyrm.m_21573_().m_26519_(this.task.from.m_7096_(), this.task.from.m_7098_(), this.task.from.m_7094_(), 1.3);
               if (this.bookwyrm.m_21573_().m_26570_() == null) {
                  this.isDone = true;
               }
            }
         } else if (BlockUtil.distanceFrom(this.bookwyrm.m_20182_(), new Vec3(this.task.to.m_7096_(), this.task.to.m_7098_(), this.task.to.m_7094_())) <= 1.5) {
            this.isDone = true;
            this.bookwyrm.setHeldStack(ItemStack.f_41583_);
            if (this.bookwyrm.f_19853_ instanceof ServerLevel serverLevel) {
               OpenChestEvent event = new OpenChestEvent(serverLevel, new BlockPos(this.task.to.m_82492_(0.0, 1.0, 0.0)), 20);
               event.open();
               EventQueue.getServerInstance().addEvent(event);
            }
         } else {
            this.bookwyrm.m_21573_().m_26519_(this.task.to.m_7096_(), this.task.to.m_7098_(), this.task.to.m_7094_(), 1.3);
            if (this.bookwyrm.m_21573_().m_26570_() == null) {
               this.isDone = true;
            }
         }
      } else {
         this.isDone = true;
         this.bookwyrm.setHeldStack(ItemStack.f_41583_);
      }
   }
}
