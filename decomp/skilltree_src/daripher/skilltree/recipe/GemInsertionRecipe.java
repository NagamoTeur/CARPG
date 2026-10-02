package daripher.skilltree.recipe;

import com.google.gson.JsonObject;
import daripher.skilltree.SkillTreeMod;
import daripher.skilltree.container.ContainerHelper;
import daripher.skilltree.init.PSTItems;
import daripher.skilltree.init.PSTRecipeSerializers;
import daripher.skilltree.item.gem.GemItem;
import java.util.Objects;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.UpgradeRecipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class GemInsertionRecipe extends UpgradeRecipe {
   public GemInsertionRecipe() {
      super(new ResourceLocation("skilltree", "gem_insertion"), Ingredient.f_43901_, Ingredient.f_43901_, ItemStack.f_41583_);
   }

   public boolean m_5818_(@NotNull Container container, @NotNull Level level) {
      return SkillTreeMod.apotheosisEnabled() ? false : this.canCraftIn(container);
   }

   @NotNull
   public ItemStack m_5874_(@NotNull Container container) {
      if (!SkillTreeMod.apotheosisEnabled() && this.canCraftIn(container)) {
         ItemStack base = container.m_8020_(0);
         ItemStack ingredient = container.m_8020_(1);
         ItemStack result = base.m_41777_();
         result.m_41764_(1);
         Player player = ContainerHelper.getViewingPlayer(container);
         Objects.requireNonNull(player);
         GemItem.insertGem(player, result, ingredient);
         return result;
      } else {
         return ItemStack.f_41583_;
      }
   }

   private boolean canCraftIn(@NotNull Container container) {
      Player player = ContainerHelper.getViewingPlayer(container);
      if (player == null) {
         return false;
      } else {
         ItemStack base = container.m_8020_(0);
         ItemStack ingredient = container.m_8020_(1);
         return ingredient.m_41720_() != PSTItems.GEM.get() ? false : GemItem.canInsertGem(player, base, ingredient);
      }
   }

   @NotNull
   public ItemStack m_8043_() {
      return ItemStack.f_41583_;
   }

   public boolean m_5598_() {
      return true;
   }

   @NotNull
   public RecipeSerializer<?> m_7707_() {
      return (RecipeSerializer<?>)PSTRecipeSerializers.GEM_INSERTION.get();
   }

   public boolean m_142505_() {
      return false;
   }

   public static class Serializer implements RecipeSerializer<GemInsertionRecipe> {
      @NotNull
      public GemInsertionRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject jsonObject) {
         return new GemInsertionRecipe();
      }

      public GemInsertionRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
         return new GemInsertionRecipe();
      }

      public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull GemInsertionRecipe recipe) {
      }
   }
}
