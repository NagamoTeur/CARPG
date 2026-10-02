package com.aqutheseal.celestisynth.common.events;

import com.aqutheseal.celestisynth.common.entity.tempestboss.TempestBoss;
import com.aqutheseal.celestisynth.common.registry.CSEntityTypes;
import com.aqutheseal.celestisynth.common.registry.CSItems;
import com.aqutheseal.celestisynth.common.registry.CSVisualTypes;
import com.aqutheseal.celestisynth.datagen.providers.CSAdvancementProvider;
import com.aqutheseal.celestisynth.datagen.providers.CSBlockModelProvider;
import com.aqutheseal.celestisynth.datagen.providers.CSBlockstateProvider;
import com.aqutheseal.celestisynth.datagen.providers.CSItemModelProvider;
import com.aqutheseal.celestisynth.datagen.providers.CSRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

public class CSCommonSetupEvents {
   public static class CSForgeSetupEvents {
   }

   public static class CSModSetupEvents {
      @SubscribeEvent
      public static void onRegistryCreatingEvent(NewRegistryEvent event) {
         event.create(new RegistryBuilder().setName(CSVisualTypes.VISUALS_KEY.m_135782_()).disableSaving());
      }

      @SubscribeEvent
      public static void onFMLCommonSetupEvent(FMLCommonSetupEvent event) {
         event.enqueueWork(
            () -> {
               BrewingRecipeRegistry.addRecipe(
                  new BrewingRecipe(
                     Ingredient.m_43929_(new ItemLike[]{Items.f_42714_}),
                     Ingredient.m_43929_(new ItemLike[]{(ItemLike)CSItems.LUNAR_SCRAP.get()}),
                     new ItemStack((ItemLike)CSItems.STARSTRUCK_SCRAP.get())
                  )
               );
               BrewingRecipeRegistry.addRecipe(
                  new BrewingRecipe(
                     Ingredient.m_43929_(new ItemLike[]{Items.f_42714_}),
                     Ingredient.m_43929_(new ItemLike[]{Items.f_42402_}),
                     new ItemStack((ItemLike)CSItems.STARSTRUCK_FEATHER.get())
                  )
               );
            }
         );
      }

      @SubscribeEvent
      public static void onEntityAttributeCreationEvent(EntityAttributeCreationEvent event) {
         event.put((EntityType)CSEntityTypes.TEMPEST.get(), TempestBoss.createAttributes().m_22265_());
      }

      @SubscribeEvent
      public static void onGatherDataEvent(GatherDataEvent event) {
         DataGenerator dataGenerator = event.getGenerator();
         ExistingFileHelper efh = event.getExistingFileHelper();
         dataGenerator.m_236039_(event.includeServer(), new CSBlockModelProvider(dataGenerator, efh));
         dataGenerator.m_236039_(event.includeServer(), new CSBlockstateProvider(dataGenerator, efh));
         dataGenerator.m_236039_(event.includeServer(), new CSItemModelProvider(dataGenerator, efh));
         dataGenerator.m_236039_(event.includeServer(), new CSRecipeProvider(dataGenerator));
         dataGenerator.m_236039_(event.includeServer(), new CSAdvancementProvider(dataGenerator, efh));
      }
   }
}
