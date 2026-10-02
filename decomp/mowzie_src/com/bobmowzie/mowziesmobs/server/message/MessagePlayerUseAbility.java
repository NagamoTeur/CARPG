package com.bobmowzie.mowziesmobs.server.message;

import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.capability.AbilityCapability;
import com.bobmowzie.mowziesmobs.server.capability.CapabilityHandler;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent.Context;

public class MessagePlayerUseAbility {
   private int index;

   public MessagePlayerUseAbility() {
   }

   public MessagePlayerUseAbility(int index) {
      this.index = index;
   }

   public static void serialize(MessagePlayerUseAbility message, FriendlyByteBuf buf) {
      buf.m_130130_(message.index);
   }

   public static MessagePlayerUseAbility deserialize(FriendlyByteBuf buf) {
      MessagePlayerUseAbility message = new MessagePlayerUseAbility();
      message.index = buf.m_130242_();
      return message;
   }

   public static class Handler implements BiConsumer<MessagePlayerUseAbility, Supplier<Context>> {
      public void accept(MessagePlayerUseAbility message, Supplier<Context> contextSupplier) {
         Context context = contextSupplier.get();
         ServerPlayer player = context.getSender();
         context.enqueueWork(() -> {
            AbilityCapability.IAbilityCapability abilityCapability = CapabilityHandler.getCapability(player, CapabilityHandler.ABILITY_CAPABILITY);
            if (abilityCapability != null) {
               AbilityHandler.INSTANCE.sendAbilityMessage(player, abilityCapability.getAbilityTypesOnEntity(player)[message.index]);
            }
         });
         context.setPacketHandled(true);
      }
   }
}
