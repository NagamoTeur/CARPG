package software.bernie.ars_nouveau.geckolib3.core.molang.expressions;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import software.bernie.ars_nouveau.geckolib3.core.molang.MolangParser;
import software.bernie.ars_nouveau.shadowed.eliotlash.mclib.math.Constant;
import software.bernie.ars_nouveau.shadowed.eliotlash.mclib.math.IValue;
import software.bernie.ars_nouveau.shadowed.eliotlash.mclib.math.Operation;

public abstract class MolangExpression implements IValue {
   public MolangParser context;

   public static boolean isZero(MolangExpression expression) {
      return isConstant(expression, 0.0);
   }

   public static boolean isOne(MolangExpression expression) {
      return isConstant(expression, 1.0);
   }

   public static boolean isConstant(MolangExpression expression, double x) {
      return !(expression instanceof MolangValue value) ? false : value.value instanceof Constant && Operation.equals(value.value.get(), x);
   }

   public static boolean isExpressionConstant(MolangExpression expression) {
      return expression instanceof MolangValue value ? value.value instanceof Constant : false;
   }

   public MolangExpression(MolangParser context) {
      this.context = context;
   }

   public JsonElement toJson() {
      return new JsonPrimitive(this.toString());
   }
}
