package com.hollingsworth.arsnouveau.client.particle;

import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;

public class ParticleRenderTypes {
   static final ParticleRenderType EMBER_RENDER = new ParticleRenderType() {
      public void m_6505_(BufferBuilder buffer, TextureManager textureManager) {
         Minecraft.m_91087_().f_91063_.m_109154_().m_109896_();
         RenderSystem.m_69478_();
         RenderSystem.m_69481_();
         RenderSystem.m_157456_(0, TextureAtlas.f_118260_);
         RenderSystem.m_69482_();
         RenderSystem.m_69458_(false);
         RenderSystem.m_69405_(SourceFactor.SRC_ALPHA.f_84751_, DestFactor.ONE.f_84646_);
         buffer.m_166779_(Mode.QUADS, DefaultVertexFormat.f_85813_);
      }

      public void m_6294_(Tesselator tessellator) {
         tessellator.m_85914_();
      }

      @Override
      public String toString() {
         return "ars_nouveau:em_rend";
      }
   };
   static final ParticleRenderType EMBER_RENDER_NO_MASK = new ParticleRenderType() {
      public void m_6505_(BufferBuilder buffer, TextureManager textureManager) {
         RenderSystem.m_69465_();
         RenderSystem.m_69478_();
         RenderSystem.m_69481_();
         RenderSystem.m_157456_(0, TextureAtlas.f_118260_);
         RenderSystem.m_69458_(false);
         RenderSystem.m_69405_(SourceFactor.SRC_ALPHA.f_84751_, DestFactor.ONE.f_84646_);
         buffer.m_166779_(Mode.QUADS, DefaultVertexFormat.f_85813_);
      }

      public void m_6294_(Tesselator tessellator) {
         tessellator.m_85914_();
         RenderSystem.m_69482_();
      }

      @Override
      public String toString() {
         return "ars_nouveau:em_rend_no_mask";
      }
   };
}
