package com.hollingsworth.arsnouveau.api.loot;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.common.datagen.DungeonLootGenerator;
import com.hollingsworth.arsnouveau.common.items.RitualTablet;
import com.hollingsworth.arsnouveau.common.potions.ModPotions;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.Config;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.ItemLike;

public class DungeonLootTables {
   public static List<Supplier<ItemStack>> BASIC_LOOT = new ArrayList<>();
   public static List<Supplier<ItemStack>> UNCOMMON_LOOT = new ArrayList<>();
   public static List<Supplier<ItemStack>> RARE_LOOT = new ArrayList<>();
   public static List<Supplier<ItemStack>> CASTER_TOMES = new ArrayList<>();
   public static Random r = new Random();

   public static ItemStack getRandomItem(List<Supplier<ItemStack>> pool) {
      return pool.isEmpty() ? ItemStack.f_41583_ : pool.get(r.nextInt(pool.size())).get();
   }

   public static List<ItemStack> getRandomRoll(DungeonLootGenerator.DungeonLootEnhancerModifier modifier) {
      List<ItemStack> stacks = new ArrayList<>();

      for (int i = 0; i < modifier.commonRolls; i++) {
         if (r.nextDouble() <= modifier.commonChance) {
            stacks.add(getRandomItem(BASIC_LOOT));
         }
      }

      for (int ix = 0; ix < modifier.uncommonRolls; ix++) {
         if (r.nextDouble() <= modifier.uncommonChance) {
            stacks.add(getRandomItem(UNCOMMON_LOOT));
         }
      }

      for (int ixx = 0; ixx < modifier.rareRolls; ixx++) {
         if (r.nextDouble() <= modifier.rareChance) {
            stacks.add(getRandomItem(RARE_LOOT));
         }
      }

      if ((Boolean)Config.SPAWN_TOMES.get()) {
         for (int ixxx = 0; ixxx < modifier.rareRolls; ixxx++) {
            if (r.nextDouble() <= modifier.rareChance) {
               stacks.add(getRandomItem(CASTER_TOMES));
            }
         }
      }

      return stacks;
   }

   static {
      BASIC_LOOT.add(() -> new ItemStack((ItemLike)ItemsRegistry.SOURCE_GEM.get(), 1 + r.nextInt(5)));
      BASIC_LOOT.add(() -> new ItemStack((ItemLike)ItemsRegistry.WILDEN_HORN.get(), 1 + r.nextInt(3)));
      BASIC_LOOT.add(() -> new ItemStack((ItemLike)ItemsRegistry.WILDEN_SPIKE.get(), 1 + r.nextInt(3)));
      BASIC_LOOT.add(() -> new ItemStack((ItemLike)ItemsRegistry.WILDEN_WING.get(), 1 + r.nextInt(3)));
      BASIC_LOOT.add(() -> new ItemStack(BlockRegistry.SOURCEBERRY_BUSH, 1 + r.nextInt(3)));
      BASIC_LOOT.add(() -> {
         ItemStack stack = new ItemStack(Items.f_42589_);
         PotionUtils.m_43549_(stack, (Potion)ModPotions.LONG_MANA_REGEN_POTION.get());
         return stack;
      });
      BASIC_LOOT.add(() -> {
         ItemStack stack = new ItemStack(Items.f_42589_);
         PotionUtils.m_43549_(stack, (Potion)ModPotions.STRONG_MANA_REGEN_POTION.get());
         return stack;
      });
      BASIC_LOOT.add(() -> {
         ItemStack stack = new ItemStack(Items.f_42589_);
         PotionUtils.m_43549_(stack, (Potion)ModPotions.MANA_REGEN_POTION.get());
         return stack;
      });
      UNCOMMON_LOOT.add(() -> new ItemStack((ItemLike)ItemsRegistry.WARP_SCROLL.get(), 1 + r.nextInt(2)));
      UNCOMMON_LOOT.add(() -> new ItemStack((ItemLike)ItemsRegistry.STARBUNCLE_SHARD.get()));
      UNCOMMON_LOOT.add(() -> new ItemStack((ItemLike)ItemsRegistry.WHIRLISPRIG_SHARDS.get()));
      UNCOMMON_LOOT.add(() -> new ItemStack((ItemLike)ItemsRegistry.DRYGMY_SHARD.get()));
      UNCOMMON_LOOT.add(() -> new ItemStack((ItemLike)ItemsRegistry.WIXIE_SHARD.get()));
      UNCOMMON_LOOT.add(() -> new ItemStack((ItemLike)ItemsRegistry.AMPLIFY_ARROW.get(), 16 + r.nextInt(16)));
      UNCOMMON_LOOT.add(() -> new ItemStack((ItemLike)ItemsRegistry.SPLIT_ARROW.get(), 16 + r.nextInt(16)));
      UNCOMMON_LOOT.add(() -> new ItemStack((ItemLike)ItemsRegistry.PIERCE_ARROW.get(), 16 + r.nextInt(16)));
      UNCOMMON_LOOT.add(() -> {
         List<RitualTablet> tablets = new ArrayList<>(ArsNouveauAPI.getInstance().getRitualItemMap().values());
         return new ItemStack((ItemLike)tablets.get(r.nextInt(tablets.size())));
      });
      RARE_LOOT.add(() -> new ItemStack((ItemLike)ItemsRegistry.FIREL_DISC.get()));
      RARE_LOOT.add(() -> new ItemStack((ItemLike)ItemsRegistry.SOUND_OF_GLASS.get()));
      RARE_LOOT.add(() -> new ItemStack((ItemLike)ItemsRegistry.WILD_HUNT.get()));
   }
}
