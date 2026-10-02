package io.redspace.ironsspellbooks.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.item.Scroll;
import io.redspace.ironsspellbooks.registries.LootRegistry;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction.Builder;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class RandomizeSpellFunction extends LootItemConditionalFunction {
   final NumberProvider qualityRange;
   final SpellFilter applicableSpells;

   protected RandomizeSpellFunction(LootItemCondition[] lootConditions, NumberProvider qualityRange, SpellFilter spellFilter) {
      super(lootConditions);
      this.qualityRange = qualityRange;
      this.applicableSpells = spellFilter;
   }

   public static Builder<?> create(NumberProvider quality, SpellFilter filter) {
      return m_80683_(functions -> new RandomizeSpellFunction(functions, quality, filter));
   }

   public static Builder<?> allSpells(NumberProvider quality) {
      return m_80683_(functions -> new RandomizeSpellFunction(functions, quality, new SpellFilter()));
   }

   protected ItemStack m_7372_(ItemStack itemStack, LootContext lootContext) {
      if (itemStack.m_41720_() instanceof Scroll || Utils.canImbue(itemStack)) {
         List<AbstractSpell> applicableSpells = this.applicableSpells.getApplicableSpells();
         if (applicableSpells.isEmpty()) {
            itemStack.m_41764_(0);
            return itemStack;
         }

         NavigableMap<Integer, AbstractSpell> spellList = this.getWeightedSpellList(applicableSpells);
         int total = spellList.floorKey(Integer.MAX_VALUE);
         AbstractSpell abstractSpell = SpellRegistry.none();
         if (!spellList.isEmpty()) {
            abstractSpell = spellList.higherEntry(lootContext.m_230907_().m_188503_(total)).getValue();
         }

         int maxLevel = abstractSpell.getMaxLevel();
         float quality = this.qualityRange.m_142688_(lootContext);
         int spellLevel = 1 + Math.round(quality * (float)(maxLevel - 1));
         if (itemStack.m_41720_() instanceof Scroll) {
            ISpellContainer.createScrollContainer(abstractSpell, spellLevel, itemStack);
         } else {
            ISpellContainer.createImbuedContainer(abstractSpell, spellLevel, itemStack);
         }
      }

      return itemStack;
   }

   private NavigableMap<Integer, AbstractSpell> getWeightedSpellList(List<AbstractSpell> entries) {
      int total = 0;
      NavigableMap<Integer, AbstractSpell> weightedSpells = new TreeMap<>();

      for (AbstractSpell entry : entries) {
         if (entry != SpellRegistry.none() && entry.isEnabled()) {
            total += this.getWeightFromRarity(SpellRarity.values()[entry.getMinRarity()]);
            weightedSpells.put(Integer.valueOf(total), entry);
         }
      }

      return weightedSpells;
   }

   public <N extends NumberProvider> N getQualityRange() {
      return (N)this.qualityRange;
   }

   private int getWeightFromRarity(SpellRarity rarity) {
      return switch (rarity) {
         case COMMON -> 40;
         case UNCOMMON -> 30;
         case RARE -> 15;
         case EPIC -> 8;
         case LEGENDARY -> 4;
      };
   }

   public LootItemFunctionType m_7162_() {
      return (LootItemFunctionType)LootRegistry.RANDOMIZE_SPELL_FUNCTION.get();
   }

   public static class Serializer extends net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction.Serializer<RandomizeSpellFunction> {
      public void serialize(JsonObject json, RandomizeSpellFunction scrollFunction, JsonSerializationContext jsonDeserializationContext) {
         super.m_6170_(json, scrollFunction, jsonDeserializationContext);
         JsonObject quality = new JsonObject();
         scrollFunction.qualityRange.m_142587_().m_79331_().m_6170_(quality, scrollFunction.getQualityRange(), jsonDeserializationContext);
         json.add("quality", quality);
         scrollFunction.applicableSpells.serialize(json);
      }

      public RandomizeSpellFunction deserialize(JsonObject json, JsonDeserializationContext jsonDeserializationContext, LootItemCondition[] lootConditions) {
         NumberProvider numberProvider = (NumberProvider)GsonHelper.m_13836_(json, "quality", jsonDeserializationContext, NumberProvider.class);
         SpellFilter applicableSpells = SpellFilter.deserializeSpellFilter(json);
         return new RandomizeSpellFunction(lootConditions, numberProvider, applicableSpells);
      }
   }
}
