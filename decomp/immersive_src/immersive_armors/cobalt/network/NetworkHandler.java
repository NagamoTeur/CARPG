package immersive_armors.cobalt.network;

import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public abstract class NetworkHandler {
   private static NetworkHandler.Impl INSTANCE;

   public static <T extends Message> void registerMessage(Class<T> msg, Function<FriendlyByteBuf, T> constructor) {
      INSTANCE.registerMessage(msg, constructor);
   }

   public static void sendToServer(Message m) {
      INSTANCE.sendToServer(m);
   }

   public static void sendToPlayer(Message m, ServerPlayer e) {
      INSTANCE.sendToPlayer(m, e);
   }

   public abstract static class Impl {
      protected Impl() {
         NetworkHandler.INSTANCE = this;
      }

      public abstract <T extends Message> void registerMessage(Class<T> var1, Function<FriendlyByteBuf, T> var2);

      public abstract void sendToServer(Message var1);

      public abstract void sendToPlayer(Message var1, ServerPlayer var2);
   }
}
