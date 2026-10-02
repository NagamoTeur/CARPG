package com.aizistral.enigmaticlegacy.mixin;

import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.registries.EnigmaticItems;
import java.util.Optional;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.hoglin.HoglinAi;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({HoglinAi.class})
public class MixinHoglinTasks {
   @Inject(
      at = {@At("RETURN")},
      method = {"findNearestValidAttackTarget"},
      cancellable = true
   )
   private static void onHoglinShallAttack(Hoglin hoglin, CallbackInfoReturnable<Optional<? extends LivingEntity>> info) {
      Optional<? extends LivingEntity> returnedTarget = (Optional<? extends LivingEntity>)info.getReturnValue();
      if (returnedTarget.isPresent() && returnedTarget.orElse(null) instanceof Player) {
         Player returnedPlayer = (Player)returnedTarget.orElse(null);
         if (SuperpositionHandler.hasItem(returnedPlayer, EnigmaticItems.ANIMAL_GUIDEBOOK)) {
            info.setReturnValue(Optional.empty());
         }
      }
   }
}
