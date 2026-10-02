package com.github.L_Ender.cataclysm.message;

import com.github.L_Ender.cataclysm.capabilities.ParryCapability;
import com.github.L_Ender.cataclysm.init.ModCapabilities;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkEvent.Context;

public class MessageParryFrame {
   private final int entityID;
   private final int frame;

   public MessageParryFrame(int id, ParryCapability.IParryCapability cap) {
      this.entityID = id;
      this.frame = cap.getParryFrame();
   }

   public MessageParryFrame(Entity entity, ParryCapability.IParryCapability cap) {
      this(entity.m_19879_(), cap);
   }

   public MessageParryFrame(FriendlyByteBuf buf) {
      this.entityID = buf.readInt();
      this.frame = buf.readInt();
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeInt(this.entityID);
      buf.writeInt(this.frame);
   }

   public static class Handler {
      public static boolean onMessage(MessageParryFrame message, Supplier<Context> ctx) {
         ctx.get().enqueueWork(() -> {
            Entity entity = Minecraft.m_91087_().f_91073_.m_6815_(message.entityID);
            if (entity instanceof LivingEntity) {
               entity.getCapability(ModCapabilities.PARRY_CAPABILITY).ifPresent(cap -> cap.setParryFrame(message.frame));
            }
         });
         ctx.get().setPacketHandled(true);
         return true;
      }
   }
}
