package daripher.autoleveling.network.message;

import daripher.autoleveling.event.MobsLevelingEvents;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent.Context;

public class SyncLevelingData {
   private int entityId;
   private int level;

   private SyncLevelingData() {
   }

   public SyncLevelingData(LivingEntity entity) {
      this.entityId = entity.m_19879_();
      this.level = MobsLevelingEvents.getLevel(entity);
   }

   public static SyncLevelingData decode(FriendlyByteBuf buf) {
      SyncLevelingData result = new SyncLevelingData();
      result.entityId = buf.readInt();
      result.level = buf.readInt();
      return result;
   }

   public static void receive(SyncLevelingData message, Supplier<Context> ctxSupplier) {
      Context ctx = ctxSupplier.get();
      ctx.setPacketHandled(true);
      ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> handlePacket(message, ctx)));
   }

   @OnlyIn(Dist.CLIENT)
   private static void handlePacket(SyncLevelingData message, Context ctx) {
      ctx.setPacketHandled(true);
      Minecraft client = Minecraft.m_91087_();
      ClientLevel level = client.f_91073_;
      if (level != null) {
         Entity entity = level.m_6815_(message.entityId);
         if (entity != null) {
            MobsLevelingEvents.setLevel((LivingEntity)entity, message.level);
         }
      }
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeInt(this.entityId);
      buf.writeInt(this.level);
   }
}
