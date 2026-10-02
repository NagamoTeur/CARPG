package shadows.apotheosis.adventure.affix.reforging;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.adventure.loot.LootRarity;

public record ReforgingRecipe(ResourceLocation id, LootRarity rarity, int matCost, int dustCost, int levelCost) implements Recipe<Container> {
   public ResourceLocation m_6423_() {
      return this.id;
   }

   public RecipeSerializer<?> m_7707_() {
      return ReforgingRecipe.Serializer.INSTANCE;
   }

   public RecipeType<?> m_6671_() {
      return Apoth.RecipeTypes.REFORGING;
   }

   @Deprecated
   public ItemStack m_8043_() {
      return ItemStack.f_41583_;
   }

   @Deprecated
   public boolean m_5818_(Container pContainer, Level pLevel) {
      return false;
   }

   @Deprecated
   public ItemStack m_5874_(Container pContainer) {
      return ItemStack.f_41583_;
   }

   @Deprecated
   public boolean m_8004_(int pWidth, int pHeight) {
      return false;
   }

   public static class Serializer implements RecipeSerializer<ReforgingRecipe> {
      public static final ReforgingRecipe.Serializer INSTANCE = new ReforgingRecipe.Serializer();

      public ReforgingRecipe fromJson(ResourceLocation id, JsonObject obj) {
         LootRarity rarity = LootRarity.byId(GsonHelper.m_13906_(obj, "rarity"));
         Preconditions.checkNotNull(rarity);
         int matCost = GsonHelper.m_13927_(obj, "material_cost");
         int dustCost = GsonHelper.m_13927_(obj, "dust_cost");
         int levelCost = GsonHelper.m_13927_(obj, "level_cost");
         return new ReforgingRecipe(id, rarity, matCost, dustCost, levelCost);
      }

      public void toNetwork(FriendlyByteBuf buf, ReforgingRecipe recipe) {
         buf.m_130070_(recipe.rarity.id());
         buf.writeByte(recipe.matCost);
         buf.writeByte(recipe.dustCost);
         buf.writeByte(recipe.levelCost);
      }

      public ReforgingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
         LootRarity rarity = LootRarity.byId(buf.m_130277_());
         return new ReforgingRecipe(id, rarity, buf.readByte(), buf.readByte(), buf.readByte());
      }
   }
}
