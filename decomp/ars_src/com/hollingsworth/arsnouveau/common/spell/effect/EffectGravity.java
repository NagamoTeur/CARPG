package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.IPotionEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.api.util.SpellUtil;
import com.hollingsworth.arsnouveau.common.entity.EnchantedFallingBlock;
import com.hollingsworth.arsnouveau.common.items.curios.ShapersFocus;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.potions.ModPotions;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAOE;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDampen;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDurationDown;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentExtendTime;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentPierce;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.jetbrains.annotations.NotNull;

public class EffectGravity extends AbstractEffect implements IPotionEffect {
   public static EffectGravity INSTANCE = new EffectGravity();

   private EffectGravity() {
      super(GlyphLib.EffectGravityID, "Gravity");
   }

   @Override
   public void onResolveBlock(
      BlockHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      BlockPos pos = rayTraceResult.m_82425_();

      for (BlockPos pos1 : SpellUtil.calcAOEBlocks(shooter, pos, rayTraceResult, spellStats.getAoeMultiplier(), spellStats.getBuffCount(AugmentPierce.INSTANCE))) {
         EnchantedFallingBlock fallingBlock = EnchantedFallingBlock.fall(world, pos1, shooter, spellContext, resolver, spellStats);
         if (fallingBlock != null) {
            ShapersFocus.tryPropagateEntitySpell(fallingBlock, world, shooter, spellContext, resolver);
         }
      }
   }

   @Override
   public void onResolveEntity(
      EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      if (rayTraceResult.m_82443_() instanceof LivingEntity living) {
         if (spellStats.hasBuff(AugmentExtendTime.INSTANCE)) {
            this.applyConfigPotion(living, (MobEffect)ModPotions.GRAVITY_EFFECT.get(), spellStats);
         } else {
            Entity entity = rayTraceResult.m_82443_();
            entity.m_20256_(entity.m_20184_().m_82520_(0.0, -1.0 - spellStats.getDurationMultiplier(), 0.0));
            entity.f_19864_ = true;
         }
      }
   }

   @Override
   public int getDefaultManaCost() {
      return 15;
   }

   @Override
   public SpellTier defaultTier() {
      return SpellTier.TWO;
   }

   @Override
   public void buildConfig(Builder builder) {
      super.buildConfig(builder);
      this.addPotionConfig(builder, 30);
      this.addExtendTimeConfig(builder, 8);
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(
         new AbstractAugment[]{
            AugmentAmplify.INSTANCE,
            AugmentDampen.INSTANCE,
            AugmentAOE.INSTANCE,
            AugmentPierce.INSTANCE,
            AugmentExtendTime.INSTANCE,
            AugmentDurationDown.INSTANCE
         }
      );
   }

   @Override
   public String getBookDescription() {
      return "Causes blocks and entities to fall. When augmented with Extend Time, players will have their flight disabled and will obtain the Gravity effect. While afflicted with Gravity, entities will rapidly fall and take double falling damage.";
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.ELEMENTAL_AIR});
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
