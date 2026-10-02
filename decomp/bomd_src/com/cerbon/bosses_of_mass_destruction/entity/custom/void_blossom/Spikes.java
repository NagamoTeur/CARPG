package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventScheduler;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.damagesource.UnshieldableDamageSource;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Spikes {
   private final LivingEntity entity;
   private final ServerLevel level;
   private final ParticleOptions indicatorParticle;
   private final int riftTime;
   private final EventScheduler eventScheduler;
   private final Supplier<Boolean> shouldCancel;

   public Spikes(
      LivingEntity entity, ServerLevel level, ParticleOptions indicatorParticle, int riftTime, EventScheduler eventScheduler, Supplier<Boolean> shouldCancel
   ) {
      this.entity = entity;
      this.level = level;
      this.indicatorParticle = indicatorParticle;
      this.riftTime = riftTime;
      this.eventScheduler = eventScheduler;
      this.shouldCancel = shouldCancel;
   }

   public List<BlockPos> tryPlaceRift(Vec3 pos) {
      List<BlockPos> blockPosList = new ArrayList<>();

      for (int i = 0; i <= 12; i += 6) {
         BlockPos above = new BlockPos(pos.m_82549_(VecUtils.yAxis.m_82490_((double)i)));
         BlockPos groundPos = BMDUtils.findGroundBelow(this.level, above, this::isOpenBlock);
         BlockPos up = groundPos.m_7494_();
         if (up.m_123342_() + 5 >= above.m_123342_()) {
            this.placeRift(up);
            blockPosList.add(groundPos);
         }
      }

      return blockPosList;
   }

   private void placeRift(BlockPos pos) {
      BMDUtils.spawnParticle(this.level, this.indicatorParticle, VecUtils.asVec3(pos).m_82549_(new Vec3(0.5, 0.1, 0.5)), Vec3.f_82478_, 0, 0.0);
      this.eventScheduler.addEvent(new TimedEvent(() -> {
         AABB box = new AABB(pos).m_82377_(0.0, 4.0, 0.0);
         List<LivingEntity> entities = this.level.m_6443_(LivingEntity.class, box, livingEntity -> livingEntity != this.entity);
         entities.forEach(livingEntity -> {
            if (livingEntity != this.entity) {
               this.damageEntity(livingEntity);
            }
         });
      }, this.riftTime, 1, this.shouldCancel));
   }

   private void damageEntity(LivingEntity livingEntity) {
      float damage = (float)this.entity.m_21133_(Attributes.f_22281_);
      livingEntity.m_6469_(new UnshieldableDamageSource(this.entity), damage);
   }

   private boolean isOpenBlock(BlockPos up) {
      return this.level.m_8055_(up).m_60629_(new DirectionalPlaceContext(this.level, up, Direction.DOWN, ItemStack.f_41583_, Direction.UP))
         || this.level.m_8055_(up).m_60734_() == Blocks.f_152543_;
   }
}
