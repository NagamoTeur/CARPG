package net.sweenus.simplyswords;

import com.google.gson.JsonObject;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import java.io.File;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.sweenus.simplyswords.client.renderer.BattleStandardDarkRenderer;
import net.sweenus.simplyswords.client.renderer.BattleStandardRenderer;
import net.sweenus.simplyswords.client.renderer.model.BattleStandardDarkModel;
import net.sweenus.simplyswords.client.renderer.model.BattleStandardModel;
import net.sweenus.simplyswords.config.Config;
import net.sweenus.simplyswords.config.SimplySwordsConfig;
import net.sweenus.simplyswords.entity.BattleStandardDarkEntity;
import net.sweenus.simplyswords.entity.BattleStandardEntity;
import net.sweenus.simplyswords.registry.EffectRegistry;
import net.sweenus.simplyswords.registry.EntityRegistry;
import net.sweenus.simplyswords.registry.ItemsRegistry;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.ModLootTableModifiers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimplySwords {
   public static final String MOD_ID = "simplyswords";
   public static final CreativeModeTab SIMPLYSWORDS = CreativeTabRegistry.create(
      new ResourceLocation("simplyswords", "simplyswords"), () -> new ItemStack((ItemLike)ItemsRegistry.RUNIC_TABLET.get())
   );
   public static final Logger LOGGER = LogManager.getLogger("simplyswords");
   public static boolean isConfigOutdated;

   public static void init() {
      SimplySwordsConfig.init();
      String version = SimplySwordsExpectPlatform.getVersion();
      String defaultConfig = String.format("{\n  \"regen_simplyswords_config_file\": false,\n  \"config_version\": %s\n}", version.substring(0, 4));
      File configFile = Config.createFile("config/simplyswords/backupconfig.json", defaultConfig, false);
      JsonObject json = Config.getJsonObject(Config.readFile(configFile));
      if (json.has("config_version")) {
         if (version.startsWith(json.get("config_version").getAsString())) {
            isConfigOutdated = false;
         } else {
            isConfigOutdated = true;
            System.out
               .println(
                  "SimplySwords: It looks like you've updated from a previous version. Please regenerate the Simply Swords configs to get the latest features."
               );
         }
      } else {
         isConfigOutdated = true;
         System.out
            .println(
               "SimplySwords: It looks like you've updated from a previous version. Please regenerate the Simply Swords configs to get the latest features."
            );
      }

      SimplySwordsConfig.generateConfigs(
         json == null || !json.has("regen_simplyswords_config_file") || json.get("regen_simplyswords_config_file").getAsBoolean()
      );
      SimplySwordsConfig.loadConfig();
      ItemsRegistry.ITEM.register();
      SoundRegistry.SOUND.register();
      EffectRegistry.EFFECT.register();
      EntityRegistry.ENTITIES.register();
      EntityAttributeRegistry.register(EntityRegistry.BATTLESTANDARD, BattleStandardEntity::createBattleStandardAttributes);
      EntityAttributeRegistry.register(EntityRegistry.BATTLESTANDARDDARK, BattleStandardDarkEntity::createBattleStandardDarkAttributes);
      ModLootTableModifiers.init();
      System.out.println(SimplySwordsExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
      EnvExecutor.runInEnv(Env.CLIENT, () -> SimplySwords.Client::initializeClient);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Client {
      public static final ModelLayerLocation BATTLESTANDARD_MODEL = new ModelLayerLocation(new ResourceLocation("battlestandard", "cube"), "main");
      public static final ModelLayerLocation BATTLESTANDARD_DARK_MODEL = new ModelLayerLocation(new ResourceLocation("battlestandarddark", "cube"), "main");

      @OnlyIn(Dist.CLIENT)
      public static void initializeClient() {
         EntityRendererRegistry.register(EntityRegistry.BATTLESTANDARD, BattleStandardRenderer::new);
         EntityModelLayerRegistry.register(BATTLESTANDARD_MODEL, BattleStandardModel::getTexturedModelData);
         EntityRendererRegistry.register(EntityRegistry.BATTLESTANDARDDARK, BattleStandardDarkRenderer::new);
         EntityModelLayerRegistry.register(BATTLESTANDARD_DARK_MODEL, BattleStandardDarkModel::getTexturedModelData);
      }
   }
}
