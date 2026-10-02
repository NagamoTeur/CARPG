package com.majruszsaccessories.config;

import com.majruszlibrary.data.Reader;
import com.majruszlibrary.data.Serializables;
import com.majruszlibrary.math.Random;
import com.majruszlibrary.math.Range;
import net.minecraft.util.Mth;

public class Config {
   static {
      Serializables.getStatic(Config.class)
         .define("accessories", Config.Accessories.class)
         .define("boosters", Config.Boosters.class)
         .define("efficiency", Config.Efficiency.class);
      Serializables.getStatic(Config.Accessories.class);
      Serializables.getStatic(Config.Boosters.class);
      Serializables.getStatic(Config.Efficiency.class)
         .define("range", Reader.range(Reader.number()), () -> Config.Efficiency.RANGE, v -> Config.Efficiency.RANGE = Range.of(-1.0F, 10.0F).clamp(v))
         .define("average", Reader.number(), () -> Config.Efficiency.AVG, v -> Config.Efficiency.AVG = (Float)Range.of(-1.0F, 10.0F).clamp(v))
         .define("standard_deviation", Reader.number(), () -> Config.Efficiency.STD, v -> Config.Efficiency.STD = (Float)Range.of(-1.0F, 10.0F).clamp(v));
   }

   public static class Accessories {
   }

   public static class Boosters {
   }

   public static class Efficiency {
      public static Range<Float> RANGE = Range.of(-0.6F, 0.6F);
      public static float AVG = 0.0F;
      public static float STD = 0.2F;

      public static float getRandom() {
         return (double)Math.abs((Float)RANGE.to - (Float)RANGE.from) < 1.0E-5 ? (Float)RANGE.from : RANGE.lerp(getGaussianRatio());
      }

      public static float getGaussianRatio() {
         return Mth.m_14036_(
            (float)(
               (Random.nextGaussian() * (double)STD + (double)AVG - (double)((Float)RANGE.from).floatValue()) / (double)((Float)RANGE.to - (Float)RANGE.from)
            ),
            0.0F,
            1.0F
         );
      }
   }
}
