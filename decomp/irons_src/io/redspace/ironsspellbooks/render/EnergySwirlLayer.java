package io.redspace.ironsspellbooks.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

@OnlyIn(Dist.CLIENT)
public class EnergySwirlLayer {
   public static final ResourceLocation EVASION_TEXTURE = new ResourceLocation("irons_spellbooks", "textures/entity/evasion.png");
   public static final ResourceLocation CHARGE_TEXTURE = new ResourceLocation("irons_spellbooks", "textures/entity/charged.png");

   private static RenderType getRenderType(ResourceLocation texture, float f) {
      return RenderType.m_110436_(texture, f * 0.02F % 1.0F, f * 0.01F % 1.0F);
   }

   private static boolean shouldRender(LivingEntity entity, Long shouldRenderFlag) {
      return ClientMagicData.getSyncedSpellData(entity).hasEffect(shouldRenderFlag);
   }

   public static class Geo extends GeoLayerRenderer<AbstractSpellCastingMob> {
      private final ResourceLocation TEXTURE;
      private final Long shouldRenderFlag;

      public Geo(IGeoRenderer entityRendererIn, ResourceLocation texture, Long shouldRenderFlag) {
         super(entityRendererIn);
         this.TEXTURE = texture;
         this.shouldRenderFlag = shouldRenderFlag;
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
         if (EnergySwirlLayer.shouldRender(entityLivingBaseIn, this.shouldRenderFlag)) {
            float f = (float)entityLivingBaseIn.f_19797_ + partialTicks;
            RenderType renderType = EnergySwirlLayer.getRenderType(this.TEXTURE, f);
            VertexConsumer vertexconsumer = bufferIn.m_6299_(renderType);
            matrixStackIn.m_85836_();
            GeoModel model = ((AnimatedGeoModel)this.getEntityModel()).getModel(AbstractSpellCastingMob.modelResource);
            model.getBone("body").ifPresent(rootBone -> rootBone.childBones.forEach(bone -> bone.setScale(1.1F, 1.1F, 1.1F)));
            this.getRenderer()
               .render(
                  model,
                  entityLivingBaseIn,
                  partialTicks,
                  renderType,
                  matrixStackIn,
                  bufferIn,
                  vertexconsumer,
                  packedLightIn,
                  OverlayTexture.f_118083_,
                  0.5F,
                  0.5F,
                  0.5F,
                  1.0F
               );
            model.getBone("body").ifPresent(rootBone -> rootBone.childBones.forEach(bone -> bone.setScale(1.0F, 1.0F, 1.0F)));
            matrixStackIn.m_85849_();
         }
      }
   }

   public static class Vanilla extends RenderLayer<Player, HumanoidModel<Player>> {
      public static ModelLayerLocation ENERGY_LAYER = new ModelLayerLocation(new ResourceLocation("irons_spellbooks", "energy_layer"), "main");
      private final HumanoidModel<Player> model = new HumanoidModel(Minecraft.m_91087_().m_167973_().m_171103_(ENERGY_LAYER));
      private final ResourceLocation TEXTURE;
      private final Long shouldRenderFlag;

      public Vanilla(RenderLayerParent pRenderer, ResourceLocation texture, Long shouldRenderFlag) {
         super(pRenderer);
         this.TEXTURE = texture;
         this.shouldRenderFlag = shouldRenderFlag;
      }

      public void render(
         PoseStack pMatrixStack,
         MultiBufferSource pBuffer,
         int pPackedLight,
         Player pLivingEntity,
         float pLimbSwing,
         float pLimbSwingAmount,
         float pPartialTicks,
         float pAgeInTicks,
         float pNetHeadYaw,
         float pHeadPitch
      ) {
         if (EnergySwirlLayer.shouldRender(pLivingEntity, this.shouldRenderFlag)) {
            float f = (float)pLivingEntity.f_19797_ + pPartialTicks;
            HumanoidModel<Player> entitymodel = this.model();
            entitymodel.m_6839_(pLivingEntity, pLimbSwing, pLimbSwingAmount, pPartialTicks);
            ((HumanoidModel)this.m_117386_()).m_102872_(entitymodel);
            VertexConsumer vertexconsumer = pBuffer.m_6299_(EnergySwirlLayer.getRenderType(this.TEXTURE, f));
            entitymodel.m_6973_(pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
            entitymodel.m_7695_(pMatrixStack, vertexconsumer, pPackedLight, OverlayTexture.f_118083_, 0.8F, 0.8F, 0.8F, 1.0F);
         }
      }

      protected HumanoidModel<Player> model() {
         return this.model;
      }

      protected boolean shouldRender(Player entity) {
         return true;
      }
   }
}
