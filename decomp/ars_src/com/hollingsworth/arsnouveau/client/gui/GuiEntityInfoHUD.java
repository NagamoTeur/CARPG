package com.hollingsworth.arsnouveau.client.gui;

import com.hollingsworth.arsnouveau.api.client.ITooltipProvider;
import com.hollingsworth.arsnouveau.common.items.ItemScroll;
import com.hollingsworth.arsnouveau.setup.Config;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.math.Matrix4f;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.RenderTooltipEvent.Pre;
import net.minecraftforge.client.gui.ScreenUtils;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;

public class GuiEntityInfoHUD {
   public static final IGuiOverlay OVERLAY = GuiEntityInfoHUD::renderOverlay;
   public static int hoverTicks = 0;
   public static Object lastHovered = null;
   public static final Color VANILLA_TOOLTIP_BORDER_1 = new Color(1347420415, true);
   public static final Color VANILLA_TOOLTIP_BORDER_2 = new Color(1344798847, true);
   public static final Color VANILLA_TOOLTIP_BACKGROUND = new Color(-267386864, true);

   public static void renderOverlay(ForgeGui gui, PoseStack poseStack, float partialTicks, int width, int height) {
      Minecraft mc = Minecraft.m_91087_();
      if (!mc.f_91066_.f_92062_ && mc.f_91072_.m_105295_() != GameType.SPECTATOR) {
         HitResult objectMouseOver = mc.f_91077_;
         List<Component> tooltip = new ArrayList<>();
         Object hovering = null;
         if (objectMouseOver instanceof BlockHitResult hitResult) {
            hovering = hitResult.m_82425_();
            if (mc.f_91073_.m_7702_(hitResult.m_82425_()) instanceof ITooltipProvider iTooltipProvider) {
               iTooltipProvider.getTooltip(tooltip);
            }
         } else if (objectMouseOver instanceof EntityHitResult result) {
            if (result.m_82443_() instanceof ITooltipProvider iTooltipProvider) {
               iTooltipProvider.getTooltip(tooltip);
            }

            if (result.m_82443_() instanceof ItemFrame frame) {
               ItemScroll.ItemScrollData data = new ItemScroll.ItemScrollData(frame.m_31822_());

               for (ItemStack i : data.getItems()) {
                  tooltip.add(i.m_41786_());
               }
            }

            hovering = result.m_82443_();
         }

         if (hovering == null || lastHovered != null && !lastHovered.equals(hovering)) {
            lastHovered = null;
            hoverTicks = 0;
         }

         if (lastHovered != null && !lastHovered.equals(hovering)) {
            hoverTicks = 0;
         } else {
            hoverTicks++;
         }

         lastHovered = hovering;
         if (!tooltip.isEmpty()) {
            poseStack.m_85836_();
            int tooltipTextWidth = 0;

            for (FormattedText textLine : tooltip) {
               int textLineWidth = mc.f_91062_.m_92852_(textLine);
               if (textLineWidth > tooltipTextWidth) {
                  tooltipTextWidth = textLineWidth;
               }
            }

            int tooltipHeight = 8;
            if (tooltip.size() > 1) {
               tooltipHeight += 2;
               tooltipHeight += (tooltip.size() - 1) * 10;
            }

            int xOffset = (Integer)Config.TOOLTIP_X_OFFSET.get();
            int posX = width / 2 + xOffset;
            int posY = height / 2 + (Integer)Config.TOOLTIP_Y_OFFSET.get();
            posX = Math.min(posX, width - tooltipTextWidth - 20);
            posY = Math.min(posY, height - tooltipHeight - 20);
            float fade = Mth.m_14036_(((float)hoverTicks + partialTicks) / 12.0F, 0.0F, 1.0F);
            Color colorBackground = VANILLA_TOOLTIP_BACKGROUND.scaleAlpha(0.75F);
            Color colorBorderTop = VANILLA_TOOLTIP_BORDER_1;
            Color colorBorderBot = VANILLA_TOOLTIP_BORDER_2;
            if (fade < 1.0F) {
               poseStack.m_85837_((double)((1.0F - fade) * Math.signum((float)xOffset + 0.5F) * 4.0F), 0.0, 0.0);
               colorBackground.scaleAlpha(fade);
               colorBorderTop.scaleAlpha(fade);
               colorBorderBot.scaleAlpha(fade);
            }

            drawHoveringText(
               ItemStack.f_41583_,
               poseStack,
               tooltip,
               posX,
               posY,
               width,
               height,
               -1,
               colorBackground.getRGB(),
               colorBorderTop.getRGB(),
               colorBorderBot.getRGB(),
               mc.f_91062_
            );
            poseStack.m_85849_();
         }
      }
   }

   public static void drawHoveringText(
      PoseStack mStack, List<? extends FormattedText> textLines, int mouseX, int mouseY, int screenWidth, int screenHeight, int maxTextWidth, Font font
   ) {
      drawHoveringText(mStack, textLines, mouseX, mouseY, screenWidth, screenHeight, maxTextWidth, -267386864, 1347420415, 1344798847, font);
   }

   public static void drawHoveringText(
      PoseStack mStack,
      List<? extends FormattedText> textLines,
      int mouseX,
      int mouseY,
      int screenWidth,
      int screenHeight,
      int maxTextWidth,
      int backgroundColor,
      int borderColorStart,
      int borderColorEnd,
      Font font
   ) {
      drawHoveringText(
         ItemStack.f_41583_,
         mStack,
         textLines,
         mouseX,
         mouseY,
         screenWidth,
         screenHeight,
         maxTextWidth,
         backgroundColor,
         borderColorStart,
         borderColorEnd,
         font
      );
   }

   public static void drawHoveringText(
      @NotNull ItemStack stack,
      PoseStack mStack,
      List<? extends FormattedText> textLines,
      int mouseX,
      int mouseY,
      int screenWidth,
      int screenHeight,
      int maxTextWidth,
      Font font
   ) {
      drawHoveringText(stack, mStack, textLines, mouseX, mouseY, screenWidth, screenHeight, maxTextWidth, -267386864, 1347420415, 1344798847, font);
   }

   public static void drawHoveringText(
      @NotNull ItemStack stack,
      PoseStack pStack,
      List<? extends FormattedText> textLines,
      int mouseX,
      int mouseY,
      int screenWidth,
      int screenHeight,
      int maxTextWidth,
      int backgroundColor,
      int borderColorStart,
      int borderColorEnd,
      Font font
   ) {
      if (!textLines.isEmpty()) {
         List<ClientTooltipComponent> list = ForgeHooksClient.gatherTooltipComponents(
            stack, textLines, stack.m_150921_(), mouseX, screenWidth, screenHeight, font, font
         );
         Pre event = new Pre(stack, pStack, mouseX, mouseY, screenWidth, screenHeight, font, list);
         if (!MinecraftForge.EVENT_BUS.post(event)) {
            mouseX = event.getX();
            mouseY = event.getY();
            screenWidth = event.getScreenWidth();
            screenHeight = event.getScreenHeight();
            font = event.getFont();
            RenderSystem.m_69465_();
            int tooltipTextWidth = 0;

            for (FormattedText textLine : textLines) {
               int textLineWidth = font.m_92852_(textLine);
               if (textLineWidth > tooltipTextWidth) {
                  tooltipTextWidth = textLineWidth;
               }
            }

            boolean needsWrap = false;
            int titleLinesCount = 1;
            int tooltipX = mouseX + 12;
            if (tooltipX + tooltipTextWidth + 4 > screenWidth) {
               tooltipX = mouseX - 16 - tooltipTextWidth;
               if (tooltipX < 4) {
                  if (mouseX > screenWidth / 2) {
                     tooltipTextWidth = mouseX - 12 - 8;
                  } else {
                     tooltipTextWidth = screenWidth - 16 - mouseX;
                  }

                  needsWrap = true;
               }
            }

            if (maxTextWidth > 0 && tooltipTextWidth > maxTextWidth) {
               tooltipTextWidth = maxTextWidth;
               needsWrap = true;
            }

            if (needsWrap) {
               int wrappedTooltipWidth = 0;
               List<FormattedText> wrappedTextLines = new ArrayList<>();

               for (int i = 0; i < textLines.size(); i++) {
                  FormattedText textLinex = textLines.get(i);
                  List<FormattedText> wrappedLine = font.m_92865_().m_92414_(textLinex, tooltipTextWidth, Style.f_131099_);
                  if (i == 0) {
                     titleLinesCount = wrappedLine.size();
                  }

                  for (FormattedText line : wrappedLine) {
                     int lineWidth = font.m_92852_(line);
                     if (lineWidth > wrappedTooltipWidth) {
                        wrappedTooltipWidth = lineWidth;
                     }

                     wrappedTextLines.add(line);
                  }
               }

               tooltipTextWidth = wrappedTooltipWidth;
               textLines = wrappedTextLines;
               if (mouseX > screenWidth / 2) {
                  tooltipX = mouseX - 16 - wrappedTooltipWidth;
               } else {
                  tooltipX = mouseX + 12;
               }
            }

            int tooltipY = mouseY - 12;
            int tooltipHeight = 8;
            if (textLines.size() > 1) {
               tooltipHeight += (textLines.size() - 1) * 10;
               if (textLines.size() > titleLinesCount) {
                  tooltipHeight += 2;
               }
            }

            if (tooltipY < 4) {
               tooltipY = 4;
            } else if (tooltipY + tooltipHeight + 4 > screenHeight) {
               tooltipY = screenHeight - tooltipHeight - 4;
            }

            int zLevel = 400;
            net.minecraftforge.client.event.RenderTooltipEvent.Color colorEvent = new net.minecraftforge.client.event.RenderTooltipEvent.Color(
               stack, pStack, tooltipX, tooltipY, font, backgroundColor, borderColorStart, borderColorEnd, list
            );
            MinecraftForge.EVENT_BUS.post(colorEvent);
            backgroundColor = colorEvent.getBackgroundStart();
            borderColorStart = colorEvent.getBorderStart();
            borderColorEnd = colorEvent.getBorderEnd();
            pStack.m_85836_();
            Matrix4f mat = pStack.m_85850_().m_85861_();
            ScreenUtils.drawGradientRect(mat, 400, tooltipX - 3, tooltipY - 4, tooltipX + tooltipTextWidth + 3, tooltipY - 3, backgroundColor, backgroundColor);
            ScreenUtils.drawGradientRect(
               mat,
               400,
               tooltipX - 3,
               tooltipY + tooltipHeight + 3,
               tooltipX + tooltipTextWidth + 3,
               tooltipY + tooltipHeight + 4,
               backgroundColor,
               backgroundColor
            );
            ScreenUtils.drawGradientRect(
               mat, 400, tooltipX - 3, tooltipY - 3, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor
            );
            ScreenUtils.drawGradientRect(mat, 400, tooltipX - 4, tooltipY - 3, tooltipX - 3, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
            ScreenUtils.drawGradientRect(
               mat,
               400,
               tooltipX + tooltipTextWidth + 3,
               tooltipY - 3,
               tooltipX + tooltipTextWidth + 4,
               tooltipY + tooltipHeight + 3,
               backgroundColor,
               backgroundColor
            );
            ScreenUtils.drawGradientRect(
               mat, 400, tooltipX - 3, tooltipY - 3 + 1, tooltipX - 3 + 1, tooltipY + tooltipHeight + 3 - 1, borderColorStart, borderColorEnd
            );
            ScreenUtils.drawGradientRect(
               mat,
               400,
               tooltipX + tooltipTextWidth + 2,
               tooltipY - 3 + 1,
               tooltipX + tooltipTextWidth + 3,
               tooltipY + tooltipHeight + 3 - 1,
               borderColorStart,
               borderColorEnd
            );
            ScreenUtils.drawGradientRect(
               mat, 400, tooltipX - 3, tooltipY - 3, tooltipX + tooltipTextWidth + 3, tooltipY - 3 + 1, borderColorStart, borderColorStart
            );
            ScreenUtils.drawGradientRect(
               mat,
               400,
               tooltipX - 3,
               tooltipY + tooltipHeight + 2,
               tooltipX + tooltipTextWidth + 3,
               tooltipY + tooltipHeight + 3,
               borderColorEnd,
               borderColorEnd
            );
            BufferSource renderType = MultiBufferSource.m_109898_(Tesselator.m_85913_().m_85915_());
            pStack.m_85837_(0.0, 0.0, 400.0);

            for (int lineNumber = 0; lineNumber < list.size(); lineNumber++) {
               ClientTooltipComponent line = list.get(lineNumber);
               if (line != null) {
                  line.m_142440_(font, tooltipX, tooltipY, mat, renderType);
               }

               if (lineNumber + 1 == titleLinesCount) {
                  tooltipY += 2;
               }

               tooltipY += 10;
            }

            renderType.m_109911_();
            pStack.m_85849_();
            RenderSystem.m_69482_();
         }
      }
   }
}
