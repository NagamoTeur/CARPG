package com.bobmowzie.mowziesmobs.server.message;

import com.bobmowzie.mowziesmobs.server.capability.CapabilityHandler;
import com.bobmowzie.mowziesmobs.server.capability.LivingCapability;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkEvent.Context;

public class MessageSunblockEffect {
   private int entityID;
   private boolean hasSunblock;

   public MessageSunblockEffect() {
   }

   public MessageSunblockEffect(LivingEntity entity, boolean activate) {
      this.entityID = entity.m_19879_();
      this.hasSunblock = activate;
   }

   public static void serialize(MessageSunblockEffect message, FriendlyByteBuf buf) {
      buf.m_130130_(message.entityID);
      buf.writeBoolean(message.hasSunblock);
   }

   public static MessageSunblockEffect deserialize(FriendlyByteBuf buf) {
      MessageSunblockEffect message = new MessageSunblockEffect();
      message.entityID = buf.m_130242_();
      message.hasSunblock = buf.readBoolean();
      return message;
   }

   public static class Handler implements BiConsumer<MessageSunblockEffect, Supplier<Context>> {
      public void accept(MessageSunblockEffect message, Supplier<Context> contextSupplier) {
         Context context = contextSupplier.get();
         context.enqueueWork(() -> {
            if (Minecraft.m_91087_().f_91073_ != null && Minecraft.m_91087_().f_91073_.m_6815_(message.entityID) instanceof LivingEntity living) {
               LivingCapability.ILivingCapability livingCapability = CapabilityHandler.getCapability(living, CapabilityHandler.LIVING_CAPABILITY);
               if (livingCapability != null) {
                  livingCapability.setHasSunblock(message.hasSunblock);
               }
            }
         });
         context.setPacketHandled(true);
      }
   }
}
