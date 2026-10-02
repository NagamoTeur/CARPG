package shadows.apotheosis.potion;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;
import shadows.apotheosis.Apoth;
import shadows.placebo.recipe.RecipeHelper;

public class PotionCharmRecipe extends ShapedRecipe {
   protected final IntList potionSlots = new IntArrayList();
   protected final Ingredient potion = makePotionIngredient();

   public PotionCharmRecipe(List<Object> ingredients, int width, int height) {
      super(
         new ResourceLocation("apotheosis", "potion_charm"),
         "",
         width,
         height,
         makeIngredients(ingredients),
         new ItemStack((ItemLike)Apoth.Items.POTION_CHARM.get())
      );

      for (int i = 0; i < ingredients.size(); i++) {
         if ("potion".equals(ingredients.get(i))) {
            this.potionSlots.add(i);
         }
      }
   }

   private static Ingredient makePotionIngredient() {
      List<ItemStack> potionStacks = new ArrayList<>();

      for (Potion p : ForgeRegistries.POTIONS) {
         if (p.m_43488_().size() == 1 && !((MobEffectInstance)p.m_43488_().get(0)).m_19544_().m_8093_()) {
            ItemStack potion = new ItemStack(Items.f_42589_);
            PotionUtils.m_43549_(potion, p);
            potionStacks.add(potion);
         }
      }

      return Ingredient.m_43927_(potionStacks.toArray(new ItemStack[0]));
   }

   private static NonNullList<Ingredient> makeIngredients(List<Object> ingredients) {
      List<Object> realIngredients = new ArrayList<>();
      Ingredient potion = makePotionIngredient();

      for (Object o : ingredients) {
         if ("potion".equals(o)) {
            realIngredients.add(potion);
         } else {
            realIngredients.add(o);
         }
      }

      return RecipeHelper.createInput("apotheosis", true, realIngredients.toArray());
   }

   public Ingredient getPotionIngredient() {
      return this.potion;
   }

   public IntList getPotionSlots() {
      return this.potionSlots;
   }

   public ItemStack m_5874_(CraftingContainer inv) {
      ItemStack out = super.m_5874_(inv);
      PotionUtils.m_43549_(out, PotionUtils.m_43579_(inv.m_8020_(4)));
      return out;
   }

   public boolean m_5818_(CraftingContainer inv, Level world) {
      if (super.m_5818_(inv, world)) {
         List<Potion> potions = this.potionSlots.intStream().mapToObj(s -> inv.m_8020_(s)).<Potion>map(PotionUtils::m_43579_).collect(Collectors.toList());
         if (potions.size() > 0
            && potions.stream().allMatch(p -> p != null && p.m_43488_().size() == 1 && !((MobEffectInstance)p.m_43488_().get(0)).m_19544_().m_8093_())) {
            return potions.stream().distinct().count() == 1L;
         }
      }

      return false;
   }

   public RecipeSerializer<?> m_7707_() {
      return PotionCharmRecipe.Serializer.INSTANCE;
   }

   public static class Serializer implements RecipeSerializer<PotionCharmRecipe> {
      public static final PotionCharmRecipe.Serializer INSTANCE = new PotionCharmRecipe.Serializer();

      public PotionCharmRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
         JsonArray inputs = json.get("recipe").getAsJsonArray();
         int width = 0;
         int height = inputs.size();
         List<Object> ingredients = new ArrayList<>();

         for (JsonElement e : inputs) {
            JsonArray arr = e.getAsJsonArray();
            width = arr.size();

            for (JsonElement input : arr) {
               if (input.isJsonPrimitive() && "potion".equals(input.getAsString())) {
                  ingredients.add("potion");
               } else {
                  ingredients.add(CraftingHelper.getIngredient(input));
               }
            }
         }

         return new PotionCharmRecipe(ingredients, width, height);
      }

      public PotionCharmRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
         int width = buffer.readByte();
         int height = buffer.readByte();
         int potions = buffer.readByte();
         IntList potionSlots = new IntArrayList();

         for (int i = 0; i < potions; i++) {
            potionSlots.add(buffer.readByte());
         }

         List<Object> inputs = new ArrayList<>(width * height);

         for (int i = 0; i < width * height; i++) {
            if (!potionSlots.contains(i)) {
               inputs.add(i, Ingredient.m_43940_(buffer));
            } else {
               inputs.add("potion");
            }
         }

         return new PotionCharmRecipe(inputs, width, height);
      }

      public void toNetwork(FriendlyByteBuf buffer, PotionCharmRecipe recipe) {
         buffer.writeByte(recipe.getRecipeWidth());
         buffer.writeByte(recipe.getRecipeHeight());
         buffer.writeByte(recipe.potionSlots.size());
         List<Ingredient> inputs = recipe.potionSlots.iterator();

         while (inputs.hasNext()) {
            int i = (Integer)inputs.next();
            buffer.writeByte(i);
         }

         inputs = recipe.m_7527_();

         for (int i = 0; i < inputs.size(); i++) {
            if (!recipe.potionSlots.contains(i)) {
               inputs.get(i).m_43923_(buffer);
            }
         }
      }
   }
}
