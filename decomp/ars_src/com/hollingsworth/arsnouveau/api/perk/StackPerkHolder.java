package com.hollingsworth.arsnouveau.api.perk;

import com.google.common.collect.ImmutableList;
import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.nbt.ItemstackData;
import com.hollingsworth.arsnouveau.common.perk.StarbunclePerk;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public abstract class StackPerkHolder extends ItemstackData implements IPerkHolder<ItemStack> {
   private List<IPerk> perks;
   private int tier;
   private Map<IPerk, CompoundTag> perkTags = new HashMap<>();

   public StackPerkHolder(ItemStack stack) {
      super(stack);
      CompoundTag tag = this.getItemTag(stack);
      List<IPerk> perkList = new ArrayList<>();
      if (tag != null) {
         this.tier = tag.m_128451_("tier");
      }

      if (tag != null && tag.m_128441_("perks")) {
         ListTag perkTagList = tag.m_128437_("perks", 10);

         for (int i = 0; i < perkTagList.size(); i++) {
            CompoundTag perkTag = perkTagList.m_128728_(i);
            String perkName = perkTag.m_128461_("perk");
            CompoundTag perkData = perkTag.m_128469_("data");
            IPerk iPerk = ArsNouveauAPI.getInstance().getPerkMap().getOrDefault(new ResourceLocation(perkName), StarbunclePerk.INSTANCE);
            perkList.add(iPerk);
            this.perkTags.put(iPerk, perkData);
         }
      }

      this.perks = ImmutableList.copyOf(perkList);
   }

   @Override
   public void writeToNBT(CompoundTag tag) {
      ListTag listTag = new ListTag();
      this.getPerks().forEach(perk -> {
         CompoundTag perkTag = new CompoundTag();
         perkTag.m_128359_("perk", perk.getRegistryName().toString());
         perkTag.m_128365_("data", (Tag)this.perkTags.getOrDefault(perk, new CompoundTag()));
         listTag.add(perkTag);
      });
      tag.m_128405_("tier", this.tier);
      tag.m_128365_("perks", listTag);
   }

   @Override
   public List<IPerk> getPerks() {
      return this.perks;
   }

   @Override
   public void setPerks(List<IPerk> perks) {
      this.perks = new ArrayList<>(perks);
      this.writeItem();
   }

   @Override
   public int getTier() {
      return this.tier;
   }

   @Override
   public void setTier(int tier) {
      this.tier = tier;
      this.writeItem();
   }

   @Nullable
   @Override
   public CompoundTag getTagForPerk(IPerk perk) {
      return this.perkTags.getOrDefault(perk, null);
   }

   @Override
   public void setTagForPerk(IPerk perk, CompoundTag tag) {
      this.perkTags.put(perk, tag);
      this.writeItem();
   }

   @Override
   public String getTagString() {
      return "an_stack_perks";
   }
}
