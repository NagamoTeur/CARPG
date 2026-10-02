package dev.latvian.mods.kubejs.recipe;

import dev.latvian.mods.kubejs.platform.IngredientPlatformHelper;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

public class ModifyRecipeCraftingGrid {
   private final CraftingContainer container;

   public ModifyRecipeCraftingGrid(CraftingContainer c) {
      this.container = c;
   }

   public ItemStack get(int index) {
      return this.container.m_8020_(index).m_41777_();
   }

   public List<ItemStack> findAll(Ingredient ingredient) {
      List<ItemStack> list = new ArrayList<>();

      for (int i = 0; i < this.container.m_6643_(); i++) {
         ItemStack stack = this.container.m_8020_(i);
         if (ingredient.test(stack)) {
            list.add(stack.m_41777_());
         }
      }

      return list;
   }

   public List<ItemStack> findAll() {
      return this.findAll(IngredientPlatformHelper.get().wildcard());
   }

   public ItemStack find(Ingredient ingredient, int skip) {
      for (int i = 0; i < this.container.m_6643_(); i++) {
         ItemStack stack = this.container.m_8020_(i);
         if (ingredient.test(stack)) {
            if (skip <= 0) {
               return stack.m_41777_();
            }

            skip--;
         }
      }

      return ItemStack.f_41583_;
   }

   public ItemStack find(Ingredient ingredient) {
      return this.find(ingredient, 0);
   }

   public int getWidth() {
      return this.container.m_39347_();
   }

   public int getHeight() {
      return this.container.m_39346_();
   }

   @Nullable
   public AbstractContainerMenu getMenu() {
      return this.container.f_39323_;
   }

   @Nullable
   public Player getPlayer() {
      if (this.container.f_39323_ instanceof CraftingMenu menu && menu.f_39351_ != null) {
         return menu.f_39351_;
      }

      if (this.container.f_39323_ instanceof InventoryMenu menu && menu.f_39703_ != null) {
         return menu.f_39703_;
      }

      return null;
   }
}
