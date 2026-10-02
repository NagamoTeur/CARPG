package com.github.L_Ender.cataclysm.client.sound;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance.Attenuation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public abstract class ItemTickableSound extends AbstractTickableSoundInstance {
   protected final LivingEntity user;

   public ItemTickableSound(LivingEntity user, SoundEvent soundEvent) {
      super(soundEvent, SoundSource.PLAYERS, SoundInstance.m_235150_());
      this.user = user;
      this.f_119580_ = Attenuation.LINEAR;
      this.f_119578_ = true;
      this.f_119575_ = (double)((float)this.user.m_20185_());
      this.f_119576_ = (double)((float)this.user.m_20186_());
      this.f_119577_ = (double)((float)this.user.m_20189_());
      this.f_119579_ = 0;
   }

   public boolean m_7767_() {
      return !this.user.m_20067_()
         && this.user.m_6117_()
         && (this.isValidItem(this.user.m_21120_(InteractionHand.MAIN_HAND)) || this.isValidItem(this.user.m_21120_(InteractionHand.OFF_HAND)));
   }

   public void m_7788_() {
      ItemStack itemStack = ItemStack.f_41583_;
      if (this.user.m_6117_()) {
         if (this.isValidItem(this.user.m_21120_(InteractionHand.MAIN_HAND))) {
            itemStack = this.user.m_21120_(InteractionHand.MAIN_HAND);
         }

         if (this.isValidItem(this.user.m_21120_(InteractionHand.OFF_HAND))) {
            itemStack = this.user.m_21120_(InteractionHand.OFF_HAND);
         }
      }

      if (this.user.m_6084_() && !itemStack.m_41619_()) {
         this.f_119575_ = (double)((float)this.user.m_20185_());
         this.f_119576_ = (double)((float)this.user.m_20186_());
         this.f_119577_ = (double)((float)this.user.m_20189_());
         this.tickVolume(itemStack);
      } else {
         this.m_119609_();
      }
   }

   protected abstract void tickVolume(ItemStack var1);

   public abstract boolean isValidItem(ItemStack var1);

   public boolean m_7784_() {
      return true;
   }

   public boolean isSameEntity(LivingEntity user) {
      return this.user.m_6084_() && this.user.m_19879_() == user.m_19879_();
   }
}
