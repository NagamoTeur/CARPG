package com.hollingsworth.arsnouveau.api.spell;

import java.util.List;

public interface ISpellValidator {
   List<SpellValidationError> validate(List<AbstractSpellPart> var1);
}
