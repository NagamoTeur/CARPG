package com.aizistral.enigmaticlegacy.crafting;

import com.aizistral.enigmaticlegacy.items.EnchantmentTransposer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

public class EnchantmentTransposingRecipe extends CustomRecipe {
   public static final SimpleRecipeSerializer<EnchantmentTransposingRecipe> SERIALIZER = new SimpleRecipeSerializer(EnchantmentTransposingRecipe::new);

   public EnchantmentTransposingRecipe(ResourceLocation id) {
      super(id);
   }

   public ItemStack assemble(CraftingContainer inv) {
      List<ItemStack> stackList = new ArrayList<>();
      ItemStack transposer = null;

      for (int i = 0; i < inv.m_6643_(); i++) {
         ItemStack checkedItemStack = inv.m_8020_(i);
         if (!checkedItemStack.m_41619_()) {
            if (checkedItemStack.m_41720_() instanceof EnchantmentTransposer) {
               if (transposer != null) {
                  return ItemStack.f_41583_;
               }

               transposer = checkedItemStack.m_41777_();
            } else {
               stackList.add(checkedItemStack);
            }
         }
      }

      return transposer != null && stackList.size() == 1 && stackList.get(0).m_41793_() && this.canDisenchant(transposer, stackList.get(0))
         ? (ItemStack)this.disenchant(transposer, stackList.get(0)).m_14418_()
         : ItemStack.f_41583_;
   }

   public boolean matches(CraftingContainer inv, Level world) {
      List<ItemStack> stackList = new ArrayList<>();
      ItemStack transposer = null;

      for (int i = 0; i < inv.m_6643_(); i++) {
         ItemStack checkedItemStack = inv.m_8020_(i);
         if (!checkedItemStack.m_41619_()) {
            if (checkedItemStack.m_41720_() instanceof EnchantmentTransposer) {
               if (transposer != null) {
                  return false;
               }

               transposer = checkedItemStack.m_41777_();
            } else {
               stackList.add(checkedItemStack);
            }
         }
      }

      return transposer != null && stackList.size() == 1 && stackList.get(0).m_41793_() && this.canDisenchant(transposer, stackList.get(0));
   }

   public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {
      NonNullList<ItemStack> remaining = NonNullList.m_122780_(inv.m_6643_(), ItemStack.f_41583_);
      Map<ItemStack, Integer> stackList = new HashMap<>();
      ItemStack transposer = null;

      for (int i = 0; i < inv.m_6643_(); i++) {
         ItemStack checkedItemStack = inv.m_8020_(i);
         if (!checkedItemStack.m_41619_()) {
            if (checkedItemStack.m_41720_() instanceof EnchantmentTransposer) {
               if (transposer != null) {
                  return remaining;
               }

               transposer = checkedItemStack.m_41777_();
            } else {
               stackList.put(checkedItemStack, i);
            }
         }
      }

      if (transposer != null && stackList.size() == 1) {
         ItemStack returned = stackList.keySet().iterator().next();
         if (returned.m_41793_() && this.canDisenchant(transposer, returned)) {
            remaining.set(stackList.get(returned), (ItemStack)this.disenchant(transposer, returned).m_14419_());
         }
      }

      return remaining;
   }

   private Tuple<ItemStack, ItemStack> disenchant(ItemStack transposer, ItemStack target) {
      Map<Enchantment, Integer> transposed = EnchantmentHelper.m_44831_(target);
      Map<Enchantment, Integer> leftover = EnchantmentHelper.m_44831_(target);
      transposed.keySet().removeIf(enchant -> !((EnchantmentTransposer)transposer.m_41720_()).canTranspose(enchant));
      leftover.keySet().removeIf(enchant -> ((EnchantmentTransposer)transposer.m_41720_()).canTranspose(enchant));
      ItemStack book = new ItemStack(Items.f_42690_);
      EnchantmentHelper.m_44865_(transposed, book);
      ItemStack item = target.m_41777_();
      EnchantmentHelper.m_44865_(leftover, item);
      return new Tuple(book, item);
   }

   private boolean canDisenchant(ItemStack transposer, ItemStack target) {
      return EnchantmentHelper.m_44831_(target).keySet().stream().anyMatch(((EnchantmentTransposer)transposer.m_41720_())::canTranspose);
   }

   public boolean m_8004_(int width, int height) {
      return width * height >= 2;
   }

   public RecipeSerializer<?> m_7707_() {
      return SERIALIZER;
   }
}
