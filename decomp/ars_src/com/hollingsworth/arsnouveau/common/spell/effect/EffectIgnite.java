package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.api.util.SpellUtil;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAOE;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDurationDown;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentExtendTime;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentPierce;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentSensitive;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.jetbrains.annotations.NotNull;

public class EffectIgnite extends AbstractEffect {
   public static EffectIgnite INSTANCE = new EffectIgnite();

   private EffectIgnite() {
      super(GlyphLib.EffectIgniteID, "Ignite");
   }

   @Override
   public void onResolveEntity(
      EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      int duration = (int)(
         (double)((Integer)this.POTION_TIME.get()).intValue() + (double)((Integer)this.EXTEND_TIME.get()).intValue() * spellStats.getDurationMultiplier()
      );
      rayTraceResult.m_82443_().m_20254_(duration);
   }

   @Override
   public void onResolveBlock(
      BlockHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      if (!spellStats.isSensitive()) {
         BlockState hitState = world.m_8055_(rayTraceResult.m_82425_());
         if ((!(hitState.m_60734_() instanceof CandleBlock) || !CandleBlock.m_152845_(hitState))
            && (!(hitState.m_60734_() instanceof CampfireBlock) || !CampfireBlock.m_51321_(hitState))) {
            if (world.m_8055_(rayTraceResult.m_82425_().m_7494_()).m_60767_().m_76336_()) {
               Direction face = rayTraceResult.m_82434_();

               for (BlockPos pos : SpellUtil.calcAOEBlocks(shooter, rayTraceResult.m_82425_(), rayTraceResult, spellStats)) {
                  BlockPos blockpos1 = pos.m_121945_(face);
                  if (BaseFireBlock.m_49255_(world, blockpos1, face)
                     && BlockUtil.destroyRespectsClaim(this.getPlayer(shooter, (ServerLevel)world), world, blockpos1)) {
                     BlockState blockstate1 = BaseFireBlock.m_49245_(world, blockpos1);
                     world.m_7731_(blockpos1, blockstate1, 3);
                     world.m_46672_(blockpos1, blockstate1.m_60734_());
                  }
               }
            }
         } else {
            AbstractCandleBlock.m_151918_(world, hitState, rayTraceResult.m_82425_(), true);
         }
      }
   }

   @Override
   public void buildConfig(Builder builder) {
      super.buildConfig(builder);
      this.addExtendTimeConfig(builder, 2);
      this.addPotionConfig(builder, 3);
   }

   @Override
   public int getDefaultManaCost() {
      return 15;
   }

   @Override
   public SpellTier defaultTier() {
      return SpellTier.ONE;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(
         new AbstractAugment[]{
            AugmentExtendTime.INSTANCE, AugmentAOE.INSTANCE, AugmentPierce.INSTANCE, AugmentDurationDown.INSTANCE, AugmentSensitive.INSTANCE
         }
      );
   }

   @Override
   public String getBookDescription() {
      return "Sets blocks and mobs on fire for a short time. Sensitive will stop this spell from igniting blocks.";
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.ELEMENTAL_FIRE});
   }
}
