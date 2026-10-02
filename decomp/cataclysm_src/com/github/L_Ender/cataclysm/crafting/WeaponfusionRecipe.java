package com.github.L_Ender.cataclysm.crafting;

import com.github.L_Ender.cataclysm.init.ModBlocks;
import com.github.L_Ender.cataclysm.init.ModRecipeSerializers;
import com.github.L_Ender.cataclysm.init.ModRecipeTypes;
import com.google.gson.JsonObject;
import java.util.stream.Stream;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeHooks;

public class WeaponfusionRecipe implements Recipe<Container> {
   final Ingredient base;
   final Ingredient addition;
   final ItemStack result;
   private final ResourceLocation id;

   public WeaponfusionRecipe(ResourceLocation p_44523_, Ingredient p_44524_, Ingredient p_44525_, ItemStack p_44526_) {
      this.id = p_44523_;
      this.base = p_44524_;
      this.addition = p_44525_;
      this.result = p_44526_;
   }

   public boolean m_5818_(Container p_44533_, Level p_44534_) {
      return this.base.test(p_44533_.m_8020_(0)) && this.addition.test(p_44533_.m_8020_(1));
   }

   public ItemStack m_5874_(Container p_44531_) {
      ItemStack itemstack = this.result.m_41777_();
      CompoundTag compoundtag = p_44531_.m_8020_(0).m_41783_();
      if (compoundtag != null) {
         itemstack.m_41751_(compoundtag.m_6426_());
      }

      return itemstack;
   }

   public boolean m_8004_(int p_44528_, int p_44529_) {
      return p_44528_ * p_44529_ >= 2;
   }

   public ItemStack m_8043_() {
      return this.result;
   }

   public Ingredient getbaseIngredient() {
      return this.base;
   }

   public Ingredient getAdditionIngredient() {
      return this.addition;
   }

   public boolean isAdditionIngredient(ItemStack p_44536_) {
      return this.addition.test(p_44536_);
   }

   public ItemStack m_8042_() {
      return new ItemStack((ItemLike)ModBlocks.MECHANICAL_FUSION_ANVIL.get());
   }

   public ResourceLocation m_6423_() {
      return this.id;
   }

   public RecipeSerializer<?> m_7707_() {
      return (RecipeSerializer<?>)ModRecipeSerializers.WEAPON_FUSION.get();
   }

   public RecipeType<?> m_6671_() {
      return (RecipeType<?>)ModRecipeTypes.WEAPON_FUSION.get();
   }

   public boolean m_142505_() {
      return Stream.of(this.base, this.addition).anyMatch(p_151284_ -> ForgeHooks.hasNoElements(p_151284_));
   }

   public static class Serializer implements RecipeSerializer<WeaponfusionRecipe> {
      public WeaponfusionRecipe fromJson(ResourceLocation p_44562_, JsonObject p_44563_) {
         Ingredient ingredient = Ingredient.m_43917_(GsonHelper.m_13930_(p_44563_, "base"));
         Ingredient ingredient1 = Ingredient.m_43917_(GsonHelper.m_13930_(p_44563_, "addition"));
         ItemStack itemstack = ShapedRecipe.m_151274_(GsonHelper.m_13930_(p_44563_, "result"));
         return new WeaponfusionRecipe(p_44562_, ingredient, ingredient1, itemstack);
      }

      public WeaponfusionRecipe fromNetwork(ResourceLocation p_44565_, FriendlyByteBuf p_44566_) {
         Ingredient ingredient = Ingredient.m_43940_(p_44566_);
         Ingredient ingredient1 = Ingredient.m_43940_(p_44566_);
         ItemStack itemstack = p_44566_.m_130267_();
         return new WeaponfusionRecipe(p_44565_, ingredient, ingredient1, itemstack);
      }

      public void toNetwork(FriendlyByteBuf p_44553_, WeaponfusionRecipe p_44554_) {
         p_44554_.base.m_43923_(p_44553_);
         p_44554_.addition.m_43923_(p_44553_);
         p_44553_.m_130055_(p_44554_.result);
      }
   }
}
