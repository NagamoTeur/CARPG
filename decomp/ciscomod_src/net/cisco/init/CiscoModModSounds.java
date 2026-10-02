package net.cisco.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CiscoModModSounds {
   public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "cisco_mod");
   public static final RegistryObject<SoundEvent> EQUILLIBRIUM = REGISTRY.register(
      "equillibrium", () -> new SoundEvent(new ResourceLocation("cisco_mod", "equillibrium"))
   );
   public static final RegistryObject<SoundEvent> ARMOR = REGISTRY.register("armor", () -> new SoundEvent(new ResourceLocation("cisco_mod", "armor")));
   public static final RegistryObject<SoundEvent> HOLY1 = REGISTRY.register("holy1", () -> new SoundEvent(new ResourceLocation("cisco_mod", "holy1")));
   public static final RegistryObject<SoundEvent> THUNDERSWOOSH = REGISTRY.register(
      "thunderswoosh", () -> new SoundEvent(new ResourceLocation("cisco_mod", "thunderswoosh"))
   );
   public static final RegistryObject<SoundEvent> EVILSWORD = REGISTRY.register(
      "evilsword", () -> new SoundEvent(new ResourceLocation("cisco_mod", "evilsword"))
   );
   public static final RegistryObject<SoundEvent> WINDJUMP = REGISTRY.register("windjump", () -> new SoundEvent(new ResourceLocation("cisco_mod", "windjump")));
   public static final RegistryObject<SoundEvent> JUMP = REGISTRY.register("jump", () -> new SoundEvent(new ResourceLocation("cisco_mod", "jump")));
   public static final RegistryObject<SoundEvent> TELEPORT = REGISTRY.register("teleport", () -> new SoundEvent(new ResourceLocation("cisco_mod", "teleport")));
   public static final RegistryObject<SoundEvent> EAGLE = REGISTRY.register("eagle", () -> new SoundEvent(new ResourceLocation("cisco_mod", "eagle")));
   public static final RegistryObject<SoundEvent> ICESWING = REGISTRY.register("iceswing", () -> new SoundEvent(new ResourceLocation("cisco_mod", "iceswing")));
   public static final RegistryObject<SoundEvent> RAGE = REGISTRY.register("rage", () -> new SoundEvent(new ResourceLocation("cisco_mod", "rage")));
   public static final RegistryObject<SoundEvent> ADJUDICATORSWING = REGISTRY.register(
      "adjudicatorswing", () -> new SoundEvent(new ResourceLocation("cisco_mod", "adjudicatorswing"))
   );
   public static final RegistryObject<SoundEvent> WIND = REGISTRY.register("wind", () -> new SoundEvent(new ResourceLocation("cisco_mod", "wind")));
   public static final RegistryObject<SoundEvent> RAGSLAM = REGISTRY.register("ragslam", () -> new SoundEvent(new ResourceLocation("cisco_mod", "ragslam")));
   public static final RegistryObject<SoundEvent> RAGSLASH = REGISTRY.register("ragslash", () -> new SoundEvent(new ResourceLocation("cisco_mod", "ragslash")));
   public static final RegistryObject<SoundEvent> RAGSTAB = REGISTRY.register("ragstab", () -> new SoundEvent(new ResourceLocation("cisco_mod", "ragstab")));
   public static final RegistryObject<SoundEvent> POLLUXSLASH = REGISTRY.register(
      "polluxslash", () -> new SoundEvent(new ResourceLocation("cisco_mod", "polluxslash"))
   );
   public static final RegistryObject<SoundEvent> CASTORSLASH = REGISTRY.register(
      "castorslash", () -> new SoundEvent(new ResourceLocation("cisco_mod", "castorslash"))
   );
   public static final RegistryObject<SoundEvent> NIGHTFALLSLASH = REGISTRY.register(
      "nightfallslash", () -> new SoundEvent(new ResourceLocation("cisco_mod", "nightfallslash"))
   );
   public static final RegistryObject<SoundEvent> NIGHTFALLSLAM = REGISTRY.register(
      "nightfallslam", () -> new SoundEvent(new ResourceLocation("cisco_mod", "nightfallslam"))
   );
   public static final RegistryObject<SoundEvent> NIGHTFALLSTAB = REGISTRY.register(
      "nightfallstab", () -> new SoundEvent(new ResourceLocation("cisco_mod", "nightfallstab"))
   );
   public static final RegistryObject<SoundEvent> SKYSLASH = REGISTRY.register("skyslash", () -> new SoundEvent(new ResourceLocation("cisco_mod", "skyslash")));
   public static final RegistryObject<SoundEvent> SKYSPIN = REGISTRY.register("skyspin", () -> new SoundEvent(new ResourceLocation("cisco_mod", "skyspin")));
   public static final RegistryObject<SoundEvent> AZURE = REGISTRY.register("azure", () -> new SoundEvent(new ResourceLocation("cisco_mod", "azure")));
   public static final RegistryObject<SoundEvent> SKYSPLITTERSTAB = REGISTRY.register(
      "skysplitterstab", () -> new SoundEvent(new ResourceLocation("cisco_mod", "skysplitterstab"))
   );
   public static final RegistryObject<SoundEvent> THUNDER = REGISTRY.register("thunder", () -> new SoundEvent(new ResourceLocation("cisco_mod", "thunder")));
   public static final RegistryObject<SoundEvent> NIGHTFALLSOUND = REGISTRY.register(
      "nightfallsound", () -> new SoundEvent(new ResourceLocation("cisco_mod", "nightfallsound"))
   );
   public static final RegistryObject<SoundEvent> GLACIATE = REGISTRY.register("glaciate", () -> new SoundEvent(new ResourceLocation("cisco_mod", "glaciate")));
   public static final RegistryObject<SoundEvent> EQUISWING1 = REGISTRY.register(
      "equiswing1", () -> new SoundEvent(new ResourceLocation("cisco_mod", "equiswing1"))
   );
   public static final RegistryObject<SoundEvent> EQUISLASH = REGISTRY.register(
      "equislash", () -> new SoundEvent(new ResourceLocation("cisco_mod", "equislash"))
   );
   public static final RegistryObject<SoundEvent> EQUISLAM = REGISTRY.register("equislam", () -> new SoundEvent(new ResourceLocation("cisco_mod", "equislam")));
   public static final RegistryObject<SoundEvent> EQUISTAB = REGISTRY.register("equistab", () -> new SoundEvent(new ResourceLocation("cisco_mod", "equistab")));
   public static final RegistryObject<SoundEvent> WHEREISYOURGODNOW = REGISTRY.register(
      "whereisyourgodnow", () -> new SoundEvent(new ResourceLocation("cisco_mod", "whereisyourgodnow"))
   );
   public static final RegistryObject<SoundEvent> BRAVESOUL = REGISTRY.register(
      "bravesoul", () -> new SoundEvent(new ResourceLocation("cisco_mod", "bravesoul"))
   );
   public static final RegistryObject<SoundEvent> WHEREISYOURGODBOSS = REGISTRY.register(
      "whereisyourgodboss", () -> new SoundEvent(new ResourceLocation("cisco_mod", "whereisyourgodboss"))
   );
   public static final RegistryObject<SoundEvent> BRAVESOULBOSS = REGISTRY.register(
      "bravesoulboss", () -> new SoundEvent(new ResourceLocation("cisco_mod", "bravesoulboss"))
   );
   public static final RegistryObject<SoundEvent> LIGHTABILITY = REGISTRY.register(
      "lightability", () -> new SoundEvent(new ResourceLocation("cisco_mod", "lightability"))
   );
   public static final RegistryObject<SoundEvent> COINFLIP = REGISTRY.register("coinflip", () -> new SoundEvent(new ResourceLocation("cisco_mod", "coinflip")));
   public static final RegistryObject<SoundEvent> ABSOLUTEEQACTIVE = REGISTRY.register(
      "absoluteeqactive", () -> new SoundEvent(new ResourceLocation("cisco_mod", "absoluteeqactive"))
   );
   public static final RegistryObject<SoundEvent> ABSOLUTEEQSLASH = REGISTRY.register(
      "absoluteeqslash", () -> new SoundEvent(new ResourceLocation("cisco_mod", "absoluteeqslash"))
   );
   public static final RegistryObject<SoundEvent> NIGHTXRAGDUAL = REGISTRY.register(
      "nightxragdual", () -> new SoundEvent(new ResourceLocation("cisco_mod", "nightxragdual"))
   );
   public static final RegistryObject<SoundEvent> LOOTBAGOPEN = REGISTRY.register(
      "lootbagopen", () -> new SoundEvent(new ResourceLocation("cisco_mod", "lootbagopen"))
   );
   public static final RegistryObject<SoundEvent> CHASEDISC = REGISTRY.register(
      "chasedisc", () -> new SoundEvent(new ResourceLocation("cisco_mod", "chasedisc"))
   );
   public static final RegistryObject<SoundEvent> OVERWATCHDISC = REGISTRY.register(
      "overwatchdisc", () -> new SoundEvent(new ResourceLocation("cisco_mod", "overwatchdisc"))
   );
   public static final RegistryObject<SoundEvent> OVERWATCHBOSS = REGISTRY.register(
      "overwatchboss", () -> new SoundEvent(new ResourceLocation("cisco_mod", "overwatchboss"))
   );
   public static final RegistryObject<SoundEvent> CHASEBOSS = REGISTRY.register(
      "chaseboss", () -> new SoundEvent(new ResourceLocation("cisco_mod", "chaseboss"))
   );
   public static final RegistryObject<SoundEvent> BJORN_SCYTHE_ACTIVE = REGISTRY.register(
      "bjorn_scythe_active", () -> new SoundEvent(new ResourceLocation("cisco_mod", "bjorn_scythe_active"))
   );
   public static final RegistryObject<SoundEvent> FROSTFANGSLASH = REGISTRY.register(
      "frostfangslash", () -> new SoundEvent(new ResourceLocation("cisco_mod", "frostfangslash"))
   );
   public static final RegistryObject<SoundEvent> SUPREMENIGHTFALLSLASH = REGISTRY.register(
      "supremenightfallslash", () -> new SoundEvent(new ResourceLocation("cisco_mod", "supremenightfallslash"))
   );
   public static final RegistryObject<SoundEvent> SUPREMENIGHTFALLACTIVE = REGISTRY.register(
      "supremenightfallactive", () -> new SoundEvent(new ResourceLocation("cisco_mod", "supremenightfallactive"))
   );
   public static final RegistryObject<SoundEvent> BJORN_SCYTHE_SLASH = REGISTRY.register(
      "bjorn_scythe_slash", () -> new SoundEvent(new ResourceLocation("cisco_mod", "bjorn_scythe_slash"))
   );
   public static final RegistryObject<SoundEvent> FROSTFANGACTIVE = REGISTRY.register(
      "frostfangactive", () -> new SoundEvent(new ResourceLocation("cisco_mod", "frostfangactive"))
   );
   public static final RegistryObject<SoundEvent> DESCENDEDACTIVE = REGISTRY.register(
      "descendedactive", () -> new SoundEvent(new ResourceLocation("cisco_mod", "descendedactive"))
   );
   public static final RegistryObject<SoundEvent> SOVEREIGNACTIVE = REGISTRY.register(
      "sovereignactive", () -> new SoundEvent(new ResourceLocation("cisco_mod", "sovereignactive"))
   );
}
