package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom;

import com.cerbon.bosses_of_mass_destruction.entity.GeoModel;
import com.cerbon.bosses_of_mass_destruction.entity.util.animation.ICodeAnimations;
import com.cerbon.bosses_of_mass_destruction.projectile.SporeBallProjectile;
import net.minecraft.util.Mth;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

public class SporeCodeAnimations implements ICodeAnimations<SporeBallProjectile> {
   public void animate(SporeBallProjectile animatable, AnimationEvent<?> data, GeoModel<SporeBallProjectile> geoModel) {
      float pitch = animatable.impacted ? animatable.m_146909_() : Mth.m_14189_(data.getPartialTick(), animatable.m_146909_() - 5.0F, animatable.m_146909_());
      software.bernie.geckolib3.geo.render.built.GeoModel model = geoModel.getModel(geoModel.getModelResource(animatable));
      model.getBone("root1").ifPresent(it -> it.setRotationX((float)Math.toRadians((double)pitch)));
   }
}
