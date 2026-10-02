package com.aqutheseal.celestisynth.datagen.providers;

import com.aqutheseal.celestisynth.Celestisynth;
import com.aqutheseal.celestisynth.common.recipe.celestialcrafting.CelestialShapedRecipeBuilder;
import com.aqutheseal.celestisynth.common.registry.CSBlocks;
import com.aqutheseal.celestisynth.common.registry.CSItems;
import java.util.function.Consumer;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.UpgradeRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class CSRecipeProvider extends RecipeProvider {
   public CSRecipeProvider(DataGenerator p_125973_) {
      super(p_125973_);
   }

   private ResourceLocation modLoc(String path) {
      return Celestisynth.prefix(path);
   }

   protected void m_176531_(Consumer<FinishedRecipe> consumer) {
      ShapedRecipeBuilder.m_126116_((ItemLike)CSItems.CELESTIAL_CORE.get())
         .m_126130_(" x ")
         .m_126130_("xyx")
         .m_126130_(" x ")
         .m_126124_('x', Ingredient.m_43929_(new ItemLike[]{Items.f_151049_}))
         .m_126124_('y', Ingredient.m_204132_(this.csItemTag("celestial_core_bases")))
         .m_126132_("has_item", m_125977_(Items.f_151049_))
         .m_126140_(consumer, this.modLoc("celestial_core"));
      ShapedRecipeBuilder.m_126116_((ItemLike)CSItems.CELESTIAL_CORE.get())
         .m_126130_(" x ")
         .m_126130_("xyx")
         .m_126130_(" x ")
         .m_126124_('x', Ingredient.m_43929_(new ItemLike[]{Items.f_151049_}))
         .m_126124_('y', Ingredient.m_43929_(new ItemLike[]{(ItemLike)CSItems.CELESTIAL_CORE_HEATED.get()}))
         .m_126132_("has_item", m_125977_((ItemLike)CSItems.CELESTIAL_CORE_HEATED.get()))
         .m_126140_(consumer, this.modLoc("celestial_core_dupe"));
      SimpleCookingRecipeBuilder.m_126272_(
            Ingredient.m_43929_(new ItemLike[]{(ItemLike)CSItems.CELESTIAL_CORE.get()}), (ItemLike)CSItems.CELESTIAL_CORE_HEATED.get(), 0.25F, 600
         )
         .m_126132_("has_item", m_125977_((ItemLike)CSItems.CELESTIAL_CORE.get()))
         .m_126140_(consumer, this.modLoc("celestial_core_smelting"));
      SimpleCookingRecipeBuilder.m_126267_(
            Ingredient.m_43929_(new ItemLike[]{(ItemLike)CSItems.CELESTIAL_CORE.get()}), (ItemLike)CSItems.CELESTIAL_CORE_HEATED.get(), 0.45F, 300
         )
         .m_126132_("has_item", m_125977_((ItemLike)CSItems.CELESTIAL_CORE.get()))
         .m_126140_(consumer, this.modLoc("celestial_core_blasting"));
      UpgradeRecipeBuilder.m_126385_(
            Ingredient.m_43929_(new ItemLike[]{Items.f_42418_}),
            Ingredient.m_43929_(new ItemLike[]{Items.f_42586_}),
            (Item)CSItems.SUPERNAL_NETHERITE_INGOT.get()
         )
         .m_126389_("has_item", m_125977_(Items.f_42418_))
         .m_126395_(consumer, this.modLoc("supernal_netherite_ingot_smithing"));
      UpgradeRecipeBuilder.m_126385_(
            Ingredient.m_43929_(new ItemLike[]{Items.f_42418_}),
            Ingredient.m_43929_(new ItemLike[]{(ItemLike)CSItems.CELESTIAL_CORE.get()}),
            (Item)CSItems.SUPERNAL_NETHERITE_INGOT.get()
         )
         .m_126389_("has_item", m_125977_(Items.f_42418_))
         .m_126395_(consumer, this.modLoc("supernal_netherite_ingot_smithing_from_core"));
      SimpleCookingRecipeBuilder.m_126272_(
            Ingredient.m_43929_(new ItemLike[]{(ItemLike)CSItems.SUPERNAL_NETHERITE_INGOT.get()}),
            (ItemLike)CSItems.CELESTIAL_NETHERITE_INGOT.get(),
            0.6F,
            1000
         )
         .m_126132_("has_item", m_125977_((ItemLike)CSItems.SUPERNAL_NETHERITE_INGOT.get()))
         .m_126140_(consumer, this.modLoc("celestial_netherite_ingot_smelting"));
      SimpleCookingRecipeBuilder.m_126267_(
            Ingredient.m_43929_(new ItemLike[]{(ItemLike)CSItems.SUPERNAL_NETHERITE_INGOT.get()}),
            (ItemLike)CSItems.CELESTIAL_NETHERITE_INGOT.get(),
            0.75F,
            500
         )
         .m_126132_("has_item", m_125977_((ItemLike)CSItems.SUPERNAL_NETHERITE_INGOT.get()))
         .m_126140_(consumer, this.modLoc("celestial_netherite_ingot_blasting"));
      ShapedRecipeBuilder.m_126116_((ItemLike)CSBlocks.CELESTIAL_CRAFTING_TABLE.get())
         .m_126130_("bnb")
         .m_126130_("ncn")
         .m_126130_("ooo")
         .m_126124_('b', Ingredient.m_43929_(new ItemLike[]{Items.f_42418_}))
         .m_126124_('n', Ingredient.m_43929_(new ItemLike[]{(ItemLike)CSItems.CELESTIAL_NETHERITE_INGOT.get()}))
         .m_126124_('c', Ingredient.m_43929_(new ItemLike[]{Items.f_41960_}))
         .m_126124_('o', Ingredient.m_43929_(new ItemLike[]{Items.f_41999_}))
         .m_126132_("has_item", m_125977_((ItemLike)CSItems.CELESTIAL_NETHERITE_INGOT.get()))
         .m_126140_(consumer, this.modLoc("celestial_crafting_table"));
      SimpleCookingRecipeBuilder.m_126272_(
            Ingredient.m_43929_(new ItemLike[]{(ItemLike)CSBlocks.LUNAR_STONE.get()}), (ItemLike)CSItems.LUNAR_SCRAP.get(), 0.15F, 200
         )
         .m_126132_("has_item", m_125977_((ItemLike)CSBlocks.LUNAR_STONE.get()))
         .m_126140_(consumer, this.modLoc("lunar_scrap_smelting"));
      SimpleCookingRecipeBuilder.m_126267_(
            Ingredient.m_43929_(new ItemLike[]{(ItemLike)CSBlocks.LUNAR_STONE.get()}), (ItemLike)CSItems.LUNAR_SCRAP.get(), 0.2F, 100
         )
         .m_126132_("has_item", m_125977_((ItemLike)CSBlocks.LUNAR_STONE.get()))
         .m_126140_(consumer, this.modLoc("lunar_scrap_blasting"));
      ShapelessRecipeBuilder.m_126189_((ItemLike)CSItems.EYEBOMINATION.get())
         .m_126211_(Items.f_42545_, 4)
         .m_126209_(Items.f_42593_)
         .m_126132_("has_item", m_125977_(Items.f_42545_))
         .m_126140_(consumer, this.modLoc("eyebomination"));
      CelestialShapedRecipeBuilder.shaped((ItemLike)CSItems.SOLARIS.get())
         .pattern("sns")
         .pattern("sis")
         .pattern(" n ")
         .define('s', Ingredient.m_43929_(new ItemLike[]{(ItemLike)CSBlocks.SOLAR_CRYSTAL.get()}))
         .define('n', Ingredient.m_43929_(new ItemLike[]{(ItemLike)CSItems.SUPERNAL_NETHERITE_INGOT.get()}))
         .define('i', Ingredient.m_43929_(new ItemLike[]{(ItemLike)CSItems.CELESTIAL_CORE_HEATED.get()}))
         .unlockedBy("has_item", m_125977_((ItemLike)CSBlocks.SOLAR_CRYSTAL.get()))
         .m_126140_(consumer, this.modLoc("solaris"));
      CelestialShapedRecipeBuilder.shaped((ItemLike)CSItems.CRESCENTIA.get())
         .pattern("lll")
         .pattern("l l")
         .pattern("nni")
         .define('l', Ingredient.m_43929_(new ItemLike[]{(ItemLike)CSItems.LUNAR_SCRAP.get()}))
         .define('n', Ingredient.m_43929_(new ItemLike[]{(ItemLike)CSItems.SUPERNAL_NETHERITE_INGOT.get()}))
         .define('i', Ingredient.m_43929_(new ItemLike[]{(ItemLike)CSItems.CELESTIAL_CORE_HEATED.get()}))
         .unlockedBy("has_item", m_125977_((ItemLike)CSBlocks.LUNAR_STONE.get()))
         .m_126140_(consumer, this.modLoc("crescentia"));
      CelestialShapedRecipeBuilder.shaped((ItemLike)CSItems.BREEZEBREAKER.get())
         .pattern(" nz")
         .pattern("znn")
         .pattern("iz ")
         .define('z', Ingredient.m_43929_(new ItemLike[]{(ItemLike)CSBlocks.ZEPHYR_DEPOSIT.get()}))
         .define('n', Ingredient.m_43929_(new ItemLike[]{(ItemLike)CSItems.SUPERNAL_NETHERITE_INGOT.get()}))
         .define('i', Ingredient.m_43929_(new ItemLike[]{(ItemLike)CSItems.CELESTIAL_CORE_HEATED.get()}))
         .unlockedBy("has_item", m_125977_((ItemLike)CSBlocks.ZEPHYR_DEPOSIT.get()))
         .m_126140_(consumer, this.modLoc("breezebreaker"));
      CelestialShapedRecipeBuilder.shaped((ItemLike)CSItems.POLTERGEIST.get())
         .pattern("eee")
         .pattern("ean")
         .pattern(" ni")
         .define('e', Ingredient.m_43929_(new ItemLike[]{(ItemLike)CSItems.EYEBOMINATION.get()}))
         .define('a', Ingredient.m_43929_(new ItemLike[]{Items.f_42396_}))
         .define('n', Ingredient.m_43929_(new ItemLike[]{(ItemLike)CSItems.SUPERNAL_NETHERITE_INGOT.get()}))
         .define('i', Ingredient.m_43929_(new ItemLike[]{(ItemLike)CSItems.CELESTIAL_CORE_HEATED.get()}))
         .unlockedBy("has_item", m_125977_((ItemLike)CSItems.EYEBOMINATION.get()))
         .m_126140_(consumer, this.modLoc("poltergeist"));
      CelestialShapedRecipeBuilder.shaped((ItemLike)CSItems.AQUAFLORA.get())
         .pattern("efi")
         .pattern("fif")
         .pattern("nfe")
         .define('f', Ingredient.m_204132_(ItemTags.f_13149_))
         .define('e', Ingredient.m_43929_(new ItemLike[]{Items.f_42094_}))
         .define('n', Ingredient.m_43929_(new ItemLike[]{(ItemLike)CSItems.CELESTIAL_NETHERITE_INGOT.get()}))
         .define('i', Ingredient.m_43929_(new ItemLike[]{(ItemLike)CSItems.CELESTIAL_CORE_HEATED.get()}))
         .unlockedBy("has_item", m_125977_(Items.f_42094_))
         .m_126140_(consumer, this.modLoc("aquaflora"));
      CelestialShapedRecipeBuilder.shaped((ItemLike)CSItems.RAINFALL_SERENITY.get())
         .pattern("nfs")
         .pattern("ibs")
         .pattern("nfs")
         .define('b', Ingredient.m_43929_(new ItemLike[]{Items.f_42411_}))
         .define('n', Ingredient.m_43929_(new ItemLike[]{(ItemLike)CSItems.CELESTIAL_NETHERITE_INGOT.get()}))
         .define('f', Ingredient.m_43929_(new ItemLike[]{(ItemLike)CSItems.STARSTRUCK_FEATHER.get()}))
         .define('s', Ingredient.m_43929_(new ItemLike[]{(ItemLike)CSItems.STARSTRUCK_SCRAP.get()}))
         .define('i', Ingredient.m_43929_(new ItemLike[]{(ItemLike)CSItems.CELESTIAL_CORE_HEATED.get()}))
         .unlockedBy("has_item", m_125977_((ItemLike)CSItems.STARSTRUCK_FEATHER.get()))
         .m_126140_(consumer, this.modLoc("rainfall_serenity"));
   }

   public TagKey<Item> csItemTag(String name) {
      return ItemTags.create(new ResourceLocation("celestisynth", name));
   }
}
