package com.hollingsworth.arsnouveau.common.mixin.camera;

import com.hollingsworth.arsnouveau.common.util.ClientCameraUtil;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import shadowed.llamalad7.mixinextras.injector.WrapWithCondition;

@Mixin(
   value = {Minecraft.class},
   priority = 1100
)
public class MinecraftMixin {
   @WrapWithCondition(
      method = {"handleKeybinds"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/Options;setCameraType(Lnet/minecraft/client/CameraType;)V"
      )}
   )
   private boolean handleKeybinds(Options options, CameraType newType) {
      return !ClientCameraUtil.isPlayerMountedOnCamera();
   }
}
