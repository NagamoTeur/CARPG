package net.thirdlife.iterrpg.init;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thirdlife.iterrpg.entity.AirElementalEntity;
import net.thirdlife.iterrpg.entity.AuraBoulderEntity;
import net.thirdlife.iterrpg.entity.AuraMobspawnEntity;
import net.thirdlife.iterrpg.entity.AuraSoulfireEntity;
import net.thirdlife.iterrpg.entity.AuraTearburstEntity;
import net.thirdlife.iterrpg.entity.BloatedEntity;
import net.thirdlife.iterrpg.entity.BlobEntity;
import net.thirdlife.iterrpg.entity.CaltropThrownEntity;
import net.thirdlife.iterrpg.entity.CarcassEntity;
import net.thirdlife.iterrpg.entity.ChainHandleEntity;
import net.thirdlife.iterrpg.entity.DebugMobmorphEntity;
import net.thirdlife.iterrpg.entity.DemonbloodProjectileEntity;
import net.thirdlife.iterrpg.entity.DemonsoulEntity;
import net.thirdlife.iterrpg.entity.DemonspineEntity;
import net.thirdlife.iterrpg.entity.DropletProjectileEntity;
import net.thirdlife.iterrpg.entity.EarthBoulderEntity;
import net.thirdlife.iterrpg.entity.EarthElementalEntity;
import net.thirdlife.iterrpg.entity.ElementalChargeEntity;
import net.thirdlife.iterrpg.entity.FireElementalEntity;
import net.thirdlife.iterrpg.entity.FireballProjectileEntity;
import net.thirdlife.iterrpg.entity.ForestVinesEntity;
import net.thirdlife.iterrpg.entity.GiantSpiderEntity;
import net.thirdlife.iterrpg.entity.GiantWeeperTearEntity;
import net.thirdlife.iterrpg.entity.GoblinEntity;
import net.thirdlife.iterrpg.entity.GoblinWarriorEntity;
import net.thirdlife.iterrpg.entity.GrieverEntity;
import net.thirdlife.iterrpg.entity.GrimBoulderEntity;
import net.thirdlife.iterrpg.entity.HobgoblinEntity;
import net.thirdlife.iterrpg.entity.InsatiableEntity;
import net.thirdlife.iterrpg.entity.MarrowEntity;
import net.thirdlife.iterrpg.entity.MournstoneEntity;
import net.thirdlife.iterrpg.entity.MudkinEntity;
import net.thirdlife.iterrpg.entity.PeeperEntity;
import net.thirdlife.iterrpg.entity.RevenantEntity;
import net.thirdlife.iterrpg.entity.ScallopEntity;
import net.thirdlife.iterrpg.entity.SorrowsealedEntity;
import net.thirdlife.iterrpg.entity.SpiderlingEntity;
import net.thirdlife.iterrpg.entity.VoidElementalEntity;
import net.thirdlife.iterrpg.entity.VoidPortalEntity;
import net.thirdlife.iterrpg.entity.WaterElementalEntity;
import net.thirdlife.iterrpg.entity.WeeperEntity;
import net.thirdlife.iterrpg.entity.WeeperTearEntity;
import net.thirdlife.iterrpg.entity.WindswirlEntity;

@EventBusSubscriber(
   bus = Bus.MOD
)
public class IterRpgModEntities {
   public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, "iter_rpg");
   public static final RegistryObject<EntityType<GoblinEntity>> GOBLIN = register(
      "goblin",
      Builder.m_20704_(GoblinEntity::new, MobCategory.MISC)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(GoblinEntity::new)
         .m_20699_(0.6F, 0.95F)
   );
   public static final RegistryObject<EntityType<GoblinWarriorEntity>> GOBLIN_WARRIOR = register(
      "goblin_warrior",
      Builder.m_20704_(GoblinWarriorEntity::new, MobCategory.MISC)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(GoblinWarriorEntity::new)
         .m_20699_(0.6F, 1.1F)
   );
   public static final RegistryObject<EntityType<HobgoblinEntity>> HOBGOBLIN = register(
      "hobgoblin",
      Builder.m_20704_(HobgoblinEntity::new, MobCategory.MISC)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(HobgoblinEntity::new)
         .m_20699_(0.8F, 2.4F)
   );
   public static final RegistryObject<EntityType<ScallopEntity>> SCALLOP = register(
      "scallop",
      Builder.m_20704_(ScallopEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(ScallopEntity::new)
         .m_20699_(0.9F, 0.5F)
   );
   public static final RegistryObject<EntityType<GiantSpiderEntity>> GIANT_SPIDER = register(
      "giant_spider",
      Builder.m_20704_(GiantSpiderEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(GiantSpiderEntity::new)
         .m_20699_(1.6F, 1.4F)
   );
   public static final RegistryObject<EntityType<SpiderlingEntity>> SPIDERLING = register(
      "spiderling",
      Builder.m_20704_(SpiderlingEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(SpiderlingEntity::new)
         .m_20699_(0.6F, 0.5F)
   );
   public static final RegistryObject<EntityType<PeeperEntity>> PEEPER = register(
      "peeper",
      Builder.m_20704_(PeeperEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(PeeperEntity::new)
         .m_20699_(0.9F, 1.0F)
   );
   public static final RegistryObject<EntityType<MudkinEntity>> MUDKIN = register(
      "mudkin",
      Builder.m_20704_(MudkinEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(MudkinEntity::new)
         .m_20699_(1.0F, 1.75F)
   );
   public static final RegistryObject<EntityType<GrieverEntity>> GRIEVER = register(
      "griever",
      Builder.m_20704_(GrieverEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(32)
         .setUpdateInterval(3)
         .setCustomClientFactory(GrieverEntity::new)
         .m_20699_(0.5F, 2.0F)
   );
   public static final RegistryObject<EntityType<MournstoneEntity>> MOURNSTONE = register(
      "mournstone",
      Builder.m_20704_(MournstoneEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(MournstoneEntity::new)
         .m_20719_()
         .m_20699_(0.9F, 2.8F)
   );
   public static final RegistryObject<EntityType<WeeperEntity>> WEEPER = register(
      "weeper",
      Builder.m_20704_(WeeperEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(WeeperEntity::new)
         .m_20699_(0.85F, 1.7F)
   );
   public static final RegistryObject<EntityType<SorrowsealedEntity>> SORROWSEALED = register(
      "sorrowsealed",
      Builder.m_20704_(SorrowsealedEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(SorrowsealedEntity::new)
         .m_20719_()
         .m_20699_(4.0F, 4.0F)
   );
   public static final RegistryObject<EntityType<CarcassEntity>> CARCASS = register(
      "carcass",
      Builder.m_20704_(CarcassEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(CarcassEntity::new)
         .m_20719_()
         .m_20699_(0.8F, 2.9F)
   );
   public static final RegistryObject<EntityType<DemonsoulEntity>> DEMONSOUL = register(
      "demonsoul",
      Builder.m_20704_(DemonsoulEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(128)
         .setUpdateInterval(3)
         .setCustomClientFactory(DemonsoulEntity::new)
         .m_20719_()
         .m_20699_(0.5F, 0.5F)
   );
   public static final RegistryObject<EntityType<RevenantEntity>> REVENANT = register(
      "revenant",
      Builder.m_20704_(RevenantEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(RevenantEntity::new)
         .m_20719_()
         .m_20699_(0.6F, 1.875F)
   );
   public static final RegistryObject<EntityType<EarthElementalEntity>> EARTH_ELEMENTAL = register(
      "earth_elemental",
      Builder.m_20704_(EarthElementalEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(EarthElementalEntity::new)
         .m_20699_(0.8F, 1.2F)
   );
   public static final RegistryObject<EntityType<WaterElementalEntity>> WATER_ELEMENTAL = register(
      "water_elemental",
      Builder.m_20704_(WaterElementalEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(WaterElementalEntity::new)
         .m_20699_(0.6F, 1.5F)
   );
   public static final RegistryObject<EntityType<AirElementalEntity>> AIR_ELEMENTAL = register(
      "air_elemental",
      Builder.m_20704_(AirElementalEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(AirElementalEntity::new)
         .m_20699_(0.8F, 1.2F)
   );
   public static final RegistryObject<EntityType<FireElementalEntity>> FIRE_ELEMENTAL = register(
      "fire_elemental",
      Builder.m_20704_(FireElementalEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(FireElementalEntity::new)
         .m_20719_()
         .m_20699_(0.6F, 1.6F)
   );
   public static final RegistryObject<EntityType<VoidElementalEntity>> VOID_ELEMENTAL = register(
      "void_elemental",
      Builder.m_20704_(VoidElementalEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(VoidElementalEntity::new)
         .m_20699_(0.8F, 1.6F)
   );
   public static final RegistryObject<EntityType<BloatedEntity>> BLOATED = register(
      "bloated",
      Builder.m_20704_(BloatedEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(BloatedEntity::new)
         .m_20699_(0.7F, 1.8F)
   );
   public static final RegistryObject<EntityType<InsatiableEntity>> INSATIABLE = register(
      "insatiable",
      Builder.m_20704_(InsatiableEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(InsatiableEntity::new)
         .m_20699_(0.8F, 2.5F)
   );
   public static final RegistryObject<EntityType<ChainHandleEntity>> CHAIN_HANDLE = register(
      "chain_handle",
      Builder.m_20704_(ChainHandleEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(ChainHandleEntity::new)
         .m_20719_()
         .m_20699_(0.4F, 0.4F)
   );
   public static final RegistryObject<EntityType<CaltropThrownEntity>> CALTROP_THROWN = register(
      "caltrop_thrown",
      Builder.m_20704_(CaltropThrownEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(CaltropThrownEntity::new)
         .m_20719_()
         .m_20699_(0.2F, 0.2F)
   );
   public static final RegistryObject<EntityType<BlobEntity>> BLOB = register(
      "blob",
      Builder.m_20704_(BlobEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(BlobEntity::new)
         .m_20719_()
         .m_20699_(0.44F, 0.44F)
   );
   public static final RegistryObject<EntityType<ForestVinesEntity>> FOREST_VINES = register(
      "forest_vines",
      Builder.m_20704_(ForestVinesEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(ForestVinesEntity::new)
         .m_20699_(0.6F, 1.6F)
   );
   public static final RegistryObject<EntityType<DemonspineEntity>> DEMONSPINE = register(
      "demonspine",
      Builder.m_20704_(DemonspineEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(DemonspineEntity::new)
         .m_20719_()
         .m_20699_(0.6F, 2.2F)
   );
   public static final RegistryObject<EntityType<AuraSoulfireEntity>> AURA_SOULFIRE = register(
      "aura_soulfire",
      Builder.m_20704_(AuraSoulfireEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(AuraSoulfireEntity::new)
         .m_20719_()
         .m_20699_(0.5F, 0.1F)
   );
   public static final RegistryObject<EntityType<AuraTearburstEntity>> AURA_TEARBURST = register(
      "aura_tearburst",
      Builder.m_20704_(AuraTearburstEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(AuraTearburstEntity::new)
         .m_20719_()
         .m_20699_(0.5F, 0.1F)
   );
   public static final RegistryObject<EntityType<AuraMobspawnEntity>> AURA_MOBSPAWN = register(
      "aura_mobspawn",
      Builder.m_20704_(AuraMobspawnEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(AuraMobspawnEntity::new)
         .m_20719_()
         .m_20699_(0.5F, 0.1F)
   );
   public static final RegistryObject<EntityType<AuraBoulderEntity>> AURA_BOULDER = register(
      "aura_boulder",
      Builder.m_20704_(AuraBoulderEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(AuraBoulderEntity::new)
         .m_20719_()
         .m_20699_(0.5F, 0.1F)
   );
   public static final RegistryObject<EntityType<GrimBoulderEntity>> GRIM_BOULDER = register(
      "grim_boulder",
      Builder.m_20704_(GrimBoulderEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(GrimBoulderEntity::new)
         .m_20719_()
         .m_20699_(1.0F, 1.0F)
   );
   public static final RegistryObject<EntityType<WindswirlEntity>> WINDSWIRL = register(
      "windswirl",
      Builder.m_20704_(WindswirlEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(WindswirlEntity::new)
         .m_20719_()
         .m_20699_(0.8F, 1.6F)
   );
   public static final RegistryObject<EntityType<DebugMobmorphEntity>> DEBUG_MOBMORPH = register(
      "debug_mobmorph",
      Builder.m_20704_(DebugMobmorphEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(DebugMobmorphEntity::new)
         .m_20719_()
         .m_20699_(0.1F, 0.1F)
   );
   public static final RegistryObject<EntityType<ElementalChargeEntity>> ELEMENTAL_CHARGE = register(
      "elemental_charge",
      Builder.m_20704_(ElementalChargeEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(ElementalChargeEntity::new)
         .m_20719_()
         .m_20699_(0.1F, 0.1F)
   );
   public static final RegistryObject<EntityType<MarrowEntity>> MARROW = register(
      "projectile_marrow",
      Builder.m_20704_(MarrowEntity::new, MobCategory.MISC)
         .setCustomClientFactory(MarrowEntity::new)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(1)
         .m_20699_(0.5F, 0.5F)
   );
   public static final RegistryObject<EntityType<FireballProjectileEntity>> FIREBALL_PROJECTILE = register(
      "projectile_fireball_projectile",
      Builder.m_20704_(FireballProjectileEntity::new, MobCategory.MISC)
         .setCustomClientFactory(FireballProjectileEntity::new)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(1)
         .m_20699_(0.5F, 0.5F)
   );
   public static final RegistryObject<EntityType<DropletProjectileEntity>> DROPLET_PROJECTILE = register(
      "projectile_droplet_projectile",
      Builder.m_20704_(DropletProjectileEntity::new, MobCategory.MISC)
         .setCustomClientFactory(DropletProjectileEntity::new)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(1)
         .m_20699_(0.5F, 0.5F)
   );
   public static final RegistryObject<EntityType<WeeperTearEntity>> WEEPER_TEAR = register(
      "projectile_weeper_tear",
      Builder.m_20704_(WeeperTearEntity::new, MobCategory.MISC)
         .setCustomClientFactory(WeeperTearEntity::new)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(1)
         .m_20699_(0.5F, 0.5F)
   );
   public static final RegistryObject<EntityType<DemonbloodProjectileEntity>> DEMONBLOOD_PROJECTILE = register(
      "projectile_demonblood_projectile",
      Builder.m_20704_(DemonbloodProjectileEntity::new, MobCategory.MISC)
         .setCustomClientFactory(DemonbloodProjectileEntity::new)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(1)
         .m_20699_(0.5F, 0.5F)
   );
   public static final RegistryObject<EntityType<GiantWeeperTearEntity>> GIANT_WEEPER_TEAR = register(
      "projectile_giant_weeper_tear",
      Builder.m_20704_(GiantWeeperTearEntity::new, MobCategory.MISC)
         .setCustomClientFactory(GiantWeeperTearEntity::new)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(1)
         .m_20699_(0.5F, 0.5F)
   );
   public static final RegistryObject<EntityType<EarthBoulderEntity>> EARTH_BOULDER = register(
      "earth_boulder",
      Builder.m_20704_(EarthBoulderEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(EarthBoulderEntity::new)
         .m_20719_()
         .m_20699_(0.6F, 0.6F)
   );
   public static final RegistryObject<EntityType<VoidPortalEntity>> VOID_PORTAL = register(
      "void_portal",
      Builder.m_20704_(VoidPortalEntity::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(VoidPortalEntity::new)
         .m_20719_()
         .m_20699_(0.8F, 0.8F)
   );

   private static <T extends Entity> RegistryObject<EntityType<T>> register(String registryname, Builder<T> entityTypeBuilder) {
      return REGISTRY.register(registryname, () -> entityTypeBuilder.m_20712_(registryname));
   }

   @SubscribeEvent
   public static void init(FMLCommonSetupEvent event) {
      event.enqueueWork(() -> {
         GoblinEntity.init();
         GoblinWarriorEntity.init();
         HobgoblinEntity.init();
         ScallopEntity.init();
         GiantSpiderEntity.init();
         SpiderlingEntity.init();
         PeeperEntity.init();
         MudkinEntity.init();
         GrieverEntity.init();
         MournstoneEntity.init();
         WeeperEntity.init();
         SorrowsealedEntity.init();
         CarcassEntity.init();
         DemonsoulEntity.init();
         RevenantEntity.init();
         EarthElementalEntity.init();
         WaterElementalEntity.init();
         AirElementalEntity.init();
         FireElementalEntity.init();
         VoidElementalEntity.init();
         BloatedEntity.init();
         InsatiableEntity.init();
         ChainHandleEntity.init();
         CaltropThrownEntity.init();
         BlobEntity.init();
         ForestVinesEntity.init();
         DemonspineEntity.init();
         AuraSoulfireEntity.init();
         AuraTearburstEntity.init();
         AuraMobspawnEntity.init();
         AuraBoulderEntity.init();
         GrimBoulderEntity.init();
         WindswirlEntity.init();
         DebugMobmorphEntity.init();
         ElementalChargeEntity.init();
         EarthBoulderEntity.init();
         VoidPortalEntity.init();
      });
   }

   @SubscribeEvent
   public static void registerAttributes(EntityAttributeCreationEvent event) {
      event.put((EntityType)GOBLIN.get(), GoblinEntity.createAttributes().m_22265_());
      event.put((EntityType)GOBLIN_WARRIOR.get(), GoblinWarriorEntity.createAttributes().m_22265_());
      event.put((EntityType)HOBGOBLIN.get(), HobgoblinEntity.createAttributes().m_22265_());
      event.put((EntityType)SCALLOP.get(), ScallopEntity.createAttributes().m_22265_());
      event.put((EntityType)GIANT_SPIDER.get(), GiantSpiderEntity.createAttributes().m_22265_());
      event.put((EntityType)SPIDERLING.get(), SpiderlingEntity.createAttributes().m_22265_());
      event.put((EntityType)PEEPER.get(), PeeperEntity.createAttributes().m_22265_());
      event.put((EntityType)MUDKIN.get(), MudkinEntity.createAttributes().m_22265_());
      event.put((EntityType)GRIEVER.get(), GrieverEntity.createAttributes().m_22265_());
      event.put((EntityType)MOURNSTONE.get(), MournstoneEntity.createAttributes().m_22265_());
      event.put((EntityType)WEEPER.get(), WeeperEntity.createAttributes().m_22265_());
      event.put((EntityType)SORROWSEALED.get(), SorrowsealedEntity.createAttributes().m_22265_());
      event.put((EntityType)CARCASS.get(), CarcassEntity.createAttributes().m_22265_());
      event.put((EntityType)DEMONSOUL.get(), DemonsoulEntity.createAttributes().m_22265_());
      event.put((EntityType)REVENANT.get(), RevenantEntity.createAttributes().m_22265_());
      event.put((EntityType)EARTH_ELEMENTAL.get(), EarthElementalEntity.createAttributes().m_22265_());
      event.put((EntityType)WATER_ELEMENTAL.get(), WaterElementalEntity.createAttributes().m_22265_());
      event.put((EntityType)AIR_ELEMENTAL.get(), AirElementalEntity.createAttributes().m_22265_());
      event.put((EntityType)FIRE_ELEMENTAL.get(), FireElementalEntity.createAttributes().m_22265_());
      event.put((EntityType)VOID_ELEMENTAL.get(), VoidElementalEntity.createAttributes().m_22265_());
      event.put((EntityType)BLOATED.get(), BloatedEntity.createAttributes().m_22265_());
      event.put((EntityType)INSATIABLE.get(), InsatiableEntity.createAttributes().m_22265_());
      event.put((EntityType)CHAIN_HANDLE.get(), ChainHandleEntity.createAttributes().m_22265_());
      event.put((EntityType)CALTROP_THROWN.get(), CaltropThrownEntity.createAttributes().m_22265_());
      event.put((EntityType)BLOB.get(), BlobEntity.createAttributes().m_22265_());
      event.put((EntityType)FOREST_VINES.get(), ForestVinesEntity.createAttributes().m_22265_());
      event.put((EntityType)DEMONSPINE.get(), DemonspineEntity.createAttributes().m_22265_());
      event.put((EntityType)AURA_SOULFIRE.get(), AuraSoulfireEntity.createAttributes().m_22265_());
      event.put((EntityType)AURA_TEARBURST.get(), AuraTearburstEntity.createAttributes().m_22265_());
      event.put((EntityType)AURA_MOBSPAWN.get(), AuraMobspawnEntity.createAttributes().m_22265_());
      event.put((EntityType)AURA_BOULDER.get(), AuraBoulderEntity.createAttributes().m_22265_());
      event.put((EntityType)GRIM_BOULDER.get(), GrimBoulderEntity.createAttributes().m_22265_());
      event.put((EntityType)WINDSWIRL.get(), WindswirlEntity.createAttributes().m_22265_());
      event.put((EntityType)DEBUG_MOBMORPH.get(), DebugMobmorphEntity.createAttributes().m_22265_());
      event.put((EntityType)ELEMENTAL_CHARGE.get(), ElementalChargeEntity.createAttributes().m_22265_());
      event.put((EntityType)EARTH_BOULDER.get(), EarthBoulderEntity.createAttributes().m_22265_());
      event.put((EntityType)VOID_PORTAL.get(), VoidPortalEntity.createAttributes().m_22265_());
   }
}
