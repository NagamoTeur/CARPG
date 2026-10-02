package io.redspace.ironsspellbooks.entity.spells.lightning_lance;

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
import net.minecraft.world.phys.Vec3;

public class LightningLanceRenderer extends EntityRenderer<LightningLanceProjectile> {
   public static final ResourceLocation[] TEXTURES = new ResourceLocation[]{
      IronsSpellbooks.id("textures/entity/lightning_lance/lightning_lance_1.png"),
      IronsSpellbooks.id("textures/entity/lightning_lance/lightning_lance_2.png"),
      IronsSpellbooks.id("textures/entity/lightning_lance/lightning_lance_3.png"),
      IronsSpellbooks.id("textures/entity/lightning_lance/lightning_lance_4.png"),
      IronsSpellbooks.id("textures/entity/lightning_lance/lightning_lance_5.png"),
      IronsSpellbooks.id("textures/entity/lightning_lance/lightning_lance_6.png"),
      IronsSpellbooks.id("textures/entity/lightning_lance/lightning_lance_7.png")
   };

   public LightningLanceRenderer(Context context) {
      super(context);
   }

   public void render(LightningLanceProjectile entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
      poseStack.m_85836_();
      Vec3 motion = entity.m_20184_();
      float xRot = -((float)(Mth.m_14136_(motion.m_165924_(), motion.f_82480_) * 180.0F / (float)Math.PI) - 90.0F);
      float yRot = -((float)(Mth.m_14136_(motion.f_82481_, motion.f_82479_) * 180.0F / (float)Math.PI) + 90.0F);
      poseStack.m_85845_(Vector3f.f_122225_.m_122240_(yRot));
      poseStack.m_85845_(Vector3f.f_122223_.m_122240_(xRot));
      renderModel(poseStack, bufferSource, entity.getAge());
      poseStack.m_85849_();
      super.m_7392_(entity, yaw, partialTicks, poseStack, bufferSource, light);
   }

   public static void renderModel(PoseStack poseStack, MultiBufferSource bufferSource, int animOffset) {
      Pose pose = poseStack.m_85850_();
      Matrix4f poseMatrix = pose.m_85861_();
      Matrix3f normalMatrix = pose.m_85864_();
      VertexConsumer consumer = bufferSource.m_6299_(RenderType.m_110436_(getTextureLocation(animOffset), 0.0F, 0.0F));
      float halfWidth = 2.0F;
      float halfHeight = 1.0F;
      float angleCorrection = 55.0F;
      poseStack.m_85845_(Vector3f.f_122223_.m_122240_(angleCorrection));
      consumer.m_85982_(poseMatrix, 0.0F, -halfWidth, -halfHeight)
         .m_6122_(255, 255, 255, 255)
         .m_7421_(0.0F, 1.0F)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(15728880)
         .m_85977_(normalMatrix, 0.0F, 1.0F, 0.0F)
         .m_5752_();
      consumer.m_85982_(poseMatrix, 0.0F, halfWidth, -halfHeight)
         .m_6122_(255, 255, 255, 255)
         .m_7421_(0.0F, 0.0F)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(15728880)
         .m_85977_(normalMatrix, 0.0F, 1.0F, 0.0F)
         .m_5752_();
      consumer.m_85982_(poseMatrix, 0.0F, halfWidth, halfHeight)
         .m_6122_(255, 255, 255, 255)
         .m_7421_(1.0F, 0.0F)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(15728880)
         .m_85977_(normalMatrix, 0.0F, 1.0F, 0.0F)
         .m_5752_();
      consumer.m_85982_(poseMatrix, 0.0F, -halfWidth, halfHeight)
         .m_6122_(255, 255, 255, 255)
         .m_7421_(1.0F, 1.0F)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(15728880)
         .m_85977_(normalMatrix, 0.0F, 1.0F, 0.0F)
         .m_5752_();
      poseStack.m_85845_(Vector3f.f_122223_.m_122240_(-angleCorrection));
      poseStack.m_85845_(Vector3f.f_122225_.m_122240_(-angleCorrection));
      consumer.m_85982_(poseMatrix, -halfWidth, 0.0F, -halfHeight)
         .m_6122_(255, 255, 255, 255)
         .m_7421_(0.0F, 1.0F)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(15728880)
         .m_85977_(normalMatrix, 0.0F, 1.0F, 0.0F)
         .m_5752_();
      consumer.m_85982_(poseMatrix, halfWidth, 0.0F, -halfHeight)
         .m_6122_(255, 255, 255, 255)
         .m_7421_(0.0F, 0.0F)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(15728880)
         .m_85977_(normalMatrix, 0.0F, 1.0F, 0.0F)
         .m_5752_();
      consumer.m_85982_(poseMatrix, halfWidth, 0.0F, halfHeight)
         .m_6122_(255, 255, 255, 255)
         .m_7421_(1.0F, 0.0F)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(15728880)
         .m_85977_(normalMatrix, 0.0F, 1.0F, 0.0F)
         .m_5752_();
      consumer.m_85982_(poseMatrix, -halfWidth, 0.0F, halfHeight)
         .m_6122_(255, 255, 255, 255)
         .m_7421_(1.0F, 1.0F)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(15728880)
         .m_85977_(normalMatrix, 0.0F, 1.0F, 0.0F)
         .m_5752_();
      poseStack.m_85845_(Vector3f.f_122225_.m_122240_(angleCorrection));
   }

   public ResourceLocation getTextureLocation(LightningLanceProjectile entity) {
      return getTextureLocation(entity.getAge());
   }

   public static ResourceLocation getTextureLocation(int offset) {
      float ticksPerFrame = 1.0F;
      return TEXTURES[(int)((float)offset / ticksPerFrame) % TEXTURES.length];
   }
}
