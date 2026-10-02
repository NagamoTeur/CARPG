package com.hollingsworth.arsnouveau.api.entity;

import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public interface IDispellable {
   boolean onDispel(@NotNull LivingEntity var1);
}
