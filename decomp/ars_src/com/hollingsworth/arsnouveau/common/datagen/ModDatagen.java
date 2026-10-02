package com.hollingsworth.arsnouveau.common.datagen;

import com.hollingsworth.arsnouveau.setup.APIRegistry;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(
   bus = Bus.MOD
)
public class ModDatagen {
   @SubscribeEvent
   public static void datagen(GatherDataEvent event) {
      APIRegistry.postInit();
      BlockTagsProvider blocktagsprovider = new BlockTagsProvider(event.getGenerator(), "ars_nouveau", event.getExistingFileHelper());
      event.getGenerator().m_236039_(event.includeClient(), new ItemModelGenerator(event.getGenerator(), "ars_nouveau", event.getExistingFileHelper()));
      event.getGenerator().m_236039_(event.includeClient(), new LangDatagen(event.getGenerator(), "ars_nouveau", "en_us"));
      event.getGenerator().m_236039_(event.includeServer(), new RecipeDatagen(event.getGenerator()));
      event.getGenerator().m_236039_(event.includeServer(), new BlockTagProvider(event.getGenerator(), event.getExistingFileHelper()));
      event.getGenerator().m_236039_(event.includeServer(), new BlockStatesDatagen(event.getGenerator(), "ars_nouveau", event.getExistingFileHelper()));
      event.getGenerator().m_236039_(event.includeServer(), new GlyphRecipeProvider(event.getGenerator()));
      event.getGenerator().m_236039_(event.includeServer(), new ApparatusRecipeProvider(event.getGenerator()));
      event.getGenerator().m_236039_(event.includeServer(), new PatchouliProvider(event.getGenerator()));
      event.getGenerator().m_236039_(event.includeServer(), new LootTableProvider(event.getGenerator()));
      event.getGenerator().m_236039_(event.includeServer(), new DefaultTableProvider(event.getGenerator()));
      event.getGenerator().m_236039_(event.includeServer(), new ImbuementRecipeProvider(event.getGenerator()));
      event.getGenerator().m_236039_(event.includeServer(), new CrushRecipeProvider(event.getGenerator()));
      event.getGenerator()
         .m_236039_(event.includeServer(), new ItemTagProvider(event.getGenerator(), blocktagsprovider, "ars_nouveau", event.getExistingFileHelper()));
      event.getGenerator().m_236039_(event.includeServer(), new EntityTagProvider(event.getGenerator(), "ars_nouveau", event.getExistingFileHelper()));
      event.getGenerator().m_236039_(event.includeServer(), new BiomeTagProvider(event.getGenerator(), "ars_nouveau", event.getExistingFileHelper()));
      event.getGenerator().m_236039_(event.includeServer(), new PlacedFeatureTagProvider(event.getGenerator(), "ars_nouveau", event.getExistingFileHelper()));
      event.getGenerator().m_236039_(event.includeServer(), new PotionEffectTagProvider(event.getGenerator(), "ars_nouveau", event.getExistingFileHelper()));
      event.getGenerator().m_236039_(event.includeServer(), new JsonDatagen(event.getGenerator()));
      event.getGenerator().m_236039_(event.includeServer(), new Advancements(event.getGenerator(), event.getExistingFileHelper()));
      event.getGenerator().m_236039_(event.includeServer(), new CasterTomeProvider(event.getGenerator()));
      event.getGenerator().m_236039_(event.includeServer(), new SummonRitualProvider(event.getGenerator()));
      event.getGenerator().m_236039_(event.includeServer(), new StructureTagProvider(event.getGenerator(), "ars_nouveau", event.getExistingFileHelper()));
      BiomeModifiersProvider.datagenModifiers(event);
   }
}
