package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.RandomUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityTick;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.particle.ClientParticleBuilder;
import com.cerbon.bosses_of_mass_destruction.util.BMDColors;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class VoidBlossomClientSpikeHandler implements IEntityTick<Level> {
   private final LinkedHashMap<BlockPos, VoidBlossomClientSpikeHandler.Spike> spikes = new LinkedHashMap<>();
   private final ClientParticleBuilder spikeParticleFactory = new ClientParticleBuilder((ParticleOptions)BMDParticles.SPARKLES.get())
      .age(RandomUtils.range(10, 15))
      .color(BMDColors.VOID_PURPLE)
      .colorVariation(0.25)
      .brightness(15728880);
   private final int maxAge = 10;

   public Map<BlockPos, VoidBlossomClientSpikeHandler.Spike> getSpikes() {
      return this.spikes;
   }

   public void addSpike(BlockPos pos) {
      Vec3 center = VecUtils.asVec3(pos).m_82549_(VecUtils.unit.m_82490_(0.5));
      double spikeHeight = 4.0 + RandomUtils.randomDouble(0.5);
      this.spikes
         .put(
            pos,
            new VoidBlossomClientSpikeHandler.Spike(
               center, RandomUtils.randVec().m_82549_(VecUtils.yAxis.m_82490_(spikeHeight)).m_82541_(), (float)spikeHeight, 10, 0
            )
         );
   }

   @Override
   public void tick(Level level) {
      List<BlockPos> toRemove = new ArrayList<>();

      for (Entry<BlockPos, VoidBlossomClientSpikeHandler.Spike> kv : this.spikes.entrySet()) {
         VoidBlossomClientSpikeHandler.Spike oldSpike = kv.getValue();
         int newAge = oldSpike.age() + 1;
         VoidBlossomClientSpikeHandler.Spike newSpike = new VoidBlossomClientSpikeHandler.Spike(
            oldSpike.pos(), oldSpike.offset(), oldSpike.height(), oldSpike.maxAge(), newAge
         );
         this.spikes.put(kv.getKey(), newSpike);
         if (newAge == 5) {
            this.spikeParticleFactory
               .build(
                  VecUtils.asVec3(kv.getKey()).m_82549_(RandomUtils.randVec().m_82549_(VecUtils.yAxis.m_82490_(2.5 + RandomUtils.randomDouble(2.0)))),
                  Vec3.f_82478_
               );
         }

         if (newAge >= 10) {
            toRemove.add(kv.getKey());
         }
      }

      for (BlockPos removal : toRemove) {
         this.spikes.remove(removal);
      }
   }

   public static record Spike(Vec3 pos, Vec3 offset, float height, int maxAge, int age) {
   }
}
