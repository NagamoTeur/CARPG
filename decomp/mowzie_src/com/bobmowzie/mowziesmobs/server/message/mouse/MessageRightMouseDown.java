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

public class MessageRightMouseDown {
   public static void serialize(MessageRightMouseDown message, FriendlyByteBuf buf) {
   }

   public static MessageRightMouseDown deserialize(FriendlyByteBuf buf) {
      return new MessageRightMouseDown();
   }

   public static final class Handler implements BiConsumer<MessageRightMouseDown, Supplier<Context>> {
      public void accept(MessageRightMouseDown message, Supplier<Context> contextSupplier) {
         Context context = contextSupplier.get();
         ServerPlayer player = context.getSender();
         context.enqueueWork(() -> this.accept(message, player));
         context.setPacketHandled(true);
      }

      private void accept(MessageRightMouseDown message, ServerPlayer player) {
         if (player != null) {
            PlayerCapability.IPlayerCapability capability = CapabilityHandler.getCapability(player, CapabilityHandler.PLAYER_CAPABILITY);
            if (capability != null) {
               capability.setMouseRightDown(true);
               Power[] powers = capability.getPowers();

               for (int i = 0; i < powers.length; i++) {
                  powers[i].onRightMouseDown(player);
               }
            }

            AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(player);
            if (abilityCapability != null) {
               for (Ability ability : abilityCapability.getAbilities()) {
                  if (ability instanceof PlayerAbility) {
                     ((PlayerAbility)ability).onRightMouseDown(player);
                  }
               }
            }
         }
      }
   }
}
