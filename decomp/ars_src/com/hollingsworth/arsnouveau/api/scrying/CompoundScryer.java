package com.hollingsworth.arsnouveau.api.scrying;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

public class CompoundScryer implements IScryer {
   public static final CompoundScryer INSTANCE = new CompoundScryer();
   List<IScryer> scryerList = new ArrayList<>();

   public CompoundScryer(IScryer... scryerList) {
      Collections.addAll(this.scryerList, scryerList);
   }

   @Override
   public boolean shouldRevealBlock(BlockState state, BlockPos p, Player player) {
      return this.scryerList.stream().anyMatch(scryer -> scryer.shouldRevealBlock(state, p, player));
   }

   @Override
   public IScryer fromTag(CompoundTag tag) {
      CompoundScryer compoundScryer = new CompoundScryer();
      int count = tag.m_128451_("scryer_count");

      for (int i = 0; i < count; i++) {
         CompoundTag scryerTag = tag.m_128469_("scryer_" + i);
         String id = scryerTag.m_128461_("id");
         IScryer scryer = ArsNouveauAPI.getInstance().getScryer(new ResourceLocation(id));
         if (scryer != null) {
            compoundScryer.scryerList.add(scryer.fromTag(scryerTag));
         }
      }

      return compoundScryer;
   }

   @Override
   public CompoundTag toTag(CompoundTag tag) {
      tag.m_128405_("scryer_count", this.scryerList.size());

      for (int i = 0; i < this.scryerList.size(); i++) {
         tag.m_128365_("scryer_" + i, this.scryerList.get(i).toTag(new CompoundTag()));
      }

      return IScryer.super.toTag(tag);
   }

   @Override
   public ResourceLocation getRegistryName() {
      return new ResourceLocation("ars_nouveau", "compound_scryer");
   }
}
