package shadows.apotheosis.adventure.affix.socket;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import shadows.apotheosis.adventure.AdventureModule;
import shadows.apotheosis.adventure.loot.LootCategory;

public class AddSocketsRecipe extends AdventureModule.ApothUpgradeRecipe {
   private final Ingredient input;
   private final int maxSockets;

   public AddSocketsRecipe(ResourceLocation id, Ingredient input, int maxSockets) {
      super(id, Ingredient.f_43901_, input, ItemStack.f_41583_);
      this.input = input;
      this.maxSockets = maxSockets;
   }

   public boolean m_5818_(Container pInv, Level pLevel) {
      ItemStack in = pInv.m_8020_(0);
      return !LootCategory.forItem(in).isNone() && SocketHelper.getSockets(in) < this.getMaxSockets() && this.getInput().test(pInv.m_8020_(1));
   }

   public ItemStack m_5874_(Container pInv) {
      ItemStack out = pInv.m_8020_(0).m_41777_();
      if (out.m_41619_()) {
         return ItemStack.f_41583_;
      } else {
         int sockets = SocketHelper.getSockets(out) + 1;
         SocketHelper.setSockets(out, sockets);
         return out;
      }
   }

   public RecipeSerializer<?> m_7707_() {
      return AddSocketsRecipe.Serializer.INSTANCE;
   }

   public boolean m_5598_() {
      return true;
   }

   public Ingredient getInput() {
      return this.input;
   }

   public int getMaxSockets() {
      return this.maxSockets;
   }

   public static class Serializer implements RecipeSerializer<AddSocketsRecipe> {
      public static AddSocketsRecipe.Serializer INSTANCE = new AddSocketsRecipe.Serializer();

      public AddSocketsRecipe fromJson(ResourceLocation id, JsonObject obj) {
         Ingredient item = CraftingHelper.getIngredient(GsonHelper.m_13930_(obj, "input"));
         int maxSockets = obj.get("max_sockets").getAsInt();
         return new AddSocketsRecipe(id, item, maxSockets);
      }

      public AddSocketsRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
         return new AddSocketsRecipe(id, Ingredient.m_43940_(buf), buf.readInt());
      }

      public void toNetwork(FriendlyByteBuf buf, AddSocketsRecipe recipe) {
         recipe.input.m_43923_(buf);
         buf.writeInt(recipe.getMaxSockets());
      }
   }
}
