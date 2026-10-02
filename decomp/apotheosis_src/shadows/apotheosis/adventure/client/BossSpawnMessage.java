package shadows.apotheosis.adventure.client;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;
import org.apache.commons.lang3.mutable.MutableInt;
import shadows.placebo.network.MessageHelper;
import shadows.placebo.network.MessageProvider;

public class BossSpawnMessage implements MessageProvider<BossSpawnMessage> {
   private final BlockPos pos;
   private final int color;

   public BossSpawnMessage(BlockPos pos, int color) {
      this.pos = pos;
      this.color = color;
   }

   public void write(BossSpawnMessage msg, FriendlyByteBuf buf) {
      buf.m_130064_(msg.pos);
      buf.writeInt(msg.color);
   }

   public BossSpawnMessage read(FriendlyByteBuf buf) {
      return new BossSpawnMessage(buf.m_130135_(), buf.readInt());
   }

   public void handle(BossSpawnMessage msg, Supplier<Context> ctx) {
      MessageHelper.handlePacket(() -> () -> AdventureModuleClient.onBossSpawn(msg.pos, toFloats(msg.color)), ctx);
   }

   private static float[] toFloats(int color) {
      return new float[]{(float)(color >> 16 & 0xFF) / 255.0F, (float)(color >> 8 & 0xFF) / 255.0F, (float)(color & 0xFF) / 255.0F};
   }

   public static record BossSpawnData(BlockPos pos, float[] color, MutableInt ticks) {
   }
}
