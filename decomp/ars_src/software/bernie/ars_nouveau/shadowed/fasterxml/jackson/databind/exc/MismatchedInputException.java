package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.exc;

import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonLocation;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonParser;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JavaType;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonMappingException;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.util.ClassUtil;

public class MismatchedInputException extends JsonMappingException {
   protected Class<?> _targetType;

   protected MismatchedInputException(JsonParser p, String msg) {
      this(p, msg, (JavaType)null);
   }

   protected MismatchedInputException(JsonParser p, String msg, JsonLocation loc) {
      super(p, msg, loc);
   }

   protected MismatchedInputException(JsonParser p, String msg, Class<?> targetType) {
      super(p, msg);
      this._targetType = targetType;
   }

   protected MismatchedInputException(JsonParser p, String msg, JavaType targetType) {
      super(p, msg);
      this._targetType = ClassUtil.rawClass(targetType);
   }

   @Deprecated
   public static MismatchedInputException from(JsonParser p, String msg) {
      return from(p, (Class<?>)null, msg);
   }

   public static MismatchedInputException from(JsonParser p, JavaType targetType, String msg) {
      return new MismatchedInputException(p, msg, targetType);
   }

   public static MismatchedInputException from(JsonParser p, Class<?> targetType, String msg) {
      return new MismatchedInputException(p, msg, targetType);
   }

   public MismatchedInputException setTargetType(JavaType t) {
      this._targetType = t.getRawClass();
      return this;
   }

   public Class<?> getTargetType() {
      return this._targetType;
   }
}
