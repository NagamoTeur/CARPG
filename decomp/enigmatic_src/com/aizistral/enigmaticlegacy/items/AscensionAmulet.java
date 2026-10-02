package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.helpers.ItemNBTHelper;
import com.aizistral.enigmaticlegacy.registries.EnigmaticItems;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;

public class AscensionAmulet extends EnigmaticAmulet {
   public AscensionAmulet() {
      this(getDefaultProperties().m_41497_(Rarity.EPIC).m_41486_(), "ascension_amulet");
   }

   protected AscensionAmulet(Properties properties, String name) {
      super(properties, name);
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      String name = ItemNBTHelper.getString(stack, "Inscription", null);
      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      if (Screen.m_96638_() && this.isVesselEnabled()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.enigmaticAmuletShift1");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.enigmaticAmuletShift2");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.enigmaticAmuletShift3");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.enigmaticAmuletShift4");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.enigmaticAmuletShift5");
      } else {
         if (this.isVesselEnabled()) {
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
         }

         if (name != null) {
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.enigmaticAmuletInscription", ChatFormatting.DARK_RED, name);
         }
      }

      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      this.addAttributes(list, stack);
   }

   @Override
   public void m_6787_(CreativeModeTab group, NonNullList<ItemStack> items) {
      if (this.m_220152_(group)) {
         items.add(new ItemStack(this));
      }
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   protected void addAttributes(List<Component> list, ItemStack stack) {
      ItemLoreHelper.addLocalizedFormattedString(list, "curios.modifiers.charm", ChatFormatting.GOLD);

      for (EnigmaticAmulet.AmuletColor color : EnigmaticAmulet.AmuletColor.values()) {
         if (color != EnigmaticAmulet.AmuletColor.RED) {
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.enigmaticAmuletModifier" + color);
         } else {
            ItemLoreHelper.addLocalizedString(
               list, "tooltip.enigmaticlegacy.enigmaticAmuletModifierRED", ChatFormatting.GOLD, minimizeNumber(damageBonus.getValue())
            );
         }
      }
   }

   public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
      return super.getAllModifiers(slotContext.entity() instanceof Player player ? player : null);
   }

   @Override
   public List<Component> getAttributesTooltip(List<Component> tooltips, ItemStack stack) {
      tooltips.clear();
      return tooltips;
   }

   @Override
   public boolean canEquip(SlotContext context, ItemStack stack) {
      return !SuperpositionHandler.hasCurio(context.entity(), EnigmaticItems.ENIGMATIC_AMULET)
         && !SuperpositionHandler.hasCurio(context.entity(), EnigmaticItems.ASCENSION_AMULET);
   }
}
