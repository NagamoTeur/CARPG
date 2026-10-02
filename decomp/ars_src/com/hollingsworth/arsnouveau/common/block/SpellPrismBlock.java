package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.api.block.IPrismaticBlock;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.common.advancement.ANCriteriaTriggers;
import com.hollingsworth.arsnouveau.common.entity.EntityProjectileSpell;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAccelerate;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDecelerate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.BlockSourceImpl;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.PositionImpl;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class SpellPrismBlock extends ModBlock implements IPrismaticBlock {
   public static final DirectionProperty FACING = DirectionalBlock.f_52588_;

   public SpellPrismBlock(Properties properties) {
      super(properties);
   }

   public SpellPrismBlock() {
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext context) {
      return (BlockState)this.m_49966_().m_61124_(FACING, context.m_7820_().m_122424_());
   }

   @Deprecated(
      forRemoval = true,
      since = "3.4.0"
   )
   public static void redirectSpell(ServerLevel world, BlockPos pos, EntityProjectileSpell spell) {
      if (world.m_8055_(pos).m_60734_() instanceof IPrismaticBlock block) {
         block.onHit(world, pos, spell);
      }
   }

   public static Position getDispensePosition(BlockSource coords) {
      Direction direction = (Direction)coords.m_6414_().m_61143_(FACING);
      double d0 = coords.m_7096_() + 0.3 * (double)direction.m_122429_();
      double d1 = coords.m_7098_() + 0.3 * (double)direction.m_122430_();
      double d2 = coords.m_7094_() + 0.3 * (double)direction.m_122431_();
      return new PositionImpl(d0, d1, d2);
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{FACING});
   }

   public BlockState m_6843_(BlockState state, Rotation rot) {
      return (BlockState)state.m_61124_(FACING, rot.m_55954_((Direction)state.m_61143_(FACING)));
   }

   public BlockState m_6943_(BlockState state, Mirror mirrorIn) {
      return state.m_60717_(mirrorIn.m_54846_((Direction)state.m_61143_(FACING)));
   }

   @Override
   public void onHit(ServerLevel world, BlockPos pos, EntityProjectileSpell spell) {
      Position iposition = getDispensePosition(new BlockSourceImpl(world, pos));
      Direction direction = (Direction)world.m_8055_(pos).m_61143_(DispenserBlock.f_52659_);
      spell.m_6034_(iposition.m_7096_(), iposition.m_7098_(), iposition.m_7094_());
      spell.prismRedirect++;
      if (spell.prismRedirect >= 3) {
         ANCriteriaTriggers.rewardNearbyPlayers(ANCriteriaTriggers.PRISMATIC, world, pos, 10);
      }

      if (spell.spellResolver == null) {
         spell.m_142687_(RemovalReason.DISCARDED);
      } else {
         float acceleration = (float)spell.spellResolver.spell.getBuffsAtIndex(0, null, AugmentAccelerate.INSTANCE)
            - (float)spell.spellResolver.spell.getBuffsAtIndex(0, null, AugmentDecelerate.INSTANCE) * 0.5F;
         float velocity = Math.max(0.1F, 0.5F + 0.1F * Math.min(2.0F, acceleration));
         spell.m_6686_((double)direction.m_122429_(), (double)direction.m_122430_(), (double)direction.m_122431_(), velocity, 0.0F);
         BlockUtil.updateObservers(world, pos);
      }
   }
}
