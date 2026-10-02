package com.hollingsworth.arsnouveau.api.event;

import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.eventbus.api.Event;

public class SpellModifierEvent extends Event {
   public SpellStats.Builder builder;
   @Nullable
   public LivingEntity caster;
   public AbstractSpellPart spellPart;
   public HitResult rayTraceResult;
   public Level world;
   public SpellContext spellContext;

   public SpellModifierEvent(
      LivingEntity caster, SpellStats.Builder builder, AbstractSpellPart spellPart, HitResult rayTraceResult, Level world, SpellContext spellContext
   ) {
      this.caster = caster;
      this.builder = builder;
      this.spellPart = spellPart;
      this.rayTraceResult = rayTraceResult;
      this.world = world;
      this.spellContext = spellContext;
   }
}
