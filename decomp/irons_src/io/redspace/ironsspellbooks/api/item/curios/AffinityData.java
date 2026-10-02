package io.redspace.ironsspellbooks.api.item.curios;

import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class AffinityData {
   public static final String ISB_ENHANCE = "ISBEnhance";
   String spellId;
   public static final AffinityData NONE = new AffinityData(SpellRegistry.none().getSpellId());

   private AffinityData(String id) {
      this.spellId = id;
   }

   public static AffinityData getAffinityData(ItemStack stack) {
      return hasAffinityData(stack) ? new AffinityData(stack.m_41784_().m_128461_("ISBEnhance")) : NONE;
   }

   public static void setAffinityData(ItemStack stack, AbstractSpell spell) {
      CompoundTag spellTag = stack.m_41784_();
      spellTag.m_128359_("ISBEnhance", spell.getSpellId());
   }

   public static boolean hasAffinityData(ItemStack itemStack) {
      return itemStack.m_41783_() != null && itemStack.m_41783_().m_128441_("ISBEnhance");
   }

   public AbstractSpell getSpell() {
      return SpellRegistry.getSpell(this.spellId);
   }

   public String getNameForItem() {
      return this.getSpell() == SpellRegistry.none()
         ? Component.m_237115_("tooltip.irons_spellbooks.no_affinity").getString()
         : this.getSpell().getSchoolType().getDisplayName().getString();
   }
}
