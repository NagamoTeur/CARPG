package com.bobmowzie.mowziesmobs.server.message;

import com.bobmowzie.mowziesmobs.client.ClientProxy;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.registries.ForgeRegistries;

public class MessageUpdateBossBar {
   private UUID bossID;
   private boolean remove;
   private ResourceLocation registryName;

   public MessageUpdateBossBar() {
   }

   public MessageUpdateBossBar(UUID bossID, LivingEntity entity) {
      this.bossID = bossID;
      if (entity != null) {
         this.registryName = ForgeRegistries.ENTITY_TYPES.getKey(entity.m_6095_());
         this.remove = false;
      } else {
         this.registryName = null;
         this.remove = true;
      }
   }

   public static void serialize(MessageUpdateBossBar message, FriendlyByteBuf buf) {
      buf.m_130077_(message.bossID);
      buf.writeBoolean(message.remove);
      if (!message.remove && message.registryName != null) {
         buf.m_130085_(message.registryName);
      }
   }

   public static MessageUpdateBossBar deserialize(FriendlyByteBuf buf) {
      MessageUpdateBossBar message = new MessageUpdateBossBar();
      message.bossID = buf.m_130259_();
      message.remove = buf.readBoolean();
      if (!message.remove) {
         message.registryName = buf.m_130281_();
      }

      return message;
   }

   public static class Handler implements BiConsumer<MessageUpdateBossBar, Supplier<Context>> {
      public void accept(MessageUpdateBossBar message, Supplier<Context> contextSupplier) {
         Context context = contextSupplier.get();
         context.enqueueWork(() -> {
            if (message.registryName == null) {
               ClientProxy.bossBarRegistryNames.remove(message.bossID);
            } else {
               ClientProxy.bossBarRegistryNames.put(message.bossID, message.registryName);
            }
         });
         context.setPacketHandled(true);
      }
   }
}
