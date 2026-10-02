package com.hollingsworth.arsnouveau.api.spell.wrapped_caster;

import com.hollingsworth.arsnouveau.api.item.inv.FilterableItemHandler;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.util.InvUtil;
import com.hollingsworth.arsnouveau.common.block.tile.RuneTile;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class RuneCaster extends TileCaster {
   public RuneCaster(RuneTile tile, SpellContext.CasterType casterType) {
      super(tile, casterType);
   }

   @NotNull
   @Override
   public List<FilterableItemHandler> getInventory() {
      RuneTile tile1 = (RuneTile)this.tile;
      if (tile1.isSensitive) {
         Player player = tile1.m_58904_().m_46003_(tile1.uuid);
         return (List<FilterableItemHandler>)(player != null ? InvUtil.fromPlayer(player) : new ArrayList<>());
      } else {
         return super.getInventory();
      }
   }
}
