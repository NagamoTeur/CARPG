package com.hollingsworth.arsnouveau.common.spell.validation;

import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.ISpellValidator;
import com.hollingsworth.arsnouveau.api.spell.SpellValidationError;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractSpellValidator implements ISpellValidator {
   @Override
   public List<SpellValidationError> validate(List<AbstractSpellPart> spellRecipe) {
      List<SpellValidationError> errors = new LinkedList<>();
      this.validateImpl(spellRecipe, errors);
      return errors;
   }

   protected abstract void validateImpl(List<AbstractSpellPart> var1, List<SpellValidationError> var2);
}
