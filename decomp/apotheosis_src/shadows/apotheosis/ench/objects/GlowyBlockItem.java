package shadows.apotheosis.ench.objects;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;

public class GlowyBlockItem extends BlockItem {
   public GlowyBlockItem(Block pBlock, Properties pProperties) {
      super(pBlock, pProperties);
   }

   public boolean m_5812_(ItemStack pStack) {
      return true;
   }

   public static class GlowyItem extends Item {
      public GlowyItem(Properties pProperties) {
         super(pProperties);
      }

      public boolean m_5812_(ItemStack pStack) {
         return true;
      }
   }
}
