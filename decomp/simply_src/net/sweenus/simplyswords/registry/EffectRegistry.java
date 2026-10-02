package net.sweenus.simplyswords.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.sweenus.simplyswords.effect.BattleFatigueEffect;
import net.sweenus.simplyswords.effect.EchoEffect;
import net.sweenus.simplyswords.effect.FreezeEffect;
import net.sweenus.simplyswords.effect.ImmolationEffect;
import net.sweenus.simplyswords.effect.OmenEffect;
import net.sweenus.simplyswords.effect.OnslaughtEffect;
import net.sweenus.simplyswords.effect.StormEffect;
import net.sweenus.simplyswords.effect.WardEffect;
import net.sweenus.simplyswords.effect.WatcherEffect;
import net.sweenus.simplyswords.effect.WildfireEffect;

public class EffectRegistry {
   public static final DeferredRegister<MobEffect> EFFECT = DeferredRegister.create("simplyswords", Registry.f_122900_);
   public static final RegistrySupplier<MobEffect> OMEN = EFFECT.register("omen", () -> new OmenEffect(MobEffectCategory.HARMFUL, 1124687));
   public static final RegistrySupplier<MobEffect> WATCHER = EFFECT.register("watcher", () -> new WatcherEffect(MobEffectCategory.HARMFUL, 1124687));
   public static final RegistrySupplier<MobEffect> WILDFIRE = EFFECT.register("wildfire", () -> new WildfireEffect(MobEffectCategory.HARMFUL, 1124687));
   public static final RegistrySupplier<MobEffect> STORM = EFFECT.register("storm", () -> new StormEffect(MobEffectCategory.HARMFUL, 1124687));
   public static final RegistrySupplier<MobEffect> FREEZE = EFFECT.register("freeze", () -> new FreezeEffect(MobEffectCategory.HARMFUL, 1124687));
   public static final RegistrySupplier<MobEffect> WARD = EFFECT.register("ward", () -> new WardEffect(MobEffectCategory.BENEFICIAL, 1124687));
   public static final RegistrySupplier<MobEffect> IMMOLATION = EFFECT.register("immolation", () -> new ImmolationEffect(MobEffectCategory.BENEFICIAL, 1124687));
   public static final RegistrySupplier<MobEffect> ECHO = EFFECT.register("echo", () -> new EchoEffect(MobEffectCategory.HARMFUL, 1124687));
   public static final RegistrySupplier<MobEffect> ONSLAUGHT = EFFECT.register("onslaught", () -> new OnslaughtEffect(MobEffectCategory.BENEFICIAL, 1124687));
   public static final RegistrySupplier<MobEffect> BATTLE_FATIGUE = EFFECT.register(
      "battle_fatigue", () -> new BattleFatigueEffect(MobEffectCategory.HARMFUL, 1124687)
   );
}
