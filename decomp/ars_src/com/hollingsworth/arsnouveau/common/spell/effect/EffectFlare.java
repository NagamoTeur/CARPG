package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.IDamageEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAOE;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDampen;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDurationDown;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentExtendTime;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentFortune;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.jetbrains.annotations.NotNull;

public class EffectFlare extends AbstractEffect implements IDamageEffect {
   public static EffectFlare INSTANCE = new EffectFlare();

   private EffectFlare() {
      super(GlyphLib.EffectFlareID, "Flare");
   }

   @Override
   public void onResolveEntity(
      EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      if (rayTraceResult.m_82443_() instanceof LivingEntity livingEntity) {
         Vec3 var16 = this.safelyGetHitPos(rayTraceResult);
         float damage = (float)((Double)this.DAMAGE.get() + (Double)this.AMP_VALUE.get() * spellStats.getAmpMultiplier());
         double range = 3.0 + spellStats.getAoeMultiplier();
         int fireSec = (int)(5.0 + (double)((Integer)this.EXTEND_TIME.get()).intValue() * spellStats.getDurationMultiplier());
         DamageSource source = this.buildDamageSource(world, shooter).m_19383_();
         if (livingEntity.m_6060_()) {
            this.attemptDamage(world, shooter, spellStats, spellContext, resolver, livingEntity, source, damage);
            ((ServerLevel)world)
               .m_8767_(
                  ParticleTypes.f_123744_,
                  var16.f_82479_,
                  var16.f_82480_ + 0.5,
                  var16.f_82481_,
                  50,
                  ParticleUtil.inRange(-0.1, 0.1),
                  ParticleUtil.inRange(-0.1, 0.1),
                  ParticleUtil.inRange(-0.1, 0.1),
                  0.3
               );

            for (Entity e : world.m_45933_(
               shooter, new AABB(livingEntity.m_20182_().m_82520_(range, range, range), livingEntity.m_20182_().m_82492_(range, range, range))
            )) {
               if (!e.equals(livingEntity) && e instanceof LivingEntity) {
                  this.attemptDamage(world, shooter, spellStats, spellContext, resolver, e, source, damage);
                  e.m_20254_(fireSec);
                  var16 = e.m_20182_();
                  ((ServerLevel)world)
                     .m_8767_(
                        ParticleTypes.f_123744_,
                        var16.f_82479_,
                        var16.f_82480_ + 0.5,
                        var16.f_82481_,
                        50,
                        ParticleUtil.inRange(-0.1, 0.1),
                        ParticleUtil.inRange(-0.1, 0.1),
                        ParticleUtil.inRange(-0.1, 0.1),
                        0.3
                     );
               }
            }
         }
      }
   }

   @Override
   protected void addDefaultAugmentLimits(Map<ResourceLocation, Integer> defaults) {
      defaults.put(AugmentAmplify.INSTANCE.getRegistryName(), 2);
   }

   @Override
   public void buildConfig(Builder builder) {
      super.buildConfig(builder);
      this.addDamageConfig(builder, 7.0);
      this.addAmpConfig(builder, 3.0);
      this.addExtendTimeConfig(builder, 1);
   }

   @Override
   public int getDefaultManaCost() {
      return 40;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(
         new AbstractAugment[]{
            AugmentAmplify.INSTANCE,
            AugmentDampen.INSTANCE,
            AugmentExtendTime.INSTANCE,
            AugmentDurationDown.INSTANCE,
            AugmentAOE.INSTANCE,
            AugmentFortune.INSTANCE
         }
      );
   }

   @Override
   public String getBookDescription() {
      return "When used on entities that are on fire, Flare causes a burst of damage and will spread fire and deal damage to other nearby entities. Does significantly more damage than Harm. Can be augmented with Extend Time, Amplify, and AOE.";
   }

   @Override
   public SpellTier defaultTier() {
      return SpellTier.TWO;
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.ELEMENTAL_FIRE});
   }
}
