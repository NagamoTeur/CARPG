package com.hollingsworth.arsnouveau.common.spell.validation;

import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.ISpellValidator;
import com.hollingsworth.arsnouveau.api.spell.SpellValidationError;
import java.util.LinkedList;
import java.util.List;

public abstract class ScanningSpellValidator<T> implements ISpellValidator {
   protected abstract T initContext();

   protected abstract void digestSpellPart(T var1, int var2, AbstractSpellPart var3, List<SpellValidationError> var4);

   protected abstract void finish(T var1, List<SpellValidationError> var2);

   @Override
   public List<SpellValidationError> validate(List<AbstractSpellPart> spellRecipe) {
      T t = this.initContext();
      List<SpellValidationError> errors = new LinkedList<>();

      for (int pos = 0; pos < spellRecipe.size(); pos++) {
         AbstractSpellPart part = spellRecipe.get(pos);
         if (part != null) {
            this.digestSpellPart(t, pos, part, errors);
         }
      }

      this.finish(t, errors);
      return errors;
   }
}
