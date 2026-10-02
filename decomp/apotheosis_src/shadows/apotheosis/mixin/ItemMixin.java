package shadows.apotheosis.mixin;

import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.ench.table.IEnchantableItem;

@Mixin({Item.class})
public class ItemMixin implements IEnchantableItem {
   @Overwrite
   public int m_6473_() {
      return Apotheosis.enableEnch ? 1 : 0;
   }
}
