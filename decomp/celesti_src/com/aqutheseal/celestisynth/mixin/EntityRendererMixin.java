package com.aqutheseal.celestisynth.mixin;

import com.aqutheseal.celestisynth.api.mixin.LivingMixinSupport;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({EntityRenderer.class})
public abstract class EntityRendererMixin<T extends Entity> {
   private EntityRendererMixin() {
      throw new IllegalAccessError("Attempted to instantiate a Mixin Class!");
   }

   @Inject(
      method = {"render"},
      at = {@At("TAIL")}
   )
   public void celestisynth$render(
      T pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, CallbackInfo ci
   ) {
      if (pEntity instanceof LivingMixinSupport lms && lms.getPhantomTagger() != null) {
         renderPhantomFlame(pPoseStack, pBuffer, pEntity);
      }
   }

   private static void renderPhantomFlame(PoseStack pMatrixStack, MultiBufferSource pBuffer, Entity pEntity) {
      TextureAtlasSprite fireSprite0 = ModelBakery.f_119219_.m_119204_();
      TextureAtlasSprite fireSprite1 = ModelBakery.f_119220_.m_119204_();
      pMatrixStack.m_85836_();
      float offsetBBWidth = pEntity.m_20205_() * 1.4F;
      pMatrixStack.m_85841_(offsetBBWidth, offsetBBWidth, offsetBBWidth);
      float xFireVertex = 0.5F;
      float offsetBBHeight = pEntity.m_20206_() / offsetBBWidth;
      float yFireVertexOffset = 0.0F;
      pMatrixStack.m_85845_(Vector3f.f_122225_.m_122240_(-Minecraft.m_91087_().f_91063_.m_109153_().m_90590_()));
      pMatrixStack.m_85837_(0.0, 0.0, (double)(-0.3F + offsetBBHeight * 0.02F));
      float zFireVertex = 0.0F;
      int stackIdx = 0;
      VertexConsumer blockVc = pBuffer.m_6299_(RenderType.m_110470_(TextureAtlas.f_118259_));

      for (Pose lastPose = pMatrixStack.m_85850_(); offsetBBHeight > 0.0F; stackIdx++) {
         TextureAtlasSprite curFireSprite = stackIdx % 2 == 0 ? fireSprite0 : fireSprite1;
         float curFireSpriteU0 = curFireSprite.m_118409_();
         float curFireSpriteV0 = curFireSprite.m_118411_();
         float curFireSpriteU1 = curFireSprite.m_118410_();
         float curFireSpriteV1 = curFireSprite.m_118412_();
         if (stackIdx / 2 % 2 == 0) {
            float cachedU1 = curFireSpriteU1;
            curFireSpriteU1 = curFireSpriteU0;
            curFireSpriteU0 = cachedU1;
         }

         fireVertex(lastPose, blockVc, xFireVertex - 0.0F, 0.0F - yFireVertexOffset, zFireVertex, curFireSpriteU1, curFireSpriteV1);
         fireVertex(lastPose, blockVc, -xFireVertex - 0.0F, 0.0F - yFireVertexOffset, zFireVertex, curFireSpriteU0, curFireSpriteV1);
         fireVertex(lastPose, blockVc, -xFireVertex - 0.0F, 1.4F - yFireVertexOffset, zFireVertex, curFireSpriteU0, curFireSpriteV0);
         fireVertex(lastPose, blockVc, xFireVertex - 0.0F, 1.4F - yFireVertexOffset, zFireVertex, curFireSpriteU1, curFireSpriteV0);
         offsetBBHeight -= 0.45F;
         yFireVertexOffset -= 0.45F;
         xFireVertex *= 0.9F;
         zFireVertex += 0.03F;
      }

      pMatrixStack.m_85849_();
   }

   private static void fireVertex(Pose pMatrixEntry, VertexConsumer pBuffer, float pX, float pY, float pZ, float pTexU, float pTexV) {
      pBuffer.m_85982_(pMatrixEntry.m_85861_(), pX, pY, pZ)
         .m_6122_(0, 20, 0, 70)
         .m_7421_(pTexU, pTexV)
         .m_7122_(0, 10)
         .m_85969_(240)
         .m_85977_(pMatrixEntry.m_85864_(), 0.0F, 1.0F, 0.0F)
         .m_5752_();
   }
}
