package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.IDamageEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDampen;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentFortune;
import java.util.Set;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.jetbrains.annotations.NotNull;

public class EffectHeal extends AbstractEffect implements IDamageEffect {
   public static EffectHeal INSTANCE = new EffectHeal();

   private EffectHeal() {
      super(GlyphLib.EffectHealID, "Heal");
   }

   @Override
   public boolean canDamage(LivingEntity shooter, SpellStats stats, SpellContext spellContext, SpellResolver resolver, @NotNull Entity entity) {
      if (entity instanceof LivingEntity living && living.m_21222_() && IDamageEffect.super.canDamage(shooter, stats, spellContext, resolver, entity)) {
         return true;
      }

      return false;
   }

   @Override
   public void onResolveEntity(
      EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      if (rayTraceResult.m_82443_() instanceof LivingEntity entity) {
         if (entity.m_213877_() || entity.m_21223_() <= 0.0F) {
            return;
         }

         float healVal = (float)((Double)this.GENERIC_DOUBLE.get() + (Double)this.AMP_VALUE.get() * spellStats.getAmpMultiplier());
         if (entity.m_21222_()) {
            this.attemptDamage(world, shooter, spellStats, spellContext, resolver, entity, this.buildDamageSource(world, shooter).m_19389_(), healVal);
         } else {
            if (entity instanceof Player player) {
               player.m_36399_(2.5F);
            }

            entity.m_5634_(healVal);
         }
      }
   }

   @Override
   public void buildConfig(Builder builder) {
      super.buildConfig(builder);
      this.addGenericDouble(builder, 3.0, "Base heal amount", "base_heal");
      this.addAmpConfig(builder, 3.0);
   }

   @Override
   public int getDefaultManaCost() {
      return 50;
   }

   @Override
   public SpellTier defaultTier() {
      return SpellTier.TWO;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(new AbstractAugment[]{AugmentAmplify.INSTANCE, AugmentDampen.INSTANCE, AugmentFortune.INSTANCE});
   }

   @Override
   public String getBookDescription() {
      return "Heals a small amount of health and consumes hunger from the caster. When used on Undead, the spell will deal an equal amount of magic damage.";
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.ABJURATION});
   }
}
