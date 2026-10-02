package daripher.skilltree.client.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class CheckBox extends Button {
   private boolean value;
   private Consumer<Boolean> responder = b -> {
   };

   public CheckBox(int x, int y, boolean defaultValue) {
      super(x, y, 14, 14, Component.m_237119_());
      this.value = defaultValue;
   }

   @Override
   public void m_5691_() {
      this.value ^= true;
      this.responder.accept(this.value);
   }

   @Override
   protected void renderBackground(@NotNull PoseStack poseStack) {
      super.renderBackground(poseStack);
      if (this.value) {
         this.m_93228_(poseStack, this.f_93620_, this.f_93621_, 0, 242, this.f_93618_, this.f_93619_);
      }
   }

   @Override
   protected int getTextureVariant() {
      return this.m_198029_() ? 3 : 4;
   }

   public void setResponder(Consumer<Boolean> responder) {
      this.responder = responder;
   }
}
