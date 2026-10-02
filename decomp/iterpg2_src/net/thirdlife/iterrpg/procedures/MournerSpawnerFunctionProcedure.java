package net.thirdlife.iterrpg.procedures;

import com.google.common.collect.UnmodifiableIterator;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.thirdlife.iterrpg.entity.GrieverEntity;
import net.thirdlife.iterrpg.entity.MournstoneEntity;
import net.thirdlife.iterrpg.entity.RevenantEntity;
import net.thirdlife.iterrpg.entity.WeeperEntity;
import net.thirdlife.iterrpg.init.IterRpgModBlocks;
import net.thirdlife.iterrpg.init.IterRpgModEntities;
import net.thirdlife.iterrpg.init.IterRpgModGameRules;

public class MournerSpawnerFunctionProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      double mobnearby = 0.0;
      double mobspawn = 0.0;
      boolean flag = false;
      if (!world.m_6106_().m_5470_().m_46207_(IterRpgModGameRules.BUILDINGDEBUG)) {
         if (!((new Object() {
            public double getValue(LevelAccessor world, BlockPos pos, String tag) {
               BlockEntity blockEntity = world.m_7702_(pos);
               return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
            }
         }).getValue(world, new BlockPos(x, y, z), "respawnTime") >= 40.0)) {
            if ((new Object() {
               public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                  BlockEntity blockEntity = world.m_7702_(pos);
                  return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
               }
            }).getValue(world, new BlockPos(x, y, z), "wave") == 0.0) {
               if (!world.m_5776_()) {
                  BlockPos _bp = new BlockPos(x, y, z);
                  BlockEntity _blockEntity = world.m_7702_(_bp);
                  BlockState _bs = world.m_8055_(_bp);
                  if (_blockEntity != null) {
                     _blockEntity.getPersistentData().m_128347_("respawnTime", (new Object() {
                        public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                           BlockEntity blockEntity = world.m_7702_(pos);
                           return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
                        }
                     }).getValue(world, new BlockPos(x, y, z), "respawnTime") + 1.0);
                  }

                  if (world instanceof Level _level) {
                     _level.m_7260_(_bp, _bs, _bs, 3);
                  }
               }

               if (world instanceof ServerLevel _level) {
                  _level.m_8767_(ParticleTypes.f_123745_, x + 0.5, y + 0.5, z + 0.5, 1, 0.32, 0.32, 0.32, 0.0);
               }
            }
         } else {
            flag = false;
            Vec3 _center = new Vec3(x, y + 3.0, z);

            for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(3.75), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if (entityiterator instanceof Player
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
                           .checkGamemode(entityiterator)
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
                           .checkGamemode(entityiterator)
                  )) {
                  flag = true;
               }
            }

            if (flag) {
               if (world instanceof ServerLevel _level) {
                  _level.m_8767_(ParticleTypes.f_123745_, x + 0.5, y + 0.5, z + 0.5, 16, 0.5, 0.5, 0.5, 0.0);
               }

               if (world instanceof ServerLevel _level) {
                  _level.m_8767_(ParticleTypes.f_123746_, x + 0.5, y + 0.5, z + 0.5, 16, 0.5, 0.5, 0.5, 0.0);
               }

               if (!world.m_5776_()) {
                  BlockPos _bpx = new BlockPos(x, y, z);
                  BlockEntity _blockEntityx = world.m_7702_(_bpx);
                  BlockState _bsx = world.m_8055_(_bpx);
                  if (_blockEntityx != null) {
                     _blockEntityx.getPersistentData().m_128347_("respawnTime", -36000.0);
                  }

                  if (world instanceof Level _level) {
                     _level.m_7260_(_bpx, _bsx, _bsx, 3);
                  }
               }

               if (!world.m_5776_()) {
                  BlockPos _bpxx = new BlockPos(x, y, z);
                  BlockEntity _blockEntityxx = world.m_7702_(_bpxx);
                  BlockState _bsxx = world.m_8055_(_bpxx);
                  if (_blockEntityxx != null) {
                     _blockEntityxx.getPersistentData().m_128347_("wave", (double)Mth.m_216271_(RandomSource.m_216327_(), 2, 3));
                  }

                  if (world instanceof Level _level) {
                     _level.m_7260_(_bpxx, _bsxx, _bsxx, 3);
                  }
               }
            } else if (world instanceof ServerLevel _level) {
               _level.m_8767_(ParticleTypes.f_123745_, x + 0.5, y + 0.5, z + 0.5, 1, 0.32, 0.32, 0.32, 0.0);
            }
         }

         if ((new Object() {
            public double getValue(LevelAccessor world, BlockPos pos, String tag) {
               BlockEntity blockEntity = world.m_7702_(pos);
               return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
            }
         }).getValue(world, new BlockPos(x, y, z), "wave") > 0.0) {
            if (world instanceof ServerLevel _level) {
               _level.m_8767_(ParticleTypes.f_123746_, x + 0.5, y + 0.5, z + 0.5, 1, 0.32, 0.32, 0.32, 0.0);
            }

            if (world.m_8055_(new BlockPos(x, y - 1.0, z)).m_60734_() == IterRpgModBlocks.RUNIC_GRIMSTONE.get()) {
               BlockPos _bpxxx = new BlockPos(x, y - 1.0, z);
               BlockState _bsxxx = ((Block)IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get()).m_49966_();
               BlockState _bso = world.m_8055_(_bpxxx);
               UnmodifiableIterator var165 = _bso.m_61148_().entrySet().iterator();

               while (var165.hasNext()) {
                  Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var165.next();
                  Property _property = _bsxxx.m_60734_().m_49965_().m_61081_(entry.getKey().m_61708_());
                  if (_property != null && _bsxxx.m_61143_(_property) != null) {
                     try {
                        _bsxxx = (BlockState)_bsxxx.m_61124_(_property, entry.getValue());
                     } catch (Exception var23) {
                     }
                  }
               }

               world.m_7731_(_bpxxx, _bsxxx, 3);
               if (!world.m_5776_()) {
                  _bpxxx = new BlockPos(x, y - 1.0, z);
                  BlockEntity _blockEntityxxx = world.m_7702_(_bpxxx);
                  _bso = world.m_8055_(_bpxxx);
                  if (_blockEntityxxx != null) {
                     _blockEntityxxx.getPersistentData().m_128347_("demoncharge", 64.0);
                  }

                  if (world instanceof Level _level) {
                     _level.m_7260_(_bpxxx, _bso, _bso, 3);
                  }
               }
            }

            if ((
                  world.m_8055_(new BlockPos(x, y - 1.0, z)).m_60734_() == IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get()
                     || world.m_8055_(new BlockPos(x, y - 1.0, z)).m_60734_() == IterRpgModBlocks.RUNIC_GRIMSTONE.get()
               )
               && !world.m_5776_()) {
               BlockPos _bpxxx = new BlockPos(x, y - 1.0, z);
               BlockEntity _blockEntityxxxx = world.m_7702_(_bpxxx);
               BlockState _bsxxx = world.m_8055_(_bpxxx);
               if (_blockEntityxxxx != null) {
                  _blockEntityxxxx.getPersistentData().m_128347_("demoncharge", 64.0);
               }

               if (world instanceof Level _level) {
                  _level.m_7260_(_bpxxx, _bsxxx, _bsxxx, 3);
               }
            }

            if (world.m_8055_(new BlockPos(x + 1.0, y, z)).m_60734_() == IterRpgModBlocks.RUNIC_GRIMSTONE.get()) {
               BlockPos _bpxxxx = new BlockPos(x + 1.0, y, z);
               BlockState _bsxxxx = ((Block)IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get()).m_49966_();
               BlockState _bso = world.m_8055_(_bpxxxx);
               UnmodifiableIterator var168 = _bso.m_61148_().entrySet().iterator();

               while (var168.hasNext()) {
                  Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var168.next();
                  Property _property = _bsxxxx.m_60734_().m_49965_().m_61081_(entry.getKey().m_61708_());
                  if (_property != null && _bsxxxx.m_61143_(_property) != null) {
                     try {
                        _bsxxxx = (BlockState)_bsxxxx.m_61124_(_property, entry.getValue());
                     } catch (Exception var22) {
                     }
                  }
               }

               world.m_7731_(_bpxxxx, _bsxxxx, 3);
               if (!world.m_5776_()) {
                  _bpxxxx = new BlockPos(x + 1.0, y, z);
                  BlockEntity _blockEntityxxxxx = world.m_7702_(_bpxxxx);
                  _bso = world.m_8055_(_bpxxxx);
                  if (_blockEntityxxxxx != null) {
                     _blockEntityxxxxx.getPersistentData().m_128347_("demoncharge", 64.0);
                  }

                  if (world instanceof Level _level) {
                     _level.m_7260_(_bpxxxx, _bso, _bso, 3);
                  }
               }
            }

            if ((
                  world.m_8055_(new BlockPos(x + 1.0, y, z)).m_60734_() == IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get()
                     || world.m_8055_(new BlockPos(x + 1.0, y, z)).m_60734_() == IterRpgModBlocks.RUNIC_GRIMSTONE.get()
               )
               && !world.m_5776_()) {
               BlockPos _bpxxxx = new BlockPos(x + 1.0, y, z);
               BlockEntity _blockEntityxxxxxx = world.m_7702_(_bpxxxx);
               BlockState _bsxxxx = world.m_8055_(_bpxxxx);
               if (_blockEntityxxxxxx != null) {
                  _blockEntityxxxxxx.getPersistentData().m_128347_("demoncharge", 64.0);
               }

               if (world instanceof Level _level) {
                  _level.m_7260_(_bpxxxx, _bsxxxx, _bsxxxx, 3);
               }
            }

            if (world.m_8055_(new BlockPos(x - 1.0, y, z)).m_60734_() == IterRpgModBlocks.RUNIC_GRIMSTONE.get()) {
               BlockPos _bpxxxxx = new BlockPos(x - 1.0, y, z);
               BlockState _bsxxxxx = ((Block)IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get()).m_49966_();
               BlockState _bso = world.m_8055_(_bpxxxxx);
               UnmodifiableIterator var171 = _bso.m_61148_().entrySet().iterator();

               while (var171.hasNext()) {
                  Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var171.next();
                  Property _property = _bsxxxxx.m_60734_().m_49965_().m_61081_(entry.getKey().m_61708_());
                  if (_property != null && _bsxxxxx.m_61143_(_property) != null) {
                     try {
                        _bsxxxxx = (BlockState)_bsxxxxx.m_61124_(_property, entry.getValue());
                     } catch (Exception var21) {
                     }
                  }
               }

               world.m_7731_(_bpxxxxx, _bsxxxxx, 3);
               if (!world.m_5776_()) {
                  _bpxxxxx = new BlockPos(x - 1.0, y, z);
                  BlockEntity _blockEntityxxxxxxx = world.m_7702_(_bpxxxxx);
                  _bso = world.m_8055_(_bpxxxxx);
                  if (_blockEntityxxxxxxx != null) {
                     _blockEntityxxxxxxx.getPersistentData().m_128347_("demoncharge", 64.0);
                  }

                  if (world instanceof Level _level) {
                     _level.m_7260_(_bpxxxxx, _bso, _bso, 3);
                  }
               }
            }

            if ((
                  world.m_8055_(new BlockPos(x - 1.0, y, z)).m_60734_() == IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get()
                     || world.m_8055_(new BlockPos(x - 1.0, y, z)).m_60734_() == IterRpgModBlocks.RUNIC_GRIMSTONE.get()
               )
               && !world.m_5776_()) {
               BlockPos _bpxxxxx = new BlockPos(x - 1.0, y, z);
               BlockEntity _blockEntityxxxxxxxx = world.m_7702_(_bpxxxxx);
               BlockState _bsxxxxx = world.m_8055_(_bpxxxxx);
               if (_blockEntityxxxxxxxx != null) {
                  _blockEntityxxxxxxxx.getPersistentData().m_128347_("demoncharge", 64.0);
               }

               if (world instanceof Level _level) {
                  _level.m_7260_(_bpxxxxx, _bsxxxxx, _bsxxxxx, 3);
               }
            }

            if (world.m_8055_(new BlockPos(x, y, z + 1.0)).m_60734_() == IterRpgModBlocks.RUNIC_GRIMSTONE.get()) {
               BlockPos _bpxxxxxx = new BlockPos(x, y, z + 1.0);
               BlockState _bsxxxxxx = ((Block)IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get()).m_49966_();
               BlockState _bso = world.m_8055_(_bpxxxxxx);
               UnmodifiableIterator var174 = _bso.m_61148_().entrySet().iterator();

               while (var174.hasNext()) {
                  Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var174.next();
                  Property _property = _bsxxxxxx.m_60734_().m_49965_().m_61081_(entry.getKey().m_61708_());
                  if (_property != null && _bsxxxxxx.m_61143_(_property) != null) {
                     try {
                        _bsxxxxxx = (BlockState)_bsxxxxxx.m_61124_(_property, entry.getValue());
                     } catch (Exception var20) {
                     }
                  }
               }

               world.m_7731_(_bpxxxxxx, _bsxxxxxx, 3);
               if (!world.m_5776_()) {
                  _bpxxxxxx = new BlockPos(x, y, z + 1.0);
                  BlockEntity _blockEntityxxxxxxxxx = world.m_7702_(_bpxxxxxx);
                  _bso = world.m_8055_(_bpxxxxxx);
                  if (_blockEntityxxxxxxxxx != null) {
                     _blockEntityxxxxxxxxx.getPersistentData().m_128347_("demoncharge", 64.0);
                  }

                  if (world instanceof Level _level) {
                     _level.m_7260_(_bpxxxxxx, _bso, _bso, 3);
                  }
               }
            }

            if ((
                  world.m_8055_(new BlockPos(x, y, z + 1.0)).m_60734_() == IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get()
                     || world.m_8055_(new BlockPos(x, y, z + 1.0)).m_60734_() == IterRpgModBlocks.RUNIC_GRIMSTONE.get()
               )
               && !world.m_5776_()) {
               BlockPos _bpxxxxxx = new BlockPos(x, y, z + 1.0);
               BlockEntity _blockEntityxxxxxxxxxx = world.m_7702_(_bpxxxxxx);
               BlockState _bsxxxxxx = world.m_8055_(_bpxxxxxx);
               if (_blockEntityxxxxxxxxxx != null) {
                  _blockEntityxxxxxxxxxx.getPersistentData().m_128347_("demoncharge", 64.0);
               }

               if (world instanceof Level _level) {
                  _level.m_7260_(_bpxxxxxx, _bsxxxxxx, _bsxxxxxx, 3);
               }
            }

            if (world.m_8055_(new BlockPos(x, y, z - 1.0)).m_60734_() == IterRpgModBlocks.RUNIC_GRIMSTONE.get()) {
               BlockPos _bpxxxxxxx = new BlockPos(x, y, z - 1.0);
               BlockState _bsxxxxxxx = ((Block)IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get()).m_49966_();
               BlockState _bso = world.m_8055_(_bpxxxxxxx);
               UnmodifiableIterator var177 = _bso.m_61148_().entrySet().iterator();

               while (var177.hasNext()) {
                  Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var177.next();
                  Property _property = _bsxxxxxxx.m_60734_().m_49965_().m_61081_(entry.getKey().m_61708_());
                  if (_property != null && _bsxxxxxxx.m_61143_(_property) != null) {
                     try {
                        _bsxxxxxxx = (BlockState)_bsxxxxxxx.m_61124_(_property, entry.getValue());
                     } catch (Exception var19) {
                     }
                  }
               }

               world.m_7731_(_bpxxxxxxx, _bsxxxxxxx, 3);
               if (!world.m_5776_()) {
                  _bpxxxxxxx = new BlockPos(x, y, z - 1.0);
                  BlockEntity _blockEntityxxxxxxxxxxx = world.m_7702_(_bpxxxxxxx);
                  _bso = world.m_8055_(_bpxxxxxxx);
                  if (_blockEntityxxxxxxxxxxx != null) {
                     _blockEntityxxxxxxxxxxx.getPersistentData().m_128347_("demoncharge", 64.0);
                  }

                  if (world instanceof Level _level) {
                     _level.m_7260_(_bpxxxxxxx, _bso, _bso, 3);
                  }
               }
            }

            if ((
                  world.m_8055_(new BlockPos(x, y, z - 1.0)).m_60734_() == IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get()
                     || world.m_8055_(new BlockPos(x, y, z - 1.0)).m_60734_() == IterRpgModBlocks.RUNIC_GRIMSTONE.get()
               )
               && !world.m_5776_()) {
               BlockPos _bpxxxxxxx = new BlockPos(x, y, z - 1.0);
               BlockEntity _blockEntityxxxxxxxxxxxx = world.m_7702_(_bpxxxxxxx);
               BlockState _bsxxxxxxx = world.m_8055_(_bpxxxxxxx);
               if (_blockEntityxxxxxxxxxxxx != null) {
                  _blockEntityxxxxxxxxxxxx.getPersistentData().m_128347_("demoncharge", 64.0);
               }

               if (world instanceof Level _level) {
                  _level.m_7260_(_bpxxxxxxx, _bsxxxxxxx, _bsxxxxxxx, 3);
               }
            }

            mobnearby = 0.0;
            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiteratorx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(5.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if (entityiteratorx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:mourners")))
                  || entityiteratorx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:demons")))
                  || entityiteratorx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("minecraft:skeletons")))) {
                  mobnearby++;
               }
            }

            if (mobnearby < 2.0) {
               if ((new Object() {
                  public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                     BlockEntity blockEntity = world.m_7702_(pos);
                     return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
                  }
               }).getValue(world, new BlockPos(x, y, z), "cooldown") == 0.0) {
                  if (world instanceof ServerLevel _level) {
                     _level.m_8767_(ParticleTypes.f_123745_, x + 0.5, y + 0.5, z + 0.5, 16, 0.5, 0.5, 0.5, 0.0);
                  }

                  if (!world.m_5776_()) {
                     BlockPos _bpxxxxxxxx = new BlockPos(x, y, z);
                     BlockEntity _blockEntityxxxxxxxxxxxxx = world.m_7702_(_bpxxxxxxxx);
                     BlockState _bsxxxxxxxx = world.m_8055_(_bpxxxxxxxx);
                     if (_blockEntityxxxxxxxxxxxxx != null) {
                        _blockEntityxxxxxxxxxxxxx.getPersistentData().m_128347_("cooldown", (double)Mth.m_216271_(RandomSource.m_216327_(), 25, 30));
                     }

                     if (world instanceof Level _level) {
                        _level.m_7260_(_bpxxxxxxxx, _bsxxxxxxxx, _bsxxxxxxxx, 3);
                     }
                  }

                  if (!world.m_5776_()) {
                     BlockPos _bpxxxxxxxxx = new BlockPos(x, y, z);
                     BlockEntity _blockEntityxxxxxxxxxxxxxx = world.m_7702_(_bpxxxxxxxxx);
                     BlockState _bsxxxxxxxxx = world.m_8055_(_bpxxxxxxxxx);
                     if (_blockEntityxxxxxxxxxxxxxx != null) {
                        _blockEntityxxxxxxxxxxxxxx.getPersistentData().m_128347_("wave", (new Object() {
                           public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                              BlockEntity blockEntity = world.m_7702_(pos);
                              return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
                           }
                        }).getValue(world, new BlockPos(x, y, z), "wave") - 1.0);
                     }

                     if (world instanceof Level _level) {
                        _level.m_7260_(_bpxxxxxxxxx, _bsxxxxxxxxx, _bsxxxxxxxxx, 3);
                     }
                  }

                  mobspawn = (double)Mth.m_216271_(RandomSource.m_216327_(), 1, 6);
                  if (mobspawn == 1.0) {
                     for (int index0 = 0; index0 < 2; index0++) {
                        if (world instanceof ServerLevel) {
                           ServerLevel _level = (ServerLevel)world;
                           Entity entityToSpawn = new GrieverEntity((EntityType<GrieverEntity>)IterRpgModEntities.GRIEVER.get(), _level);
                           entityToSpawn.m_7678_(x + 0.5, y + 1.0, z + 0.5, 0.0F, 0.0F);
                           entityToSpawn.m_5618_(0.0F);
                           entityToSpawn.m_5616_(0.0F);
                           entityToSpawn.m_20334_(Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2), 0.2, Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2));
                           if (entityToSpawn instanceof Mob _mobToSpawn) {
                              _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                           }

                           world.m_7967_(entityToSpawn);
                        }
                     }

                     if (world instanceof ServerLevel _level) {
                        Entity entityToSpawn = new MournstoneEntity((EntityType<MournstoneEntity>)IterRpgModEntities.MOURNSTONE.get(), _level);
                        entityToSpawn.m_7678_(x + 0.5, y + 1.0, z + 0.5, 0.0F, 0.0F);
                        entityToSpawn.m_5618_(0.0F);
                        entityToSpawn.m_5616_(0.0F);
                        entityToSpawn.m_20334_(Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2), 0.2, Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2));
                        if (entityToSpawn instanceof Mob _mobToSpawn) {
                           _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                        }

                        world.m_7967_(entityToSpawn);
                     }

                     if (world instanceof ServerLevel _level) {
                        Entity entityToSpawn = new WeeperEntity((EntityType<WeeperEntity>)IterRpgModEntities.WEEPER.get(), _level);
                        entityToSpawn.m_7678_(x + 0.5, y + 1.0, z + 0.5, 0.0F, 0.0F);
                        entityToSpawn.m_5618_(0.0F);
                        entityToSpawn.m_5616_(0.0F);
                        entityToSpawn.m_20334_(Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2), 0.2, Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2));
                        if (entityToSpawn instanceof Mob _mobToSpawn) {
                           _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                        }

                        world.m_7967_(entityToSpawn);
                     }

                     if (world instanceof ServerLevel _level) {
                        Entity entityToSpawn = new RevenantEntity((EntityType<RevenantEntity>)IterRpgModEntities.REVENANT.get(), _level);
                        entityToSpawn.m_7678_(x + 0.5, y + 1.0, z + 0.5, 0.0F, 0.0F);
                        entityToSpawn.m_5618_(0.0F);
                        entityToSpawn.m_5616_(0.0F);
                        entityToSpawn.m_20334_(Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2), 0.2, Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2));
                        if (entityToSpawn instanceof Mob _mobToSpawn) {
                           _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                        }

                        world.m_7967_(entityToSpawn);
                     }
                  } else if (mobspawn == 2.0) {
                     for (int index1 = 0; index1 < 2; index1++) {
                        if (world instanceof ServerLevel) {
                           ServerLevel _level = (ServerLevel)world;
                           Entity entityToSpawn = new Skeleton(EntityType.f_20524_, _level);
                           entityToSpawn.m_7678_(x + 0.5, y + 1.0, z + 0.5, 0.0F, 0.0F);
                           entityToSpawn.m_5618_(0.0F);
                           entityToSpawn.m_5616_(0.0F);
                           entityToSpawn.m_20334_(Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2), 0.2, Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2));
                           if (entityToSpawn instanceof Mob _mobToSpawn) {
                              _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                           }

                           world.m_7967_(entityToSpawn);
                        }
                     }

                     for (int index2 = 0; index2 < 2; index2++) {
                        if (world instanceof ServerLevel) {
                           ServerLevel _level = (ServerLevel)world;
                           Entity entityToSpawn = new WitherSkeleton(EntityType.f_20497_, _level);
                           entityToSpawn.m_7678_(x + 0.5, y + 1.0, z + 0.5, 0.0F, 0.0F);
                           entityToSpawn.m_5618_(0.0F);
                           entityToSpawn.m_5616_(0.0F);
                           entityToSpawn.m_20334_(Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2), 0.2, Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2));
                           if (entityToSpawn instanceof Mob _mobToSpawn) {
                              _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                           }

                           world.m_7967_(entityToSpawn);
                        }
                     }

                     if (world instanceof ServerLevel _level) {
                        Entity entityToSpawn = new RevenantEntity((EntityType<RevenantEntity>)IterRpgModEntities.REVENANT.get(), _level);
                        entityToSpawn.m_7678_(x + 0.5, y + 1.0, z + 0.5, 0.0F, 0.0F);
                        entityToSpawn.m_5618_(0.0F);
                        entityToSpawn.m_5616_(0.0F);
                        entityToSpawn.m_20334_(Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2), 0.2, Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2));
                        if (entityToSpawn instanceof Mob _mobToSpawn) {
                           _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                        }

                        world.m_7967_(entityToSpawn);
                     }
                  } else if (mobspawn == 3.0) {
                     for (int index3 = 0; index3 < 2; index3++) {
                        if (world instanceof ServerLevel) {
                           ServerLevel _level = (ServerLevel)world;
                           Entity entityToSpawn = new MournstoneEntity((EntityType<MournstoneEntity>)IterRpgModEntities.MOURNSTONE.get(), _level);
                           entityToSpawn.m_7678_(x + 0.5, y + 1.0, z + 0.5, 0.0F, 0.0F);
                           entityToSpawn.m_5618_(0.0F);
                           entityToSpawn.m_5616_(0.0F);
                           entityToSpawn.m_20334_(Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2), 0.2, Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2));
                           if (entityToSpawn instanceof Mob _mobToSpawn) {
                              _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                           }

                           world.m_7967_(entityToSpawn);
                        }
                     }

                     for (int index4 = 0; index4 < 2; index4++) {
                        if (world instanceof ServerLevel) {
                           ServerLevel _level = (ServerLevel)world;
                           Entity entityToSpawn = new WitherSkeleton(EntityType.f_20497_, _level);
                           entityToSpawn.m_7678_(x + 0.5, y + 1.0, z + 0.5, 0.0F, 0.0F);
                           entityToSpawn.m_5618_(0.0F);
                           entityToSpawn.m_5616_(0.0F);
                           entityToSpawn.m_20334_(Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2), 0.2, Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2));
                           if (entityToSpawn instanceof Mob _mobToSpawn) {
                              _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                           }

                           world.m_7967_(entityToSpawn);
                        }
                     }

                     if (world instanceof ServerLevel _level) {
                        Entity entityToSpawn = new GrieverEntity((EntityType<GrieverEntity>)IterRpgModEntities.GRIEVER.get(), _level);
                        entityToSpawn.m_7678_(x + 0.5, y + 1.0, z + 0.5, 0.0F, 0.0F);
                        entityToSpawn.m_5618_(0.0F);
                        entityToSpawn.m_5616_(0.0F);
                        entityToSpawn.m_20334_(Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2), 0.2, Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2));
                        if (entityToSpawn instanceof Mob _mobToSpawn) {
                           _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                        }

                        world.m_7967_(entityToSpawn);
                     }
                  } else if (mobspawn == 4.0) {
                     for (int index5 = 0; index5 < 2; index5++) {
                        if (world instanceof ServerLevel) {
                           ServerLevel _level = (ServerLevel)world;
                           Entity entityToSpawn = new Skeleton(EntityType.f_20524_, _level);
                           entityToSpawn.m_7678_(x + 0.5, y + 1.0, z + 0.5, 0.0F, 0.0F);
                           entityToSpawn.m_5618_(0.0F);
                           entityToSpawn.m_5616_(0.0F);
                           entityToSpawn.m_20334_(Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2), 0.2, Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2));
                           if (entityToSpawn instanceof Mob _mobToSpawn) {
                              _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                           }

                           world.m_7967_(entityToSpawn);
                        }
                     }

                     for (int index6 = 0; index6 < 2; index6++) {
                        if (world instanceof ServerLevel) {
                           ServerLevel _level = (ServerLevel)world;
                           Entity entityToSpawn = new WeeperEntity((EntityType<WeeperEntity>)IterRpgModEntities.WEEPER.get(), _level);
                           entityToSpawn.m_7678_(x + 0.5, y + 1.0, z + 0.5, 0.0F, 0.0F);
                           entityToSpawn.m_5618_(0.0F);
                           entityToSpawn.m_5616_(0.0F);
                           entityToSpawn.m_20334_(Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2), 0.2, Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2));
                           if (entityToSpawn instanceof Mob _mobToSpawn) {
                              _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                           }

                           world.m_7967_(entityToSpawn);
                        }
                     }

                     if (world instanceof ServerLevel _level) {
                        Entity entityToSpawn = new MournstoneEntity((EntityType<MournstoneEntity>)IterRpgModEntities.MOURNSTONE.get(), _level);
                        entityToSpawn.m_7678_(x + 0.5, y + 1.0, z + 0.5, 0.0F, 0.0F);
                        entityToSpawn.m_5618_(0.0F);
                        entityToSpawn.m_5616_(0.0F);
                        entityToSpawn.m_20334_(Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2), 0.2, Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2));
                        if (entityToSpawn instanceof Mob _mobToSpawn) {
                           _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                        }

                        world.m_7967_(entityToSpawn);
                     }

                     if (world instanceof ServerLevel _level) {
                        Entity entityToSpawn = new GrieverEntity((EntityType<GrieverEntity>)IterRpgModEntities.GRIEVER.get(), _level);
                        entityToSpawn.m_7678_(x + 0.5, y + 1.0, z + 0.5, 0.0F, 0.0F);
                        entityToSpawn.m_5618_(0.0F);
                        entityToSpawn.m_5616_(0.0F);
                        entityToSpawn.m_20334_(Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2), 0.2, Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2));
                        if (entityToSpawn instanceof Mob _mobToSpawn) {
                           _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                        }

                        world.m_7967_(entityToSpawn);
                     }
                  } else if (mobspawn == 5.0) {
                     for (int index7 = 0; index7 < 2; index7++) {
                        if (world instanceof ServerLevel) {
                           ServerLevel _level = (ServerLevel)world;
                           Entity entityToSpawn = new MournstoneEntity((EntityType<MournstoneEntity>)IterRpgModEntities.MOURNSTONE.get(), _level);
                           entityToSpawn.m_7678_(x + 0.5, y + 1.0, z + 0.5, 0.0F, 0.0F);
                           entityToSpawn.m_5618_(0.0F);
                           entityToSpawn.m_5616_(0.0F);
                           entityToSpawn.m_20334_(Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2), 0.2, Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2));
                           if (entityToSpawn instanceof Mob _mobToSpawn) {
                              _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                           }

                           world.m_7967_(entityToSpawn);
                        }
                     }

                     for (int index8 = 0; index8 < 2; index8++) {
                        if (world instanceof ServerLevel) {
                           ServerLevel _level = (ServerLevel)world;
                           Entity entityToSpawn = new GrieverEntity((EntityType<GrieverEntity>)IterRpgModEntities.GRIEVER.get(), _level);
                           entityToSpawn.m_7678_(x + 0.5, y + 1.0, z + 0.5, 0.0F, 0.0F);
                           entityToSpawn.m_5618_(0.0F);
                           entityToSpawn.m_5616_(0.0F);
                           entityToSpawn.m_20334_(Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2), 0.2, Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2));
                           if (entityToSpawn instanceof Mob _mobToSpawn) {
                              _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                           }

                           world.m_7967_(entityToSpawn);
                        }
                     }
                  } else if (mobspawn == 6.0) {
                     if (world instanceof ServerLevel _level) {
                        Entity entityToSpawn = new GrieverEntity((EntityType<GrieverEntity>)IterRpgModEntities.GRIEVER.get(), _level);
                        entityToSpawn.m_7678_(x + 0.5, y + 1.0, z + 0.5, 0.0F, 0.0F);
                        entityToSpawn.m_5618_(0.0F);
                        entityToSpawn.m_5616_(0.0F);
                        entityToSpawn.m_20334_(Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2), 0.2, Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2));
                        if (entityToSpawn instanceof Mob _mobToSpawn) {
                           _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                        }

                        world.m_7967_(entityToSpawn);
                     }

                     if (world instanceof ServerLevel _level) {
                        Entity entityToSpawn = new MournstoneEntity((EntityType<MournstoneEntity>)IterRpgModEntities.MOURNSTONE.get(), _level);
                        entityToSpawn.m_7678_(x + 0.5, y + 1.0, z + 0.5, 0.0F, 0.0F);
                        entityToSpawn.m_5618_(0.0F);
                        entityToSpawn.m_5616_(0.0F);
                        entityToSpawn.m_20334_(Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2), 0.2, Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2));
                        if (entityToSpawn instanceof Mob _mobToSpawn) {
                           _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                        }

                        world.m_7967_(entityToSpawn);
                     }

                     if (world instanceof ServerLevel _level) {
                        Entity entityToSpawn = new WeeperEntity((EntityType<WeeperEntity>)IterRpgModEntities.WEEPER.get(), _level);
                        entityToSpawn.m_7678_(x + 0.5, y + 1.0, z + 0.5, 0.0F, 0.0F);
                        entityToSpawn.m_5618_(0.0F);
                        entityToSpawn.m_5616_(0.0F);
                        entityToSpawn.m_20334_(Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2), 0.2, Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2));
                        if (entityToSpawn instanceof Mob _mobToSpawn) {
                           _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                        }

                        world.m_7967_(entityToSpawn);
                     }

                     if (world instanceof ServerLevel _level) {
                        Entity entityToSpawn = new Skeleton(EntityType.f_20524_, _level);
                        entityToSpawn.m_7678_(x + 0.5, y + 1.0, z + 0.5, 0.0F, 0.0F);
                        entityToSpawn.m_5618_(0.0F);
                        entityToSpawn.m_5616_(0.0F);
                        entityToSpawn.m_20334_(Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2), 0.2, Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2));
                        if (entityToSpawn instanceof Mob _mobToSpawn) {
                           _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                        }

                        world.m_7967_(entityToSpawn);
                     }

                     if (world instanceof ServerLevel _level) {
                        Entity entityToSpawn = new WitherSkeleton(EntityType.f_20497_, _level);
                        entityToSpawn.m_7678_(x + 0.5, y + 1.0, z + 0.5, 0.0F, 0.0F);
                        entityToSpawn.m_5618_(0.0F);
                        entityToSpawn.m_5616_(0.0F);
                        entityToSpawn.m_20334_(Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2), 0.2, Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2));
                        if (entityToSpawn instanceof Mob _mobToSpawn) {
                           _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                        }

                        world.m_7967_(entityToSpawn);
                     }
                  }
               } else if (!world.m_5776_()) {
                  BlockPos _bpxxxxxxxxxx = new BlockPos(x, y, z);
                  BlockEntity _blockEntityxxxxxxxxxxxxxxx = world.m_7702_(_bpxxxxxxxxxx);
                  BlockState _bsxxxxxxxxxx = world.m_8055_(_bpxxxxxxxxxx);
                  if (_blockEntityxxxxxxxxxxxxxxx != null) {
                     _blockEntityxxxxxxxxxxxxxxx.getPersistentData().m_128347_("cooldown", (new Object() {
                        public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                           BlockEntity blockEntity = world.m_7702_(pos);
                           return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
                        }
                     }).getValue(world, new BlockPos(x, y, z), "cooldown") - 1.0);
                  }

                  if (world instanceof Level _level) {
                     _level.m_7260_(_bpxxxxxxxxxx, _bsxxxxxxxxxx, _bsxxxxxxxxxx, 3);
                  }
               }
            }
         }
      }
   }
}
