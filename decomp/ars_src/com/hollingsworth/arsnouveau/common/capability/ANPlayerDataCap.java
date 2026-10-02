package com.hollingsworth.arsnouveau.common.capability;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.familiar.AbstractFamiliarHolder;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public class ANPlayerDataCap implements IPlayerCap {
   public Set<AbstractSpellPart> glyphs = new HashSet<>();
   public Set<FamiliarData> familiars = new HashSet<>();
   public ResourceLocation lastSummonedFamiliar;

   @Override
   public Collection<AbstractSpellPart> getKnownGlyphs() {
      return this.glyphs;
   }

   @Override
   public void setKnownGlyphs(Collection<AbstractSpellPart> glyphs) {
      this.glyphs = new HashSet<>(glyphs);
   }

   @Override
   public boolean unlockGlyph(AbstractSpellPart spellPart) {
      return this.glyphs.add(spellPart);
   }

   @Override
   public boolean knowsGlyph(AbstractSpellPart spellPart) {
      return this.glyphs.contains(spellPart);
   }

   @Override
   public boolean unlockFamiliar(AbstractFamiliarHolder holderID) {
      return this.familiars.add(new FamiliarData(holderID.getRegistryName()));
   }

   @Override
   public boolean ownsFamiliar(AbstractFamiliarHolder holderID) {
      return this.familiars.stream().anyMatch(f -> f.familiarHolder.getRegistryName().equals(holderID.getRegistryName()));
   }

   @Override
   public Collection<FamiliarData> getUnlockedFamiliars() {
      return this.familiars;
   }

   @Nullable
   @Override
   public FamiliarData getFamiliarData(ResourceLocation id) {
      return this.familiars.stream().filter(f -> f.familiarHolder.getRegistryName().equals(id)).findFirst().orElse(null);
   }

   @Nullable
   @Override
   public FamiliarData getLastSummonedFamiliar() {
      return this.lastSummonedFamiliar == null ? null : this.getFamiliarData(this.lastSummonedFamiliar);
   }

   @Override
   public void setLastSummonedFamiliar(ResourceLocation lastSummonedFamiliar) {
      this.lastSummonedFamiliar = lastSummonedFamiliar;
   }

   @Override
   public void setUnlockedFamiliars(Collection<FamiliarData> familiars) {
      this.familiars = new HashSet<>(familiars);
   }

   @Override
   public boolean removeFamiliar(AbstractFamiliarHolder holderID) {
      return this.familiars.removeIf(f -> f.familiarHolder.getRegistryName().equals(holderID.getRegistryName()));
   }

   public CompoundTag serializeNBT() {
      CompoundTag tag = new CompoundTag();
      CompoundTag glyphsTag = new CompoundTag();
      List<AbstractSpellPart> glyphsList = this.glyphs.stream().toList();

      for (int i = 0; i < glyphsList.size(); i++) {
         glyphsTag.m_128359_("glyph" + i, glyphsList.get(i).getRegistryName().toString());
      }

      glyphsTag.m_128405_("size", glyphsList.size());
      tag.m_128365_("glyphs", glyphsTag);
      CompoundTag familiarsTag = new CompoundTag();
      List<FamiliarData> familiarsList = this.familiars.stream().toList();

      for (int i = 0; i < familiarsList.size(); i++) {
         familiarsTag.m_128365_("familiar" + i, familiarsList.get(i).toTag());
      }

      familiarsTag.m_128405_("size", familiarsList.size());
      tag.m_128365_("familiars", familiarsTag);
      if (this.lastSummonedFamiliar != null) {
         tag.m_128359_("lastSummonedFamiliar", this.lastSummonedFamiliar.toString());
      }

      return tag;
   }

   public void deserializeNBT(CompoundTag nbt) {
      ArsNouveauAPI api = ArsNouveauAPI.getInstance();
      CompoundTag glyphsTag = nbt.m_128469_("glyphs");

      for (int i = 0; i < glyphsTag.m_128451_("size"); i++) {
         ResourceLocation id = new ResourceLocation(glyphsTag.m_128461_("glyph" + i));
         AbstractSpellPart part = api.getSpellPart(id);
         if (part != null) {
            this.glyphs.add(part);
         }
      }

      CompoundTag familiarsTag = nbt.m_128469_("familiars");

      for (int ix = 0; ix < familiarsTag.m_128451_("size"); ix++) {
         this.familiars.add(new FamiliarData(familiarsTag.m_128469_("familiar" + ix)));
      }

      if (nbt.m_128441_("lastSummonedFamiliar")) {
         this.lastSummonedFamiliar = new ResourceLocation(nbt.m_128461_("lastSummonedFamiliar"));
      }
   }
}
