package com.github.L_Ender.cataclysm.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import java.util.UUID;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.common.ForgeMod;

public class Athame extends SwordItem {
   private final Multimap<Attribute, AttributeModifier> incineratorAttributes;

   public Athame(Properties group) {
      super(Tiers.STONE, 3, 0.0F, group);
      Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
      builder.put(Attributes.f_22281_, new AttributeModifier(f_41374_, "Tool modifier", 4.0, Operation.ADDITION));
      builder.put(Attributes.f_22283_, new AttributeModifier(f_41375_, "Tool modifier", -1.8F, Operation.ADDITION));
      builder.put(
         (Attribute)ForgeMod.ATTACK_RANGE.get(),
         new AttributeModifier(UUID.fromString("F0A24AD4-7D3B-4890-A3E6-CDFF88DA7975"), "Tool modifier", -1.0, Operation.ADDITION)
      );
      this.incineratorAttributes = builder.build();
   }

   public Multimap<Attribute, AttributeModifier> m_7167_(EquipmentSlot equipmentSlot) {
      return equipmentSlot == EquipmentSlot.MAINHAND ? this.incineratorAttributes : super.m_7167_(equipmentSlot);
   }

   public boolean m_6832_(ItemStack pickaxe, ItemStack stack) {
      return stack.m_150930_(Items.f_42695_);
   }
}
