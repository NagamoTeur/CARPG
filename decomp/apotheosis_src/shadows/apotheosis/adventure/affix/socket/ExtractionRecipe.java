package shadows.apotheosis.adventure.affix.socket;

import com.google.gson.JsonObject;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.adventure.AdventureModule;

public class ExtractionRecipe extends AdventureModule.ApothUpgradeRecipe implements IExtUpgradeRecipe {
   private static final ResourceLocation ID = new ResourceLocation("apotheosis:extraction");

   public ExtractionRecipe() {
      super(ID, Ingredient.f_43901_, Ingredient.m_43929_(new ItemLike[]{(ItemLike)Apoth.Items.VIAL_OF_EXTRACTION.get()}), ItemStack.f_41583_);
   }

   public boolean m_5818_(Container pInv, Level pLevel) {
      List<ItemStack> sockets = SocketHelper.getGems(pInv.m_8020_(0));
      return pInv.m_8020_(1).m_41720_() == Apoth.Items.VIAL_OF_EXTRACTION.get() && !sockets.isEmpty() && !sockets.get(0).m_41619_();
   }

   public ItemStack m_5874_(Container pInv) {
      ItemStack base = pInv.m_8020_(0);
      List<ItemStack> gems = SocketHelper.getGems(base);
      if (gems.isEmpty()) {
         return ItemStack.f_41583_;
      } else {
         ItemStack out = gems.get(0);
         out.m_41749_("uuids");
         return out;
      }
   }

   @Override
   public void onCraft(Container inv, Player player, ItemStack output) {
      ItemStack base = inv.m_8020_(0);
      List<ItemStack> gems = SocketHelper.getGems(base);

      for (int i = 1; i < gems.size(); i++) {
         ItemStack stack = gems.get(i);
         if (!stack.m_41619_()) {
            stack.m_41749_("uuids");
            if (!player.m_36356_(stack)) {
               Block.m_49840_(player.f_19853_, player.m_20183_(), stack);
            }
         }
      }
   }

   public RecipeSerializer<?> m_7707_() {
      return ExtractionRecipe.Serializer.INSTANCE;
   }

   public RecipeType<?> m_6671_() {
      return RecipeType.f_44113_;
   }

   public boolean m_5598_() {
      return true;
   }

   public static class Serializer implements RecipeSerializer<ExtractionRecipe> {
      public static ExtractionRecipe.Serializer INSTANCE = new ExtractionRecipe.Serializer();

      public ExtractionRecipe fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
         return new ExtractionRecipe();
      }

      public ExtractionRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
         return new ExtractionRecipe();
      }

      public void toNetwork(FriendlyByteBuf pBuffer, ExtractionRecipe pRecipe) {
      }
   }
}
