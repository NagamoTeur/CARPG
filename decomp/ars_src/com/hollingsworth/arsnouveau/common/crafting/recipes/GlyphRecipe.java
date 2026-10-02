package com.hollingsworth.arsnouveau.common.crafting.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hollingsworth.arsnouveau.api.RegistryHelper;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.common.block.tile.ScribesTile;
import com.hollingsworth.arsnouveau.common.items.Glyph;
import com.hollingsworth.arsnouveau.setup.RecipeRegistry;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;

public class GlyphRecipe implements Recipe<ScribesTile> {
   public ItemStack output;
   public List<Ingredient> inputs;
   public ResourceLocation id;
   public int exp;

   public GlyphRecipe(ResourceLocation id, ItemStack output, List<Ingredient> inputs, int exp) {
      this.id = id;
      this.output = output;
      this.inputs = inputs;
      this.exp = exp;
   }

   public GlyphRecipe withIngredient(Ingredient i) {
      this.inputs.add(i);
      return this;
   }

   public GlyphRecipe withIngredient(Ingredient ingredient, int count) {
      for (int i = 0; i < count; i++) {
         this.withIngredient(ingredient);
      }

      return this;
   }

   public GlyphRecipe withIngredient(TagKey<Item> tag, int count) {
      for (int i = 0; i < count; i++) {
         this.withIngredient(Ingredient.m_204132_(tag));
      }

      return this;
   }

   public GlyphRecipe withItem(ItemLike i) {
      this.inputs.add(Ingredient.m_43929_(new ItemLike[]{i}));
      return this;
   }

   public GlyphRecipe withItem(RegistryObject<? extends ItemLike> i) {
      return this.withItem((ItemLike)i.get());
   }

   public GlyphRecipe withItem(RegistryObject<? extends ItemLike> item, int count) {
      return this.withItem((ItemLike)item.get(), count);
   }

   public GlyphRecipe withItem(ItemLike item, int count) {
      for (int i = 0; i < count; i++) {
         this.withItem(item);
      }

      return this;
   }

   public GlyphRecipe withStack(ItemStack i) {
      this.inputs.add(Ingredient.m_43927_(new ItemStack[]{i}));
      return this;
   }

   public GlyphRecipe withStack(ItemStack stack, int count) {
      for (int i = 0; i < count; i++) {
         this.withStack(stack);
      }

      return this;
   }

   public AbstractSpellPart getSpellPart() {
      return ((Glyph)this.output.m_41720_()).spellPart;
   }

   public boolean matches(ScribesTile pContainer, Level pLevel) {
      return false;
   }

   public ItemStack assemble(ScribesTile pContainer) {
      return ItemStack.f_41583_;
   }

   public boolean m_8004_(int pWidth, int pHeight) {
      return true;
   }

   public ItemStack m_8043_() {
      return this.output.m_41777_();
   }

   public ResourceLocation m_6423_() {
      return this.id;
   }

   public RecipeSerializer<?> m_7707_() {
      return (RecipeSerializer<?>)RecipeRegistry.GLYPH_SERIALIZER.get();
   }

   public RecipeType<?> m_6671_() {
      return (RecipeType<?>)RecipeRegistry.GLYPH_TYPE.get();
   }

   public JsonElement asRecipe() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("type", "ars_nouveau:glyph");
      jsonobject.addProperty("count", this.output.m_41613_());
      JsonArray pedestalArr = new JsonArray();

      for (Ingredient i : this.inputs) {
         JsonObject object = new JsonObject();
         object.add("item", i.m_43942_());
         pedestalArr.add(object);
      }

      jsonobject.add("inputItems", pedestalArr);
      jsonobject.addProperty("exp", this.exp);
      jsonobject.addProperty("output", RegistryHelper.getRegistryName(this.output.m_41720_()).toString());
      return jsonobject;
   }

   public static class Serializer implements RecipeSerializer<GlyphRecipe> {
      public GlyphRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
         Item output = GsonHelper.m_13909_(json, "output");
         int count = GsonHelper.m_13927_(json, "count");
         ItemStack outputStack = new ItemStack(output, count);
         int levels = GsonHelper.m_13927_(json, "exp");
         JsonArray inputItems = GsonHelper.m_13933_(json, "inputItems");
         List<Ingredient> stacks = new ArrayList<>();

         for (JsonElement e : inputItems) {
            JsonObject obj = e.getAsJsonObject();
            Ingredient input = null;
            if (GsonHelper.m_13885_(obj, "item")) {
               input = Ingredient.m_43917_(GsonHelper.m_13933_(obj, "item"));
            } else {
               input = Ingredient.m_43917_(GsonHelper.m_13930_(obj, "item"));
            }

            stacks.add(input);
         }

         return new GlyphRecipe(recipeId, outputStack, stacks, levels);
      }

      public void toNetwork(FriendlyByteBuf buf, GlyphRecipe recipe) {
         buf.writeInt(recipe.inputs.size());

         for (Ingredient i : recipe.inputs) {
            i.m_43923_(buf);
         }

         buf.m_130055_(recipe.output);
         buf.writeInt(recipe.exp);
      }

      @Nullable
      public GlyphRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
         int length = buffer.readInt();
         List<Ingredient> stacks = new ArrayList<>();

         for (int i = 0; i < length; i++) {
            try {
               stacks.add(Ingredient.m_43940_(buffer));
            } catch (Exception var7) {
               var7.printStackTrace();
               break;
            }
         }

         return new GlyphRecipe(recipeId, buffer.m_130267_(), stacks, buffer.readInt());
      }
   }
}
