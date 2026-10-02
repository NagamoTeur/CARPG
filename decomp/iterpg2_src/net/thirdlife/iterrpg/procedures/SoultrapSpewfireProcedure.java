package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.init.IterRpgModGameRules;

public class SoultrapSpewfireProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      double distance = 0.0;
      boolean flag = false;
      boolean wallmeet = false;
      if (!world.m_6106_().m_5470_().m_46207_(IterRpgModGameRules.BUILDINGDEBUG)) {
         flag = false;
         Vec3 _center = new Vec3(x, y, z);

         for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(8.0), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
            .collect(Collectors.toList())) {
            if (entityiterator instanceof LivingEntity
               && !entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("minecraft:skeletons")))
               && !entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:mourners")))
               && !entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:demons")))) {
               flag = true;
            }
         }

         if (flag && !world.m_5776_()) {
            BlockPos _bp = new BlockPos(x, y, z);
            BlockEntity _blockEntity = world.m_7702_(_bp);
            BlockState _bs = world.m_8055_(_bp);
            if (_blockEntity != null) {
               _blockEntity.getPersistentData().m_128347_("soulcharge", (new Object() {
                  public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                     BlockEntity blockEntity = world.m_7702_(pos);
                     return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
                  }
               }).getValue(world, new BlockPos(x, y, z), "soulcharge") + (double)Mth.m_216271_(RandomSource.m_216327_(), 3, 4));
            }

            if (world instanceof Level _level) {
               _level.m_7260_(_bp, _bs, _bs, 3);
            }
         }

         if ((new Object() {
            public double getValue(LevelAccessor world, BlockPos pos, String tag) {
               BlockEntity blockEntity = world.m_7702_(pos);
               return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
            }
         }).getValue(world, new BlockPos(x, y, z), "soulcharge") >= 20.0 && world instanceof ServerLevel _level) {
            _level.m_8767_(ParticleTypes.f_123745_, x + 0.5 + (double)(new Object() {
               public Direction getDirection(BlockState _bs) {
                  if (_bs.m_60734_().m_49965_().m_61081_("facing") instanceof DirectionProperty _dp) {
                     return (Direction)_bs.m_61143_(_dp);
                  } else {
                     if (_bs.m_60734_().m_49965_().m_61081_("axis") instanceof EnumProperty _ep && _ep.m_6908_().toArray()[0] instanceof Axis) {
                        return Direction.m_122387_((Axis)_bs.m_61143_(_ep), AxisDirection.POSITIVE);
                     }

                     return Direction.NORTH;
                  }
               }
            }).getDirection(world.m_8055_(new BlockPos(x, y, z))).m_122429_(), y + 0.5 + (double)(new Object() {
               public Direction getDirection(BlockState _bs) {
                  if (_bs.m_60734_().m_49965_().m_61081_("facing") instanceof DirectionProperty _dp) {
                     return (Direction)_bs.m_61143_(_dp);
                  } else {
                     if (_bs.m_60734_().m_49965_().m_61081_("axis") instanceof EnumProperty _ep && _ep.m_6908_().toArray()[0] instanceof Axis) {
                        return Direction.m_122387_((Axis)_bs.m_61143_(_ep), AxisDirection.POSITIVE);
                     }

                     return Direction.NORTH;
                  }
               }
            }).getDirection(world.m_8055_(new BlockPos(x, y, z))).m_122430_(), z + 0.5 + (double)(new Object() {
               public Direction getDirection(BlockState _bs) {
                  if (_bs.m_60734_().m_49965_().m_61081_("facing") instanceof DirectionProperty _dp) {
                     return (Direction)_bs.m_61143_(_dp);
                  } else {
                     if (_bs.m_60734_().m_49965_().m_61081_("axis") instanceof EnumProperty _ep && _ep.m_6908_().toArray()[0] instanceof Axis) {
                        return Direction.m_122387_((Axis)_bs.m_61143_(_ep), AxisDirection.POSITIVE);
                     }

                     return Direction.NORTH;
                  }
               }
            }).getDirection(world.m_8055_(new BlockPos(x, y, z))).m_122431_(), 4, 0.25, 0.25, 0.25, 0.0);
         }

         if ((new Object() {
            public double getValue(LevelAccessor world, BlockPos pos, String tag) {
               BlockEntity blockEntity = world.m_7702_(pos);
               return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
            }
         }).getValue(world, new BlockPos(x, y, z), "soulcharge") >= 40.0) {
            if (!world.m_5776_()) {
               BlockPos _bpx = new BlockPos(x, y, z);
               BlockEntity _blockEntityx = world.m_7702_(_bpx);
               BlockState _bsx = world.m_8055_(_bpx);
               if (_blockEntityx != null) {
                  _blockEntityx.getPersistentData().m_128347_("soulcharge", -60.0);
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
                  _blockEntityxx.getPersistentData().m_128347_("fire", (double)Mth.m_216271_(RandomSource.m_216327_(), 4, 8));
               }

               if (world instanceof Level _level) {
                  _level.m_7260_(_bpxx, _bsxx, _bsxx, 3);
               }
            }

            if (world instanceof Level _level) {
               if (!_level.m_5776_()) {
                  _level.m_5594_(
                     null,
                     new BlockPos(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.firecharge.use")),
                     SoundSource.BLOCKS,
                     1.0F,
                     1.25F
                  );
               } else {
                  _level.m_7785_(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.firecharge.use")),
                     SoundSource.BLOCKS,
                     1.0F,
                     1.25F,
                     false
                  );
               }
            }
         }

         if ((new Object() {
            public double getValue(LevelAccessor world, BlockPos pos, String tag) {
               BlockEntity blockEntity = world.m_7702_(pos);
               return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
            }
         }).getValue(world, new BlockPos(x, y, z), "fire") > 0.0) {
            if (!world.m_5776_()) {
               BlockPos _bpxxx = new BlockPos(x, y, z);
               BlockEntity _blockEntityxxx = world.m_7702_(_bpxxx);
               BlockState _bsxxx = world.m_8055_(_bpxxx);
               if (_blockEntityxxx != null) {
                  _blockEntityxxx.getPersistentData().m_128347_("fire", (new Object() {
                     public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                        BlockEntity blockEntity = world.m_7702_(pos);
                        return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
                     }
                  }).getValue(world, new BlockPos(x, y, z), "fire") - 1.0);
               }

               if (world instanceof Level _levelx) {
                  _levelx.m_7260_(_bpxxx, _bsxxx, _bsxxx, 3);
               }
            }

            distance = 1.0;
            wallmeet = true;

            for (int index0 = 0; index0 < 12; index0++) {
               if (world.m_8055_(new BlockPos(x + 0.5 + (double)(new Object() {
                  public Direction getDirection(BlockState _bs) {
                     if (_bs.m_60734_().m_49965_().m_61081_("facing") instanceof DirectionProperty _dp) {
                        return (Direction)_bs.m_61143_(_dp);
                     } else {
                        if (_bs.m_60734_().m_49965_().m_61081_("axis") instanceof EnumProperty _ep && _ep.m_6908_().toArray()[0] instanceof Axis) {
                           return Direction.m_122387_((Axis)_bs.m_61143_(_ep), AxisDirection.POSITIVE);
                        }

                        return Direction.NORTH;
                     }
                  }
               }).getDirection(world.m_8055_(new BlockPos(x, y, z))).m_122429_() * distance, y + 0.5 + (double)(new Object() {
                  public Direction getDirection(BlockState _bs) {
                     if (_bs.m_60734_().m_49965_().m_61081_("facing") instanceof DirectionProperty _dp) {
                        return (Direction)_bs.m_61143_(_dp);
                     } else {
                        if (_bs.m_60734_().m_49965_().m_61081_("axis") instanceof EnumProperty _ep && _ep.m_6908_().toArray()[0] instanceof Axis) {
                           return Direction.m_122387_((Axis)_bs.m_61143_(_ep), AxisDirection.POSITIVE);
                        }

                        return Direction.NORTH;
                     }
                  }
               }).getDirection(world.m_8055_(new BlockPos(x, y, z))).m_122430_() * distance, z + 0.5 + (double)(new Object() {
                  public Direction getDirection(BlockState _bs) {
                     if (_bs.m_60734_().m_49965_().m_61081_("facing") instanceof DirectionProperty _dp) {
                        return (Direction)_bs.m_61143_(_dp);
                     } else {
                        if (_bs.m_60734_().m_49965_().m_61081_("axis") instanceof EnumProperty _ep && _ep.m_6908_().toArray()[0] instanceof Axis) {
                           return Direction.m_122387_((Axis)_bs.m_61143_(_ep), AxisDirection.POSITIVE);
                        }

                        return Direction.NORTH;
                     }
                  }
               }).getDirection(world.m_8055_(new BlockPos(x, y, z))).m_122431_() * distance)).m_60815_()) {
                  wallmeet = false;
               }

               if (wallmeet) {
                  if (world instanceof ServerLevel _levelx) {
                     _levelx.m_8767_(ParticleTypes.f_123745_, x + 0.5 + (double)(new Object() {
                        public Direction getDirection(BlockState _bs) {
                           if (_bs.m_60734_().m_49965_().m_61081_("facing") instanceof DirectionProperty _dp) {
                              return (Direction)_bs.m_61143_(_dp);
                           } else {
                              if (_bs.m_60734_().m_49965_().m_61081_("axis") instanceof EnumProperty _ep && _ep.m_6908_().toArray()[0] instanceof Axis) {
                                 return Direction.m_122387_((Axis)_bs.m_61143_(_ep), AxisDirection.POSITIVE);
                              }

                              return Direction.NORTH;
                           }
                        }
                     }).getDirection(world.m_8055_(new BlockPos(x, y, z))).m_122429_() * distance, y + 0.5 + (double)(new Object() {
                        public Direction getDirection(BlockState _bs) {
                           if (_bs.m_60734_().m_49965_().m_61081_("facing") instanceof DirectionProperty _dp) {
                              return (Direction)_bs.m_61143_(_dp);
                           } else {
                              if (_bs.m_60734_().m_49965_().m_61081_("axis") instanceof EnumProperty _ep && _ep.m_6908_().toArray()[0] instanceof Axis) {
                                 return Direction.m_122387_((Axis)_bs.m_61143_(_ep), AxisDirection.POSITIVE);
                              }

                              return Direction.NORTH;
                           }
                        }
                     }).getDirection(world.m_8055_(new BlockPos(x, y, z))).m_122430_() * distance, z + 0.5 + (double)(new Object() {
                        public Direction getDirection(BlockState _bs) {
                           if (_bs.m_60734_().m_49965_().m_61081_("facing") instanceof DirectionProperty _dp) {
                              return (Direction)_bs.m_61143_(_dp);
                           } else {
                              if (_bs.m_60734_().m_49965_().m_61081_("axis") instanceof EnumProperty _ep && _ep.m_6908_().toArray()[0] instanceof Axis) {
                                 return Direction.m_122387_((Axis)_bs.m_61143_(_ep), AxisDirection.POSITIVE);
                              }

                              return Direction.NORTH;
                           }
                        }
                     }).getDirection(world.m_8055_(new BlockPos(x, y, z))).m_122431_() * distance, 4, 0.15, 0.15, 0.15, 0.002);
                  }

                  distance += 0.5;
                  Vec3 _centerx = new Vec3(x + 0.5 + (double)(new Object() {
                     public Direction getDirection(BlockState _bs) {
                        if (_bs.m_60734_().m_49965_().m_61081_("facing") instanceof DirectionProperty _dp) {
                           return (Direction)_bs.m_61143_(_dp);
                        } else {
                           if (_bs.m_60734_().m_49965_().m_61081_("axis") instanceof EnumProperty _ep && _ep.m_6908_().toArray()[0] instanceof Axis) {
                              return Direction.m_122387_((Axis)_bs.m_61143_(_ep), AxisDirection.POSITIVE);
                           }

                           return Direction.NORTH;
                        }
                     }
                  }).getDirection(world.m_8055_(new BlockPos(x, y, z))).m_122429_() * distance, y + 0.5 + (double)(new Object() {
                     public Direction getDirection(BlockState _bs) {
                        if (_bs.m_60734_().m_49965_().m_61081_("facing") instanceof DirectionProperty _dp) {
                           return (Direction)_bs.m_61143_(_dp);
                        } else {
                           if (_bs.m_60734_().m_49965_().m_61081_("axis") instanceof EnumProperty _ep && _ep.m_6908_().toArray()[0] instanceof Axis) {
                              return Direction.m_122387_((Axis)_bs.m_61143_(_ep), AxisDirection.POSITIVE);
                           }

                           return Direction.NORTH;
                        }
                     }
                  }).getDirection(world.m_8055_(new BlockPos(x, y, z))).m_122430_() * distance, z + 0.5 + (double)(new Object() {
                     public Direction getDirection(BlockState _bs) {
                        if (_bs.m_60734_().m_49965_().m_61081_("facing") instanceof DirectionProperty _dp) {
                           return (Direction)_bs.m_61143_(_dp);
                        } else {
                           if (_bs.m_60734_().m_49965_().m_61081_("axis") instanceof EnumProperty _ep && _ep.m_6908_().toArray()[0] instanceof Axis) {
                              return Direction.m_122387_((Axis)_bs.m_61143_(_ep), AxisDirection.POSITIVE);
                           }

                           return Direction.NORTH;
                        }
                     }
                  }).getDirection(world.m_8055_(new BlockPos(x, y, z))).m_122431_() * distance);

                  for (Entity entityiteratorx : world.m_6443_(Entity.class, new AABB(_centerx, _centerx).m_82400_(0.5), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                     .collect(Collectors.toList())) {
                     if (entityiteratorx instanceof LivingEntity
                        && !entityiteratorx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("minecraft:skeletons")))
                        && !entityiteratorx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:mourners")))
                        && !entityiteratorx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:demons")))) {
                        entityiteratorx.m_6469_(DamageSource.f_19305_, 3.0F);
                        entityiteratorx.m_20254_(5);
                     }
                  }
               }
            }
         }
      }
   }
}
