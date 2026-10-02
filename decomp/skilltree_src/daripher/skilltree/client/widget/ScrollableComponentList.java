package daripher.skilltree.client.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class ScrollableComponentList extends AbstractWidget {
   private final int maxHeight;
   private List<Component> components = new ArrayList<>();
   private int maxLines;
   private int scroll;

   public ScrollableComponentList(int y, int maxHeight) {
      super(0, y, 0, 0, Component.m_237119_());
      this.maxHeight = maxHeight;
   }

   public void m_6303_(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
      if (!this.components.isEmpty()) {
         this.renderBackground(poseStack);
         this.renderText(poseStack);
         this.renderScrollBar(poseStack);
      }
   }

   private void renderBackground(@NotNull PoseStack poseStack) {
      m_93172_(poseStack, this.f_93620_, this.f_93621_, this.f_93620_ + this.f_93618_, this.f_93621_ + this.f_93619_, -587202560);
   }

   private void renderText(@NotNull PoseStack poseStack) {
      Font font = Minecraft.m_91087_().f_91062_;

      for (int i = this.scroll; i < this.maxLines + this.scroll; i++) {
         Component component = this.components.get(i);
         int x = this.f_93620_ + 5;
         int y = this.f_93621_ + 5 + (i - this.scroll) * (9 + 3);
         m_93243_(poseStack, font, component, x, y, 8092645);
      }
   }

   private void renderScrollBar(@NotNull PoseStack poseStack) {
      if (this.components.size() > this.maxLines) {
         int scrollSize = this.f_93619_ * this.maxLines / this.components.size();
         int maxScroll = this.components.size() - this.maxLines;
         int scrollShift = (int)((float)(this.f_93619_ - scrollSize) / (float)maxScroll * (float)this.scroll);
         int x = this.f_93620_ + this.f_93618_ - 3;
         int y = this.f_93621_ + scrollShift;
         m_93172_(poseStack, x, this.f_93621_, this.f_93620_ + this.f_93618_, this.f_93621_ + this.f_93619_, -584965598);
         m_93172_(poseStack, x, y, this.f_93620_ + this.f_93618_, this.f_93621_ + scrollShift + scrollSize, -578254712);
      }
   }

   public boolean m_6050_(double mouseX, double mouseY, double amount) {
      int maxScroll = this.components.size() - this.maxLines;
      if (amount < 0.0 && this.scroll < maxScroll) {
         this.scroll++;
      }

      if (amount > 0.0 && this.scroll > 0) {
         this.scroll--;
      }

      return true;
   }

   public void setComponents(List<Component> components) {
      this.maxLines = components.size();
      this.components = components;
      this.f_93618_ = 0;
      Font font = Minecraft.m_91087_().f_91062_;

      for (Component stat : components) {
         int statWidth = font.m_92852_(stat);
         if (statWidth > this.f_93618_) {
            this.f_93618_ = statWidth;
         }
      }

      this.f_93618_ += 14;

      for (this.f_93619_ = components.size() * (9 + 3) + 10; this.f_93619_ > this.maxHeight; this.maxLines--) {
         this.f_93619_ -= 9 + 3;
      }
   }

   public void m_142291_(@NotNull NarrationElementOutput output) {
   }
}
