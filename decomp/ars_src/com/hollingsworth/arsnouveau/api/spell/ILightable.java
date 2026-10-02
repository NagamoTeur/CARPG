package com.hollingsworth.arsnouveau.api.spell;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public interface ILightable {
   void onLight(HitResult var1, Level var2, LivingEntity var3, SpellStats var4, SpellContext var5);
}
