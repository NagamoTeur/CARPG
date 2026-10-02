package com.hollingsworth.arsnouveau.api.enchanting_apparatus;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hollingsworth.arsnouveau.api.RegistryHelper;
import com.hollingsworth.arsnouveau.common.block.tile.EnchantingApparatusTile;
import com.hollingsworth.arsnouveau.common.util.Log;
import com.hollingsworth.arsnouveau.setup.RecipeRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.RecipeMatcher;

public class EnchantingApparatusRecipe implements IEnchantingRecipe {
   public Ingredient reagent;
   public ItemStack result;
   public List<Ingredient> pedestalItems;
   public ResourceLocation id;
   public int sourceCost;
   public boolean keepNbtOfReagent = false;

   public EnchantingApparatusRecipe(ItemStack result, Ingredient reagent, List<Ingredient> pedestalItems) {
      this.reagent = reagent;
      this.pedestalItems = pedestalItems;
      this.result = result;
      this.sourceCost = 0;
      this.id = new ResourceLocation("ars_nouveau", RegistryHelper.getRegistryName(result.m_41720_()).m_135815_());
   }

   public EnchantingApparatusRecipe(ResourceLocation id, List<Ingredient> pedestalItems, Ingredient reagent, ItemStack result) {
      this(id, pedestalItems, reagent, result, 0, false);
   }

   public EnchantingApparatusRecipe(
      ResourceLocation id, List<Ingredient> pedestalItems, Ingredient reagent, ItemStack result, int cost, boolean keepNbtOfReagent
   ) {
      this.reagent = reagent;
      this.pedestalItems = pedestalItems;
      this.result = result;
      this.sourceCost = cost;
      this.id = id;
      this.keepNbtOfReagent = keepNbtOfReagent;
   }

   public EnchantingApparatusRecipe() {
      this.reagent = Ingredient.f_43901_;
      this.result = ItemStack.f_41583_;
      this.pedestalItems = new ArrayList<>();
      this.sourceCost = 0;
      this.id = new ResourceLocation("ars_nouveau", "empty");
   }

   public boolean excludeJei() {
      return false;
   }

   @Override
   public boolean isMatch(List<ItemStack> pedestalItems, ItemStack reagent, EnchantingApparatusTile enchantingApparatusTile, @Nullable Player player) {
      pedestalItems = pedestalItems.stream().filter(itemStack -> !itemStack.m_41619_()).collect(Collectors.toList());
      return this.doesReagentMatch(reagent) && this.pedestalItems.size() == pedestalItems.size() && doItemsMatch(pedestalItems, this.pedestalItems);
   }

   public boolean doesReagentMatch(ItemStack reag) {
      return this.reagent.test(reag);
   }

   @Override
   public ItemStack getResult(List<ItemStack> pedestalItems, ItemStack reagent, EnchantingApparatusTile enchantingApparatusTile) {
      ItemStack result = this.result.m_41777_();
      if (this.keepNbtOfReagent && reagent.m_41782_()) {
         result.m_41751_(reagent.m_41783_());
         result.m_41721_(0);
      }

      return result.m_41777_();
   }

   public static boolean doItemsMatch(List<ItemStack> inputs, List<Ingredient> recipeItems) {
      StackedContents recipeitemhelper = new StackedContents();

      for (ItemStack i : inputs) {
         recipeitemhelper.m_36468_(i, 1);
      }

      return inputs.size() == recipeItems.size() && RecipeMatcher.findMatches(inputs, recipeItems) != null;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         EnchantingApparatusRecipe that = (EnchantingApparatusRecipe)o;
         return Objects.equals(this.reagent, that.reagent) && Objects.equals(this.pedestalItems, that.pedestalItems);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.reagent, this.pedestalItems);
   }

   @Override
   public String toString() {
      return "EnchantingApparatusRecipe{catalyst=" + this.reagent + ", result=" + this.result + ", pedestalItems=" + this.pedestalItems + "}";
   }

   public JsonElement asRecipe() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("type", "ars_nouveau:enchanting_apparatus");
      JsonArray pedestalArr = new JsonArray();

      for (Ingredient i : this.pedestalItems) {
         pedestalArr.add(i.m_43942_());
      }

      JsonArray reagent = new JsonArray();
      reagent.add(this.reagent.m_43942_());
      jsonobject.add("reagent", reagent);
      JsonObject resultObj = new JsonObject();
      resultObj.addProperty("item", RegistryHelper.getRegistryName(this.result.m_41720_()).toString());
      int count = this.result.m_41613_();
      if (count > 1) {
         resultObj.addProperty("count", count);
      }

      jsonobject.add("pedestalItems", pedestalArr);
      jsonobject.add("output", resultObj);
      jsonobject.addProperty("sourceCost", this.sourceCost);
      jsonobject.addProperty("keepNbtOfReagent", this.keepNbtOfReagent);
      return jsonobject;
   }

   @Override
   public boolean consumesSource() {
      return this.getSourceCost() > 0;
   }

   @Override
   public int getSourceCost() {
      return this.sourceCost;
   }

   public boolean matches(EnchantingApparatusTile tile, Level worldIn) {
      return this.isMatch(tile.getPedestalItems(), tile.getStack(), tile, null);
   }

   public boolean matches(EnchantingApparatusTile tile, Level worldIn, @Nullable Player playerEntity) {
      return this.isMatch(tile.getPedestalItems(), tile.getStack(), tile, playerEntity);
   }

   public ItemStack assemble(EnchantingApparatusTile inv) {
      return this.result;
   }

   public boolean m_8004_(int width, int height) {
      return false;
   }

   public ItemStack m_8043_() {
      return this.result == null ? ItemStack.f_41583_ : this.result;
   }

   public ResourceLocation m_6423_() {
      return this.id;
   }

   public RecipeSerializer<?> m_7707_() {
      return (RecipeSerializer<?>)RecipeRegistry.APPARATUS_SERIALIZER.get();
   }

   public RecipeType<?> m_6671_() {
      return (RecipeType<?>)Registry.f_122864_.m_7745_(new ResourceLocation("ars_nouveau", "enchanting_apparatus"));
   }

   public static class Serializer implements RecipeSerializer<EnchantingApparatusRecipe> {
      private List<Ingredient> getPedestalItems(ResourceLocation recipeId, JsonArray pedestalJson) {
         return StreamSupport.<JsonElement>stream(pedestalJson.spliterator(), true).map(el -> {
            if (el instanceof JsonObject obj && (GsonHelper.m_144772_(obj, "item") || GsonHelper.m_13885_(obj, "item"))) {
               Log.getLogger().warn("Use of deprecated recipe format in recipe ID: {}", recipeId);
               return Ingredient.m_43917_(obj.get("item"));
            }

            return Ingredient.m_43917_(el);
         }).collect(Collectors.toList());
      }

      public EnchantingApparatusRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
         Ingredient reagent = Ingredient.m_43917_(GsonHelper.m_13933_(json, "reagent"));
         ItemStack output = ShapedRecipe.m_151274_(GsonHelper.m_13930_(json, "output"));
         int cost = json.has("sourceCost") ? GsonHelper.m_13927_(json, "sourceCost") : 0;
         boolean keepNbtOfReagent = json.has("keepNbtOfReagent") && GsonHelper.m_13912_(json, "keepNbtOfReagent");
         JsonArray pedestalItems = GsonHelper.m_13933_(json, "pedestalItems");
         List<Ingredient> stacks = this.getPedestalItems(recipeId, pedestalItems);
         return new EnchantingApparatusRecipe(recipeId, stacks, reagent, output, cost, keepNbtOfReagent);
      }

      @Nullable
      public EnchantingApparatusRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
         int length = buffer.readInt();
         Ingredient reagent = Ingredient.m_43940_(buffer);
         ItemStack output = buffer.m_130267_();
         List<Ingredient> stacks = new ArrayList<>();

         for (int i = 0; i < length; i++) {
            try {
               stacks.add(Ingredient.m_43940_(buffer));
            } catch (Exception var9) {
               var9.printStackTrace();
               break;
            }
         }

         int cost = buffer.readInt();
         boolean keepNbtOfReagent = buffer.readBoolean();
         return new EnchantingApparatusRecipe(recipeId, stacks, reagent, output, cost, keepNbtOfReagent);
      }

      public void toNetwork(FriendlyByteBuf buf, EnchantingApparatusRecipe recipe) {
         buf.writeInt(recipe.pedestalItems.size());
         recipe.reagent.m_43923_(buf);
         buf.m_130055_(recipe.result);

         for (Ingredient i : recipe.pedestalItems) {
            i.m_43923_(buf);
         }

         buf.writeInt(recipe.sourceCost);
         buf.writeBoolean(recipe.keepNbtOfReagent);
      }
   }
}
