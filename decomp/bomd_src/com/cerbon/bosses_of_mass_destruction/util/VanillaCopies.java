package com.cerbon.bosses_of_mass_destruction.util;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class VanillaCopies {
   public static void renderBillboard(
      PoseStack poseStack, MultiBufferSource buffer, int i, EntityRenderDispatcher dispatcher, RenderType type, Quaternion rotation
   ) {
      poseStack.m_85836_();
      poseStack.m_85845_(dispatcher.m_114470_());
      poseStack.m_85845_(Vector3f.f_122225_.m_122240_((float)Math.toRadians(180.0)));
      poseStack.m_85845_(rotation);
      Pose pose = poseStack.m_85850_();
      Matrix4f matrix4f = pose.m_85861_();
      Matrix3f matrix3f = pose.m_85864_();
      VertexConsumer vertexConsumer = buffer.m_6299_(type);
      produceVertex(vertexConsumer, matrix4f, matrix3f, i, 0.0F, 0, 0, 1);
      produceVertex(vertexConsumer, matrix4f, matrix3f, i, 1.0F, 0, 1, 1);
      produceVertex(vertexConsumer, matrix4f, matrix3f, i, 1.0F, 1, 1, 0);
      produceVertex(vertexConsumer, matrix4f, matrix3f, i, 0.0F, 1, 0, 0);
      poseStack.m_85849_();
   }

   public static void produceVertex(
      VertexConsumer vertexConsumer, Matrix4f modelMatrix, Matrix3f normalMatrix, int light, float x, int y, int textureU, int textureV
   ) {
      vertexConsumer.m_85982_(modelMatrix, x - 0.5F, (float)y - 0.25F, 0.0F)
         .m_6122_(255, 255, 255, 255)
         .m_7421_((float)textureU, (float)textureV)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(light)
         .m_85977_(normalMatrix, 0.0F, 1.0F, 0.0F)
         .m_5752_();
   }

   @NotNull
   public static Vector3f[] buildFlatGeometry(
      @NotNull Camera camera, float tickDelta, double prevPosX, double prevPosY, double prevPosZ, double x, double y, double z, float scale, float rotation
   ) {
      Vec3 vec3 = camera.m_90583_();
      float f = (float)(Mth.m_14139_((double)tickDelta, prevPosX, x) - vec3.m_7096_());
      float g = (float)(Mth.m_14139_((double)tickDelta, prevPosY, y) - vec3.m_7098_());
      float h = (float)(Mth.m_14139_((double)tickDelta, prevPosZ, z) - vec3.m_7094_());
      Vector3f[] vector3fs = new Vector3f[]{
         new Vector3f(-1.0F, 0.0F, -1.0F), new Vector3f(-1.0F, 0.0F, 1.0F), new Vector3f(1.0F, 0.0F, 1.0F), new Vector3f(1.0F, 0.0F, -1.0F)
      };

      for (int k = 0; k <= 3; k++) {
         Vector3f vector3f2 = vector3fs[k];
         vector3f2.m_122251_(Vector3f.f_122225_.m_122240_(rotation));
         vector3f2.m_122261_(scale);
         vector3f2.m_122272_(f, g, h);
      }

      return vector3fs;
   }

   public static Vector3f[] buildBillBoardGeometry(
      @NotNull Camera camera, float tickDelta, double prevPosX, double prevPosY, double prevPosZ, double x, double y, double z, float scale, float rotation
   ) {
      Vec3 vec3 = camera.m_90583_();
      float f = (float)(Mth.m_14139_((double)tickDelta, prevPosX, x) - vec3.m_7096_());
      float g = (float)(Mth.m_14139_((double)tickDelta, prevPosY, y) - vec3.m_7098_());
      float h = (float)(Mth.m_14139_((double)tickDelta, prevPosZ, z) - vec3.m_7094_());
      Quaternion quaternion2 = camera.m_90591_();
      Vector3f[] vector3fs = new Vector3f[]{
         new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)
      };

      for (int k = 0; k <= 3; k++) {
         Vector3f vector3f2 = vector3fs[k];
         vector3f2.m_122251_(Vector3f.f_122227_.m_122240_(rotation));
         vector3f2.m_122251_(quaternion2);
         vector3f2.m_122261_(scale);
         vector3f2.m_122272_(f, g, h);
      }

      return vector3fs;
   }

   public static void renderBeam(
      LivingEntity actor, Vec3 target, Vec3 prevTarget, float partialTicks, Vec3 color, PoseStack poseStack, MultiBufferSource buffer, RenderType renderType
   ) {
      float j = (float)actor.f_19853_.m_46467_() + partialTicks;
      float k = j % 1.0F;
      float l = actor.m_20192_();
      poseStack.m_85836_();
      poseStack.m_85837_(0.0, (double)l, 0.0);
      Vec3 vec3 = MathUtils.lerpVec(partialTicks, prevTarget, target);
      Vec3 vec32 = fromLerpedPosition(actor, (double)l, partialTicks);
      Vec3 vec33 = vec3.m_82546_(vec32);
      float m = (float)vec33.m_82553_();
      vec33 = vec33.m_82541_();
      float n = (float)Math.acos(vec33.f_82480_);
      float o = (float)Math.atan2(vec33.f_82481_, vec33.f_82479_);
      poseStack.m_85845_(Vector3f.f_122225_.m_122240_(((float) (Math.PI / 2) - o) * (180.0F / (float)Math.PI)));
      poseStack.m_85845_(Vector3f.f_122223_.m_122240_(n * (180.0F / (float)Math.PI)));
      float q = j * 0.05F * -1.5F;
      int red = (int)(color.m_7096_() * 255.0);
      int green = (int)(color.m_7098_() * 255.0);
      int blue = (int)(color.m_7094_() * 255.0);
      float x = Mth.m_14089_(q + (float) (Math.PI * 3.0 / 4.0)) * 0.282F;
      float y = Mth.m_14031_(q + (float) (Math.PI * 3.0 / 4.0)) * 0.282F;
      float z = Mth.m_14089_(q + (float) (Math.PI / 4)) * 0.282F;
      float aa = Mth.m_14031_(q + (float) (Math.PI / 4)) * 0.282F;
      float ab = Mth.m_14089_(q + ((float) Math.PI * 5.0F / 4.0F)) * 0.282F;
      float ac = Mth.m_14031_(q + ((float) Math.PI * 5.0F / 4.0F)) * 0.282F;
      float ad = Mth.m_14089_(q + ((float) Math.PI * 7.0F / 4.0F)) * 0.282F;
      float ae = Mth.m_14031_(q + ((float) Math.PI * 7.0F / 4.0F)) * 0.282F;
      float af = Mth.m_14089_(q + (float) Math.PI) * 0.2F;
      float ag = Mth.m_14031_(q + (float) Math.PI) * 0.2F;
      float ah = Mth.m_14089_(q + 0.0F) * 0.2F;
      float ai = Mth.m_14031_(q + 0.0F) * 0.2F;
      float aj = Mth.m_14089_(q + (float) (Math.PI / 2)) * 0.2F;
      float ak = Mth.m_14031_(q + (float) (Math.PI / 2)) * 0.2F;
      float al = Mth.m_14089_(q + (float) (Math.PI * 3.0 / 2.0)) * 0.2F;
      float am = Mth.m_14031_(q + (float) (Math.PI * 3.0 / 2.0)) * 0.2F;
      float aq = -1.0F - k;
      float ar = m * 2.5F + aq;
      VertexConsumer vertexConsumer = buffer.m_6299_(renderType);
      Pose pose = poseStack.m_85850_();
      Matrix4f matrix4f = pose.m_85861_();
      Matrix3f matrix3f = pose.m_85864_();
      vertex(vertexConsumer, matrix4f, matrix3f, af, m, ag, red, green, blue, 0.4999F, ar);
      vertex(vertexConsumer, matrix4f, matrix3f, af, 0.0F, ag, red, green, blue, 0.4999F, aq);
      vertex(vertexConsumer, matrix4f, matrix3f, ah, 0.0F, ai, red, green, blue, 0.0F, aq);
      vertex(vertexConsumer, matrix4f, matrix3f, ah, m, ai, red, green, blue, 0.0F, ar);
      vertex(vertexConsumer, matrix4f, matrix3f, aj, m, ak, red, green, blue, 0.4999F, ar);
      vertex(vertexConsumer, matrix4f, matrix3f, aj, 0.0F, ak, red, green, blue, 0.4999F, aq);
      vertex(vertexConsumer, matrix4f, matrix3f, al, 0.0F, am, red, green, blue, 0.0F, aq);
      vertex(vertexConsumer, matrix4f, matrix3f, al, m, am, red, green, blue, 0.0F, ar);
      float as = 0.0F;
      if (actor.f_19797_ % 2 == 0) {
         as = 0.5F;
      }

      vertex(vertexConsumer, matrix4f, matrix3f, x, m, y, red, green, blue, 0.5F, as + 0.5F);
      vertex(vertexConsumer, matrix4f, matrix3f, z, m, aa, red, green, blue, 1.0F, as + 0.5F);
      vertex(vertexConsumer, matrix4f, matrix3f, ad, m, ae, red, green, blue, 1.0F, as);
      vertex(vertexConsumer, matrix4f, matrix3f, ab, m, ac, red, green, blue, 0.5F, as);
      poseStack.m_85849_();
   }

   public static void vertex(
      VertexConsumer vertexConsumer, Matrix4f matrix4f, Matrix3f matrix3f, float f, float g, float h, int i, int j, int k, float l, float m
   ) {
      vertexConsumer.m_85982_(matrix4f, f, g, h)
         .m_6122_(i, j, k, 255)
         .m_7421_(l, m)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(15728800)
         .m_85977_(matrix3f, 0.0F, 0.0F, -1.0F)
         .m_5752_();
   }

   public static Vec3 fromLerpedPosition(LivingEntity entity, double yOffset, float delta) {
      double d = Mth.m_14139_((double)delta, entity.f_19790_, entity.m_20185_());
      double e = Mth.m_14139_((double)delta, entity.f_19791_, entity.m_20186_()) + yOffset;
      double f = Mth.m_14139_((double)delta, entity.f_19792_, entity.m_20189_());
      return new Vec3(d, e, f);
   }
}
