package software.bernie.ars_nouveau.shadowed.eliotlash.mclib.math.functions.utility;

import software.bernie.ars_nouveau.shadowed.eliotlash.mclib.math.IValue;
import software.bernie.ars_nouveau.shadowed.eliotlash.mclib.math.functions.Function;

public class RandomInteger extends Function {
   public java.util.Random random = new java.util.Random();

   public RandomInteger(IValue[] values, String name) throws Exception {
      super(values, name);
   }

   @Override
   public int getRequiredArguments() {
      return 2;
   }

   @Override
   public double get() {
      double min = Math.ceil(this.getArg(0));
      double max = Math.floor(this.getArg(1));
      return Math.floor(Math.random() * (max - min) + min);
   }
}
