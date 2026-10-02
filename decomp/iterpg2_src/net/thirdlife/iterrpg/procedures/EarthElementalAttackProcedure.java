package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.IterRpgMod;
import net.thirdlife.iterrpg.entity.EarthBoulderEntity;
import net.thirdlife.iterrpg.entity.ForestVinesEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;
import net.thirdlife.iterrpg.init.IterRpgModParticleTypes;

public class EarthElementalAttackProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         boolean shouldtick = false;
         boolean shouldspawn = false;
         boolean flag = false;
         double attack = 0.0;
         double timer = 0.0;
         double particle = 0.0;
         double fireforce = 0.0;
         double xpos = 0.0;
         double ypos = 0.0;
         double zpos = 0.0;
         double yspawn = 0.0;
         double attacktype = 0.0;
         double iteration = 0.0;
         if (entity.getPersistentData().m_128459_("attack") >= 80.0) {
            entity.getPersistentData().m_128347_("attack", 0.0);
            if (entity.getPersistentData().m_128459_("attackchosen") == 0.0) {
               entity.getPersistentData().m_128347_("attackchosen", 1.0);
               attacktype = 1.0;
            } else if (entity.getPersistentData().m_128459_("attackchosen") == 1.0) {
               entity.getPersistentData().m_128347_("attackchosen", 2.0);
               attacktype = 2.0;
            } else if (entity.getPersistentData().m_128459_("attackchosen") == 2.0) {
               entity.getPersistentData().m_128347_("attackchosen", 0.0);
               attacktype = 3.0;
            }

            if (attacktype == 1.0) {
               if (world instanceof ServerLevel _level) {
                  _level.m_8767_((SimpleParticleType)IterRpgModParticleTypes.ELEMENTAL_LEAF.get(), x, y + 0.4, z, 32, 0.25, 0.25, 0.25, 0.05);
               }

               Vec3 _center = new Vec3(x, y, z);

               for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(12.0), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                  .collect(Collectors.toList())) {
                  if (entityiterator instanceof Player) {
                     for (int index0 = 0; index0 < 8; index0++) {
                        xpos = Mth.m_216263_(RandomSource.m_216327_(), -6.0, 6.0);
                        ypos = -4.0;
                        zpos = Mth.m_216263_(RandomSource.m_216327_(), -6.0, 6.0);
                        shouldspawn = false;

                        for (int index1 = 0; index1 < 8; index1++) {
                           if (world.m_46859_(
                                 new BlockPos(entityiterator.m_20185_() + xpos, entityiterator.m_20186_() + ypos, entityiterator.m_20189_() + zpos)
                              )
                              && world.m_8055_(
                                    new BlockPos(
                                       entityiterator.m_20185_() + xpos,
                                       (double)Math.round(entityiterator.m_20186_() + ypos - 1.0),
                                       entityiterator.m_20189_() + zpos
                                    )
                                 )
                                 .m_60815_()) {
                              yspawn = ypos;
                              shouldspawn = true;
                           }

                           ypos++;
                        }

                        if (shouldspawn) {
                           if (world instanceof ServerLevel _level) {
                              Entity entityToSpawn = new ForestVinesEntity((EntityType<ForestVinesEntity>)IterRpgModEntities.FOREST_VINES.get(), _level);
                              entityToSpawn.m_7678_(
                                 entityiterator.m_20185_() + xpos,
                                 Math.floor(entityiterator.m_20186_() + yspawn),
                                 entityiterator.m_20189_() + zpos,
                                 world.m_213780_().m_188501_() * 360.0F,
                                 0.0F
                              );
                              if (entityToSpawn instanceof Mob _mobToSpawn) {
                                 _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                              }

                              world.m_7967_(entityToSpawn);
                           }

                           if (world instanceof ServerLevel _level) {
                              _level.m_8767_(
                                 (SimpleParticleType)IterRpgModParticleTypes.ELEMENTAL_LEAF.get(),
                                 entityiterator.m_20185_() + xpos,
                                 entityiterator.m_20186_() + yspawn + 0.2,
                                 entityiterator.m_20189_() + zpos,
                                 6,
                                 0.25,
                                 0.5,
                                 0.25,
                                 0.25
                              );
                           }
                        }
                     }
                  }
               }
            } else if (attacktype == 2.0) {
               for (int index2 = 0; index2 < 3; index2++) {
                  xpos = Mth.m_216263_(RandomSource.m_216327_(), -3.0, 3.0);
                  ypos = -4.0;
                  zpos = Mth.m_216263_(RandomSource.m_216327_(), -3.0, 3.0);
                  shouldspawn = false;

                  for (int index3 = 0; index3 < 8; index3++) {
                     if (world.m_46859_(new BlockPos(entity.m_20185_() + xpos, entity.m_20186_() + ypos, entity.m_20189_() + zpos))
                        && world.m_8055_(new BlockPos(entity.m_20185_() + xpos, (double)Math.round(entity.m_20186_() + ypos - 1.0), entity.m_20189_() + zpos))
                           .m_60815_()) {
                        yspawn = ypos;
                        shouldspawn = true;
                     }

                     ypos++;
                  }

                  if (shouldspawn) {
                     if (world instanceof ServerLevel _level) {
                        Entity entityToSpawn = new EarthBoulderEntity((EntityType<EarthBoulderEntity>)IterRpgModEntities.EARTH_BOULDER.get(), _level);
                        entityToSpawn.m_7678_(
                           entity.m_20185_() + xpos,
                           Math.floor(entity.m_20186_() + yspawn) + 0.1,
                           entity.m_20189_() + zpos,
                           world.m_213780_().m_188501_() * 360.0F,
                           0.0F
                        );
                        if (entityToSpawn instanceof Mob _mobToSpawn) {
                           _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                        }

                        world.m_7967_(entityToSpawn);
                     }

                     if (world instanceof ServerLevel _level) {
                        _level.m_8767_(
                           ParticleTypes.f_123777_,
                           entity.m_20185_() + xpos,
                           entity.m_20186_() + yspawn + 0.2,
                           entity.m_20189_() + zpos,
                           2,
                           0.25,
                           0.5,
                           0.25,
                           0.0025
                        );
                     }
                  }
               }
            } else if (attacktype == 3.0) {
               if (!world.m_6443_(Player.class, AABB.m_165882_(new Vec3(x, y, z), 12.0, 12.0, 12.0), e -> true).isEmpty()) {
                  flag = false;
                  iteration = 0.0;

                  for (int index4 = 0; index4 < 10; index4++) {
                     if (world.m_8055_(new BlockPos(x, y - iteration, z)).m_60783_(world, new BlockPos(x, y - iteration, z), Direction.UP)) {
                        flag = true;
                     }

                     iteration++;
                  }

                  if (flag) {
                     entity.getPersistentData().m_128347_("slam", 1.0);
                     entity.m_20256_(new Vec3(0.0, 0.2, 0.0));
                     IterRpgMod.queueServerWork(10, () -> entity.m_20256_(new Vec3(0.0, -1.0, 0.0)));
                  }
               } else {
                  for (int index5 = 0; index5 < 3; index5++) {
                     xpos = Mth.m_216263_(RandomSource.m_216327_(), -3.0, 3.0);
                     ypos = -4.0;
                     zpos = Mth.m_216263_(RandomSource.m_216327_(), -3.0, 3.0);
                     shouldspawn = false;

                     for (int index6 = 0; index6 < 8; index6++) {
                        if (world.m_46859_(new BlockPos(entity.m_20185_() + xpos, entity.m_20186_() + ypos, entity.m_20189_() + zpos))
                           && world.m_8055_(
                                 new BlockPos(entity.m_20185_() + xpos, (double)Math.round(entity.m_20186_() + ypos - 1.0), entity.m_20189_() + zpos)
                              )
                              .m_60815_()) {
                           yspawn = ypos;
                           shouldspawn = true;
                        }

                        ypos++;
                     }

                     if (shouldspawn) {
                        if (world instanceof ServerLevel _level) {
                           Entity entityToSpawn = new EarthBoulderEntity((EntityType<EarthBoulderEntity>)IterRpgModEntities.EARTH_BOULDER.get(), _level);
                           entityToSpawn.m_7678_(
                              entity.m_20185_() + xpos,
                              Math.floor(entity.m_20186_() + yspawn) + 0.1,
                              entity.m_20189_() + zpos,
                              world.m_213780_().m_188501_() * 360.0F,
                              0.0F
                           );
                           if (entityToSpawn instanceof Mob _mobToSpawn) {
                              _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                           }

                           world.m_7967_(entityToSpawn);
                        }

                        if (world instanceof ServerLevel _level) {
                           _level.m_8767_(
                              ParticleTypes.f_123777_,
                              entity.m_20185_() + xpos,
                              entity.m_20186_() + yspawn + 0.2,
                              entity.m_20189_() + zpos,
                              2,
                              0.25,
                              0.5,
                              0.25,
                              0.0025
                           );
                        }
                     }
                  }
               }
            }
         } else {
            shouldtick = false;
            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiteratorx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(12.0), e -> true)
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
                  )) {
                  shouldtick = true;
               }
            }

            if (shouldtick) {
               entity.getPersistentData().m_128347_("attack", entity.getPersistentData().m_128459_("attack") + 1.0);
            }
         }

         if (entity.getPersistentData().m_128459_("slam") == 1.0 && world instanceof ServerLevel _level) {
            _level.m_8767_(ParticleTypes.f_123797_, x, y, z, 2, 0.2, 0.2, 0.2, 0.0);
         }

         if (entity.getPersistentData().m_128459_("slam") == 1.0
            && world.m_8055_(new BlockPos(x, y - 0.1, z)).m_60783_(world, new BlockPos(x, y - 0.1, z), Direction.UP)) {
            entity.getPersistentData().m_128347_("slam", 0.0);
            if (world instanceof ServerLevel _level) {
               _level.m_8767_(ParticleTypes.f_123813_, x, y, z, 1, 0.0, 0.0, 0.0, 0.0);
            }

            if (world instanceof ServerLevel _level) {
               _level.m_8767_(ParticleTypes.f_123797_, x, y, z, 16, 1.0, 0.0, 1.0, 0.0);
            }

            if (world instanceof ServerLevel _level) {
               _level.m_8767_(ParticleTypes.f_123777_, x, y, z, 6, 1.0, 0.2, 1.0, 0.05);
            }

            if (world instanceof ServerLevel _level) {
               _level.m_8767_(ParticleTypes.f_123783_, x, y, z, 16, 1.0, 0.0, 1.0, 0.0);
            }

            if (world instanceof Level _level) {
               if (!_level.m_5776_()) {
                  _level.m_5594_(
                     null,
                     new BlockPos(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.calcite.break")),
                     SoundSource.HOSTILE,
                     1.0F,
                     1.0F
                  );
               } else {
                  _level.m_7785_(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.calcite.break")),
                     SoundSource.HOSTILE,
                     1.0F,
                     1.0F,
                     false
                  );
               }
            }

            if (world instanceof Level _levelx) {
               if (!_levelx.m_5776_()) {
                  _levelx.m_5594_(
                     null,
                     new BlockPos(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.stone.break")),
                     SoundSource.HOSTILE,
                     1.0F,
                     1.0F
                  );
               } else {
                  _levelx.m_7785_(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.stone.break")),
                     SoundSource.HOSTILE,
                     1.0F,
                     1.0F,
                     false
                  );
               }
            }

            if (world instanceof Level _levelxx) {
               if (!_levelxx.m_5776_()) {
                  _levelxx.m_5594_(
                     null,
                     new BlockPos(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.rooted_dirt.break")),
                     SoundSource.HOSTILE,
                     1.0F,
                     1.0F
                  );
               } else {
                  _levelxx.m_7785_(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.rooted_dirt.break")),
                     SoundSource.HOSTILE,
                     1.0F,
                     1.0F,
                     false
                  );
               }
            }

            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiteratorxx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(4.5), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if (entity.m_20096_()
                  && !entityiteratorxx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:elementals")))
                  && !entityiteratorxx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))
                  && world instanceof Level) {
                  Level _levelxxx = (Level)world;
                  if (!_levelxxx.m_5776_()) {
                     _levelxxx.m_46511_(
                        null, entityiteratorxx.m_20185_(), entityiteratorxx.m_20186_(), entityiteratorxx.m_20189_(), 0.1F, BlockInteraction.NONE
                     );
                  }
               }
            }
         }
      }
   }
}
