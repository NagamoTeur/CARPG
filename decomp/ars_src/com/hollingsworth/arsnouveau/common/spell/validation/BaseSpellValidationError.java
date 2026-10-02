package com.hollingsworth.arsnouveau.common.spell.validation;

import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.SpellValidationError;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class BaseSpellValidationError implements SpellValidationError {
   private static final Component[] NO_ARGS = new Component[0];
   private final int position;
   private final AbstractSpellPart spellPart;
   private final String localizationCode;
   private final Object[] translationArguments;

   public BaseSpellValidationError(int position, AbstractSpellPart spellPart, String localizationCode, Component... translationArguments) {
      this.position = position;
      this.spellPart = spellPart;
      this.localizationCode = localizationCode;
      this.translationArguments = translationArguments;
   }

   public BaseSpellValidationError(int position, AbstractSpellPart spellPart, String localizationCode) {
      this(position, spellPart, localizationCode, NO_ARGS);
   }

   public BaseSpellValidationError(int position, AbstractSpellPart spellPart, String localizationCode, AbstractSpellPart... spellParts) {
      this(position, spellPart, localizationCode, translateGlyphs(spellParts));
   }

   @Override
   public int getPosition() {
      return this.position;
   }

   @Nullable
   @Override
   public AbstractSpellPart getSpellPart() {
      return this.spellPart;
   }

   @Override
   public MutableComponent makeTextComponentExisting() {
      return this.makeTextComponent("ars_nouveau.spell.validation.exists.");
   }

   @Override
   public MutableComponent makeTextComponentAdding() {
      return this.makeTextComponent("ars_nouveau.spell.validation.adding.");
   }

   private MutableComponent makeTextComponent(String keyPrefix) {
      return Component.m_237110_(keyPrefix + this.localizationCode, this.translationArguments);
   }

   private static Component[] translateGlyphs(AbstractSpellPart... parts) {
      Component[] textComponents = new Component[parts.length];

      for (int i = 0; i < parts.length; i++) {
         textComponents[i] = Component.m_237115_(parts[i].getLocalizationKey());
      }

      return textComponents;
   }
}
