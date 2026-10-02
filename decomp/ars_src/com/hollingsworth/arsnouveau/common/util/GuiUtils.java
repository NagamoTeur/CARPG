package com.hollingsworth.arsnouveau.common.util;

import net.minecraft.client.gui.components.AbstractWidget;

public class GuiUtils {
   public static boolean isMouseInRelativeRange(int mouseX, int mouseY, AbstractWidget widget) {
      return isMouseInRelativeRange(mouseX, mouseY, widget.f_93620_, widget.f_93621_, widget.m_5711_(), widget.m_93694_());
   }

   public static boolean isMouseInRelativeRange(int mouseX, int mouseY, int x, int y, int w, int h) {
      return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
   }
}
