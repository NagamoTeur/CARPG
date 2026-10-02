package com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.random;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.RandomUtils;
import java.util.Random;
import net.minecraft.world.phys.Vec3;

public class ModRandom implements IRandom {
   @Override
   public double getDouble() {
      return new Random().nextDouble();
   }

   @Override
   public Vec3 getVector() {
      return RandomUtils.randVec();
   }
}
