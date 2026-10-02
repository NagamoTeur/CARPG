package io.redspace.ironsspellbooks.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;

public class BloodSlashBlock extends Block {
   public BloodSlashBlock() {
      super(Properties.m_60939_(Material.f_76278_).m_60978_(2.5F).m_60918_(SoundType.f_56742_).m_60955_());
   }

   public BloodSlashBlock(Properties properties) {
      super(properties);
   }

   public void m_141947_(@NotNull Level level, @NotNull BlockPos blockPos, @NotNull BlockState blockState, @NotNull Entity entity) {
      if (entity instanceof Player player) {
         int duration = 200;
         byte var7 = 2;
      }
   }
}
