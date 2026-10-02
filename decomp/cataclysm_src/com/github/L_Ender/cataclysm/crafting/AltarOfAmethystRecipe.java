package com.github.L_Ender.cataclysm.crafting;

import com.github.L_Ender.cataclysm.init.ModRecipeSerializers;
import com.github.L_Ender.cataclysm.init.ModRecipeTypes;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

public class AltarOfAmethystRecipe implements Recipe<Container> {
   private final Ingredient ingredients;
   private ItemStack result;
   private int time;
   private final ResourceLocation id;

   public AltarOfAmethystRecipe(ResourceLocation p_44523_, Ingredient ingredients, ItemStack result, int time) {
      this.id = p_44523_;
      this.ingredients = ingredients;
      this.result = result;
      this.time = time;
   }

   public ItemStack getResult() {
      return this.result;
   }

   public boolean m_5818_(Container p_44002_, Level p_44003_) {
      return this.ingredients.test(p_44002_.m_8020_(0));
   }

   public ItemStack m_5874_(Container p_44001_) {
      return this.result.m_41777_();
   }

   public boolean m_8004_(int p_43999_, int p_44000_) {
      return true;
   }

   public Ingredient getbaseIngredient() {
      return this.ingredients;
   }

   public ItemStack m_8043_() {
      return this.result;
   }

   public ResourceLocation m_6423_() {
      return this.id;
   }

   public RecipeSerializer<?> m_7707_() {
      return (RecipeSerializer<?>)ModRecipeSerializers.AMETHYST_BLESS.get();
   }

   public RecipeType<?> m_6671_() {
      return (RecipeType<?>)ModRecipeTypes.AMETHYST_BLESS.get();
   }

   public int getTime() {
      return this.time;
   }

   public static class Serializer implements RecipeSerializer<AltarOfAmethystRecipe> {
      public AltarOfAmethystRecipe fromJson(ResourceLocation p_44562_, JsonObject p_44563_) {
         Ingredient ingredient = Ingredient.m_43917_(GsonHelper.m_13930_(p_44563_, "ingredients"));
         ItemStack itemstack = ShapedRecipe.m_151274_(GsonHelper.m_13930_(p_44563_, "result"));
         int i = GsonHelper.m_13824_(p_44563_, "time", 200);
         return new AltarOfAmethystRecipe(p_44562_, ingredient, itemstack, i);
      }

      public AltarOfAmethystRecipe fromNetwork(ResourceLocation p_44565_, FriendlyByteBuf p_44566_) {
         Ingredient ingredient = Ingredient.m_43940_(p_44566_);
         ItemStack itemstack = p_44566_.m_130267_();
         int i = p_44566_.m_130242_();
         return new AltarOfAmethystRecipe(p_44565_, ingredient, itemstack, i);
      }

      public void toNetwork(FriendlyByteBuf p_44553_, AltarOfAmethystRecipe p_44554_) {
         p_44554_.ingredients.m_43923_(p_44553_);
         p_44553_.m_130055_(p_44554_.result);
         p_44553_.m_130130_(p_44554_.time);
      }
   }
}
