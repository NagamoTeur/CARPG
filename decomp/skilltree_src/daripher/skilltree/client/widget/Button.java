package daripher.skilltree.client.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import daripher.skilltree.client.screen.ScreenHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class Button extends net.minecraft.client.gui.components.Button {
   protected OnPress pressFunc = b -> {
   };

   public Button(int x, int y, int width, int height, Component message) {
      super(x, y, width, height, message, b -> {
      });
   }

   public void setPressFunc(OnPress pressFunc) {
      this.pressFunc = pressFunc;
   }

   public void m_5691_() {
      this.pressFunc.m_93750_(this);
   }

   public void m_6303_(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
      this.renderBackground(poseStack);
      this.renderText(poseStack);
   }

   protected void renderBackground(@NotNull PoseStack poseStack) {
      ScreenHelper.prepareTextureRendering(new ResourceLocation("skilltree:textures/screen/widgets.png"));
      int v = this.getTextureVariant() * 14;
      this.m_93228_(poseStack, this.f_93620_, this.f_93621_, 0, v, this.f_93618_ / 2, this.f_93619_);
      this.m_93228_(poseStack, this.f_93620_ + this.f_93618_ / 2, this.f_93621_, -this.f_93618_ / 2, v, this.f_93618_ / 2, this.f_93619_);
   }

   protected void renderText(@NotNull PoseStack poseStack) {
      Minecraft minecraft = Minecraft.m_91087_();
      Font font = minecraft.f_91062_;
      int textColor = this.getFGColor();
      textColor |= Mth.m_14167_(this.f_93625_ * 255.0F) << 24;
      m_93215_(poseStack, font, this.m_6035_(), this.f_93620_ + this.f_93618_ / 2, this.f_93621_ + (this.f_93619_ - 8) / 2, textColor);
   }

   protected int getTextureVariant() {
      return !this.m_142518_() ? 0 : (this.m_198029_() ? 2 : 1);
   }

   public boolean m_7933_(int keyCode, int scanCode, int modifiers) {
      return false;
   }

   public boolean m_5953_(double mouseX, double mouseY) {
      return this.f_93624_
         && mouseX >= (double)this.f_93620_
         && mouseY >= (double)this.f_93621_
         && mouseX < (double)(this.f_93620_ + this.f_93618_)
         && mouseY < (double)(this.f_93621_ + this.f_93619_);
   }
}
