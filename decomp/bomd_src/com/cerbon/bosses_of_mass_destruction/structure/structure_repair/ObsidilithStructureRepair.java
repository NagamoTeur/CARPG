package com.cerbon.bosses_of_mass_destruction.structure.structure_repair;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventScheduler;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.block.BMDBlocks;
import com.cerbon.bosses_of_mass_destruction.capability.util.BMDCapabilities;
import com.cerbon.bosses_of_mass_destruction.entity.BMDEntities;
import com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith.ObsidilithEffectHandler;
import com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith.ObsidilithUtils;
import com.cerbon.bosses_of_mass_destruction.packet.BMDPacketHandler;
import com.cerbon.bosses_of_mass_destruction.packet.custom.ObsidilithReviveS2CPacket;
import com.cerbon.bosses_of_mass_destruction.structure.BMDStructures;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ObsidilithStructureRepair implements StructureRepair {
   @Override
   public ResourceKey<Structure> associatedStructure() {
      return BMDStructures.OBSIDILITH_STRUCTURE_REGISTRY.getConfiguredStructureKey();
   }

   @Override
   public void repairStructure(ServerLevel level, StructureStart structureStart) {
      BlockPos topCenter = this.getTopCenter(structureStart);
      EventScheduler levelEventScheduler = BMDCapabilities.getLevelEventScheduler(level);
      BMDPacketHandler.sendToAllPlayersTrackingChunk(
         new ObsidilithReviveS2CPacket(VecUtils.asVec3(topCenter).m_82520_(0.5, 0.5, 0.5)), level, VecUtils.asVec3(topCenter).m_82520_(0.5, 0.5, 0.5)
      );

      for (int y = 0; y <= 15; y++) {
         int y1 = y;
         levelEventScheduler.addEvent(new TimedEvent(() -> {
            level.m_5594_(null, topCenter, SoundEvents.f_12447_, SoundSource.BLOCKS, 1.0F, BMDUtils.randomPitch(level.f_46441_));

            for (Vec3 pos : ObsidilithUtils.circlePos) {
               level.m_7471_(new BlockPos((int)pos.f_82479_, y1, (int)pos.f_82481_).m_121955_(topCenter), false);
            }

            if (y1 == 0) {
               level.m_46597_(topCenter, ((Block)BMDBlocks.OBSIDILITH_SUMMON_BLOCK.get()).m_49966_());
            }
         }, y * 5));
      }
   }

   @Override
   public boolean shouldRepairStructure(ServerLevel level, StructureStart structureStart) {
      BlockPos topCenter = this.getTopCenter(structureStart);
      boolean noBoss = level.m_143280_(
            (EntityTypeTest)BMDEntities.OBSIDILITH.get(), obsidilithEntity -> obsidilithEntity.m_20238_(VecUtils.asVec3(topCenter)) < 10000.0
         )
         .isEmpty();
      boolean hasAltar = level.m_8055_(topCenter).m_60734_() == BMDBlocks.OBSIDILITH_SUMMON_BLOCK.get();
      return noBoss && !hasAltar;
   }

   private BlockPos getTopCenter(StructureStart structureStart) {
      BlockPos centerPos = structureStart.m_73601_().m_162394_();
      return new BlockPos(centerPos.m_123341_(), structureStart.m_73601_().m_162400_(), centerPos.m_123343_());
   }

   @OnlyIn(Dist.CLIENT)
   public static void handleObsidilithRevivePacket(Vec3 pos, ClientLevel level) {
      ObsidilithEffectHandler.spawnPillarParticles(pos, BMDCapabilities.getLevelEventScheduler(level));
   }
}
