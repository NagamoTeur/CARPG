package dev.latvian.mods.kubejs.core;

import dev.architectury.hooks.level.entity.ItemEntityHooks;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

@RemapPrefixForJS("kjs$")
public interface ItemEntityKJS extends EntityKJS {
   default ItemEntity kjs$self() {
      return (ItemEntity)this;
   }

   @Nullable
   @Override
   default ItemStack kjs$getItem() {
      ItemStack stack = this.kjs$self().m_32055_();
      return stack.m_41619_() ? null : stack;
   }

   default int kjs$getLifespan() {
      return ItemEntityHooks.lifespan(this.kjs$self()).getAsInt();
   }

   default void kjs$setLifespan(int lifespan) {
      ItemEntityHooks.lifespan(this.kjs$self()).accept(lifespan);
   }

   default void kjs$setDefaultPickUpDelay() {
      this.kjs$self().m_32010_(10);
   }

   default void kjs$setNoPickUpDelay() {
      this.kjs$self().m_32010_(0);
   }

   default void kjs$setInfinitePickUpDelay() {
      this.kjs$self().m_32010_(32767);
   }

   default void kjs$setNoDespawn() {
      this.kjs$self().m_149678_();
   }

   default int kjs$getTicksUntilDespawn() {
      return this.kjs$getLifespan() - this.kjs$self().f_31985_;
   }

   default void kjs$setTicksUntilDespawn(int ticks) {
      this.kjs$self().f_31985_ = this.kjs$getLifespan() - ticks;
   }
}
