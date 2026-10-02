package com.cerbon.bosses_of_mass_destruction.entity.custom.lich;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.config.mob.LichConfig;
import com.cerbon.bosses_of_mass_destruction.item.BMDItems;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

public class LichKillCounter {
   private final LichConfig.SummonMechanic config;
   private final List<EntityType<?>> countedEntities;

   public LichKillCounter(LichConfig.SummonMechanic config) {
      this.config = config;
      this.countedEntities = config.entitiesThatCountToSummonCounter != null
         ? config.entitiesThatCountToSummonCounter
            .stream()
            .map(string -> (EntityType)ForgeRegistries.ENTITY_TYPES.getValue(ResourceLocation.m_135820_(string)))
            .collect(Collectors.toList())
         : List.of();
   }

   public void afterKilledOtherEntity(Entity entity, LivingEntity killedEntity) {
      if (entity instanceof ServerPlayer && this.countedEntities.contains(killedEntity.m_6095_())) {
         int entitiesKilled = this.getUndeadKilled((ServerPlayer)entity);
         if (entitiesKilled > 0 && entitiesKilled % this.config.numEntitiesKilledToDropSoulStar == 0) {
            BMDUtils.spawnParticle(
               ((ServerPlayer)entity).m_9236_(),
               (ParticleOptions)BMDParticles.SOUL_FLAME.get(),
               killedEntity.m_20182_().m_82549_(VecUtils.yAxis),
               VecUtils.unit,
               15,
               0.0
            );
            killedEntity.m_19998_((ItemLike)BMDItems.SOUL_STAR.get());
         }
      }
   }

   private int getUndeadKilled(ServerPlayer entity) {
      return this.countedEntities.stream().mapToInt(entityType -> entity.m_8951_().m_13015_(Stats.f_12986_.m_12902_(entityType))).sum();
   }
}
