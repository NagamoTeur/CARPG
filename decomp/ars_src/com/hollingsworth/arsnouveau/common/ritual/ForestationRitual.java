package com.hollingsworth.arsnouveau.common.ritual;

import com.hollingsworth.arsnouveau.api.ritual.FeaturePlacementRitual;
import com.hollingsworth.arsnouveau.api.ritual.features.BigTreeFeature;
import com.hollingsworth.arsnouveau.api.ritual.features.BonemealFeature;
import com.hollingsworth.arsnouveau.api.ritual.features.CocoaFeature;
import com.hollingsworth.arsnouveau.api.ritual.features.ConvertBlockFeature;
import com.hollingsworth.arsnouveau.api.ritual.features.IPlaceableFeature;
import com.hollingsworth.arsnouveau.api.ritual.features.PlaceBlockFeature;
import com.hollingsworth.arsnouveau.api.ritual.features.RandomTreeFeature;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.common.lib.RitualLib;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import oshi.util.tuples.Pair;

public class ForestationRitual extends FeaturePlacementRitual {
   @Override
   public void addFeatures(List<IPlaceableFeature> features) {
      boolean isTaiga = this.getConsumedItems().stream().anyMatch(i -> i.m_41720_() == Items.f_41952_);
      boolean isJungle = this.getConsumedItems().stream().anyMatch(i -> i.m_41720_() == Items.f_151079_);
      if (isTaiga) {
         features.add(new RandomTreeFeature(List.of(Blocks.f_50747_.m_49966_()), 8.0, 0.8));
         features.add(
            new ConvertBlockFeature(
               0.0,
               0.8,
               state -> state.m_204336_(BlockTags.f_144274_) || state.m_60734_() == Blocks.f_50440_,
               state -> Blocks.f_50599_.m_49966_(),
               new Pair(new BlockPos(0, -1, 0), BlockPos.f_121853_)
            )
         );
         features.add(
            new PlaceBlockFeature(0.0, 0.1, () -> (double)this.getWorld().f_46441_.m_188501_() < 0.3 ? Blocks.f_50360_.m_49966_() : Blocks.f_50035_.m_49966_())
         );
         features.add(new PlaceBlockFeature(0.0, 0.1, Blocks.f_50072_::m_49966_));
         features.add(new PlaceBlockFeature(0.0, 0.1, Blocks.f_50034_::m_49966_));
         features.add(
            new PlaceBlockFeature(
               0.0, 0.01, () -> (BlockState)Blocks.f_50685_.m_49966_().m_61124_(SweetBerryBushBlock.f_57244_, this.getWorld().f_46441_.m_188503_(4))
            )
         );
      } else if (isJungle) {
         features.add(new BigTreeFeature(Blocks.f_50749_.m_49966_(), 12.0, 0.3));
         features.add(new RandomTreeFeature(List.of(Blocks.f_50749_.m_49966_()), 6.0, 0.95));
         features.add(new CocoaFeature(6.0, 0.4));
         features.add(new PlaceBlockFeature(0.0, 0.1, Blocks.f_50034_::m_49966_));
         features.add(new PlaceBlockFeature(0.0, 0.01, Blocks.f_50186_::m_49966_));
         features.add(new PlaceBlockFeature(0.0, 0.1, Blocks.f_50035_::m_49966_));
      } else {
         features.add(new RandomTreeFeature(List.of(Blocks.f_50746_.m_49966_(), Blocks.f_50748_.m_49966_()), 5.0, 0.8));
         features.add(new BonemealFeature(6.0, 0.8));
      }
   }

   @Override
   public ResourceLocation getRegistryName() {
      return new ResourceLocation("ars_nouveau", RitualLib.FORESTATION);
   }

   @Override
   public boolean canConsumeItem(ItemStack stack) {
      boolean isTaiga = this.getConsumedItems().stream().anyMatch(i -> i.m_41720_() == Items.f_41952_);
      boolean isJungle = this.getConsumedItems().stream().anyMatch(i -> i.m_41720_() == Items.f_151079_);
      boolean isVariant = isTaiga || isJungle;
      return super.canConsumeItem(stack) || stack.m_41720_() == Items.f_41952_ && !isVariant || stack.m_41720_() == Items.f_151079_ && !isVariant;
   }

   @Override
   public String getLangName() {
      return "Forestation";
   }

   @Override
   public String getLangDescription() {
      return "Places grown Oak and Birch trees, and applies bonemeal in a 7x7 (circular) area. Augmenting with a source gem will increase the radius by 1 for each gem. Augmenting with a Brown Mushroom will convert the area to Podzol and spawn taiga resources. Augmenting with Glow Berries will spawn jungle resources.";
   }

   @Override
   public ParticleColor getCenterColor() {
      return new ParticleColor(0, 255, 0);
   }
}
