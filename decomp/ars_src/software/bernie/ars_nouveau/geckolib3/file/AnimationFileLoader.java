package software.bernie.ars_nouveau.geckolib3.file;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map.Entry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ChainedJsonException;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import org.apache.commons.io.IOUtils;
import software.bernie.ars_nouveau.geckolib3.GeckoLib;
import software.bernie.ars_nouveau.geckolib3.core.builder.Animation;
import software.bernie.ars_nouveau.geckolib3.core.molang.MolangParser;
import software.bernie.ars_nouveau.geckolib3.util.json.JsonAnimationUtils;

public class AnimationFileLoader {
   public AnimationFile loadAllAnimations(MolangParser parser, ResourceLocation location, ResourceManager manager) {
      AnimationFile animationFile = new AnimationFile();
      JsonObject jsonRepresentation = this.loadFile(location, manager);

      for (Entry<String, JsonElement> entry : JsonAnimationUtils.getAnimations(jsonRepresentation)) {
         String animationName = entry.getKey();

         try {
            Animation animation = JsonAnimationUtils.deserializeJsonToAnimation(JsonAnimationUtils.getAnimation(jsonRepresentation, animationName), parser);
            animationFile.putAnimation(animationName, animation);
         } catch (ChainedJsonException var11) {
            GeckoLib.LOGGER.error("Could not load animation: {}", animationName, var11);
            throw new RuntimeException(var11);
         }
      }

      return animationFile;
   }

   private JsonObject loadFile(ResourceLocation location, ResourceManager manager) {
      String content = getResourceAsString(location, manager);
      Gson GSON = new Gson();
      return (JsonObject)GsonHelper.m_13794_(GSON, content, JsonObject.class);
   }

   public static String getResourceAsString(ResourceLocation location, ResourceManager manager) {
      try {
         String var8;
         try (InputStream inputStream = manager.m_215593_(location).m_215507_()) {
            var8 = IOUtils.toString(inputStream, Charset.defaultCharset());
         }

         return var8;
      } catch (Exception var7) {
         String message = "Couldn't load " + location;
         GeckoLib.LOGGER.error(message, var7);
         throw new RuntimeException(new FileNotFoundException(location.toString()));
      }
   }
}
