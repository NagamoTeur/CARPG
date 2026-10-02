package com.cerbon.bosses_of_mass_destruction.event;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventScheduler;
import com.cerbon.bosses_of_mass_destruction.block.custom.LevitationBlockEntity;
import com.cerbon.bosses_of_mass_destruction.capability.ChunkBlockCacheProvider;
import com.cerbon.bosses_of_mass_destruction.capability.LevelEventSchedulerProvider;
import com.cerbon.bosses_of_mass_destruction.capability.PlayerMoveHistoryProvider;
import com.cerbon.bosses_of_mass_destruction.entity.BMDEntities;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
   modid = "bosses_of_mass_destruction"
)
public class ForgeEvents {
   @SubscribeEvent
   public static void onAttachCapabilitiesLevel(AttachCapabilitiesEvent<Level> event) {
      if (event.getObject() != null) {
         if (!((Level)event.getObject()).getCapability(LevelEventSchedulerProvider.EVENT_SCHEDULER).isPresent()) {
            event.addCapability(new ResourceLocation("bosses_of_mass_destruction", "event_scheduler"), new LevelEventSchedulerProvider());
         }

         if (!((Level)event.getObject()).getCapability(ChunkBlockCacheProvider.CHUNK_BLOCK_CACHE).isPresent()) {
            event.addCapability(new ResourceLocation("bosses_of_mass_destruction", "chunk_block_cache_capability"), new ChunkBlockCacheProvider());
         }
      }
   }

   @SubscribeEvent
   public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
      if (event.getObject() instanceof Player player && !player.getCapability(PlayerMoveHistoryProvider.HISTORICAL_DATA).isPresent()) {
         event.addCapability(new ResourceLocation("bosses_of_mass_destruction", "player_move_history"), new PlayerMoveHistoryProvider());
      }
   }

   @SubscribeEvent
   protected static void onPlayerTick(PlayerTickEvent event) {
      if (event.side == LogicalSide.SERVER) {
         event.player.getCapability(PlayerMoveHistoryProvider.HISTORICAL_DATA).ifPresent(data -> {
            Vec3 previousPosition = (Vec3)data.get(0);
            Vec3 newPosition = event.player.m_20182_();
            if (previousPosition.m_82557_(newPosition) > 5.0) {
               data.clear();
            }

            data.set(newPosition);
         });
         LevitationBlockEntity.tickFlight((ServerPlayer)event.player);
      }
   }

   @SubscribeEvent
   public static void onLevelTick(LevelTickEvent event) {
      if ((event.side == LogicalSide.SERVER || event.side == LogicalSide.CLIENT) && event.level.m_46467_() % 2L == 0L) {
         event.level.getCapability(LevelEventSchedulerProvider.EVENT_SCHEDULER).ifPresent(EventScheduler::updateEvents);
      }
   }

   @SubscribeEvent(
      priority = EventPriority.HIGH
   )
   public static void onLivingDeath(LivingDeathEvent event) {
      if (BMDEntities.mobConfig.lichConfig.summonMechanic.isEnabled) {
         Entity attacker = event.getSource().m_7639_();
         if (attacker != null) {
            BMDEntities.killCounter.afterKilledOtherEntity(attacker, event.getEntity());
         }
      }
   }
}
