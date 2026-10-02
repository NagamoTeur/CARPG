package com.hollingsworth.arsnouveau.common.capability;

import com.hollingsworth.arsnouveau.api.mana.IManaCap;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;

public class ManaCap implements IManaCap {
   private final LivingEntity livingEntity;
   private double mana;
   private int maxMana;
   private int glyphBonus;
   private int bookTier;

   public ManaCap(@Nullable LivingEntity entity) {
      this.livingEntity = entity;
   }

   @Override
   public double getCurrentMana() {
      return this.mana;
   }

   @Override
   public int getMaxMana() {
      return this.maxMana;
   }

   @Override
   public void setMaxMana(int maxMana) {
      this.maxMana = maxMana;
   }

   @Override
   public double setMana(double mana) {
      if (mana > (double)this.getMaxMana()) {
         this.mana = (double)this.getMaxMana();
      } else if (mana < 0.0) {
         this.mana = 0.0;
      } else {
         this.mana = mana;
      }

      return this.getCurrentMana();
   }

   @Override
   public double addMana(double manaToAdd) {
      this.setMana(this.getCurrentMana() + manaToAdd);
      return this.getCurrentMana();
   }

   @Override
   public double removeMana(double manaToRemove) {
      if (manaToRemove < 0.0) {
         manaToRemove = 0.0;
      }

      this.setMana(this.getCurrentMana() - manaToRemove);
      return this.getCurrentMana();
   }

   @Override
   public int getGlyphBonus() {
      return this.glyphBonus;
   }

   @Override
   public void setGlyphBonus(int glyphBonus) {
      this.glyphBonus = glyphBonus;
   }

   @Override
   public int getBookTier() {
      return this.bookTier;
   }

   @Override
   public void setBookTier(int bookTier) {
      this.bookTier = bookTier;
   }

   public CompoundTag serializeNBT() {
      CompoundTag tag = new CompoundTag();
      tag.m_128347_("current", this.getCurrentMana());
      tag.m_128405_("max", this.getMaxMana());
      tag.m_128405_("glyph", this.getGlyphBonus());
      tag.m_128405_("book_tier", this.getBookTier());
      return tag;
   }

   public void deserializeNBT(CompoundTag tag) {
      this.setMaxMana(tag.m_128451_("max"));
      this.setMana(tag.m_128459_("current"));
      this.setBookTier(tag.m_128451_("book_tier"));
      this.setGlyphBonus(tag.m_128451_("glyph"));
   }
}
