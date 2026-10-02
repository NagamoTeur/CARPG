package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.thirdlife.iterrpg.init.IterRpgModBlocks;

public class BarrierEngageProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      double distance = 0.0;
      boolean flag = false;
      boolean wallmeet = false;
      boolean up = false;
      boolean down = false;
      if (world.m_8055_(new BlockPos(x + 1.0, y, z)).m_60734_() == IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get()
         || world.m_8055_(new BlockPos(x - 1.0, y, z)).m_60734_() == IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get()
         || world.m_8055_(new BlockPos(x, y + 1.0, z)).m_60734_() == IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get()
         || world.m_8055_(new BlockPos(x, y - 1.0, z)).m_60734_() == IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get()
         || world.m_8055_(new BlockPos(x, y, z + 1.0)).m_60734_() == IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get()
         || world.m_8055_(new BlockPos(x, y, z - 1.0)).m_60734_() == IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get()) {
         distance = 0.5;

         for (int index0 = 0; index0 < 12; index0++) {
            if (world.m_8055_(new BlockPos(x + 0.5, y + 0.5 + distance, z + 0.5)).m_60734_() == IterRpgModBlocks.BARRIER_PROJECTOR.get()) {
               up = true;
            }

            distance += 0.5;
         }

         if (up) {
            wallmeet = true;
            distance = 1.0;

            for (int index1 = 0; index1 < 8; index1++) {
               if (wallmeet) {
                  if (Math.random() >= 0.75 && world instanceof ServerLevel _level) {
                     _level.m_8767_(ParticleTypes.f_123808_, x + 0.5, y + 0.5 + distance, z + 0.5, 1, 0.2, 0.2, 0.2, 0.002);
                  }

                  Vec3 _center = new Vec3(x + 0.5, y + 0.5 + distance, z + 0.5);

                  for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(0.5), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                     .collect(Collectors.toList())) {
                     if (entityiterator instanceof ThrownEnderpearl) {
                        if (world instanceof ServerLevel _level) {
                           _level.m_8767_(
                              ParticleTypes.f_123799_, entityiterator.m_20185_(), entityiterator.m_20186_(), entityiterator.m_20189_(), 6, 0.2, 0.2, 0.2, 0.02
                           );
                        }

                        if (!entityiterator.f_19853_.m_5776_()) {
                           entityiterator.m_146870_();
                        }
                     }
                  }

                  if (world.m_46859_(new BlockPos(x + 0.5, y + 0.5 + distance, z + 0.5))) {
                     world.m_7731_(new BlockPos(x + 0.5, y + 0.5 + distance, z + 0.5), ((Block)IterRpgModBlocks.GIANT_PHANTOM_CHAIN.get()).m_49966_(), 3);
                  }

                  if (world.m_8055_(new BlockPos(x + 0.5, y + 0.5 + distance, z + 0.5)).m_60734_() == IterRpgModBlocks.GIANT_PHANTOM_CHAIN.get()
                     && !world.m_5776_()) {
                     BlockPos _bp = new BlockPos(x + 0.5, y + 0.5 + distance, z + 0.5);
                     BlockEntity _blockEntity = world.m_7702_(_bp);
                     BlockState _bs = world.m_8055_(_bp);
                     if (_blockEntity != null) {
                        _blockEntity.getPersistentData().m_128347_("demoncharge", 2.0);
                     }

                     if (world instanceof Level _level) {
                        _level.m_7260_(_bp, _bs, _bs, 3);
                     }
                  }

                  if (world.m_8055_(new BlockPos(x + 0.5, y + 0.5 + distance, z + 0.5)).m_60734_() == IterRpgModBlocks.BARRIER_PROJECTOR.get()) {
                     wallmeet = false;
                  }

                  distance += 0.5;
               }
            }
         }
      }
   }
}
