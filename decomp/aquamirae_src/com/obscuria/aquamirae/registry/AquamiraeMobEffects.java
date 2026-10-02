package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.common.effects.AbyssBlindnessMobEffect;
import com.obscuria.aquamirae.common.effects.ArmorDecreaseMobEffect;
import com.obscuria.aquamirae.common.effects.CrystallizationMobEffect;
import com.obscuria.aquamirae.common.effects.HealthDecreaseMobEffect;
import com.obscuria.aquamirae.common.effects.StrongArmorMobEffect;
import com.obscuria.aquamirae.common.effects.SwimSpeedMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AquamiraeMobEffects {
   public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, "aquamirae");
   public static final RegistryObject<MobEffect> CRYSTALLIZATION = REGISTRY.register("crystallization", CrystallizationMobEffect::new);
   public static final RegistryObject<MobEffect> HEALTH_DECREASE = REGISTRY.register("health_decrease", HealthDecreaseMobEffect::new);
   public static final RegistryObject<MobEffect> ARMOR_DECREASE = REGISTRY.register("armor_decrease", ArmorDecreaseMobEffect::new);
   public static final RegistryObject<MobEffect> SWIM_SPEED = REGISTRY.register("swim_speed", SwimSpeedMobEffect::new);
   public static final RegistryObject<MobEffect> STRONG_ARMOR = REGISTRY.register("strong_armor", StrongArmorMobEffect::new);
   public static final RegistryObject<MobEffect> ABYSS_BLINDNESS = REGISTRY.register("abyss_blindness", AbyssBlindnessMobEffect::new);
}
