package com.bobmowzie.mowziesmobs.client.sound;

import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class BlackPinkSound extends AbstractTickableSoundInstance {
   private final AbstractMinecart minecart;

   public BlackPinkSound(AbstractMinecart minecart) {
      super((SoundEvent)MMSounds.MUSIC_BLACK_PINK.get(), SoundSource.NEUTRAL, SoundInstance.m_235150_());
      this.minecart = minecart;
   }

   public void m_7788_() {
      if (this.minecart.m_6084_()) {
         this.f_119575_ = (double)((float)this.minecart.m_20185_());
         this.f_119576_ = (double)((float)this.minecart.m_20186_());
         this.f_119577_ = (double)((float)this.minecart.m_20189_());
      } else {
         this.m_119609_();
      }
   }
}
