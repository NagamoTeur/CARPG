package io.redspace.ironsspellbooks.entity.armor.simple_wizard;

import io.redspace.ironsspellbooks.item.armor.WizardArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WizardArmorModel extends AnimatedGeoModel<WizardArmorItem> {
   public ResourceLocation getModelResource(WizardArmorItem object) {
      return new ResourceLocation("irons_spellbooks", "geo/wizard_armor.geo.json");
   }

   public ResourceLocation getTextureResource(WizardArmorItem object) {
      return new ResourceLocation("irons_spellbooks", "textures/models/armor/wizard_armor.png");
   }

   public ResourceLocation getAnimationResource(WizardArmorItem animatable) {
      return new ResourceLocation("irons_spellbooks", "animations/wizard_armor_animation.json");
   }
}
