package com.github.L_Ender.cataclysm.message;

import com.github.L_Ender.cataclysm.entity.partentity.Cm_Part_Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.DataItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.network.NetworkEvent.Context;

public class MessageCMMultipart {
   private int id;
   private Entity entity;
   private int len;
   private List<MessageCMMultipart.PartDataHolder> data = new ArrayList<>();

   public MessageCMMultipart(FriendlyByteBuf buf) {
      this.id = buf.readInt();
      this.len = buf.readInt();

      for (int i = 0; i < this.len; i++) {
         if (buf.readBoolean()) {
            this.data.add(MessageCMMultipart.PartDataHolder.decode(buf));
         }
      }
   }

   public MessageCMMultipart(Entity entity) {
      this.entity = entity;
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeInt(this.entity.m_19879_());
      PartEntity<?>[] parts = this.entity.getParts();
      if (parts != null) {
         buf.writeInt(parts.length);

         for (PartEntity<?> part : parts) {
            if (part instanceof Cm_Part_Entity<?> tfPart) {
               buf.writeBoolean(true);
               tfPart.writeData().encode(buf);
            } else {
               buf.writeBoolean(false);
            }
         }
      } else {
         buf.writeInt(0);
      }
   }

   public static class Handler {
      public static boolean onMessage(final MessageCMMultipart message, Supplier<Context> ctx) {
         ctx.get().enqueueWork(new Runnable() {
            @Override
            public void run() {
               Level world = Minecraft.m_91087_().f_91073_;
               if (world != null) {
                  Entity ent = world.m_6815_(message.id);
                  if (ent != null && ent.isMultipartEntity()) {
                     PartEntity<?>[] parts = ent.getParts();
                     if (parts == null) {
                        return;
                     }

                     int index = 0;

                     for (PartEntity<?> part : parts) {
                        if (part instanceof Cm_Part_Entity<?> tfPart) {
                           tfPart.readData(message.data.get(index));
                           index++;
                        }
                     }
                  }
               }
            }
         });
         ctx.get().setPacketHandled(true);
         return true;
      }
   }

   public static record PartDataHolder(
      double x, double y, double z, float yRot, float xRot, float width, float height, boolean fixed, boolean dirty, List<DataItem<?>> data
   ) {
      public void encode(FriendlyByteBuf buffer) {
         buffer.writeDouble(this.x);
         buffer.writeDouble(this.y);
         buffer.writeDouble(this.z);
         buffer.writeFloat(this.yRot);
         buffer.writeFloat(this.xRot);
         buffer.writeFloat(this.width);
         buffer.writeFloat(this.height);
         buffer.writeBoolean(this.fixed);
         buffer.writeBoolean(this.dirty);
         if (this.dirty) {
            SynchedEntityData.m_135358_(this.data, buffer);
         }
      }

      static MessageCMMultipart.PartDataHolder decode(FriendlyByteBuf buffer) {
         boolean dirty;
         return new MessageCMMultipart.PartDataHolder(
            buffer.readDouble(),
            buffer.readDouble(),
            buffer.readDouble(),
            buffer.readFloat(),
            buffer.readFloat(),
            buffer.readFloat(),
            buffer.readFloat(),
            buffer.readBoolean(),
            dirty = buffer.readBoolean(),
            dirty ? SynchedEntityData.m_135361_(buffer) : null
         );
      }
   }
}
