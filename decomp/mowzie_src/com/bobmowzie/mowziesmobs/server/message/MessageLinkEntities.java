package com.bobmowzie.mowziesmobs.server.message;

import com.bobmowzie.mowziesmobs.server.entity.ILinkedEntity;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent.Context;

public class MessageLinkEntities {
   private int sourceID;
   private int targetID;

   public MessageLinkEntities() {
   }

   public MessageLinkEntities(Entity source, Entity target) {
      if (source instanceof ILinkedEntity) {
         this.sourceID = source.m_19879_();
         this.targetID = target.m_19879_();
      }
   }

   public static void serialize(MessageLinkEntities message, FriendlyByteBuf buf) {
      buf.m_130130_(message.sourceID);
      buf.m_130130_(message.targetID);
   }

   public static MessageLinkEntities deserialize(FriendlyByteBuf buf) {
      MessageLinkEntities message = new MessageLinkEntities();
      message.sourceID = buf.m_130242_();
      message.targetID = buf.m_130242_();
      return message;
   }

   public static class Handler implements BiConsumer<MessageLinkEntities, Supplier<Context>> {
      @OnlyIn(Dist.CLIENT)
      public void accept(MessageLinkEntities message, Supplier<Context> contextSupplier) {
         Context context = contextSupplier.get();
         context.enqueueWork(() -> {
            Level level = Minecraft.m_91087_().f_91073_;
            if (level != null) {
               Entity entitySource = Minecraft.m_91087_().f_91073_.m_6815_(message.sourceID);
               Entity entityTarget = Minecraft.m_91087_().f_91073_.m_6815_(message.targetID);
               if (entitySource instanceof ILinkedEntity && entityTarget != null) {
                  ((ILinkedEntity)entitySource).link(entityTarget);
               }
            }
         });
         context.setPacketHandled(true);
      }
   }
}
