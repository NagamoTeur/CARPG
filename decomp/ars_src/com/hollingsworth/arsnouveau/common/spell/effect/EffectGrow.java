package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
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
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentPierce;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class EffectGrow extends AbstractEffect {
   public static EffectGrow INSTANCE = new EffectGrow();

   private EffectGrow() {
      super(GlyphLib.EffectGrowID, "Grow");
   }

   @Override
   public void onResolveBlock(
      BlockHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      for (BlockPos blockpos : SpellUtil.calcAOEBlocks(shooter, rayTraceResult.m_82425_(), rayTraceResult, spellStats)) {
         ItemStack stack = new ItemStack(Items.f_42499_, 64);
         if (BlockUtil.destroyRespectsClaim(shooter, world, blockpos) && world instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)world;
            if (BoneMealItem.applyBonemeal(stack, world, blockpos, ANFakePlayer.getPlayer(serverLevel))) {
               if (!world.f_46443_) {
                  world.m_46796_(1505, blockpos, 0);
               }
            } else {
               BlockPos relative = blockpos.m_121945_(rayTraceResult.m_82434_());
               boolean flag = world.m_8055_(blockpos).m_60783_(world, blockpos, rayTraceResult.m_82434_());
               if (flag && BoneMealItem.m_40631_(stack, world, relative, rayTraceResult.m_82434_()) && !world.f_46443_) {
                  world.m_46796_(1505, relative, 0);
               }
            }
         }
      }
   }

   @Override
   public int getDefaultManaCost() {
      return 70;
   }

   @Override
   public SpellTier defaultTier() {
      return SpellTier.TWO;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(new AbstractAugment[]{AugmentAOE.INSTANCE, AugmentPierce.INSTANCE});
   }

   @Override
   public String getBookDescription() {
      return "Causes plants to accelerate in growth as if they were bonemealed.";
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.ELEMENTAL_EARTH});
   }
}
