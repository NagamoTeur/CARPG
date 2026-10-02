package com.aqutheseal.celestisynth.common.network.util;

import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class SetPersistentIntPacket {
   private final String id;
   private final int value;

   public SetPersistentIntPacket(String id, int value) {
      this.id = id;
      this.value = value;
   }

   public SetPersistentIntPacket(FriendlyByteBuf buf) {
      this.id = buf.m_130277_();
      this.value = buf.readInt();
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.m_130070_(this.id);
      buf.writeInt(this.value);
   }

   public boolean handle(Supplier<Context> supplier) {
      Context context = supplier.get();
      context.enqueueWork(() -> {
         CompoundTag persistentData = context.getSender().getPersistentData();
         persistentData.m_128405_(this.id, this.value);
      });
      return true;
   }
}
