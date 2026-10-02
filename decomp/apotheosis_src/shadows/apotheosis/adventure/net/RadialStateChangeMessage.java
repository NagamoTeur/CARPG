package shadows.apotheosis.adventure.net;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent.Context;
import shadows.apotheosis.adventure.affix.effect.RadialAffix;
import shadows.placebo.network.MessageHelper;
import shadows.placebo.network.MessageProvider;

public class RadialStateChangeMessage implements MessageProvider<RadialStateChangeMessage> {
   public void write(RadialStateChangeMessage radialStateChangeMessage, FriendlyByteBuf friendlyByteBuf) {
   }

   public RadialStateChangeMessage read(FriendlyByteBuf friendlyByteBuf) {
      return new RadialStateChangeMessage();
   }

   public void handle(RadialStateChangeMessage radialStateChangeMessage, Supplier<Context> ctx) {
      MessageHelper.handlePacket(() -> () -> {
            Player player = ctx.get().getSender();
            if (player != null) {
               RadialAffix.toggleRadialState(player);
            }
         }, ctx);
   }
}
