package com.aizistral.enigmaticlegacy.mixin;

import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.Shulker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Shulker.class})
public abstract class MixinShulker extends AbstractGolem {
   protected MixinShulker() {
      super(null, null);
      throw new IllegalStateException("Can't touch this");
   }

   @Inject(
      method = {"teleportSomewhere"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void onTeleportSomewhere(CallbackInfoReturnable<Boolean> info) {
      if (this.getPersistentData().m_128441_("ELTeleportBlock")) {
         info.setReturnValue(false);
      }
   }
}
