package com.bobmowzie.mowziesmobs.server.item;

import com.bobmowzie.mowziesmobs.client.model.LayerHandler;
import com.bobmowzie.mowziesmobs.client.model.armor.WroughtHelmModel;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class ItemWroughtHelm extends MowzieArmorItem {
   private static final ItemWroughtHelm.WroughtHelmMaterial ARMOR_WROUGHT_HELM = new ItemWroughtHelm.WroughtHelmMaterial();

   public ItemWroughtHelm(Properties properties) {
      super(ARMOR_WROUGHT_HELM, EquipmentSlot.HEAD, properties);
   }

   public boolean m_6832_(ItemStack toRepair, ItemStack repair) {
      return ConfigHandler.COMMON.TOOLS_AND_ABILITIES.WROUGHT_HELM.breakable.get() ? super.m_6832_(toRepair, repair) : false;
   }

   public boolean m_8120_(ItemStack p_77616_1_) {
      return true;
   }

   public boolean m_41465_() {
      return (Boolean)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.WROUGHT_HELM.breakable.get();
   }

   public int getDamage(ItemStack stack) {
      return ConfigHandler.COMMON.TOOLS_AND_ABILITIES.WROUGHT_HELM.breakable.get() ? super.getDamage(stack) : 0;
   }

   public int getMaxDamage(ItemStack stack) {
      return ConfigHandler.COMMON.TOOLS_AND_ABILITIES.WROUGHT_HELM.breakable.get() ? super.getMaxDamage(stack) : 0;
   }

   public void setDamage(ItemStack stack, int damage) {
      if ((Boolean)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.WROUGHT_HELM.breakable.get()) {
         super.setDamage(stack, damage);
      }
   }

   @Nullable
   public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
      return new ResourceLocation("mowziesmobs", "textures/item/wrought_helmet.png").toString();
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      super.m_7373_(stack, worldIn, tooltip, flagIn);
      tooltip.add(Component.m_237115_(this.m_5524_() + ".text.0").m_6270_(ItemHandler.TOOLTIP_STYLE));
   }

   @Override
   public ConfigHandler.ArmorConfig getConfig() {
      return ConfigHandler.COMMON.TOOLS_AND_ABILITIES.WROUGHT_HELM.armorConfig;
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept(ItemWroughtHelm.ArmorRender.INSTANCE);
   }

   private static final class ArmorRender implements IClientItemExtensions {
      private static final ItemWroughtHelm.ArmorRender INSTANCE = new ItemWroughtHelm.ArmorRender();
      private static HumanoidModel<?> MODEL;

      public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
         if (MODEL == null) {
            EntityModelSet models = Minecraft.m_91087_().m_167973_();
            ModelPart root = models.m_171103_(LayerHandler.WROUGHT_HELM_LAYER);
            MODEL = new WroughtHelmModel(root);
         }

         return MODEL;
      }
   }

   private static class WroughtHelmMaterial implements ArmorMaterial {
      public int m_7366_(EquipmentSlot equipmentSlotType) {
         return ArmorMaterials.IRON.m_7366_(equipmentSlotType);
      }

      public int m_7365_(EquipmentSlot equipmentSlotType) {
         return ConfigHandler.COMMON.TOOLS_AND_ABILITIES.WROUGHT_HELM.armorConfig.damageReductionValue;
      }

      public int m_6646_() {
         return ArmorMaterials.IRON.m_6646_();
      }

      public SoundEvent m_7344_() {
         return ArmorMaterials.IRON.m_7344_();
      }

      public Ingredient m_6230_() {
         return ArmorMaterials.IRON.m_6230_();
      }

      public String m_6082_() {
         return "wrought_helm";
      }

      public float m_6651_() {
         return ConfigHandler.COMMON.TOOLS_AND_ABILITIES.WROUGHT_HELM.armorConfig.toughnessValue;
      }

      public float m_6649_() {
         return 0.1F;
      }
   }
}
