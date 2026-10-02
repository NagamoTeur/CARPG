package com.hollingsworth.arsnouveau.api.nbt;

import com.hollingsworth.arsnouveau.api.perk.IPerk;
import com.hollingsworth.arsnouveau.api.perk.IPerkHolder;
import net.minecraft.nbt.CompoundTag;

public abstract class PerkData extends AbstractData {
   public IPerkHolder<?> perkHolder;
   public IPerk perk;

   public PerkData(IPerkHolder<?> perkHolder, IPerk perk) {
      super(perkHolder.getTagForPerk(perk));
      this.perkHolder = perkHolder;
      this.perk = perk;
   }

   public void writePerks() {
      CompoundTag tag = new CompoundTag();
      this.writeToNBT(tag);
      this.perkHolder.setTagForPerk(this.perk, tag);
   }

   @Override
   public void writeToNBT(CompoundTag tag) {
   }
}
