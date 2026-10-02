package com.hollingsworth.arsnouveau.api.spell;

import javax.annotation.Nullable;
import net.minecraft.network.chat.MutableComponent;

public interface SpellValidationError {
   int getPosition();

   @Nullable
   AbstractSpellPart getSpellPart();

   MutableComponent makeTextComponentExisting();

   MutableComponent makeTextComponentAdding();
}
