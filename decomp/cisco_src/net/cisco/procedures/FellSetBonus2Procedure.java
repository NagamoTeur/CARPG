package net.cisco.procedures;

import javax.annotation.Nullable;
import net.cisco.init.CiscoModModItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class FellSetBonus2Procedure {
   @SubscribeEvent
   public static void onEntityAttacked(LivingAttackEvent event) {
      if (event != null && event.getEntity() != null) {
         execute(event, event.getEntity(), event.getSource().m_7639_(), (double)event.getAmount());
      }
   }

   public static void execute(Entity entity, Entity sourceentity, double amount) {
      execute(null, entity, sourceentity, amount);
   }

   private static void execute(@Nullable Event event, Entity entity, Entity sourceentity, double amount) {
      if (entity != null && sourceentity != null) {
         if ((entity instanceof LivingEntity _entGetArmorxxx ? _entGetArmorxxx.m_6844_(EquipmentSlot.FEET) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.FELL_KING_ARMOR_BOOTS.get()
            && (entity instanceof LivingEntity _entGetArmorxx ? _entGetArmorxx.m_6844_(EquipmentSlot.LEGS) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.FELL_KING_ARMOR_LEGGINGS.get()
            && (entity instanceof LivingEntity _entGetArmorx ? _entGetArmorx.m_6844_(EquipmentSlot.CHEST) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.FELL_KING_ARMOR_CHESTPLATE.get()
            && (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.m_6844_(EquipmentSlot.HEAD) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.FELL_KING_ARMOR_HELMET.get()
            && sourceentity instanceof LivingEntity _entity) {
            _entity.m_6469_(new DamageSource("Sokkvabekkr").m_19380_(), (float)(amount - amount / 1.4));
         }
      }
   }
}
