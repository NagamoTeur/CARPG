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
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentSensitive;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.jetbrains.annotations.NotNull;

public class EffectKnockback extends AbstractEffect {
   public static EffectKnockback INSTANCE = new EffectKnockback();

   private EffectKnockback() {
      super(GlyphLib.EffectKnockbackID, "Knockback");
   }

   @Override
   public void onResolveEntity(
      EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      float strength = (float)((Double)this.GENERIC_DOUBLE.get() + (Double)this.AMP_VALUE.get() * spellStats.getAmpMultiplier());
      this.knockback(rayTraceResult.m_82443_(), shooter, strength);
      rayTraceResult.m_82443_().f_19864_ = true;
   }

   @Override
   public void onResolveBlock(
      BlockHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      if (!spellStats.isSensitive()) {
         List<BlockPos> posList = SpellUtil.calcAOEBlocks(shooter, rayTraceResult.m_82425_(), rayTraceResult, spellStats);
         float strength = (float)((Double)this.GENERIC_DOUBLE.get() + (Double)this.AMP_VALUE.get() * spellStats.getAmpMultiplier());

         for (BlockPos p : posList) {
            EnchantedFallingBlock fallingBlock = EnchantedFallingBlock.fall(world, p, shooter, spellContext, resolver, spellStats);
            if (fallingBlock != null) {
               this.knockback(fallingBlock, shooter, strength);
               ShapersFocus.tryPropagateEntitySpell(fallingBlock, world, shooter, spellContext, resolver);
            }
         }
      }
   }

   public void knockback(Entity target, LivingEntity shooter, float strength) {
      this.knockback(
         target,
         (double)strength,
         (double)Mth.m_14031_(shooter.f_19857_ * (float) (Math.PI / 180.0)),
         (double)(-Mth.m_14089_(shooter.f_19857_ * (float) (Math.PI / 180.0)))
      );
   }

   public void knockback(Entity entity, double strength, double xRatio, double zRatio) {
      if (entity instanceof LivingEntity living) {
         strength *= 1.0 - living.m_21133_(Attributes.f_22278_);
      }

      if (strength > 0.0) {
         entity.f_19812_ = true;
         Vec3 vec3 = entity.m_20184_();
         Vec3 vec31 = new Vec3(xRatio, 0.0, zRatio).m_82541_().m_82490_(strength);
         entity.m_20334_(
            vec3.f_82479_ / 2.0 - vec31.f_82479_,
            entity.m_20096_() ? Math.min(0.4, vec3.f_82480_ / 2.0 + strength) : vec3.f_82480_,
            vec3.f_82481_ / 2.0 - vec31.f_82481_
         );
      }
   }

   @Override
   public void buildConfig(Builder builder) {
      super.buildConfig(builder);
      this.addGenericDouble(builder, 1.5, "Base knockback value", "base_value");
      this.addAmpConfig(builder, 1.0);
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
      return "Knocks a target or block away a short distance from the caster. Sensitive will stop this spell from launching blocks.";
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.ELEMENTAL_AIR});
   }
}
