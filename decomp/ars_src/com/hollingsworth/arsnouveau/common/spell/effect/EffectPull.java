package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.util.SpellUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.entity.EnchantedFallingBlock;
import com.hollingsworth.arsnouveau.common.items.curios.ShapersFocus;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAOE;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDampen;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentPierce;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentSensitive;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.jetbrains.annotations.NotNull;

public class EffectPull extends AbstractEffect {
   public static EffectPull INSTANCE = new EffectPull();

   private EffectPull() {
      super(GlyphLib.EffectPullID, "Pull");
   }

   @Override
   public void onResolveEntity(
      EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      Entity target = rayTraceResult.m_82443_();
      Vec3 vec3d = new Vec3(shooter.m_20185_() - target.m_20185_(), shooter.m_20186_() - target.m_20186_(), shooter.m_20189_() - target.m_20189_());
      double d2 = (Double)this.GENERIC_DOUBLE.get() + (Double)this.AMP_VALUE.get() * spellStats.getAmpMultiplier();
      target.m_20256_(vec3d.m_82541_().m_82490_(d2));
      target.f_19864_ = true;
   }

   @Override
   public void onResolveBlock(
      BlockHitResult blockHitResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      if (!spellStats.isSensitive()) {
         for (BlockPos p : SpellUtil.calcAOEBlocks(shooter, blockHitResult.m_82425_(), blockHitResult, spellStats)) {
            if (this.canBlockBeHarvested(spellStats, world, p)) {
               EnchantedFallingBlock fallingBlockEntity = EnchantedFallingBlock.fall(world, p, shooter, spellContext, resolver, spellStats);
               if (fallingBlockEntity != null) {
                  this.setMotion(fallingBlockEntity, blockHitResult, spellStats);
                  ShapersFocus.tryPropagateEntitySpell(fallingBlockEntity, world, shooter, spellContext, resolver);
               }
            }
         }
      }
   }

   public void setMotion(Entity entity, BlockHitResult blockHitResult, SpellStats spellStats) {
      double scalar = 0.5 + ParticleUtil.inRange(-0.05, 0.05) + spellStats.getAmpMultiplier() * 0.3;
      Vec3i directionVec = blockHitResult.m_82434_().m_122436_();
      Vec3 deltaVec = new Vec3(
         (double)directionVec.m_123341_() * scalar, (double)directionVec.m_123342_() * scalar + 0.1, (double)directionVec.m_123343_() * scalar
      );
      entity.m_20256_(deltaVec);
   }

   @Override
   public void buildConfig(Builder builder) {
      super.buildConfig(builder);
      this.addGenericDouble(builder, 1.0, "Base movement velocity", "base_value");
      this.addAmpConfig(builder, 0.5);
   }

   @Override
   public int getDefaultManaCost() {
      return 15;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(
         new AbstractAugment[]{AugmentAmplify.INSTANCE, AugmentDampen.INSTANCE, AugmentAOE.INSTANCE, AugmentPierce.INSTANCE, AugmentSensitive.INSTANCE}
      );
   }

   @Override
   public String getBookDescription() {
      return "Pulls the target closer to the caster. When used on blocks, they become falling blocks with motion towards the side of the block that was hit. Sensitive will stop this spell from pulling blocks.";
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.ELEMENTAL_AIR});
   }
}
