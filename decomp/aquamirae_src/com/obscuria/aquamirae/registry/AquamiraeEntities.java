package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.common.entities.Anglerfish;
import com.obscuria.aquamirae.common.entities.CaptainCornelia;
import com.obscuria.aquamirae.common.entities.Eel;
import com.obscuria.aquamirae.common.entities.GoldenMoth;
import com.obscuria.aquamirae.common.entities.LuminousJelly;
import com.obscuria.aquamirae.common.entities.Maw;
import com.obscuria.aquamirae.common.entities.MazeMother;
import com.obscuria.aquamirae.common.entities.PillagersPatrol;
import com.obscuria.aquamirae.common.entities.Spinefish;
import com.obscuria.aquamirae.common.entities.TorturedSoul;
import com.obscuria.aquamirae.common.entities.projectiles.MazeRose;
import com.obscuria.aquamirae.common.entities.projectiles.PoisonedChakra;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.entity.SpawnPlacements.Type;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent.Operation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(
   bus = Bus.MOD
)
public class AquamiraeEntities {
   public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, "aquamirae");
   public static final RegistryObject<EntityType<GoldenMoth>> GOLDEN_MOTH = register(
      "golden_moth",
      Builder.m_20704_(GoldenMoth::new, MobCategory.AMBIENT)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(128)
         .setUpdateInterval(3)
         .setCustomClientFactory(GoldenMoth::new)
         .m_20719_()
         .m_20699_(0.5F, 0.2F)
   );
   public static final RegistryObject<EntityType<Maw>> MAW = register(
      "maw",
      Builder.m_20704_(Maw::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(Maw::new)
         .m_20699_(1.2F, 1.2F)
   );
   public static final RegistryObject<EntityType<Anglerfish>> ANGLERFISH = register(
      "anglerfish",
      Builder.m_20704_(Anglerfish::new, MobCategory.WATER_CREATURE)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(Anglerfish::new)
         .m_20699_(2.0F, 2.6F)
   );
   public static final RegistryObject<EntityType<MazeMother>> MAZE_MOTHER = register(
      "maze_mother",
      Builder.m_20704_(MazeMother::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(128)
         .setUpdateInterval(3)
         .setCustomClientFactory(MazeMother::new)
         .m_20699_(8.0F, 3.0F)
   );
   public static final RegistryObject<EntityType<CaptainCornelia>> CAPTAIN_CORNELIA = register(
      "captain_cornelia",
      Builder.m_20704_(CaptainCornelia::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(CaptainCornelia::new)
         .m_20719_()
         .m_20699_(0.6F, 2.3F)
   );
   public static final RegistryObject<EntityType<PillagersPatrol>> PILLAGERS_PATROL = register(
      "pillagers_patrol",
      Builder.m_20704_(PillagersPatrol::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(false)
         .setTrackingRange(8)
         .setUpdateInterval(3)
         .setCustomClientFactory(PillagersPatrol::new)
         .m_20719_()
         .m_20699_(1.0F, 1.0F)
   );
   public static final RegistryObject<EntityType<TorturedSoul>> TORTURED_SOUL = register(
      "tortured_soul",
      Builder.m_20704_(TorturedSoul::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(TorturedSoul::new)
         .m_20699_(0.6F, 1.8F)
   );
   public static final RegistryObject<EntityType<Eel>> EEL = register(
      "eel",
      Builder.m_20704_(Eel::new, MobCategory.MONSTER)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(64)
         .setUpdateInterval(3)
         .setCustomClientFactory(Eel::new)
         .m_20699_(2.4F, 3.4F)
   );
   public static final RegistryObject<EntityType<MazeRose>> MAZE_ROSE = register(
      "maze_rose",
      Builder.m_20704_(MazeRose::new, MobCategory.MISC)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(128)
         .setUpdateInterval(3)
         .setCustomClientFactory(MazeRose::new)
         .m_20699_(1.3F, 0.15F)
   );
   public static final RegistryObject<EntityType<PoisonedChakra>> POISONED_CHAKRA = register(
      "poisoned_chakra",
      Builder.m_20704_(PoisonedChakra::new, MobCategory.MISC)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(128)
         .setUpdateInterval(3)
         .setCustomClientFactory(PoisonedChakra::new)
         .m_20699_(1.3F, 0.15F)
   );
   public static final RegistryObject<EntityType<Spinefish>> SPINEFISH = register(
      "spinefish",
      Builder.m_20704_(Spinefish::new, MobCategory.WATER_AMBIENT)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(128)
         .setUpdateInterval(3)
         .setCustomClientFactory(Spinefish::new)
         .m_20699_(0.7F, 0.7F)
   );
   public static final RegistryObject<EntityType<LuminousJelly>> LUMINOUS_JELLY = register(
      "luminous_jelly",
      Builder.m_20704_(LuminousJelly::new, MobCategory.WATER_AMBIENT)
         .setShouldReceiveVelocityUpdates(true)
         .setTrackingRange(128)
         .setUpdateInterval(3)
         .setCustomClientFactory(LuminousJelly::new)
         .m_20699_(0.5F, 0.9F)
   );

   private static <T extends Entity> RegistryObject<EntityType<T>> register(String registryname, Builder<T> entityTypeBuilder) {
      return REGISTRY.register(registryname, () -> entityTypeBuilder.m_20712_(registryname));
   }

   @SubscribeEvent
   public static void registerAttributes(EntityAttributeCreationEvent event) {
      event.put((EntityType)GOLDEN_MOTH.get(), GoldenMoth.createAttributes().m_22265_());
      event.put((EntityType)MAW.get(), Maw.createAttributes().m_22265_());
      event.put((EntityType)ANGLERFISH.get(), Anglerfish.createAttributes().m_22265_());
      event.put((EntityType)MAZE_MOTHER.get(), MazeMother.createAttributes().m_22265_());
      event.put((EntityType)CAPTAIN_CORNELIA.get(), CaptainCornelia.createAttributes().m_22265_());
      event.put((EntityType)PILLAGERS_PATROL.get(), Mob.m_21552_().m_22265_());
      event.put((EntityType)TORTURED_SOUL.get(), TorturedSoul.createAttributes().m_22265_());
      event.put((EntityType)EEL.get(), Eel.createAttributes().m_22265_());
      event.put((EntityType)SPINEFISH.get(), Mob.m_21552_().m_22265_());
      event.put((EntityType)LUMINOUS_JELLY.get(), Mob.m_21552_().m_22265_());
      event.put((EntityType)POISONED_CHAKRA.get(), LivingEntity.m_21183_().m_22265_());
      event.put((EntityType)MAZE_ROSE.get(), LivingEntity.m_21183_().m_22265_());
   }

   @SubscribeEvent
   public static void registerSpawns(SpawnPlacementRegisterEvent event) {
      event.register((EntityType)GOLDEN_MOTH.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, GoldenMoth.getSpawnRules(), Operation.REPLACE);
      event.register((EntityType)SPINEFISH.get(), Type.IN_WATER, Types.MOTION_BLOCKING_NO_LEAVES, Spinefish.getSpawnRules(), Operation.REPLACE);
      event.register((EntityType)ANGLERFISH.get(), Type.IN_WATER, Types.MOTION_BLOCKING_NO_LEAVES, Anglerfish.getSpawnRules(), Operation.REPLACE);
      event.register((EntityType)MAW.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Maw.getSpawnRules(), Operation.REPLACE);
      event.register((EntityType)TORTURED_SOUL.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, TorturedSoul.getSpawnRules(), Operation.REPLACE);
      event.register((EntityType)PILLAGERS_PATROL.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, PillagersPatrol.getSpawnRules(), Operation.REPLACE);
   }
}
