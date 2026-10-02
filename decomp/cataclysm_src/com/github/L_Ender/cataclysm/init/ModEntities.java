package com.github.L_Ender.cataclysm.init;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.Endermaptera_Entity;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.Koboleton_Entity;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.The_Watcher_Entity;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Amethyst_Crab_Entity;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ancient_Ancient_Remnant_Entity;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ender_Golem_Entity;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ender_Guardian_Entity;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ignis_Entity;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ignited_Revenant_Entity;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Nameless_Sorcerer_Entity;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Old_Netherite_Monstrosity_Entity;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Harbinger_Entity;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.Abyss_Blast_Entity;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.Abyss_Blast_Portal_Entity;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.Abyss_Mine_Entity;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.Abyss_Orb_Entity;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.Abyss_Portal_Entity;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.Dimensional_Rift_Entity;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.Portal_Abyss_Blast_Entity;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.The_Leviathan_Entity;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.The_Leviathan_Tongue_Entity;
import com.github.L_Ender.cataclysm.entity.Deepling.Coral_Golem_Entity;
import com.github.L_Ender.cataclysm.entity.Deepling.Deepling_Angler_Entity;
import com.github.L_Ender.cataclysm.entity.Deepling.Deepling_Brute_Entity;
import com.github.L_Ender.cataclysm.entity.Deepling.Deepling_Entity;
import com.github.L_Ender.cataclysm.entity.Deepling.Deepling_Priest_Entity;
import com.github.L_Ender.cataclysm.entity.Deepling.Deepling_Warlock_Entity;
import com.github.L_Ender.cataclysm.entity.Deepling.Lionfish_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Coralssus_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Ignited_Berserker_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Kobolediator_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.The_Prowler_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Wadjet_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Draugar.Aptrgangr_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Draugar.Draugr_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Draugar.Elite_Draugr_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Draugar.Royal_Draugr_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Ancient_Remnant.Ancient_Remnant_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Maledictus.Maledictus_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.NewNetherite_Monstrosity.Netherite_Monstrosity_Entity;
import com.github.L_Ender.cataclysm.entity.Pet.Modern_Remnant_Entity;
import com.github.L_Ender.cataclysm.entity.Pet.Netherite_Ministrosity_Entity;
import com.github.L_Ender.cataclysm.entity.Pet.The_Baby_Leviathan_Entity;
import com.github.L_Ender.cataclysm.entity.effect.Abyss_Mark_Entity;
import com.github.L_Ender.cataclysm.entity.effect.Boltstrike_Entity;
import com.github.L_Ender.cataclysm.entity.effect.Cm_Falling_Block_Entity;
import com.github.L_Ender.cataclysm.entity.effect.Flame_Strike_Entity;
import com.github.L_Ender.cataclysm.entity.effect.Sandstorm_Entity;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.effect.Void_Vortex_Entity;
import com.github.L_Ender.cataclysm.entity.effect.Wall_Watcher_Entity;
import com.github.L_Ender.cataclysm.entity.effect.Wither_Smoke_Effect_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Amethyst_Cluster_Projectile_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Ancient_Desert_Stele_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Ashen_Breath_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Axe_Blade_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Blazing_Bone_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Cursed_Sandstorm_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Death_Laser_Beam_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.EarthQuake_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Ender_Guardian_Bullet_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Eye_Of_Dungeon_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Flame_Jet_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Flare_Bomb_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Ignis_Abyss_Fireball_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Ignis_Fireball_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Laser_Beam_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Lava_Bomb_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Lionfish_Spike_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Mini_Abyss_Blast_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Phantom_Arrow_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Phantom_Halberd_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Poison_Dart_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Sandstorm_Projectile;
import com.github.L_Ender.cataclysm.entity.projectile.ThrownCoral_Bardiche_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.ThrownCoral_Spear_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Tidal_Hook_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Tidal_Tentacle_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Void_Howitzer_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Void_Rune_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Void_Scatter_Arrow_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Void_Shard_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Wither_Homing_Missile_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Wither_Howitzer_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Wither_Missile_Entity;
import com.google.common.base.Predicates;
import java.util.function.Predicate;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.entity.SpawnPlacements.Type;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(
   modid = "cataclysm",
   bus = Bus.MOD
)
public class ModEntities {
   public static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, "cataclysm");
   public static final RegistryObject<EntityType<Ender_Golem_Entity>> ENDER_GOLEM = ENTITY_TYPE.register(
      "ender_golem", () -> Builder.m_20704_(Ender_Golem_Entity::new, MobCategory.MONSTER).m_20699_(2.5F, 3.5F).m_20719_().m_20712_("cataclysm:ender_golem")
   );
   public static final RegistryObject<EntityType<Ender_Guardian_Entity>> ENDER_GUARDIAN = ENTITY_TYPE.register(
      "ender_guardian",
      () -> Builder.m_20704_(Ender_Guardian_Entity::new, MobCategory.MONSTER)
            .m_20699_(2.5F, 3.8F)
            .m_20719_()
            .m_20702_(10)
            .setShouldReceiveVelocityUpdates(true)
            .m_20712_("cataclysm:ender_guardian")
   );
   public static final RegistryObject<EntityType<Old_Netherite_Monstrosity_Entity>> OLD_NETHERITE_MONSTROSITY = ENTITY_TYPE.register(
      "old_netherite_monstrosity",
      () -> Builder.m_20704_(Old_Netherite_Monstrosity_Entity::new, MobCategory.MONSTER)
            .m_20699_(3.0F, 5.75F)
            .m_20719_()
            .m_20702_(4)
            .m_20712_("cataclysm:old_netherite_monstrosity")
   );
   public static final RegistryObject<EntityType<Netherite_Monstrosity_Entity>> NETHERITE_MONSTROSITY = ENTITY_TYPE.register(
      "netherite_monstrosity",
      () -> Builder.m_20704_(Netherite_Monstrosity_Entity::new, MobCategory.MONSTER)
            .m_20699_(3.0F, 5.75F)
            .m_20719_()
            .m_20702_(4)
            .m_20712_("cataclysm:netherite_monstrosity")
   );
   public static final RegistryObject<EntityType<Netherite_Ministrosity_Entity>> NETHERITE_MINISTROSITY = ENTITY_TYPE.register(
      "netherite_ministrosity",
      () -> Builder.m_20704_(Netherite_Ministrosity_Entity::new, MobCategory.CREATURE)
            .m_20699_(0.5F, 0.9F)
            .m_20702_(10)
            .m_20719_()
            .m_20712_("cataclysm:netherite_ministrosity")
   );
   public static final RegistryObject<EntityType<Lava_Bomb_Entity>> LAVA_BOMB = ENTITY_TYPE.register(
      "lava_bomb",
      () -> Builder.m_20704_(Lava_Bomb_Entity::new, MobCategory.MISC)
            .m_20699_(0.5F, 0.5F)
            .m_20719_()
            .setShouldReceiveVelocityUpdates(true)
            .setUpdateInterval(20)
            .m_20712_("cataclysm:lava_bomb")
   );
   public static final RegistryObject<EntityType<Flare_Bomb_Entity>> FLARE_BOMB = ENTITY_TYPE.register(
      "flare_bomb",
      () -> Builder.m_20704_(Flare_Bomb_Entity::new, MobCategory.MISC)
            .m_20699_(0.5F, 0.5F)
            .m_20719_()
            .setShouldReceiveVelocityUpdates(true)
            .setUpdateInterval(20)
            .m_20712_("cataclysm:flare_bomb")
   );
   public static final RegistryObject<EntityType<Flame_Jet_Entity>> FLAME_JET = ENTITY_TYPE.register(
      "flame_jet",
      () -> Builder.m_20704_(Flame_Jet_Entity::new, MobCategory.MISC).m_20699_(0.6F, 2.5F).m_20702_(6).m_20717_(2).m_20719_().m_20712_("cataclysm:flame_jet")
   );
   public static final RegistryObject<EntityType<Nameless_Sorcerer_Entity>> NAMELESS_SORCERER = ENTITY_TYPE.register(
      "nameless_sorcerer",
      () -> Builder.m_20704_(Nameless_Sorcerer_Entity::new, MobCategory.MONSTER).m_20699_(0.6F, 1.95F).m_20712_("cataclysm:nameless_sorcerer")
   );
   public static final RegistryObject<EntityType<Ignis_Entity>> IGNIS = ENTITY_TYPE.register(
      "ignis",
      () -> Builder.m_20704_(Ignis_Entity::new, MobCategory.MONSTER)
            .m_20699_(2.25F, 3.5F)
            .m_20719_()
            .m_20702_(10)
            .setShouldReceiveVelocityUpdates(true)
            .m_20712_("cataclysm:ignis")
   );
   public static final RegistryObject<EntityType<Ender_Guardian_Bullet_Entity>> ENDER_GUARDIAN_BULLET = ENTITY_TYPE.register(
      "ender_guardian_bullet",
      () -> Builder.m_20704_(Ender_Guardian_Bullet_Entity::new, MobCategory.MISC)
            .m_20699_(0.3125F, 0.3125F)
            .setUpdateInterval(1)
            .setTrackingRange(64)
            .setShouldReceiveVelocityUpdates(true)
            .m_20712_("cataclysm:ender_guardian_bullet")
   );
   public static final RegistryObject<EntityType<Void_Rune_Entity>> VOID_RUNE = ENTITY_TYPE.register(
      "void_rune",
      () -> Builder.m_20704_(Void_Rune_Entity::new, MobCategory.MISC).m_20699_(0.6F, 1.95F).m_20702_(6).m_20717_(2).m_20719_().m_20712_("cataclysm:void_rune")
   );
   public static final RegistryObject<EntityType<Abyss_Mine_Entity>> ABYSS_MINE = ENTITY_TYPE.register(
      "abyss_mine",
      () -> Builder.m_20704_(Abyss_Mine_Entity::new, MobCategory.MISC).m_20699_(1.0F, 1.0F).m_20702_(6).m_20717_(2).m_20719_().m_20712_("cataclysm:abyss_mine")
   );
   public static final RegistryObject<EntityType<Endermaptera_Entity>> ENDERMAPTERA = ENTITY_TYPE.register(
      "endermaptera", () -> Builder.m_20704_(Endermaptera_Entity::new, MobCategory.MONSTER).m_20699_(1.2F, 0.8F).m_20719_().m_20712_("cataclysm:endermaptera")
   );
   public static final RegistryObject<EntityType<Deepling_Entity>> DEEPLING = ENTITY_TYPE.register(
      "deepling", () -> Builder.m_20704_(Deepling_Entity::new, MobCategory.MONSTER).m_20699_(0.6F, 2.3F).m_20702_(8).m_20712_("cataclysm:deepling")
   );
   public static final RegistryObject<EntityType<Deepling_Brute_Entity>> DEEPLING_BRUTE = ENTITY_TYPE.register(
      "deepling_brute",
      () -> Builder.m_20704_(Deepling_Brute_Entity::new, MobCategory.MONSTER).m_20699_(0.7F, 2.6F).m_20702_(8).m_20712_("cataclysm:deepling_brute")
   );
   public static final RegistryObject<EntityType<Deepling_Angler_Entity>> DEEPLING_ANGLER = ENTITY_TYPE.register(
      "deepling_angler",
      () -> Builder.m_20704_(Deepling_Angler_Entity::new, MobCategory.MONSTER).m_20699_(0.65F, 2.45F).m_20702_(8).m_20712_("cataclysm:deepling_angler")
   );
   public static final RegistryObject<EntityType<Deepling_Priest_Entity>> DEEPLING_PRIEST = ENTITY_TYPE.register(
      "deepling_priest",
      () -> Builder.m_20704_(Deepling_Priest_Entity::new, MobCategory.MONSTER).m_20699_(0.6F, 2.3F).m_20702_(8).m_20712_("cataclysm:deepling_priest")
   );
   public static final RegistryObject<EntityType<Deepling_Warlock_Entity>> DEEPLING_WARLOCK = ENTITY_TYPE.register(
      "deepling_warlock",
      () -> Builder.m_20704_(Deepling_Warlock_Entity::new, MobCategory.MONSTER).m_20699_(0.6F, 2.3F).m_20702_(8).m_20712_("cataclysm:deepling_warlock")
   );
   public static final RegistryObject<EntityType<Abyss_Mark_Entity>> ABYSS_MARK = ENTITY_TYPE.register(
      "abyss_mark",
      () -> Builder.m_20704_(Abyss_Mark_Entity::new, MobCategory.MISC)
            .m_20699_(0.5F, 0.5F)
            .m_20719_()
            .setUpdateInterval(1)
            .setTrackingRange(20)
            .setShouldReceiveVelocityUpdates(true)
            .m_20712_("cataclysm:abyss_mark")
   );
   public static final RegistryObject<EntityType<Lionfish_Entity>> LIONFISH = ENTITY_TYPE.register(
      "lionfish", () -> Builder.m_20704_(Lionfish_Entity::new, MobCategory.MONSTER).m_20699_(0.6F, 0.55F).m_20702_(6).m_20712_("cataclysm:lionfish")
   );
   public static final RegistryObject<EntityType<Coral_Golem_Entity>> CORAL_GOLEM = ENTITY_TYPE.register(
      "coral_golem", () -> Builder.m_20704_(Coral_Golem_Entity::new, MobCategory.MONSTER).m_20699_(2.5F, 2.7F).m_20702_(10).m_20712_("cataclysm:coral_golem")
   );
   public static final RegistryObject<EntityType<Coralssus_Entity>> CORALSSUS = ENTITY_TYPE.register(
      "coralssus", () -> Builder.m_20704_(Coralssus_Entity::new, MobCategory.MONSTER).m_20699_(2.75F, 2.85F).m_20702_(8).m_20712_("cataclysm:coralssus")
   );
   public static final RegistryObject<EntityType<Ignited_Revenant_Entity>> IGNITED_REVENANT = ENTITY_TYPE.register(
      "ignited_revenant",
      () -> Builder.m_20704_(Ignited_Revenant_Entity::new, MobCategory.MONSTER).m_20699_(1.6F, 2.8F).m_20719_().m_20712_("cataclysm:ignited_revenant")
   );
   public static final RegistryObject<EntityType<Ignited_Berserker_Entity>> IGNITED_BERSERKER = ENTITY_TYPE.register(
      "ignited_berserker",
      () -> Builder.m_20704_(Ignited_Berserker_Entity::new, MobCategory.MONSTER).m_20699_(1.0F, 2.4F).m_20719_().m_20712_("cataclysm:ignited_berserker")
   );
   public static final RegistryObject<EntityType<The_Harbinger_Entity>> THE_HARBINGER = ENTITY_TYPE.register(
      "the_harbinger",
      () -> Builder.m_20704_(The_Harbinger_Entity::new, MobCategory.MONSTER)
            .m_20699_(1.6F, 3.75F)
            .m_20719_()
            .m_20714_(new Block[]{Blocks.f_50070_})
            .m_20702_(10)
            .m_20712_("cataclysm:the_harbinger")
   );
   public static final RegistryObject<EntityType<The_Watcher_Entity>> THE_WATCHER = ENTITY_TYPE.register(
      "the_watcher", () -> Builder.m_20704_(The_Watcher_Entity::new, MobCategory.MONSTER).m_20699_(0.85F, 0.85F).m_20719_().m_20712_("cataclysm:the_watcher")
   );
   public static final RegistryObject<EntityType<The_Prowler_Entity>> THE_PROWLER = ENTITY_TYPE.register(
      "the_prowler",
      () -> Builder.m_20704_(The_Prowler_Entity::new, MobCategory.MONSTER).m_20699_(2.5F, 2.75F).m_20719_().m_20702_(8).m_20712_("cataclysm:the_prowler")
   );
   public static final RegistryObject<EntityType<The_Leviathan_Entity>> THE_LEVIATHAN = ENTITY_TYPE.register(
      "the_leviathan",
      () -> Builder.m_20704_(The_Leviathan_Entity::new, MobCategory.MONSTER)
            .m_20699_(4.5F, 3.0F)
            .m_20719_()
            .m_20702_(8)
            .setShouldReceiveVelocityUpdates(true)
            .m_20712_("cataclysm:the_leviathan")
   );
   public static final RegistryObject<EntityType<The_Baby_Leviathan_Entity>> THE_BABY_LEVIATHAN = ENTITY_TYPE.register(
      "the_baby_leviathan",
      () -> Builder.m_20704_(The_Baby_Leviathan_Entity::new, MobCategory.CREATURE)
            .m_20699_(0.75F, 0.42F)
            .m_20702_(10)
            .m_20719_()
            .m_20712_("cataclysm:the_baby_leviathan")
   );
   public static final RegistryObject<EntityType<Void_Scatter_Arrow_Entity>> VOID_SCATTER_ARROW = ENTITY_TYPE.register(
      "void_scatter_arrow",
      () -> Builder.m_20704_(Void_Scatter_Arrow_Entity::new, MobCategory.MISC)
            .m_20699_(0.5F, 0.5F)
            .setCustomClientFactory(Void_Scatter_Arrow_Entity::new)
            .m_20717_(20)
            .m_20702_(4)
            .m_20712_("cataclysm:void_scatter_arrow")
   );
   public static final RegistryObject<EntityType<Poison_Dart_Entity>> POISON_DART = ENTITY_TYPE.register(
      "poison_dart",
      () -> Builder.m_20704_(Poison_Dart_Entity::new, MobCategory.MISC)
            .m_20699_(0.5F, 0.5F)
            .setCustomClientFactory(Poison_Dart_Entity::new)
            .m_20717_(20)
            .m_20702_(4)
            .m_20712_("cataclysm:void_scatter_arrow")
   );
   public static final RegistryObject<EntityType<Phantom_Arrow_Entity>> PHANTOM_ARROW = ENTITY_TYPE.register(
      "phantom_arrow",
      () -> Builder.m_20704_(Phantom_Arrow_Entity::new, MobCategory.MISC)
            .m_20699_(0.5F, 0.5F)
            .setCustomClientFactory(Phantom_Arrow_Entity::new)
            .setUpdateInterval(1)
            .setShouldReceiveVelocityUpdates(true)
            .m_20712_("cataclysm:phantom_arrow")
   );
   public static final RegistryObject<EntityType<Phantom_Halberd_Entity>> PHANTOM_HALBERD = ENTITY_TYPE.register(
      "phantom_halberd",
      () -> Builder.m_20704_(Phantom_Halberd_Entity::new, MobCategory.MISC)
            .m_20699_(0.6F, 1.95F)
            .m_20702_(6)
            .m_20717_(2)
            .m_20719_()
            .m_20712_("cataclysm:phantom_halberd")
   );
   public static final RegistryObject<EntityType<Void_Shard_Entity>> VOID_SHARD = ENTITY_TYPE.register(
      "void_shard",
      () -> Builder.m_20704_(Void_Shard_Entity::new, MobCategory.MISC)
            .m_20699_(0.5F, 0.5F)
            .setCustomClientFactory(Void_Shard_Entity::new)
            .m_20717_(20)
            .m_20702_(4)
            .m_20712_("cataclysm:void_shard")
   );
   public static final RegistryObject<EntityType<Blazing_Bone_Entity>> BLAZING_BONE = ENTITY_TYPE.register(
      "blazing_bone",
      () -> Builder.m_20704_(Blazing_Bone_Entity::new, MobCategory.MISC).m_20699_(0.5F, 0.5F).m_20717_(20).m_20702_(4).m_20712_("cataclysm:blazing_bone")
   );
   public static final RegistryObject<EntityType<Lionfish_Spike_Entity>> LIONFISH_SPIKE = ENTITY_TYPE.register(
      "lionfish_spike",
      () -> Builder.m_20704_(Lionfish_Spike_Entity::new, MobCategory.MISC).m_20699_(0.5F, 0.5F).m_20717_(20).m_20702_(4).m_20712_("cataclysm:lionfish_spike")
   );
   public static final RegistryObject<EntityType<ScreenShake_Entity>> SCREEN_SHAKE = ENTITY_TYPE.register(
      "screen_shake",
      () -> Builder.m_20704_(ScreenShake_Entity::new, MobCategory.MISC)
            .m_20698_()
            .m_20699_(0.0F, 0.0F)
            .setUpdateInterval(Integer.MAX_VALUE)
            .m_20712_("cataclysm:screen_shake")
   );
   public static final RegistryObject<EntityType<Cm_Falling_Block_Entity>> CM_FALLING_BLOCK = ENTITY_TYPE.register(
      "cm_falling_block",
      () -> Builder.m_20704_(Cm_Falling_Block_Entity::new, MobCategory.MISC)
            .m_20699_(0.98F, 0.98F)
            .m_20702_(10)
            .m_20717_(20)
            .m_20712_("cataclysm:cm_falling_block")
   );
   public static final RegistryObject<EntityType<Ignis_Fireball_Entity>> IGNIS_FIREBALL = ENTITY_TYPE.register(
      "ignis_fireball",
      () -> Builder.m_20704_(Ignis_Fireball_Entity::new, MobCategory.MISC)
            .m_20699_(0.6F, 0.6F)
            .setUpdateInterval(1)
            .setTrackingRange(20)
            .setShouldReceiveVelocityUpdates(true)
            .m_20712_("cataclysm:ignis_fireball")
   );
   public static final RegistryObject<EntityType<Ignis_Abyss_Fireball_Entity>> IGNIS_ABYSS_FIREBALL = ENTITY_TYPE.register(
      "ignis_abyss_fireball",
      () -> Builder.m_20704_(Ignis_Abyss_Fireball_Entity::new, MobCategory.MISC)
            .m_20699_(0.6F, 0.6F)
            .setUpdateInterval(1)
            .setTrackingRange(20)
            .setShouldReceiveVelocityUpdates(true)
            .m_20712_("cataclysm:ignis_abyss_fireball")
   );
   public static final RegistryObject<EntityType<Wither_Smoke_Effect_Entity>> WITHER_SMOKE_EFFECT = ENTITY_TYPE.register(
      "wither_smoke_effect",
      () -> Builder.m_20704_(Wither_Smoke_Effect_Entity::new, MobCategory.MISC)
            .m_20699_(6.0F, 0.5F)
            .m_20719_()
            .m_20702_(10)
            .m_20717_(Integer.MAX_VALUE)
            .m_20712_("cataclysm:wither_smoke_effect")
   );
   public static final RegistryObject<EntityType<Flame_Strike_Entity>> FLAME_STRIKE = ENTITY_TYPE.register(
      "flame_strike",
      () -> Builder.m_20704_(Flame_Strike_Entity::new, MobCategory.MISC)
            .m_20699_(6.0F, 0.5F)
            .m_20719_()
            .m_20702_(10)
            .m_20717_(Integer.MAX_VALUE)
            .m_20712_("cataclysm:flame_strike")
   );
   public static final RegistryObject<EntityType<Boltstrike_Entity>> BOLT_STRIKE = ENTITY_TYPE.register(
      "bolt_strike",
      () -> Builder.m_20704_(Boltstrike_Entity::new, MobCategory.MISC)
            .m_20699_(0.0F, 0.0F)
            .m_20702_(16)
            .m_20717_(Integer.MAX_VALUE)
            .m_20712_("cataclysm:bolt_strike")
   );
   public static final RegistryObject<EntityType<Ashen_Breath_Entity>> ASHEN_BREATH = ENTITY_TYPE.register(
      "ashen_breath",
      () -> Builder.m_20704_(Ashen_Breath_Entity::new, MobCategory.MISC)
            .m_20699_(0.0F, 0.0F)
            .m_20719_()
            .setUpdateInterval(1)
            .m_20712_("cataclysm:ashen_breath")
   );
   public static final RegistryObject<EntityType<Wall_Watcher_Entity>> WALL_WATCHER = ENTITY_TYPE.register(
      "wall_watcher",
      () -> Builder.m_20704_(Wall_Watcher_Entity::new, MobCategory.MISC).m_20699_(0.0F, 0.0F).m_20698_().m_20719_().m_20712_("cataclysm:wall_watcher")
   );
   public static final RegistryObject<EntityType<Death_Laser_Beam_Entity>> DEATH_LASER_BEAM = ENTITY_TYPE.register(
      "death_laser_beam",
      () -> Builder.m_20704_(Death_Laser_Beam_Entity::new, MobCategory.MISC).m_20699_(0.1F, 0.1F).m_20719_().m_20712_("cataclysm:death_laser_beam")
   );
   public static final RegistryObject<EntityType<Abyss_Blast_Entity>> ABYSS_BLAST = ENTITY_TYPE.register(
      "abyss_blast", () -> Builder.m_20704_(Abyss_Blast_Entity::new, MobCategory.MISC).m_20699_(0.1F, 0.1F).m_20719_().m_20712_("cataclysm:abyss_blast")
   );
   public static final RegistryObject<EntityType<Mini_Abyss_Blast_Entity>> MINI_ABYSS_BLAST = ENTITY_TYPE.register(
      "mini_abyss_blast",
      () -> Builder.m_20704_(Mini_Abyss_Blast_Entity::new, MobCategory.MISC).m_20699_(0.1F, 0.1F).m_20719_().m_20712_("cataclysm:mini_abyss_blast")
   );
   public static final RegistryObject<EntityType<Portal_Abyss_Blast_Entity>> PORTAL_ABYSS_BLAST = ENTITY_TYPE.register(
      "portal_abyss_blast",
      () -> Builder.m_20704_(Portal_Abyss_Blast_Entity::new, MobCategory.MISC).m_20699_(0.1F, 0.1F).m_20719_().m_20712_("cataclysm:portal_abyss_blast")
   );
   public static final RegistryObject<EntityType<Laser_Beam_Entity>> LASER_BEAM = ENTITY_TYPE.register(
      "laser_beam",
      () -> Builder.m_20704_(Laser_Beam_Entity::new, MobCategory.MISC)
            .m_20699_(0.3125F, 0.3125F)
            .m_20719_()
            .m_20702_(4)
            .m_20717_(10)
            .m_20712_("cataclysm:laser_beam")
   );
   public static final RegistryObject<EntityType<Wither_Missile_Entity>> WITHER_MISSILE = ENTITY_TYPE.register(
      "wither_missile",
      () -> Builder.m_20704_(Wither_Missile_Entity::new, MobCategory.MISC)
            .m_20699_(0.5F, 0.5F)
            .m_20702_(4)
            .m_20717_(10)
            .setShouldReceiveVelocityUpdates(true)
            .m_20712_("cataclysm:wither_missile")
   );
   public static final RegistryObject<EntityType<Wither_Homing_Missile_Entity>> WITHER_HOMING_MISSILE = ENTITY_TYPE.register(
      "wither_homing_missile",
      () -> Builder.m_20704_(Wither_Homing_Missile_Entity::new, MobCategory.MISC)
            .m_20699_(0.25F, 0.25F)
            .setUpdateInterval(1)
            .setTrackingRange(20)
            .setShouldReceiveVelocityUpdates(true)
            .m_20712_("cataclysm:wither_homing_missile")
   );
   public static final RegistryObject<EntityType<Wither_Howitzer_Entity>> WITHER_HOWITZER = ENTITY_TYPE.register(
      "wither_howitzer",
      () -> Builder.m_20704_(Wither_Howitzer_Entity::new, MobCategory.MISC)
            .m_20699_(0.5F, 0.5F)
            .m_20719_()
            .setShouldReceiveVelocityUpdates(true)
            .setUpdateInterval(20)
            .m_20712_("cataclysm:wither_howitzer")
   );
   public static final RegistryObject<EntityType<Abyss_Orb_Entity>> ABYSS_ORB = ENTITY_TYPE.register(
      "abyss_orb",
      () -> Builder.m_20704_(Abyss_Orb_Entity::new, MobCategory.MISC)
            .m_20699_(0.5F, 0.5F)
            .m_20719_()
            .setUpdateInterval(1)
            .setTrackingRange(20)
            .setShouldReceiveVelocityUpdates(true)
            .m_20712_("cataclysm:abyss_orb")
   );
   public static final RegistryObject<EntityType<Void_Howitzer_Entity>> VOID_HOWITZER = ENTITY_TYPE.register(
      "void_howitzer",
      () -> Builder.m_20704_(Void_Howitzer_Entity::new, MobCategory.MISC)
            .m_20699_(0.5F, 0.5F)
            .m_20719_()
            .setShouldReceiveVelocityUpdates(true)
            .setUpdateInterval(20)
            .m_20712_("cataclysm:void_howitzer")
   );
   public static final RegistryObject<EntityType<Eye_Of_Dungeon_Entity>> EYE_OF_DUNGEON = ENTITY_TYPE.register(
      "eye_of_dungeon",
      () -> Builder.m_20704_(Eye_Of_Dungeon_Entity::new, MobCategory.MISC).m_20699_(0.25F, 0.25F).m_20702_(4).m_20717_(4).m_20712_("cataclysm:eye_of_dungeon")
   );
   public static final RegistryObject<EntityType<Void_Vortex_Entity>> VOID_VORTEX = ENTITY_TYPE.register(
      "void_vortex",
      () -> Builder.m_20704_(Void_Vortex_Entity::new, MobCategory.MISC)
            .m_20699_(2.5F, 0.5F)
            .m_20719_()
            .m_20702_(10)
            .m_20717_(Integer.MAX_VALUE)
            .m_20712_("cataclysm:void_vortex")
   );
   public static final RegistryObject<EntityType<The_Leviathan_Tongue_Entity>> THE_LEVIATHAN_TONGUE = ENTITY_TYPE.register(
      "the_leviathan_tongue",
      () -> Builder.m_20704_(The_Leviathan_Tongue_Entity::new, MobCategory.MISC).m_20699_(0.5F, 0.5F).m_20712_("cataclysm:the_leviathan_tongue")
   );
   public static final RegistryObject<EntityType<Tidal_Tentacle_Entity>> TIDAL_TENTACLE = ENTITY_TYPE.register(
      "tidal_tentacle", () -> Builder.m_20704_(Tidal_Tentacle_Entity::new, MobCategory.MISC).m_20699_(0.1F, 0.1F).m_20712_("cataclysm:tidal_tentacle")
   );
   public static final RegistryObject<EntityType<Tidal_Hook_Entity>> TIDAL_HOOK = ENTITY_TYPE.register(
      "tidal_hook", () -> Builder.m_20704_(Tidal_Hook_Entity::new, MobCategory.MISC).m_20699_(0.5F, 0.5F).m_20712_("cataclysm:tidal_hook")
   );
   public static final RegistryObject<EntityType<Abyss_Portal_Entity>> ABYSS_PORTAL = ENTITY_TYPE.register(
      "abyss_portal",
      () -> Builder.m_20704_(Abyss_Portal_Entity::new, MobCategory.MISC)
            .m_20719_()
            .m_20699_(3.0F, 0.15F)
            .setCustomClientFactory(Abyss_Portal_Entity::new)
            .m_20712_("cataclysm:abyss_portal")
   );
   public static final RegistryObject<EntityType<Abyss_Blast_Portal_Entity>> ABYSS_BLAST_PORTAL = ENTITY_TYPE.register(
      "abyss_blast_portal",
      () -> Builder.m_20704_(Abyss_Blast_Portal_Entity::new, MobCategory.MISC)
            .m_20699_(4.0F, 0.5F)
            .m_20719_()
            .m_20702_(4)
            .m_20717_(10)
            .m_20712_("cataclysm:abyss_blast_portal")
   );
   public static final RegistryObject<EntityType<ThrownCoral_Spear_Entity>> CORAL_SPEAR = ENTITY_TYPE.register(
      "coral_spear",
      () -> Builder.m_20704_(ThrownCoral_Spear_Entity::new, MobCategory.MISC).m_20699_(0.5F, 0.5F).m_20702_(4).m_20717_(20).m_20712_("cataclysm:coral_spear")
   );
   public static final RegistryObject<EntityType<ThrownCoral_Bardiche_Entity>> CORAL_BARDICHE = ENTITY_TYPE.register(
      "coral_bardiche",
      () -> Builder.m_20704_(ThrownCoral_Bardiche_Entity::new, MobCategory.MISC)
            .m_20699_(0.5F, 0.5F)
            .m_20702_(4)
            .m_20717_(20)
            .m_20712_("cataclysm:coral_bardiche")
   );
   public static final RegistryObject<EntityType<Dimensional_Rift_Entity>> DIMENSIONAL_RIFT = ENTITY_TYPE.register(
      "dimensional_rift",
      () -> Builder.m_20704_(Dimensional_Rift_Entity::new, MobCategory.MISC)
            .m_20699_(2.0F, 2.0F)
            .m_20719_()
            .m_20702_(10)
            .m_20717_(Integer.MAX_VALUE)
            .m_20712_("cataclysm:dimensional_rift")
   );
   public static final RegistryObject<EntityType<Amethyst_Crab_Entity>> AMETHYST_CRAB = ENTITY_TYPE.register(
      "amethyst_crab",
      () -> Builder.m_20704_(Amethyst_Crab_Entity::new, MobCategory.MONSTER).m_20699_(2.5F, 2.6F).m_20719_().m_20712_("cataclysm:amethyst_crab")
   );
   public static final RegistryObject<EntityType<EarthQuake_Entity>> EARTHQUAKE = ENTITY_TYPE.register(
      "earthquake",
      () -> Builder.m_20704_(EarthQuake_Entity::new, MobCategory.MISC)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(20)
            .setUpdateInterval(1)
            .m_20699_(0.5F, 0.5F)
            .m_20712_("cataclysm:earthquake")
   );
   public static final RegistryObject<EntityType<Amethyst_Cluster_Projectile_Entity>> AMETHYST_CLUSTER_PROJECTILE = ENTITY_TYPE.register(
      "amethyst_cluster_projectile",
      () -> Builder.m_20704_(Amethyst_Cluster_Projectile_Entity::new, MobCategory.MISC)
            .m_20699_(0.5F, 0.0F)
            .m_20719_()
            .setShouldReceiveVelocityUpdates(true)
            .setUpdateInterval(20)
            .m_20712_("cataclysm:amethyst_cluster_projectile")
   );
   public static final RegistryObject<EntityType<Ancient_Ancient_Remnant_Entity>> ANCIENT_ANCIENT_REMNANT = ENTITY_TYPE.register(
      "ancient_ancient_remnant",
      () -> Builder.m_20704_(Ancient_Ancient_Remnant_Entity::new, MobCategory.MONSTER)
            .m_20699_(3.8F, 5.0F)
            .m_20719_()
            .m_20702_(8)
            .setShouldReceiveVelocityUpdates(true)
            .m_20712_("cataclysm:ancient_ancient_remnant")
   );
   public static final RegistryObject<EntityType<Ancient_Remnant_Entity>> ANCIENT_REMNANT = ENTITY_TYPE.register(
      "ancient_remnant",
      () -> Builder.m_20704_(Ancient_Remnant_Entity::new, MobCategory.MONSTER)
            .m_20699_(4.35F, 5.0F)
            .m_20719_()
            .m_20702_(8)
            .setShouldReceiveVelocityUpdates(true)
            .m_20712_("cataclysm:ancient_remnant")
   );
   public static final RegistryObject<EntityType<Modern_Remnant_Entity>> MODERN_REMNANT = ENTITY_TYPE.register(
      "modern_remnant",
      () -> Builder.m_20704_(Modern_Remnant_Entity::new, MobCategory.CREATURE)
            .m_20699_(0.75F, 0.42F)
            .m_20702_(10)
            .m_20719_()
            .m_20712_("cataclysm:modern_remnant")
   );
   public static final RegistryObject<EntityType<Koboleton_Entity>> KOBOLETON = ENTITY_TYPE.register(
      "koboleton", () -> Builder.m_20704_(Koboleton_Entity::new, MobCategory.MONSTER).m_20699_(0.85F, 1.6F).m_20702_(8).m_20712_("cataclysm:koboleton")
   );
   public static final RegistryObject<EntityType<Kobolediator_Entity>> KOBOLEDIATOR = ENTITY_TYPE.register(
      "kobolediator", () -> Builder.m_20704_(Kobolediator_Entity::new, MobCategory.MONSTER).m_20699_(2.4F, 4.4F).m_20702_(8).m_20712_("cataclysm:kobolediator")
   );
   public static final RegistryObject<EntityType<Wadjet_Entity>> WADJET = ENTITY_TYPE.register(
      "wadjet", () -> Builder.m_20704_(Wadjet_Entity::new, MobCategory.MONSTER).m_20699_(0.85F, 3.4F).m_20702_(8).m_20712_("cataclysm:wadjet")
   );
   public static final RegistryObject<EntityType<Sandstorm_Entity>> SANDSTORM = ENTITY_TYPE.register(
      "sandstorm",
      () -> Builder.m_20704_(Sandstorm_Entity::new, MobCategory.MISC)
            .m_20699_(2.5F, 4.5F)
            .m_20719_()
            .m_20702_(10)
            .m_20717_(Integer.MAX_VALUE)
            .m_20712_("cataclysm:sandstorm")
   );
   public static final RegistryObject<EntityType<Sandstorm_Projectile>> SANDSTORM_PROJECTILE = ENTITY_TYPE.register(
      "sandstorm_projectile",
      () -> Builder.m_20704_(Sandstorm_Projectile::new, MobCategory.MISC)
            .m_20699_(0.5F, 1.0F)
            .m_20702_(4)
            .m_20717_(10)
            .setShouldReceiveVelocityUpdates(true)
            .m_20712_("cataclysm:sandstorm_projectile")
   );
   public static final RegistryObject<EntityType<Cursed_Sandstorm_Entity>> CURSED_SANDSTORM = ENTITY_TYPE.register(
      "cursed_sandstorm",
      () -> Builder.m_20704_(Cursed_Sandstorm_Entity::new, MobCategory.MISC)
            .m_20699_(0.5F, 1.0F)
            .setShouldReceiveVelocityUpdates(true)
            .setUpdateInterval(1)
            .setTrackingRange(20)
            .setShouldReceiveVelocityUpdates(true)
            .m_20712_("cataclysm:cursed_sandstorm")
   );
   public static final RegistryObject<EntityType<Ancient_Desert_Stele_Entity>> ANCIENT_DESERT_STELE = ENTITY_TYPE.register(
      "ancient_desert_stele",
      () -> Builder.m_20704_(Ancient_Desert_Stele_Entity::new, MobCategory.MISC)
            .m_20699_(0.8F, 1.375F)
            .m_20702_(6)
            .m_20717_(2)
            .setShouldReceiveVelocityUpdates(true)
            .m_20719_()
            .m_20712_("cataclysm:ancient_desert_stele")
   );
   public static final RegistryObject<EntityType<Maledictus_Entity>> MALEDICTUS = ENTITY_TYPE.register(
      "maledictus",
      () -> Builder.m_20704_(Maledictus_Entity::new, MobCategory.MONSTER)
            .m_20699_(1.5F, 3.0F)
            .m_20719_()
            .m_20702_(10)
            .setShouldReceiveVelocityUpdates(true)
            .m_20712_("cataclysm:maledictus")
   );
   public static final RegistryObject<EntityType<Draugr_Entity>> DRAUGR = ENTITY_TYPE.register(
      "draugr", () -> Builder.m_20704_(Draugr_Entity::new, MobCategory.MONSTER).m_20699_(0.6F, 1.95F).m_20702_(10).m_20712_("cataclysm:draugr")
   );
   public static final RegistryObject<EntityType<Royal_Draugr_Entity>> ROYAL_DRAUGR = ENTITY_TYPE.register(
      "royal_draugr",
      () -> Builder.m_20704_(Royal_Draugr_Entity::new, MobCategory.MONSTER).m_20699_(0.6F, 1.95F).m_20702_(10).m_20712_("cataclysm:royal_draugr")
   );
   public static final RegistryObject<EntityType<Elite_Draugr_Entity>> ELITE_DRAUGR = ENTITY_TYPE.register(
      "elite_draugr",
      () -> Builder.m_20704_(Elite_Draugr_Entity::new, MobCategory.MONSTER).m_20699_(0.8F, 2.6F).m_20702_(10).m_20712_("cataclysm:elite_draugr")
   );
   public static final RegistryObject<EntityType<Aptrgangr_Entity>> APTRGANGR = ENTITY_TYPE.register(
      "aptrgangr", () -> Builder.m_20704_(Aptrgangr_Entity::new, MobCategory.MONSTER).m_20699_(2.4F, 4.0F).m_20702_(8).m_20712_("cataclysm:aptrgangr")
   );
   public static final RegistryObject<EntityType<Axe_Blade_Entity>> AXE_BLADE = ENTITY_TYPE.register(
      "axe_blade",
      () -> Builder.m_20704_(Axe_Blade_Entity::new, MobCategory.MISC)
            .m_20699_(1.2F, 2.5F)
            .m_20702_(4)
            .m_20717_(10)
            .setShouldReceiveVelocityUpdates(true)
            .m_20712_("cataclysm:axe_blade")
   );

   public static Predicate<LivingEntity> buildPredicateFromTag(TagKey<EntityType<?>> entityTag) {
      return entityTag == null ? Predicates.alwaysFalse() : e -> e.m_6084_() && e.m_6095_().m_204039_(entityTag);
   }

   public static boolean rollSpawn(int rolls, RandomSource random, MobSpawnType reason) {
      return reason == MobSpawnType.SPAWNER ? true : rolls <= 0 || random.m_188503_(rolls) == 0;
   }

   @SubscribeEvent
   public static void initializeAttributes(EntityAttributeCreationEvent event) {
      SpawnPlacements.m_21754_((EntityType)ENDERMAPTERA.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Endermaptera_Entity::canSpawn);
      SpawnPlacements.m_21754_((EntityType)KOBOLETON.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Koboleton_Entity::checkKoboletonSpawnRules);
      SpawnPlacements.m_21754_((EntityType)DEEPLING_ANGLER.get(), Type.IN_WATER, Types.MOTION_BLOCKING_NO_LEAVES, Deepling_Angler_Entity::candeeplingSpawn);
      SpawnPlacements.m_21754_((EntityType)DEEPLING.get(), Type.IN_WATER, Types.MOTION_BLOCKING_NO_LEAVES, Deepling_Entity::candeeplingSpawn);
      SpawnPlacements.m_21754_((EntityType)DEEPLING_BRUTE.get(), Type.IN_WATER, Types.MOTION_BLOCKING_NO_LEAVES, Deepling_Brute_Entity::candeeplingSpawn);
      SpawnPlacements.m_21754_((EntityType)DEEPLING_WARLOCK.get(), Type.IN_WATER, Types.MOTION_BLOCKING_NO_LEAVES, Deepling_Warlock_Entity::candeeplingSpawn);
      SpawnPlacements.m_21754_((EntityType)DEEPLING_PRIEST.get(), Type.IN_WATER, Types.MOTION_BLOCKING_NO_LEAVES, Deepling_Priest_Entity::candeeplingSpawn);
      SpawnPlacements.m_21754_((EntityType)CORAL_GOLEM.get(), Type.IN_WATER, Types.MOTION_BLOCKING_NO_LEAVES, Coral_Golem_Entity::cangolemSpawn);
      SpawnPlacements.m_21754_((EntityType)AMETHYST_CRAB.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Amethyst_Crab_Entity::canCrabSpawnSpawnRules);
      SpawnPlacements.m_21754_((EntityType)IGNITED_BERSERKER.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_219019_);
      event.put((EntityType)ENDER_GOLEM.get(), Ender_Golem_Entity.ender_golem().m_22265_());
      event.put((EntityType)NETHERITE_MINISTROSITY.get(), Netherite_Ministrosity_Entity.ministrosity().m_22265_());
      event.put((EntityType)NETHERITE_MONSTROSITY.get(), Netherite_Monstrosity_Entity.netherite_monstrosity().m_22265_());
      event.put((EntityType)OLD_NETHERITE_MONSTROSITY.get(), Old_Netherite_Monstrosity_Entity.netherite_monstrosity().m_22265_());
      event.put((EntityType)NAMELESS_SORCERER.get(), Nameless_Sorcerer_Entity.nameless_sorcerer().m_22265_());
      event.put((EntityType)IGNIS.get(), Ignis_Entity.ignis().m_22265_());
      event.put((EntityType)ENDER_GUARDIAN.get(), Ender_Guardian_Entity.ender_guardian().m_22265_());
      event.put((EntityType)ENDERMAPTERA.get(), Endermaptera_Entity.endermaptera().m_22265_());
      event.put((EntityType)IGNITED_REVENANT.get(), Ignited_Revenant_Entity.ignited_revenant().m_22265_());
      event.put((EntityType)IGNITED_BERSERKER.get(), Ignited_Berserker_Entity.ignited_berserker().m_22265_());
      event.put((EntityType)THE_HARBINGER.get(), The_Harbinger_Entity.harbinger().m_22265_());
      event.put((EntityType)THE_LEVIATHAN.get(), The_Leviathan_Entity.leviathan().m_22265_());
      event.put((EntityType)THE_BABY_LEVIATHAN.get(), The_Baby_Leviathan_Entity.babyleviathan().m_22265_());
      event.put((EntityType)DEEPLING.get(), Deepling_Entity.deepling().m_22265_());
      event.put((EntityType)DEEPLING_BRUTE.get(), Deepling_Brute_Entity.deeplingbrute().m_22265_());
      event.put((EntityType)DEEPLING_ANGLER.get(), Deepling_Angler_Entity.deepling().m_22265_());
      event.put((EntityType)DEEPLING_PRIEST.get(), Deepling_Priest_Entity.deeplingpriest().m_22265_());
      event.put((EntityType)DEEPLING_WARLOCK.get(), Deepling_Warlock_Entity.deeplingwarlock().m_22265_());
      event.put((EntityType)CORAL_GOLEM.get(), Coral_Golem_Entity.coralgolem().m_22265_());
      event.put((EntityType)CORALSSUS.get(), Coralssus_Entity.coralssus().m_22265_());
      event.put((EntityType)LIONFISH.get(), Lionfish_Entity.lionfish().m_22265_());
      event.put((EntityType)AMETHYST_CRAB.get(), Amethyst_Crab_Entity.amethyst_crab().m_22265_());
      event.put((EntityType)ANCIENT_ANCIENT_REMNANT.get(), Ancient_Ancient_Remnant_Entity.ancient_remnant().m_22265_());
      event.put((EntityType)MODERN_REMNANT.get(), Modern_Remnant_Entity.modernremnant().m_22265_());
      event.put((EntityType)KOBOLETON.get(), Koboleton_Entity.koboleton().m_22265_());
      event.put((EntityType)THE_WATCHER.get(), The_Watcher_Entity.the_watcher().m_22265_());
      event.put((EntityType)THE_PROWLER.get(), The_Prowler_Entity.the_prowler().m_22265_());
      event.put((EntityType)KOBOLEDIATOR.get(), Kobolediator_Entity.kobolediator().m_22265_());
      event.put((EntityType)APTRGANGR.get(), Aptrgangr_Entity.aptrgangr().m_22265_());
      event.put((EntityType)WADJET.get(), Wadjet_Entity.wadjet().m_22265_());
      event.put((EntityType)MALEDICTUS.get(), Maledictus_Entity.maledictus().m_22265_());
      event.put((EntityType)ANCIENT_REMNANT.get(), Ancient_Remnant_Entity.maledictus().m_22265_());
      event.put((EntityType)DRAUGR.get(), Draugr_Entity.draugr().m_22265_());
      event.put((EntityType)ROYAL_DRAUGR.get(), Royal_Draugr_Entity.royal_draugr().m_22265_());
      event.put((EntityType)ELITE_DRAUGR.get(), Elite_Draugr_Entity.elite_draugr().m_22265_());
   }
}
