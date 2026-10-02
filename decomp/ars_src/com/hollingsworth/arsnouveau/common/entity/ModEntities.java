package com.hollingsworth.arsnouveau.common.entity;

import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.common.entity.familiar.FamiliarAmethystGolem;
import com.hollingsworth.arsnouveau.common.entity.familiar.FamiliarBookwyrm;
import com.hollingsworth.arsnouveau.common.entity.familiar.FamiliarDrygmy;
import com.hollingsworth.arsnouveau.common.entity.familiar.FamiliarEntity;
import com.hollingsworth.arsnouveau.common.entity.familiar.FamiliarStarbuncle;
import com.hollingsworth.arsnouveau.common.entity.familiar.FamiliarWhirlisprig;
import com.hollingsworth.arsnouveau.common.entity.familiar.FamiliarWixie;
import com.hollingsworth.arsnouveau.common.lib.LibEntityNames;
import com.hollingsworth.arsnouveau.common.light.LightManager;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentSensitive;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectColdSnap;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectFlare;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectFreeze;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectHarm;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectHex;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectIgnite;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectSnare;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectWither;
import com.hollingsworth.arsnouveau.common.spell.method.MethodProjectile;
import com.hollingsworth.arsnouveau.setup.Config;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.entity.SpawnPlacements.Type;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
   public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, "ars_nouveau");
   public static final RegistryObject<EntityType<EntityProjectileSpell>> SPELL_PROJ = registerEntity(
      "spell_proj",
      Builder.m_20704_(EntityProjectileSpell::new, MobCategory.MISC)
         .m_20699_(0.5F, 0.5F)
         .m_20716_()
         .setTrackingRange(20)
         .m_20719_()
         .setShouldReceiveVelocityUpdates(true)
         .setUpdateInterval(120)
         .setCustomClientFactory(EntityProjectileSpell::new)
   );
   public static final RegistryObject<EntityType<EntityAllyVex>> ALLY_VEX = registerEntity(
      "ally_vex", Builder.m_20704_(EntityAllyVex::new, MobCategory.MISC).m_20699_(0.4F, 0.8F).m_20719_()
   );
   public static final RegistryObject<EntityType<EntityEvokerFangs>> ENTITY_EVOKER_FANGS_ENTITY_TYPE = registerEntity(
      "fangs", Builder.m_20704_(EntityEvokerFangs::new, MobCategory.MISC).m_20699_(0.5F, 0.8F).setUpdateInterval(60)
   );
   public static final RegistryObject<EntityType<EntityBookwyrm>> ENTITY_BOOKWYRM_TYPE = registerEntity(
      "bookwyrm", Builder.m_20704_(EntityBookwyrm::new, MobCategory.MISC).m_20699_(0.4F, 0.6F).setTrackingRange(10).setShouldReceiveVelocityUpdates(true)
   );
   public static final RegistryObject<EntityType<Starbuncle>> STARBUNCLE_TYPE = registerEntity(
      "starbuncle", Builder.m_20704_(Starbuncle::new, MobCategory.CREATURE).m_20699_(0.6F, 0.63F).setTrackingRange(10).setShouldReceiveVelocityUpdates(true)
   );
   public static final RegistryObject<EntityType<EntityFollowProjectile>> ENTITY_FOLLOW_PROJ = registerEntity(
      "follow_proj",
      Builder.m_20704_(EntityFollowProjectile::new, MobCategory.MISC)
         .m_20699_(0.5F, 0.5F)
         .m_20716_()
         .m_20719_()
         .setTrackingRange(10)
         .setShouldReceiveVelocityUpdates(true)
         .setCustomClientFactory(EntityFollowProjectile::new)
   );
   public static final RegistryObject<EntityType<Whirlisprig>> WHIRLISPRIG_TYPE = registerEntity(
      "whirlisprig", Builder.m_20704_(Whirlisprig::new, MobCategory.CREATURE).m_20699_(0.6F, 0.98F).setTrackingRange(10).setShouldReceiveVelocityUpdates(true)
   );
   public static final RegistryObject<EntityType<EntityWixie>> ENTITY_WIXIE_TYPE = registerEntity(
      "wixie", Builder.m_20704_(EntityWixie::new, MobCategory.MISC).m_20699_(0.6F, 0.98F).setTrackingRange(10).setShouldReceiveVelocityUpdates(true)
   );
   public static final RegistryObject<EntityType<EntityFlyingItem>> ENTITY_FLYING_ITEM = registerEntity(
      "flying_item",
      Builder.m_20704_(EntityFlyingItem::new, MobCategory.MISC)
         .m_20699_(0.5F, 0.5F)
         .m_20719_()
         .setTrackingRange(10)
         .setUpdateInterval(60)
         .setShouldReceiveVelocityUpdates(true)
         .setCustomClientFactory(EntityFlyingItem::new)
         .m_20716_()
   );
   public static final RegistryObject<EntityType<EntityRitualProjectile>> ENTITY_RITUAL = registerEntity(
      "ritual",
      Builder.m_20704_(EntityRitualProjectile::new, MobCategory.MISC)
         .m_20699_(0.5F, 0.5F)
         .setTrackingRange(10)
         .setUpdateInterval(60)
         .setShouldReceiveVelocityUpdates(true)
         .setCustomClientFactory(EntityRitualProjectile::new)
   );
   public static final RegistryObject<EntityType<WildenHunter>> WILDEN_HUNTER = registerEntity(
      "wilden_hunter", Builder.m_20704_(WildenHunter::new, MobCategory.MONSTER).m_20699_(1.2F, 1.2F).setTrackingRange(10).setShouldReceiveVelocityUpdates(true)
   );
   public static final RegistryObject<EntityType<EntitySpellArrow>> ENTITY_SPELL_ARROW = registerEntity(
      "spell_arrow",
      Builder.m_20704_(EntitySpellArrow::new, MobCategory.MISC)
         .m_20702_(20)
         .m_20717_(20)
         .setShouldReceiveVelocityUpdates(true)
         .setCustomClientFactory(EntitySpellArrow::new)
   );
   public static final RegistryObject<EntityType<SummonWolf>> SUMMON_WOLF = registerEntity(
      "summon_wolf", Builder.m_20704_(SummonWolf::new, MobCategory.CREATURE).m_20699_(0.6F, 0.85F).m_20702_(10)
   );
   public static final RegistryObject<EntityType<WildenStalker>> WILDEN_STALKER = registerEntity(
      "wilden_stalker",
      Builder.m_20704_(WildenStalker::new, MobCategory.MONSTER).m_20699_(0.95F, 1.0F).setTrackingRange(10).setShouldReceiveVelocityUpdates(true)
   );
   public static final RegistryObject<EntityType<SummonHorse>> SUMMON_HORSE = registerEntity(
      "summon_horse", Builder.m_20704_(SummonHorse::new, MobCategory.CREATURE).m_20699_(1.3964844F, 1.6F).m_20702_(10)
   );
   public static final RegistryObject<EntityType<SummonSkeleton>> SUMMON_SKELETON = registerEntity(
      "summon_skeleton", Builder.m_20704_(SummonSkeleton::new, MobCategory.CREATURE).m_20699_(1.0F, 1.8F).m_20702_(10)
   );
   public static final RegistryObject<EntityType<WildenGuardian>> WILDEN_GUARDIAN = registerEntity(
      "wilden_guardian",
      Builder.m_20704_(WildenGuardian::new, MobCategory.MONSTER).m_20699_(1.15F, 1.15F).setTrackingRange(10).setShouldReceiveVelocityUpdates(true)
   );
   public static final RegistryObject<EntityType<WildenChimera>> WILDEN_BOSS = registerEntity(
      "wilden_boss", Builder.m_20704_(WildenChimera::new, MobCategory.MONSTER).m_20699_(2.5F, 2.25F).setTrackingRange(10).setShouldReceiveVelocityUpdates(true)
   );
   public static final RegistryObject<EntityType<LightningEntity>> LIGHTNING_ENTITY = registerEntity(
      "an_lightning",
      Builder.m_20704_(LightningEntity::new, MobCategory.MISC)
         .m_20699_(0.0F, 0.0F)
         .m_20702_(16)
         .m_20717_(Integer.MAX_VALUE)
         .setShouldReceiveVelocityUpdates(true)
         .setUpdateInterval(60)
   );
   public static final RegistryObject<EntityType<EntityDummy>> ENTITY_DUMMY = registerEntity(
      "dummy", Builder.m_20704_(EntityDummy::new, MobCategory.MISC).m_20699_(1.0F, 2.0F).setTrackingRange(10).setShouldReceiveVelocityUpdates(true)
   );
   public static final RegistryObject<EntityType<EntityDrygmy>> ENTITY_DRYGMY = registerEntity(
      "drygmy", Builder.m_20704_(EntityDrygmy::new, MobCategory.CREATURE).m_20699_(0.6F, 0.85F).m_20702_(10)
   );
   public static final RegistryObject<EntityType<EntityOrbitProjectile>> ORBIT_SPELL = registerEntity(
      "orbit",
      Builder.m_20704_(EntityOrbitProjectile::new, MobCategory.MISC)
         .m_20699_(0.5F, 0.5F)
         .m_20719_()
         .m_20702_(20)
         .m_20717_(20)
         .setShouldReceiveVelocityUpdates(true)
         .setCustomClientFactory(EntityOrbitProjectile::new)
   );
   public static final RegistryObject<EntityType<EntityChimeraProjectile>> ENTITY_CHIMERA_SPIKE = registerEntity(
      "spike",
      Builder.m_20704_(EntityChimeraProjectile::new, MobCategory.MISC)
         .m_20702_(20)
         .m_20717_(20)
         .setShouldReceiveVelocityUpdates(true)
         .setCustomClientFactory(EntityChimeraProjectile::new)
   );
   public static final RegistryObject<EntityType<FamiliarStarbuncle>> ENTITY_FAMILIAR_STARBUNCLE = registerEntity(
      LibEntityNames.FAMILIAR_STARBUNCLE, Builder.m_20704_(FamiliarStarbuncle::new, MobCategory.CREATURE).m_20699_(0.5F, 0.5F).setTrackingRange(10)
   );
   public static final RegistryObject<EntityType<FamiliarWixie>> ENTITY_FAMILIAR_WIXIE = registerEntity(
      LibEntityNames.FAMILIAR_WIXIE, Builder.m_20704_(FamiliarWixie::new, MobCategory.CREATURE).m_20699_(0.5F, 0.5F).setTrackingRange(10)
   );
   public static final RegistryObject<EntityType<FamiliarBookwyrm>> ENTITY_FAMILIAR_BOOKWYRM = registerEntity(
      LibEntityNames.FAMILIAR_BOOKWYRM, Builder.m_20704_(FamiliarBookwyrm::new, MobCategory.CREATURE).m_20699_(0.5F, 0.5F).setTrackingRange(10)
   );
   public static final RegistryObject<EntityType<FamiliarDrygmy>> ENTITY_FAMILIAR_DRYGMY = registerEntity(
      LibEntityNames.FAMILIAR_DRYGMY, Builder.m_20704_(FamiliarDrygmy::new, MobCategory.CREATURE).m_20699_(0.5F, 0.5F).setTrackingRange(10)
   );
   public static final RegistryObject<EntityType<FamiliarWhirlisprig>> ENTITY_FAMILIAR_SYLPH = registerEntity(
      LibEntityNames.FAMILIAR_WHIRLISPRIG, Builder.m_20704_(FamiliarWhirlisprig::new, MobCategory.CREATURE).m_20699_(0.5F, 0.5F).setTrackingRange(10)
   );
   public static final RegistryObject<EntityType<FamiliarAmethystGolem>> FAMILIAR_AMETHYST_GOLEM = registerEntity(
      LibEntityNames.FAMILIAR_AMETHYST_GOLEM, Builder.m_20704_(FamiliarAmethystGolem::new, MobCategory.CREATURE).m_20699_(1.0F, 1.0F).setTrackingRange(10)
   );
   public static final RegistryObject<EntityType<EntityLingeringSpell>> LINGER_SPELL = registerEntity(
      "linger",
      Builder.m_20704_(EntityLingeringSpell::new, MobCategory.MISC)
         .m_20699_(0.5F, 0.5F)
         .setTrackingRange(20)
         .setShouldReceiveVelocityUpdates(true)
         .m_20716_()
         .setUpdateInterval(120)
         .setCustomClientFactory(EntityLingeringSpell::new)
   );
   public static final RegistryObject<EntityType<WealdWalker>> ENTITY_CASCADING_WEALD = registerEntity(
      LibEntityNames.CASCADING_WEALD_WALKER, Builder.m_20704_((type, world) -> {
         WealdWalker walker = new WealdWalker(type, world);
         walker.spell = new Spell(MethodProjectile.INSTANCE, EffectFreeze.INSTANCE, EffectColdSnap.INSTANCE);
         walker.color = new ParticleColor(50, 50, 250);
         return walker;
      }, MobCategory.CREATURE).m_20699_(1.4F, 3.0F).setTrackingRange(10)
   );
   public static final RegistryObject<EntityType<WealdWalker>> ENTITY_FLOURISHING_WEALD = registerEntity(
      LibEntityNames.FLOURISHING_WEALD_WALKER, Builder.m_20704_((type, world) -> {
         WealdWalker walker = new WealdWalker(type, world);
         walker.spell = new Spell(MethodProjectile.INSTANCE, EffectHarm.INSTANCE, AugmentAmplify.INSTANCE, AugmentAmplify.INSTANCE, EffectSnare.INSTANCE);
         walker.color = new ParticleColor(50, 250, 55);
         return walker;
      }, MobCategory.CREATURE).m_20699_(1.4F, 3.0F).setTrackingRange(10)
   );
   public static final RegistryObject<EntityType<WealdWalker>> ENTITY_BLAZING_WEALD = registerEntity(
      LibEntityNames.BLAZING_WEALD_WALKER, Builder.m_20704_((type, world) -> {
         WealdWalker walker = new WealdWalker(type, world);
         walker.spell = new Spell(MethodProjectile.INSTANCE, EffectIgnite.INSTANCE, AugmentSensitive.INSTANCE, EffectFlare.INSTANCE);
         walker.color = new ParticleColor(250, 15, 15);
         return walker;
      }, MobCategory.CREATURE).m_20699_(1.4F, 3.0F).setTrackingRange(10)
   );
   public static final RegistryObject<EntityType<WealdWalker>> ENTITY_VEXING_WEALD = registerEntity(
      LibEntityNames.VEXING_WEALD_WALKER, Builder.m_20704_((type, world) -> {
         WealdWalker walker = new WealdWalker(type, world);
         walker.spell = new Spell(MethodProjectile.INSTANCE, EffectHex.INSTANCE, EffectWither.INSTANCE, AugmentAmplify.INSTANCE, AugmentAmplify.INSTANCE);
         walker.color = new ParticleColor(250, 50, 250);
         return walker;
      }, MobCategory.CREATURE).m_20699_(1.4F, 3.0F).setTrackingRange(10)
   );
   public static final RegistryObject<EntityType<AmethystGolem>> AMETHYST_GOLEM = registerEntity(
      "amethyst_golem", Builder.m_20704_(AmethystGolem::new, MobCategory.CREATURE).m_20699_(1.0F, 1.0F).setTrackingRange(10)
   );
   public static final RegistryObject<EntityType<ScryerCamera>> SCRYER_CAMERA = registerEntity(
      "scryer_camera",
      Builder.m_20704_(ScryerCamera::new, MobCategory.MISC)
         .m_20699_(1.0E-4F, 1.0E-4F)
         .setTrackingRange(256)
         .setUpdateInterval(20)
         .setShouldReceiveVelocityUpdates(true)
   );
   public static final RegistryObject<EntityType<EnchantedFallingBlock>> ENCHANTED_FALLING_BLOCK = registerEntity(
      "enchanted_falling_block",
      Builder.m_20704_(EnchantedFallingBlock::new, MobCategory.MISC).m_20699_(0.98F, 0.98F).setShouldReceiveVelocityUpdates(true).setTrackingRange(256)
   );
   public static final RegistryObject<EntityType<EnchantedMageblock>> ENCHANTED_MAGE_BLOCK = registerEntity(
      "enchanted_mage_block",
      Builder.m_20704_(EnchantedMageblock::new, MobCategory.MISC).m_20699_(0.98F, 0.98F).setShouldReceiveVelocityUpdates(true).setTrackingRange(256)
   );
   public static final RegistryObject<EntityType<EnchantedSkull>> ENCHANTED_HEAD_BLOCK = registerEntity(
      "enchanted_head_block",
      Builder.m_20704_(EnchantedSkull::new, MobCategory.MISC).m_20699_(0.98F, 0.98F).setShouldReceiveVelocityUpdates(true).setTrackingRange(256)
   );
   public static final RegistryObject<EntityType<GiftStarbuncle>> GIFT_STARBY = registerEntity(
      "gift_starby",
      Builder.m_20704_(GiftStarbuncle::new, MobCategory.CREATURE).m_20699_(0.6F, 0.63F).setTrackingRange(10).setShouldReceiveVelocityUpdates(true)
   );
   public static final RegistryObject<EntityType<EntityWallSpell>> WALL_SPELL = registerEntity(
      "wall",
      Builder.m_20704_(EntityWallSpell::new, MobCategory.MISC)
         .m_20699_(0.5F, 0.5F)
         .setTrackingRange(20)
         .m_20716_()
         .setShouldReceiveVelocityUpdates(true)
         .setUpdateInterval(120)
         .setCustomClientFactory(EntityWallSpell::new)
   );
   public static final RegistryObject<EntityType<AnimBlockSummon>> ANIMATED_BLOCK = registerEntity(
      "animated_block", Builder.m_20704_(AnimBlockSummon::new, MobCategory.MISC).m_20699_(1.0F, 1.5F).m_20716_().setTrackingRange(10)
   );
   public static final RegistryObject<EntityType<AnimHeadSummon>> ANIMATED_HEAD = registerEntity(
      "animated_head", Builder.m_20704_(AnimHeadSummon::new, MobCategory.MISC).m_20699_(1.0F, 1.5F).m_20716_().setTrackingRange(10)
   );
   public static final RegistryObject<EntityType<Lily>> LILY = registerEntity(
      "lily", Builder.m_20704_(Lily::new, MobCategory.MISC).m_20699_(0.5F, 0.75F).setTrackingRange(10)
   );

   static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(String name, Builder<T> builder) {
      return ENTITIES.register(name, () -> builder.m_20712_("ars_nouveau:" + name));
   }

   public static void registerPlacements() {
      SpawnPlacements.m_21754_((EntityType)STARBUNCLE_TYPE.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, ModEntities::genericGroundSpawn);
      SpawnPlacements.m_21754_((EntityType)GIFT_STARBY.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, ModEntities::genericGroundSpawn);
      SpawnPlacements.m_21754_((EntityType)WHIRLISPRIG_TYPE.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, ModEntities::genericGroundSpawn);
      SpawnPlacements.m_21754_((EntityType)ENTITY_DRYGMY.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, ModEntities::genericGroundSpawn);
      SpawnPlacements.m_21754_((EntityType)WILDEN_GUARDIAN.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, ModEntities::wildenSpawnRules);
      SpawnPlacements.m_21754_((EntityType)WILDEN_HUNTER.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, ModEntities::wildenSpawnRules);
      SpawnPlacements.m_21754_((EntityType)WILDEN_STALKER.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, ModEntities::wildenSpawnRules);
      SpawnPlacements.m_21754_((EntityType)ENTITY_BLAZING_WEALD.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, ModEntities::genericGroundSpawn);
      SpawnPlacements.m_21754_((EntityType)ENTITY_CASCADING_WEALD.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, ModEntities::genericGroundSpawn);
      SpawnPlacements.m_21754_((EntityType)ENTITY_FLOURISHING_WEALD.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, ModEntities::genericGroundSpawn);
      SpawnPlacements.m_21754_((EntityType)ENTITY_VEXING_WEALD.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, ModEntities::genericGroundSpawn);
      LightManager.init();
   }

   public static boolean canMonsterSpawnInLight(
      EntityType<? extends Monster> type, ServerLevelAccessor worldIn, MobSpawnType reason, BlockPos pos, RandomSource randomIn
   ) {
      return Monster.m_219013_(type, worldIn, reason, pos, randomIn)
         && !((List)Config.DIMENSION_BLACKLIST.get()).contains(worldIn.m_6018_().m_46472_().m_135782_().toString());
   }

   public static boolean wildenSpawnRules(
      EntityType<? extends Monster> type, ServerLevelAccessor worldIn, MobSpawnType reason, BlockPos pos, RandomSource randomIn
   ) {
      return worldIn.m_46791_() != Difficulty.PEACEFUL
         && Monster.m_219013_(type, worldIn, reason, pos, randomIn)
         && !((List)Config.DIMENSION_BLACKLIST.get()).contains(worldIn.m_6018_().m_46472_().m_135782_().toString());
   }

   public static boolean guardianSpawnRules(
      EntityType<Drowned> pDrowned, ServerLevelAccessor pServerLevel, MobSpawnType pMobSpawnType, BlockPos pPos, RandomSource pRandom
   ) {
      return pServerLevel.m_46791_() != Difficulty.PEACEFUL
         && (pMobSpawnType != MobSpawnType.SPAWNER || pServerLevel.m_6425_(pPos).m_205070_(FluidTags.f_13131_));
   }

   public static boolean genericGroundSpawn(EntityType<? extends Entity> animal, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, RandomSource random) {
      return worldIn.m_8055_(pos.m_7495_()).m_60713_(Blocks.f_50440_) && worldIn.m_45524_(pos, 0) > 8;
   }

   @EventBusSubscriber(
      modid = "ars_nouveau",
      bus = Bus.MOD
   )
   public static class RegistrationHandler {
      @SubscribeEvent
      public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
         event.put((EntityType)ModEntities.ENTITY_BOOKWYRM_TYPE.get(), EntityBookwyrm.attributes().m_22265_());
         event.put((EntityType)ModEntities.ALLY_VEX.get(), Vex.m_34040_().m_22265_());
         event.put((EntityType)ModEntities.STARBUNCLE_TYPE.get(), Starbuncle.attributes().m_22265_());
         event.put((EntityType)ModEntities.WHIRLISPRIG_TYPE.get(), Whirlisprig.attributes().m_22265_());
         event.put((EntityType)ModEntities.ENTITY_DRYGMY.get(), Whirlisprig.attributes().m_22265_());
         event.put((EntityType)ModEntities.ENTITY_WIXIE_TYPE.get(), EntityWixie.attributes().m_22265_());
         event.put((EntityType)ModEntities.WILDEN_HUNTER.get(), WildenHunter.getModdedAttributes().m_22265_());
         event.put((EntityType)ModEntities.WILDEN_STALKER.get(), WildenStalker.getModdedAttributes().m_22265_());
         event.put((EntityType)ModEntities.SUMMON_WOLF.get(), Wolf.m_30425_().m_22265_());
         event.put((EntityType)ModEntities.SUMMON_HORSE.get(), AbstractHorse.m_30627_().m_22265_());
         event.put((EntityType)ModEntities.WILDEN_GUARDIAN.get(), WildenGuardian.getModdedAttributes().m_22265_());
         event.put(
            (EntityType)ModEntities.ENTITY_DUMMY.get(), Mob.m_21552_().m_22268_(Attributes.f_22276_, 20.0).m_22268_(Attributes.f_22279_, 0.25).m_22265_()
         );
         event.put((EntityType)ModEntities.WILDEN_BOSS.get(), WildenChimera.getModdedAttributes().m_22265_());
         event.put((EntityType)ModEntities.ENTITY_FAMILIAR_STARBUNCLE.get(), FamiliarEntity.attributes().m_22265_());
         event.put((EntityType)ModEntities.ENTITY_FAMILIAR_BOOKWYRM.get(), FamiliarEntity.attributes().m_22265_());
         event.put((EntityType)ModEntities.ENTITY_FAMILIAR_WIXIE.get(), FamiliarEntity.attributes().m_22265_());
         event.put((EntityType)ModEntities.ENTITY_FAMILIAR_SYLPH.get(), FamiliarEntity.attributes().m_22265_());
         event.put((EntityType)ModEntities.ENTITY_FAMILIAR_DRYGMY.get(), FamiliarEntity.attributes().m_22265_());
         event.put((EntityType)ModEntities.FAMILIAR_AMETHYST_GOLEM.get(), FamiliarEntity.attributes().m_22265_());
         event.put((EntityType)ModEntities.ENTITY_CASCADING_WEALD.get(), WealdWalker.attributes().m_22265_());
         event.put((EntityType)ModEntities.ENTITY_BLAZING_WEALD.get(), WealdWalker.attributes().m_22265_());
         event.put((EntityType)ModEntities.ENTITY_FLOURISHING_WEALD.get(), WealdWalker.attributes().m_22265_());
         event.put((EntityType)ModEntities.ENTITY_VEXING_WEALD.get(), WealdWalker.attributes().m_22265_());
         event.put((EntityType)ModEntities.AMETHYST_GOLEM.get(), AmethystGolem.attributes().m_22265_());
         event.put((EntityType)ModEntities.SUMMON_SKELETON.get(), SummonSkeleton.m_32166_().m_22265_());
         event.put((EntityType)ModEntities.GIFT_STARBY.get(), GiftStarbuncle.attributes().m_22265_());
         event.put((EntityType)ModEntities.ANIMATED_BLOCK.get(), AnimBlockSummon.createAttributes().m_22265_());
         event.put((EntityType)ModEntities.ANIMATED_HEAD.get(), AnimBlockSummon.createAttributes().m_22265_());
         event.put((EntityType)ModEntities.LILY.get(), Lily.createAttributes().m_22265_());
      }
   }
}
