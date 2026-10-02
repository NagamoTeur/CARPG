package com.aizistral.enigmaticlegacy.api.items;

public interface IAdvancedPotionItem {
   IAdvancedPotionItem.PotionType getPotionType();

   public static enum PotionType {
      COMMON,
      ULTIMATE;
   }
}
