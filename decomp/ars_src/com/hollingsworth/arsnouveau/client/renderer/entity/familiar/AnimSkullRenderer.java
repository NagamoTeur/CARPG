package com.hollingsworth.arsnouveau.client.renderer.entity.familiar;

import com.hollingsworth.arsnouveau.client.renderer.entity.AnimBlockRenderer;
import com.hollingsworth.arsnouveau.client.renderer.entity.EnchantedSkullRenderer;
import com.hollingsworth.arsnouveau.common.entity.AnimHeadSummon;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoBone;
import software.bernie.ars_nouveau.geckolib3.util.RenderUtils;

public class AnimSkullRenderer extends AnimBlockRenderer<AnimHeadSummon> {
   public AnimSkullRenderer(Context renderManager) {
      super(renderManager);
   }

   @Override
   public void renderRecursively(
      GeoBone bone, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha
   ) {
      if (bone.getName().equals("block")) {
         AnimHeadSummon animBlock = this.animatable;
         if (animBlock == null) {
            return;
         }

         poseStack.m_85836_();
         RenderUtils.translateToPivotPoint(poseStack, bone);
         poseStack.m_85845_(Vector3f.f_122225_.m_122240_(180.0F));
         poseStack.m_85837_(0.0, 0.2, 0.0);
         poseStack.m_85841_(1.4F, 1.4F, 1.4F);
         EnchantedSkullRenderer.renderSkull(animBlock.getStack(), poseStack, this.bufferSource, packedLight);
         poseStack.m_85849_();
         buffer = this.bufferSource.m_6299_(RenderType.m_110458_(TEXTURE));
      }

      super.renderRecursively(bone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
