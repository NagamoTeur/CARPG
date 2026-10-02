package com.hollingsworth.arsnouveau.common.util;

import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;

public class PotionUtil {
   public static void addPotionToTag(Potion potionIn, CompoundTag tag) {
      ResourceLocation resourcelocation = Registry.f_122828_.m_7981_(potionIn);
      if (potionIn == Potions.f_43598_) {
         if (tag.m_128441_("Potion")) {
            tag.m_128473_("Potion");
         }
      } else {
         tag.m_128359_("Potion", resourcelocation.toString());
      }
   }
}
