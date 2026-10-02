package shadows.apotheosis.potion;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import shadows.apotheosis.Apotheosis;

public class LuckyFootItem extends Item {
   public LuckyFootItem() {
      super(new Properties().m_41487_(1).m_41491_(Apotheosis.APOTH_GROUP));
   }

   public boolean m_5812_(ItemStack stack) {
      return true;
   }
}
