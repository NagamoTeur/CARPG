package daripher.skilltree.mixin.minecraft;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({AbstractArrow.class})
public abstract class AbstractArrowMixin {
   @Shadow
   protected boolean f_36703_;

   @Inject(
      method = {"playerTouch"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void pickupAsItem(Player player, CallbackInfo callback) {
      AbstractArrow arrow = (AbstractArrow)this;
      if (!arrow.f_19853_.f_46443_) {
         ItemStack pickupItem = this.m_7941_();
         if (pickupItem != null && pickupItem.m_204117_(ItemTags.f_13161_)) {
            if (arrow.f_36706_ <= 0) {
               if (this.f_36703_ || arrow.m_36797_()) {
                  if (arrow.f_36705_ == Pickup.ALLOWED) {
                     ItemEntity item = new ItemEntity(arrow.f_19853_, player.m_20185_(), player.m_20186_(), player.m_20189_(), pickupItem);
                     arrow.f_19853_.m_7967_(item);
                     item.m_32010_(0);
                     arrow.m_146870_();
                     callback.cancel();
                  }
               }
            }
         }
      }
   }

   @Shadow
   protected abstract ItemStack m_7941_();
}
