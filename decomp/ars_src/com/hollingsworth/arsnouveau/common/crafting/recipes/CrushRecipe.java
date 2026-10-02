package com.hollingsworth.arsnouveau.common.crafting.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hollingsworth.arsnouveau.api.RegistryHelper;
import com.hollingsworth.arsnouveau.setup.RecipeRegistry;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class CrushRecipe implements Recipe<Container> {
   public final Ingredient input;
   public final List<CrushRecipe.CrushOutput> outputs;
   public final ResourceLocation id;
   private boolean skipBlockPlace;

   public CrushRecipe(ResourceLocation id, Ingredient input, List<CrushRecipe.CrushOutput> outputs, boolean skipBlockPlace) {
      this.input = input;
      this.outputs = outputs;
      this.id = id;
      this.skipBlockPlace = skipBlockPlace;
   }

   @Deprecated
   public CrushRecipe(ResourceLocation id, Ingredient input, List<CrushRecipe.CrushOutput> outputs) {
      this(id, input, outputs, false);
   }

   public CrushRecipe(String id, Ingredient input, List<CrushRecipe.CrushOutput> outputs) {
      this(new ResourceLocation("ars_nouveau", "crush_" + id), input, outputs, false);
   }

   public CrushRecipe(String id, Ingredient input, List<CrushRecipe.CrushOutput> outputs, boolean skipBlockPlace) {
      this(new ResourceLocation("ars_nouveau", "crush_" + id), input, outputs, skipBlockPlace);
   }

   public CrushRecipe(String id, Ingredient input) {
      this(id, input, new ArrayList<>());
   }

   public List<ItemStack> getRolledOutputs(RandomSource random) {
      List<ItemStack> finalOutputs = new ArrayList<>();

      for (CrushRecipe.CrushOutput crushRoll : this.outputs) {
         if (random.m_188500_() <= (double)crushRoll.chance) {
            if (crushRoll.maxRange > 1) {
               int num = random.m_188503_(crushRoll.maxRange) + 1;

               for (int i = 0; i < num; i++) {
                  finalOutputs.add(crushRoll.stack.m_41777_());
               }
            } else {
               finalOutputs.add(crushRoll.stack.m_41777_());
            }
         }
      }

      return finalOutputs;
   }

   public CrushRecipe withItems(ItemStack output, float chance) {
      this.outputs.add(new CrushRecipe.CrushOutput(output, chance));
      return this;
   }

   public CrushRecipe withItems(ItemStack output) {
      this.outputs.add(new CrushRecipe.CrushOutput(output, 1.0F));
      return this;
   }

   public CrushRecipe skipBlockPlace() {
      this.skipBlockPlace = true;
      return this;
   }

   public Boolean shouldSkipBlockPlace() {
      return this.skipBlockPlace;
   }

   public boolean m_5818_(Container inventory, Level world) {
      return this.input.test(inventory.m_8020_(0));
   }

   public boolean matches(ItemStack i, Level world) {
      return this.input.test(i);
   }

   @NotNull
   public ItemStack m_5874_(Container inventory) {
      return ItemStack.f_41583_;
   }

   public boolean m_8004_(int p_194133_1_, int p_194133_2_) {
      return true;
   }

   @NotNull
   public ItemStack m_8043_() {
      return ItemStack.f_41583_;
   }

   @NotNull
   public ResourceLocation m_6423_() {
      return this.id;
   }

   public RecipeSerializer<?> m_7707_() {
      return (RecipeSerializer<?>)RecipeRegistry.CRUSH_SERIALIZER.get();
   }

   public RecipeType<?> m_6671_() {
      return (RecipeType<?>)RecipeRegistry.CRUSH_TYPE.get();
   }

   public JsonElement asRecipe() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("type", "ars_nouveau:crush");
      jsonobject.add("input", this.input.m_43942_());
      JsonArray array = new JsonArray();

      for (CrushRecipe.CrushOutput output : this.outputs) {
         JsonObject element = new JsonObject();
         element.addProperty("item", RegistryHelper.getRegistryName(output.stack.m_41720_()).toString());
         element.addProperty("chance", output.chance);
         element.addProperty("count", output.stack.m_41613_());
         element.addProperty("maxRange", output.maxRange);
         array.add(element);
      }

      jsonobject.add("output", array);
      jsonobject.addProperty("skip_block_place", this.skipBlockPlace);
      return jsonobject;
   }

   public static class CrushOutput {
      public ItemStack stack;
      public float chance;
      public int maxRange;

      public CrushOutput(ItemStack stack, float chance) {
         this(stack, chance, 1);
      }

      public CrushOutput(ItemStack stack, float chance, int maxRange) {
         this.stack = stack;
         this.chance = chance;
         this.maxRange = maxRange;
      }
   }

   public static class Serializer implements RecipeSerializer<CrushRecipe> {
      public CrushRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
         Ingredient input;
         if (GsonHelper.m_13885_(json, "input")) {
            input = Ingredient.m_43917_(GsonHelper.m_13933_(json, "input"));
         } else {
            input = Ingredient.m_43917_(GsonHelper.m_13930_(json, "input"));
         }

         JsonArray outputs = GsonHelper.m_13933_(json, "output");
         List<CrushRecipe.CrushOutput> parsedOutputs = new ArrayList<>();

         for (JsonElement e : outputs) {
            JsonObject obj = e.getAsJsonObject();
            float chance = GsonHelper.m_13915_(obj, "chance");
            String itemId = GsonHelper.m_13906_(obj, "item");
            int count = obj.has("count") ? GsonHelper.m_13927_(obj, "count") : 1;
            ItemStack output = new ItemStack((ItemLike)ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemId)), count);
            int maxRange = obj.has("maxRange") ? GsonHelper.m_13927_(obj, "maxRange") : 1;
            parsedOutputs.add(new CrushRecipe.CrushOutput(output, chance, maxRange));
         }

         boolean skipBlockPlace = json.has("skip_block_place") && GsonHelper.m_13912_(json, "skip_block_place");
         return new CrushRecipe(recipeId, input, parsedOutputs, skipBlockPlace);
      }

      public void toNetwork(FriendlyByteBuf buf, CrushRecipe recipe) {
         buf.writeInt(recipe.outputs.size());
         recipe.input.m_43923_(buf);

         for (CrushRecipe.CrushOutput i : recipe.outputs) {
            buf.writeFloat(i.chance);
            buf.writeItemStack(i.stack, false);
            buf.writeInt(i.maxRange);
         }

         buf.writeBoolean(recipe.skipBlockPlace);
      }

      @Nullable
      public CrushRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
         int length = buffer.readInt();
         Ingredient input = Ingredient.m_43940_(buffer);
         List<CrushRecipe.CrushOutput> stacks = new ArrayList<>();

         for (int i = 0; i < length; i++) {
            try {
               float chance = buffer.readFloat();
               ItemStack outStack = buffer.m_130267_();
               int maxRange = buffer.readInt();
               stacks.add(new CrushRecipe.CrushOutput(outStack, chance, maxRange));
            } catch (Exception var10) {
               var10.printStackTrace();
               break;
            }
         }

         boolean skipBlockPlace = buffer.readBoolean();
         return new CrushRecipe(recipeId, input, stacks, skipBlockPlace);
      }
   }
}
