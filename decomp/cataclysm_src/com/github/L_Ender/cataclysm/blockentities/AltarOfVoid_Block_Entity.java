package com.github.L_Ender.cataclysm.blockentities;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ender_Guardian_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class AltarOfVoid_Block_Entity extends BlockEntity {
   protected static final int SHORT_RANGE = 6;
   protected boolean spawnedBoss = false;

   public AltarOfVoid_Block_Entity(BlockPos pos, BlockState state) {
      super((BlockEntityType)ModTileentites.ALTAR_OF_VOID.get(), pos, state);
   }

   public static void commonTick(Level level, BlockPos pos, BlockState state, AltarOfVoid_Block_Entity entity) {
      entity.tick(level, pos, state, entity);
   }

   public boolean anyPlayerInRange() {
      return this.f_58857_
         .m_45914_(
            (double)this.f_58858_.m_123341_() + 0.5, (double)this.f_58858_.m_123342_() + 0.5, (double)this.f_58858_.m_123343_() + 0.5, (double)this.getRange()
         );
   }

   public void tick(Level level, BlockPos pos, BlockState state, AltarOfVoid_Block_Entity te) {
      if (!this.spawnedBoss && this.anyPlayerInRange()) {
         if (!level.f_46443_ && level.m_46791_() != Difficulty.PEACEFUL && this.spawnMyBoss((ServerLevel)level)) {
            level.m_46961_(pos, false);
            this.spawnedBoss = true;
         }
      }
   }

   protected boolean spawnMyBoss(ServerLevelAccessor world) {
      Ender_Guardian_Entity enderGuardian = (Ender_Guardian_Entity)((EntityType)ModEntities.ENDER_GUARDIAN.get()).m_20615_(this.f_58857_);
      enderGuardian.m_20035_(this.f_58858_, world.m_6018_().f_46441_.m_188501_() * 360.0F, 0.0F);
      enderGuardian.m_6518_(world, world.m_6436_(this.f_58858_), MobSpawnType.SPAWNER, null, null);
      enderGuardian.m_21446_(this.f_58858_, 46);
      return world.m_7967_(enderGuardian);
   }

   protected int getRange() {
      return 6;
   }
}
