package com.hollingsworth.arsnouveau.api.spell;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@Deprecated
public interface IPickupResponder extends IInventoryResponder {
   @Deprecated
   @NotNull
   ItemStack onPickup(ItemStack var1);
}
