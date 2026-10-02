package com.aizistral.enigmaticlegacy.packets.clients;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.objects.Vector3;
import java.util.function.Supplier;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketGenericParticleEffect {
   private Vector3 pos;
   private int num;
   private boolean check;
   private PacketGenericParticleEffect.Effect effect;

   public PacketGenericParticleEffect(double x, double y, double z, int number, boolean checkSettings, PacketGenericParticleEffect.Effect effect) {
      this.pos = new Vector3(x, y, z);
      this.num = number;
      this.check = checkSettings;
      this.effect = effect;
   }

   public static void encode(PacketGenericParticleEffect msg, FriendlyByteBuf buf) {
      buf.writeDouble(msg.pos.x);
      buf.writeDouble(msg.pos.y);
      buf.writeDouble(msg.pos.z);
      buf.writeInt(msg.num);
      buf.writeBoolean(msg.check);
      buf.m_130072_(msg.effect.toString(), 512);
   }

   public static PacketGenericParticleEffect decode(FriendlyByteBuf buf) {
      return new PacketGenericParticleEffect(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readInt(), buf.readBoolean(), evaluateEffect(buf));
   }

   private static PacketGenericParticleEffect.Effect evaluateEffect(FriendlyByteBuf buf) {
      PacketGenericParticleEffect.Effect effect;
      try {
         effect = PacketGenericParticleEffect.Effect.valueOf(buf.m_130136_(512));
      } catch (Exception var3) {
         var3.printStackTrace();
         effect = PacketGenericParticleEffect.Effect.NONE;
      }

      return effect;
   }

   public static void handle(PacketGenericParticleEffect msg, Supplier<Context> ctx) {
      ctx.get()
         .enqueueWork(
            () -> {
               Player player = EnigmaticLegacy.PROXY.getClientPlayer();
               Vector3 pos = msg.pos;
               int amount = msg.num;
               if (msg.check) {
                  amount = (int)((float)amount * SuperpositionHandler.getParticleMultiplier());
               }

               if (msg.effect == PacketGenericParticleEffect.Effect.GUARDIAN_CURSE) {
                  double dist = 0.05;
                  double distHearts = 0.5;

                  for (int counter = 0; counter < 4; counter++) {
                     player.f_19853_
                        .m_6493_(
                           ParticleTypes.f_123792_,
                           true,
                           pos.x + SuperpositionHandler.getRandomNegative() * distHearts,
                           pos.y + SuperpositionHandler.getRandomNegative() * distHearts,
                           pos.z + SuperpositionHandler.getRandomNegative() * distHearts,
                           SuperpositionHandler.getRandomNegative() * 1.05,
                           SuperpositionHandler.getRandomNegative() * 1.05,
                           SuperpositionHandler.getRandomNegative() * 1.05
                        );
                  }

                  for (int counter = 0; counter < 12; counter++) {
                     player.f_19853_
                        .m_6493_(
                           ParticleTypes.f_123755_,
                           true,
                           pos.x,
                           pos.y,
                           pos.z,
                           SuperpositionHandler.getRandomNegative() * dist,
                           SuperpositionHandler.getRandomNegative() * dist,
                           SuperpositionHandler.getRandomNegative() * dist
                        );
                  }
               }
            }
         );
      ctx.get().setPacketHandled(true);
   }

   public static enum Effect {
      NONE,
      GUARDIAN_CURSE;
   }
}
