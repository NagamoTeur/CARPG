package com.hollingsworth.arsnouveau.common.crafting.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hollingsworth.arsnouveau.api.RegistryHelper;
import com.hollingsworth.arsnouveau.api.enchanting_apparatus.EnchantingApparatusRecipe;
import com.hollingsworth.arsnouveau.common.block.tile.ImbuementTile;
import com.hollingsworth.arsnouveau.setup.RecipeRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;

public class ImbuementRecipe implements Recipe<ImbuementTile> {
   public final Ingredient input;
   public final ItemStack output;
   public final int source;
   public final ResourceLocation id;
   public List<Ingredient> pedestalItems;

   public ImbuementRecipe(ResourceLocation resourceLocation, Ingredient input, ItemStack output, int source, List<Ingredient> pedestalItems) {
      this.id = resourceLocation;
      this.input = input;
      this.output = output;
      this.source = source;
      this.pedestalItems = pedestalItems;
   }

   public ImbuementRecipe(String id, Ingredient ingredient, ItemStack output, int source, List<Ingredient> pedestalItems) {
      this(new ResourceLocation("ars_nouveau", "imbuement_" + id), ingredient, output, source, pedestalItems);
   }

   public ImbuementRecipe(String id, Ingredient ingredient, ItemStack output, int source) {
      this(new ResourceLocation("ars_nouveau", "imbuement_" + id), ingredient, output, source, new ArrayList<>());
   }

   public ImbuementRecipe withPedestalItem(Ingredient i) {
      this.pedestalItems.add(i);
      return this;
   }

   public ImbuementRecipe withPedestalItem(RegistryObject<? extends ItemLike> i) {
      return this.withPedestalItem((ItemLike)i.get());
   }

   public ImbuementRecipe withPedestalItem(ItemStack i) {
      this.pedestalItems.add(Ingredient.m_43927_(new ItemStack[]{i}));
      return this;
   }

   public ImbuementRecipe withPedestalItem(ItemLike i) {
      this.pedestalItems.add(Ingredient.m_43929_(new ItemLike[]{i}));
      return this;
   }

   public boolean isMatch(List<ItemStack> pedestalItems, ItemStack reagent, ImbuementTile imbuementTile, @Nullable Player player) {
      pedestalItems = pedestalItems.stream().filter(itemStack -> !itemStack.m_41619_()).collect(Collectors.toList());
      return this.doesReagentMatch(reagent)
         && this.pedestalItems.size() == pedestalItems.size()
         && EnchantingApparatusRecipe.doItemsMatch(pedestalItems, this.pedestalItems);
   }

   public boolean doesReagentMatch(ItemStack reag) {
      return this.input.test(reag);
   }

   public boolean matches(ImbuementTile pContainer, Level pLevel) {
      return this.input.test(pContainer.m_8020_(0)) && EnchantingApparatusRecipe.doItemsMatch(pContainer.getPedestalItems(), this.pedestalItems);
   }

   public ItemStack assemble(ImbuementTile pContainer) {
      return ItemStack.f_41583_;
   }

   public boolean m_8004_(int p_43999_, int p_44000_) {
      return true;
   }

   public ItemStack m_8043_() {
      return ItemStack.f_41583_;
   }

   public ResourceLocation m_6423_() {
      return this.id;
   }

   public RecipeSerializer<?> m_7707_() {
      return (RecipeSerializer<?>)RecipeRegistry.IMBUEMENT_SERIALIZER.get();
   }

   public RecipeType<?> m_6671_() {
      return (RecipeType<?>)Registry.f_122864_.m_7745_(new ResourceLocation("ars_nouveau", "imbuement"));
   }

   public JsonElement asRecipe() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("type", "ars_nouveau:imbuement");
      jsonobject.add("input", this.input.m_43942_());
      jsonobject.addProperty("output", RegistryHelper.getRegistryName(this.output.m_41720_()).toString());
      jsonobject.addProperty("count", this.output.m_41613_());
      jsonobject.addProperty("source", this.source);
      JsonArray pedestalArr = new JsonArray();

      for (Ingredient i : this.pedestalItems) {
         JsonObject object = new JsonObject();
         object.add("item", i.m_43942_());
         pedestalArr.add(object);
      }

      jsonobject.add("pedestalItems", pedestalArr);
      return jsonobject;
   }

   public static class Serializer implements RecipeSerializer<ImbuementRecipe> {
      public ImbuementRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
         Ingredient inputStack = null;
         if (GsonHelper.m_13885_(json, "input")) {
            inputStack = Ingredient.m_43917_(GsonHelper.m_13933_(json, "input"));
         } else {
            inputStack = Ingredient.m_43917_(GsonHelper.m_13930_(json, "input"));
         }

         Item output = GsonHelper.m_13909_(json, "output");
         int count = GsonHelper.m_13927_(json, "count");
         ItemStack outputStack = new ItemStack(output, count);
         int source = GsonHelper.m_13927_(json, "source");
         JsonArray pedestalItems = GsonHelper.m_13933_(json, "pedestalItems");
         List<Ingredient> stacks = new ArrayList<>();

         for (JsonElement e : pedestalItems) {
            JsonObject obj = e.getAsJsonObject();
            Ingredient input = null;
            if (GsonHelper.m_13885_(obj, "item")) {
               input = Ingredient.m_43917_(GsonHelper.m_13933_(obj, "item"));
            } else {
               input = Ingredient.m_43917_(GsonHelper.m_13930_(obj, "item"));
            }

            stacks.add(input);
         }

         return new ImbuementRecipe(recipeId, inputStack, outputStack, source, stacks);
      }

      public void toNetwork(FriendlyByteBuf buf, ImbuementRecipe recipe) {
         buf.writeInt(recipe.pedestalItems.size());

         for (Ingredient i : recipe.pedestalItems) {
            i.m_43923_(buf);
         }

         recipe.input.m_43923_(buf);
         buf.m_130055_(recipe.output);
         buf.writeInt(recipe.source);
      }

      @Nullable
      public ImbuementRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
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

         return new ImbuementRecipe(recipeId, Ingredient.m_43940_(buffer), buffer.m_130267_(), buffer.readInt(), stacks);
      }
   }
}
