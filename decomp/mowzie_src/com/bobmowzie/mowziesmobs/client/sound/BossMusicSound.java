package com.bobmowzie.mowziesmobs.client.sound;

import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.ilexiconn.llibrary.client.model.tools.ControlledAnimation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance.Attenuation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class BossMusicSound extends AbstractTickableSoundInstance {
   private MowzieEntity boss;
   private int ticksExisted = 0;
   private int timeUntilFade;
   private final SoundEvent soundEvent;
   ControlledAnimation volumeControl;

   public BossMusicSound(SoundEvent sound, MowzieEntity boss) {
      super(sound, SoundSource.MUSIC, SoundInstance.m_235150_());
      this.boss = boss;
      this.soundEvent = sound;
      this.f_119580_ = Attenuation.NONE;
      this.f_119578_ = true;
      this.f_119579_ = 0;
      this.f_119575_ = boss.m_20185_();
      this.f_119576_ = boss.m_20186_();
      this.f_119577_ = boss.m_20189_();
      this.volumeControl = new ControlledAnimation(40);
      this.volumeControl.setTimer(20);
      this.f_119573_ = this.volumeControl.getAnimationFraction();
      this.timeUntilFade = 80;
   }

   public boolean m_7767_() {
      return BossMusicPlayer.bossMusic == this;
   }

   public void m_7788_() {
      if (this.boss != null && this.boss.m_6084_() && !this.boss.m_20067_()) {
         this.volumeControl.increaseTimer();
         this.timeUntilFade = 60;
      } else {
         if (this.boss != null && !this.boss.m_6084_()) {
            this.timeUntilFade = 0;
         }

         this.boss = null;
         if (this.timeUntilFade > 0) {
            this.timeUntilFade--;
         } else {
            this.volumeControl.decreaseTimer();
         }
      }

      if ((double)this.volumeControl.getAnimationFraction() < 0.025) {
         this.m_119609_();
         BossMusicPlayer.bossMusic = null;
      }

      this.f_119573_ = this.volumeControl.getAnimationFraction();
      if (this.ticksExisted % 100 == 0) {
         Minecraft.m_91087_().m_91397_().m_120186_();
      }

      this.ticksExisted++;
   }

   public void setBoss(MowzieEntity boss) {
      this.boss = boss;
   }

   public MowzieEntity getBoss() {
      return this.boss;
   }

   public SoundEvent getSoundEvent() {
      return this.soundEvent;
   }
}
