package com.hollingsworth.arsnouveau.api.perk;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

public class ArmorPerkHolder extends StackPerkHolder {
   private String color;
   private List<List<PerkSlot>> slotsForTier;

   public ArmorPerkHolder(ItemStack stack, List<List<PerkSlot>> slotsForTier) {
      super(stack);
      CompoundTag tag = this.getItemTag(stack);
      this.slotsForTier = slotsForTier;
      if (tag != null) {
         this.color = tag.m_128461_("color");
      }
   }

   public String getColor() {
      return this.color == null ? DyeColor.PURPLE.m_41065_() : this.color;
   }

   public void setColor(String color) {
      this.color = color;
      this.writeItem();
   }

   @Override
   public void writeToNBT(CompoundTag tag) {
      super.writeToNBT(tag);
      if (this.color != null) {
         tag.m_128359_("color", this.color);
      }
   }

   @Override
   public List<PerkSlot> getSlotsForTier() {
      List<PerkSlot> slots = new ArrayList<>(this.slotsForTier.get(this.getTier()));
      slots.sort(Comparator.comparingInt(a -> -a.value));
      return slots;
   }
}
