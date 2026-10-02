package io.redspace.ironsspellbooks.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.capabilities.magic.MultiTargetEntityCastData;
import io.redspace.ironsspellbooks.capabilities.magic.RecastInstance;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class SpellTargetingLayer {
   public static final ResourceLocation TEXTURE = new ResourceLocation("irons_spellbooks", "textures/entity/target/heal.png");

   private static Vector3f getColor(String spellId) {
      return SpellRegistry.getSpell(spellId).getTargetingColor();
   }

   public static void renderTargetLayer(PoseStack poseStack, MultiBufferSource bufferSource, LivingEntity entity) {
      VertexConsumer consumer = bufferSource.m_6299_(RenderType.m_110436_(TEXTURE, 0.0F, 0.0F));
      AABB aabb = entity.m_20191_().m_82386_(-entity.m_20185_(), -entity.m_20186_(), -entity.m_20189_());
      float width = (float)aabb.m_82362_();
      float height = (float)aabb.m_82376_();
      float halfWidth = width * 0.55F;
      float magicYOffset = (float)(1.5 - (double)height);
      Vector3f color = null;
      if (ClientMagicData.getRecasts().hasRecastsActive()) {
         for (RecastInstance recastInstance : ClientMagicData.getRecasts().getActiveRecasts()) {
            if (recastInstance.getCastData() instanceof MultiTargetEntityCastData targetEntityCastData && targetEntityCastData.isTargeted(entity)) {
               color = getColor(recastInstance.getSpellId());
               break;
            }
         }
      }

      if (color == null) {
         color = getColor(ClientMagicData.getTargetingData().spellId);
      }

      color.m_122261_(0.4F);
      poseStack.m_85836_();
      poseStack.m_85837_(0.0, (double)magicYOffset, 0.0);
      Pose pose = poseStack.m_85850_();
      Matrix4f poseMatrix = pose.m_85861_();
      Matrix3f normalMatrix = pose.m_85864_();

      for (int i = 0; i < 4; i++) {
         consumer.m_85982_(poseMatrix, halfWidth, height, halfWidth)
            .m_85950_(color.m_122239_(), color.m_122260_(), color.m_122269_(), 1.0F)
            .m_7421_(0.0F, 1.0F)
            .m_86008_(OverlayTexture.f_118083_)
            .m_85969_(15728880)
            .m_85977_(normalMatrix, 0.0F, 1.0F, 0.0F)
            .m_5752_();
         consumer.m_85982_(poseMatrix, halfWidth, 0.0F, halfWidth)
            .m_85950_(color.m_122239_(), color.m_122260_(), color.m_122269_(), 1.0F)
            .m_7421_(0.0F, 0.0F)
            .m_86008_(OverlayTexture.f_118083_)
            .m_85969_(15728880)
            .m_85977_(normalMatrix, 0.0F, 1.0F, 0.0F)
            .m_5752_();
         consumer.m_85982_(poseMatrix, -halfWidth, 0.0F, halfWidth)
            .m_85950_(color.m_122239_(), color.m_122260_(), color.m_122269_(), 1.0F)
            .m_7421_(1.0F, 0.0F)
            .m_86008_(OverlayTexture.f_118083_)
            .m_85969_(15728880)
            .m_85977_(normalMatrix, 0.0F, 1.0F, 0.0F)
            .m_5752_();
         consumer.m_85982_(poseMatrix, -halfWidth, height, halfWidth)
            .m_85950_(color.m_122239_(), color.m_122260_(), color.m_122269_(), 1.0F)
            .m_7421_(1.0F, 1.0F)
            .m_86008_(OverlayTexture.f_118083_)
            .m_85969_(15728880)
            .m_85977_(normalMatrix, 0.0F, 1.0F, 0.0F)
            .m_5752_();
         poseStack.m_85845_(Vector3f.f_122225_.m_122240_(90.0F));
      }

      poseStack.m_85849_();
   }

   public static boolean shouldRender(LivingEntity entity) {
      if (ClientMagicData.getRecasts().hasRecastsActive()) {
         for (RecastInstance recastInstance : ClientMagicData.getRecasts().getActiveRecasts()) {
            if (recastInstance.getCastData() instanceof MultiTargetEntityCastData targetEntityCastData && targetEntityCastData.isTargeted(entity)) {
               return true;
            }
         }
      }

      return ClientMagicData.getTargetingData().isTargeted(entity);
   }

   public static class Geo extends GeoLayerRenderer<AbstractSpellCastingMob> {
      public Geo(IGeoRenderer entityRendererIn) {
         super(entityRendererIn);
      }

      public void render(
         PoseStack poseStack,
         MultiBufferSource multiBufferSource,
         int packedLightIn,
         AbstractSpellCastingMob abstractSpellCastingMob,
         float limbSwing,
         float limbSwingAmount,
         float partialTicks,
         float ageInTicks,
         float netHeadYaw,
         float headPitch
      ) {
         if (SpellTargetingLayer.shouldRender(abstractSpellCastingMob)) {
            poseStack.m_85836_();
            poseStack.m_85845_(Vector3f.f_122223_.m_122240_(180.0F));
            poseStack.m_85837_(0.0, -abstractSpellCastingMob.m_20191_().m_82376_() / 2.0, 0.0);
            SpellTargetingLayer.renderTargetLayer(poseStack, multiBufferSource, abstractSpellCastingMob);
            poseStack.m_85849_();
         }
      }
   }

   public static class Vanilla<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
      public Vanilla(RenderLayerParent<T, M> pRenderer) {
         super(pRenderer);
      }

      public void render(
         PoseStack poseStack,
         MultiBufferSource bufferSource,
         int pPackedLight,
         T entity,
         float pLimbSwing,
         float pLimbSwingAmount,
         float pPartialTick,
         float pAgeInTicks,
         float pNetHeadYaw,
         float pHeadPitch
      ) {
         if (SpellTargetingLayer.shouldRender(entity)) {
            SpellTargetingLayer.renderTargetLayer(poseStack, bufferSource, entity);
         }
      }
   }
}
