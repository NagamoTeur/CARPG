package com.cerbon.bosses_of_mass_destruction.entity.spawn;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.phys.Vec3;

public class SimpleMobSpawner implements IMobSpawner {
   private final ServerLevel serverLevel;

   public SimpleMobSpawner(ServerLevel serverLevel) {
      this.serverLevel = serverLevel;
   }

   @Override
   public void spawn(Vec3 pos, Entity entity) {
      entity.m_146884_(pos);
      if (entity instanceof Mob mob) {
         mob.m_6518_(this.serverLevel, this.serverLevel.m_6436_(entity.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
      }

      this.serverLevel.m_47205_(entity);
      if (entity instanceof Mob) {
         ((Mob)entity).m_21373_();
      }
   }
}
