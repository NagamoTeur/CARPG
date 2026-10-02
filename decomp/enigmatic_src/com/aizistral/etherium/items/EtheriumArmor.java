package com.aizistral.etherium.items;

import com.aizistral.enigmaticlegacy.api.materials.EnigmaticArmorMaterials;
import com.aizistral.enigmaticlegacy.config.EtheriumConfigHandler;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.helpers.ItemNBTHelper;
import com.aizistral.etherium.core.EtheriumUtil;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EtheriumArmor extends ArmorItem {
   public EtheriumArmor(EquipmentSlot slot) {
      super(EnigmaticArmorMaterials.ETHERIUM, slot, EtheriumUtil.defaultProperties(EtheriumArmor.class).m_41486_());
   }

   public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
      return "enigmaticlegacy:textures/models/armor/unseen_armor.png";
   }

   public static boolean hasFullSet(@Nonnull Player player) {
      if (player == null) {
         return false;
      } else {
         for (ItemStack stack : player.m_6168_()) {
            if (stack.m_41720_().getClass() != EtheriumArmor.class) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean hasShield(@Nonnull Player player) {
      return player != null
         && hasFullSet(player)
         && (double)(player.m_21223_() / player.m_21233_()) <= EtheriumConfigHandler.instance().getShieldThreshold(player).asMultiplier(false);
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      if (Screen.m_96638_()) {
         EtheriumArmor armor = (EtheriumArmor)stack.m_41720_();
         if (armor.m_40402_() == EquipmentSlot.HEAD) {
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.etheriumHelmet1");
         } else if (armor.m_40402_() == EquipmentSlot.CHEST) {
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.etheriumChestplate1");
         } else if (armor.m_40402_() == EquipmentSlot.LEGS) {
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.etheriumLeggings1");
         } else if (armor.m_40402_() == EquipmentSlot.FEET) {
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.etheriumBoots1");
         }
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }

      if (hasFullSet(Minecraft.m_91087_().f_91074_)
         || ItemNBTHelper.verifyExistance(stack, "forceDisplaySetBonus") && ItemNBTHelper.getBoolean(stack, "forceDisplaySetBonus", false)) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.etheriumArmorSetBonus1");
         ItemLoreHelper.addLocalizedString(
            list,
            "tooltip.enigmaticlegacy.etheriumArmorSetBonus2",
            ChatFormatting.GOLD,
            EtheriumConfigHandler.instance().getShieldThreshold(Minecraft.m_91087_().f_91074_).asPercentage() + "%"
         );
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.etheriumArmorSetBonus3");
         ItemLoreHelper.addLocalizedString(
            list,
            "tooltip.enigmaticlegacy.etheriumArmorSetBonus4",
            ChatFormatting.GOLD,
            EtheriumConfigHandler.instance().getShieldReduction().asPercentage() + "%"
         );
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.etheriumArmorSetBonus5");
      }

      if (stack.m_41793_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      }
   }
}
