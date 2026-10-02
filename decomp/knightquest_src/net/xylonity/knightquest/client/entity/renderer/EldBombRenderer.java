package net.xylonity.knightquest.client.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.xylonity.knightquest.client.entity.model.EldBombModel;
import net.xylonity.knightquest.common.entity.entities.EldBombEntity;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EldBombRenderer extends GeoEntityRenderer<EldBombEntity> {
   private static final ResourceLocation DEFAULT_TEXTURE = new ResourceLocation("knightquest", "textures/entity/eldbomb.png");
   private static final ResourceLocation WHITE_TEXTURE = new ResourceLocation("knightquest", "textures/entity/eldbomb_white.png");

   public EldBombRenderer(Context renderManager) {
      super(renderManager, new EldBombModel());
   }

   @NotNull
   public ResourceLocation getTextureLocation(@NotNull EldBombEntity animatable) {
      if (animatable.getSwell() > 10) {
         return animatable.f_19797_ / 5 % 2 == 0 ? WHITE_TEXTURE : DEFAULT_TEXTURE;
      } else {
         return DEFAULT_TEXTURE;
      }
   }

   public void render(
      EldBombEntity entity, float entityYaw, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight
   ) {
      float f = entity.m_32320_(partialTick);
      float f1 = 1.0F + Mth.m_14031_(f * 100.0F) * f * 0.01F;
      f = Mth.m_14036_(f, 0.0F, 1.0F);
      f *= f;
      f *= f;
      float f2 = (1.0F + f * 0.4F) * f1;
      float f3 = (1.0F + f * 0.1F) / f1;
      poseStack.m_85841_(f2, f3, f2);
      super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
   }
}
