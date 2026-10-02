package com.obscuria.aquamirae.registry;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AquamiraeParticleTypes {
   public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, "aquamirae");
   public static final RegistryObject<SimpleParticleType> SHINE = REGISTRY.register("shine", () -> new SimpleParticleType(true));
   public static final RegistryObject<SimpleParticleType> GHOST_SHINE = REGISTRY.register("ghost_shine", () -> new SimpleParticleType(true));
   public static final RegistryObject<SimpleParticleType> GHOST = REGISTRY.register("ghost", () -> new SimpleParticleType(true));
   public static final RegistryObject<SimpleParticleType> ELECTRIC = REGISTRY.register("electric", () -> new SimpleParticleType(true));
}
