package com.hollingsworth.arsnouveau.setup;

import com.hollingsworth.arsnouveau.api.sound.SpellSound;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundRegistry {
   public static final DeferredRegister<SoundEvent> SOUND_REG = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "ars_nouveau");
   public static final String DEFAULT_SOUND_LIB = "fire_family";
   public static final String GAIA_SOUND_LIB = "gaia_family";
   public static final String TEMPESTRY_SOUND_LIB = "tempestry_family";
   public static final String FIRE_SOUND_LIB = "fire_family_2";
   public static final String NO_SOUND_LIB = "empty";
   public static final String EA_CHANNEL = "ea_channel";
   public static final String EA_FINISH = "ea_finish";
   public static RegistryObject<SoundEvent> DEFAULT_FAMILY = SOUND_REG.register("fire_family", () -> makeSound("fire_family"));
   public static RegistryObject<SoundEvent> EMPTY_SOUND_FAMILY = SOUND_REG.register("empty", () -> makeSound("empty"));
   public static RegistryObject<SoundEvent> APPARATUS_CHANNEL = SOUND_REG.register("ea_channel", () -> makeSound("ea_channel"));
   public static RegistryObject<SoundEvent> APPARATUS_FINISH = SOUND_REG.register("ea_finish", () -> makeSound("ea_finish"));
   public static RegistryObject<SoundEvent> GAIA_FAMILY = SOUND_REG.register("gaia_family", () -> makeSound("gaia_family"));
   public static RegistryObject<SoundEvent> TEMPESTRY_FAMILY = SOUND_REG.register("tempestry_family", () -> makeSound("tempestry_family"));
   public static RegistryObject<SoundEvent> FIRE_FAMILY = SOUND_REG.register("fire_family_2", () -> makeSound("fire_family_2"));
   public static RegistryObject<SoundEvent> ARIA_BIBLIO = SOUND_REG.register("aria_biblio", () -> makeSound("aria_biblio"));
   public static RegistryObject<SoundEvent> WILD_HUNT = SOUND_REG.register("firel_the_wild_hunt", () -> makeSound("firel_the_wild_hunt"));
   public static RegistryObject<SoundEvent> SOUND_OF_GLASS = SOUND_REG.register("thistle_the_sound_of_glass", () -> makeSound("thistle_the_sound_of_glass"));
   public static SpellSound DEFAULT_SPELL_SOUND;
   public static SpellSound EMPTY_SPELL_SOUND;
   public static SpellSound GAIA_SPELL_SOUND;
   public static SpellSound TEMPESTRY_SPELL_SOUND;
   public static SpellSound FIRE_SPELL_SOUND;

   static SoundEvent makeSound(String name) {
      return new SoundEvent(new ResourceLocation("ars_nouveau", name));
   }
}
