package com.majruszsaccessories.particles;

import com.majruszlibrary.data.Reader;
import com.majruszlibrary.data.Serializables;
import com.majruszlibrary.particles.CustomParticleOptions;
import com.majruszlibrary.particles.CustomParticleType;
import com.majruszsaccessories.MajruszsAccessories;

public class BonusParticleType extends CustomParticleType<BonusParticleType.Options> {
   public BonusParticleType() {
      super(BonusParticleType.Options::new);
   }

   static {
      Serializables.get(BonusParticleType.Options.class).define("color", Reader.integer(), s -> s.color, (s, v) -> s.color = v);
   }

   public static class Options extends CustomParticleOptions<BonusParticleType.Options> {
      public int color;

      public Options() {
         super(MajruszsAccessories.BONUS_PARTICLE);
      }

      public Options(int color) {
         this();
         this.color = color;
      }
   }
}
