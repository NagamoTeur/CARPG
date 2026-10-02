package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.entity.IDispellable;
import com.hollingsworth.arsnouveau.api.event.DispelEvent;
import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.lib.PotionEffectTags;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.Registry;
import net.minecraft.core.HolderSet.Named;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;

public class EffectDispel extends AbstractEffect {
   public static EffectDispel INSTANCE = new EffectDispel();

   private EffectDispel() {
      super(GlyphLib.EffectDispelID, "Dispel");
   }

   @Override
   public void onResolveEntity(
      @NotNull EntityHitResult rayTraceResult,
      Level world,
      @NotNull LivingEntity shooter,
      SpellStats spellStats,
      SpellContext spellContext,
      SpellResolver resolver
   ) {
      if (rayTraceResult.m_82443_() instanceof LivingEntity entity) {
         Collection<MobEffectInstance> effects = entity.m_21220_();
         MobEffectInstance[] array = effects.toArray(new MobEffectInstance[0]);
         Optional<Named<MobEffect>> blacklist = Registry.f_122823_.m_203431_(PotionEffectTags.DISPEL_DENY);
         Optional<Named<MobEffect>> whitelist = Registry.f_122823_.m_203431_(PotionEffectTags.DISPEL_ALLOW);

         for (MobEffectInstance e : array) {
            if (e.isCurativeItem(new ItemStack(Items.f_42455_))) {
               if (!blacklist.isPresent() || !blacklist.get().m_203614_().anyMatch(effect -> effect.get() == e.m_19544_())) {
                  entity.m_21195_(e.m_19544_());
               }
            } else if (whitelist.isPresent() && whitelist.get().m_203614_().anyMatch(effect -> effect.get() == e.m_19544_())) {
               entity.m_21195_(e.m_19544_());
            }
         }

         if (!entity.m_6084_() || entity.m_21223_() <= 0.0F || entity.m_213877_()) {
            return;
         }

         if (MinecraftForge.EVENT_BUS.post(new DispelEvent.Pre(rayTraceResult, world, shooter, spellStats, spellContext))) {
            return;
         }

         if (entity instanceof IDispellable iDispellable) {
            iDispellable.onDispel(shooter);
         }

         MinecraftForge.EVENT_BUS.post(new DispelEvent.Post(rayTraceResult, world, shooter, spellStats, spellContext));
      }
   }

   @Override
   public void onResolveBlock(
      BlockHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      if (!MinecraftForge.EVENT_BUS.post(new DispelEvent.Pre(rayTraceResult, world, shooter, spellStats, spellContext))) {
         if (world.m_8055_(rayTraceResult.m_82425_()) instanceof IDispellable dispellable) {
            dispellable.onDispel(shooter);
         }

         if (world.m_7702_(rayTraceResult.m_82425_()) instanceof IDispellable dispellable) {
            dispellable.onDispel(shooter);
         }

         MinecraftForge.EVENT_BUS.post(new DispelEvent.Post(rayTraceResult, world, shooter, spellStats, spellContext));
      }
   }

   @Override
   public int getDefaultManaCost() {
      return 30;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(new AbstractAugment[0]);
   }

   @Override
   public String getBookDescription() {
      return "Removes any potion effects on the target. When used on a witch at half health, the witch will vanish in return for a Wixie shard. Will also dispel tamed summons back into their charm.";
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.ABJURATION});
   }
}
