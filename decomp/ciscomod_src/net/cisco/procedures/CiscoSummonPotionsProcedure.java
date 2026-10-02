package net.cisco.procedures;

import net.cisco.CiscoModMod;
import net.cisco.entity.AfterImageEntity;
import net.cisco.entity.CiscoEntity;
import net.cisco.init.CiscoModModEntities;
import net.cisco.init.CiscoModModMobEffects;
import net.cisco.network.CiscoModModVariables;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
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

public class CiscoSummonPotionsProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         double chain = 0.0;
         double ChainWait = 0.0;
         if (entity instanceof CiscoEntity) {
            if ((entity instanceof LivingEntity _livEntx ? _livEntx.m_21223_() : -1.0F)
               > (entity instanceof LivingEntity _livEnt ? _livEnt.m_21233_() : -1.0F) / 2.0F) {
               if ((entity instanceof Mob _mobEnt ? _mobEnt.m_5448_() : null) != null) {
                  entity.getPersistentData().m_128347_("IA", entity.getPersistentData().m_128459_("IA") + 2.0);
               }

               if (entity.getPersistentData().m_128459_("IA") == 20.0 && entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                  _entity.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 180, 4));
               }

               if (entity.getPersistentData().m_128459_("IA") == 24.0) {
                  if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                     _entity.m_7292_(new MobEffectInstance(MobEffects.f_19596_, 180, 1));
                  }

                  if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                     _entity.m_7292_(new MobEffectInstance((MobEffect)CiscoModModMobEffects.CISCOS_MIGHT.get(), 180, 0));
                  }

                  if (world instanceof Level _level) {
                     if (!_level.m_5776_()) {
                        _level.m_5594_(
                           null,
                           new BlockPos(x, y, z),
                           (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("cisco_mod:armor")),
                           SoundSource.NEUTRAL,
                           1.0F,
                           1.0F
                        );
                     } else {
                        _level.m_7785_(
                           x,
                           y,
                           z,
                           (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("cisco_mod:armor")),
                           SoundSource.NEUTRAL,
                           1.0F,
                           1.0F,
                           false
                        );
                     }
                  }
               }

               if (entity.getPersistentData().m_128459_("IA") == 80.0 && entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                  _entity.m_7292_(new MobEffectInstance(MobEffects.f_19598_, 140, 1));
               }

               if (world.m_6443_(AfterImageEntity.class, AABB.m_165882_(new Vec3(x, y, z), 25.0, 25.0, 25.0), e -> true).isEmpty()
                  && entity.getPersistentData().m_128459_("IA") == 160.0) {
                  if (world instanceof ServerLevel _levelx) {
                     Entity entityToSpawn = new AfterImageEntity((EntityType<AfterImageEntity>)CiscoModModEntities.AFTER_IMAGE.get(), _levelx);
                     entityToSpawn.m_7678_(x, y, z + 1.5, world.m_213780_().m_188501_() * 360.0F, 0.0F);
                     if (entityToSpawn instanceof Mob _mobToSpawn) {
                        _mobToSpawn.m_6518_(_levelx, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                     }

                     world.m_7967_(entityToSpawn);
                  }

                  if (world instanceof ServerLevel _levelx) {
                     Entity entityToSpawn = new AfterImageEntity((EntityType<AfterImageEntity>)CiscoModModEntities.AFTER_IMAGE.get(), _levelx);
                     entityToSpawn.m_7678_(x + 1.5, y, z, world.m_213780_().m_188501_() * 360.0F, 0.0F);
                     if (entityToSpawn instanceof Mob _mobToSpawn) {
                        _mobToSpawn.m_6518_(_levelx, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                     }

                     world.m_7967_(entityToSpawn);
                  }

                  if (world instanceof ServerLevel _levelx) {
                     Entity entityToSpawn = new AfterImageEntity((EntityType<AfterImageEntity>)CiscoModModEntities.AFTER_IMAGE.get(), _levelx);
                     entityToSpawn.m_7678_(x, y, z - 1.5, world.m_213780_().m_188501_() * 360.0F, 0.0F);
                     if (entityToSpawn instanceof Mob _mobToSpawn) {
                        _mobToSpawn.m_6518_(_levelx, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                     }

                     world.m_7967_(entityToSpawn);
                  }

                  if (world instanceof ServerLevel _levelx) {
                     Entity entityToSpawn = new AfterImageEntity((EntityType<AfterImageEntity>)CiscoModModEntities.AFTER_IMAGE.get(), _levelx);
                     entityToSpawn.m_7678_(x - 1.5, y, z, world.m_213780_().m_188501_() * 360.0F, 0.0F);
                     if (entityToSpawn instanceof Mob _mobToSpawn) {
                        _mobToSpawn.m_6518_(_levelx, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                     }

                     world.m_7967_(entityToSpawn);
                  }

                  if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                     _entity.m_7292_(new MobEffectInstance(MobEffects.f_19605_, 120, 3));
                  }
               }

               if (entity.getPersistentData().m_128459_("IA") == 670.0) {
                  entity.getPersistentData().m_128347_("IA", 0.0);
               }
            } else if ((entity instanceof LivingEntity _livEntx ? _livEntx.m_21223_() : -1.0F)
               < (entity instanceof LivingEntity _livEnt ? _livEnt.m_21233_() : -1.0F) / 2.0F) {
               if ((entity instanceof Mob _mobEnt ? _mobEnt.m_5448_() : null) != null) {
                  entity.getPersistentData().m_128347_("IB", entity.getPersistentData().m_128459_("IB") + 1.0);
               }

               if (entity.getPersistentData().m_128459_("IB") == 10.0 && entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                  _entity.m_7292_(new MobEffectInstance(MobEffects.f_19596_, 60, 1));
               }

               if (entity.getPersistentData().m_128459_("IB") == 20.0) {
                  if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                     _entity.m_7292_(new MobEffectInstance((MobEffect)CiscoModModMobEffects.CISCO_RAGE.get(), 180, 1));
                  }

                  if (world instanceof Level _levelx) {
                     if (!_levelx.m_5776_()) {
                        _levelx.m_5594_(
                           null,
                           new BlockPos(x, y, z),
                           (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("cisco_mod:rage")),
                           SoundSource.NEUTRAL,
                           1.0F,
                           1.0F
                        );
                     } else {
                        _levelx.m_7785_(
                           x,
                           y,
                           z,
                           (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("cisco_mod:rage")),
                           SoundSource.NEUTRAL,
                           1.0F,
                           1.0F,
                           false
                        );
                     }
                  }
               }

               if (entity.getPersistentData().m_128459_("IB") == 80.0 && entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                  _entity.m_7292_(new MobEffectInstance(MobEffects.f_19605_, 20, 2));
               }

               if (entity.getPersistentData().m_128459_("IB") == 160.0 && entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                  _entity.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 160, 2));
               }

               if (entity.getPersistentData().m_128459_("IB") == 260.0) {
                  entity.getPersistentData().m_128347_("IB", 0.0);
               }
            }
         }

         if (!CiscoModModVariables.MapVariables.get(world).IsBossAliveAndInBattle && entity instanceof CiscoEntity) {
            CiscoModMod.queueServerWork(
               40,
               () -> {
                  if (world instanceof ServerLevel _levelxx) {
                     _levelxx.m_7654_()
                        .m_129892_()
                        .m_230957_(
                           new CommandSourceStack(
                                 CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _levelxx, 4, "", Component.m_237113_(""), _levelxx.m_7654_(), null
                              )
                              .m_81324_(),
                           "stopsound @a master cisco_mod:bravesoulboss "
                        );
                  }

                  if (!entity.f_19853_.m_5776_()) {
                     entity.m_146870_();
                  }
               }
            );
         }

         if ((entity instanceof Mob _mobEnt ? _mobEnt.m_5448_() : null) != null && entity instanceof CiscoEntity) {
            if (!world.m_6443_(AfterImageEntity.class, AABB.m_165882_(new Vec3(x, y, z), 18.0, 18.0, 18.0), e -> true).isEmpty()) {
               entity.m_20331_(true);
            } else {
               entity.m_20331_(false);
            }

            if (entity.m_20147_() && entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
               _entity.m_7292_(new MobEffectInstance(MobEffects.f_19619_, 60, 1));
            }
         }
      }
   }
}
