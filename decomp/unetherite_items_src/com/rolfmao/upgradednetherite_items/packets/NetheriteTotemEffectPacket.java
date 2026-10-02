package com.rolfmao.upgradednetherite_items.packets;

import com.rolfmao.upgradednetherite_items.UpgradedNetherite_ItemsMod;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent.Context;

public class NetheriteTotemEffectPacket {
   private ItemStack itemStack;
   private Entity entity;

   public NetheriteTotemEffectPacket(FriendlyByteBuf buf) {
      Minecraft mc = Minecraft.m_91087_();
      this.itemStack = buf.m_130267_();
      this.entity = mc.f_91073_.m_6815_(buf.readInt());
   }

   public NetheriteTotemEffectPacket(ItemStack itemStack, Entity entity) {
      this.itemStack = itemStack;
      this.entity = entity;
   }

   public void encode(FriendlyByteBuf buf) {
      buf.m_130055_(this.itemStack);
      buf.writeInt(this.entity.m_19879_());
   }

   public void handle(Supplier<Context> context) {
      context.get()
         .enqueueWork(
            () -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> UpgradedNetherite_ItemsMod.INSTANCE.playActivateAnimation(this.itemStack, this.entity))
         );
      context.get().setPacketHandled(true);
   }
}
