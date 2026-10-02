package com.github.L_Ender.cataclysm.blockentities;

import com.github.L_Ender.cataclysm.blocks.Cursed_Tombstone_Block;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Maledictus.Maledictus_Entity;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class Cursed_tombstone_Entity extends BlockEntity {
   public int tickCount;
   public int summonCooldownProgress = 0;
   private final RandomSource rnd = RandomSource.m_216327_();

   public Cursed_tombstone_Entity(BlockPos pos, BlockState state) {
      super((BlockEntityType)ModTileentites.CURSED_TOMBSTONE.get(), pos, state);
   }

   public static void commonTick(Level level, BlockPos pos, BlockState blockState, Cursed_tombstone_Entity entity) {
      if (blockState.m_60734_() instanceof Cursed_Tombstone_Block) {
         if (!(Boolean)blockState.m_61143_(Cursed_Tombstone_Block.POWERED)) {
            if (entity.summonCooldownProgress < CMConfig.Cursed_tombstone_summon_cooldown * 20 * 60) {
               entity.summonCooldownProgress++;
            } else if (!level.f_46443_) {
               level.m_7731_(pos, (BlockState)blockState.m_61124_(Cursed_Tombstone_Block.POWERED, true), 2);
            }
         } else if ((Boolean)blockState.m_61143_(Cursed_Tombstone_Block.LIT)) {
            entity.tickCount++;
            if (entity.tickCount == 1) {
               ScreenShake_Entity.ScreenShake(level, Vec3.m_82512_(pos), 20.0F, 0.05F, 0, 80);
            }

            if (entity.tickCount > 60 && entity.tickCount < 63) {
               double d0 = (double)((float)pos.m_123341_() + 0.5F);
               double d1 = (double)(pos.m_123342_() + 2);
               double d2 = (double)((float)pos.m_123343_() + 0.5F);
               float size = 3.0F;

               for (float i = -size; i <= size; i++) {
                  for (float j = -size; j <= size; j++) {
                     for (float k = -size; k <= size; k++) {
                        double d3 = (double)j + (entity.rnd.m_188500_() - entity.rnd.m_188500_()) * 0.5;
                        double d4 = (double)i + (entity.rnd.m_188500_() - entity.rnd.m_188500_()) * 0.5;
                        double d5 = (double)k + (entity.rnd.m_188500_() - entity.rnd.m_188500_()) * 0.5;
                        double d6 = (double)Mth.m_14116_((float)(d3 * d3 + d4 * d4 + d5 * d5)) / 0.5 + entity.rnd.m_188583_() * 0.05;
                        level.m_7106_((ParticleOptions)ModParticle.PHANTOM_WING_FLAME.get(), d0, d1, d2, d3 / d6, d4 / d6, d5 / d6);
                        if (i != -size && i != size && j != -size && j != size) {
                           k += size * 2.0F - 1.0F;
                        }
                     }
                  }
               }
            }

            if (entity.tickCount > 63) {
               Maledictus_Entity maledictus = (Maledictus_Entity)((EntityType)ModEntities.MALEDICTUS.get()).m_20615_(level);
               if (maledictus != null) {
                  ScreenShake_Entity.ScreenShake(level, Vec3.m_82512_(pos), 20.0F, 0.1F, 0, 40);
                  maledictus.m_6034_((double)pos.m_123341_() + 0.5, (double)(pos.m_123342_() + 2), (double)pos.m_123343_() + 0.5);
                  maledictus.setTombstonePos(pos);
                  maledictus.setTombstoneDirection((Direction)blockState.m_61143_(Cursed_Tombstone_Block.FACING));
                  if (!level.f_46443_) {
                     int MthX = Mth.m_14143_((float)pos.m_123341_());
                     int MthY = Mth.m_14143_((float)pos.m_123342_());
                     int MthZ = Mth.m_14143_((float)pos.m_123343_());

                     for (int k2 = -1; k2 <= 1; k2++) {
                        for (int l2 = -1; l2 <= 1; l2++) {
                           for (int j = 0; j <= 5; j++) {
                              int i3 = MthX + k2;
                              int kx = MthY + j;
                              int l = MthZ + l2;
                              BlockPos blockpos = new BlockPos(i3, kx, l);
                              BlockState block = level.m_8055_(blockpos);
                              if (block != Blocks.f_50016_.m_49966_() && !block.m_204336_(ModTag.ALTAR_DESTROY_IMMUNE)) {
                                 level.m_46961_(blockpos, false);
                              }
                           }
                        }
                     }

                     level.m_7967_(maledictus);
                     level.m_46961_(pos, false);
                  }
               }
            }
         } else {
            entity.tickCount = 0;
         }
      }
   }

   public void m_142466_(CompoundTag p_155312_) {
      super.m_142466_(p_155312_);
      if (p_155312_.m_128425_("summonCooldownProgress", 11)) {
         this.summonCooldownProgress = p_155312_.m_128451_("summonCooldownProgress");
      }
   }

   protected void m_183515_(CompoundTag p_187486_) {
      super.m_183515_(p_187486_);
      p_187486_.m_128405_("summonCooldownProgress", this.summonCooldownProgress);
   }
}
