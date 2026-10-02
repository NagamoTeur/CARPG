package net.mindoth.dreadsteel.message;

import java.util.function.Supplier;
import net.mindoth.dreadsteel.item.weapon.DreadsteelScythe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent.Context;

public class MessageSwingArm {
   public static MessageSwingArm decode(FriendlyByteBuf buf) {
      return new MessageSwingArm();
   }

   public static void encode(MessageSwingArm message, FriendlyByteBuf buf) {
   }

   public static class Handler {
      public static void handle(MessageSwingArm message, Supplier<Context> context) {
         context.get().setPacketHandled(true);
         Player player = context.get().getSender();
         if (player != null) {
            DreadsteelScythe.onLeftClick(player, player.m_21120_(InteractionHand.MAIN_HAND));
         }
      }
   }
}
