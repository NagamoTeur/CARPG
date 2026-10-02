package com.hollingsworth.arsnouveau.api.item;

import com.hollingsworth.arsnouveau.client.util.ColorPos;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public interface IWandable {
   default void onFinishedConnectionFirst(@Nullable BlockPos storedPos, @Nullable LivingEntity storedEntity, Player playerEntity) {
   }

   default void onFinishedConnectionLast(@Nullable BlockPos storedPos, @Nullable LivingEntity storedEntity, Player playerEntity) {
   }

   default void onWanded(Player playerEntity) {
   }

   default List<ColorPos> getWandHighlight(List<ColorPos> list) {
      return list;
   }
}
