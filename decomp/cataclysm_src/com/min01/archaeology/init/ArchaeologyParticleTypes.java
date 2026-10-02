package com.min01.archaeology.init;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ArchaeologyParticleTypes {
   public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, "minecraft");
   public static final RegistryObject<SimpleParticleType> DUST_PLUME = PARTICLES.register("dust_plume", () -> new SimpleParticleType(false));
}
