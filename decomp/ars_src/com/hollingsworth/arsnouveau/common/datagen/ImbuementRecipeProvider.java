package com.hollingsworth.arsnouveau.common.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hollingsworth.arsnouveau.common.crafting.recipes.ImbuementRecipe;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags.Items;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ImbuementRecipeProvider implements DataProvider {
   public final DataGenerator generator;
   public List<ImbuementRecipe> recipes = new ArrayList<>();
   protected static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private static final Logger LOGGER = LogManager.getLogger();

   public ImbuementRecipeProvider(DataGenerator generatorIn) {
      this.generator = generatorIn;
   }

   public void m_213708_(CachedOutput cache) throws IOException {
      this.recipes.add(new ImbuementRecipe("lapis", Ingredient.m_204132_(Items.GEMS_LAPIS), ItemsRegistry.SOURCE_GEM.get().m_7968_(), 500));
      this.recipes
         .add(
            new ImbuementRecipe(
               "amethyst", Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_151049_}), ItemsRegistry.SOURCE_GEM.get().m_7968_(), 500
            )
         );
      this.recipes
         .add(
            new ImbuementRecipe(
               "amethyst_block",
               Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_150998_}),
               new ItemStack(BlockRegistry.SOURCE_GEM_BLOCK),
               2000
            )
         );
      this.recipes
         .add(
            new ImbuementRecipe("fire_essence", RecipeDatagen.SOURCE_GEM, new ItemStack((ItemLike)ItemsRegistry.FIRE_ESSENCE.get()), 2000)
               .withPedestalItem(net.minecraft.world.item.Items.f_42409_)
               .withPedestalItem(net.minecraft.world.item.Items.f_42000_)
               .withPedestalItem(net.minecraft.world.item.Items.f_42403_)
         );
      this.recipes
         .add(
            new ImbuementRecipe("air_essence", RecipeDatagen.SOURCE_GEM, new ItemStack((ItemLike)ItemsRegistry.AIR_ESSENCE.get()), 2000)
               .withPedestalItem(net.minecraft.world.item.Items.f_42402_)
               .withPedestalItem(ItemsRegistry.WILDEN_WING)
               .withPedestalItem(Ingredient.m_204132_(ItemTags.f_13161_))
         );
      this.recipes
         .add(
            new ImbuementRecipe("earth_essence", RecipeDatagen.SOURCE_GEM, new ItemStack((ItemLike)ItemsRegistry.EARTH_ESSENCE.get()), 2000)
               .withPedestalItem(Ingredient.m_204132_(Items.INGOTS_IRON))
               .withPedestalItem(Ingredient.m_204132_(Items.SEEDS))
               .withPedestalItem(Ingredient.m_204132_(ItemTags.f_198160_))
         );
      this.recipes
         .add(
            new ImbuementRecipe("water_essence", RecipeDatagen.SOURCE_GEM, new ItemStack((ItemLike)ItemsRegistry.WATER_ESSENCE.get()), 2000)
               .withPedestalItem(net.minecraft.world.item.Items.f_42447_)
               .withPedestalItem(net.minecraft.world.item.Items.f_41981_)
               .withPedestalItem(net.minecraft.world.item.Items.f_41910_)
         );
      this.recipes
         .add(
            new ImbuementRecipe("conjuration_essence", RecipeDatagen.SOURCE_GEM, new ItemStack((ItemLike)ItemsRegistry.CONJURATION_ESSENCE.get()), 2000)
               .withPedestalItem(ItemsRegistry.WILDEN_HORN)
               .withPedestalItem(ItemsRegistry.STARBUNCLE_SHARD)
               .withPedestalItem(net.minecraft.world.item.Items.f_42517_)
         );
      this.recipes
         .add(
            new ImbuementRecipe("abjuration_essence", RecipeDatagen.SOURCE_GEM, new ItemStack((ItemLike)ItemsRegistry.ABJURATION_ESSENCE.get()), 2000)
               .withPedestalItem(net.minecraft.world.item.Items.f_42592_)
               .withPedestalItem(net.minecraft.world.item.Items.f_42501_)
               .withPedestalItem(net.minecraft.world.item.Items.f_42455_)
         );
      this.recipes
         .add(
            new ImbuementRecipe("manipulation_essence", RecipeDatagen.SOURCE_GEM, new ItemStack((ItemLike)ItemsRegistry.MANIPULATION_ESSENCE.get()), 2000)
               .withPedestalItem(net.minecraft.world.item.Items.f_42083_)
               .withPedestalItem(net.minecraft.world.item.Items.f_42451_)
               .withPedestalItem(net.minecraft.world.item.Items.f_42524_)
         );
      this.recipes
         .add(
            new ImbuementRecipe("pierce_arrow", Ingredient.m_204132_(ItemTags.f_13161_), new ItemStack((ItemLike)ItemsRegistry.PIERCE_ARROW.get()), 100)
               .withPedestalItem((ItemLike)ItemsRegistry.SOURCE_GEM.get())
               .withPedestalItem((ItemLike)ItemsRegistry.AIR_ESSENCE.get())
               .withPedestalItem((ItemLike)ItemsRegistry.WILDEN_SPIKE.get())
         );
      this.recipes
         .add(
            new ImbuementRecipe("amplify_arrow", Ingredient.m_204132_(ItemTags.f_13161_), new ItemStack((ItemLike)ItemsRegistry.AMPLIFY_ARROW.get()), 100)
               .withPedestalItem((ItemLike)ItemsRegistry.SOURCE_GEM.get())
               .withPedestalItem((ItemLike)ItemsRegistry.AIR_ESSENCE.get())
               .withPedestalItem(Ingredient.m_204132_(Items.GEMS_DIAMOND))
         );
      this.recipes
         .add(
            new ImbuementRecipe("split_arrow", Ingredient.m_204132_(ItemTags.f_13161_), new ItemStack((ItemLike)ItemsRegistry.SPLIT_ARROW.get()), 100)
               .withPedestalItem((ItemLike)ItemsRegistry.SOURCE_GEM.get())
               .withPedestalItem((ItemLike)ItemsRegistry.AIR_ESSENCE.get())
               .withPedestalItem((ItemLike)ItemsRegistry.WILDEN_HORN.get())
         );
      Path output = this.generator.m_123916_();

      for (ImbuementRecipe g : this.recipes) {
         Path path = getRecipePath(output, g.m_6423_().m_135815_());
         DataProvider.m_236072_(cache, g.asRecipe(), path);
      }
   }

   private static Path getRecipePath(Path pathIn, String str) {
      return pathIn.resolve("data/ars_nouveau/recipes/" + str + ".json");
   }

   public String m_6055_() {
      return "Imbuement";
   }
}
