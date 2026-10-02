package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.thirdlife.iterrpg.entity.AuraMobspawnEntity;
import net.thirdlife.iterrpg.entity.GrieverEntity;
import net.thirdlife.iterrpg.entity.MournstoneEntity;
import net.thirdlife.iterrpg.entity.WeeperEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;
import net.thirdlife.iterrpg.init.IterRpgModParticleTypes;

public class SorrowSealedAttackProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         Entity auraselect = null;
         boolean dospawn = false;
         boolean doattack = false;
         boolean shoudattack = false;
         boolean attack = false;
         double auraposx = 0.0;
         double auraposz = 0.0;
         double auraposy = 0.0;
         double attackType = 0.0;
         double ycheck = 0.0;
         double specialattack = 0.0;
         double attacktrigger = 0.0;
         double ypos = 0.0;
         double distance = 0.0;
         double yfinal = 0.0;
         double mobcount = 0.0;
         double aurarand = 0.0;
         double repeatnum = 0.0;
         double amount = 0.0;
         double offset = 0.0;
         double dist = 0.0;
         double zdir = 0.0;
         double ydir = 0.0;
         double xdir = 0.0;
         if (entity.getPersistentData().m_128459_("patience") <= -1.0) {
            if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
               _entity.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 8, 4, true, false));
            }

            Vec3 _center = new Vec3(x, y - 10.0, z);

            for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(15.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if (entityiterator instanceof GrieverEntity
                  || entityiterator instanceof WeeperEntity
                  || entityiterator instanceof MournstoneEntity
                  || entityiterator instanceof AuraMobspawnEntity) {
                  xdir = entityiterator.m_20185_() - entity.m_20185_();
                  ydir = entityiterator.m_20186_() + (double)(entityiterator.m_20206_() / 2.0F) - (entity.m_20186_() + 4.0);
                  zdir = entityiterator.m_20189_() - entity.m_20189_();
                  dist = 0.0;

                  for (int index0 = 0; index0 < 20; index0++) {
                     if (world instanceof ServerLevel _level) {
                        _level.m_8767_(
                           ParticleTypes.f_123808_,
                           entity.m_20185_() + dist * xdir,
                           entity.m_20186_() + 4.0 + ydir * dist,
                           entity.m_20189_() + dist * zdir,
                           1,
                           0.0,
                           0.0,
                           0.0,
                           0.0
                        );
                     }

                     dist += 0.05;
                  }
               }
            }
         }

         if (entity.getPersistentData().m_128459_("crossfire") >= 0.0) {
            entity.getPersistentData().m_128347_("crossfire", entity.getPersistentData().m_128459_("crossfire") - 0.5);
            if (world instanceof ServerLevel _level) {
               _level.m_8767_(
                  (SimpleParticleType)IterRpgModParticleTypes.VOID_EYE_PARTICLE.get(),
                  entity.getPersistentData().m_128459_("crossfireX") + entity.getPersistentData().m_128459_("crossfire"),
                  entity.getPersistentData().m_128459_("crossfireY") + 1.0,
                  entity.getPersistentData().m_128459_("crossfireZ"),
                  12,
                  0.1,
                  0.5,
                  0.1,
                  0.0
               );
            }

            if (world instanceof ServerLevel _level) {
               _level.m_8767_(
                  (SimpleParticleType)IterRpgModParticleTypes.VOID_EYE_PARTICLE.get(),
                  entity.getPersistentData().m_128459_("crossfireX") - entity.getPersistentData().m_128459_("crossfire"),
                  entity.getPersistentData().m_128459_("crossfireY") + 1.0,
                  entity.getPersistentData().m_128459_("crossfireZ"),
                  12,
                  0.1,
                  0.5,
                  0.1,
                  0.0
               );
            }

            if (world instanceof ServerLevel _level) {
               _level.m_8767_(
                  (SimpleParticleType)IterRpgModParticleTypes.VOID_EYE_PARTICLE.get(),
                  entity.getPersistentData().m_128459_("crossfireX"),
                  entity.getPersistentData().m_128459_("crossfireY") + 1.0,
                  entity.getPersistentData().m_128459_("crossfireZ") + entity.getPersistentData().m_128459_("crossfire"),
                  12,
                  0.1,
                  0.5,
                  0.1,
                  0.0
               );
            }

            if (world instanceof ServerLevel _level) {
               _level.m_8767_(
                  (SimpleParticleType)IterRpgModParticleTypes.VOID_EYE_PARTICLE.get(),
                  entity.getPersistentData().m_128459_("crossfireX"),
                  entity.getPersistentData().m_128459_("crossfireY") + 1.0,
                  entity.getPersistentData().m_128459_("crossfireZ") - entity.getPersistentData().m_128459_("crossfire"),
                  12,
                  0.1,
                  0.5,
                  0.1,
                  0.0
               );
            }

            Vec3 _center = new Vec3(
               entity.getPersistentData().m_128459_("crossfireX") + entity.getPersistentData().m_128459_("crossfire"),
               entity.getPersistentData().m_128459_("crossfireY") + 1.0,
               entity.getPersistentData().m_128459_("crossfireZ")
            );

            for (Entity entityiteratorx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(0.625), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if (entityiteratorx instanceof Player
                     && (
                        (new Object() {
                                 public boolean checkGamemode(Entity _ent) {
                                    if (_ent instanceof ServerPlayer _serverPlayer) {
                                       return _serverPlayer.f_8941_.m_9290_() == GameType.SURVIVAL;
                                    } else {
                                       return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                                          ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                                             && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.SURVIVAL
                                          : false;
                                    }
                                 }
                              })
                              .checkGamemode(entityiteratorx)
                           || (new Object() {
                                 public boolean checkGamemode(Entity _ent) {
                                    if (_ent instanceof ServerPlayer _serverPlayer) {
                                       return _serverPlayer.f_8941_.m_9290_() == GameType.ADVENTURE;
                                    } else {
                                       return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                                          ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                                             && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.ADVENTURE
                                          : false;
                                    }
                                 }
                              })
                              .checkGamemode(entityiteratorx)
                     )
                  || entityiteratorx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("forge:player_allies")))) {
                  entityiteratorx.m_6469_(DamageSource.f_19318_, 4.0F);
               }
            }

            _center = new Vec3(
               entity.getPersistentData().m_128459_("crossfireX") - entity.getPersistentData().m_128459_("crossfire"),
               entity.getPersistentData().m_128459_("crossfireY") + 1.0,
               entity.getPersistentData().m_128459_("crossfireZ")
            );

            for (Entity entityiteratorxx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(0.625), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if (entityiteratorxx instanceof Player
                     && (
                        (new Object() {
                                 public boolean checkGamemode(Entity _ent) {
                                    if (_ent instanceof ServerPlayer _serverPlayer) {
                                       return _serverPlayer.f_8941_.m_9290_() == GameType.SURVIVAL;
                                    } else {
                                       return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                                          ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                                             && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.SURVIVAL
                                          : false;
                                    }
                                 }
                              })
                              .checkGamemode(entityiteratorxx)
                           || (new Object() {
                                 public boolean checkGamemode(Entity _ent) {
                                    if (_ent instanceof ServerPlayer _serverPlayer) {
                                       return _serverPlayer.f_8941_.m_9290_() == GameType.ADVENTURE;
                                    } else {
                                       return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                                          ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                                             && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.ADVENTURE
                                          : false;
                                    }
                                 }
                              })
                              .checkGamemode(entityiteratorxx)
                     )
                  || entityiteratorxx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("forge:player_allies")))) {
                  entityiteratorxx.m_6469_(DamageSource.f_19318_, 4.0F);
               }
            }

            _center = new Vec3(
               entity.getPersistentData().m_128459_("crossfireX"),
               entity.getPersistentData().m_128459_("crossfireY") + 1.0,
               entity.getPersistentData().m_128459_("crossfireZ") + entity.getPersistentData().m_128459_("crossfire")
            );

            for (Entity entityiteratorxxx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(0.625), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if (entityiteratorxxx instanceof Player
                     && (
                        (new Object() {
                                 public boolean checkGamemode(Entity _ent) {
                                    if (_ent instanceof ServerPlayer _serverPlayer) {
                                       return _serverPlayer.f_8941_.m_9290_() == GameType.SURVIVAL;
                                    } else {
                                       return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                                          ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                                             && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.SURVIVAL
                                          : false;
                                    }
                                 }
                              })
                              .checkGamemode(entityiteratorxxx)
                           || (new Object() {
                                 public boolean checkGamemode(Entity _ent) {
                                    if (_ent instanceof ServerPlayer _serverPlayer) {
                                       return _serverPlayer.f_8941_.m_9290_() == GameType.ADVENTURE;
                                    } else {
                                       return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                                          ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                                             && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.ADVENTURE
                                          : false;
                                    }
                                 }
                              })
                              .checkGamemode(entityiteratorxxx)
                     )
                  || entityiteratorxxx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("forge:player_allies")))) {
                  entityiteratorxxx.m_6469_(DamageSource.f_19318_, 4.0F);
               }
            }

            _center = new Vec3(
               entity.getPersistentData().m_128459_("crossfireX"),
               entity.getPersistentData().m_128459_("crossfireY") + 1.0,
               entity.getPersistentData().m_128459_("crossfireZ") - entity.getPersistentData().m_128459_("crossfire")
            );

            for (Entity entityiteratorxxxx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(0.625), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if (entityiteratorxxxx instanceof Player
                     && (
                        (new Object() {
                                 public boolean checkGamemode(Entity _ent) {
                                    if (_ent instanceof ServerPlayer _serverPlayer) {
                                       return _serverPlayer.f_8941_.m_9290_() == GameType.SURVIVAL;
                                    } else {
                                       return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                                          ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                                             && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.SURVIVAL
                                          : false;
                                    }
                                 }
                              })
                              .checkGamemode(entityiteratorxxxx)
                           || (new Object() {
                                 public boolean checkGamemode(Entity _ent) {
                                    if (_ent instanceof ServerPlayer _serverPlayer) {
                                       return _serverPlayer.f_8941_.m_9290_() == GameType.ADVENTURE;
                                    } else {
                                       return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                                          ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                                             && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.ADVENTURE
                                          : false;
                                    }
                                 }
                              })
                              .checkGamemode(entityiteratorxxxx)
                     )
                  || entityiteratorxxxx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("forge:player_allies")))) {
                  entityiteratorxxxx.m_6469_(DamageSource.f_19318_, 4.0F);
               }
            }
         }

         if (entity.getPersistentData().m_128459_("miniAttack") >= 125.0 + 10.0 * entity.getPersistentData().m_128459_("patience")) {
            entity.getPersistentData().m_128347_("miniAttack", 0.0);
            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiteratorxxxxx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(24.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if (entityiteratorxxxxx instanceof Player
                     && (
                        (new Object() {
                                 public boolean checkGamemode(Entity _ent) {
                                    if (_ent instanceof ServerPlayer _serverPlayer) {
                                       return _serverPlayer.f_8941_.m_9290_() == GameType.SURVIVAL;
                                    } else {
                                       return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                                          ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                                             && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.SURVIVAL
                                          : false;
                                    }
                                 }
                              })
                              .checkGamemode(entityiteratorxxxxx)
                           || (new Object() {
                                 public boolean checkGamemode(Entity _ent) {
                                    if (_ent instanceof ServerPlayer _serverPlayer) {
                                       return _serverPlayer.f_8941_.m_9290_() == GameType.ADVENTURE;
                                    } else {
                                       return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                                          ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                                             && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.ADVENTURE
                                          : false;
                                    }
                                 }
                              })
                              .checkGamemode(entityiteratorxxxxx)
                     )
                  || entityiteratorxxxxx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("forge:player_allies")))) {
                  entity.getPersistentData().m_128347_("crossfire", 16.0);
                  entity.getPersistentData().m_128347_("crossfireX", entityiteratorxxxxx.m_20185_());
                  entity.getPersistentData().m_128347_("crossfireY", entityiteratorxxxxx.m_20186_());
                  entity.getPersistentData().m_128347_("crossfireZ", entityiteratorxxxxx.m_20189_());
               }
            }
         } else {
            entity.getPersistentData().m_128347_("miniAttack", entity.getPersistentData().m_128459_("miniAttack") + 1.0);
         }

         if (entity.getPersistentData().m_128459_("attackCooldown") >= entity.getPersistentData().m_128459_("MaxCooldown")) {
            Vec3 _center = new Vec3(x, y + 1.5, z);

            for (Entity entityiteratorxxxxxx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(2.75), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if (entityiteratorxxxxxx instanceof Player
                     && (
                        (new Object() {
                                 public boolean checkGamemode(Entity _ent) {
                                    if (_ent instanceof ServerPlayer _serverPlayer) {
                                       return _serverPlayer.f_8941_.m_9290_() == GameType.SURVIVAL;
                                    } else {
                                       return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                                          ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                                             && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.SURVIVAL
                                          : false;
                                    }
                                 }
                              })
                              .checkGamemode(entityiteratorxxxxxx)
                           || (new Object() {
                                 public boolean checkGamemode(Entity _ent) {
                                    if (_ent instanceof ServerPlayer _serverPlayer) {
                                       return _serverPlayer.f_8941_.m_9290_() == GameType.ADVENTURE;
                                    } else {
                                       return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                                          ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                                             && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.ADVENTURE
                                          : false;
                                    }
                                 }
                              })
                              .checkGamemode(entityiteratorxxxxxx)
                     )
                  || entityiteratorxxxxxx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("forge:player_allies")))) {
                  entityiteratorxxxxxx.m_6469_(DamageSource.f_19318_, 5.0F);
                  entityiteratorxxxxxx.m_20256_(
                     new Vec3(
                        Mth.m_216263_(RandomSource.m_216327_(), -3.0, 3.0),
                        Mth.m_216263_(RandomSource.m_216327_(), 0.5, 1.0),
                        Mth.m_216263_(RandomSource.m_216327_(), -3.0, 3.0)
                     )
                  );
               }
            }

            entity.getPersistentData().m_128347_("attackCooldown", 0.0);
            entity.getPersistentData().m_128347_("MaxCooldown", (double)Mth.m_216271_(RandomSource.m_216327_(), 64, 96));
            if (entity.getPersistentData().m_128459_("patience") > -1.0) {
               entity.getPersistentData().m_128347_("patience", entity.getPersistentData().m_128459_("patience") - 1.0);
               doattack = false;
               _center = new Vec3(x, y, z);

               for (Entity entityiteratorxxxxxxx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(24.0), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                  .collect(Collectors.toList())) {
                  if (entityiteratorxxxxxxx instanceof Player
                        && (
                           (new Object() {
                                    public boolean checkGamemode(Entity _ent) {
                                       if (_ent instanceof ServerPlayer _serverPlayer) {
                                          return _serverPlayer.f_8941_.m_9290_() == GameType.SURVIVAL;
                                       } else {
                                          return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                                             ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                                                && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.SURVIVAL
                                             : false;
                                       }
                                    }
                                 })
                                 .checkGamemode(entityiteratorxxxxxxx)
                              || (new Object() {
                                    public boolean checkGamemode(Entity _ent) {
                                       if (_ent instanceof ServerPlayer _serverPlayer) {
                                          return _serverPlayer.f_8941_.m_9290_() == GameType.ADVENTURE;
                                       } else {
                                          return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                                             ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                                                && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.ADVENTURE
                                             : false;
                                       }
                                    }
                                 })
                                 .checkGamemode(entityiteratorxxxxxxx)
                        )
                     || entityiteratorxxxxxxx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("forge:player_allies")))) {
                     doattack = true;
                  }
               }

               if (doattack) {
                  specialattack = (double)Mth.m_216271_(RandomSource.m_216327_(), 1, 2);
                  shoudattack = true;
                  if (world instanceof ServerLevel _level) {
                     _level.m_8767_(ParticleTypes.f_123746_, x, y + 2.0, z, 32, 1.0, 1.0, 1.0, 0.16);
                  }

                  if (world instanceof ServerLevel _level) {
                     _level.m_8767_((SimpleParticleType)IterRpgModParticleTypes.ARCANE_PARTICLE.get(), x, y + 2.0, z, 32, 1.0, 1.0, 1.0, 0.16);
                  }

                  if (world instanceof ServerLevel _level) {
                     _level.m_8767_(ParticleTypes.f_123745_, x, y + 2.0, z, 32, 1.0, 1.0, 1.0, 0.16);
                  }

                  _center = new Vec3(x, y + 2.0, z);

                  for (Entity entityiteratorxxxxxxxx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(24.0), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                     .collect(Collectors.toList())) {
                     if ((
                           entityiteratorxxxxxxxx instanceof Player
                                 && (
                                    (new Object() {
                                             public boolean checkGamemode(Entity _ent) {
                                                if (_ent instanceof ServerPlayer _serverPlayer) {
                                                   return _serverPlayer.f_8941_.m_9290_() == GameType.SURVIVAL;
                                                } else {
                                                   return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                                                      ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                                                         && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_()
                                                            == GameType.SURVIVAL
                                                      : false;
                                                }
                                             }
                                          })
                                          .checkGamemode(entityiteratorxxxxxxxx)
                                       || (new Object() {
                                             public boolean checkGamemode(Entity _ent) {
                                                if (_ent instanceof ServerPlayer _serverPlayer) {
                                                   return _serverPlayer.f_8941_.m_9290_() == GameType.ADVENTURE;
                                                } else {
                                                   return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                                                      ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                                                         && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_()
                                                            == GameType.ADVENTURE
                                                      : false;
                                                }
                                             }
                                          })
                                          .checkGamemode(entityiteratorxxxxxxxx)
                                 )
                              || entityiteratorxxxxxxxx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("forge:player_allies")))
                        )
                        && shoudattack) {
                        shoudattack = false;

                        for (int index1 = 0; index1 < Mth.m_216271_(RandomSource.m_216327_(), 8, 12); index1++) {
                           auraposx = entityiteratorxxxxxxxx.m_20185_() + Mth.m_216263_(RandomSource.m_216327_(), -6.0, 6.0);
                           auraposz = entityiteratorxxxxxxxx.m_20189_() + Mth.m_216263_(RandomSource.m_216327_(), -6.0, 6.0);
                           auraposy = (double)Math.round(entityiteratorxxxxxxxx.m_20186_() - 4.0);
                           ycheck = auraposy;
                           dospawn = false;

                           for (int index2 = 0; index2 < 12; index2++) {
                              if (!world.m_8055_(new BlockPos(auraposx, auraposy, auraposz)).m_60815_()
                                 && world.m_8055_(new BlockPos(auraposx, auraposy - 1.0, auraposz)).m_60815_()) {
                                 ycheck = auraposy;
                                 dospawn = true;
                              }

                              if (!dospawn) {
                                 auraposy++;
                              }
                           }

                           if (dospawn) {
                              AuraSpawnProcedure.execute(world, auraposx, ycheck, auraposz);
                           }
                        }

                        shoudattack = true;

                        for (int index3 = 0; index3 < Mth.m_216271_(RandomSource.m_216327_(), 8, 12); index3++) {
                           auraposx = entity.m_20185_() + Mth.m_216263_(RandomSource.m_216327_(), -12.0, 12.0);
                           auraposz = entity.m_20189_() + Mth.m_216263_(RandomSource.m_216327_(), -12.0, 12.0);
                           auraposy = (double)Math.round(entity.m_20186_() - 4.0);
                           ycheck = auraposy;
                           dospawn = false;

                           for (int index4 = 0; index4 < 12; index4++) {
                              if (!world.m_8055_(new BlockPos(auraposx, auraposy, auraposz)).m_60815_()
                                 && world.m_8055_(new BlockPos(auraposx, auraposy - 1.0, auraposz)).m_60815_()) {
                                 ycheck = auraposy;
                                 dospawn = true;
                              }

                              if (!dospawn) {
                                 auraposy++;
                              }
                           }

                           if (dospawn) {
                              AuraSpawnProcedure.execute(world, auraposx, ycheck, auraposz);
                           }
                        }
                     }
                  }
               } else {
                  entity.getPersistentData().m_128347_("attackCooldown", 0.0);
               }
            } else if (entity.getPersistentData().m_128459_("patience") > -2.0) {
               entity.getPersistentData().m_128347_("patience", -5.0);
               shoudattack = true;
               _center = new Vec3(x, y, z);

               for (Entity entityiteratorxxxxxxxxx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(24.0), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                  .collect(Collectors.toList())) {
                  if ((
                        entityiteratorxxxxxxxxx instanceof Player
                              && (
                                 (new Object() {
                                          public boolean checkGamemode(Entity _ent) {
                                             if (_ent instanceof ServerPlayer _serverPlayer) {
                                                return _serverPlayer.f_8941_.m_9290_() == GameType.SURVIVAL;
                                             } else {
                                                return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                                                   ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                                                      && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.SURVIVAL
                                                   : false;
                                             }
                                          }
                                       })
                                       .checkGamemode(entityiteratorxxxxxxxxx)
                                    || (new Object() {
                                          public boolean checkGamemode(Entity _ent) {
                                             if (_ent instanceof ServerPlayer _serverPlayer) {
                                                return _serverPlayer.f_8941_.m_9290_() == GameType.ADVENTURE;
                                             } else {
                                                return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                                                   ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                                                      && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_()
                                                         == GameType.ADVENTURE
                                                   : false;
                                             }
                                          }
                                       })
                                       .checkGamemode(entityiteratorxxxxxxxxx)
                              )
                           || entityiteratorxxxxxxxxx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("forge:player_allies")))
                     )
                     && shoudattack) {
                     shoudattack = false;

                     for (int index5 = 0; index5 < 6; index5++) {
                        auraposx = entityiteratorxxxxxxxxx.m_20185_() + Mth.m_216263_(RandomSource.m_216327_(), -6.0, 6.0);
                        auraposz = entityiteratorxxxxxxxxx.m_20189_() + Mth.m_216263_(RandomSource.m_216327_(), -6.0, 6.0);
                        auraposy = (double)Math.round(entityiteratorxxxxxxxxx.m_20186_() - 4.0);
                        ycheck = auraposy;
                        dospawn = false;

                        for (int index6 = 0; index6 < 12; index6++) {
                           if (!world.m_8055_(new BlockPos(auraposx, auraposy, auraposz)).m_60815_()
                              && world.m_8055_(new BlockPos(auraposx, auraposy - 1.0, auraposz)).m_60815_()) {
                              ycheck = auraposy;
                              dospawn = true;
                           }

                           if (!dospawn) {
                              auraposy++;
                           }
                        }

                        if (dospawn && world instanceof ServerLevel _level) {
                           Entity entityToSpawn = new AuraMobspawnEntity((EntityType<AuraMobspawnEntity>)IterRpgModEntities.AURA_MOBSPAWN.get(), _level);
                           entityToSpawn.m_7678_(auraposx, ycheck, auraposz, 0.0F, 0.0F);
                           entityToSpawn.m_5618_(0.0F);
                           entityToSpawn.m_5616_(0.0F);
                           if (entityToSpawn instanceof Mob _mobToSpawn) {
                              _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                           }

                           world.m_7967_(entityToSpawn);
                        }
                     }
                  }
               }
            } else {
               mobcount = 0.0;
               _center = new Vec3(x, y - 10.0, z);

               for (Entity entityiteratorxxxxxxxxxx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(15.0), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                  .collect(Collectors.toList())) {
                  if (entityiteratorxxxxxxxxxx instanceof GrieverEntity
                     || entityiteratorxxxxxxxxxx instanceof WeeperEntity
                     || entityiteratorxxxxxxxxxx instanceof MournstoneEntity
                     || entityiteratorxxxxxxxxxx instanceof AuraMobspawnEntity) {
                     mobcount++;
                  }
               }

               if (mobcount <= 2.0) {
                  entity.getPersistentData().m_128347_("patience", (double)Mth.m_216271_(RandomSource.m_216327_(), 6, 8));
                  if (entity instanceof LivingEntity _entity) {
                     _entity.m_21219_();
                  }
               }
            }
         } else {
            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiteratorxxxxxxxxxxx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(24.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if (entityiteratorxxxxxxxxxxx instanceof Player
                     && (
                        (new Object() {
                                 public boolean checkGamemode(Entity _ent) {
                                    if (_ent instanceof ServerPlayer _serverPlayer) {
                                       return _serverPlayer.f_8941_.m_9290_() == GameType.SURVIVAL;
                                    } else {
                                       return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                                          ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                                             && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.SURVIVAL
                                          : false;
                                    }
                                 }
                              })
                              .checkGamemode(entityiteratorxxxxxxxxxxx)
                           || (new Object() {
                                 public boolean checkGamemode(Entity _ent) {
                                    if (_ent instanceof ServerPlayer _serverPlayer) {
                                       return _serverPlayer.f_8941_.m_9290_() == GameType.ADVENTURE;
                                    } else {
                                       return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                                          ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                                             && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.ADVENTURE
                                          : false;
                                    }
                                 }
                              })
                              .checkGamemode(entityiteratorxxxxxxxxxxx)
                     )
                  || entityiteratorxxxxxxxxxxx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("forge:player_allies")))) {
                  attacktrigger = 1.0;
               }
            }

            if (attacktrigger == 1.0) {
               entity.getPersistentData().m_128347_("attackCooldown", entity.getPersistentData().m_128459_("attackCooldown") + 1.0);
               if (world instanceof ServerLevel _level) {
                  _level.m_8767_(ParticleTypes.f_123746_, x, y + 2.0, z, 1, 1.0, 1.0, 1.0, 0.025);
               }

               if (world instanceof ServerLevel _level) {
                  _level.m_8767_((SimpleParticleType)IterRpgModParticleTypes.ARCANE_PARTICLE.get(), x, y + 2.0, z, 1, 1.0, 1.0, 1.0, 0.025);
               }

               if (world instanceof ServerLevel _level) {
                  _level.m_8767_(ParticleTypes.f_123745_, x, y + 2.0, z, 1, 1.0, 1.0, 1.0, 0.025);
               }
            }
         }
      }
   }
}
