package com.bobmowzie.mowziesmobs.client.sound;

import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.ilexiconn.llibrary.client.model.tools.ControlledAnimation;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IceBreathSound extends AbstractTickableSoundInstance {
   private final Entity iceBreath;
   int ticksExisted = 0;
   ControlledAnimation volumeControl;
   boolean active = true;

   public IceBreathSound(Entity icebreath) {
      super((SoundEvent)MMSounds.ENTITY_FROSTMAW_ICEBREATH.get(), SoundSource.NEUTRAL, SoundInstance.m_235150_());
      this.iceBreath = icebreath;
      this.f_119573_ = 3.0F;
      this.f_119574_ = 1.0F;
      this.f_119575_ = (double)((float)icebreath.m_20185_());
      this.f_119576_ = (double)((float)icebreath.m_20186_());
      this.f_119577_ = (double)((float)icebreath.m_20189_());
      this.volumeControl = new ControlledAnimation(10);
      this.f_119578_ = true;
   }

   public void m_7788_() {
      if (this.active) {
         this.volumeControl.increaseTimer();
      } else {
         this.volumeControl.decreaseTimer();
      }

      this.f_119573_ = this.volumeControl.getAnimationFraction();
      if ((double)this.volumeControl.getAnimationFraction() <= 0.05) {
         this.m_119609_();
      }

      if (this.iceBreath != null) {
         this.active = true;
         this.f_119575_ = (double)((float)this.iceBreath.m_20185_());
         this.f_119576_ = (double)((float)this.iceBreath.m_20186_());
         this.f_119577_ = (double)((float)this.iceBreath.m_20189_());
         if (!this.iceBreath.m_6084_()) {
            this.active = false;
         }
      } else {
         this.active = false;
      }

      this.ticksExisted++;
   }
}
