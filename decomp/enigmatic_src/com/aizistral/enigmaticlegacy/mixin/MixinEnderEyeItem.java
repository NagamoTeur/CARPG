package com.aizistral.enigmaticlegacy.mixin;

import com.aizistral.enigmaticlegacy.api.events.EndPortalActivatedEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.EnderEyeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({EnderEyeItem.class})
public class MixinEnderEyeItem {
   private UseOnContext lastContext;

   @Inject(
      method = {"useOn"},
      at = {@At("HEAD")}
   )
   private void onUseOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> info) {
      this.lastContext = context;
   }

   @Redirect(
      method = {"useOn"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/level/Level;globalLevelEvent(ILnet/minecraft/core/BlockPos;I)V"
      )
   )
   private void globalEvent(Level level, int id, BlockPos pos, int data) {
      if (this.lastContext != null) {
         MinecraftForge.EVENT_BUS.post(new EndPortalActivatedEvent(this.lastContext.m_43723_(), pos));
      }

      level.m_6798_(id, pos, data);
   }
}
