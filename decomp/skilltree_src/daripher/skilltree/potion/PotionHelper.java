package daripher.skilltree.potion;

import daripher.skilltree.config.Config;
import daripher.skilltree.item.ItemHelper;
import daripher.skilltree.skill.bonus.item.PotionAmplificationBonus;
import daripher.skilltree.skill.bonus.item.PotionDurationBonus;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.PotionUtils;

public class PotionHelper {
   private static final String CUSTOM_COLOR_TAG = "CustomPotionColor";
   private static final String MIXED_POTION_TAG = "MixedPotion";

   public static boolean isMixture(ItemStack stack) {
      return stack.m_41782_() && Objects.requireNonNull(stack.m_41783_()).m_128441_("MixedPotion");
   }

   public static boolean isPotion(ItemStack stack) {
      return stack.m_41720_() instanceof PotionItem;
   }

   public static int getAmplifierBonus(ItemStack stack) {
      return ItemHelper.getItemBonuses(stack, PotionAmplificationBonus.class)
         .stream()
         .map(PotionAmplificationBonus::getChance)
         .reduce(Float::sum)
         .map(Float::intValue)
         .orElse(0);
   }

   public static float getDurationMultiplier(ItemStack stack) {
      float multiplier = 1.0F;
      return multiplier
         + ItemHelper.getItemBonuses(stack, PotionDurationBonus.class).stream().map(PotionDurationBonus::getMultiplier).reduce(Float::sum).orElse(0.0F);
   }

   public static void setPotionColor(ItemStack itemStack, int color) {
      CompoundTag tag = itemStack.m_41783_();
      if (tag != null) {
         tag.m_128405_("CustomPotionColor", color);
      }
   }

   public static ItemStack mixPotions(ItemStack potion1, ItemStack potion2) {
      List<ItemStack> potions = Arrays.asList(potion1, potion2);
      ItemStack result = new ItemStack(potion1.m_41720_(), 2);
      List<MobEffectInstance> effects = potions.stream()
         .sorted(PotionHelper::comparePotions)
         .<List<MobEffectInstance>>map(PotionUtils::m_43547_)
         .map(PotionHelper::applyMixtureMultipliers)
         .flatMap(Collection::stream)
         .collect(Collectors.toList());
      PotionUtils.m_43552_(result, effects);
      setPotionColor(result, PotionUtils.m_43575_(potions.get(0)));
      result.m_41784_().m_128379_("MixedPotion", true);
      return result;
   }

   private static List<MobEffectInstance> applyMixtureMultipliers(List<MobEffectInstance> effects) {
      effects.replaceAll(effect -> {
         int duration = (int)((double)effect.m_19557_() * Config.mixture_effects_duration);
         int amplifier = (int)((double)effect.m_19564_() * Config.mixture_effects_strength);
         return new MobEffectInstance(effect.m_19544_(), duration, amplifier);
      });
      return effects;
   }

   private static int comparePotions(ItemStack potionStack1, ItemStack potionStack2) {
      return potionStack1.m_41778_().compareTo(potionStack2.m_41778_());
   }
}
