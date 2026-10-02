package shadows.apotheosis.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public interface INBTSensitiveFallingBlock {
   ItemStack toStack(BlockState var1, CompoundTag var2);
}
