package com.github.alexthe666.alexsmobs.misc;

import com.github.alexthe666.alexsmobs.effect.AMEffectRegistry;
import com.github.alexthe666.alexsmobs.enchantment.AMEnchantmentRegistry;
import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import java.lang.reflect.Field;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AMItemGroup extends CreativeModeTab {
   public static final AMItemGroup INSTANCE = new AMItemGroup();

   private AMItemGroup() {
      super("alexsmobs");
   }

   public ItemStack m_6976_() {
      return new ItemStack((ItemLike)AMItemRegistry.TAB_ICON.get());
   }

   @OnlyIn(Dist.CLIENT)
   public void m_6151_(NonNullList<ItemStack> items) {
      super.m_6151_(items);

      try {
         for (Field f : AMEffectRegistry.class.getDeclaredFields()) {
            Object obj = f.get(null);
            if (obj instanceof Potion) {
               ItemStack potionStack = AMEffectRegistry.createPotion((Potion)obj);
               items.add(potionStack);
            }
         }
      } catch (IllegalAccessException var9) {
         throw new RuntimeException(var9);
      }

      try {
         for (Field fx : AMEnchantmentRegistry.class.getDeclaredFields()) {
            Object obj = fx.get(null);
            if (obj instanceof Enchantment) {
               Enchantment enchant = (Enchantment)obj;
               if (enchant.isAllowedOnBooks()) {
                  items.add(EnchantedBookItem.m_41161_(new EnchantmentInstance(enchant, enchant.m_6586_())));
               }
            }
         }
      } catch (IllegalAccessException var8) {
         throw new RuntimeException(var8);
      }
   }
}
