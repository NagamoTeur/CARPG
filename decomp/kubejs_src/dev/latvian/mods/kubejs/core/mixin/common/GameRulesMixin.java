package dev.latvian.mods.kubejs.core.mixin.common;

import dev.latvian.mods.kubejs.core.GameRulesKJS;
import dev.latvian.mods.kubejs.util.UtilsJS;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.GameRuleTypeVisitor;
import net.minecraft.world.level.GameRules.Key;
import net.minecraft.world.level.GameRules.Type;
import net.minecraft.world.level.GameRules.Value;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@RemapPrefixForJS("kjs$")
@Mixin({GameRules.class})
public abstract class GameRulesMixin implements GameRulesKJS {
   private Map<String, Key<?>> kjs$keyCache;

   @Shadow
   public abstract <T extends Value<T>> T m_46170_(Key<T> var1);

   @Nullable
   private Key<?> getKey(String rule) {
      if (this.kjs$keyCache == null) {
         this.kjs$keyCache = new HashMap<>();
         GameRules.m_46164_(new GameRuleTypeVisitor() {
            public <T extends Value<T>> void m_6889_(Key<T> key, Type<T> type) {
               GameRulesMixin.this.kjs$keyCache.put(key.toString(), key);
            }
         });
      }

      return this.kjs$keyCache.get(rule);
   }

   @Nullable
   @Override
   public Value<?> kjs$get(String rule) {
      Key<? extends Value<?>> key = (Key<? extends Value<?>>)this.getKey(rule);
      return key == null ? null : this.m_46170_((Key<Value<?>>)key);
   }

   @Override
   public void kjs$set(String rule, String value) {
      Key<? extends Value<?>> key = (Key<? extends Value<?>>)this.getKey(rule);
      Value<?> r = key == null ? null : this.m_46170_((Key<Value<?>>)key);
      if (r != null) {
         r.m_7377_(value);
         if (UtilsJS.staticServer != null) {
            r.m_46368_(UtilsJS.staticServer);
         }
      }
   }
}
