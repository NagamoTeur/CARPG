package shadows.apotheosis.core.attributeslib.api;

import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.Nullable;

public interface IFormattableAttribute {
   default MutableComponent toValueComponent(Operation op, double value, TooltipFlag flag) {
      if (this == Attributes.f_22278_ || this == ForgeMod.SWIM_SPEED.get()) {
         return Component.m_237110_("attributeslib.value.percent", new Object[]{ItemStack.f_41584_.format(value * 100.0)});
      } else if (this == Attributes.f_22279_ && op == Operation.ADDITION) {
         return Component.m_237110_("attributeslib.value.percent", new Object[]{ItemStack.f_41584_.format(value * 1000.0)});
      } else {
         String key = op == Operation.ADDITION ? "attributeslib.value.flat" : "attributeslib.value.percent";
         return Component.m_237110_(key, new Object[]{ItemStack.f_41584_.format(op == Operation.ADDITION ? value : value * 100.0)});
      }
   }

   default MutableComponent toComponent(AttributeModifier modif, TooltipFlag flag) {
      Attribute attr = this.ths();
      double value = modif.m_22218_();
      Component debugInfo = CommonComponents.f_237098_;
      if (flag.m_7050_()) {
         double advValue = (double)(modif.m_22217_() == Operation.MULTIPLY_TOTAL ? 1 : 0) + modif.m_22218_();
         String valueStr = ItemStack.f_41584_.format(advValue);

         String txt = switch (modif.m_22217_()) {
            case ADDITION -> advValue > 0.0 ? String.format("[+%s]", valueStr) : String.format("[%s]", valueStr);
            case MULTIPLY_BASE -> advValue > 0.0 ? String.format("[+%sx]", valueStr) : String.format("[%sx]", valueStr);
            case MULTIPLY_TOTAL -> String.format("[x%s]", valueStr);
            default -> throw new IncompatibleClassChangeError();
         };
         debugInfo = Component.m_237113_(" ").m_7220_(Component.m_237113_(txt).m_130940_(ChatFormatting.GRAY));
      }

      MutableComponent comp;
      if (value > 0.0) {
         comp = Component.m_237110_(
               "attributeslib.modifier.plus", new Object[]{this.toValueComponent(modif.m_22217_(), value, flag), Component.m_237115_(attr.m_22087_())}
            )
            .m_130940_(ChatFormatting.BLUE);
      } else {
         value *= -1.0;
         comp = Component.m_237110_(
               "attributeslib.modifier.take", new Object[]{this.toValueComponent(modif.m_22217_(), value, flag), Component.m_237115_(attr.m_22087_())}
            )
            .m_130940_(ChatFormatting.RED);
      }

      return comp.m_7220_(debugInfo);
   }

   @Nullable
   default UUID getBaseUUID() {
      if (this == Attributes.f_22281_) {
         return AttributeHelper.BASE_ATTACK_DAMAGE;
      } else if (this == Attributes.f_22283_) {
         return AttributeHelper.BASE_ATTACK_SPEED;
      } else {
         return this == ForgeMod.ATTACK_RANGE.get() ? AttributeHelper.BASE_ATTACK_RANGE : null;
      }
   }

   default MutableComponent toBaseComponent(double value, double entityBase, boolean merged, TooltipFlag flag) {
      Attribute attr = this.ths();
      Component debugInfo = CommonComponents.f_237098_;
      if (flag.m_7050_() && !merged) {
         debugInfo = Component.m_237113_(" ")
            .m_7220_(
               Component.m_237110_("attributeslib.adv.base", new Object[]{ItemStack.f_41584_.format(entityBase), ItemStack.f_41584_.format(value - entityBase)})
                  .m_130940_(ChatFormatting.GRAY)
            );
      }

      MutableComponent comp = Component.m_237110_(
         "attribute.modifier.equals.0", new Object[]{ItemStack.f_41584_.format(value), Component.m_237115_(attr.m_22087_())}
      );
      return comp.m_7220_(debugInfo);
   }

   default double getBonusBaseValue(ItemStack stack) {
      return this == Attributes.f_22281_ ? (double)EnchantmentHelper.m_44833_(stack, MobType.f_21640_) : 0.0;
   }

   default void addBonusTooltips(ItemStack stack, Consumer<Component> tooltip, TooltipFlag flag) {
      if (this == Attributes.f_22281_) {
         float sharpness = EnchantmentHelper.m_44833_(stack, MobType.f_21640_);
         Component debugInfo = CommonComponents.f_237098_;
         if (flag.m_7050_()) {
            debugInfo = Component.m_237113_(" ")
               .m_7220_(Component.m_237110_("attributeslib.adv.sharpness_bonus", new Object[]{sharpness}).m_130940_(ChatFormatting.GRAY));
         }

         MutableComponent comp = AttributeHelper.list()
            .m_7220_(
               Component.m_237110_(
                     "attribute.modifier.plus.0", new Object[]{ItemStack.f_41584_.format((double)sharpness), Component.m_237115_(this.ths().m_22087_())}
                  )
                  .m_130940_(ChatFormatting.BLUE)
            );
         tooltip.accept(comp.m_7220_(debugInfo));
      }
   }

   default Attribute ths() {
      return (Attribute)this;
   }

   static MutableComponent toComponent(Attribute attr, AttributeModifier modif, TooltipFlag flag) {
      return ((IFormattableAttribute)attr).toComponent(modif, flag);
   }

   static MutableComponent toValueComponent(Attribute attr, Operation op, double value, TooltipFlag flag) {
      return ((IFormattableAttribute)attr).toValueComponent(op, value, flag);
   }

   static MutableComponent toBaseComponent(Attribute attr, double value, double entityBase, boolean merged, TooltipFlag flag) {
      return ((IFormattableAttribute)attr).toBaseComponent(value, entityBase, merged, flag);
   }
}
