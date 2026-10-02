package com.hollingsworth.arsnouveau.api.registry;

import com.hollingsworth.arsnouveau.api.loot.DungeonLootTables;
import com.hollingsworth.arsnouveau.common.tomes.CasterTomeData;
import com.hollingsworth.arsnouveau.setup.RecipeRegistry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;

public class CasterTomeRegistry {
   private static List<CasterTomeData> TOME_DATA = new ArrayList<>();

   public static List<CasterTomeData> getTomeData() {
      return Collections.unmodifiableList(TOME_DATA);
   }

   public static List<CasterTomeData> reloadTomeData(RecipeManager recipeManager) {
      TOME_DATA = new ArrayList<>();
      List<CasterTomeData> recipes = recipeManager.m_44013_((RecipeType)RecipeRegistry.CASTER_TOME_TYPE.get());
      DungeonLootTables.CASTER_TOMES = new ArrayList<>();
      TOME_DATA = new ArrayList<>();
      TOME_DATA.addAll(recipes);
      recipes.forEach(tome -> DungeonLootTables.CASTER_TOMES.add(tome::m_8043_));
      return TOME_DATA;
   }
}
