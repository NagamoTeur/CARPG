package com.aizistral.enigmaticlegacy.blocks;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockBigLamp extends LanternBlock {
   protected final VoxelShape sittingLantern = Shapes.m_83124_(Block.m_49796_(1.0, 0.0, 1.0, 15.0, 14.0, 15.0), new VoxelShape[0]);
   protected final VoxelShape hangingLantern = Shapes.m_83124_(Block.m_49796_(1.0, 1.0, 1.0, 15.0, 15.0, 15.0), new VoxelShape[0]);

   public BlockBigLamp() {
      super(Properties.m_60926_(Blocks.f_50681_));
   }

   public RenderShape m_7514_(BlockState p_60550_) {
      return RenderShape.MODEL;
   }

   public VoxelShape m_5940_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      return state.m_61143_(f_153459_) ? this.hangingLantern : this.sittingLantern;
   }

   public List<ItemStack> m_7381_(BlockState state, Builder builder) {
      return List.of(new ItemStack(this));
   }
}
