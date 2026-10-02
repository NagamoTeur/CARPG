package com.aizistral.enigmaticlegacy.mixin;

import com.aizistral.enigmaticlegacy.gui.AcknowlegmentNameRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.patchouli.client.book.gui.BookTextRenderer;
import vazkii.patchouli.client.book.gui.GuiBook;
import vazkii.patchouli.client.book.gui.GuiBookLanding;

@Mixin({GuiBookLanding.class})
public abstract class MixinGuiBookLanding extends GuiBook {
   @Shadow(
      remap = false
   )
   BookTextRenderer text;
   AcknowlegmentNameRenderer renderer;

   public MixinGuiBookLanding() {
      super(null, null);
      throw new IllegalStateException("Can't touch this");
   }

   @Inject(
      method = {"init"},
      at = {@At("RETURN")},
      cancellable = false,
      require = 1,
      remap = true
   )
   private void onInit(CallbackInfo info) {
      if ("enigmaticlegacy".equals(this.book.getModNamespace())) {
         this.renderer = new AcknowlegmentNameRenderer(this, () -> this.f_96547_);
      }
   }

   @Inject(
      method = {"drawHeader"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1,
      remap = false
   )
   private void onDrawHeader(PoseStack ms, CallbackInfo info) {
      if ("enigmaticlegacy".equals(this.book.getModNamespace())) {
         info.cancel();
         this.renderer.drawHeader(ms);
      }
   }
}
