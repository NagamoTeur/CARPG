package com.hollingsworth.arsnouveau.api.scrying;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

public class TagScryer implements IScryer {
   public static final TagScryer INSTANCE = new TagScryer();
   ResourceLocation tagID;
   TagKey<Block> blockTag;

   public TagScryer() {
   }

   public TagScryer(TagKey<Block> blockTag) {
      this.blockTag = blockTag;
      this.tagID = blockTag.f_203868_();
   }

   @Override
   public boolean shouldRevealBlock(BlockState state, BlockPos p, Player player) {
      return this.blockTag != null && state.m_204336_(this.blockTag);
   }

   @Override
   public IScryer fromTag(CompoundTag tag) {
      TagScryer scryer = new TagScryer();
      if (tag.m_128441_("blockTag")) {
         scryer.blockTag = ForgeRegistries.BLOCKS.tags().getTag(new TagKey(Registry.f_122901_, new ResourceLocation(tag.m_128461_("blockTag")))).getKey();
      }

      return scryer;
   }

   @Override
   public CompoundTag toTag(CompoundTag tag) {
      if (this.tagID != null) {
         tag.m_128359_("blockTag", this.tagID.toString());
      }

      return IScryer.super.toTag(tag);
   }

   @Override
   public ResourceLocation getRegistryName() {
      return new ResourceLocation("ars_nouveau", "tag_scryer");
   }
}
