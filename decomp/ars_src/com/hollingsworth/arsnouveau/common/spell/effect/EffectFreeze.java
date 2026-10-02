package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.IPotionEffect;
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
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentSensitive;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.jetbrains.annotations.NotNull;

public class EffectFreeze extends AbstractEffect implements IPotionEffect {
   public static EffectFreeze INSTANCE = new EffectFreeze();

   private EffectFreeze() {
      super(GlyphLib.EffectFreezeID, "Freeze");
   }

   @Override
   public void onResolveBlock(
      BlockHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      BlockPos pos = rayTraceResult.m_82425_();

      for (BlockPos p : SpellUtil.calcAOEBlocks(shooter, pos, rayTraceResult, spellStats.getAoeMultiplier(), spellStats.getBuffCount(AugmentPierce.INSTANCE))) {
         BlockPos affectedPos = this.extinguishOrFreeze(world, p, spellStats);
         if (affectedPos != null) {
            ShapersFocus.tryPropagateBlockSpell(
               new BlockHitResult(
                  new Vec3((double)affectedPos.m_123341_(), (double)affectedPos.m_123342_(), (double)affectedPos.m_123343_()),
                  rayTraceResult.m_82434_(),
                  affectedPos,
                  false
               ),
               world,
               shooter,
               spellContext,
               resolver
            );
         }

         for (Direction d : Direction.values()) {
            BlockPos relative = p.m_121945_(d);
            affectedPos = this.extinguishOrFreeze(world, relative, spellStats);
            if (affectedPos != null) {
               ShapersFocus.tryPropagateBlockSpell(
                  new BlockHitResult(
                     new Vec3((double)affectedPos.m_123341_(), (double)affectedPos.m_123342_(), (double)affectedPos.m_123343_()),
                     rayTraceResult.m_82434_(),
                     affectedPos,
                     false
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

   @Override
   public void onResolveEntity(
      EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      if (rayTraceResult.m_82443_() instanceof LivingEntity living) {
         this.applyConfigPotion(living, MobEffects.f_19597_, spellStats);
      }
   }

   @Nullable
   public BlockPos extinguishOrFreeze(Level world, BlockPos p, SpellStats spellStats) {
      BlockState hitState = world.m_8055_(p);
      BlockState aboveState = world.m_8055_(p.m_7494_());
      FluidState aboveFluidstate = world.m_6425_(p.m_7494_());
      if (aboveFluidstate.m_76152_() == Fluids.f_76193_ && aboveState.m_60734_() instanceof LiquidBlock) {
         if (spellStats.isSensitive()) {
            world.m_46597_(p.m_7494_(), Blocks.f_50449_.m_49966_());
         } else {
            world.m_46597_(p.m_7494_(), Blocks.f_50126_.m_49966_());
         }

         return p.m_7494_();
      } else if (aboveFluidstate.m_76152_() == Fluids.f_76195_ && aboveState.m_60734_() instanceof LiquidBlock) {
         world.m_46597_(p.m_7494_(), Blocks.f_50080_.m_49966_());
         return p.m_7494_();
      } else if (aboveFluidstate.m_76152_() == Fluids.f_76194_ && aboveState.m_60734_() instanceof LiquidBlock) {
         world.m_46597_(p.m_7494_(), Blocks.f_50652_.m_49966_());
         return p.m_7494_();
      } else if (aboveState.m_60767_() == Material.f_76309_) {
         world.m_46961_(p.m_7494_(), false);
         return p.m_7494_();
      } else if (hitState.m_60734_() == Blocks.f_50126_) {
         world.m_7731_(p, Blocks.f_50354_.m_49966_(), 3);
         return p;
      } else if (hitState.m_60734_() == Blocks.f_50354_) {
         world.m_7731_(p, Blocks.f_50568_.m_49966_(), 3);
         return p;
      } else {
         return null;
      }
   }

   @Override
   public void buildConfig(Builder builder) {
      super.buildConfig(builder);
      this.addPotionConfig(builder, 10);
      this.addExtendTimeConfig(builder, 5);
   }

   @Override
   public int getDefaultManaCost() {
      return 15;
   }

   @Override
   protected Map<ResourceLocation, Integer> getDefaultAugmentLimits(Map<ResourceLocation, Integer> defaults) {
      super.getDefaultAugmentLimits(defaults);
      defaults.put(AugmentSensitive.INSTANCE.getRegistryName(), 1);
      return defaults;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      Set<AbstractAugment> augments = new HashSet<>(this.getPotionAugments());
      augments.add(AugmentAOE.INSTANCE);
      augments.add(AugmentPierce.INSTANCE);
      augments.add(AugmentSensitive.INSTANCE);
      return augments;
   }

   @Override
   public String getBookDescription() {
      return "Freezes water or lava in a small area or slows a target for a short time. Freeze on Ice will turn it into Packed Ice, and Packed Ice into Blue Ice. Sensitive will turn water into Frosted Ice and will vanish after a short time.";
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.ELEMENTAL_WATER});
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
