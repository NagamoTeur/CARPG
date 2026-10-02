package io.redspace.ironsspellbooks.entity.armor;

import io.redspace.ironsspellbooks.item.armor.CryomancerArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CryomancerArmorModel extends AnimatedGeoModel<CryomancerArmorItem> {
   public ResourceLocation getModelResource(CryomancerArmorItem object) {
      return new ResourceLocation("irons_spellbooks", "geo/cryomancer_armor.geo.json");
   }

   public ResourceLocation getTextureResource(CryomancerArmorItem object) {
      return new ResourceLocation("irons_spellbooks", "textures/models/armor/cryomancer.png");
   }

   public ResourceLocation getAnimationResource(CryomancerArmorItem animatable) {
      return new ResourceLocation("irons_spellbooks", "animations/wizard_armor_animation.json");
   }
}
