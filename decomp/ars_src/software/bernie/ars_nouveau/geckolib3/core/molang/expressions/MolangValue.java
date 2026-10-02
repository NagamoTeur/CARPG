package software.bernie.ars_nouveau.geckolib3.core.molang.expressions;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import software.bernie.ars_nouveau.geckolib3.core.molang.MolangParser;
import software.bernie.ars_nouveau.shadowed.eliotlash.mclib.math.Constant;
import software.bernie.ars_nouveau.shadowed.eliotlash.mclib.math.IValue;

public class MolangValue extends MolangExpression {
   public IValue value;
   public boolean returns;

   public MolangValue(MolangParser context, IValue value) {
      super(context);
      this.value = value;
   }

   public MolangExpression addReturn() {
      this.returns = true;
      return this;
   }

   @Override
   public double get() {
      return this.value.get();
   }

   @Override
   public String toString() {
      return (this.returns ? "return " : "") + this.value.toString();
   }

   @Override
   public JsonElement toJson() {
      return (JsonElement)(this.value instanceof Constant ? new JsonPrimitive(this.value.get()) : super.toJson());
   }
}
