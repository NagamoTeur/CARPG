package shadows.apotheosis.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.monster.Slime;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin({Slime.class})
public class SlimeMixin {
   @Inject(
      at = {@At(
         value = "INVOKE",
         target = "net/minecraft/world/level/Level.addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"
      )},
      method = {"remove(Lnet/minecraft/world/entity/Entity$RemovalReason;)V"},
      locals = LocalCapture.CAPTURE_FAILHARD,
      require = 1
   )
   public void apoth_markMovable(
      RemovalReason reason, CallbackInfo ci, int size, Component name, boolean noAI, float f, int j, int k, int l, float f1, float f2, Slime slime
   ) {
      if (noAI) {
         boolean isMoveable = ((Slime)this).getPersistentData().m_128471_("apotheosis:movable");
         if (isMoveable) {
            slime.getPersistentData().m_128379_("apotheosis:movable", true);
         }
      }
   }
}
