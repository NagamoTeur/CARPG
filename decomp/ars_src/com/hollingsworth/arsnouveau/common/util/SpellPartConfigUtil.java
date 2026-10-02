package com.hollingsworth.arsnouveau.common.util;

import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class SpellPartConfigUtil {
   private static final Pattern AUGMENT_LIMITS_PATTERN = Pattern.compile("([^/=]+)=(\\d+)");

   public static SpellPartConfigUtil.AugmentLimits buildAugmentLimitsConfig(Builder builder, Map<ResourceLocation, Integer> defaults) {
      ConfigValue<List<? extends String>> configValue = builder.comment(
            new String[]{
               "Limits the number of times a given augment may be applied to a given effect", "Example entry: \"" + GlyphLib.AugmentAmplifyID + "=5\""
            }
         )
         .defineList("augment_limits", writeAugmentConfig(defaults), SpellPartConfigUtil::validateAugmentLimits);
      return new SpellPartConfigUtil.AugmentLimits(configValue);
   }

   public static SpellPartConfigUtil.ComboLimits buildInvalidCombosConfig(Builder builder, Set<ResourceLocation> defaults) {
      ConfigValue<List<? extends String>> configValue = builder.comment(
            new String[]{"Prevents the given glyph from being used in the same spell as the given glyph", "Example entry: \"" + GlyphLib.EffectBurstID + "\""}
         )
         .defineList("invalid_combos", writeComboConfig(defaults), o -> {
            if (o instanceof String s && ResourceLocation.m_135830_(s)) {
               return true;
            }

            return false;
         });
      return new SpellPartConfigUtil.ComboLimits(configValue);
   }

   private static List<String> writeComboConfig(Set<ResourceLocation> augmentLimits) {
      return augmentLimits.stream().<String>map(ResourceLocation::toString).collect(Collectors.toList());
   }

   private static List<String> writeAugmentConfig(Map<ResourceLocation, Integer> augmentLimits) {
      return augmentLimits.entrySet().stream().map(e -> e.getKey().toString() + "=" + e.getValue().toString()).collect(Collectors.toList());
   }

   private static boolean validateAugmentLimits(Object rawConfig) {
      return rawConfig instanceof CharSequence ? AUGMENT_LIMITS_PATTERN.matcher((CharSequence)rawConfig).matches() : false;
   }

   public static class AugmentLimits {
      private ConfigValue<List<? extends String>> configValue;

      private AugmentLimits(ConfigValue<List<? extends String>> configValue) {
         this.configValue = configValue;
      }

      public int getAugmentLimit(ResourceLocation augmentTag) {
         Map<ResourceLocation, Integer> limits = this.parseAugmentLimits();
         return limits.getOrDefault(augmentTag, Integer.MAX_VALUE);
      }

      private Map<ResourceLocation, Integer> parseAugmentLimits() {
         return ((List)this.configValue.get())
            .stream()
            .map(SpellPartConfigUtil.AUGMENT_LIMITS_PATTERN::matcher)
            .filter(Matcher::matches)
            .collect(Collectors.toMap(m -> new ResourceLocation(m.group(1)), m -> Integer.valueOf(m.group(2))));
      }
   }

   public static class ComboLimits {
      private final ConfigValue<List<? extends String>> configValue;

      public ComboLimits(ConfigValue<List<? extends String>> configValue) {
         this.configValue = configValue;
      }

      public boolean contains(ResourceLocation glyphTag) {
         return this.parseComboLimits().contains(glyphTag);
      }

      public Set<ResourceLocation> parseComboLimits() {
         return (Set<ResourceLocation>)(this.configValue == null
            ? new HashSet<>()
            : ((List)this.configValue.get()).stream().<ResourceLocation>map(ResourceLocation::m_135820_).collect(Collectors.toSet()));
      }
   }
}
