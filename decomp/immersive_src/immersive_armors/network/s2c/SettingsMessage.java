package immersive_armors.network.s2c;

import immersive_armors.Main;
import immersive_armors.cobalt.network.Message;
import immersive_armors.config.Config;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class SettingsMessage extends Message {
   public final Config config;

   public SettingsMessage() {
      Main.setSharedConfig(Config.getInstance());
      this.config = Config.getInstance();
   }

   public SettingsMessage(FriendlyByteBuf b) {
      this.config = Config.fromJsonString(b.m_130277_());
   }

   @Override
   public void encode(FriendlyByteBuf b) {
      b.m_130070_(this.config.toJsonString());
   }

   @Override
   public void receive(Player e) {
      Main.networkManager.handleSettingsMessage(this);
   }
}
