package com.hollingsworth.arsnouveau.setup;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class ConfigUtil {
   public static final Pattern STRING_INT_MAP = Pattern.compile("([^/=]+)=(\\p{Digit}+)");

   public static Map<String, Integer> parseMapConfig(ConfigValue<List<? extends String>> configValue) {
      return ((List)configValue.get())
         .stream()
         .map(STRING_INT_MAP::matcher)
         .filter(Matcher::matches)
         .collect(Collectors.toMap(m -> m.group(1), m -> Integer.valueOf(m.group(2))));
   }

   public static List<String> writeConfig(Map<String, Integer> map) {
      return map.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue().toString()).collect(Collectors.toList());
   }

   public static boolean validateMap(Object rawConfig) {
      return rawConfig instanceof CharSequence raw ? STRING_INT_MAP.matcher(raw).matches() : false;
   }
}
