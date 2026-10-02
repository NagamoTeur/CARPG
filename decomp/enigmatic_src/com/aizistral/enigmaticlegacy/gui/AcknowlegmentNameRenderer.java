package com.aizistral.enigmaticlegacy.gui;

import com.aizistral.enigmaticlegacy.items.TheAcknowledgment;
import com.aizistral.enigmaticlegacy.items.TheInfinitum;
import com.aizistral.enigmaticlegacy.items.TheTwist;
import com.aizistral.enigmaticlegacy.registries.EnigmaticItems;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.patchouli.client.book.gui.GuiBook;
import vazkii.patchouli.common.book.Book;

@OnlyIn(Dist.CLIENT)
public class AcknowlegmentNameRenderer {
   private final Minecraft minecraft = Minecraft.m_91087_();
   private final GuiBook gui;
   private final Book book;
   private final Supplier<Font> font;
   private final Component name;

   public AcknowlegmentNameRenderer(GuiBook gui, Supplier<Font> font) {
      this.gui = gui;
      this.book = gui.book;
      this.font = font;
      Component customName = this.book.getBookItem().m_41786_();
      ItemStack stack = this.minecraft.f_91074_.m_21206_();
      if (stack == null || !(stack.m_41720_() instanceof TheAcknowledgment)) {
         stack = this.minecraft.f_91074_.m_21205_();
      }

      if (stack != null) {
         if (stack.m_41720_() instanceof TheTwist) {
            customName = EnigmaticItems.THE_TWIST.m_7626_(stack);
         } else if (stack.m_41720_() instanceof TheInfinitum) {
            customName = EnigmaticItems.THE_INFINITUM.m_7626_(stack);
         }
      }

      this.name = customName;
   }

   public void drawHeader(PoseStack ms) {
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      GuiBook.drawFromTexture(ms, this.gui.book, -8, 12, 0, 180, 140, 31);
      int color = this.book.nameplateColor;
      this.font.get().m_92889_(ms, this.name, 13.0F, 16.0F, color);
      Component toDraw = this.book.getSubtitle().m_130948_(this.book.getFontStyle());
      this.font.get().m_92889_(ms, toDraw, 24.0F, 24.0F, color);
   }
}
