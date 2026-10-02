package net.xylonity.knightquest.common.event;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.event.EntityRenderersEvent.AddLayers;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.xylonity.knightquest.client.armor.GeoItemArmor;
import net.xylonity.knightquest.client.armor.GeoItemArmorRenderer;
import net.xylonity.knightquest.client.armor.chest.GeoItemArmorChest;
import net.xylonity.knightquest.client.armor.chest.GeoItemArmorChestRenderer;
import net.xylonity.knightquest.client.armor.leg.GeoItemArmorLeg;
import net.xylonity.knightquest.client.armor.leg.GeoItemArmorLegRenderer;
import net.xylonity.knightquest.client.entity.renderer.BadPatchRenderer;
import net.xylonity.knightquest.client.entity.renderer.EldBombRenderer;
import net.xylonity.knightquest.client.entity.renderer.EldKnightRenderer;
import net.xylonity.knightquest.client.entity.renderer.GhostyRenderer;
import net.xylonity.knightquest.client.entity.renderer.GremlinRenderer;
import net.xylonity.knightquest.client.entity.renderer.LizzyRenderer;
import net.xylonity.knightquest.client.entity.renderer.NethermanCloneRenderer;
import net.xylonity.knightquest.client.entity.renderer.NethermanProjectileChargeRenderer;
import net.xylonity.knightquest.client.entity.renderer.NethermanRenderer;
import net.xylonity.knightquest.client.entity.renderer.RatmanRenderer;
import net.xylonity.knightquest.client.entity.renderer.SamhainRenderer;
import net.xylonity.knightquest.client.entity.renderer.ShieldRenderer;
import net.xylonity.knightquest.client.entity.renderer.SwampmanAxeRenderer;
import net.xylonity.knightquest.client.entity.renderer.SwampmanRenderer;
import net.xylonity.knightquest.common.particle.GhostyParticle;
import net.xylonity.knightquest.common.particle.GremlinParticle;
import net.xylonity.knightquest.common.particle.PoisonCloudParticle;
import net.xylonity.knightquest.common.particle.PoisonParticle;
import net.xylonity.knightquest.common.particle.SnowflakeParticle;
import net.xylonity.knightquest.common.particle.YellowParticle;
import net.xylonity.knightquest.common.particle.explosiveenhancement.BlastWaveParticle;
import net.xylonity.knightquest.common.particle.explosiveenhancement.BubbleParticle;
import net.xylonity.knightquest.common.particle.explosiveenhancement.FireballParticle;
import net.xylonity.knightquest.common.particle.explosiveenhancement.ShockwaveParticle;
import net.xylonity.knightquest.common.particle.explosiveenhancement.SmokeParticle;
import net.xylonity.knightquest.common.particle.explosiveenhancement.SparksParticle;
import net.xylonity.knightquest.common.particle.explosiveenhancement.UnderwaterBlastwaveParticle;
import net.xylonity.knightquest.common.particle.explosiveenhancement.UnderwaterSparksParticle;
import net.xylonity.knightquest.common.particle.explosiveenhancement.blue.BlueBlastWaveParticle;
import net.xylonity.knightquest.common.particle.explosiveenhancement.blue.BlueFireballParticle;
import net.xylonity.knightquest.common.particle.explosiveenhancement.red.RedBlastWaveParticle;
import net.xylonity.knightquest.common.particle.explosiveenhancement.red.RedFireballParticle;
import net.xylonity.knightquest.registry.KnightQuestEntities;
import net.xylonity.knightquest.registry.KnightQuestParticles;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

@EventBusSubscriber(
   modid = "knightquest",
   bus = Bus.MOD,
   value = {Dist.CLIENT}
)
public class KQClientEventProviders {
   @SubscribeEvent
   public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
      event.register((ParticleType)KnightQuestParticles.GREMLIN_PARTICLE.get(), GremlinParticle.Provider::new);
      event.register((ParticleType)KnightQuestParticles.YELLOW_PARTICLE.get(), YellowParticle.Provider::new);
      event.register((ParticleType)KnightQuestParticles.GHOSTY_PARTICLE.get(), GhostyParticle.Provider::new);
      event.register((ParticleType)KnightQuestParticles.SNOWFLAKE_PARTICLE.get(), SnowflakeParticle.Provider::new);
      event.register((ParticleType)KnightQuestParticles.POISON_PARTICLE.get(), PoisonParticle.Provider::new);
      event.register((ParticleType)KnightQuestParticles.POISON_CLOUD_PARTICLE.get(), PoisonCloudParticle.Provider::new);
      event.register((ParticleType)KnightQuestParticles.BLASTWAVE.get(), BlastWaveParticle.Provider::new);
      event.register((ParticleType)KnightQuestParticles.FIREBALL.get(), FireballParticle.Provider::new);
      event.register((ParticleType)KnightQuestParticles.BLANK_FIREBALL.get(), FireballParticle.Provider::new);
      event.register((ParticleType)KnightQuestParticles.SMOKE.get(), SmokeParticle.Provider::new);
      event.register((ParticleType)KnightQuestParticles.SPARKS.get(), SparksParticle.Provider::new);
      event.register((ParticleType)KnightQuestParticles.BUBBLE.get(), BubbleParticle.Provider::new);
      event.register((ParticleType)KnightQuestParticles.SHOCKWAVE.get(), ShockwaveParticle.Provider::new);
      event.register((ParticleType)KnightQuestParticles.BLANK_SHOCKWAVE.get(), ShockwaveParticle.Provider::new);
      event.register((ParticleType)KnightQuestParticles.UNDERWATERBLASTWAVE.get(), UnderwaterBlastwaveParticle.Provider::new);
      event.register((ParticleType)KnightQuestParticles.UNDERWATERSPARKS.get(), UnderwaterSparksParticle.Provider::new);
      event.register((ParticleType)KnightQuestParticles.BLUEBLASTWAVE.get(), BlueBlastWaveParticle.Provider::new);
      event.register((ParticleType)KnightQuestParticles.BLUEFIREBALL.get(), BlueFireballParticle.Provider::new);
      event.register((ParticleType)KnightQuestParticles.REDBLASTWAVE.get(), RedBlastWaveParticle.Provider::new);
      event.register((ParticleType)KnightQuestParticles.REDFIREBALL.get(), RedFireballParticle.Provider::new);
   }

   @SubscribeEvent
   public static void onClientSetup(FMLClientSetupEvent event) {
      EntityRenderers.m_174036_((EntityType)KnightQuestEntities.GREMLIN.get(), GremlinRenderer::new);
      EntityRenderers.m_174036_((EntityType)KnightQuestEntities.ELDBOMB.get(), EldBombRenderer::new);
      EntityRenderers.m_174036_((EntityType)KnightQuestEntities.ELDKINGHT.get(), EldKnightRenderer::new);
      EntityRenderers.m_174036_((EntityType)KnightQuestEntities.SWAMPMAN.get(), SwampmanRenderer::new);
      EntityRenderers.m_174036_((EntityType)KnightQuestEntities.RATMAN.get(), RatmanRenderer::new);
      EntityRenderers.m_174036_((EntityType)KnightQuestEntities.SAMHAIN.get(), SamhainRenderer::new);
      EntityRenderers.m_174036_((EntityType)KnightQuestEntities.LIZZY.get(), LizzyRenderer::new);
      EntityRenderers.m_174036_((EntityType)KnightQuestEntities.BADPATCH.get(), BadPatchRenderer::new);
      EntityRenderers.m_174036_((EntityType)KnightQuestEntities.SHIELD.get(), ShieldRenderer::new);
      EntityRenderers.m_174036_((EntityType)KnightQuestEntities.GHOSTY.get(), GhostyRenderer::new);
      EntityRenderers.m_174036_((EntityType)KnightQuestEntities.NETHERMAN.get(), NethermanRenderer::new);
      EntityRenderers.m_174036_((EntityType)KnightQuestEntities.NETHERMAN_PROJECTILE_CHARGE.get(), NethermanProjectileChargeRenderer::new);
      EntityRenderers.m_174036_((EntityType)KnightQuestEntities.NETHERMAN_CLONE.get(), NethermanCloneRenderer::new);
      EntityRenderers.m_174036_((EntityType)KnightQuestEntities.SWAMPMAN_AXE.get(), SwampmanAxeRenderer::new);
   }

   @SubscribeEvent
   public static void registerArmorRenderer(AddLayers event) {
      GeoArmorRenderer.registerArmorRenderer(GeoItemArmor.class, new GeoItemArmorRenderer());
      GeoArmorRenderer.registerArmorRenderer(GeoItemArmorChest.class, new GeoItemArmorChestRenderer());
      GeoArmorRenderer.registerArmorRenderer(GeoItemArmorLeg.class, new GeoItemArmorLegRenderer());
   }
}
