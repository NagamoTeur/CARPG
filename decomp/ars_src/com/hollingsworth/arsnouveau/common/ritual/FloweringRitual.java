package com.hollingsworth.arsnouveau.common.ritual;

import com.hollingsworth.arsnouveau.api.ritual.FeaturePlacementRitual;
import com.hollingsworth.arsnouveau.api.ritual.features.BonemealFeature;
import com.hollingsworth.arsnouveau.api.ritual.features.IPlaceableFeature;
import com.hollingsworth.arsnouveau.api.ritual.features.PlaceBlockFeature;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.common.lib.RitualLib;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class FloweringRitual extends FeaturePlacementRitual {
   public List<Block> flowers = new ArrayList<>();

   public FloweringRitual() {
      this.flowers
         .addAll(
            Arrays.asList(
               Blocks.f_50111_,
               Blocks.f_50112_,
               Blocks.f_50113_,
               Blocks.f_50114_,
               Blocks.f_50115_,
               Blocks.f_50116_,
               Blocks.f_50117_,
               Blocks.f_50118_,
               Blocks.f_50119_,
               Blocks.f_50120_,
               Blocks.f_50121_,
               Blocks.f_50071_,
               Blocks.f_50355_,
               Blocks.f_50356_,
               Blocks.f_50358_,
               Blocks.f_50357_
            )
         );
   }

   @Override
   public void addFeatures(List<IPlaceableFeature> features) {
      boolean isDesert = this.getConsumedItems().stream().anyMatch(i -> i.m_204117_(ItemTags.f_13137_));
      if (!isDesert) {
         features.add(new PlaceBlockFeature(1.5, 0.8, () -> this.flowers.get(this.getWorld().f_46441_.m_188503_(this.flowers.size())).m_49966_()));
         features.add(new BonemealFeature(5.0, 0.8));
      } else {
         features.add(new PlaceBlockFeature(3.0, 0.04, Blocks.f_50128_::m_49966_));
         features.add(new PlaceBlockFeature(1.5, 0.1, Blocks.f_50036_::m_49966_));
      }
   }

   @Override
   public boolean canConsumeItem(ItemStack stack) {
      boolean isDesert = this.getConsumedItems().stream().anyMatch(i -> i.m_204117_(ItemTags.f_13137_));
      return super.canConsumeItem(stack) || !isDesert && stack.m_204117_(ItemTags.f_13137_);
   }

   @Override
   public ResourceLocation getRegistryName() {
      return new ResourceLocation("ars_nouveau", RitualLib.FLOWERING);
   }

   @Override
   public String getLangName() {
      return "Flowering";
   }

   @Override
   public String getLangDescription() {
      return "Populates the nearby area with flowers and grass. Augmenting with a source gem will increase the radius by 1 for each gem. Augmenting with sand will replace flowers with Cactus and Dead Bushes.";
   }

   @Override
   public ParticleColor getCenterColor() {
      return new ParticleColor(100, 100, 100);
   }
}
