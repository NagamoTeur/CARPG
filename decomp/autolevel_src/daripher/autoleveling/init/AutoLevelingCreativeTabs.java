package daripher.autoleveling.init;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class AutoLevelingCreativeTabs {
   public static final CreativeModeTab AUTO_LEVELING = new CreativeModeTab("autoleveling") {
      @NotNull
      public ItemStack m_6976_() {
         return new ItemStack((ItemLike)AutoLevelingItems.BLACKLIST_TOOL.get());
      }
   };
}
