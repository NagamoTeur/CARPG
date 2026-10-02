package com.aizistral.enigmaticlegacy.mixin;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.patchouli.client.book.gui.GuiBook;
import vazkii.patchouli.client.book.gui.GuiBookEntry;

@Mixin({GuiBook.class})
public class MixinGuiBook {
   @Inject(
      method = {"openWebLink"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 0,
      remap = false
   )
   private static void onOpenWebLink(Screen prevScreen, String address, CallbackInfo info) {
      if (prevScreen instanceof GuiBookEntry entry && "enigmaticlegacy".equals(entry.book.getModNamespace())) {
         info.cancel();
         Minecraft mc = Minecraft.m_91087_();
         mc.m_91152_(new ConfirmLinkScreen(yes -> {
            if (yes) {
               Util.m_137581_().m_137646_(address);
            }

            mc.m_91152_(prevScreen);
         }, address, true));
      }
   }
}
