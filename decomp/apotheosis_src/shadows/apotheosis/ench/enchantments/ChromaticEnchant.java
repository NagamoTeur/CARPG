package shadows.apotheosis.ench.enchantments;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import shadows.apotheosis.ench.EnchModule;

public class ChromaticEnchant extends Enchantment {
   private static final Map<DyeColor, ItemLike> ITEM_BY_DYE = (Map<DyeColor, ItemLike>)Util.m_137469_(Maps.newEnumMap(DyeColor.class), map -> {
      map.put(DyeColor.WHITE, Blocks.f_50041_);
      map.put(DyeColor.ORANGE, Blocks.f_50042_);
      map.put(DyeColor.MAGENTA, Blocks.f_50096_);
      map.put(DyeColor.LIGHT_BLUE, Blocks.f_50097_);
      map.put(DyeColor.YELLOW, Blocks.f_50098_);
      map.put(DyeColor.LIME, Blocks.f_50099_);
      map.put(DyeColor.PINK, Blocks.f_50100_);
      map.put(DyeColor.GRAY, Blocks.f_50101_);
      map.put(DyeColor.LIGHT_GRAY, Blocks.f_50102_);
      map.put(DyeColor.CYAN, Blocks.f_50103_);
      map.put(DyeColor.PURPLE, Blocks.f_50104_);
      map.put(DyeColor.BLUE, Blocks.f_50105_);
      map.put(DyeColor.BROWN, Blocks.f_50106_);
      map.put(DyeColor.GREEN, Blocks.f_50107_);
      map.put(DyeColor.RED, Blocks.f_50108_);
      map.put(DyeColor.BLACK, Blocks.f_50109_);
   });

   public ChromaticEnchant() {
      super(Rarity.UNCOMMON, EnchModule.SHEARS, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
   }

   public int m_6183_(int pLevel) {
      return 40;
   }

   public List<ItemStack> molestSheepItems(Sheep sheep, ItemStack shears, List<ItemStack> items) {
      if (shears.getEnchantmentLevel(this) > 0) {
         for (int i = 0; i < items.size(); i++) {
            if (items.get(i).m_204117_(ItemTags.f_13167_)) {
               items.set(i, new ItemStack(ITEM_BY_DYE.get(DyeColor.m_41053_(sheep.f_19796_.m_188503_(16)))));
            }
         }
      }

      return items;
   }
}
