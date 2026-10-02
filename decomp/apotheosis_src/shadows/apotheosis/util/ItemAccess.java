package shadows.apotheosis.util;

import java.util.UUID;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;

public final class ItemAccess extends Item {
   private ItemAccess(Properties pProperties) {
      super(pProperties);
   }

   public static UUID getBaseAD() {
      return Item.f_41374_;
   }

   public static UUID getBaseAS() {
      return Item.f_41375_;
   }
}
