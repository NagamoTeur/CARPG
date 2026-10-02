package net.thirdlife.iterrpg.init;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thirdlife.iterrpg.potion.ArcaneConductionMobEffect;
import net.thirdlife.iterrpg.potion.CursedMobEffect;

public class IterRpgModMobEffects {
   public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, "iter_rpg");
   public static final RegistryObject<MobEffect> CURSED = REGISTRY.register("cursed", () -> new CursedMobEffect());
   public static final RegistryObject<MobEffect> ARCANE_CONDUCTION = REGISTRY.register("arcane_conduction", () -> new ArcaneConductionMobEffect());
}
