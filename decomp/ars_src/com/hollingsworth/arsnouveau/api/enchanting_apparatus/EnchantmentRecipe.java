package com.hollingsworth.arsnouveau.api.enchanting_apparatus;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hollingsworth.arsnouveau.api.RegistryHelper;
import com.hollingsworth.arsnouveau.common.block.tile.EnchantingApparatusTile;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.RecipeRegistry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class EnchantmentRecipe extends EnchantingApparatusRecipe {
   public Enchantment enchantment;
   public int enchantLevel;

   public EnchantmentRecipe(List<Ingredient> pedestalItems, Enchantment enchantment, int level, int manaCost) {
      this.pedestalItems = pedestalItems;
      this.enchantment = enchantment;
      this.enchantLevel = level;
      this.sourceCost = manaCost;
      this.id = new ResourceLocation("ars_nouveau", RegistryHelper.getRegistryName(enchantment).m_135815_() + "_" + level);
   }

   @Override
   public RecipeType<?> m_6671_() {
      return (RecipeType<?>)RecipeRegistry.ENCHANTMENT_TYPE.get();
   }

   public boolean doesReagentMatch(ItemStack stack, Player playerEntity) {
      if (stack.m_41619_()) {
         return false;
      } else {
         Map<Enchantment, Integer> enchantments = EnchantmentHelper.m_44831_(stack);
         int level = enchantments.getOrDefault(this.enchantment, 0);
         Collection<Enchantment> enchantList = EnchantmentHelper.m_44831_(stack).keySet();
         enchantList.remove(this.enchantment);
         if (stack.m_41720_() != Items.f_42517_ && stack.m_41720_() != Items.f_42690_ && !this.enchantment.m_6081_(stack)) {
            PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.enchanting.incompatible"));
            return false;
         } else if (!EnchantmentHelper.m_44859_(enchantList, this.enchantment)) {
            PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.enchanting.incompatible"));
            return false;
         } else if (this.enchantLevel - level != 1) {
            PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.enchanting.bad_level"));
            return false;
         } else {
            return true;
         }
      }
   }

   @Override
   public boolean excludeJei() {
      return true;
   }

   @Override
   public boolean isMatch(List<ItemStack> pedestalItems, ItemStack reagent, EnchantingApparatusTile enchantingApparatusTile, @Nullable Player player) {
      pedestalItems = pedestalItems.stream().filter(itemStack -> !itemStack.m_41619_()).collect(Collectors.toList());
      return this.pedestalItems.size() == pedestalItems.size() && doItemsMatch(pedestalItems, this.pedestalItems) && this.doesReagentMatch(reagent, player);
   }

   @Override
   public boolean doesReagentMatch(ItemStack stack) {
      Map<Enchantment, Integer> enchantments = EnchantmentHelper.m_44831_(stack);
      int level = enchantments.getOrDefault(this.enchantment, 0);
      return this.enchantment.m_6081_(stack)
         && this.enchantLevel - level == 1
         && EnchantmentHelper.m_44859_(EnchantmentHelper.m_44831_(stack).keySet(), this.enchantment);
   }

   @Override
   public ItemStack assemble(EnchantingApparatusTile inv) {
      ItemStack stack = inv.getStack().m_41720_() == Items.f_42517_ ? new ItemStack(Items.f_42690_) : inv.getStack().m_41777_();
      Map<Enchantment, Integer> enchantments = EnchantmentHelper.m_44831_(stack);
      enchantments.put(this.enchantment, this.enchantLevel);
      EnchantmentHelper.m_44865_(enchantments, stack);
      return stack;
   }

   @Override
   public ItemStack getResult(List<ItemStack> pedestalItems, ItemStack reagent, EnchantingApparatusTile tile) {
      return this.assemble(tile);
   }

   @Override
   public RecipeSerializer<?> m_7707_() {
      return (RecipeSerializer<?>)RecipeRegistry.ENCHANTMENT_SERIALIZER.get();
   }

   @Override
   public JsonElement asRecipe() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("type", "ars_nouveau:enchantment");
      jsonobject.addProperty("enchantment", RegistryHelper.getRegistryName(this.enchantment).toString());
      jsonobject.addProperty("level", this.enchantLevel);
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

   public static class Serializer implements RecipeSerializer<EnchantmentRecipe> {
      public EnchantmentRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
         Enchantment enchantment = (Enchantment)ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(GsonHelper.m_13906_(json, "enchantment")));
         int level = GsonHelper.m_13824_(json, "level", 1);
         int manaCost = GsonHelper.m_13824_(json, "sourceCost", 0);
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

         return new EnchantmentRecipe(stacks, enchantment, level, manaCost);
      }

      @Nullable
      public EnchantmentRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
         int length = buffer.readInt();
         String enchantID = buffer.m_130277_();
         int level = buffer.readInt();
         int manaCost = buffer.readInt();
         List<Ingredient> stacks = new ArrayList<>();

         for (int i = 0; i < length; i++) {
            try {
               stacks.add(Ingredient.m_43940_(buffer));
            } catch (Exception var10) {
               var10.printStackTrace();
               break;
            }
         }

         return new EnchantmentRecipe(stacks, (Enchantment)ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(enchantID)), level, manaCost);
      }

      public void toNetwork(FriendlyByteBuf buf, EnchantmentRecipe recipe) {
         buf.writeInt(recipe.pedestalItems.size());
         buf.m_130070_(RegistryHelper.getRegistryName(recipe.enchantment).toString());
         buf.writeInt(recipe.enchantLevel);
         buf.writeInt(recipe.getSourceCost());

         for (Ingredient i : recipe.pedestalItems) {
            i.m_43923_(buf);
         }
      }
   }
}
