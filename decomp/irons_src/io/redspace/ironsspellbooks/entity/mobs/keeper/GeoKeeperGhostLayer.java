package io.redspace.ironsspellbooks.entity.mobs.keeper;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

@OnlyIn(Dist.CLIENT)
public class GeoKeeperGhostLayer extends GeoLayerRenderer<AbstractSpellCastingMob> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("irons_spellbooks", "textures/entity/keeper/keeper_ghost.png");

   public GeoKeeperGhostLayer(IGeoRenderer entityRendererIn) {
      super(entityRendererIn);
   }

   public void render(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      AbstractSpellCastingMob entityLivingBaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      int hurtTime = entityLivingBaseIn.f_20916_;
      if (hurtTime > 0) {
         float alpha = (float)hurtTime / (float)entityLivingBaseIn.f_20917_;
         float f = ((float)entityLivingBaseIn.f_19797_ + partialTicks) * 0.6F;
         RenderType renderType = RenderType.m_110436_(TEXTURE, f * 0.02F % 1.0F, f * 0.01F % 1.0F);
         VertexConsumer vertexconsumer = bufferIn.m_6299_(renderType);
         matrixStackIn.m_85836_();
         GeoModel model = this.getEntityModel().getModel(KeeperModel.modelResource);
         float scale = 0.7692308F;
         matrixStackIn.m_85841_(scale, scale, scale);
         model.getBone("body").ifPresent(rootBone -> rootBone.childBones.forEach(bone -> {
               if (bone.getName().equals("head")) {
                  bone.setScale(0.65F, 0.65F, 0.65F);
               } else {
                  bone.setScale(0.95F, 0.99F, 0.95F);
               }
            }));
         this.getRenderer()
            .render(
               model,
               entityLivingBaseIn,
               partialTicks,
               renderType,
               matrixStackIn,
               bufferIn,
               vertexconsumer,
               15728880,
               OverlayTexture.f_118083_,
               0.15F * alpha,
               0.02F * alpha,
               0.0F * alpha,
               1.0F
            );
         model.getBone("body").ifPresent(rootBone -> rootBone.childBones.forEach(bone -> bone.setScale(1.0F, 1.0F, 1.0F)));
         matrixStackIn.m_85849_();
      }
   }
}
