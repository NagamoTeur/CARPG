package com.cerbon.bosses_of_mass_destruction.entity.custom.lich;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import com.cerbon.bosses_of_mass_destruction.entity.GeoModel;
import com.cerbon.bosses_of_mass_destruction.entity.util.animation.ICodeAnimations;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

public class LichCodeAnimations implements ICodeAnimations<LichEntity> {
   public void animate(LichEntity animatable, AnimationEvent<?> data, GeoModel<LichEntity> geoModel) {
      float bodyYaw = Mth.m_14189_(data.getPartialTick(), animatable.f_20884_, animatable.f_20883_);
      float headYaw = Mth.m_14189_(data.getPartialTick(), animatable.f_20886_, animatable.f_20885_);
      float headPitch = Mth.m_14179_(data.getPartialTick(), animatable.f_19860_, animatable.m_146909_());
      Vec3 velocity = MathUtils.lerpVec(data.getPartialTick(), animatable.velocityHistory.get(1), animatable.velocityHistory.get(0));
      int neutralPoseDegree = 30;
      int maxDegreeVariation = 15;
      double bodyPitch = (double)MathUtils.directionToPitch(velocity) * ((double)maxDegreeVariation / 90.0) + (double)neutralPoseDegree;
      float yaw = headYaw - bodyYaw;
      double adjustedHeadPitch = (double)headPitch - bodyPitch;
      software.bernie.geckolib3.geo.render.built.GeoModel model = geoModel.getModel(geoModel.getModelResource(animatable));
      model.getBone("code_root").ifPresent(bone -> bone.setRotationX((float)(-Math.toRadians(bodyPitch))));
      model.getBone("headBase").ifPresent(bone -> bone.setRotationX((float)(-Math.toRadians(adjustedHeadPitch))));
      model.getBone("headBase").ifPresent(bone -> bone.setRotationX((float)Math.toRadians((double)yaw)));
   }
}
