package com.hollingsworth.arsnouveau.common.spell.validation;

import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.ISpellValidator;
import com.hollingsworth.arsnouveau.api.spell.SpellValidationError;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CombinedSpellValidator implements ISpellValidator {
   public final List<ISpellValidator> validators;

   public CombinedSpellValidator(List<ISpellValidator> validators) {
      this.validators = validators;
   }

   public CombinedSpellValidator(ISpellValidator... validators) {
      this.validators = Arrays.asList(validators);
   }

   @Override
   public List<SpellValidationError> validate(List<AbstractSpellPart> spellRecipe) {
      return this.validators.stream().flatMap(v -> v.validate(spellRecipe).stream()).collect(Collectors.toList());
   }
}
