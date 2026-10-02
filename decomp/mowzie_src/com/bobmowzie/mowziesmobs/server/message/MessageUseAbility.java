package com.bobmowzie.mowziesmobs.server.message;

import com.bobmowzie.mowziesmobs.server.capability.AbilityCapability;
import com.bobmowzie.mowziesmobs.server.capability.CapabilityHandler;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkEvent.Context;

public class MessageUseAbility {
   private int entityID;
   private int index;

   public MessageUseAbility() {
   }

   public MessageUseAbility(int entityID, int index) {
      this.entityID = entityID;
      this.index = index;
   }

   public static void serialize(MessageUseAbility message, FriendlyByteBuf buf) {
      buf.m_130130_(message.entityID);
      buf.m_130130_(message.index);
   }

   public static MessageUseAbility deserialize(FriendlyByteBuf buf) {
      MessageUseAbility message = new MessageUseAbility();
      message.entityID = buf.m_130242_();
      message.index = buf.m_130242_();
      return message;
   }

   public static class Handler implements BiConsumer<MessageUseAbility, Supplier<Context>> {
      public void accept(MessageUseAbility message, Supplier<Context> contextSupplier) {
         Context context = contextSupplier.get();
         context.enqueueWork(() -> {
            LivingEntity entity = (LivingEntity)Minecraft.m_91087_().f_91073_.m_6815_(message.entityID);
            if (entity != null) {
               AbilityCapability.IAbilityCapability abilityCapability = CapabilityHandler.getCapability(entity, CapabilityHandler.ABILITY_CAPABILITY);
               if (abilityCapability != null) {
                  abilityCapability.activateAbility(entity, abilityCapability.getAbilityTypesOnEntity(entity)[message.index]);
               }
            }
         });
         context.setPacketHandled(true);
      }
   }
}
