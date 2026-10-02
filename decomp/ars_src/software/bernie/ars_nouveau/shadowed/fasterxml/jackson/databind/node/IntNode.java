package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.node;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonGenerator;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonParser;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonProcessingException;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonToken;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.io.NumberOutput;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.SerializerProvider;

public class IntNode extends NumericNode {
   static final int MIN_CANONICAL = -1;
   static final int MAX_CANONICAL = 10;
   private static final IntNode[] CANONICALS;
   protected final int _value;

   public IntNode(int v) {
      this._value = v;
   }

   public static IntNode valueOf(int i) {
      return i <= 10 && i >= -1 ? CANONICALS[i - -1] : new IntNode(i);
   }

   @Override
   public JsonToken asToken() {
      return JsonToken.VALUE_NUMBER_INT;
   }

   @Override
   public JsonParser.NumberType numberType() {
      return JsonParser.NumberType.INT;
   }

   @Override
   public boolean isIntegralNumber() {
      return true;
   }

   @Override
   public boolean isInt() {
      return true;
   }

   @Override
   public boolean canConvertToInt() {
      return true;
   }

   @Override
   public boolean canConvertToLong() {
      return true;
   }

   @Override
   public Number numberValue() {
      return this._value;
   }

   @Override
   public short shortValue() {
      return (short)this._value;
   }

   @Override
   public int intValue() {
      return this._value;
   }

   @Override
   public long longValue() {
      return (long)this._value;
   }

   @Override
   public float floatValue() {
      return (float)this._value;
   }

   @Override
   public double doubleValue() {
      return (double)this._value;
   }

   @Override
   public BigDecimal decimalValue() {
      return BigDecimal.valueOf((long)this._value);
   }

   @Override
   public BigInteger bigIntegerValue() {
      return BigInteger.valueOf((long)this._value);
   }

   @Override
   public String asText() {
      return NumberOutput.toString(this._value);
   }

   @Override
   public boolean asBoolean(boolean defaultValue) {
      return this._value != 0;
   }

   @Override
   public final void serialize(JsonGenerator jg, SerializerProvider provider) throws IOException, JsonProcessingException {
      jg.writeNumber(this._value);
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (o == null) {
         return false;
      } else {
         return o instanceof IntNode ? ((IntNode)o)._value == this._value : false;
      }
   }

   @Override
   public int hashCode() {
      return this._value;
   }

   static {
      int count = 12;
      CANONICALS = new IntNode[count];

      for (int i = 0; i < count; i++) {
         CANONICALS[i] = new IntNode(-1 + i);
      }
   }
}
