package com.bobmowzie.mowziesmobs.client.sound;

import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySunstrike;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SunstrikeSound extends AbstractTickableSoundInstance {
   private final EntitySunstrike sunstrike;

   public SunstrikeSound(EntitySunstrike sunstrike) {
      super((SoundEvent)MMSounds.SUNSTRIKE.get(), SoundSource.NEUTRAL, SoundInstance.m_235150_());
      this.sunstrike = sunstrike;
      this.f_119573_ = 1.5F;
      this.f_119574_ = 1.1F;
      this.f_119575_ = (double)((float)sunstrike.m_20185_());
      this.f_119576_ = (double)((float)sunstrike.m_20186_());
      this.f_119577_ = (double)((float)sunstrike.m_20189_());
   }

   public void m_7788_() {
      if (!this.sunstrike.m_6084_()) {
         this.m_119609_();
      }
   }
}
