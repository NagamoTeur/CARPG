package com.hollingsworth.arsnouveau.common.items.summon_charms;

import com.hollingsworth.arsnouveau.api.item.AbstractSummonCharm;
import com.hollingsworth.arsnouveau.common.block.tile.SummoningTile;
import com.hollingsworth.arsnouveau.common.entity.AmethystGolem;
import com.hollingsworth.arsnouveau.common.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class AmethystGolemCharm extends AbstractSummonCharm {
   @Override
   public InteractionResult useOnBlock(UseOnContext context, Level world, BlockPos pos) {
      AmethystGolem amy = new AmethystGolem((EntityType<? extends PathfinderMob>)ModEntities.AMETHYST_GOLEM.get(), world);
      amy.m_6034_((double)pos.m_123341_(), (double)pos.m_7494_().m_123342_(), (double)pos.m_123343_());
      world.m_7967_(amy);
      return InteractionResult.SUCCESS;
   }

   @Override
   public InteractionResult useOnSummonTile(UseOnContext context, Level world, SummoningTile tile, BlockPos pos) {
      return this.useOnBlock(context, world, pos);
   }
}
