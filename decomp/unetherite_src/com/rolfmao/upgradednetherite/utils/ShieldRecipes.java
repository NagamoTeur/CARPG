package com.rolfmao.upgradednetherite.utils;

import com.rolfmao.upgradednetherite.content.UpgradedNetheriteShield;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShieldDecorationRecipe;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraft.world.level.Level;

public class ShieldRecipes extends ShieldDecorationRecipe {
   public static final SimpleRecipeSerializer<ShieldRecipes> SERIALIZER = new SimpleRecipeSerializer(ShieldRecipes::new);

   public ShieldRecipes(ResourceLocation idIn) {
      super(idIn);
   }

   public boolean m_5818_(CraftingContainer inv, Level worldIn) {
      ItemStack itemstack = ItemStack.f_41583_;
      ItemStack itemstack1 = ItemStack.f_41583_;

      for (int i = 0; i < inv.m_6643_(); i++) {
         ItemStack itemstack2 = inv.m_8020_(i);
         if (!itemstack2.m_41619_()) {
            if (itemstack2.m_41720_() instanceof BannerItem) {
               if (!itemstack1.m_41619_()) {
                  return false;
               }

               itemstack1 = itemstack2;
            } else {
               if (!(itemstack2.m_41720_() instanceof UpgradedNetheriteShield)) {
                  return false;
               }

               if (!itemstack.m_41619_()) {
                  return false;
               }

               if (itemstack2.m_41737_("BlockEntityTag") != null) {
                  return false;
               }

               itemstack = itemstack2;
            }
         }
      }

      return !itemstack.m_41619_() && !itemstack1.m_41619_();
   }

   public ItemStack m_5874_(CraftingContainer inv) {
      ItemStack itemstack = ItemStack.f_41583_;
      ItemStack itemstack1 = ItemStack.f_41583_;

      for (int i = 0; i < inv.m_6643_(); i++) {
         ItemStack itemstack2 = inv.m_8020_(i);
         if (!itemstack2.m_41619_()) {
            if (itemstack2.m_41720_() instanceof BannerItem) {
               itemstack = itemstack2;
            } else if (itemstack2.m_41720_() instanceof UpgradedNetheriteShield) {
               itemstack1 = itemstack2.m_41777_();
            }
         }
      }

      if (itemstack1.m_41619_()) {
         return itemstack1;
      } else {
         CompoundTag compoundnbt = itemstack.m_41737_("BlockEntityTag");
         CompoundTag compoundnbt1 = compoundnbt == null ? new CompoundTag() : compoundnbt.m_6426_();
         compoundnbt1.m_128405_("Base", ((BannerItem)itemstack.m_41720_()).m_40545_().m_41060_());
         itemstack1.m_41700_("BlockEntityTag", compoundnbt1);
         return itemstack1;
      }
   }

   public boolean m_8004_(int width, int height) {
      return width * height >= 2;
   }

   public RecipeSerializer<?> m_7707_() {
      return SERIALIZER;
   }
}
