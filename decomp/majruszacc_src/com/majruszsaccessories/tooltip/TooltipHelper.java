package com.majruszsaccessories.tooltip;

import com.majruszlibrary.math.Range;
import com.majruszlibrary.text.TextHelper;
import com.majruszsaccessories.common.AccessoryHolder;
import com.majruszsaccessories.config.RangedFloat;
import com.majruszsaccessories.config.RangedInteger;
import com.majruszsaccessories.items.BoosterItem;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class TooltipHelper {
   public static final ChatFormatting DEFAULT_FORMAT = ChatFormatting.GRAY;

   public static MutableComponent asFormula(Object base, Object bonus) {
      return TextHelper.translatable("majruszsaccessories.items.formula", new Object[]{base, bonus}).m_130940_(DEFAULT_FORMAT);
   }

   public static MutableComponent asRange(Object base, Object bonus) {
      return TextHelper.translatable("majruszsaccessories.items.range", new Object[]{base, bonus}).m_130940_(DEFAULT_FORMAT);
   }

   public static TooltipHelper.IntegerTooltip asValue(RangedInteger value) {
      return new TooltipHelper.IntegerTooltip(value);
   }

   public static TooltipHelper.FloatTooltip asValue(RangedFloat value) {
      return new TooltipHelper.FloatTooltip(value);
   }

   public static TooltipHelper.IntegerTooltip asFixedValue(RangedInteger value) {
      return new TooltipHelper.IntegerTooltip(value).bonusMultiplier(0);
   }

   public static TooltipHelper.FloatTooltip asFixedValue(RangedFloat value) {
      return new TooltipHelper.FloatTooltip(value).bonusMultiplier(0.0F);
   }

   public static TooltipHelper.PercentTooltip asPercent(RangedFloat value) {
      return new TooltipHelper.PercentTooltip(value);
   }

   public static TooltipHelper.PercentTooltip asFixedPercent(RangedFloat value) {
      return new TooltipHelper.PercentTooltip(value).bonusMultiplier(0.0F);
   }

   public static ITooltipProvider asBooster(final Supplier<BoosterItem> item) {
      return new ITooltipProvider() {
         @Override
         public MutableComponent getTooltip(AccessoryHolder holder) {
            return !holder.isValid()
               ? TextHelper.literal("")
               : TextHelper.translatable("majruszsaccessories.items.booster_name", new Object[]{item.get().m_41466_()})
                  .m_130940_(item.get().m_41460_(ItemStack.f_41583_).f_43022_)
                  .m_130946_(" ");
         }
      };
   }

   public static ITooltipProvider asItem(final Supplier<Item> item) {
      return new ITooltipProvider() {
         @Override
         public MutableComponent getTooltip(AccessoryHolder holder) {
            return item.get().m_41466_().m_6881_();
         }
      };
   }

   public static ITooltipProvider asEntity(final Supplier<EntityType<?>> type) {
      return new ITooltipProvider() {
         @Override
         public MutableComponent getTooltip(AccessoryHolder holder) {
            return type.get().m_20676_().m_6881_();
         }
      };
   }

   public static class FloatTooltip implements ITooltipProvider {
      private final RangedFloat value;
      private float bonusMultiplier = 1.0F;
      private float valueMultiplier = 1.0F;
      private float diffMargin = 0.001F;
      private boolean isScaledOnlyOnDetailed = false;
      private int scale = 2;

      FloatTooltip(RangedFloat value) {
         this.value = value;
      }

      @Override
      public MutableComponent getTooltip(AccessoryHolder holder) {
         int scale = this.isScaledOnlyOnDetailed ? 0 : this.scale;
         float bonusValue = getScaled(holder.apply(this.value, this.bonusMultiplier) * this.valueMultiplier, scale);
         float defaultValue = getScaled(this.value.get() * this.valueMultiplier, scale);
         float diff = bonusValue - defaultValue;
         return TextHelper.literal(TextHelper.minPrecision(bonusValue, scale))
            .m_130940_(Math.abs(diff) >= this.diffMargin ? holder.getBonusFormatting() : TooltipHelper.DEFAULT_FORMAT);
      }

      @Override
      public MutableComponent getDetailedTooltip(AccessoryHolder holder) {
         float bonusValue = holder.apply(this.value, this.bonusMultiplier) * this.valueMultiplier;
         float defaultValue = this.value.get() * this.valueMultiplier;
         float diff = bonusValue - defaultValue;
         MutableComponent component = Math.abs(diff) >= this.diffMargin ? TextHelper.literal(TextHelper.signed(diff, this.scale)) : TextHelper.literal("");
         return TooltipHelper.asFormula(TextHelper.minPrecision(defaultValue, this.scale), component.m_130940_(holder.getBonusFormatting()));
      }

      @Override
      public MutableComponent getRangeTooltip(AccessoryHolder holder) {
         Range<Float> range = holder.getClampedBonusRange();
         int scale = this.isScaledOnlyOnDetailed ? 0 : this.scale;
         float minValue = getScaled(AccessoryHolder.apply((Float)range.from, this.value, this.bonusMultiplier) * this.valueMultiplier, scale);
         float maxValue = getScaled(AccessoryHolder.apply((Float)range.to, this.value, this.bonusMultiplier) * this.valueMultiplier, scale);
         float defaultValue = getScaled(this.value.get() * this.valueMultiplier, scale);
         MutableComponent minComponent = TextHelper.literal(TextHelper.minPrecision(minValue, this.scale))
            .m_130940_(
               Math.abs(minValue - defaultValue) >= this.diffMargin ? AccessoryHolder.getBonusFormatting((Float)range.from) : TooltipHelper.DEFAULT_FORMAT
            );
         if (Math.abs(maxValue - minValue) >= this.diffMargin) {
            MutableComponent maxComponent = TextHelper.literal(TextHelper.minPrecision(maxValue, this.scale))
               .m_130940_(
                  Math.abs(maxValue - defaultValue) >= this.diffMargin ? AccessoryHolder.getBonusFormatting((Float)range.to) : TooltipHelper.DEFAULT_FORMAT
               );
            return TooltipHelper.asRange(minComponent, maxComponent);
         } else {
            return minComponent;
         }
      }

      public TooltipHelper.FloatTooltip bonusMultiplier(float multiplier) {
         this.bonusMultiplier = multiplier;
         return this;
      }

      public TooltipHelper.FloatTooltip valueMultiplier(float multiplier) {
         this.valueMultiplier = multiplier;
         return this;
      }

      public TooltipHelper.FloatTooltip scale(int scale) {
         this.scale = scale;
         this.diffMargin = (float)Math.pow(0.1, (double)(scale + 2));
         return this;
      }

      public TooltipHelper.FloatTooltip scaleOnlyOnDetailed() {
         this.isScaledOnlyOnDetailed = true;
         return this;
      }

      private static float getScaled(float value, int scale) {
         return new BigDecimal((double)value).setScale(scale, RoundingMode.HALF_EVEN).stripTrailingZeros().floatValue();
      }
   }

   public static class IntegerTooltip implements ITooltipProvider {
      private final RangedInteger value;
      private int bonusMultiplier = 1;
      private int valueMultiplier = 1;

      IntegerTooltip(RangedInteger value) {
         this.value = value;
      }

      @Override
      public MutableComponent getTooltip(AccessoryHolder holder) {
         int bonusValue = holder.apply(this.value, this.bonusMultiplier) * this.valueMultiplier;
         int defaultValue = this.value.get() * this.valueMultiplier;
         int diff = bonusValue - defaultValue;
         return TextHelper.literal("%d".formatted(bonusValue)).m_130940_(diff != 0 ? holder.getBonusFormatting() : TooltipHelper.DEFAULT_FORMAT);
      }

      @Override
      public MutableComponent getDetailedTooltip(AccessoryHolder holder) {
         int bonusValue = holder.apply(this.value, this.bonusMultiplier) * this.valueMultiplier;
         int defaultValue = this.value.get() * this.valueMultiplier;
         int diff = bonusValue - defaultValue;
         MutableComponent component = diff != 0 ? TextHelper.literal(TextHelper.signed(diff)) : TextHelper.literal("");
         return TooltipHelper.asFormula(defaultValue, component.m_130940_(holder.getBonusFormatting()));
      }

      @Override
      public MutableComponent getRangeTooltip(AccessoryHolder holder) {
         Range<Float> range = holder.getClampedBonusRange();
         int minValue = AccessoryHolder.apply((Float)range.from, this.value, this.bonusMultiplier) * this.valueMultiplier;
         int maxValue = AccessoryHolder.apply((Float)range.to, this.value, this.bonusMultiplier) * this.valueMultiplier;
         int defaultValue = this.value.get() * this.valueMultiplier;
         MutableComponent minComponent = TextHelper.literal(minValue + "")
            .m_130940_(minValue != defaultValue ? AccessoryHolder.getBonusFormatting((Float)range.from) : TooltipHelper.DEFAULT_FORMAT);
         if (minValue != maxValue) {
            MutableComponent maxComponent = TextHelper.literal(maxValue + "")
               .m_130940_(maxValue != defaultValue ? AccessoryHolder.getBonusFormatting((Float)range.to) : TooltipHelper.DEFAULT_FORMAT);
            return TooltipHelper.asRange(minComponent, maxComponent);
         } else {
            return minComponent;
         }
      }

      public TooltipHelper.IntegerTooltip bonusMultiplier(int multiplier) {
         this.bonusMultiplier = multiplier;
         return this;
      }

      public TooltipHelper.IntegerTooltip valueMultiplier(int multiplier) {
         this.valueMultiplier = multiplier;
         return this;
      }
   }

   public static class PercentTooltip implements ITooltipProvider {
      private final RangedFloat value;
      private float bonusMultiplier = 1.0F;
      private float valueMultiplier = 1.0F;
      private float diffMargin = 0.001F;
      private int scale = 2;

      PercentTooltip(RangedFloat value) {
         this.value = value;
      }

      @Override
      public MutableComponent getTooltip(AccessoryHolder holder) {
         float bonusValue = holder.apply(this.value, this.bonusMultiplier) * this.valueMultiplier;
         float defaultValue = this.value.get() * this.valueMultiplier;
         float diff = bonusValue - defaultValue;
         return TextHelper.literal(TextHelper.percent(bonusValue, this.scale))
            .m_130940_(Math.abs(diff) >= this.diffMargin ? holder.getBonusFormatting() : TooltipHelper.DEFAULT_FORMAT);
      }

      @Override
      public MutableComponent getDetailedTooltip(AccessoryHolder holder) {
         float bonusValue = holder.apply(this.value, this.bonusMultiplier) * this.valueMultiplier;
         float defaultValue = this.value.get() * this.valueMultiplier;
         float diff = bonusValue - defaultValue;
         MutableComponent component = Math.abs(diff) >= this.diffMargin
            ? TextHelper.literal(TextHelper.signedPercent(diff, this.scale))
            : TextHelper.literal("");
         return TooltipHelper.asFormula(TextHelper.percent(defaultValue, this.scale), component.m_130940_(holder.getBonusFormatting()));
      }

      @Override
      public MutableComponent getRangeTooltip(AccessoryHolder holder) {
         Range<Float> range = holder.getClampedBonusRange();
         float minValue = AccessoryHolder.apply((Float)range.from, this.value, this.bonusMultiplier) * this.valueMultiplier;
         float maxValue = AccessoryHolder.apply((Float)range.to, this.value, this.bonusMultiplier) * this.valueMultiplier;
         MutableComponent minComponent = TextHelper.literal(TextHelper.percent(minValue, this.scale))
            .m_130940_(AccessoryHolder.getBonusFormatting((Float)range.from));
         return Math.abs(maxValue - minValue) >= this.diffMargin
            ? TooltipHelper.asRange(
               minComponent, TextHelper.literal(TextHelper.percent(maxValue, this.scale)).m_130940_(AccessoryHolder.getBonusFormatting((Float)range.to))
            )
            : minComponent;
      }

      public TooltipHelper.PercentTooltip bonusMultiplier(float multiplier) {
         this.bonusMultiplier = multiplier;
         return this;
      }

      public TooltipHelper.PercentTooltip valueMultiplier(float multiplier) {
         this.valueMultiplier = multiplier;
         return this;
      }

      public TooltipHelper.PercentTooltip scale(int scale) {
         this.scale = scale;
         this.diffMargin = (float)Math.pow(0.1, (double)(scale + 2));
         return this;
      }
   }
}
