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
import com.hollingsworth.arsnouveau.common.block.tile.IntangibleAirTile;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAOE;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDampen;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDurationDown;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentExtendTime;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentPierce;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.jetbrains.annotations.NotNull;

public class EffectIntangible extends AbstractEffect {
   public static EffectIntangible INSTANCE = new EffectIntangible();

   private EffectIntangible() {
      super(GlyphLib.EffectIntangibleID, "Intangible");
   }

   @Override
   public void onResolveBlock(
      BlockHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      BlockPos pos = rayTraceResult.m_82425_();
      int duration = (int)(
         (double)((Integer)this.GENERIC_INT.get()).intValue() + (double)((Integer)this.EXTEND_TIME.get()).intValue() * spellStats.getDurationMultiplier()
      );

      for (BlockPos pos1 : SpellUtil.calcAOEBlocks(shooter, pos, rayTraceResult, spellStats)) {
         if (world.m_7702_(pos1) == null
            && world.m_8055_(pos1).m_60767_() != Material.f_76296_
            && world.m_8055_(pos1).m_60734_() != Blocks.f_50752_
            && this.canBlockBeHarvested(spellStats, world, pos)
            && BlockUtil.destroyRespectsClaim(this.getPlayer(shooter, (ServerLevel)world), world, pos1)) {
            BlockState state = world.m_8055_(pos1);
            int id = Block.m_49956_(state);
            world.m_46597_(pos1, BlockRegistry.INTANGIBLE_AIR.m_49966_());
            IntangibleAirTile tile = (IntangibleAirTile)world.m_7702_(pos1);
            if (tile != null) {
               tile.stateID = id;
               tile.maxLength = duration * 20;
            }
         }
      }
   }

   @Override
   public void buildConfig(Builder builder) {
      super.buildConfig(builder);
      this.addGenericInt(builder, 3, "Base duration, in seconds", "base");
      this.addExtendTimeConfig(builder, 1);
   }

   @Override
   public SpellTier defaultTier() {
      return SpellTier.THREE;
   }

   @Override
   public int getDefaultManaCost() {
      return 30;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(
         new AbstractAugment[]{
            AugmentAmplify.INSTANCE,
            AugmentDampen.INSTANCE,
            AugmentExtendTime.INSTANCE,
            AugmentPierce.INSTANCE,
            AugmentAOE.INSTANCE,
            AugmentDurationDown.INSTANCE
         }
      );
   }

   @Override
   public String getBookDescription() {
      return "Causes blocks to temporarily turn into air. Can be modified with Amplify for blocks of higher hardness, AOE, Duration Down, and Extend Time.";
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.MANIPULATION});
   }
}
