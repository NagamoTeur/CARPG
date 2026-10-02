package com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventScheduler;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.RandomUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.capability.util.BMDCapabilities;
import com.cerbon.bosses_of_mass_destruction.client.render.NodeBossBarRenderer;
import com.cerbon.bosses_of_mass_destruction.entity.BMDEntities;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import com.cerbon.bosses_of_mass_destruction.util.VanillaCopiesServer;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.phys.Vec3;

public class ObsidilithUtils {
   private static final int textureSize = 256;
   private static final ResourceLocation bossBarDividerTexture = new ResourceLocation(
      "bosses_of_mass_destruction", "textures/gui/obsidilith_boss_bar_dividers.png"
   );
   public static final List<Float> hpPillarShieldMilestones = List.of(0.0F, 0.25F, 0.5F, 0.75F, 1.0F);
   public static final EntityDataAccessor<Boolean> isShielded = SynchedEntityData.m_135353_(ObsidilithEntity.class, EntityDataSerializers.f_135035_);
   public static final NodeBossBarRenderer obsidilithBossBarRenderer = new NodeBossBarRenderer(
      ((EntityType)BMDEntities.OBSIDILITH.get()).m_20675_(), hpPillarShieldMilestones, bossBarDividerTexture, 256
   );
   public static final List<Vec3> circlePos = MathUtils.buildBlockCircle(Math.sqrt(Math.pow(2.0, 2.0) + Math.pow(1.0, 2.0)));
   public static final byte deathStatus = 3;
   public static final byte burstAttackStatus = 5;
   public static final byte waveAttackStatus = 6;
   public static final byte spikeAttackStatus = 7;
   public static final byte anvilAttackStatus = 8;
   public static final byte pillarDefenseStatus = 9;
   public static final int deathPillarHeight = 15;
   public static final int ticksBetweenPillarLayer = 5;

   public static Vec3 approximatePlayerNextPosition(List<Vec3> previousPosition, Vec3 currentPos) {
      return previousPosition.stream()
         .map(vec3 -> VecUtils.planeProject(vec3.m_82546_(currentPos), VecUtils.yAxis))
         .reduce(Vec3::m_82549_)
         .orElse(Vec3.f_82478_)
         .m_82542_(-0.5, -0.5, -0.5)
         .m_82549_(currentPos);
   }

   public static void onDeath(LivingEntity actor, int experienceDrop) {
      Level level = actor.f_19853_;
      if (!level.m_5776_()) {
         BlockPos blockPos = actor.m_20183_();
         Vec3 vecPos = actor.m_20182_();
         EventScheduler eventScheduler = BMDCapabilities.getLevelEventScheduler(level);
         level.m_46511_(actor, actor.m_20185_(), actor.m_20186_(), actor.m_20189_(), 2.0F, BlockInteraction.NONE);

         for (int y = 0; y <= 15; y++) {
            int y1 = y;
            eventScheduler.addEvent(new TimedEvent(() -> {
               actor.m_5496_(SoundEvents.f_12447_, 1.0F, BMDUtils.randomPitch(actor.m_217043_()));

               for (Vec3 pos : circlePos) {
                  level.m_46597_(new BlockPos((int)pos.f_82479_, y1, (int)pos.f_82481_).m_121955_(blockPos), Blocks.f_50080_.m_49966_());
               }
            }, y * 5));
         }

         eventScheduler.addEvent(
            new TimedEvent(
               () -> {
                  BlockPos chestPos = blockPos.m_6630_(16);
                  level.m_7731_(chestPos, Blocks.f_50456_.m_49966_(), 2);
                  RandomizableContainerBlockEntity.m_222766_(
                     level, actor.m_217043_(), chestPos, new ResourceLocation("bosses_of_mass_destruction", "chests/obsidilith")
                  );
               },
               75
            )
         );
         int expTicks = 20;
         int expPerTick = (int)((float)experienceDrop / (float)expTicks);
         Vec3 pillarTop = vecPos.m_82549_(VecUtils.yAxis.m_82490_(15.0));
         eventScheduler.addEvent(
            new TimedEvent(
               () -> VanillaCopiesServer.awardExperience(
                     expPerTick, pillarTop.m_82549_(VecUtils.planeProject(RandomUtils.randVec(), VecUtils.yAxis).m_82490_(2.0)), level
                  ),
               75,
               expTicks,
               () -> false
            )
         );
      }
   }
}
