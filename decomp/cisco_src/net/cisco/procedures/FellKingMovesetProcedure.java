package net.cisco.procedures;

import net.cisco.CiscoModMod;
import net.cisco.entity.FellShieldEntity;
import net.cisco.entity.FellkingbossEntity;
import net.cisco.entity.LegionnaireJotunnEntity;
import net.cisco.entity.LegionnaireKingsguardEntity;
import net.cisco.init.CiscoModModEntities;
import net.cisco.network.CiscoModModVariables;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class FellKingMovesetProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         double chain = 0.0;
         double ChainWait = 0.0;
         if (entity instanceof FellkingbossEntity) {
            if ((entity instanceof Mob _mobEnt ? _mobEnt.m_5448_() : null) != null) {
               entity.getPersistentData().m_128347_("FA", entity.getPersistentData().m_128459_("FA") + 2.0);
            }

            if (entity.getPersistentData().m_128459_("FA") == 20.0 && entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
               _entity.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 180, 2));
            }

            if (world.m_6443_(LegionnaireKingsguardEntity.class, AABB.m_165882_(new Vec3(x, y, z), 25.0, 25.0, 25.0), e -> true).isEmpty()
               && entity.getPersistentData().m_128459_("FA") == 40.0) {
               if (entity instanceof FellkingbossEntity) {
                  ((FellkingbossEntity)entity).setAnimation("summon");
               }

               if (world instanceof Level _level) {
                  if (!_level.m_5776_()) {
                     _level.m_5594_(
                        null,
                        new BlockPos(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.prepare_summon")),
                        SoundSource.NEUTRAL,
                        3.0F,
                        1.0F
                     );
                  } else {
                     _level.m_7785_(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.prepare_summon")),
                        SoundSource.NEUTRAL,
                        3.0F,
                        1.0F,
                        false
                     );
                  }
               }

               if (world instanceof ServerLevel _levelx) {
                  Entity entityToSpawn = new LegionnaireKingsguardEntity(
                     (EntityType<LegionnaireKingsguardEntity>)CiscoModModEntities.LEGIONNAIRE_KINGSGUARD.get(), _levelx
                  );
                  entityToSpawn.m_7678_(x, y, z + 2.0, world.m_213780_().m_188501_() * 360.0F, 0.0F);
                  if (entityToSpawn instanceof Mob _mobToSpawn) {
                     _mobToSpawn.m_6518_(_levelx, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                  }

                  world.m_7967_(entityToSpawn);
               }

               if (world instanceof ServerLevel _levelx) {
                  Entity entityToSpawn = new LegionnaireKingsguardEntity(
                     (EntityType<LegionnaireKingsguardEntity>)CiscoModModEntities.LEGIONNAIRE_KINGSGUARD.get(), _levelx
                  );
                  entityToSpawn.m_7678_(x + 2.0, y, z, world.m_213780_().m_188501_() * 360.0F, 0.0F);
                  if (entityToSpawn instanceof Mob _mobToSpawn) {
                     _mobToSpawn.m_6518_(_levelx, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                  }

                  world.m_7967_(entityToSpawn);
               }

               if (world instanceof ServerLevel _levelx) {
                  Entity entityToSpawn = new LegionnaireKingsguardEntity(
                     (EntityType<LegionnaireKingsguardEntity>)CiscoModModEntities.LEGIONNAIRE_KINGSGUARD.get(), _levelx
                  );
                  entityToSpawn.m_7678_(x, y, z - 2.0, world.m_213780_().m_188501_() * 360.0F, 0.0F);
                  if (entityToSpawn instanceof Mob _mobToSpawn) {
                     _mobToSpawn.m_6518_(_levelx, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                  }

                  world.m_7967_(entityToSpawn);
               }

               if (world instanceof ServerLevel _levelx) {
                  Entity entityToSpawn = new LegionnaireKingsguardEntity(
                     (EntityType<LegionnaireKingsguardEntity>)CiscoModModEntities.LEGIONNAIRE_KINGSGUARD.get(), _levelx
                  );
                  entityToSpawn.m_7678_(x - 2.0, y, z, world.m_213780_().m_188501_() * 360.0F, 0.0F);
                  if (entityToSpawn instanceof Mob _mobToSpawn) {
                     _mobToSpawn.m_6518_(_levelx, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                  }

                  world.m_7967_(entityToSpawn);
               }
            }

            if (entity.getPersistentData().m_128459_("FA") == 40.0) {
               if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                  _entity.m_7292_(new MobEffectInstance(MobEffects.f_19596_, 180, 1));
               }

               if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                  _entity.m_7292_(new MobEffectInstance(MobEffects.f_19600_, 180, 4));
               }
            }

            if (!world.m_6443_(FellkingbossEntity.class, AABB.m_165882_(new Vec3(x, y, z), 20.0, 20.0, 20.0), e -> true).isEmpty()
               && world.m_6443_(FellShieldEntity.class, AABB.m_165882_(new Vec3(x, y, z), 20.0, 20.0, 20.0), e -> true).isEmpty()
               && entity.getPersistentData().m_128459_("FA") == 60.0) {
               if (entity instanceof FellkingbossEntity) {
                  ((FellkingbossEntity)entity).setAnimation("challenge");
               }

               if (world instanceof Level _levelx) {
                  if (!_levelx.m_5776_()) {
                     _levelx.m_5594_(
                        null,
                        new BlockPos(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.use")),
                        SoundSource.NEUTRAL,
                        1.0F,
                        1.0F
                     );
                  } else {
                     _levelx.m_7785_(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.use")),
                        SoundSource.NEUTRAL,
                        1.0F,
                        1.0F,
                        false
                     );
                  }
               }

               if (world instanceof ServerLevel _levelxx) {
                  Entity entityToSpawn = new FellShieldEntity((EntityType<FellShieldEntity>)CiscoModModEntities.FELL_SHIELD.get(), _levelxx);
                  entityToSpawn.m_7678_(x, y, z, world.m_213780_().m_188501_() * 360.0F, 0.0F);
                  if (entityToSpawn instanceof Mob _mobToSpawn) {
                     _mobToSpawn.m_6518_(_levelxx, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                  }

                  world.m_7967_(entityToSpawn);
               }

               if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                  _entity.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 120, 1));
               }
            }

            if (entity.getPersistentData().m_128459_("FA") == 75.0) {
               if (entity instanceof FellkingbossEntity) {
                  ((FellkingbossEntity)entity).setAnimation("attack2");
               }

               if (entity instanceof LivingEntity _entity) {
                  _entity.m_21011_(InteractionHand.MAIN_HAND, true);
               }

               if (world instanceof Level _levelxx) {
                  if (!_levelxx.m_5776_()) {
                     _levelxx.m_5594_(
                        null,
                        new BlockPos(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("cisco_mod:nightxragdual")),
                        SoundSource.NEUTRAL,
                        1.0F,
                        1.0F
                     );
                  } else {
                     _levelxx.m_7785_(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("cisco_mod:nightxragdual")),
                        SoundSource.NEUTRAL,
                        1.0F,
                        1.0F,
                        false
                     );
                  }
               }
            }

            if (entity.getPersistentData().m_128459_("FA") == 90.0 && entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
               _entity.m_7292_(new MobEffectInstance(MobEffects.f_19598_, 140, 1));
            }

            if (world.m_6443_(LegionnaireJotunnEntity.class, AABB.m_165882_(new Vec3(x, y, z), 25.0, 25.0, 25.0), e -> true).isEmpty()
               && entity.getPersistentData().m_128459_("FA") == 100.0) {
               if (entity instanceof FellkingbossEntity) {
                  ((FellkingbossEntity)entity).setAnimation("summon");
               }

               if (world instanceof Level _levelxxx) {
                  if (!_levelxxx.m_5776_()) {
                     _levelxxx.m_5594_(
                        null,
                        new BlockPos(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.prepare_summon")),
                        SoundSource.NEUTRAL,
                        3.0F,
                        1.0F
                     );
                  } else {
                     _levelxxx.m_7785_(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.prepare_summon")),
                        SoundSource.NEUTRAL,
                        3.0F,
                        1.0F,
                        false
                     );
                  }
               }

               if (world instanceof ServerLevel _levelxxxx) {
                  Entity entityToSpawn = new LegionnaireJotunnEntity(
                     (EntityType<LegionnaireJotunnEntity>)CiscoModModEntities.LEGIONNAIRE_JOTUNN.get(), _levelxxxx
                  );
                  entityToSpawn.m_7678_(x, y, z + 2.0, world.m_213780_().m_188501_() * 360.0F, 0.0F);
                  if (entityToSpawn instanceof Mob _mobToSpawn) {
                     _mobToSpawn.m_6518_(_levelxxxx, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                  }

                  world.m_7967_(entityToSpawn);
               }

               if (world instanceof ServerLevel _levelxxxx) {
                  Entity entityToSpawn = new LegionnaireJotunnEntity(
                     (EntityType<LegionnaireJotunnEntity>)CiscoModModEntities.LEGIONNAIRE_JOTUNN.get(), _levelxxxx
                  );
                  entityToSpawn.m_7678_(x + 2.0, y, z, world.m_213780_().m_188501_() * 360.0F, 0.0F);
                  if (entityToSpawn instanceof Mob _mobToSpawn) {
                     _mobToSpawn.m_6518_(_levelxxxx, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                  }

                  world.m_7967_(entityToSpawn);
               }
            }

            if (entity.getPersistentData().m_128459_("FA") == 400.0) {
               entity.getPersistentData().m_128347_("FA", 0.0);
            }
         }

         if (!CiscoModModVariables.MapVariables.get(world).FellKingLives && entity instanceof FellkingbossEntity) {
            CiscoModMod.queueServerWork(
               40,
               () -> {
                  if (world instanceof ServerLevel _levelxxxx) {
                     _levelxxxx.m_7654_()
                        .m_129892_()
                        .m_230957_(
                           new CommandSourceStack(
                                 CommandSource.f_80164_,
                                 new Vec3(x, y, z),
                                 Vec2.f_82462_,
                                 _levelxxxx,
                                 4,
                                 "",
                                 Component.m_237113_(""),
                                 _levelxxxx.m_7654_(),
                                 null
                              )
                              .m_81324_(),
                           "stopsound @a master cisco_mod:overwatchboss"
                        );
                  }

                  if (!entity.f_19853_.m_5776_()) {
                     entity.m_146870_();
                  }
               }
            );
         }
      }
   }
}
