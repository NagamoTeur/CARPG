package daripher.autoleveling.mixin.minecraft;

import java.util.Objects;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Wolf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Wolf.class})
public abstract class WolfMixin extends TamableAnimal {
   protected WolfMixin() {
      super(null, null);
   }

   @Redirect(
      method = {"setTame(Z)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/entity/animal/Wolf;setHealth(F)V",
         ordinal = 0
      )
   )
   private void setTame_inject1(Wolf entity, float health) {
   }

   @Inject(
      method = {"setTame(Z)V"},
      at = {@At("HEAD")}
   )
   private void setTame_inject2(boolean tamed, CallbackInfo callbackInfo) {
      if (tamed) {
         float healthPercentage = this.m_21223_() / this.m_21233_();
         Objects.requireNonNull(this.m_21051_(Attributes.f_22276_)).m_22100_(20.0);
         this.m_21153_(this.m_21233_() * healthPercentage);
      }
   }
}
