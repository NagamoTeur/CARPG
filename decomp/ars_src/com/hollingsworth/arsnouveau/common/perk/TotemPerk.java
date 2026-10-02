package com.hollingsworth.arsnouveau.common.perk;

import com.hollingsworth.arsnouveau.api.nbt.PerkData;
import com.hollingsworth.arsnouveau.api.perk.IPerkHolder;
import com.hollingsworth.arsnouveau.api.perk.Perk;
import com.hollingsworth.arsnouveau.api.perk.PerkSlot;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public class TotemPerk extends Perk {
   public static TotemPerk INSTANCE = new TotemPerk(new ResourceLocation("ars_nouveau", "thread_undying"));

   public TotemPerk(ResourceLocation key) {
      super(key);
   }

   @Override
   public String getLangDescription() {
      return "Once every time you sleep, you will nullify death a single time as if holding a Totem of the Undying. Requires a tier 3 slot.";
   }

   @Override
   public PerkSlot minimumSlot() {
      return PerkSlot.THREE;
   }

   @Override
   public String getLangName() {
      return "Undying";
   }

   public static class Data extends PerkData {
      private boolean isActive;

      public Data(IPerkHolder<?> perkHolder) {
         super(perkHolder, TotemPerk.INSTANCE);
         CompoundTag initTag = this.getInitTag();
         if (initTag != null) {
            this.isActive = !initTag.m_128441_("isActive") || initTag.m_128471_("isActive");
         }
      }

      public void setActive(boolean active) {
         this.isActive = active;
         this.writePerks();
      }

      public boolean isActive() {
         return this.isActive;
      }

      @Override
      public void writeToNBT(CompoundTag tag) {
         super.writeToNBT(tag);
         tag.m_128379_("isActive", this.isActive);
      }
   }
}
