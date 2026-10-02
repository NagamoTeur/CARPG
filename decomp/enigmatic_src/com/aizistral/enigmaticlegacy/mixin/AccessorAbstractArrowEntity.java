package com.aizistral.enigmaticlegacy.mixin;

import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({AbstractArrow.class})
public interface AccessorAbstractArrowEntity {
   @Invoker("resetPiercedEntities")
   void clearHitEntities();
}
