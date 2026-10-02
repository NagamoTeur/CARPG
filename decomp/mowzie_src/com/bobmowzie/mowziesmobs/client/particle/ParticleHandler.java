package com.bobmowzie.mowziesmobs.client.particle;

import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleData;
import com.bobmowzie.mowziesmobs.client.particle.util.RibbonParticleData;
import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleOptions.Deserializer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(
   modid = "mowziesmobs",
   bus = Bus.MOD,
   value = {Dist.CLIENT}
)
public class ParticleHandler {
   public static final DeferredRegister<ParticleType<?>> REG = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, "mowziesmobs");
   public static final RegistryObject<SimpleParticleType> SPARKLE = register("sparkle", false);
   public static final RegistryObject<ParticleType<ParticleVanillaCloudExtended.VanillaCloudData>> VANILLA_CLOUD_EXTENDED = REG.register(
      "vanilla_cloud_extended",
      () -> new ParticleType<ParticleVanillaCloudExtended.VanillaCloudData>(false, ParticleVanillaCloudExtended.VanillaCloudData.DESERIALIZER) {
            public Codec<ParticleVanillaCloudExtended.VanillaCloudData> m_7652_() {
               return ParticleVanillaCloudExtended.VanillaCloudData.CODEC(
                  (ParticleType<ParticleVanillaCloudExtended.VanillaCloudData>)ParticleHandler.VANILLA_CLOUD_EXTENDED.get()
               );
            }
         }
   );
   public static final RegistryObject<ParticleType<ParticleSnowFlake.SnowflakeData>> SNOWFLAKE = REG.register(
      "snowflake", () -> new ParticleType<ParticleSnowFlake.SnowflakeData>(false, ParticleSnowFlake.SnowflakeData.DESERIALIZER) {
            public Codec<ParticleSnowFlake.SnowflakeData> m_7652_() {
               return ParticleSnowFlake.SnowflakeData.CODEC((ParticleType<ParticleSnowFlake.SnowflakeData>)ParticleHandler.SNOWFLAKE.get());
            }
         }
   );
   public static final RegistryObject<ParticleType<ParticleCloud.CloudData>> CLOUD = REG.register(
      "cloud_soft", () -> new ParticleType<ParticleCloud.CloudData>(false, ParticleCloud.CloudData.DESERIALIZER) {
            public Codec<ParticleCloud.CloudData> m_7652_() {
               return ParticleCloud.CloudData.CODEC((ParticleType<ParticleCloud.CloudData>)ParticleHandler.CLOUD.get());
            }
         }
   );
   public static final RegistryObject<ParticleType<ParticleOrb.OrbData>> ORB = REG.register(
      "orb_0", () -> new ParticleType<ParticleOrb.OrbData>(false, ParticleOrb.OrbData.DESERIALIZER) {
            public Codec<ParticleOrb.OrbData> m_7652_() {
               return ParticleOrb.OrbData.CODEC((ParticleType<ParticleOrb.OrbData>)ParticleHandler.ORB.get());
            }
         }
   );
   public static final RegistryObject<ParticleType<ParticleRing.RingData>> RING = REG.register(
      "ring_0", () -> new ParticleType<ParticleRing.RingData>(false, ParticleRing.RingData.DESERIALIZER) {
            public Codec<ParticleRing.RingData> m_7652_() {
               return ParticleRing.RingData.CODEC((ParticleType<ParticleRing.RingData>)ParticleHandler.RING.get());
            }
         }
   );
   public static final RegistryObject<ParticleType<AdvancedParticleData>> RING2 = register("ring", AdvancedParticleData.DESERIALIZER);
   public static final RegistryObject<ParticleType<AdvancedParticleData>> RING_BIG = register("ring_big", AdvancedParticleData.DESERIALIZER);
   public static final RegistryObject<ParticleType<AdvancedParticleData>> PIXEL = register("pixel", AdvancedParticleData.DESERIALIZER);
   public static final RegistryObject<ParticleType<AdvancedParticleData>> ORB2 = register("orb", AdvancedParticleData.DESERIALIZER);
   public static final RegistryObject<ParticleType<AdvancedParticleData>> EYE = register("eye", AdvancedParticleData.DESERIALIZER);
   public static final RegistryObject<ParticleType<AdvancedParticleData>> BUBBLE = register("bubble", AdvancedParticleData.DESERIALIZER);
   public static final RegistryObject<ParticleType<AdvancedParticleData>> SUN = register("sun", AdvancedParticleData.DESERIALIZER);
   public static final RegistryObject<ParticleType<AdvancedParticleData>> SUN_NOVA = register("sun_nova", AdvancedParticleData.DESERIALIZER);
   public static final RegistryObject<ParticleType<AdvancedParticleData>> FLARE = register("flare", AdvancedParticleData.DESERIALIZER);
   public static final RegistryObject<ParticleType<AdvancedParticleData>> FLARE_RADIAL = register("flare_radial", AdvancedParticleData.DESERIALIZER);
   public static final RegistryObject<ParticleType<AdvancedParticleData>> BURST_IN = register("ring1", AdvancedParticleData.DESERIALIZER);
   public static final RegistryObject<ParticleType<AdvancedParticleData>> BURST_MESSY = register("burst_messy", AdvancedParticleData.DESERIALIZER);
   public static final RegistryObject<ParticleType<AdvancedParticleData>> RING_SPARKS = register("sparks_ring", AdvancedParticleData.DESERIALIZER);
   public static final RegistryObject<ParticleType<AdvancedParticleData>> BURST_OUT = register("ring2", AdvancedParticleData.DESERIALIZER);
   public static final RegistryObject<ParticleType<AdvancedParticleData>> GLOW = register("glow", AdvancedParticleData.DESERIALIZER);
   public static final RegistryObject<ParticleType<AdvancedParticleData>> ARROW_HEAD = register("arrow_head", AdvancedParticleData.DESERIALIZER);
   public static final RegistryObject<ParticleType<AdvancedParticleData>> STRIX_FOOTPRINT = register("strix_footprint", AdvancedParticleData.DESERIALIZER);
   public static final RegistryObject<ParticleType<RibbonParticleData>> RIBBON_FLAT = registerRibbon("ribbon_flat", RibbonParticleData.DESERIALIZER);
   public static final RegistryObject<ParticleType<RibbonParticleData>> RIBBON_STREAKS = registerRibbon("ribbon_streaks", RibbonParticleData.DESERIALIZER);
   public static final RegistryObject<ParticleType<RibbonParticleData>> RIBBON_GLOW = registerRibbon("ribbon_glow", RibbonParticleData.DESERIALIZER);
   public static final RegistryObject<ParticleType<RibbonParticleData>> RIBBON_SQUIGGLE = registerRibbon("ribbon_squiggle", RibbonParticleData.DESERIALIZER);

   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public static void registerParticles(RegisterParticleProvidersEvent event) {
      event.register((ParticleType)SPARKLE.get(), ParticleSparkle.SparkleFactory::new);
      event.register((ParticleType)VANILLA_CLOUD_EXTENDED.get(), ParticleVanillaCloudExtended.CloudFactory::new);
      event.register((ParticleType)SNOWFLAKE.get(), ParticleSnowFlake.SnowFlakeFactory::new);
      event.register((ParticleType)CLOUD.get(), ParticleCloud.CloudFactory::new);
      event.register((ParticleType)ORB.get(), ParticleOrb.OrbFactory::new);
      event.register((ParticleType)RING.get(), ParticleRing.RingFactory::new);
      event.register((ParticleType)RING2.get(), AdvancedParticleBase.Factory::new);
      event.register((ParticleType)RING_BIG.get(), AdvancedParticleBase.Factory::new);
      event.register((ParticleType)PIXEL.get(), AdvancedParticleBase.Factory::new);
      event.register((ParticleType)ORB2.get(), AdvancedParticleBase.Factory::new);
      event.register((ParticleType)EYE.get(), AdvancedParticleBase.Factory::new);
      event.register((ParticleType)BUBBLE.get(), AdvancedParticleBase.Factory::new);
      event.register((ParticleType)SUN.get(), AdvancedParticleBase.Factory::new);
      event.register((ParticleType)SUN_NOVA.get(), AdvancedParticleBase.Factory::new);
      event.register((ParticleType)FLARE.get(), AdvancedParticleBase.Factory::new);
      event.register((ParticleType)FLARE_RADIAL.get(), AdvancedParticleBase.Factory::new);
      event.register((ParticleType)BURST_IN.get(), AdvancedParticleBase.Factory::new);
      event.register((ParticleType)BURST_MESSY.get(), AdvancedParticleBase.Factory::new);
      event.register((ParticleType)RING_SPARKS.get(), AdvancedParticleBase.Factory::new);
      event.register((ParticleType)BURST_OUT.get(), AdvancedParticleBase.Factory::new);
      event.register((ParticleType)GLOW.get(), AdvancedParticleBase.Factory::new);
      event.register((ParticleType)ARROW_HEAD.get(), AdvancedParticleBase.Factory::new);
      event.register((ParticleType)STRIX_FOOTPRINT.get(), ParticleDecal.Factory::new);
      event.register((ParticleType)RIBBON_FLAT.get(), ParticleRibbon.Factory::new);
      event.register((ParticleType)RIBBON_STREAKS.get(), ParticleRibbon.Factory::new);
      event.register((ParticleType)RIBBON_GLOW.get(), ParticleRibbon.Factory::new);
      event.register((ParticleType)RIBBON_SQUIGGLE.get(), ParticleRibbon.Factory::new);
   }

   private static RegistryObject<SimpleParticleType> register(String key, boolean alwaysShow) {
      return REG.register(key, () -> new SimpleParticleType(alwaysShow));
   }

   private static RegistryObject<ParticleType<AdvancedParticleData>> register(String key, Deserializer<AdvancedParticleData> deserializer) {
      return REG.register(key, () -> new ParticleType<AdvancedParticleData>(false, deserializer) {
            public Codec<AdvancedParticleData> m_7652_() {
               return AdvancedParticleData.CODEC(this);
            }
         });
   }

   private static RegistryObject<ParticleType<RibbonParticleData>> registerRibbon(String key, Deserializer<RibbonParticleData> deserializer) {
      return REG.register(key, () -> new ParticleType<RibbonParticleData>(false, deserializer) {
            public Codec<RibbonParticleData> m_7652_() {
               return RibbonParticleData.CODEC_RIBBON(this);
            }
         });
   }
}
