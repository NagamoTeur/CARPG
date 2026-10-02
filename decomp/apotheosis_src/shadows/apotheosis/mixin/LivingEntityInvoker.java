package shadows.apotheosis.mixin;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({LivingEntity.class})
public interface LivingEntityInvoker {
   @Invoker
   void callActuallyHurt(DamageSource var1, float var2);

   @Invoker
   boolean callCheckTotemDeathProtection(DamageSource var1);

   @Invoker
   SoundEvent callGetDeathSound();

   @Invoker
   float callGetSoundVolume();
}
