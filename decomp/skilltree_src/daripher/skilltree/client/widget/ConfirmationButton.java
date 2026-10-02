package daripher.skilltree.client.widget;

import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class ConfirmationButton extends Button {
   protected boolean confirming;
   private Component confirmationMessage;

   public ConfirmationButton(int x, int y, int width, int height, Component message) {
      super(x, y, width, height, message);
   }

   @NotNull
   public Component m_6035_() {
      return this.confirming && this.confirmationMessage != null ? this.confirmationMessage : super.m_6035_();
   }

   @Override
   public void m_5691_() {
      if (!this.confirming) {
         this.confirming = true;
      } else {
         this.pressFunc.m_93750_(this);
      }
   }

   public boolean m_6375_(double pMouseX, double pMouseY, int pButton) {
      boolean clicked = super.m_6375_(pMouseX, pMouseY, pButton);
      if (!clicked) {
         this.confirming = false;
      }

      return clicked;
   }

   public void setConfirmationMessage(Component message) {
      this.confirmationMessage = message;
   }
}
