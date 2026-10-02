package com.github.L_Ender.cataclysm.client.render.item;

import com.github.L_Ender.cataclysm.client.model.CMModelLayers;
import com.github.L_Ender.cataclysm.client.model.armor.Bloom_Stone_Pauldrons_Model;
import com.github.L_Ender.cataclysm.client.model.armor.Bone_Reptile_Armor_Model;
import com.github.L_Ender.cataclysm.client.model.armor.Cursium_Armor_Model;
import com.github.L_Ender.cataclysm.client.model.armor.Ignitium_Armor_Model;
import com.github.L_Ender.cataclysm.client.model.armor.MonstrousHelm_Model;
import com.github.L_Ender.cataclysm.client.model.armor.ignitium_Elytra_chestplate_Model;
import com.github.L_Ender.cataclysm.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class CustomArmorRenderProperties implements IClientItemExtensions {
   private static boolean init;
   public static ignitium_Elytra_chestplate_Model ELYTRA_ARMOR;
   public static MonstrousHelm_Model MONSTROUS_HELM_MODEL;
   public static Ignitium_Armor_Model IGNITIUM_ARMOR_MODEL;
   public static Ignitium_Armor_Model IGNITIUM_ARMOR_MODEL_LEGS;
   public static Bloom_Stone_Pauldrons_Model BLOOM_STONE_PAULDRONS_MODEL;
   public static Bone_Reptile_Armor_Model BONE_REPTILE_ARMOR_MODEL;
   public static Cursium_Armor_Model CURSIUM_ARMOR_MODEL;
   public static Cursium_Armor_Model CURSIUM_ARMOR_MODEL_LEGS;

   public static void initializeModels() {
      init = true;
      MONSTROUS_HELM_MODEL = new MonstrousHelm_Model(Minecraft.m_91087_().m_167973_().m_171103_(CMModelLayers.MONSTROUS_HELM));
      IGNITIUM_ARMOR_MODEL = new Ignitium_Armor_Model(Minecraft.m_91087_().m_167973_().m_171103_(CMModelLayers.IGNITIUM_ARMOR_MODEL));
      ELYTRA_ARMOR = new ignitium_Elytra_chestplate_Model(Minecraft.m_91087_().m_167973_().m_171103_(CMModelLayers.ELYTRA_ARMOR));
      IGNITIUM_ARMOR_MODEL_LEGS = new Ignitium_Armor_Model(Minecraft.m_91087_().m_167973_().m_171103_(CMModelLayers.IGNITIUM_ARMOR_MODEL_LEGS));
      BLOOM_STONE_PAULDRONS_MODEL = new Bloom_Stone_Pauldrons_Model(Minecraft.m_91087_().m_167973_().m_171103_(CMModelLayers.BLOOM_STONE_PAULDRONS_MODEL));
      BONE_REPTILE_ARMOR_MODEL = new Bone_Reptile_Armor_Model(Minecraft.m_91087_().m_167973_().m_171103_(CMModelLayers.BONE_REPTILE_ARMOR_MODEL));
      CURSIUM_ARMOR_MODEL = new Cursium_Armor_Model(Minecraft.m_91087_().m_167973_().m_171103_(CMModelLayers.CURSIUM_ARMOR_MODEL));
      CURSIUM_ARMOR_MODEL_LEGS = new Cursium_Armor_Model(Minecraft.m_91087_().m_167973_().m_171103_(CMModelLayers.CURSIUM_ARMOR_MODEL_LEGS));
   }

   public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
      if (!init) {
         initializeModels();
      }

      if (itemStack.m_41720_() == ModItems.MONSTROUS_HELM.get()) {
         return MONSTROUS_HELM_MODEL;
      } else if (itemStack.m_41720_() == ModItems.IGNITIUM_HELMET.get()) {
         return IGNITIUM_ARMOR_MODEL;
      } else if (itemStack.m_41720_() == ModItems.IGNITIUM_CHESTPLATE.get()) {
         return IGNITIUM_ARMOR_MODEL;
      } else if (itemStack.m_41720_() == ModItems.IGNITIUM_LEGGINGS.get()) {
         return IGNITIUM_ARMOR_MODEL_LEGS;
      } else if (itemStack.m_41720_() == ModItems.IGNITIUM_BOOTS.get()) {
         return IGNITIUM_ARMOR_MODEL;
      } else if (itemStack.m_41720_() == ModItems.CURSIUM_HELMET.get()) {
         return CURSIUM_ARMOR_MODEL;
      } else if (itemStack.m_41720_() == ModItems.CURSIUM_CHESTPLATE.get()) {
         return CURSIUM_ARMOR_MODEL;
      } else if (itemStack.m_41720_() == ModItems.CURSIUM_LEGGINGS.get()) {
         return CURSIUM_ARMOR_MODEL_LEGS;
      } else if (itemStack.m_41720_() == ModItems.CURSIUM_BOOTS.get()) {
         return CURSIUM_ARMOR_MODEL;
      } else if (itemStack.m_41720_() == ModItems.BLOOM_STONE_PAULDRONS.get()) {
         return BLOOM_STONE_PAULDRONS_MODEL;
      } else if (itemStack.m_41720_() == ModItems.BONE_REPTILE_HELMET.get()) {
         return BONE_REPTILE_ARMOR_MODEL;
      } else if (itemStack.m_41720_() == ModItems.BONE_REPTILE_CHESTPLATE.get()) {
         return BONE_REPTILE_ARMOR_MODEL;
      } else {
         return (HumanoidModel<?>)(itemStack.m_41720_() == ModItems.IGNITIUM_ELYTRA_CHESTPLATE.get() ? ELYTRA_ARMOR.withAnimations(entityLiving) : _default);
      }
   }
}
