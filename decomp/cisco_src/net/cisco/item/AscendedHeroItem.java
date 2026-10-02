package net.cisco.item;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import net.cisco.client.model.Modelascendedbottom;
import net.cisco.client.model.Modelascendedtop;
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
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class AscendedHeroItem extends ArmorItem {
   public AscendedHeroItem(EquipmentSlot slot, Properties properties) {
      super(new ArmorMaterial() {
         public int m_7366_(EquipmentSlot slot) {
            return new int[]{13, 15, 16, 11}[slot.m_20749_()] * 1000;
         }

         public int m_7365_(EquipmentSlot slot) {
            return new int[]{20, 25, 27, 22}[slot.m_20749_()];
         }

         public int m_6646_() {
            return 20;
         }

         public SoundEvent m_7344_() {
            return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_netherite"));
         }

         public Ingredient m_6230_() {
            return Ingredient.m_151265_();
         }

         public String m_6082_() {
            return "ascended_hero";
         }

         public float m_6651_() {
            return 7.0F;
         }

         public float m_6649_() {
            return 0.25F;
         }
      }, slot, properties);
   }

   public static class Boots extends AscendedHeroItem {
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
                           (new Modelascendedtop(Minecraft.m_91087_().m_167973_().m_171103_(Modelascendedtop.LAYER_LOCATION))).LeftLeg,
                           "right_leg",
                           (new Modelascendedtop(Minecraft.m_91087_().m_167973_().m_171103_(Modelascendedtop.LAYER_LOCATION))).RightLeg,
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
         list.add(
            Component.m_237113_(
               "§6[Full Set Bonus]: Advent of Ascension: §fWhen above 50 percent hp grants an 80% boost to main offensive and defensive stats."
            )
         );
         list.add(Component.m_237113_("§8-"));
         list.add(
            Component.m_237113_(
               "§6[Full Set Bonus]: Lights Blessing: §fSaves the user from fatal damage once every 10 mins and reduces incoming damage by half."
            )
         );
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§a[On Key Press]: §9Azure Ascendant: §fHeals nearby players and removes certain negative effects. (default x)"));
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
         return "cisco_mod:textures/entities/ascendedhero_layer_1.png";
      }
   }

   public static class Chestplate extends AscendedHeroItem {
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
                           (new Modelascendedtop(Minecraft.m_91087_().m_167973_().m_171103_(Modelascendedtop.LAYER_LOCATION))).Body,
                           "left_arm",
                           (new Modelascendedtop(Minecraft.m_91087_().m_167973_().m_171103_(Modelascendedtop.LAYER_LOCATION))).LeftArm,
                           "right_arm",
                           (new Modelascendedtop(Minecraft.m_91087_().m_167973_().m_171103_(Modelascendedtop.LAYER_LOCATION))).RightArm,
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
         list.add(
            Component.m_237113_(
               "§6[Full Set Bonus]: Advent of Ascension: §fWhen above 50 percent hp grants an 80% boost to main offensive and defensive stats."
            )
         );
         list.add(Component.m_237113_("§8-"));
         list.add(
            Component.m_237113_(
               "§6[Full Set Bonus]: Lights Blessing: §fSaves the user from fatal damage once every 10 mins and reduces incoming damage by half."
            )
         );
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§a[On Key Press]: §9Azure Ascendant: §fHeals nearby players and removes certain negative effects. (default x)"));
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
         return "cisco_mod:textures/entities/ascendedhero_layer_1.png";
      }
   }

   public static class Helmet extends AscendedHeroItem {
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
                           (new Modelascendedtop(Minecraft.m_91087_().m_167973_().m_171103_(Modelascendedtop.LAYER_LOCATION))).Head,
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
         list.add(
            Component.m_237113_(
               "§6[Full Set Bonus]: Advent of Ascension: §fWhen above 50 percent hp grants an 80% boost to main offensive and defensive stats."
            )
         );
         list.add(Component.m_237113_("§8-"));
         list.add(
            Component.m_237113_(
               "§6[Full Set Bonus]: Lights Blessing: §fSaves the user from fatal damage once every 10 mins and reduces incoming damage by half."
            )
         );
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§a[On Key Press]: §9Azure Ascendant: §fHeals nearby players and removes certain negative effects. (default x)"));
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
         return "cisco_mod:textures/entities/ascendedhero_layer_1.png";
      }
   }

   public static class Leggings extends AscendedHeroItem {
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
                           (new Modelascendedbottom(Minecraft.m_91087_().m_167973_().m_171103_(Modelascendedbottom.LAYER_LOCATION))).LeftLeg,
                           "right_leg",
                           (new Modelascendedbottom(Minecraft.m_91087_().m_167973_().m_171103_(Modelascendedbottom.LAYER_LOCATION))).RightLeg,
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
         list.add(
            Component.m_237113_(
               "§6[Full Set Bonus]: Advent of Ascension: §fWhen above 50 percent hp grants an 80% boost to main offensive and defensive stats."
            )
         );
         list.add(Component.m_237113_("§8-"));
         list.add(
            Component.m_237113_(
               "§6[Full Set Bonus]: Lights Blessing: §fSaves the user from fatal damage once every 10 mins and reduces incoming damage by half."
            )
         );
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§a[On Key Press]: §9Azure Ascendant: §fHeals nearby players and removes certain negative effects. (default x)"));
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
         return "cisco_mod:textures/entities/ascendedhero_layer_2.png";
      }
   }
}
