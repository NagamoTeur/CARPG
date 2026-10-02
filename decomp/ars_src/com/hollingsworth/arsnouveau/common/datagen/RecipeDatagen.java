package com.hollingsworth.arsnouveau.common.datagen;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.common.items.RitualTablet;
import com.hollingsworth.arsnouveau.common.lib.LibBlockNames;
import com.hollingsworth.arsnouveau.common.lib.RitualLib;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.util.function.Consumer;
import net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags.Items;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipeDatagen extends RecipeProvider {
   public static Ingredient SOURCE_GEM = Ingredient.m_204132_(ItemTagProvider.SOURCE_GEM_TAG);
   public static Ingredient SOURCE_GEM_BLOCK = Ingredient.m_204132_(ItemTagProvider.SOURCE_GEM_BLOCK_TAG);
   public static Ingredient ARCHWOOD_LOG = Ingredient.m_204132_(ItemTagProvider.ARCHWOOD_LOG_TAG);
   public static Ingredient WILDEN_DROP = Ingredient.m_204132_(ItemTagProvider.WILDEN_DROP_TAG);
   public static Ingredient SUMMON_SHARDS = Ingredient.m_204132_(ItemTagProvider.SUMMON_SHARDS_TAG);
   public Consumer<FinishedRecipe> consumer;
   private static int STONECUTTER_COUNTER = 0;

   public RecipeDatagen(DataGenerator generatorIn) {
      super(generatorIn);
   }

   protected void m_176531_(Consumer<FinishedRecipe> consumer) {
      this.consumer = consumer;
      Block SOURCESTONE = BlockRegistry.getBlock("sourcestone");
      ShapelessRecipeBuilder.m_126189_(ItemsRegistry.WORN_NOTEBOOK)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126186_(Ingredient.m_204132_(Items.GEMS_LAPIS), 1)
         .m_126209_(net.minecraft.world.item.Items.f_42517_)
         .m_176498_(consumer);
      ShapelessRecipeBuilder.m_126191_(ItemsRegistry.MAGE_FIBER, 4)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126209_(ItemsRegistry.MAGE_BLOOM)
         .m_176498_(consumer);
      ShapelessRecipeBuilder.m_126191_(ItemsRegistry.RUNIC_CHALK, 1)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126209_(ItemsRegistry.MANIPULATION_ESSENCE)
         .m_126209_(net.minecraft.world.item.Items.f_42499_)
         .m_126209_(ItemsRegistry.MAGE_FIBER)
         .m_176498_(consumer);
      ShapelessRecipeBuilder.m_126191_(ItemsRegistry.BLAZE_FIBER, 2)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126211_(ItemsRegistry.MAGE_FIBER, 2)
         .m_126209_(net.minecraft.world.item.Items.f_42593_)
         .m_176498_(consumer);
      ShapelessRecipeBuilder.m_126191_(ItemsRegistry.END_FIBER, 2)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126211_(ItemsRegistry.BLAZE_FIBER, 2)
         .m_126209_(net.minecraft.world.item.Items.f_42731_)
         .m_176498_(consumer);
      ShapedRecipeBuilder.m_126116_(BlockRegistry.SOURCE_JAR)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126130_("yyy")
         .m_126130_("x x")
         .m_126130_("yyy")
         .m_206416_('x', Items.GLASS)
         .m_126127_('y', BlockRegistry.ARCHWOOD_SLABS)
         .m_176498_(consumer);
      ShapedRecipeBuilder.m_126116_((ItemLike)BlockRegistry.ARCANE_PEDESTAL.get())
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126130_("xzx")
         .m_126130_("yxy")
         .m_126130_("yxy")
         .m_126127_('x', SOURCESTONE)
         .m_206416_('y', Items.NUGGETS_GOLD)
         .m_126124_('z', SOURCE_GEM)
         .m_176498_(consumer);
      ShapedRecipeBuilder.m_126116_(BlockRegistry.ENCHANTING_APP_BLOCK)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126130_("nsn")
         .m_126130_("gdg")
         .m_126130_("nsn")
         .m_206416_('n', Items.NUGGETS_GOLD)
         .m_126127_('s', SOURCESTONE)
         .m_206416_('d', Items.GEMS_DIAMOND)
         .m_206416_('g', Items.INGOTS_GOLD)
         .m_176498_(consumer);
      ShapedRecipeBuilder.m_126116_(ItemsRegistry.MUNDANE_BELT)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126130_("   ")
         .m_126130_("xyx")
         .m_126130_(" x ")
         .m_206416_('x', Items.LEATHER)
         .m_126124_('y', SOURCE_GEM)
         .m_176498_(consumer);
      ShapedRecipeBuilder.m_126116_(ItemsRegistry.RING_OF_POTENTIAL)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126130_("xxx")
         .m_126130_("xyx")
         .m_126130_("xxx")
         .m_206416_('x', Items.NUGGETS_IRON)
         .m_126124_('y', SOURCE_GEM)
         .m_176498_(consumer);
      ShapedRecipeBuilder.m_126116_(BlockRegistry.SCRIBES_BLOCK)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126130_("xxx")
         .m_126130_("y y")
         .m_126130_("z z")
         .m_126124_('x', Ingredient.m_43929_(new ItemLike[]{BlockRegistry.ARCHWOOD_SLABS}))
         .m_206416_('y', Items.NUGGETS_GOLD)
         .m_126124_('z', ARCHWOOD_LOG)
         .m_176498_(consumer);
      ShapedRecipeBuilder.m_126116_(ItemsRegistry.DULL_TRINKET)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126130_(" x ")
         .m_126130_("xyx")
         .m_126130_(" x ")
         .m_206416_('x', Items.NUGGETS_IRON)
         .m_126124_('y', SOURCE_GEM)
         .m_176498_(consumer);
      ShapedRecipeBuilder.m_126116_(BlockRegistry.ARCANE_CORE_BLOCK)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126130_("xxx")
         .m_126130_("yzy")
         .m_126130_("xxx")
         .m_206416_('y', Items.INGOTS_GOLD)
         .m_126127_('x', SOURCESTONE)
         .m_126124_('z', SOURCE_GEM)
         .m_176498_(consumer);
      ShapedRecipeBuilder.m_126118_(SOURCESTONE, 8)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126130_("xxx")
         .m_126130_("xyx")
         .m_126130_("xxx")
         .m_126124_('y', SOURCE_GEM)
         .m_206416_('x', Items.STONE)
         .m_176498_(consumer);
      ShapedRecipeBuilder.m_126118_(BlockRegistry.IMBUEMENT_BLOCK.m_5456_(), 1)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126130_("xyx")
         .m_126130_("x x")
         .m_126130_("xyx")
         .m_126127_('x', BlockRegistry.ARCHWOOD_PLANK)
         .m_206416_('y', Items.INGOTS_GOLD)
         .m_176498_(consumer);
      ShapedRecipeBuilder.m_126118_(ItemsRegistry.BLANK_PARCHMENT, 1)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126130_("yyy")
         .m_126130_("yxy")
         .m_126130_("yyy")
         .m_126127_('x', net.minecraft.world.item.Items.f_42516_)
         .m_126127_('y', ItemsRegistry.MAGE_FIBER)
         .m_176498_(consumer);
      ShapelessRecipeBuilder.m_126191_(ItemsRegistry.ALLOW_ITEM_SCROLL, 1)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126211_(ItemsRegistry.BLANK_PARCHMENT, 1)
         .m_126186_(Ingredient.m_204132_(Items.CHESTS), 1)
         .m_176498_(consumer);
      ShapelessRecipeBuilder.m_126191_(ItemsRegistry.DENY_ITEM_SCROLL, 1)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126211_(ItemsRegistry.BLANK_PARCHMENT, 1)
         .m_126186_(Ingredient.m_204132_(Items.COBBLESTONE), 1)
         .m_176498_(consumer);
      ShapelessRecipeBuilder.m_126189_(ItemsRegistry.WARP_SCROLL)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126186_(Ingredient.m_204132_(Items.GEMS_LAPIS), 4)
         .m_126209_(ItemsRegistry.BLANK_PARCHMENT)
         .m_126186_(SOURCE_GEM, 4)
         .m_176498_(consumer);
      ShapedRecipeBuilder.m_126116_(BlockRegistry.VOLCANIC_BLOCK)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126130_(" s ")
         .m_126130_("gig")
         .m_126130_(" s ")
         .m_206416_('g', Items.INGOTS_GOLD)
         .m_126124_('s', SOURCE_GEM)
         .m_126127_('i', net.minecraft.world.item.Items.f_42448_)
         .m_176498_(consumer);
      ShapelessRecipeBuilder.m_126191_(BlockRegistry.LAVA_LILY, 8)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126211_(net.minecraft.world.item.Items.f_42094_, 1)
         .m_126186_(SOURCE_GEM, 8)
         .m_176498_(consumer);
      this.shapelessBuilder(BlockRegistry.ARCHWOOD_PLANK, 4).m_126184_(ARCHWOOD_LOG).m_176498_(consumer);
      makeWood(BlockRegistry.VEXING_LOG, BlockRegistry.VEXING_WOOD, 3).m_176498_(consumer);
      makeWood(BlockRegistry.CASCADING_LOG, BlockRegistry.CASCADING_WOOD, 3).m_176498_(consumer);
      makeWood(BlockRegistry.BLAZING_LOG, BlockRegistry.BLAZING_WOOD, 3).m_176498_(consumer);
      makeWood(BlockRegistry.FLOURISHING_LOG, BlockRegistry.FLOURISHING_WOOD, 3).m_176498_(consumer);
      shapedWoodenStairs(consumer, BlockRegistry.ARCHWOOD_STAIRS, BlockRegistry.ARCHWOOD_PLANK);
      shapelessWoodenButton(consumer, BlockRegistry.ARCHWOOD_BUTTON, BlockRegistry.ARCHWOOD_PLANK);
      shapedWoodenDoor(consumer, BlockRegistry.ARCHWOOD_DOOR, BlockRegistry.ARCHWOOD_PLANK);
      shapedWoodenFence(consumer, BlockRegistry.ARCHWOOD_FENCE, BlockRegistry.ARCHWOOD_PLANK);
      shapedWoodenFenceGate(consumer, BlockRegistry.ARCHWOOD_FENCE_GATE, BlockRegistry.ARCHWOOD_PLANK);
      shapedWoodenPressurePlate(consumer, BlockRegistry.ARCHWOOD_PPlate, BlockRegistry.ARCHWOOD_PLANK);
      shapedWoodenSlab(consumer, BlockRegistry.ARCHWOOD_SLABS, BlockRegistry.ARCHWOOD_PLANK);
      strippedLogToWood(consumer, BlockRegistry.STRIPPED_AWLOG_BLUE, BlockRegistry.STRIPPED_AWWOOD_BLUE);
      strippedLogToWood(consumer, BlockRegistry.STRIPPED_AWLOG_GREEN, BlockRegistry.STRIPPED_AWWOOD_GREEN);
      strippedLogToWood(consumer, BlockRegistry.STRIPPED_AWLOG_RED, BlockRegistry.STRIPPED_AWWOOD_RED);
      strippedLogToWood(consumer, BlockRegistry.STRIPPED_AWLOG_PURPLE, BlockRegistry.STRIPPED_AWWOOD_PURPLE);
      shapedWoodenTrapdoor(consumer, BlockRegistry.ARCHWOOD_TRAPDOOR, BlockRegistry.ARCHWOOD_PLANK);
      ShapedRecipeBuilder.m_126118_(BlockRegistry.SOURCE_GEM_BLOCK, 1)
         .m_126130_("xx")
         .m_126130_("xx")
         .m_126124_('x', SOURCE_GEM)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_176498_(consumer);
      this.shapelessBuilder(ItemsRegistry.SOURCE_GEM, 4)
         .m_126211_(BlockRegistry.SOURCE_GEM_BLOCK, 1)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "source_gem_block_2"));
      this.shapelessBuilder(net.minecraft.world.item.Items.f_42454_, 1)
         .m_126209_(ItemsRegistry.WILDEN_WING)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "wing_to_leather"));
      this.shapelessBuilder(net.minecraft.world.item.Items.f_42499_, 3)
         .m_126209_(ItemsRegistry.WILDEN_HORN)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "horn_to_bonemeal"));
      this.shapelessBuilder(net.minecraft.world.item.Items.f_42536_, 5)
         .m_126209_(ItemsRegistry.WILDEN_SPIKE)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "spike_to_dye"));
      ShapedRecipeBuilder.m_126118_(net.minecraft.world.item.Items.f_42412_, 32)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126130_(" x ")
         .m_126130_(" y ")
         .m_126130_(" z ")
         .m_126127_('x', ItemsRegistry.WILDEN_SPIKE)
         .m_126127_('y', net.minecraft.world.item.Items.f_42398_)
         .m_126127_('z', net.minecraft.world.item.Items.f_42402_)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "spike_to_arrow"));
      this.shapelessBuilder(BlockRegistry.POTION_JAR).m_126209_(BlockRegistry.SOURCE_JAR).m_126209_(ItemsRegistry.ABJURATION_ESSENCE).m_176498_(consumer);
      this.shapelessBuilder(BlockRegistry.POTION_JAR)
         .m_126209_(BlockRegistry.POTION_JAR)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "potion_jar_empty"));
      this.shapelessBuilder(BlockRegistry.RITUAL_BLOCK)
         .m_126209_((ItemLike)BlockRegistry.ARCANE_PEDESTAL.get())
         .m_126184_(SOURCE_GEM_BLOCK)
         .m_126186_(Ingredient.m_204132_(Items.INGOTS_GOLD), 3)
         .m_176498_(consumer);
      this.shapelessBuilder(BlockRegistry.SCONCE_BLOCK).m_126184_(SOURCE_GEM).m_126186_(Ingredient.m_204132_(Items.NUGGETS_GOLD), 2).m_176498_(consumer);
      this.shapelessBuilder(this.getRitualItem(RitualLib.MOONFALL))
         .m_126209_(BlockRegistry.CASCADING_LOG)
         .m_126209_(net.minecraft.world.item.Items.f_42532_)
         .m_206419_(Items.STORAGE_BLOCKS_COAL)
         .m_126209_(net.minecraft.world.item.Items.f_42524_)
         .m_176498_(consumer);
      this.shapelessBuilder(this.getRitualItem(RitualLib.MOONFALL))
         .m_126209_(BlockRegistry.CASCADING_LOG)
         .m_126209_(ItemsRegistry.WILDEN_WING)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "moonfall_2"));
      this.shapelessBuilder(this.getRitualItem(RitualLib.SUNRISE))
         .m_126209_(BlockRegistry.BLAZING_LOG)
         .m_126211_(net.minecraft.world.item.Items.f_41939_, 3)
         .m_126209_(net.minecraft.world.item.Items.f_42524_)
         .m_176498_(consumer);
      this.shapelessBuilder(this.getRitualItem(RitualLib.SUNRISE))
         .m_126209_(BlockRegistry.BLAZING_LOG)
         .m_126209_(net.minecraft.world.item.Items.f_42206_)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "sunrise_2"));
      this.shapelessBuilder(this.getRitualItem(RitualLib.DIG))
         .m_126209_(BlockRegistry.FLOURISHING_LOG)
         .m_126209_(net.minecraft.world.item.Items.f_42385_)
         .m_206419_(Items.STORAGE_BLOCKS_COAL)
         .m_176498_(consumer);
      this.shapelessBuilder(this.getRitualItem(RitualLib.CLOUDSHAPER))
         .m_126209_(BlockRegistry.CASCADING_LOG)
         .m_126209_(net.minecraft.world.item.Items.f_42402_)
         .m_126184_(SOURCE_GEM_BLOCK)
         .m_176498_(consumer);
      this.shapelessBuilder(this.getRitualItem(RitualLib.CHALLENGE))
         .m_126209_(BlockRegistry.VEXING_LOG)
         .m_126209_(net.minecraft.world.item.Items.f_42110_)
         .m_126209_(net.minecraft.world.item.Items.f_42532_)
         .m_176498_(consumer);
      this.shapelessBuilder(this.getRitualItem(RitualLib.CHALLENGE))
         .m_126209_(BlockRegistry.VEXING_LOG)
         .m_126209_(ItemsRegistry.WILDEN_HORN)
         .m_126209_(net.minecraft.world.item.Items.f_42616_)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "challenge_2"));
      this.shapelessBuilder(this.getRitualItem(RitualLib.OVERGROWTH))
         .m_126209_(BlockRegistry.FLOURISHING_LOG)
         .m_126211_(ItemsRegistry.MAGE_BLOOM, 3)
         .m_126211_(ItemsRegistry.EARTH_ESSENCE, 2)
         .m_176498_(consumer);
      this.shapelessBuilder(this.getRitualItem(RitualLib.FERTILITY))
         .m_126209_(BlockRegistry.FLOURISHING_LOG)
         .m_126211_(net.minecraft.world.item.Items.f_42405_, 3)
         .m_126209_(net.minecraft.world.item.Items.f_42436_)
         .m_126211_(net.minecraft.world.item.Items.f_42593_, 2)
         .m_176498_(consumer);
      this.shapelessBuilder(this.getRitualItem(RitualLib.RESTORATION))
         .m_126209_(BlockRegistry.FLOURISHING_LOG)
         .m_126209_(net.minecraft.world.item.Items.f_42436_)
         .m_126211_(ItemsRegistry.ABJURATION_ESSENCE, 1)
         .m_176498_(consumer);
      this.shapelessBuilder(this.getRitualItem(RitualLib.DISINTEGRATION))
         .m_126209_(BlockRegistry.BLAZING_LOG)
         .m_126211_(net.minecraft.world.item.Items.f_42430_, 3)
         .m_126211_(net.minecraft.world.item.Items.f_42517_, 3)
         .m_176498_(consumer);
      this.shapelessBuilder(this.getRitualItem(RitualLib.WARP)).m_126209_(BlockRegistry.VEXING_LOG).m_126209_(ItemsRegistry.WARP_SCROLL).m_176498_(consumer);
      this.shapelessBuilder(ItemsRegistry.GREATER_EXPERIENCE_GEM).m_126211_(ItemsRegistry.EXPERIENCE_GEM, 4).m_176498_(consumer);
      this.shapelessBuilder(ItemsRegistry.EXPERIENCE_GEM, 4).m_126209_(ItemsRegistry.GREATER_EXPERIENCE_GEM).m_176498_(consumer);
      this.shapelessBuilder(ItemsRegistry.ALLOW_ITEM_SCROLL)
         .m_126209_(ItemsRegistry.ALLOW_ITEM_SCROLL)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "clear_allow"));
      this.shapelessBuilder(ItemsRegistry.DENY_ITEM_SCROLL)
         .m_126209_(ItemsRegistry.DENY_ITEM_SCROLL)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "clear_deny"));
      this.shapelessBuilder(this.getRitualItem(RitualLib.SCRYING))
         .m_126209_(BlockRegistry.VEXING_LOG)
         .m_126211_(net.minecraft.world.item.Items.f_42591_, 3)
         .m_126209_(net.minecraft.world.item.Items.f_42054_)
         .m_126184_(SOURCE_GEM_BLOCK)
         .m_176498_(consumer);
      this.shapelessBuilder(this.getRitualItem(RitualLib.FLIGHT))
         .m_126209_(BlockRegistry.VEXING_LOG)
         .m_126211_(ItemsRegistry.WILDEN_WING, 3)
         .m_126186_(Ingredient.m_204132_(Items.GEMS_DIAMOND), 2)
         .m_126209_(net.minecraft.world.item.Items.f_42584_)
         .m_176498_(consumer);
      this.shapelessBuilder(ItemsRegistry.MIMIC_ITEM_SCROLL)
         .m_126209_(ItemsRegistry.ALLOW_ITEM_SCROLL)
         .m_126209_(net.minecraft.world.item.Items.f_42009_)
         .m_176498_(consumer);
      this.shapelessBuilder(this.getRitualItem(RitualLib.WILDEN_SUMMON))
         .m_126209_(BlockRegistry.VEXING_LOG)
         .m_126186_(WILDEN_DROP, 3)
         .m_126209_(net.minecraft.world.item.Items.f_41854_)
         .m_176498_(consumer);
      this.shapelessBuilder(this.getRitualItem(RitualLib.WILDEN_SUMMON))
         .m_126209_(BlockRegistry.VEXING_LOG)
         .m_126211_(net.minecraft.world.item.Items.f_42110_, 1)
         .m_126211_(net.minecraft.world.item.Items.f_42383_, 1)
         .m_126211_(net.minecraft.world.item.Items.f_42411_, 1)
         .m_126209_(net.minecraft.world.item.Items.f_41854_)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "wilden_summon_alt"));
      this.shapelessBuilder(this.getRitualItem(RitualLib.ANIMAL_SUMMON))
         .m_126209_(BlockRegistry.VEXING_LOG)
         .m_126186_(SUMMON_SHARDS, 3)
         .m_126209_(net.minecraft.world.item.Items.f_41854_)
         .m_176498_(consumer);
      this.shapelessBuilder(this.getRitualItem(RitualLib.GRAVITY))
         .m_126209_(BlockRegistry.FLOURISHING_LOG)
         .m_126209_(ItemsRegistry.AIR_ESSENCE)
         .m_126209_(ItemsRegistry.EARTH_ESSENCE)
         .m_126209_(net.minecraft.world.item.Items.f_42402_)
         .m_126209_(net.minecraft.world.item.Items.f_42146_)
         .m_176498_(consumer);
      STONECUTTER_COUNTER = 1;
      ShapedRecipeBuilder.m_126116_(BlockRegistry.ALCHEMICAL_BLOCK)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126130_(" s ")
         .m_126130_("gig")
         .m_126130_(" s ")
         .m_206416_('g', Items.INGOTS_GOLD)
         .m_126124_('s', SOURCE_GEM)
         .m_126127_('i', net.minecraft.world.item.Items.f_42543_)
         .m_176498_(consumer);
      ShapedRecipeBuilder.m_126116_(BlockRegistry.VITALIC_BLOCK)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126130_(" s ")
         .m_126130_("gig")
         .m_126130_(" s ")
         .m_206416_('g', Items.INGOTS_GOLD)
         .m_126124_('s', SOURCE_GEM)
         .m_126127_('i', net.minecraft.world.item.Items.f_42546_)
         .m_176498_(consumer);
      ShapedRecipeBuilder.m_126116_(BlockRegistry.MYCELIAL_BLOCK)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126130_(" s ")
         .m_126130_("gig")
         .m_126130_(" s ")
         .m_206416_('g', Items.INGOTS_GOLD)
         .m_126124_('s', SOURCE_GEM)
         .m_126127_('i', net.minecraft.world.item.Items.f_42400_)
         .m_176498_(consumer);
      ShapedRecipeBuilder.m_126116_(BlockRegistry.AGRONOMIC_SOURCELINK)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126130_(" s ")
         .m_126130_("gig")
         .m_126130_(" s ")
         .m_206416_('g', Items.INGOTS_GOLD)
         .m_126124_('s', SOURCE_GEM)
         .m_126127_('i', net.minecraft.world.item.Items.f_42405_)
         .m_176498_(consumer);
      this.shapelessBuilder(ItemsRegistry.SOURCE_BERRY_PIE)
         .m_126209_(net.minecraft.world.item.Items.f_42521_)
         .m_126209_(net.minecraft.world.item.Items.f_42501_)
         .m_126209_(ItemsRegistry.MAGE_BLOOM)
         .m_126211_(BlockRegistry.SOURCEBERRY_BUSH, 3)
         .m_176498_(consumer);
      this.shapelessBuilder(ItemsRegistry.SOURCE_BERRY_ROLL)
         .m_126211_(net.minecraft.world.item.Items.f_42405_, 3)
         .m_126209_(BlockRegistry.SOURCEBERRY_BUSH)
         .m_176498_(consumer);
      ShapedRecipeBuilder.m_126116_(BlockRegistry.RELAY)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126130_("g g")
         .m_126130_("gMg")
         .m_126130_("g g")
         .m_206416_('g', Items.INGOTS_GOLD)
         .m_126124_('M', SOURCE_GEM_BLOCK)
         .m_176498_(consumer);
      this.shapelessBuilder(this.getRitualItem(RitualLib.BINDING))
         .m_126209_(BlockRegistry.VEXING_LOG)
         .m_126209_(ItemsRegistry.BLANK_PARCHMENT)
         .m_126211_(net.minecraft.world.item.Items.f_42584_, 1)
         .m_126186_(SOURCE_GEM, 3)
         .m_176498_(consumer);
      ShapedRecipeBuilder.m_126116_(BlockRegistry.BASIC_SPELL_TURRET)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126130_("xxx")
         .m_126130_("xzy")
         .m_126130_("yyy")
         .m_126124_('z', Ingredient.m_204132_(Items.STORAGE_BLOCKS_REDSTONE))
         .m_126124_('x', SOURCE_GEM)
         .m_126124_('y', Ingredient.m_204132_(Items.INGOTS_GOLD))
         .m_176498_(consumer);
      ShapedRecipeBuilder.m_126116_(BlockRegistry.ARCHWOOD_CHEST)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126130_("xxx")
         .m_126130_("xyx")
         .m_126130_("xxx")
         .m_126127_('x', BlockRegistry.ARCHWOOD_PLANK)
         .m_126127_('y', net.minecraft.world.item.Items.f_42587_)
         .m_176498_(consumer);
      ShapedRecipeBuilder.m_126116_(BlockRegistry.SPELL_PRISM)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126130_("gxg")
         .m_126130_("xnx")
         .m_126130_("gxg")
         .m_126127_('x', BlockRegistry.ARCHWOOD_PLANK)
         .m_126124_('g', Ingredient.m_204132_(Items.INGOTS_GOLD))
         .m_126124_('n', Ingredient.m_204132_(Items.STORAGE_BLOCKS_QUARTZ))
         .m_176498_(consumer);
      this.shapelessBuilder(net.minecraft.world.item.Items.f_42009_)
         .m_126209_(BlockRegistry.ARCHWOOD_CHEST)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "archwood_to_chest"));
      this.shapelessBuilder(this.getRitualItem(RitualLib.AWAKENING))
         .m_126209_(BlockRegistry.FLOURISHING_LOG)
         .m_126209_(BlockRegistry.BLAZING_SAPLING)
         .m_126209_(BlockRegistry.CASCADING_SAPLING)
         .m_126209_(BlockRegistry.FLOURISHING_SAPLING)
         .m_126209_(BlockRegistry.VEXING_SAPLING)
         .m_126186_(SOURCE_GEM, 4)
         .m_176498_(consumer);
      this.shapelessBuilder(net.minecraft.world.item.Items.f_42489_, 2)
         .m_126211_(ItemsRegistry.MAGE_BLOOM, 2)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "magebloom_to_pink"));
      this.shapelessBuilder(net.minecraft.world.item.Items.f_42493_)
         .m_126209_(BlockRegistry.SOURCEBERRY_BUSH)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "sourceberry_to_purple"));
      this.shapelessBuilder(net.minecraft.world.item.Items.f_42447_)
         .m_126209_(ItemsRegistry.WATER_ESSENCE)
         .m_126209_(net.minecraft.world.item.Items.f_42446_)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "water_essence_to_bucket"));
      this.shapelessBuilder(net.minecraft.world.item.Items.f_42613_, 3)
         .m_126209_(ItemsRegistry.FIRE_ESSENCE)
         .m_126209_(net.minecraft.world.item.Items.f_42403_)
         .m_126209_(net.minecraft.world.item.Items.f_42413_)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "fire_essence_to_charge"));
      ShapedRecipeBuilder.m_126116_(ItemsRegistry.DOWSING_ROD)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126130_(" x ")
         .m_126130_("a a")
         .m_206416_('x', Items.INGOTS_GOLD)
         .m_126127_('a', BlockRegistry.ARCHWOOD_PLANK)
         .m_176498_(consumer);
      this.shapelessBuilder(ItemsRegistry.ANNOTATED_CODEX)
         .m_126209_(ItemsRegistry.BLANK_PARCHMENT)
         .m_126209_(net.minecraft.world.item.Items.f_42454_)
         .m_176498_(consumer);
      this.shapelessBuilder(net.minecraft.world.item.Items.f_151055_)
         .m_126209_(ItemsRegistry.AIR_ESSENCE)
         .m_126209_(net.minecraft.world.item.Items.f_42446_)
         .m_126209_(net.minecraft.world.item.Items.f_41981_)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "air_essence_to_snow_bucket"));
      this.shapedBuilder(net.minecraft.world.item.Items.f_42049_, 8)
         .m_126130_("xxx")
         .m_126130_("xyx")
         .m_126130_("xxx")
         .m_206416_('x', Items.SAND)
         .m_126127_('y', ItemsRegistry.CONJURATION_ESSENCE)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "conjuration_essence_to_soul_sand"));
      this.shapedBuilder(net.minecraft.world.item.Items.f_42102_, 8)
         .m_126130_("xxx")
         .m_126130_("xyx")
         .m_126130_("xxx")
         .m_206416_('x', Items.STONE)
         .m_126127_('y', ItemsRegistry.CONJURATION_ESSENCE)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "conjuration_essence_to_end_stone"));
      this.shapelessBuilder(net.minecraft.world.item.Items.f_41999_)
         .m_126209_(net.minecraft.world.item.Items.f_42448_)
         .m_126209_(ItemsRegistry.WATER_ESSENCE)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "water_essence_to_obsidian"));
      this.shapedBuilder(net.minecraft.world.item.Items.f_42258_, 8)
         .m_126130_("xxx")
         .m_126130_("xyx")
         .m_126130_("xxx")
         .m_206416_('x', Items.STONE)
         .m_126127_('y', ItemsRegistry.FIRE_ESSENCE)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "fire_essence_to_magma_block"));
      this.shapedBuilder(net.minecraft.world.item.Items.f_41958_, 8)
         .m_126130_("xxx")
         .m_126130_("xyx")
         .m_126130_("xxx")
         .m_126127_('x', net.minecraft.world.item.Items.f_42064_)
         .m_126127_('y', ItemsRegistry.MANIPULATION_ESSENCE)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "manipulation_essence_to_granite"));
      this.shapedBuilder(net.minecraft.world.item.Items.f_42170_, 8)
         .m_126130_("xxx")
         .m_126130_("xyx")
         .m_126130_("xxx")
         .m_126127_('x', net.minecraft.world.item.Items.f_41958_)
         .m_126127_('y', ItemsRegistry.MANIPULATION_ESSENCE)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "manipulation_essence_to_andesite"));
      this.shapedBuilder(net.minecraft.world.item.Items.f_42064_, 8)
         .m_126130_("xxx")
         .m_126130_("xyx")
         .m_126130_("xxx")
         .m_126127_('x', net.minecraft.world.item.Items.f_42170_)
         .m_126127_('y', ItemsRegistry.MANIPULATION_ESSENCE)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "manipulation_essence_to_diorite"));
      this.shapedBuilder(net.minecraft.world.item.Items.f_42093_, 8)
         .m_126130_("xxx")
         .m_126130_("xyx")
         .m_126130_("xxx")
         .m_126127_('x', net.minecraft.world.item.Items.f_42276_)
         .m_126127_('y', ItemsRegistry.MANIPULATION_ESSENCE)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "manipulation_essence_to_mycelium"));
      this.shapedBuilder(net.minecraft.world.item.Items.f_151016_, 8)
         .m_126130_("xxx")
         .m_126130_("xyx")
         .m_126130_("xxx")
         .m_126127_('x', net.minecraft.world.item.Items.f_42093_)
         .m_126127_('y', ItemsRegistry.MANIPULATION_ESSENCE)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "manipulation_essence_to_moss_block"));
      this.shapedBuilder(net.minecraft.world.item.Items.f_42276_, 8)
         .m_126130_("xxx")
         .m_126130_("xyx")
         .m_126130_("xxx")
         .m_126127_('x', net.minecraft.world.item.Items.f_151016_)
         .m_126127_('y', ItemsRegistry.MANIPULATION_ESSENCE)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "manipulation_essence_to_grass_block"));
      this.shapedBuilder(net.minecraft.world.item.Items.f_151048_, 8)
         .m_126130_("xxx")
         .m_126130_("xyx")
         .m_126130_("xxx")
         .m_126127_('x', net.minecraft.world.item.Items.f_151034_)
         .m_126127_('y', ItemsRegistry.MANIPULATION_ESSENCE)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "manipulation_essence_to_tuff"));
      this.shapedBuilder(net.minecraft.world.item.Items.f_151047_, 8)
         .m_126130_("xxx")
         .m_126130_("xyx")
         .m_126130_("xxx")
         .m_126127_('x', net.minecraft.world.item.Items.f_151048_)
         .m_126127_('y', ItemsRegistry.MANIPULATION_ESSENCE)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "manipulation_essence_to_calcite"));
      this.shapedBuilder(net.minecraft.world.item.Items.f_151034_, 8)
         .m_126130_("xxx")
         .m_126130_("xyx")
         .m_126130_("xxx")
         .m_126127_('x', net.minecraft.world.item.Items.f_151047_)
         .m_126127_('y', ItemsRegistry.MANIPULATION_ESSENCE)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "manipulation_essence_to_deepslate"));
      this.shapelessBuilder(BlockRegistry.CASCADING_SAPLING)
         .m_126209_(ItemsRegistry.MANIPULATION_ESSENCE)
         .m_126209_(BlockRegistry.BLAZING_SAPLING)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "manipulation_essence_to_cascading_sapling"));
      this.shapelessBuilder(BlockRegistry.FLOURISHING_SAPLING)
         .m_126209_(ItemsRegistry.MANIPULATION_ESSENCE)
         .m_126209_(BlockRegistry.CASCADING_SAPLING)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "manipulation_essence_to_flourishing_sapling"));
      this.shapelessBuilder(BlockRegistry.VEXING_SAPLING)
         .m_126209_(ItemsRegistry.MANIPULATION_ESSENCE)
         .m_126209_(BlockRegistry.FLOURISHING_SAPLING)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "manipulation_essence_to_vexing_sapling"));
      this.shapelessBuilder(BlockRegistry.BLAZING_SAPLING)
         .m_126209_(ItemsRegistry.MANIPULATION_ESSENCE)
         .m_126209_(BlockRegistry.VEXING_SAPLING)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "manipulation_essence_to_blazin_sapling"));
      this.shapedBuilder(BlockRegistry.ORANGE_SBED)
         .m_126130_("xxx")
         .m_126130_("xyx")
         .m_126130_("xxx")
         .m_126127_('x', ItemsRegistry.MAGE_FIBER)
         .m_126127_('y', net.minecraft.world.item.Items.f_42402_)
         .m_176498_(consumer);
      this.shapelessBuilder(BlockRegistry.RED_SBED).m_206419_(ItemTagProvider.SUMMON_BED_ITEMS).m_206419_(Items.DYES_RED).m_176498_(consumer);
      this.shapelessBuilder(BlockRegistry.GREEN_SBED).m_206419_(ItemTagProvider.SUMMON_BED_ITEMS).m_206419_(Items.DYES_GREEN).m_176498_(consumer);
      this.shapelessBuilder(BlockRegistry.BLUE_SBED).m_206419_(ItemTagProvider.SUMMON_BED_ITEMS).m_206419_(Items.DYES_BLUE).m_176498_(consumer);
      this.shapelessBuilder(BlockRegistry.PURPLE_SBED).m_206419_(ItemTagProvider.SUMMON_BED_ITEMS).m_206419_(Items.DYES_PURPLE).m_176498_(consumer);
      this.shapelessBuilder(BlockRegistry.YELLOW_SBED).m_206419_(ItemTagProvider.SUMMON_BED_ITEMS).m_206419_(Items.DYES_YELLOW).m_176498_(consumer);
      this.shapelessBuilder(BlockRegistry.SCRYERS_CRYSTAL).m_126209_(net.minecraft.world.item.Items.f_42545_).m_126184_(SOURCE_GEM).m_176498_(consumer);
      this.shapelessBuilder(ItemsRegistry.BLANK_PARCHMENT)
         .m_126209_(ItemsRegistry.SCRYER_SCROLL)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "scry_to_blank_parchment"));
      this.shapelessBuilder(ItemsRegistry.BLANK_PARCHMENT)
         .m_126209_(ItemsRegistry.SPELL_PARCHMENT)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "wipe_spell_parchment"));
      this.shapedBuilder(ItemsRegistry.STARBUNCLE_SHADES)
         .m_126130_("xyx")
         .m_126127_('x', net.minecraft.world.item.Items.f_151011_)
         .m_126127_('y', ItemsRegistry.SOURCE_GEM)
         .m_176498_(consumer);

      for (String s : LibBlockNames.DECORATIVE_SOURCESTONE) {
         if (!s.equals("sourcestone")) {
            makeStonecutter(consumer, BlockRegistry.getBlock("sourcestone"), BlockRegistry.getBlock(s), "sourcestone");
            this.shapelessBuilder(SOURCESTONE)
               .m_126209_(BlockRegistry.getBlock(s))
               .m_126140_(consumer, new ResourceLocation("ars_nouveau", s + "_to_sourcestone"));
            Block stair = (Block)ForgeRegistries.BLOCKS.getValue(new ResourceLocation("ars_nouveau", s + "_stairs"));
            Block slab = (Block)ForgeRegistries.BLOCKS.getValue(new ResourceLocation("ars_nouveau", s + "_slab"));
            SingleItemRecipeBuilder.m_126313_(Ingredient.m_43929_(new ItemLike[]{BlockRegistry.getBlock(s)}), stair)
               .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
               .m_126140_(consumer, new ResourceLocation("ars_nouveau", s + "_stonecutter_stair"));
            SingleItemRecipeBuilder.m_126316_(Ingredient.m_43929_(new ItemLike[]{BlockRegistry.getBlock(s)}), slab, 2)
               .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
               .m_126140_(consumer, new ResourceLocation("ars_nouveau", s + "_stone_cutterslab"));
            shapedWoodenStairs(consumer, stair, BlockRegistry.getBlock(s), s + "_stairs");
            shapedWoodenSlab(consumer, slab, BlockRegistry.getBlock(s), s + "_slab");
         }
      }

      this.shapelessBuilder(this.getRitualItem(RitualLib.HARVEST))
         .m_126209_(BlockRegistry.FLOURISHING_LOG)
         .m_126209_(ItemsRegistry.EARTH_ESSENCE)
         .m_126209_(net.minecraft.world.item.Items.f_42387_)
         .m_176498_(consumer);
      this.shapedBuilder(ItemsRegistry.WIXIE_HAT)
         .m_126130_("xxx")
         .m_126130_("xyx")
         .m_126130_("xxx")
         .m_126127_('x', ItemsRegistry.MAGE_FIBER)
         .m_206416_('y', Items.INGOTS_GOLD)
         .m_176498_(consumer);
      this.shapedBuilder(BlockRegistry.POTION_DIFFUSER)
         .m_126130_(" x ")
         .m_126130_("zyz")
         .m_126130_("xxx")
         .m_126127_('x', BlockRegistry.ARCHWOOD_PLANK)
         .m_206416_('y', Items.RODS_BLAZE)
         .m_206416_('z', Items.INGOTS_GOLD)
         .m_176498_(consumer);
      this.shapedBuilder(ItemsRegistry.BLANK_THREAD, 1)
         .m_126130_("xxx")
         .m_126130_("yyy")
         .m_126130_("xxx")
         .m_126127_('x', ItemsRegistry.MAGE_FIBER)
         .m_206416_('y', Items.NUGGETS_GOLD)
         .m_176498_(consumer);
      this.shapedBuilder(BlockRegistry.ALTERATION_TABLE)
         .m_126130_(" x ")
         .m_126130_("xyx")
         .m_126130_(" x ")
         .m_126127_('x', ItemsRegistry.MAGE_FIBER)
         .m_126127_('y', BlockRegistry.SCRIBES_BLOCK)
         .m_176498_(consumer);
      this.shapedBuilder(BlockRegistry.MOB_JAR)
         .m_126130_("yyy")
         .m_126130_("x x")
         .m_126130_("xxx")
         .m_126127_('y', BlockRegistry.ARCHWOOD_SLABS)
         .m_126124_('x', Ingredient.m_204132_(Items.GLASS))
         .m_176498_(consumer);
      this.shapelessBuilder(this.getRitualItem(RitualLib.CONTAINMENT))
         .m_126209_(BlockRegistry.VEXING_LOG)
         .m_126209_(ItemsRegistry.MANIPULATION_ESSENCE)
         .m_126211_(net.minecraft.world.item.Items.f_42590_, 3)
         .m_176498_(consumer);
      this.shapedBuilder(BlockRegistry.VOID_PRISM)
         .m_126130_("xxx")
         .m_126130_("xyx")
         .m_126130_("xxx")
         .m_126127_('y', BlockRegistry.SPELL_PRISM)
         .m_126124_('x', Ingredient.m_204132_(Items.OBSIDIAN))
         .m_176498_(consumer);
      this.shapedBuilder(BlockRegistry.MAGEBLOOM_BLOCK).m_126130_("xx ").m_126130_("xx ").m_126127_('x', ItemsRegistry.MAGE_FIBER).m_176498_(consumer);
      this.shapelessBuilder(ItemsRegistry.MAGE_FIBER, 4)
         .m_126209_(BlockRegistry.MAGEBLOOM_BLOCK)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "magebloom_block_to_magebloom"));
      this.shapedBuilder(BlockRegistry.FALSE_WEAVE, 8)
         .m_126130_("xxx")
         .m_126130_("xyx")
         .m_126130_("xxx")
         .m_126127_('x', BlockRegistry.MAGEBLOOM_BLOCK)
         .m_126127_('y', ItemsRegistry.AIR_ESSENCE)
         .m_176498_(consumer);
      this.shapedBuilder(BlockRegistry.GHOST_WEAVE, 8)
         .m_126130_("xxx")
         .m_126130_("xyx")
         .m_126130_("xxx")
         .m_126127_('x', BlockRegistry.MAGEBLOOM_BLOCK)
         .m_126127_('y', ItemsRegistry.ABJURATION_ESSENCE)
         .m_176498_(consumer);
      this.shapedBuilder(BlockRegistry.MIRROR_WEAVE, 8)
         .m_126130_("xxx")
         .m_126130_("xyx")
         .m_126130_("xxx")
         .m_126127_('x', BlockRegistry.MAGEBLOOM_BLOCK)
         .m_126127_('y', ItemsRegistry.CONJURATION_ESSENCE)
         .m_176498_(consumer);
      this.shapelessBuilder(this.getRitualItem(RitualLib.PLAINS))
         .m_126209_(BlockRegistry.FLOURISHING_LOG)
         .m_126209_(Blocks.f_50440_)
         .m_126209_(ItemsRegistry.EARTH_ESSENCE)
         .m_176498_(consumer);
      this.shapelessBuilder(this.getRitualItem(RitualLib.FORESTATION))
         .m_126209_(BlockRegistry.FLOURISHING_LOG)
         .m_126209_(BlockRegistry.MENDOSTEEN_POD)
         .m_126209_(ItemsRegistry.EARTH_ESSENCE)
         .m_176498_(consumer);
      this.shapelessBuilder(this.getRitualItem(RitualLib.FLOWERING))
         .m_126209_(BlockRegistry.FLOURISHING_LOG)
         .m_126211_(net.minecraft.world.item.Items.f_41940_, 3)
         .m_126211_(net.minecraft.world.item.Items.f_41939_, 3)
         .m_126209_(ItemsRegistry.EARTH_ESSENCE)
         .m_176498_(consumer);
      this.shapelessBuilder(this.getRitualItem(RitualLib.DESERT))
         .m_126209_(BlockRegistry.BLAZING_LOG)
         .m_126209_(Blocks.f_49992_)
         .m_126209_(ItemsRegistry.EARTH_ESSENCE)
         .m_176498_(consumer);
      this.shapedBuilder(BlockRegistry.MAGELIGHT_TORCH, 1)
         .m_126130_("   ")
         .m_126130_("xyx")
         .m_126130_(" x ")
         .m_206416_('x', Items.NUGGETS_GOLD)
         .m_126124_('y', SOURCE_GEM)
         .m_176498_(consumer);
      this.shapelessBuilder(BlockRegistry.ARCANE_PLATFORM).m_126209_(BlockRegistry.ARCANE_PEDESTAL).m_176498_(consumer);
      this.shapelessBuilder(BlockRegistry.ARCANE_PEDESTAL)
         .m_126209_(BlockRegistry.ARCANE_PLATFORM)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "platform_to_pedestal"));
      this.shapedBuilder(BlockRegistry.SKY_WEAVE, 8)
         .m_126130_("xxx")
         .m_126130_("xyx")
         .m_126130_("xxx")
         .m_126127_('x', BlockRegistry.MAGEBLOOM_BLOCK)
         .m_126127_('y', ItemsRegistry.MANIPULATION_ESSENCE)
         .m_176498_(consumer);
      this.shapedBuilder(BlockRegistry.ITEM_DETECTOR)
         .m_126130_("xxx")
         .m_126130_("xyx")
         .m_126130_("zzz")
         .m_206416_('x', Items.GLASS)
         .m_126127_('y', Blocks.f_50455_)
         .m_126127_('z', BlockRegistry.ARCHWOOD_PLANK)
         .m_176498_(consumer);
      this.shapedBuilder(BlockRegistry.REPOSITORY)
         .m_126130_("yxy")
         .m_126130_("x x")
         .m_126130_("yxy")
         .m_126124_('x', ARCHWOOD_LOG)
         .m_206416_('y', Items.NUGGETS_GOLD)
         .m_176498_(consumer);
      this.shapelessBuilder(this.getRitualItem(RitualLib.SANCTUARY))
         .m_126209_(BlockRegistry.CASCADING_LOG)
         .m_126209_(ItemsRegistry.WATER_ESSENCE)
         .m_126209_(net.minecraft.world.item.Items.f_42251_)
         .m_176498_(consumer);
      this.shapelessBuilder(BlockRegistry.ROTATING_TURRET).m_126209_(BlockRegistry.BASIC_SPELL_TURRET).m_176498_(consumer);
      this.shapelessBuilder(BlockRegistry.BASIC_SPELL_TURRET)
         .m_126209_(BlockRegistry.ROTATING_TURRET)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "rotating_turret_to_basic_spell_turret"));
      this.shapelessBuilder(ItemsRegistry.STARBUNCLE_SHARD)
         .m_126209_(ItemsRegistry.STARBUNCLE_SHARD)
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", "wipe_starby_shard"));
      ShapedRecipeBuilder.m_126116_(BlockRegistry.REDSTONE_RELAY)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126130_("gxg")
         .m_126130_("gMg")
         .m_126130_("gxg")
         .m_206416_('g', Items.INGOTS_GOLD)
         .m_126124_('M', SOURCE_GEM_BLOCK)
         .m_206416_('x', Items.DUSTS_REDSTONE)
         .m_176498_(consumer);
   }

   public RitualTablet getRitualItem(String name) {
      return ArsNouveauAPI.getInstance().getRitualItemMap().get(new ResourceLocation("ars_nouveau", name));
   }

   public ShapedRecipeBuilder shapedBuilder(ItemLike item) {
      return this.shapedBuilder(item, 1);
   }

   public ShapedRecipeBuilder shapedBuilder(ItemLike result, int count) {
      return ShapedRecipeBuilder.m_126118_(result, count).m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}));
   }

   public static ShapedRecipeBuilder makeWood(ItemLike logs, ItemLike wood, int count) {
      return ShapedRecipeBuilder.m_126118_(wood, count)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126130_("xx ")
         .m_126130_("xx ")
         .m_126127_('x', logs);
   }

   private static void shapedWoodenTrapdoor(Consumer<FinishedRecipe> recipeConsumer, ItemLike trapdoor, ItemLike input) {
      ShapedRecipeBuilder.m_126118_(trapdoor, 2)
         .m_126127_('#', input)
         .m_126130_("###")
         .m_126130_("###")
         .m_126145_("wooden_trapdoor")
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_176498_(recipeConsumer);
   }

   public static void shapedWoodenStairs(Consumer<FinishedRecipe> recipeConsumer, ItemLike stairs, ItemLike input) {
      ShapedRecipeBuilder.m_126118_(stairs, 4)
         .m_126127_('#', input)
         .m_126130_("#  ")
         .m_126130_("## ")
         .m_126130_("###")
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_176498_(recipeConsumer);
   }

   public static void shapedWoodenStairs(Consumer<FinishedRecipe> recipeConsumer, ItemLike stairs, ItemLike input, String name) {
      ShapedRecipeBuilder.m_126118_(stairs, 4)
         .m_126127_('#', input)
         .m_126130_("#  ")
         .m_126130_("## ")
         .m_126130_("###")
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126140_(recipeConsumer, new ResourceLocation("ars_nouveau", name));
   }

   private static void shapelessWoodenButton(Consumer<FinishedRecipe> recipeConsumer, ItemLike button, ItemLike input) {
      ShapelessRecipeBuilder.m_126189_(button)
         .m_126209_(input)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_176498_(recipeConsumer);
   }

   private static void strippedLogToWood(Consumer<FinishedRecipe> recipeConsumer, ItemLike stripped, ItemLike output) {
      ShapedRecipeBuilder.m_126118_(output, 3)
         .m_126127_('#', stripped)
         .m_126130_("##")
         .m_126130_("##")
         .m_126145_("bark")
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_176498_(recipeConsumer);
   }

   private static void shapedWoodenDoor(Consumer<FinishedRecipe> recipeConsumer, ItemLike door, ItemLike input) {
      ShapedRecipeBuilder.m_126118_(door, 3)
         .m_126127_('#', input)
         .m_126130_("##")
         .m_126130_("##")
         .m_126130_("##")
         .m_126145_("wooden_door")
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_176498_(recipeConsumer);
   }

   private static void shapedWoodenFence(Consumer<FinishedRecipe> recipeConsumer, ItemLike fence, ItemLike input) {
      ShapedRecipeBuilder.m_126118_(fence, 3)
         .m_126127_('#', net.minecraft.world.item.Items.f_42398_)
         .m_126127_('W', input)
         .m_126130_("W#W")
         .m_126130_("W#W")
         .m_126145_("wooden_fence")
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_176498_(recipeConsumer);
   }

   private static void shapedWoodenFenceGate(Consumer<FinishedRecipe> recipeConsumer, ItemLike fenceGate, ItemLike input) {
      ShapedRecipeBuilder.m_126116_(fenceGate)
         .m_126127_('#', net.minecraft.world.item.Items.f_42398_)
         .m_126127_('W', input)
         .m_126130_("#W#")
         .m_126130_("#W#")
         .m_126145_("wooden_fence_gate")
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_176498_(recipeConsumer);
   }

   private static void shapedWoodenPressurePlate(Consumer<FinishedRecipe> recipeConsumer, ItemLike pressurePlate, ItemLike input) {
      ShapedRecipeBuilder.m_126116_(pressurePlate)
         .m_126127_('#', input)
         .m_126130_("##")
         .m_126145_("wooden_pressure_plate")
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_176498_(recipeConsumer);
   }

   private static void shapedWoodenSlab(Consumer<FinishedRecipe> recipeConsumer, ItemLike slab, ItemLike input) {
      ShapedRecipeBuilder.m_126118_(slab, 6)
         .m_126127_('#', input)
         .m_126130_("###")
         .m_126145_("wooden_slab")
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_176498_(recipeConsumer);
   }

   private static void shapedWoodenSlab(Consumer<FinishedRecipe> recipeConsumer, ItemLike slab, ItemLike input, String name) {
      ShapedRecipeBuilder.m_126118_(slab, 6)
         .m_126127_('#', input)
         .m_126130_("###")
         .m_126145_("wooden_slab")
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126140_(recipeConsumer, new ResourceLocation("ars_nouveau", name));
   }

   public ShapelessRecipeBuilder shapelessBuilder(ItemLike result) {
      return this.shapelessBuilder(result, 1);
   }

   public ShapelessRecipeBuilder shapelessBuilder(ItemLike result, int resultCount) {
      return ShapelessRecipeBuilder.m_126191_(result, resultCount)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}));
   }

   public static void makeStonecutter(Consumer<FinishedRecipe> consumer, ItemLike input, ItemLike output, String reg) {
      SingleItemRecipeBuilder.m_126313_(Ingredient.m_43929_(new ItemLike[]{input}), output)
         .m_126132_("has_journal", TriggerInstance.m_43199_(new ItemLike[]{ItemsRegistry.WORN_NOTEBOOK}))
         .m_126140_(consumer, new ResourceLocation("ars_nouveau", reg + "_" + STONECUTTER_COUNTER));
      STONECUTTER_COUNTER++;
   }
}
