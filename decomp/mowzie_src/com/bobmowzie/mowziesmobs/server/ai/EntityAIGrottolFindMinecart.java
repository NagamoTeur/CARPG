package com.bobmowzie.mowziesmobs.server.ai;

import com.bobmowzie.mowziesmobs.server.entity.grottol.EntityGrottol;
import com.google.common.base.Predicate;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.vehicle.Minecart;

public final class EntityAIGrottolFindMinecart extends Goal {
   private final EntityGrottol grottol;
   private final Comparator<Entity> sorter;
   private final Predicate<Minecart> predicate;
   private Minecart minecart;
   private int time;

   public EntityAIGrottolFindMinecart(EntityGrottol grottol) {
      this.grottol = grottol;
      this.sorter = Comparator.comparing(grottol::m_20280_);
      this.predicate = minecart -> minecart != null
            && minecart.m_6084_()
            && !minecart.m_20160_()
            && EntityGrottol.isBlockRail(minecart.f_19853_.m_8055_(minecart.m_20183_()).m_60734_());
      this.m_7021_(EnumSet.of(Flag.LOOK, Flag.MOVE));
   }

   public boolean m_8036_() {
      if (this.grottol.fleeTime <= 1) {
         return false;
      } else {
         List<Minecart> minecarts = this.grottol.f_19853_.m_6443_(Minecart.class, this.grottol.m_20191_().m_82377_(8.0, 4.0, 8.0), this.predicate);
         minecarts.sort(this.sorter);
         if (minecarts.isEmpty()) {
            return false;
         } else {
            this.minecart = minecarts.get(0);
            return true;
         }
      }
   }

   public boolean m_8045_() {
      return this.predicate.test(this.minecart) && this.time < 1200 && !this.grottol.isInMinecart();
   }

   public void m_8056_() {
      this.time = 0;
      this.grottol.m_21573_().m_5624_(this.minecart, 0.5);
   }

   public void m_8041_() {
      this.grottol.m_21573_().m_26573_();
   }

   public void m_8037_() {
      if (this.grottol.m_20280_(this.minecart) > 2.1025) {
         this.grottol.m_21563_().m_24960_(this.minecart, 10.0F, (float)this.grottol.m_8132_());
         if (++this.time % 40 == 0) {
            this.grottol.m_21573_().m_5624_(this.minecart, 0.5);
         }
      } else {
         this.grottol.m_7998_(this.minecart, true);
         if (this.minecart.m_38176_() == 0) {
            this.minecart.m_38160_(-this.minecart.m_38177_());
            this.minecart.m_38154_(10);
            this.minecart.m_38109_(50.0F);
         }
      }
   }
}
