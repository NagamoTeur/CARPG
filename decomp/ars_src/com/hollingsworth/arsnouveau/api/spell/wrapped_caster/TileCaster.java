package com.hollingsworth.arsnouveau.api.spell.wrapped_caster;

import com.hollingsworth.arsnouveau.api.item.inv.FilterableItemHandler;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.util.InvUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class TileCaster implements IWrappedCaster {
   protected SpellContext.CasterType casterType;
   protected BlockEntity tile;

   public TileCaster(BlockEntity tile, SpellContext.CasterType casterType) {
      this.tile = tile;
      this.casterType = casterType;
   }

   @NotNull
   @Override
   public List<FilterableItemHandler> getInventory() {
      return new ArrayList<>(InvUtil.adjacentInventories(this.tile.m_58904_(), this.tile.m_58899_()));
   }

   @Override
   public SpellContext.CasterType getCasterType() {
      return this.casterType;
   }

   public BlockEntity getTile() {
      return this.tile;
   }

   @Override
   public Direction getFacingDirection() {
      return this.tile.m_58900_().m_61138_(BlockStateProperties.f_61372_)
         ? (Direction)this.tile.m_58900_().m_61143_(BlockStateProperties.f_61372_)
         : Direction.NORTH;
   }

   @Override
   public BlockEntity getNearbyBlockEntity(Predicate<BlockEntity> predicate) {
      for (Direction dir : Direction.values()) {
         BlockEntity tile = this.tile.m_58904_().m_7702_(this.tile.m_58899_().m_121945_(dir));
         if (tile != null && predicate.test(tile)) {
            return tile;
         }
      }

      return null;
   }

   @Override
   public Vec3 getPosition() {
      return new Vec3((double)this.tile.m_58899_().m_123341_(), (double)this.tile.m_58899_().m_123342_(), (double)this.tile.m_58899_().m_123343_());
   }
}
