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
import com.hollingsworth.arsnouveau.api.util.SpellUtil;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDampen;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentExtract;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentFortune;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentPierce;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.NotNull;

public class EffectCut extends AbstractEffect implements IDamageEffect {
   public static EffectCut INSTANCE = new EffectCut();

   private EffectCut() {
      super(GlyphLib.EffectCutID, "Cut");
   }

   @Override
   public boolean canDamage(LivingEntity shooter, SpellStats stats, SpellContext spellContext, SpellResolver resolver, @NotNull Entity entity) {
      return IDamageEffect.super.canDamage(shooter, stats, spellContext, resolver, entity) && !(entity instanceof IForgeShearable);
   }

   @Override
   public void onResolveEntity(
      EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      Entity entity = rayTraceResult.m_82443_();
      if (entity instanceof IForgeShearable shearable) {
         ItemStack shears = new ItemStack(Items.f_42574_);
         this.applyEnchantments(spellStats, shears);
         if (shearable.isShearable(shears, world, entity.m_20183_())) {
            List<ItemStack> items = shearable.onSheared(
               this.getPlayer(shooter, (ServerLevel)world), shears, world, entity.m_20183_(), spellStats.getBuffCount(AugmentFortune.INSTANCE)
            );
            items.forEach(i -> world.m_7967_(new ItemEntity(world, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), i)));
         }
      } else {
         float damage = (float)((Double)this.DAMAGE.get() + (Double)this.AMP_VALUE.get() * spellStats.getAmpMultiplier());
         this.attemptDamage(world, shooter, spellStats, spellContext, resolver, entity, this.buildDamageSource(world, shooter), damage);
      }
   }

   @Override
   public void onResolveBlock(
      BlockHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      for (BlockPos p : SpellUtil.calcAOEBlocks(
         shooter, rayTraceResult.m_82425_(), rayTraceResult, spellStats.getAoeMultiplier(), spellStats.getBuffCount(AugmentPierce.INSTANCE)
      )) {
         if (spellStats.getBuffCount(AugmentAmplify.INSTANCE) > 0) {
            this.doStrip(p, rayTraceResult, world, shooter, spellStats, spellContext, resolver);
         } else {
            this.doShear(p, rayTraceResult, world, shooter, spellStats, spellContext, resolver);
         }
      }
   }

   private boolean dupeCheck(Level world, BlockPos pos) {
      BlockEntity be = world.m_7702_(pos);
      return be != null && (world.getCapability(ForgeCapabilities.ITEM_HANDLER).isPresent() || be instanceof Container);
   }

   public void doStrip(
      BlockPos p,
      BlockHitResult rayTraceResult,
      Level world,
      @NotNull LivingEntity shooter,
      SpellStats spellStats,
      SpellContext spellContext,
      SpellResolver resolver
   ) {
      ItemStack axe = new ItemStack(Items.f_42391_);
      this.applyEnchantments(spellStats, axe);
      Player entity = ANFakePlayer.getPlayer((ServerLevel)world);
      entity.m_21008_(InteractionHand.MAIN_HAND, axe);
      if (!this.dupeCheck(world, p)) {
         entity.m_6034_((double)p.m_123341_(), (double)p.m_123342_(), (double)p.m_123343_());
         world.m_8055_(p).m_60664_(world, entity, InteractionHand.MAIN_HAND, rayTraceResult);
         axe.m_41661_(new UseOnContext(entity, InteractionHand.MAIN_HAND, rayTraceResult));
      }
   }

   public void doShear(
      BlockPos p,
      BlockHitResult rayTraceResult,
      Level world,
      @NotNull LivingEntity shooter,
      SpellStats spellStats,
      SpellContext spellContext,
      SpellResolver resolver
   ) {
      ItemStack shears = new ItemStack(Items.f_42574_);
      this.applyEnchantments(spellStats, shears);
      if (world.m_8055_(p).m_60734_() instanceof IForgeShearable shearable && shearable.isShearable(shears, world, p)) {
         List<ItemStack> items = shearable.onSheared(
            this.getPlayer(shooter, (ServerLevel)world), shears, world, p, spellStats.getBuffCount(AugmentFortune.INSTANCE)
         );
         items.forEach(i -> world.m_7967_(new ItemEntity(world, (double)p.m_123341_(), (double)p.m_123342_(), (double)p.m_123343_(), i)));
      }

      Player entity = ANFakePlayer.getPlayer((ServerLevel)world);
      entity.m_21008_(InteractionHand.MAIN_HAND, shears);
      if (!this.dupeCheck(world, p)) {
         entity.m_6034_((double)p.m_123341_(), (double)p.m_123342_(), (double)p.m_123343_());
         world.m_8055_(p).m_60664_(world, entity, InteractionHand.MAIN_HAND, rayTraceResult);
         shears.m_41661_(new UseOnContext(entity, InteractionHand.MAIN_HAND, rayTraceResult));
      }
   }

   @Override
   public void buildConfig(Builder builder) {
      super.buildConfig(builder);
      this.addDamageConfig(builder, 1.0);
      this.addAmpConfig(builder, 1.0);
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(new AbstractAugment[]{AugmentExtract.INSTANCE, AugmentFortune.INSTANCE, AugmentAmplify.INSTANCE, AugmentDampen.INSTANCE});
   }

   @Override
   protected void addDefaultAugmentLimits(Map<ResourceLocation, Integer> defaults) {
      defaults.put(AugmentAmplify.INSTANCE.getRegistryName(), 2);
   }

   @Override
   public String getBookDescription() {
      return "Simulates using shears on entities and blocks, or damages non-shearable entities for a small amount. Amplify will simulate using an Axe instead of Shears. For simulating breaking with shears, see Break and Sensitive. Costs nothing.";
   }

   @Override
   public int getDefaultManaCost() {
      return 0;
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.MANIPULATION});
   }
}
