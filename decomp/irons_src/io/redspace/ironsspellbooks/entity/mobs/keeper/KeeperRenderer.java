package io.redspace.ironsspellbooks.entity.mobs.keeper;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMobRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class KeeperRenderer extends AbstractSpellCastingMobRenderer {
   public KeeperRenderer(Context context) {
      super(context, new KeeperModel());
      this.addLayer(new GeoKeeperGhostLayer(this));
      this.f_114477_ = 0.65F;
   }

   public void renderEarly(
      AbstractSpellCastingMob animatable,
      PoseStack poseStack,
      float partialTick,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      int packedLight,
      int packedOverlay,
      float red,
      float green,
      float blue,
      float partialTicks
   ) {
      poseStack.m_85841_(1.3F, 1.3F, 1.3F);
      super.renderEarly(animatable, poseStack, partialTick, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, partialTicks);
   }

   public RenderType getRenderType(
      AbstractSpellCastingMob animatable,
      float partialTick,
      PoseStack poseStack,
      @Nullable MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      int packedLight,
      ResourceLocation texture
   ) {
      return RenderType.m_110473_(texture);
   }

   public int getOverlay(AbstractSpellCastingMob entity, float u) {
      return OverlayTexture.m_118093_(OverlayTexture.m_118088_(u), OverlayTexture.m_118096_(entity.f_20919_ > 0));
   }
}
