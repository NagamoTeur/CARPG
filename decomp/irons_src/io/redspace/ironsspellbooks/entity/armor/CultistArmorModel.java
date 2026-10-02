package io.redspace.ironsspellbooks.entity.armor;

import io.redspace.ironsspellbooks.item.armor.CultistArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CultistArmorModel extends AnimatedGeoModel<CultistArmorItem> {
   public ResourceLocation getModelResource(CultistArmorItem object) {
      return new ResourceLocation("irons_spellbooks", "geo/cultist_armor.geo.json");
   }

   public ResourceLocation getTextureResource(CultistArmorItem object) {
      return new ResourceLocation("irons_spellbooks", "textures/models/armor/cultist.png");
   }

   public ResourceLocation getAnimationResource(CultistArmorItem animatable) {
      return new ResourceLocation("irons_spellbooks", "animations/wizard_armor_animation.json");
   }
}
