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
import com.hollingsworth.arsnouveau.common.items.curios.ShapersFocus;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAOE;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentPierce;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class EffectConjureWater extends AbstractEffect {
   public static EffectConjureWater INSTANCE = new EffectConjureWater();

   private EffectConjureWater() {
      super(GlyphLib.EffectConjureWaterID, "Conjure Water");
   }

   @Override
   public void onResolveEntity(
      EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      Entity entity = rayTraceResult.m_82443_();
      if (entity.m_6060_()) {
         entity.m_20095_();
      }
   }

   @Override
   public void onResolveBlock(
      BlockHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      double aoeBuff = spellStats.getAoeMultiplier();
      List<BlockPos> posList = SpellUtil.calcAOEBlocks(
         shooter, rayTraceResult.m_82425_(), rayTraceResult, aoeBuff, spellStats.getBuffCount(AugmentPierce.INSTANCE)
      );
      if (!world.m_6042_().f_63857_()) {
         for (BlockPos pos1 : posList) {
            if (BlockUtil.destroyRespectsClaim(this.getPlayer(shooter, (ServerLevel)world), world, pos1) && world.m_46739_(pos1)) {
               BlockState hitState = world.m_8055_(pos1);
               Block var14 = hitState.m_60734_();
               if (var14 instanceof LiquidBlockContainer) {
                  LiquidBlockContainer liquidBlockContainer = (LiquidBlockContainer)var14;
                  if (liquidBlockContainer.m_6044_(world, pos1, world.m_8055_(pos1), Fluids.f_76193_)) {
                     liquidBlockContainer.m_7361_(world, pos1, hitState, Fluids.f_76193_.m_76068_(true));
                     ShapersFocus.tryPropagateBlockSpell(
                        new BlockHitResult(
                           new Vec3((double)pos1.m_123341_(), (double)pos1.m_123342_(), (double)pos1.m_123343_()), rayTraceResult.m_82434_(), pos1, false
                        ),
                        world,
                        shooter,
                        spellContext,
                        resolver
                     );
                     continue;
                  }
               }

               if (world.m_8055_(pos1.m_121945_(rayTraceResult.m_82434_())).m_60722_(Fluids.f_76193_)) {
                  pos1 = pos1.m_121945_(rayTraceResult.m_82434_());
                  world.m_46597_(pos1, Blocks.f_49990_.m_49966_());
                  ShapersFocus.tryPropagateBlockSpell(
                     new BlockHitResult(
                        new Vec3((double)pos1.m_123341_(), (double)pos1.m_123342_(), (double)pos1.m_123343_()), rayTraceResult.m_82434_(), pos1, false
                     ),
                     world,
                     shooter,
                     spellContext,
                     resolver
                  );
               }
            }
         }
      }
   }

   @Override
   public int getDefaultManaCost() {
      return 80;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(new AbstractAugment[]{AugmentAOE.INSTANCE, AugmentPierce.INSTANCE});
   }

   @Override
   public String getBookDescription() {
      return "Places water at a location or extinguishes entities on fire.";
   }

   @Override
   public SpellTier defaultTier() {
      return SpellTier.TWO;
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.ELEMENTAL_WATER});
   }
}
