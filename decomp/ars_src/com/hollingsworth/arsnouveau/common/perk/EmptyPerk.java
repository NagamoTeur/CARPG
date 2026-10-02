package com.hollingsworth.arsnouveau.common.perk;

import com.hollingsworth.arsnouveau.api.perk.Perk;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class EmptyPerk extends Perk {
   public static ResourceLocation registryName = new ResourceLocation("ars_nouveau", "blank_thread");
   public static EmptyPerk INSTANCE = new EmptyPerk(registryName);

   public EmptyPerk(ResourceLocation key) {
      super(key);
   }

   @Override
   public String getName() {
      return Component.m_237115_("item.ars_nouveau.blank_thread").getString();
   }

   @Override
   public String getLangName() {
      return "Blank";
   }

   @Override
   public String getDescriptionKey() {
      return "tooltip.ars_nouveau.blank_thread";
   }
}
