package com.aqutheseal.celestisynth.datagen.providers;

import com.aqutheseal.celestisynth.common.registry.CSSoundEvents;
import net.minecraft.data.DataGenerator;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import net.minecraftforge.registries.RegistryObject;

public class CSSoundProvider extends SoundDefinitionsProvider {
   public CSSoundProvider(DataGenerator generator, String modId, ExistingFileHelper helper) {
      super(generator, modId, helper);
   }

   public void registerSounds() {
      for (RegistryObject<SoundEvent> sounds : CSSoundEvents.SOUND_EVENTS.getEntries()) {
         this.add(
            (SoundEvent)sounds.get(),
            definition().subtitle("sound.celestisynth." + sounds.getId().m_135815_()).with(sound(sounds.getId().m_135815_()).stream())
         );
      }
   }
}
