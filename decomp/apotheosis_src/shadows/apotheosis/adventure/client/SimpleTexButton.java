package shadows.apotheosis.adventure.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.client.gui.components.Button.OnTooltip;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

public class SimpleTexButton extends Button {
   protected final ResourceLocation texture;
   protected final int xTexStart;
   protected final int yTexStart;
   protected final int textureWidth;
   protected final int textureHeight;
   protected Component inactiveMessage = CommonComponents.f_237098_;

   public SimpleTexButton(int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, ResourceLocation texture, OnPress pOnPress) {
      this(pX, pY, pWidth, pHeight, pXTexStart, pYTexStart, texture, 256, 256, pOnPress);
   }

   public SimpleTexButton(
      int pX,
      int pY,
      int pWidth,
      int pHeight,
      int pXTexStart,
      int pYTexStart,
      ResourceLocation texture,
      int pTextureWidth,
      int pTextureHeight,
      OnPress pOnPress
   ) {
      this(pX, pY, pWidth, pHeight, pXTexStart, pYTexStart, texture, pTextureWidth, pTextureHeight, pOnPress, CommonComponents.f_237098_);
   }

   public SimpleTexButton(
      int pX,
      int pY,
      int pWidth,
      int pHeight,
      int pXTexStart,
      int pYTexStart,
      ResourceLocation texture,
      int pTextureWidth,
      int pTextureHeight,
      OnPress pOnPress,
      Component pMessage
   ) {
      this(pX, pY, pWidth, pHeight, pXTexStart, pYTexStart, texture, pTextureWidth, pTextureHeight, pOnPress, f_93716_, pMessage);
   }

   public SimpleTexButton(
      int pX,
      int pY,
      int pWidth,
      int pHeight,
      int pXTexStart,
      int pYTexStart,
      ResourceLocation texture,
      int pTextureWidth,
      int pTextureHeight,
      OnPress pOnPress,
      OnTooltip pOnTooltip,
      Component pMessage
   ) {
      super(pX, pY, pWidth, pHeight, pMessage, pOnPress, pOnTooltip);
      this.textureWidth = pTextureWidth;
      this.textureHeight = pTextureHeight;
      this.xTexStart = pXTexStart;
      this.yTexStart = pYTexStart;
      this.texture = texture;
   }

   public SimpleTexButton setInactiveMessage(Component msg) {
      this.inactiveMessage = msg;
      return this;
   }

   public void setPosition(int pX, int pY) {
      this.f_93620_ = pX;
      this.f_93621_ = pY;
   }

   public void m_6303_(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157456_(0, this.texture);
      int yTex = this.yTexStart;
      if (!this.m_142518_()) {
         yTex += this.f_93619_;
      } else if (this.m_198029_()) {
         yTex += this.f_93619_ * 2;
      }

      RenderSystem.m_69482_();
      m_93133_(
         pPoseStack, this.f_93620_, this.f_93621_, (float)this.xTexStart, (float)yTex, this.f_93618_, this.f_93619_, this.textureWidth, this.textureHeight
      );
      if (this.m_198029_()) {
         this.m_7428_(pPoseStack, pMouseX, pMouseY);
      }
   }

   public void m_7428_(PoseStack pPoseStack, int pMouseX, int pMouseY) {
      if (this.f_93718_ != f_93716_) {
         this.f_93718_.m_93752_(this, pPoseStack, pMouseX, pMouseY);
      } else if (this.m_6035_() != CommonComponents.f_237098_) {
         MutableComponent primary = (MutableComponent)this.m_6035_();
         if (!this.f_93623_) {
            primary = primary.m_130940_(ChatFormatting.GRAY);
         }

         List<FormattedCharSequence> tooltips = new ArrayList<>();
         tooltips.add(primary.m_7532_());
         if (!this.f_93623_ && this.inactiveMessage != CommonComponents.f_237098_) {
            tooltips.add(this.inactiveMessage.m_7532_());
         }

         Minecraft.m_91087_().f_91080_.m_96617_(pPoseStack, tooltips, pMouseX, pMouseY);
      }
   }
}
