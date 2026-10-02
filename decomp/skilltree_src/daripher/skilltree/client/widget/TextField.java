package daripher.skilltree.client.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import daripher.skilltree.client.screen.ScreenHelper;
import daripher.skilltree.mixin.EditBoxAccessor;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TextField extends EditBox implements TickingWidget {
   public static final int INVALID_TEXT_COLOR = 14155776;
   private static final int HINT_COLOR = 5723991;
   private Predicate<String> softFilter = Objects::nonNull;
   private Function<String, String> suggestionProvider = s -> null;
   private String hint = null;

   public TextField(int x, int y, int width, int height, String defaultText) {
      super(Minecraft.m_91087_().f_91062_, x, y, width, height, Component.m_237119_());
      this.m_94199_(80);
      this.m_94144_(defaultText);
   }

   public boolean m_7933_(int keyCode, int scanCode, int modifiers) {
      EditBoxAccessor accessor = (EditBoxAccessor)this;
      if (keyCode == 258 && accessor.getSuggestion() != null) {
         this.m_94144_(this.m_94155_() + accessor.getSuggestion());
         this.m_94167_(null);
         return true;
      } else {
         boolean result = super.m_7933_(keyCode, scanCode, modifiers);
         this.m_94167_(this.suggestionProvider.apply(this.m_94155_()));
         return result;
      }
   }

   public boolean m_5534_(char codePoint, int modifiers) {
      boolean result = super.m_5534_(codePoint, modifiers);
      this.m_94167_(this.suggestionProvider.apply(this.m_94155_()));
      return result;
   }

   public void m_94151_(@NotNull Consumer<String> responder) {
      super.m_94151_(s -> {
         if (this.isValueValid()) {
            responder.accept(s);
         }
      });
   }

   public void setSuggestionProvider(Function<String, String> suggestionProvider) {
      this.suggestionProvider = suggestionProvider;
   }

   public TextField setSoftFilter(Predicate<String> filter) {
      this.softFilter = filter;
      return this;
   }

   public void m_6303_(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
      EditBoxAccessor accessor = (EditBoxAccessor)this;
      if (this.m_94213_()) {
         ScreenHelper.prepareTextureRendering(new ResourceLocation("skilltree:textures/screen/widgets.png"));
         int v = this.m_198029_() ? 42 : 56;
         this.m_93228_(poseStack, this.f_93620_, this.f_93621_, 0, v, this.f_93618_ / 2, this.f_93619_);
         this.m_93228_(poseStack, this.f_93620_ + this.f_93618_ / 2, this.f_93621_, -this.f_93618_ / 2, v, this.f_93618_ / 2, this.f_93619_);
         int textColor = this.getTextColor();
         int cursorVisiblePosition = this.m_94207_() - accessor.getDisplayPos();
         int highlightWidth = accessor.getHighlightPos() - accessor.getDisplayPos();
         Minecraft minecraft = Minecraft.m_91087_();
         Font font = minecraft.f_91062_;
         String visibleText = font.m_92834_(this.m_94155_().substring(accessor.getDisplayPos()), this.m_94210_());
         boolean isTextSplitByCursor = cursorVisiblePosition >= 0 && cursorVisiblePosition <= visibleText.length();
         boolean isCursorVisible = this.m_93696_() && accessor.getFrame() / 6 % 2 == 0 && isTextSplitByCursor;
         if (visibleText.isEmpty() && this.hint != null && !this.m_93696_()) {
            visibleText = this.hint;
         }

         int textX = this.f_93620_ + 5;
         int textStartX = textX;
         int textY = this.f_93621_ + 3;
         if (highlightWidth > visibleText.length()) {
            highlightWidth = visibleText.length();
         }

         if (!visibleText.isEmpty()) {
            String s1 = isTextSplitByCursor ? visibleText.substring(0, cursorVisiblePosition) : visibleText;
            textX = font.m_92744_(poseStack, accessor.getFormatter().apply(s1, accessor.getDisplayPos()), (float)textX, (float)textY, textColor);
         }

         boolean isCursorSurrounded = this.m_94207_() < this.m_94155_().length() || this.m_94155_().length() >= accessor.getMaxLength();
         int cursorX = textX;
         if (!isTextSplitByCursor) {
            cursorX = cursorVisiblePosition > 0 ? this.f_93620_ + this.f_93618_ : this.f_93620_;
         } else if (isCursorSurrounded) {
            cursorX = textX - 1;
            textX--;
         }

         if (!visibleText.isEmpty() && isTextSplitByCursor && cursorVisiblePosition < visibleText.length()) {
            font.m_92744_(
               poseStack, accessor.getFormatter().apply(visibleText.substring(cursorVisiblePosition), this.m_94207_()), (float)textX, (float)textY, textColor
            );
         }

         if (!isCursorSurrounded && accessor.getSuggestion() != null) {
            font.m_92750_(poseStack, accessor.getSuggestion(), (float)(cursorX - 1), (float)textY, -8355712);
         }

         if (isCursorVisible) {
            if (isCursorSurrounded) {
               GuiComponent.m_93172_(poseStack, cursorX, textY - 1, cursorX + 1, textY + 9, -3092272);
            } else {
               font.m_92750_(poseStack, "_", (float)cursorX, (float)textY, textColor);
            }
         }

         if (highlightWidth != cursorVisiblePosition) {
            int highlightEndX = textStartX + font.m_92895_(visibleText.substring(0, highlightWidth));
            accessor.invokeRenderHighlight(cursorX, textY - 1, highlightEndX - 1, textY + 9);
         }
      }
   }

   public boolean isValueValid() {
      return this.softFilter.test(this.m_94155_());
   }

   public TextField setHint(@Nullable String hint) {
      this.hint = hint;
      return this;
   }

   private int getTextColor() {
      return this.m_94155_().isEmpty() ? 5723991 : (this.isValueValid() ? 14737632 : 14155776);
   }

   public boolean m_6375_(double mouseX, double mouseY, int button) {
      this.m_93692_(this.m_93680_(mouseX, mouseY));
      return super.m_6375_(mouseX, mouseY, button);
   }

   @Override
   public void onWidgetTick() {
      this.m_94120_();
   }
}
