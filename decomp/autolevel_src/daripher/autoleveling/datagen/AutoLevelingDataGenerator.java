package daripher.autoleveling.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(
   modid = "autoleveling",
   bus = Bus.MOD
)
public class AutoLevelingDataGenerator {
   @SubscribeEvent
   public static void onGatherData(GatherDataEvent event) {
      DataGenerator dataGenerator = event.getGenerator();
      ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
      dataGenerator.m_236039_(event.includeClient(), new AutoLevelingLanguageProvider(dataGenerator));
      dataGenerator.m_236039_(event.includeClient(), new AutoLevelingItemModelProvider(dataGenerator, existingFileHelper));
   }
}
