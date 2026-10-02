package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.util.SpellUtil;
import com.hollingsworth.arsnouveau.common.entity.EnchantedFallingBlock;
import com.hollingsworth.arsnouveau.common.items.curios.ShapersFocus;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAOE;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDampen;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentPierce;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.jetbrains.annotations.NotNull;

public class EffectLeap extends AbstractEffect {
   public static EffectLeap INSTANCE = new EffectLeap();
   BooleanValue NERF;

   private EffectLeap() {
      super(GlyphLib.EffectLeapID, "Leap");
   }

   @Override
   public void onResolveEntity(
      EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      Entity entity = rayTraceResult.m_82443_();
      if (!(Boolean)this.NERF.get() || entity != shooter || shooter.m_20096_()) {
         double bonus;
         Vec3 vector;
         if (entity instanceof LivingEntity) {
            vector = entity.m_20154_();
            bonus = Math.max(0.0, (Double)this.GENERIC_DOUBLE.get() + (Double)this.AMP_VALUE.get() * spellStats.getAmpMultiplier());
         } else {
            vector = shooter.m_20154_();
            bonus = (Double)this.GENERIC_DOUBLE.get() + (Double)this.AMP_VALUE.get() * spellStats.getAmpMultiplier();
         }

         entity.m_20334_(vector.f_82479_ * bonus, vector.f_82480_ * bonus, vector.f_82481_ * bonus);
         entity.f_19789_ = 0.0F;
         entity.f_19864_ = true;
      }
   }

   @Override
   public void onResolveBlock(
      BlockHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      for (BlockPos pos1 : SpellUtil.calcAOEBlocks(shooter, rayTraceResult.m_82425_(), rayTraceResult, spellStats)) {
         EnchantedFallingBlock entity = EnchantedFallingBlock.fall(world, pos1, shooter, spellContext, resolver, spellStats);
         if (entity != null) {
            Vec3 vector = shooter.m_20154_();
            double bonus = (Double)this.GENERIC_DOUBLE.get() + (Double)this.AMP_VALUE.get() * spellStats.getAmpMultiplier();
            entity.m_20334_(vector.f_82479_ * bonus, vector.f_82480_ * bonus, vector.f_82481_ * bonus);
            entity.f_19864_ = true;
            entity.f_19789_ = 0.0F;
            ShapersFocus.tryPropagateEntitySpell(entity, world, shooter, spellContext, resolver);
         }
      }
   }

   @Override
   public void buildConfig(Builder builder) {
      super.buildConfig(builder);
      this.NERF = builder.comment("If true, will not launch the caster if they are not on the ground.").define("force_ground", false);
      this.addGenericDouble(builder, 1.5, "Base knockup amount", "knock_up");
      this.addAmpConfig(builder, 1.0);
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(new AbstractAugment[]{AugmentAmplify.INSTANCE, AugmentDampen.INSTANCE, AugmentAOE.INSTANCE, AugmentPierce.INSTANCE});
   }

   @Override
   public String getBookDescription() {
      return "Launches the target in the direction they are looking. Amplification will increase the distance moved.";
   }

   @Override
   public int getDefaultManaCost() {
      return 25;
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.ELEMENTAL_AIR});
   }
}
