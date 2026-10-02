package com.github.L_Ender.cataclysm.blocks;

import com.github.L_Ender.cataclysm.entity.projectile.Poison_Dart_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;

public class Sandstone_Poison_Dart_Trap extends Block {
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.f_54117_;
   public static final BooleanProperty LIT = BlockStateProperties.f_61443_;

   public Sandstone_Poison_Dart_Trap(Properties properties) {
      super(properties);
      this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(FACING, Direction.NORTH)).m_61124_(LIT, false));
   }

   public static Vec3 getDispensePosition(BlockPos coords, Direction dir) {
      double d0 = (double)coords.m_123341_() + 0.5 + 0.7 * (double)dir.m_122429_();
      double d1 = (double)coords.m_123342_() + 0.15 + 0.7 * (double)dir.m_122430_();
      double d2 = (double)coords.m_123343_() + 0.5 + 0.7 * (double)dir.m_122431_();
      return new Vec3(d0, d1, d2);
   }

   public void m_6861_(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
      this.tickGustmaker(state, worldIn, pos, false);
   }

   public void m_213897_(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
      this.tickGustmaker(state, worldIn, pos, true);
   }

   public void tickGustmaker(BlockState state, Level worldIn, BlockPos pos, boolean tickOff) {
      boolean flag = worldIn.m_46753_(pos) || worldIn.m_46753_(pos.m_7495_()) || worldIn.m_46753_(pos.m_7494_());
      boolean flag1 = (Boolean)state.m_61143_(LIT);
      if (flag && !flag1) {
         if (worldIn.m_46749_(pos)) {
            Vec3 dispensePosition = getDispensePosition(pos, (Direction)state.m_61143_(FACING));
            Direction direction = (Direction)state.m_61143_(FACING);
            Poison_Dart_Entity dart = new Poison_Dart_Entity(
               (EntityType)ModEntities.POISON_DART.get(),
               dispensePosition.f_82479_,
               (double)((float)dispensePosition.f_82480_ + 0.25F),
               (double)((float)dispensePosition.f_82481_),
               worldIn
            );
            dart.f_36705_ = Pickup.DISALLOWED;
            dart.m_6686_((double)direction.m_122429_(), (double)((float)direction.m_122430_() + 0.1F), (double)direction.m_122431_(), 2.5F, 1.0F);
            worldIn.m_7967_(dart);
         }

         worldIn.m_7731_(pos, (BlockState)state.m_61124_(LIT, true), 2);
         worldIn.m_186460_(pos, this, 20);
      } else if (flag1 && tickOff) {
         worldIn.m_186460_(pos, this, 20);
         worldIn.m_7731_(pos, (BlockState)state.m_61124_(LIT, false), 2);
      }
   }

   public BlockState m_5573_(BlockPlaceContext p_48689_) {
      return (BlockState)this.m_49966_().m_61124_(FACING, p_48689_.m_8125_().m_122424_());
   }

   public BlockState m_6843_(BlockState state, Rotation rot) {
      return (BlockState)state.m_61124_(FACING, rot.m_55954_((Direction)state.m_61143_(FACING)));
   }

   public BlockState m_6943_(BlockState state, Mirror mirrorIn) {
      return state.m_60717_(mirrorIn.m_54846_((Direction)state.m_61143_(FACING)));
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{FACING, LIT});
   }
}
