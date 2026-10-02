package com.hollingsworth.arsnouveau.api.enchanting_apparatus;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hollingsworth.arsnouveau.api.spell.ISpellCaster;
import com.hollingsworth.arsnouveau.api.util.CasterUtil;
import com.hollingsworth.arsnouveau.common.block.tile.EnchantingApparatusTile;
import com.hollingsworth.arsnouveau.common.enchantment.EnchantmentRegistry;
import com.hollingsworth.arsnouveau.common.spell.casters.ReactiveCaster;
import com.hollingsworth.arsnouveau.setup.RecipeRegistry;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class SpellWriteRecipe extends EnchantingApparatusRecipe implements ITextOutput {
   public SpellWriteRecipe(ResourceLocation id, List<Ingredient> pedestalItems, int cost) {
      this.pedestalItems = pedestalItems;
      this.id = id;
      this.sourceCost = cost;
   }

   public SpellWriteRecipe(List<Ingredient> pedestalItems) {
      this.pedestalItems = pedestalItems;
      this.id = new ResourceLocation("ars_nouveau", "spell_write");
   }

   @Override
   public boolean excludeJei() {
      return true;
   }

   @Override
   public boolean isMatch(List<ItemStack> pedestalItems, ItemStack reagent, EnchantingApparatusTile enchantingApparatusTile, @Nullable Player player) {
      int level = EnchantmentHelper.m_44831_(reagent).getOrDefault(EnchantmentRegistry.REACTIVE_ENCHANTMENT.get(), 0);
      ItemStack parchment = ReactiveEnchantmentRecipe.getParchment(pedestalItems);
      return !parchment.m_41619_()
         && !CasterUtil.getCaster(parchment).getSpell().isEmpty()
         && level > 0
         && super.isMatch(pedestalItems, reagent, enchantingApparatusTile, player);
   }

   @Override
   public boolean doesReagentMatch(ItemStack reag) {
      return true;
   }

   @Override
   public ItemStack getResult(List<ItemStack> pedestalItems, ItemStack reagent, EnchantingApparatusTile enchantingApparatusTile) {
      ItemStack parchment = ReactiveEnchantmentRecipe.getParchment(pedestalItems);
      ISpellCaster caster = CasterUtil.getCaster(parchment);
      ReactiveCaster reactiveCaster = new ReactiveCaster(reagent);
      reactiveCaster.setSpell(caster.getSpell());
      reactiveCaster.setColor(caster.getColor());
      return reagent.m_41777_();
   }

   @Override
   public JsonElement asRecipe() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("type", "ars_nouveau:spell_write");
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

   @Override
   public RecipeType<?> m_6671_() {
      return (RecipeType<?>)RecipeRegistry.SPELL_WRITE_TYPE.get();
   }

   @Override
   public Component getOutputComponent() {
      return Component.m_237115_("ars_nouveau.spell_write.book_desc");
   }

   @Override
   public RecipeSerializer<?> m_7707_() {
      return (RecipeSerializer<?>)RecipeRegistry.SPELL_WRITE_RECIPE.get();
   }

   public static class Serializer implements RecipeSerializer<SpellWriteRecipe> {
      public SpellWriteRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
         int cost = json.has("sourceCost") ? GsonHelper.m_13927_(json, "sourceCost") : 0;
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

         return new SpellWriteRecipe(recipeId, stacks, cost);
      }

      @Nullable
      public SpellWriteRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
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

         int cost = buffer.readInt();
         return new SpellWriteRecipe(recipeId, stacks, cost);
      }

      public void toNetwork(FriendlyByteBuf buf, SpellWriteRecipe recipe) {
         buf.writeInt(recipe.pedestalItems.size());

         for (Ingredient i : recipe.pedestalItems) {
            i.m_43923_(buf);
         }

         buf.writeInt(recipe.sourceCost);
      }
   }
}
