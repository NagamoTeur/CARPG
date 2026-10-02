package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom;

import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityTick;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;

public class LightBlockPlacer implements IEntityTick<ServerLevel> {
   private final Entity entity;

   public LightBlockPlacer(Entity entity) {
      this.entity = entity;
   }

   public void tick(ServerLevel level) {
      if (!level.m_8055_(this.entity.m_20183_()).m_60713_(Blocks.f_152480_)) {
         level.m_46597_(this.entity.m_20183_(), Blocks.f_152480_.m_49966_());
      }
   }
}
