package io.redspace.ironsspellbooks.registries;

import io.redspace.ironsspellbooks.entity.VisualFallingBlockEntity;
import io.redspace.ironsspellbooks.entity.mobs.CatacombsZombie;
import io.redspace.ironsspellbooks.entity.mobs.MagehunterVindicator;
import io.redspace.ironsspellbooks.entity.mobs.SummonedHorse;
import io.redspace.ironsspellbooks.entity.mobs.SummonedPolarBear;
import io.redspace.ironsspellbooks.entity.mobs.SummonedSkeleton;
import io.redspace.ironsspellbooks.entity.mobs.SummonedVex;
import io.redspace.ironsspellbooks.entity.mobs.SummonedZombie;
import io.redspace.ironsspellbooks.entity.mobs.dead_king_boss.DeadKingBoss;
import io.redspace.ironsspellbooks.entity.mobs.dead_king_boss.DeadKingCorpseEntity;
import io.redspace.ironsspellbooks.entity.mobs.debug_wizard.DebugWizard;
import io.redspace.ironsspellbooks.entity.mobs.frozen_humanoid.FrozenHumanoid;
import io.redspace.ironsspellbooks.entity.mobs.keeper.KeeperEntity;
import io.redspace.ironsspellbooks.entity.mobs.necromancer.NecromancerEntity;
import io.redspace.ironsspellbooks.entity.mobs.summoned_frog.SummonedFrog;
import io.redspace.ironsspellbooks.entity.mobs.wizards.alchemist.ApothecaristEntity;
import io.redspace.ironsspellbooks.entity.mobs.wizards.archevoker.ArchevokerEntity;
import io.redspace.ironsspellbooks.entity.mobs.wizards.cryomancer.CryomancerEntity;
import io.redspace.ironsspellbooks.entity.mobs.wizards.cultist.CultistEntity;
import io.redspace.ironsspellbooks.entity.mobs.wizards.priest.PriestEntity;
import io.redspace.ironsspellbooks.entity.mobs.wizards.pyromancer.PyromancerEntity;
import io.redspace.ironsspellbooks.entity.spells.ArrowVolleyEntity;
import io.redspace.ironsspellbooks.entity.spells.ChainLightning;
import io.redspace.ironsspellbooks.entity.spells.EarthquakeAoe;
import io.redspace.ironsspellbooks.entity.spells.EchoingStrikeEntity;
import io.redspace.ironsspellbooks.entity.spells.ExtendedWitherSkull;
import io.redspace.ironsspellbooks.entity.spells.HealingAoe;
import io.redspace.ironsspellbooks.entity.spells.LightningStrike;
import io.redspace.ironsspellbooks.entity.spells.StompAoe;
import io.redspace.ironsspellbooks.entity.spells.acid_orb.AcidOrb;
import io.redspace.ironsspellbooks.entity.spells.ball_lightning.BallLightning;
import io.redspace.ironsspellbooks.entity.spells.black_hole.BlackHole;
import io.redspace.ironsspellbooks.entity.spells.blood_needle.BloodNeedle;
import io.redspace.ironsspellbooks.entity.spells.blood_slash.BloodSlashProjectile;
import io.redspace.ironsspellbooks.entity.spells.comet.Comet;
import io.redspace.ironsspellbooks.entity.spells.cone_of_cold.ConeOfColdProjectile;
import io.redspace.ironsspellbooks.entity.spells.creeper_head.CreeperHeadProjectile;
import io.redspace.ironsspellbooks.entity.spells.devour_jaw.DevourJaw;
import io.redspace.ironsspellbooks.entity.spells.dragon_breath.DragonBreathPool;
import io.redspace.ironsspellbooks.entity.spells.dragon_breath.DragonBreathProjectile;
import io.redspace.ironsspellbooks.entity.spells.eldritch_blast.EldritchBlastVisualEntity;
import io.redspace.ironsspellbooks.entity.spells.electrocute.ElectrocuteProjectile;
import io.redspace.ironsspellbooks.entity.spells.fire_breath.FireBreathProjectile;
import io.redspace.ironsspellbooks.entity.spells.fireball.MagicFireball;
import io.redspace.ironsspellbooks.entity.spells.fireball.SmallMagicFireball;
import io.redspace.ironsspellbooks.entity.spells.firebolt.FireboltProjectile;
import io.redspace.ironsspellbooks.entity.spells.firefly_swarm.FireflySwarmProjectile;
import io.redspace.ironsspellbooks.entity.spells.flame_strike.FlameStrike;
import io.redspace.ironsspellbooks.entity.spells.guiding_bolt.GuidingBoltProjectile;
import io.redspace.ironsspellbooks.entity.spells.gust.GustCollider;
import io.redspace.ironsspellbooks.entity.spells.ice_block.IceBlockProjectile;
import io.redspace.ironsspellbooks.entity.spells.icicle.IcicleProjectile;
import io.redspace.ironsspellbooks.entity.spells.lightning_lance.LightningLanceProjectile;
import io.redspace.ironsspellbooks.entity.spells.magic_arrow.MagicArrowProjectile;
import io.redspace.ironsspellbooks.entity.spells.magic_missile.MagicMissileProjectile;
import io.redspace.ironsspellbooks.entity.spells.magma_ball.FireBomb;
import io.redspace.ironsspellbooks.entity.spells.magma_ball.FireField;
import io.redspace.ironsspellbooks.entity.spells.poison_arrow.PoisonArrow;
import io.redspace.ironsspellbooks.entity.spells.poison_breath.PoisonBreathProjectile;
import io.redspace.ironsspellbooks.entity.spells.poison_cloud.PoisonCloud;
import io.redspace.ironsspellbooks.entity.spells.poison_cloud.PoisonSplash;
import io.redspace.ironsspellbooks.entity.spells.portal.PortalEntity;
import io.redspace.ironsspellbooks.entity.spells.ray_of_frost.RayOfFrostVisualEntity;
import io.redspace.ironsspellbooks.entity.spells.root.RootEntity;
import io.redspace.ironsspellbooks.entity.spells.shield.ShieldEntity;
import io.redspace.ironsspellbooks.entity.spells.small_magic_arrow.SmallMagicArrow;
import io.redspace.ironsspellbooks.entity.spells.spectral_hammer.SpectralHammer;
import io.redspace.ironsspellbooks.entity.spells.sunbeam.Sunbeam;
import io.redspace.ironsspellbooks.entity.spells.target_area.TargetedAreaEntity;
import io.redspace.ironsspellbooks.entity.spells.void_tentacle.VoidTentacle;
import io.redspace.ironsspellbooks.entity.spells.wall_of_fire.WallOfFireEntity;
import io.redspace.ironsspellbooks.entity.spells.wisp.WispEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityRegistry {
   private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, "irons_spellbooks");
   public static final RegistryObject<EntityType<WispEntity>> WISP = ENTITIES.register(
      "wisp",
      () -> Builder.m_20704_(WispEntity::new, MobCategory.MISC)
            .m_20699_(0.5F, 0.5F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "wisp").toString())
   );
   public static final RegistryObject<EntityType<SpectralHammer>> SPECTRAL_HAMMER = ENTITIES.register(
      "spectral_hammer",
      () -> Builder.m_20704_(SpectralHammer::new, MobCategory.MISC)
            .m_20699_(1.0F, 1.0F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "spectral_hammer").toString())
   );
   public static final RegistryObject<EntityType<MagicMissileProjectile>> MAGIC_MISSILE_PROJECTILE = ENTITIES.register(
      "magic_missile",
      () -> Builder.m_20704_(MagicMissileProjectile::new, MobCategory.MISC)
            .m_20699_(0.5F, 0.5F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "magic_missile").toString())
   );
   public static final RegistryObject<EntityType<ConeOfColdProjectile>> CONE_OF_COLD_PROJECTILE = ENTITIES.register(
      "cone_of_cold",
      () -> Builder.m_20704_(ConeOfColdProjectile::new, MobCategory.MISC)
            .m_20699_(1.0F, 1.0F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "cone_of_cold").toString())
   );
   public static final RegistryObject<EntityType<BloodSlashProjectile>> BLOOD_SLASH_PROJECTILE = ENTITIES.register(
      "blood_slash",
      () -> Builder.m_20704_(BloodSlashProjectile::new, MobCategory.MISC)
            .m_20699_(2.0F, 0.5F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "blood_slash").toString())
   );
   public static final RegistryObject<EntityType<ElectrocuteProjectile>> ELECTROCUTE_PROJECTILE = ENTITIES.register(
      "electrocute",
      () -> Builder.m_20704_(ElectrocuteProjectile::new, MobCategory.MISC)
            .m_20699_(1.0F, 1.0F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "electrocute").toString())
   );
   public static final RegistryObject<EntityType<FireboltProjectile>> FIREBOLT_PROJECTILE = ENTITIES.register(
      "firebolt",
      () -> Builder.m_20704_(FireboltProjectile::new, MobCategory.MISC)
            .m_20699_(0.5F, 0.5F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "firebolt").toString())
   );
   public static final RegistryObject<EntityType<IcicleProjectile>> ICICLE_PROJECTILE = ENTITIES.register(
      "icicle",
      () -> Builder.m_20704_(IcicleProjectile::new, MobCategory.MISC)
            .m_20699_(0.5F, 0.5F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "icicle").toString())
   );
   public static final RegistryObject<EntityType<FireBreathProjectile>> FIRE_BREATH_PROJECTILE = ENTITIES.register(
      "fire_breath",
      () -> Builder.m_20704_(FireBreathProjectile::new, MobCategory.MISC)
            .m_20699_(1.0F, 1.0F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "fire_breath").toString())
   );
   public static final RegistryObject<EntityType<DragonBreathProjectile>> DRAGON_BREATH_PROJECTILE = ENTITIES.register(
      "dragon_breath",
      () -> Builder.m_20704_(DragonBreathProjectile::new, MobCategory.MISC)
            .m_20699_(1.0F, 1.0F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "dragon_breath").toString())
   );
   public static final RegistryObject<EntityType<DebugWizard>> DEBUG_WIZARD = ENTITIES.register(
      "debug_wizard",
      () -> Builder.m_20704_(DebugWizard::new, MobCategory.MONSTER)
            .m_20699_(0.6F, 1.8F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "simple_wizard").toString())
   );
   public static final RegistryObject<EntityType<SummonedHorse>> SPECTRAL_STEED = ENTITIES.register(
      "spectral_steed",
      () -> Builder.m_20704_(SummonedHorse::new, MobCategory.CREATURE)
            .m_20699_(1.3964844F, 1.6F)
            .m_20702_(10)
            .m_20712_(new ResourceLocation("irons_spellbooks", "spectral_steed").toString())
   );
   public static final RegistryObject<EntityType<ShieldEntity>> SHIELD_ENTITY = ENTITIES.register(
      "shield",
      () -> Builder.m_20704_(ShieldEntity::new, MobCategory.MISC)
            .m_20699_(1.0F, 1.0F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "shield").toString())
   );
   public static final RegistryObject<EntityType<WallOfFireEntity>> WALL_OF_FIRE_ENTITY = ENTITIES.register(
      "wall_of_fire",
      () -> Builder.m_20704_(WallOfFireEntity::new, MobCategory.MISC)
            .m_20699_(10.0F, 1.0F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "wall_of_fire").toString())
   );
   public static final RegistryObject<EntityType<SummonedVex>> SUMMONED_VEX = ENTITIES.register(
      "summoned_vex",
      () -> Builder.m_20704_(SummonedVex::new, MobCategory.CREATURE)
            .m_20699_(0.4F, 0.8F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "summoned_vex").toString())
   );
   public static final RegistryObject<EntityType<PyromancerEntity>> PYROMANCER = ENTITIES.register(
      "pyromancer",
      () -> Builder.m_20704_(PyromancerEntity::new, MobCategory.MONSTER)
            .m_20699_(0.6F, 1.8F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "pyromancer").toString())
   );
   public static final RegistryObject<EntityType<CryomancerEntity>> CRYOMANCER = ENTITIES.register(
      "cryomancer",
      () -> Builder.m_20704_(CryomancerEntity::new, MobCategory.MONSTER)
            .m_20699_(0.6F, 1.8F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "cryomancer").toString())
   );
   public static final RegistryObject<EntityType<LightningLanceProjectile>> LIGHTNING_LANCE_PROJECTILE = ENTITIES.register(
      "lightning_lance",
      () -> Builder.m_20704_(LightningLanceProjectile::new, MobCategory.MISC)
            .m_20699_(1.25F, 1.25F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "lightning_lance").toString())
   );
   public static final RegistryObject<EntityType<NecromancerEntity>> NECROMANCER = ENTITIES.register(
      "necromancer",
      () -> Builder.m_20704_(NecromancerEntity::new, MobCategory.MONSTER)
            .m_20699_(0.6F, 1.8F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "necromancer").toString())
   );
   public static final RegistryObject<EntityType<SummonedZombie>> SUMMONED_ZOMBIE = ENTITIES.register(
      "summoned_zombie",
      () -> Builder.m_20704_(SummonedZombie::new, MobCategory.MONSTER)
            .m_20699_(0.6F, 1.8F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "summoned_zombie").toString())
   );
   public static final RegistryObject<EntityType<SummonedSkeleton>> SUMMONED_SKELETON = ENTITIES.register(
      "summoned_skeleton",
      () -> Builder.m_20704_(SummonedSkeleton::new, MobCategory.MONSTER)
            .m_20699_(0.6F, 1.8F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "summoned_skeleton").toString())
   );
   public static final RegistryObject<EntityType<ExtendedWitherSkull>> WITHER_SKULL_PROJECTILE = ENTITIES.register(
      "wither_skull",
      () -> Builder.m_20704_(ExtendedWitherSkull::new, MobCategory.MISC)
            .m_20699_(0.5F, 0.5F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "wither_skull").toString())
   );
   public static final RegistryObject<EntityType<MagicArrowProjectile>> MAGIC_ARROW_PROJECTILE = ENTITIES.register(
      "magic_arrow",
      () -> Builder.m_20704_(MagicArrowProjectile::new, MobCategory.MISC)
            .m_20699_(0.8F, 0.8F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "magic_arrow").toString())
   );
   public static final RegistryObject<EntityType<CreeperHeadProjectile>> CREEPER_HEAD_PROJECTILE = ENTITIES.register(
      "creeper_head",
      () -> Builder.m_20704_(CreeperHeadProjectile::new, MobCategory.MISC)
            .m_20699_(0.5F, 0.5F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "creeper_head").toString())
   );
   public static final RegistryObject<EntityType<FrozenHumanoid>> FROZEN_HUMANOID = ENTITIES.register(
      "frozen_humanoid",
      () -> Builder.m_20704_(FrozenHumanoid::new, MobCategory.MISC)
            .m_20699_(0.6F, 1.8F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "frozen_humanoid").toString())
   );
   public static final RegistryObject<EntityType<SmallMagicFireball>> SMALL_FIREBALL_PROJECTILE = ENTITIES.register(
      "small_fireball",
      () -> Builder.m_20704_(SmallMagicFireball::new, MobCategory.MISC)
            .m_20699_(0.5F, 0.5F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "small_fireball").toString())
   );
   public static final RegistryObject<EntityType<MagicFireball>> MAGIC_FIREBALL = ENTITIES.register(
      "fireball",
      () -> Builder.m_20704_(MagicFireball::new, MobCategory.MISC)
            .m_20699_(1.0F, 1.0F)
            .m_20702_(4)
            .m_20712_(new ResourceLocation("irons_spellbooks", "fireball").toString())
   );
   public static final RegistryObject<EntityType<SummonedPolarBear>> SUMMONED_POLAR_BEAR = ENTITIES.register(
      "summoned_polar_bear",
      () -> Builder.m_20704_(SummonedPolarBear::new, MobCategory.CREATURE)
            .m_20714_(new Block[]{Blocks.f_152499_})
            .m_20699_(1.4F, 1.4F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "summoned_polar_bear").toString())
   );
   public static final RegistryObject<EntityType<DeadKingBoss>> DEAD_KING = ENTITIES.register(
      "dead_king",
      () -> Builder.m_20704_(DeadKingBoss::new, MobCategory.MONSTER)
            .m_20699_(0.9F, 3.5F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "dead_king").toString())
   );
   public static final RegistryObject<EntityType<DeadKingCorpseEntity>> DEAD_KING_CORPSE = ENTITIES.register(
      "dead_king_corpse",
      () -> Builder.m_20704_(DeadKingCorpseEntity::new, MobCategory.MISC)
            .m_20699_(1.5F, 0.95F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "dead_king_corpse").toString())
   );
   public static final RegistryObject<EntityType<CatacombsZombie>> CATACOMBS_ZOMBIE = ENTITIES.register(
      "catacombs_zombie",
      () -> Builder.m_20704_(CatacombsZombie::new, MobCategory.MONSTER)
            .m_20699_(1.5F, 0.95F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "catacombs_zombie").toString())
   );
   public static final RegistryObject<EntityType<ArchevokerEntity>> ARCHEVOKER = ENTITIES.register(
      "archevoker",
      () -> Builder.m_20704_(ArchevokerEntity::new, MobCategory.MONSTER)
            .m_20699_(0.6F, 2.0F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "archevoker").toString())
   );
   public static final RegistryObject<EntityType<MagehunterVindicator>> MAGEHUNTER_VINDICATOR = ENTITIES.register(
      "magehunter_vindicator",
      () -> Builder.m_20704_(MagehunterVindicator::new, MobCategory.MONSTER)
            .m_20699_(1.5F, 0.95F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "magehunter_vindicator").toString())
   );
   public static final RegistryObject<EntityType<KeeperEntity>> KEEPER = ENTITIES.register(
      "citadel_keeper",
      () -> Builder.m_20704_(KeeperEntity::new, MobCategory.MONSTER)
            .m_20699_(0.85F, 2.3F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "citadel_keeper").toString())
   );
   public static final RegistryObject<EntityType<VoidTentacle>> SCULK_TENTACLE = ENTITIES.register(
      "sculk_tentacle",
      () -> Builder.m_20704_(VoidTentacle::new, MobCategory.MISC)
            .m_20699_(2.5F, 5.5F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "sculk_tentacle").toString())
   );
   public static final RegistryObject<EntityType<IceBlockProjectile>> ICE_BLOCK_PROJECTILE = ENTITIES.register(
      "ice_block_projectile",
      () -> Builder.m_20704_(IceBlockProjectile::new, MobCategory.MISC)
            .m_20699_(1.25F, 1.0F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "ice_block_projectile").toString())
   );
   public static final RegistryObject<EntityType<PoisonCloud>> POISON_CLOUD = ENTITIES.register(
      "poison_cloud",
      () -> Builder.m_20704_(PoisonCloud::new, MobCategory.MISC)
            .m_20699_(4.0F, 0.8F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "poison_cloud").toString())
   );
   public static final RegistryObject<EntityType<Sunbeam>> SUNBEAM = ENTITIES.register(
      "sunbeam",
      () -> Builder.m_20704_(Sunbeam::new, MobCategory.MISC)
            .m_20699_(1.5F, 8.0F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "sunbeam").toString())
   );
   public static final RegistryObject<EntityType<DragonBreathPool>> DRAGON_BREATH_POOL = ENTITIES.register(
      "dragon_breath_pool",
      () -> Builder.m_20704_(DragonBreathPool::new, MobCategory.MISC)
            .m_20699_(4.0F, 0.8F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "dragon_breath_pool").toString())
   );
   public static final RegistryObject<EntityType<PoisonBreathProjectile>> POISON_BREATH_PROJECTILE = ENTITIES.register(
      "poison_breath",
      () -> Builder.m_20704_(PoisonBreathProjectile::new, MobCategory.MISC)
            .m_20699_(1.0F, 1.0F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "poison_breath").toString())
   );
   public static final RegistryObject<EntityType<PoisonArrow>> POISON_ARROW = ENTITIES.register(
      "poison_arrow",
      () -> Builder.m_20704_(PoisonArrow::new, MobCategory.MISC)
            .m_20699_(0.5F, 0.5F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "poison_arrow").toString())
   );
   public static final RegistryObject<EntityType<SmallMagicArrow>> SMALL_MAGIC_ARROW = ENTITIES.register(
      "small_magic_arrow",
      () -> Builder.m_20704_(SmallMagicArrow::new, MobCategory.MISC)
            .m_20699_(0.5F, 0.5F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "small_magic_arrow").toString())
   );
   public static final RegistryObject<EntityType<PoisonSplash>> POISON_SPLASH = ENTITIES.register(
      "poison_splash",
      () -> Builder.m_20704_(PoisonSplash::new, MobCategory.MISC)
            .m_20699_(3.5F, 4.0F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "poison_splash").toString())
   );
   public static final RegistryObject<EntityType<SummonedFrog>> SUMMONED_FROG = ENTITIES.register(
      "summoned_frog",
      () -> Builder.m_20704_(SummonedFrog::new, MobCategory.MISC)
            .m_20699_(0.5F, 0.5F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "summoned_frog").toString())
   );
   public static final RegistryObject<EntityType<AcidOrb>> ACID_ORB = ENTITIES.register(
      "acid_orb",
      () -> Builder.m_20704_(AcidOrb::new, MobCategory.MISC)
            .m_20699_(0.75F, 0.75F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "acid_orb").toString())
   );
   public static final RegistryObject<EntityType<RootEntity>> ROOT = ENTITIES.register(
      "root",
      () -> Builder.m_20704_(RootEntity::new, MobCategory.MISC)
            .m_20699_(1.0F, 1.0F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "root").toString())
   );
   public static final RegistryObject<EntityType<BlackHole>> BLACK_HOLE = ENTITIES.register(
      "black_hole",
      () -> Builder.m_20704_(BlackHole::new, MobCategory.MISC)
            .m_20699_(11.0F, 11.0F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "black_hole").toString())
   );
   public static final RegistryObject<EntityType<BloodNeedle>> BLOOD_NEEDLE = ENTITIES.register(
      "blood_needle",
      () -> Builder.m_20704_(BloodNeedle::new, MobCategory.MISC)
            .m_20699_(0.5F, 0.5F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "blood_needle").toString())
   );
   public static final RegistryObject<EntityType<FireField>> FIRE_FIELD = ENTITIES.register(
      "fire_field",
      () -> Builder.m_20704_(FireField::new, MobCategory.MISC)
            .m_20699_(4.0F, 0.8F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "fire_field").toString())
   );
   public static final RegistryObject<EntityType<FireBomb>> FIRE_BOMB = ENTITIES.register(
      "magma_ball",
      () -> Builder.m_20704_(FireBomb::new, MobCategory.MISC)
            .m_20699_(0.75F, 0.75F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "magma_ball").toString())
   );
   public static final RegistryObject<EntityType<Comet>> COMET = ENTITIES.register(
      "comet",
      () -> Builder.m_20704_(Comet::new, MobCategory.MISC)
            .m_20699_(0.5F, 0.5F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "comet").toString())
   );
   public static final RegistryObject<EntityType<TargetedAreaEntity>> TARGET_AREA_ENTITY = ENTITIES.register(
      "target_area",
      () -> Builder.m_20704_(TargetedAreaEntity::new, MobCategory.MISC)
            .m_20699_(0.5F, 0.5F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "target_area").toString())
   );
   public static final RegistryObject<EntityType<HealingAoe>> HEALING_AOE = ENTITIES.register(
      "healing_aoe",
      () -> Builder.m_20704_(HealingAoe::new, MobCategory.MISC)
            .m_20699_(4.0F, 0.8F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "healing_aoe").toString())
   );
   public static final RegistryObject<EntityType<EarthquakeAoe>> EARTHQUAKE_AOE = ENTITIES.register(
      "earthquake_aoe",
      () -> Builder.m_20704_(EarthquakeAoe::new, MobCategory.MISC)
            .m_20699_(4.0F, 0.8F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "earthquake_aoe").toString())
   );
   public static final RegistryObject<EntityType<PriestEntity>> PRIEST = ENTITIES.register(
      "priest",
      () -> Builder.m_20704_(PriestEntity::new, MobCategory.CREATURE)
            .m_20699_(0.6F, 2.0F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "priest").toString())
   );
   public static final RegistryObject<EntityType<VisualFallingBlockEntity>> FALLING_BLOCK = ENTITIES.register(
      "visual_falling_block",
      () -> Builder.m_20704_(VisualFallingBlockEntity::new, MobCategory.MISC)
            .m_20699_(0.98F, 0.98F)
            .m_20702_(10)
            .m_20717_(20)
            .m_20712_(new ResourceLocation("irons_spellbooks", "visual_falling_block").toString())
   );
   public static final RegistryObject<EntityType<GuidingBoltProjectile>> GUIDING_BOLT = ENTITIES.register(
      "guiding_bolt",
      () -> Builder.m_20704_(GuidingBoltProjectile::new, MobCategory.MISC)
            .m_20699_(0.5F, 0.5F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "guiding_bolt").toString())
   );
   public static final RegistryObject<EntityType<GustCollider>> GUST_COLLIDER = ENTITIES.register(
      "gust",
      () -> Builder.m_20704_(GustCollider::new, MobCategory.MISC)
            .m_20699_(1.0F, 1.0F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "gust").toString())
   );
   public static final RegistryObject<EntityType<ChainLightning>> CHAIN_LIGHTNING = ENTITIES.register(
      "chain_lightning",
      () -> Builder.m_20704_(ChainLightning::new, MobCategory.MISC)
            .m_20699_(1.0F, 1.0F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "chain_lightning").toString())
   );
   public static final RegistryObject<EntityType<RayOfFrostVisualEntity>> RAY_OF_FROST_VISUAL_ENTITY = ENTITIES.register(
      "ray_of_frost",
      () -> Builder.m_20704_(RayOfFrostVisualEntity::new, MobCategory.MISC)
            .m_20699_(1.0F, 1.0F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "ray_of_frost").toString())
   );
   public static final RegistryObject<EntityType<EldritchBlastVisualEntity>> ELDRITCH_BLAST_VISUAL_ENTITY = ENTITIES.register(
      "eldritch_blast",
      () -> Builder.m_20704_(EldritchBlastVisualEntity::new, MobCategory.MISC)
            .m_20699_(1.0F, 1.0F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "eldritch_blast").toString())
   );
   public static final RegistryObject<EntityType<DevourJaw>> DEVOUR_JAW = ENTITIES.register(
      "devour_jaw",
      () -> Builder.m_20704_(DevourJaw::new, MobCategory.MISC)
            .m_20699_(2.0F, 2.0F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "devour_jaw").toString())
   );
   public static final RegistryObject<EntityType<FireflySwarmProjectile>> FIREFLY_SWARM = ENTITIES.register(
      "firefly_swarm",
      () -> Builder.m_20704_(FireflySwarmProjectile::new, MobCategory.MISC)
            .m_20699_(0.9F, 0.9F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "firefly_swarm").toString())
   );
   public static final RegistryObject<EntityType<FlameStrike>> FLAME_STRIKE = ENTITIES.register(
      "flame_strike",
      () -> Builder.m_20704_(FlameStrike::new, MobCategory.MISC)
            .m_20699_(5.0F, 1.0F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "flame_strike").toString())
   );
   public static final RegistryObject<EntityType<ArrowVolleyEntity>> ARROW_VOLLEY_ENTITY = ENTITIES.register(
      "arrow_volley",
      () -> Builder.m_20704_(ArrowVolleyEntity::new, MobCategory.MISC)
            .m_20699_(1.0F, 1.0F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "arrow_volley").toString())
   );
   public static final RegistryObject<EntityType<PortalEntity>> PORTAL = ENTITIES.register(
      "portal",
      () -> Builder.m_20704_(PortalEntity::new, MobCategory.MISC)
            .m_20699_(0.8F, 2.0F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "portal").toString())
   );
   public static final RegistryObject<EntityType<StompAoe>> STOMP_AOE = ENTITIES.register(
      "stomp_aoe",
      () -> Builder.m_20704_(StompAoe::new, MobCategory.MISC)
            .m_20699_(1.0F, 1.0F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "stomp_aoe").toString())
   );
   public static final RegistryObject<EntityType<LightningStrike>> LIGHTNING_STRIKE = ENTITIES.register(
      "lightning_strike",
      () -> Builder.m_20704_(LightningStrike::new, MobCategory.MISC)
            .m_20699_(1.0F, 1.0F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "lightning_strike").toString())
   );
   public static final RegistryObject<EntityType<ApothecaristEntity>> APOTHECARIST = ENTITIES.register(
      "apothecarist",
      () -> Builder.m_20704_(ApothecaristEntity::new, MobCategory.MONSTER)
            .m_20699_(0.6F, 1.8F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "apothecarist").toString())
   );
   public static final RegistryObject<EntityType<EchoingStrikeEntity>> ECHOING_STRIKE = ENTITIES.register(
      "echoing_strike",
      () -> Builder.m_20704_(EchoingStrikeEntity::new, MobCategory.MISC)
            .m_20699_(2.0F, 2.0F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "echoing_strike").toString())
   );
   public static final RegistryObject<EntityType<CultistEntity>> CULTIST = ENTITIES.register(
      "cultist",
      () -> Builder.m_20704_(CultistEntity::new, MobCategory.MONSTER)
            .m_20699_(0.6F, 1.8F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "cultist").toString())
   );
   public static final RegistryObject<EntityType<BallLightning>> BALL_LIGHTNING = ENTITIES.register(
      "ball_lightning",
      () -> Builder.m_20704_(BallLightning::new, MobCategory.MISC)
            .m_20699_(1.1F, 1.1F)
            .m_20702_(64)
            .m_20712_(new ResourceLocation("irons_spellbooks", "ball_lightning").toString())
   );

   public static void register(IEventBus eventBus) {
      ENTITIES.register(eventBus);
   }
}
