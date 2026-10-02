package com.hollingsworth.arsnouveau.common.perk;

import com.google.common.collect.Multimap;
import com.hollingsworth.arsnouveau.api.perk.Perk;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;

public class KnockbackResistPerk extends Perk {
   public static final KnockbackResistPerk INSTANCE = new KnockbackResistPerk(new ResourceLocation("ars_nouveau", "thread_knockback_resist"));
   public static final UUID PERK_UUID = UUID.fromString("b1d84c5d-4c84-4626-b275-94698b08aae1");

   public KnockbackResistPerk(ResourceLocation key) {
      super(key);
   }

   @Override
   public Multimap<Attribute, AttributeModifier> getModifiers(EquipmentSlot pEquipmentSlot, ItemStack stack, int slotValue) {
      return this.attributeBuilder()
         .put(Attributes.f_22278_, new AttributeModifier(PERK_UUID, "KnockbackPerk", 0.15 * (double)slotValue, Operation.ADDITION))
         .build();
   }

   @Override
   public String getLangName() {
      return "Sturdy";
   }

   @Override
   public String getLangDescription() {
      return "Grants 15%% knockback resistance per level.";
   }
}
