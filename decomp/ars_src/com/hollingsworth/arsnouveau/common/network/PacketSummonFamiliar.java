package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.api.event.FamiliarSummonEvent;
import com.hollingsworth.arsnouveau.api.familiar.IFamiliar;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.capability.CapabilityRegistry;
import com.hollingsworth.arsnouveau.common.capability.IPlayerCap;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketSummonFamiliar {
   ResourceLocation familiarID;

   @Deprecated(
      forRemoval = true
   )
   public PacketSummonFamiliar(ResourceLocation id, int entityID) {
      this.familiarID = id;
   }

   public PacketSummonFamiliar(ResourceLocation id) {
      this.familiarID = id;
   }

   public PacketSummonFamiliar(FriendlyByteBuf buf) {
      this.familiarID = buf.m_130281_();
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.m_130085_(this.familiarID);
   }

   public void handle(Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         if (ctx.get().getSender() != null) {
            IPlayerCap cap = (IPlayerCap)CapabilityRegistry.getPlayerDataCap(ctx.get().getSender()).orElse(null);
            if (cap == null) {
               return;
            }

            Entity owner = ctx.get().getSender();
            if (owner == null) {
               return;
            }

            IFamiliar familiarEntity = cap.getFamiliarData(this.familiarID).getEntity(ctx.get().getSender().f_19853_);
            familiarEntity.setOwnerID(owner.m_20148_());
            familiarEntity.getThisEntity().m_6034_(owner.m_20185_(), owner.m_20186_(), owner.m_20189_());
            FamiliarSummonEvent summonEvent = new FamiliarSummonEvent(familiarEntity.getThisEntity(), owner);
            MinecraftForge.EVENT_BUS.post(summonEvent);
            if (!summonEvent.isCanceled()) {
               owner.f_19853_.m_7967_(familiarEntity.getThisEntity());
               ParticleUtil.spawnPoof((ServerLevel)owner.f_19853_, familiarEntity.getThisEntity().m_20183_());
               cap.setLastSummonedFamiliar(this.familiarID);
            }
         }
      });
      ctx.get().setPacketHandled(true);
   }
}
