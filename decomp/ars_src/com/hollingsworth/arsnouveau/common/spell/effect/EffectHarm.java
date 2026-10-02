package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.IDamageEffect;
import com.hollingsworth.arsnouveau.api.spell.IPotionEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDampen;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDurationDown;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentExtendTime;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentFortune;
import java.util.Map;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.jetbrains.annotations.NotNull;

public class EffectHarm extends AbstractEffect implements IDamageEffect, IPotionEffect {
   public static EffectHarm INSTANCE = new EffectHarm();

   private EffectHarm() {
      super(GlyphLib.EffectHarmID, "Harm");
   }

   @Override
   public void onResolveEntity(
      EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      if (!(rayTraceResult.m_82443_() instanceof ItemEntity)) {
         double damage = (Double)this.DAMAGE.get() + (Double)this.AMP_VALUE.get() * spellStats.getAmpMultiplier();
         int time = (int)spellStats.getDurationMultiplier();
         if (time > 0 && rayTraceResult.m_82443_() instanceof LivingEntity entity) {
            this.applyConfigPotion(entity, MobEffects.f_19614_, spellStats);
         } else {
            this.attemptDamage(
               world,
               shooter,
               spellStats,
               spellContext,
               resolver,
               rayTraceResult.m_82443_(),
               DamageSource.m_19344_(this.getPlayer(shooter, (ServerLevel)world)),
               (float)damage
            );
         }
      }
   }

   @Override
   public void buildConfig(Builder builder) {
      super.buildConfig(builder);
      this.addDamageConfig(builder, 5.0);
      this.addAmpConfig(builder, 2.0);
      this.addPotionConfig(builder, 5);
      this.addExtendTimeConfig(builder, 5);
   }

   @Override
   public boolean defaultedStarterGlyph() {
      return true;
   }

   @Override
   public int getDefaultManaCost() {
      return 15;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(
         new AbstractAugment[]{
            AugmentAmplify.INSTANCE, AugmentDampen.INSTANCE, AugmentExtendTime.INSTANCE, AugmentDurationDown.INSTANCE, AugmentFortune.INSTANCE
         }
      );
   }

   @Override
   protected void addDefaultAugmentLimits(Map<ResourceLocation, Integer> defaults) {
      defaults.put(AugmentAmplify.INSTANCE.getRegistryName(), 2);
   }

   @Override
   public String getBookDescription() {
      return "A spell you start with. Damages a target. May be increased by Amplify, or applies the Poison debuff when using Extend Time. Note, multiple Harms without a delay will not apply due to invincibility on hit.";
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.ELEMENTAL_EARTH});
   }

   @Override
   public int getBaseDuration() {
      return this.POTION_TIME == null ? 30 : (Integer)this.POTION_TIME.get();
   }

   @Override
   public int getExtendTimeDuration() {
      return this.EXTEND_TIME == null ? 8 : (Integer)this.EXTEND_TIME.get();
   }
}
