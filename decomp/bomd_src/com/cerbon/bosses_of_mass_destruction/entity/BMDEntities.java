package com.cerbon.bosses_of_mass_destruction.entity;

import com.cerbon.bosses_of_mass_destruction.animation.PauseAnimationTimer;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.data.WeakHashPredicate;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.RandomUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.client.render.BillboardRenderer;
import com.cerbon.bosses_of_mass_destruction.client.render.CompositeRenderer;
import com.cerbon.bosses_of_mass_destruction.client.render.ConditionalRenderer;
import com.cerbon.bosses_of_mass_destruction.client.render.FrameLimiter;
import com.cerbon.bosses_of_mass_destruction.client.render.FullRenderLight;
import com.cerbon.bosses_of_mass_destruction.client.render.LerpedPosRenderer;
import com.cerbon.bosses_of_mass_destruction.client.render.PetalBladeParticleRenderer;
import com.cerbon.bosses_of_mass_destruction.client.render.PetalBladeRenderer;
import com.cerbon.bosses_of_mass_destruction.client.render.SimpleEntityRenderer;
import com.cerbon.bosses_of_mass_destruction.config.BMDConfig;
import com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet.GauntletCodeAnimations;
import com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet.GauntletEnergyRenderer;
import com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet.GauntletEntity;
import com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet.GauntletLaserRenderer;
import com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet.GauntletOverlay;
import com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet.GauntletTextureProvider;
import com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet.LaserParticleRenderer;
import com.cerbon.bosses_of_mass_destruction.entity.custom.lich.BoundedLighting;
import com.cerbon.bosses_of_mass_destruction.entity.custom.lich.EternalNightRenderer;
import com.cerbon.bosses_of_mass_destruction.entity.custom.lich.LichBoneLight;
import com.cerbon.bosses_of_mass_destruction.entity.custom.lich.LichCodeAnimations;
import com.cerbon.bosses_of_mass_destruction.entity.custom.lich.LichEntity;
import com.cerbon.bosses_of_mass_destruction.entity.custom.lich.LichKillCounter;
import com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith.ObsidilithArmorRenderer;
import com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith.ObsidilithBoneLight;
import com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith.ObsidilithEntity;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.NoRedOnDeathOverlay;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.SporeBallOverlay;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.SporeBallSizeRenderer;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.SporeCodeAnimations;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.VoidBlossomBoneLight;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.VoidBlossomCodeAnimations;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.VoidBlossomEntity;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.VoidBlossomSpikeRenderer;
import com.cerbon.bosses_of_mass_destruction.entity.util.SimpleGeoRenderer;
import com.cerbon.bosses_of_mass_destruction.entity.util.SimpleLivingGeoRenderer;
import com.cerbon.bosses_of_mass_destruction.item.custom.ChargedEnderPearlEntity;
import com.cerbon.bosses_of_mass_destruction.item.custom.SoulStarEntity;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.particle.ClientParticleBuilder;
import com.cerbon.bosses_of_mass_destruction.particle.ParticleFactories;
import com.cerbon.bosses_of_mass_destruction.projectile.MagicMissileProjectile;
import com.cerbon.bosses_of_mass_destruction.projectile.PetalBladeProjectile;
import com.cerbon.bosses_of_mass_destruction.projectile.SporeBallProjectile;
import com.cerbon.bosses_of_mass_destruction.projectile.comet.CometCodeAnimations;
import com.cerbon.bosses_of_mass_destruction.projectile.comet.CometProjectile;
import com.cerbon.bosses_of_mass_destruction.util.BMDColors;
import com.mojang.blaze3d.Blaze3D;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BMDEntities {
   public static final BMDConfig mobConfig = (BMDConfig)AutoConfig.getConfigHolder(BMDConfig.class).getConfig();
   public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, "bosses_of_mass_destruction");
   public static final RegistryObject<EntityType<LichEntity>> LICH = ENTITY_TYPES.register(
      "lich",
      () -> Builder.m_20704_((entityType, level) -> new LichEntity(entityType, level, mobConfig.lichConfig), MobCategory.MONSTER)
            .m_20699_(1.8F, 3.0F)
            .m_20717_(1)
            .m_20712_(new ResourceLocation("bosses_of_mass_destruction", "lich").toString())
   );
   public static final RegistryObject<EntityType<MagicMissileProjectile>> MAGIC_MISSILE = ENTITY_TYPES.register(
      "blue_fireball",
      () -> Builder.m_20704_(MagicMissileProjectile::new, MobCategory.MISC)
            .m_20699_(0.25F, 0.25F)
            .m_20712_(new ResourceLocation("bosses_of_mass_destruction", "blue_fireball").toString())
   );
   public static final RegistryObject<EntityType<CometProjectile>> COMET = ENTITY_TYPES.register(
      "comet",
      () -> Builder.m_20704_(CometProjectile::new, MobCategory.MISC)
            .m_20699_(0.25F, 0.25F)
            .m_20712_(new ResourceLocation("bosses_of_mass_destruction", "comet").toString())
   );
   public static final RegistryObject<EntityType<SoulStarEntity>> SOUL_STAR = ENTITY_TYPES.register(
      "soul_star",
      () -> Builder.m_20704_(SoulStarEntity::new, MobCategory.MISC)
            .m_20699_(0.25F, 0.25F)
            .m_20712_(new ResourceLocation("bosses_of_mass_destruction", "soul_star").toString())
   );
   public static final RegistryObject<EntityType<ChargedEnderPearlEntity>> CHARGED_ENDER_PEARL = ENTITY_TYPES.register(
      "charged_ender_pearl",
      () -> Builder.m_20704_(ChargedEnderPearlEntity::new, MobCategory.MISC)
            .m_20699_(0.25F, 0.25F)
            .m_20712_(new ResourceLocation("bosses_of_mass_destruction", "charged_ender_pearl").toString())
   );
   public static final RegistryObject<EntityType<ObsidilithEntity>> OBSIDILITH = ENTITY_TYPES.register(
      "obsidilith",
      () -> Builder.m_20704_((entityType, level) -> new ObsidilithEntity(entityType, level, mobConfig.obsidilithConfig), MobCategory.MONSTER)
            .m_20699_(2.0F, 4.4F)
            .m_20719_()
            .m_20712_(new ResourceLocation("bosses_of_mass_destruction", "obsidilith").toString())
   );
   public static final RegistryObject<EntityType<GauntletEntity>> GAUNTLET = ENTITY_TYPES.register(
      "gauntlet",
      () -> Builder.m_20704_((entityType, level) -> new GauntletEntity(entityType, level, mobConfig.gauntletConfig), MobCategory.MONSTER)
            .m_20699_(5.0F, 4.0F)
            .m_20719_()
            .m_20712_(new ResourceLocation("bosses_of_mass_destruction", "gauntlet").toString())
   );
   public static final RegistryObject<EntityType<VoidBlossomEntity>> VOID_BLOSSOM = ENTITY_TYPES.register(
      "void_blossom",
      () -> Builder.m_20704_((entityType, level) -> new VoidBlossomEntity(entityType, level, mobConfig.voidBlossomConfig), MobCategory.MONSTER)
            .m_20699_(8.0F, 10.0F)
            .m_20719_()
            .setTrackingRange(3)
            .m_20712_(new ResourceLocation("bosses_of_mass_destruction", "void_blossom").toString())
   );
   public static final RegistryObject<EntityType<SporeBallProjectile>> SPORE_BALL = ENTITY_TYPES.register(
      "spore_ball",
      () -> Builder.m_20704_(SporeBallProjectile::new, MobCategory.MISC)
            .m_20699_(0.25F, 0.25F)
            .m_20712_(new ResourceLocation("bosses_of_mass_destruction", "spore_ball").toString())
   );
   public static final RegistryObject<EntityType<PetalBladeProjectile>> PETAL_BLADE = ENTITY_TYPES.register(
      "petal_blade",
      () -> Builder.m_20704_(PetalBladeProjectile::new, MobCategory.MISC)
            .m_20699_(0.25F, 0.25F)
            .m_20712_(new ResourceLocation("bosses_of_mass_destruction", "petal_blade").toString())
   );
   public static final LichKillCounter killCounter = new LichKillCounter(mobConfig.lichConfig.summonMechanic);

   public static void createAttributes(EntityAttributeCreationEvent event) {
      event.put(
         (EntityType)LICH.get(),
         Mob.m_21552_()
            .m_22268_(Attributes.f_22280_, 5.0)
            .m_22268_(Attributes.f_22276_, mobConfig.lichConfig.health)
            .m_22268_(Attributes.f_22277_, 64.0)
            .m_22268_(Attributes.f_22281_, mobConfig.lichConfig.missile.damage)
            .m_22265_()
      );
      event.put(
         (EntityType)OBSIDILITH.get(),
         Mob.m_21552_()
            .m_22268_(Attributes.f_22276_, mobConfig.obsidilithConfig.health)
            .m_22268_(Attributes.f_22277_, 32.0)
            .m_22268_(Attributes.f_22281_, mobConfig.obsidilithConfig.attack)
            .m_22268_(Attributes.f_22278_, 10.0)
            .m_22268_(Attributes.f_22284_, mobConfig.obsidilithConfig.armor)
            .m_22265_()
      );
      event.put(
         (EntityType)GAUNTLET.get(),
         Mob.m_21552_()
            .m_22268_(Attributes.f_22280_, 4.0)
            .m_22268_(Attributes.f_22277_, 48.0)
            .m_22268_(Attributes.f_22276_, mobConfig.gauntletConfig.health)
            .m_22268_(Attributes.f_22278_, 10.0)
            .m_22268_(Attributes.f_22281_, mobConfig.gauntletConfig.attack)
            .m_22268_(Attributes.f_22284_, mobConfig.gauntletConfig.armor)
            .m_22265_()
      );
      event.put(
         (EntityType)VOID_BLOSSOM.get(),
         Mob.m_21552_()
            .m_22268_(Attributes.f_22276_, mobConfig.voidBlossomConfig.health)
            .m_22268_(Attributes.f_22277_, 32.0)
            .m_22268_(Attributes.f_22281_, mobConfig.voidBlossomConfig.attack)
            .m_22268_(Attributes.f_22278_, 10.0)
            .m_22268_(Attributes.f_22284_, mobConfig.voidBlossomConfig.armor)
            .m_22265_()
      );
   }

   @OnlyIn(Dist.CLIENT)
   public static void initClient() {
      PauseAnimationTimer pauseSecondTimer = new PauseAnimationTimer(Blaze3D::m_83640_, () -> Minecraft.m_91087_().m_91104_());
      EntityRenderers.m_174036_(
         (EntityType)LICH.get(),
         context -> {
            ResourceLocation texture = new ResourceLocation("bosses_of_mass_destruction", "textures/entity/lich.png");
            return new SimpleLivingGeoRenderer(
               context,
               new GeoModel<LichEntity>(
                  lichEntity -> new ResourceLocation("bosses_of_mass_destruction", "geo/lich.geo.json"),
                  entity -> texture,
                  new ResourceLocation("bosses_of_mass_destruction", "animations/lich.animation.json"),
                  new LichCodeAnimations()
               ),
               new BoundedLighting(5),
               new LichBoneLight(),
               new EternalNightRenderer(),
               null,
               null,
               RenderType.m_110458_(texture),
               true
            );
         }
      );
      EntityRenderers.m_174036_(
         (EntityType)OBSIDILITH.get(),
         context -> {
            ObsidilithBoneLight runeColorHandler = new ObsidilithBoneLight();
            GeoModel<ObsidilithEntity> modelProvider = new GeoModel<>(
               entity -> new ResourceLocation("bosses_of_mass_destruction", "geo/obsidilith.geo.json"),
               entity -> new ResourceLocation("bosses_of_mass_destruction", "textures/entity/obsidilith.png"),
               new ResourceLocation("bosses_of_mass_destruction", "animations/obsidilith.animation.json"),
               (animatable, data, geoModel) -> {
               }
            );
            ObsidilithArmorRenderer armorRenderer = new ObsidilithArmorRenderer(modelProvider, context);
            return new SimpleLivingGeoRenderer(
               context, modelProvider, null, runeColorHandler, new CompositeRenderer(armorRenderer, runeColorHandler), armorRenderer, null, null, false
            );
         }
      );
      EntityRenderers.m_174036_(
         (EntityType)COMET.get(),
         context -> new SimpleGeoRenderer(
               context,
               new GeoModel<CometProjectile>(
                  geoAnimatable -> new ResourceLocation("bosses_of_mass_destruction", "geo/comet.geo.json"),
                  entity -> new ResourceLocation("bosses_of_mass_destruction", "textures/entity/comet.png"),
                  new ResourceLocation("bosses_of_mass_destruction", "animations/comet.animation.json"),
                  new CometCodeAnimations()
               ),
               new ConditionalRenderer(
                  new WeakHashPredicate(() -> new FrameLimiter(60.0F, pauseSecondTimer)::canDoFrame),
                  new LerpedPosRenderer(vec3 -> ParticleFactories.cometTrail().build(vec3.m_82549_(RandomUtils.randVec().m_82490_(0.5)), Vec3.f_82478_))
               ),
               null,
               new FullRenderLight(),
               null
            )
      );
      EntityRenderers.m_174036_((EntityType)SOUL_STAR.get(), context -> new ThrownItemRenderer(context, 1.0F, true));
      EntityRenderers.m_174036_((EntityType)CHARGED_ENDER_PEARL.get(), ThrownItemRenderer::new);
      ResourceLocation missileTexture = new ResourceLocation("bosses_of_mass_destruction", "textures/entity/blue_magic_missile.png");
      RenderType magicMissileRenderType = RenderType.m_110458_(missileTexture);
      EntityRenderers.m_174036_(
         (EntityType)MAGIC_MISSILE.get(),
         context -> new SimpleEntityRenderer(
               context,
               new CompositeRenderer(
                  new BillboardRenderer(context.m_174022_(), magicMissileRenderType, f -> 0.5F),
                  new ConditionalRenderer(
                     new WeakHashPredicate(() -> new FrameLimiter(20.0F, pauseSecondTimer)::canDoFrame),
                     new LerpedPosRenderer(vec3 -> ParticleFactories.soulFlame().build(vec3.m_82549_(RandomUtils.randVec().m_82490_(0.25)), Vec3.f_82478_))
                  )
               ),
               entity -> missileTexture,
               new FullRenderLight()
            )
      );
      EntityRenderers.m_174036_(
         (EntityType)GAUNTLET.get(),
         context -> {
            GeoModel<GauntletEntity> modelProvider = new GeoModel<>(
               entity -> new ResourceLocation("bosses_of_mass_destruction", "geo/gauntlet.geo.json"),
               new GauntletTextureProvider(),
               new ResourceLocation("bosses_of_mass_destruction", "animations/gauntlet.animation.json"),
               new GauntletCodeAnimations()
            );
            GauntletEnergyRenderer energyRenderer = new GauntletEnergyRenderer(modelProvider, context);
            GauntletOverlay overlayOverride = new GauntletOverlay();
            return new SimpleLivingGeoRenderer(
               context,
               modelProvider,
               null,
               null,
               new CompositeRenderer(
                  new GauntletLaserRenderer(),
                  new ConditionalRenderer(new WeakHashPredicate(() -> new FrameLimiter(20.0F, pauseSecondTimer)::canDoFrame), new LaserParticleRenderer()),
                  energyRenderer,
                  overlayOverride
               ),
               energyRenderer,
               overlayOverride,
               null,
               false
            );
         }
      );
      EntityRenderers.m_174036_(
         (EntityType)VOID_BLOSSOM.get(),
         context -> {
            ResourceLocation texture = new ResourceLocation("bosses_of_mass_destruction", "textures/entity/void_blossom.png");
            GeoModel<VoidBlossomEntity> modelProvider = new GeoModel<>(
               entity -> new ResourceLocation("bosses_of_mass_destruction", "geo/void_blossom.geo.json"),
               entity -> texture,
               new ResourceLocation("bosses_of_mass_destruction", "animations/void_blossom.animation.json"),
               new VoidBlossomCodeAnimations()
            );
            VoidBlossomBoneLight boneLight = new VoidBlossomBoneLight();
            NoRedOnDeathOverlay overlay = new NoRedOnDeathOverlay();
            return new SimpleLivingGeoRenderer(
               context, modelProvider, null, boneLight, new CompositeRenderer(new VoidBlossomSpikeRenderer(), boneLight, overlay), null, overlay, null, false
            );
         }
      );
      EntityRenderers.m_174036_(
         (EntityType)SPORE_BALL.get(),
         context -> {
            SporeBallOverlay explosionFlasher = new SporeBallOverlay();
            return new SimpleGeoRenderer(
               context,
               new GeoModel<SporeBallProjectile>(
                  geoAnimatable -> new ResourceLocation("bosses_of_mass_destruction", "geo/comet.geo.json"),
                  entity -> new ResourceLocation("bosses_of_mass_destruction", "textures/entity/spore.png"),
                  new ResourceLocation("bosses_of_mass_destruction", "animations/comet.animation.json"),
                  new SporeCodeAnimations()
               ),
               new CompositeRenderer(
                  new ConditionalRenderer(
                     new WeakHashPredicate(() -> new FrameLimiter(60.0F, pauseSecondTimer)::canDoFrame),
                     new LerpedPosRenderer(
                        vec3 -> {
                           ClientParticleBuilder projectileParticles = new ClientParticleBuilder((ParticleOptions)BMDParticles.OBSIDILITH_BURST.get())
                              .color(BMDColors.GREEN)
                              .colorVariation(0.4)
                              .scale(0.5F)
                              .brightness(15728880);
                           projectileParticles.build(vec3.m_82549_(RandomUtils.randVec().m_82490_(0.25)), VecUtils.yAxis.m_82490_(0.1));
                        }
                     )
                  ),
                  explosionFlasher
               ),
               new SporeBallSizeRenderer(),
               new FullRenderLight(),
               explosionFlasher
            );
         }
      );
      ResourceLocation petalTexture = new ResourceLocation("bosses_of_mass_destruction", "textures/entity/petal_blade.png");
      RenderType petalBladeRenderType = RenderType.m_110458_(petalTexture);
      EntityRenderers.m_174036_(
         (EntityType)PETAL_BLADE.get(),
         context -> new SimpleEntityRenderer(
               context,
               new CompositeRenderer(
                  new PetalBladeRenderer(context.m_174022_(), petalBladeRenderType),
                  new ConditionalRenderer(new WeakHashPredicate(() -> new FrameLimiter(30.0F, pauseSecondTimer)::canDoFrame), new PetalBladeParticleRenderer())
               ),
               entity -> petalTexture,
               new FullRenderLight()
            )
      );
   }

   public static void register(IEventBus eventBus) {
      ENTITY_TYPES.register(eventBus);
   }
}
