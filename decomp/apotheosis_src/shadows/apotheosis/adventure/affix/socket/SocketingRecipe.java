package shadows.apotheosis.adventure.affix.socket;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event.Result;
import shadows.apotheosis.adventure.AdventureModule;
import shadows.apotheosis.adventure.affix.socket.gem.Gem;
import shadows.apotheosis.adventure.affix.socket.gem.GemItem;
import shadows.apotheosis.adventure.event.ItemSocketingEvent;

public class SocketingRecipe extends AdventureModule.ApothUpgradeRecipe {
   private static final ResourceLocation ID = new ResourceLocation("apotheosis:socketing");

   public SocketingRecipe() {
      super(ID, Ingredient.f_43901_, Ingredient.f_43901_, ItemStack.f_41583_);
   }

   public boolean m_5818_(Container inv, Level pLevel) {
      ItemStack input = inv.m_8020_(0);
      ItemStack gemStack = inv.m_8020_(1);
      Gem gem = GemItem.getGem(gemStack);
      if (gem == null) {
         return false;
      } else if (!SocketHelper.hasEmptySockets(input)) {
         return false;
      } else {
         ItemSocketingEvent.CanSocket event = new ItemSocketingEvent.CanSocket(input, gemStack);
         MinecraftForge.EVENT_BUS.post(event);
         Result res = event.getResult();
         return res == Result.ALLOW ? true : res == Result.DEFAULT && gem.canApplyTo(inv.m_8020_(0), gemStack, GemItem.getLootRarity(gemStack));
      }
   }

   public ItemStack m_5874_(Container inv) {
      ItemStack input = inv.m_8020_(0);
      ItemStack gemStack = inv.m_8020_(1);
      if (input.m_41619_()) {
         return ItemStack.f_41583_;
      } else {
         ItemStack result = input.m_41777_();
         result.m_41764_(1);
         int socket = SocketHelper.getFirstEmptySocket(result);
         List<ItemStack> gems = new ArrayList<>(SocketHelper.getGems(result));
         ItemStack gemToInsert = gemStack.m_41777_();
         gemToInsert.m_41764_(1);
         gems.set(socket, gemStack.m_41777_());
         SocketHelper.setGems(result, gems);
         ItemSocketingEvent.ModifyResult event = new ItemSocketingEvent.ModifyResult(input, gemToInsert, result);
         MinecraftForge.EVENT_BUS.post(event);
         result = event.getOutput();
         if (result.m_41619_()) {
            throw new IllegalArgumentException("ItemSocketingEvent$ModifyResult produced an empty output stack.");
         } else {
            return result;
         }
      }
   }

   public boolean m_8004_(int pWidth, int pHeight) {
      return pWidth * pHeight >= 2;
   }

   public ItemStack m_8043_() {
      return ItemStack.f_41583_;
   }

   public ItemStack m_8042_() {
      return new ItemStack(Blocks.f_50625_);
   }

   public ResourceLocation m_6423_() {
      return ID;
   }

   public RecipeSerializer<?> m_7707_() {
      return SocketingRecipe.Serializer.INSTANCE;
   }

   public RecipeType<?> m_6671_() {
      return RecipeType.f_44113_;
   }

   public boolean m_5598_() {
      return true;
   }

   public static class Serializer implements RecipeSerializer<SocketingRecipe> {
      public static SocketingRecipe.Serializer INSTANCE = new SocketingRecipe.Serializer();

      public SocketingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
         return new SocketingRecipe();
      }

      public SocketingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
         return new SocketingRecipe();
      }

      public void toNetwork(FriendlyByteBuf pBuffer, SocketingRecipe pRecipe) {
      }
   }
}
