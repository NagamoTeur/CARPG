package daripher.skilltree.client.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import daripher.skilltree.client.screen.ScreenHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class Label extends AbstractWidget {
   public static final ResourceLocation WIDGETS_TEXTURE = new ResourceLocation("skilltree:textures/screen/widgets.png");
   private boolean hasBackground;

   public Label(int x, int y, Component message) {
      super(x, y, 0, 14, message);
   }

   public Label(int x, int y, int width, int height, Component message) {
      super(x, y, width, height, message);
      this.setHasBackground(true);
   }

   public void m_6303_(@NotNull PoseStack poseStack, int m, int pMouseY, float partialTick) {
      Minecraft minecraft = Minecraft.m_91087_();
      Font font = minecraft.f_91062_;
      if (this.hasBackground) {
         ScreenHelper.prepareTextureRendering(WIDGETS_TEXTURE);
         this.m_93228_(poseStack, this.f_93620_, this.f_93621_, 0, 14, this.f_93618_ / 2, this.f_93619_);
         this.m_93228_(poseStack, this.f_93620_ + this.f_93618_ / 2, this.f_93621_, 256 - this.f_93618_ / 2, 14, this.f_93618_ / 2, this.f_93619_);
         int textColor = this.getFGColor() | Mth.m_14167_(this.f_93625_ * 255.0F) << 24;
         m_93215_(poseStack, font, this.m_6035_(), this.f_93620_ + this.f_93618_ / 2, this.f_93621_ + (this.f_93619_ - 8) / 2, textColor);
      } else {
         m_93243_(poseStack, font, this.m_6035_(), this.f_93620_, this.f_93621_ + 3, this.getFGColor());
      }
   }

   public void m_142291_(NarrationElementOutput output) {
      output.m_169146_(NarratedElementType.TITLE, this.m_6035_());
   }

   public void setHasBackground(boolean hasBackground) {
      this.hasBackground = hasBackground;
   }
}
