package com.github.L_Ender.cataclysm.client.render;

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
public class CMRenderTypes extends RenderType {
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

   public CMRenderTypes(
      String p_173178_, VertexFormat p_173179_, Mode p_173180_, int p_173181_, boolean p_173182_, boolean p_173183_, Runnable p_173184_, Runnable p_173185_
   ) {
      super(p_173178_, p_173179_, p_173180_, p_173181_, p_173182_, p_173183_, p_173184_, p_173185_);
   }

   public static RenderType getBright(ResourceLocation locationIn) {
      TextureStateShard renderstate$texturestate = new TextureStateShard(locationIn, false, false);
      return m_173215_(
         "bright",
         DefaultVertexFormat.f_85812_,
         Mode.QUADS,
         256,
         false,
         true,
         CompositeState.m_110628_()
            .m_173290_(renderstate$texturestate)
            .m_173292_(f_173065_)
            .m_110685_(f_110134_)
            .m_110661_(f_110110_)
            .m_110671_(f_110152_)
            .m_110677_(f_110154_)
            .m_110691_(false)
      );
   }

   public static RenderType getFlickering(ResourceLocation p_228652_0_, float lightLevel) {
      TextureStateShard renderstate$texturestate = new TextureStateShard(p_228652_0_, false, false);
      return m_173215_(
         "flickering",
         DefaultVertexFormat.f_85812_,
         Mode.QUADS,
         256,
         false,
         true,
         CompositeState.m_110628_()
            .m_173290_(renderstate$texturestate)
            .m_173292_(f_173065_)
            .m_110685_(f_110139_)
            .m_110661_(f_110110_)
            .m_110671_(f_110152_)
            .m_110677_(f_110154_)
            .m_110691_(false)
      );
   }

   public static RenderType getfullBright(ResourceLocation locationIn) {
      TextureStateShard renderstate$texturestate = new TextureStateShard(locationIn, false, false);
      return m_173215_(
         "full_bright",
         DefaultVertexFormat.f_85812_,
         Mode.QUADS,
         256,
         false,
         true,
         CompositeState.m_110628_()
            .m_173290_(renderstate$texturestate)
            .m_173292_(f_173065_)
            .m_110685_(f_110139_)
            .m_110661_(f_110110_)
            .m_110671_(f_110152_)
            .m_110677_(f_110154_)
            .m_110691_(false)
      );
   }

   public static RenderType getGlowingEffect(ResourceLocation locationIn) {
      TextureStateShard renderstate$texturestate = new TextureStateShard(locationIn, false, false);
      return m_173215_(
         "glow_effect",
         DefaultVertexFormat.f_85812_,
         Mode.QUADS,
         256,
         true,
         true,
         CompositeState.m_110628_()
            .m_173290_(renderstate$texturestate)
            .m_173292_(f_173068_)
            .m_110685_(f_110139_)
            .m_110661_(f_110110_)
            .m_110677_(f_110154_)
            .m_110687_(f_110115_)
            .m_110691_(false)
      );
   }

   public static RenderType getGhost(ResourceLocation texture) {
      CompositeState renderState = CompositeState.m_110628_()
         .m_173292_(f_173074_)
         .m_110661_(f_110110_)
         .m_173290_(new TextureStateShard(texture, false, false))
         .m_110685_(f_110139_)
         .m_110671_(f_110152_)
         .m_110677_(f_110154_)
         .m_110687_(f_110114_)
         .m_110663_(f_110113_)
         .m_110669_(f_110117_)
         .m_110691_(false);
      return m_173215_("ghost", DefaultVertexFormat.f_85812_, Mode.QUADS, 256, true, true, renderState);
   }

   public static RenderType CMEyes(ResourceLocation locationIn) {
      TextureStateShard renderstateshard$texturestateshard = new TextureStateShard(locationIn, false, false);
      return m_173215_(
         "cm_eyes",
         DefaultVertexFormat.f_85812_,
         Mode.QUADS,
         256,
         false,
         true,
         CompositeState.m_110628_()
            .m_173292_(f_173073_)
            .m_173290_(renderstateshard$texturestateshard)
            .m_110685_(f_110135_)
            .m_110661_(f_110110_)
            .m_110687_(f_110115_)
            .m_110691_(false)
      );
   }

   public static RenderType getPulse() {
      CompositeState renderState = CompositeState.m_110628_()
         .m_173292_(f_173074_)
         .m_110661_(f_110110_)
         .m_173290_(new TextureStateShard(new ResourceLocation("cataclysm:textures/particle/em_pulse.png"), true, true))
         .m_110685_(f_110139_)
         .m_110671_(f_110152_)
         .m_110677_(f_110154_)
         .m_110687_(f_110115_)
         .m_110663_(f_110113_)
         .m_110669_(f_110119_)
         .m_110691_(false);
      return m_173215_("em_pulse", DefaultVertexFormat.f_85812_, Mode.QUADS, 256, true, true, renderState);
   }

   public static RenderType getTrailEffect(ResourceLocation locationIn) {
      TextureStateShard renderstate$texturestate = new TextureStateShard(locationIn, false, false);
      return m_173215_(
         "trail_effect",
         DefaultVertexFormat.f_85812_,
         Mode.QUADS,
         256,
         true,
         true,
         CompositeState.m_110628_()
            .m_173290_(renderstate$texturestate)
            .m_173292_(f_173064_)
            .m_110685_(f_110139_)
            .m_110675_(f_110129_)
            .m_110661_(f_110110_)
            .m_110671_(f_110152_)
            .m_110677_(f_110154_)
            .m_110687_(f_110115_)
            .m_110691_(false)
      );
   }

   public static RenderType DragonDeath(ResourceLocation texture) {
      CompositeState rendertype$compositestate = CompositeState.m_110628_()
         .m_173292_(f_173072_)
         .m_173290_(new TextureStateShard(texture, false, false))
         .m_110661_(f_110110_)
         .m_110691_(true);
      return m_173215_("entity_alpha", DefaultVertexFormat.f_85812_, Mode.QUADS, 256, true, true, rendertype$compositestate);
   }

   public static RenderType getShockWave() {
      CompositeState renderState = CompositeState.m_110628_()
         .m_173292_(f_173074_)
         .m_110661_(f_110110_)
         .m_173290_(new TextureStateShard(new ResourceLocation("cataclysm:textures/particle/shock_wave.png"), true, true))
         .m_110685_(f_110139_)
         .m_110671_(f_110152_)
         .m_110677_(f_110154_)
         .m_110687_(f_110115_)
         .m_110663_(f_110113_)
         .m_110669_(f_110119_)
         .m_110691_(false);
      return m_173215_("shock_wave", DefaultVertexFormat.f_85812_, Mode.QUADS, 256, true, true, renderState);
   }
}
