package com.hollingsworth.arsnouveau.common.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.RegistryHelper;
import com.hollingsworth.arsnouveau.api.enchanting_apparatus.ArmorUpgradeRecipe;
import com.hollingsworth.arsnouveau.api.enchanting_apparatus.EnchantingApparatusRecipe;
import com.hollingsworth.arsnouveau.api.enchanting_apparatus.IEnchantingRecipe;
import com.hollingsworth.arsnouveau.api.enchanting_apparatus.ReactiveEnchantmentRecipe;
import com.hollingsworth.arsnouveau.api.enchanting_apparatus.SpellWriteRecipe;
import com.hollingsworth.arsnouveau.common.enchantment.EnchantmentRegistry;
import com.hollingsworth.arsnouveau.common.items.PerkItem;
import com.hollingsworth.arsnouveau.common.perk.ChillingPerk;
import com.hollingsworth.arsnouveau.common.perk.DepthsPerk;
import com.hollingsworth.arsnouveau.common.perk.FeatherPerk;
import com.hollingsworth.arsnouveau.common.perk.GlidingPerk;
import com.hollingsworth.arsnouveau.common.perk.IgnitePerk;
import com.hollingsworth.arsnouveau.common.perk.JumpHeightPerk;
import com.hollingsworth.arsnouveau.common.perk.LootingPerk;
import com.hollingsworth.arsnouveau.common.perk.MagicCapacityPerk;
import com.hollingsworth.arsnouveau.common.perk.MagicResistPerk;
import com.hollingsworth.arsnouveau.common.perk.PotionDurationPerk;
import com.hollingsworth.arsnouveau.common.perk.RepairingPerk;
import com.hollingsworth.arsnouveau.common.perk.SaturationPerk;
import com.hollingsworth.arsnouveau.common.perk.SpellDamagePerk;
import com.hollingsworth.arsnouveau.common.perk.StarbunclePerk;
import com.hollingsworth.arsnouveau.common.perk.TotemPerk;
import com.hollingsworth.arsnouveau.common.perk.VampiricPerk;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags.Items;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApparatusRecipeProvider implements DataProvider {
   public final DataGenerator generator;
   public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   public static final Logger LOGGER = LogManager.getLogger();
   public List<EnchantingApparatusRecipe> recipes = new ArrayList<>();

   public ApparatusRecipeProvider(DataGenerator generatorIn) {
      this.generator = generatorIn;
   }

   public void m_213708_(CachedOutput cache) throws IOException {
      this.addEntries();
      Path output = this.generator.m_123916_();

      for (IEnchantingRecipe g : this.recipes) {
         if (g instanceof EnchantingApparatusRecipe recipe) {
            Path path = getRecipePath(output, recipe.m_6423_().m_135815_());
            DataProvider.m_236072_(cache, recipe.asRecipe(), path);
         }
      }
   }

   public ApparatusRecipeBuilder builder() {
      return ApparatusRecipeBuilder.builder();
   }

   public void addEntries() {
      this.addRecipe(
         this.builder()
            .withResult(ItemsRegistry.BELT_OF_LEVITATION)
            .withReagent(ItemsRegistry.MUNDANE_BELT)
            .withPedestalItem(4, Ingredient.m_204132_(Items.INGOTS_GOLD))
            .withPedestalItem(3, net.minecraft.world.item.Items.f_42402_)
            .withPedestalItem(ItemsRegistry.AIR_ESSENCE)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(ItemsRegistry.JAR_OF_LIGHT)
            .withReagent(net.minecraft.world.item.Items.f_42590_)
            .withPedestalItem(4, net.minecraft.world.item.Items.f_42054_)
            .withPedestalItem(2, net.minecraft.world.item.Items.f_42105_)
            .withPedestalItem(2, Ingredient.m_204132_(Items.GLASS))
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(BlockRegistry.MAGE_BLOOM_CROP)
            .withReagent(Ingredient.m_204132_(Items.SEEDS))
            .withPedestalItem(4, RecipeDatagen.SOURCE_GEM)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(ItemsRegistry.RING_OF_LESSER_DISCOUNT)
            .withReagent(ItemsRegistry.RING_OF_POTENTIAL)
            .withPedestalItem(4, Ingredient.m_204132_(Items.GEMS_DIAMOND))
            .withPedestalItem(2, Ingredient.m_204132_(Items.ENDER_PEARLS))
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(ItemsRegistry.RING_OF_GREATER_DISCOUNT)
            .withReagent(ItemsRegistry.RING_OF_LESSER_DISCOUNT)
            .withPedestalItem(4, Ingredient.m_204132_(Items.GEMS_DIAMOND))
            .withPedestalItem(2, Ingredient.m_204132_(Items.RODS_BLAZE))
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM)
            .build()
      );
      this.addRecipe(
         new EnchantingApparatusRecipe(
            new ItemStack((ItemLike)ItemsRegistry.BELT_OF_UNSTABLE_GIFTS.get()),
            Ingredient.m_43929_(new ItemLike[]{(ItemLike)ItemsRegistry.MUNDANE_BELT.get()}),
            Arrays.asList(
               Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42501_}),
               Ingredient.m_204132_(Items.CROPS_NETHER_WART),
               Ingredient.m_204132_(Items.RODS_BLAZE),
               Ingredient.m_204132_(Items.DUSTS_GLOWSTONE),
               Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42592_}),
               Ingredient.m_204132_(Items.DUSTS_REDSTONE),
               Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42543_}),
               Ingredient.m_204132_(Items.FEATHERS)
            )
         )
      );
      this.addRecipe(
         this.builder()
            .withResult(ItemsRegistry.STARBUNCLE_CHARM)
            .withReagent(ItemsRegistry.STARBUNCLE_SHARD)
            .withPedestalItem(4, Ingredient.m_204132_(Items.INGOTS_GOLD))
            .keepNbtOfReagent(true)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(ItemsRegistry.AMULET_OF_MANA_BOOST)
            .withReagent(ItemsRegistry.DULL_TRINKET)
            .withPedestalItem(3, Ingredient.m_204132_(Items.GEMS_DIAMOND))
            .withPedestalItem(5, RecipeDatagen.SOURCE_GEM)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(ItemsRegistry.AMULET_OF_MANA_REGEN)
            .withReagent(ItemsRegistry.DULL_TRINKET)
            .withPedestalItem(2, Ingredient.m_204132_(Items.GEMS_DIAMOND))
            .withPedestalItem(2, Ingredient.m_204132_(Items.INGOTS_GOLD))
            .withPedestalItem(4, RecipeDatagen.SOURCE_GEM)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(ItemsRegistry.WHIRLISPRIG_CHARM)
            .withReagent(ItemsRegistry.WHIRLISPRIG_SHARDS)
            .withPedestalItem(RecipeDatagen.SOURCE_GEM)
            .withPedestalItem(BlockRegistry.MAGE_BLOOM_CROP)
            .withPedestalItem(ItemsRegistry.MAGE_BLOOM)
            .withPedestalItem(Ingredient.m_204132_(Items.GEMS_DIAMOND))
            .withPedestalItem(net.minecraft.world.item.Items.f_42799_)
            .withPedestalItem(net.minecraft.world.item.Items.f_42800_)
            .withPedestalItem(net.minecraft.world.item.Items.f_42801_)
            .withPedestalItem(Ingredient.m_204132_(Items.SEEDS_WHEAT))
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(ItemsRegistry.VOID_JAR)
            .withReagent(net.minecraft.world.item.Items.f_42590_)
            .withPedestalItem(net.minecraft.world.item.Items.f_42448_)
            .withPedestalItem(net.minecraft.world.item.Items.f_42446_)
            .withPedestalItem(ItemsRegistry.ALLOW_ITEM_SCROLL)
            .withPedestalItem(Ingredient.m_204132_(Items.ENDER_PEARLS))
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(ItemsRegistry.DOMINION_ROD)
            .withReagent(net.minecraft.world.item.Items.f_42398_)
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM)
            .withPedestalItem(Ingredient.m_204132_(Items.INGOTS_GOLD))
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(ItemsRegistry.WIXIE_CHARM)
            .withReagent(ItemsRegistry.WIXIE_SHARD)
            .withPedestalItem(Ingredient.m_204132_(ItemTags.f_13180_))
            .withPedestalItem(Ingredient.m_204132_(Items.GEMS_EMERALD))
            .withPedestalItem(net.minecraft.world.item.Items.f_41960_)
            .withPedestalItem(net.minecraft.world.item.Items.f_42543_)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(ItemsRegistry.WAND)
            .withReagent(RecipeDatagen.ARCHWOOD_LOG)
            .withPedestalItem(4, RecipeDatagen.SOURCE_GEM)
            .withPedestalItem(2, Ingredient.m_204132_(Items.INGOTS_GOLD))
            .withPedestalItem(ItemsRegistry.AIR_ESSENCE)
            .withPedestalItem(ItemsRegistry.MANIPULATION_ESSENCE)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(ItemsRegistry.SPELL_BOW)
            .withReagent(net.minecraft.world.item.Items.f_42411_)
            .withPedestalItem(RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(Ingredient.m_204132_(Items.STORAGE_BLOCKS_GOLD))
            .withPedestalItem(ItemsRegistry.MANIPULATION_ESSENCE)
            .keepNbtOfReagent(true)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(new ItemStack((ItemLike)ItemsRegistry.POTION_FLASK.get()))
            .withReagent(Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42590_}))
            .withPedestalItem(2, ItemsRegistry.ABJURATION_ESSENCE)
            .withPedestalItem(RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(Ingredient.m_204132_(Items.STORAGE_BLOCKS_GOLD))
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(new ItemStack((ItemLike)ItemsRegistry.POTION_FLASK_EXTEND_TIME.get()))
            .withReagent(Ingredient.m_43929_(new ItemLike[]{(ItemLike)ItemsRegistry.POTION_FLASK.get()}))
            .withPedestalItem(8, ItemsRegistry.MANIPULATION_ESSENCE)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(new ItemStack((ItemLike)ItemsRegistry.POTION_FLASK_AMPLIFY.get()))
            .withReagent(Ingredient.m_43929_(new ItemLike[]{(ItemLike)ItemsRegistry.POTION_FLASK.get()}))
            .withPedestalItem(8, ItemsRegistry.ABJURATION_ESSENCE)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(new ItemStack(BlockRegistry.POTION_MELDER))
            .withReagent(Ingredient.m_43929_(new ItemLike[]{BlockRegistry.POTION_JAR}))
            .withPedestalItem(2, ItemsRegistry.ABJURATION_ESSENCE)
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_GOLD))
            .withPedestalItem(4, Ingredient.m_204132_(Items.RODS_BLAZE))
            .build()
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(4, Ingredient.m_204132_(ItemTags.f_13156_))
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44971_, 1, 5000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(4, Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42593_}))
            .withPedestalItem(ItemsRegistry.AIR_ESSENCE)
            .withPedestalItem(ItemsRegistry.EARTH_ESSENCE)
            .withPedestalItem(ItemsRegistry.FIRE_ESSENCE)
            .withPedestalItem(ItemsRegistry.WATER_ESSENCE)
            .buildEnchantmentRecipe((Enchantment)EnchantmentRegistry.REACTIVE_ENCHANTMENT.get(), 2, 6000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(4, Ingredient.m_204132_(Items.GEMS_EMERALD))
            .withPedestalItem(1, Ingredient.m_204132_(Items.ENDER_PEARLS))
            .withPedestalItem(ItemsRegistry.ABJURATION_ESSENCE)
            .withPedestalItem(ItemsRegistry.CONJURATION_ESSENCE)
            .withPedestalItem(ItemsRegistry.MANIPULATION_ESSENCE)
            .buildEnchantmentRecipe((Enchantment)EnchantmentRegistry.REACTIVE_ENCHANTMENT.get(), 3, 9000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42591_}))
            .withPedestalItem(1, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(1, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44979_, 1, 2000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(3, Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42591_}))
            .withPedestalItem(1, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(1, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44979_, 2, 3500)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(4, Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42592_}))
            .withPedestalItem(1, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(1, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44979_, 3, 5000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(4, Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42592_}))
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(1, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44979_, 4, 6500)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(4, Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42592_}))
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44979_, 5, 8000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_IRON))
            .withPedestalItem(1, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(0, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44968_, 1, 2000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_IRON))
            .withPedestalItem(1, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44968_, 2, 4000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, Ingredient.m_204132_(Items.GEMS_DIAMOND))
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(2, Ingredient.m_204132_(Items.OBSIDIAN))
            .buildEnchantmentRecipe(Enchantments.f_44968_, 3, 6000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(4, Ingredient.m_204132_(Items.GEMS_DIAMOND))
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_GOLD))
            .buildEnchantmentRecipe(Enchantments.f_44968_, 4, 8000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, ItemsRegistry.WATER_ESSENCE)
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .buildEnchantmentRecipe(Enchantments.f_44973_, 1, 3000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, ItemsRegistry.WATER_ESSENCE)
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(2, Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42695_}))
            .buildEnchantmentRecipe(Enchantments.f_44973_, 2, 6000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(4, ItemsRegistry.WATER_ESSENCE)
            .withPedestalItem(3, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(1, Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42715_}))
            .buildEnchantmentRecipe(Enchantments.f_44973_, 3, 9000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(1, Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42501_}))
            .withPedestalItem(1, Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42385_}))
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .buildEnchantmentRecipe(Enchantments.f_44984_, 1, 2000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(1, Ingredient.m_204132_(Items.STORAGE_BLOCKS_REDSTONE))
            .withPedestalItem(1, Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42432_}))
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .buildEnchantmentRecipe(Enchantments.f_44984_, 2, 3500)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_REDSTONE))
            .withPedestalItem(1, Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42432_}))
            .withPedestalItem(3, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(2, Ingredient.m_204132_(Items.OBSIDIAN))
            .buildEnchantmentRecipe(Enchantments.f_44984_, 3, 5000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_REDSTONE))
            .withPedestalItem(1, Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42390_}))
            .withPedestalItem(1, Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42384_}))
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(2, Ingredient.m_204132_(Items.OBSIDIAN))
            .buildEnchantmentRecipe(Enchantments.f_44984_, 4, 6500)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_REDSTONE))
            .withPedestalItem(1, Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42390_}))
            .withPedestalItem(1, Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42389_}))
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44984_, 5, 8000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(1, ItemsRegistry.AIR_ESSENCE)
            .withPedestalItem(1, Ingredient.m_204132_(Items.SLIMEBALLS))
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .buildEnchantmentRecipe(Enchantments.f_44967_, 1, 2000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, ItemsRegistry.AIR_ESSENCE)
            .withPedestalItem(1, Ingredient.m_204132_(Items.SLIMEBALLS))
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .buildEnchantmentRecipe(Enchantments.f_44967_, 2, 3500)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(3, ItemsRegistry.AIR_ESSENCE)
            .withPedestalItem(1, Ingredient.m_204132_(Items.SLIMEBALLS))
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44967_, 3, 5000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(4, ItemsRegistry.AIR_ESSENCE)
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44967_, 4, 6500)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(1, ItemsRegistry.FIRE_ESSENCE)
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44981_, 1, 2000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, ItemsRegistry.FIRE_ESSENCE)
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44981_, 2, 4000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(1, ItemsRegistry.FIRE_ESSENCE)
            .withPedestalItem(1, ItemsRegistry.ABJURATION_ESSENCE)
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .buildEnchantmentRecipe(Enchantments.f_44966_, 1, 2000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, ItemsRegistry.FIRE_ESSENCE)
            .withPedestalItem(1, ItemsRegistry.ABJURATION_ESSENCE)
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .buildEnchantmentRecipe(Enchantments.f_44966_, 2, 3500)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, ItemsRegistry.FIRE_ESSENCE)
            .withPedestalItem(1, ItemsRegistry.ABJURATION_ESSENCE)
            .withPedestalItem(3, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(1, Ingredient.m_204132_(Items.RODS_BLAZE))
            .buildEnchantmentRecipe(Enchantments.f_44966_, 3, 5000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, ItemsRegistry.FIRE_ESSENCE)
            .withPedestalItem(2, ItemsRegistry.ABJURATION_ESSENCE)
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(2, Ingredient.m_204132_(Items.RODS_BLAZE))
            .buildEnchantmentRecipe(Enchantments.f_44966_, 4, 6500)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(3, ItemsRegistry.FIRE_ESSENCE)
            .withPedestalItem(3, ItemsRegistry.ABJURATION_ESSENCE)
            .withPedestalItem(1, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(1, Ingredient.m_204132_(Items.RODS_BLAZE))
            .buildEnchantmentRecipe(Enchantments.f_44966_, 5, 8000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(1, ItemsRegistry.FIRE_ESSENCE)
            .withPedestalItem(1, ItemsRegistry.AIR_ESSENCE)
            .withPedestalItem(1, Ingredient.m_204132_(Items.RODS_BLAZE))
            .buildEnchantmentRecipe(Enchantments.f_44990_, 1, 5000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(1, ItemsRegistry.EARTH_ESSENCE)
            .withPedestalItem(1, Ingredient.m_204132_(Items.GEMS_DIAMOND))
            .withPedestalItem(6, RecipeDatagen.SOURCE_GEM_BLOCK)
            .buildEnchantmentRecipe(Enchantments.f_44987_, 1, 6000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, ItemsRegistry.EARTH_ESSENCE)
            .withPedestalItem(4, Ingredient.m_204132_(Items.GEMS_DIAMOND))
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .buildEnchantmentRecipe(Enchantments.f_44987_, 2, 8000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(3, ItemsRegistry.EARTH_ESSENCE)
            .withPedestalItem(1, Ingredient.m_204132_(Items.STORAGE_BLOCKS_DIAMOND))
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .buildEnchantmentRecipe(Enchantments.f_44987_, 3, 9000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(7, ItemsRegistry.AIR_ESSENCE)
            .withPedestalItem(1, Ingredient.m_204132_(Items.STORAGE_BLOCKS_DIAMOND))
            .buildEnchantmentRecipe(Enchantments.f_44952_, 1, 9000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(1, ItemsRegistry.MANIPULATION_ESSENCE)
            .withPedestalItem(1, ItemsRegistry.AIR_ESSENCE)
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .buildEnchantmentRecipe(Enchantments.f_44980_, 1, 2000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(1, ItemsRegistry.MANIPULATION_ESSENCE)
            .withPedestalItem(2, ItemsRegistry.AIR_ESSENCE)
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .buildEnchantmentRecipe(Enchantments.f_44980_, 2, 4000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(1, ItemsRegistry.EARTH_ESSENCE)
            .withPedestalItem(1, Ingredient.m_204132_(Items.GEMS_EMERALD))
            .withPedestalItem(6, RecipeDatagen.SOURCE_GEM_BLOCK)
            .buildEnchantmentRecipe(Enchantments.f_44982_, 1, 6000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, ItemsRegistry.EARTH_ESSENCE)
            .withPedestalItem(4, Ingredient.m_204132_(Items.GEMS_EMERALD))
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .buildEnchantmentRecipe(Enchantments.f_44982_, 2, 8000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(3, ItemsRegistry.EARTH_ESSENCE)
            .withPedestalItem(1, Ingredient.m_204132_(Items.STORAGE_BLOCKS_EMERALD))
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .buildEnchantmentRecipe(Enchantments.f_44982_, 3, 9000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(3, ItemsRegistry.MANIPULATION_ESSENCE)
            .withPedestalItem(5, RecipeDatagen.SOURCE_GEM_BLOCK)
            .buildEnchantmentRecipe(Enchantments.f_44959_, 1, 9000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(1, ItemsRegistry.MANIPULATION_ESSENCE)
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44961_, 1, 2500)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, ItemsRegistry.MANIPULATION_ESSENCE)
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44961_, 2, 5000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(3, ItemsRegistry.MANIPULATION_ESSENCE)
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44961_, 3, 7500)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(4, ItemsRegistry.MANIPULATION_ESSENCE)
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44961_, 4, 9000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(1, Ingredient.m_204132_(Items.GEMS_DIAMOND))
            .withPedestalItem(1, ItemsRegistry.AIR_ESSENCE)
            .withPedestalItem(1, RecipeDatagen.SOURCE_GEM_BLOCK)
            .buildEnchantmentRecipe(Enchantments.f_44988_, 1, 2000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, Ingredient.m_204132_(Items.GEMS_DIAMOND))
            .withPedestalItem(1, ItemsRegistry.AIR_ESSENCE)
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .buildEnchantmentRecipe(Enchantments.f_44988_, 2, 3500)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(3, Ingredient.m_204132_(Items.GEMS_DIAMOND))
            .withPedestalItem(1, ItemsRegistry.AIR_ESSENCE)
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44988_, 3, 5000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(4, Ingredient.m_204132_(Items.GEMS_DIAMOND))
            .withPedestalItem(1, ItemsRegistry.AIR_ESSENCE)
            .withPedestalItem(1, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44988_, 4, 6500)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(5, Ingredient.m_204132_(Items.GEMS_DIAMOND))
            .withPedestalItem(1, ItemsRegistry.AIR_ESSENCE)
            .withPedestalItem(1, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(1, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44988_, 5, 9000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(1, ItemsRegistry.AIR_ESSENCE)
            .withPedestalItem(1, ItemsRegistry.ABJURATION_ESSENCE)
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .buildEnchantmentRecipe(Enchantments.f_44969_, 1, 2000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, ItemsRegistry.AIR_ESSENCE)
            .withPedestalItem(1, ItemsRegistry.ABJURATION_ESSENCE)
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .buildEnchantmentRecipe(Enchantments.f_44969_, 2, 3500)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, ItemsRegistry.AIR_ESSENCE)
            .withPedestalItem(1, ItemsRegistry.ABJURATION_ESSENCE)
            .withPedestalItem(3, RecipeDatagen.SOURCE_GEM_BLOCK)
            .buildEnchantmentRecipe(Enchantments.f_44969_, 3, 5000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, ItemsRegistry.AIR_ESSENCE)
            .withPedestalItem(2, ItemsRegistry.ABJURATION_ESSENCE)
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .buildEnchantmentRecipe(Enchantments.f_44969_, 4, 6500)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(3, ItemsRegistry.AIR_ESSENCE)
            .withPedestalItem(3, ItemsRegistry.ABJURATION_ESSENCE)
            .withPedestalItem(1, RecipeDatagen.SOURCE_GEM_BLOCK)
            .buildEnchantmentRecipe(Enchantments.f_44969_, 5, 8000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(1, ItemsRegistry.MANIPULATION_ESSENCE)
            .withPedestalItem(1, ItemsRegistry.ABJURATION_ESSENCE)
            .withPedestalItem(1, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .buildEnchantmentRecipe(Enchantments.f_44965_, 1, 2000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, ItemsRegistry.MANIPULATION_ESSENCE)
            .withPedestalItem(1, ItemsRegistry.ABJURATION_ESSENCE)
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44965_, 2, 3500)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, ItemsRegistry.MANIPULATION_ESSENCE)
            .withPedestalItem(1, ItemsRegistry.ABJURATION_ESSENCE)
            .withPedestalItem(3, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44965_, 3, 5000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, ItemsRegistry.MANIPULATION_ESSENCE)
            .withPedestalItem(2, ItemsRegistry.ABJURATION_ESSENCE)
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44965_, 4, 6500)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(3, ItemsRegistry.MANIPULATION_ESSENCE)
            .withPedestalItem(3, ItemsRegistry.ABJURATION_ESSENCE)
            .withPedestalItem(1, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(1, Ingredient.m_204132_(Items.STORAGE_BLOCKS_DIAMOND))
            .buildEnchantmentRecipe(Enchantments.f_44965_, 5, 8000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, ItemsRegistry.AIR_ESSENCE)
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .buildEnchantmentRecipe(Enchantments.f_44989_, 1, 2000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(3, ItemsRegistry.AIR_ESSENCE)
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .buildEnchantmentRecipe(Enchantments.f_44989_, 2, 4000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(1, ItemsRegistry.AIR_ESSENCE)
            .withPedestalItem(1, ItemsRegistry.ABJURATION_ESSENCE)
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .buildEnchantmentRecipe(Enchantments.f_44960_, 1, 2000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(1, ItemsRegistry.AIR_ESSENCE)
            .withPedestalItem(2, ItemsRegistry.ABJURATION_ESSENCE)
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .buildEnchantmentRecipe(Enchantments.f_44960_, 2, 4000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(1, ItemsRegistry.AIR_ESSENCE)
            .withPedestalItem(2, ItemsRegistry.ABJURATION_ESSENCE)
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .buildEnchantmentRecipe(Enchantments.f_44960_, 3, 6000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(1, ItemsRegistry.WATER_ESSENCE)
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .buildEnchantmentRecipe(Enchantments.f_44970_, 1, 3000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(4, ItemsRegistry.WATER_ESSENCE)
            .withPedestalItem(4, RecipeDatagen.SOURCE_GEM_BLOCK)
            .buildEnchantmentRecipe(Enchantments.f_44970_, 2, 6000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(6, ItemsRegistry.WATER_ESSENCE)
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .buildEnchantmentRecipe(Enchantments.f_44970_, 3, 9000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_QUARTZ))
            .withPedestalItem(1, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(1, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44977_, 1, 2000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_QUARTZ))
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(1, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44977_, 2, 3500)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_QUARTZ))
            .withPedestalItem(1, Ingredient.m_204132_(Items.GEMS_DIAMOND))
            .withPedestalItem(1, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44977_, 3, 5000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_QUARTZ))
            .withPedestalItem(2, Ingredient.m_204132_(Items.GEMS_DIAMOND))
            .withPedestalItem(1, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44977_, 4, 6500)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_QUARTZ))
            .withPedestalItem(2, Ingredient.m_204132_(Items.GEMS_DIAMOND))
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44977_, 5, 8000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, Ingredient.m_204132_(Items.GEMS_EMERALD))
            .withPedestalItem(2, Ingredient.m_204132_(Items.GEMS_DIAMOND))
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44985_, 1, 9000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42262_}))
            .withPedestalItem(1, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(1, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44978_, 1, 2000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(3, Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42262_}))
            .withPedestalItem(1, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(1, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44978_, 2, 3500)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(4, Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42262_}))
            .withPedestalItem(1, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(1, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44978_, 3, 5000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(4, Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42262_}))
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(1, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44978_, 4, 6500)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(4, Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42262_}))
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44978_, 5, 8000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(1, ItemsRegistry.MANIPULATION_ESSENCE)
            .withPedestalItem(1, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(1, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44983_, 1, 2000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, ItemsRegistry.MANIPULATION_ESSENCE)
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44983_, 2, 3500)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(3, ItemsRegistry.MANIPULATION_ESSENCE)
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(3, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44983_, 3, 5000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(1, Ingredient.m_43929_(new ItemLike[]{(ItemLike)ItemsRegistry.WILDEN_SPIKE.get()}))
            .withPedestalItem(1, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(1, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44972_, 1, 2000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, Ingredient.m_43929_(new ItemLike[]{(ItemLike)ItemsRegistry.WILDEN_SPIKE.get()}))
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44972_, 2, 3500)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(3, Ingredient.m_43929_(new ItemLike[]{(ItemLike)ItemsRegistry.WILDEN_SPIKE.get()}))
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(3, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44972_, 3, 5000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(1, ItemsRegistry.ABJURATION_ESSENCE)
            .withPedestalItem(1, Ingredient.m_204132_(Items.GEMS_DIAMOND))
            .withPedestalItem(1, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(1, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44986_, 1, 2000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(1, ItemsRegistry.ABJURATION_ESSENCE)
            .withPedestalItem(2, Ingredient.m_204132_(Items.GEMS_DIAMOND))
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(1, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44986_, 2, 3500)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(1, ItemsRegistry.ABJURATION_ESSENCE)
            .withPedestalItem(3, Ingredient.m_204132_(Items.GEMS_DIAMOND))
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe(Enchantments.f_44986_, 3, 5000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(1, BlockRegistry.SOURCEBERRY_BUSH)
            .withPedestalItem(4, RecipeDatagen.SOURCE_GEM_BLOCK)
            .buildEnchantmentRecipe((Enchantment)EnchantmentRegistry.MANA_BOOST_ENCHANTMENT.get(), 1, 2000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(1, BlockRegistry.SOURCEBERRY_BUSH)
            .withPedestalItem(1, Ingredient.m_204132_(Items.GEMS_DIAMOND))
            .withPedestalItem(4, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe((Enchantment)EnchantmentRegistry.MANA_BOOST_ENCHANTMENT.get(), 2, 3500)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(1, BlockRegistry.SOURCEBERRY_BUSH)
            .withPedestalItem(2, Ingredient.m_204132_(Items.GEMS_DIAMOND))
            .withPedestalItem(4, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(1, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe((Enchantment)EnchantmentRegistry.MANA_BOOST_ENCHANTMENT.get(), 3, 5000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, BlockRegistry.SOURCEBERRY_BUSH)
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe((Enchantment)EnchantmentRegistry.MANA_REGEN_ENCHANTMENT.get(), 1, 2000)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, BlockRegistry.SOURCEBERRY_BUSH)
            .withPedestalItem(2, ItemsRegistry.ABJURATION_ESSENCE)
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe((Enchantment)EnchantmentRegistry.MANA_REGEN_ENCHANTMENT.get(), 2, 3500)
      );
      this.addRecipe(
         this.builder()
            .withPedestalItem(2, BlockRegistry.SOURCEBERRY_BUSH)
            .withPedestalItem(3, ItemsRegistry.ABJURATION_ESSENCE)
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(1, Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS))
            .buildEnchantmentRecipe((Enchantment)EnchantmentRegistry.MANA_REGEN_ENCHANTMENT.get(), 3, 5000)
      );
      this.addRecipe(
         this.builder()
            .withResult(ItemsRegistry.ENCHANTERS_SWORD)
            .withReagent(net.minecraft.world.item.Items.f_42388_)
            .withPedestalItem(1, Ingredient.m_204132_(Items.GEMS_DIAMOND))
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_GOLD))
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .keepNbtOfReagent(true)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(ItemsRegistry.ENCHANTERS_SHIELD)
            .withReagent(net.minecraft.world.item.Items.f_42740_)
            .withPedestalItem(2, Ingredient.m_204132_(Items.STORAGE_BLOCKS_GOLD))
            .withPedestalItem(2, RecipeDatagen.SOURCE_GEM_BLOCK)
            .keepNbtOfReagent(true)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(ItemsRegistry.DRYGMY_CHARM)
            .withReagent(ItemsRegistry.DRYGMY_SHARD)
            .withPedestalItem(Ingredient.m_204132_(ItemTags.f_13156_))
            .withPedestalItem(net.minecraft.world.item.Items.f_42405_)
            .withPedestalItem(net.minecraft.world.item.Items.f_42410_)
            .withPedestalItem(net.minecraft.world.item.Items.f_42619_)
            .withPedestalItem(Ingredient.m_204132_(Items.SEEDS))
            .withPedestalItem(3, RecipeDatagen.SOURCE_GEM)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(ItemsRegistry.SUMMONING_FOCUS)
            .withReagent(RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(ItemsRegistry.WILDEN_HORN)
            .withPedestalItem(ItemsRegistry.WILDEN_SPIKE)
            .withPedestalItem(ItemsRegistry.WILDEN_WING)
            .withPedestalItem(ItemsRegistry.WILDEN_TRIBUTE)
            .withPedestalItem(Ingredient.m_204132_(Items.INGOTS_GOLD))
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(BlockRegistry.RELAY_SPLITTER)
            .withReagent(BlockRegistry.RELAY)
            .withPedestalItem(4, Ingredient.m_204132_(Items.GEMS_QUARTZ))
            .withPedestalItem(4, Ingredient.m_204132_(Items.GEMS_LAPIS))
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(BlockRegistry.RELAY_WARP)
            .withReagent(BlockRegistry.RELAY)
            .withPedestalItem(4, Ingredient.m_204132_(Items.ENDER_PEARLS))
            .withPedestalItem(4, Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42731_}))
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(BlockRegistry.RELAY_DEPOSIT)
            .withReagent(BlockRegistry.RELAY)
            .withPedestalItem(4, Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42155_}))
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(ItemsRegistry.ENCHANTERS_MIRROR)
            .withReagent(RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(2, Ingredient.m_204132_(Items.GLASS))
            .withPedestalItem(2, ItemsRegistry.MANIPULATION_ESSENCE)
            .withPedestalItem(2, RecipeDatagen.ARCHWOOD_LOG)
            .withPedestalItem(2, Ingredient.m_204132_(Items.INGOTS_GOLD))
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(BlockRegistry.TIMER_SPELL_TURRET)
            .withReagent(BlockRegistry.BASIC_SPELL_TURRET)
            .withPedestalItem(net.minecraft.world.item.Items.f_42524_)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(BlockRegistry.ENCHANTED_SPELL_TURRET)
            .withReagent(BlockRegistry.BASIC_SPELL_TURRET)
            .withPedestalItem(RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(2, Ingredient.m_204132_(Items.RODS_BLAZE))
            .build()
      );
      List<Ingredient> reactiveIngredients = new ArrayList<>();
      reactiveIngredients.add(Ingredient.m_43929_(new ItemLike[]{(ItemLike)ItemsRegistry.SPELL_PARCHMENT.get()}));
      reactiveIngredients.add(Ingredient.m_204132_(Items.STORAGE_BLOCKS_LAPIS));
      reactiveIngredients.add(RecipeDatagen.SOURCE_GEM_BLOCK);
      this.addRecipe(new ReactiveEnchantmentRecipe(reactiveIngredients, 3000));
      List<Ingredient> spellWriteList = new ArrayList<>();
      spellWriteList.add(Ingredient.m_43929_(new ItemLike[]{(ItemLike)ItemsRegistry.SPELL_PARCHMENT.get()}));
      this.addRecipe(new SpellWriteRecipe(spellWriteList));
      this.addRecipe(
         this.builder()
            .withPedestalItem(4, Ingredient.m_204132_(Items.GEMS_DIAMOND))
            .withPedestalItem(1, Ingredient.m_204132_(Items.ENDER_PEARLS))
            .withPedestalItem(1, Ingredient.m_43929_(new ItemLike[]{(ItemLike)ItemsRegistry.WILDEN_TRIBUTE.get()}))
            .buildEnchantmentRecipe((Enchantment)EnchantmentRegistry.REACTIVE_ENCHANTMENT.get(), 4, 9000)
      );
      this.addRecipe(
         this.builder()
            .withResult(BlockRegistry.RELAY_COLLECTOR)
            .withReagent(BlockRegistry.RELAY)
            .withPedestalItem(4, Ingredient.m_204132_(Items.CHESTS))
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(BlockRegistry.SCRYERS_OCULUS)
            .withReagent(net.minecraft.world.item.Items.f_42545_)
            .withPedestalItem(Blocks.f_50455_)
            .withPedestalItem(net.minecraft.world.item.Items.f_151059_)
            .withPedestalItem(RecipeDatagen.SOURCE_GEM_BLOCK)
            .withPedestalItem(Ingredient.m_204132_(Items.GEMS_DIAMOND))
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(ItemsRegistry.SHAPERS_FOCUS)
            .withReagent(ItemsRegistry.MANIPULATION_ESSENCE)
            .withPedestalItem(1, Ingredient.m_204132_(Items.INGOTS_GOLD))
            .withPedestalItem(net.minecraft.world.item.Items.f_41869_)
            .withPedestalItem(net.minecraft.world.item.Items.f_42204_)
            .withPedestalItem(Ingredient.m_204132_(Items.GEMS_DIAMOND))
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(ItemsRegistry.ALCHEMISTS_CROWN)
            .withReagent(net.minecraft.world.item.Items.f_42476_)
            .withPedestalItem(3, net.minecraft.world.item.Items.f_42590_)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(BlockRegistry.POTION_DIFFUSER)
            .withReagent(Blocks.f_50683_)
            .withPedestalItem(3, Ingredient.m_204132_(Items.INGOTS_GOLD))
            .withPedestalItem(3, BlockRegistry.ARCHWOOD_PLANK)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(ItemsRegistry.SPLASH_LAUNCHER)
            .withReagent(net.minecraft.world.item.Items.f_41855_)
            .withPedestalItem(2, Items.INGOTS_GOLD)
            .withPedestalItem(2, Items.RODS_BLAZE)
            .withPedestalItem(4, Items.GUNPOWDER)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(ItemsRegistry.LINGERING_LAUNCHER)
            .withReagent(ItemsRegistry.SPLASH_LAUNCHER)
            .withPedestalItem(net.minecraft.world.item.Items.f_42735_)
            .withPedestalItem(2, ItemsRegistry.AIR_ESSENCE)
            .build()
      );
      this.addRecipe(new ArmorUpgradeRecipe(List.of(Ingredient.m_204132_(Items.RODS_BLAZE), Ingredient.m_204132_(Items.RODS_BLAZE)), 2500, 1));
      this.addRecipe(
         new ArmorUpgradeRecipe(
            List.of(
               Ingredient.m_204132_(Items.ENDER_PEARLS),
               Ingredient.m_204132_(Items.ENDER_PEARLS),
               Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42730_})
            ),
            5000,
            2
         )
      );
      this.addRecipe(
         this.builder()
            .withResult(this.getPerkItem(StarbunclePerk.INSTANCE.getRegistryName()))
            .withReagent(ItemsRegistry.BLANK_THREAD)
            .withPedestalItem(3, ItemsRegistry.STARBUNCLE_SHARD)
            .withPedestalItem(3, net.minecraft.world.item.Items.f_42501_)
            .withPedestalItem(2, ItemsRegistry.MANIPULATION_ESSENCE)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(this.getPerkItem(FeatherPerk.INSTANCE.getRegistryName()))
            .withReagent(ItemsRegistry.BLANK_THREAD)
            .withPedestalItem(4, Items.FEATHERS)
            .withPedestalItem(2, ItemsRegistry.ABJURATION_ESSENCE)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(this.getPerkItem(JumpHeightPerk.INSTANCE.getRegistryName()))
            .withReagent(ItemsRegistry.BLANK_THREAD)
            .withPedestalItem(net.minecraft.world.item.Items.f_42591_)
            .withPedestalItem(3, net.minecraft.world.item.Items.f_42649_)
            .withPedestalItem(2, ItemsRegistry.AIR_ESSENCE)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(this.getPerkItem(SaturationPerk.INSTANCE.getRegistryName()))
            .withReagent(ItemsRegistry.BLANK_THREAD)
            .withPedestalItem(3, ItemsRegistry.WHIRLISPRIG_SHARDS)
            .withPedestalItem(2, ItemsRegistry.EARTH_ESSENCE)
            .withPedestalItem(net.minecraft.world.item.Items.f_42436_)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(this.getPerkItem(DepthsPerk.INSTANCE.getRegistryName()))
            .withReagent(ItemsRegistry.BLANK_THREAD)
            .withPedestalItem(3, ItemTags.f_13156_)
            .withPedestalItem(2, ItemsRegistry.WATER_ESSENCE)
            .withPedestalItem(net.minecraft.world.item.Items.f_42529_)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(this.getPerkItem(RepairingPerk.INSTANCE.getRegistryName()))
            .withReagent(ItemsRegistry.BLANK_THREAD)
            .withPedestalItem(net.minecraft.world.item.Items.f_42146_)
            .withPedestalItem(2, ItemsRegistry.MANIPULATION_ESSENCE)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(this.getPerkItem(PotionDurationPerk.INSTANCE.getRegistryName()))
            .withReagent(ItemsRegistry.BLANK_THREAD)
            .withPedestalItem(3, ItemsRegistry.WIXIE_SHARD)
            .withPedestalItem(2, ItemsRegistry.ABJURATION_ESSENCE)
            .withPedestalItem(2, Items.CROPS_NETHER_WART)
            .withPedestalItem(net.minecraft.world.item.Items.f_42585_)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(this.getPerkItem(LootingPerk.INSTANCE.getRegistryName()))
            .withReagent(ItemsRegistry.BLANK_THREAD)
            .withPedestalItem(3, ItemsRegistry.DRYGMY_SHARD)
            .withPedestalItem(2, ItemsRegistry.EARTH_ESSENCE)
            .withPedestalItem(net.minecraft.world.item.Items.f_42648_)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(this.getPerkItem(SpellDamagePerk.INSTANCE.getRegistryName()))
            .withReagent(ItemsRegistry.BLANK_THREAD)
            .withPedestalItem(ItemsRegistry.FIRE_ESSENCE)
            .withPedestalItem(ItemsRegistry.MAGE_BLOOM)
            .withPedestalItem(ItemsRegistry.MANIPULATION_ESSENCE)
            .withPedestalItem(ItemsRegistry.WATER_ESSENCE)
            .withPedestalItem(ItemsRegistry.ABJURATION_ESSENCE)
            .withPedestalItem(ItemsRegistry.AIR_ESSENCE)
            .withPedestalItem(ItemsRegistry.EARTH_ESSENCE)
            .withPedestalItem(ItemsRegistry.CONJURATION_ESSENCE)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(this.getPerkItem(GlidingPerk.INSTANCE.getRegistryName()))
            .withReagent(ItemsRegistry.BLANK_THREAD)
            .withPedestalItem(2, ItemsRegistry.AIR_ESSENCE)
            .withPedestalItem(net.minecraft.world.item.Items.f_42741_)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(this.getPerkItem(MagicCapacityPerk.INSTANCE.getRegistryName()))
            .withReagent(ItemsRegistry.BLANK_THREAD)
            .withPedestalItem(3, BlockRegistry.SOURCEBERRY_BUSH)
            .withPedestalItem(3, ItemsRegistry.MAGE_BLOOM)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(this.getPerkItem(MagicResistPerk.INSTANCE.getRegistryName()))
            .withReagent(ItemsRegistry.BLANK_THREAD)
            .withPedestalItem(8, ItemsRegistry.MAGE_FIBER)
            .build()
      );
      this.makeArmor(ItemsRegistry.NOVICE_BOOTS, net.minecraft.world.item.Items.f_42479_);
      this.makeArmor(ItemsRegistry.NOVICE_LEGGINGS, net.minecraft.world.item.Items.f_42478_);
      this.makeArmor(ItemsRegistry.NOVICE_ROBES, net.minecraft.world.item.Items.f_42477_);
      this.makeArmor(ItemsRegistry.NOVICE_HOOD, net.minecraft.world.item.Items.f_42476_);
      this.makeArmor(ItemsRegistry.APPRENTICE_HOOD, net.minecraft.world.item.Items.f_42468_);
      this.makeArmor(ItemsRegistry.APPRENTICE_ROBES, net.minecraft.world.item.Items.f_42469_);
      this.makeArmor(ItemsRegistry.APPRENTICE_LEGGINGS, net.minecraft.world.item.Items.f_42470_);
      this.makeArmor(ItemsRegistry.APPRENTICE_BOOTS, net.minecraft.world.item.Items.f_42471_);
      this.makeArmor(ItemsRegistry.ARCHMAGE_BOOTS, net.minecraft.world.item.Items.f_42475_);
      this.makeArmor(ItemsRegistry.ARCHMAGE_LEGGINGS, net.minecraft.world.item.Items.f_42474_);
      this.makeArmor(ItemsRegistry.ARCHMAGE_ROBES, net.minecraft.world.item.Items.f_42473_);
      this.makeArmor(ItemsRegistry.ARCHMAGE_HOOD, net.minecraft.world.item.Items.f_42472_);
      this.addRecipe(
         this.builder()
            .withResult(this.getPerkItem(IgnitePerk.INSTANCE.getRegistryName()))
            .withReagent(ItemsRegistry.BLANK_THREAD)
            .withPedestalItem(net.minecraft.world.item.Items.f_42542_)
            .withPedestalItem(ItemsRegistry.FIRE_ESSENCE)
            .withPedestalItem(net.minecraft.world.item.Items.f_42613_)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(this.getPerkItem(ChillingPerk.INSTANCE.getRegistryName()))
            .withReagent(ItemsRegistry.BLANK_THREAD)
            .withPedestalItem(net.minecraft.world.item.Items.f_42363_)
            .withPedestalItem(2, ItemsRegistry.WATER_ESSENCE)
            .withPedestalItem(net.minecraft.world.item.Items.f_151055_)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(this.getPerkItem(VampiricPerk.INSTANCE.getRegistryName()))
            .withReagent(ItemsRegistry.BLANK_THREAD)
            .withPedestalItem(BlockRegistry.MENDOSTEEN_POD)
            .withPedestalItem(net.minecraft.world.item.Items.f_220194_)
            .withPedestalItem(2, ItemsRegistry.ABJURATION_ESSENCE)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(this.getPerkItem(TotemPerk.INSTANCE.getRegistryName()))
            .withReagent(ItemsRegistry.BLANK_THREAD)
            .withPedestalItem(net.minecraft.world.item.Items.f_42747_)
            .withPedestalItem(net.minecraft.world.item.Items.f_42714_)
            .withPedestalItem(2, ItemsRegistry.ABJURATION_ESSENCE)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(ItemsRegistry.SPELL_CROSSBOW)
            .keepNbtOfReagent(true)
            .withReagent(net.minecraft.world.item.Items.f_42717_)
            .withPedestalItem(Ingredient.m_204132_(Items.STORAGE_BLOCKS_GOLD))
            .withPedestalItem(ItemsRegistry.MANIPULATION_ESSENCE)
            .withPedestalItem(RecipeDatagen.SOURCE_GEM_BLOCK)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(BlockRegistry.BRAZIER_RELAY)
            .withReagent(BlockRegistry.RITUAL_BLOCK)
            .withPedestalItem(3, ItemsRegistry.MANIPULATION_ESSENCE)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(ItemsRegistry.STABLE_WARP_SCROLL)
            .withReagent(ItemsRegistry.WARP_SCROLL)
            .withPedestalItem(4, net.minecraft.world.item.Items.f_42593_)
            .withPedestalItem(2, Items.ENDER_PEARLS)
            .keepNbtOfReagent(true)
            .build()
      );
      this.addRecipe(
         this.builder()
            .withResult(ItemsRegistry.SCRY_CASTER)
            .withReagent(BlockRegistry.SCRYERS_CRYSTAL)
            .withPedestalItem(4, net.minecraft.world.item.Items.f_42593_)
            .withPedestalItem(4, Items.ENDER_PEARLS)
            .keepNbtOfReagent(true)
            .build()
      );
      this.addRecipe(this.builder().withResult(BlockRegistry.CRAFTING_LECTERN).withReagent(Blocks.f_50624_).withPedestalItem(4, Items.CHESTS).build());
      this.addRecipe(
         this.builder()
            .withResult(ItemsRegistry.WARP_SCROLL, 2)
            .withReagent(ItemsRegistry.WARP_SCROLL)
            .withPedestalItem(ItemsRegistry.WARP_SCROLL)
            .keepNbtOfReagent(true)
            .withSourceCost(1000)
            .withId(new ResourceLocation("ars_nouveau", "warp_scroll_copy"))
            .build()
      );
      this.addRecipe(this.builder().withResult(BlockRegistry.SPELL_SENSOR).withReagent(Blocks.f_152500_).build());
      this.addRecipe(
         this.builder()
            .withReagent(ItemsRegistry.RING_OF_POTENTIAL)
            .withResult(ItemsRegistry.JUMP_RING)
            .withPedestalItem(3, ItemsRegistry.WILDEN_WING)
            .withPedestalItem(ItemsRegistry.AIR_ESSENCE)
            .build()
      );
   }

   public void makeArmor(ItemLike outputItem, ItemLike armorItem) {
      this.addRecipe(this.builder().withResult(outputItem).withReagent(armorItem).withPedestalItem(4, ItemsRegistry.MAGE_FIBER).keepNbtOfReagent(true).build());
   }

   public PerkItem getPerkItem(ResourceLocation id) {
      return ArsNouveauAPI.getInstance().getPerkItemMap().get(id);
   }

   public void addRecipe(EnchantingApparatusRecipe recipe) {
      this.recipes.add(recipe);
   }

   protected static Path getRecipePath(Path pathIn, Item item) {
      return getRecipePath(pathIn, RegistryHelper.getRegistryName(item).m_135815_());
   }

   protected static Path getRecipePath(Path pathIn, String str) {
      return pathIn.resolve("data/ars_nouveau/recipes/" + str + ".json");
   }

   public String m_6055_() {
      return "Apparatus";
   }
}
