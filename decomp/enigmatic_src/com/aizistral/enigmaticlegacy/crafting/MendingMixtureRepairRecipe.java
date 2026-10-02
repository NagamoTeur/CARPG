package com.aizistral.enigmaticlegacy.crafting;

import com.aizistral.enigmaticlegacy.registries.EnigmaticItems;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraft.world.level.Level;

public class MendingMixtureRepairRecipe extends CustomRecipe {
   public static final SimpleRecipeSerializer<MendingMixtureRepairRecipe> SERIALIZER = new SimpleRecipeSerializer(MendingMixtureRepairRecipe::new);

   public MendingMixtureRepairRecipe(ResourceLocation id) {
      super(id);
   }

   public ItemStack assemble(CraftingContainer inv) {
      List<ItemStack> stackList = new ArrayList<>();

      for (int i = 0; i < inv.m_6643_(); i++) {
         ItemStack slotStack = inv.m_8020_(i);
         if (!slotStack.m_41619_()) {
            stackList.add(slotStack);
         }
      }

      if (stackList.size() == 2
         && (stackList.get(0).m_41763_() || stackList.get(1).m_41763_())
         && (stackList.get(0).m_41720_() == EnigmaticItems.MENDING_MIXTURE || stackList.get(1).m_41720_() == EnigmaticItems.MENDING_MIXTURE)) {
         ItemStack tool = stackList.get(0).m_41763_() ? stackList.get(0).m_41777_() : stackList.get(1).m_41777_();
         tool.m_41721_(0);
         return tool;
      } else {
         return ItemStack.f_41583_;
      }
   }

   public boolean matches(CraftingContainer inv, Level world) {
      List<ItemStack> stackList = new ArrayList<>();

      for (int i = 0; i < inv.m_6643_(); i++) {
         ItemStack slotStack = inv.m_8020_(i);
         if (!slotStack.m_41619_()) {
            stackList.add(slotStack);
         }
      }

      return stackList.size() == 2
         && (stackList.get(0).m_41763_() || stackList.get(1).m_41763_())
         && (stackList.get(0).m_41720_() == EnigmaticItems.MENDING_MIXTURE || stackList.get(1).m_41720_() == EnigmaticItems.MENDING_MIXTURE);
   }

   public boolean m_8004_(int width, int height) {
      return width * height >= 2;
   }

   public RecipeSerializer<?> m_7707_() {
      return SERIALIZER;
   }
}
