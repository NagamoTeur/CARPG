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

public class ToughnessPerk extends Perk {
   public static final ToughnessPerk INSTANCE = new ToughnessPerk(new ResourceLocation("ars_nouveau", "thread_toughness"));
   public static final UUID PERK_UUID = UUID.fromString("a628398e-20e1-493c-b81f-d1e58d7d0d69");

   public ToughnessPerk(ResourceLocation key) {
      super(key);
   }

   @Override
   public Multimap<Attribute, AttributeModifier> getModifiers(EquipmentSlot pEquipmentSlot, ItemStack stack, int slotValue) {
      return this.attributeBuilder()
         .put(Attributes.f_22285_, new AttributeModifier(PERK_UUID, "Toughness", (double)(1 * slotValue), Operation.ADDITION))
         .build();
   }

   @Override
   public String getLangName() {
      return "Toughness";
   }

   @Override
   public String getLangDescription() {
      return "Grants an additional point of toughness each level.";
   }
}
