package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom;

import com.cerbon.bosses_of_mass_destruction.entity.GeoModel;
import com.cerbon.bosses_of_mass_destruction.entity.util.animation.ICodeAnimations;
import net.minecraft.util.Mth;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

public class VoidBlossomCodeAnimations implements ICodeAnimations<VoidBlossomEntity> {
   public void animate(VoidBlossomEntity animatable, AnimationEvent<?> data, GeoModel<VoidBlossomEntity> geoModel) {
      float bodyYaw = Mth.m_14189_(data.getPartialTick(), animatable.f_20884_, animatable.f_20883_);
      software.bernie.geckolib3.geo.render.built.GeoModel model = geoModel.getModel(geoModel.getModelResource(animatable));
      model.getBone("Leaves").ifPresent(geoBone -> geoBone.setRotationY((float)Math.toRadians((double)bodyYaw)));
      model.getBone("Thorns").ifPresent(geoBone -> geoBone.setRotationY((float)Math.toRadians((double)bodyYaw)));
      model.getBone("Roots").ifPresent(geoBone -> geoBone.setRotationY((float)Math.toRadians((double)bodyYaw)));
   }
}
