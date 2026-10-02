package software.bernie.ars_nouveau.shadowed.eliotlash.mclib.math.functions.utility;

import software.bernie.ars_nouveau.shadowed.eliotlash.mclib.math.IValue;
import software.bernie.ars_nouveau.shadowed.eliotlash.mclib.math.functions.Function;

public class DieRoll extends Function {
   public java.util.Random random = new java.util.Random();

   public DieRoll(IValue[] values, String name) throws Exception {
      super(values, name);
   }

   @Override
   public int getRequiredArguments() {
      return 3;
   }

   @Override
   public double get() {
      double i = 0.0;
      double total = 0.0;

      while (i < this.getArg(0)) {
         total += Math.random() * (this.getArg(2) - this.getArg(2));
      }

      return total;
   }
}
