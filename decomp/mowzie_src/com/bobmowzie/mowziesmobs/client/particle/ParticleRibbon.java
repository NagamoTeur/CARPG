package com.bobmowzie.mowziesmobs.client.particle;

import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleRotation;
import com.bobmowzie.mowziesmobs.client.particle.util.RibbonComponent;
import com.bobmowzie.mowziesmobs.client.particle.util.RibbonParticleData;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector4f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ParticleRibbon extends AdvancedParticleBase {
   public Vec3[] positions;
   public Vec3[] prevPositions;
   public float texPanOffset;

   protected ParticleRibbon(
      ClientLevel worldIn,
      double xCoordIn,
      double yCoordIn,
      double zCoordIn,
      double motionX,
      double motionY,
      double motionZ,
      ParticleRotation rotation,
      double scale,
      double r,
      double g,
      double b,
      double a,
      double drag,
      double duration,
      boolean emissive,
      int length,
      ParticleComponent[] components
   ) {
      super(worldIn, xCoordIn, yCoordIn, zCoordIn, motionX, motionY, motionZ, rotation, scale, r, g, b, a, drag, duration, emissive, false, components);
      this.positions = new Vec3[length];
      this.prevPositions = new Vec3[length];
      if (this.positions.length >= 1) {
         this.positions[0] = new Vec3(this.getPosX(), this.getPosY(), this.getPosZ());
      }

      if (this.prevPositions.length >= 1) {
         this.prevPositions[0] = this.getPrevPos();
      }
   }

   @Override
   protected void updatePosition() {
      super.updatePosition();
   }

   @Override
   public void m_5744_(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
      this.f_107230_ = this.prevAlpha + (this.f_107230_ - this.prevAlpha) * partialTicks;
      if ((double)this.f_107230_ < 0.01) {
         this.f_107230_ = 0.01F;
      }

      this.f_107227_ = this.prevRed + (this.red - this.prevRed) * partialTicks;
      this.f_107228_ = this.prevGreen + (this.green - this.prevGreen) * partialTicks;
      this.f_107229_ = this.prevBlue + (this.blue - this.prevBlue) * partialTicks;
      this.particleScale = this.prevScale + (this.scale - this.prevScale) * partialTicks;

      for (ParticleComponent component : this.components) {
         component.preRender(this, partialTicks);
      }

      int j = this.m_6355_(partialTicks);
      float r = this.f_107227_;
      float g = this.f_107228_;
      float b = this.f_107229_;
      float a = this.f_107230_;
      float scale = this.particleScale;
      float prevR = r;
      float prevG = g;
      float prevB = b;
      float prevA = a;
      float prevScale = scale;

      for (ParticleComponent component : this.components) {
         if (component instanceof RibbonComponent.PropertyOverLength) {
            RibbonComponent.PropertyOverLength pOverLength = (RibbonComponent.PropertyOverLength)component;
            float value = pOverLength.evaluate(0.0F);
            if (pOverLength.getProperty() == RibbonComponent.PropertyOverLength.EnumRibbonProperty.SCALE) {
               prevScale *= value;
            } else if (pOverLength.getProperty() == RibbonComponent.PropertyOverLength.EnumRibbonProperty.RED) {
               prevR *= value;
            } else if (pOverLength.getProperty() == RibbonComponent.PropertyOverLength.EnumRibbonProperty.GREEN) {
               prevG *= value;
            } else if (pOverLength.getProperty() == RibbonComponent.PropertyOverLength.EnumRibbonProperty.BLUE) {
               prevB *= value;
            } else if (pOverLength.getProperty() == RibbonComponent.PropertyOverLength.EnumRibbonProperty.ALPHA) {
               prevA *= value;
            }
         }
      }

      Vec3 offsetDir = new Vec3(0.0, 0.0, 0.0);

      for (int index = 0; index < this.positions.length - 1; index++) {
         if (this.positions[index] != null && this.positions[index + 1] != null) {
            r = this.f_107227_;
            g = this.f_107228_;
            b = this.f_107229_;
            scale = this.particleScale;
            float t = ((float)index + 1.0F) / ((float)this.positions.length - 1.0F);
            float tPrev = (float)index / ((float)this.positions.length - 1.0F);

            for (ParticleComponent componentx : this.components) {
               if (componentx instanceof RibbonComponent.PropertyOverLength) {
                  RibbonComponent.PropertyOverLength pOverLength = (RibbonComponent.PropertyOverLength)componentx;
                  float value = pOverLength.evaluate(t);
                  if (pOverLength.getProperty() == RibbonComponent.PropertyOverLength.EnumRibbonProperty.SCALE) {
                     scale *= value;
                  } else if (pOverLength.getProperty() == RibbonComponent.PropertyOverLength.EnumRibbonProperty.RED) {
                     r *= value;
                  } else if (pOverLength.getProperty() == RibbonComponent.PropertyOverLength.EnumRibbonProperty.GREEN) {
                     g *= value;
                  } else if (pOverLength.getProperty() == RibbonComponent.PropertyOverLength.EnumRibbonProperty.BLUE) {
                     b *= value;
                  } else if (pOverLength.getProperty() == RibbonComponent.PropertyOverLength.EnumRibbonProperty.ALPHA) {
                     a *= value;
                  }
               }
            }

            Vec3 Vector3d = renderInfo.m_90583_();
            Vec3 p1 = this.prevPositions[index]
               .m_82549_(this.positions[index].m_82546_(this.prevPositions[index]).m_82490_((double)partialTicks))
               .m_82546_(Vector3d);
            Vec3 p2 = this.prevPositions[index + 1]
               .m_82549_(this.positions[index + 1].m_82546_(this.prevPositions[index + 1]).m_82490_((double)partialTicks))
               .m_82546_(Vector3d);
            if (index == 0) {
               Vec3 moveDir = p2.m_82546_(p1).m_82541_();
               if (this.rotation instanceof ParticleRotation.FaceCamera) {
                  Vec3 viewVec = new Vec3(renderInfo.m_90596_());
                  offsetDir = moveDir.m_82537_(viewVec).m_82541_();
               } else {
                  offsetDir = moveDir.m_82537_(new Vec3(0.0, 1.0, 0.0)).m_82541_();
               }

               offsetDir = offsetDir.m_82490_((double)prevScale);
            }

            Vec3[] aVector3d2 = new Vec3[]{offsetDir.m_82490_(-1.0), offsetDir, null, null};
            Vec3 moveDir = p2.m_82546_(p1).m_82541_();
            if (this.rotation instanceof ParticleRotation.FaceCamera) {
               Vec3 viewVec = new Vec3(renderInfo.m_90596_());
               offsetDir = moveDir.m_82537_(viewVec).m_82541_();
            } else {
               offsetDir = moveDir.m_82537_(new Vec3(0.0, 1.0, 0.0)).m_82541_();
            }

            offsetDir = offsetDir.m_82490_((double)scale);
            aVector3d2[2] = offsetDir;
            aVector3d2[3] = offsetDir.m_82490_(-1.0);
            Vector4f[] vertices2 = new Vector4f[]{
               new Vector4f((float)aVector3d2[0].f_82479_, (float)aVector3d2[0].f_82480_, (float)aVector3d2[0].f_82481_, 1.0F),
               new Vector4f((float)aVector3d2[1].f_82479_, (float)aVector3d2[1].f_82480_, (float)aVector3d2[1].f_82481_, 1.0F),
               new Vector4f((float)aVector3d2[2].f_82479_, (float)aVector3d2[2].f_82480_, (float)aVector3d2[2].f_82481_, 1.0F),
               new Vector4f((float)aVector3d2[3].f_82479_, (float)aVector3d2[3].f_82480_, (float)aVector3d2[3].f_82481_, 1.0F)
            };
            Matrix4f boxTranslate = Matrix4f.m_27653_((float)p1.f_82479_, (float)p1.f_82480_, (float)p1.f_82481_);
            vertices2[0].m_123607_(boxTranslate);
            vertices2[1].m_123607_(boxTranslate);
            boxTranslate = Matrix4f.m_27653_((float)p2.f_82479_, (float)p2.f_82480_, (float)p2.f_82481_);
            vertices2[2].m_123607_(boxTranslate);
            vertices2[3].m_123607_(boxTranslate);
            float halfU = (this.m_5952_() - this.m_5970_()) / 2.0F + this.m_5970_();
            float f = this.m_5970_() + this.texPanOffset;
            float f1 = halfU + this.texPanOffset;
            float f2 = this.m_5951_();
            float f3 = this.m_5950_();
            buffer.m_5483_((double)vertices2[0].m_123601_(), (double)vertices2[0].m_123615_(), (double)vertices2[0].m_123616_())
               .m_7421_(f1, f3)
               .m_85950_(prevR, prevG, prevB, prevA)
               .m_85969_(j)
               .m_5752_();
            buffer.m_5483_((double)vertices2[1].m_123601_(), (double)vertices2[1].m_123615_(), (double)vertices2[1].m_123616_())
               .m_7421_(f1, f2)
               .m_85950_(prevR, prevG, prevB, prevA)
               .m_85969_(j)
               .m_5752_();
            buffer.m_5483_((double)vertices2[2].m_123601_(), (double)vertices2[2].m_123615_(), (double)vertices2[2].m_123616_())
               .m_7421_(f, f2)
               .m_85950_(r, g, b, a)
               .m_85969_(j)
               .m_5752_();
            buffer.m_5483_((double)vertices2[3].m_123601_(), (double)vertices2[3].m_123615_(), (double)vertices2[3].m_123616_())
               .m_7421_(f, f3)
               .m_85950_(r, g, b, a)
               .m_85969_(j)
               .m_5752_();
            prevR = r;
            prevG = g;
            prevB = b;
            prevA = a;
         }
      }

      for (ParticleComponent componentxx : this.components) {
         componentxx.postRender(this, buffer, renderInfo, partialTicks, j);
      }
   }

   public AABB m_107277_() {
      if (this.positions != null && this.positions.length > 0 && this.positions[0] != null) {
         double minX = this.positions[0].m_7096_() - 0.1;
         double minY = this.positions[0].m_7098_() - 0.1;
         double minZ = this.positions[0].m_7094_() - 0.1;
         double maxX = this.positions[0].m_7096_() + 0.1;
         double maxY = this.positions[0].m_7098_() + 0.1;
         double maxZ = this.positions[0].m_7094_() + 0.1;

         for (Vec3 pos : this.positions) {
            if (pos != null) {
               minX = Math.min(minX, pos.m_7096_());
               minY = Math.min(minY, pos.m_7098_());
               minZ = Math.min(minZ, pos.m_7094_());
               maxX = Math.max(maxX, pos.m_7096_());
               maxY = Math.max(maxY, pos.m_7098_());
               maxZ = Math.max(maxZ, pos.m_7094_());
            }
         }

         return new AABB(minX, minY, minZ, maxX, maxY, maxZ);
      } else {
         return super.m_107277_();
      }
   }

   public float getMinUPublic() {
      return this.m_5970_();
   }

   public float getMaxUPublic() {
      return this.m_5952_();
   }

   public float getMinVPublic() {
      return this.m_5951_();
   }

   public float getMaxVPublic() {
      return this.m_5950_();
   }

   public static void spawnRibbon(
      Level world,
      ParticleType<? extends RibbonParticleData> particle,
      int length,
      double x,
      double y,
      double z,
      double motionX,
      double motionY,
      double motionZ,
      boolean faceCamera,
      double yaw,
      double pitch,
      double roll,
      double scale,
      double r,
      double g,
      double b,
      double a,
      double drag,
      double duration,
      boolean emissive
   ) {
      spawnRibbon(
         world,
         particle,
         length,
         x,
         y,
         z,
         motionX,
         motionY,
         motionZ,
         faceCamera,
         yaw,
         pitch,
         roll,
         scale,
         r,
         g,
         b,
         a,
         drag,
         duration,
         emissive,
         new ParticleComponent[0]
      );
   }

   public static void spawnRibbon(
      Level world,
      ParticleType<? extends RibbonParticleData> particle,
      int length,
      double x,
      double y,
      double z,
      double motionX,
      double motionY,
      double motionZ,
      boolean faceCamera,
      double yaw,
      double pitch,
      double roll,
      double scale,
      double r,
      double g,
      double b,
      double a,
      double drag,
      double duration,
      boolean emissive,
      ParticleComponent[] components
   ) {
      ParticleRotation rotation = (ParticleRotation)(faceCamera
         ? new ParticleRotation.FaceCamera(0.0F)
         : new ParticleRotation.EulerAngles((float)yaw, (float)pitch, (float)roll));
      world.m_7106_(
         new RibbonParticleData(particle, rotation, scale, r, g, b, a, drag, duration, emissive, length, components), x, y, z, motionX, motionY, motionZ
      );
   }

   @OnlyIn(Dist.CLIENT)
   public static final class Factory implements ParticleProvider<RibbonParticleData> {
      private final SpriteSet spriteSet;

      public Factory(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(RibbonParticleData typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         ParticleRibbon particle = new ParticleRibbon(
            worldIn,
            x,
            y,
            z,
            xSpeed,
            ySpeed,
            zSpeed,
            typeIn.getRotation(),
            typeIn.getScale(),
            typeIn.getRed(),
            typeIn.getGreen(),
            typeIn.getBlue(),
            typeIn.getAlpha(),
            typeIn.getAirDrag(),
            typeIn.getDuration(),
            typeIn.isEmissive(),
            typeIn.getLength(),
            typeIn.getComponents()
         );
         particle.m_108339_(this.spriteSet);
         return particle;
      }
   }
}
