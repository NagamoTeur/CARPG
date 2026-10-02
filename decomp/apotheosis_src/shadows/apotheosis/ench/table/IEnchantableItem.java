package shadows.apotheosis.ench.table;

import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

public interface IEnchantableItem {
   default ItemStack onEnchantment(ItemStack stack, List<EnchantmentInstance> enchantments) {
      boolean isBook = stack.m_150930_(Items.f_42517_);
      if (isBook) {
         ItemStack enchBook = new ItemStack(Items.f_42690_);
         CompoundTag tag = stack.m_41783_();
         if (tag != null) {
            stack.m_41751_(tag.m_6426_());
         }

         stack = enchBook;
      }

      for (EnchantmentInstance inst : enchantments) {
         if (isBook) {
            EnchantedBookItem.m_41153_(stack, inst);
         } else {
            stack.m_41663_(inst.f_44947_, inst.f_44948_);
         }
      }

      return stack;
   }

   default List<EnchantmentInstance> selectEnchantments(
      List<EnchantmentInstance> builtList, RandomSource rand, ItemStack stack, int level, float quanta, float arcana, boolean treasure
   ) {
      return builtList;
   }

   default boolean forciblyAllowsTableEnchantment(ItemStack stack, Enchantment enchantment) {
      return stack.m_150930_(Items.f_42517_) && enchantment.isAllowedOnBooks();
   }

   default boolean isTreasureAllowed(ItemStack stack, boolean wasTreasureAllowed) {
      return wasTreasureAllowed;
   }
}
