package shadows.apotheosis.util;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import shadows.placebo.network.MessageHelper;
import shadows.placebo.network.MessageProvider;

public class ParticleMessage implements MessageProvider<ParticleMessage> {
   ParticleType<?> type;
   double x;
   double y;
   double z;
   double velX;
   double velY;
   double velZ;
   int count;

   public ParticleMessage() {
   }

   public ParticleMessage(ParticleType<?> type, double x, double y, double z, double velX, double velY, double velZ, int count) {
      this.type = type;
      this.x = x;
      this.y = y;
      this.z = z;
      this.velX = velX;
      this.velY = velY;
      this.velZ = velZ;
      this.count = count;
   }

   public Class<ParticleMessage> getMsgClass() {
      return ParticleMessage.class;
   }

   public ParticleMessage read(FriendlyByteBuf buf) {
      ParticleType<?> type = (ParticleType<?>)((ForgeRegistry)ForgeRegistries.PARTICLE_TYPES).getValue(buf.readInt());
      double x = buf.readDouble();
      double y = buf.readDouble();
      double z = buf.readDouble();
      double velX = buf.readDouble();
      double velY = buf.readDouble();
      double velZ = buf.readDouble();
      int count = buf.readInt();
      return new ParticleMessage(type, x, y, z, velX, velY, velZ, count);
   }

   public void write(ParticleMessage msg, FriendlyByteBuf buf) {
      buf.writeInt(((ForgeRegistry)ForgeRegistries.PARTICLE_TYPES).getID(msg.type));
      buf.writeDouble(msg.x).writeDouble(msg.y).writeDouble(msg.z);
      buf.writeDouble(msg.velX).writeDouble(msg.velY).writeDouble(msg.velZ);
      buf.writeInt(msg.count);
   }

   public void handle(ParticleMessage msg, Supplier<Context> ctx) {
      MessageHelper.handlePacket(() -> () -> {
            for (int i = 0; i < msg.count; i++) {
               ParticleOptions var10001 = (ParticleOptions)msg.type;
               Minecraft.m_91087_().f_91073_.m_7106_(var10001, msg.x, msg.y, msg.z, msg.velX, msg.velY, msg.velZ);
            }
         }, ctx);
   }
}
