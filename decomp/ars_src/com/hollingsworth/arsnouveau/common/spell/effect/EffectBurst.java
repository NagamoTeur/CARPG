package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAOE;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDampen;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentSensitive;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.jetbrains.annotations.NotNull;

public class EffectBurst extends AbstractEffect {
   public static final EffectBurst INSTANCE = new EffectBurst("burst", "Burst");

   public EffectBurst(String tag, String description) {
      super(tag, description);
   }

   @Override
   public String getBookDescription() {
      return "Resolves the spell in a spherical area around the target. Augment with Sensitive to target blocks instead of entities and Dampen to make an empty sphere. Augment with AOE to increase the radius. ";
   }

   @Override
   public void onResolveBlock(
      BlockHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      this.makeSphere(rayTraceResult.m_82425_(), world, shooter, spellStats, spellContext, resolver);
   }

   @Override
   public void onResolveEntity(
      EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      this.makeSphere(rayTraceResult.m_82443_().m_20183_(), world, shooter, spellStats, spellContext, resolver);
   }

   public void makeSphere(BlockPos center, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver) {
      spellContext.setCanceled(true);
      if (!spellContext.getRemainingSpell().isEmpty()) {
         SpellContext newContext = resolver.spellContext.clone().withSpell(spellContext.getRemainingSpell());
         int radius = (int)(1.0 + spellStats.getAoeMultiplier());
         Predicate<Double> Sphere = spellStats.hasBuff(AugmentDampen.INSTANCE)
            ? distance -> distance <= (double)radius + 0.5 && distance >= (double)radius - 0.5
            : distance -> distance <= (double)radius + 0.5;
         if (spellStats.isSensitive()) {
            for (BlockPos pos : BlockPos.m_121925_(center, radius, radius, radius)) {
               if (Sphere.test(BlockUtil.distanceFromCenter(pos, center))) {
                  pos = pos.m_7949_();
                  resolver.getNewResolver(newContext.clone())
                     .onResolveEffect(
                        world,
                        new BlockHitResult(new Vec3((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_()), Direction.UP, pos, false)
                     );
               }
            }
         } else {
            for (LivingEntity entity : world.m_45976_(LivingEntity.class, new AABB(center).m_82377_((double)radius, (double)radius, (double)radius))) {
               if (Sphere.test(BlockUtil.distanceFromCenter(entity.m_20183_(), center))) {
                  resolver.getNewResolver(newContext.clone()).onResolveEffect(world, new EntityHitResult(entity));
               }
            }
         }
      }
   }

   @Override
   public void buildConfig(Builder builder) {
      super.buildConfig(builder);
      this.PER_SPELL_LIMIT = builder.comment("The maximum number of times this glyph may appear in a single spell").defineInRange("per_spell_limit", 1, 1, 1);
   }

   @Override
   public int getDefaultManaCost() {
      return 500;
   }

   @Override
   public SpellTier defaultTier() {
      return SpellTier.THREE;
   }

   @NotNull
   @Override
   protected Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(new AbstractAugment[]{AugmentAOE.INSTANCE, AugmentSensitive.INSTANCE, AugmentDampen.INSTANCE});
   }

   @Override
   protected void addDefaultInvalidCombos(Set<ResourceLocation> defaults) {
      defaults.add(EffectLinger.INSTANCE.getRegistryName());
      defaults.add(EffectWall.INSTANCE.getRegistryName());
   }
}
