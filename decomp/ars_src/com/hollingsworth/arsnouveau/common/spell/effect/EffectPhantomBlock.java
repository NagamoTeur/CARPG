package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.api.util.SpellUtil;
import com.hollingsworth.arsnouveau.common.block.MageBlock;
import com.hollingsworth.arsnouveau.common.block.tile.MageBlockTile;
import com.hollingsworth.arsnouveau.common.items.curios.ShapersFocus;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAOE;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDurationDown;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentExtendTime;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentPierce;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.NotNull;

public class EffectPhantomBlock extends AbstractEffect {
   public static EffectPhantomBlock INSTANCE = new EffectPhantomBlock();

   private EffectPhantomBlock() {
      super(GlyphLib.EffectPhantomBlockID, "Conjure Mageblock");
   }

   @Override
   public void onResolveBlock(
      BlockHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      ANFakePlayer fakePlayer = ANFakePlayer.getPlayer((ServerLevel)world);

      for (BlockPos pos : SpellUtil.calcAOEBlocks(shooter, rayTraceResult.m_82425_(), rayTraceResult, spellStats)) {
         pos = rayTraceResult.m_82436_() ? pos : pos.m_121945_(rayTraceResult.m_82434_());
         if (world.m_46739_(pos) && BlockUtil.destroyRespectsClaim(this.getPlayer(shooter, (ServerLevel)world), world, pos)) {
            BlockState state = world.m_8055_(pos);
            if (state.m_60767_().m_76336_() && world.m_45752_(BlockRegistry.MAGE_BLOCK.m_49966_(), pos, CollisionContext.m_82750_(fakePlayer))) {
               world.m_46597_(pos, (BlockState)BlockRegistry.MAGE_BLOCK.m_49966_().m_61124_(MageBlock.TEMPORARY, !spellStats.hasBuff(AugmentAmplify.INSTANCE)));
               if (world.m_7702_(pos) instanceof MageBlockTile tile) {
                  tile.color = spellContext.getColors();
                  tile.lengthModifier = spellStats.getDurationMultiplier();
                  tile.isPermanent = spellStats.hasBuff(AugmentAmplify.INSTANCE);
                  world.m_7260_(pos, world.m_8055_(pos), world.m_8055_(pos), 2);
                  ShapersFocus.tryPropagateBlockSpell(
                     new BlockHitResult(
                        new Vec3((double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 0.5, (double)pos.m_123343_() + 0.5),
                        rayTraceResult.m_82434_(),
                        pos,
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
   }

   @Override
   protected Map<ResourceLocation, Integer> getDefaultAugmentLimits(Map<ResourceLocation, Integer> defaults) {
      super.getDefaultAugmentLimits(defaults);
      defaults.put(AugmentAmplify.INSTANCE.getRegistryName(), 1);
      return defaults;
   }

   @Override
   public int getDefaultManaCost() {
      return 5;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(
         new AbstractAugment[]{AugmentAOE.INSTANCE, AugmentPierce.INSTANCE, AugmentAmplify.INSTANCE, AugmentExtendTime.INSTANCE, AugmentDurationDown.INSTANCE}
      );
   }

   @Override
   public String getBookDescription() {
      return "Creates a temporary block that will disappear after a short time. Amplify will cause the block to be permanent. Dispelling this block will destroy it instantly.";
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.CONJURATION});
   }
}
