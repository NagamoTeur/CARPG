package com.hollingsworth.arsnouveau.common.spell.validation;

import com.hollingsworth.arsnouveau.api.spell.AbstractCastMethod;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.SpellValidationError;
import java.util.List;

public class MaxOneCastMethodSpellValidator extends ScanningSpellValidator<MaxOneCastMethodSpellValidator.OneCastContext> {
   protected MaxOneCastMethodSpellValidator.OneCastContext initContext() {
      return new MaxOneCastMethodSpellValidator.OneCastContext();
   }

   protected void digestSpellPart(
      MaxOneCastMethodSpellValidator.OneCastContext context, int position, AbstractSpellPart spellPart, List<SpellValidationError> validationErrors
   ) {
      if (spellPart instanceof AbstractCastMethod) {
         context.count++;
         if (context.count > 1) {
            validationErrors.add(new MaxOneCastMethodSpellValidator.OneCastMethodSpellValidationError(position, (AbstractCastMethod)spellPart));
         }
      }
   }

   protected void finish(MaxOneCastMethodSpellValidator.OneCastContext context, List<SpellValidationError> validationErrors) {
   }

   public static class OneCastContext {
      int count = 0;
   }

   private static class OneCastMethodSpellValidationError extends BaseSpellValidationError {
      public OneCastMethodSpellValidationError(int position, AbstractCastMethod method) {
         super(position, method, "max_one_cast_method", method);
      }
   }
}
