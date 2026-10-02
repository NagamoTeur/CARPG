package com.bobmowzie.mowziesmobs.client.particle.util;

import com.bobmowzie.mowziesmobs.client.particle.ParticleRibbon;
import com.bobmowzie.mowziesmobs.client.render.MMRenderType;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AdvancedParticleBase extends TextureSheetParticle {
   public boolean doRender;
   public float airDrag;
   public float red;
   public float green;
   public float blue;
   public float f_107230_;
   public float prevRed;
   public float prevGreen;
   public float prevBlue;
   public float prevAlpha;
   public float scale;
   public float prevScale;
   public float particleScale;
   public ParticleRotation rotation;
   public boolean emissive;
   public double prevMotionX;
   public double prevMotionY;
   public double prevMotionZ;
   public ParticleComponent[] components;
   public ParticleRibbon ribbon;

   protected AdvancedParticleBase(
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
      boolean canCollide,
      ParticleComponent[] components
   ) {
      super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0, 0.0, 0.0);
      this.f_107215_ = motionX;
      this.f_107216_ = motionY;
      this.f_107217_ = motionZ;
      this.red = (float)r;
      this.green = (float)g;
      this.blue = (float)b;
      this.f_107230_ = (float)a;
      this.scale = (float)scale;
      this.f_107225_ = (int)duration;
      this.airDrag = (float)drag;
      this.rotation = rotation;
      this.components = components;
      this.emissive = emissive;
      this.ribbon = null;
      this.doRender = true;

      for (ParticleComponent component : components) {
         component.init(this);
      }

      this.f_107209_ = this.f_107212_;
      this.f_107210_ = this.f_107213_;
      this.f_107211_ = this.f_107214_;
      this.prevRed = this.red;
      this.prevGreen = this.green;
      this.prevBlue = this.blue;
      this.prevAlpha = this.f_107230_;
      this.rotation.setPrevValues();
      this.prevScale = this.scale;
      this.f_107219_ = canCollide;
   }

   public ParticleRenderType m_7556_() {
      return MMRenderType.PARTICLE_SHEET_TRANSLUCENT_NO_DEPTH;
   }

   public int m_6355_(float partialTick) {
      int i = super.m_6355_(partialTick);
      if (this.emissive) {
         int k = i >> 16 & 0xFF;
         return 240 | k << 16;
      } else {
         return i;
      }
   }

   public void m_5989_() {
      this.prevRed = this.red;
      this.prevGreen = this.green;
      this.prevBlue = this.blue;
      this.prevAlpha = this.f_107230_;
      this.prevScale = this.scale;
      this.rotation.setPrevValues();
      this.f_107209_ = this.f_107212_;
      this.f_107210_ = this.f_107213_;
      this.f_107211_ = this.f_107214_;
      this.prevMotionX = this.f_107215_;
      this.prevMotionY = this.f_107216_;
      this.prevMotionZ = this.f_107217_;

      for (ParticleComponent component : this.components) {
         component.preUpdate(this);
      }

      if (this.f_107224_++ >= this.f_107225_) {
         this.m_107274_();
      }

      this.updatePosition();

      for (ParticleComponent component : this.components) {
         component.postUpdate(this);
      }

      if (this.ribbon != null) {
         this.ribbon.m_107264_(this.f_107212_, this.f_107213_, this.f_107214_);
         this.ribbon.positions[0] = new Vec3(this.f_107212_, this.f_107213_, this.f_107214_);
         this.ribbon.prevPositions[0] = this.getPrevPos();
      }
   }

   protected void updatePosition() {
      this.m_6257_(this.f_107215_, this.f_107216_, this.f_107217_);
      if (this.f_107218_ && this.f_107219_) {
         this.f_107215_ *= 0.7F;
         this.f_107217_ *= 0.7F;
      }

      this.f_107215_ = this.f_107215_ * (double)this.airDrag;
      this.f_107216_ = this.f_107216_ * (double)this.airDrag;
      this.f_107217_ = this.f_107217_ * (double)this.airDrag;
   }

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

      if (this.doRender) {
         Vec3 Vector3d = renderInfo.m_90583_();
         float f = (float)(Mth.m_14139_((double)partialTicks, this.f_107209_, this.f_107212_) - Vector3d.m_7096_());
         float f1 = (float)(Mth.m_14139_((double)partialTicks, this.f_107210_, this.f_107213_) - Vector3d.m_7098_());
         float f2 = (float)(Mth.m_14139_((double)partialTicks, this.f_107211_, this.f_107214_) - Vector3d.m_7094_());
         Quaternion quaternion = new Quaternion(0.0F, 0.0F, 0.0F, 1.0F);
         if (this.rotation instanceof ParticleRotation.FaceCamera faceCameraRot) {
            if (faceCameraRot.faceCameraAngle == 0.0F && faceCameraRot.prevFaceCameraAngle == 0.0F) {
               quaternion = renderInfo.m_90591_();
            } else {
               quaternion = new Quaternion(renderInfo.m_90591_());
               float f3 = Mth.m_14179_(partialTicks, faceCameraRot.prevFaceCameraAngle, faceCameraRot.faceCameraAngle);
               quaternion.m_80148_(Vector3f.f_122227_.m_122270_(f3));
            }
         } else if (this.rotation instanceof ParticleRotation.EulerAngles eulerRot) {
            float rotX = eulerRot.prevPitch + (eulerRot.pitch - eulerRot.prevPitch) * partialTicks;
            float rotY = eulerRot.prevYaw + (eulerRot.yaw - eulerRot.prevYaw) * partialTicks;
            float rotZ = eulerRot.prevRoll + (eulerRot.roll - eulerRot.prevRoll) * partialTicks;
            Quaternion quatX = new Quaternion(rotX, 0.0F, 0.0F, false);
            Quaternion quatY = new Quaternion(0.0F, rotY, 0.0F, false);
            Quaternion quatZ = new Quaternion(0.0F, 0.0F, rotZ, false);
            quaternion.m_80148_(quatZ);
            quaternion.m_80148_(quatY);
            quaternion.m_80148_(quatX);
         }

         if (this.rotation instanceof ParticleRotation.OrientVector orientRot) {
            double x = orientRot.prevOrientation.f_82479_ + (orientRot.orientation.f_82479_ - orientRot.prevOrientation.f_82479_) * (double)partialTicks;
            double y = orientRot.prevOrientation.f_82480_ + (orientRot.orientation.f_82480_ - orientRot.prevOrientation.f_82480_) * (double)partialTicks;
            double z = orientRot.prevOrientation.f_82481_ + (orientRot.orientation.f_82481_ - orientRot.prevOrientation.f_82481_) * (double)partialTicks;
            float pitch = (float)Math.asin(-y);
            float yaw = (float)Mth.m_14136_(x, z);
            Quaternion quatX = new Quaternion(pitch, 0.0F, 0.0F, false);
            Quaternion quatY = new Quaternion(0.0F, yaw, 0.0F, false);
            quaternion.m_80148_(quatY);
            quaternion.m_80148_(quatX);
         }

         Vector3f[] avector3f = new Vector3f[]{
            new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)
         };
         float f4 = this.particleScale * 0.1F;

         for (int i = 0; i < 4; i++) {
            Vector3f vector3f = avector3f[i];
            vector3f.m_122251_(quaternion);
            vector3f.m_122261_(f4);
            vector3f.m_122272_(f, f1, f2);
         }

         float f7 = this.m_5970_();
         float f8 = this.m_5952_();
         float f5 = this.m_5951_();
         float f6 = this.m_5950_();
         int j = this.m_6355_(partialTicks);
         buffer.m_5483_((double)avector3f[0].m_122239_(), (double)avector3f[0].m_122260_(), (double)avector3f[0].m_122269_())
            .m_7421_(f8, f6)
            .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
            .m_85969_(j)
            .m_5752_();
         buffer.m_5483_((double)avector3f[1].m_122239_(), (double)avector3f[1].m_122260_(), (double)avector3f[1].m_122269_())
            .m_7421_(f8, f5)
            .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
            .m_85969_(j)
            .m_5752_();
         buffer.m_5483_((double)avector3f[2].m_122239_(), (double)avector3f[2].m_122260_(), (double)avector3f[2].m_122269_())
            .m_7421_(f7, f5)
            .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
            .m_85969_(j)
            .m_5752_();
         buffer.m_5483_((double)avector3f[3].m_122239_(), (double)avector3f[3].m_122260_(), (double)avector3f[3].m_122269_())
            .m_7421_(f7, f6)
            .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
            .m_85969_(j)
            .m_5752_();

         for (ParticleComponent component : this.components) {
            component.postRender(this, buffer, renderInfo, partialTicks, j);
         }
      }
   }

   public float getAge() {
      return (float)this.f_107224_;
   }

   public double getPosX() {
      return this.f_107212_;
   }

   public void setPosX(double posX) {
      this.m_107264_(posX, this.f_107213_, this.f_107214_);
   }

   public double getPosY() {
      return this.f_107213_;
   }

   public void setPosY(double posY) {
      this.m_107264_(this.f_107212_, posY, this.f_107214_);
   }

   public double getPosZ() {
      return this.f_107214_;
   }

   public void setPosZ(double posZ) {
      this.m_107264_(this.f_107212_, this.f_107213_, posZ);
   }

   public double getMotionX() {
      return this.f_107215_;
   }

   public void setMotionX(double motionX) {
      this.f_107215_ = motionX;
   }

   public double getMotionY() {
      return this.f_107216_;
   }

   public void setMotionY(double motionY) {
      this.f_107216_ = motionY;
   }

   public double getMotionZ() {
      return this.f_107217_;
   }

   public void setMotionZ(double motionZ) {
      this.f_107217_ = motionZ;
   }

   public Vec3 getPrevPos() {
      return new Vec3(this.f_107209_, this.f_107210_, this.f_107211_);
   }

   public double getPrevPosX() {
      return this.f_107209_;
   }

   public double getPrevPosY() {
      return this.f_107210_;
   }

   public double getPrevPosZ() {
      return this.f_107211_;
   }

   public Level getWorld() {
      return this.f_107208_;
   }

   public static void spawnParticle(
      Level world,
      ParticleType<AdvancedParticleData> particle,
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
      double faceCameraAngle,
      double scale,
      double r,
      double g,
      double b,
      double a,
      double drag,
      double duration,
      boolean emissive,
      boolean canCollide
   ) {
      spawnParticle(
         world,
         particle,
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
         faceCameraAngle,
         scale,
         r,
         g,
         b,
         a,
         drag,
         duration,
         emissive,
         canCollide,
         new ParticleComponent[0]
      );
   }

   public static void spawnParticle(
      Level world,
      ParticleType<AdvancedParticleData> particle,
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
      double faceCameraAngle,
      double scale,
      double r,
      double g,
      double b,
      double a,
      double drag,
      double duration,
      boolean emissive,
      boolean canCollide,
      ParticleComponent[] components
   ) {
      ParticleRotation rotation = (ParticleRotation)(faceCamera
         ? new ParticleRotation.FaceCamera((float)faceCameraAngle)
         : new ParticleRotation.EulerAngles((float)yaw, (float)pitch, (float)roll));
      world.m_7106_(
         new AdvancedParticleData(particle, rotation, scale, r, g, b, a, drag, duration, emissive, canCollide, components), x, y, z, motionX, motionY, motionZ
      );
   }

   public static void spawnParticle(
      Level world,
      ParticleType<AdvancedParticleData> particle,
      double x,
      double y,
      double z,
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
      boolean canCollide,
      ParticleComponent[] components
   ) {
      world.m_7106_(
         new AdvancedParticleData(particle, rotation, scale, r, g, b, a, drag, duration, emissive, canCollide, components), x, y, z, motionX, motionY, motionZ
      );
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements ParticleProvider<AdvancedParticleData> {
      private final SpriteSet spriteSet;

      public Factory(SpriteSet sprite) {
         this.spriteSet = sprite;
      }

      public Particle createParticle(
         AdvancedParticleData typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
      ) {
         AdvancedParticleBase particle = new AdvancedParticleBase(
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
            typeIn.getCanCollide(),
            typeIn.getComponents()
         );
         particle.m_107253_((float)typeIn.getRed(), (float)typeIn.getGreen(), (float)typeIn.getBlue());
         particle.m_108335_(this.spriteSet);
         return particle;
      }
   }
}
