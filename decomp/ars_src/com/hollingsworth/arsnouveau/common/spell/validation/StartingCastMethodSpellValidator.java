package com.hollingsworth.arsnouveau.common.spell.validation;

import com.hollingsworth.arsnouveau.api.spell.AbstractCastMethod;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.SpellValidationError;
import java.util.List;

public class StartingCastMethodSpellValidator extends AbstractSpellValidator {
   @Override
   protected void validateImpl(List<AbstractSpellPart> spellRecipe, List<SpellValidationError> errors) {
      if (!spellRecipe.isEmpty() && !(spellRecipe.get(0) instanceof AbstractCastMethod)) {
         errors.add(new StartingCastMethodSpellValidator.StartingCastMethodSpellValidationError());
      }
   }

   private static class StartingCastMethodSpellValidationError extends BaseSpellValidationError {
      public StartingCastMethodSpellValidationError() {
         super(-1, null, "starting_cast_method");
      }
   }
}
