package com.hollingsworth.arsnouveau.api.spell;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@Deprecated(
   forRemoval = true
)
public interface IPlaceBlockResponder extends IInventoryResponder {
   @NotNull
   ItemStack onPlaceBlock();
}
