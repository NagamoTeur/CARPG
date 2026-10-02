package com.bobmowzie.mowziesmobs.server.message;

import com.bobmowzie.mowziesmobs.server.entity.sculptor.EntitySculptor;
import com.bobmowzie.mowziesmobs.server.inventory.ContainerSculptorTrade;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent.Context;

public class MessageSculptorTrade {
   private int entityID;

   public MessageSculptorTrade() {
   }

   public MessageSculptorTrade(LivingEntity sender) {
      this.entityID = sender.m_19879_();
   }

   public static void serialize(MessageSculptorTrade message, FriendlyByteBuf buf) {
      buf.m_130130_(message.entityID);
   }

   public static MessageSculptorTrade deserialize(FriendlyByteBuf buf) {
      MessageSculptorTrade message = new MessageSculptorTrade();
      message.entityID = buf.m_130242_();
      return message;
   }

   public static class Handler implements BiConsumer<MessageSculptorTrade, Supplier<Context>> {
      public void accept(MessageSculptorTrade message, Supplier<Context> contextSupplier) {
         Context context = contextSupplier.get();
         ServerPlayer player = context.getSender();
         context.enqueueWork(() -> {
            if (player != null) {
               if (!(player.f_19853_.m_6815_(message.entityID) instanceof EntitySculptor sculptor)) {
                  return;
               }

               if (sculptor.getCustomer() != player) {
                  return;
               }

               AbstractContainerMenu container = player.f_36096_;
               if (!(container instanceof ContainerSculptorTrade)) {
                  return;
               }

               boolean satisfied = sculptor.fulfillDesire(container.m_38853_(0));
               if (satisfied) {
                  ((ContainerSculptorTrade)container).returnItems();
                  container.m_38946_();
                  sculptor.sendAbilityMessage(EntitySculptor.START_TEST);
               }
            }
         });
         context.setPacketHandled(true);
      }
   }
}
