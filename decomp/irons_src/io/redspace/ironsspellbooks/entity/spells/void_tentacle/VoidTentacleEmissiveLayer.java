package io.redspace.ironsspellbooks.entity.spells.void_tentacle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

@OnlyIn(Dist.CLIENT)
public class VoidTentacleEmissiveLayer extends GeoLayerRenderer<VoidTentacle> {
   public static final ResourceLocation TEXTURE = IronsSpellbooks.id("textures/entity/void_tentacle/void_tentacle_emissive.png");

   public VoidTentacleEmissiveLayer(IGeoRenderer entityRendererIn) {
      super(entityRendererIn);
   }

   public void render(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      VoidTentacle entityLivingBaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      RenderType renderType = RenderType.m_110488_(TEXTURE);
      VertexConsumer vertexconsumer = bufferIn.m_6299_(renderType);
      matrixStackIn.m_85836_();
      float f = Mth.m_14031_(
               (float)(
                  ((double)((float)entityLivingBaseIn.f_19797_ + partialTicks) + (entityLivingBaseIn.m_20185_() + entityLivingBaseIn.m_20189_()) * 500.0)
                     * 0.15F
               )
            )
            * 0.5F
         + 0.5F;
      GeoModel model = this.getEntityModel().getModel(VoidTentacleModel.modelResource);
      this.getRenderer()
         .render(
            model, entityLivingBaseIn, partialTicks, renderType, matrixStackIn, bufferIn, vertexconsumer, 15728880, OverlayTexture.f_118083_, f, f, f, 1.0F
         );
      matrixStackIn.m_85849_();
   }
}
