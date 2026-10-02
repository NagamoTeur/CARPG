package shadows.apotheosis.adventure.affix.socket;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IExtUpgradeRecipe {
   void onCraft(Container var1, Player var2, ItemStack var3);
}
