package com.bobmowzie.mowziesmobs.server.message.mouse;

import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ability.PlayerAbility;
import com.bobmowzie.mowziesmobs.server.capability.AbilityCapability;
import com.bobmowzie.mowziesmobs.server.capability.CapabilityHandler;
import com.bobmowzie.mowziesmobs.server.capability.PlayerCapability;
import com.bobmowzie.mowziesmobs.server.power.Power;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent.Context;

public class MessageLeftMouseUp {
   public static void serialize(MessageLeftMouseUp message, FriendlyByteBuf buf) {
   }

   public static MessageLeftMouseUp deserialize(FriendlyByteBuf buf) {
      return new MessageLeftMouseUp();
   }

   public static final class Handler implements BiConsumer<MessageLeftMouseUp, Supplier<Context>> {
      public void accept(MessageLeftMouseUp message, Supplier<Context> contextSupplier) {
         Context context = contextSupplier.get();
         ServerPlayer player = context.getSender();
         context.enqueueWork(() -> this.accept(message, player));
         context.setPacketHandled(true);
      }

      private void accept(MessageLeftMouseUp message, ServerPlayer player) {
         if (player != null) {
            PlayerCapability.IPlayerCapability capability = CapabilityHandler.getCapability(player, CapabilityHandler.PLAYER_CAPABILITY);
            if (capability != null) {
               capability.setMouseLeftDown(false);
               Power[] powers = capability.getPowers();

               for (int i = 0; i < powers.length; i++) {
                  powers[i].onLeftMouseUp(player);
               }
            }

            AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(player);
            if (abilityCapability != null) {
               for (Ability ability : abilityCapability.getAbilities()) {
                  if (ability instanceof PlayerAbility) {
                     ((PlayerAbility)ability).onLeftMouseUp(player);
                  }
               }
            }
         }
      }
   }
}
