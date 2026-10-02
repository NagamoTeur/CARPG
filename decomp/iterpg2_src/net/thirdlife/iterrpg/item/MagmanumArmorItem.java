package net.thirdlife.iterrpg.item;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.init.IterRpgModItems;

public abstract class MagmanumArmorItem extends ArmorItem {
   public MagmanumArmorItem(EquipmentSlot slot, Properties properties) {
      super(new ArmorMaterial() {
         public int m_7366_(EquipmentSlot slot) {
            return new int[]{13, 15, 16, 11}[slot.m_20749_()] * 25;
         }

         public int m_7365_(EquipmentSlot slot) {
            return new int[]{2, 5, 5, 2}[slot.m_20749_()];
         }

         public int m_6646_() {
            return 9;
         }

         public SoundEvent m_7344_() {
            return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_generic"));
         }

         public Ingredient m_6230_() {
            return Ingredient.m_43927_(new ItemStack[]{new ItemStack((ItemLike)IterRpgModItems.MAGMANUM_CHUNK.get())});
         }

         public String m_6082_() {
            return "magmanum_armor";
         }

         public float m_6651_() {
            return 0.0F;
         }

         public float m_6649_() {
            return 0.0F;
         }
      }, slot, properties);
   }

   public static class Boots extends MagmanumArmorItem {
      public Boots() {
         super(EquipmentSlot.FEET, new Properties().m_41491_(CreativeModeTab.f_40757_).m_41486_());
      }

      public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
         super.m_7373_(itemstack, world, list, flag);
         list.add(Component.m_237115_("iterpg.desc.armor_magmanum"));
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
         return "iter_rpg:textures/models/armor/magmanum_layer_1.png";
      }
   }

   public static class Chestplate extends MagmanumArmorItem {
      public Chestplate() {
         super(EquipmentSlot.CHEST, new Properties().m_41491_(CreativeModeTab.f_40757_).m_41486_());
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
         return "iter_rpg:textures/models/armor/magmanum_layer_1.png";
      }

      public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
         super.m_7373_(itemstack, world, list, flag);
         list.add(Component.m_237115_("iterpg.desc.armor_magmanum"));
      }
   }

   public static class Helmet extends MagmanumArmorItem {
      public Helmet() {
         super(EquipmentSlot.HEAD, new Properties().m_41491_(CreativeModeTab.f_40757_).m_41486_());
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
         return "iter_rpg:textures/models/armor/magmanum_layer_1.png";
      }

      public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
         super.m_7373_(itemstack, world, list, flag);
         list.add(Component.m_237115_("iterpg.desc.armor_magmanum"));
      }
   }

   public static class Leggings extends MagmanumArmorItem {
      public Leggings() {
         super(EquipmentSlot.LEGS, new Properties().m_41491_(CreativeModeTab.f_40757_).m_41486_());
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
         return "iter_rpg:textures/models/armor/magmanum_layer_2.png";
      }

      public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
         super.m_7373_(itemstack, world, list, flag);
         list.add(Component.m_237115_("iterpg.desc.armor_magmanum"));
      }
   }
}
