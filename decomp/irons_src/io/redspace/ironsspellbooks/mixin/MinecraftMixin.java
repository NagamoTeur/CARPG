package io.redspace.ironsspellbooks.mixin;

import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Minecraft.class})
public class MinecraftMixin {
   @Inject(
      method = {"shouldEntityAppearGlowing"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void changeGlowOutline(Entity pEntity, CallbackInfoReturnable<Boolean> cir) {
      if (Minecraft.m_91087_().f_91074_ != null
         && Minecraft.m_91087_().f_91074_.m_21023_((MobEffect)MobEffectRegistry.PLANAR_SIGHT.get())
         && pEntity instanceof LivingEntity
         && Mth.m_14154_((float)(pEntity.m_20186_() - Minecraft.m_91087_().f_91074_.m_20186_())) < 18.0F) {
         cir.setReturnValue(true);
      }
   }
}
