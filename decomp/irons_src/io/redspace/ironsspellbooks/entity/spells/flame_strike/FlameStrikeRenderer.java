package io.redspace.ironsspellbooks.entity.spells.flame_strike;

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

public class FlameStrikeRenderer extends EntityRenderer<FlameStrike> {
   private static final ResourceLocation[] TEXTURES = new ResourceLocation[]{
      IronsSpellbooks.id("textures/entity/flame_strike/flame_strike_1.png"),
      IronsSpellbooks.id("textures/entity/flame_strike/flame_strike_2.png"),
      IronsSpellbooks.id("textures/entity/flame_strike/flame_strike_3.png"),
      IronsSpellbooks.id("textures/entity/flame_strike/flame_strike_4.png")
   };

   public FlameStrikeRenderer(Context context) {
      super(context);
   }

   public void render(FlameStrike entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
      poseStack.m_85836_();
      Pose pose = poseStack.m_85850_();
      poseStack.m_85845_(Vector3f.f_122225_.m_122240_(90.0F - entity.m_146908_()));
      poseStack.m_85845_(Vector3f.f_122223_.m_122240_(-entity.m_146909_()));
      this.drawSlash(pose, entity, bufferSource, entity.m_20205_() * 1.5F, entity.isMirrored());
      poseStack.m_85849_();
      super.m_7392_(entity, yaw, partialTicks, poseStack, bufferSource, light);
   }

   private void drawSlash(Pose pose, FlameStrike entity, MultiBufferSource bufferSource, float width, boolean mirrored) {
      Matrix4f poseMatrix = pose.m_85861_();
      Matrix3f normalMatrix = pose.m_85864_();
      VertexConsumer consumer = bufferSource.m_6299_(RenderType.m_110458_(this.getTextureLocation(entity)));
      float halfWidth = width * 0.5F;
      float height = entity.m_20206_() * 0.5F;
      consumer.m_85982_(poseMatrix, -halfWidth, height, -halfWidth)
         .m_6122_(255, 255, 255, 255)
         .m_7421_(0.0F, mirrored ? 0.0F : 1.0F)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(15728880)
         .m_85977_(normalMatrix, 0.0F, 1.0F, 0.0F)
         .m_5752_();
      consumer.m_85982_(poseMatrix, halfWidth, height, -halfWidth)
         .m_6122_(255, 255, 255, 255)
         .m_7421_(1.0F, mirrored ? 0.0F : 1.0F)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(15728880)
         .m_85977_(normalMatrix, 0.0F, 1.0F, 0.0F)
         .m_5752_();
      consumer.m_85982_(poseMatrix, halfWidth, height, halfWidth)
         .m_6122_(255, 255, 255, 255)
         .m_7421_(1.0F, mirrored ? 1.0F : 0.0F)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(15728880)
         .m_85977_(normalMatrix, 0.0F, 1.0F, 0.0F)
         .m_5752_();
      consumer.m_85982_(poseMatrix, -halfWidth, height, halfWidth)
         .m_6122_(255, 255, 255, 255)
         .m_7421_(0.0F, mirrored ? 1.0F : 0.0F)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(15728880)
         .m_85977_(normalMatrix, 0.0F, 1.0F, 0.0F)
         .m_5752_();
   }

   public ResourceLocation getTextureLocation(FlameStrike entity) {
      int frame = entity.f_19797_ / 2 % TEXTURES.length;
      return TEXTURES[frame];
   }
}
