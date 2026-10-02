package io.redspace.ironsspellbooks.entity.armor.netherite;

import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.item.GeoArmorItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class NetheriteMageArmorRenderer<T extends GeoArmorItem & IAnimatable> extends GeoArmorRenderer<T> {
   public NetheriteMageArmorRenderer(AnimatedGeoModel<T> modelProvider) {
      super(modelProvider);
   }
}
