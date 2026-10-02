package com.hollingsworth.arsnouveau.api.spell;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.particle.ParticleColorRegistry;
import com.hollingsworth.arsnouveau.api.sound.ConfiguredSpellSound;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class Spell implements Cloneable {
   @Deprecated(
      forRemoval = true
   )
   public static final Spell EMPTY = new Spell();
   public List<AbstractSpellPart> recipe = new ArrayList<>();
   public String name = "";
   public ParticleColor color = ParticleColor.defaultParticleColor();
   public ConfiguredSpellSound sound = ConfiguredSpellSound.DEFAULT;
   private int discount = 0;

   public Spell(List<AbstractSpellPart> recipe) {
      this.recipe = (List<AbstractSpellPart>)(recipe == null ? new ArrayList<>() : recipe);
   }

   public Spell() {
   }

   public Spell(AbstractSpellPart... spellParts) {
      this.add(spellParts);
   }

   public Spell add(AbstractSpellPart spellPart) {
      this.recipe.add(spellPart);
      return this;
   }

   public Spell add(AbstractSpellPart... spellParts) {
      for (AbstractSpellPart part : spellParts) {
         this.add(part);
      }

      return this;
   }

   public Spell add(AbstractSpellPart spellPart, int count) {
      for (int i = 0; i < count; i++) {
         this.recipe.add(spellPart);
      }

      return this;
   }

   public Spell setRecipe(@NotNull List<AbstractSpellPart> recipe) {
      this.recipe = recipe;
      return this;
   }

   public Spell withColor(@NotNull ParticleColor color) {
      this.color = color;
      return this;
   }

   public Spell withSound(@NotNull ConfiguredSpellSound sound) {
      this.sound = sound;
      return this;
   }

   public int getSpellSize() {
      return this.recipe.size();
   }

   @Nullable
   public AbstractCastMethod getCastMethod() {
      if (this.recipe != null && !this.recipe.isEmpty()) {
         return this.recipe.get(0) instanceof AbstractCastMethod ? (AbstractCastMethod)this.recipe.get(0) : null;
      } else {
         return null;
      }
   }

   public List<AbstractAugment> getAugments(int startPosition, @Nullable LivingEntity caster) {
      ArrayList<AbstractAugment> augments = new ArrayList<>();
      if (this.recipe != null && !this.recipe.isEmpty()) {
         for (int j = startPosition + 1; j < this.recipe.size(); j++) {
            AbstractSpellPart nextGlyph = this.recipe.get(j);
            if (!(nextGlyph instanceof AbstractAugment augment)) {
               break;
            }

            augments.add(augment);
         }

         return augments;
      } else {
         return augments;
      }
   }

   public int getInstanceCount(AbstractSpellPart spellPart) {
      int count = 0;

      for (AbstractSpellPart abstractSpellPart : this.recipe) {
         if (abstractSpellPart.equals(spellPart)) {
            count++;
         }
      }

      return count;
   }

   public int getBuffsAtIndex(int startPosition, @Nullable LivingEntity caster, AbstractAugment augment) {
      return (int)this.getAugments(startPosition, caster).stream().filter(a -> a.equals(augment)).count();
   }

   public int getDiscountedCost() {
      return Math.max(0, this.getNoDiscountCost() - this.discount);
   }

   public int getNoDiscountCost() {
      int cost = 0;
      if (this.recipe == null) {
         return cost;
      } else {
         for (AbstractSpellPart spell : this.recipe) {
            cost += spell.getCastingCost();
         }

         return Math.max(0, cost);
      }
   }

   public int getFinalCostAndReset() {
      int cost = this.getDiscountedCost();
      this.discount = 0;
      return cost;
   }

   public void addDiscount(int discount) {
      this.discount += discount;
   }

   public void setDiscount(int discount) {
      this.discount = discount;
   }

   public int getDiscount() {
      return this.discount;
   }

   public boolean isEmpty() {
      return this.recipe == null || this.recipe.isEmpty();
   }

   public CompoundTag serialize() {
      CompoundTag tag = new CompoundTag();
      tag.m_128359_("name", this.name);
      tag.m_128365_("spellColor", this.color.serialize());
      tag.m_128365_("sound", this.sound.serialize());
      CompoundTag recipeTag = new CompoundTag();

      for (int i = 0; i < this.recipe.size(); i++) {
         AbstractSpellPart part = this.recipe.get(i);
         recipeTag.m_128359_("part" + i, part.getRegistryName().toString());
      }

      recipeTag.m_128405_("size", this.recipe.size());
      tag.m_128365_("recipe", recipeTag);
      return tag;
   }

   public static Spell fromTag(@Nullable CompoundTag tag) {
      if (tag == null) {
         return new Spell();
      } else {
         Spell spell = new Spell();
         spell.name = tag.m_128461_("name");
         spell.color = ParticleColorRegistry.from(tag.m_128469_("spellColor"));
         spell.sound = ConfiguredSpellSound.fromTag(tag.m_128469_("sound"));
         CompoundTag recipeTag = tag.m_128469_("recipe");
         int size = recipeTag.m_128451_("size");

         for (int i = 0; i < size; i++) {
            ResourceLocation registryName = new ResourceLocation(recipeTag.m_128461_("part" + i));
            AbstractSpellPart part = ArsNouveauAPI.getInstance().getSpellpartMap().get(registryName);
            if (part != null) {
               spell.recipe.add(part);
            }
         }

         return spell;
      }
   }

   public String getDisplayString() {
      StringBuilder str = new StringBuilder();

      for (int i = 0; i < this.recipe.size(); i++) {
         AbstractSpellPart spellPart = this.recipe.get(i);
         int num = 1;

         for (int j = i + 1; j < this.recipe.size() && spellPart.name.equals(this.recipe.get(j).name); j++) {
            num++;
         }

         if (num > 1) {
            str.append(spellPart.getLocaleName()).append(" x").append(num);
            i += num - 1;
         } else {
            str.append(spellPart.getLocaleName());
         }

         if (i < this.recipe.size() - 1) {
            str.append(" -> ");
         }
      }

      return str.toString();
   }

   public boolean isValid() {
      return !this.isEmpty();
   }

   public Spell add(AbstractSpellPart spellPart, int count, int index) {
      for (int i = 0; i < count; i++) {
         this.recipe.add(index, spellPart);
      }

      return this;
   }

   public List<ResourceLocation> serializeRecipe() {
      return this.recipe.stream().map(AbstractSpellPart::getRegistryName).toList();
   }

   public Spell clone() {
      try {
         Spell clone = (Spell)super.clone();
         clone.recipe = new ArrayList<>(this.recipe);
         clone.color = this.color.clone();
         clone.sound = this.sound;
         return clone;
      } catch (CloneNotSupportedException var2) {
         throw new AssertionError();
      }
   }
}
