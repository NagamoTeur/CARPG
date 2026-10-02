package com.hollingsworth.arsnouveau.api.spell;

import com.hollingsworth.arsnouveau.api.sound.ConfiguredSpellSound;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SpellCaster implements ISpellCaster {
   private Map<Integer, Spell> spells = new HashMap<>();
   private int slot;
   public ItemStack stack = ItemStack.f_41583_;
   public String flavorText = "";
   public boolean isHidden;
   public String hiddenText = "";

   public SpellCaster(ItemStack stack) {
      this(stack.m_41784_());
      this.stack = stack;
   }

   @NotNull
   @Override
   public Spell getSpell() {
      return this.spells.getOrDefault(this.getCurrentSlot(), new Spell());
   }

   @NotNull
   @Override
   public Spell getSpell(int slot) {
      return this.spells.getOrDefault(slot, new Spell());
   }

   @Override
   public int getMaxSlots() {
      return 1;
   }

   @Override
   public int getCurrentSlot() {
      return this.slot;
   }

   @Override
   public void setCurrentSlot(int slot) {
      this.slot = slot;
      this.writeItem(this.stack);
   }

   @Override
   public void setSpell(Spell spell, int slot) {
      this.spells.put(slot, spell);
      this.writeItem(this.stack);
   }

   @Override
   public void setSpell(Spell spell) {
      this.setSpell(spell, this.getCurrentSlot());
   }

   @Override
   public void setSpellRecipe(List<AbstractSpellPart> spellRecipe, int slot) {
      if (this.spells.containsKey(slot)) {
         this.spells.get(slot).setRecipe(spellRecipe);
      } else {
         this.spells.put(slot, new Spell(spellRecipe));
      }

      this.writeItem(this.stack);
   }

   @Override
   public ParticleColor getColor(int slot) {
      return this.getSpell(slot).color;
   }

   @Override
   public void setFlavorText(String str) {
      this.flavorText = str;
      this.writeItem(this.stack);
   }

   @Override
   public String getSpellName(int slot) {
      return this.getSpell(slot).name;
   }

   @Override
   public String getSpellName() {
      return this.getSpellName(this.getCurrentSlot());
   }

   @Override
   public void setSpellName(String name) {
      this.setSpellName(name, this.getCurrentSlot());
   }

   @Override
   public void setSpellName(String name, int slot) {
      this.getSpell(slot).name = name;
      this.writeItem(this.stack);
   }

   @Override
   public void setSpellHidden(boolean hidden) {
      this.isHidden = hidden;
      this.writeItem(this.stack);
   }

   @Override
   public boolean isSpellHidden() {
      return this.isHidden;
   }

   @Override
   public void setHiddenRecipe(String recipe) {
      this.hiddenText = recipe;
      this.writeItem(this.stack);
   }

   @Override
   public String getHiddenRecipe() {
      return this.hiddenText;
   }

   @Override
   public String getFlavorText() {
      return this.flavorText == null ? "" : this.flavorText;
   }

   @Override
   public void setColor(ParticleColor color) {
      this.setColor(color, this.getCurrentSlot());
   }

   @Override
   public void setColor(ParticleColor color, int slot) {
      this.getSpell(slot).color = color;
      this.writeItem(this.stack);
   }

   @NotNull
   @Override
   public ConfiguredSpellSound getSound(int slot) {
      return this.getSpell(slot).sound;
   }

   @Override
   public void setSound(ConfiguredSpellSound sound) {
      this.setSound(sound, this.getCurrentSlot());
   }

   @Override
   public void setSound(ConfiguredSpellSound sound, int slot) {
      this.getSpell(slot).sound = sound;
      this.writeItem(this.stack);
   }

   @NotNull
   @Override
   public ParticleColor getColor() {
      return this.getSpell().color;
   }

   @Override
   public Map<Integer, Spell> getSpells() {
      return this.spells;
   }

   public CompoundTag writeTag(CompoundTag tag) {
      tag.m_128405_("current_slot", this.getCurrentSlot());
      tag.m_128359_("flavor", this.getFlavorText());
      CompoundTag spellTag = new CompoundTag();

      for (int i = 0; i < this.getMaxSlots(); i++) {
         Spell spell = this.getSpell(i);
         spellTag.m_128365_("spell" + i, spell.serialize());
      }

      tag.m_128365_("spells", spellTag);
      tag.m_128405_("spell_count", this.getSpells().size());
      tag.m_128379_("is_hidden", this.isSpellHidden());
      tag.m_128359_("hidden_recipe", this.getHiddenRecipe());
      return tag;
   }

   public SpellCaster(CompoundTag itemTag) {
      CompoundTag tag = itemTag.m_128469_(this.getTagID().toString());
      this.slot = tag.m_128451_("current_slot");
      this.flavorText = tag.m_128461_("flavor");
      this.isHidden = tag.m_128471_("is_hidden");
      this.hiddenText = tag.m_128461_("hidden_recipe");
      CompoundTag spellTag = tag.m_128469_("spells");

      for (int i = 0; i < this.getMaxSlots(); i++) {
         if (spellTag.m_128441_("spell" + i)) {
            Spell spell = Spell.fromTag(spellTag.m_128469_("spell" + i));
            this.spells.put(i, spell);
         }
      }
   }

   public void writeItem(ItemStack stack) {
      if (stack != null && !stack.m_41619_()) {
         CompoundTag tag = stack.m_41784_();
         CompoundTag casterTag = new CompoundTag();
         this.writeTag(casterTag);
         tag.m_128365_(this.getTagID().toString(), casterTag);
         stack.m_41751_(tag);
      }
   }

   public void serializeOnTag(CompoundTag tag) {
      CompoundTag thisData = this.writeTag(new CompoundTag());
      tag.m_128365_(this.getTagID().toString(), thisData);
   }

   @Override
   public ResourceLocation getTagID() {
      return new ResourceLocation("ars_nouveau", "caster");
   }
}
