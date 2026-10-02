package net.thirdlife.iterrpg.init;

import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.thirdlife.iterrpg.client.particle.ArcaneParticleParticle;
import net.thirdlife.iterrpg.client.particle.ChainParticleParticle;
import net.thirdlife.iterrpg.client.particle.CoinParticleParticle;
import net.thirdlife.iterrpg.client.particle.DemonbloodParticle;
import net.thirdlife.iterrpg.client.particle.ElementalDropletParticle;
import net.thirdlife.iterrpg.client.particle.ElementalLeafParticle;
import net.thirdlife.iterrpg.client.particle.ElementalParticleParticle;
import net.thirdlife.iterrpg.client.particle.ElementalVoidParticle;
import net.thirdlife.iterrpg.client.particle.GobsteelShardsParticle;
import net.thirdlife.iterrpg.client.particle.PortalSparkParticleParticle;
import net.thirdlife.iterrpg.client.particle.VoidEyeParticleParticle;
import net.thirdlife.iterrpg.client.particle.WeeperTearParticleParticle;

@EventBusSubscriber(
   bus = Bus.MOD,
   value = {Dist.CLIENT}
)
public class IterRpgModParticles {
   @SubscribeEvent
   public static void registerParticles(RegisterParticleProvidersEvent event) {
      event.register((ParticleType)IterRpgModParticleTypes.ARCANE_PARTICLE.get(), ArcaneParticleParticle::provider);
      event.register((ParticleType)IterRpgModParticleTypes.WEEPER_TEAR_PARTICLE.get(), WeeperTearParticleParticle::provider);
      event.register((ParticleType)IterRpgModParticleTypes.ELEMENTAL_DROPLET.get(), ElementalDropletParticle::provider);
      event.register((ParticleType)IterRpgModParticleTypes.DEMONBLOOD.get(), DemonbloodParticle::provider);
      event.register((ParticleType)IterRpgModParticleTypes.COIN_PARTICLE.get(), CoinParticleParticle::provider);
      event.register((ParticleType)IterRpgModParticleTypes.ELEMENTAL_LEAF.get(), ElementalLeafParticle::provider);
      event.register((ParticleType)IterRpgModParticleTypes.CHAIN_PARTICLE.get(), ChainParticleParticle::provider);
      event.register((ParticleType)IterRpgModParticleTypes.ELEMENTAL_VOID.get(), ElementalVoidParticle::provider);
      event.register((ParticleType)IterRpgModParticleTypes.GOBSTEEL_SHARDS.get(), GobsteelShardsParticle::provider);
      event.register((ParticleType)IterRpgModParticleTypes.ELEMENTAL_PARTICLE.get(), ElementalParticleParticle::provider);
      event.register((ParticleType)IterRpgModParticleTypes.VOID_EYE_PARTICLE.get(), VoidEyeParticleParticle::provider);
      event.register((ParticleType)IterRpgModParticleTypes.PORTAL_SPARK_PARTICLE.get(), PortalSparkParticleParticle::provider);
   }
}
