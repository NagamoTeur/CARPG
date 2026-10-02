package com.bobmowzie.mowziesmobs.server.message;

import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkEvent.Context;

public class MessagePlayerAttackMob {
   private int entityID;

   public MessagePlayerAttackMob() {
   }

   public MessagePlayerAttackMob(LivingEntity target) {
      this.entityID = target.m_19879_();
   }

   public static void serialize(MessagePlayerAttackMob message, FriendlyByteBuf buf) {
      buf.m_130130_(message.entityID);
   }

   public static MessagePlayerAttackMob deserialize(FriendlyByteBuf buf) {
      MessagePlayerAttackMob message = new MessagePlayerAttackMob();
      message.entityID = buf.m_130242_();
      return message;
   }

   public static class Handler implements BiConsumer<MessagePlayerAttackMob, Supplier<Context>> {
      public void accept(MessagePlayerAttackMob message, Supplier<Context> contextSupplier) {
         Context context = contextSupplier.get();
         ServerPlayer player = context.getSender();
         context.enqueueWork(() -> {
            if (player != null) {
               Entity entity = player.f_19853_.m_6815_(message.entityID);
               if (entity != null) {
                  player.m_5706_(entity);
               }
            }
         });
         context.setPacketHandled(true);
      }
   }
}
