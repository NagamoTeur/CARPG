package com.bobmowzie.mowziesmobs.server.message;

import com.bobmowzie.mowziesmobs.MowziesMobs;
import com.bobmowzie.mowziesmobs.server.block.BlockGrottol;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent.Context;

public final class MessageBlackPinkInYourArea {
   private int entityID;

   public MessageBlackPinkInYourArea() {
   }

   public MessageBlackPinkInYourArea(AbstractMinecart minecart) {
      this(minecart.m_19879_());
   }

   private MessageBlackPinkInYourArea(int entityId) {
      this.entityID = entityId;
   }

   public static void serialize(MessageBlackPinkInYourArea message, FriendlyByteBuf buf) {
      buf.m_130130_(message.entityID);
   }

   public static MessageBlackPinkInYourArea deserialize(FriendlyByteBuf buf) {
      MessageBlackPinkInYourArea message = new MessageBlackPinkInYourArea();
      message.entityID = buf.m_130242_();
      return message;
   }

   public static class Handler implements BiConsumer<MessageBlackPinkInYourArea, Supplier<Context>> {
      public void accept(MessageBlackPinkInYourArea message, Supplier<Context> contextSupplier) {
         Context context = contextSupplier.get();
         context.enqueueWork(() -> {
            ClientLevel world = Minecraft.m_91087_().f_91073_;
            if (world.m_6815_(message.entityID) instanceof AbstractMinecart minecart) {
               MowziesMobs.PROXY.playBlackPinkSound(minecart);
               BlockState state = (BlockState)Blocks.f_50069_.m_49966_().m_61124_(BlockGrottol.VARIANT, BlockGrottol.Variant.BLACK_PINK);
               BlockPos pos = minecart.m_20183_();
               float scale = 0.75F;
               double x = minecart.m_20185_();
               double y = minecart.m_20186_() + 0.375 + 0.5 + (double)((float)(minecart.m_7144_() - 8) / 16.0F * 0.75F);
               double z = minecart.m_20189_();
               SoundType sound = state.m_60734_().getSoundType(state, world, pos, minecart);
               world.m_7785_(x, y, z, sound.m_56775_(), minecart.m_5720_(), (sound.m_56773_() + 1.0F) / 2.0F, sound.m_56774_() * 0.8F, false);
               MowziesMobs.PROXY.minecartParticles(world, minecart, 0.75F, x, y, z, state, pos);
            }
         });
         context.setPacketHandled(true);
      }
   }
}
