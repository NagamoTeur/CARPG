package daripher.skilltree.data.generation;

import daripher.skilltree.init.PSTItems;
import daripher.skilltree.item.gem.GemType;
import java.util.Collection;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class PSTItemModelsProvider extends ItemModelProvider {
   private final PSTGemTypesProvider gemTypesProvider;

   public PSTItemModelsProvider(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper, PSTGemTypesProvider gemTypesProvider) {
      super(dataGenerator, "skilltree", existingFileHelper);
      this.gemTypesProvider = gemTypesProvider;
   }

   protected void registerModels() {
      Collection<RegistryObject<Item>> items = PSTItems.REGISTRY.getEntries();
      items.stream().map(RegistryObject::get).forEach(this::basicItem);
      this.gemTypesProvider.getGemTypes().values().forEach(this::gemModels);
   }

   public void gemModels(GemType gemType) {
      String gemName = gemType.id().m_135815_();
      ResourceLocation texture = new ResourceLocation("skilltree", "gems/" + gemName);
      UncheckedModelFile parent = new UncheckedModelFile("item/generated");
      ((ItemModelBuilder)((ItemModelBuilder)this.getBuilder("apotheosis:item/gems/" + gemName)).parent(parent)).texture("layer0", texture);
      ((ItemModelBuilder)((ItemModelBuilder)this.getBuilder("skilltree:item/gems/" + gemName)).parent(parent)).texture("layer0", texture);
   }
}
