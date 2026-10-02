package com.cerbon.bosses_of_mass_destruction.block.custom;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.RandomUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.block.BMDBlocks;
import com.cerbon.bosses_of_mass_destruction.capability.util.BMDCapabilities;
import com.cerbon.bosses_of_mass_destruction.entity.custom.lich.LichUtils;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.VoidBlossomEntity;
import com.cerbon.bosses_of_mass_destruction.entity.util.EntityAdapter;
import com.cerbon.bosses_of_mass_destruction.entity.util.EntityStats;
import com.cerbon.bosses_of_mass_destruction.packet.BMDPacketHandler;
import com.cerbon.bosses_of_mass_destruction.packet.custom.HealS2CPacket;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.particle.ClientParticleBuilder;
import com.cerbon.bosses_of_mass_destruction.util.BMDColors;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class VoidBlossomBlock extends Block {
   private static final int healAnimationDelay = 16;
   private static final int healDelay = 64;
   private final VoxelShape shape = m_49796_(2.0, 0.0, 2.0, 14.0, 3.0, 14.0);

   public VoidBlossomBlock(Properties properties) {
      super(properties);
   }

   public void m_6807_(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean movedByPiston) {
      level.m_186460_(pos, this, 1);
   }

   public void m_213897_(@NotNull BlockState state, ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
      level.m_186460_(pos, this, 64);
      this.healNearbyEntities(level, pos);
   }

   private void healNearbyEntities(ServerLevel level, BlockPos pos) {
      level.m_45976_(VoidBlossomEntity.class, new AABB(pos).m_82377_(40.0, 20.0, 40.0))
         .forEach(
            voidBlossom -> {
               BMDCapabilities.getLevelEventScheduler(level)
                  .addEvent(
                     new TimedEvent(
                        () -> LichUtils.cappedHeal(
                              new EntityAdapter(voidBlossom), new EntityStats(voidBlossom), VoidBlossomEntity.hpMilestones, 10.0F, voidBlossom::m_5634_
                           ),
                        16
                     )
                  );
               BMDPacketHandler.sendToAllPlayersTrackingChunk(
                  new HealS2CPacket(VecUtils.asVec3(pos).m_82549_(VecUtils.unit.m_82490_(0.5)), voidBlossom.m_20182_().m_82549_(VecUtils.yAxis.m_82490_(5.0))),
                  level,
                  voidBlossom.m_20182_()
               );
            }
         );
   }

   @NotNull
   public BlockState m_7417_(
      @NotNull BlockState state,
      @NotNull Direction direction,
      @NotNull BlockState neighborState,
      @NotNull LevelAccessor level,
      @NotNull BlockPos pos,
      @NotNull BlockPos neighborPos
   ) {
      if (direction == Direction.DOWN && !this.m_7898_(state, level, pos)) {
         this.m_6786_(level, pos, state);
         return Blocks.f_50016_.m_49966_();
      } else {
         return super.m_7417_(state, direction, neighborState, level, pos, neighborPos);
      }
   }

   public boolean m_7898_(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
      return m_49863_(level, pos, Direction.UP) && !level.m_46801_(pos);
   }

   public void m_6786_(@NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockState state) {
      for (int x = -1; x <= 1; x++) {
         for (int z = -1; z <= 1; z++) {
            for (int y = -1; y <= 1; y++) {
               if (x != 0 || z != 0) {
                  BlockPos pos1 = pos.m_7918_(x, y, z);
                  if (level.m_8055_(pos1).m_60734_() == BMDBlocks.VINE_WALL.get()) {
                     level.m_186460_(pos1, (Block)BMDBlocks.VINE_WALL.get(), (2 - y) * 20 + RandomUtils.range(0, 19));
                  }
               }
            }
         }
      }
   }

   @NotNull
   public VoxelShape m_5940_(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
      return this.shape;
   }

   public void m_5707_(Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
      if (level.f_46443_) {
         for (int i = 0; i < 12; i++) {
            Vec3 vel = VecUtils.yAxis.m_82490_(RandomUtils.range(0.1, 0.2));
            Vec3 spawnPos = VecUtils.asVec3(pos)
               .m_82549_(VecUtils.unit.m_82490_(0.5))
               .m_82549_(VecUtils.planeProject(RandomUtils.randVec(), VecUtils.yAxis).m_82541_().m_82490_(0.5));
            VoidBlossomBlock.Particles.spikeParticleFactory.build(spawnPos, vel);
         }
      }

      super.m_5707_(level, pos, state, player);
   }

   @OnlyIn(Dist.CLIENT)
   public static void handleVoidBlossomHeal(ClientLevel level, Vec3 source, Vec3 dest) {
      spawnHealParticle(dest, source, level);
      spawnChargeParticle(source, level);
   }

   @OnlyIn(Dist.CLIENT)
   private static void spawnChargeParticle(Vec3 source, ClientLevel level) {
      Vec3 particlePos = source.m_82549_(VecUtils.yAxis.m_82490_(0.25));
      BMDCapabilities.getLevelEventScheduler(level)
         .addEvent(
            new TimedEvent(
               () -> VoidBlossomBlock.Particles.healParticleFactory.build(particlePos.m_82549_(RandomUtils.randVec().m_82490_(0.2)), Vec3.f_82478_),
               32,
               32,
               () -> false
            )
         );
   }

   @OnlyIn(Dist.CLIENT)
   private static void spawnHealParticle(Vec3 dest, Vec3 source, ClientLevel level) {
      List<Vec3> particlePositions = new ArrayList<>();
      int numCirclePoints = 16;
      List<Vec3> circlePoints = MathUtils.circlePoints(0.5, numCirclePoints, dest.m_82546_(source).m_82541_()).stream().toList();
      MathUtils.lineCallback(source, dest, 32, (pos, ix) -> particlePositions.add(pos.m_82549_(circlePoints.get(ix % numCirclePoints))));
      AtomicInteger i = new AtomicInteger();
      BMDCapabilities.getLevelEventScheduler(level).addEvent(new TimedEvent(() -> {
         VoidBlossomBlock.Particles.healParticleFactory.build(particlePositions.get(i.get()), Vec3.f_82478_);
         VoidBlossomBlock.Particles.healParticleFactory.build(particlePositions.get(i.get() + 1), Vec3.f_82478_);
         i.addAndGet(2);
      }, 0, 16, () -> false));
   }

   public static void handleVoidBlossomPlace(Vec3 pos) {
      for (int i = 0; i <= 12; i++) {
         Vec3 spawnPos = pos.m_82549_(VecUtils.planeProject(RandomUtils.randVec(), VecUtils.yAxis).m_82541_().m_82490_(0.5));
         Vec3 vel = VecUtils.yAxis.m_82490_(RandomUtils.range(0.1, 0.3));
         int randomRot = RandomUtils.range(0, 360);
         float angularMomentum = (float)RandomUtils.randSign() * 4.0F;
         VoidBlossomBlock.Particles.petalParticleFactory
            .continuousRotation(f -> (float)randomRot + (float)f.getAge() * angularMomentum)
            .continuousVelocity(f -> vel.m_82490_(1.0 - (double)f.ageRatio))
            .build(spawnPos, vel);
      }
   }

   public static class Particles {
      private static final ClientParticleBuilder spikeParticleFactory = new ClientParticleBuilder((ParticleOptions)BMDParticles.LINE.get())
         .color((Function<Float, Vec3>)(f -> MathUtils.lerpVec(f, BMDColors.VOID_PURPLE, BMDColors.ULTRA_DARK_PURPLE)))
         .colorVariation(0.15)
         .brightness(15728880)
         .scale(0.25F)
         .age(10, 15);
      private static final ClientParticleBuilder healParticleFactory = new ClientParticleBuilder((ParticleOptions)BMDParticles.OBSIDILITH_BURST.get())
         .color((Function<Float, Vec3>)(f -> MathUtils.lerpVec(f, BMDColors.PINK, BMDColors.ULTRA_DARK_PURPLE)))
         .colorVariation(0.15)
         .brightness(15728880)
         .scale(f -> 0.4F * (1.0F - f * 0.75F))
         .age(10);
      private static final ClientParticleBuilder petalParticleFactory = new ClientParticleBuilder((ParticleOptions)BMDParticles.PETAL.get())
         .color((Function<Float, Vec3>)(f -> MathUtils.lerpVec(f, BMDColors.PINK, BMDColors.ULTRA_DARK_PURPLE)))
         .brightness(15728880)
         .colorVariation(0.15)
         .scale(f -> 0.15F * (1.0F - f * 0.25F))
         .age(30);
   }
}
