package com.ilexiconn.llibrary.server.network;

import com.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class AnimationMessage {
   private int entityID;
   private int index;

   public AnimationMessage() {
   }

   public AnimationMessage(int entityID, int index) {
      this.entityID = entityID;
      this.index = index;
   }

   public static void serialize(AnimationMessage message, FriendlyByteBuf buf) {
      buf.m_130130_(message.entityID);
      buf.m_130130_(message.index);
   }

   public static AnimationMessage deserialize(FriendlyByteBuf buf) {
      AnimationMessage message = new AnimationMessage();
      message.entityID = buf.m_130242_();
      message.index = buf.m_130242_();
      return message;
   }

   public static class Handler implements BiConsumer<AnimationMessage, Supplier<Context>> {
      public void accept(AnimationMessage message, Supplier<Context> contextSupplier) {
         Context context = contextSupplier.get();
         context.enqueueWork(() -> {
            IAnimatedEntity entity = (IAnimatedEntity)Minecraft.m_91087_().f_91073_.m_6815_(message.entityID);
            if (entity != null) {
               if (message.index == -1) {
                  entity.setAnimation(IAnimatedEntity.NO_ANIMATION);
               } else {
                  entity.setAnimation(entity.getAnimations()[message.index]);
               }

               entity.setAnimationTick(0);
            }
         });
         context.setPacketHandled(true);
      }
   }
}
