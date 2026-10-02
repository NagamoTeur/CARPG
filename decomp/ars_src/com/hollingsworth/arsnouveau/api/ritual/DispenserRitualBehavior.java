package com.hollingsworth.arsnouveau.api.ritual;

import com.hollingsworth.arsnouveau.common.block.tile.RitualBrazierTile;
import com.hollingsworth.arsnouveau.common.items.RitualTablet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;

public class DispenserRitualBehavior implements DispenseItemBehavior {
   public ItemStack m_6115_(BlockSource pSource, ItemStack pStack) {
      BlockPos blockpos = pSource.m_7961_().m_121945_((Direction)pSource.m_6414_().m_61143_(DispenserBlock.f_52659_));
      if (pStack.m_41720_() instanceof RitualTablet tablet
         && pSource.m_7727_().m_7702_(blockpos) instanceof RitualBrazierTile brazier
         && brazier.canTakeAnotherRitual()) {
         brazier.setRitual(tablet.ritual.getRegistryName());
         pStack.m_41774_(1);
      }

      return pStack;
   }
}
