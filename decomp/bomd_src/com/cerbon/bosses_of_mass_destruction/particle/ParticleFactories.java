package com.cerbon.bosses_of_mass_destruction.particle;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import com.cerbon.bosses_of_mass_destruction.util.BMDColors;
import java.util.function.Function;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.phys.Vec3;

public class ParticleFactories {
   public static ClientParticleBuilder cometTrail() {
      return new ClientParticleBuilder((ParticleOptions)BMDParticles.DISAPPEARING_SWIRL.get())
         .color((Function<Float, Vec3>)(f -> MathUtils.lerpVec(f, BMDColors.COMET_BLUE, BMDColors.FADED_COMET_BLUE)))
         .brightness(15728880)
         .scale(f -> 0.5F + f * 0.3F);
   }

   public static ClientParticleBuilder soulFlame() {
      return new ClientParticleBuilder((ParticleOptions)BMDParticles.SOUL_FLAME.get()).brightness(15728880);
   }
}
