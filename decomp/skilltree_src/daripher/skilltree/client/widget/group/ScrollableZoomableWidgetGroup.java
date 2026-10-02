package daripher.skilltree.client.widget.group;

import com.mojang.blaze3d.vertex.PoseStack;
import java.awt.geom.Rectangle2D.Double;
import javax.annotation.Nullable;
import net.minecraft.client.gui.components.AbstractWidget;
import org.jetbrains.annotations.NotNull;

public class ScrollableZoomableWidgetGroup<T extends AbstractWidget> extends WidgetGroup<T> {
   protected float scrollSpeedX;
   protected float scrollSpeedY;
   protected float scrollX;
   protected float scrollY;
   protected int maxScrollX;
   protected int maxScrollY;
   private float zoom = 1.0F;

   public ScrollableZoomableWidgetGroup(int pX, int pY, int pWidth, int pHeight) {
      super(pX, pY, pWidth, pHeight);
   }

   @Override
   public void m_6305_(@NotNull PoseStack graphics, int mouseX, int mouseY, float partialTick) {
      this.updateScroll(partialTick);
      graphics.m_85836_();
      graphics.m_85837_((double)this.scrollX, (double)this.scrollY, 0.0);

      for (T widget : this.widgets) {
         graphics.m_85836_();
         double widgetCenterX = (double)((float)widget.f_93620_ + (float)widget.m_5711_() / 2.0F);
         double widgetCenterY = (double)((float)widget.f_93621_ + (float)widget.m_93694_() / 2.0F);
         graphics.m_85837_(widgetCenterX, widgetCenterY, 0.0);
         graphics.m_85841_(this.zoom, this.zoom, 1.0F);
         graphics.m_85837_(-widgetCenterX, -widgetCenterY, 0.0);
         widget.m_6305_(graphics, mouseX, mouseY, partialTick);
         graphics.m_85849_();
      }

      graphics.m_85849_();
   }

   @Override
   public boolean m_7979_(double mouseX, double mouseY, int button, double dragX, double dragY) {
      if (button != 2) {
         return false;
      } else {
         if (this.maxScrollX > 0) {
            this.scrollSpeedX += (float)(dragX * 0.25);
         }

         if (this.maxScrollY > 0) {
            this.scrollSpeedY += (float)(dragY * 0.25);
         }

         return super.m_7979_(mouseX, mouseY, button, dragX, dragY);
      }
   }

   @Override
   public boolean m_6050_(double mouseX, double mouseY, double delta) {
      if (delta > 0.0 && this.zoom < 2.0F) {
         this.zoom += 0.05F;
      }

      if (delta < 0.0 && this.zoom > 0.25F) {
         this.zoom -= 0.05F;
      }

      this.rebuildFunc.run();
      return true;
   }

   @Nullable
   public T getWidgetAt(double mouseX, double mouseY) {
      mouseX -= (double)this.scrollX;
      mouseY -= (double)this.scrollY;

      for (T widget : this.widgets) {
         Double widgetArea = this.getWidgetArea(widget);
         if (widgetArea.contains(mouseX, mouseY)) {
            return widget;
         }
      }

      return null;
   }

   @NotNull
   protected Double getWidgetArea(T widget) {
      double width = (double)((float)widget.m_5711_() * this.zoom);
      double height = (double)((float)widget.m_93694_() * this.zoom);
      double x = (double)widget.f_93620_ + (double)widget.m_5711_() / 2.0 - width / 2.0;
      double y = (double)widget.f_93621_ + (double)widget.m_93694_() / 2.0 - height / 2.0;
      return new Double(x, y, width, height);
   }

   private void updateScroll(float partialTick) {
      this.scrollX = this.scrollX + this.scrollSpeedX * partialTick;
      this.scrollX = Math.max((float)(-this.maxScrollX) * this.zoom, Math.min((float)this.maxScrollX * this.zoom, this.scrollX));
      this.scrollSpeedX *= 0.8F;
      this.scrollY = this.scrollY + this.scrollSpeedY * partialTick;
      this.scrollY = Math.max((float)(-this.maxScrollY) * this.zoom, Math.min((float)this.maxScrollY * this.zoom, this.scrollY));
      this.scrollSpeedY *= 0.8F;
   }

   public void setMaxScrollX(int maxScrollX) {
      this.maxScrollX = maxScrollX;
   }

   public void setMaxScrollY(int maxScrollY) {
      this.maxScrollY = maxScrollY;
   }

   public int getMaxScrollX() {
      return this.maxScrollX;
   }

   public int getMaxScrollY() {
      return this.maxScrollY;
   }

   public float getScrollX() {
      return this.scrollX;
   }

   public float getScrollY() {
      return this.scrollY;
   }

   public float getZoom() {
      return this.zoom;
   }
}
