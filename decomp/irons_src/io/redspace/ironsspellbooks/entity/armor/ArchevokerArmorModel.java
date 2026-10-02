package io.redspace.ironsspellbooks.entity.armor;

import io.redspace.ironsspellbooks.item.armor.ArchevokerArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ArchevokerArmorModel extends AnimatedGeoModel<ArchevokerArmorItem> {
   public ResourceLocation getModelResource(ArchevokerArmorItem object) {
      return new ResourceLocation("irons_spellbooks", "geo/archevoker_armor.geo.json");
   }

   public ResourceLocation getTextureResource(ArchevokerArmorItem object) {
      return new ResourceLocation("irons_spellbooks", "textures/models/armor/archevoker.png");
   }

   public ResourceLocation getAnimationResource(ArchevokerArmorItem animatable) {
      return new ResourceLocation("irons_spellbooks", "animations/wizard_armor_animation.json");
   }
}
