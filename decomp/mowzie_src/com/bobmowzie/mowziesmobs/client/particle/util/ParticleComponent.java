package com.bobmowzie.mowziesmobs.client.particle.util;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;

public abstract class ParticleComponent {
   public void init(AdvancedParticleBase particle) {
   }

   public void preUpdate(AdvancedParticleBase particle) {
   }

   public void postUpdate(AdvancedParticleBase particle) {
   }

   public void preRender(AdvancedParticleBase particle, float partialTicks) {
   }

   public void postRender(AdvancedParticleBase particle, VertexConsumer buffer, Camera renderInfo, float partialTicks, int lightmap) {
   }

   public static ParticleComponent.Constant constant(float value) {
      return new ParticleComponent.Constant(value);
   }

   public abstract static class AnimData {
      public float evaluate(float t) {
         return 0.0F;
      }
   }

   public static class Attractor extends ParticleComponent {
      private final Vec3[] location;
      private final float strength;
      private final float killDist;
      private final ParticleComponent.Attractor.EnumAttractorBehavior behavior;
      private Vec3 startLocation;

      public Attractor(Vec3[] location, float strength, float killDist, ParticleComponent.Attractor.EnumAttractorBehavior behavior) {
         this.location = location;
         this.strength = strength;
         this.killDist = killDist;
         this.behavior = behavior;
      }

      @Override
      public void init(AdvancedParticleBase particle) {
         this.startLocation = new Vec3(particle.getPosX(), particle.getPosY(), particle.getPosZ());
      }

      @Override
      public void preUpdate(AdvancedParticleBase particle) {
         float ageFrac = particle.getAge() / (float)(particle.m_107273_() - 1);
         if (this.location.length > 0) {
            Vec3 destinationVec = this.location[0];
            Vec3 currPos = new Vec3(particle.getPosX(), particle.getPosY(), particle.getPosZ());
            Vec3 diff = destinationVec.m_82546_(currPos);
            if (diff.m_82553_() < (double)this.killDist) {
               particle.m_107274_();
            }

            if (this.behavior == ParticleComponent.Attractor.EnumAttractorBehavior.EXPONENTIAL) {
               Vec3 path = destinationVec.m_82546_(this.startLocation)
                  .m_82490_(Math.pow((double)ageFrac, (double)this.strength))
                  .m_82549_(this.startLocation)
                  .m_82546_(currPos);
               particle.m_6257_(path.f_82479_, path.f_82480_, path.f_82481_);
            } else if (this.behavior == ParticleComponent.Attractor.EnumAttractorBehavior.LINEAR) {
               Vec3 path = destinationVec.m_82546_(this.startLocation).m_82490_((double)ageFrac).m_82549_(this.startLocation).m_82546_(currPos);
               particle.m_6257_(path.f_82479_, path.f_82480_, path.f_82481_);
            } else {
               double dist = Math.max(diff.m_82553_(), 0.001);
               diff = diff.m_82541_().m_82490_((double)this.strength / (dist * dist));
               particle.setMotionX(Math.min(particle.getMotionX() + diff.f_82479_, 5.0));
               particle.setMotionY(Math.min(particle.getMotionY() + diff.f_82480_, 5.0));
               particle.setMotionZ(Math.min(particle.getMotionZ() + diff.f_82481_, 5.0));
            }
         }
      }

      public static enum EnumAttractorBehavior {
         LINEAR,
         EXPONENTIAL,
         SIMULATED;
      }
   }

   public static class Constant extends ParticleComponent.AnimData {
      float value;

      public Constant(float value) {
         this.value = value;
      }

      @Override
      public float evaluate(float t) {
         return this.value;
      }
   }

   public static class FaceMotion extends ParticleComponent {
      @Override
      public void preRender(AdvancedParticleBase particle, float partialTicks) {
         super.preRender(particle, partialTicks);
         double dx = particle.getPosX() - particle.getPrevPosX();
         double dy = particle.getPosY() - particle.getPrevPosY();
         double dz = particle.getPosZ() - particle.getPrevPosZ();
         double d = Math.sqrt(dx * dx + dy * dy + dz * dz);
         if (d != 0.0) {
            if (particle.rotation instanceof ParticleRotation.EulerAngles eulerRot) {
               double a = dy / d;
               a = Math.max(-1.0, Math.min(1.0, a));
               float pitch = -((float)Math.asin(a));
               float yaw = -((float)(Math.atan2(dz, dx) + Math.PI));
               eulerRot.roll = pitch;
               eulerRot.yaw = yaw;
            } else if (particle.rotation instanceof ParticleRotation.OrientVector orientRot) {
               orientRot.orientation = new Vec3(dx, dy, dz).m_82541_();
            }
         }
      }
   }

   public static class KeyTrack extends ParticleComponent.AnimData {
      float[] values;
      float[] times;

      public KeyTrack(float[] values, float[] times) {
         this.values = values;
         this.times = times;
         if (values.length != times.length) {
            System.out.println("Malformed key track. Must have same number of keys and values or key track will evaluate to 0.");
         }
      }

      @Override
      public float evaluate(float t) {
         if (this.values.length != this.times.length) {
            return 0.0F;
         } else {
            for (int i = 0; i < this.times.length; i++) {
               float time = this.times[i];
               if (t == time) {
                  return this.values[i];
               }

               if (t < time) {
                  if (i == 0) {
                     return this.values[0];
                  }

                  float a = (t - this.times[i - 1]) / (time - this.times[i - 1]);
                  return this.values[i - 1] * (1.0F - a) + this.values[i] * a;
               }

               if (i == this.values.length - 1) {
                  return this.values[i];
               }
            }

            return 0.0F;
         }
      }

      public static ParticleComponent.KeyTrack startAndEnd(float startValue, float endValue) {
         return new ParticleComponent.KeyTrack(new float[]{startValue, endValue}, new float[]{0.0F, 1.0F});
      }

      public static ParticleComponent.KeyTrack oscillate(float value1, float value2, int frequency) {
         if (frequency <= 1) {
            new ParticleComponent.KeyTrack(new float[]{value1, value2}, new float[]{0.0F, 1.0F});
         }

         float step = 1.0F / (float)frequency;
         float[] times = new float[frequency + 1];
         float[] values = new float[frequency + 1];

         for (int i = 0; i < frequency + 1; i++) {
            float value = i % 2 == 0 ? value1 : value2;
            times[i] = step * (float)i;
            values[i] = value;
         }

         return new ParticleComponent.KeyTrack(values, times);
      }
   }

   public static class Orbit extends ParticleComponent {
      private final Vec3[] location;
      private final ParticleComponent.AnimData phase;
      private final ParticleComponent.AnimData radius;
      private final ParticleComponent.AnimData axisX;
      private final ParticleComponent.AnimData axisY;
      private final ParticleComponent.AnimData axisZ;
      private final boolean faceCamera;

      public Orbit(
         Vec3[] location,
         ParticleComponent.AnimData phase,
         ParticleComponent.AnimData radius,
         ParticleComponent.AnimData axisX,
         ParticleComponent.AnimData axisY,
         ParticleComponent.AnimData axisZ,
         boolean faceCamera
      ) {
         this.location = location;
         this.phase = phase;
         this.radius = radius;
         this.axisX = axisX;
         this.axisY = axisY;
         this.axisZ = axisZ;
         this.faceCamera = faceCamera;
      }

      @Override
      public void init(AdvancedParticleBase particle) {
         this.apply(particle, 0.0F);
      }

      @Override
      public void preUpdate(AdvancedParticleBase particle) {
         float ageFrac = particle.getAge() / (float)particle.m_107273_();
         this.apply(particle, ageFrac);
      }

      private void apply(AdvancedParticleBase particle, float t) {
         float p = this.phase.evaluate(t);
         float r = this.radius.evaluate(t);
         Vector3f axis;
         if (this.faceCamera && Minecraft.m_91087_().f_91074_ != null) {
            axis = new Vector3f(Minecraft.m_91087_().f_91074_.m_20154_());
            axis.m_122278_();
         } else {
            axis = new Vector3f(this.axisX.evaluate(t), this.axisY.evaluate(t), this.axisZ.evaluate(t));
            axis.m_122278_();
         }

         Quaternion quat = new Quaternion(axis, p * (float) Math.PI * 2.0F, false);
         Vector3f up = new Vector3f(0.0F, 1.0F, 0.0F);
         Vector3f start = axis;
         if ((double)Math.abs(axis.m_122276_(up)) > 0.99) {
            start = new Vector3f(1.0F, 0.0F, 0.0F);
         }

         start.m_122279_(up);
         start.m_122278_();
         start.m_122251_(quat);
         start.m_122261_(r);
         if (this.location.length > 0 && this.location[0] != null) {
            start.m_122272_((float)this.location[0].f_82479_, (float)this.location[0].f_82480_, (float)this.location[0].f_82481_);
         }

         particle.m_107264_((double)start.m_122239_(), (double)start.m_122260_(), (double)start.m_122269_());
      }
   }

   public static class Oscillator extends ParticleComponent.AnimData {
      float value1;
      float value2;
      float frequency;
      float phaseShift;

      public Oscillator(float value1, float value2, float frequency, float phaseShift) {
         this.value1 = value1;
         this.value2 = value2;
         this.frequency = frequency;
         this.phaseShift = phaseShift;
      }

      @Override
      public float evaluate(float t) {
         float a = (this.value2 - this.value1) / 2.0F;
         return (float)((double)(this.value1 + a) + (double)a * Math.cos((double)(t * this.frequency + this.phaseShift)));
      }
   }

   public static class PinLocation extends ParticleComponent {
      private final Vec3[] location;

      public PinLocation(Vec3[] location) {
         this.location = location;
      }

      @Override
      public void init(AdvancedParticleBase particle) {
         if (this.location != null && this.location.length > 0 && this.location[0] != null) {
            particle.m_107264_(this.location[0].f_82479_, this.location[0].f_82480_, this.location[0].f_82481_);
         }
      }

      @Override
      public void preUpdate(AdvancedParticleBase particle) {
         if (this.location != null && this.location.length > 0 && this.location[0] != null) {
            particle.m_107264_(this.location[0].f_82479_, this.location[0].f_82480_, this.location[0].f_82481_);
         }
      }

      @Override
      public void preRender(AdvancedParticleBase particle, float partialTicks) {
         super.preRender(particle, partialTicks);
         particle.doRender = this.location != null && this.location.length > 0 && this.location[0] != null;
      }
   }

   public static class PropertyControl extends ParticleComponent {
      private final ParticleComponent.AnimData animData;
      private final ParticleComponent.PropertyControl.EnumParticleProperty property;
      private final boolean additive;

      public PropertyControl(ParticleComponent.PropertyControl.EnumParticleProperty property, ParticleComponent.AnimData animData, boolean additive) {
         this.property = property;
         this.animData = animData;
         this.additive = additive;
      }

      @Override
      public void init(AdvancedParticleBase particle) {
         float value = this.animData.evaluate(0.0F);
         this.applyUpdate(particle, value);
         this.applyRender(particle, value);
      }

      @Override
      public void preRender(AdvancedParticleBase particle, float partialTicks) {
         float ageFrac = (particle.getAge() + partialTicks) / (float)particle.m_107273_();
         float value = this.animData.evaluate(ageFrac);
         this.applyRender(particle, value);
      }

      @Override
      public void preUpdate(AdvancedParticleBase particle) {
         float ageFrac = particle.getAge() / (float)particle.m_107273_();
         float value = this.animData.evaluate(ageFrac);
         this.applyUpdate(particle, value);
      }

      private void applyUpdate(AdvancedParticleBase particle, float value) {
         if (this.property == ParticleComponent.PropertyControl.EnumParticleProperty.POS_X) {
            if (this.additive) {
               particle.setPosX(particle.getPosX() + (double)value);
            } else {
               particle.setPosX((double)value);
            }
         } else if (this.property == ParticleComponent.PropertyControl.EnumParticleProperty.POS_Y) {
            if (this.additive) {
               particle.setPosY(particle.getPosY() + (double)value);
            } else {
               particle.setPosY((double)value);
            }
         } else if (this.property == ParticleComponent.PropertyControl.EnumParticleProperty.POS_Z) {
            if (this.additive) {
               particle.setPosZ(particle.getPosZ() + (double)value);
            } else {
               particle.setPosZ((double)value);
            }
         } else if (this.property == ParticleComponent.PropertyControl.EnumParticleProperty.MOTION_X) {
            if (this.additive) {
               particle.setMotionX(particle.getMotionX() + (double)value);
            } else {
               particle.setMotionX((double)value);
            }
         } else if (this.property == ParticleComponent.PropertyControl.EnumParticleProperty.MOTION_Y) {
            if (this.additive) {
               particle.setMotionY(particle.getMotionY() + (double)value);
            } else {
               particle.setMotionY((double)value);
            }
         } else if (this.property == ParticleComponent.PropertyControl.EnumParticleProperty.MOTION_Z) {
            if (this.additive) {
               particle.setMotionZ(particle.getMotionZ() + (double)value);
            } else {
               particle.setMotionZ((double)value);
            }
         } else if (this.property == ParticleComponent.PropertyControl.EnumParticleProperty.AIR_DRAG) {
            if (this.additive) {
               particle.airDrag += value;
            } else {
               particle.airDrag = value;
            }
         }
      }

      private void applyRender(AdvancedParticleBase particle, float value) {
         if (this.property == ParticleComponent.PropertyControl.EnumParticleProperty.RED) {
            if (this.additive) {
               particle.red += value;
            } else {
               particle.red = value;
            }
         } else if (this.property == ParticleComponent.PropertyControl.EnumParticleProperty.GREEN) {
            if (this.additive) {
               particle.green += value;
            } else {
               particle.green = value;
            }
         } else if (this.property == ParticleComponent.PropertyControl.EnumParticleProperty.BLUE) {
            if (this.additive) {
               particle.blue += value;
            } else {
               particle.blue = value;
            }
         } else if (this.property == ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA) {
            if (this.additive) {
               particle.f_107230_ += value;
            } else {
               particle.f_107230_ = value;
            }
         } else if (this.property == ParticleComponent.PropertyControl.EnumParticleProperty.SCALE) {
            if (this.additive) {
               particle.scale += value;
            } else {
               particle.scale = value;
            }
         } else if (this.property == ParticleComponent.PropertyControl.EnumParticleProperty.YAW) {
            if (particle.rotation instanceof ParticleRotation.EulerAngles eulerRot) {
               if (this.additive) {
                  eulerRot.yaw += value;
               } else {
                  eulerRot.yaw = value;
               }
            }
         } else if (this.property == ParticleComponent.PropertyControl.EnumParticleProperty.PITCH) {
            if (particle.rotation instanceof ParticleRotation.EulerAngles eulerRotx) {
               if (this.additive) {
                  eulerRotx.pitch += value;
               } else {
                  eulerRotx.pitch = value;
               }
            }
         } else if (this.property == ParticleComponent.PropertyControl.EnumParticleProperty.ROLL) {
            if (particle.rotation instanceof ParticleRotation.EulerAngles eulerRotxx) {
               if (this.additive) {
                  eulerRotxx.roll += value;
               } else {
                  eulerRotxx.roll = value;
               }
            }
         } else if (this.property == ParticleComponent.PropertyControl.EnumParticleProperty.PARTICLE_ANGLE
            && particle.rotation instanceof ParticleRotation.FaceCamera faceCameraRot) {
            if (this.additive) {
               faceCameraRot.faceCameraAngle += value;
            } else {
               faceCameraRot.faceCameraAngle = value;
            }
         }
      }

      public static enum EnumParticleProperty {
         POS_X,
         POS_Y,
         POS_Z,
         MOTION_X,
         MOTION_Y,
         MOTION_Z,
         RED,
         GREEN,
         BLUE,
         ALPHA,
         SCALE,
         YAW,
         PITCH,
         ROLL,
         PARTICLE_ANGLE,
         AIR_DRAG;
      }
   }
}
