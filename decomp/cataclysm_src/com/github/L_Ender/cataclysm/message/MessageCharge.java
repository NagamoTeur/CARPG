package com.github.L_Ender.cataclysm.message;

import com.github.L_Ender.cataclysm.capabilities.ChargeCapability;
import com.github.L_Ender.cataclysm.init.ModCapabilities;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkEvent.Context;

public class MessageCharge {
   private final int entityID;
   private final boolean hasCharge;

   public MessageCharge(int id, ChargeCapability.IChargeCapability cap) {
      this.entityID = id;
      this.hasCharge = cap.isCharge();
   }

   public MessageCharge(Entity entity, ChargeCapability.IChargeCapability cap) {
      this(entity.m_19879_(), cap);
   }

   public MessageCharge(FriendlyByteBuf buf) {
      this.entityID = buf.readInt();
      this.hasCharge = buf.readBoolean();
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeInt(this.entityID);
      buf.writeBoolean(this.hasCharge);
   }

   public static class Handler {
      public static boolean onMessage(MessageCharge message, Supplier<Context> ctx) {
         ctx.get().enqueueWork(() -> {
            Entity entity = Minecraft.m_91087_().f_91073_.m_6815_(message.entityID);
            if (entity instanceof LivingEntity) {
               entity.getCapability(ModCapabilities.CHARGE_CAPABILITY).ifPresent(cap -> cap.setCharge(message.hasCharge));
            }
         });
         ctx.get().setPacketHandled(true);
         return true;
      }
   }
}
