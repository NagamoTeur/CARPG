package com.bobmowzie.mowziesmobs.server.item;

import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;

public abstract class MowzieAxeItem extends AxeItem {
   public MowzieAxeItem(Tier tier, float attackDamageIn, float attackSpeedIn, Properties builderIn) {
      super(tier, attackDamageIn, attackSpeedIn, builderIn);
   }

   public void getAttributesFromConfig() {
      Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
      builder.put(Attributes.f_22281_, new AttributeModifier(f_41374_, "Tool modifier", (Double)this.getConfig().attackDamage.get() - 1.0, Operation.ADDITION));
      builder.put(Attributes.f_22283_, new AttributeModifier(f_41375_, "Tool modifier", (Double)this.getConfig().attackSpeed.get() - 4.0, Operation.ADDITION));
      this.f_40982_ = builder.build();
   }

   public float m_41008_() {
      return ((Double)this.getConfig().attackDamage.get()).floatValue();
   }

   public abstract ConfigHandler.ToolConfig getConfig();
}
