package io.redspace.ironsspellbooks.entity.spells.blood_slash;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class BloodSlashRenderer extends EntityRenderer<BloodSlashProjectile> {
   private static ResourceLocation TEXTURE = IronsSpellbooks.id("textures/entity/blood_slash/blood_slash_large.png");
   private static ResourceLocation[] TEXTURES = new ResourceLocation[]{
      new ResourceLocation("textures/particle/sweep_0.png"),
      new ResourceLocation("textures/particle/sweep_1.png"),
      new ResourceLocation("textures/particle/sweep_2.png"),
      new ResourceLocation("textures/particle/sweep_3.png"),
      new ResourceLocation("textures/particle/sweep_4.png"),
      new ResourceLocation("textures/particle/sweep_5.png"),
      new ResourceLocation("textures/particle/sweep_6.png"),
      new ResourceLocation("textures/particle/sweep_7.png")
   };

   public BloodSlashRenderer(Context context) {
      super(context);
   }

   public void render(BloodSlashProjectile entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
      poseStack.m_85836_();
      Pose pose = poseStack.m_85850_();
      Matrix4f poseMatrix = pose.m_85861_();
      Matrix3f normalMatrix = pose.m_85864_();
      poseStack.m_85845_(Vector3f.f_122225_.m_122240_(Mth.m_14179_(partialTicks, entity.f_19859_, entity.m_146908_())));
      poseStack.m_85845_(Vector3f.f_122223_.m_122240_(-Mth.m_14179_(partialTicks, entity.f_19860_, entity.m_146909_())));
      entity.animationTime++;
      poseStack.m_85845_(Vector3f.f_122227_.m_122240_((float)(entity.animationSeed % 30 - 15) * (float)Math.sin((double)entity.animationTime * 0.015)));
      float oldWith = (float)entity.oldBB.m_82362_();
      float width = entity.m_20205_();
      width = oldWith + (width - oldWith) * Math.min(partialTicks, 1.0F);
      poseStack.m_85845_(Vector3f.f_122225_.m_122240_(-15.0F));
      poseStack.m_85845_(Vector3f.f_122227_.m_122240_(-10.0F));
      this.drawSlash(pose, entity, bufferSource, light, width, 4);
      poseStack.m_85845_(Vector3f.f_122225_.m_122240_(30.0F));
      poseStack.m_85845_(Vector3f.f_122227_.m_122240_(20.0F));
      this.drawSlash(pose, entity, bufferSource, light, width, 0);
      poseStack.m_85849_();
      super.m_7392_(entity, yaw, partialTicks, poseStack, bufferSource, light);
   }

   private void drawSlash(Pose pose, BloodSlashProjectile entity, MultiBufferSource bufferSource, int light, float width, int offset) {
      Matrix4f poseMatrix = pose.m_85861_();
      Matrix3f normalMatrix = pose.m_85864_();
      VertexConsumer consumer = bufferSource.m_6299_(RenderType.m_110458_(this.getTextureLocation(entity, offset)));
      float halfWidth = width * 0.5F;
      consumer.m_85982_(poseMatrix, -halfWidth, -0.1F, -halfWidth)
         .m_6122_(90, 0, 10, 255)
         .m_7421_(0.0F, 1.0F)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(light)
         .m_85977_(normalMatrix, 0.0F, 1.0F, 0.0F)
         .m_5752_();
      consumer.m_85982_(poseMatrix, halfWidth, -0.1F, -halfWidth)
         .m_6122_(90, 0, 10, 255)
         .m_7421_(1.0F, 1.0F)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(light)
         .m_85977_(normalMatrix, 0.0F, 1.0F, 0.0F)
         .m_5752_();
      consumer.m_85982_(poseMatrix, halfWidth, -0.1F, halfWidth)
         .m_6122_(90, 0, 10, 255)
         .m_7421_(1.0F, 0.0F)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(light)
         .m_85977_(normalMatrix, 0.0F, 1.0F, 0.0F)
         .m_5752_();
      consumer.m_85982_(poseMatrix, -halfWidth, -0.1F, halfWidth)
         .m_6122_(90, 0, 10, 255)
         .m_7421_(0.0F, 0.0F)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(light)
         .m_85977_(normalMatrix, 0.0F, 1.0F, 0.0F)
         .m_5752_();
   }

   public ResourceLocation getTextureLocation(BloodSlashProjectile entity) {
      int frame = entity.animationTime / 4 % TEXTURES.length;
      return TEXTURES[frame];
   }

   private ResourceLocation getTextureLocation(BloodSlashProjectile entity, int offset) {
      int frame = (entity.animationTime / 6 + offset) % TEXTURES.length;
      return TEXTURES[frame];
   }
}
