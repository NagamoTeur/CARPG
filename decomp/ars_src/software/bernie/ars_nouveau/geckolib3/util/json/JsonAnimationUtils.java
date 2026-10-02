package software.bernie.ars_nouveau.geckolib3.util.json;

import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import net.minecraft.server.ChainedJsonException;
import software.bernie.ars_nouveau.geckolib3.core.builder.Animation;
import software.bernie.ars_nouveau.geckolib3.core.builder.ILoopType;
import software.bernie.ars_nouveau.geckolib3.core.keyframe.BoneAnimation;
import software.bernie.ars_nouveau.geckolib3.core.keyframe.EventKeyFrame;
import software.bernie.ars_nouveau.geckolib3.core.keyframe.ParticleEventKeyFrame;
import software.bernie.ars_nouveau.geckolib3.core.keyframe.VectorKeyFrameList;
import software.bernie.ars_nouveau.geckolib3.core.molang.MolangParser;
import software.bernie.ars_nouveau.geckolib3.util.AnimationUtils;
import software.bernie.ars_nouveau.shadowed.eliotlash.mclib.math.IValue;

public class JsonAnimationUtils {
   private static Gson GSON = null;

   public static Set<Entry<String, JsonElement>> getAnimations(JsonObject json) {
      return json.getAsJsonObject("animations").entrySet();
   }

   public static List<Entry<String, JsonElement>> getBones(JsonObject json) {
      JsonObject bones = json.getAsJsonObject("bones");
      return (List<Entry<String, JsonElement>>)(bones == null ? List.of() : new ArrayList<>(bones.entrySet()));
   }

   public static Set<Entry<String, JsonElement>> getRotationKeyFrames(JsonObject json) {
      JsonElement rotationObject = json.get("rotation");
      if (rotationObject.isJsonArray()) {
         return ImmutableSet.of(new SimpleEntry<>("0", rotationObject.getAsJsonArray()));
      } else if (rotationObject.isJsonPrimitive()) {
         JsonPrimitive primitive = rotationObject.getAsJsonPrimitive();
         JsonElement jsonElement = getGson().toJsonTree(Arrays.asList(primitive, primitive, primitive));
         return ImmutableSet.of(new SimpleEntry<>("0", jsonElement));
      } else {
         return rotationObject.getAsJsonObject().entrySet();
      }
   }

   public static Set<Entry<String, JsonElement>> getPositionKeyFrames(JsonObject json) {
      JsonElement positionObject = json.get("position");
      if (positionObject.isJsonArray()) {
         return ImmutableSet.of(new SimpleEntry<>("0", positionObject.getAsJsonArray()));
      } else if (positionObject.isJsonPrimitive()) {
         JsonPrimitive primitive = positionObject.getAsJsonPrimitive();
         JsonElement jsonElement = getGson().toJsonTree(Arrays.asList(primitive, primitive, primitive));
         return ImmutableSet.of(new SimpleEntry<>("0", jsonElement));
      } else {
         return positionObject.getAsJsonObject().entrySet();
      }
   }

   public static Set<Entry<String, JsonElement>> getScaleKeyFrames(JsonObject json) {
      JsonElement scaleObject = json.get("scale");
      if (scaleObject.isJsonArray()) {
         return ImmutableSet.of(new SimpleEntry<>("0", scaleObject.getAsJsonArray()));
      } else if (scaleObject.isJsonPrimitive()) {
         JsonPrimitive primitive = scaleObject.getAsJsonPrimitive();
         JsonElement jsonElement = getGson().toJsonTree(Arrays.asList(primitive, primitive, primitive));
         return ImmutableSet.of(new SimpleEntry<>("0", jsonElement));
      } else {
         return scaleObject.getAsJsonObject().entrySet();
      }
   }

   public static List<Entry<String, JsonElement>> getSoundEffectFrames(JsonObject json) {
      JsonObject soundEffects = json.getAsJsonObject("sound_effects");
      return (List<Entry<String, JsonElement>>)(soundEffects == null ? List.of() : new ObjectArrayList(soundEffects.entrySet()));
   }

   public static List<Entry<String, JsonElement>> getParticleEffectFrames(JsonObject json) {
      JsonObject particleEffects = json.getAsJsonObject("particle_effects");
      return (List<Entry<String, JsonElement>>)(particleEffects == null ? List.of() : new ObjectArrayList(particleEffects.entrySet()));
   }

   public static List<Entry<String, JsonElement>> getCustomInstructionKeyFrames(JsonObject json) {
      JsonObject customInstructions = json.getAsJsonObject("timeline");
      return (List<Entry<String, JsonElement>>)(customInstructions == null ? List.of() : new ArrayList<>(customInstructions.entrySet()));
   }

   private static JsonElement getObjectByKey(Set<Entry<String, JsonElement>> json, String key) throws ChainedJsonException {
      for (Entry<String, JsonElement> entry : json) {
         if (entry.getKey().equals(key)) {
            return entry.getValue();
         }
      }

      throw new ChainedJsonException("Could not find key: " + key);
   }

   public static Entry<String, JsonElement> getAnimation(JsonObject animationFile, String animationName) throws ChainedJsonException {
      return new SimpleEntry<>(animationName, getObjectByKey(getAnimations(animationFile), animationName));
   }

   public static Set<Entry<String, JsonElement>> getObjectListAsArray(JsonObject json) {
      return json.entrySet();
   }

   public static Animation deserializeJsonToAnimation(Entry<String, JsonElement> element, MolangParser parser) throws ClassCastException, IllegalStateException {
      Animation animation = new Animation();
      JsonObject animationJsonObject = element.getValue().getAsJsonObject();
      animation.animationName = element.getKey();
      JsonElement animationLength = animationJsonObject.get("animation_length");
      animation.animationLength = animationLength == null ? -1.0 : AnimationUtils.convertSecondsToTicks(animationLength.getAsDouble());
      animation.boneAnimations = new ObjectArrayList();
      animation.loop = ILoopType.fromJson(animationJsonObject.get("loop"));

      for (Entry<String, JsonElement> keyFrame : getSoundEffectFrames(animationJsonObject)) {
         animation.soundKeyFrames
            .add(new EventKeyFrame<>(Double.parseDouble(keyFrame.getKey()) * 20.0, keyFrame.getValue().getAsJsonObject().get("effect").getAsString()));
      }

      for (Entry<String, JsonElement> keyFrame : getParticleEffectFrames(animationJsonObject)) {
         JsonObject object = keyFrame.getValue().getAsJsonObject();
         JsonElement effect = object.get("effect");
         JsonElement locator = object.get("locator");
         JsonElement pre_effect_script = object.get("pre_effect_script");
         animation.particleKeyFrames
            .add(
               new ParticleEventKeyFrame(
                  Double.parseDouble(keyFrame.getKey()) * 20.0,
                  effect == null ? "" : effect.getAsString(),
                  locator == null ? "" : locator.getAsString(),
                  pre_effect_script == null ? "" : pre_effect_script.getAsString()
               )
            );
      }

      for (Entry<String, JsonElement> keyFrame : getCustomInstructionKeyFrames(animationJsonObject)) {
         animation.customInstructionKeyframes
            .add(
               new EventKeyFrame<>(
                  Double.parseDouble(keyFrame.getKey()) * 20.0,
                  keyFrame.getValue() instanceof JsonArray
                     ? convertJsonArrayToList(keyFrame.getValue().getAsJsonArray()).toString()
                     : keyFrame.getValue().getAsString()
               )
            );
      }

      for (Entry<String, JsonElement> bone : getBones(animationJsonObject)) {
         BoneAnimation boneAnimation = new BoneAnimation(bone.getKey());
         JsonObject boneJsonObj = bone.getValue().getAsJsonObject();

         try {
            boneAnimation.scaleKeyFrames = JsonKeyFrameUtils.convertJsonToKeyFrames(new ObjectArrayList(getScaleKeyFrames(boneJsonObj)), parser);
         } catch (Exception var13) {
            boneAnimation.scaleKeyFrames = new VectorKeyFrameList<>();
         }

         try {
            boneAnimation.positionKeyFrames = JsonKeyFrameUtils.convertJsonToKeyFrames(new ObjectArrayList(getPositionKeyFrames(boneJsonObj)), parser);
         } catch (Exception var12) {
            boneAnimation.positionKeyFrames = new VectorKeyFrameList<>();
         }

         try {
            boneAnimation.rotationKeyFrames = JsonKeyFrameUtils.convertJsonToRotationKeyFrames(new ObjectArrayList(getRotationKeyFrames(boneJsonObj)), parser);
         } catch (Exception var11) {
            boneAnimation.rotationKeyFrames = new VectorKeyFrameList<>();
         }

         animation.boneAnimations.add(boneAnimation);
      }

      if (animation.animationLength == -1.0) {
         animation.animationLength = calculateLength(animation.boneAnimations);
      }

      return animation;
   }

   private static double calculateLength(List<BoneAnimation> boneAnimations) {
      double longestLength = 0.0;

      for (BoneAnimation animation : boneAnimations) {
         double xKeyframeTime = animation.rotationKeyFrames.getLastKeyframeTime();
         double yKeyframeTime = animation.positionKeyFrames.getLastKeyframeTime();
         double zKeyframeTime = animation.scaleKeyFrames.getLastKeyframeTime();
         longestLength = maxAll(longestLength, xKeyframeTime, yKeyframeTime, zKeyframeTime);
      }

      return longestLength == 0.0 ? Double.MAX_VALUE : longestLength;
   }

   static List<IValue> convertJsonArrayToList(JsonArray array) {
      return (List<IValue>)getGson().fromJson(array, ArrayList.class);
   }

   private static Gson getGson() {
      if (GSON == null) {
         GSON = new Gson();
      }

      return GSON;
   }

   public static double maxAll(double... values) {
      double max = 0.0;

      for (double value : values) {
         max = Math.max(value, max);
      }

      return max;
   }
}
