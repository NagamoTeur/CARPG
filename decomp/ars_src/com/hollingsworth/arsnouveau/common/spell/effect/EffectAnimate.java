package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.common.entity.AnimBlockSummon;
import com.hollingsworth.arsnouveau.common.entity.AnimHeadSummon;
import com.hollingsworth.arsnouveau.common.entity.EnchantedFallingBlock;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.jetbrains.annotations.NotNull;

public class EffectAnimate extends AbstractEffect {
   public static EffectAnimate INSTANCE = new EffectAnimate();

   public EffectAnimate() {
      super(GlyphLib.EffectAnimateID, "Animate Block");
   }

   @Override
   public void onResolveBlock(
      BlockHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      BlockPos pos = rayTraceResult.m_82425_();
      BlockState state = world.m_8055_(pos);
      if (EnchantedFallingBlock.canFall(world, pos, shooter, spellStats)) {
         BlockEntity blockEntity = world.m_7702_(pos);
         this.animateBlock(
            rayTraceResult,
            new Vec3((double)pos.m_123341_() + 0.5, (double)pos.m_123342_(), (double)pos.m_123343_() + 0.5),
            world,
            shooter,
            spellStats,
            spellContext,
            resolver,
            state,
            blockEntity == null ? new CompoundTag() : blockEntity.m_187482_()
         );
         world.m_7731_(pos, state.m_60819_().m_76188_(), 3);
      }
   }

   @Override
   public void onResolveEntity(
      EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      if (rayTraceResult.m_82443_() instanceof EnchantedFallingBlock fallingBlock && !fallingBlock.m_213877_()) {
         AnimBlockSummon summon = this.animateBlock(
            rayTraceResult, fallingBlock.f_19825_, world, shooter, spellStats, spellContext, resolver, fallingBlock.getBlockState(), fallingBlock.blockData
         );
         summon.m_20256_(fallingBlock.m_20184_());
         summon.f_19864_ = true;
         summon.f_19789_ = 0.0F;
         fallingBlock.m_146870_();
      }
   }

   private AnimBlockSummon animateBlock(
      HitResult rayTraceResult,
      Vec3 pos,
      Level world,
      @NotNull LivingEntity shooter,
      SpellStats spellStats,
      SpellContext spellContext,
      SpellResolver resolver,
      BlockState state,
      CompoundTag data
   ) {
      AnimBlockSummon blockSummon = (AnimBlockSummon)(state.m_60734_() instanceof AbstractSkullBlock
         ? new AnimHeadSummon(world, state, data)
         : new AnimBlockSummon(world, state));
      blockSummon.setColor(spellContext.getColors().getColor());
      blockSummon.m_146884_(pos);
      int ticks = (int)(
         20.0
            * ((double)((Integer)this.GENERIC_INT.get()).intValue() + (double)((Integer)this.EXTEND_TIME.get()).intValue() * spellStats.getDurationMultiplier())
      );
      blockSummon.setTicksLeft(ticks);
      blockSummon.m_6710_(shooter.m_21214_());
      blockSummon.m_21561_(true);
      blockSummon.m_7105_(true);
      if (shooter instanceof Player player) {
         blockSummon.m_21828_(player);
      }

      this.summonLivingEntity(rayTraceResult, world, shooter, spellStats, spellContext, resolver, blockSummon);
      return blockSummon;
   }

   @Override
   public void buildConfig(Builder builder) {
      super.buildConfig(builder);
      this.addGenericInt(builder, 60, "Base duration in seconds", "duration");
      this.addExtendTimeConfig(builder, 60);
   }

   @Override
   public int getDefaultManaCost() {
      return 200;
   }

   @Override
   public SpellTier defaultTier() {
      return SpellTier.TWO;
   }

   @Override
   public String getBookDescription() {
      return "Animates a block to fight for you. The animated block will convert to a falling block when it dies. Costs a large amount of mana, but does not grant Summoning Sickness";
   }

   @NotNull
   @Override
   protected Set<AbstractAugment> getCompatibleAugments() {
      return this.getSummonAugments();
   }

   @NotNull
   @Override
   protected Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.MANIPULATION, SpellSchools.CONJURATION});
   }
}
