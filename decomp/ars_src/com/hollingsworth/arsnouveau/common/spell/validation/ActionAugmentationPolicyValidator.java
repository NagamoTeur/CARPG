package com.hollingsworth.arsnouveau.common.spell.validation;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.SpellValidationError;
import java.util.List;

public class ActionAugmentationPolicyValidator extends SpellPhraseValidator {
   @Override
   protected void validatePhrase(SpellPhraseValidator.SpellPhrase phrase, List<SpellValidationError> errors) {
      AbstractSpellPart action = phrase.getAction();
      if (action != null) {
         phrase.getAugmentPositionMap()
            .forEach(
               (augmentTag, augments) -> {
                  int limit = action.getAugmentLimit(augmentTag);
                  if (augments.size() > limit) {
                     for (int i = limit; i < augments.size(); i++) {
                        errors.add(
                           new ActionAugmentationPolicyValidator.ActionAugmentationPolicyValidationError(
                              augments.get(i).position, (AbstractAugment)augments.get(i).spellPart, action, limit
                           )
                        );
                     }
                  }
               }
            );
      }
   }

   private static class ActionAugmentationPolicyValidationError extends BaseSpellValidationError {
      public ActionAugmentationPolicyValidationError(int position, AbstractAugment augment, AbstractSpellPart action, int limit) {
         super(position, augment, limit <= 0 ? "action_augmentation_policy.zero" : "action_augmentation_policy", action, augment);
      }
   }
}
