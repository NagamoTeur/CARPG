package com.hollingsworth.arsnouveau.api.util;

import com.hollingsworth.arsnouveau.api.item.inv.FilterableItemHandler;
import com.hollingsworth.arsnouveau.common.items.ItemScroll;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;
import org.jetbrains.annotations.NotNull;

public class InvUtil {
   public static List<FilterableItemHandler> adjacentInventories(Level level, BlockPos pos) {
      List<FilterableItemHandler> inventories = new ArrayList<>();

      for (Direction d : Direction.values()) {
         BlockEntity adjacentInvTile = level.m_7702_(pos.m_121945_(d));
         if (adjacentInvTile != null && !adjacentInvTile.m_58901_()) {
            IItemHandler handler = (IItemHandler)adjacentInvTile.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null);
            if (handler != null) {
               inventories.add(new FilterableItemHandler(handler, filtersOnTile(adjacentInvTile)));
            }
         }
      }

      return inventories;
   }

   public static FilterableItemHandler getFilteredHandler(@NotNull BlockEntity tile) {
      return new FilterableItemHandler((IItemHandler)tile.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null), filtersOnTile(tile));
   }

   public static List<Function<ItemStack, ItemScroll.SortPref>> filtersOnTile(@Nullable BlockEntity thisTile) {
      if (thisTile != null && !thisTile.m_58901_()) {
         Level level = thisTile.m_58904_();
         BlockPos pos = thisTile.m_58899_();
         List<Function<ItemStack, ItemScroll.SortPref>> filters = new ArrayList<>();
         IItemHandler inv = (IItemHandler)thisTile.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null);
         if (inv == null) {
            return filters;
         } else {
            for (ItemFrame i : level.m_45976_(ItemFrame.class, new AABB(pos).m_82400_(1.0))) {
               BlockEntity tileOnFrame = level.m_7702_(i.m_20183_().m_121945_(i.m_6350_().m_122424_()));
               ItemStack stackInFrame = i.m_31822_();
               if (tileOnFrame != null && tileOnFrame.equals(thisTile) && !i.m_31822_().m_41619_() && !stackInFrame.m_41619_()) {
                  if (stackInFrame.m_41720_() instanceof ItemScroll scrollItem) {
                     filters.add(stackToStore -> scrollItem.getSortPref(stackToStore, stackInFrame, inv));
                  } else {
                     filters.add(stackToStore -> stackToStore.m_41720_() == stackInFrame.m_41720_() ? ItemScroll.SortPref.HIGHEST : ItemScroll.SortPref.INVALID);
                  }
               }
            }

            return filters;
         }
      } else {
         return new ArrayList<>();
      }
   }

   public static List<FilterableItemHandler> fromPlayer(Player player) {
      List<FilterableItemHandler> list = new ArrayList<>();
      list.add(new FilterableItemHandler(new PlayerMainInvWrapper(player.f_36093_), new ArrayList<>()));
      return list;
   }
}
