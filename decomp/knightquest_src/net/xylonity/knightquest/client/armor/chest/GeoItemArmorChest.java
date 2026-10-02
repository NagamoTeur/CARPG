package net.xylonity.knightquest.client.armor.chest;

import java.util.List;
import java.util.Objects;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.xylonity.knightquest.KnightQuest;
import net.xylonity.knightquest.common.item.KQArmorItem;
import net.xylonity.knightquest.common.material.KQArmorMaterials;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.item.GeoArmorItem;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class GeoItemArmorChest extends GeoArmorItem implements IAnimatable {
   private AnimationFactory factory = GeckoLibUtil.createFactory(this);
   private final String modelResource;
   private final String textureResource;
   private final String bonusTooltip;

   public GeoItemArmorChest(KQArmorMaterials material, EquipmentSlot type, Properties properties, String textureResource, String modelResource) {
      super(material, type, properties.m_41491_(KnightQuest.CREATIVE_MODE_TAB));
      this.bonusTooltip = material.getKeyName();
      this.textureResource = textureResource;
      this.modelResource = modelResource;
   }

   public String getModelResource() {
      return this.modelResource;
   }

   public String getTextureResource() {
      return this.textureResource;
   }

   private boolean isArmorSetConfigEnabled(String bonusTooltip) {
      try {
         KQArmorItem.ArmorSet armorSet = KQArmorItem.ArmorSet.valueOf(bonusTooltip.toUpperCase());
         return armorSet.isEnabled();
      } catch (Exception var3) {
         return false;
      }
   }

   public void m_7373_(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
      if (this.isArmorSetConfigEnabled(this.bonusTooltip)) {
         if (!Objects.equals(this.bonusTooltip, "chainmail") && !Objects.equals(this.bonusTooltip, "tengu")) {
            pTooltipComponents.add(Component.m_237115_("tooltip.item.knightquest.full_set_bonus"));
            pTooltipComponents.add(Component.m_237115_("tooltip.item.knightquest." + this.bonusTooltip + "_helmet.bonus"));
         } else if (Objects.equals(this.bonusTooltip, "tengu")) {
            pTooltipComponents.add(Component.m_237115_("tooltip.item.knightquest.full_helmet_bonus"));
            pTooltipComponents.add(Component.m_237115_("tooltip.item.knightquest." + this.bonusTooltip + "_helmet.bonus"));
         }
      }

      super.m_7373_(pStack, pLevel, pTooltipComponents, pIsAdvanced);
   }

   private PlayState predicate(AnimationEvent<?> animationState) {
      animationState.getController().setAnimation(new AnimationBuilder().addAnimation("idle", EDefaultLoopTypes.LOOP));
      return PlayState.CONTINUE;
   }

   public void registerControllers(AnimationData data) {
      data.addAnimationController(new AnimationController(this, "controller", 0.0F, this::predicate));
   }

   public AnimationFactory getFactory() {
      return this.factory;
   }
}
