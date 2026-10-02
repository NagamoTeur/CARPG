package com.bobmowzie.mowziesmobs.server.message;

import com.bobmowzie.mowziesmobs.server.capability.CapabilityHandler;
import com.bobmowzie.mowziesmobs.server.capability.FrozenCapability;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkEvent.Context;

public class MessageFreezeEffect {
   private int entityID;
   private boolean isFrozen;

   public MessageFreezeEffect() {
   }

   public MessageFreezeEffect(LivingEntity entity, boolean activate) {
      this.entityID = entity.m_19879_();
      this.isFrozen = activate;
   }

   public static void serialize(MessageFreezeEffect message, FriendlyByteBuf buf) {
      buf.m_130130_(message.entityID);
      buf.writeBoolean(message.isFrozen);
   }

   public static MessageFreezeEffect deserialize(FriendlyByteBuf buf) {
      MessageFreezeEffect message = new MessageFreezeEffect();
      message.entityID = buf.m_130242_();
      message.isFrozen = buf.readBoolean();
      return message;
   }

   public static class Handler implements BiConsumer<MessageFreezeEffect, Supplier<Context>> {
      public void accept(MessageFreezeEffect message, Supplier<Context> contextSupplier) {
         Context context = contextSupplier.get();
         context.enqueueWork(() -> {
            if (Minecraft.m_91087_().f_91073_ != null && Minecraft.m_91087_().f_91073_.m_6815_(message.entityID) instanceof LivingEntity living) {
               FrozenCapability.IFrozenCapability livingCapability = CapabilityHandler.getCapability(living, CapabilityHandler.FROZEN_CAPABILITY);
               if (livingCapability != null) {
                  if (message.isFrozen) {
                     livingCapability.onFreeze(living);
                  } else {
                     livingCapability.onUnfreeze(living);
                  }
               }
            }
         });
         context.setPacketHandled(true);
      }
   }
}
