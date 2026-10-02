package dev.latvian.mods.kubejs.item;

import com.google.common.collect.Lists;
import dev.architectury.hooks.item.food.FoodPropertiesHooks;
import dev.latvian.mods.kubejs.registry.KubeJSRegistries;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.typings.Param;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.Builder;
import org.apache.commons.lang3.tuple.Pair;

public class FoodBuilder {
   private int hunger;
   private float saturation;
   private boolean meat;
   private boolean alwaysEdible;
   private boolean fastToEat;
   private final List<Pair<Supplier<MobEffectInstance>, Float>> effects = Lists.newArrayList();
   public Consumer<FoodEatenEventJS> eaten;

   public FoodBuilder() {
   }

   public FoodBuilder(FoodProperties properties) {
      this.hunger = properties.m_38744_();
      this.saturation = properties.m_38745_();
      this.meat = properties.m_38746_();
      this.alwaysEdible = properties.m_38747_();
      this.fastToEat = properties.m_38748_();
      properties.m_38749_().forEach(pair -> this.effects.add(Pair.of(pair::getFirst, (Float)pair.getSecond())));
   }

   @Info("Sets the hunger restored.")
   public FoodBuilder hunger(int h) {
      this.hunger = h;
      return this;
   }

   @Info("Sets the saturation modifier. Note that the saturation restored is hunger * saturation.")
   public FoodBuilder saturation(float s) {
      this.saturation = s;
      return this;
   }

   @Info("Sets whether the food is meat.")
   public FoodBuilder meat(boolean flag) {
      this.meat = flag;
      return this;
   }

   @Info("Sets the food is meat.")
   public FoodBuilder meat() {
      return this.meat(true);
   }

   @Info("Sets whether the food is always edible.")
   public FoodBuilder alwaysEdible(boolean flag) {
      this.alwaysEdible = flag;
      return this;
   }

   @Info("Sets the food is always edible.")
   public FoodBuilder alwaysEdible() {
      return this.alwaysEdible(true);
   }

   @Info("Sets whether the food is fast to eat (having half of the eating time).")
   public FoodBuilder fastToEat(boolean flag) {
      this.fastToEat = flag;
      return this;
   }

   @Info("Sets the food is fast to eat (having half of the eating time).")
   public FoodBuilder fastToEat() {
      return this.fastToEat(true);
   }

   @Info(
      value = "Adds an effect to the food. Note that the effect duration is in ticks (20 ticks = 1 second).\n",
      params = {@Param(
         name = "mobEffectId",
         value = "The id of the effect. Can be either a string or a ResourceLocation."
      ), @Param(
         name = "duration",
         value = "The duration of the effect in ticks."
      ), @Param(
         name = "amplifier",
         value = "The amplifier of the effect. 0 means level 1, 1 means level 2, etc."
      ), @Param(
         name = "probability",
         value = "The probability of the effect being applied. 1 = 100%."
      )}
   )
   public FoodBuilder effect(ResourceLocation mobEffectId, int duration, int amplifier, float probability) {
      this.effects.add(Pair.of(new FoodBuilder.EffectSupplier(mobEffectId, duration, amplifier), probability));
      return this;
   }

   @Info("Removes an effect from the food.")
   public FoodBuilder removeEffect(MobEffect mobEffect) {
      if (mobEffect == null) {
         return this;
      } else {
         this.effects.removeIf(pair -> {
            MobEffectInstance effectInstance = (MobEffectInstance)((Supplier)pair.getKey()).get();
            return effectInstance.m_19576_().equals(mobEffect.m_19481_());
         });
         return this;
      }
   }

   @Info("Sets a callback that is called when the food is eaten.\n\nNote: This is currently not having effect in `ItemEvents.modification`,\nas firing this callback requires an `ItemBuilder` instance in the `Item`.\n")
   public FoodBuilder eaten(Consumer<FoodEatenEventJS> e) {
      this.eaten = e;
      return this;
   }

   public FoodProperties build() {
      Builder b = new Builder();
      b.m_38760_(this.hunger);
      b.m_38758_(this.saturation);
      if (this.meat) {
         b.m_38757_();
      }

      if (this.alwaysEdible) {
         b.m_38765_();
      }

      if (this.fastToEat) {
         b.m_38766_();
      }

      for (Pair<Supplier<MobEffectInstance>, Float> effect : this.effects) {
         FoodPropertiesHooks.effect(b, (Supplier)effect.getLeft(), (Float)effect.getRight());
      }

      return b.m_38767_();
   }

   private static class EffectSupplier implements Supplier<MobEffectInstance> {
      private final ResourceLocation id;
      private final int duration;
      private final int amplifier;
      private MobEffect cachedEffect;

      public EffectSupplier(ResourceLocation id, int duration, int amplifier) {
         this.id = id;
         this.duration = duration;
         this.amplifier = amplifier;
      }

      public MobEffectInstance get() {
         if (this.cachedEffect == null) {
            this.cachedEffect = (MobEffect)KubeJSRegistries.mobEffects().get(this.id);
            if (this.cachedEffect == null) {
               Set<ResourceLocation> effectIds = KubeJSRegistries.mobEffects()
                  .entrySet()
                  .stream()
                  .map(entry -> ((ResourceKey)entry.getKey()).m_135782_())
                  .collect(Collectors.toSet());
               throw new RuntimeException(
                  String.format("Missing effect '%s'. Check spelling or maybe potion id was used instead of effect id. Possible ids: %s", this.id, effectIds)
               );
            }
         }

         return new MobEffectInstance(this.cachedEffect, this.duration, this.amplifier);
      }
   }
}
