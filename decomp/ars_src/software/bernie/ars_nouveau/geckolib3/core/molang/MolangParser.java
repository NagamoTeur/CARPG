package software.bernie.ars_nouveau.geckolib3.core.molang;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleSupplier;
import software.bernie.ars_nouveau.geckolib3.core.molang.expressions.MolangAssignment;
import software.bernie.ars_nouveau.geckolib3.core.molang.expressions.MolangExpression;
import software.bernie.ars_nouveau.geckolib3.core.molang.expressions.MolangMultiStatement;
import software.bernie.ars_nouveau.geckolib3.core.molang.expressions.MolangValue;
import software.bernie.ars_nouveau.geckolib3.core.molang.functions.CosDegrees;
import software.bernie.ars_nouveau.geckolib3.core.molang.functions.SinDegrees;
import software.bernie.ars_nouveau.shadowed.eliotlash.mclib.math.Constant;
import software.bernie.ars_nouveau.shadowed.eliotlash.mclib.math.IValue;
import software.bernie.ars_nouveau.shadowed.eliotlash.mclib.math.MathBuilder;
import software.bernie.ars_nouveau.shadowed.eliotlash.mclib.math.Variable;

public class MolangParser extends MathBuilder {
   public static final Map<String, LazyVariable> VARIABLES = new Object2ObjectOpenHashMap();
   public static final MolangExpression ZERO = new MolangValue(null, new Constant(0.0));
   public static final MolangExpression ONE = new MolangValue(null, new Constant(1.0));
   public static final String RETURN = "return ";

   public MolangParser() {
      this.doCoreRemaps();
      this.registerAdditionalVariables();
   }

   private void doCoreRemaps() {
      this.functions.put("cos", CosDegrees.class);
      this.functions.put("sin", SinDegrees.class);
      this.remap("abs", "math.abs");
      this.remap("acos", "math.acos");
      this.remap("asin", "math.asin");
      this.remap("atan", "math.atan");
      this.remap("atan2", "math.atan2");
      this.remap("ceil", "math.ceil");
      this.remap("clamp", "math.clamp");
      this.remap("cos", "math.cos");
      this.remap("die_roll", "math.die_roll");
      this.remap("die_roll_integer", "math.die_roll_integer");
      this.remap("exp", "math.exp");
      this.remap("floor", "math.floor");
      this.remap("hermite_blend", "math.hermite_blend");
      this.remap("lerp", "math.lerp");
      this.remap("lerprotate", "math.lerprotate");
      this.remap("ln", "math.ln");
      this.remap("max", "math.max");
      this.remap("min", "math.min");
      this.remap("mod", "math.mod");
      this.remap("pi", "math.pi");
      this.remap("pow", "math.pow");
      this.remap("random", "math.random");
      this.remap("random_integer", "math.random_integer");
      this.remap("round", "math.round");
      this.remap("sin", "math.sin");
      this.remap("sqrt", "math.sqrt");
      this.remap("trunc", "math.trunc");
   }

   private void registerAdditionalVariables() {
      this.register(new LazyVariable("query.anim_time", 0.0));
      this.register(new LazyVariable("query.actor_count", 0.0));
      this.register(new LazyVariable("query.health", 0.0));
      this.register(new LazyVariable("query.max_health", 0.0));
      this.register(new LazyVariable("query.distance_from_camera", 0.0));
      this.register(new LazyVariable("query.yaw_speed", 0.0));
      this.register(new LazyVariable("query.is_in_water_or_rain", 0.0));
      this.register(new LazyVariable("query.is_in_water", 0.0));
      this.register(new LazyVariable("query.is_on_ground", 0.0));
      this.register(new LazyVariable("query.time_of_day", 0.0));
      this.register(new LazyVariable("query.is_on_fire", 0.0));
      this.register(new LazyVariable("query.ground_speed", 0.0));
   }

   @Override
   public void register(Variable variable) {
      if (!(variable instanceof LazyVariable)) {
         variable = LazyVariable.from(variable);
      }

      VARIABLES.put(variable.getName(), (LazyVariable)variable);
   }

   public void remap(String old, String newName) {
      this.functions.put(newName, this.functions.remove(old));
   }

   @Deprecated(
      forRemoval = true
   )
   public void setValue(String name, double value) {
      this.setValue(name, () -> value);
   }

   public void setValue(String name, DoubleSupplier value) {
      LazyVariable variable = this.getVariable(name);
      if (variable != null) {
         variable.set(value);
      }
   }

   protected LazyVariable getVariable(String name) {
      return VARIABLES.computeIfAbsent(name, key -> new LazyVariable(key, 0.0));
   }

   public LazyVariable getVariable(String name, MolangMultiStatement currentStatement) {
      if (currentStatement != null) {
         LazyVariable variable = currentStatement.locals.get(name);
         if (variable != null) {
            return variable;
         }
      }

      return this.getVariable(name);
   }

   public MolangExpression parseJson(JsonElement element) throws MolangException {
      if (!element.isJsonPrimitive()) {
         return ZERO;
      } else {
         JsonPrimitive primitive = element.getAsJsonPrimitive();
         if (primitive.isNumber()) {
            return new MolangValue(this, new Constant(primitive.getAsDouble()));
         } else if (primitive.isString()) {
            String string = primitive.getAsString();

            try {
               return new MolangValue(this, new Constant(Double.parseDouble(string)));
            } catch (NumberFormatException var5) {
               return this.parseExpression(string);
            }
         } else {
            return ZERO;
         }
      }
   }

   public MolangExpression parseExpression(String expression) throws MolangException {
      MolangMultiStatement result = null;

      for (String split : expression.toLowerCase().trim().split(";")) {
         String trimmed = split.trim();
         if (!trimmed.isEmpty()) {
            if (result == null) {
               result = new MolangMultiStatement(this);
            }

            result.expressions.add(this.parseOneLine(trimmed, result));
         }
      }

      if (result == null) {
         throw new MolangException("Molang expression cannot be blank!");
      } else {
         return result;
      }
   }

   protected MolangExpression parseOneLine(String expression, MolangMultiStatement currentStatement) throws MolangException {
      if (expression.startsWith("return ")) {
         try {
            return new MolangValue(this, this.parse(expression.substring("return ".length()))).addReturn();
         } catch (Exception var6) {
            throw new MolangException("Couldn't parse return '" + expression + "' expression!");
         }
      } else {
         try {
            List<Object> symbols = this.breakdownChars(this.breakdown(expression));
            if (symbols.size() >= 3 && symbols.get(0) instanceof String && this.isVariable(symbols.get(0)) && symbols.get(1).equals("=")) {
               String name = (String)symbols.get(0);
               symbols = symbols.subList(2, symbols.size());
               LazyVariable variable;
               if (!VARIABLES.containsKey(name) && !currentStatement.locals.containsKey(name)) {
                  currentStatement.locals.put(name, variable = new LazyVariable(name, 0.0));
               } else {
                  variable = this.getVariable(name, currentStatement);
               }

               return new MolangAssignment(this, variable, this.parseSymbolsMolang(symbols));
            } else {
               return new MolangValue(this, this.parseSymbolsMolang(symbols));
            }
         } catch (Exception var7) {
            throw new MolangException("Couldn't parse '" + expression + "' expression!");
         }
      }
   }

   private IValue parseSymbolsMolang(List<Object> symbols) throws MolangException {
      try {
         return this.parseSymbols(symbols);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new MolangException("Couldn't parse an expression!");
      }
   }

   @Override
   protected boolean isOperator(String s) {
      return super.isOperator(s) || s.equals("=");
   }
}
