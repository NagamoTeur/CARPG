package daripher.skilltree.init;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class PSTCreativeTabs {
   public static final CreativeModeTab SKILLTREE = new CreativeModeTab("skilltree") {
      @NotNull
      public ItemStack m_6976_() {
         return new ItemStack((ItemLike)PSTItems.AMNESIA_SCROLL.get());
      }
   };
}
