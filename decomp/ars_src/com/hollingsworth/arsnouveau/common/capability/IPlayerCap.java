package com.hollingsworth.arsnouveau.common.capability;

import com.hollingsworth.arsnouveau.api.familiar.AbstractFamiliarHolder;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

public interface IPlayerCap extends INBTSerializable<CompoundTag> {
   Collection<AbstractSpellPart> getKnownGlyphs();

   void setKnownGlyphs(Collection<AbstractSpellPart> var1);

   boolean unlockGlyph(AbstractSpellPart var1);

   boolean knowsGlyph(AbstractSpellPart var1);

   boolean unlockFamiliar(AbstractFamiliarHolder var1);

   boolean ownsFamiliar(AbstractFamiliarHolder var1);

   Collection<FamiliarData> getUnlockedFamiliars();

   @Nullable
   FamiliarData getFamiliarData(ResourceLocation var1);

   @Nullable
   FamiliarData getLastSummonedFamiliar();

   void setLastSummonedFamiliar(ResourceLocation var1);

   void setUnlockedFamiliars(Collection<FamiliarData> var1);

   boolean removeFamiliar(AbstractFamiliarHolder var1);
}
