package immersive_armors.forge.cobalt.network;

import immersive_armors.cobalt.network.Message;
import immersive_armors.cobalt.network.NetworkHandler;
import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandlerImpl extends NetworkHandler.Impl {
   private static final String PROTOCOL_VERSION = "1";
   private final SimpleChannel channel = NetworkRegistry.newSimpleChannel(new ResourceLocation("ic_ia", "main"), () -> "1", "1"::equals, "1"::equals);
   private int id = 0;

   @Override
   public <T extends Message> void registerMessage(Class<T> msg, Function<FriendlyByteBuf, T> constructor) {
      this.channel.registerMessage(this.id++, msg, Message::encode, constructor, (m, ctx) -> {
         ((Context)ctx.get()).enqueueWork(() -> {
            ServerPlayer sender = ((Context)ctx.get()).getSender();
            m.receive(sender);
         });
         ((Context)ctx.get()).setPacketHandled(true);
      });
   }

   @Override
   public void sendToServer(Message m) {
      this.channel.sendToServer(m);
   }

   @Override
   public void sendToPlayer(Message m, ServerPlayer e) {
      this.channel.send(PacketDistributor.PLAYER.with(() -> e), m);
   }
}
