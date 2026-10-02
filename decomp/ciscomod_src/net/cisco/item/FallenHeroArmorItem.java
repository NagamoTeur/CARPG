package net.cisco.item;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import net.cisco.client.model.Modelfallgelower;
import net.cisco.client.model.Modelfallgenxtophalf;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class FallenHeroArmorItem extends ArmorItem {
   public FallenHeroArmorItem(EquipmentSlot slot, Properties properties) {
      super(new ArmorMaterial() {
         public int m_7366_(EquipmentSlot slot) {
            return new int[]{13, 15, 16, 11}[slot.m_20749_()] * 400;
         }

         public int m_7365_(EquipmentSlot slot) {
            return new int[]{11, 12, 16, 12}[slot.m_20749_()];
         }

         public int m_6646_() {
            return 20;
         }

         public SoundEvent m_7344_() {
            return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_netherite"));
         }

         public Ingredient m_6230_() {
            return Ingredient.m_43927_(new ItemStack[]{new ItemStack(Items.f_42418_)});
         }

         public String m_6082_() {
            return "fallen_hero_armor";
         }

         public float m_6651_() {
            return 6.0F;
         }

         public float m_6649_() {
            return 0.25F;
         }
      }, slot, properties);
   }

   public static class Boots extends FallenHeroArmorItem {
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
                           (new Modelfallgenxtophalf(Minecraft.m_91087_().m_167973_().m_171103_(Modelfallgenxtophalf.LAYER_LOCATION))).LeftLeg,
                           "right_leg",
                           (new Modelfallgenxtophalf(Minecraft.m_91087_().m_167973_().m_171103_(Modelfallgenxtophalf.LAYER_LOCATION))).RightLeg,
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
         list.add(Component.m_237113_("§5A hero consumed by darkness."));
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§6[Full set Bonus] : §fwhen below 80 percent hp grants effect:"));
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§4Unrelenting Dark: §fgain increased attack, armor toughness, speed and armor."));
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§a[Key Press Ability] : §4Onslaught's End : §fcleanes wither and poison effects and heals the user (default  key [x])"));
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
         return "cisco_mod:textures/entities/fallgenx_layer_1.png";
      }
   }

   public static class Chestplate extends FallenHeroArmorItem {
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
                           (new Modelfallgenxtophalf(Minecraft.m_91087_().m_167973_().m_171103_(Modelfallgenxtophalf.LAYER_LOCATION))).Body,
                           "left_arm",
                           (new Modelfallgenxtophalf(Minecraft.m_91087_().m_167973_().m_171103_(Modelfallgenxtophalf.LAYER_LOCATION))).LeftArm,
                           "right_arm",
                           (new Modelfallgenxtophalf(Minecraft.m_91087_().m_167973_().m_171103_(Modelfallgenxtophalf.LAYER_LOCATION))).RightArm,
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
         list.add(Component.m_237113_("§5A hero consumed by darkness."));
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§6[Full set Bonus] : §fwhen below 80 percent hp grants effect:"));
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§4Unrelenting Dark: §fgain increased attack, armor toughness, speed and armor."));
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§a[Key Press Ability] : §4Onslaught's End : §fcleanes wither and poison effects and heals the user (default  key [x])"));
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
         return "cisco_mod:textures/entities/fallgenx_layer_1.png";
      }
   }

   public static class Helmet extends FallenHeroArmorItem {
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
                           (new Modelfallgenxtophalf(Minecraft.m_91087_().m_167973_().m_171103_(Modelfallgenxtophalf.LAYER_LOCATION))).Head,
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
         list.add(Component.m_237113_("§5A hero consumed by darkness."));
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§6[Full set Bonus] : §fwhen below 80 percent hp grants effect:"));
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§4Unrelenting Dark: §fgain increased attack, armor toughness, speed and armor."));
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§a[Key Press Ability] : §4Onslaught's End : §fcleanes wither and poison effects and heals the user (default  key [x])"));
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
         return "cisco_mod:textures/entities/fallgenx_layer_1.png";
      }
   }

   public static class Leggings extends FallenHeroArmorItem {
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
                           (new Modelfallgelower(Minecraft.m_91087_().m_167973_().m_171103_(Modelfallgelower.LAYER_LOCATION))).LeftLeg,
                           "right_leg",
                           (new Modelfallgelower(Minecraft.m_91087_().m_167973_().m_171103_(Modelfallgelower.LAYER_LOCATION))).RightLeg,
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
         list.add(Component.m_237113_("§5A hero consumed by darkness."));
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§6[Full set Bonus] : §fwhen below 80 percent hp grants effect:"));
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§4Unrelenting Dark: §fgain increased attack, armor toughness, speed and armor."));
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§a[Key Press Ability] : §4Onslaught's End : §fcleanes wither and poison effects and heals the user (default  key [x])"));
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
         return "cisco_mod:textures/entities/fallgenlower_layer_2.png";
      }
   }
}
