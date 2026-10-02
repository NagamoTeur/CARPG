package shadows.apotheosis.adventure.client;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;

public class GhostVertexBuilder implements VertexConsumer {
   private final VertexConsumer wrapped;
   private final int alpha;

   public GhostVertexBuilder(VertexConsumer wrapped, int alpha) {
      this.wrapped = wrapped;
      this.alpha = alpha;
   }

   public VertexConsumer m_5483_(double x, double y, double z) {
      return this.wrapped.m_5483_(x, y, z);
   }

   public VertexConsumer m_6122_(int red, int green, int blue, int alpha) {
      return this.wrapped.m_6122_(red, green, blue, alpha * this.alpha / 255);
   }

   public VertexConsumer m_7421_(float u, float v) {
      return this.wrapped.m_7421_(u, v);
   }

   public VertexConsumer m_7122_(int u, int v) {
      return this.wrapped.m_7122_(u, v);
   }

   public VertexConsumer m_7120_(int u, int v) {
      return this.wrapped.m_7120_(u, v);
   }

   public VertexConsumer m_5601_(float x, float y, float z) {
      return this.wrapped.m_5601_(x, y, z);
   }

   public void m_5752_() {
      this.wrapped.m_5752_();
   }

   public void m_7404_(int pRed, int pGreen, int pBlue, int pAlpha) {
   }

   public void m_141991_() {
   }

   public static class GhostBufferSource implements MultiBufferSource {
      private final MultiBufferSource wrapped;

      public GhostBufferSource(MultiBufferSource wrapped) {
         this.wrapped = wrapped;
      }

      public VertexConsumer m_6299_(RenderType type) {
         return new GhostVertexBuilder(this.wrapped.m_6299_(type), 153);
      }
   }
}
