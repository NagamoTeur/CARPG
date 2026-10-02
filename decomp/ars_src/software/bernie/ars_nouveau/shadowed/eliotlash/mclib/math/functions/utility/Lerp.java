package software.bernie.ars_nouveau.shadowed.eliotlash.mclib.math.functions.utility;

import software.bernie.ars_nouveau.shadowed.eliotlash.mclib.math.IValue;
import software.bernie.ars_nouveau.shadowed.eliotlash.mclib.math.functions.Function;
import software.bernie.ars_nouveau.shadowed.eliotlash.mclib.utils.Interpolations;

public class Lerp extends Function {
   public Lerp(IValue[] values, String name) throws Exception {
      super(values, name);
   }

   @Override
   public int getRequiredArguments() {
      return 3;
   }

   @Override
   public double get() {
      return Interpolations.lerp(this.getArg(0), this.getArg(1), this.getArg(2));
   }
}
