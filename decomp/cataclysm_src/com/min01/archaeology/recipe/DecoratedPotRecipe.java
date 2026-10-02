package com.min01.archaeology.recipe;

import com.min01.archaeology.blockentity.DecoratedPotBlockEntity;
import com.min01.archaeology.init.ArchaelogyTags;
import com.min01.archaeology.init.ArchaeologyRecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class DecoratedPotRecipe extends CustomRecipe {
   public DecoratedPotRecipe(ResourceLocation location) {
      super(location);
   }

   public boolean matches(CraftingContainer container, Level level) {
      if (!this.m_8004_(container.m_39347_(), container.m_39346_())) {
         return false;
      } else {
         for (int i = 0; i < container.m_6643_(); i++) {
            ItemStack stack = container.m_8020_(i);
            if (i % 2 == 0) {
               if (!stack.m_150930_(Items.f_41852_)) {
                  return false;
               }
            } else if (!stack.m_204117_(ArchaelogyTags.DECORATED_POT_INGREDIENTS)) {
               return false;
            }
         }

         return true;
      }
   }

   public ItemStack assemble(CraftingContainer container) {
      DecoratedPotBlockEntity.Decorations decorations = new DecoratedPotBlockEntity.Decorations(
         container.m_8020_(1).m_41720_(), container.m_8020_(3).m_41720_(), container.m_8020_(5).m_41720_(), container.m_8020_(7).m_41720_()
      );
      return DecoratedPotBlockEntity.createDecoratedPotItem(decorations);
   }

   public boolean m_8004_(int width, int height) {
      return width == 3 && height == 3;
   }

   @NotNull
   public RecipeSerializer<?> m_7707_() {
      return (RecipeSerializer<?>)ArchaeologyRecipeSerializer.DECORATED_POT_RECIPE.get();
   }
}
