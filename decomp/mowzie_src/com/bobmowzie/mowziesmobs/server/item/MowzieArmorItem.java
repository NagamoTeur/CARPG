package com.bobmowzie.mowziesmobs.server.item;

import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import java.util.UUID;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item.Properties;

public abstract class MowzieArmorItem extends ArmorItem {
   private static final UUID[] ARMOR_MODIFIERS = new UUID[]{
      UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"),
      UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"),
      UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"),
      UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")
   };

   public MowzieArmorItem(ArmorMaterial materialIn, EquipmentSlot slot, Properties builderIn) {
      super(materialIn, slot, builderIn);
   }

   public void getAttributesFromConfig() {
      Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
      UUID uuid = ARMOR_MODIFIERS[this.f_40377_.m_20749_()];
      builder.put(
         Attributes.f_22284_,
         new AttributeModifier(uuid, "Armor modifier", (double)((Integer)this.getConfig().damageReduction.get()).intValue(), Operation.ADDITION)
      );
      builder.put(Attributes.f_22285_, new AttributeModifier(uuid, "Armor toughness", (Double)this.getConfig().toughness.get(), Operation.ADDITION));
      if (this.f_40378_ > 0.0F) {
         builder.put(Attributes.f_22278_, new AttributeModifier(uuid, "Armor knockback resistance", (double)this.f_40378_, Operation.ADDITION));
      }

      this.f_40383_ = builder.build();
   }

   public abstract ConfigHandler.ArmorConfig getConfig();
}
