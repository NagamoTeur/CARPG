package com.aizistral.enigmaticlegacy.handlers;

import java.util.function.Function;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.ITeleporter;

public class RealSmoothTeleporter implements ITeleporter {
   public RealSmoothTeleporter() {
   }

   public RealSmoothTeleporter(ServerPlayer serverPlayer, double x, double y, double z) {
      serverPlayer.m_6027_(x, y, z);
   }

   public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
      return entity instanceof ServerPlayer ? repositionEntity.apply(false) : repositionEntity.apply(false);
   }

   private void fireTriggers(ServerLevel p_213846_1_, ServerPlayer player) {
      ResourceKey<Level> registrykey = p_213846_1_.m_46472_();
      ResourceKey<Level> registrykey1 = player.f_19853_.m_46472_();
      CriteriaTriggers.f_10588_.m_19757_(player, registrykey, registrykey1);
   }
}
