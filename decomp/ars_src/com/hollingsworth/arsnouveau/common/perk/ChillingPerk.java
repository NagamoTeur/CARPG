package com.hollingsworth.arsnouveau.common.perk;

import com.hollingsworth.arsnouveau.api.perk.IEffectResolvePerk;
import com.hollingsworth.arsnouveau.api.perk.Perk;
import com.hollingsworth.arsnouveau.api.perk.PerkInstance;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.IDamageEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.common.potions.ModPotions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class ChillingPerk extends Perk implements IEffectResolvePerk {
   public static ChillingPerk INSTANCE = new ChillingPerk(new ResourceLocation("ars_nouveau", "thread_chilling"));

   public ChillingPerk(ResourceLocation key) {
      super(key);
   }

   @Override
   public String getLangDescription() {
      return "Damaging effects inflict Freezing on the target before the spell resolves. Freezing lasts for 10 seconds per level, and becomes Freezing 2 at a level 3 slot.";
   }

   @Override
   public String getLangName() {
      return "Chilling";
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
         && entityHitResult.m_82443_() instanceof LivingEntity livingEntity
         && damageEffect.canDamage(shooter, spellStats, spellContext, resolver, entityHitResult.m_82443_())
         && shooter != entityHitResult.m_82443_()) {
         livingEntity.m_146917_(livingEntity.m_146888_() + 1);
         livingEntity.m_7292_(
            new MobEffectInstance(
               (MobEffect)ModPotions.FREEZING_EFFECT.get(), perkInstance.getSlot().value * 10 * 20, perkInstance.getSlot().value >= 3 ? 2 : 1
            )
         );
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
