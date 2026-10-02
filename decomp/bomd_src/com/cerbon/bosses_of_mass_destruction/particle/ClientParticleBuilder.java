package com.cerbon.bosses_of_mass_destruction.particle;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.RandomUtils;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.phys.Vec3;

public class ClientParticleBuilder {
   private final ParticleOptions options;
   private Function<SimpleParticle, Vec3> getVel;
   private Function<SimpleParticle, Vec3> continuousPos;
   private Function<Float, Vec3> color;
   private Function<Float, Integer> brightness;
   private Function<Float, Float> scale;
   private Supplier<Integer> age;
   private Function<SimpleParticle, Float> getRotation;
   private Double colorVariation;

   public ClientParticleBuilder(ParticleOptions options) {
      this.options = options;
   }

   public ClientParticleBuilder continuousRotation(Function<SimpleParticle, Float> rotation) {
      this.getRotation = rotation;
      return this;
   }

   public ClientParticleBuilder continuousVelocity(Function<SimpleParticle, Vec3> velocity) {
      this.getVel = velocity;
      return this;
   }

   public ClientParticleBuilder continuousPosition(Function<SimpleParticle, Vec3> positionProvider) {
      this.continuousPos = positionProvider;
      return this;
   }

   public ClientParticleBuilder color(Function<Float, Vec3> color) {
      this.color = color;
      return this;
   }

   public ClientParticleBuilder color(Vec3 color) {
      this.color = f -> color;
      return this;
   }

   public ClientParticleBuilder rotation(Float rotation) {
      this.getRotation = p -> rotation;
      return this;
   }

   public ClientParticleBuilder brightness(Integer brightness) {
      this.brightness = f -> brightness;
      return this;
   }

   public ClientParticleBuilder scale(Function<Float, Float> scale) {
      this.scale = scale;
      return this;
   }

   public ClientParticleBuilder scale(Float scale) {
      this.scale = f -> scale;
      return this;
   }

   public ClientParticleBuilder age(Supplier<Integer> age) {
      this.age = age;
      return this;
   }

   public ClientParticleBuilder age(Integer age) {
      this.age = () -> age;
      return this;
   }

   public ClientParticleBuilder age(Integer min, Integer max) {
      this.age = () -> RandomUtils.range(min, max);
      return this;
   }

   public ClientParticleBuilder colorVariation(Double variation) {
      this.colorVariation = variation;
      return this;
   }

   public void build(Vec3 pos, Vec3 vel) {
      Minecraft client = Minecraft.m_91087_();
      Camera camera = client.f_91063_.m_109153_();
      if (camera.m_90593_()) {
         Particle particle = client.f_91061_.m_107370_(this.options, pos.f_82479_, pos.f_82480_, pos.f_82481_, vel.f_82479_, vel.f_82480_, vel.f_82481_);
         if (particle == null) {
            return;
         }

         if (this.scale != null) {
            particle.m_6569_(this.scale.apply(0.0F));
         }

         if (this.color != null) {
            Vec3 clr = this.color.apply(0.0F);
            particle.m_107253_((float)clr.f_82479_, (float)clr.f_82480_, (float)clr.f_82481_);
         }

         if (this.age != null) {
            particle.m_107257_(this.age.get());
         }

         if (particle instanceof SimpleParticle simpleParticle) {
            if (this.brightness != null) {
               simpleParticle.setBrightnessOverride(this.brightness);
            }

            if (this.color != null) {
               simpleParticle.setColorOverride(this.color);
            }

            if (this.scale != null) {
               simpleParticle.setScaleOverride(this.scale);
            }

            if (this.getVel != null) {
               simpleParticle.setVelocityOverride(this.getVel);
            }

            if (this.continuousPos != null) {
               simpleParticle.setPositionOverride(this.continuousPos);
            }

            if (this.colorVariation != null) {
               simpleParticle.setColorVariation(this.colorVariation);
            }

            if (this.getRotation != null) {
               simpleParticle.setRotationOverride(this.getRotation);
            }
         }
      }
   }
}
