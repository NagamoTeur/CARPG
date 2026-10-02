package com.cerbon.bosses_of_mass_destruction.block.custom;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventScheduler;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.RandomUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.capability.util.BMDCapabilities;
import com.cerbon.bosses_of_mass_destruction.entity.BMDEntities;
import com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet.GauntletEntity;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.particle.ClientParticleBuilder;
import com.cerbon.bosses_of_mass_destruction.util.BMDColors;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class GauntletBlackstoneBlock extends Block {
   public GauntletBlackstoneBlock(Properties properties) {
      super(properties);
   }

   public void m_5707_(Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
      if (!level.f_46443_) {
         for (Direction dir : List.of(Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH)) {
            BlockPos centerPos = pos.m_121955_(dir.m_122436_());
            Block centerBlock = level.m_8055_(centerPos).m_60734_();
            if (centerBlock == this) {
               this.spawnGauntlet(centerPos, level);
               break;
            }
         }

         super.m_5707_(level, pos, state, player);
      }
   }

   private void spawnGauntlet(BlockPos centerPos, Level level) {
      Vec3 spawnPos = VecUtils.asVec3(centerPos).m_82549_(new Vec3(0.5, -0.5, 0.5));
      GauntletEntity entity = (GauntletEntity)((EntityType)BMDEntities.GAUNTLET.get()).m_20615_(level);
      if (entity != null) {
         entity.m_20248_(spawnPos.f_82479_, spawnPos.f_82480_, spawnPos.f_82481_);
         level.m_7967_(entity);
      }

      EventScheduler eventScheduler = BMDCapabilities.getLevelEventScheduler(level);

      for (int y = -1; y <= 4; y++) {
         int y1 = y;
         eventScheduler.addEvent(new TimedEvent(() -> {
            for (int x = -1; x <= 1; x++) {
               for (int z = -1; z <= 1; z++) {
                  level.m_46961_(centerPos.m_7918_(x, y1, z), false);
               }
            }
         }, 10 + y * 5));
      }
   }

   public void m_214162_(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
      Vec3 particlePos = VecUtils.asVec3(pos).m_82549_(VecUtils.unit.m_82490_(0.5)).m_82549_(RandomUtils.randVec().m_82541_());
      GauntletBlackstoneBlock.Particles.laserChargeParticles.build(particlePos, Vec3.f_82478_);
   }

   public static class Particles {
      public static final ClientParticleBuilder laserChargeParticles = new ClientParticleBuilder((ParticleOptions)BMDParticles.SPARKLES.get())
         .brightness(15728880)
         .color(BMDColors.LASER_RED)
         .colorVariation(0.2);
   }
}
