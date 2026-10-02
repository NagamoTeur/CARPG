package com.hollingsworth.arsnouveau.common.mob_jar;

import com.hollingsworth.arsnouveau.api.mob_jar.JarBehavior;
import com.hollingsworth.arsnouveau.common.block.tile.MobJarTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class SheepBehavior extends JarBehavior<Sheep> {
   @Override
   public void tick(MobJarTile tile) {
      Sheep sheep = this.entityFromJar(tile);
      if (!sheep.f_19853_.f_46443_ && sheep.m_29875_()) {
         if (sheep.m_217043_().m_188503_(sheep.m_6162_() ? 50 : 1000) != 0) {
            return;
         }

         BlockPos pos1 = tile.m_58899_().m_7495_();
         if (sheep.f_19853_.m_8055_(pos1).m_60713_(Blocks.f_50440_)) {
            sheep.f_19853_.m_7605_(sheep, (byte)10);
            sheep.f_19853_.m_46796_(2001, pos1, Block.m_49956_(Blocks.f_50440_.m_49966_()));
            sheep.f_19853_.m_7731_(pos1, Blocks.f_50493_.m_49966_(), 2);
            sheep.m_8035_();
            this.syncClient(tile);
         }
      }
   }
}
