package com.hollingsworth.arsnouveau.common.event;

import com.hollingsworth.arsnouveau.api.event.SpellDamageEvent;
import com.hollingsworth.arsnouveau.api.perk.IPerk;
import com.hollingsworth.arsnouveau.api.perk.IPerkHolder;
import com.hollingsworth.arsnouveau.api.perk.PerkInstance;
import com.hollingsworth.arsnouveau.api.util.PerkUtil;
import com.hollingsworth.arsnouveau.common.perk.TotemPerk;
import com.hollingsworth.arsnouveau.common.perk.VampiricPerk;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot.Type;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.level.SleepFinishedTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
   modid = "ars_nouveau"
)
public class PerkEvents {
   @SubscribeEvent
   public static void equipmentChangedEvent(LivingEquipmentChangeEvent event) {
      if (!event.getEntity().f_19853_.f_46443_ && event.getEntity() instanceof Player player) {
         if (event.getSlot().m_20743_() != Type.ARMOR) {
            return;
         }

         List<PerkInstance> perkInstances = PerkUtil.getPerksFromItem(event.getFrom());
         List<PerkInstance> toInstances = PerkUtil.getPerksFromItem(event.getTo());
         if (perkInstances.equals(toInstances)) {
            return;
         }

         List<IPerk> playerPerks = new ArrayList<>(PerkUtil.getPerksFromPlayer(player).stream().map(PerkInstance::getPerk).toList());
         List<IPerk> itemPerks = PerkUtil.getPerksFromItem(event.getTo()).stream().map(PerkInstance::getPerk).toList();

         for (IPerk perk : itemPerks) {
            playerPerks.remove(perk);
         }

         for (IPerk equippedPerks : playerPerks) {
            if (itemPerks.contains(equippedPerks)) {
               PortUtil.sendMessageNoSpam(player, Component.m_237115_("ars_nouveau.perks.duplicated"));
               return;
            }
         }
      }
   }

   @SubscribeEvent
   public static void spellDamageEvent(SpellDamageEvent.Post event) {
      if (event.caster instanceof Player player) {
         int vampLevel = PerkUtil.countForPerk(VampiricPerk.INSTANCE, player);
         if (vampLevel > 0) {
            float healAmount = event.damage * 0.2F * (float)vampLevel;
            player.m_5634_(healAmount);
         }
      }
   }

   @SubscribeEvent
   public static void totemEvent(LivingDeathEvent event) {
      LivingEntity entity = event.getEntity();
      if (entity instanceof Player player) {
         IPerkHolder<ItemStack> holder = PerkUtil.getHolderForPerk(TotemPerk.INSTANCE, player);
         if (holder == null) {
            return;
         }

         TotemPerk.Data perkData = new TotemPerk.Data(holder);
         if (!perkData.isActive()) {
            return;
         }

         entity.m_21153_(1.0F);
         entity.m_21219_();
         entity.m_7292_(new MobEffectInstance(MobEffects.f_19605_, 900, 1));
         entity.m_7292_(new MobEffectInstance(MobEffects.f_19617_, 100, 1));
         entity.m_7292_(new MobEffectInstance(MobEffects.f_19607_, 800, 0));
         entity.f_19853_.m_7605_(entity, (byte)35);
         perkData.setActive(false);
         PortUtil.sendMessage(player, Component.m_237115_("ars_nouveau.totem_perk.trigger"));
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void sleepEvent(SleepFinishedTimeEvent event) {
      for (Player p : event.getLevel().m_6907_()) {
         IPerkHolder<ItemStack> holder = PerkUtil.getHolderForPerk(TotemPerk.INSTANCE, p);
         if (holder != null) {
            TotemPerk.Data perkData = new TotemPerk.Data(holder);
            perkData.setActive(true);
            PortUtil.sendMessage(p, Component.m_237115_("ars_nouveau.totem_perk.active"));
         }
      }
   }
}
