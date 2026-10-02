package com.majruszsaccessories.integration;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface ISlotPlatform {
   boolean isInstalled();

   List<ItemStack> find(LivingEntity var1, Predicate<ItemStack> var2);
}
