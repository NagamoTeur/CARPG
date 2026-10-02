package dev.latvian.mods.kubejs.bindings;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;

public class DamageSourceWrapper {
   private static Map<String, DamageSource> damageSourceMap;

   public static DamageSource of(Object name) {
      if (name instanceof DamageSource) {
         return (DamageSource)name;
      } else if (name instanceof Player player) {
         return DamageSource.m_19344_(player);
      } else {
         if (damageSourceMap == null) {
            damageSourceMap = new HashMap<>();

            try {
               for (Field field : DamageSource.class.getDeclaredFields()) {
                  field.setAccessible(true);
                  if (Modifier.isStatic(field.getModifiers()) && field.getType() == DamageSource.class) {
                     DamageSource s = (DamageSource)field.get(null);
                     damageSourceMap.put(s.m_19385_(), s);
                  }
               }
            } catch (Exception var6) {
            }
         }

         return damageSourceMap.getOrDefault(String.valueOf(name), DamageSource.f_19318_);
      }
   }
}
