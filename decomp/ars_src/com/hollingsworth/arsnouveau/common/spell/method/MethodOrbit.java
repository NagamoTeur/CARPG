package com.hollingsworth.arsnouveau.common.spell.method;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractCastMethod;
import com.hollingsworth.arsnouveau.api.spell.CastResolveType;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.common.entity.EntityOrbitProjectile;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAOE;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAccelerate;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDecelerate;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDurationDown;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentExtendTime;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentPierce;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentSensitive;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentSplit;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class MethodOrbit extends AbstractCastMethod {
   public static MethodOrbit INSTANCE = new MethodOrbit();

   private MethodOrbit() {
      super(GlyphLib.MethodOrbitID, "Orbit");
   }

   public void summonProjectiles(Level world, LivingEntity shooter, SpellResolver resolver, SpellStats stats) {
      int total = 3 + stats.getBuffCount(AugmentSplit.INSTANCE);

      for (int i = 0; i < total; i++) {
         EntityOrbitProjectile wardProjectile = new EntityOrbitProjectile(world, resolver);
         wardProjectile.wardedEntity = shooter;
         wardProjectile.setOwnerID(shooter.m_19879_());
         wardProjectile.setOffset(i);
         wardProjectile.setAccelerates((int)stats.getAccMultiplier());
         wardProjectile.setAoe((float)stats.getAoeMultiplier());
         wardProjectile.extendTimes = (int)stats.getDurationMultiplier();
         wardProjectile.setTotal(total);
         wardProjectile.setColor(resolver.spellContext.getColors());
         world.m_7967_(wardProjectile);
      }
   }

   @Override
   public CastResolveType onCast(
      @Nullable ItemStack stack, LivingEntity playerEntity, Level world, SpellStats spellStats, SpellContext context, SpellResolver resolver
   ) {
      this.summonProjectiles(world, playerEntity, resolver, spellStats);
      return CastResolveType.SUCCESS;
   }

   @Override
   public CastResolveType onCastOnBlock(UseOnContext context, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver) {
      this.summonProjectiles(context.m_43725_(), context.m_43723_(), resolver, spellStats);
      return CastResolveType.SUCCESS;
   }

   @Override
   public CastResolveType onCastOnBlock(
      BlockHitResult blockRayTraceResult, LivingEntity caster, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      this.summonProjectiles(caster.f_19853_, caster, resolver, spellStats);
      return CastResolveType.SUCCESS;
   }

   @Override
   public CastResolveType onCastOnEntity(
      @Nullable ItemStack stack,
      LivingEntity caster,
      Entity target,
      InteractionHand hand,
      SpellStats spellStats,
      SpellContext spellContext,
      SpellResolver resolver
   ) {
      this.summonProjectiles(caster.f_19853_, caster, resolver, spellStats);
      return CastResolveType.SUCCESS;
   }

   @Override
   public int getDefaultManaCost() {
      return 50;
   }

   @Override
   public SpellTier defaultTier() {
      return SpellTier.THREE;
   }

   @Override
   public String getBookDescription() {
      return "Summons three orbiting projectiles around the caster that will cast a spell on any entities it may hit. Additional projectiles, their speed, radius, and duration may be augmented. Sensitive will cause Orbit to hit blocks.";
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(
         new AbstractAugment[]{
            AugmentAccelerate.INSTANCE,
            AugmentDecelerate.INSTANCE,
            AugmentAOE.INSTANCE,
            AugmentPierce.INSTANCE,
            AugmentSplit.INSTANCE,
            AugmentExtendTime.INSTANCE,
            AugmentDurationDown.INSTANCE,
            AugmentSensitive.INSTANCE
         }
      );
   }
}
