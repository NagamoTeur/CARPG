package com.github.L_Ender.cataclysm.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Cataclysm_Skull_Block extends Abstract_Cataclysm_Skull_Block {
   public static final int MAX = 15;
   private static final int ROTATIONS = 16;
   public static final IntegerProperty ROTATION = BlockStateProperties.f_61390_;
   protected static final VoxelShape SHAPE = Block.m_49796_(4.0, 0.0, 4.0, 12.0, 8.0, 12.0);

   public Cataclysm_Skull_Block(Cataclysm_Skull_Block.Type p_56318_, Properties p_56319_) {
      super(p_56318_, p_56319_);
      this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(ROTATION, 0));
   }

   public VoxelShape m_5940_(BlockState p_56331_, BlockGetter p_56332_, BlockPos p_56333_, CollisionContext p_56334_) {
      return SHAPE;
   }

   public VoxelShape m_7952_(BlockState p_56336_, BlockGetter p_56337_, BlockPos p_56338_) {
      return Shapes.m_83040_();
   }

   public BlockState m_5573_(BlockPlaceContext p_56321_) {
      return (BlockState)this.m_49966_().m_61124_(ROTATION, Mth.m_14107_((double)(p_56321_.m_7074_() * 16.0F / 360.0F) + 0.5) & 15);
   }

   public BlockState m_6843_(BlockState p_56326_, Rotation p_56327_) {
      return (BlockState)p_56326_.m_61124_(ROTATION, p_56327_.m_55949_((Integer)p_56326_.m_61143_(ROTATION), 16));
   }

   public BlockState m_6943_(BlockState p_56323_, Mirror p_56324_) {
      return (BlockState)p_56323_.m_61124_(ROTATION, p_56324_.m_54843_((Integer)p_56323_.m_61143_(ROTATION), 16));
   }

   protected void m_7926_(Builder<Block, BlockState> p_56329_) {
      p_56329_.m_61104_(new Property[]{ROTATION});
   }

   public interface Type {
   }

   public static enum Types implements Cataclysm_Skull_Block.Type {
      KOBOLEDIATOR,
      APTRGANGR,
      DRAUGR;
   }
}
