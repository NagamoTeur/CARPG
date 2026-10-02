package com.aizistral.enigmaticlegacy.objects;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.effect.MobEffectInstance;

public class AdvancedPotion {
   private List<MobEffectInstance> effects = new ArrayList<>();
   private String id;

   public AdvancedPotion(String identifier, MobEffectInstance... effects) {
      for (MobEffectInstance effect : effects) {
         this.effects.add(effect);
      }

      this.id = identifier;
   }

   public String getId() {
      return this.id;
   }

   public List<MobEffectInstance> getEffects() {
      List<MobEffectInstance> returnList = new ArrayList<>();

      for (MobEffectInstance effect : this.effects) {
         returnList.add(new MobEffectInstance(effect));
      }

      return returnList;
   }
}
