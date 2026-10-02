package com.cerbon.bosses_of_mass_destruction.projectile.comet;

import com.cerbon.bosses_of_mass_destruction.entity.GeoModel;
import com.cerbon.bosses_of_mass_destruction.entity.util.animation.ICodeAnimations;
import net.minecraft.util.Mth;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

public class CometCodeAnimations implements ICodeAnimations<CometProjectile> {
   public void animate(CometProjectile animatable, AnimationEvent<?> data, GeoModel<CometProjectile> geoModel) {
      float pitch = Mth.m_14189_(data.getPartialTick(), animatable.m_146909_() - 5.0F, animatable.m_146909_());
      software.bernie.geckolib3.geo.render.built.GeoModel model = geoModel.getModel(geoModel.getModelResource(animatable));
      model.getBone("root1").ifPresent(geoBone -> geoBone.setRotationX((float)Math.toRadians((double)pitch)));
   }
}
