package com.hollingsworth.arsnouveau.api.enchanting_apparatus;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hollingsworth.arsnouveau.api.perk.ArmorPerkHolder;
import com.hollingsworth.arsnouveau.api.util.PerkUtil;
import com.hollingsworth.arsnouveau.common.block.tile.EnchantingApparatusTile;
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

public class ArmorUpgradeRecipe extends EnchantingApparatusRecipe implements ITextOutput {
   public int tier;

   public ArmorUpgradeRecipe(List<Ingredient> pedestalItems, int cost, int tier) {
      this(new ResourceLocation("ars_nouveau", "upgrade_" + tier), pedestalItems, cost, tier);
   }

   public ArmorUpgradeRecipe(ResourceLocation id, List<Ingredient> pedestalItems, int cost, int tier) {
      this.pedestalItems = pedestalItems;
      this.id = id;
      this.sourceCost = cost;
      this.tier = tier;
   }

   @Override
   public boolean excludeJei() {
      return true;
   }

   @Override
   public JsonElement asRecipe() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("type", "ars_nouveau:armor_upgrade");
      jsonobject.addProperty("sourceCost", this.getSourceCost());
      JsonArray pedestalArr = new JsonArray();

      for (Ingredient i : this.pedestalItems) {
         JsonObject object = new JsonObject();
         object.add("item", i.m_43942_());
         pedestalArr.add(object);
      }

      jsonobject.add("pedestalItems", pedestalArr);
      jsonobject.addProperty("tier", this.tier);
      return jsonobject;
   }

   @Override
   public boolean doesReagentMatch(ItemStack reag) {
      return true;
   }

   @Override
   public boolean isMatch(List<ItemStack> pedestalItems, ItemStack reagent, EnchantingApparatusTile enchantingApparatusTile, @Nullable Player player) {
      return !(PerkUtil.getPerkHolder(reagent) instanceof ArmorPerkHolder armorPerkHolder)
         ? false
         : armorPerkHolder.getTier() == this.tier - 1 && super.isMatch(pedestalItems, reagent, enchantingApparatusTile, player);
   }

   @Override
   public ItemStack getResult(List<ItemStack> pedestalItems, ItemStack reagent, EnchantingApparatusTile enchantingApparatusTile) {
      if (PerkUtil.getPerkHolder(reagent) instanceof ArmorPerkHolder armorPerkHolder) {
         armorPerkHolder.setTier(this.tier);
         return reagent.m_41777_();
      } else {
         return reagent.m_41777_();
      }
   }

   @Override
   public RecipeType<?> m_6671_() {
      return (RecipeType<?>)RecipeRegistry.ARMOR_UPGRADE_TYPE.get();
   }

   @Override
   public Component getOutputComponent() {
      return Component.m_237110_("ars_nouveau.armor_upgrade.book_desc", new Object[]{this.tier});
   }

   @Override
   public RecipeSerializer<?> m_7707_() {
      return (RecipeSerializer<?>)RecipeRegistry.ARMOR_SERIALIZER.get();
   }

   public static class Serializer implements RecipeSerializer<ArmorUpgradeRecipe> {
      public ArmorUpgradeRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
         int cost = json.has("sourceCost") ? GsonHelper.m_13927_(json, "sourceCost") : 0;
         int tier = json.has("tier") ? GsonHelper.m_13927_(json, "tier") : 0;
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

         return new ArmorUpgradeRecipe(recipeId, stacks, cost, tier);
      }

      @Nullable
      public ArmorUpgradeRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
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
         int tier = buffer.readInt();
         return new ArmorUpgradeRecipe(recipeId, stacks, cost, tier);
      }

      public void toNetwork(FriendlyByteBuf buf, ArmorUpgradeRecipe recipe) {
         buf.writeInt(recipe.pedestalItems.size());

         for (Ingredient i : recipe.pedestalItems) {
            i.m_43923_(buf);
         }

         buf.writeInt(recipe.sourceCost);
         buf.writeInt(recipe.tier);
      }
   }
}
