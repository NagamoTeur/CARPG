package com.hollingsworth.arsnouveau.api.item;

import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public interface ISpellModifierItem extends ISpellModifier {
   default SpellStats.Builder applyItemModifiers(
      ItemStack stack,
      SpellStats.Builder builder,
      AbstractSpellPart spellPart,
      HitResult rayTraceResult,
      Level world,
      @Nullable LivingEntity shooter,
      SpellContext spellContext
   ) {
      return this.applyModifiers(builder, spellPart, rayTraceResult, world, shooter, spellContext);
   }
}
