package com.bobmowzie.mowziesmobs.client.sound;

import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NagaSwoopSound extends AbstractTickableSoundInstance {
   private final Entity naga;
   int ticksExisted = 0;
   boolean active = true;

   public NagaSwoopSound(Entity naga) {
      super((SoundEvent)MMSounds.ENTITY_NAGA_SWOOP.get(), SoundSource.HOSTILE, SoundInstance.m_235150_());
      this.naga = naga;
      this.f_119573_ = 2.0F;
      this.f_119574_ = 1.2F;
      this.f_119575_ = (double)((float)naga.m_20185_());
      this.f_119576_ = (double)((float)naga.m_20186_());
      this.f_119577_ = (double)((float)naga.m_20189_());
      this.f_119578_ = false;
   }

   public void m_7788_() {
      if (this.naga != null) {
         this.active = true;
         this.f_119575_ = (double)((float)this.naga.m_20185_());
         this.f_119576_ = (double)((float)this.naga.m_20186_());
         this.f_119577_ = (double)((float)this.naga.m_20189_());
         if (!this.naga.m_6084_()) {
            this.active = false;
            this.m_119609_();
         }
      }

      this.ticksExisted++;
   }
}
