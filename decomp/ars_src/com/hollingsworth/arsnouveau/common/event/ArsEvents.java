package com.hollingsworth.arsnouveau.common.event;

import com.hollingsworth.arsnouveau.api.event.DispelEvent;
import com.hollingsworth.arsnouveau.api.event.ManaRegenCalcEvent;
import com.hollingsworth.arsnouveau.api.event.SpellCastEvent;
import com.hollingsworth.arsnouveau.api.event.SpellDamageEvent;
import com.hollingsworth.arsnouveau.api.event.SpellResolveEvent;
import com.hollingsworth.arsnouveau.api.perk.PerkAttributes;
import com.hollingsworth.arsnouveau.common.block.tile.GhostWeaveTile;
import com.hollingsworth.arsnouveau.common.block.tile.SpellSensorTile;
import com.hollingsworth.arsnouveau.common.potions.ModPotions;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectInvisibility;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
   modid = "ars_nouveau"
)
public class ArsEvents {
   @SubscribeEvent
   public static void castEvent(SpellCastEvent castEvent) {
      SpellSensorTile.onSpellCast(castEvent);
   }

   @SubscribeEvent
   public static void regenCalc(ManaRegenCalcEvent e) {
      if (e.getEntity() != null && e.getEntity().m_21023_((MobEffect)ModPotions.HEX_EFFECT.get())) {
         e.setRegen(e.getRegen() / 2.0);
      }
   }

   @SubscribeEvent
   public static void spellCalc(SpellDamageEvent.Pre e) {
      if (e.caster != null) {
         if (e.caster.m_21023_((MobEffect)ModPotions.SPELL_DAMAGE_EFFECT.get())) {
            e.damage = e.damage + 1.5F * (float)(e.caster.m_21124_((MobEffect)ModPotions.SPELL_DAMAGE_EFFECT.get()).m_19564_() + 1);
         }

         if (e.caster.m_21204_().m_22171_((Attribute)PerkAttributes.SPELL_DAMAGE_BONUS.get())) {
            e.damage = (float)((double)e.damage + e.caster.m_21133_((Attribute)PerkAttributes.SPELL_DAMAGE_BONUS.get()));
         }
      }
   }

   @SubscribeEvent
   public static void spellResolve(SpellResolveEvent.Post e) {
      SpellSensorTile.onSpellResolve(e);
      if (e.spell.recipe.contains(EffectInvisibility.INSTANCE)
         && e.rayTraceResult instanceof BlockHitResult blockHitResult
         && e.world.m_7702_(blockHitResult.m_82425_()) instanceof GhostWeaveTile ghostWeaveTile) {
         ghostWeaveTile.setVisibility(true);
      }
   }

   @SubscribeEvent
   public static void dispelEvent(DispelEvent e) {
      if (e.rayTraceResult instanceof BlockHitResult blockHitResult && e.world.m_7702_(blockHitResult.m_82425_()) instanceof GhostWeaveTile ghostWeaveTile) {
         ghostWeaveTile.setVisibility(false);
      }
   }
}
