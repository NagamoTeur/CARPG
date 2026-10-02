package dev.latvian.mods.kubejs.recipe;

import dev.latvian.mods.kubejs.event.EventJS;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;

public class CompostableRecipesEventJS extends EventJS {
   public static Object2FloatMap<ItemLike> originalMap = null;

   public CompostableRecipesEventJS() {
      if (originalMap == null) {
         originalMap = new Object2FloatOpenHashMap(ComposterBlock.f_51914_);
      } else {
         ComposterBlock.f_51914_.clear();
         ComposterBlock.f_51914_.putAll(originalMap);
      }
   }

   public void remove(Ingredient ingredient) {
      for (Item item : ingredient.kjs$getItemTypes()) {
         ComposterBlock.f_51914_.removeFloat(item);
      }
   }

   public void removeAll() {
      ComposterBlock.f_51914_.clear();
   }

   public void add(Ingredient ingredient, float f) {
      for (Item item : ingredient.kjs$getItemTypes()) {
         ComposterBlock.f_51914_.put(item, Mth.m_14036_(f, 0.0F, 1.0F));
      }
   }
}
