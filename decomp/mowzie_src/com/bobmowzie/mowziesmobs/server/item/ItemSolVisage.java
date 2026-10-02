package com.bobmowzie.mowziesmobs.server.item;

import com.bobmowzie.mowziesmobs.client.render.item.RenderSolVisageItem;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class ItemSolVisage extends MowzieArmorItem implements UmvuthanaMask, IAnimatable {
   private static final ItemSolVisage.SolVisageMaterial SOL_VISAGE_MATERIAL = new ItemSolVisage.SolVisageMaterial();
   public String controllerName = "controller";
   public AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public ItemSolVisage(Properties properties) {
      super(SOL_VISAGE_MATERIAL, EquipmentSlot.HEAD, properties);
   }

   public boolean m_6832_(ItemStack toRepair, ItemStack repair) {
      return ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SOL_VISAGE.breakable.get() ? super.m_6832_(toRepair, repair) : false;
   }

   public boolean m_8120_(ItemStack p_77616_1_) {
      return true;
   }

   public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
      return true;
   }

   public boolean m_41465_() {
      return (Boolean)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SOL_VISAGE.breakable.get();
   }

   public int getDamage(ItemStack stack) {
      return ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SOL_VISAGE.breakable.get() ? super.getDamage(stack) : 0;
   }

   public int getMaxDamage(ItemStack stack) {
      return ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SOL_VISAGE.breakable.get() ? super.getMaxDamage(stack) : 0;
   }

   public void setDamage(ItemStack stack, int damage) {
      if ((Boolean)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SOL_VISAGE.breakable.get()) {
         super.setDamage(stack, damage);
      }
   }

   @Nullable
   public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
      return new ResourceLocation("mowziesmobs", "textures/entity/umvuthi.png").toString();
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      super.m_7373_(stack, worldIn, tooltip, flagIn);
      tooltip.add(Component.m_237115_(this.m_5524_() + ".text.0").m_6270_(ItemHandler.TOOLTIP_STYLE));
      tooltip.add(Component.m_237115_(this.m_5524_() + ".text.1").m_6270_(ItemHandler.TOOLTIP_STYLE));
      tooltip.add(Component.m_237115_(this.m_5524_() + ".text.2").m_6270_(ItemHandler.TOOLTIP_STYLE));
   }

   @Override
   public ConfigHandler.ArmorConfig getConfig() {
      return ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SOL_VISAGE.armorConfig;
   }

   public <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
      event.getController().setAnimation(new AnimationBuilder().addAnimation("default", EDefaultLoopTypes.LOOP));
      return PlayState.CONTINUE;
   }

   public void registerControllers(AnimationData data) {
      data.addAnimationController(new AnimationController(this, this.controllerName, 0.0F, this::predicate));
   }

   public AnimationFactory getFactory() {
      return this.factory;
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      super.initializeClient(consumer);
      consumer.accept(
         new IClientItemExtensions() {
            private final BlockEntityWithoutLevelRenderer renderer = new RenderSolVisageItem();

            public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
               return armorSlot != EquipmentSlot.HEAD
                  ? null
                  : GeoArmorRenderer.getRenderer(ItemSolVisage.this.getClass(), entityLiving)
                     .applyEntityStats(_default)
                     .setCurrentItem(entityLiving, itemStack, armorSlot)
                     .applySlot(armorSlot);
            }

            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
               return this.renderer;
            }
         }
      );
   }

   private static class SolVisageMaterial implements ArmorMaterial {
      public int m_7366_(EquipmentSlot equipmentSlotType) {
         return ArmorMaterials.GOLD.m_7366_(equipmentSlotType);
      }

      public int m_7365_(EquipmentSlot equipmentSlotType) {
         return ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SOL_VISAGE.armorConfig.damageReductionValue;
      }

      public int m_6646_() {
         return ArmorMaterials.GOLD.m_6646_();
      }

      public SoundEvent m_7344_() {
         return ArmorMaterials.GOLD.m_7344_();
      }

      public Ingredient m_6230_() {
         return ArmorMaterials.GOLD.m_6230_();
      }

      public String m_6082_() {
         return "sol_visage";
      }

      public float m_6651_() {
         return ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SOL_VISAGE.armorConfig.toughnessValue;
      }

      public float m_6649_() {
         return ArmorMaterials.GOLD.m_6649_();
      }
   }
}
