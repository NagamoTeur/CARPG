package io.redspace.ironsspellbooks.entity.armor.netherite;

import io.redspace.ironsspellbooks.item.armor.NetheriteMageArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class NetheriteMageArmorModel extends AnimatedGeoModel<NetheriteMageArmorItem> {
   public ResourceLocation getModelResource(NetheriteMageArmorItem object) {
      return new ResourceLocation("irons_spellbooks", "geo/netherite_armor.geo.json");
   }

   public ResourceLocation getTextureResource(NetheriteMageArmorItem object) {
      return new ResourceLocation("irons_spellbooks", "textures/models/armor/netherite.png");
   }

   public ResourceLocation getAnimationResource(NetheriteMageArmorItem animatable) {
      return new ResourceLocation("irons_spellbooks", "animations/wizard_armor_animation.json");
   }
}
