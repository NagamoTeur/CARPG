package com.hollingsworth.arsnouveau.common.capability;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.familiar.AbstractFamiliarHolder;
import com.hollingsworth.arsnouveau.api.familiar.IFamiliar;
import com.hollingsworth.arsnouveau.common.lib.LibEntityNames;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class FamiliarData {
   public static final String ENTITY_TAG = "entityTag";
   public static final String FAMILIAR_ID = "familiarID";
   public AbstractFamiliarHolder familiarHolder;
   public CompoundTag entityTag;

   public FamiliarData(ResourceLocation entityID) {
      this.familiarHolder = ArsNouveauAPI.getInstance().getFamiliarHolderMap().get(entityID);
      this.entityTag = new CompoundTag();
   }

   public FamiliarData(CompoundTag tag) {
      this.entityTag = tag.m_128441_("entityTag") ? tag.m_128469_("entityTag") : new CompoundTag();
      this.familiarHolder = ArsNouveauAPI.getInstance()
         .getFamiliarHolderMap()
         .getOrDefault(
            new ResourceLocation(tag.m_128461_("familiarID")),
            ArsNouveauAPI.getInstance().getFamiliarHolderMap().get(new ResourceLocation("ars_nouveau", LibEntityNames.FAMILIAR_WIXIE))
         );
   }

   public CompoundTag toTag() {
      CompoundTag tag = new CompoundTag();
      tag.m_128359_("familiarID", this.familiarHolder.getRegistryName().toString());
      tag.m_128365_("entityTag", this.entityTag);
      return tag;
   }

   public IFamiliar getEntity(Level level) {
      IFamiliar familiar = this.familiarHolder.getSummonEntity(level, this.entityTag);
      familiar.setHolderID(this.familiarHolder.getRegistryName());
      return familiar;
   }
}
