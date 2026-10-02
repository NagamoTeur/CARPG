package daripher.skilltree.client.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import daripher.skilltree.client.screen.ScreenHelper;
import daripher.skilltree.client.tooltip.TooltipHelper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class SelectionList<T> extends AbstractButton {
   public static final ResourceLocation WIDGETS_TEXTURE = new ResourceLocation("skilltree:textures/screen/widgets.png");
   private static final int LINE_HEIGHT = 14;
   private Function<T, Component> nameGetter = t -> Component.m_237113_(t.toString());
   private Consumer<T> responder = t -> {
   };
   private final List<T> valuesList;
   private String search = "";
   private T value;
   private int maxDisplayed;
   private int maxScroll;
   private int scroll;

   public SelectionList(int x, int y, int width, Collection<T> possibleValues) {
      super(x, y, width, 14, Component.m_237119_());
      this.valuesList = new ArrayList<>(possibleValues);
      this.setMaxDisplayed(10);
   }

   public void m_5691_() {
      this.responder.accept(this.value);
   }

   public void m_6305_(@NotNull PoseStack graphics, int mouseX, int mouseY, float partialTick) {
      if (this.f_93624_) {
         int y = this.f_93621_;
         this.renderLineBackground(graphics, y, 42, 7);

         for (int i = 0; i < this.maxDisplayed - 1; i++) {
            int rowY = y + 7 + i * 14;
            this.renderLineBackground(graphics, rowY, 70, 14);
         }

         y += (this.maxDisplayed - 1) * 14 + 7;
         this.renderLineBackground(graphics, y, 49, 7);
         y -= (this.maxDisplayed - 1) * 14 + 7;
         Minecraft minecraft = Minecraft.m_91087_();
         Font font = minecraft.f_91062_;

         for (int i = 0; i < this.maxDisplayed; i++) {
            this.renderLine(graphics, i, y, font);
         }

         this.renderScroll(graphics);
      }
   }

   private void renderLine(@NotNull PoseStack graphics, int line, int y, Font font) {
      List<T> values = this.getValues();
      if (line + this.scroll < values.size()) {
         String text = this.nameGetter.apply(values.get(line + this.scroll)).getString();
         String selectedText = this.nameGetter.apply(this.value).getString();
         int textColor = text.equals(selectedText) ? 5635925 : 14737632;
         text = TooltipHelper.getTrimmedString(text, this.f_93618_ - 10);
         int textX = this.f_93620_ + 5;
         int textY = y + 3 + line * 14;
         this.renderLine(graphics, font, text, textX, textY, textColor);
      }
   }

   private void renderLine(@NotNull PoseStack graphics, Font font, String line, int textX, int textY, int textColor) {
      String lowerCase = line.toLowerCase();
      if (!this.search.isEmpty() && lowerCase.contains(this.search)) {
         String split1 = line.substring(0, lowerCase.indexOf(this.search));
         m_93236_(graphics, font, split1, textX, textY, textColor);
         textX += font.m_92895_(split1);
         String split2 = line.substring(lowerCase.indexOf(this.search), lowerCase.indexOf(this.search) + this.search.length());
         m_93236_(graphics, font, split2, textX, textY, 16766530);
         textX += font.m_92895_(split2);
         String split3 = line.substring(lowerCase.indexOf(this.search) + this.search.length());
         m_93236_(graphics, font, split3, textX, textY, textColor);
      } else {
         m_93236_(graphics, font, line, textX, textY, textColor);
      }
   }

   private List<T> getValues() {
      return !this.search.isEmpty() ? this.valuesList.stream().filter(this::isSearched).toList() : this.valuesList;
   }

   private boolean isSearched(T value) {
      return this.nameGetter.apply(value).getString().toLowerCase().contains(this.search);
   }

   private void renderLineBackground(@NotNull PoseStack graphics, int rowY, int vOffset, int height) {
      ScreenHelper.prepareTextureRendering(WIDGETS_TEXTURE);
      m_93133_(graphics, this.f_93620_, rowY, 0.0F, (float)vOffset, this.f_93618_ / 2, height, 256, 256);
      m_93133_(graphics, this.f_93620_ + this.f_93618_ / 2, rowY, (float)(-this.f_93618_ / 2), (float)vOffset, this.f_93618_ / 2, height, 256, 256);
   }

   private void renderScroll(PoseStack graphics) {
      if (this.getValues().size() > this.maxDisplayed) {
         int maxScrollSize = this.f_93619_ - 8;
         int scrollSize = maxScrollSize * this.maxDisplayed / this.getValues().size();
         int x = this.f_93620_ + this.f_93618_ - 4;
         int y = this.f_93621_ + 3 + (maxScrollSize - scrollSize) * this.scroll / this.maxScroll;
         m_93172_(graphics, x, y, x + 1, y + scrollSize + 1, -5592406);
      }
   }

   public void m_5716_(double mouseX, double mouseY) {
      if (this.m_93680_(mouseX, mouseY)) {
         int clickedLine = ((int)mouseY - this.f_93621_) / 14 + this.scroll;
         List<T> values = this.getValues();
         if (clickedLine < values.size()) {
            this.value = values.get(clickedLine);
            this.m_5691_();
         }
      }
   }

   public void m_94757_(double mouseX, double mouseY) {
      super.m_94757_(mouseX, mouseY);
   }

   public boolean m_6050_(double mouseX, double mouseY, double delta) {
      if (this.m_5953_(mouseX, mouseY)) {
         this.setScroll(this.scroll - Mth.m_14205_(delta));
         return true;
      } else {
         return false;
      }
   }

   public boolean m_5534_(char codePoint, int modifiers) {
      if (!this.m_198029_()) {
         return false;
      } else if (!SharedConstants.m_136188_(codePoint)) {
         return false;
      } else {
         this.search = this.search + Character.toLowerCase(codePoint);
         this.setScrollToSelection();
         return true;
      }
   }

   public boolean m_7933_(int keyCode, int scanCode, int modifiers) {
      if (this.search.isEmpty()) {
         return false;
      } else if (keyCode == 259) {
         this.search = this.search.substring(0, this.search.length() - 1);
         this.setScrollToSelection();
         return true;
      } else if (keyCode == 256) {
         this.search = "";
         this.setScrollToSelection();
         return true;
      } else {
         return false;
      }
   }

   private void setScroll(int scroll) {
      this.scroll = Math.min(this.maxScroll, Math.max(0, scroll));
   }

   public SelectionList<T> setNameGetter(Function<T, Component> nameGetter) {
      this.nameGetter = nameGetter;
      this.getValues().sort((v1, v2) -> {
         String name1 = nameGetter.apply((T)v1).getString();
         String name2 = nameGetter.apply((T)v2).getString();
         return name1.compareTo(name2);
      });
      this.setScrollToSelection();
      return this;
   }

   public Function<T, Component> getNameGetter() {
      return this.nameGetter;
   }

   public SelectionList<T> setResponder(Consumer<T> responder) {
      this.responder = responder;
      return this;
   }

   public T getValue() {
      return this.value;
   }

   public SelectionList<T> setValue(T value) {
      this.value = value;
      this.setScrollToSelection();
      return this;
   }

   public int getMaxDisplayed() {
      return this.maxDisplayed;
   }

   public SelectionList<T> setMaxDisplayed(int maxDisplayed) {
      maxDisplayed = Math.min(maxDisplayed, this.getValues().size());
      this.maxDisplayed = maxDisplayed;
      this.maxScroll = this.getValues().size() - maxDisplayed;
      this.setHeight(14 * maxDisplayed);
      return this;
   }

   public void setScrollToSelection() {
      this.setScroll(this.getValues().indexOf(this.value));
   }

   public void m_142291_(@NotNull NarrationElementOutput output) {
   }
}
