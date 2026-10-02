package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.api.util.SpellUtil;
import com.hollingsworth.arsnouveau.common.datagen.BlockTagProvider;
import com.hollingsworth.arsnouveau.common.items.curios.ShapersFocus;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAOE;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDampen;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentExtract;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentFortune;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentPierce;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentRandomize;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentSensitive;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class EffectBreak extends AbstractEffect {
   public static EffectBreak INSTANCE = new EffectBreak();

   private EffectBreak() {
      super(GlyphLib.EffectBreakID, "Break");
   }

   @Override
   public int getDefaultManaCost() {
      return 10;
   }

   public ItemStack getStack(LivingEntity shooter, BlockHitResult blockHitResult) {
      ItemStack stack = shooter.m_21205_().m_41777_();
      boolean usePick = shooter.f_19853_.m_8055_(blockHitResult.m_82425_()).m_204336_(BlockTagProvider.BREAK_WITH_PICKAXE);
      if (usePick) {
         return new ItemStack(Items.f_42390_);
      } else {
         return stack.m_41619_() ? new ItemStack(Items.f_42390_) : stack;
      }
   }

   @Override
   public void onResolveBlock(
      BlockHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      BlockPos pos = rayTraceResult.m_82425_();
      MobEffectInstance miningFatigue = shooter.m_21124_(MobEffects.f_19599_);
      if (miningFatigue != null) {
         spellStats.setAmpMultiplier(spellStats.getAmpMultiplier() - (double)miningFatigue.m_19564_());
      }

      double aoeBuff = spellStats.getAoeMultiplier();
      int pierceBuff = spellStats.getBuffCount(AugmentPierce.INSTANCE);
      List<BlockPos> posList = SpellUtil.calcAOEBlocks(shooter, pos, rayTraceResult, aoeBuff, pierceBuff);
      ItemStack stack = spellStats.isSensitive() ? new ItemStack(Items.f_42574_) : this.getStack(shooter, rayTraceResult);
      int numFortune = spellStats.getBuffCount(AugmentFortune.INSTANCE);
      int numSilkTouch = spellStats.getBuffCount(AugmentExtract.INSTANCE);
      if (numFortune > 0 && stack.getEnchantmentLevel(Enchantments.f_44987_) < numFortune) {
         stack.m_41663_(Enchantments.f_44987_, numFortune);
      }

      if (numSilkTouch > 0 && stack.getEnchantmentLevel(Enchantments.f_44985_) < numSilkTouch) {
         stack.m_41663_(Enchantments.f_44985_, numSilkTouch);
      }

      for (BlockPos pos1 : posList) {
         if (!(world.f_46441_.m_188501_() < (float)spellStats.getBuffCount(AugmentRandomize.INSTANCE) * 0.25F)) {
            BlockState state = world.m_8055_(pos1);
            if (this.canBlockBeHarvested(spellStats, world, pos1)
               && BlockUtil.destroyRespectsClaim(this.getPlayer(shooter, (ServerLevel)world), world, pos1)
               && !state.m_204336_(BlockTagProvider.BREAK_BLACKLIST)
               && BlockUtil.breakExtraBlock((ServerLevel)world, pos1, stack, shooter.m_20148_(), true)) {
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

   @Override
   public boolean defaultedStarterGlyph() {
      return true;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(
         new AbstractAugment[]{
            AugmentAmplify.INSTANCE,
            AugmentDampen.INSTANCE,
            AugmentPierce.INSTANCE,
            AugmentAOE.INSTANCE,
            AugmentExtract.INSTANCE,
            AugmentFortune.INSTANCE,
            AugmentSensitive.INSTANCE,
            AugmentRandomize.INSTANCE
         }
      );
   }

   @Override
   public String getBookDescription() {
      return "A spell you start with. Breaks blocks of an average hardness. Can be amplified to increase the harvest level. Sensitive will simulate breaking blocks with Shears instead of a pickaxe.";
   }

   @Override
   protected Map<ResourceLocation, Integer> getDefaultAugmentLimits(Map<ResourceLocation, Integer> defaults) {
      super.getDefaultAugmentLimits(defaults);
      defaults.put(AugmentFortune.INSTANCE.getRegistryName(), 4);
      return defaults;
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.ELEMENTAL_EARTH});
   }
}
