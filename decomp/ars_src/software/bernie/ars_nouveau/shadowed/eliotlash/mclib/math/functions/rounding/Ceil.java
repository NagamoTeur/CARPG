package software.bernie.ars_nouveau.shadowed.eliotlash.mclib.math.functions.rounding;

import software.bernie.ars_nouveau.shadowed.eliotlash.mclib.math.IValue;
import software.bernie.ars_nouveau.shadowed.eliotlash.mclib.math.functions.Function;

public class Ceil extends Function {
   public Ceil(IValue[] values, String name) throws Exception {
      super(values, name);
   }

   @Override
   public int getRequiredArguments() {
      return 1;
   }

   @Override
   public double get() {
      return Math.ceil(this.getArg(0));
   }
}
