package com.cerbon.bosses_of_mass_destruction.client.render;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.RandomUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.particle.ClientParticleBuilder;
import com.cerbon.bosses_of_mass_destruction.util.BMDColors;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Function;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class PetalBladeParticleRenderer<T extends Entity> implements IRenderer<T> {
   private final ClientParticleBuilder petalParticleFactory = new ClientParticleBuilder((ParticleOptions)BMDParticles.PETAL.get())
      .color(
         (Function<Float, Vec3>)(f -> (double)f.floatValue() < 0.7
               ? MathUtils.lerpVec(f, BMDColors.PINK, new Vec3(1.0, 0.85, 0.95))
               : MathUtils.lerpVec(f, new Vec3(1.0, 0.85, 0.95), BMDColors.ULTRA_DARK_PURPLE))
      )
      .brightness(15728880)
      .colorVariation(0.15)
      .scale(f -> (float)RandomUtils.range(0.1, 0.2) * (1.0F - f * 0.25F));

   @Override
   public void render(T entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light) {
      Vec3 prevPos = new Vec3(entity.f_19854_, entity.f_19855_, entity.f_19856_);
      Vec3 pos = MathUtils.lerpVec(partialTicks, prevPos, entity.m_20182_());
      Vec3 dir = entity.m_20182_().m_82546_(prevPos).m_82541_();
      int randomRot = RandomUtils.range(0, 360);
      float angularMomentum = (float)RandomUtils.randSign() * 4.0F;
      this.petalParticleFactory
         .continuousRotation(particle -> (float)randomRot + (float)particle.getAge() * angularMomentum)
         .build(
            pos.m_82549_(RandomUtils.randVec().m_82490_(0.25)),
            VecUtils.planeProject(RandomUtils.randVec(), VecUtils.yAxis).m_82546_(VecUtils.yAxis).m_82541_().m_82490_(0.1).m_82549_(dir.m_82490_(0.05))
         );
   }
}
