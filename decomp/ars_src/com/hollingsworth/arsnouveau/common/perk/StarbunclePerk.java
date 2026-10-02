package com.hollingsworth.arsnouveau.common.perk;

import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.hollingsworth.arsnouveau.api.perk.Perk;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;

public class StarbunclePerk extends Perk {
   public static final StarbunclePerk INSTANCE = new StarbunclePerk(new ResourceLocation("ars_nouveau", "thread_starbuncle"));
   public static final UUID PERK_SPEED_UUID = UUID.fromString("46937d0b-123c-4786-95b5-748afd50f398");
   public static final UUID PERK_STEP_UUID = UUID.fromString("46937d0b-123c-4786-95b5-748afd50f398");

   protected StarbunclePerk(ResourceLocation key) {
      super(key);
   }

   @Override
   public Multimap<Attribute, AttributeModifier> getModifiers(EquipmentSlot pEquipmentSlot, ItemStack stack, int slotValue) {
      Builder<Attribute, AttributeModifier> modifiers = new Builder();
      modifiers.put(Attributes.f_22279_, new AttributeModifier(PERK_SPEED_UUID, "StarbunclePerk", 0.2 * (double)slotValue, Operation.MULTIPLY_TOTAL));
      if (slotValue >= 3) {
         modifiers.put((Attribute)ForgeMod.STEP_HEIGHT_ADDITION.get(), new AttributeModifier(PERK_STEP_UUID, "StarbuncleStepPerk", 2.0, Operation.ADDITION));
      }

      return modifiers.build();
   }

   @Override
   public String getLangName() {
      return "Starbuncle";
   }

   @Override
   public String getLangDescription() {
      return "Increases the speed of the player by 20%% each level and grants step assist at level 3.";
   }
}
