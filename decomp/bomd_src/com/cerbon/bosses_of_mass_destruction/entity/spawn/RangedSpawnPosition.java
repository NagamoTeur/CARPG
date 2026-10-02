package com.cerbon.bosses_of_mass_destruction.entity.spawn;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.random.IRandom;
import net.minecraft.world.phys.Vec3;

public class RangedSpawnPosition implements ISpawnPosition {
   private final Vec3 position;
   private final double minDistance;
   private final double maxDistance;
   private final IRandom random;

   public RangedSpawnPosition(Vec3 position, double minDistance, double maxDistance, IRandom random) {
      this.position = position;
      this.minDistance = minDistance;
      this.maxDistance = maxDistance;
      this.random = random;
   }

   @Override
   public Vec3 getPos() {
      Vec3 randomOffset = this.random.getVector();
      Vec3 coercedRandomOffset = randomOffset.m_82541_().m_82490_(Math.max(Math.min(randomOffset.m_82553_(), this.maxDistance), this.minDistance));
      return this.position.m_82549_(coercedRandomOffset);
   }
}
