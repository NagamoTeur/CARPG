package com.bobmowzie.mowziesmobs.server.entity;

import com.bobmowzie.mowziesmobs.server.entity.effects.EntityAxeAttack;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityBlockSwapper;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityCameraShake;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityFallingBlock;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityIceBall;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityIceBreath;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityPoisonBall;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySolarBeam;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySunstrike;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySuperNova;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityBoulderPlatform;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityBoulderProjectile;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityPillar;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityPillarPiece;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityRockSling;
import com.bobmowzie.mowziesmobs.server.entity.foliaath.EntityBabyFoliaath;
import com.bobmowzie.mowziesmobs.server.entity.foliaath.EntityFoliaath;
import com.bobmowzie.mowziesmobs.server.entity.frostmaw.EntityFrostmaw;
import com.bobmowzie.mowziesmobs.server.entity.frostmaw.EntityFrozenController;
import com.bobmowzie.mowziesmobs.server.entity.grottol.EntityGrottol;
import com.bobmowzie.mowziesmobs.server.entity.lantern.EntityLantern;
import com.bobmowzie.mowziesmobs.server.entity.naga.EntityNaga;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthana;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaCrane;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaCraneToPlayer;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaFollowerToPlayer;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaFollowerToRaptor;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaMinion;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaRaptor;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthi;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.MaskType;
import com.bobmowzie.mowziesmobs.server.entity.wroughtnaut.EntityWroughtnaut;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(
   modid = "mowziesmobs",
   bus = Bus.MOD
)
public class EntityHandler {
   public static final DeferredRegister<EntityType<?>> REG = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, "mowziesmobs");
   public static final RegistryObject<EntityType<EntityFoliaath>> FOLIAATH = REG.register(
      "foliaath",
      () -> Builder.m_20704_(EntityFoliaath::new, MobCategory.MONSTER)
            .m_20699_(0.5F, 2.5F)
            .m_20712_(new ResourceLocation("mowziesmobs", "foliaath").toString())
   );
   public static final RegistryObject<EntityType<EntityBabyFoliaath>> BABY_FOLIAATH = REG.register(
      "baby_foliaath",
      () -> Builder.m_20704_(EntityBabyFoliaath::new, MobCategory.MONSTER)
            .m_20699_(0.4F, 0.4F)
            .m_20712_(new ResourceLocation("mowziesmobs", "baby_foliaath").toString())
   );
   public static final RegistryObject<EntityType<EntityWroughtnaut>> WROUGHTNAUT = REG.register(
      "ferrous_wroughtnaut",
      () -> Builder.m_20704_(EntityWroughtnaut::new, MobCategory.MONSTER)
            .m_20699_(2.5F, 3.5F)
            .setUpdateInterval(1)
            .m_20712_(new ResourceLocation("mowziesmobs", "ferrous_wroughtnaut").toString())
   );
   public static final RegistryObject<EntityType<EntityUmvuthanaFollowerToRaptor>> UMVUTHANA_FOLLOWER_TO_RAPTOR = REG.register(
      "umvuthana_follower_raptor",
      () -> umvuthanaFollowerToRaptorBuilder()
            .m_20699_(MaskType.FEAR.entityWidth, MaskType.FEAR.entityHeight)
            .setUpdateInterval(1)
            .m_20712_(new ResourceLocation("mowziesmobs", "umvuthana_follower_raptor").toString())
   );
   public static final RegistryObject<EntityType<EntityUmvuthanaFollowerToPlayer>> UMVUTHANA_FOLLOWER_TO_PLAYER = REG.register(
      "umvuthana_follower_player",
      () -> umvuthanaFollowerToPlayerBuilder()
            .m_20699_(MaskType.FEAR.entityWidth, MaskType.FEAR.entityHeight)
            .setUpdateInterval(1)
            .m_20712_(new ResourceLocation("mowziesmobs", "umvuthana_follower_player").toString())
   );
   public static final RegistryObject<EntityType<EntityUmvuthanaCraneToPlayer>> UMVUTHANA_CRANE_TO_PLAYER = REG.register(
      "umvuthana_crane_player",
      () -> umvuthanaCraneToPlayerBuilder()
            .m_20699_(MaskType.FAITH.entityWidth, MaskType.FAITH.entityHeight)
            .setUpdateInterval(1)
            .m_20712_(new ResourceLocation("mowziesmobs", "umvuthana_crane_player").toString())
   );
   public static final RegistryObject<EntityType<EntityUmvuthanaMinion>> UMVUTHANA_MINION = REG.register(
      "umvuthana",
      () -> Builder.m_20704_(EntityUmvuthanaMinion::new, MobCategory.MONSTER)
            .m_20699_(MaskType.FEAR.entityWidth, MaskType.FEAR.entityHeight)
            .setUpdateInterval(1)
            .m_20712_(new ResourceLocation("mowziesmobs", "umvuthana").toString())
   );
   public static final RegistryObject<EntityType<EntityUmvuthanaRaptor>> UMVUTHANA_RAPTOR = REG.register(
      "umvuthana_raptor",
      () -> Builder.m_20704_(EntityUmvuthanaRaptor::new, MobCategory.MONSTER)
            .m_20699_(MaskType.FURY.entityWidth, MaskType.FURY.entityHeight)
            .setUpdateInterval(1)
            .m_20712_(new ResourceLocation("mowziesmobs", "umvuthana_raptor").toString())
   );
   public static final RegistryObject<EntityType<EntityUmvuthanaCrane>> UMVUTHANA_CRANE = REG.register(
      "umvuthana_crane",
      () -> Builder.m_20704_(EntityUmvuthanaCrane::new, MobCategory.MONSTER)
            .m_20699_(MaskType.FEAR.entityWidth, MaskType.FEAR.entityHeight)
            .setUpdateInterval(1)
            .m_20712_(new ResourceLocation("mowziesmobs", "umvuthana_crane").toString())
   );
   public static final RegistryObject<EntityType<EntityUmvuthi>> UMVUTHI = REG.register(
      "umvuthi",
      () -> Builder.m_20704_(EntityUmvuthi::new, MobCategory.MONSTER)
            .m_20699_(1.5F, 3.2F)
            .setUpdateInterval(1)
            .m_20712_(new ResourceLocation("mowziesmobs", "umvuthi").toString())
   );
   public static final RegistryObject<EntityType<EntityFrostmaw>> FROSTMAW = REG.register(
      "frostmaw",
      () -> Builder.m_20704_(EntityFrostmaw::new, MobCategory.MONSTER)
            .m_20699_(4.0F, 4.0F)
            .setUpdateInterval(1)
            .m_20712_(new ResourceLocation("mowziesmobs", "frostmaw").toString())
   );
   public static final RegistryObject<EntityType<EntityGrottol>> GROTTOL = REG.register(
      "grottol",
      () -> Builder.m_20704_(EntityGrottol::new, MobCategory.MONSTER)
            .m_20699_(0.9F, 1.2F)
            .setUpdateInterval(1)
            .m_20712_(new ResourceLocation("mowziesmobs", "grottol").toString())
   );
   public static final RegistryObject<EntityType<EntityLantern>> LANTERN = REG.register(
      "lantern",
      () -> Builder.m_20704_(EntityLantern::new, MobCategory.AMBIENT)
            .m_20699_(1.0F, 1.0F)
            .setUpdateInterval(1)
            .m_20712_(new ResourceLocation("mowziesmobs", "lantern").toString())
   );
   public static final RegistryObject<EntityType<EntityNaga>> NAGA = REG.register(
      "naga",
      () -> Builder.m_20704_(EntityNaga::new, MobCategory.MONSTER)
            .m_20699_(3.0F, 1.0F)
            .setTrackingRange(128)
            .setUpdateInterval(1)
            .m_20712_(new ResourceLocation("mowziesmobs", "naga").toString())
   );
   public static final RegistryObject<EntityType<EntitySunstrike>> SUNSTRIKE = REG.register(
      "sunstrike", () -> sunstrikeBuilder().m_20699_(0.1F, 0.1F).m_20712_(new ResourceLocation("mowziesmobs", "sunstrike").toString())
   );
   public static final RegistryObject<EntityType<EntitySolarBeam>> SOLAR_BEAM = REG.register(
      "solar_beam", () -> solarBeamBuilder().m_20699_(0.1F, 0.1F).setUpdateInterval(1).m_20712_(new ResourceLocation("mowziesmobs", "solar_beam").toString())
   );
   public static final RegistryObject<EntityType<EntityBoulderProjectile>> BOULDER_PROJECTILE = REG.register(
      "boulder_projectile",
      () -> boulderProjectileBuilder().m_20699_(1.0F, 1.0F).setUpdateInterval(1).m_20712_(new ResourceLocation("mowziesmobs", "boulder_projectile").toString())
   );
   public static final RegistryObject<EntityType<EntityRockSling>> ROCK_SLING = REG.register(
      "rock_sling", () -> rockSlingBuilder().m_20699_(1.0F, 1.0F).setUpdateInterval(1).m_20712_(new ResourceLocation("mowziesmobs", "rock_sling").toString())
   );
   public static final RegistryObject<EntityType<EntityBoulderPlatform>> BOULDER_PLATFORM = REG.register(
      "boulder_platform",
      () -> boulderPlatformBuilder().m_20699_(1.0F, 1.0F).setUpdateInterval(1).m_20712_(new ResourceLocation("mowziesmobs", "boulder_platform").toString())
   );
   public static final RegistryObject<EntityType<EntityPillar>> PILLAR = REG.register(
      "pillar", () -> pillarBuilder().m_20699_(1.0F, 1.0F).setUpdateInterval(1).m_20712_(new ResourceLocation("mowziesmobs", "pillar").toString())
   );
   public static final RegistryObject<EntityType<EntityPillarPiece>> PILLAR_PIECE = REG.register(
      "pillar_piece",
      () -> pillarPieceBuilder().m_20699_(1.0F, 1.0F).setUpdateInterval(1).m_20712_(new ResourceLocation("mowziesmobs", "pillar_piece").toString())
   );
   public static final RegistryObject<EntityType<EntityAxeAttack>> AXE_ATTACK = REG.register(
      "axe_attack", () -> axeAttackBuilder().m_20699_(1.0F, 1.0F).setUpdateInterval(1).m_20712_(new ResourceLocation("mowziesmobs", "axe_attack").toString())
   );
   public static final RegistryObject<EntityType<EntityIceBreath>> ICE_BREATH = REG.register(
      "ice_breath", () -> iceBreathBuilder().m_20699_(0.0F, 0.0F).setUpdateInterval(1).m_20712_(new ResourceLocation("mowziesmobs", "ice_breath").toString())
   );
   public static final RegistryObject<EntityType<EntityIceBall>> ICE_BALL = REG.register(
      "ice_ball", () -> iceBallBuilder().m_20699_(0.5F, 0.5F).setUpdateInterval(20).m_20712_(new ResourceLocation("mowziesmobs", "ice_ball").toString())
   );
   public static final RegistryObject<EntityType<EntityFrozenController>> FROZEN_CONTROLLER = REG.register(
      "frozen_controller",
      () -> frozenControllerBuilder().m_20698_().m_20699_(0.0F, 0.0F).m_20712_(new ResourceLocation("mowziesmobs", "frozen_controller").toString())
   );
   public static final RegistryObject<EntityType<EntityDart>> DART = REG.register(
      "dart", () -> dartBuilder().m_20698_().m_20699_(0.5F, 0.5F).setUpdateInterval(20).m_20712_(new ResourceLocation("mowziesmobs", "dart").toString())
   );
   public static final RegistryObject<EntityType<EntityPoisonBall>> POISON_BALL = REG.register(
      "poison_ball",
      () -> poisonBallBuilder().m_20699_(0.5F, 0.5F).setUpdateInterval(20).m_20712_(new ResourceLocation("mowziesmobs", "poison_ball").toString())
   );
   public static final RegistryObject<EntityType<EntitySuperNova>> SUPER_NOVA = REG.register(
      "super_nova",
      () -> superNovaBuilder().m_20699_(1.0F, 1.0F).setUpdateInterval(Integer.MAX_VALUE).m_20712_(new ResourceLocation("mowziesmobs", "super_nova").toString())
   );
   public static final RegistryObject<EntityType<EntityFallingBlock>> FALLING_BLOCK = REG.register(
      "falling_block", () -> fallingBlockBuilder().m_20699_(1.0F, 1.0F).m_20712_(new ResourceLocation("mowziesmobs", "falling_block").toString())
   );
   public static final RegistryObject<EntityType<EntityBlockSwapper>> BLOCK_SWAPPER = REG.register(
      "block_swapper",
      () -> blockSwapperBuilder()
            .m_20698_()
            .m_20699_(1.0F, 1.0F)
            .setUpdateInterval(Integer.MAX_VALUE)
            .m_20712_(new ResourceLocation("mowziesmobs", "block_swapper").toString())
   );
   public static final RegistryObject<EntityType<EntityBlockSwapper.EntityBlockSwapperSculptor>> BLOCK_SWAPPER_SCULPTOR = REG.register(
      "block_swapper_sculptor",
      () -> blockSwapperSculptorBuilder()
            .m_20698_()
            .m_20699_(1.0F, 1.0F)
            .setUpdateInterval(Integer.MAX_VALUE)
            .m_20712_(new ResourceLocation("mowziesmobs", "block_swapper_sculptor").toString())
   );
   public static final RegistryObject<EntityType<EntityCameraShake>> CAMERA_SHAKE = REG.register(
      "camera_shake",
      () -> cameraShakeBuilder()
            .m_20699_(1.0F, 1.0F)
            .setUpdateInterval(Integer.MAX_VALUE)
            .m_20712_(new ResourceLocation("mowziesmobs", "camera_shake").toString())
   );

   private static Builder<EntityUmvuthanaFollowerToRaptor> umvuthanaFollowerToRaptorBuilder() {
      return Builder.m_20704_(EntityUmvuthanaFollowerToRaptor::new, MobCategory.MONSTER);
   }

   private static Builder<EntityUmvuthanaFollowerToPlayer> umvuthanaFollowerToPlayerBuilder() {
      return Builder.m_20704_(EntityUmvuthanaFollowerToPlayer::new, MobCategory.MONSTER);
   }

   private static Builder<EntityUmvuthanaCraneToPlayer> umvuthanaCraneToPlayerBuilder() {
      return Builder.m_20704_(EntityUmvuthanaCraneToPlayer::new, MobCategory.MONSTER);
   }

   private static Builder<EntitySunstrike> sunstrikeBuilder() {
      return Builder.m_20704_(EntitySunstrike::new, MobCategory.MISC);
   }

   private static Builder<EntitySolarBeam> solarBeamBuilder() {
      return Builder.m_20704_(EntitySolarBeam::new, MobCategory.MISC);
   }

   private static Builder<EntityBoulderProjectile> boulderProjectileBuilder() {
      return Builder.m_20704_(EntityBoulderProjectile::new, MobCategory.MISC);
   }

   private static Builder<EntityRockSling> rockSlingBuilder() {
      return Builder.m_20704_(EntityRockSling::new, MobCategory.MISC);
   }

   private static Builder<EntityBoulderPlatform> boulderPlatformBuilder() {
      return Builder.m_20704_(EntityBoulderPlatform::new, MobCategory.MISC);
   }

   private static Builder<EntityPillar> pillarBuilder() {
      return Builder.m_20704_(EntityPillar::new, MobCategory.MISC);
   }

   private static Builder<EntityPillarPiece> pillarPieceBuilder() {
      return Builder.m_20704_(EntityPillarPiece::new, MobCategory.MISC);
   }

   private static Builder<EntityAxeAttack> axeAttackBuilder() {
      return Builder.m_20704_(EntityAxeAttack::new, MobCategory.MISC);
   }

   private static Builder<EntityIceBreath> iceBreathBuilder() {
      return Builder.m_20704_(EntityIceBreath::new, MobCategory.MISC);
   }

   private static Builder<EntityIceBall> iceBallBuilder() {
      return Builder.m_20704_(EntityIceBall::new, MobCategory.MISC);
   }

   private static Builder<EntityFrozenController> frozenControllerBuilder() {
      return Builder.m_20704_(EntityFrozenController::new, MobCategory.MISC);
   }

   private static Builder<EntityDart> dartBuilder() {
      return Builder.m_20704_(EntityDart::new, MobCategory.MISC);
   }

   private static Builder<EntityPoisonBall> poisonBallBuilder() {
      return Builder.m_20704_(EntityPoisonBall::new, MobCategory.MISC);
   }

   private static Builder<EntitySuperNova> superNovaBuilder() {
      return Builder.m_20704_(EntitySuperNova::new, MobCategory.MISC);
   }

   private static Builder<EntityFallingBlock> fallingBlockBuilder() {
      return Builder.m_20704_(EntityFallingBlock::new, MobCategory.MISC);
   }

   private static Builder<EntityBlockSwapper> blockSwapperBuilder() {
      return Builder.m_20704_(EntityBlockSwapper::new, MobCategory.MISC);
   }

   private static Builder<EntityBlockSwapper.EntityBlockSwapperSculptor> blockSwapperSculptorBuilder() {
      return Builder.m_20704_(EntityBlockSwapper.EntityBlockSwapperSculptor::new, MobCategory.MISC);
   }

   private static Builder<EntityCameraShake> cameraShakeBuilder() {
      return Builder.m_20704_(EntityCameraShake::new, MobCategory.MISC);
   }

   @SubscribeEvent
   public static void onCreateAttributes(EntityAttributeCreationEvent event) {
      event.put((EntityType)FOLIAATH.get(), EntityFoliaath.createAttributes().m_22265_());
      event.put((EntityType)BABY_FOLIAATH.get(), EntityBabyFoliaath.createAttributes().m_22265_());
      event.put((EntityType)WROUGHTNAUT.get(), EntityWroughtnaut.createAttributes().m_22265_());
      event.put((EntityType)UMVUTHANA_RAPTOR.get(), EntityUmvuthanaRaptor.createAttributes().m_22265_());
      event.put((EntityType)UMVUTHANA_MINION.get(), EntityUmvuthana.createAttributes().m_22265_());
      event.put((EntityType)UMVUTHANA_FOLLOWER_TO_PLAYER.get(), EntityUmvuthanaFollowerToPlayer.createAttributes().m_22265_());
      event.put((EntityType)UMVUTHANA_CRANE_TO_PLAYER.get(), EntityUmvuthanaFollowerToPlayer.createAttributes().m_22265_());
      event.put((EntityType)UMVUTHANA_FOLLOWER_TO_RAPTOR.get(), EntityUmvuthana.createAttributes().m_22265_());
      event.put((EntityType)UMVUTHANA_CRANE.get(), EntityUmvuthana.createAttributes().m_22265_());
      event.put((EntityType)UMVUTHI.get(), EntityUmvuthi.createAttributes().m_22265_());
      event.put((EntityType)FROSTMAW.get(), EntityFrostmaw.createAttributes().m_22265_());
      event.put((EntityType)NAGA.get(), EntityNaga.createAttributes().m_22265_());
      event.put((EntityType)LANTERN.get(), EntityLantern.createAttributes().m_22265_());
      event.put((EntityType)GROTTOL.get(), EntityGrottol.createAttributes().m_22265_());
   }
}
