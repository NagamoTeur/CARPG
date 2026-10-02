package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.thirdlife.iterrpg.entity.GoblinEntity;
import net.thirdlife.iterrpg.entity.GoblinWarriorEntity;
import net.thirdlife.iterrpg.entity.HobgoblinEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;
import net.thirdlife.iterrpg.init.IterRpgModGameRules;

public class GoblinSpawnerRepopulateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      double gobamount = 0.0;
      double hobgob = 0.0;
      double gob = 0.0;
      double gobwar = 0.0;
      double gobpoints = 0.0;
      double decide = 0.0;
      double xc = 0.0;
      double zc = 0.0;
      boolean hoblimit = false;
      if (!world.m_6106_().m_5470_().m_46207_(IterRpgModGameRules.BUILDINGDEBUG)) {
         if ((new Object() {
            public double getValue(LevelAccessor world, BlockPos pos, String tag) {
               BlockEntity blockEntity = world.m_7702_(pos);
               return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
            }
         }).getValue(world, new BlockPos(x, y, z), "respawnTime") >= GoblinSpawnerCycleConfigProcedure.execute() + (new Object() {
            public double getValue(LevelAccessor world, BlockPos pos, String tag) {
               BlockEntity blockEntity = world.m_7702_(pos);
               return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
            }
         }).getValue(world, new BlockPos(x, y, z), "TimeOffset")) {
            if (world instanceof ServerLevel _level) {
               _level.m_8767_(ParticleTypes.f_123744_, x + 0.5, y + 0.5, z + 0.5, 16, 0.5, 0.5, 0.5, 0.0);
            }

            if (!world.m_5776_()) {
               BlockPos _bp = new BlockPos(x, y, z);
               BlockEntity _blockEntity = world.m_7702_(_bp);
               BlockState _bs = world.m_8055_(_bp);
               if (_blockEntity != null) {
                  _blockEntity.getPersistentData().m_128347_("respawnTime", 0.0);
               }

               if (world instanceof Level _level) {
                  _level.m_7260_(_bp, _bs, _bs, 3);
               }
            }

            if (!world.m_5776_()) {
               BlockPos _bpx = new BlockPos(x, y, z);
               BlockEntity _blockEntityx = world.m_7702_(_bpx);
               BlockState _bsx = world.m_8055_(_bpx);
               if (_blockEntityx != null) {
                  _blockEntityx.getPersistentData().m_128347_("TimeOffset", (double)Mth.m_216271_(RandomSource.m_216327_(), -320, 320));
               }

               if (world instanceof Level _level) {
                  _level.m_7260_(_bpx, _bsx, _bsx, 3);
               }
            }

            gobamount = 0.0;
            Vec3 _center = new Vec3(x + 0.5, y + 0.5, z + 0.5);

            for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(48.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if (entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:goblins")))) {
                  gobamount++;
               }
            }

            if (gobamount < 4.0) {
               hoblimit = true;
               gobpoints = (double)Mth.m_216271_(RandomSource.m_216327_(), 5, 7);

               for (int index0 = 0; index0 < 16; index0++) {
                  if (gobpoints > 0.0) {
                     decide = (double)Mth.m_216271_(RandomSource.m_216327_(), 1, 3);
                     if (decide == 1.0 && gobpoints >= 2.0 && hoblimit) {
                        hobgob++;
                        gobpoints -= 2.0;
                        hoblimit = false;
                     } else if (decide == 2.0 && gobpoints >= 1.0) {
                        gobwar++;
                        gobpoints--;
                     } else if (decide == 3.0 && gobpoints >= 0.75) {
                        gob++;
                        gobpoints -= 0.75;
                     }
                  }
               }

               for (int index1 = 0; index1 < 2; index1++) {
                  if (hobgob > 0.0) {
                     xc = (double)Mth.m_216271_(RandomSource.m_216327_(), -2, 2);
                     zc = (double)Mth.m_216271_(RandomSource.m_216327_(), -2, 2);
                     if (world instanceof ServerLevel) {
                        ServerLevel _level = (ServerLevel)world;
                        Entity entityToSpawn = new HobgoblinEntity((EntityType<HobgoblinEntity>)IterRpgModEntities.HOBGOBLIN.get(), _level);
                        entityToSpawn.m_7678_(
                           x + xc,
                           (double)world.m_6924_(Types.MOTION_BLOCKING_NO_LEAVES, (int)(x + xc), (int)(z + zc)),
                           z + zc,
                           (float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0),
                           0.0F
                        );
                        entityToSpawn.m_5618_((float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0));
                        entityToSpawn.m_5616_((float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0));
                        if (entityToSpawn instanceof Mob _mobToSpawn) {
                           _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                        }

                        world.m_7967_(entityToSpawn);
                     }

                     if (world instanceof ServerLevel _level) {
                        _level.m_8767_(
                           ParticleTypes.f_123759_,
                           x + xc,
                           (double)world.m_6924_(Types.MOTION_BLOCKING_NO_LEAVES, (int)(x + xc), (int)(z + zc)),
                           z + zc,
                           8,
                           0.08,
                           0.16,
                           0.08,
                           0.08
                        );
                     }

                     hobgob--;
                  }
               }

               for (int index2 = 0; index2 < 4; index2++) {
                  if (gobwar > 0.0) {
                     xc = (double)Mth.m_216271_(RandomSource.m_216327_(), -2, 2);
                     zc = (double)Mth.m_216271_(RandomSource.m_216327_(), -2, 2);
                     if (world instanceof ServerLevel) {
                        ServerLevel _level = (ServerLevel)world;
                        Entity entityToSpawn = new GoblinWarriorEntity((EntityType<GoblinWarriorEntity>)IterRpgModEntities.GOBLIN_WARRIOR.get(), _level);
                        entityToSpawn.m_7678_(
                           x + xc,
                           (double)world.m_6924_(Types.MOTION_BLOCKING_NO_LEAVES, (int)(x + xc), (int)(z + zc)),
                           z + zc,
                           (float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0),
                           0.0F
                        );
                        entityToSpawn.m_5618_((float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0));
                        entityToSpawn.m_5616_((float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0));
                        if (entityToSpawn instanceof Mob _mobToSpawn) {
                           _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                        }

                        world.m_7967_(entityToSpawn);
                     }

                     if (world instanceof ServerLevel _level) {
                        _level.m_8767_(
                           ParticleTypes.f_123759_,
                           x + xc,
                           (double)world.m_6924_(Types.MOTION_BLOCKING_NO_LEAVES, (int)(x + xc), (int)(z + zc)),
                           z + zc,
                           8,
                           0.08,
                           0.16,
                           0.08,
                           0.08
                        );
                     }

                     gobwar--;
                  }
               }

               for (int index3 = 0; index3 < 6; index3++) {
                  if (gob > 0.0) {
                     xc = (double)Mth.m_216271_(RandomSource.m_216327_(), -2, 2);
                     zc = (double)Mth.m_216271_(RandomSource.m_216327_(), -2, 2);
                     if (world instanceof ServerLevel) {
                        ServerLevel _level = (ServerLevel)world;
                        Entity entityToSpawn = new GoblinEntity((EntityType<GoblinEntity>)IterRpgModEntities.GOBLIN.get(), _level);
                        entityToSpawn.m_7678_(
                           x + xc,
                           (double)world.m_6924_(Types.MOTION_BLOCKING_NO_LEAVES, (int)(x + xc), (int)(z + zc)),
                           z + zc,
                           (float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0),
                           0.0F
                        );
                        entityToSpawn.m_5618_((float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0));
                        entityToSpawn.m_5616_((float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0));
                        if (entityToSpawn instanceof Mob _mobToSpawn) {
                           _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                        }

                        world.m_7967_(entityToSpawn);
                     }

                     if (world instanceof ServerLevel _level) {
                        _level.m_8767_(
                           ParticleTypes.f_123759_,
                           x + xc,
                           (double)world.m_6924_(Types.MOTION_BLOCKING_NO_LEAVES, (int)(x + xc), (int)(z + zc)),
                           z + zc,
                           8,
                           0.08,
                           0.16,
                           0.08,
                           0.08
                        );
                     }

                     gob--;
                  }
               }
            }
         } else {
            if (!world.m_5776_()) {
               BlockPos _bpxx = new BlockPos(x, y, z);
               BlockEntity _blockEntityxx = world.m_7702_(_bpxx);
               BlockState _bsxx = world.m_8055_(_bpxx);
               if (_blockEntityxx != null) {
                  _blockEntityxx.getPersistentData().m_128347_("respawnTime", (new Object() {
                     public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                        BlockEntity blockEntity = world.m_7702_(pos);
                        return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
                     }
                  }).getValue(world, new BlockPos(x, y, z), "respawnTime") + 1.0);
               }

               if (world instanceof Level _level) {
                  _level.m_7260_(_bpxx, _bsxx, _bsxx, 3);
               }
            }

            if (world instanceof ServerLevel _level) {
               _level.m_8767_(ParticleTypes.f_123744_, x + 0.5, y + 0.5, z + 0.5, 1, 0.32, 0.32, 0.32, 0.0);
            }
         }
      }
   }
}
