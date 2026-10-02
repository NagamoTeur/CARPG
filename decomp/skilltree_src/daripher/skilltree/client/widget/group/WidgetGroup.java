package daripher.skilltree.client.widget.group;

import com.mojang.blaze3d.vertex.PoseStack;
import daripher.skilltree.client.widget.TickingWidget;
import java.awt.geom.Rectangle2D.Float;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class WidgetGroup<T extends AbstractWidget> extends AbstractWidget implements TickingWidget {
   protected final Set<T> widgets = new HashSet<>();
   protected Runnable rebuildFunc = () -> {
   };

   public WidgetGroup(int x, int y, int width, int height) {
      super(x, y, width, height, Component.m_237119_());
   }

   public void m_6305_(@NotNull PoseStack graphics, int mouseX, int mouseY, float partialTick) {
      this.widgetsCopy().forEach(widget -> widget.m_6305_(graphics, mouseX, mouseY, partialTick));
      graphics.m_85836_();
      graphics.m_85837_(0.0, 0.0, 1.0);
      graphics.m_85849_();
   }

   public void m_142291_(@NotNull NarrationElementOutput output) {
   }

   public boolean m_7933_(int keyCode, int scanCode, int modifiers) {
      boolean result = false;

      for (T widget : this.widgetsCopy()) {
         if (widget.m_7933_(keyCode, scanCode, modifiers)) {
            result = true;
         }
      }

      return result;
   }

   public boolean m_7920_(int keyCode, int scanCode, int modifiers) {
      boolean result = false;

      for (T widget : this.widgetsCopy()) {
         if (widget.m_7920_(keyCode, scanCode, modifiers)) {
            result = true;
         }
      }

      return result;
   }

   public boolean m_6375_(double mouseX, double mouseY, int button) {
      boolean result = false;

      for (T widget : this.widgetsCopy()) {
         if (widget.m_6375_(mouseX, mouseY, button)) {
            result = true;
         }
      }

      return result;
   }

   public boolean m_7979_(double mouseX, double mouseY, int button, double dragX, double dragY) {
      boolean result = false;

      for (T widget : this.widgetsCopy()) {
         if (widget.m_7979_(mouseX, mouseY, button, dragX, dragY)) {
            result = true;
         }
      }

      return result;
   }

   public boolean m_6348_(double mouseX, double mouseY, int button) {
      boolean result = false;

      for (T widget : this.widgetsCopy()) {
         if (widget.m_6348_(mouseX, mouseY, button)) {
            result = true;
         }
      }

      return result;
   }

   public boolean m_6050_(double mouseX, double mouseY, double delta) {
      boolean result = false;

      for (T widget : this.widgetsCopy()) {
         if (widget.m_6050_(mouseX, mouseY, delta)) {
            result = true;
         }
      }

      return result;
   }

   public boolean m_5534_(char codePoint, int modifiers) {
      boolean result = false;

      for (T widget : this.widgetsCopy()) {
         if (widget.m_5534_(codePoint, modifiers)) {
            result = true;
         }
      }

      return result;
   }

   public void m_94757_(double mouseX, double mouseY) {
      this.widgetsCopy().forEach(widget -> widget.m_94757_(mouseX, mouseY));
   }

   @Override
   public void onWidgetTick() {
      for (T t : this.widgetsCopy()) {
         if (t instanceof TickingWidget tickingWidget) {
            tickingWidget.onWidgetTick();
         }
      }
   }

   @NotNull
   public <W extends T> W addWidget(@NotNull W widget) {
      this.widgets.add((T)widget);
      return widget;
   }

   public Set<T> getWidgets() {
      return this.widgets;
   }

   public void clearWidgets() {
      this.widgets.clear();
   }

   public void setRebuildFunc(Runnable rebuildFunc) {
      this.rebuildFunc = rebuildFunc;
   }

   public void rebuildWidgets() {
      this.rebuildFunc.run();
   }

   protected HashSet<T> widgetsCopy() {
      return new HashSet<>(this.widgets);
   }

   public Float getArea() {
      return new Float((float)this.f_93620_, (float)this.f_93621_, (float)this.f_93618_, (float)this.f_93619_);
   }
}
