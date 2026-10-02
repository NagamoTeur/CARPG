package daripher.skilltree.recipe;

import com.google.gson.JsonObject;
import daripher.skilltree.init.PSTRecipeSerializers;
import daripher.skilltree.potion.PotionHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MixtureRecipe extends CustomRecipe implements SkillRequiringRecipe {
   public MixtureRecipe(ResourceLocation id) {
      super(id);
   }

   public boolean matches(@NotNull CraftingContainer container, @NotNull Level level) {
      if (this.isUncraftable(container, this)) {
         return false;
      } else {
         ItemStack potionStack1 = ItemStack.f_41583_;
         ItemStack potionStack2 = ItemStack.f_41583_;
         int potionsCount = 0;

         for (int slot = 0; slot < container.m_6643_(); slot++) {
            ItemStack stackInSlot = container.m_8020_(slot);
            if (!stackInSlot.m_41619_() && PotionHelper.isPotion(stackInSlot) && !PotionHelper.isMixture(stackInSlot)) {
               potionsCount++;
               if (potionStack1.m_41619_()) {
                  potionStack1 = stackInSlot;
               } else {
                  potionStack2 = stackInSlot;
               }
            }
         }

         return !PotionUtils.m_43547_(potionStack1).isEmpty() && !PotionUtils.m_43547_(potionStack2).isEmpty()
            ? potionsCount == 2 && potionStack1.m_41720_() == potionStack2.m_41720_()
            : false;
      }
   }

   @NotNull
   public ItemStack assemble(@NotNull CraftingContainer container) {
      if (this.isUncraftable(container, this)) {
         return ItemStack.f_41583_;
      } else {
         ItemStack potionStack1 = ItemStack.f_41583_;
         ItemStack potionStack2 = ItemStack.f_41583_;

         for (int slot = 0; slot < container.m_6643_(); slot++) {
            ItemStack stackInSlot = container.m_8020_(slot);
            if (!stackInSlot.m_41619_() && PotionHelper.isPotion(stackInSlot) && !PotionHelper.isMixture(stackInSlot)) {
               if (potionStack1.m_41619_()) {
                  potionStack1 = stackInSlot;
               } else {
                  potionStack2 = stackInSlot;
               }
            }
         }

         return PotionHelper.mixPotions(potionStack1, potionStack2);
      }
   }

   public boolean m_8004_(int width, int height) {
      return width * height >= 2;
   }

   @NotNull
   public RecipeSerializer<?> m_7707_() {
      return (RecipeSerializer<?>)PSTRecipeSerializers.POTION_MIXING.get();
   }

   public static class Serializer implements RecipeSerializer<MixtureRecipe> {
      @NotNull
      public MixtureRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject jsonObject) {
         return new MixtureRecipe(id);
      }

      public MixtureRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
         return new MixtureRecipe(id);
      }

      public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull MixtureRecipe recipe) {
      }
   }
}
