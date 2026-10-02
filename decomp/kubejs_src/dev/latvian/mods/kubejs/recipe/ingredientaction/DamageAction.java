package dev.latvian.mods.kubejs.recipe.ingredientaction;

import com.google.gson.JsonObject;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;

public class DamageAction extends IngredientAction {
   public final int amount;

   public DamageAction(int a) {
      this.amount = a;
   }

   @Override
   public ItemStack transform(ItemStack old, int index, CraftingContainer container) {
      old.m_41721_(old.m_41773_() + this.amount);
      return old.m_41773_() >= old.m_41776_() ? ItemStack.f_41583_ : old;
   }

   @Override
   public String getType() {
      return "damage";
   }

   @Override
   public void toJson(JsonObject json) {
      json.addProperty("damage", this.amount);
   }
}
