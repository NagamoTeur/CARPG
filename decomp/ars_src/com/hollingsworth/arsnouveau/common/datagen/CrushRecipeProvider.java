package com.hollingsworth.arsnouveau.common.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hollingsworth.arsnouveau.common.crafting.recipes.CrushRecipe;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags.Items;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CrushRecipeProvider implements DataProvider {
   public final DataGenerator generator;
   public List<CrushRecipe> recipes = new ArrayList<>();
   public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   public static final Logger LOGGER = LogManager.getLogger();

   public CrushRecipeProvider(DataGenerator generatorIn) {
      this.generator = generatorIn;
   }

   public void m_213708_(CachedOutput cache) throws IOException {
      this.recipes.add(new CrushRecipe("stone", Ingredient.m_204132_(Items.STONE)).withItems(net.minecraft.world.item.Items.f_41832_.m_7968_(), 1.0F));
      this.recipes
         .add(
            new CrushRecipe("gravel", Ingredient.m_204132_(Items.GRAVEL))
               .withItems(net.minecraft.world.item.Items.f_41830_.m_7968_(), 1.0F)
               .withItems(net.minecraft.world.item.Items.f_42484_.m_7968_(), 0.02F)
         );
      this.recipes
         .add(new CrushRecipe("cobblestone", Ingredient.m_204132_(Items.COBBLESTONE)).withItems(net.minecraft.world.item.Items.f_41832_.m_7968_(), 1.0F));
      this.recipes
         .add(
            new CrushRecipe("white_dye", Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_41950_}))
               .withItems(new ItemStack(net.minecraft.world.item.Items.f_42535_, 2))
         );
      this.recipes
         .add(
            new CrushRecipe("orange_dye", Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_41945_}))
               .withItems(new ItemStack(net.minecraft.world.item.Items.f_42536_, 2))
         );
      this.recipes
         .add(
            new CrushRecipe("magenta_dye", Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_41942_}))
               .withItems(new ItemStack(net.minecraft.world.item.Items.f_42537_, 2))
         );
      this.recipes
         .add(
            new CrushRecipe("light_blue_dye", Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_41941_}))
               .withItems(new ItemStack(net.minecraft.world.item.Items.f_42538_, 2))
         );
      this.recipes
         .add(
            new CrushRecipe("yellow_dye", Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_41939_}))
               .withItems(new ItemStack(net.minecraft.world.item.Items.f_42539_, 2))
         );
      this.recipes
         .add(
            new CrushRecipe("pink_dye", Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_41947_}))
               .withItems(new ItemStack(net.minecraft.world.item.Items.f_42489_, 2))
         );
      this.recipes
         .add(
            new CrushRecipe("light_gray_dye_oxeye", Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_41948_}))
               .withItems(new ItemStack(net.minecraft.world.item.Items.f_42491_, 2))
         );
      this.recipes
         .add(
            new CrushRecipe("light_gray_dye_azure", Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_41943_}))
               .withItems(new ItemStack(net.minecraft.world.item.Items.f_42491_, 2))
         );
      this.recipes
         .add(
            new CrushRecipe("light_gray_dye_tulip", Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_41946_}))
               .withItems(new ItemStack(net.minecraft.world.item.Items.f_42491_, 2))
         );
      this.recipes
         .add(
            new CrushRecipe("blue_dye", Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_41949_}))
               .withItems(new ItemStack(net.minecraft.world.item.Items.f_42494_, 2))
         );
      this.recipes
         .add(
            new CrushRecipe("brown_dye", Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42533_}))
               .withItems(new ItemStack(net.minecraft.world.item.Items.f_42495_, 2))
         );
      this.recipes
         .add(
            new CrushRecipe("red_dye_tulip", Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_41944_}))
               .withItems(new ItemStack(net.minecraft.world.item.Items.f_42497_, 2))
         );
      this.recipes
         .add(
            new CrushRecipe("red_dye_beetroot", Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42732_}))
               .withItems(new ItemStack(net.minecraft.world.item.Items.f_42497_, 2))
         );
      this.recipes
         .add(
            new CrushRecipe("red_dye_poppy", Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_41940_}))
               .withItems(new ItemStack(net.minecraft.world.item.Items.f_42497_, 2))
         );
      this.recipes
         .add(
            new CrushRecipe("red_dye_rose_bush", Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42208_}))
               .withItems(new ItemStack(net.minecraft.world.item.Items.f_42497_, 4))
         );
      this.recipes
         .add(
            new CrushRecipe("yellow_dye_sunflower", Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42206_}))
               .withItems(new ItemStack(net.minecraft.world.item.Items.f_42539_, 4))
         );
      this.recipes
         .add(
            new CrushRecipe("magenta_dye_lilac", Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42207_}))
               .withItems(new ItemStack(net.minecraft.world.item.Items.f_42537_, 4))
         );
      this.recipes
         .add(
            new CrushRecipe("pink_dye_peony", Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42209_}))
               .withItems(new ItemStack(net.minecraft.world.item.Items.f_42489_, 4))
         );
      this.recipes
         .add(
            new CrushRecipe("terracotta", Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42199_}))
               .withItems(net.minecraft.world.item.Items.f_41831_.m_7968_())
         );
      this.recipes
         .add(
            new CrushRecipe("sugar_cane", Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_41909_}))
               .withItems(new ItemStack(net.minecraft.world.item.Items.f_42501_, 2))
         );
      this.recipes
         .add(
            new CrushRecipe("sandstone_to_sand", Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_41856_}))
               .withItems(net.minecraft.world.item.Items.f_41830_.m_7968_())
         );
      this.recipes
         .add(
            new CrushRecipe("quartz_block_to_quartz", Ingredient.m_204132_(Items.STORAGE_BLOCKS_QUARTZ))
               .withItems(new ItemStack(net.minecraft.world.item.Items.f_42692_, 4))
         );
      Path output = this.generator.m_123916_();

      for (CrushRecipe g : this.recipes) {
         Path path = getRecipePath(output, g.m_6423_().m_135815_());
         DataProvider.m_236072_(cache, g.asRecipe(), path);
      }
   }

   private static Path getRecipePath(Path pathIn, String str) {
      return pathIn.resolve("data/ars_nouveau/recipes/" + str + ".json");
   }

   public String m_6055_() {
      return "Crush";
   }
}
