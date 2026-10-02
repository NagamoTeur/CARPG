package com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet;

import com.cerbon.bosses_of_mass_destruction.entity.GeoModel;
import com.cerbon.bosses_of_mass_destruction.entity.util.animation.ICodeAnimations;
import net.minecraft.util.Mth;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

public class GauntletCodeAnimations implements ICodeAnimations<GauntletEntity> {
   public void animate(GauntletEntity animatable, AnimationEvent<?> data, GeoModel<GauntletEntity> geoModel) {
      float headPitch = Mth.m_14179_(data.getPartialTick(), animatable.f_19860_, animatable.m_146909_());
      software.bernie.geckolib3.geo.render.built.GeoModel model = geoModel.getModel(geoModel.getModelResource(animatable));
      model.getBone("codeRoot").ifPresent(bone -> bone.setRotationX((float)(-Math.toRadians((double)headPitch))));
   }
}
