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
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDampen;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.jetbrains.annotations.NotNull;

public class EffectWindshear extends AbstractEffect implements IDamageEffect {
   public static EffectWindshear INSTANCE = new EffectWindshear();

   private EffectWindshear() {
      super(GlyphLib.EffectWindshearID, "Wind Shear");
   }

   @Override
   public void onResolveEntity(
      EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      if (!rayTraceResult.m_82443_().m_20096_() && !(rayTraceResult.m_82443_() instanceof ItemEntity)) {
         int numBlocks = 0;

         for (BlockPos pos = rayTraceResult.m_82443_().m_20183_(); !world.m_8055_(pos.m_7495_()).m_60767_().m_76334_() && numBlocks <= 10; numBlocks++) {
            pos = pos.m_7495_();
         }

         this.attemptDamage(
            world,
            shooter,
            spellStats,
            spellContext,
            resolver,
            rayTraceResult.m_82443_(),
            this.buildDamageSource(world, shooter),
            (float)((Double)this.DAMAGE.get() + (double)numBlocks)
         );
         Vec3 vec = rayTraceResult.m_82443_().f_19825_;

         for (int i = 0; i < 10; i++) {
            ((ServerLevel)world)
               .m_8767_(
                  ParticleTypes.f_123766_,
                  vec.f_82479_ + ParticleUtil.inRange(-0.2, 0.2),
                  vec.f_82480_ + 0.5 + ParticleUtil.inRange(-0.2, 0.2),
                  vec.f_82481_ + ParticleUtil.inRange(-0.2, 0.2),
                  30,
                  ParticleUtil.inRange(-0.2, 0.2),
                  ParticleUtil.inRange(-0.2, 0.2),
                  ParticleUtil.inRange(-0.2, 0.2),
                  0.3
               );
         }
      }
   }

   @Override
   public DamageSource buildDamageSource(Level world, LivingEntity shooter) {
      return new EntityDamageSource("fall", this.getPlayer(shooter, (ServerLevel)world)).m_19380_().m_146708_();
   }

   @Override
   public void buildConfig(Builder builder) {
      super.buildConfig(builder);
      this.addDamageConfig(builder, 5.0);
      this.addAmpConfig(builder, 2.5);
      this.addGenericDouble(builder, 0.75, "Damage per block in the air", "airDamage");
   }

   @Override
   protected void addDefaultAugmentLimits(Map<ResourceLocation, Integer> defaults) {
      defaults.put(AugmentAmplify.INSTANCE.getRegistryName(), 2);
   }

   @Override
   public String getBookDescription() {
      return "Deals damage to targets in the air, with an increasing amount based on how high the target is off the ground, up to 10 blocks. Targets on the ground take no damage.";
   }

   @Override
   public int getDefaultManaCost() {
      return 50;
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.ELEMENTAL_AIR});
   }

   @Override
   public SpellTier defaultTier() {
      return SpellTier.TWO;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(new AbstractAugment[]{AugmentDampen.INSTANCE, AugmentAmplify.INSTANCE});
   }
}
