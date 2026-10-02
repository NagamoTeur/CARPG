package com.hollingsworth.arsnouveau.common.ritual;

import com.hollingsworth.arsnouveau.api.ritual.ConjureBiomeRitual;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.common.lib.RitualLib;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ConjurePlainsRitual extends ConjureBiomeRitual {
   boolean isSnowy;

   public ConjurePlainsRitual() {
      super(Biomes.f_48202_);
   }

   @Override
   public void onStart() {
      super.onStart();
      this.isSnowy = this.getConsumedItems().stream().anyMatch(i -> i.m_150930_(BlockRegistry.FROSTAYA_POD.m_5456_()));
      if (this.isSnowy) {
         this.biome = Biomes.f_186761_;
      }
   }

   @Override
   public BlockState stateForPos(BlockPos nextPos) {
      return nextPos.m_123342_() == this.getPos().m_123342_() - 1 ? Blocks.f_50440_.m_49966_() : Blocks.f_50493_.m_49966_();
   }

   @Override
   public boolean canConsumeItem(ItemStack stack) {
      boolean frostaya = this.getConsumedItems().stream().anyMatch(i -> i.m_150930_(BlockRegistry.FROSTAYA_POD.m_5456_()));
      return super.canConsumeItem(stack) || stack.m_150930_(BlockRegistry.FROSTAYA_POD.m_5456_()) && !frostaya;
   }

   @Override
   public ResourceLocation getRegistryName() {
      return new ResourceLocation("ars_nouveau", RitualLib.PLAINS);
   }

   @Override
   public String getLangName() {
      return "Conjure Island: Plains";
   }

   @Override
   public String getLangDescription() {
      return "Creates an island of grass and dirt in a circle around the ritual, converting the area to Plains. Augmenting with a Frostaya with convert to Snow Plains. The island will generate with a radius of 7 blocks. Augmenting the ritual with Source Gems will increase the radius by 1 for each gem. Source must be provided nearby as blocks are generated.";
   }

   @Override
   public ParticleColor getCenterColor() {
      return !this.isSnowy && !this.didConsumeItem(BlockRegistry.FROSTAYA_POD) ? new ParticleColor(100, 255, 100) : new ParticleColor(100, 100, 150);
   }

   @Override
   public void write(CompoundTag tag) {
      super.write(tag);
      tag.m_128379_("isSnowy", this.isSnowy);
   }

   @Override
   public void read(CompoundTag tag) {
      super.read(tag);
      this.isSnowy = tag.m_128471_("isSnowy");
      if (this.isSnowy) {
         this.biome = Biomes.f_186761_;
      }
   }
}
