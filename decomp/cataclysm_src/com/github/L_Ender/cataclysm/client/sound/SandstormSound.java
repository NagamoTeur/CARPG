package com.github.L_Ender.cataclysm.client.sound;

import com.github.L_Ender.cataclysm.entity.effect.Sandstorm_Entity;
import com.github.L_Ender.cataclysm.init.ModSounds;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance.Attenuation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class SandstormSound extends AbstractTickableSoundInstance {
   private final Sandstorm_Entity sandstom;

   public SandstormSound(Sandstorm_Entity sandstom) {
      super((SoundEvent)ModSounds.SANDSTORM.get(), SoundSource.HOSTILE, SoundInstance.m_235150_());
      this.sandstom = sandstom;
      this.f_119580_ = Attenuation.LINEAR;
      this.f_119578_ = true;
      this.f_119575_ = (double)((float)this.sandstom.m_20185_());
      this.f_119576_ = (double)((float)this.sandstom.m_20186_());
      this.f_119577_ = (double)((float)this.sandstom.m_20189_());
      this.f_119579_ = 0;
   }

   public boolean m_7767_() {
      return this.sandstom.m_6084_() && !this.sandstom.m_20067_();
   }

   public void m_7788_() {
      this.f_119575_ = (double)((float)this.sandstom.m_20185_());
      this.f_119576_ = (double)((float)this.sandstom.m_20186_());
      this.f_119577_ = (double)((float)this.sandstom.m_20189_());
      this.f_119573_ = 0.05F;
      this.f_119574_ = 1.0F;
   }

   public boolean m_7784_() {
      return true;
   }

   public boolean isSameEntity(Sandstorm_Entity entity) {
      return this.sandstom.m_6084_() && this.sandstom.m_19879_() == entity.m_19879_();
   }
}
