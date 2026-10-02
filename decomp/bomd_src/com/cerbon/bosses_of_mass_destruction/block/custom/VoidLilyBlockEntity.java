package com.cerbon.bosses_of_mass_destruction.block.custom;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.RandomUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.block.BMDBlockEntities;
import com.cerbon.bosses_of_mass_destruction.capability.util.BMDCapabilities;
import com.cerbon.bosses_of_mass_destruction.packet.BMDPacketHandler;
import com.cerbon.bosses_of_mass_destruction.packet.custom.SendParticleS2CPacket;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.particle.ClientParticleBuilder;
import com.cerbon.bosses_of_mass_destruction.structure.BMDStructures;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class VoidLilyBlockEntity extends BlockEntity {
   private Vec3 structureDirection = null;

   public VoidLilyBlockEntity(BlockPos pos, BlockState blockState) {
      super((BlockEntityType)BMDBlockEntities.VOID_LILY_BLOCK_ENTITY.get(), pos, blockState);
   }

   public void m_142466_(CompoundTag tag) {
      if (tag.m_128441_("dirX")) {
         this.structureDirection = new Vec3(tag.m_128459_("dirX"), tag.m_128459_("dirY"), tag.m_128459_("dirZ"));
      }

      super.m_142466_(tag);
   }

   protected void m_183515_(@NotNull CompoundTag tag) {
      Vec3 dir = this.structureDirection;
      if (dir != null) {
         tag.m_128347_("dirX", dir.f_82479_);
         tag.m_128347_("dirY", dir.f_82480_);
         tag.m_128347_("dirZ", dir.f_82481_);
      }

      super.m_183515_(tag);
   }

   public static void tick(Level level, BlockPos pos, BlockState state, VoidLilyBlockEntity entity) {
      if (RandomUtils.range(0, 30) == 0 && level instanceof ServerLevel serverLevel) {
         Vec3 direction = entity.structureDirection;
         if (direction == null) {
            setNearestStructureDirection(serverLevel, pos, entity);
         }

         if (direction != null) {
            BMDPacketHandler.sendToAllPlayersTrackingChunk(new SendParticleS2CPacket(pos, direction), serverLevel, VecUtils.asVec3(pos));
         }
      }
   }

   private static void setNearestStructureDirection(ServerLevel level, BlockPos pos, VoidLilyBlockEntity entity) {
      BlockPos blockPos = level.m_215011_(BMDStructures.VOID_LILY_STRUCTURE_KEY, pos, 50, false);
      if (blockPos != null) {
         entity.structureDirection = VecUtils.asVec3(new BlockPos(blockPos.m_123341_(), level.m_141937_(), blockPos.m_123343_()).m_121996_(pos)).m_82541_();
      } else {
         entity.structureDirection = VecUtils.yAxis;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static void spawnVoidLilyParticles(ClientLevel level, Vec3 pos, Vec3 dir) {
      Vec3 streakPos = pos.m_82549_(new Vec3(0.5, 0.7, 0.5)).m_82549_(RandomUtils.randVec().m_82490_(0.5));
      Vec3 right = dir.m_82537_(VecUtils.yAxis).m_82541_();
      double sinCurve = RandomUtils.range(8.0, 11.0) * (double)RandomUtils.randSign();
      double curveLength = RandomUtils.range(1.2, 1.6);
      int particleAmount = RandomUtils.range(7, 10);
      BMDCapabilities.getLevelEventScheduler(level).addEvent(new TimedEvent(() -> {
         Vec3 particlePos = streakPos.m_82549_(RandomUtils.randVec().m_82490_(0.05));
         VoidLilyBlockEntity.Particles.pollenParticles.continuousPosition(p -> {
            float age = p.ageRatio;
            Vec3 forward = dir.m_82490_((double)age * curveLength);
            Vec3 sinSide = right.m_82490_(Math.sin((double)age * sinCurve) * 0.1);
            return particlePos.m_82549_(forward).m_82549_(sinSide);
         }).build(particlePos, Vec3.f_82478_);
      }, 0, particleAmount, () -> false));
   }

   private static class Particles {
      @OnlyIn(Dist.CLIENT)
      private static final ClientParticleBuilder pollenParticles = new ClientParticleBuilder((ParticleOptions)BMDParticles.POLLEN.get())
         .scale(age -> (float)(Math.sin((double)(age * (float) Math.PI)) * 0.04F))
         .age(30);
   }
}
