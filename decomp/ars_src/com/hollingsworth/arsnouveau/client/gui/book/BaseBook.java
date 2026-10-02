package com.hollingsworth.arsnouveau.client.gui.book;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.SpellValidationError;
import com.hollingsworth.arsnouveau.client.gui.BookSlider;
import com.hollingsworth.arsnouveau.client.gui.ModdedScreen;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class BaseBook extends ModdedScreen {
   public final int FULL_WIDTH = 290;
   public final int FULL_HEIGHT = 194;
   public static ResourceLocation background = new ResourceLocation("ars_nouveau", "textures/gui/spell_book_template.png");
   public int bookLeft;
   public int bookTop;
   public int bookRight;
   public int bookBottom;
   public List<SpellValidationError> validationErrors = new ArrayList<>();
   public ArsNouveauAPI api = ArsNouveauAPI.getInstance();
   public ItemRenderer itemre = this.f_96542_;
   public static Comparator<AbstractSpellPart> COMPARE_GLYPH_BY_TYPE = Comparator.comparingInt(AbstractSpellPart::getTypeIndex);
   public static Comparator<AbstractSpellPart> COMPARE_TYPE_THEN_NAME = COMPARE_GLYPH_BY_TYPE.thenComparing(AbstractSpellPart::getLocaleName);

   public BaseBook() {
      super(Component.m_237113_(""));
   }

   @Override
   public void m_7856_() {
      super.m_7856_();
      this.f_96541_.f_91068_.m_90926_(true);
      this.bookLeft = this.f_96543_ / 2 - 145;
      this.bookTop = this.f_96544_ / 2 - 97;
      this.bookRight = this.f_96543_ / 2 + 145;
      this.bookBottom = this.f_96544_ / 2 + 97;
   }

   @Override
   public void m_6305_(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
      super.m_6305_(matrixStack, mouseX, mouseY, partialTicks);
      matrixStack.m_85836_();
      if (this.scaleFactor != 1.0F) {
         matrixStack.m_85841_(this.scaleFactor, this.scaleFactor, this.scaleFactor);
         mouseX = (int)((float)mouseX / this.scaleFactor);
         mouseY = (int)((float)mouseY / this.scaleFactor);
      }

      this.drawScreenAfterScale(matrixStack, mouseX, mouseY, partialTicks);
      matrixStack.m_85849_();
   }

   public void drawBackgroundElements(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
      drawFromTexture(background, 0, 0, 0, 0, 290, 194, 290, 194, stack);
   }

   public static void drawFromTexture(
      ResourceLocation resourceLocation, int x, int y, int uOffset, int vOffset, int width, int height, int fileWidth, int fileHeight, PoseStack stack
   ) {
      RenderSystem.m_157456_(0, resourceLocation);
      m_93133_(stack, x, y, (float)uOffset, (float)vOffset, width, height, fileWidth, fileHeight);
   }

   public void drawForegroundElements(int mouseX, int mouseY, float partialTicks) {
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public void drawScreenAfterScale(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
      this.resetTooltip();
      this.m_7333_(stack);
      stack.m_85836_();
      stack.m_85837_((double)this.bookLeft, (double)this.bookTop, 0.0);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      this.drawBackgroundElements(stack, mouseX, mouseY, partialTicks);
      this.drawForegroundElements(mouseX, mouseY, partialTicks);
      stack.m_85849_();
      super.m_6305_(stack, mouseX, mouseY, partialTicks);
      this.drawTooltip(stack, mouseX, mouseY);
   }

   public BookSlider buildSlider(int x, int y, Component prefix, Component suffix, double currentVal) {
      return new BookSlider(x, y, 100, 20, prefix, suffix, 1.0, 255.0, currentVal, 1.0, 1, true);
   }
}
