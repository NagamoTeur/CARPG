package com.cerbon.bosses_of_mass_destruction.packet.custom;

import com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet.GauntletEntity;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent.Context;

public class ChangeHitboxS2CPacket {
   private final int entityId;
   private final boolean open;

   public ChangeHitboxS2CPacket(int entityId, boolean open) {
      this.entityId = entityId;
      this.open = open;
   }

   public ChangeHitboxS2CPacket(FriendlyByteBuf buf) {
      this.entityId = buf.readInt();
      this.open = buf.readBoolean();
   }

   public void write(FriendlyByteBuf buf) {
      buf.writeInt(this.entityId);
      buf.writeBoolean(this.open);
   }

   public void handle(Supplier<Context> supplier) {
      Context ctx = supplier.get();
      ctx.enqueueWork(() -> {
         Minecraft client = Minecraft.m_91087_();
         ClientLevel level = client.f_91073_;
         if (level != null) {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> client.execute(() -> {
                     Entity entity = level.m_6815_(this.entityId);
                     if (entity instanceof GauntletEntity) {
                        if (this.open) {
                           ((GauntletEntity)entity).hitboxHelper.setOpenHandHitbox();
                        } else {
                           ((GauntletEntity)entity).hitboxHelper.setClosedFistHitbox();
                        }
                     }
                  }));
         }
      });
      ctx.setPacketHandled(true);
   }
}
