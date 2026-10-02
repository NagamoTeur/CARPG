package com.bobmowzie.mowziesmobs.client.render;

import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderStateShard.TextureStateShard;
import net.minecraft.client.renderer.RenderType.CompositeState;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class MMRenderType extends RenderType {
   public static ParticleRenderType PARTICLE_SHEET_TRANSLUCENT_NO_DEPTH = new ParticleRenderType() {
      public void m_6505_(BufferBuilder p_217600_1_, TextureManager p_217600_2_) {
         RenderSystem.m_69458_(false);
         RenderSystem.m_69464_();
         RenderSystem.m_157456_(0, TextureAtlas.f_118260_);
         RenderSystem.m_69478_();
         RenderSystem.m_69408_(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         p_217600_1_.m_166779_(Mode.QUADS, DefaultVertexFormat.f_85813_);
      }

      public void m_6294_(Tesselator p_217599_1_) {
         p_217599_1_.m_85914_();
      }

      @Override
      public String toString() {
         return "PARTICLE_SHEET_TRANSLUCENT_NO_DEPTH";
      }
   };

   public MMRenderType(
      String nameIn,
      VertexFormat formatIn,
      Mode drawModeIn,
      int bufferSizeIn,
      boolean useDelegateIn,
      boolean needsSortingIn,
      Runnable setupTaskIn,
      Runnable clearTaskIn
   ) {
      super(nameIn, formatIn, drawModeIn, bufferSizeIn, useDelegateIn, needsSortingIn, setupTaskIn, clearTaskIn);
   }

   public static RenderType getGlowingEffect(ResourceLocation locationIn) {
      TextureStateShard shard = new TextureStateShard(locationIn, false, false);
      CompositeState rendertype$state = CompositeState.m_110628_()
         .m_173290_(shard)
         .m_173292_(f_173068_)
         .m_110685_(f_110139_)
         .m_110661_(f_110110_)
         .m_110677_(f_110154_)
         .m_110687_(f_110115_)
         .m_110691_(false);
      return m_173215_("glow_effect", DefaultVertexFormat.f_85812_, Mode.QUADS, 256, true, true, rendertype$state);
   }

   public static RenderType getSolarFlare(ResourceLocation locationIn) {
      TextureStateShard shard = new TextureStateShard(locationIn, false, false);
      CompositeState rendertype$state = CompositeState.m_110628_()
         .m_173290_(shard)
         .m_173292_(f_173068_)
         .m_110685_(f_110139_)
         .m_110663_(f_110111_)
         .m_110661_(f_110110_)
         .m_110677_(f_110155_)
         .m_110687_(f_110115_)
         .m_110691_(false);
      return m_173215_("solar_flare", DefaultVertexFormat.f_85812_, Mode.QUADS, 256, true, false, rendertype$state);
   }
}
