package io.redspace.ironsspellbooks.mixin;

import io.redspace.ironsspellbooks.entity.mobs.MagicSummon;
import io.redspace.ironsspellbooks.item.curios.CurioBaseItem;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Entity.class})
public abstract class EntityMixin {
   @Shadow
   SynchedEntityData f_19804_;
   @Shadow
   static EntityDataAccessor<Integer> f_146800_;

   @Shadow
   public abstract boolean m_20229_(double var1, double var3, double var5);

   @Inject(
      method = {"isAlliedTo(Lnet/minecraft/world/entity/Entity;)Z"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void isAlliedTo(Entity entity, CallbackInfoReturnable<Boolean> cir) {
      Entity self = (Entity)this;
      if (entity instanceof MagicSummon summon && summon.getSummoner() != null) {
         cir.setReturnValue(self.m_7307_(summon.getSummoner()) || self.equals(summon.getSummoner()));
      }
   }

   @Inject(
      method = {"setTicksFrozen"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void setTicksFrozen(int pTicksFrozen, CallbackInfo ci) {
      if (this instanceof LivingEntity livingEntity && livingEntity.m_21023_((MobEffect)MobEffectRegistry.CHILLED.get())) {
         int currentTicks = ((Entity)this).m_146888_();
         int deltaTicks = pTicksFrozen - currentTicks;
         if (deltaTicks > 0) {
            deltaTicks *= 2;
            this.f_19804_.m_135381_(f_146800_, currentTicks + deltaTicks);
            ci.cancel();
         }
      }
   }

   @Inject(
      method = {"isInvisibleTo"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void isInvisibleTo(Player player, CallbackInfoReturnable<Boolean> cir) {
      if (((CurioBaseItem)ItemRegistry.INVISIBILITY_RING.get()).isEquippedBy(player)) {
         cir.setReturnValue(false);
      }
   }
}
