package com.hollingsworth.arsnouveau.common.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hollingsworth.arsnouveau.api.loot.DungeonLootTables;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.io.IOException;
import java.util.function.Supplier;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition.Builder;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class DungeonLootGenerator extends GlobalLootModifierProvider {
   private final DataGenerator gen;
   private final String modid;
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

   public DungeonLootGenerator(DataGenerator gen, String modid) {
      super(gen, modid);
      this.gen = gen;
      this.modid = modid;
   }

   protected void start() {
   }

   public void m_213708_(CachedOutput cache) throws IOException {
      super.m_213708_(cache);
   }

   public LootItemCondition getList(String[] chests) {
      Builder condition = null;

      for (String s : chests) {
         if (condition == null) {
            condition = LootTableIdCondition.builder(new ResourceLocation(s));
         } else {
            condition = condition.m_7818_(LootTableIdCondition.builder(new ResourceLocation(s)));
         }
      }

      return condition.m_6409_();
   }

   public static class DungeonLootEnhancerModifier extends LootModifier {
      public double commonChance;
      public double uncommonChance;
      public double rareChance;
      public int commonRolls;
      public int uncommonRolls;
      public int rareRolls;
      public static final Supplier<Codec<DungeonLootGenerator.DungeonLootEnhancerModifier>> CODEC = () -> RecordCodecBuilder.create(
            instance -> instance.group(
                     LOOT_CONDITIONS_CODEC.fieldOf("conditions").forGetter(lm -> lm.conditions),
                     Codec.DOUBLE.fieldOf("common_chance").forGetter(d -> d.commonChance),
                     Codec.DOUBLE.fieldOf("uncommon_chance").forGetter(d -> d.uncommonChance),
                     Codec.DOUBLE.fieldOf("rare_chance").forGetter(d -> d.rareChance),
                     Codec.INT.fieldOf("common_rolls").forGetter(d -> d.commonRolls),
                     Codec.INT.fieldOf("uncommon_rolls").forGetter(d -> d.uncommonRolls),
                     Codec.INT.fieldOf("rare_rolls").forGetter(d -> d.rareRolls)
                  )
                  .apply(instance, DungeonLootGenerator.DungeonLootEnhancerModifier::new)
         );

      public DungeonLootEnhancerModifier(
         LootItemCondition[] conditionsIn, double commonChance, double uncommonChance, double rareChance, int commonRolls, int uncommonRolls, int rareRolls
      ) {
         super(conditionsIn);
         this.commonChance = commonChance;
         this.uncommonChance = uncommonChance;
         this.rareChance = rareChance;
         this.commonRolls = commonRolls;
         this.uncommonRolls = uncommonRolls;
         this.rareRolls = rareRolls;
      }

      public DungeonLootEnhancerModifier(LootItemCondition[] conditionsIn) {
         super(conditionsIn);
         this.commonChance = 0.3;
         this.uncommonChance = 0.2;
         this.rareChance = 0.1;
         this.commonRolls = 3;
         this.uncommonRolls = 2;
         this.rareRolls = 1;
      }

      protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
         generatedLoot.addAll(DungeonLootTables.getRandomRoll(this));
         return generatedLoot;
      }

      public Codec<? extends IGlobalLootModifier> codec() {
         return CODEC.get();
      }
   }
}
