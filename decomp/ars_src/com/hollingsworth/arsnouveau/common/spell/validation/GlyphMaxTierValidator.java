package com.hollingsworth.arsnouveau.common.spell.validation;

import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.SpellValidationError;
import java.util.List;
import net.minecraft.util.Unit;

public class GlyphMaxTierValidator extends ScanningSpellValidator<Unit> {
   private final int maxTier;

   public GlyphMaxTierValidator(int maxTier) {
      this.maxTier = maxTier;
   }

   protected Unit initContext() {
      return Unit.INSTANCE;
   }

   protected void digestSpellPart(Unit context, int position, AbstractSpellPart spellPart, List<SpellValidationError> validationErrors) {
      if (spellPart.getConfigTier().value > this.maxTier) {
         validationErrors.add(new GlyphMaxTierValidator.GlyphTierValidationError(position, spellPart, "glyph_tier"));
      }
   }

   protected void finish(Unit context, List<SpellValidationError> validationErrors) {
   }

   private static class GlyphTierValidationError extends BaseSpellValidationError {
      public GlyphTierValidationError(int position, AbstractSpellPart spellPart, String localizationCode) {
         super(position, spellPart, localizationCode, spellPart);
      }
   }
}
