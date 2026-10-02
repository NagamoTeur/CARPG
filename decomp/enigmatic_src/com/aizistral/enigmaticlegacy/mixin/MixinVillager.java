package com.aizistral.enigmaticlegacy.mixin;

import com.aizistral.enigmaticlegacy.handlers.EnigmaticEventHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Villager.class})
public abstract class MixinVillager extends AbstractVillager {
   public MixinVillager() {
      super(null, null);
      throw new IllegalStateException("Can't touch this");
   }

   @Inject(
      method = {"spawnGolemIfNeeded"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private void onGolemCreation(ServerLevel level, long gameTime, int villagersRequired, CallbackInfo info) {
      if (EnigmaticEventHandler.DESOLATION_BOXES.values().stream().anyMatch(this.m_20191_()::m_82381_)) {
         info.cancel();
      }
   }
}
