package com.cerbon.bosses_of_mass_destruction.packet.custom.multipart_entities;

import com.cerbon.bosses_of_mass_destruction.api.multipart_entities.client.PlayerInteractMultipartEntity;
import com.cerbon.bosses_of_mass_destruction.api.multipart_entities.entity.MultipartAwareEntity;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent.Context;

public class MultipartEntityInteractionC2SPacket {
   private final int entityId;
   private final String part;
   private final InteractionHand hand;
   private final boolean isSneaking;
   private final PlayerInteractMultipartEntity.InteractionType interactionType;

   public MultipartEntityInteractionC2SPacket(
      int entityId, String part, InteractionHand hand, boolean isSneaking, PlayerInteractMultipartEntity.InteractionType interactionType
   ) {
      this.entityId = entityId;
      this.part = part;
      this.hand = hand;
      this.isSneaking = isSneaking;
      this.interactionType = interactionType;
   }

   public MultipartEntityInteractionC2SPacket(FriendlyByteBuf buf) {
      this.entityId = buf.readInt();
      this.part = buf.m_130136_(32767);
      this.hand = (InteractionHand)buf.m_130066_(InteractionHand.class);
      this.isSneaking = buf.readBoolean();
      this.interactionType = (PlayerInteractMultipartEntity.InteractionType)buf.m_130066_(PlayerInteractMultipartEntity.InteractionType.class);
   }

   public void write(FriendlyByteBuf buf) {
      buf.writeInt(this.entityId);
      buf.m_130070_(this.part);
      buf.m_130068_(this.hand);
      buf.writeBoolean(this.isSneaking);
      buf.m_130068_(this.interactionType);
   }

   public void handle(Supplier<Context> supplier) {
      Context ctx = supplier.get();
      ctx.enqueueWork(() -> {
         ServerPlayer serverPlayer = ctx.getSender();
         if (serverPlayer != null) {
            serverPlayer.m_20260_(this.isSneaking);
            ServerLevel serverLevel = serverPlayer.m_9236_();
            Entity entity = serverLevel.m_6815_(this.entityId);
            if (entity != null) {
               if (this.interactionType == PlayerInteractMultipartEntity.InteractionType.INTERACT) {
                  entity.m_6096_(serverPlayer, this.hand);
               } else {
                  if (this.interactionType != PlayerInteractMultipartEntity.InteractionType.ATTACK) {
                     throw new RuntimeException();
                  }

                  if (entity instanceof MultipartAwareEntity multipartAwareEntity) {
                     multipartAwareEntity.setNextDamagedPart(this.part);
                  }

                  serverPlayer.m_5706_(entity);
               }
            }
         }
      });
      ctx.setPacketHandled(true);
   }
}
