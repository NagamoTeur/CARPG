package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventScheduler;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventSeries;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.block.BMDBlocks;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.hitbox.HitboxId;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.hitbox.NetworkedHitboxManager;
import com.cerbon.bosses_of_mass_destruction.packet.BMDPacketHandler;
import com.cerbon.bosses_of_mass_destruction.packet.custom.PlaceS2CPacket;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class BlossomAction implements IActionWithCooldown {
   private final VoidBlossomEntity entity;
   private final EventScheduler eventScheduler;
   private final Supplier<Boolean> shouldCancel;
   private final List<Vec3> blossomPositions = Stream.of(
         VecUtils.xAxis,
         VecUtils.zAxis,
         VecUtils.xAxis.m_82548_(),
         VecUtils.zAxis.m_82548_(),
         VecUtils.xAxis.m_82549_(VecUtils.zAxis),
         VecUtils.xAxis.m_82549_(VecUtils.zAxis.m_82548_()),
         VecUtils.xAxis.m_82548_().m_82549_(VecUtils.zAxis),
         VecUtils.xAxis.m_82548_().m_82549_(VecUtils.zAxis.m_82548_())
      )
      .map(vec3 -> vec3.m_82541_().m_82490_(15.0))
      .toList();

   public BlossomAction(VoidBlossomEntity entity, EventScheduler eventScheduler, Supplier<Boolean> shouldCancel) {
      this.entity = entity;
      this.eventScheduler = eventScheduler;
      this.shouldCancel = shouldCancel;
   }

   @Override
   public int perform() {
      Level level = this.entity.f_19853_;
      if (!(level instanceof ServerLevel)) {
         return 80;
      } else {
         this.eventScheduler
            .addEvent(
               new EventSeries(
                  new TimedEvent(() -> this.entity.m_20088_().m_135381_(NetworkedHitboxManager.hitbox, HitboxId.SpikeWave3.getId()), 20, 1, this.shouldCancel),
                  new TimedEvent(() -> this.entity.m_20088_().m_135381_(NetworkedHitboxManager.hitbox, HitboxId.Idle.getId()), 80)
               )
            );
         this.placeBlossoms((ServerLevel)level);
         return 120;
      }
   }

   private void placeBlossoms(ServerLevel level) {
      List<BlockPos> positions = this.blossomPositions.stream().map(pos -> new BlockPos(pos.m_82549_(this.entity.m_20182_()))).collect(Collectors.toList());
      Collections.shuffle(positions);
      float hpRatio = this.entity.m_21223_() / this.entity.m_21233_();
      int protectedPositions;
      if (hpRatio < VoidBlossomEntity.hpMilestones.get(1)) {
         protectedPositions = 6;
      } else if (hpRatio < VoidBlossomEntity.hpMilestones.get(2)) {
         protectedPositions = 3;
      } else {
         protectedPositions = 0;
      }

      BMDUtils.playSound(level, this.entity.m_20182_(), (SoundEvent)BMDSounds.SPIKE_WAVE_INDICATOR.get(), SoundSource.HOSTILE, 2.0F, 0.7F, 64.0, null);

      for (int i = 0; i < 8; i++) {
         int i1 = i;
         this.eventScheduler
            .addEvent(
               new TimedEvent(
                  () -> {
                     BlockPos blossomPos = positions.get(i1);
                     level.m_46597_(blossomPos, Blocks.f_152544_.m_49966_());
                     level.m_46597_(blossomPos.m_7494_(), ((Block)BMDBlocks.VOID_BLOSSOM.get()).m_49966_());
                     BMDPacketHandler.sendToAllPlayersTrackingChunk(
                        new PlaceS2CPacket(VecUtils.asVec3(blossomPos).m_82549_(VecUtils.unit.m_82490_(0.5))), level, this.entity.m_20182_()
                     );
                     BMDUtils.playSound(
                        level,
                        VecUtils.asVec3(blossomPos),
                        (SoundEvent)BMDSounds.PETAL_BLADE.get(),
                        SoundSource.HOSTILE,
                        1.0F,
                        BMDUtils.randomPitch(this.entity.m_217043_()),
                        64.0,
                        null
                     );
                     if (i1 < protectedPositions) {
                        for (int x = -1; x <= 1; x++) {
                           for (int z = -1; z <= 1; z++) {
                              for (int y = 0; y <= 2; y++) {
                                 if (x != 0 || z != 0) {
                                    level.m_46597_(blossomPos.m_7918_(x, y, z), ((Block)BMDBlocks.VINE_WALL.get()).m_49966_());
                                 }
                              }
                           }
                        }
                     }
                  },
                  40 + i * 8,
                  1,
                  this.shouldCancel
               )
            );
      }
   }
}
