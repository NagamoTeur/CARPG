package io.redspace.ironsspellbooks.network;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent.Context;

public class ClientboundAddMotionToPlayer {
   private final double x;
   private final double y;
   private final double z;
   private final boolean preserveMomentum;

   public ClientboundAddMotionToPlayer(double x, double y, double z, boolean preserveMomentum) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.preserveMomentum = preserveMomentum;
   }

   public ClientboundAddMotionToPlayer(Vec3 motion, boolean preserveMomentum) {
      this.x = motion.f_82479_;
      this.y = motion.f_82480_;
      this.z = motion.f_82481_;
      this.preserveMomentum = preserveMomentum;
   }

   public ClientboundAddMotionToPlayer(FriendlyByteBuf buf) {
      this.x = buf.readDouble();
      this.y = buf.readDouble();
      this.z = buf.readDouble();
      this.preserveMomentum = buf.readBoolean();
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.writeDouble(this.x);
      buf.writeDouble(this.y);
      buf.writeDouble(this.z);
      buf.writeBoolean(this.preserveMomentum);
   }

   public boolean handle(Supplier<Context> supplier) {
      Context ctx = supplier.get();
      ctx.enqueueWork(() -> {
      });
      if (this.preserveMomentum) {
         Minecraft.m_91087_().f_91074_.m_5997_(this.x, this.y, this.z);
      } else {
         Minecraft.m_91087_().f_91074_.m_20334_(this.x, this.y, this.z);
      }

      return true;
   }
}
