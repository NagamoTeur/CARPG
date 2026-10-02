package com.aqutheseal.celestisynth.common.recipe.celestialcrafting;

import com.aqutheseal.celestisynth.common.registry.CSRecipeTypes;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.Advancement.Builder;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;

public class CelestialShapedRecipeBuilder implements RecipeBuilder {
   private final Item result;
   private final int count;
   private final List<String> rows = Lists.newArrayList();
   private final Map<Character, Ingredient> key = Maps.newLinkedHashMap();
   private final Builder advancement = Builder.m_138353_();
   @Nullable
   private String group;

   public CelestialShapedRecipeBuilder(ItemLike pResult, int pCount) {
      this.result = pResult.m_5456_();
      this.count = pCount;
   }

   public static CelestialShapedRecipeBuilder shaped(ItemLike pResult) {
      return shaped(pResult, 1);
   }

   public static CelestialShapedRecipeBuilder shaped(ItemLike pResult, int pCount) {
      return new CelestialShapedRecipeBuilder(pResult, pCount);
   }

   public CelestialShapedRecipeBuilder define(Character pSymbol, TagKey<Item> pTag) {
      return this.define(pSymbol, Ingredient.m_204132_(pTag));
   }

   public CelestialShapedRecipeBuilder define(Character pSymbol, ItemLike pItem) {
      return this.define(pSymbol, Ingredient.m_43929_(new ItemLike[]{pItem}));
   }

   public CelestialShapedRecipeBuilder define(Character pSymbol, Ingredient pIngredient) {
      if (this.key.containsKey(pSymbol)) {
         throw new IllegalArgumentException("Symbol '" + pSymbol + "' is already defined!");
      } else if (pSymbol == ' ') {
         throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
      } else {
         this.key.put(pSymbol, pIngredient);
         return this;
      }
   }

   public CelestialShapedRecipeBuilder pattern(String pPattern) {
      if (!this.rows.isEmpty() && pPattern.length() != this.rows.get(0).length()) {
         throw new IllegalArgumentException("Pattern must be the same width on every line!");
      } else {
         this.rows.add(pPattern);
         return this;
      }
   }

   public CelestialShapedRecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
      this.advancement.m_138386_(pCriterionName, pCriterionTrigger);
      return this;
   }

   public CelestialShapedRecipeBuilder group(@Nullable String pGroupName) {
      this.group = pGroupName;
      return this;
   }

   public Item m_142372_() {
      return this.result;
   }

   public void m_126140_(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
      this.ensureValid(pRecipeId);
      this.advancement
         .m_138396_(f_236353_)
         .m_138386_("has_the_recipe", RecipeUnlockedTrigger.m_63728_(pRecipeId))
         .m_138354_(net.minecraft.advancements.AdvancementRewards.Builder.m_10009_(pRecipeId))
         .m_138360_(RequirementsStrategy.f_15979_);
      pFinishedRecipeConsumer.accept(
         new CelestialShapedRecipeBuilder.Result(
            pRecipeId,
            this.result,
            this.count,
            this.group == null ? "" : this.group,
            this.rows,
            this.key,
            this.advancement,
            new ResourceLocation(pRecipeId.m_135827_(), "recipes/" + this.result.m_41471_().m_40783_() + "/" + pRecipeId.m_135815_())
         )
      );
   }

   private void ensureValid(ResourceLocation pId) {
      if (this.rows.isEmpty()) {
         throw new IllegalStateException("No pattern is defined for shaped recipe " + pId + "!");
      } else {
         Set<Character> set = Sets.newHashSet(this.key.keySet());
         set.remove(' ');

         for (String s : this.rows) {
            for (int i = 0; i < s.length(); i++) {
               char c0 = s.charAt(i);
               if (!this.key.containsKey(c0) && c0 != ' ') {
                  throw new IllegalStateException("Pattern in recipe " + pId + " uses undefined symbol '" + c0 + "'");
               }

               set.remove(c0);
            }
         }

         if (!set.isEmpty()) {
            throw new IllegalStateException("Ingredients are defined but not used in pattern for recipe " + pId);
         } else if (this.rows.size() == 1 && this.rows.get(0).length() == 1) {
            throw new IllegalStateException("Shaped recipe " + pId + " only takes in a single item - should it be a shapeless recipe instead?");
         } else if (this.advancement.m_138405_().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + pId);
         }
      }
   }

   public static class Result implements FinishedRecipe {
      private final ResourceLocation id;
      private final Item result;
      private final int count;
      private final String group;
      private final List<String> pattern;
      private final Map<Character, Ingredient> key;
      private final Builder advancement;
      private final ResourceLocation advancementId;

      public Result(
         ResourceLocation pId,
         Item pResult,
         int pCount,
         String pGroup,
         List<String> pPattern,
         Map<Character, Ingredient> pKey,
         Builder pAdvancement,
         ResourceLocation pAdvancementId
      ) {
         this.id = pId;
         this.result = pResult;
         this.count = pCount;
         this.group = pGroup;
         this.pattern = pPattern;
         this.key = pKey;
         this.advancement = pAdvancement;
         this.advancementId = pAdvancementId;
      }

      public void m_7917_(JsonObject pJson) {
         if (!this.group.isEmpty()) {
            pJson.addProperty("group", this.group);
         }

         JsonArray jsonarray = new JsonArray();

         for (String s : this.pattern) {
            jsonarray.add(s);
         }

         pJson.add("pattern", jsonarray);
         JsonObject jsonobject = new JsonObject();

         for (Entry<Character, Ingredient> entry : this.key.entrySet()) {
            jsonobject.add(String.valueOf(entry.getKey()), entry.getValue().m_43942_());
         }

         pJson.add("key", jsonobject);
         JsonObject jsonobject1 = new JsonObject();
         jsonobject1.addProperty("item", Registry.f_122827_.m_7981_(this.result).toString());
         if (this.count > 1) {
            jsonobject1.addProperty("count", this.count);
         }

         pJson.add("result", jsonobject1);
      }

      public RecipeSerializer<?> m_6637_() {
         return (RecipeSerializer<?>)CSRecipeTypes.SHAPED_CELESTIAL_CRAFTING.get();
      }

      public ResourceLocation m_6445_() {
         return this.id;
      }

      @Nullable
      public JsonObject m_5860_() {
         return this.advancement.m_138400_();
      }

      @Nullable
      public ResourceLocation m_6448_() {
         return this.advancementId;
      }
   }
}
