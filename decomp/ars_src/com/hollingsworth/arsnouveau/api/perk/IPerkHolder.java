package com.hollingsworth.arsnouveau.api.perk;

import com.hollingsworth.arsnouveau.api.util.RomanNumber;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public interface IPerkHolder<T> {
   default List<PerkInstance> getPerkInstances() {
      List<PerkInstance> perkInstances = new ArrayList<>();
      List<PerkSlot> slots = new ArrayList<>(this.getSlotsForTier());
      List<IPerk> perks = this.getPerks();

      for (int i = 0; i < slots.size(); i++) {
         if (i < perks.size()) {
            perkInstances.add(new PerkInstance(slots.get(i), perks.get(i)));
         }
      }

      return perkInstances;
   }

   List<IPerk> getPerks();

   void setPerks(List<IPerk> var1);

   List<PerkSlot> getSlotsForTier();

   default boolean isEmpty() {
      return this.getPerks().isEmpty();
   }

   default void appendPerkTooltip(List<Component> tooltip, T obj) {
      for (PerkInstance perkInstance : this.getPerkInstances()) {
         IPerk perk = perkInstance.getPerk();
         ResourceLocation location = perk.getRegistryName();
         tooltip.add(
            Component.m_237113_(
               Component.m_237115_("item." + location.m_135827_() + "." + location.m_135815_()).getString()
                  + " "
                  + RomanNumber.toRoman(perkInstance.getSlot().value)
            )
         );
      }

      int missing = this.getSlotsForTier().size() - this.getPerkInstances().size();

      for (int i = 0; i < missing; i++) {
         PerkSlot slot = new ArrayList<>(this.getSlotsForTier()).subList(this.getPerkInstances().size(), this.getSlotsForTier().size()).get(i);
         tooltip.add(
            Component.m_237113_(Component.m_237115_("Empty").getString() + " " + RomanNumber.toRoman(slot.value))
               .m_130940_(ChatFormatting.RED)
               .m_130940_(ChatFormatting.ITALIC)
         );
      }
   }

   int getTier();

   void setTier(int var1);

   @Nullable
   CompoundTag getTagForPerk(IPerk var1);

   void setTagForPerk(IPerk var1, CompoundTag var2);
}
