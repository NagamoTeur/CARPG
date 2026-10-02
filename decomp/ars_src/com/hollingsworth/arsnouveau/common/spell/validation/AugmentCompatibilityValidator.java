package com.hollingsworth.arsnouveau.common.spell.validation;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.SpellValidationError;
import java.util.Collection;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AugmentCompatibilityValidator extends SpellPhraseValidator {
   private static Logger logger = LogManager.getLogger("ars_nouveau.AugmentCompatibilityValidator");

   @Override
   protected void validatePhrase(SpellPhraseValidator.SpellPhrase phrase, List<SpellValidationError> validationErrors) {
      AbstractSpellPart action = phrase.getAction();
      if (action != null) {
         phrase.getAugmentPositionMap()
            .values()
            .stream()
            .flatMap(Collection::stream)
            .forEach(
               aug -> {
                  if (!action.compatibleAugments.contains(aug.spellPart)) {
                     validationErrors.add(
                        new AugmentCompatibilityValidator.AugmentCompatibilitySpellValidationError(aug.position, action, (AbstractAugment)aug.spellPart)
                     );
                  }
               }
            );
      }
   }

   private static class AugmentCompatibilitySpellValidationError extends BaseSpellValidationError {
      public AugmentCompatibilitySpellValidationError(int position, AbstractSpellPart spellPart, AbstractAugment augment) {
         super(position, spellPart, "augment_compatibility", spellPart, augment);
      }
   }
}
