package com.hollingsworth.arsnouveau.client.particle;

import com.hollingsworth.arsnouveau.api.ritual.AbstractRitual;
import com.hollingsworth.arsnouveau.common.entity.EntityFollowProjectile;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketANEffect;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

public class ParticleUtil {
   public static Random r = new Random();

   public static double inRange(double min, double max) {
      return ThreadLocalRandom.current().nextDouble(min, max);
   }

   public static double getCenterOfBlock(double a) {
      return a + 0.5;
   }

   public static Vec3 pointInSphere() {
      double u = Math.random();
      double v = Math.random();
      double theta = u * 2.0 * Math.PI;
      double phi = Math.acos(2.0 * v - 1.0);
      double r = Math.cbrt(Math.random());
      double sinTheta = Math.sin(theta);
      double cosTheta = Math.cos(theta);
      double sinPhi = Math.sin(phi);
      double cosPhi = Math.cos(phi);
      double x = r * sinPhi * cosTheta;
      double y = r * sinPhi * sinTheta;
      double z = r * cosPhi;
      return new Vec3(x, y, z);
   }

   public static void spawnFollowProjectile(Level world, BlockPos from, BlockPos to) {
      if (world.m_46749_(to) && world.m_46749_(from)) {
         EntityFollowProjectile aoeProjectile = new EntityFollowProjectile(world, from, to);
         world.m_7967_(aoeProjectile);
      }
   }

   public static void beam(BlockPos toThisBlock, BlockPos fromThisBlock, Level world) {
      double x2 = getCenterOfBlock((double)toThisBlock.m_123341_());
      double z2 = getCenterOfBlock((double)toThisBlock.m_123343_());
      double y2 = getCenterOfBlock((double)toThisBlock.m_123342_());
      double x1 = getCenterOfBlock((double)fromThisBlock.m_123341_());
      double z1 = getCenterOfBlock((double)fromThisBlock.m_123343_());
      double y1 = getCenterOfBlock((double)fromThisBlock.m_123342_());
      double d5 = 1.2;
      double d0 = x2 - x1;
      double d1 = y2 - y1;
      double d2 = z2 - z1;
      double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
      d0 /= d3;
      d1 /= d3;
      d2 /= d3;
      double d4 = r.nextDouble();

      while (d4 + 0.65 < d3) {
         d4 += 1.8 - d5 + r.nextDouble() * (1.5 - d5);
         if (world.f_46443_) {
            world.m_7106_(ParticleTypes.f_123809_, x1 + d0 * d4, y1 + d1 * d4, z1 + d2 * d4, 0.0, 0.0, 0.0);
         }

         if (world instanceof ServerLevel) {
            ((ServerLevel)world).m_8767_(ParticleTypes.f_123771_, x1 + d0 * d4, y1 + d1 * d4, z1 + d2 * d4, r.nextInt(4), 0.0, 0.0, 0.0, 0.0);
         }
      }
   }

   public static ParticleColor.IntWrapper defaultParticleColorWrapper() {
      return new ParticleColor.IntWrapper(255, 25, 180);
   }

   public static void spawnPoof(ServerLevel world, BlockPos pos) {
      for (int i = 0; i < 10; i++) {
         double d0 = (double)pos.m_123341_() + 0.5;
         double d1 = (double)pos.m_123342_() + 1.2;
         double d2 = (double)pos.m_123343_() + 0.5;
         world.m_8767_(
            ParticleTypes.f_123810_,
            d0,
            d1,
            d2,
            2,
            ((double)(world.f_46441_.m_188501_() * 1.0F) - 0.5) / 3.0,
            ((double)(world.f_46441_.m_188501_() * 1.0F) - 0.5) / 3.0,
            ((double)(world.f_46441_.m_188501_() * 1.0F) - 0.5) / 3.0,
            0.1F
         );
      }
   }

   public static void spawnTouch(ClientLevel world, BlockPos loc) {
      spawnTouch(world, loc, ParticleColor.defaultParticleColor());
   }

   public static void spawnTouch(ClientLevel world, BlockPos loc, ParticleColor particleColor) {
      for (int i = 0; i < 5; i++) {
         double d0 = (double)loc.m_123341_() + 0.5;
         double d1 = (double)loc.m_123342_() + 1.0;
         double d2 = (double)loc.m_123343_() + 0.5;
         world.m_7106_(
            GlowParticleData.createData(particleColor),
            d0,
            d1,
            d2,
            ((double)(world.f_46441_.m_188501_() * 1.0F) - 0.5) / 5.0,
            ((double)(world.f_46441_.m_188501_() * 1.0F) - 0.5) / 5.0,
            ((double)(world.f_46441_.m_188501_() * 1.0F) - 0.5) / 5.0
         );
      }
   }

   public static void spawnTouchPacket(Level world, BlockPos pos, ParticleColor.IntWrapper color) {
      Networking.sendToNearby(world, pos, new PacketANEffect(PacketANEffect.EffectType.BURST, pos, color));
   }

   public static void spawnRitualAreaEffect(BlockEntity entity, RandomSource rand, ParticleColor color, int range) {
      spawnRitualAreaEffect(entity.m_58899_(), entity.m_58904_(), rand, color, range);
   }

   public static void spawnRitualAreaEffect(BlockPos pos, Level world, RandomSource rand, ParticleColor color, int range) {
      spawnRitualAreaEffect(pos, world, rand, color, range, 10, 2);
   }

   public static void spawnRitualAreaEffect(BlockPos pos, Level world, RandomSource rand, ParticleColor color, int range, int chance, int numParticles) {
      BlockPos.m_121990_(pos.m_7918_(range, 0, range), pos.m_7918_(-range, 0, -range)).forEach(blockPos -> {
         if (rand.m_188503_(chance) == 0) {
            for (int i = 0; i < rand.m_188503_(numParticles); i++) {
               double x = (double)blockPos.m_123341_() + inRange(-0.5, 0.5) + 0.5;
               double y = (double)blockPos.m_123342_() + inRange(-0.5, 0.5);
               double z = (double)blockPos.m_123343_() + inRange(-0.5, 0.5) + 0.5;
               world.m_7106_(ParticleLineData.createData(color), x, y, z, x, y + inRange(0.5, 5.0), z);
            }
         }
      });
   }

   public static void spawnRitualSkyEffect(BlockEntity tileEntity, RandomSource rand, ParticleColor.IntWrapper color) {
      int min = -5;
      int max = 5;
      BlockPos nearPos = new BlockPos(
         tileEntity.m_58899_().m_123341_() + rand.m_188503_(max - min) + min,
         tileEntity.m_58899_().m_123342_(),
         tileEntity.m_58899_().m_123343_() + rand.m_188503_(max - min) + min
      );
      BlockPos toPos = nearPos.m_6630_(rand.m_188503_(3) + 10);
      EntityFollowProjectile proj1 = new EntityFollowProjectile(tileEntity.m_58904_(), tileEntity.m_58899_().m_7494_(), toPos, color);
      proj1.m_20088_().m_135381_(EntityFollowProjectile.SPAWN_TOUCH, true);
      proj1.m_20088_().m_135381_(EntityFollowProjectile.DESPAWN, 15);
      tileEntity.m_58904_().m_7967_(proj1);
   }

   public static void spawnRitualSkyEffect(AbstractRitual ritual, BlockEntity tileEntity, RandomSource rand, ParticleColor.IntWrapper color) {
      int scalar = 20;
      if (ritual.getContext().progress >= 5) {
         scalar = 10;
      }

      if (ritual.getContext().progress >= 10) {
         scalar = 5;
      }

      if (ritual.getContext().progress >= 13) {
         scalar = 3;
      }

      if (!ritual.getWorld().f_46443_ && ritual.getProgress() <= 15 && (ritual.getWorld().m_46467_() % 20L == 0L || rand.m_188503_(scalar) == 0)) {
         spawnRitualSkyEffect(tileEntity, rand, color);
      }
   }

   public static void spawnFallingSkyEffect(BlockEntity tileEntity, RandomSource rand, ParticleColor.IntWrapper color) {
      int min = -5;
      int max = 5;
      BlockPos nearPos = new BlockPos(
         tileEntity.m_58899_().m_123341_() + rand.m_188503_(max - min) + min,
         tileEntity.m_58899_().m_123342_() + 8,
         tileEntity.m_58899_().m_123343_() + rand.m_188503_(max - min) + min
      );
      BlockPos toPos = nearPos.m_6625_(8);
      EntityFollowProjectile proj1 = new EntityFollowProjectile(tileEntity.m_58904_(), nearPos, toPos, color);
      proj1.m_20088_().m_135381_(EntityFollowProjectile.SPAWN_TOUCH, true);
      proj1.m_20088_().m_135381_(EntityFollowProjectile.DESPAWN, 20);
      tileEntity.m_58904_().m_7967_(proj1);
   }

   public static void spawnFallingSkyEffect(AbstractRitual ritual, BlockEntity tileEntity, RandomSource rand, ParticleColor.IntWrapper color) {
      if (ritual != null) {
         int scalar = 20;
         if (ritual.getContext().progress >= 5) {
            scalar = 10;
         }

         if (ritual.getContext().progress >= 10) {
            scalar = 5;
         }

         if (ritual.getContext().progress >= 13) {
            scalar = 3;
         }

         if (!ritual.getWorld().f_46443_ && ritual.getProgress() <= 15 && (ritual.getWorld().m_46467_() % 20L == 0L || rand.m_188503_(scalar) == 0)) {
            spawnFallingSkyEffect(tileEntity, rand, color);
         }
      }
   }

   public static void spawnLight(Level world, ParticleColor color, Vec3 vec, int intensity) {
      for (int i = 0; i < intensity; i++) {
         world.m_7106_(
            GlowParticleData.createData(color),
            vec.m_7096_() + inRange(-0.1, 0.1),
            vec.m_7098_() + inRange(-0.1, 0.1),
            vec.m_7094_() + inRange(-0.1, 0.1),
            0.0,
            0.0,
            0.0
         );
      }
   }

   public static void spawnOrb(Level level, ParticleColor color, BlockPos pos, int lifetime) {
      if (level instanceof ServerLevel server) {
         for (int i = 0; i <= 10; i++) {
            server.m_8767_(
               GlowParticleData.createData(color, 0.4F, 0.5F, lifetime),
               (double)pos.m_123341_() + inRange(0.3, 0.7),
               (double)pos.m_123342_() + inRange(-0.2, 0.2),
               (double)pos.m_123343_() + inRange(0.3, 0.7),
               1,
               0.0,
               0.0,
               0.0,
               0.0
            );
         }
      }
   }
}
