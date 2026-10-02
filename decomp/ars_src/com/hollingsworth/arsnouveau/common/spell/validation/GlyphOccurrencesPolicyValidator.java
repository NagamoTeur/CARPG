package com.hollingsworth.arsnouveau.common.spell.validation;

import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.SpellValidationError;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;

public class GlyphOccurrencesPolicyValidator extends ScanningSpellValidator<Map<ResourceLocation, Integer>> {
   protected Map<ResourceLocation, Integer> initContext() {
      return new HashMap<>();
   }

   protected void digestSpellPart(
      Map<ResourceLocation, Integer> partCounts, int position, AbstractSpellPart spellPart, List<SpellValidationError> validationErrors
   ) {
      if (partCounts.containsKey(spellPart.getRegistryName())) {
         partCounts.put(spellPart.getRegistryName(), partCounts.get(spellPart.getRegistryName()) + 1);
      } else {
         partCounts.put(spellPart.getRegistryName(), 1);
      }

      int limit = spellPart.PER_SPELL_LIMIT == null ? Integer.MAX_VALUE : (Integer)spellPart.PER_SPELL_LIMIT.get();
      if (partCounts.getOrDefault(spellPart.getRegistryName(), 0) > limit) {
         validationErrors.add(new GlyphOccurrencesPolicyValidator.GlyphOccurrencesPolicySpellValidationError(position, spellPart, limit));
      }
   }

   protected void finish(Map<ResourceLocation, Integer> context, List<SpellValidationError> validationErrors) {
   }

   private static class GlyphOccurrencesPolicySpellValidationError extends BaseSpellValidationError {
      public GlyphOccurrencesPolicySpellValidationError(int position, AbstractSpellPart part, int limit) {
         super(position, part, "glyph_occurrences_policy", part);
      }
   }
}
