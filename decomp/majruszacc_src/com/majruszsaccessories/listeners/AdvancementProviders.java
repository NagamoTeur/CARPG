package com.majruszsaccessories.listeners;

import com.majruszlibrary.events.OnItemCrafted;
import com.majruszlibrary.events.base.Priority;
import com.majruszsaccessories.MajruszsAccessories;
import com.majruszsaccessories.common.AccessoryHolder;
import com.majruszsaccessories.items.AccessoryItem;
import net.minecraft.server.level.ServerPlayer;

public class AdvancementProviders {
   private static void giveAdvancements(OnItemCrafted data) {
      ServerPlayer player = (ServerPlayer)data.player;
      AccessoryHolder holder = AccessoryHolder.getOrCreate(data.itemStack);
      if (holder.hasAnyBooster()) {
         MajruszsAccessories.HELPER.triggerAchievement(player, "booster_used");
      }

      if (Math.abs((double)holder.getBonus() - 0.69) < 1.0E-5) {
         MajruszsAccessories.HELPER.triggerAchievement(player, "booster_nice");
      }
   }

   static {
      OnItemCrafted.listen(AdvancementProviders::giveAdvancements)
         .addCondition(data -> data.player instanceof ServerPlayer)
         .addCondition(data -> data.itemStack.m_41720_() instanceof AccessoryItem)
         .priority(Priority.LOWEST);
   }
}
