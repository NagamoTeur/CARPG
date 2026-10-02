package shadows.apotheosis.adventure.affix.socket;

import com.google.gson.JsonObject;
import java.util.Collections;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.adventure.AdventureModule;

public class ExpulsionRecipe extends AdventureModule.ApothUpgradeRecipe {
   private static final ResourceLocation ID = new ResourceLocation("apotheosis:expulsion");

   public ExpulsionRecipe() {
      super(ID, Ingredient.f_43901_, Ingredient.m_43929_(new ItemLike[]{(ItemLike)Apoth.Items.VIAL_OF_EXPULSION.get()}), ItemStack.f_41583_);
   }

   public boolean m_5818_(Container pInv, Level pLevel) {
      return pInv.m_8020_(1).m_41720_() == Apoth.Items.VIAL_OF_EXPULSION.get() && SocketHelper.getGems(pInv.m_8020_(0)).stream().anyMatch(i -> !i.m_41619_());
   }

   public ItemStack m_5874_(Container pInv) {
      ItemStack out = pInv.m_8020_(0).m_41777_();
      if (out.m_41619_()) {
         return ItemStack.f_41583_;
      } else {
         SocketHelper.setGems(out, Collections.emptyList());
         return out;
      }
   }

   public RecipeSerializer<?> m_7707_() {
      return ExpulsionRecipe.Serializer.INSTANCE;
   }

   public RecipeType<?> m_6671_() {
      return RecipeType.f_44113_;
   }

   public boolean m_5598_() {
      return true;
   }

   public static class Serializer implements RecipeSerializer<ExpulsionRecipe> {
      public static ExpulsionRecipe.Serializer INSTANCE = new ExpulsionRecipe.Serializer();

      public ExpulsionRecipe fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
         return new ExpulsionRecipe();
      }

      public ExpulsionRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
         return new ExpulsionRecipe();
      }

      public void toNetwork(FriendlyByteBuf pBuffer, ExpulsionRecipe pRecipe) {
      }
   }
}
