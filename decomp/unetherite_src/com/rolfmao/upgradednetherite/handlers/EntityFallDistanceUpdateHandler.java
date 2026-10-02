package com.rolfmao.upgradednetherite.handlers;

import com.rolfmao.upgradednetherite.UpgradedNetheriteMod;
import com.rolfmao.upgradednetherite.packets.PacketEntityFallDistanceUpdate;
import net.minecraft.world.entity.Entity;

public class EntityFallDistanceUpdateHandler {
   public static void EntityFallDistanceUpdate(Integer entityId, Float fallDistance) {
      UpgradedNetheriteMod.packetInstance.sendToServer(new PacketEntityFallDistanceUpdate(entityId, fallDistance));
   }

   public static void handleEntityFallDistanceUpdate(Entity entity, Float fallDistance) {
      entity.f_19789_ = fallDistance;
   }
}
