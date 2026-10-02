package com.hollingsworth.arsnouveau.api.enchanting_apparatus;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hollingsworth.arsnouveau.api.spell.ISpellCaster;
import com.hollingsworth.arsnouveau.api.util.CasterUtil;
import com.hollingsworth.arsnouveau.common.block.tile.EnchantingApparatusTile;
import com.hollingsworth.arsnouveau.common.enchantment.EnchantmentRegistry;
import com.hollingsworth.arsnouveau.common.items.SpellParchment;
import com.hollingsworth.arsnouveau.common.spell.casters.ReactiveCaster;
import com.hollingsworth.arsnouveau.setup.RecipeRegistry;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

public class ReactiveEnchantmentRecipe extends EnchantmentRecipe {
   public ReactiveEnchantmentRecipe(List<Ingredient> pedestalItems, int sourceCost) {
      super(pedestalItems, (Enchantment)EnchantmentRegistry.REACTIVE_ENCHANTMENT.get(), 1, sourceCost);
   }

   @Override
   public boolean isMatch(List<ItemStack> pedestalItems, ItemStack reagent, EnchantingApparatusTile enchantingApparatusTile, @Nullable Player player) {
      ItemStack parchment = getParchment(pedestalItems);
      return super.isMatch(pedestalItems, reagent, enchantingApparatusTile, player)
         && !parchment.m_41619_()
         && !CasterUtil.getCaster(parchment).getSpell().isEmpty();
   }

   @NotNull
   public static ItemStack getParchment(List<ItemStack> pedestalItems) {
      for (ItemStack stack : pedestalItems) {
         if (stack.m_41720_() instanceof SpellParchment) {
            return stack;
         }
      }

      return ItemStack.f_41583_;
   }

   @Override
   public RecipeType<?> m_6671_() {
      return (RecipeType<?>)RecipeRegistry.REACTIVE_TYPE.get();
   }

   @Override
   public RecipeSerializer<?> m_7707_() {
      return (RecipeSerializer<?>)RecipeRegistry.REACTIVE_RECIPE.get();
   }

   @Override
   public ItemStack getResult(List<ItemStack> pedestalItems, ItemStack reagent, EnchantingApparatusTile enchantingApparatusTile) {
      ItemStack resultStack = super.getResult(pedestalItems, reagent, enchantingApparatusTile);
      ItemStack parchment = getParchment(pedestalItems);
      ISpellCaster parchmentCaster = CasterUtil.getCaster(parchment);
      ReactiveCaster reactiveCaster = new ReactiveCaster(resultStack);
      reactiveCaster.setColor(parchmentCaster.getColor());
      reactiveCaster.setSpell(parchmentCaster.getSpell());
      return resultStack;
   }

   @Override
   public JsonElement asRecipe() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("type", "ars_nouveau:reactive_enchantment");
      jsonobject.addProperty("sourceCost", this.getSourceCost());
      JsonArray pedestalArr = new JsonArray();

      for (Ingredient i : this.pedestalItems) {
         JsonObject object = new JsonObject();
         object.add("item", i.m_43942_());
         pedestalArr.add(object);
      }

      jsonobject.add("pedestalItems", pedestalArr);
      return jsonobject;
   }

   public static class Serializer implements RecipeSerializer<ReactiveEnchantmentRecipe> {
      public ReactiveEnchantmentRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
         int sourceCost = GsonHelper.m_13824_(json, "sourceCost", 0);
         JsonArray pedestalItems = GsonHelper.m_13933_(json, "pedestalItems");
         List<Ingredient> stacks = new ArrayList<>();

         for (JsonElement e : pedestalItems) {
            JsonObject obj = e.getAsJsonObject();
            Ingredient input;
            if (GsonHelper.m_13885_(obj, "item")) {
               input = Ingredient.m_43917_(GsonHelper.m_13933_(obj, "item"));
            } else {
               input = Ingredient.m_43917_(GsonHelper.m_13930_(obj, "item"));
            }

            stacks.add(input);
         }

         return new ReactiveEnchantmentRecipe(stacks, sourceCost);
      }

      @Nullable
      public ReactiveEnchantmentRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
         int length = buffer.readInt();
         int sourceCost = buffer.readInt();
         List<Ingredient> stacks = new ArrayList<>();

         for (int i = 0; i < length; i++) {
            try {
               stacks.add(Ingredient.m_43940_(buffer));
            } catch (Exception var8) {
               var8.printStackTrace();
               break;
            }
         }

         return new ReactiveEnchantmentRecipe(stacks, sourceCost);
      }

      public void toNetwork(FriendlyByteBuf buf, ReactiveEnchantmentRecipe recipe) {
         buf.writeInt(recipe.pedestalItems.size());
         buf.writeInt(recipe.getSourceCost());

         for (Ingredient i : recipe.pedestalItems) {
            i.m_43923_(buf);
         }
      }
   }
}
