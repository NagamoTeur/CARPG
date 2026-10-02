package net.cisco.procedures;

import javax.annotation.Nullable;
import net.cisco.init.CiscoModModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class EagleArmorFixProcedure {
   @SubscribeEvent
   public static void onEntityJoin(EntityJoinLevelEvent event) {
      execute(event, event.getEntity());
   }

   public static void execute(Entity entity) {
      execute(null, entity);
   }

   private static void execute(@Nullable Event event, Entity entity) {
      if (entity != null) {
         if ((entity instanceof LivingEntity _entGetArmorxxx ? _entGetArmorxxx.m_6844_(EquipmentSlot.HEAD) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.GILDED_EAGLE_HELMET.get()
            && (entity instanceof LivingEntity _entGetArmorxx ? _entGetArmorxx.m_6844_(EquipmentSlot.CHEST) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.GILDED_EAGLE_CHESTPLATE.get()
            && (entity instanceof LivingEntity _entGetArmorx ? _entGetArmorx.m_6844_(EquipmentSlot.LEGS) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.GILDED_EAGLE_LEGGINGS.get()
            && (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.m_6844_(EquipmentSlot.FEET) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.GILDED_EAGLE_BOOTS.get()) {
            (entity instanceof LivingEntity _entGetArmorxxxx ? _entGetArmorxxxx.m_6844_(EquipmentSlot.HEAD) : ItemStack.f_41583_)
               .m_41784_()
               .m_128379_("eagleability", false);
            (entity instanceof LivingEntity _entGetArmorxxxxx ? _entGetArmorxxxxx.m_6844_(EquipmentSlot.CHEST) : ItemStack.f_41583_)
               .m_41784_()
               .m_128379_("eagleability", false);
            (entity instanceof LivingEntity _entGetArmorxxxxxx ? _entGetArmorxxxxxx.m_6844_(EquipmentSlot.LEGS) : ItemStack.f_41583_)
               .m_41784_()
               .m_128379_("eagleability", false);
            (entity instanceof LivingEntity _entGetArmorxxxxxxx ? _entGetArmorxxxxxxx.m_6844_(EquipmentSlot.FEET) : ItemStack.f_41583_)
               .m_41784_()
               .m_128379_("eagleability", false);
         }
      }
   }
}
