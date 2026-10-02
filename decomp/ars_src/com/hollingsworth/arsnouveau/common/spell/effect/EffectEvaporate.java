package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.util.SpellUtil;
import com.hollingsworth.arsnouveau.common.items.curios.ShapersFocus;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAOE;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentPierce;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class EffectEvaporate extends AbstractEffect {
   public static EffectEvaporate INSTANCE = new EffectEvaporate();

   private EffectEvaporate() {
      super(GlyphLib.EffectEvaporate, "Evaporate");
   }

   @Override
   public void onResolveBlock(
      BlockHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      BlockPos pos = rayTraceResult.m_82425_();

      for (BlockPos p : SpellUtil.calcAOEBlocks(shooter, pos, rayTraceResult, spellStats.getAoeMultiplier(), spellStats.getBuffCount(AugmentPierce.INSTANCE))) {
         this.evaporate(world, p, rayTraceResult, shooter, spellContext, resolver);

         for (Direction d : Direction.values()) {
            this.evaporate(world, p.m_121945_(d), rayTraceResult, shooter, spellContext, resolver);
         }
      }
   }

   public void evaporate(Level world, BlockPos p, BlockHitResult rayTraceResult, LivingEntity shooter, SpellContext context, SpellResolver resolver) {
      BlockState state = world.m_8055_(p);
      if (!world.m_6425_(p).m_76178_() && state.m_60734_() instanceof LiquidBlock) {
         world.m_7731_(p, Blocks.f_50016_.m_49966_(), 3);
         ShapersFocus.tryPropagateBlockSpell(
            new BlockHitResult(new Vec3((double)p.m_123341_(), (double)p.m_123342_(), (double)p.m_123343_()), rayTraceResult.m_82434_(), p, false),
            world,
            shooter,
            context,
            resolver
         );
      } else if (state.m_60734_() instanceof SimpleWaterloggedBlock && state.m_61138_(BlockStateProperties.f_61362_)) {
         world.m_7731_(p, (BlockState)state.m_61124_(BlockStateProperties.f_61362_, Boolean.FALSE), 3);
         ShapersFocus.tryPropagateBlockSpell(
            new BlockHitResult(new Vec3((double)p.m_123341_(), (double)p.m_123342_(), (double)p.m_123343_()), rayTraceResult.m_82434_(), p, false),
            world,
            shooter,
            context,
            resolver
         );
      }
   }

   @Override
   public String getBookDescription() {
      return "Deletes fluids in an area. Can be expanded with AOE.";
   }

   @Override
   public int getDefaultManaCost() {
      return 50;
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.MANIPULATION});
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.setOf(new AbstractAugment[]{AugmentAOE.INSTANCE, AugmentPierce.INSTANCE});
   }
}
