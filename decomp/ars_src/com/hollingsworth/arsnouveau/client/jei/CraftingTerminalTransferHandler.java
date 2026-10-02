package com.hollingsworth.arsnouveau.client.jei;

import com.hollingsworth.arsnouveau.client.container.CraftingTerminalMenu;
import com.hollingsworth.arsnouveau.client.container.IAutoFillTerminal;
import com.hollingsworth.arsnouveau.client.container.StoredItemStack;
import com.hollingsworth.arsnouveau.common.menu.MenuRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import mezz.jei.api.recipe.transfer.IRecipeTransferError.Type;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import org.jetbrains.annotations.Nullable;

public class CraftingTerminalTransferHandler<C extends AbstractContainerMenu & IAutoFillTerminal> implements IRecipeTransferHandler<C, CraftingRecipe> {
   private final Class<C> containerClass;
   private final IRecipeTransferHandlerHelper helper;
   private static final List<Class<? extends AbstractContainerMenu>> containerClasses = new ArrayList<>();
   private static final IRecipeTransferError ERROR_INSTANCE = new IRecipeTransferError() {
      public Type getType() {
         return Type.INTERNAL;
      }
   };

   public CraftingTerminalTransferHandler(Class<C> containerClass, IRecipeTransferHandlerHelper helper) {
      this.containerClass = containerClass;
      this.helper = helper;
   }

   public Class<C> getContainerClass() {
      return this.containerClass;
   }

   @Nullable
   public IRecipeTransferError transferRecipe(
      AbstractContainerMenu container, CraftingRecipe recipe, IRecipeSlotsView recipeSlots, Player player, boolean maxTransfer, boolean doTransfer
   ) {
      if (!(container instanceof IAutoFillTerminal term)) {
         return ERROR_INSTANCE;
      } else {
         List<IRecipeSlotView> missing = new ArrayList<>();
         List<IRecipeSlotView> views = recipeSlots.getSlotViews();
         List<ItemStack[]> inputs = new ArrayList<>();
         Set<StoredItemStack> stored = new HashSet<>(term.getStoredItems());

         for (IRecipeSlotView view : views) {
            if (view.getRole() == RecipeIngredientRole.INPUT || view.getRole() == RecipeIngredientRole.CATALYST) {
               ItemStack[] possibleStacks = view.getIngredients(VanillaTypes.ITEM_STACK).toArray(ItemStack[]::new);
               if (possibleStacks.length == 0) {
                  inputs.add(null);
               } else {
                  inputs.add(possibleStacks);
                  boolean found = false;

                  for (ItemStack stack : possibleStacks) {
                     if (stack != null && player.m_150109_().m_36030_(stack) != -1) {
                        found = true;
                        break;
                     }
                  }

                  if (!found) {
                     for (ItemStack stackx : possibleStacks) {
                        StoredItemStack s = new StoredItemStack(stackx);
                        if (stored.contains(s)) {
                           found = true;
                           break;
                        }
                     }
                  }

                  if (!found) {
                     missing.add(view);
                  }
               }
            }
         }

         if (doTransfer) {
            ItemStack[][] stacks = inputs.toArray(new ItemStack[0][]);
            CompoundTag compound = new CompoundTag();
            ListTag list = new ListTag();

            for (int i = 0; i < stacks.length; i++) {
               if (stacks[i] != null) {
                  CompoundTag CompoundNBT = new CompoundTag();
                  CompoundNBT.m_128344_("s", (byte)i);
                  int k = 0;

                  for (int j = 0; j < stacks[i].length && k < 9; j++) {
                     if (stacks[i][j] != null && !stacks[i][j].m_41619_()) {
                        StoredItemStack s = new StoredItemStack(stacks[i][j]);
                        if (stored.contains(s) || player.m_150109_().m_36030_(stacks[i][j]) != -1) {
                           CompoundTag tag = new CompoundTag();
                           stacks[i][j].m_41739_(tag);
                           CompoundNBT.m_128365_("i" + k++, tag);
                        }
                     }
                  }

                  CompoundNBT.m_128344_("l", (byte)Math.min(9, k));
                  list.add(CompoundNBT);
               }
            }

            compound.m_128365_("i", list);
            term.sendMessage(compound);
         }

         return !missing.isEmpty()
            ? new CraftingTerminalTransferHandler.TransferWarning(
               this.helper.createUserErrorForMissingSlots(Component.m_237115_("tooltip.ars_nouveau.items_missing"), missing)
            )
            : null;
      }
   }

   public static void registerTransferHandlers(IRecipeTransferRegistration recipeTransferRegistry) {
      for (Class<? extends AbstractContainerMenu> aClass : containerClasses) {
         recipeTransferRegistry.addRecipeTransferHandler(
            new CraftingTerminalTransferHandler((Class<C>)aClass, recipeTransferRegistry.getTransferHelper()), RecipeTypes.CRAFTING
         );
      }
   }

   public Optional<MenuType<C>> getMenuType() {
      return Optional.of((MenuType<C>)MenuRegistry.STORAGE.get());
   }

   public RecipeType<CraftingRecipe> getRecipeType() {
      return RecipeTypes.CRAFTING;
   }

   static {
      containerClasses.add(CraftingTerminalMenu.class);
   }

   private static class TransferWarning implements IRecipeTransferError {
      private final IRecipeTransferError parent;

      public TransferWarning(IRecipeTransferError parent) {
         this.parent = parent;
      }

      public Type getType() {
         return Type.COSMETIC;
      }

      public void showError(PoseStack matrixStack, int mouseX, int mouseY, IRecipeSlotsView recipeLayout, int recipeX, int recipeY) {
         this.parent.showError(matrixStack, mouseX, mouseY, recipeLayout, recipeX, recipeY);
      }
   }
}
