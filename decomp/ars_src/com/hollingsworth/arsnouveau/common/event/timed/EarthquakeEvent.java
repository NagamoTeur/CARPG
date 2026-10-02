package com.hollingsworth.arsnouveau.common.event.timed;

import com.hollingsworth.arsnouveau.api.event.ITimedEvent;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;

public class EarthquakeEvent implements ITimedEvent {
   int ticks;
   BlockPos origin;
   Level world;
   List<BlockPos> posList = new ArrayList<>();
   int counter;

   public EarthquakeEvent(Level world, BlockPos origin, BlockPos destination) {
      this.origin = origin;
      this.world = world;
   }

   @Override
   public void tick(boolean serverSide) {
      for (int i = 0; i < 5; i++) {
         if (this.counter < this.posList.size() && (double)this.world.m_213780_().m_188501_() < 0.5) {
            BlockPos p = this.posList.get(this.counter);
            FallingBlockEntity blockEntity = FallingBlockEntity.m_201971_(this.world, p, this.world.m_8055_(p));
            blockEntity.m_20334_(0.0, 0.5 + ParticleUtil.inRange(-0.1, 0.1), 0.0);
            this.world.m_7967_(blockEntity);
         }

         this.counter++;
      }
   }

   @Override
   public boolean isExpired() {
      return this.counter > this.posList.size();
   }
}
