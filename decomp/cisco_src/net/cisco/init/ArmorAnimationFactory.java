package net.cisco.init;

import net.cisco.item.DescendedHeroItem;
import net.cisco.item.SovereignAscendantItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import software.bernie.geckolib3.item.GeoArmorItem;

@EventBusSubscriber
public class ArmorAnimationFactory {
   @SubscribeEvent
   public static void animatedArmors(PlayerTickEvent event) {
      String animation = "";
      if (event.phase == Phase.END) {
         if (event.player.m_6844_(EquipmentSlot.HEAD).m_41720_() != ItemStack.f_41583_.m_41720_()
            && event.player.m_6844_(EquipmentSlot.HEAD).m_41720_() instanceof GeoArmorItem
            && !event.player.m_6844_(EquipmentSlot.HEAD).m_41784_().m_128461_("geckoAnim").equals("")) {
            animation = event.player.m_6844_(EquipmentSlot.HEAD).m_41784_().m_128461_("geckoAnim");
            event.player.m_6844_(EquipmentSlot.HEAD).m_41784_().m_128359_("geckoAnim", "");
            if (event.player.m_6844_(EquipmentSlot.HEAD).m_41720_() instanceof DescendedHeroItem animatable && event.player.f_19853_.m_5776_()) {
               animatable.animationprocedure = animation;
            }

            if (event.player.m_6844_(EquipmentSlot.HEAD).m_41720_() instanceof SovereignAscendantItem animatable && event.player.f_19853_.m_5776_()) {
               animatable.animationprocedure = animation;
            }
         }

         if (event.player.m_6844_(EquipmentSlot.CHEST).m_41720_() != ItemStack.f_41583_.m_41720_()
            && event.player.m_6844_(EquipmentSlot.CHEST).m_41720_() instanceof GeoArmorItem
            && !event.player.m_6844_(EquipmentSlot.CHEST).m_41784_().m_128461_("geckoAnim").equals("")) {
            animation = event.player.m_6844_(EquipmentSlot.CHEST).m_41784_().m_128461_("geckoAnim");
            event.player.m_6844_(EquipmentSlot.CHEST).m_41784_().m_128359_("geckoAnim", "");
            if (event.player.m_6844_(EquipmentSlot.CHEST).m_41720_() instanceof DescendedHeroItem animatable && event.player.f_19853_.m_5776_()) {
               animatable.animationprocedure = animation;
            }

            if (event.player.m_6844_(EquipmentSlot.CHEST).m_41720_() instanceof SovereignAscendantItem animatable && event.player.f_19853_.m_5776_()) {
               animatable.animationprocedure = animation;
            }
         }

         if (event.player.m_6844_(EquipmentSlot.LEGS).m_41720_() != ItemStack.f_41583_.m_41720_()
            && event.player.m_6844_(EquipmentSlot.LEGS).m_41720_() instanceof GeoArmorItem
            && !event.player.m_6844_(EquipmentSlot.LEGS).m_41784_().m_128461_("geckoAnim").equals("")) {
            animation = event.player.m_6844_(EquipmentSlot.LEGS).m_41784_().m_128461_("geckoAnim");
            event.player.m_6844_(EquipmentSlot.LEGS).m_41784_().m_128359_("geckoAnim", "");
            if (event.player.m_6844_(EquipmentSlot.LEGS).m_41720_() instanceof DescendedHeroItem animatable && event.player.f_19853_.m_5776_()) {
               animatable.animationprocedure = animation;
            }

            if (event.player.m_6844_(EquipmentSlot.LEGS).m_41720_() instanceof SovereignAscendantItem animatable && event.player.f_19853_.m_5776_()) {
               animatable.animationprocedure = animation;
            }
         }

         if (event.player.m_6844_(EquipmentSlot.FEET).m_41720_() != ItemStack.f_41583_.m_41720_()
            && event.player.m_6844_(EquipmentSlot.FEET).m_41720_() instanceof GeoArmorItem
            && !event.player.m_6844_(EquipmentSlot.FEET).m_41784_().m_128461_("geckoAnim").equals("")) {
            animation = event.player.m_6844_(EquipmentSlot.FEET).m_41784_().m_128461_("geckoAnim");
            event.player.m_6844_(EquipmentSlot.FEET).m_41784_().m_128359_("geckoAnim", "");
            if (event.player.m_6844_(EquipmentSlot.FEET).m_41720_() instanceof DescendedHeroItem animatable && event.player.f_19853_.m_5776_()) {
               animatable.animationprocedure = animation;
            }

            if (event.player.m_6844_(EquipmentSlot.FEET).m_41720_() instanceof SovereignAscendantItem animatable && event.player.f_19853_.m_5776_()) {
               animatable.animationprocedure = animation;
            }
         }
      }
   }
}
