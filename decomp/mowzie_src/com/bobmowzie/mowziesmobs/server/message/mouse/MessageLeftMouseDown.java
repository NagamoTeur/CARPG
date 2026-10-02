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

public class MessageLeftMouseDown {
   public static void serialize(MessageLeftMouseDown message, FriendlyByteBuf buf) {
   }

   public static MessageLeftMouseDown deserialize(FriendlyByteBuf buf) {
      return new MessageLeftMouseDown();
   }

   public static final class Handler implements BiConsumer<MessageLeftMouseDown, Supplier<Context>> {
      public void accept(MessageLeftMouseDown message, Supplier<Context> contextSupplier) {
         Context context = contextSupplier.get();
         ServerPlayer player = context.getSender();
         context.enqueueWork(() -> this.accept(message, player));
         context.setPacketHandled(true);
      }

      private void accept(MessageLeftMouseDown message, ServerPlayer player) {
         if (player != null) {
            PlayerCapability.IPlayerCapability capability = CapabilityHandler.getCapability(player, CapabilityHandler.PLAYER_CAPABILITY);
            if (capability != null) {
               capability.setMouseLeftDown(true);
               Power[] powers = capability.getPowers();

               for (int i = 0; i < powers.length; i++) {
                  powers[i].onLeftMouseDown(player);
               }
            }

            AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(player);
            if (abilityCapability != null) {
               for (Ability ability : abilityCapability.getAbilities()) {
                  if (ability instanceof PlayerAbility) {
                     ((PlayerAbility)ability).onLeftMouseDown(player);
                  }
               }
            }
         }
      }
   }
}
