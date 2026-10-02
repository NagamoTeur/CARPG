package com.bobmowzie.mowziesmobs.client.sound;

import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthana;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.ilexiconn.llibrary.client.model.tools.ControlledAnimation;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SunblockSound extends AbstractTickableSoundInstance {
   private final LivingEntity entity;
   int ticksExisted = 0;
   ControlledAnimation volumeControl;
   boolean active = true;

   public SunblockSound(LivingEntity entity) {
      super((SoundEvent)MMSounds.ENTITY_UMVUTHANA_HEAL_LOOP.get(), SoundSource.NEUTRAL, SoundInstance.m_235150_());
      this.entity = entity;
      this.f_119573_ = 4.0F;
      this.f_119574_ = 1.0F;
      this.f_119575_ = (double)((float)entity.m_20185_());
      this.f_119576_ = (double)((float)entity.m_20186_());
      this.f_119577_ = (double)((float)entity.m_20189_());
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

      if (this.entity != null) {
         this.active = true;
         this.f_119575_ = (double)((float)this.entity.m_20185_());
         this.f_119576_ = (double)((float)this.entity.m_20186_());
         this.f_119577_ = (double)((float)this.entity.m_20189_());
         boolean umvuthanaHealing = false;
         if (this.entity instanceof EntityUmvuthana hasSunblock) {
            ;
         }

         boolean hasSunblock = this.entity.m_21023_((MobEffect)EffectHandler.SUNBLOCK.get());
         this.active = umvuthanaHealing || hasSunblock;
         if (!this.entity.m_6084_()) {
            this.active = false;
         }
      } else {
         this.active = false;
      }

      this.ticksExisted++;
   }
}
