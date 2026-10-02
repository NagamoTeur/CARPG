package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.init.ModItems;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class Ignitium_Elytra_ChestPlate extends ArmorItem {
   public Ignitium_Elytra_ChestPlate(Properties props, Armortier mat) {
      super(mat, EquipmentSlot.CHEST, props);
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept((IClientItemExtensions)Cataclysm.PROXY.getArmorRenderProperties());
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

   public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
      return true;
   }

   public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
      return ElytraItem.m_41140_(stack);
   }

   public EquipmentSlot getEquipmentSlot(ItemStack stack) {
      return EquipmentSlot.CHEST;
   }

   @Nullable
   public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
      return "cataclysm:textures/armor/ignitium_elytra_chestplate.png";
   }
}
