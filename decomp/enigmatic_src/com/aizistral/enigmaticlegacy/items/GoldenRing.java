package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBaseCurio;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;

public class GoldenRing extends ItemBaseCurio {
   public GoldenRing() {
      super(ItemBaseCurio.getDefaultProperties().m_41497_(Rarity.UNCOMMON));
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, Level world, List<Component> list, TooltipFlag flag) {
      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      if (Screen.m_96638_()) {
         if (Minecraft.m_91087_().f_91074_ != null && SuperpositionHandler.isTheCursedOne(Minecraft.m_91087_().f_91074_)) {
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.goldenRing1Cursed");
         } else {
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.goldenRing1");
         }
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }
   }

   public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
      Multimap<Attribute, AttributeModifier> attributes = HashMultimap.create();
      attributes.put(
         Attributes.f_22286_,
         new AttributeModifier(UUID.fromString("6c913e9a-0d6f-4b3b-81b9-4c82f7778b52"), "enigmaticlegacy:luck_bonus", 1.0, Operation.ADDITION)
      );
      return attributes;
   }

   public boolean rendersPiglinsNeutral(String identifier, ItemStack stack, LivingEntity wearer) {
      return true;
   }

   public boolean isPiglinCurrency(ItemStack stack) {
      return true;
   }
}
