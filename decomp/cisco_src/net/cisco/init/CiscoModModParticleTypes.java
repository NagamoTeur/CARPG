package net.cisco.init;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CiscoModModParticleTypes {
   public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, "cisco_mod");
   public static final RegistryObject<SimpleParticleType> DRAGON_SEEKER_PARTICLE = REGISTRY.register(
      "dragon_seeker_particle", () -> new SimpleParticleType(true)
   );
}
