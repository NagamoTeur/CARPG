package com.cerbon.bosses_of_mass_destruction.block.custom;

import com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith.ObsidilithEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public class ObsidilithRuneBlock extends Block {
   public ObsidilithRuneBlock(Properties properties) {
      super(properties);
   }

   public void m_213897_(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
      this.linkToEntities(level, pos);
   }

   private void linkToEntities(ServerLevel level, BlockPos pos) {
      level.m_45976_(ObsidilithEntity.class, new AABB(pos).m_82377_(15.0, 40.0, 15.0)).forEach(entity -> entity.addActivePillar(pos));
   }

   public void m_6807_(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean movedByPiston) {
      level.m_186460_(pos, this, 10);
   }
}
