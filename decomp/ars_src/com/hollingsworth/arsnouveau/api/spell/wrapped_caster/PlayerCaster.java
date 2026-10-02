package com.hollingsworth.arsnouveau.api.spell.wrapped_caster;

import com.hollingsworth.arsnouveau.api.item.inv.FilterableItemHandler;
import com.hollingsworth.arsnouveau.api.item.inv.InventoryManager;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;
import org.jetbrains.annotations.NotNull;

public class PlayerCaster extends LivingCaster {
   public Player player;

   public PlayerCaster(Player livingEntity) {
      super(livingEntity);
      this.player = livingEntity;
   }

   @Override
   public InventoryManager getInvManager() {
      return new InventoryManager(this.getInventory()).extractSlotMax(9).insertSlotMax(-1);
   }

   @NotNull
   @Override
   public List<FilterableItemHandler> getInventory() {
      List<FilterableItemHandler> base = new ArrayList<>();
      base.add(new FilterableItemHandler(new PlayerMainInvWrapper(this.player.f_36093_), new ArrayList<>()));
      return base;
   }

   @Override
   public SpellContext.CasterType getCasterType() {
      return SpellContext.CasterType.PLAYER;
   }
}
