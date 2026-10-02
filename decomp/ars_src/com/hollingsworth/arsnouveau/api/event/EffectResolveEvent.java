package com.hollingsworth.arsnouveau.api.event;

import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.eventbus.api.Event;

public class EffectResolveEvent extends Event {
   public Level world;
   @Nullable
   public LivingEntity shooter;
   public HitResult rayTraceResult;
   public Spell spell;
   public SpellContext context;
   public AbstractEffect resolveEffect;
   public SpellStats spellStats;
   public SpellResolver resolver;

   @Deprecated(
      forRemoval = false
   )
   public EffectResolveEvent(
      Level world,
      LivingEntity shooter,
      HitResult result,
      Spell spell,
      SpellContext spellContext,
      AbstractEffect resolveEffect,
      SpellStats spellStats,
      SpellResolver spellResolver
   ) {
      this.world = world;
      this.shooter = shooter;
      this.rayTraceResult = result;
      this.spell = spell;
      this.context = spellContext;
      this.resolveEffect = resolveEffect;
      this.spellStats = spellStats;
      this.resolver = spellResolver;
   }

   public static class Post extends EffectResolveEvent {
      public Post(
         Level world,
         LivingEntity shooter,
         HitResult result,
         Spell spell,
         SpellContext spellContext,
         AbstractEffect resolveEffect,
         SpellStats spellStats,
         SpellResolver spellResolver
      ) {
         super(world, shooter, result, spell, spellContext, resolveEffect, spellStats, spellResolver);
      }

      public boolean isCancelable() {
         return false;
      }
   }

   public static class Pre extends EffectResolveEvent {
      public Pre(
         Level world,
         LivingEntity shooter,
         HitResult result,
         Spell spell,
         SpellContext spellContext,
         AbstractEffect resolveEffect,
         SpellStats spellStats,
         SpellResolver spellResolver
      ) {
         super(world, shooter, result, spell, spellContext, resolveEffect, spellStats, spellResolver);
      }

      public boolean isCancelable() {
         return true;
      }
   }
}
