package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.network.IterRpgModVariables;

public class ThunderclapUsedProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
      if (entity != null) {
         double distance = 0.0;
         double damage = 0.0;
         double directionx = 0.0;
         double directiony = 0.0;
         double directionz = 0.0;
         boolean hit = false;
         boolean particle = false;
         if ((new Object() {
                  public boolean checkGamemode(Entity _ent) {
                     if (_ent instanceof ServerPlayer _serverPlayer) {
                        return _serverPlayer.f_8941_.m_9290_() == GameType.CREATIVE;
                     } else {
                        return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                           ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                              && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.CREATIVE
                           : false;
                     }
                  }
               })
               .checkGamemode(entity)
            || ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                     .orElse(new IterRpgModVariables.PlayerVariables()))
                  .Mana
               >= 8.0) {
            distance = 0.0;
            directionx = entity.m_20154_().f_82479_;
            directiony = entity.m_20154_().f_82480_;
            directionz = entity.m_20154_().f_82481_;
            if (entity instanceof Player _player) {
               _player.m_36335_().m_41524_(itemstack.m_41720_(), 24);
            }

            double _setval = ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                     .orElse(new IterRpgModVariables.PlayerVariables()))
                  .Mana
               - 8.0;
            entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
               capability.Mana = _setval;
               capability.syncPlayerVariables(entity);
            });
            if (((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                     .orElse(new IterRpgModVariables.PlayerVariables()))
                  .MagicCooldown
               < 24.0) {
               _setval = 24.0;
               entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                  capability.MagicCooldown = _setval;
                  capability.syncPlayerVariables(entity);
               });
            }

            damage = 6.0;
            if (world instanceof Level _level) {
               if (!_level.m_5776_()) {
                  _level.m_5594_(
                     null,
                     new BlockPos(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.lightning_bolt.thunder")),
                     SoundSource.PLAYERS,
                     0.75F,
                     (float)Mth.m_216263_(RandomSource.m_216327_(), 2.0, 3.0)
                  );
               } else {
                  _level.m_7785_(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.lightning_bolt.thunder")),
                     SoundSource.PLAYERS,
                     0.75F,
                     (float)Mth.m_216263_(RandomSource.m_216327_(), 2.0, 3.0),
                     false
                  );
               }
            }

            hit = true;

            for (int index0 = 0; index0 < 160; index0++) {
               if (hit) {
                  if (world instanceof ServerLevel _levelx) {
                     _levelx.m_8767_(
                        ParticleTypes.f_123810_,
                        x + directionx * distance,
                        y + directiony * distance + 1.25,
                        z + directionz * distance,
                        2,
                        0.032,
                        0.032,
                        0.032,
                        0.0
                     );
                  }

                  if (world instanceof ServerLevel _levelx) {
                     _levelx.m_8767_(
                        ParticleTypes.f_175831_,
                        x + directionx * distance,
                        y + directiony * distance + 1.25,
                        z + directionz * distance,
                        1,
                        0.032,
                        0.032,
                        0.032,
                        0.0
                     );
                  }

                  Vec3 _center = new Vec3(x + directionx * distance, y + directiony * distance + 1.0, z + directionz * distance);

                  for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(1.25), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                     .collect(Collectors.toList())) {
                     if ((!(entityiterator instanceof TamableAnimal _tamEnt) || !_tamEnt.m_21824_())
                        && !entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))
                        && entity != entityiterator) {
                        entityiterator.m_6469_(new EntityDamageSource("generic.player", entity), (float)damage);
                        particle = true;
                     }
                  }

                  if (world.m_8055_(new BlockPos(x + directionx * distance, y + directiony * distance + 1.0, z + directionz * distance)).m_60815_()
                     && !world.m_8055_(new BlockPos(x + directionx * distance, y + directiony * distance + 2.0, z + directionz * distance))
                        .m_204336_(BlockTags.create(new ResourceLocation("forge:iter_projectile_passable")))) {
                     _center = new Vec3(x + directionx * distance, y + directiony * distance + 1.0, z + directionz * distance);

                     for (Entity entityiteratorx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(2.5), e -> true)
                        .stream()
                        .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                        .collect(Collectors.toList())) {
                        if ((!(entityiteratorx instanceof TamableAnimal _tamEnt) || !_tamEnt.m_21824_())
                           && !entityiteratorx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))
                           && entity != entityiteratorx) {
                           entityiteratorx.m_6469_(new EntityDamageSource("generic.player", entity), (float)damage);
                           if (world instanceof ServerLevel _levelx) {
                              _levelx.m_8767_(
                                 ParticleTypes.f_175830_,
                                 x + directionx * distance,
                                 y + directiony * distance + 1.0,
                                 z + directionz * distance,
                                 16,
                                 0.08,
                                 0.08,
                                 0.08,
                                 0.08
                              );
                           }
                        }
                     }

                     hit = false;
                     particle = true;
                  }

                  if (particle) {
                     if (world instanceof ServerLevel _levelx) {
                        _levelx.m_8767_(
                           ParticleTypes.f_175830_,
                           x + directionx * distance,
                           y + directiony * distance + 1.0,
                           z + directionz * distance,
                           16,
                           0.16,
                           0.16,
                           0.16,
                           0.064
                        );
                     }

                     if (world instanceof ServerLevel _levelx) {
                        _levelx.m_8767_(
                           ParticleTypes.f_175829_,
                           x + directionx * distance,
                           y + directiony * distance + 1.0,
                           z + directionz * distance,
                           8,
                           0.16,
                           0.16,
                           0.16,
                           0.064
                        );
                     }

                     particle = false;
                  }

                  distance += 0.16;
                  directionx += Mth.m_216263_(RandomSource.m_216327_(), -0.032, 0.032);
                  directiony += Mth.m_216263_(RandomSource.m_216327_(), -0.032, 0.032);
                  directionz += Mth.m_216263_(RandomSource.m_216327_(), -0.032, 0.032);
               }
            }

            for (int index1 = 0; index1 < 4; index1++) {
               hit = true;
               distance = 0.0;
               damage = 3.0;
               directionx = entity.m_20154_().f_82479_;
               directiony = entity.m_20154_().f_82480_;
               directionz = entity.m_20154_().f_82481_;

               for (int index2 = 0; index2 < 64; index2++) {
                  if (hit) {
                     if (world instanceof ServerLevel _levelx) {
                        _levelx.m_8767_(
                           ParticleTypes.f_123810_,
                           x + directionx * distance,
                           y + directiony * distance + 1.25,
                           z + directionz * distance,
                           1,
                           0.0,
                           0.0,
                           0.0,
                           0.0
                        );
                     }

                     Vec3 _center = new Vec3(x + directionx * distance, y + directiony * distance + 1.0, z + directionz * distance);

                     for (Entity entityiteratorxx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(1.0), e -> true)
                        .stream()
                        .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                        .collect(Collectors.toList())) {
                        if ((!(entityiteratorxx instanceof TamableAnimal _tamEntx) || !_tamEntx.m_21824_())
                           && !entityiteratorxx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))
                           && entity != entityiteratorxx) {
                           entityiteratorxx.m_6469_(new EntityDamageSource("generic.player", entity), (float)damage);
                           particle = true;
                        }
                     }

                     if (world.m_8055_(new BlockPos(x + directionx * distance, y + directiony * distance + 1.0, z + directionz * distance)).m_60815_()
                        && !world.m_8055_(new BlockPos(x + directionx * distance, y + directiony * distance + 2.0, z + directionz * distance))
                           .m_204336_(BlockTags.create(new ResourceLocation("forge:iter_projectile_passable")))) {
                        _center = new Vec3(x + directionx * distance, y + directiony * distance + 1.0, z + directionz * distance);

                        for (Entity entityiteratorxxx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(2.0), e -> true)
                           .stream()
                           .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                           .collect(Collectors.toList())) {
                           if ((!(entityiteratorxxx instanceof TamableAnimal _tamEntx) || !_tamEntx.m_21824_())
                              && !entityiteratorxxx.m_6095_()
                                 .m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))
                              && entity != entityiteratorxxx) {
                              entityiteratorxxx.m_6469_(new EntityDamageSource("generic.player", entity), (float)damage);
                              particle = true;
                           }
                        }

                        hit = false;
                        particle = true;
                     }

                     if (particle) {
                        if (world instanceof ServerLevel _levelx) {
                           _levelx.m_8767_(
                              ParticleTypes.f_175830_,
                              x + directionx * distance,
                              y + directiony * distance + 1.0,
                              z + directionz * distance,
                              16,
                              0.16,
                              0.16,
                              0.16,
                              0.16
                           );
                        }

                        particle = false;
                     }

                     distance += 0.64;
                     directionx += Mth.m_216263_(RandomSource.m_216327_(), -0.032, 0.032);
                     directiony += Mth.m_216263_(RandomSource.m_216327_(), -0.032, 0.032);
                     directionz += Mth.m_216263_(RandomSource.m_216327_(), -0.032, 0.032);
                  }
               }
            }

            if (!(new Object() {
                     public boolean checkGamemode(Entity _ent) {
                        if (_ent instanceof ServerPlayer _serverPlayer) {
                           return _serverPlayer.f_8941_.m_9290_() == GameType.CREATIVE;
                        } else {
                           return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                              ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                                 && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.CREATIVE
                              : false;
                        }
                     }
                  })
                  .checkGamemode(entity)
               && itemstack.m_220157_(1, RandomSource.m_216327_(), null)) {
               itemstack.m_41774_(1);
               itemstack.m_41721_(0);
            }
         }
      }
   }
}
