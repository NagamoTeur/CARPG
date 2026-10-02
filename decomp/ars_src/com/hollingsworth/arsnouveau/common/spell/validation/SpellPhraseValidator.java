package com.hollingsworth.arsnouveau.common.spell.validation;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.SpellValidationError;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;

public abstract class SpellPhraseValidator extends AbstractSpellValidator {
   protected abstract void validatePhrase(SpellPhraseValidator.SpellPhrase var1, List<SpellValidationError> var2);

   @Override
   protected void validateImpl(List<AbstractSpellPart> spellRecipe, List<SpellValidationError> validationErrors) {
      for (SpellPhraseValidator.SpellPhrase phrase : splitSpellIntoPhrases(spellRecipe)) {
         this.validatePhrase(phrase, validationErrors);
      }
   }

   public static List<SpellPhraseValidator.SpellPhrase> splitSpellIntoPhrases(List<AbstractSpellPart> recipe) {
      List<SpellPhraseValidator.SpellPhrase> phrases = new LinkedList<>();
      AbstractSpellPart action = null;
      List<AbstractAugment> augments = new ArrayList<>();
      Map<ResourceLocation, List<SpellPhraseValidator.SpellPhrase.SpellPartPosition<AbstractAugment>>> augmentPositionMap = new LinkedHashMap<>();
      int phraseStart = 0;

      for (int pos = 0; pos < recipe.size(); pos++) {
         AbstractSpellPart currentPart = recipe.get(pos);
         if (currentPart instanceof AbstractAugment) {
            augments.add((AbstractAugment)currentPart);
            if (!augmentPositionMap.containsKey(currentPart.getRegistryName())) {
               augmentPositionMap.put(currentPart.getRegistryName(), new LinkedList<>());
            }

            augmentPositionMap.get(currentPart.getRegistryName())
               .add(new SpellPhraseValidator.SpellPhrase.SpellPartPosition<>((AbstractAugment)currentPart, pos));
         } else if (currentPart != null) {
            phrases.add(new SpellPhraseValidator.SpellPhrase(action, augments, augmentPositionMap, phraseStart));
            action = currentPart;
            augments = new ArrayList<>();
            augmentPositionMap = new LinkedHashMap<>();
            phraseStart = pos;
         }
      }

      phrases.add(new SpellPhraseValidator.SpellPhrase(action, augments, augmentPositionMap, phraseStart));
      return phrases;
   }

   public static class SpellPhrase {
      private final int firstPosition;
      private final AbstractSpellPart action;
      private final List<AbstractAugment> augments;
      private final Map<ResourceLocation, List<SpellPhraseValidator.SpellPhrase.SpellPartPosition<AbstractAugment>>> augmentPositionMap;

      private SpellPhrase(
         @Nullable AbstractSpellPart action,
         List<AbstractAugment> augments,
         Map<ResourceLocation, List<SpellPhraseValidator.SpellPhrase.SpellPartPosition<AbstractAugment>>> augmentPositionMap,
         int firstPosition
      ) {
         this.firstPosition = firstPosition;
         this.action = action;
         this.augments = augments;
         this.augmentPositionMap = augmentPositionMap;
      }

      @Nullable
      public AbstractSpellPart getAction() {
         return this.action;
      }

      public List<AbstractAugment> getAugments() {
         return this.augments;
      }

      public int getFirstPosition() {
         return this.firstPosition;
      }

      public Map<ResourceLocation, List<SpellPhraseValidator.SpellPhrase.SpellPartPosition<AbstractAugment>>> getAugmentPositionMap() {
         return this.augmentPositionMap;
      }

      public static final class SpellPartPosition<T extends AbstractSpellPart> {
         final T spellPart;
         final int position;

         public SpellPartPosition(T spellPart, int position) {
            this.spellPart = spellPart;
            this.position = position;
         }
      }
   }
}
