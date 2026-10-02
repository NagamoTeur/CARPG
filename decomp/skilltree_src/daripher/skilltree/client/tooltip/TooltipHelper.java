package daripher.skilltree.client.tooltip;

import daripher.skilltree.effect.SkillBonusEffect;
import daripher.skilltree.skill.bonus.SkillBonus;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TooltipHelper {
   private static final Style SKILL_BONUS_STYLE = Style.f_131099_.m_178520_(8092645);
   private static final Style SKILL_BONUS_STYLE_NEGATIVE = Style.f_131099_.m_178520_(14834266);
   private static final Style ITEM_BONUS_STYLE = Style.f_131099_.m_178520_(8041442);
   private static final Style ITEM_BONUS_STYLE_NEGATIVE = Style.f_131099_.m_178520_(14391186);

   public static Component getEffectInstanceTooltip(MobEffectInstance effect) {
      Component effectDescription;
      if (effect.m_19544_() instanceof SkillBonusEffect skillEffect) {
         effectDescription = skillEffect.getBonus().copy().multiply((double)(effect.m_19564_() + 1)).getTooltip().m_6270_(Style.f_131099_);
      } else {
         effectDescription = effect.m_19544_().m_19482_();
         if (effect.m_19564_() == 0) {
            return effectDescription;
         }

         Component amplifier = Component.m_237115_("potion.potency." + effect.m_19564_());
         effectDescription = Component.m_237110_("potion.withAmplifier", new Object[]{effectDescription, amplifier});
      }

      return effectDescription;
   }

   public static Component getOperationName(Operation operation) {
      return Component.m_237113_(switch (operation) {
         case ADDITION -> "Addition";
         case MULTIPLY_BASE -> "Multiply Base";
         case MULTIPLY_TOTAL -> "Multiply Total";
         default -> throw new IncompatibleClassChangeError();
      });
   }

   public static Component getOptionalTooltip(String descriptionId, String subtype) {
      String key = "%s.%s".formatted(descriptionId, subtype);
      Component tooltip = Component.m_237115_(key);
      return (Component)(!tooltip.getString().equals(key) ? tooltip : Component.m_237115_(descriptionId));
   }

   public static void consumeTranslated(String descriptionId, Consumer<MutableComponent> consumer) {
      MutableComponent tooltip = Component.m_237115_(descriptionId);
      if (!tooltip.getString().equals(descriptionId)) {
         consumer.accept(tooltip);
      }
   }

   public static MutableComponent getSkillBonusTooltip(Component bonusDescription, double amount, Operation operation) {
      float multiplier = 1.0F;
      if (operation != Operation.ADDITION) {
         multiplier = 100.0F;
      }

      double visibleAmount = amount * (double)multiplier;
      if (amount < 0.0) {
         visibleAmount *= -1.0;
      }

      String operationDescription = amount > 0.0 ? "plus" : "take";
      operationDescription = "attribute.modifier." + operationDescription + "." + operation.ordinal();
      String multiplierDescription = formatNumber(visibleAmount);
      return Component.m_237110_(operationDescription, new Object[]{multiplierDescription, bonusDescription});
   }

   public static String formatNumber(double number) {
      return ItemStack.f_41584_.format(number);
   }

   public static MutableComponent getSkillBonusTooltip(String bonus, double amount, Operation operation) {
      return getSkillBonusTooltip(Component.m_237115_(bonus), amount, operation);
   }

   public static Style getSkillBonusStyle(boolean positive) {
      return positive ? SKILL_BONUS_STYLE : SKILL_BONUS_STYLE_NEGATIVE;
   }

   public static Style getItemBonusStyle(boolean positive) {
      return positive ? ITEM_BONUS_STYLE : ITEM_BONUS_STYLE_NEGATIVE;
   }

   public static MutableComponent getTextureName(ResourceLocation location) {
      String texture = location.m_135815_();
      texture = texture.substring(texture.lastIndexOf("/") + 1);
      texture = texture.replace(".png", "");
      texture = idToName(texture);
      return Component.m_237113_(texture);
   }

   public static MutableComponent getTargetName(SkillBonus.Target target) {
      return Component.m_237113_(idToName(target.name().toLowerCase()));
   }

   public static String getRecipeDescriptionId(ResourceLocation recipeId) {
      return "recipe.%s.%s".formatted(recipeId.m_135827_(), recipeId.m_135815_());
   }

   @NotNull
   public static String idToName(String path) {
      String[] words = path.split("_");
      StringBuilder name = new StringBuilder();
      Arrays.stream(words).map(w -> w.substring(0, 1).toUpperCase() + w.substring(1)).forEach(w -> {
         name.append(" ");
         name.append(w);
      });
      return name.substring(1);
   }

   public static List<MutableComponent> split(MutableComponent component, Font font, int maxWidth) {
      String[] split = component.getString().split(" ");
      if (split.length < 2) {
         return List.of(component);
      } else {
         String line = split[0];
         List<MutableComponent> components = new ArrayList<>();

         for (int i = 1; i < split.length; i++) {
            String next = line + " " + split[i];
            if (font.m_92895_(next) > maxWidth) {
               components.add(Component.m_237115_(line).m_130948_(component.m_7383_()));
               line = "  " + split[i];
            } else {
               line = next;
            }
         }

         components.add(Component.m_237115_(line).m_130948_(component.m_7383_()));
         return components;
      }
   }

   @NotNull
   public static String getTrimmedString(Font font, String message, int maxWidth) {
      if (font.m_92895_(message) > maxWidth) {
         while (font.m_92895_(message + "...") > maxWidth) {
            message = message.substring(0, message.length() - 1);
         }

         message = message + "...";
      }

      return message;
   }

   @NotNull
   public static String getTrimmedString(String message, int maxWidth) {
      return getTrimmedString(Minecraft.m_91087_().f_91062_, message, maxWidth);
   }
}
