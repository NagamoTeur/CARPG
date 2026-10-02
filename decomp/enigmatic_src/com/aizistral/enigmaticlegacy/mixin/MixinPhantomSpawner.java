package com.aizistral.enigmaticlegacy.mixin;

import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.registries.EnigmaticItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.PhantomSpawner;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({PhantomSpawner.class})
public class MixinPhantomSpawner {
   private int ticksUntilSpawn = 0;

   @Inject(
      at = {@At("RETURN")},
      method = {"tick"},
      cancellable = true
   )
   private void onHandlePhantomSpawns(ServerLevel world, boolean p_230253_2_, boolean p_230253_3_, CallbackInfoReturnable<Integer> info) {
      if (p_230253_2_ && world.m_46469_().m_46207_(GameRules.f_46155_)) {
         RandomSource random = world.f_46441_;
         this.ticksUntilSpawn--;
         if (this.ticksUntilSpawn <= 0) {
            this.ticksUntilSpawn = this.ticksUntilSpawn + (60 + random.m_188503_(60)) * 20;
            if (world.m_7445_() >= 5 || !world.m_6042_().f_223549_()) {
               int i = 0;

               for (ServerPlayer player : world.m_6907_()) {
                  if (!player.m_5833_() && !player.m_7500_()) {
                     BlockPos blockpos = player.m_20183_();
                     if (!world.m_6042_().f_223549_() || blockpos.m_123342_() >= world.m_5736_() && world.m_45527_(blockpos)) {
                        DifficultyInstance difficulty = world.m_6436_(blockpos);
                        if (difficulty.m_19049_(random.m_188501_() * 3.0F)) {
                           ServerStatsCounter serverstatisticsmanager = player.m_8951_();
                           int ticksSinceRest = Mth.m_14045_(serverstatisticsmanager.m_13015_(Stats.f_12988_.m_12902_(Stats.f_12992_)), 1, Integer.MAX_VALUE);
                           if (SuperpositionHandler.hasCurio(player, EnigmaticItems.CURSED_RING) && random.m_188503_(ticksSinceRest) <= 72000) {
                              BlockPos blockpos1 = blockpos.m_6630_(20 + random.m_188503_(15))
                                 .m_122030_(-10 + random.m_188503_(21))
                                 .m_122020_(-10 + random.m_188503_(21));
                              BlockState blockstate = world.m_8055_(blockpos1);
                              FluidState fluidstate = world.m_6425_(blockpos1);
                              if (NaturalSpawner.m_47056_(world, blockpos1, blockstate, fluidstate, EntityType.f_20509_)) {
                                 SpawnGroupData ilivingentitydata = null;
                                 int l = 1 + random.m_188503_(difficulty.m_19048_().m_19028_() + 1);

                                 for (int i1 = 0; i1 < l; i1++) {
                                    Phantom phantomentity = (Phantom)EntityType.f_20509_.m_20615_(world);
                                    phantomentity.m_20035_(blockpos1, 0.0F, 0.0F);
                                    ilivingentitydata = phantomentity.m_6518_(world, difficulty, MobSpawnType.NATURAL, ilivingentitydata, (CompoundTag)null);
                                    world.m_47205_(phantomentity);
                                 }

                                 i += l;
                              }
                           }
                        }
                     }
                  }
               }

               info.setReturnValue((Integer)info.getReturnValue() + i);
            }
         }
      }
   }
}
