package com.hollingsworth.arsnouveau.common.perk;

import com.google.common.collect.Multimap;
import com.hollingsworth.arsnouveau.api.perk.Perk;
import com.hollingsworth.arsnouveau.api.perk.PerkAttributes;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;

public class PotionDurationPerk extends Perk {
   public static final PotionDurationPerk INSTANCE = new PotionDurationPerk(new ResourceLocation("ars_nouveau", "thread_wixie"));
   public static final UUID PERK_UUID = UUID.fromString("f4c0926e-82a7-44d6-bd6b-a0321a65de2f");

   public PotionDurationPerk(ResourceLocation key) {
      super(key);
   }

   @Override
   public Multimap<Attribute, AttributeModifier> getModifiers(EquipmentSlot pEquipmentSlot, ItemStack stack, int slotValue) {
      return this.attributeBuilder()
         .put((Attribute)PerkAttributes.WIXIE.get(), new AttributeModifier(PERK_UUID, "PotionDurationPerk", 0.15 * (double)slotValue, Operation.MULTIPLY_BASE))
         .build();
   }

   @Override
   public String getLangName() {
      return "Wixie";
   }

   @Override
   public String getLangDescription() {
      return "Increases the duration of potions by 15%% each level.";
   }
}
