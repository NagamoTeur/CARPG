package com.aizistral.enigmaticlegacy.objects;

import java.util.HashMap;
import net.minecraft.world.entity.LivingEntity;

public class CooldownMap extends HashMap<LivingEntity, Integer> {
   private static final long serialVersionUID = 1159860520734947286L;

   public void tick(LivingEntity entity) {
      if (this.containsKey(entity)) {
         if (this.get(entity) > 0) {
            this.put(entity, Integer.valueOf(this.get(entity) - 1));
         }
      } else {
         this.put(entity, Integer.valueOf(0));
      }
   }

   public boolean hasCooldown(LivingEntity entity) {
      return this.containsKey(entity) && this.get(entity) > 0;
   }

   public int getCooldown(LivingEntity entity) {
      if (this.containsKey(entity)) {
         return this.get(entity);
      } else {
         this.put(entity, Integer.valueOf(0));
         return 0;
      }
   }
}
