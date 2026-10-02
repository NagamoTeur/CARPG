package shadows.apotheosis.core.attributeslib.impl;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import shadows.apotheosis.core.attributeslib.api.IFormattableAttribute;

public class PercentBasedAttribute extends RangedAttribute implements IFormattableAttribute {
   public PercentBasedAttribute(String pDescriptionId, double pDefaultValue, double pMin, double pMax) {
      super(pDescriptionId, pDefaultValue, pMin, pMax);
   }

   @Override
   public MutableComponent toValueComponent(Operation op, double value, TooltipFlag flag) {
      return Component.m_237110_("attributeslib.value.percent", new Object[]{ItemStack.f_41584_.format(value * 100.0)});
   }
}
