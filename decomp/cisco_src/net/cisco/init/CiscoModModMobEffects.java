package net.cisco.init;

import net.cisco.potion.AdventofAscensionMobEffect;
import net.cisco.potion.AvatarofTheDarkOneMobEffect;
import net.cisco.potion.CiscoRageMobEffect;
import net.cisco.potion.CiscosMightMobEffect;
import net.cisco.potion.DebilitatingDesireMobEffect;
import net.cisco.potion.FellFrostMobEffect;
import net.cisco.potion.FellflameMobEffect;
import net.cisco.potion.GeminiBlightMobEffect;
import net.cisco.potion.HellbrandEffectMobEffect;
import net.cisco.potion.SovereignSplendourMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CiscoModModMobEffects {
   public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, "cisco_mod");
   public static final RegistryObject<MobEffect> CISCOS_MIGHT = REGISTRY.register("ciscos_might", () -> new CiscosMightMobEffect());
   public static final RegistryObject<MobEffect> CISCO_RAGE = REGISTRY.register("cisco_rage", () -> new CiscoRageMobEffect());
   public static final RegistryObject<MobEffect> GEMINI_BLIGHT = REGISTRY.register("gemini_blight", () -> new GeminiBlightMobEffect());
   public static final RegistryObject<MobEffect> ADVENTOF_ASCENSION = REGISTRY.register("adventof_ascension", () -> new AdventofAscensionMobEffect());
   public static final RegistryObject<MobEffect> FELLFLAME = REGISTRY.register("fellflame", () -> new FellflameMobEffect());
   public static final RegistryObject<MobEffect> FELL_FROST = REGISTRY.register("fell_frost", () -> new FellFrostMobEffect());
   public static final RegistryObject<MobEffect> DEBILITATING_DESIRE = REGISTRY.register("debilitating_desire", () -> new DebilitatingDesireMobEffect());
   public static final RegistryObject<MobEffect> HELLBRAND_EFFECT = REGISTRY.register("hellbrand_effect", () -> new HellbrandEffectMobEffect());
   public static final RegistryObject<MobEffect> AVATAROF_THE_DARK_ONE = REGISTRY.register("avatarof_the_dark_one", () -> new AvatarofTheDarkOneMobEffect());
   public static final RegistryObject<MobEffect> SOVEREIGN_SPLENDOUR = REGISTRY.register("sovereign_splendour", () -> new SovereignSplendourMobEffect());
}
