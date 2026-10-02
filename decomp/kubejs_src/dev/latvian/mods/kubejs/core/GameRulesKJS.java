package dev.latvian.mods.kubejs.core;

import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.level.GameRules.IntegerValue;
import net.minecraft.world.level.GameRules.Value;
import org.jetbrains.annotations.Nullable;

public interface GameRulesKJS {
   @Nullable
   Value<?> kjs$get(String var1);

   void kjs$set(String var1, String var2);

   default String kjs$getString(String rule) {
      Value<? extends Value<?>> o = (Value<? extends Value<?>>)this.kjs$get(rule);
      return o == null ? "" : o.m_5831_();
   }

   default boolean kjs$getBoolean(String rule) {
      if (this.kjs$get(rule) instanceof BooleanValue v && v.m_46223_()) {
         return true;
      }

      return false;
   }

   default int kjs$getInt(String rule) {
      return this.kjs$get(rule) instanceof IntegerValue v ? v.m_46288_() : 0;
   }
}
