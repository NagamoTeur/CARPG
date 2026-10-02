package shadows.apotheosis.mixin;

import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({LivingEntity.class})
public abstract class MHFMixinLivingEntity {
   @Unique
   @Nullable
   private Float actualHealth = null;

   @Inject(
      method = {"readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V"},
      at = {@At("HEAD")}
   )
   private void maxhealthfix$readAdditionalSaveData(CompoundTag tag, CallbackInfo callback) {
      if (tag.m_128425_("Health", 99)) {
         float savedHealth = tag.m_128457_("Health");
         if (savedHealth > this.m_21233_() && savedHealth > 0.0F) {
            this.actualHealth = savedHealth;
         }
      }
   }

   @Inject(
      method = {"detectEquipmentUpdates()V"},
      at = {@At("RETURN")}
   )
   private void maxhealthfix$detectEquipmentUpdates(CallbackInfo callback) {
      if (this.actualHealth != null) {
         if (this.actualHealth > 0.0F && this.actualHealth > this.m_21223_()) {
            this.m_21153_(this.actualHealth);
         }

         this.actualHealth = null;
      }
   }

   @Shadow
   public abstract float m_21233_();

   @Shadow
   public abstract float m_21223_();

   @Shadow
   public abstract void m_21153_(float var1);
}
