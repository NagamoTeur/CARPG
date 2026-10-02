package com.hollingsworth.arsnouveau.client.gui;

import com.hollingsworth.arsnouveau.client.ClientInfo;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GlyphRecipeTooltip implements ClientTooltipComponent {
   public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/gui/container/bundle.png");
   private static final int MARGIN_Y = 4;
   private static final int BORDER_WIDTH = 1;
   private static final int TEX_SIZE = 128;
   private static final int SLOT_SIZE_X = 18;
   private static final int SLOT_SIZE_Y = 20;
   private final List<Ingredient> items;

   public GlyphRecipeTooltip(List<Ingredient> items) {
      this.items = items;
   }

   public int m_142103_() {
      return this.gridSizeY() * 20 + 2 + 4;
   }

   public int m_142069_(Font pFont) {
      return this.gridSizeX() * 18 + 2;
   }

   public void m_183452_(Font pFont, int pMouseX, int pMouseY, PoseStack pPoseStack, ItemRenderer pItemRenderer, int pBlitOffset) {
      if (!this.items.isEmpty()) {
         int i = this.gridSizeX();
         int j = this.gridSizeY();
         boolean overWEight = false;
         int k = 0;

         for (int l = 0; l < j; l++) {
            for (int i1 = 0; i1 < i; i1++) {
               int j1 = pMouseX + i1 * 18 + 1;
               int k1 = pMouseY + l * 20 + 1;
               this.renderSlot(j1, k1, k++, overWEight, pFont, pPoseStack, pItemRenderer, pBlitOffset);
            }
         }

         this.drawBorder(pMouseX, pMouseY, i, j, pPoseStack, pBlitOffset);
      }
   }

   private void renderSlot(int pX, int pY, int pItemIndex, boolean pIsBundleFull, Font pFont, PoseStack pPoseStack, ItemRenderer pItemRenderer, int pBlitOffset) {
      if (pItemIndex >= this.items.size()) {
         this.blit(pPoseStack, pX, pY, pBlitOffset, pIsBundleFull ? GlyphRecipeTooltip.Texture.BLOCKED_SLOT : GlyphRecipeTooltip.Texture.SLOT);
      } else {
         List<ItemStack> items = new ArrayList<>(List.of(this.items.get(pItemIndex).m_43908_()));
         ItemStack itemstack = items.get(ClientInfo.ticksInGame / 20 % items.size());
         this.blit(pPoseStack, pX, pY, pBlitOffset, GlyphRecipeTooltip.Texture.SLOT);
         pItemRenderer.m_174253_(itemstack, pX + 1, pY + 1, pItemIndex);
         pItemRenderer.m_115169_(pFont, itemstack, pX + 1, pY + 1);
      }
   }

   private void drawBorder(int pX, int pY, int pSlotWidth, int pSlotHeight, PoseStack pPoseStack, int pBlitOffset) {
      this.blit(pPoseStack, pX, pY, pBlitOffset, GlyphRecipeTooltip.Texture.BORDER_CORNER_TOP);
      this.blit(pPoseStack, pX + pSlotWidth * 18 + 1, pY, pBlitOffset, GlyphRecipeTooltip.Texture.BORDER_CORNER_TOP);

      for (int i = 0; i < pSlotWidth; i++) {
         this.blit(pPoseStack, pX + 1 + i * 18, pY, pBlitOffset, GlyphRecipeTooltip.Texture.BORDER_HORIZONTAL_TOP);
         this.blit(pPoseStack, pX + 1 + i * 18, pY + pSlotHeight * 20, pBlitOffset, GlyphRecipeTooltip.Texture.BORDER_HORIZONTAL_BOTTOM);
      }

      for (int j = 0; j < pSlotHeight; j++) {
         this.blit(pPoseStack, pX, pY + j * 20 + 1, pBlitOffset, GlyphRecipeTooltip.Texture.BORDER_VERTICAL);
         this.blit(pPoseStack, pX + pSlotWidth * 18 + 1, pY + j * 20 + 1, pBlitOffset, GlyphRecipeTooltip.Texture.BORDER_VERTICAL);
      }

      this.blit(pPoseStack, pX, pY + pSlotHeight * 20, pBlitOffset, GlyphRecipeTooltip.Texture.BORDER_CORNER_BOTTOM);
      this.blit(pPoseStack, pX + pSlotWidth * 18 + 1, pY + pSlotHeight * 20, pBlitOffset, GlyphRecipeTooltip.Texture.BORDER_CORNER_BOTTOM);
   }

   private void blit(PoseStack pPoseStack, int pX, int pY, int pBlitOffset, GlyphRecipeTooltip.Texture pTexture) {
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, TEXTURE_LOCATION);
      GuiComponent.m_93143_(pPoseStack, pX, pY, pBlitOffset, (float)pTexture.x, (float)pTexture.y, pTexture.w, pTexture.h, 128, 128);
   }

   private int gridSizeX() {
      return this.items.size() == 0 ? 0 : Math.min(3, this.items.size());
   }

   private int gridSizeY() {
      if (this.items.isEmpty()) {
         return 0;
      } else {
         return this.items.size() % 3 != 0 ? this.items.size() / 3 + 1 : this.items.size() / 3;
      }
   }

   @OnlyIn(Dist.CLIENT)
   static enum Texture {
      SLOT(0, 0, 18, 20),
      BLOCKED_SLOT(0, 40, 18, 20),
      BORDER_VERTICAL(0, 18, 1, 20),
      BORDER_HORIZONTAL_TOP(0, 20, 18, 1),
      BORDER_HORIZONTAL_BOTTOM(0, 60, 18, 1),
      BORDER_CORNER_TOP(0, 20, 1, 1),
      BORDER_CORNER_BOTTOM(0, 60, 1, 1);

      public final int x;
      public final int y;
      public final int w;
      public final int h;

      private Texture(int p_169928_, int p_169929_, int p_169930_, int p_169931_) {
         this.x = p_169928_;
         this.y = p_169929_;
         this.w = p_169930_;
         this.h = p_169931_;
      }
   }
}
