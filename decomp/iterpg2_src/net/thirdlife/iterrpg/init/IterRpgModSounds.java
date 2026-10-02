package net.thirdlife.iterrpg.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class IterRpgModSounds {
   public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "iter_rpg");
   public static final RegistryObject<SoundEvent> GRIEVER_AMBIENT = REGISTRY.register(
      "griever_ambient", () -> new SoundEvent(new ResourceLocation("iter_rpg", "griever_ambient"))
   );
   public static final RegistryObject<SoundEvent> GRIEVER_DEATH = REGISTRY.register(
      "griever_death", () -> new SoundEvent(new ResourceLocation("iter_rpg", "griever_death"))
   );
   public static final RegistryObject<SoundEvent> GRIEVER_SCREAM = REGISTRY.register(
      "griever_scream", () -> new SoundEvent(new ResourceLocation("iter_rpg", "griever_scream"))
   );
   public static final RegistryObject<SoundEvent> FLAIL_STRIKE = REGISTRY.register(
      "flail_strike", () -> new SoundEvent(new ResourceLocation("iter_rpg", "flail_strike"))
   );
   public static final RegistryObject<SoundEvent> GRIEVER_HURT = REGISTRY.register(
      "griever_hurt", () -> new SoundEvent(new ResourceLocation("iter_rpg", "griever_hurt"))
   );
   public static final RegistryObject<SoundEvent> WEEPER_AMBIENT = REGISTRY.register(
      "weeper_ambient", () -> new SoundEvent(new ResourceLocation("iter_rpg", "weeper_ambient"))
   );
   public static final RegistryObject<SoundEvent> WEEPER_DEATH = REGISTRY.register(
      "weeper_death", () -> new SoundEvent(new ResourceLocation("iter_rpg", "weeper_death"))
   );
   public static final RegistryObject<SoundEvent> WEEPER_HURT = REGISTRY.register(
      "weeper_hurt", () -> new SoundEvent(new ResourceLocation("iter_rpg", "weeper_hurt"))
   );
   public static final RegistryObject<SoundEvent> MUDKIN_HURT = REGISTRY.register(
      "mudkin_hurt", () -> new SoundEvent(new ResourceLocation("iter_rpg", "mudkin_hurt"))
   );
   public static final RegistryObject<SoundEvent> MUDKIN_DEATH = REGISTRY.register(
      "mudkin_death", () -> new SoundEvent(new ResourceLocation("iter_rpg", "mudkin_death"))
   );
   public static final RegistryObject<SoundEvent> MUDKIN_RUN = REGISTRY.register(
      "mudkin_run", () -> new SoundEvent(new ResourceLocation("iter_rpg", "mudkin_run"))
   );
   public static final RegistryObject<SoundEvent> MUDKIN_AMBIENT = REGISTRY.register(
      "mudkin_ambient", () -> new SoundEvent(new ResourceLocation("iter_rpg", "mudkin_ambient"))
   );
   public static final RegistryObject<SoundEvent> MOURNFUL_ABYSS_RECORD = REGISTRY.register(
      "mournful_abyss_record", () -> new SoundEvent(new ResourceLocation("iter_rpg", "mournful_abyss_record"))
   );
}
