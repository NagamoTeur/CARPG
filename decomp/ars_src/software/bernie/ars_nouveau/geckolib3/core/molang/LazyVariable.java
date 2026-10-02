package software.bernie.ars_nouveau.geckolib3.core.molang;

import java.util.function.DoubleSupplier;
import software.bernie.ars_nouveau.shadowed.eliotlash.mclib.math.Variable;

public class LazyVariable extends Variable {
   private DoubleSupplier valueSupplier;

   public LazyVariable(String name, double value) {
      this(name, () -> value);
   }

   public LazyVariable(String name, DoubleSupplier valueSupplier) {
      super(name, 0.0);
      this.valueSupplier = valueSupplier;
   }

   @Override
   public void set(double value) {
      this.valueSupplier = () -> value;
   }

   public void set(DoubleSupplier valueSupplier) {
      this.valueSupplier = valueSupplier;
   }

   @Override
   public double get() {
      return this.valueSupplier.getAsDouble();
   }

   public static LazyVariable from(Variable variable) {
      return new LazyVariable(variable.getName(), variable.get());
   }
}
