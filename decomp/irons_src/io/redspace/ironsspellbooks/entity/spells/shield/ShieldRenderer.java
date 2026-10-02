package io.redspace.ironsspellbooks.entity.spells.shield;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.entity.spells.magic_arrow.MagicArrowRenderer;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;

public class ShieldRenderer extends EntityRenderer<ShieldEntity> implements RenderLayerParent<ShieldEntity, ShieldModel> {
   public static ResourceLocation SPECTRAL_OVERLAY_TEXTURE = IronsSpellbooks.id("textures/entity/shield/shield_overlay.png");
   private static ResourceLocation SIGIL_TEXTURE = IronsSpellbooks.id("textures/block/scroll_forge_sigil.png");
   private final ShieldModel model;
   protected final List<RenderLayer<ShieldEntity, ShieldModel>> layers = new ArrayList<>();

   public ShieldRenderer(Context context) {
      super(context);
      this.model = new ShieldModel(context.m_174023_(ShieldModel.LAYER_LOCATION));
      this.layers.add(new ShieldTrimLayer(this, context));
   }

   public void render(ShieldEntity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
      poseStack.m_85836_();
      Pose pose = poseStack.m_85850_();
      Matrix4f poseMatrix = pose.m_85861_();
      Matrix3f normalMatrix = pose.m_85864_();
      poseStack.m_85845_(Vector3f.f_122225_.m_122240_(-entity.m_146908_()));
      poseStack.m_85845_(Vector3f.f_122223_.m_122240_(entity.m_146909_()));
      Vec2 offset = getEnergySwirlOffset(entity, partialTicks);
      VertexConsumer consumer = bufferSource.m_6299_(
         MagicArrowRenderer.CustomRenderType.magicSwirl(this.getTextureLocation(entity), offset.f_82470_, offset.f_82471_)
      );
      float width = (float)entity.width * 0.65F;
      poseStack.m_85841_(width, width, width);
      RenderSystem.m_69461_();
      this.model.m_7695_(poseStack, consumer, 15728880, OverlayTexture.f_118083_, 0.65F, 0.65F, 0.65F, 1.0F);

      for (RenderLayer<ShieldEntity, ShieldModel> layer : this.layers) {
         layer.m_6494_(poseStack, bufferSource, light, entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      }

      poseStack.m_85849_();
      super.m_7392_(entity, yaw, partialTicks, poseStack, bufferSource, light);
   }

   private static float shittyNoise(float f) {
      return (float)(Math.sin((double)(f / 4.0F)) + 2.0 * Math.sin((double)(f / 3.0F)) + 3.0 * Math.sin((double)(f / 2.0F)) + 4.0 * Math.sin((double)f))
         * 0.25F;
   }

   public static Vec2 getEnergySwirlOffset(ShieldEntity entity, float partialTicks, int offset) {
      float f = ((float)entity.f_19797_ + partialTicks) * 0.02F;
      return new Vec2(shittyNoise(1.2F * f + (float)offset), shittyNoise(f + 456.0F + (float)offset));
   }

   public static Vec2 getEnergySwirlOffset(ShieldEntity entity, float partialTicks) {
      return getEnergySwirlOffset(entity, partialTicks, 0);
   }

   public ShieldModel getModel() {
      return this.model;
   }

   public ResourceLocation getTextureLocation(ShieldEntity entity) {
      return SPECTRAL_OVERLAY_TEXTURE;
   }
}
