package com.hollingsworth.arsnouveau.common.spell.method;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractCastMethod;
import com.hollingsworth.arsnouveau.api.spell.CastResolveType;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketANEffect;
import java.util.Set;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class MethodSelf extends AbstractCastMethod {
   public static MethodSelf INSTANCE = new MethodSelf();

   private MethodSelf() {
      super(GlyphLib.MethodSelfID, "Self");
   }

   @Override
   public CastResolveType onCast(ItemStack stack, LivingEntity caster, Level world, SpellStats spellStats, SpellContext context, SpellResolver resolver) {
      resolver.onResolveEffect(caster.m_20193_(), new EntityHitResult(caster));
      Networking.sendToNearby(caster.f_19853_, caster, new PacketANEffect(PacketANEffect.EffectType.TIMED_HELIX, caster.m_20183_()));
      return CastResolveType.SUCCESS;
   }

   @Override
   public CastResolveType onCastOnBlock(UseOnContext context, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver) {
      Level world = context.m_43725_();
      resolver.onResolveEffect(world, new EntityHitResult(context.m_43723_()));
      Networking.sendToNearby(context.m_43725_(), context.m_43723_(), new PacketANEffect(PacketANEffect.EffectType.TIMED_HELIX, context.m_43723_().m_20183_()));
      return CastResolveType.SUCCESS;
   }

   @Override
   public CastResolveType onCastOnBlock(
      BlockHitResult blockRayTraceResult, LivingEntity caster, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      Level world = caster.f_19853_;
      resolver.onResolveEffect(world, new EntityHitResult(caster));
      Networking.sendToNearby(caster.f_19853_, caster, new PacketANEffect(PacketANEffect.EffectType.TIMED_HELIX, caster.m_20183_()));
      return CastResolveType.SUCCESS;
   }

   @Override
   public CastResolveType onCastOnEntity(
      ItemStack stack, LivingEntity playerIn, Entity target, InteractionHand hand, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      Level world = playerIn.f_19853_;
      resolver.onResolveEffect(world, new EntityHitResult(playerIn));
      Networking.sendToNearby(playerIn.f_19853_, playerIn, new PacketANEffect(PacketANEffect.EffectType.TIMED_HELIX, playerIn.m_20183_()));
      return CastResolveType.SUCCESS;
   }

   @Override
   public int getDefaultManaCost() {
      return 10;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(new AbstractAugment[0]);
   }

   @Override
   public String getBookDescription() {
      return "A spell you start with. Applies spells on the caster.";
   }

   @Override
   public boolean defaultedStarterGlyph() {
      return true;
   }
}
