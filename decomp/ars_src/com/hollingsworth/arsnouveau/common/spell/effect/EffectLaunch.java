package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.util.SpellUtil;
import com.hollingsworth.arsnouveau.common.entity.EnchantedFallingBlock;
import com.hollingsworth.arsnouveau.common.items.curios.ShapersFocus;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAOE;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDampen;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentPierce;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentSensitive;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.jetbrains.annotations.NotNull;

public class EffectLaunch extends AbstractEffect {
   public static EffectLaunch INSTANCE = new EffectLaunch();

   private EffectLaunch() {
      super(GlyphLib.EffectLaunchID, "Launch");
   }

   @Override
   public void onResolveEntity(
      EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      Entity entity = rayTraceResult.m_82443_();
      entity.m_20256_(entity.m_20184_().m_82520_(0.0, (Double)this.GENERIC_DOUBLE.get() + (Double)this.AMP_VALUE.get() * spellStats.getAmpMultiplier(), 0.0));
      entity.f_19864_ = true;
      entity.f_19789_ = 0.0F;
   }

   @Override
   public void onResolveBlock(
      BlockHitResult result, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      if (!spellStats.isSensitive()) {
         for (BlockPos pos1 : SpellUtil.calcAOEBlocks(shooter, result.m_82425_(), result, spellStats)) {
            EnchantedFallingBlock entity = EnchantedFallingBlock.fall(world, pos1, shooter, spellContext, resolver, spellStats);
            if (entity != null) {
               entity.m_20256_(
                  entity.m_20184_().m_82520_(0.0, (Double)this.GENERIC_DOUBLE.get() * 0.6 + (Double)this.AMP_VALUE.get() * spellStats.getAmpMultiplier(), 0.0)
               );
               entity.f_19864_ = true;
               entity.f_19789_ = 0.0F;
               ShapersFocus.tryPropagateEntitySpell(entity, world, shooter, spellContext, resolver);
            }
         }
      }
   }

   @Override
   public void buildConfig(Builder builder) {
      super.buildConfig(builder);
      this.addGenericDouble(builder, 0.8, "Base knockup amount", "knockup");
      this.addAmpConfig(builder, 0.25);
   }

   @Override
   public int getDefaultManaCost() {
      return 30;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(
         new AbstractAugment[]{AugmentAmplify.INSTANCE, AugmentDampen.INSTANCE, AugmentAOE.INSTANCE, AugmentSensitive.INSTANCE, AugmentPierce.INSTANCE}
      );
   }

   @Override
   public String getBookDescription() {
      return "Launches an entity or block into the air. Can be used for large jumps, yeeting mobs in the sky or for scaling mountains! Sensitive will stop this spell from launching blocks.";
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.ELEMENTAL_AIR});
   }
}
