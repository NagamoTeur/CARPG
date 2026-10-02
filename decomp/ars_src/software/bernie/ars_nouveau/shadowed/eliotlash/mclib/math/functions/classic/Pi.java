package software.bernie.ars_nouveau.shadowed.eliotlash.mclib.math.functions.classic;

import software.bernie.ars_nouveau.shadowed.eliotlash.mclib.math.IValue;
import software.bernie.ars_nouveau.shadowed.eliotlash.mclib.math.functions.Function;

public class Pi extends Function {
   public Pi(IValue[] values, String name) throws Exception {
      super(values, name);
   }

   @Override
   public double get() {
      return Math.PI;
   }
}
