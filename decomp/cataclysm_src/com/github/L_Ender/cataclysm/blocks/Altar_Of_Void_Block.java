package com.github.L_Ender.cataclysm.blocks;

import com.github.L_Ender.cataclysm.blockentities.AltarOfVoid_Block_Entity;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Altar_Of_Void_Block extends BaseEntityBlock {
   private static final VoxelShape BASE = Block.m_49796_(1.0, 0.0, 1.0, 15.0, 4.0, 15.0);
   private static final VoxelShape MID = Block.m_49796_(2.0, 4.0, 2.0, 14.0, 10.0, 14.0);
   private static final VoxelShape TOP = Block.m_49796_(0.0, 10.0, 0.0, 16.0, 14.0, 16.0);
   private static final VoxelShape AXIS_AABB = Shapes.m_83124_(BASE, new VoxelShape[]{MID, TOP});

   public Altar_Of_Void_Block() {
      super(
         Properties.m_60939_(Material.f_76281_)
            .m_60955_()
            .m_60953_(block -> 7)
            .m_60991_((block, world, pos) -> true)
            .m_60913_(-1.0F, 3600000.0F)
            .m_222994_()
            .m_60918_(SoundType.f_56743_)
      );
   }

   public VoxelShape m_5940_(BlockState p_48816_, BlockGetter p_48817_, BlockPos p_48818_, CollisionContext p_48819_) {
      return AXIS_AABB;
   }

   @Nullable
   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new AltarOfVoid_Block_Entity(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_152180_, BlockState p_152181_, BlockEntityType<T> p_152182_) {
      return m_152132_(p_152182_, (BlockEntityType)ModTileentites.ALTAR_OF_VOID.get(), AltarOfVoid_Block_Entity::commonTick);
   }
}
