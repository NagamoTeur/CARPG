package com.hollingsworth.arsnouveau.common.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.RegistryHelper;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.common.crafting.recipes.GlyphRecipe;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAOE;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAccelerate;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDampen;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDecelerate;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDurationDown;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentExtendTime;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentExtract;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentFortune;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentPierce;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentRandomize;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentSensitive;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentSplit;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectAnimate;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectBlink;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectBounce;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectBreak;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectBurst;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectColdSnap;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectConjureWater;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectCraft;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectCrush;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectCut;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectDelay;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectDispel;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectEnderChest;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectEvaporate;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectExchange;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectExplosion;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectFangs;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectFell;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectFirework;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectFlare;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectFreeze;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectGlide;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectGravity;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectGrow;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectHarm;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectHarvest;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectHeal;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectHex;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectIgnite;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectInfuse;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectIntangible;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectInteract;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectInvisibility;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectKnockback;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectLaunch;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectLeap;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectLight;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectLightning;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectLinger;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectName;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectPhantomBlock;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectPickup;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectPlaceBlock;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectPull;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectRedstone;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectRotate;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectRune;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectSenseMagic;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectSlowfall;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectSmelt;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectSnare;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectSummonDecoy;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectSummonSteed;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectSummonUndead;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectSummonVex;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectSummonWolves;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectToss;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectWall;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectWindshear;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectWither;
import com.hollingsworth.arsnouveau.common.spell.method.MethodOrbit;
import com.hollingsworth.arsnouveau.common.spell.method.MethodProjectile;
import com.hollingsworth.arsnouveau.common.spell.method.MethodSelf;
import com.hollingsworth.arsnouveau.common.spell.method.MethodTouch;
import com.hollingsworth.arsnouveau.common.spell.method.MethodUnderfoot;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GlyphRecipeProvider implements DataProvider {
   public final DataGenerator generator;
   protected static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private static final Logger LOGGER = LogManager.getLogger();
   public List<GlyphRecipe> recipes = new ArrayList<>();

   public GlyphRecipeProvider(DataGenerator generatorIn) {
      this.generator = generatorIn;
   }

   public void m_213708_(CachedOutput cache) throws IOException {
      Path output = this.generator.m_123916_();
      this.add(this.get(AugmentAccelerate.INSTANCE).withItem(Items.f_41860_).withItem(Items.f_42501_).withItem(Items.f_42524_));
      this.add(this.get(AugmentDecelerate.INSTANCE).withItem(Items.f_42049_).withItem(Items.f_41863_).withItem(Items.f_42524_));
      this.add(this.get(AugmentAmplify.INSTANCE).withItem(Items.f_42390_));
      this.add(this.get(AugmentAOE.INSTANCE).withItem(Items.f_42689_));
      this.add(this.get(AugmentDampen.INSTANCE).withItem(Items.f_42691_));
      this.add(this.get(AugmentDurationDown.INSTANCE).withItem(Items.f_42524_).withItem(Items.f_42525_));
      this.add(
         this.get(AugmentExtendTime.INSTANCE)
            .withItem(Items.f_42524_)
            .withIngredient(Ingredient.m_204132_(net.minecraftforge.common.Tags.Items.STORAGE_BLOCKS_REDSTONE))
      );
      this.add(this.get(AugmentExtract.INSTANCE).withItem(Items.f_42616_));
      this.add(this.get(AugmentFortune.INSTANCE).withItem(Items.f_42648_));
      this.add(this.get(AugmentPierce.INSTANCE).withItem(Items.f_42412_).withItem(ItemsRegistry.WILDEN_SPIKE));
      this.add(this.get(AugmentSensitive.INSTANCE).withItem(Items.f_42340_).withItem(Items.f_41940_).withItem(Items.f_42447_));
      this.add(this.get(AugmentSplit.INSTANCE).withItem(BlockRegistry.RELAY_SPLITTER).withItem(ItemsRegistry.WILDEN_SPIKE).withItem(Items.f_42776_));
      this.add(
         this.get(MethodOrbit.INSTANCE)
            .withItem(Items.f_42522_)
            .withItem(Items.f_42545_)
            .withIngredient(Ingredient.m_204132_(net.minecraftforge.common.Tags.Items.RODS_BLAZE))
      );
      this.add(this.get(MethodProjectile.INSTANCE).withItem(Items.f_42772_).withItem(Items.f_42412_));
      this.add(this.get(MethodSelf.INSTANCE).withIngredient(Ingredient.m_204132_(ItemTags.f_13177_)).withItem(Items.f_42469_));
      this.add(this.get(MethodTouch.INSTANCE).withIngredient(Ingredient.m_204132_(ItemTags.f_13171_)));
      this.add(this.get(MethodUnderfoot.INSTANCE).withItem(Items.f_42471_).withIngredient(Ingredient.m_204132_(ItemTags.f_13177_)));
      this.add(
         this.get(EffectBlink.INSTANCE)
            .withItem(ItemsRegistry.MANIPULATION_ESSENCE)
            .withIngredient(Ingredient.m_204132_(net.minecraftforge.common.Tags.Items.ENDER_PEARLS), 4)
      );
      this.add(
         this.get(EffectBounce.INSTANCE)
            .withItem(ItemsRegistry.ABJURATION_ESSENCE)
            .withIngredient(Ingredient.m_204132_(net.minecraftforge.common.Tags.Items.SLIMEBALLS), 3)
      );
      this.add(this.get(EffectBreak.INSTANCE).withItem(Items.f_42385_));
      this.add(this.get(EffectColdSnap.INSTANCE).withItem(ItemsRegistry.WATER_ESSENCE).withItem(Items.f_151055_).withItem(Items.f_41980_));
      this.add(this.get(EffectConjureWater.INSTANCE).withItem(ItemsRegistry.WATER_ESSENCE).withItem(Items.f_42447_));
      this.add(this.get(EffectCraft.INSTANCE).withItem(Items.f_41960_));
      this.add(this.get(EffectCrush.INSTANCE).withItem(ItemsRegistry.EARTH_ESSENCE).withItem(Items.f_42773_).withItem(Items.f_41869_));
      this.add(this.get(EffectCut.INSTANCE).withItem(ItemsRegistry.MANIPULATION_ESSENCE).withItem(Items.f_42574_).withItem(Items.f_42383_));
      this.add(this.get(EffectDelay.INSTANCE).withItem(ItemsRegistry.MANIPULATION_ESSENCE).withItem(Items.f_42350_).withItem(Items.f_42524_));
      this.add(this.get(EffectDispel.INSTANCE).withItem(ItemsRegistry.ABJURATION_ESSENCE).withItem(Items.f_42455_, 3));
      this.add(this.get(EffectEnderChest.INSTANCE).withItem(ItemsRegistry.MANIPULATION_ESSENCE).withItem(Items.f_42108_));
      this.add(this.get(EffectEvaporate.INSTANCE).withItem(ItemsRegistry.MANIPULATION_ESSENCE).withItem(Items.f_41902_, 3));
      this.add(
         this.get(EffectExchange.INSTANCE)
            .withItem(ItemsRegistry.MANIPULATION_ESSENCE)
            .withItem(Items.f_42110_)
            .withIngredient(Ingredient.m_204132_(net.minecraftforge.common.Tags.Items.ENDER_PEARLS), 2)
      );
      this.add(this.get(EffectExplosion.INSTANCE).withItem(ItemsRegistry.FIRE_ESSENCE).withItem(Items.f_41996_, 3).withItem(Items.f_42613_));
      this.add(this.get(EffectFangs.INSTANCE).withItem(ItemsRegistry.CONJURATION_ESSENCE).withItem(Items.f_42695_, 2).withItem(Items.f_42747_));
      this.add(this.get(EffectFell.INSTANCE).withItem(ItemsRegistry.EARTH_ESSENCE).withItem(Items.f_42391_));
      this.add(this.get(EffectFirework.INSTANCE).withItem(ItemsRegistry.FIRE_ESSENCE).withItem(Items.f_42688_, 2).withItem(Items.f_42689_));
      this.add(
         this.get(EffectFlare.INSTANCE).withItem(ItemsRegistry.FIRE_ESSENCE).withItem(Items.f_42409_, 2).withItem(Items.f_42613_, 2).withItem(Items.f_42585_)
      );
      this.add(this.get(EffectFreeze.INSTANCE).withItem(ItemsRegistry.WATER_ESSENCE).withItem(Items.f_41981_, 2));
      this.add(
         this.get(EffectGlide.INSTANCE)
            .withItem(ItemsRegistry.AIR_ESSENCE)
            .withItem(Items.f_42741_)
            .withIngredient(Ingredient.m_204132_(net.minecraftforge.common.Tags.Items.GEMS_DIAMOND), 3)
      );
      this.add(
         this.get(EffectGravity.INSTANCE)
            .withItem(ItemsRegistry.AIR_ESSENCE)
            .withItem(Items.f_42146_, 2)
            .withIngredient(Ingredient.m_204132_(net.minecraftforge.common.Tags.Items.FEATHERS), 3)
      );
      this.add(
         this.get(EffectGrow.INSTANCE)
            .withItem(ItemsRegistry.EARTH_ESSENCE)
            .withItem(Items.f_42262_, 5)
            .withIngredient(Ingredient.m_204132_(net.minecraftforge.common.Tags.Items.SEEDS), 3)
      );
      this.add(this.get(EffectHarm.INSTANCE).withItem(ItemsRegistry.EARTH_ESSENCE).withItem(Items.f_42383_, 3));
      this.add(this.get(EffectHarvest.INSTANCE).withItem(ItemsRegistry.EARTH_ESSENCE).withItem(Items.f_42387_, 1));
      this.add(this.get(EffectHeal.INSTANCE).withItem(ItemsRegistry.ABJURATION_ESSENCE).withItem(Items.f_42546_, 4).withItem(Items.f_42436_));
      this.add(
         this.get(EffectHex.INSTANCE).withItem(ItemsRegistry.ABJURATION_ESSENCE).withItem(Items.f_42592_).withItem(Items.f_42585_, 3).withItem(Items.f_41951_)
      );
      this.add(this.get(EffectIgnite.INSTANCE).withItem(Items.f_42409_).withIngredient(ItemTags.f_13160_, 3));
      this.add(
         this.get(EffectIntangible.INSTANCE)
            .withItem(ItemsRegistry.MANIPULATION_ESSENCE)
            .withItem(Items.f_42714_, 3)
            .withIngredient(Ingredient.m_204132_(net.minecraftforge.common.Tags.Items.ENDER_PEARLS), 2)
      );
      this.add(
         this.get(EffectInteract.INSTANCE)
            .withItem(ItemsRegistry.MANIPULATION_ESSENCE)
            .withItem(Items.f_41966_)
            .withIngredient(Ingredient.m_204132_(ItemTags.f_13177_))
            .withIngredient(Ingredient.m_204132_(ItemTags.f_13171_))
      );
      this.add(
         this.get(EffectInvisibility.INSTANCE)
            .withItem(ItemsRegistry.ABJURATION_ESSENCE)
            .withItem(Items.f_42592_)
            .withIngredient(Ingredient.m_204132_(net.minecraftforge.common.Tags.Items.RODS_BLAZE))
      );
      this.add(this.get(EffectKnockback.INSTANCE).withItem(ItemsRegistry.AIR_ESSENCE).withItem(Items.f_41869_, 3));
      this.add(this.get(EffectLaunch.INSTANCE).withItem(ItemsRegistry.AIR_ESSENCE).withItem(Items.f_42649_, 3));
      this.add(this.get(EffectLeap.INSTANCE).withItem(ItemsRegistry.AIR_ESSENCE).withItem(ItemsRegistry.WILDEN_WING, 3));
      this.add(this.get(EffectLight.INSTANCE).withItem(Items.f_42778_).withItem(Items.f_42000_));
      this.add(this.get(EffectLightning.INSTANCE).withItem(ItemsRegistry.AIR_ESSENCE).withItem(Items.f_151041_, 3).withItem(Items.f_42716_));
      this.add(
         this.get(EffectLinger.INSTANCE)
            .withItem(ItemsRegistry.MANIPULATION_ESSENCE)
            .withItem(Items.f_42735_)
            .withIngredient(Ingredient.m_204132_(net.minecraftforge.common.Tags.Items.STORAGE_BLOCKS_DIAMOND))
            .withIngredient(Ingredient.m_204132_(net.minecraftforge.common.Tags.Items.RODS_BLAZE), 2)
      );
      this.add(this.get(EffectPhantomBlock.INSTANCE).withIngredient(net.minecraftforge.common.Tags.Items.GLASS, 8));
      this.add(this.get(EffectPickup.INSTANCE).withItem(Items.f_42155_, 2));
      this.add(this.get(EffectPull.INSTANCE).withItem(Items.f_42523_, 1));
      this.add(
         this.get(EffectRedstone.INSTANCE)
            .withItem(ItemsRegistry.MANIPULATION_ESSENCE)
            .withIngredient(net.minecraftforge.common.Tags.Items.STORAGE_BLOCKS_REDSTONE, 3)
      );
      this.add(this.get(EffectRune.INSTANCE).withItem(ItemsRegistry.MANIPULATION_ESSENCE).withItem(ItemsRegistry.RUNIC_CHALK).withItem(Items.f_42109_));
      this.add(
         this.get(EffectSlowfall.INSTANCE)
            .withItem(ItemsRegistry.AIR_ESSENCE)
            .withItem(ItemsRegistry.WILDEN_WING)
            .withItem(Items.f_42402_, 3)
            .withIngredient(net.minecraftforge.common.Tags.Items.RODS_BLAZE, 1)
            .withIngredient(net.minecraftforge.common.Tags.Items.CROPS_NETHER_WART, 1)
      );
      this.add(
         this.get(EffectSmelt.INSTANCE)
            .withItem(ItemsRegistry.FIRE_ESSENCE)
            .withItem(Items.f_42770_, 4)
            .withIngredient(net.minecraftforge.common.Tags.Items.RODS_BLAZE, 1)
      );
      this.add(this.get(EffectSnare.INSTANCE).withItem(ItemsRegistry.EARTH_ESSENCE).withItem(Items.f_41863_, 4));
      this.add(this.get(EffectSummonDecoy.INSTANCE).withItem(ItemsRegistry.CONJURATION_ESSENCE).withItem(Items.f_42650_, 4));
      this.add(this.get(EffectSummonSteed.INSTANCE).withItem(Items.f_42454_, 4));
      this.add(this.get(EffectSummonVex.INSTANCE).withItem(ItemsRegistry.CONJURATION_ESSENCE).withItem(Items.f_42747_, 1));
      this.add(
         this.get(EffectSummonWolves.INSTANCE).withItem(ItemsRegistry.CONJURATION_ESSENCE).withItem(Items.f_42500_, 3).withItem(ItemsRegistry.WILDEN_WING, 4)
      );
      this.add(this.get(EffectToss.INSTANCE).withItem(ItemsRegistry.MANIPULATION_ESSENCE).withItem(Items.f_42162_, 1));
      this.add(this.get(EffectWindshear.INSTANCE).withItem(ItemsRegistry.AIR_ESSENCE).withItem(Items.f_42383_, 3));
      this.add(this.get(EffectWither.INSTANCE).withItem(ItemsRegistry.ABJURATION_ESSENCE).withItem(Items.f_42679_, 3));
      this.add(this.get(EffectPlaceBlock.INSTANCE).withItem(ItemsRegistry.MANIPULATION_ESSENCE).withItem(Items.f_41855_));
      this.add(this.get(EffectSummonUndead.INSTANCE).withItem(ItemsRegistry.CONJURATION_ESSENCE).withItem(Items.f_42500_, 1).withItem(Items.f_42679_));
      this.add(this.get(EffectName.INSTANCE).withItem(ItemsRegistry.MANIPULATION_ESSENCE).withItem(Items.f_42656_));
      this.add(
         this.get(EffectSenseMagic.INSTANCE)
            .withItem(ItemsRegistry.ABJURATION_ESSENCE)
            .withItem(ItemsRegistry.DOWSING_ROD)
            .withItem(ItemsRegistry.STARBUNCLE_SHARD)
      );
      this.add(
         this.get(EffectInfuse.INSTANCE)
            .withItem(ItemsRegistry.ABJURATION_ESSENCE)
            .withItem(Items.f_42590_)
            .withIngredient(net.minecraftforge.common.Tags.Items.RODS_BLAZE, 1)
      );
      this.add(
         this.get(EffectWall.INSTANCE)
            .withItem(ItemsRegistry.MANIPULATION_ESSENCE)
            .withItem(Items.f_42735_)
            .withIngredient(Ingredient.m_204132_(net.minecraftforge.common.Tags.Items.STORAGE_BLOCKS_DIAMOND))
            .withIngredient(Ingredient.m_204132_(net.minecraftforge.common.Tags.Items.RODS_BLAZE), 2)
      );
      this.add(this.get(EffectRotate.INSTANCE).withItem(ItemsRegistry.MANIPULATION_ESSENCE));
      this.add(this.get(EffectAnimate.INSTANCE).withItem(ItemsRegistry.CONJURATION_ESSENCE).withIngredient(net.minecraftforge.common.Tags.Items.OBSIDIAN, 3));
      this.add(this.get(EffectBurst.INSTANCE).withItem(ItemsRegistry.MANIPULATION_ESSENCE).withItem(Items.f_41996_, 5).withItem(Items.f_42689_));
      this.add(this.get(AugmentRandomize.INSTANCE).withItem(Items.f_42136_, 2));

      for (GlyphRecipe recipe : this.recipes) {
         Path path = getScribeGlyphPath(output, recipe.output.m_41720_());
         DataProvider.m_236072_(cache, recipe.asRecipe(), path);
      }
   }

   public void add(GlyphRecipe recipe) {
      this.recipes.add(recipe);
   }

   public GlyphRecipe get(AbstractSpellPart spellPart) {
      return new GlyphRecipe(
         spellPart.getRegistryName(), ArsNouveauAPI.getInstance().getGlyphItem(spellPart).m_7968_(), new ArrayList<>(), this.getExpFromTier(spellPart)
      );
   }

   public int getExpFromTier(AbstractSpellPart spellPart) {
      return switch (spellPart.defaultTier().value) {
         case 1 -> 27;
         case 2 -> 55;
         case 3 -> 160;
         default -> 0;
      };
   }

   protected static Path getScribeGlyphPath(Path pathIn, Item glyph) {
      return pathIn.resolve("data/ars_nouveau/recipes/" + RegistryHelper.getRegistryName(glyph).m_135815_() + ".json");
   }

   public String m_6055_() {
      return "Glyph Recipes";
   }
}
