package com.aqutheseal.celestisynth.datagen.providers;

import com.aqutheseal.celestisynth.Celestisynth;
import com.aqutheseal.celestisynth.common.registry.CSBlocks;
import com.aqutheseal.celestisynth.common.registry.CSItems;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile.ExistingModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CSItemModelProvider extends ItemModelProvider {
   private static final List<RegistryObject<Item>> exemptions = new ArrayList<>();

   public CSItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
      super(generator, "celestisynth", existingFileHelper);
   }

   protected void registerModels() {
      exemptions.add(CSItems.SOLARIS);
      exemptions.add(CSItems.CRESCENTIA);
      exemptions.add(CSItems.BREEZEBREAKER);
      exemptions.add(CSItems.POLTERGEIST);
      this.defaultItem(CSItems.ITEMS.getEntries());
      this.csSinglePredicatedModel(CSItems.AQUAFLORA, "item/long_blade", this.csLoc("blooming"), "item/long_blade");
      this.csCustomModel(((Block)CSBlocks.SOLAR_CRYSTAL.get()).m_5456_(), this.getMcLoc("item/generated"));
      this.block(CSBlocks.LUNAR_STONE);
      this.block(CSBlocks.ZEPHYR_DEPOSIT);
   }

   public void defaultItem(Collection<RegistryObject<Item>> items) {
      for (RegistryObject<Item> item : items) {
         if (exemptions.contains(item)) {
            return;
         }

         String name = item.getId().m_135815_();
         Item getItem = (Item)item.get();
         ResourceLocation datagenLoc = Celestisynth.prefix("item/" + name);
         ExistingModelFile modelType = !(getItem instanceof DiggerItem) && !(getItem instanceof SwordItem)
            ? this.getMcLoc("item/generated")
            : this.getMcLoc("item/handheld");
         if (getItem instanceof BlockItem) {
            return;
         }

         if (this.existingFileHelper.exists(datagenLoc, TEXTURE) || !this.existingFileHelper.exists(datagenLoc, MODEL)) {
            ((ItemModelBuilder)((ItemModelBuilder)this.getBuilder(name)).parent(modelType)).texture("layer0", "item/" + name);
         }
      }
   }

   public void block(RegistryObject<Block> blockItem) {
      String name = blockItem.getId().m_135815_();
      ((ItemModelBuilder)this.getBuilder(name)).parent(this.getCSLoc("block/" + name));
   }

   public void csCustomModel(RegistryObject<Item> item, ExistingModelFile modelPath) {
      this.csCustomModel((Item)item.get(), modelPath);
   }

   public void csCustomModel(Item item, ExistingModelFile modelType) {
      String name = ForgeRegistries.ITEMS.getKey(item).m_135815_();
      ((ItemModelBuilder)((ItemModelBuilder)this.getBuilder(name)).parent(modelType)).texture("layer0", "item/" + name);
   }

   public void csSinglePredicatedModel(RegistryObject<Item> item, String modelPath, ResourceLocation predicate, String predicatedModelPath) {
      String name = item.getId().m_135815_();
      ExistingModelFile modelType = this.getCSLoc(modelPath);
      ExistingModelFile predModelType = this.getCSLoc(predicatedModelPath);
      ((ItemModelBuilder)((ItemModelBuilder)((ItemModelBuilder)this.getBuilder(name)).parent(modelType)).texture("layer0", "item/" + name))
         .override()
         .predicate(predicate, 1.0F)
         .model(this.getBuilder(name + "_" + predicate.m_135815_()));
      ((ItemModelBuilder)((ItemModelBuilder)this.getBuilder(name + "_" + predicate.m_135815_())).parent(predModelType))
         .texture("layer0", "item/" + name + "_" + predicate.m_135815_());
   }

   public ExistingModelFile getMcLoc(String mcModel) {
      return this.getExistingFile(this.mcLoc(mcModel));
   }

   public ExistingModelFile getCSLoc(String csModel) {
      return this.getExistingFile(this.csLoc(csModel));
   }

   public ResourceLocation csLoc(String name) {
      return Celestisynth.prefix(name);
   }
}
