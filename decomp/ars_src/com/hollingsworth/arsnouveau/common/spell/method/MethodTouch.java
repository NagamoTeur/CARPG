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
import com.hollingsworth.arsnouveau.common.network.PacketAddFadingLight;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentSensitive;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class MethodTouch extends AbstractCastMethod {
   public static MethodTouch INSTANCE = new MethodTouch();

   private MethodTouch() {
      super(GlyphLib.MethodTouchID, "Touch");
   }

   @Override
   public int getDefaultManaCost() {
      return 5;
   }

   @Override
   public CastResolveType onCast(ItemStack stack, LivingEntity caster, Level world, SpellStats spellStats, SpellContext context, SpellResolver resolver) {
      return CastResolveType.FAILURE;
   }

   @Override
   public CastResolveType onCastOnBlock(UseOnContext context, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver) {
      Level world = context.m_43725_();
      BlockHitResult res = new BlockHitResult(context.m_43720_(), context.m_43719_(), context.m_8083_(), false);
      resolver.onResolveEffect(world, res);
      Networking.sendToNearby(
         context.m_43725_(), context.m_43723_(), new PacketANEffect(PacketANEffect.EffectType.BURST, res.m_82425_(), spellContext.getColors().toWrapper())
      );
      this.addFadingLight(
         context.m_43725_(), (double)res.m_82425_().m_123341_() + 0.5, (double)res.m_82425_().m_123342_() + 0.5, (double)res.m_82425_().m_123343_() + 0.5
      );
      return CastResolveType.SUCCESS;
   }

   @Override
   public CastResolveType onCastOnBlock(BlockHitResult res, LivingEntity caster, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver) {
      resolver.onResolveEffect(caster.m_20193_(), res);
      Networking.sendToNearby(
         caster.f_19853_, caster, new PacketANEffect(PacketANEffect.EffectType.BURST, res.m_82425_(), spellContext.getColors().toWrapper())
      );
      this.addFadingLight(
         caster.m_9236_(), (double)res.m_82425_().m_123341_() + 0.5, (double)res.m_82425_().m_123342_() + 0.5, (double)res.m_82425_().m_123343_() + 0.5
      );
      return CastResolveType.SUCCESS;
   }

   @Override
   public CastResolveType onCastOnEntity(
      ItemStack stack, LivingEntity caster, Entity target, InteractionHand hand, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      resolver.onResolveEffect(caster.m_20193_(), new EntityHitResult(target));
      Networking.sendToNearby(
         caster.f_19853_, caster, new PacketANEffect(PacketANEffect.EffectType.BURST, target.m_20183_(), spellContext.getColors().toWrapper())
      );
      this.addFadingLight(
         caster.m_9236_(),
         (double)target.m_20183_().m_123341_() + 0.5,
         (double)target.m_20183_().m_123342_() + 0.5,
         (double)target.m_20183_().m_123343_() + 0.5
      );
      return spellContext.getType() != SpellContext.CasterType.RUNE ? CastResolveType.SUCCESS : CastResolveType.SUCCESS_NO_EXPEND;
   }

   public void addFadingLight(Level level, double x, double y, double z) {
      Networking.sendToNearby(level, new BlockPos(x, y, z), new PacketAddFadingLight(x, y, z));
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(new AbstractAugment[]{AugmentSensitive.INSTANCE});
   }

   @Override
   public String getBookDescription() {
      return "Applies spells at the block or entity that is targeted.";
   }

   @Override
   public boolean defaultedStarterGlyph() {
      return true;
   }
}
