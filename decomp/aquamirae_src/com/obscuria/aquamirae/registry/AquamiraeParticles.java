package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.client.particle.ElectricParticle;
import com.obscuria.aquamirae.client.particle.GhostParticle;
import com.obscuria.aquamirae.client.particle.GhostShineParticle;
import com.obscuria.aquamirae.client.particle.ShineParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(
   bus = Bus.MOD,
   value = {Dist.CLIENT}
)
public class AquamiraeParticles {
   @SubscribeEvent
   public static void registerParticles(RegisterParticleProvidersEvent event) {
      event.register((ParticleType)AquamiraeParticleTypes.SHINE.get(), ShineParticle::provider);
      event.register((ParticleType)AquamiraeParticleTypes.GHOST_SHINE.get(), GhostShineParticle::provider);
      event.register((ParticleType)AquamiraeParticleTypes.GHOST.get(), GhostParticle::provider);
      event.register((ParticleType)AquamiraeParticleTypes.ELECTRIC.get(), ElectricParticle::provider);
   }
}
