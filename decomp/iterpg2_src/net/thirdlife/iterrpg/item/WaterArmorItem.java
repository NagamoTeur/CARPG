package net.thirdlife.iterrpg.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.init.IterRpgModItems;
import net.thirdlife.iterrpg.procedures.OceanArmorSetBonusProcedure;
import net.thirdlife.iterrpg.procedures.WaterSetRepairProcedure;

public abstract class WaterArmorItem extends ArmorItem {
   public WaterArmorItem(EquipmentSlot slot, Properties properties) {
      super(
         new ArmorMaterial() {
            public int m_7366_(EquipmentSlot slot) {
               return new int[]{13, 15, 16, 11}[slot.m_20749_()] * 25;
            }

            public int m_7365_(EquipmentSlot slot) {
               return new int[]{3, 4, 5, 3}[slot.m_20749_()];
            }

            public int m_6646_() {
               return 16;
            }

            public SoundEvent m_7344_() {
               return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_iron"));
            }

            public Ingredient m_6230_() {
               return Ingredient.m_43927_(
                  new ItemStack[]{
                     new ItemStack((ItemLike)IterRpgModItems.DROPLET_PROJECTILE.get()), new ItemStack((ItemLike)IterRpgModItems.WATER_INGOT.get())
                  }
               );
            }

            public String m_6082_() {
               return "water_armor";
            }

            public float m_6651_() {
               return 2.0F;
            }

            public float m_6649_() {
               return 0.0F;
            }
         },
         slot,
         properties
      );
   }

   public static class Boots extends WaterArmorItem {
      public Boots() {
         super(EquipmentSlot.FEET, new Properties().m_41491_(CreativeModeTab.f_40757_));
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
         return "iter_rpg:textures/models/armor/ocean_armor_layer_1.png";
      }

      public void onArmorTick(ItemStack itemstack, Level world, Player entity) {
         WaterSetRepairProcedure.execute(world, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), entity, itemstack);
      }
   }

   public static class Chestplate extends WaterArmorItem {
      public Chestplate() {
         super(EquipmentSlot.CHEST, new Properties().m_41491_(CreativeModeTab.f_40757_));
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
         return "iter_rpg:textures/models/armor/ocean_armor_layer_1.png";
      }

      public void onArmorTick(ItemStack itemstack, Level world, Player entity) {
         OceanArmorSetBonusProcedure.execute(world, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), entity, itemstack);
      }
   }

   public static class Helmet extends WaterArmorItem {
      public Helmet() {
         super(EquipmentSlot.HEAD, new Properties().m_41491_(CreativeModeTab.f_40757_));
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
         return "iter_rpg:textures/models/armor/ocean_armor_layer_1.png";
      }

      public void onArmorTick(ItemStack itemstack, Level world, Player entity) {
         WaterSetRepairProcedure.execute(world, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), entity, itemstack);
      }
   }

   public static class Leggings extends WaterArmorItem {
      public Leggings() {
         super(EquipmentSlot.LEGS, new Properties().m_41491_(CreativeModeTab.f_40757_));
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
         return "iter_rpg:textures/models/armor/ocean_armor_layer_2.png";
      }

      public void onArmorTick(ItemStack itemstack, Level world, Player entity) {
         WaterSetRepairProcedure.execute(world, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), entity, itemstack);
      }
   }
}
