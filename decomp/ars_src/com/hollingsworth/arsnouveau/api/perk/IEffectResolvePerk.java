package com.hollingsworth.arsnouveau.api.perk;

import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public interface IEffectResolvePerk {
   void onPreResolve(
      HitResult var1, Level var2, @NotNull LivingEntity var3, SpellStats var4, SpellContext var5, SpellResolver var6, AbstractEffect var7, PerkInstance var8
   );

   void onPostResolve(
      HitResult var1, Level var2, @NotNull LivingEntity var3, SpellStats var4, SpellContext var5, SpellResolver var6, AbstractEffect var7, PerkInstance var8
   );
}
