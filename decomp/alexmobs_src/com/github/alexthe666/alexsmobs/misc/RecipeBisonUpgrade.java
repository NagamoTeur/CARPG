package com.github.alexthe666.alexsmobs.misc;

import com.github.alexthe666.alexsmobs.block.AMBlockRegistry;
import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.UpgradeRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class RecipeBisonUpgrade extends UpgradeRecipe {
   public RecipeBisonUpgrade(ResourceLocation id) {
      super(id, Ingredient.f_43901_, Ingredient.f_43901_, ItemStack.f_41583_);
   }

   public boolean m_5818_(Container container, Level lvl) {
      return !this.createBoots(container).m_41619_();
   }

   public ItemStack m_5874_(Container container) {
      return this.createBoots(container);
   }

   private ItemStack createBoots(Container container) {
      ItemStack boots = ItemStack.f_41583_;
      if (container.m_8020_(1).m_150930_(((Block)AMBlockRegistry.BISON_FUR_BLOCK.get()).m_5456_())) {
         for (int j = 0; j < container.m_6643_(); j++) {
            ItemStack itemstack1 = container.m_8020_(j);
            boolean notFurred = !itemstack1.m_41782_() || itemstack1.m_41783_() != null && !itemstack1.m_41783_().m_128471_("BisonFur");
            if (!itemstack1.m_41619_() && notFurred && LivingEntity.m_147233_(itemstack1) == EquipmentSlot.FEET) {
               boots = itemstack1;
            }
         }
      }

      if (!boots.m_41619_()) {
         ItemStack stack = boots.m_41777_();
         CompoundTag tag = stack.m_41784_();
         tag.m_128379_("BisonFur", true);
         stack.m_41751_(tag);
         return stack;
      } else {
         return ItemStack.f_41583_;
      }
   }

   public boolean m_8004_(int x, int y) {
      return x * y >= 2;
   }

   public ItemStack m_8043_() {
      return ItemStack.f_41583_;
   }

   public ItemStack m_8042_() {
      return new ItemStack(Blocks.f_50625_);
   }

   public RecipeSerializer<?> m_7707_() {
      return (RecipeSerializer<?>)AMRecipeRegistry.BISON_UPGRADE.get();
   }

   public RecipeType<?> m_6671_() {
      return RecipeType.f_44113_;
   }

   public NonNullList<Ingredient> m_7527_() {
      return NonNullList.m_122783_(Ingredient.m_43929_(new ItemLike[]{(ItemLike)AMItemRegistry.BISON_FUR.get()}), new Ingredient[0]);
   }
}
