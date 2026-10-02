package net.cisco.item;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import net.cisco.client.model.Modeldarklower;
import net.cisco.client.model.Modeldarksteeltop;
import net.cisco.init.CiscoModModItems;
import net.cisco.init.CiscoModModTabs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class DarksteelArmorItem extends ArmorItem {
   public DarksteelArmorItem(EquipmentSlot slot, Properties properties) {
      super(new ArmorMaterial() {
         public int m_7366_(EquipmentSlot slot) {
            return new int[]{13, 15, 16, 11}[slot.m_20749_()] * 250;
         }

         public int m_7365_(EquipmentSlot slot) {
            return new int[]{8, 10, 12, 9}[slot.m_20749_()];
         }

         public int m_6646_() {
            return 18;
         }

         public SoundEvent m_7344_() {
            return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_netherite"));
         }

         public Ingredient m_6230_() {
            return Ingredient.m_43927_(new ItemStack[]{new ItemStack((ItemLike)CiscoModModItems.DARKSTEEL.get())});
         }

         public String m_6082_() {
            return "darksteel_armor";
         }

         public float m_6651_() {
            return 5.0F;
         }

         public float m_6649_() {
            return 0.15F;
         }
      }, slot, properties);
   }

   public static class Boots extends DarksteelArmorItem {
      public Boots() {
         super(EquipmentSlot.FEET, new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD).m_41486_());
      }

      public void initializeClient(Consumer<IClientItemExtensions> consumer) {
         consumer.accept(
            new IClientItemExtensions() {
               @OnlyIn(Dist.CLIENT)
               public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
                  HumanoidModel armorModel = new HumanoidModel(
                     new ModelPart(
                        Collections.emptyList(),
                        Map.of(
                           "left_leg",
                           (new Modeldarksteeltop(Minecraft.m_91087_().m_167973_().m_171103_(Modeldarksteeltop.LAYER_LOCATION))).LeftLeg,
                           "right_leg",
                           (new Modeldarksteeltop(Minecraft.m_91087_().m_167973_().m_171103_(Modeldarksteeltop.LAYER_LOCATION))).RightLeg,
                           "head",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "hat",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "body",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "right_arm",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "left_arm",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap())
                        )
                     )
                  );
                  armorModel.f_102817_ = living.m_6144_();
                  armorModel.f_102609_ = defaultModel.f_102609_;
                  armorModel.f_102610_ = living.m_6162_();
                  return armorModel;
               }
            }
         );
      }

      public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
         super.m_7373_(itemstack, world, list, flag);
         list.add(Component.m_237113_("§dArmor plated with darksteel for increased protection."));
         list.add(Component.m_237113_("§8-"));
         list.add(
            Component.m_237113_(
               "§6[Full Set Bonus]: Aegis Impervium : §fOnce every 5 mins if damage taken is greater than max hp survive with 10 hp and gain 3 second temporary invunerability."
            )
         );
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§6[Full Set Bonus]: Resilience : §fGain a small resistance buff."));
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
         return "cisco_mod:textures/entities/darksteeltop_layer_1.png";
      }
   }

   public static class Chestplate extends DarksteelArmorItem {
      public Chestplate() {
         super(EquipmentSlot.CHEST, new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD).m_41486_());
      }

      public void initializeClient(Consumer<IClientItemExtensions> consumer) {
         consumer.accept(
            new IClientItemExtensions() {
               @OnlyIn(Dist.CLIENT)
               public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
                  HumanoidModel armorModel = new HumanoidModel(
                     new ModelPart(
                        Collections.emptyList(),
                        Map.of(
                           "body",
                           (new Modeldarksteeltop(Minecraft.m_91087_().m_167973_().m_171103_(Modeldarksteeltop.LAYER_LOCATION))).Body,
                           "left_arm",
                           (new Modeldarksteeltop(Minecraft.m_91087_().m_167973_().m_171103_(Modeldarksteeltop.LAYER_LOCATION))).LeftArm,
                           "right_arm",
                           (new Modeldarksteeltop(Minecraft.m_91087_().m_167973_().m_171103_(Modeldarksteeltop.LAYER_LOCATION))).RightArm,
                           "head",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "hat",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "right_leg",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "left_leg",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap())
                        )
                     )
                  );
                  armorModel.f_102817_ = living.m_6144_();
                  armorModel.f_102609_ = defaultModel.f_102609_;
                  armorModel.f_102610_ = living.m_6162_();
                  return armorModel;
               }
            }
         );
      }

      public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
         super.m_7373_(itemstack, world, list, flag);
         list.add(Component.m_237113_("§dArmor plated with darksteel for increased protection."));
         list.add(Component.m_237113_("§8-"));
         list.add(
            Component.m_237113_(
               "§6[Full Set Bonus]: Aegis Impervium : §fOnce every 5 mins if damage taken is greater than max hp survive with 10 hp and gain 3 second temporary invunerability."
            )
         );
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§6[Full Set Bonus]: Resilience : §fGain a small resistance buff."));
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
         return "cisco_mod:textures/entities/darksteeltop_layer_1.png";
      }
   }

   public static class Helmet extends DarksteelArmorItem {
      public Helmet() {
         super(EquipmentSlot.HEAD, new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD).m_41486_());
      }

      public void initializeClient(Consumer<IClientItemExtensions> consumer) {
         consumer.accept(
            new IClientItemExtensions() {
               public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
                  HumanoidModel armorModel = new HumanoidModel(
                     new ModelPart(
                        Collections.emptyList(),
                        Map.of(
                           "head",
                           (new Modeldarksteeltop(Minecraft.m_91087_().m_167973_().m_171103_(Modeldarksteeltop.LAYER_LOCATION))).Head,
                           "hat",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "body",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "right_arm",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "left_arm",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "right_leg",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "left_leg",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap())
                        )
                     )
                  );
                  armorModel.f_102817_ = living.m_6144_();
                  armorModel.f_102609_ = defaultModel.f_102609_;
                  armorModel.f_102610_ = living.m_6162_();
                  return armorModel;
               }
            }
         );
      }

      public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
         super.m_7373_(itemstack, world, list, flag);
         list.add(Component.m_237113_("§dArmor plated with darksteel for increased protection."));
         list.add(Component.m_237113_("§8-"));
         list.add(
            Component.m_237113_(
               "§6[Full Set Bonus]: Aegis Impervium : §fOnce every 5 mins if damage taken is greater than max hp survive with 10 hp and gain 3 second temporary invunerability."
            )
         );
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§6[Full Set Bonus]: Resilience : §fGain a small resistance buff."));
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
         return "cisco_mod:textures/entities/darksteeltop_layer_1.png";
      }
   }

   public static class Leggings extends DarksteelArmorItem {
      public Leggings() {
         super(EquipmentSlot.LEGS, new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD).m_41486_());
      }

      public void initializeClient(Consumer<IClientItemExtensions> consumer) {
         consumer.accept(
            new IClientItemExtensions() {
               @OnlyIn(Dist.CLIENT)
               public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
                  HumanoidModel armorModel = new HumanoidModel(
                     new ModelPart(
                        Collections.emptyList(),
                        Map.of(
                           "left_leg",
                           (new Modeldarklower(Minecraft.m_91087_().m_167973_().m_171103_(Modeldarklower.LAYER_LOCATION))).LeftLeg,
                           "right_leg",
                           (new Modeldarklower(Minecraft.m_91087_().m_167973_().m_171103_(Modeldarklower.LAYER_LOCATION))).RightLeg,
                           "head",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "hat",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "body",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "right_arm",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                           "left_arm",
                           new ModelPart(Collections.emptyList(), Collections.emptyMap())
                        )
                     )
                  );
                  armorModel.f_102817_ = living.m_6144_();
                  armorModel.f_102609_ = defaultModel.f_102609_;
                  armorModel.f_102610_ = living.m_6162_();
                  return armorModel;
               }
            }
         );
      }

      public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
         super.m_7373_(itemstack, world, list, flag);
         list.add(Component.m_237113_("§dArmor plated with darksteel for increased protection."));
         list.add(Component.m_237113_("§8-"));
         list.add(
            Component.m_237113_(
               "§6[Full Set Bonus]: Aegis Impervium : §fOnce every 5 mins if damage taken is greater than max hp survive with 10 hp and gain 3 second temporary invunerability."
            )
         );
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§6[Full Set Bonus]: Resilience : §fGain a small resistance buff."));
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
         return "cisco_mod:textures/entities/darksteelarmor_layer_2.png";
      }
   }
}
