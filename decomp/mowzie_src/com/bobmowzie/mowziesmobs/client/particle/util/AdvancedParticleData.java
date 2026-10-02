package com.bobmowzie.mowziesmobs.client.particle.util;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleOptions.Deserializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AdvancedParticleData implements ParticleOptions {
   public static final Deserializer<AdvancedParticleData> DESERIALIZER = new Deserializer<AdvancedParticleData>() {
      public AdvancedParticleData fromCommand(ParticleType<AdvancedParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException {
         reader.expect(' ');
         double airDrag = reader.readDouble();
         reader.expect(' ');
         double red = reader.readDouble();
         reader.expect(' ');
         double green = reader.readDouble();
         reader.expect(' ');
         double blue = reader.readDouble();
         reader.expect(' ');
         double alpha = reader.readDouble();
         reader.expect(' ');
         String rotationMode = reader.readString();
         reader.expect(' ');
         double scale = reader.readDouble();
         reader.expect(' ');
         double yaw = reader.readDouble();
         reader.expect(' ');
         double pitch = reader.readDouble();
         reader.expect(' ');
         double roll = reader.readDouble();
         reader.expect(' ');
         boolean emissive = reader.readBoolean();
         reader.expect(' ');
         double duration = reader.readDouble();
         reader.expect(' ');
         double faceCameraAngle = reader.readDouble();
         reader.expect(' ');
         boolean canCollide = reader.readBoolean();
         ParticleRotation rotation;
         if (rotationMode.equals("face_camera")) {
            rotation = new ParticleRotation.FaceCamera((float)faceCameraAngle);
         } else if (rotationMode.equals("euler")) {
            rotation = new ParticleRotation.EulerAngles((float)yaw, (float)pitch, (float)roll);
         } else {
            rotation = new ParticleRotation.OrientVector(new Vec3(yaw, pitch, roll));
         }

         return new AdvancedParticleData(particleTypeIn, rotation, scale, red, green, blue, alpha, airDrag, duration, emissive, canCollide);
      }

      public AdvancedParticleData fromNetwork(ParticleType<AdvancedParticleData> particleTypeIn, FriendlyByteBuf buffer) {
         double airDrag = (double)buffer.readFloat();
         double red = (double)buffer.readFloat();
         double green = (double)buffer.readFloat();
         double blue = (double)buffer.readFloat();
         double alpha = (double)buffer.readFloat();
         String rotationMode = buffer.m_130277_();
         double scale = (double)buffer.readFloat();
         double yaw = (double)buffer.readFloat();
         double pitch = (double)buffer.readFloat();
         double roll = (double)buffer.readFloat();
         boolean emissive = buffer.readBoolean();
         double duration = (double)buffer.readFloat();
         double faceCameraAngle = (double)buffer.readFloat();
         boolean canCollide = buffer.readBoolean();
         ParticleRotation rotation;
         if (rotationMode.equals("face_camera")) {
            rotation = new ParticleRotation.FaceCamera((float)faceCameraAngle);
         } else if (rotationMode.equals("euler")) {
            rotation = new ParticleRotation.EulerAngles((float)yaw, (float)pitch, (float)roll);
         } else {
            rotation = new ParticleRotation.OrientVector(new Vec3(yaw, pitch, roll));
         }

         return new AdvancedParticleData(particleTypeIn, rotation, scale, red, green, blue, alpha, airDrag, duration, emissive, canCollide);
      }
   };
   private final ParticleType<? extends AdvancedParticleData> type;
   private final float airDrag;
   private final float red;
   private final float green;
   private final float blue;
   private final float alpha;
   private final ParticleRotation rotation;
   private final float scale;
   private final boolean emissive;
   private final float duration;
   private final boolean canCollide;
   private final ParticleComponent[] components;

   public AdvancedParticleData(
      ParticleType<? extends AdvancedParticleData> type,
      ParticleRotation rotation,
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
      this(type, rotation, scale, r, g, b, a, drag, duration, emissive, canCollide, new ParticleComponent[0]);
   }

   public AdvancedParticleData(
      ParticleType<? extends AdvancedParticleData> type,
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
      this.type = type;
      this.rotation = rotation;
      this.scale = (float)scale;
      this.red = (float)r;
      this.green = (float)g;
      this.blue = (float)b;
      this.alpha = (float)a;
      this.emissive = emissive;
      this.airDrag = (float)drag;
      this.duration = (float)duration;
      this.canCollide = canCollide;
      this.components = components;
   }

   public void m_7711_(FriendlyByteBuf buffer) {
      float faceCameraAngle = 0.0F;
      float yaw = 0.0F;
      float pitch = 0.0F;
      float roll = 0.0F;
      String rotationMode;
      if (this.rotation instanceof ParticleRotation.FaceCamera) {
         rotationMode = "face_camera";
         faceCameraAngle = ((ParticleRotation.FaceCamera)this.rotation).faceCameraAngle;
      } else if (this.rotation instanceof ParticleRotation.EulerAngles) {
         rotationMode = "euler";
         yaw = ((ParticleRotation.EulerAngles)this.rotation).yaw;
         pitch = ((ParticleRotation.EulerAngles)this.rotation).pitch;
         roll = ((ParticleRotation.EulerAngles)this.rotation).roll;
      } else {
         rotationMode = "orient";
         Vec3 vec = ((ParticleRotation.OrientVector)this.rotation).orientation;
         yaw = (float)vec.f_82479_;
         pitch = (float)vec.f_82480_;
         roll = (float)vec.f_82481_;
      }

      buffer.writeFloat(this.airDrag);
      buffer.writeFloat(this.red);
      buffer.writeFloat(this.green);
      buffer.writeFloat(this.blue);
      buffer.writeFloat(this.alpha);
      buffer.m_130070_(rotationMode);
      buffer.writeFloat(this.scale);
      buffer.writeFloat(yaw);
      buffer.writeFloat(pitch);
      buffer.writeFloat(roll);
      buffer.writeBoolean(this.emissive);
      buffer.writeFloat(this.duration);
      buffer.writeFloat(faceCameraAngle);
      buffer.writeBoolean(this.canCollide);
   }

   public String m_5942_() {
      float faceCameraAngle = 0.0F;
      float yaw = 0.0F;
      float pitch = 0.0F;
      float roll = 0.0F;
      String rotationMode;
      if (this.rotation instanceof ParticleRotation.FaceCamera) {
         rotationMode = "face_camera";
         faceCameraAngle = ((ParticleRotation.FaceCamera)this.rotation).faceCameraAngle;
      } else if (this.rotation instanceof ParticleRotation.EulerAngles) {
         rotationMode = "euler";
         yaw = ((ParticleRotation.EulerAngles)this.rotation).yaw;
         pitch = ((ParticleRotation.EulerAngles)this.rotation).pitch;
         roll = ((ParticleRotation.EulerAngles)this.rotation).roll;
      } else {
         rotationMode = "orient";
         Vec3 vec = ((ParticleRotation.OrientVector)this.rotation).orientation;
         yaw = (float)vec.f_82479_;
         pitch = (float)vec.f_82480_;
         roll = (float)vec.f_82481_;
      }

      return String.format(
         Locale.ROOT,
         "%s %.2f %.2f %.2f %.2f %.2f %s %.2f %.2f %.2f %.2f %b %.2f %.2f %b",
         Registry.f_122829_.m_7981_(this.m_6012_()),
         this.airDrag,
         this.red,
         this.green,
         this.blue,
         this.alpha,
         rotationMode,
         this.scale,
         yaw,
         pitch,
         roll,
         this.emissive,
         this.duration,
         faceCameraAngle,
         this.canCollide
      );
   }

   public ParticleType<? extends AdvancedParticleData> m_6012_() {
      return this.type;
   }

   @OnlyIn(Dist.CLIENT)
   public double getRed() {
      return (double)this.red;
   }

   @OnlyIn(Dist.CLIENT)
   public double getGreen() {
      return (double)this.green;
   }

   @OnlyIn(Dist.CLIENT)
   public double getBlue() {
      return (double)this.blue;
   }

   @OnlyIn(Dist.CLIENT)
   public double getAlpha() {
      return (double)this.alpha;
   }

   @OnlyIn(Dist.CLIENT)
   public double getAirDrag() {
      return (double)this.airDrag;
   }

   @OnlyIn(Dist.CLIENT)
   public ParticleRotation getRotation() {
      return this.rotation;
   }

   @OnlyIn(Dist.CLIENT)
   public double getScale() {
      return (double)this.scale;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean isEmissive() {
      return this.emissive;
   }

   @OnlyIn(Dist.CLIENT)
   public double getDuration() {
      return (double)this.duration;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean getCanCollide() {
      return this.canCollide;
   }

   @OnlyIn(Dist.CLIENT)
   public ParticleComponent[] getComponents() {
      return this.components;
   }

   public static Codec<AdvancedParticleData> CODEC(ParticleType<AdvancedParticleData> particleType) {
      return RecordCodecBuilder.create(
         codecBuilder -> codecBuilder.group(
                  Codec.DOUBLE.fieldOf("scale").forGetter(AdvancedParticleData::getScale),
                  Codec.DOUBLE.fieldOf("r").forGetter(AdvancedParticleData::getRed),
                  Codec.DOUBLE.fieldOf("g").forGetter(AdvancedParticleData::getGreen),
                  Codec.DOUBLE.fieldOf("b").forGetter(AdvancedParticleData::getBlue),
                  Codec.DOUBLE.fieldOf("a").forGetter(AdvancedParticleData::getAlpha),
                  Codec.DOUBLE.fieldOf("drag").forGetter(AdvancedParticleData::getAirDrag),
                  Codec.DOUBLE.fieldOf("duration").forGetter(AdvancedParticleData::getDuration),
                  Codec.BOOL.fieldOf("emissive").forGetter(AdvancedParticleData::isEmissive),
                  Codec.BOOL.fieldOf("canCollide").forGetter(AdvancedParticleData::getCanCollide)
               )
               .apply(
                  codecBuilder,
                  (scale, r, g, b, a, drag, duration, emissive, canCollide) -> new AdvancedParticleData(
                        particleType, new ParticleRotation.FaceCamera(0.0F), scale, r, g, b, a, drag, duration, emissive, canCollide, new ParticleComponent[0]
                     )
               )
      );
   }
}
