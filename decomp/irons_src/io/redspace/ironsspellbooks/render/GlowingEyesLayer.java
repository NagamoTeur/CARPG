package io.redspace.ironsspellbooks.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class GlowingEyesLayer {
   public static final ResourceLocation EYE_TEXTURE = new ResourceLocation("irons_spellbooks", "textures/entity/purple_eyes.png");
   public static final RenderType EYES = RenderType.m_110488_(EYE_TEXTURE);

   public static GlowingEyesLayer.EyeType getEyeType(LivingEntity entity) {
      if (ClientMagicData.getSyncedSpellData(entity).hasEffect(8L)) {
         return GlowingEyesLayer.EyeType.Abyssal;
      } else {
         return ClientMagicData.getSyncedSpellData(entity).hasEffect(128L) ? GlowingEyesLayer.EyeType.Planar_Sight : GlowingEyesLayer.EyeType.None;
      }
   }

   public static float getEyeScale(LivingEntity entity) {
      if (entity.m_6844_(EquipmentSlot.HEAD).m_150930_((Item)ItemRegistry.SHADOWWALKER_HELMET.get())) {
         return GlowingEyesLayer.EyeType.Ender_Armor.scale;
      } else if (ClientMagicData.getSyncedSpellData(entity).hasEffect(8L)) {
         return GlowingEyesLayer.EyeType.Abyssal.scale;
      } else {
         return ClientMagicData.getSyncedSpellData(entity).hasEffect(128L) ? GlowingEyesLayer.EyeType.Planar_Sight.scale : GlowingEyesLayer.EyeType.None.scale;
      }
   }

   public static enum EyeType {
      None(0.0F, 0.0F, 0.0F, 0.0F),
      Abyssal(1.0F, 1.0F, 1.0F, 1.0F),
      Planar_Sight(0.42F, 0.258F, 0.96F, 1.0F),
      Ender_Armor(0.816F, 0.0F, 1.0F, 1.15F);

      public final float r;
      public final float g;
      public final float b;
      public final float scale;

      private EyeType(float r, float g, float b, float scale) {
         this.r = r;
         this.g = g;
         this.b = b;
         this.scale = scale;
      }
   }

   public static class Geo extends GeoLayerRenderer<AbstractSpellCastingMob> {
      public Geo(IGeoRenderer entityRendererIn) {
         super(entityRendererIn);
      }

      public RenderType getRenderType(ResourceLocation textureLocation) {
         return GlowingEyesLayer.EYES;
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
         GlowingEyesLayer.EyeType eye = GlowingEyesLayer.getEyeType(abstractSpellCastingMob);
         if (eye != GlowingEyesLayer.EyeType.None) {
            GeoModel model = this.entityRenderer
               .getGeoModelProvider()
               .getModel(this.entityRenderer.getGeoModelProvider().getModelResource(abstractSpellCastingMob));
            model.getBone("head")
               .ifPresent(
                  headBone -> {
                     float scale = GlowingEyesLayer.getEyeScale(abstractSpellCastingMob);
                     headBone.setScale(scale, scale, scale);
                     this.renderModel(
                        this.getEntityModel(),
                        GlowingEyesLayer.EYE_TEXTURE,
                        poseStack,
                        multiBufferSource,
                        packedLightIn,
                        abstractSpellCastingMob,
                        partialTicks,
                        eye.r,
                        eye.g,
                        eye.b
                     );
                  }
               );
         }
      }
   }

   public static class Vanilla<T extends LivingEntity, M extends HumanoidModel<T>> extends EyesLayer<T, M> {
      public Vanilla(RenderLayerParent pRenderer) {
         super(pRenderer);
      }

      public RenderType m_5708_() {
         return GlowingEyesLayer.EYES;
      }

      public void render(
         PoseStack poseStack,
         MultiBufferSource multiBufferSource,
         int pPackedLight,
         T livingEntity,
         float limbSwing,
         float limbSwingAmount,
         float partialTicks,
         float ageInTicks,
         float netHeadYaw,
         float headPitch
      ) {
         GlowingEyesLayer.EyeType eye = GlowingEyesLayer.getEyeType(livingEntity);
         if (eye != GlowingEyesLayer.EyeType.None) {
            VertexConsumer vertexconsumer = multiBufferSource.m_6299_(this.m_5708_());
            float scale = GlowingEyesLayer.getEyeScale(livingEntity);
            poseStack.m_85841_(scale, scale, scale);
            ((HumanoidModel)this.m_117386_()).m_7695_(poseStack, vertexconsumer, 15728640, OverlayTexture.f_118083_, eye.r, eye.g, eye.b, 1.0F);
         }
      }
   }
}
