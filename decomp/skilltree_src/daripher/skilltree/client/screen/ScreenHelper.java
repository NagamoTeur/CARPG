package daripher.skilltree.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.math.Vector3f;
import daripher.skilltree.client.tooltip.TooltipHelper;
import daripher.skilltree.client.widget.skill.SkillButton;
import daripher.skilltree.client.widget.skill.SkillConnection;
import daripher.skilltree.skill.PassiveSkillTree;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ScreenHelper {
   public static void drawCenteredOutlinedText(PoseStack poseStack, String text, int x, int y, int color) {
      Font font = Minecraft.m_91087_().f_91062_;
      x -= font.m_92895_(text) / 2;
      font.m_92883_(poseStack, text, (float)(x + 1), (float)y, 0);
      font.m_92883_(poseStack, text, (float)(x - 1), (float)y, 0);
      font.m_92883_(poseStack, text, (float)x, (float)(y + 1), 0);
      font.m_92883_(poseStack, text, (float)x, (float)(y - 1), 0);
      font.m_92883_(poseStack, text, (float)x, (float)y, color);
   }

   public static void drawRectangle(PoseStack poseStack, int x, int y, int width, int height, int color) {
      GuiComponent.m_93172_(poseStack, x, y, x + width, y + 1, color);
      GuiComponent.m_93172_(poseStack, x, y + height - 1, x + width, y + height, color);
      GuiComponent.m_93172_(poseStack, x, y + 1, x + 1, y + height - 1, color);
      GuiComponent.m_93172_(poseStack, x + width - 1, y + 1, x + width, y + height - 1, color);
   }

   public static void prepareTextureRendering(ResourceLocation textureLocation) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157456_(0, textureLocation);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_69478_();
      RenderSystem.m_69453_();
      RenderSystem.m_69482_();
   }

   public static float getAngleBetweenButtons(Button button1, Button button2) {
      float x1 = (float)button1.f_93620_ + (float)button1.m_5711_() / 2.0F;
      float y1 = (float)button1.f_93621_ + (float)button1.m_93694_() / 2.0F;
      float x2 = (float)button2.f_93620_ + (float)button2.m_5711_() / 2.0F;
      float y2 = (float)button2.f_93621_ + (float)button2.m_93694_() / 2.0F;
      return (float)Mth.m_14136_((double)(y2 - y1), (double)(x2 - x1));
   }

   public static float getDistanceBetweenButtons(Button button1, Button button2) {
      float x1 = (float)button1.f_93620_ + (float)button1.m_5711_() / 2.0F;
      float y1 = (float)button1.f_93621_ + (float)button1.m_93694_() / 2.0F;
      float x2 = (float)button2.f_93620_ + (float)button2.m_5711_() / 2.0F;
      float y2 = (float)button2.f_93621_ + (float)button2.m_93694_() / 2.0F;
      return Mth.m_14116_((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
   }

   public static void renderSkillTooltip(PassiveSkillTree skillTree, SkillButton button, PoseStack poseStack, float x, float y, int width, int height) {
      Font font = Minecraft.m_91087_().f_91062_;
      int maxWidth = width - 10;
      List<MutableComponent> tooltip = new ArrayList<>();

      for (MutableComponent component : button.getTooltip(skillTree)) {
         if (font.m_92852_(component) > maxWidth) {
            tooltip.addAll(TooltipHelper.split(component, font, maxWidth));
         } else {
            tooltip.add(component);
         }
      }

      if (!tooltip.isEmpty()) {
         int tooltipWidth = 0;
         int tooltipHeight = tooltip.size() == 1 ? 8 : 10;

         for (MutableComponent componentx : tooltip) {
            int k = font.m_92852_(componentx);
            if (k > tooltipWidth) {
               tooltipWidth = k;
            }

            tooltipHeight += 9 + 2;
         }

         tooltipWidth += 42;
         float tooltipX = x + 12.0F;
         float tooltipY = y - 12.0F;
         if (tooltipX + (float)tooltipWidth > (float)width) {
            tooltipX -= (float)(28 + tooltipWidth);
         }

         if (tooltipY + (float)tooltipHeight + 6.0F > (float)height) {
            tooltipY = (float)(height - tooltipHeight - 6);
         }

         if (tooltipX < 5.0F) {
            tooltipX = 5.0F;
         }

         if (tooltipY < 5.0F) {
            tooltipY = 5.0F;
         }

         poseStack.m_85836_();
         poseStack.m_85837_((double)tooltipX, (double)tooltipY, 0.0);
         ItemRenderer itemRenderer = Minecraft.m_91087_().m_91291_();
         float zOffset = itemRenderer.f_115093_;
         itemRenderer.f_115093_ = 400.0F;
         GuiComponent.m_93172_(poseStack, 1, 4, tooltipWidth - 1, tooltipHeight + 4, -587202560);
         RenderSystem.m_69493_();
         BufferSource buffer = MultiBufferSource.m_109898_(Tesselator.m_85913_().m_85915_());
         poseStack.m_85837_(0.0, 0.0, 400.0);
         int textX = 5;
         int textY = 2;
         prepareTextureRendering(button.skill.getTooltipFrameTexture());
         GuiComponent.m_93133_(poseStack, -4, -4, 0.0F, 0.0F, 21, 20, 110, 20);
         GuiComponent.m_93133_(poseStack, tooltipWidth + 4 - 21, -4, -21.0F, 0.0F, 21, 20, 110, 20);
         int centerWidth = tooltipWidth + 8 - 42;
         int centerX = 17;

         while (centerWidth > 0) {
            int partWidth = Math.min(centerWidth, 68);
            GuiComponent.m_93133_(poseStack, centerX, -4, 21.0F, 0.0F, partWidth, 20, 110, 20);
            centerX += partWidth;
            centerWidth -= partWidth;
         }

         MutableComponent title = tooltip.remove(0);
         GuiComponent.m_93215_(poseStack, font, title, tooltipWidth / 2, textY, 16777215);
         textY += 19;

         for (MutableComponent componentx : tooltip) {
            font.m_92889_(poseStack, componentx, (float)textX, (float)textY, 16777215);
            textY += 9 + 2;
         }

         buffer.m_109911_();
         poseStack.m_85849_();
         itemRenderer.f_115093_ = zOffset;
      }
   }

   public static void renderGatewayConnection(
      PoseStack poseStack, double x, double y, SkillConnection connection, boolean highlighted, float zoom, float animation
   ) {
      prepareTextureRendering(new ResourceLocation("skilltree:textures/screen/long_connection.png"));
      poseStack.m_85836_();
      SkillButton button1 = connection.getFirstButton();
      SkillButton button2 = connection.getSecondButton();
      double connectionX = (double)(button1.f_93620_ + (float)button1.m_5711_() / 2.0F);
      double connectionY = (double)(button1.f_93621_ + (float)button1.m_93694_() / 2.0F);
      poseStack.m_85837_(connectionX + x, connectionY + y, 0.0);
      float rotation = getAngleBetweenButtons(button1, button2);
      poseStack.m_85845_(Vector3f.f_122227_.m_122270_(rotation));
      int length = (int)(getDistanceBetweenButtons(button1, button2) / zoom);
      poseStack.m_85841_(zoom, zoom, 1.0F);
      GuiComponent.m_93160_(poseStack, 0, -8, length, 6, -animation, highlighted ? 0.0F : 6.0F, length, 6, 30, 12);
      GuiComponent.m_93160_(poseStack, 0, 2, length, 6, animation, highlighted ? 0.0F : 6.0F, length, 6, -30, 12);
      poseStack.m_85849_();
   }

   public static void renderOneWayConnection(
      PoseStack poseStack, double x, double y, SkillConnection connection, boolean highlighted, float zoom, float animation
   ) {
      prepareTextureRendering(new ResourceLocation("skilltree:textures/screen/one_way_connection.png"));
      poseStack.m_85836_();
      SkillButton button1 = connection.getFirstButton();
      SkillButton button2 = connection.getSecondButton();
      double connectionX = (double)(button1.f_93620_ + (float)button1.m_5711_() / 2.0F);
      double connectionY = (double)(button1.f_93621_ + (float)button1.m_93694_() / 2.0F);
      poseStack.m_85837_(connectionX + x, connectionY + y, 0.0);
      float rotation = getAngleBetweenButtons(button1, button2);
      poseStack.m_85845_(Vector3f.f_122227_.m_122270_(rotation));
      int length = (int)(getDistanceBetweenButtons(button1, button2) / zoom);
      poseStack.m_85841_(zoom, zoom, 1.0F);
      GuiComponent.m_93160_(poseStack, 0, -3, length, 6, -animation, highlighted ? 0.0F : 6.0F, length, 6, 30, 12);
      poseStack.m_85849_();
   }

   public static void renderConnection(PoseStack poseStack, double x, double y, SkillConnection connection, float zoom, float animation) {
      prepareTextureRendering(new ResourceLocation("skilltree:textures/screen/direct_connection.png"));
      poseStack.m_85836_();
      SkillButton button1 = connection.getFirstButton();
      SkillButton button2 = connection.getSecondButton();
      double connectionX = (double)(button1.f_93620_ + (float)button1.m_5711_() / 2.0F);
      double connectionY = (double)(button1.f_93621_ + (float)button1.m_93694_() / 2.0F);
      poseStack.m_85837_(connectionX + x, connectionY + y, 0.0);
      float rotation = getAngleBetweenButtons(button1, button2);
      poseStack.m_85845_(Vector3f.f_122227_.m_122270_(rotation));
      int length = (int)getDistanceBetweenButtons(button1, button2);
      boolean highlighted = button1.skillLearned && button2.skillLearned;
      poseStack.m_85841_(1.0F, zoom, 1.0F);
      GuiComponent.m_93160_(poseStack, 0, -3, length, 6, 0.0F, highlighted ? 0.0F : 6.0F, length, 6, 50, 12);
      boolean shouldAnimate = button1.skillLearned && button2.canLearn || button2.skillLearned && button1.canLearn;
      if (!highlighted && shouldAnimate) {
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, (Mth.m_14031_(animation / 3.0F) + 1.0F) / 2.0F);
         GuiComponent.m_93160_(poseStack, 0, -3, length, 6, 0.0F, 0.0F, length, 6, 50, 12);
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      }

      poseStack.m_85849_();
   }
}
