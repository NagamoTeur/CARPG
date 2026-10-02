package com.hollingsworth.arsnouveau.common.items.curios;

import com.google.common.collect.Multimap;
import com.hollingsworth.arsnouveau.api.item.ArsNouveauCurio;
import com.hollingsworth.arsnouveau.api.mana.IManaEquipment;
import com.hollingsworth.arsnouveau.api.perk.PerkAttributes;
import java.util.UUID;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public abstract class AbstractManaCurio extends ArsNouveauCurio implements IManaEquipment {
   @Override
   public int getMaxManaBoost(ItemStack i) {
      return 0;
   }

   @Override
   public int getManaRegenBonus(ItemStack i) {
      return 0;
   }

   public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
      Multimap<Attribute, AttributeModifier> attributes = super.getAttributeModifiers(slotContext, uuid, stack);
      attributes.put(
         (Attribute)PerkAttributes.FLAT_MANA_BONUS.get(),
         new AttributeModifier(uuid, "max_mana_modifier_curio", (double)this.getMaxManaBoost(stack), Operation.ADDITION)
      );
      attributes.put(
         (Attribute)PerkAttributes.MANA_REGEN_BONUS.get(),
         new AttributeModifier(uuid, "mana_regen_modifier_curio", (double)this.getManaRegenBonus(stack), Operation.ADDITION)
      );
      return attributes;
   }
}
