package com.hollingsworth.arsnouveau.api.util;

import com.hollingsworth.arsnouveau.api.event.ManaRegenCalcEvent;
import com.hollingsworth.arsnouveau.api.event.MaxManaCalcEvent;
import com.hollingsworth.arsnouveau.api.mana.IManaCap;
import com.hollingsworth.arsnouveau.api.mana.IManaDiscountEquipment;
import com.hollingsworth.arsnouveau.api.perk.PerkAttributes;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.common.capability.CapabilityRegistry;
import com.hollingsworth.arsnouveau.common.enchantment.EnchantmentRegistry;
import com.hollingsworth.arsnouveau.common.potions.ModPotions;
import com.hollingsworth.arsnouveau.setup.config.ServerConfig;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.MinecraftForge;

public class ManaUtil {
   public static int getPlayerDiscounts(LivingEntity e, Spell spell) {
      if (e == null) {
         return 0;
      } else {
         AtomicInteger discounts = new AtomicInteger();
         CuriosUtil.getAllWornItems(e).ifPresent(items -> {
            for (int i = 0; i < items.getSlots(); i++) {
               ItemStack item = items.getStackInSlot(i);
               if (item.m_41720_() instanceof IManaDiscountEquipment discountItemx) {
                  discounts.addAndGet(discountItemx.getManaDiscount(item, spell));
               }
            }
         });

         for (ItemStack armor : e.m_6168_()) {
            if (armor.m_41720_() instanceof IManaDiscountEquipment discountItem) {
               discounts.addAndGet(discountItem.getManaDiscount(armor, spell));
            }
         }

         return discounts.get();
      }
   }

   public static double getCurrentMana(LivingEntity e) {
      IManaCap mana = (IManaCap)CapabilityRegistry.getMana(e).orElse(null);
      return mana == null ? 0.0 : mana.getCurrentMana();
   }

   public static ManaUtil.Mana calcMaxMana(Player e) {
      IManaCap mana = (IManaCap)CapabilityRegistry.getMana(e).orElse(null);
      if (mana == null) {
         return new ManaUtil.Mana(0, 0.0F);
      } else {
         int max = (Integer)ServerConfig.INIT_MAX_MANA.get();
         max = (int)((double)max + PerkUtil.perkValue(e, (Attribute)PerkAttributes.FLAT_MANA_BONUS.get()));

         for (ItemStack i : e.m_20158_()) {
            max += ServerConfig.MANA_BOOST_BONUS.get() * i.getEnchantmentLevel((Enchantment)EnchantmentRegistry.MANA_BOOST_ENCHANTMENT.get());
         }

         int tier = mana.getBookTier();
         int numGlyphs = mana.getGlyphBonus();
         max += numGlyphs * ServerConfig.GLYPH_MAX_BONUS.get();
         max += tier * ServerConfig.TIER_MAX_BONUS.get();
         max = (int)((double)max * PerkUtil.perkValue(e, (Attribute)PerkAttributes.MAX_MANA_BONUS.get()));
         MaxManaCalcEvent event = new MaxManaCalcEvent(e, max);
         MinecraftForge.EVENT_BUS.post(event);
         max = event.getMax();
         float reserve = event.getReserve();
         return new ManaUtil.Mana(max, reserve);
      }
   }

   public static int getMaxMana(Player e) {
      return calcMaxMana(e).getRealMax();
   }

   public static double getManaRegen(Player e) {
      IManaCap mana = (IManaCap)CapabilityRegistry.getMana(e).orElse(null);
      if (mana == null) {
         return 0.0;
      } else {
         double regen = (double)((Integer)ServerConfig.INIT_MANA_REGEN.get()).intValue();
         if (e.m_21051_((Attribute)PerkAttributes.MANA_REGEN_BONUS.get()) != null) {
            regen += e.m_21133_((Attribute)PerkAttributes.MANA_REGEN_BONUS.get());
         }

         for (ItemStack i : e.m_20158_()) {
            regen += (double)(
               ServerConfig.MANA_REGEN_ENCHANT_BONUS.get() * i.getEnchantmentLevel((Enchantment)EnchantmentRegistry.MANA_REGEN_ENCHANTMENT.get())
            );
         }

         int tier = mana.getBookTier();
         double numGlyphs = (double)mana.getGlyphBonus();
         regen += numGlyphs * ServerConfig.GLYPH_REGEN_BONUS.get();
         regen += (double)(tier * ServerConfig.TIER_REGEN_BONUS.get());
         if (e.m_21023_((MobEffect)ModPotions.MANA_REGEN_EFFECT.get())) {
            regen += (double)(ServerConfig.MANA_REGEN_POTION.get() * (1 + e.m_21124_((MobEffect)ModPotions.MANA_REGEN_EFFECT.get()).m_19564_()));
         }

         ManaRegenCalcEvent event = new ManaRegenCalcEvent(e, regen);
         MinecraftForge.EVENT_BUS.post(event);
         return event.getRegen();
      }
   }

   public static record Mana(int Max, float Reserve) {
      public int getRealMax() {
         return (int)((double)this.Max * (1.0 - (double)this.Reserve));
      }
   }
}
