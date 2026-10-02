package com.hollingsworth.arsnouveau.api.spell.wrapped_caster;

import com.hollingsworth.arsnouveau.api.item.inv.FilterableItemHandler;
import com.hollingsworth.arsnouveau.api.item.inv.InventoryManager;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IWrappedCaster {
   SpellContext.CasterType getCasterType();

   default InventoryManager getInvManager() {
      return new InventoryManager(this.getInventory());
   }

   @NotNull
   default List<FilterableItemHandler> getInventory() {
      return new ArrayList<>();
   }

   default Direction getFacingDirection() {
      return Direction.NORTH;
   }

   @Nullable
   default BlockEntity getNearbyBlockEntity(Predicate<BlockEntity> predicate) {
      return null;
   }

   default Vec3 getPosition() {
      return Vec3.f_82478_;
   }
}
