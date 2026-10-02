package com.aqutheseal.celestisynth.mixin;

import com.aqutheseal.celestisynth.api.item.CSWeapon;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Inventory.class})
public abstract class InventoryMixin {
   private InventoryMixin() {
      throw new IllegalAccessError("Attempted to instantiate a Mixin Class!");
   }

   @Shadow
   public abstract ItemStack m_36056_();

   @Inject(
      method = {"setPickedItem"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void celestisynth$setPickedItem(ItemStack stack, CallbackInfo ci) {
      this.cancelCI(ci);
   }

   @Inject(
      method = {"pickSlot"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void celestisynth$pickSlot(int slot, CallbackInfo ci) {
      this.cancelCI(ci);
   }

   @Inject(
      method = {"swapPaint"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void celestisynth$swapPaint(double slot, CallbackInfo ci) {
      this.cancelCI(ci);
   }

   private void cancelCI(CallbackInfo ci) {
      ItemStack selected = this.m_36056_();
      if (selected.m_41720_() instanceof CSWeapon) {
         CompoundTag controllerTag = selected.m_41737_("csController");
         if (controllerTag != null && controllerTag.m_128471_("cs.hasAnimationBegun")) {
            ci.cancel();
         }
      }
   }
}
