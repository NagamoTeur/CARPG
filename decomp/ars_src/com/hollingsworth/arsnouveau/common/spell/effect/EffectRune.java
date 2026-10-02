package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.common.block.tile.RuneTile;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentSensitive;
import com.hollingsworth.arsnouveau.common.spell.method.MethodTouch;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class EffectRune extends AbstractEffect {
   public static EffectRune INSTANCE = new EffectRune();

   public EffectRune() {
      super(GlyphLib.EffectRuneID, "Rune");
   }

   @Override
   public void onResolveBlock(
      BlockHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      BlockPos pos = rayTraceResult.m_82425_();
      pos = rayTraceResult.m_82436_() ? pos : pos.m_121945_(rayTraceResult.m_82434_());
      spellContext.setCanceled(true);
      Spell newSpell = spellContext.getRemainingSpell();
      if (world.m_8055_(pos).m_60767_().m_76336_()) {
         if (!world.m_46739_(pos)) {
            return;
         }

         world.m_46597_(pos, BlockRegistry.RUNE_BLOCK.m_49966_());
         if (world.m_7702_(pos) instanceof RuneTile runeTile) {
            if (shooter instanceof Player) {
               runeTile.uuid = shooter.m_20148_();
            }

            runeTile.isTemporary = true;
            newSpell.recipe.add(0, MethodTouch.INSTANCE);
            runeTile.spell = newSpell;
            runeTile.isSensitive = spellStats.isSensitive();
         }
      }
   }

   @Override
   public int getDefaultManaCost() {
      return 30;
   }

   @Override
   public String getBookDescription() {
      return "Places a rune on the ground that will cast the spell on targets that touch the rune. Unlike runes placed by Runic Chalk, these runes are temporary and cannot be recharged. When using Item Pickup, items are deposited into adjacent inventories. Sensitive will cause the rune to use the Owner's inventory for pickup and usage instead. Players with Magic Find will be able to read spells inscribed on runes.";
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.MANIPULATION});
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.setOf(new AbstractAugment[]{AugmentSensitive.INSTANCE});
   }
}
