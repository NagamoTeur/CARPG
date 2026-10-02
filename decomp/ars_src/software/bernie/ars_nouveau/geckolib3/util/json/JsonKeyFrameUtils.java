package software.bernie.ars_nouveau.geckolib3.util.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Map.Entry;
import org.apache.commons.lang3.math.NumberUtils;
import software.bernie.ars_nouveau.geckolib3.GeckoLib;
import software.bernie.ars_nouveau.geckolib3.core.ConstantValue;
import software.bernie.ars_nouveau.geckolib3.core.easing.EasingType;
import software.bernie.ars_nouveau.geckolib3.core.keyframe.KeyFrame;
import software.bernie.ars_nouveau.geckolib3.core.keyframe.VectorKeyFrameList;
import software.bernie.ars_nouveau.geckolib3.core.molang.MolangException;
import software.bernie.ars_nouveau.geckolib3.core.molang.MolangParser;
import software.bernie.ars_nouveau.geckolib3.util.AnimationUtils;
import software.bernie.ars_nouveau.shadowed.eliotlash.mclib.math.IValue;

public class JsonKeyFrameUtils {
   private static VectorKeyFrameList<KeyFrame<IValue>> convertJson(List<Entry<String, JsonElement>> element, boolean isRotation, MolangParser parser) throws NumberFormatException, MolangException {
      IValue previousXValue = null;
      IValue previousYValue = null;
      IValue previousZValue = null;
      List<KeyFrame<IValue>> xKeyFrames = new ObjectArrayList();
      List<KeyFrame<IValue>> yKeyFrames = new ObjectArrayList();
      List<KeyFrame<IValue>> zKeyFrames = new ObjectArrayList();

      for (int i = 0; i < element.size(); i++) {
         Entry<String, JsonElement> keyframe = element.get(i);
         if (!keyframe.getKey().equals("easing") && !keyframe.getKey().equals("easingArgs")) {
            Entry<String, JsonElement> previousKeyFrame = i == 0 ? null : element.get(i - 1);
            double previousKeyFrameLocation = previousKeyFrame == null ? 0.0 : Double.parseDouble(previousKeyFrame.getKey());
            double currentKeyFrameLocation = NumberUtils.isCreatable(keyframe.getKey()) ? Double.parseDouble(keyframe.getKey()) : 0.0;
            double animationTimeDifference = currentKeyFrameLocation - previousKeyFrameLocation;
            JsonArray vectorJsonArray = getKeyFrameVector(keyframe.getValue());
            IValue xValue = parseExpression(parser, vectorJsonArray.get(0));
            IValue yValue = parseExpression(parser, vectorJsonArray.get(1));
            IValue zValue = parseExpression(parser, vectorJsonArray.get(2));
            IValue currentXValue = (IValue)(isRotation && xValue instanceof ConstantValue ? ConstantValue.fromDouble(Math.toRadians(-xValue.get())) : xValue);
            IValue currentYValue = (IValue)(isRotation && yValue instanceof ConstantValue ? ConstantValue.fromDouble(Math.toRadians(-yValue.get())) : yValue);
            IValue currentZValue = (IValue)(isRotation && zValue instanceof ConstantValue ? ConstantValue.fromDouble(Math.toRadians(zValue.get())) : zValue);
            KeyFrame<IValue> xKeyFrame;
            KeyFrame<IValue> yKeyFrame;
            KeyFrame<IValue> zKeyFrame;
            if (keyframe.getValue().isJsonObject() && hasEasingType(keyframe.getValue())) {
               EasingType easingType = getEasingType(keyframe.getValue());
               if (hasEasingArgs(keyframe.getValue())) {
                  List<IValue> easingArgs = getEasingArgs(keyframe.getValue());
                  xKeyFrame = new KeyFrame<>(
                     AnimationUtils.convertSecondsToTicks(animationTimeDifference),
                     i == 0 ? currentXValue : previousXValue,
                     currentXValue,
                     easingType,
                     easingArgs
                  );
                  yKeyFrame = new KeyFrame<>(
                     AnimationUtils.convertSecondsToTicks(animationTimeDifference),
                     i == 0 ? currentYValue : previousYValue,
                     currentYValue,
                     easingType,
                     easingArgs
                  );
                  zKeyFrame = new KeyFrame<>(
                     AnimationUtils.convertSecondsToTicks(animationTimeDifference),
                     i == 0 ? currentZValue : previousZValue,
                     currentZValue,
                     easingType,
                     easingArgs
                  );
               } else {
                  xKeyFrame = new KeyFrame<>(
                     AnimationUtils.convertSecondsToTicks(animationTimeDifference), i == 0 ? currentXValue : previousXValue, currentXValue, easingType
                  );
                  yKeyFrame = new KeyFrame<>(
                     AnimationUtils.convertSecondsToTicks(animationTimeDifference), i == 0 ? currentYValue : previousYValue, currentYValue, easingType
                  );
                  zKeyFrame = new KeyFrame<>(
                     AnimationUtils.convertSecondsToTicks(animationTimeDifference), i == 0 ? currentZValue : previousZValue, currentZValue, easingType
                  );
               }
            } else {
               xKeyFrame = new KeyFrame<>(AnimationUtils.convertSecondsToTicks(animationTimeDifference), i == 0 ? currentXValue : previousXValue, currentXValue);
               yKeyFrame = new KeyFrame<>(AnimationUtils.convertSecondsToTicks(animationTimeDifference), i == 0 ? currentYValue : previousYValue, currentYValue);
               zKeyFrame = new KeyFrame<>(AnimationUtils.convertSecondsToTicks(animationTimeDifference), i == 0 ? currentZValue : previousZValue, currentZValue);
            }

            previousXValue = currentXValue;
            previousYValue = currentYValue;
            previousZValue = currentZValue;
            xKeyFrames.add(xKeyFrame);
            yKeyFrames.add(yKeyFrame);
            zKeyFrames.add(zKeyFrame);
         }
      }

      return new VectorKeyFrameList<>(xKeyFrames, yKeyFrames, zKeyFrames);
   }

   private static JsonArray getKeyFrameVector(JsonElement element) {
      return element.isJsonArray() ? element.getAsJsonArray() : element.getAsJsonObject().get("vector").getAsJsonArray();
   }

   private static boolean hasEasingType(JsonElement element) {
      return element.getAsJsonObject().has("easing");
   }

   private static boolean hasEasingArgs(JsonElement element) {
      return element.getAsJsonObject().has("easingArgs");
   }

   private static EasingType getEasingType(JsonElement element) {
      String easingString = element.getAsJsonObject().get("easing").getAsString();

      try {
         String uppercaseEasingString = Character.toUpperCase(easingString.charAt(0)) + easingString.substring(1);
         return EasingType.valueOf(uppercaseEasingString);
      } catch (Exception var3) {
         GeckoLib.LOGGER.fatal("Unknown easing type: {}", easingString);
         throw new RuntimeException(var3);
      }
   }

   private static List<IValue> getEasingArgs(JsonElement element) {
      JsonObject asJsonObject = element.getAsJsonObject();
      JsonElement easingArgs = asJsonObject.get("easingArgs");
      JsonArray asJsonArray = easingArgs.getAsJsonArray();
      return JsonAnimationUtils.convertJsonArrayToList(asJsonArray);
   }

   public static VectorKeyFrameList<KeyFrame<IValue>> convertJsonToKeyFrames(List<Entry<String, JsonElement>> element, MolangParser parser) throws NumberFormatException, MolangException {
      return convertJson(element, false, parser);
   }

   public static VectorKeyFrameList<KeyFrame<IValue>> convertJsonToRotationKeyFrames(List<Entry<String, JsonElement>> element, MolangParser parser) throws NumberFormatException, MolangException {
      VectorKeyFrameList<KeyFrame<IValue>> frameList = convertJson(element, true, parser);
      return new VectorKeyFrameList<>(frameList.xKeyFrames, frameList.yKeyFrames, frameList.zKeyFrames);
   }

   public static IValue parseExpression(MolangParser parser, JsonElement element) throws MolangException {
      return (IValue)(element.getAsJsonPrimitive().isString() ? parser.parseJson(element) : ConstantValue.fromDouble(element.getAsDouble()));
   }
}
