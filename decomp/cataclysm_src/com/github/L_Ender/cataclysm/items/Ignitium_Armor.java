package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.init.ModItems;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class Ignitium_Armor extends ArmorItem {
   public Ignitium_Armor(Armortier material, EquipmentSlot slot, Properties properties) {
      super(material, slot, properties);
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept((IClientItemExtensions)Cataclysm.PROXY.getArmorRenderProperties());
   }

   public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
      return "cataclysm:textures/armor/ignitium_armor" + (slot == EquipmentSlot.LEGS ? "_legs.png" : ".png");
   }

   public void setDamage(ItemStack stack, int damage) {
      if (CMConfig.Armor_Infinity_Durability) {
         super.setDamage(stack, 0);
      } else {
         super.setDamage(stack, damage);
      }
   }

   public boolean m_6832_(ItemStack p_41134_, ItemStack p_41135_) {
      return p_41135_.m_150930_((Item)ModItems.IGNITIUM_INGOT.get());
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      if (this.f_40377_ == EquipmentSlot.HEAD) {
         tooltip.add(Component.m_237115_("item.cataclysm.ignitium_helmet.desc").m_130940_(ChatFormatting.DARK_GREEN));
      }

      if (this.f_40377_ == EquipmentSlot.CHEST) {
         tooltip.add(Component.m_237115_("item.cataclysm.ignitium_chestplate.desc").m_130940_(ChatFormatting.DARK_GREEN));
      }

      if (this.f_40377_ == EquipmentSlot.LEGS) {
         tooltip.add(Component.m_237115_("item.cataclysm.ignitium_leggings.desc").m_130940_(ChatFormatting.DARK_GREEN));
      }

      if (this.f_40377_ == EquipmentSlot.FEET) {
         tooltip.add(Component.m_237115_("item.cataclysm.ignitium_boots.desc").m_130940_(ChatFormatting.DARK_GREEN));
      }
   }
}
