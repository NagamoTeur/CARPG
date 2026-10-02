package com.hollingsworth.arsnouveau.client.gui.utils;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;

public final class TintedVertexConsumer implements VertexConsumer {
   private final VertexConsumer wrapped;
   private final float red;
   private final float green;
   private final float blue;
   private final float alpha;

   public TintedVertexConsumer(VertexConsumer wrapped, float red, float green, float blue, float alpha) {
      this.wrapped = wrapped;
      this.red = red;
      this.green = green;
      this.blue = blue;
      this.alpha = alpha;
   }

   public VertexConsumer m_5483_(double x, double y, double z) {
      return this.wrapped.m_5483_(x, y, z);
   }

   public VertexConsumer m_6122_(int red, int green, int blue, int alpha) {
      return this.wrapped
         .m_6122_((int)((float)red * this.red), (int)((float)green * this.green), (int)((float)blue * this.blue), (int)((float)alpha * this.alpha));
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

   public void m_7404_(int r, int g, int b, int a) {
      this.wrapped.m_7404_(r, g, b, a);
   }

   public void m_141991_() {
      this.wrapped.m_141991_();
   }

   public VertexFormat getVertexFormat() {
      return DefaultVertexFormat.f_85811_;
   }
}
