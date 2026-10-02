package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.event.DelayedSpellEvent;
import com.hollingsworth.arsnouveau.api.event.EventQueue;
import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketClientDelayEffect;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDurationDown;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentExtendTime;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.jetbrains.annotations.NotNull;

public class EffectDelay extends AbstractEffect {
   public static EffectDelay INSTANCE = new EffectDelay();

   private EffectDelay() {
      super(GlyphLib.EffectDelayID, "Delay");
   }

   public void sendPacket(
      Level world,
      HitResult rayTraceResult,
      @Nullable LivingEntity shooter,
      SpellContext spellContext,
      SpellStats spellStats,
      BlockHitResult blockResult,
      Entity hitEntity
   ) {
      spellContext.setCanceled(true);
      if (spellContext.getCurrentIndex() < spellContext.getSpell().recipe.size()) {
         Spell newSpell = spellContext.getRemainingSpell();
         SpellContext newContext = spellContext.clone().withSpell(newSpell);
         int duration = (Integer)this.GENERIC_INT.get() + (Integer)this.EXTEND_TIME.get() * spellStats.getBuffCount(AugmentExtendTime.INSTANCE) * 20;
         int decreasedTime = (Integer)this.EXTEND_TIME.get() * 10 * spellStats.getBuffCount(AugmentDurationDown.INSTANCE);
         duration -= decreasedTime;
         EventQueue.getServerInstance().addEvent(new DelayedSpellEvent(duration, newSpell, rayTraceResult, world, shooter, newContext));
         Networking.sendToNearby(
            world,
            new BlockPos(this.safelyGetHitPos(rayTraceResult)),
            new PacketClientDelayEffect(duration, shooter, newSpell, newContext, blockResult, hitEntity)
         );
      }
   }

   @Override
   public void onResolveBlock(
      BlockHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      this.sendPacket(world, rayTraceResult, shooter, spellContext, spellStats, rayTraceResult, null);
   }

   @Override
   public void onResolveEntity(
      EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      this.sendPacket(world, rayTraceResult, shooter, spellContext, spellStats, null, rayTraceResult.m_82443_());
   }

   @Override
   public void buildConfig(Builder builder) {
      super.buildConfig(builder);
      this.addExtendTimeConfig(builder, 1);
      this.addGenericInt(builder, 20, "Base duration in ticks.", "base_duration");
   }

   @Override
   public int getDefaultManaCost() {
      return 0;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(new AbstractAugment[]{AugmentExtendTime.INSTANCE, AugmentDurationDown.INSTANCE});
   }

   @Override
   public String getBookDescription() {
      return "Delays the resolution of effects placed to the right of this spell for a few moments. The delay may be increased with the Extend Time augment, or decreased with Duration Down.";
   }

   @Override
   public SpellTier defaultTier() {
      return SpellTier.ONE;
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.MANIPULATION});
   }
}
