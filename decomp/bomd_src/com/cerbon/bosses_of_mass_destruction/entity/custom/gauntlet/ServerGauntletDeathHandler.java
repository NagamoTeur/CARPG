package com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventScheduler;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.RandomUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.config.mob.GauntletConfig;
import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityTick;
import com.cerbon.bosses_of_mass_destruction.util.VanillaCopiesServer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.phys.Vec3;

public class ServerGauntletDeathHandler implements IEntityTick<ServerLevel> {
   private final GauntletEntity entity;
   private final EventScheduler eventScheduler;
   private final GauntletConfig mobConfig;
   public static final int deathAnimationTime = 50;

   public ServerGauntletDeathHandler(GauntletEntity entity, EventScheduler eventScheduler, GauntletConfig mobConfig) {
      this.entity = entity;
      this.eventScheduler = eventScheduler;
      this.mobConfig = mobConfig;
   }

   public void tick(ServerLevel level) {
      this.entity.f_20919_++;
      if (this.entity.f_20919_ == 50) {
         level.m_46511_(
            null,
            this.entity.m_20182_().f_82479_,
            this.entity.m_20182_().f_82480_,
            this.entity.m_20182_().f_82481_,
            4.0F,
            VanillaCopiesServer.getEntityDestructionType(this.entity.f_19853_)
         );
         if (this.mobConfig.spawnAncientDebrisOnDeath) {
            this.createLoot(level);
         }

         this.dropExp();
         this.entity.m_142687_(RemovalReason.KILLED);
      }
   }

   private void createLoot(ServerLevel level) {
      for (int i = 0; i <= 4; i++) {
         Vec3 randomDir = RandomUtils.randVec().m_82541_();
         int length = 8 - i;
         Vec3 start = this.entity.m_20182_();
         Vec3 end = this.entity.m_20182_().m_82549_(randomDir.m_82490_((double)length));
         int points = length * 2;
         MathUtils.lineCallback(start, end, points, (vec3, point) -> {
            BlockPos blockPos = new BlockPos(vec3);
            if (point == points - 1) {
               level.m_46597_(blockPos, Blocks.f_50722_.m_49966_());
            } else {
               level.m_46597_(blockPos, Blocks.f_50134_.m_49966_());
            }
         });
      }

      BlockPos chestPos = this.entity.m_20183_().m_7494_();
      level.m_7731_(chestPos, Blocks.f_50087_.m_49966_(), 2);
      RandomizableContainerBlockEntity.m_222766_(
         level, this.entity.m_217043_(), chestPos, new ResourceLocation("bosses_of_mass_destruction", "chests/gauntlet")
      );
   }

   private void dropExp() {
      int expTicks = 20;
      int expPerTick = (int)((float)this.mobConfig.experienceDrop / (float)expTicks);
      Vec3 pos = this.entity.m_20182_();
      this.eventScheduler
         .addEvent(
            new TimedEvent(
               () -> VanillaCopiesServer.awardExperience(
                     expPerTick, pos.m_82549_(VecUtils.planeProject(RandomUtils.randVec(), VecUtils.yAxis).m_82490_(2.0)), this.entity.f_19853_
                  ),
               0,
               expTicks,
               () -> false
            )
         );
   }
}
