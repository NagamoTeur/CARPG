package com.hollingsworth.arsnouveau.api.spell;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.network.chat.Component;

public class SpellSchool {
   private final String id;
   private Set<SpellSchool> subSchools = new HashSet<>();
   private Set<AbstractSpellPart> spellParts = new HashSet<>();

   public SpellSchool(String id) {
      this.id = id;
   }

   public boolean isPartOfSchool(AbstractSpellPart part) {
      if (this.getSpellParts().contains(part)) {
         return true;
      } else {
         for (SpellSchool spellSchool : this.getSubSchools()) {
            if (spellSchool.getSpellParts().contains(part)) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean addSpellPart(AbstractSpellPart spellPart) {
      return this.getSpellParts().add(spellPart);
   }

   public Component getTextComponent() {
      return Component.m_237115_("ars_nouveau.school." + this.getId());
   }

   public String getId() {
      return this.id;
   }

   public Set<SpellSchool> getSubSchools() {
      return this.subSchools;
   }

   public void setSubSchools(Set<SpellSchool> subSchools) {
      this.subSchools = subSchools;
   }

   public SpellSchool withSubSchool(SpellSchool spellSchool) {
      this.getSubSchools().add(spellSchool);
      return this;
   }

   public Set<AbstractSpellPart> getSpellParts() {
      return this.spellParts;
   }

   public void setSpellParts(Set<AbstractSpellPart> spellParts) {
      this.spellParts = spellParts;
   }
}
