package com.hollingsworth.arsnouveau.client.gui;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ModdedScreen extends Screen {
   public int maxScale;
   public float scaleFactor;
   public List<Component> tooltip;

   public ModdedScreen(Component titleIn) {
      super(titleIn);
   }

   public void m_7856_() {
      super.m_7856_();
      Window res = this.getMinecraft().m_91268_();
      double oldGuiScale = (double)res.m_85385_((Integer)this.f_96541_.f_91066_.m_231928_().m_231551_(), this.f_96541_.m_91390_());
      this.maxScale = this.getMaxAllowedScale();
      int persistentScale = Math.min(0, this.maxScale);
      double newGuiScale = (double)res.m_85385_(persistentScale, this.f_96541_.m_91390_());
      this.scaleFactor = 1.0F;
   }

   public boolean isMouseInRelativeRange(int mouseX, int mouseY, int x, int y, int w, int h) {
      return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
   }

   public void drawTooltip(PoseStack stack, int mouseX, int mouseY) {
      if (this.tooltip != null && !this.tooltip.isEmpty()) {
         this.renderComponentTooltip(stack, this.tooltip, mouseX, mouseY, this.f_96547_);
      }
   }

   public void m_6305_(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
      super.m_6305_(matrixStack, mouseX, mouseY, partialTicks);
   }

   public final void resetTooltip() {
      this.tooltip = null;
   }

   public boolean m_7043_() {
      return false;
   }

   int getMaxAllowedScale() {
      return this.getMinecraft().m_91268_().m_85385_(0, this.f_96541_.m_91390_());
   }
}
