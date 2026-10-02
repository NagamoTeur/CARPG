package software.bernie.ars_nouveau.geckolib3.core.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.Locale;

public interface ILoopType {
   boolean isRepeatingAfterEnd();

   static ILoopType fromJson(JsonElement json) {
      if (json != null && json.isJsonPrimitive()) {
         JsonPrimitive primitive = json.getAsJsonPrimitive();
         if (primitive.isBoolean()) {
            return primitive.getAsBoolean() ? ILoopType.EDefaultLoopTypes.LOOP : ILoopType.EDefaultLoopTypes.PLAY_ONCE;
         } else {
            if (primitive.isString()) {
               String string = primitive.getAsString();
               if (string.equalsIgnoreCase("false")) {
                  return ILoopType.EDefaultLoopTypes.PLAY_ONCE;
               }

               if (string.equalsIgnoreCase("true")) {
                  return ILoopType.EDefaultLoopTypes.LOOP;
               }

               try {
                  return ILoopType.EDefaultLoopTypes.valueOf(string.toUpperCase(Locale.ROOT));
               } catch (Exception var4) {
               }
            }

            return ILoopType.EDefaultLoopTypes.PLAY_ONCE;
         }
      } else {
         return ILoopType.EDefaultLoopTypes.PLAY_ONCE;
      }
   }

   public static enum EDefaultLoopTypes implements ILoopType {
      LOOP(true),
      PLAY_ONCE,
      HOLD_ON_LAST_FRAME;

      private final boolean looping;

      private EDefaultLoopTypes(boolean looping) {
         this.looping = looping;
      }

      private EDefaultLoopTypes() {
         this(false);
      }

      @Override
      public boolean isRepeatingAfterEnd() {
         return this.looping;
      }
   }
}
