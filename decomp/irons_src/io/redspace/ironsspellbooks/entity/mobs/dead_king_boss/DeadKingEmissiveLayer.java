package io.redspace.ironsspellbooks.entity.mobs.dead_king_boss;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

public class DeadKingEmissiveLayer extends GeoLayerRenderer<AbstractSpellCastingMob> {
   public static final ResourceLocation TEXTURE_NORMAL = new ResourceLocation("irons_spellbooks", "textures/entity/dead_king/dead_king_glowing.png");
   public static final ResourceLocation TEXTURE_ENRAGED = new ResourceLocation("irons_spellbooks", "textures/entity/dead_king/dead_king_enraged_glowing.png");

   public DeadKingEmissiveLayer(GeoEntityRenderer renderer) {
      super(renderer);
   }

   public static ResourceLocation currentTexture(AbstractSpellCastingMob entity) {
      if (entity instanceof DeadKingBoss boss && boss.isPhase(DeadKingBoss.Phases.FinalPhase)) {
         return TEXTURE_ENRAGED;
      }

      return TEXTURE_NORMAL;
   }

   public static ResourceLocation currentModel(AbstractSpellCastingMob deadKingBoss) {
      return DeadKingModel.MODEL;
   }

   public static RenderType renderType(ResourceLocation resourceLocation) {
      return RenderType.m_110436_(resourceLocation, 0.0F, 0.0F);
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
      if (!(entityLivingBaseIn instanceof DeadKingCorpseEntity) && !entityLivingBaseIn.m_20145_()) {
         GeoModel model = this.getEntityModel().getModel(currentModel(entityLivingBaseIn));
         matrixStackIn.m_85836_();
         float scale = 0.7692308F;
         matrixStackIn.m_85841_(scale, scale, scale);
         RenderType renderType = renderType(currentTexture(entityLivingBaseIn));
         VertexConsumer vertexconsumer = bufferIn.m_6299_(renderType);
         this.getRenderer()
            .render(
               model,
               entityLivingBaseIn,
               partialTicks,
               renderType,
               matrixStackIn,
               bufferIn,
               vertexconsumer,
               15728640,
               OverlayTexture.f_118083_,
               1.0F,
               1.0F,
               1.0F,
               1.0F
            );
         matrixStackIn.m_85849_();
      }
   }
}
