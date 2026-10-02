package com.hollingsworth.arsnouveau.common.spell.method;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractCastMethod;
import com.hollingsworth.arsnouveau.api.spell.CastResolveType;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class MethodUnderfoot extends AbstractCastMethod {
   public static MethodUnderfoot INSTANCE = new MethodUnderfoot();

   private MethodUnderfoot() {
      super(GlyphLib.MethodUnderfootID, "Underfoot");
   }

   @Override
   public CastResolveType onCast(
      @Nullable ItemStack stack, LivingEntity caster, Level world, SpellStats spellStats, SpellContext context, SpellResolver resolver
   ) {
      resolver.onResolveEffect(caster.m_20193_(), new BlockHitResult(caster.f_19825_, Direction.DOWN, caster.m_20183_().m_7495_(), true));
      return CastResolveType.SUCCESS;
   }

   @Override
   public CastResolveType onCastOnBlock(UseOnContext context, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver) {
      LivingEntity caster = context.m_43723_();
      resolver.onResolveEffect(caster.m_20193_(), new BlockHitResult(caster.f_19825_, Direction.DOWN, caster.m_20183_().m_7495_(), true));
      return CastResolveType.SUCCESS;
   }

   @Override
   public CastResolveType onCastOnBlock(
      BlockHitResult blockRayTraceResult, LivingEntity caster, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      resolver.onResolveEffect(caster.m_20193_(), new BlockHitResult(caster.f_19825_, Direction.DOWN, caster.m_20183_().m_7495_(), true));
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
      resolver.onResolveEffect(caster.m_20193_(), new BlockHitResult(caster.f_19825_, Direction.DOWN, caster.m_20183_().m_7495_(), true));
      return CastResolveType.SUCCESS;
   }

   @Override
   public int getDefaultManaCost() {
      return 5;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(new AbstractAugment[0]);
   }

   @Override
   public String getBookDescription() {
      return "Targets the spell on the block beneath the player.";
   }
}
