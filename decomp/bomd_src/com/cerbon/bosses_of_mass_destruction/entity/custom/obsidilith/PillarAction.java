package com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventScheduler;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MobUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.RandomUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.block.BMDBlocks;
import com.cerbon.bosses_of_mass_destruction.capability.util.BMDCapabilities;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class PillarAction implements IActionWithCooldown {
   private final LivingEntity entity;
   private final EventScheduler eventScheduler;
   public static final double pillarXzDistance = 13.0;
   public static final double maxYDistance = 15.0;
   public static final int pillarDelay = 40;

   public PillarAction(LivingEntity entity) {
      this.entity = entity;
      this.eventScheduler = BMDCapabilities.getLevelEventScheduler(entity.f_19853_);
   }

   @Override
   public int perform() {
      Level level = this.entity.f_19853_;
      if (!(level instanceof ServerLevel)) {
         return 80;
      } else {
         List<BlockPos> pillarPositions = this.getPillarPositions();
         BMDUtils.playSound(
            (ServerLevel)level, this.entity.m_20182_(), (SoundEvent)BMDSounds.OBSIDILITH_PREPARE_ATTACK.get(), SoundSource.HOSTILE, 3.0F, 1.4F, 64.0, null
         );
         pillarPositions.forEach(
            blockPos -> {
               MathUtils.lineCallback(
                  MobUtils.eyePos(this.entity),
                  VecUtils.asVec3(blockPos).m_82520_(0.5, 0.5, 0.5),
                  13,
                  (vec3, integer) -> BMDUtils.spawnParticle(
                        (ServerLevel)level, (ParticleOptions)BMDParticles.PILLAR_SPAWN_INDICATOR_2.get(), vec3, Vec3.f_82478_, 0, 0.0
                     )
               );
               BMDUtils.spawnParticle(
                  (ServerLevel)level,
                  (ParticleOptions)BMDParticles.PILLAR_SPAWN_INDICATOR.get(),
                  VecUtils.asVec3(blockPos.m_6630_(5)),
                  new Vec3(0.3, 3.0, 0.3),
                  20,
                  0.0
               );
            }
         );
         this.eventScheduler
            .addEvent(
               new TimedEvent(() -> pillarPositions.forEach(blockPos -> this.buildPillar(blockPos, (ServerLevel)level)), 40, 1, () -> !this.entity.m_6084_())
            );
         return 100;
      }
   }

   private List<BlockPos> getPillarPositions() {
      int numPillars = 4;
      List<BlockPos> pillars = new ArrayList<>();

      for (int i = 0; i < numPillars; i++) {
         Vec3 position = VecUtils.planeProject(RandomUtils.randVec(), VecUtils.yAxis).m_82541_().m_82490_(13.0).m_82549_(this.entity.m_20182_());
         BlockPos above = BMDUtils.findGroundBelow(this.entity.f_19853_, new BlockPos(position).m_6630_(14), pos -> true);
         BlockPos ground = BMDUtils.findGroundBelow(this.entity.f_19853_, above, pos -> true);
         if (!((double)(above.m_123342_() - ground.m_123342_()) > 15.0)) {
            pillars.add(ground);
         }
      }

      return pillars;
   }

   private void buildPillar(BlockPos pos, ServerLevel serverLevel) {
      int pillarHeight = 2;
      IntStream.range(0, pillarHeight).forEach(i -> this.entity.f_19853_.m_46597_(pos.m_6630_(i), Blocks.f_50080_.m_49966_()));
      BlockPos pillarTop = pos.m_6630_(pillarHeight);
      this.entity.f_19853_.m_46597_(pillarTop, ((Block)BMDBlocks.OBSIDILITH_RUNE.get()).m_49966_());
      BMDUtils.playSound(serverLevel, VecUtils.asVec3(pos), SoundEvents.f_11728_, SoundSource.HOSTILE, 1.0F, 16.0, null);
   }
}
