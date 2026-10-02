package io.redspace.ironsspellbooks.gui.overlays;

import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public class ScreenEffectsOverlay extends GuiComponent {
   public static final ResourceLocation MAGIC_AURA_TEXTURE = new ResourceLocation("irons_spellbooks", "textures/gui/overlays/enchanted_ward_vignette.png");
   public static final ResourceLocation HEARTSTOP_TEXTURE = new ResourceLocation("irons_spellbooks", "textures/gui/overlays/heartstop.png");

   public static void render(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
      Player player = Minecraft.m_91087_().f_91074_;
      if (player != null) {
         if (player.m_21023_((MobEffect)MobEffectRegistry.HEARTSTOP.get())) {
            setupRenderer(1.0F, 0.0F, 0.0F, 0.25F, HEARTSTOP_TEXTURE);
            renderOverlay(HEARTSTOP_TEXTURE, 0.5F, 1.0F, 1.0F, 0.5F, screenWidth, screenHeight);
         }
      }
   }

   private static void setupRenderer(float r, float g, float b, float a, ResourceLocation texture) {
      RenderSystem.m_69465_();
      RenderSystem.m_69458_(false);
      RenderSystem.m_69416_(SourceFactor.ZERO, DestFactor.ONE_MINUS_SRC_COLOR, SourceFactor.ONE, DestFactor.ZERO);
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(r, b, g, a);
      RenderSystem.m_157456_(0, texture);
   }

   private static void renderOverlay(ResourceLocation texture, float r, float g, float b, float a, int screenWidth, int screenHeight) {
      RenderSystem.m_69465_();
      RenderSystem.m_69458_(false);
      RenderSystem.m_69416_(SourceFactor.ZERO, DestFactor.ONE_MINUS_SRC_COLOR, SourceFactor.ONE, DestFactor.ZERO);
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157456_(0, texture);
      RenderSystem.m_157429_(r, g, b, a);
      Tesselator tesselator = Tesselator.m_85913_();
      BufferBuilder bufferbuilder = tesselator.m_85915_();
      bufferbuilder.m_166779_(Mode.QUADS, DefaultVertexFormat.f_85817_);
      bufferbuilder.m_5483_(0.0, (double)screenHeight, -90.0).m_7421_(0.0F, 1.0F).m_5752_();
      bufferbuilder.m_5483_((double)screenWidth, (double)screenHeight, -90.0).m_7421_(1.0F, 1.0F).m_5752_();
      bufferbuilder.m_5483_((double)screenWidth, 0.0, -90.0).m_7421_(1.0F, 0.0F).m_5752_();
      bufferbuilder.m_5483_(0.0, 0.0, -90.0).m_7421_(0.0F, 0.0F).m_5752_();
      tesselator.m_85914_();
      RenderSystem.m_69458_(true);
      RenderSystem.m_69482_();
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_69453_();
   }
}
