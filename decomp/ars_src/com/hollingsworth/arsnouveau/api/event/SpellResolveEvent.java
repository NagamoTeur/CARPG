package com.hollingsworth.arsnouveau.api.event;

import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.eventbus.api.Event;

public class SpellResolveEvent extends Event {
   public Level world;
   @Nullable
   public LivingEntity shooter;
   public HitResult rayTraceResult;
   public Spell spell;
   public SpellContext context;
   public SpellResolver resolver;

   @Deprecated(
      forRemoval = true
   )
   public SpellResolveEvent(Level world, LivingEntity shooter, HitResult result, Spell spell, SpellContext spellContext, SpellResolver resolver) {
      this.world = world;
      this.shooter = shooter;
      this.rayTraceResult = result;
      this.spell = spell;
      this.context = spellContext;
      this.resolver = resolver;
   }

   public static class Post extends SpellResolveEvent {
      public Post(Level world, LivingEntity shooter, HitResult result, Spell spell, SpellContext spellContext, SpellResolver resolver) {
         super(world, shooter, result, spell, spellContext, resolver);
      }

      public boolean isCancelable() {
         return false;
      }
   }

   public static class Pre extends SpellResolveEvent {
      public Pre(Level world, LivingEntity shooter, HitResult result, Spell spell, SpellContext spellContext, SpellResolver resolver) {
         super(world, shooter, result, spell, spellContext, resolver);
      }

      public boolean isCancelable() {
         return true;
      }
   }
}
