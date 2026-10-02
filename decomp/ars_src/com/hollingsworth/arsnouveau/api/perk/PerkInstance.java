package com.hollingsworth.arsnouveau.api.perk;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.common.perk.StarbunclePerk;
import java.util.Objects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public class PerkInstance {
   private PerkSlot slot;
   private IPerk perk;

   public PerkInstance(CompoundTag tag) {
      ResourceLocation perkId = new ResourceLocation(tag.m_128461_("perkId"));
      this.perk = ArsNouveauAPI.getInstance().getPerkMap().getOrDefault(perkId, StarbunclePerk.INSTANCE);
      this.slot = PerkSlot.PERK_SLOTS.getOrDefault(new ResourceLocation(tag.m_128461_("slotId")), PerkSlot.ONE);
   }

   public PerkInstance(PerkSlot slot, IPerk perk) {
      this.slot = slot;
      this.perk = perk;
   }

   public PerkSlot getSlot() {
      return this.slot;
   }

   public IPerk getPerk() {
      return this.perk;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         PerkInstance that = (PerkInstance)o;
         return Objects.equals(this.slot, that.slot) && Objects.equals(this.perk, that.perk);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.slot, this.perk);
   }
}
