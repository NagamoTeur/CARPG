package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.api.util.StackUtil;
import com.hollingsworth.arsnouveau.common.items.SpellBook;
import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketSetBookMode {
   public CompoundTag tag;

   public PacketSetBookMode(FriendlyByteBuf buf) {
      this.tag = buf.m_130260_();
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.m_130079_(this.tag);
   }

   public PacketSetBookMode(CompoundTag tag) {
      this.tag = tag;
   }

   public void handle(Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> ctx.get().enqueueWork(() -> {
            ServerPlayer sender = ctx.get().getSender();
            if (sender != null) {
               ItemStack stack = StackUtil.getHeldSpellbook(ctx.get().getSender());
               if (stack.m_41720_() instanceof SpellBook) {
                  stack.m_41751_(this.tag);
               }
            }
         }));
      ctx.get().setPacketHandled(true);
   }
}
