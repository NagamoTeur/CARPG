package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
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
import com.hollingsworth.arsnouveau.common.potions.ModPotions;
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
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.jetbrains.annotations.NotNull;

public class EffectColdSnap extends AbstractEffect implements IDamageEffect {
   public static EffectColdSnap INSTANCE = new EffectColdSnap();

   private EffectColdSnap() {
      super(GlyphLib.EffectColdSnapID, "Cold Snap");
   }

   @Override
   public void onResolveEntity(
      EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      if (rayTraceResult.m_82443_() instanceof LivingEntity livingEntity) {
         Vec3 var15 = this.safelyGetHitPos(rayTraceResult);
         float damage = (float)((Double)this.DAMAGE.get() + (Double)this.AMP_VALUE.get() * spellStats.getAmpMultiplier());
         double range = 3.0 + spellStats.getAoeMultiplier();
         int snareSec = (int)(
            (double)((Integer)this.POTION_TIME.get()).intValue() + (double)((Integer)this.EXTEND_TIME.get()).intValue() * spellStats.getDurationMultiplier()
         );
         if (this.canDamage(livingEntity)) {
            this.damage(var15, world, shooter, livingEntity, spellStats, spellContext, resolver, snareSec, damage);

            for (LivingEntity e : world.m_45976_(
               LivingEntity.class, new AABB(livingEntity.m_20182_().m_82520_(range, range, range), livingEntity.m_20182_().m_82492_(range, range, range))
            )) {
               if (!e.equals(livingEntity) && !e.equals(shooter)) {
                  if (this.canDamage(e)) {
                     var15 = e.m_20182_();
                     this.damage(var15, world, shooter, e, spellStats, spellContext, resolver, snareSec, damage);
                  } else {
                     e.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 20 * snareSec, (int)spellStats.getAmpMultiplier()));
                  }
               }
            }
         }
      }
   }

   public boolean canDamage(LivingEntity livingEntity) {
      return livingEntity.m_20070_() || livingEntity.m_21023_(MobEffects.f_19597_) || (double)livingEntity.m_146889_() > 0.0;
   }

   public void damage(
      Vec3 vec,
      Level world,
      LivingEntity shooter,
      LivingEntity livingEntity,
      SpellStats stats,
      SpellContext context,
      SpellResolver resolver,
      int snareTime,
      float damage
   ) {
      this.attemptDamage(world, shooter, stats, context, resolver, livingEntity, this.buildDamageSource(world, shooter), damage);
      ((ServerLevel)world)
         .m_8767_(
            ParticleTypes.f_123764_,
            vec.f_82479_,
            vec.f_82480_ + 0.5,
            vec.f_82481_,
            50,
            ParticleUtil.inRange(-0.1, 0.1),
            ParticleUtil.inRange(-0.1, 0.1),
            ParticleUtil.inRange(-0.1, 0.1),
            0.3
         );
      livingEntity.m_7292_(new MobEffectInstance((MobEffect)ModPotions.SNARE_EFFECT.get(), 20 * snareTime));
   }

   @Override
   public DamageSource buildDamageSource(Level world, LivingEntity shooter) {
      EntityDamageSource damageSource = new EntityDamageSource("freeze", (Entity)(shooter == null ? ANFakePlayer.getPlayer((ServerLevel)world) : shooter));
      damageSource.m_19389_();
      return damageSource;
   }

   @Override
   public void buildConfig(Builder builder) {
      super.buildConfig(builder);
      this.addDamageConfig(builder, 6.0);
      this.addAmpConfig(builder, 2.5);
      this.addPotionConfig(builder, 5);
      this.addExtendTimeConfig(builder, 1);
   }

   @Override
   protected void addDefaultAugmentLimits(Map<ResourceLocation, Integer> defaults) {
      defaults.put(AugmentAmplify.INSTANCE.getRegistryName(), 2);
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
            AugmentDurationDown.INSTANCE,
            AugmentAOE.INSTANCE,
            AugmentFortune.INSTANCE
         }
      );
   }

   @Override
   public String getBookDescription() {
      return "Snares and causes a burst of damage to an entity that is afflicted by slowness, freezing, or is wet. Nearby enemies that are not afflicted by Slow will be slowed. Nearby Enemies afflicted by Slow or wetness will also be hit by Cold Snap. Can be augmented using Amplify, Extend Time, and AOE.";
   }

   @Override
   public SpellTier defaultTier() {
      return SpellTier.TWO;
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.ELEMENTAL_WATER});
   }
}
