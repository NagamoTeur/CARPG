package com.hollingsworth.arsnouveau.common.spell.validation;

import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.ISpellValidator;
import com.hollingsworth.arsnouveau.api.spell.SpellValidationError;
import com.hollingsworth.arsnouveau.setup.config.ServerConfig;
import java.util.LinkedList;
import java.util.List;

public class StandardSpellValidator implements ISpellValidator {
   private static final ISpellValidator MAX_ONE_CAST_METHOD = new MaxOneCastMethodSpellValidator();
   private static final ISpellValidator NON_EMPTY_SPELL = new NonEmptySpellValidator();
   private static final ISpellValidator REQUIRE_CAST_METHOD_START = new StartingCastMethodSpellValidator();
   private static final ISpellValidator GLYPH_OCCURRENCES_POLICY = new GlyphOccurrencesPolicyValidator();
   private static final ISpellValidator EFFECT_AUGMENTATION_POLICY = new ActionAugmentationPolicyValidator();
   private static final ISpellValidator AUGMENT_COMPATIBILITY = new AugmentCompatibilityValidator();
   private static final ISpellValidator INVALID_COMBINATION_POLICY = new InvalidCombinationValidator();
   public final ISpellValidator combinedValidator;

   public StandardSpellValidator(boolean enforceCastTimeValidations) {
      List<ISpellValidator> validators = new LinkedList<>();
      validators.add(MAX_ONE_CAST_METHOD);
      if (!enforceCastTimeValidations) {
         validators.add(AUGMENT_COMPATIBILITY);
         validators.add(EFFECT_AUGMENTATION_POLICY);
         validators.add(GLYPH_OCCURRENCES_POLICY);
         validators.add(INVALID_COMBINATION_POLICY);
      }

      if (enforceCastTimeValidations) {
         validators.add(NON_EMPTY_SPELL);
         validators.add(REQUIRE_CAST_METHOD_START);
         if ((Boolean)ServerConfig.ENFORCE_AUGMENT_CAP_ON_CAST.get()) {
            validators.add(EFFECT_AUGMENTATION_POLICY);
         }

         if ((Boolean)ServerConfig.ENFORCE_GLYPH_LIMIT_ON_CAST.get()) {
            validators.add(GLYPH_OCCURRENCES_POLICY);
         }
      }

      this.combinedValidator = new CombinedSpellValidator(validators);
   }

   @Override
   public List<SpellValidationError> validate(List<AbstractSpellPart> spellRecipe) {
      return this.combinedValidator.validate(spellRecipe);
   }
}
