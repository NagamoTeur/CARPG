package com.hollingsworth.arsnouveau.client.container;

import net.minecraft.nbt.CompoundTag;

public class SortSettings {
   public int controlMode;
   public boolean reverseSort;
   public int sortType;
   public int searchType;
   public boolean expanded;

   public SortSettings(int controlMode, boolean reverseSort, int sortType, int searchType, boolean collapse) {
      this.controlMode = controlMode;
      this.reverseSort = reverseSort;
      this.sortType = sortType;
      this.searchType = searchType;
      this.expanded = collapse;
   }

   public SortSettings() {
   }

   public CompoundTag toTag() {
      CompoundTag updateTag = new CompoundTag();
      updateTag.m_128405_("controlMode", this.controlMode);
      updateTag.m_128379_("reverse", this.reverseSort);
      updateTag.m_128405_("sortType", this.sortType);
      updateTag.m_128405_("searchType", this.searchType);
      updateTag.m_128379_("expanded", this.expanded);
      return updateTag;
   }

   public static SortSettings fromTag(CompoundTag tag) {
      return new SortSettings(
         tag.m_128451_("controlMode"), tag.m_128471_("reverse"), tag.m_128451_("sortType"), tag.m_128451_("searchType"), tag.m_128471_("expanded")
      );
   }
}
