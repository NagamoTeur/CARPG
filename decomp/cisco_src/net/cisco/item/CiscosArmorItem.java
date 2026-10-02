package net.cisco.item;

import java.util.List;
import net.cisco.init.CiscoModModItems;
import net.cisco.init.CiscoModModTabs;
import net.cisco.procedures.DivineArmorBootsTickEventProcedure;
import net.cisco.procedures.DivineArmorChestplateTickEventProcedure;
import net.cisco.procedures.DivineArmorHelmetTickEventProcedure;
import net.cisco.procedures.DivineArmorLeggingsTickEventProcedure;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class CiscosArmorItem extends ArmorItem {
   public CiscosArmorItem(EquipmentSlot slot, Properties properties) {
      super(new ArmorMaterial() {
         public int m_7366_(EquipmentSlot slot) {
            return new int[]{13, 15, 16, 11}[slot.m_20749_()] * 300;
         }

         public int m_7365_(EquipmentSlot slot) {
            return new int[]{9, 14, 16, 10}[slot.m_20749_()];
         }

         public int m_6646_() {
            return 20;
         }

         public SoundEvent m_7344_() {
            return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_netherite"));
         }

         public Ingredient m_6230_() {
            return Ingredient.m_43927_(new ItemStack[]{new ItemStack((ItemLike)CiscoModModItems.BRIGHTSTEEL_INGOT.get())});
         }

         public String m_6082_() {
            return "ciscos_armor";
         }

         public float m_6651_() {
            return 5.0F;
         }

         public float m_6649_() {
            return 0.25F;
         }
      }, slot, properties);
   }

   public static class Boots extends CiscosArmorItem {
      public Boots() {
         super(EquipmentSlot.FEET, new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD).m_41486_());
      }

      public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
         super.m_7373_(itemstack, world, list, flag);
         list.add(Component.m_237113_("§5Blessed by Mercury my boots granted me a powerful jump."));
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§6[Full set Bonus] : §fgrants effect Cisco's Might."));
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§a[Key Press Ability] : §fCisco's Clarity. (default  key [x] )"));
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
         return "cisco_mod:textures/models/armor/divine_armor__layer_1.png";
      }

      public void onArmorTick(ItemStack itemstack, Level world, Player entity) {
         DivineArmorBootsTickEventProcedure.execute(entity);
      }
   }

   public static class Chestplate extends CiscosArmorItem {
      public Chestplate() {
         super(EquipmentSlot.CHEST, new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD).m_41486_());
      }

      public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
         super.m_7373_(itemstack, world, list, flag);
         list.add(Component.m_237113_("§5Blessed by Hercules my chestplate granted me divine protection."));
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§6[Full set Bonus] : §fgrants effect Cisco's Might."));
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§a[Key Press Ability] : §fCisco's Clarity. (default  key [x] )"));
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
         return "cisco_mod:textures/models/armor/divine_armor__layer_1.png";
      }

      public void onArmorTick(ItemStack itemstack, Level world, Player entity) {
         DivineArmorChestplateTickEventProcedure.execute(entity);
      }
   }

   public static class Helmet extends CiscosArmorItem {
      public Helmet() {
         super(EquipmentSlot.HEAD, new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD).m_41486_());
      }

      public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
         super.m_7373_(itemstack, world, list, flag);
         list.add(Component.m_237113_("§5Blessed by neptune my helmet allowed me to breath underwater."));
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§6[Full set Bonus] : §fgrants effect Cisco's Might."));
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§a[Key Press Ability] : §fCisco's Clarity. (default  key [x] )"));
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
         return "cisco_mod:textures/models/armor/divine_armor__layer_1.png";
      }

      public void onArmorTick(ItemStack itemstack, Level world, Player entity) {
         DivineArmorHelmetTickEventProcedure.execute(entity);
      }
   }

   public static class Leggings extends CiscosArmorItem {
      public Leggings() {
         super(EquipmentSlot.LEGS, new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD).m_41486_());
      }

      public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
         super.m_7373_(itemstack, world, list, flag);
         list.add(Component.m_237113_("§5Blessed by Apollo my leggings granted me speed."));
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§6[Full set Bonus] : §fgrants effect Cisco's Might."));
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§a[Key Press Ability] : §fCisco's Clarity. (default  key [x] )"));
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
         return "cisco_mod:textures/models/armor/divine_armor__layer_2.png";
      }

      public void onArmorTick(ItemStack itemstack, Level world, Player entity) {
         DivineArmorLeggingsTickEventProcedure.execute(entity);
      }
   }
}
