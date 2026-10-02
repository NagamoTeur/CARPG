package com.rolfmao.upgradednetherite;

import com.rolfmao.upgradednetherite.config.ConfigHelper;
import com.rolfmao.upgradednetherite.config.ConfigHolder;
import com.rolfmao.upgradednetherite.utils.ShieldRecipes;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(
   modid = "upgradednetherite",
   bus = Bus.MOD
)
public class ModEventSubscriber {
   private static final DeferredRegister<RecipeSerializer<?>> RECIPE = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, "upgradednetherite");
   public static final RegistryObject<SimpleRecipeSerializer<ShieldRecipes>> SHIELD_RECIPE = RECIPE.register(
      "shield_decoration", () -> ShieldRecipes.SERIALIZER
   );

   @SubscribeEvent
   public static void onModConfigEvent(ModConfigEvent event) {
      ModConfig config = event.getConfig();
      if (config.getSpec() == ConfigHolder.CLIENT_SPEC) {
         ConfigHelper.bakeClient(config);
      } else if (config.getSpec() == ConfigHolder.SERVER_SPEC) {
         ConfigHelper.bakeServer(config);
      }
   }

   public static void create(IEventBus bus) {
      RECIPE.register(bus);
   }
}
