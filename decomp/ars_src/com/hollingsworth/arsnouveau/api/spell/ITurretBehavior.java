package com.hollingsworth.arsnouveau.api.spell;

import com.hollingsworth.arsnouveau.common.block.tile.BasicSpellTurretTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.FakePlayer;

public interface ITurretBehavior {
   @Deprecated(
      forRemoval = true
   )
   default void onCast(
      SpellResolver resolver,
      BasicSpellTurretTile tile,
      ServerLevel serverLevel,
      BlockPos pos,
      FakePlayer fakePlayer,
      Position dispensePosition,
      Direction direction
   ) {
      this.onCast(resolver, serverLevel, pos, fakePlayer, dispensePosition, direction);
   }

   default void onCast(SpellResolver resolver, ServerLevel serverLevel, BlockPos pos, Player fakePlayer, Position dispensePosition, Direction direction) {
   }
}
