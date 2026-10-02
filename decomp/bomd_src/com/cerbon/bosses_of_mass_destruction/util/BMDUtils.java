package com.cerbon.bosses_of_mass_destruction.util;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.RandomUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.particle.ClientParticleBuilder;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class BMDUtils {
   public static void addDeltaMovement(Entity entity, Vec3 velocity) {
      entity.m_20256_(entity.m_20184_().m_82549_(velocity));
   }

   public static void spawnParticle(ServerLevel level, ParticleOptions particleType, Vec3 pos, Vec3 velOrOffset, int count, double speed) {
      level.m_8767_(particleType, pos.f_82479_, pos.f_82480_, pos.f_82481_, count, velOrOffset.f_82479_, velOrOffset.f_82480_, velOrOffset.f_82481_, speed);
   }

   public static void playSound(
      ServerLevel level, Vec3 pos, SoundEvent soundEvent, SoundSource soundSource, float volume, float pitch, double range, Player player
   ) {
      level.m_7654_()
         .m_6846_()
         .m_11241_(
            player,
            pos.f_82479_,
            pos.f_82480_,
            pos.f_82481_,
            range,
            level.m_46472_(),
            new ClientboundSoundPacket(soundEvent, soundSource, pos.f_82479_, pos.f_82480_, pos.f_82481_, volume, pitch, level.f_46441_.m_188505_())
         );
   }

   public static void playSound(ServerLevel level, Vec3 pos, SoundEvent soundEvent, SoundSource soundSource, float volume, double range, Player player) {
      playSound(level, pos, soundEvent, soundSource, volume, randomPitch(level.f_46441_), range, player);
   }

   public static float randomPitch(@NotNull RandomSource random) {
      return (random.m_188501_() - random.m_188501_()) * 0.2F + 1.0F;
   }

   public static BlockPos findGroundBelow(Level level, BlockPos pos, Function<BlockPos, Boolean> isOpenBlock) {
      int bottomY = level.m_141937_();

      for (int i = pos.m_123342_(); i >= bottomY + 1; i--) {
         BlockPos tempPos = new BlockPos(pos.m_123341_(), i, pos.m_123343_());
         if (level.m_8055_(tempPos).m_60659_(level, tempPos, Direction.UP, SupportType.FULL) && isOpenBlock.apply(tempPos.m_7494_())) {
            return tempPos;
         }
      }

      return new BlockPos(pos.m_123341_(), bottomY, pos.m_123343_());
   }

   public static void preventDespawnExceptPeaceful(LivingEntity mobEntity, Level level) {
      if (level.m_46791_() == Difficulty.PEACEFUL) {
         mobEntity.m_146870_();
      } else {
         mobEntity.m_21310_(0);
      }
   }

   public static List<Entity> findEntitiesInLine(Level level, Vec3 start, Vec3 end, Entity toExclude) {
      AABB aabb = new AABB(start, end);
      return level.m_6249_(toExclude, aabb, entity -> entity.m_20191_().m_82371_(start, end).isPresent());
   }

   public static void spawnRotatingParticles(BMDUtils.RotatingParticles particleParams) {
      int startingRotation = new Random().nextInt(360);
      double randomRadius = RandomUtils.range(particleParams.minRadius(), particleParams.maxRadius());
      double rotationSpeed = RandomUtils.range(particleParams.minSpeed(), particleParams.maxSpeed());
      ClientParticleBuilder builder = particleParams.particleBuilder();
      builder.continuousPosition(age -> rotateAroundPos(particleParams.pos(), age.getAge(), startingRotation, randomRadius, rotationSpeed))
         .build(rotateAroundPos(particleParams.pos(), 0, startingRotation, randomRadius, rotationSpeed), Vec3.f_82478_);
   }

   private static Vec3 rotateAroundPos(Vec3 pos, int age, int startingRotation, double radius, double rotationSpeed) {
      Vec3 xzOffset = VecUtils.xAxis.m_82524_((float)Math.toRadians((double)age * rotationSpeed + (double)startingRotation));
      return pos.m_82549_(xzOffset.m_82490_(radius));
   }

   public static ConfiguredFeature<?, ?> getConfiguredFeature(WorldGenLevel level, ResourceKey<ConfiguredFeature<?, ?>> key) {
      return (ConfiguredFeature<?, ?>)level.m_5962_().m_175515_(Registry.f_122881_).m_123013_(key);
   }

   public static record RotatingParticles(Vec3 pos, ClientParticleBuilder particleBuilder, double minRadius, double maxRadius, double minSpeed, double maxSpeed) {
   }
}
