package immersive_armors.cobalt.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public abstract class Message {
   protected Message() {
   }

   public abstract void encode(FriendlyByteBuf var1);

   public abstract void receive(Player var1);
}
