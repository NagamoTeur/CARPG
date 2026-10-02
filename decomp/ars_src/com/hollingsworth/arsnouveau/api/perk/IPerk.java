package com.hollingsworth.arsnouveau.api.perk;

import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IPerk {
   default Multimap<Attribute, AttributeModifier> getModifiers(EquipmentSlot pEquipmentSlot, ItemStack stack, int slotValue) {
      return new Builder().build();
   }

   default Builder<Attribute, AttributeModifier> attributeBuilder() {
      return new Builder();
   }

   default PerkSlot minimumSlot() {
      return PerkSlot.ONE;
   }

   default boolean validForSlot(PerkSlot slot, ItemStack stack, Player player) {
      if (this.minimumSlot().value > slot.value) {
         PortUtil.sendMessage(player, Component.m_237110_("ars_nouveau.perk.invalid_for_slot", new Object[]{this.minimumSlot().value}));
         return false;
      } else {
         return true;
      }
   }

   ResourceLocation getRegistryName();

   default String getName() {
      return Component.m_237110_(
            this.getRegistryName().m_135827_() + ".thread_of",
            new Object[]{Component.m_237115_("item." + this.getRegistryName().m_135827_() + "." + this.getRegistryName().m_135815_()).getString()}
         )
         .getString();
   }

   default String getLangName() {
      return "";
   }

   default String getLangDescription() {
      return "";
   }

   default String getDescriptionKey() {
      return this.getRegistryName().m_135827_() + ".perk_desc." + this.getRegistryName().m_135815_();
   }
}
