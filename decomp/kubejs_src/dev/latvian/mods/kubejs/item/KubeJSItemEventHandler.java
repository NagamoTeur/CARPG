package dev.latvian.mods.kubejs.item;

import dev.architectury.event.CompoundEventResult;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.InteractionEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.latvian.mods.kubejs.bindings.event.ItemEvents;
import dev.latvian.mods.kubejs.bindings.event.PlayerEvents;
import dev.latvian.mods.kubejs.player.InventoryChangedEventJS;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class KubeJSItemEventHandler {
   public static void init() {
      InteractionEvent.RIGHT_CLICK_ITEM.register(KubeJSItemEventHandler::rightClick);
      PlayerEvent.PICKUP_ITEM_PRE.register(KubeJSItemEventHandler::canPickUp);
      PlayerEvent.PICKUP_ITEM_POST.register(KubeJSItemEventHandler::pickup);
      PlayerEvent.DROP_ITEM.register(KubeJSItemEventHandler::drop);
      InteractionEvent.INTERACT_ENTITY.register(KubeJSItemEventHandler::entityInteract);
      PlayerEvent.CRAFT_ITEM.register(KubeJSItemEventHandler::crafted);
      PlayerEvent.SMELT_ITEM.register(KubeJSItemEventHandler::smelted);
   }

   private static CompoundEventResult<ItemStack> rightClick(Player player, InteractionHand hand) {
      if (!ItemEvents.RIGHT_CLICKED.hasListeners()) {
         return CompoundEventResult.pass();
      } else {
         ItemStack stack = player.m_21120_(hand);
         return !player.m_36335_().m_41519_(stack.m_41720_())
            ? ItemEvents.RIGHT_CLICKED.post(player, stack.m_41720_(), new ItemClickedEventJS(player, hand, stack)).archCompound()
            : CompoundEventResult.pass();
      }
   }

   private static EventResult canPickUp(Player player, ItemEntity entity, ItemStack stack) {
      return ItemEvents.CAN_PICK_UP.hasListeners()
         ? ItemEvents.CAN_PICK_UP.post(player, stack.m_41720_(), new ItemPickedUpEventJS(player, entity, stack)).arch()
         : EventResult.pass();
   }

   private static void pickup(Player player, ItemEntity entity, ItemStack stack) {
      if (ItemEvents.PICKED_UP.hasListeners()) {
         ItemEvents.PICKED_UP.post(player, stack.m_41720_(), new ItemPickedUpEventJS(player, entity, stack));
      }
   }

   private static EventResult drop(Player player, ItemEntity entity) {
      return ItemEvents.DROPPED.hasListeners()
         ? ItemEvents.DROPPED.post(player, entity.m_32055_().m_41720_(), new ItemDroppedEventJS(player, entity)).arch()
         : EventResult.pass();
   }

   private static EventResult entityInteract(Player player, Entity entity, InteractionHand hand) {
      return ItemEvents.ENTITY_INTERACTED.hasListeners()
         ? ItemEvents.ENTITY_INTERACTED.post(player, player.m_21120_(hand).m_41720_(), new ItemEntityInteractedEventJS(player, entity, hand)).arch()
         : EventResult.pass();
   }

   private static void crafted(Player player, ItemStack stack, Container grid) {
      if (!stack.m_41619_()) {
         if (ItemEvents.CRAFTED.hasListeners()) {
            ItemEvents.CRAFTED.post(player, stack.m_41720_(), new ItemCraftedEventJS(player, stack, grid));
         }

         if (PlayerEvents.INVENTORY_CHANGED.hasListeners()) {
            PlayerEvents.INVENTORY_CHANGED.post(player, stack.m_41720_(), new InventoryChangedEventJS(player, stack, -1));
         }
      }
   }

   private static void smelted(Player player, ItemStack stack) {
      if (!stack.m_41619_()) {
         if (ItemEvents.SMELTED.hasListeners()) {
            ItemEvents.SMELTED.post(player, stack.m_41720_(), new ItemSmeltedEventJS(player, stack));
         }

         if (PlayerEvents.INVENTORY_CHANGED.hasListeners()) {
            PlayerEvents.INVENTORY_CHANGED.post(player, stack.m_41720_(), new InventoryChangedEventJS(player, stack, -1));
         }
      }
   }
}
