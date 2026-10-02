package com.hollingsworth.arsnouveau.client.gui.buttons;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.network.chat.Component;

public class ANButton extends Button {
   public ANButton(int x, int y, int w, int h, Component text, OnPress onPress) {
      super(x, y, w, h, text, onPress);
   }

   public void setX(int i) {
      this.f_93620_ = i;
   }

   public void setY(int i) {
      this.f_93621_ = i;
   }

   public int getX() {
      return this.f_93620_;
   }

   public int getY() {
      return this.f_93621_;
   }

   public void setPosition(int x, int y) {
      this.f_93620_ = x;
      this.f_93621_ = y;
   }
}
