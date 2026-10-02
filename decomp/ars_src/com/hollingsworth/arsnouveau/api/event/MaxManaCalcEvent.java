package com.hollingsworth.arsnouveau.api.event;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;

public class MaxManaCalcEvent extends LivingEvent {
   private int max;
   private float reserve;

   public MaxManaCalcEvent(LivingEntity entity, int max) {
      super(entity);
      this.max = max;
      this.reserve = 0.0F;
   }

   public void setMax(int newMax) {
      this.max = Math.max(newMax, 0);
   }

   public int getMax() {
      return this.max;
   }

   public void setReserve(float newReserve) {
      this.reserve = Mth.m_14036_(0.0F, newReserve, 1.0F);
   }

   public float getReserve() {
      return this.reserve;
   }
}
