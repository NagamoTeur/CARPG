package software.bernie.ars_nouveau.geckolib3.network.messages;

import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.simple.SimpleChannel;
import software.bernie.ars_nouveau.geckolib3.GeckoLib;
import software.bernie.ars_nouveau.geckolib3.network.GeckoLibNetwork;
import software.bernie.ars_nouveau.geckolib3.network.ISyncable;

public class SyncAnimationMsg {
   private final String key;
   private final int id;
   private final int state;

   public SyncAnimationMsg(String key, int id, int state) {
      this.key = key;
      this.id = id;
      this.state = state;
   }

   public static void register(SimpleChannel channel, int id) {
      channel.registerMessage(
         id, SyncAnimationMsg.class, SyncAnimationMsg::encode, SyncAnimationMsg::decode, SyncAnimationMsg::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT)
      );
   }

   private static SyncAnimationMsg decode(FriendlyByteBuf buf) {
      String key = buf.m_130277_();
      int id = buf.m_130242_();
      int state = buf.m_130242_();
      return new SyncAnimationMsg(key, id, state);
   }

   private void encode(FriendlyByteBuf buf) {
      buf.m_130070_(this.key);
      buf.m_130130_(this.id);
      buf.m_130130_(this.state);
   }

   private void handle(Supplier<Context> sup) {
      Context ctx = sup.get();
      ctx.enqueueWork(() -> {
         ISyncable syncable = GeckoLibNetwork.getSyncable(this.key);
         if (syncable != null) {
            syncable.onAnimationSync(this.id, this.state);
         } else {
            GeckoLib.LOGGER.warn("Syncable on the server is missing on the client for " + this.key);
         }
      });
      ctx.setPacketHandled(true);
   }
}
