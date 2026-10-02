package com.bobmowzie.mowziesmobs.server.message;

import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.capability.AbilityCapability;
import com.bobmowzie.mowziesmobs.server.capability.CapabilityHandler;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkEvent.Context;

public class MessageInterruptAbility {
   private int entityID;
   private int index;

   public MessageInterruptAbility() {
   }

   public MessageInterruptAbility(int entityID, int index) {
      this.entityID = entityID;
      this.index = index;
   }

   public static void serialize(MessageInterruptAbility message, FriendlyByteBuf buf) {
      buf.m_130130_(message.entityID);
      buf.m_130130_(message.index);
   }

   public static MessageInterruptAbility deserialize(FriendlyByteBuf buf) {
      MessageInterruptAbility message = new MessageInterruptAbility();
      message.entityID = buf.m_130242_();
      message.index = buf.m_130242_();
      return message;
   }

   public static class Handler implements BiConsumer<MessageInterruptAbility, Supplier<Context>> {
      public void accept(MessageInterruptAbility message, Supplier<Context> contextSupplier) {
         Context context = contextSupplier.get();
         context.enqueueWork(() -> {
            LivingEntity entity = (LivingEntity)Minecraft.m_91087_().f_91073_.m_6815_(message.entityID);
            if (entity != null) {
               AbilityCapability.IAbilityCapability abilityCapability = CapabilityHandler.getCapability(entity, CapabilityHandler.ABILITY_CAPABILITY);
               if (abilityCapability != null) {
                  AbilityType<?, ?> abilityType = abilityCapability.getAbilityTypesOnEntity(entity)[message.index];
                  Ability instance = abilityCapability.getAbilityMap().get(abilityType);
                  if (instance.isUsing()) {
                     instance.interrupt();
                  }
               }
            }
         });
         context.setPacketHandled(true);
      }
   }
}
