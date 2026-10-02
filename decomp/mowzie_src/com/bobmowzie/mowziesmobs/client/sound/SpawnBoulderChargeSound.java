package com.bobmowzie.mowziesmobs.client.sound;

import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.abilities.player.geomancy.SpawnBoulderAbility;
import com.bobmowzie.mowziesmobs.server.capability.AbilityCapability;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpawnBoulderChargeSound extends AbstractTickableSoundInstance {
   private final LivingEntity user;
   private final SpawnBoulderAbility ability;

   public SpawnBoulderChargeSound(LivingEntity user) {
      super((SoundEvent)MMSounds.EFFECT_GEOMANCY_BOULDER_CHARGE.get(), SoundSource.PLAYERS, SoundInstance.m_235150_());
      this.user = user;
      this.f_119573_ = 1.0F;
      this.f_119574_ = 0.95F;
      this.f_119575_ = (double)((float)user.m_20185_());
      this.f_119576_ = (double)((float)user.m_20186_());
      this.f_119577_ = (double)((float)user.m_20189_());
      AbilityCapability.IAbilityCapability capability = AbilityHandler.INSTANCE.getAbilityCapability(user);
      if (capability != null) {
         this.ability = (SpawnBoulderAbility)capability.getAbilityMap().get(AbilityHandler.SPAWN_BOULDER_ABILITY);
      } else {
         this.ability = null;
      }
   }

   public void m_7788_() {
      if (this.ability == null) {
         this.m_119609_();
      } else {
         if (!this.ability.isUsing() || this.ability.getCurrentSection().sectionType != AbilitySection.AbilitySectionType.STARTUP) {
            this.m_119609_();
         }
      }
   }
}
