package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.api.util.LootUtil;
import com.hollingsworth.arsnouveau.api.util.SpellUtil;
import com.hollingsworth.arsnouveau.common.datagen.BlockTagProvider;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAOE;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentFortune;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentPierce;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.StemGrownBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class EffectHarvest extends AbstractEffect {
   public static EffectHarvest INSTANCE = new EffectHarvest();

   private EffectHarvest() {
      super(GlyphLib.EffectHarvestID, "Harvest");
   }

   public static void harvestNetherwart(
      BlockPos pos, BlockState state, Level world, @Nullable LivingEntity shooter, SpellStats spellStats, SpellContext spellContext
   ) {
      if ((Integer)state.m_61143_(NetherWartBlock.f_54967_) == 3) {
         processAndSpawnDrops(pos, state, world, shooter, spellStats, spellContext, true);
         world.m_46597_(pos, (BlockState)state.m_61124_(NetherWartBlock.f_54967_, 0));
      }
   }

   public static void harvestPods(BlockPos pos, BlockState state, Level world, LivingEntity shooter, SpellStats spellStats, SpellContext spellContext) {
      if ((Integer)state.m_61143_(CocoaBlock.f_51736_) == 2) {
         processAndSpawnDrops(pos, state, world, shooter, spellStats, spellContext, true);
         world.m_46597_(pos, (BlockState)state.m_61124_(CocoaBlock.f_51736_, 0));
      }
   }

   public static void processAndSpawnDrops(
      BlockPos pos, BlockState state, Level world, @Nullable LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, boolean takeSeedToReplant
   ) {
      List<ItemStack> cropDrops = Block.m_49869_(state, (ServerLevel)world, pos, world.m_7702_(pos));
      if (spellStats.hasBuff(AugmentFortune.INSTANCE)) {
         cropDrops = state.m_60724_(LootUtil.getFortuneContext((ServerLevel)world, pos, shooter, spellStats.getBuffCount(AugmentFortune.INSTANCE)));
      }

      if (takeSeedToReplant) {
         for (ItemStack i : cropDrops) {
            if (i.m_41720_() instanceof BlockItem && ((BlockItem)i.m_41720_()).m_40614_() == state.m_60734_()) {
               i.m_41774_(1);
               break;
            }
         }
      }

      cropDrops.forEach(d -> {
         if (!d.m_41619_() && d.m_41720_() != BlockRegistry.MAGE_BLOOM_CROP.m_5456_()) {
            world.m_7967_(new ItemEntity(world, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), d));
         }
      });
   }

   @Override
   public void onResolveBlock(
      BlockHitResult ray, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      for (BlockPos blockpos : SpellUtil.calcAOEBlocks(shooter, ray.m_82425_(), ray, spellStats)) {
         BlockState state = world.m_8055_(blockpos);
         if (state.m_60734_() instanceof FarmBlock
            || world.m_8055_(blockpos.m_7494_()).m_60734_() instanceof CropBlock
            || world.m_8055_(blockpos.m_7494_()).m_60734_() instanceof NetherWartBlock
            || world.m_8055_(blockpos.m_7494_()).m_204336_(BlockTagProvider.HARVEST_STEMS)) {
            blockpos = blockpos.m_7494_();
            state = world.m_8055_(blockpos);
         }

         if (state.m_60734_() instanceof CocoaBlock) {
            harvestPods(blockpos, state, world, shooter, spellStats, spellContext);
         } else if (state.m_60734_() instanceof NetherWartBlock) {
            harvestNetherwart(blockpos, state, world, shooter, spellStats, spellContext);
         } else if (!(state.m_60734_() instanceof StemGrownBlock)
            && (!state.m_204336_(BlockTagProvider.HARVEST_STEMS) || state.m_60734_() != world.m_8055_(blockpos.m_7495_()).m_60734_())) {
            if (state.m_60734_() instanceof CropBlock crop && crop.m_52307_(state) && world instanceof ServerLevel) {
               processAndSpawnDrops(blockpos, state, world, shooter, spellStats, spellContext, true);
               world.m_46597_(blockpos, crop.m_52289_(1));
            }
         } else {
            processAndSpawnDrops(blockpos, state, world, shooter, spellStats, spellContext, false);
            BlockUtil.destroyBlockSafely(world, blockpos, false, shooter);
         }
      }
   }

   @Override
   public int getDefaultManaCost() {
      return 10;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(new AbstractAugment[]{AugmentAOE.INSTANCE, AugmentPierce.INSTANCE, AugmentFortune.INSTANCE});
   }

   @Override
   public String getBookDescription() {
      return "When used on grown crops, this spell will obtain the fully grown product without destroying the plant.";
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.ELEMENTAL_EARTH});
   }
}
