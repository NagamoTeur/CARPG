package com.hollingsworth.arsnouveau.client.gui;

import com.hollingsworth.arsnouveau.common.items.SpellBook;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import vazkii.patchouli.client.RenderHelper;
import vazkii.patchouli.client.base.ClientTicker;
import vazkii.patchouli.client.book.BookEntry;
import vazkii.patchouli.client.book.ClientBookRegistry;
import vazkii.patchouli.client.book.gui.GuiBook;
import vazkii.patchouli.common.base.PatchouliConfig;
import vazkii.patchouli.common.book.Book;
import vazkii.patchouli.common.util.ItemStackUtil;

public class PatchouliTooltipEvent {
   private static float lexiconLookupTime = 0.0F;

   public static void onTooltip(PoseStack ms, ItemStack stack, int mouseX, int mouseY) {
      Minecraft mc = Minecraft.m_91087_();
      int tooltipY = mouseY - 4;
      if (mc.f_91074_ != null && !(mc.f_91080_ instanceof GuiBook)) {
         Pair<BookEntry, Integer> lexiconEntry = null;
         boolean hasSpellBook = false;

         for (int i = 0; i < Inventory.m_36059_(); i++) {
            ItemStack stackAt = mc.f_91074_.m_150109_().m_8020_(i);
            if (!stackAt.m_41619_()) {
               Book book = ItemStackUtil.getBookFromStack(stackAt);
               if (book != null && book.id.equals(new ResourceLocation("ars_nouveau", "worn_notebook"))) {
                  return;
               }

               if (stackAt.m_41720_() instanceof SpellBook) {
                  hasSpellBook = true;
               }
            }
         }

         if (!hasSpellBook) {
            return;
         }

         ItemStack lexiconStack = new ItemStack(ItemsRegistry.WORN_NOTEBOOK);
         Book bookx = ItemStackUtil.getBookFromStack(lexiconStack);
         Pair<BookEntry, Integer> entry = bookx.getContents().getEntryForStack(stack);
         if (entry != null && !((BookEntry)entry.getFirst()).isLocked()) {
            lexiconEntry = entry;
         }

         if (lexiconEntry != null) {
            int x = mouseX - 34;
            RenderSystem.m_69465_();
            GuiComponent.m_93172_(ms, x - 4, tooltipY - 4, x + 20, tooltipY + 26, 1140850688);
            GuiComponent.m_93172_(ms, x - 6, tooltipY - 6, x + 22, tooltipY + 28, 1140850688);
            if (PatchouliConfig.get().useShiftForQuickLookup() ? Screen.m_96638_() : Screen.m_96637_()) {
               lexiconLookupTime = lexiconLookupTime + ClientTicker.delta;
               int cx = x + 8;
               int cy = tooltipY + 8;
               float r = 12.0F;
               float time = 20.0F;
               float angles = lexiconLookupTime / time * 360.0F;
               RenderSystem.m_69472_();
               RenderSystem.m_69478_();
               RenderSystem.m_69405_(770, 771);
               BufferBuilder buf = Tesselator.m_85913_().m_85915_();
               buf.m_166779_(Mode.TRIANGLE_FAN, DefaultVertexFormat.f_85815_);
               float a = 0.5F + 0.2F * ((float)Math.cos((double)(ClientTicker.total / 10.0F)) * 0.5F + 0.5F);
               buf.m_5483_((double)cx, (double)cy, 0.0).m_85950_(0.0F, 0.5F, 0.0F, a).m_5752_();

               for (float ix = angles; ix > 0.0F; ix--) {
                  double rad = (double)((ix - 90.0F) / 180.0F) * Math.PI;
                  buf.m_5483_((double)cx + Math.cos(rad) * (double)r, (double)cy + Math.sin(rad) * (double)r, 0.0).m_85950_(0.0F, 1.0F, 0.0F, 1.0F).m_5752_();
               }

               buf.m_5483_((double)cx, (double)cy, 0.0).m_85950_(0.0F, 1.0F, 0.0F, 0.0F).m_5752_();
               Tesselator.m_85913_().m_85914_();
               RenderSystem.m_69461_();
               RenderSystem.m_69493_();
               if (lexiconLookupTime >= time) {
                  int spread = (Integer)lexiconEntry.getSecond();
                  ClientBookRegistry.INSTANCE
                     .displayBookGui(((BookEntry)lexiconEntry.getFirst()).getBook().id, ((BookEntry)lexiconEntry.getFirst()).getId(), spread * 2);
               }
            } else {
               lexiconLookupTime = 0.0F;
            }

            mc.m_91291_().f_115093_ = 300.0F;
            RenderHelper.renderItemStackInGui(ms, lexiconStack, x, tooltipY);
            mc.m_91291_().f_115093_ = 0.0F;
            ms.m_85836_();
            ms.m_85837_(0.0, 0.0, 500.0);
            mc.f_91062_.m_92750_(ms, "?", (float)(x + 10), (float)(tooltipY + 8), -1);
            ms.m_85841_(0.5F, 0.5F, 1.0F);
            boolean mac = Minecraft.f_91002_;
            Component key = Component.m_237113_(PatchouliConfig.get().useShiftForQuickLookup() ? "Shift" : (mac ? "Cmd" : "Ctrl"))
               .m_130940_(ChatFormatting.BOLD);
            mc.f_91062_.m_92763_(ms, key, (float)((x + 10) * 2 - 16), (float)((tooltipY + 8) * 2 + 20), -1);
            ms.m_85849_();
            RenderSystem.m_69482_();
         } else {
            lexiconLookupTime = 0.0F;
         }
      } else {
         lexiconLookupTime = 0.0F;
      }
   }
}
