package net.cisco.item;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import net.cisco.client.model.Modelbjornbottom;
import net.cisco.client.model.Modelbjorntophalf;
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

public abstract class BjornItem extends ArmorItem {
   public BjornItem(EquipmentSlot slot, Properties properties) {
      super(new ArmorMaterial() {
         public int m_7366_(EquipmentSlot slot) {
            return new int[]{13, 15, 16, 11}[slot.m_20749_()] * 350;
         }

         public int m_7365_(EquipmentSlot slot) {
            return new int[]{8, 9, 9, 8}[slot.m_20749_()];
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
            return "bjorn";
         }

         public float m_6651_() {
            return 5.0F;
         }

         public float m_6649_() {
            return 0.25F;
         }
      }, slot, properties);
   }

   public static class Boots extends BjornItem {
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
                           (new Modelbjorntophalf(Minecraft.m_91087_().m_167973_().m_171103_(Modelbjorntophalf.LAYER_LOCATION))).LeftLeg,
                           "right_leg",
                           (new Modelbjorntophalf(Minecraft.m_91087_().m_167973_().m_171103_(Modelbjorntophalf.LAYER_LOCATION))).RightLeg,
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
         list.add(Component.m_237113_("§5Armor worn by the second fell king Bjorn."));
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§6[Full Set Bonus]: Fensalir-Eldi: §fReduces incoming damage by 20 percent and grants a 10 percent boost to armor."));
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§6[Full Set Bonus]: Fellflame: §fBoosts attack by 7% of total armor."));
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
         return "cisco_mod:textures/entities/bjorn1_layer_1.png";
      }
   }

   public static class Chestplate extends BjornItem {
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
                           (new Modelbjorntophalf(Minecraft.m_91087_().m_167973_().m_171103_(Modelbjorntophalf.LAYER_LOCATION))).Body,
                           "left_arm",
                           (new Modelbjorntophalf(Minecraft.m_91087_().m_167973_().m_171103_(Modelbjorntophalf.LAYER_LOCATION))).LeftArm,
                           "right_arm",
                           (new Modelbjorntophalf(Minecraft.m_91087_().m_167973_().m_171103_(Modelbjorntophalf.LAYER_LOCATION))).RightArm,
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
         list.add(Component.m_237113_("§5Armor worn by the second fell king Bjorn."));
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§6[Full Set Bonus]: Fensalir-Eldi: §fReduces incoming damage by 20 percent and grants a 10 percent boost to armor."));
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§6[Full Set Bonus]: Fellflame: §fBoosts attack by 7% of total armor."));
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
         return "cisco_mod:textures/entities/bjorn1_layer_1.png";
      }
   }

   public static class Helmet extends BjornItem {
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
                           (new Modelbjorntophalf(Minecraft.m_91087_().m_167973_().m_171103_(Modelbjorntophalf.LAYER_LOCATION))).Head,
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
         list.add(Component.m_237113_("§5Armor worn by the second fell king Bjorn."));
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§6[Full Set Bonus]: Fensalir-Eldi: §fReduces incoming damage by 20 percent and grants a 10 percent boost to armor."));
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§6[Full Set Bonus]: Fellflame: §fBoosts attack by 7% of total armor."));
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
         return "cisco_mod:textures/entities/bjorn1_layer_1.png";
      }
   }

   public static class Leggings extends BjornItem {
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
                           (new Modelbjornbottom(Minecraft.m_91087_().m_167973_().m_171103_(Modelbjornbottom.LAYER_LOCATION))).LeftLeg,
                           "right_leg",
                           (new Modelbjornbottom(Minecraft.m_91087_().m_167973_().m_171103_(Modelbjornbottom.LAYER_LOCATION))).RightLeg,
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
         list.add(Component.m_237113_("§5Armor worn by the second fell king Bjorn."));
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§6[Full Set Bonus]: Fensalir-Eldi: §fReduces incoming damage by 20 percent and grants a 10 percent boost to armor."));
         list.add(Component.m_237113_("§8-"));
         list.add(Component.m_237113_("§6[Full Set Bonus]: Fellflame: §fBoosts attack by 7% of total armor."));
      }

      public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
         return "cisco_mod:textures/entities/bjorn1_layer_2.png";
      }
   }
}
