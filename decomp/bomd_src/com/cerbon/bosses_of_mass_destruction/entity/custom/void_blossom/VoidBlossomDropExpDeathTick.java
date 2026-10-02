package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventScheduler;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.RandomUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityTick;
import com.cerbon.bosses_of_mass_destruction.util.VanillaCopiesServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class VoidBlossomDropExpDeathTick implements IEntityTick<ServerLevel> {
   private final LivingEntity entity;
   private final EventScheduler eventScheduler;
   private final int exp;

   public VoidBlossomDropExpDeathTick(LivingEntity entity, EventScheduler eventScheduler, int exp) {
      this.entity = entity;
      this.eventScheduler = eventScheduler;
      this.exp = exp;
   }

   public void tick(ServerLevel level) {
      if (this.entity.f_20919_ == 1) {
         this.scheduleExp();
      }
   }

   private void scheduleExp() {
      int expTicks = 20;
      int expPerTick = (int)((float)this.exp / (float)expTicks);
      Vec3 fallDirection = VecUtils.planeProject(this.entity.m_20156_(), VecUtils.yAxis).m_82524_(180.0F);
      Vec3 originPos = this.entity.m_20182_().m_82549_(VecUtils.yAxis.m_82490_(2.0));
      this.eventScheduler.addEvent(new TimedEvent(() -> {
         Vec3 pos = originPos.m_82549_(RandomUtils.randVec().m_82490_(2.0)).m_82549_(fallDirection.m_82490_(RandomUtils.randomDouble(6.0) + 6.0));
         VanillaCopiesServer.awardExperience(expPerTick, pos, this.entity.f_19853_);
      }, (int)(70.0F - (float)expTicks - 1.0F), expTicks, () -> false));
   }
}
