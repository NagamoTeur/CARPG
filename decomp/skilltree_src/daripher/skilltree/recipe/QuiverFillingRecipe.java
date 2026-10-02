package daripher.skilltree.recipe;

import com.google.gson.JsonObject;
import daripher.skilltree.init.PSTRecipeSerializers;
import daripher.skilltree.item.ItemHelper;
import daripher.skilltree.item.quiver.QuiverItem;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class QuiverFillingRecipe extends CustomRecipe {
   public QuiverFillingRecipe(ResourceLocation id) {
      super(id);
   }

   public boolean matches(CraftingContainer container, @NotNull Level level) {
      ItemStack quiver = ItemStack.f_41583_;
      ItemStack arrows = ItemStack.f_41583_;
      int itemsCount = 0;

      for (int slot = 0; slot < container.m_6643_(); slot++) {
         ItemStack stack = container.m_8020_(slot);
         if (!stack.m_41619_()) {
            itemsCount++;
            if (ItemHelper.isQuiver(stack)) {
               quiver = stack;
            }

            if (stack.m_204117_(ItemTags.f_13161_)) {
               arrows = stack;
            }
         }
      }

      if (itemsCount != 2) {
         return false;
      } else if (quiver.m_41619_() || arrows.m_41619_()) {
         return false;
      } else {
         return QuiverItem.isFull(quiver) ? false : !QuiverItem.containsArrows(quiver) || ItemStack.m_150942_(QuiverItem.getArrows(quiver), arrows);
      }
   }

   @NotNull
   public ItemStack assemble(CraftingContainer container) {
      ItemStack quiver = ItemStack.f_41583_;
      ItemStack arrows = ItemStack.f_41583_;

      for (int slot = 0; slot < container.m_6643_(); slot++) {
         ItemStack stack = container.m_8020_(slot);
         if (!stack.m_41619_()) {
            if (ItemHelper.isQuiver(stack)) {
               quiver = stack;
            }

            if (stack.m_204117_(ItemTags.f_13161_)) {
               arrows = stack;
            }
         }
      }

      ItemStack result = quiver.m_41777_();
      int capacity = QuiverItem.getCapacity(result);
      int arrowsTaken = Math.min(capacity - QuiverItem.getArrowsCount(result), arrows.m_41613_());
      QuiverItem.addArrows(result, arrows, arrowsTaken);
      return result;
   }

   @NotNull
   public NonNullList<ItemStack> getRemainingItems(CraftingContainer container) {
      ItemStack quiver = ItemStack.f_41583_;
      ItemStack arrows = ItemStack.f_41583_;
      int arrowsSlot = 0;

      for (int slot = 0; slot < container.m_6643_(); slot++) {
         ItemStack stack = container.m_8020_(slot);
         if (!stack.m_41619_()) {
            if (ItemHelper.isQuiver(stack)) {
               quiver = stack;
            }

            if (stack.m_204117_(ItemTags.f_13161_)) {
               arrows = stack;
               arrowsSlot = slot;
            }
         }
      }

      int capacity = QuiverItem.getCapacity(quiver);
      int arrowsTaken = Math.min(capacity - QuiverItem.getArrowsCount(quiver), arrows.m_41613_());
      if (arrowsTaken == arrows.m_41613_()) {
         container.m_6836_(arrowsSlot, ItemStack.f_41583_);
      } else {
         arrows.m_41774_(arrowsTaken - 1);
      }

      return super.m_7457_(container);
   }

   public boolean m_8004_(int width, int height) {
      return width * height >= 2;
   }

   @NotNull
   public RecipeSerializer<?> m_7707_() {
      return (RecipeSerializer<?>)PSTRecipeSerializers.QUIVER_FILLING.get();
   }

   public static class Serializer implements RecipeSerializer<QuiverFillingRecipe> {
      @NotNull
      public QuiverFillingRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject jsonObject) {
         return new QuiverFillingRecipe(id);
      }

      public QuiverFillingRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
         return new QuiverFillingRecipe(id);
      }

      public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull QuiverFillingRecipe recipe) {
      }
   }
}
