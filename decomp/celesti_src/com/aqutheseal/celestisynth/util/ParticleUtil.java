package com.aqutheseal.celestisynth.util;

import com.aqutheseal.celestisynth.common.network.util.CSSpawnParticlePacket;
import com.aqutheseal.celestisynth.manager.CSNetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public final class ParticleUtil {
   private ParticleUtil() {
      throw new IllegalAccessError("Attempted to access a Utility Class!");
   }

   public static <T extends ParticleType<?>> int sendParticles(
      ServerLevel serverWorld,
      T pType,
      double pPosX,
      double pPosY,
      double pPosZ,
      int pParticleCount,
      double pXOffset,
      double pYOffset,
      double pZOffset,
      double pXSpeed,
      double pYSpeed,
      double pZSpeed
   ) {
      CSSpawnParticlePacket sspawnparticlepacket = new CSSpawnParticlePacket(
         pType, false, pPosX, pPosY, pPosZ, (float)pXOffset, (float)pYOffset, (float)pZOffset, (float)pXSpeed, (float)pYSpeed, (float)pZSpeed, pParticleCount
      );
      int i = 0;

      for (int j = 0; j < serverWorld.m_6907_().size(); j++) {
         ServerPlayer serverplayerentity = (ServerPlayer)serverWorld.m_6907_().get(j);
         if (sendParticles(serverplayerentity, pPosX, pPosY, pPosZ, sspawnparticlepacket)) {
            i++;
         }
      }

      return i;
   }

   public static <T extends ParticleType<?>> int sendParticle(Level world, T pType, double pPosX, double pPosY, double pPosZ) {
      return sendParticles(world, pType, pPosX, pPosY, pPosZ, 0, 0.0, 0.0, 0.0);
   }

   public static <T extends ParticleType<?>> int sendParticles(
      Level world, T pType, double pPosX, double pPosY, double pPosZ, int pParticleCount, double pXSpeed, double pYSpeed, double pZSpeed
   ) {
      return !world.m_5776_() ? sendParticles((ServerLevel)world, pType, pPosX, pPosY, pPosZ, pParticleCount, 0.0, 0.0, 0.0, pXSpeed, pYSpeed, pZSpeed) : 0;
   }

   public static <T extends ParticleType<?>> int sendParticles(
      ServerLevel serverWorld, T pType, double pPosX, double pPosY, double pPosZ, int pParticleCount, double pXSpeed, double pYSpeed, double pZSpeed
   ) {
      return sendParticles(serverWorld, pType, pPosX, pPosY, pPosZ, pParticleCount, 0.0, 0.0, 0.0, pXSpeed, pYSpeed, pZSpeed);
   }

   private static boolean sendParticles(ServerPlayer pPlayer, double pPosX, double pPosY, double pPosZ, CSSpawnParticlePacket packet) {
      if (pPlayer.m_9236_().m_5776_()) {
         return false;
      } else {
         BlockPos blockpos = pPlayer.m_20183_();
         if (blockpos.m_123314_(new Vec3i((int)pPosX, (int)pPosY, (int)pPosZ), 1024.0)) {
            CSNetworkManager.sendToAll(packet);
            return true;
         } else {
            return false;
         }
      }
   }
}
