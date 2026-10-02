package net.cisco.procedures;

import javax.annotation.Nullable;
import net.cisco.entity.CiscoEntity;
import net.cisco.init.CiscoModModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class CiscoOnEntityTickUpdateProcedure {
   @SubscribeEvent
   public static void onEntityTick(LivingTickEvent event) {
      execute(event, event.getEntity());
   }

   public static void execute(Entity entity) {
      execute(null, entity);
   }

   private static void execute(@Nullable Event event, Entity entity) {
      if (entity != null) {
         if (entity instanceof CiscoEntity
            && (entity instanceof LivingEntity _livEntx ? _livEntx.m_21223_() : -1.0F)
               < (entity instanceof LivingEntity _livEnt ? _livEnt.m_21233_() : -1.0F) / 2.0F) {
            if (entity instanceof LivingEntity _entity) {
               ItemStack _setstack = new ItemStack((ItemLike)CiscoModModItems.NIGHTFALL.get());
               _setstack.m_41764_(1);
               _entity.m_21008_(InteractionHand.MAIN_HAND, _setstack);
               if (_entity instanceof Player _player) {
                  _player.m_150109_().m_6596_();
               }
            }

            if (entity instanceof Player _player) {
               _player.m_150109_().f_35975_.set(0, new ItemStack((ItemLike)CiscoModModItems.FALLEN_HERO_ARMOR_BOOTS.get()));
               _player.m_150109_().m_6596_();
            } else if (entity instanceof LivingEntity _living) {
               _living.m_8061_(EquipmentSlot.FEET, new ItemStack((ItemLike)CiscoModModItems.FALLEN_HERO_ARMOR_BOOTS.get()));
            }

            if (entity instanceof Player _player) {
               _player.m_150109_().f_35975_.set(1, new ItemStack((ItemLike)CiscoModModItems.FALLEN_HERO_ARMOR_LEGGINGS.get()));
               _player.m_150109_().m_6596_();
            } else if (entity instanceof LivingEntity _living) {
               _living.m_8061_(EquipmentSlot.LEGS, new ItemStack((ItemLike)CiscoModModItems.FALLEN_HERO_ARMOR_LEGGINGS.get()));
            }

            if (entity instanceof Player _player) {
               _player.m_150109_().f_35975_.set(2, new ItemStack((ItemLike)CiscoModModItems.FALLEN_HERO_ARMOR_CHESTPLATE.get()));
               _player.m_150109_().m_6596_();
            } else if (entity instanceof LivingEntity _living) {
               _living.m_8061_(EquipmentSlot.CHEST, new ItemStack((ItemLike)CiscoModModItems.FALLEN_HERO_ARMOR_CHESTPLATE.get()));
            }

            if (entity instanceof Player _player) {
               _player.m_150109_().f_35975_.set(3, new ItemStack((ItemLike)CiscoModModItems.FALLEN_HERO_ARMOR_HELMET.get()));
               _player.m_150109_().m_6596_();
            } else if (entity instanceof LivingEntity _living) {
               _living.m_8061_(EquipmentSlot.HEAD, new ItemStack((ItemLike)CiscoModModItems.FALLEN_HERO_ARMOR_HELMET.get()));
            }
         }
      }
   }
}
