package com.hollingsworth.arsnouveau.common.mixin.camera;

import com.hollingsworth.arsnouveau.common.util.CameraUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.ViewArea;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(
   value = {LevelRenderer.class},
   priority = 1100
)
public class LevelRendererMixin {
   @Shadow
   @Final
   private Minecraft f_109461_;

   @Redirect(
      method = {"setupRender"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/renderer/ViewArea;repositionCamera(DD)V"
      )
   )
   public void onRepositionCamera(ViewArea viewArea, double x, double z) {
      if (!CameraUtil.isPlayerMountedOnCamera(this.f_109461_.f_91074_)) {
         viewArea.m_110850_(x, z);
      }
   }
}
