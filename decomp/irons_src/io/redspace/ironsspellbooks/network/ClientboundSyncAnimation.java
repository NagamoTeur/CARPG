package io.redspace.ironsspellbooks.network;

import io.redspace.ironsspellbooks.entity.mobs.IAnimatedAttacker;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent.Context;

public class ClientboundSyncAnimation<T extends Entity & IAnimatedAttacker> {
   int entityId;
   String animationId;

   public ClientboundSyncAnimation(String animationId, T entity) {
      this.entityId = entity.m_19879_();
      this.animationId = animationId;
   }

   public ClientboundSyncAnimation(FriendlyByteBuf buf) {
      this.entityId = buf.readInt();
      this.animationId = buf.m_130277_();
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.writeInt(this.entityId);
      buf.m_130070_(this.animationId);
   }

   public boolean handle(Supplier<Context> supplier) {
      Context ctx = supplier.get();
      ctx.enqueueWork(() -> {
         ClientLevel level = Minecraft.m_91087_().f_91073_;
         if (level != null) {
            if (level.m_6815_(this.entityId) instanceof IAnimatedAttacker animatedAttacker) {
               animatedAttacker.playAnimation(this.animationId);
            }
         }
      });
      return true;
   }
}
