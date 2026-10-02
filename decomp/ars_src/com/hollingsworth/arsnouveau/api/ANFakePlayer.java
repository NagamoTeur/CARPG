package com.hollingsworth.arsnouveau.api;

import com.mojang.authlib.GameProfile;
import java.lang.ref.WeakReference;
import java.util.OptionalInt;
import java.util.UUID;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.MenuProvider;
import net.minecraftforge.common.util.FakePlayer;
import org.jetbrains.annotations.Nullable;

public class ANFakePlayer extends FakePlayer {
   private static final Connection NETWORK_MANAGER = new Connection(PacketFlow.CLIENTBOUND);
   public static final GameProfile PROFILE = new GameProfile(UUID.fromString("7400926d-1007-4e53-880f-b43e67f2bf29"), "Ars_Nouveau");
   private static WeakReference<ANFakePlayer> FAKE_PLAYER = null;

   public double getReachDistance() {
      return 4.5;
   }

   private ANFakePlayer(ServerLevel world) {
      super(world, PROFILE);
      this.f_8906_ = new ANFakePlayer.FakePlayNetHandler(world.m_7654_(), this);
   }

   public static ANFakePlayer getPlayer(ServerLevel world) {
      ANFakePlayer ret = FAKE_PLAYER != null ? FAKE_PLAYER.get() : null;
      if (ret == null) {
         ret = new ANFakePlayer(world);
         FAKE_PLAYER = new WeakReference<>(ret);
      }

      FAKE_PLAYER.get().f_19853_ = world;
      return FAKE_PLAYER.get();
   }

   public OptionalInt m_5893_(MenuProvider container) {
      return OptionalInt.empty();
   }

   public Component m_5446_() {
      return Component.m_237113_("AN_Fake_Player");
   }

   private static class FakePlayNetHandler extends ServerGamePacketListenerImpl {
      public FakePlayNetHandler(MinecraftServer server, ServerPlayer playerIn) {
         super(server, ANFakePlayer.NETWORK_MANAGER, playerIn);
      }

      public void m_9829_(Packet<?> packetIn) {
      }

      public void m_243119_(Packet<?> p_243227_, @Nullable PacketSendListener p_243273_) {
      }
   }
}
