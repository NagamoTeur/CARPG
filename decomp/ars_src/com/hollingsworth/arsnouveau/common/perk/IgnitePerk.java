package com.hollingsworth.arsnouveau.common.perk;

import com.hollingsworth.arsnouveau.api.perk.IEffectResolvePerk;
import com.hollingsworth.arsnouveau.api.perk.Perk;
import com.hollingsworth.arsnouveau.api.perk.PerkInstance;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.IDamageEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class IgnitePerk extends Perk implements IEffectResolvePerk {
   public static IgnitePerk INSTANCE = new IgnitePerk(new ResourceLocation("ars_nouveau", "thread_kindling"));

   public IgnitePerk(ResourceLocation key) {
      super(key);
   }

   @Override
   public String getLangDescription() {
      return "Damaging effects cause the target to burn for a short duration before the effect resolves. Burn time is increased by 5 seconds per level.";
   }

   @Override
   public String getLangName() {
      return "Kindling";
   }

   @Override
   public void onPreResolve(
      HitResult rayTraceResult,
      Level world,
      @NotNull LivingEntity shooter,
      SpellStats spellStats,
      SpellContext spellContext,
      SpellResolver resolver,
      AbstractEffect effect,
      PerkInstance perkInstance
   ) {
      if (effect instanceof IDamageEffect damageEffect
         && rayTraceResult instanceof EntityHitResult entityHitResult
         && damageEffect.canDamage(shooter, spellStats, spellContext, resolver, entityHitResult.m_82443_())
         && shooter != entityHitResult.m_82443_()) {
         entityHitResult.m_82443_().m_20254_(100 * perkInstance.getSlot().value);
      }
   }

   @Override
   public void onPostResolve(
      HitResult rayTraceResult,
      Level world,
      @NotNull LivingEntity shooter,
      SpellStats spellStats,
      SpellContext spellContext,
      SpellResolver resolver,
      AbstractEffect effect,
      PerkInstance perkInstance
   ) {
   }
}
