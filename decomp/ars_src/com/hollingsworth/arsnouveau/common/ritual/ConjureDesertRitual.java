package com.hollingsworth.arsnouveau.common.ritual;

import com.hollingsworth.arsnouveau.api.ritual.ConjureBiomeRitual;
import com.hollingsworth.arsnouveau.common.lib.RitualLib;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ConjureDesertRitual extends ConjureBiomeRitual {
   public ConjureDesertRitual() {
      super(Biomes.f_48203_);
   }

   @Override
   public void onStart() {
      super.onStart();
      if (this.getConsumedItems().stream().anyMatch(i -> i.m_204117_(ItemTags.f_198161_))) {
         this.biome = Biomes.f_48159_;
      }
   }

   @Override
   public BlockState stateForPos(BlockPos placePos) {
      boolean isBadlands = this.getConsumedItems().stream().anyMatch(i -> i.m_204117_(ItemTags.f_198161_));
      if (isBadlands) {
         int depth = this.getPos().m_123342_() - placePos.m_123342_();
         if (depth == 1) {
            return Blocks.f_49993_.m_49966_();
         } else if (depth == 2) {
            return Blocks.f_50288_.m_49966_();
         } else {
            return depth != 3 && depth != 4 ? Blocks.f_50352_.m_49966_() : Blocks.f_50301_.m_49966_();
         }
      } else {
         return placePos.m_123342_() == this.getPos().m_123342_() - 1 ? Blocks.f_49992_.m_49966_() : Blocks.f_50062_.m_49966_();
      }
   }

   @Override
   public String getLangName() {
      return "Conjure Island: Desert";
   }

   @Override
   public String getLangDescription() {
      return "Creates an island of sand and sandstone in a circle around the ritual, converting the area to Desert. The island will generate with a radius of 7 blocks. Augmenting the ritual with Source Gems will increase the radius by 1 for each gem. Source must be provided nearby as blocks are generated. Augmenting with Terracotta will create Badlands instead.";
   }

   @Override
   public boolean canConsumeItem(ItemStack stack) {
      boolean isBadlands = this.getConsumedItems().stream().anyMatch(i -> i.m_204117_(ItemTags.f_198161_));
      return super.canConsumeItem(stack) || !isBadlands && stack.m_204117_(ItemTags.f_198161_);
   }

   @Override
   public ResourceLocation getRegistryName() {
      return new ResourceLocation("ars_nouveau", RitualLib.DESERT);
   }

   @Override
   public void read(CompoundTag tag) {
      super.read(tag);
      boolean isBadlands = this.getConsumedItems().stream().anyMatch(i -> i.m_204117_(ItemTags.f_198161_));
      if (isBadlands) {
         this.biome = Biomes.f_48159_;
      }
   }
}
