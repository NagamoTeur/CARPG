package com.github.L_Ender.cataclysm.jei;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class AltarOfAmethystDrawable implements IDrawable {
   private static final ResourceLocation TEXTURE = new ResourceLocation("cataclysm", "textures/gui/altar_of_amethyst_jei.png");

   public int getWidth() {
      return 125;
   }

   public int getHeight() {
      return 59;
   }

   public void draw(PoseStack poseStack, int xOffset, int yOffset) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, TEXTURE);
      GuiComponent.m_93133_(poseStack, xOffset, yOffset, 0.0F, 0.0F, 125, 59, 256, 256);
   }
}
