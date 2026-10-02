package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.common.block.tile.SourceJarTile;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class SourceJar extends SourceBlock implements SimpleWaterloggedBlock {
   public static final Property<Integer> fill = IntegerProperty.m_61631_("fill", 0, 11);
   static final VoxelShape shape = Stream.of(
         Block.m_49796_(4.0, 13.0, 4.0, 12.0, 14.0, 12.0),
         Block.m_49796_(2.0, 0.0, 2.0, 14.0, 2.0, 14.0),
         Block.m_49796_(3.0, 2.0, 3.0, 13.0, 13.0, 13.0),
         Block.m_49796_(3.0, 14.0, 3.0, 13.0, 16.0, 13.0)
      )
      .reduce((v1, v2) -> Shapes.m_83113_(v1, v2, BooleanOp.f_82695_))
      .get();

   public SourceJar() {
      this(TickableModBlock.defaultProperties().m_60955_(), "source_jar");
   }

   public SourceJar(Properties properties, String registryName) {
      super(properties, registryName);
      this.m_49959_((BlockState)this.m_49966_().m_61124_(BlockStateProperties.f_61362_, false));
   }

   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new SourceJarTile(pos, state);
   }

   public RenderShape m_7514_(BlockState p_149645_1_) {
      return RenderShape.MODEL;
   }

   public VoxelShape m_5940_(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
      return shape;
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{fill});
      builder.m_61104_(new Property[]{BlockStateProperties.f_61362_});
   }

   public FluidState m_5888_(BlockState state) {
      return state.m_61143_(BlockStateProperties.f_61362_) ? Fluids.f_76193_.m_76068_(false) : Fluids.f_76191_.m_76145_();
   }

   @NotNull
   public BlockState m_5573_(BlockPlaceContext context) {
      FluidState fluidState = context.m_43725_().m_6425_(context.m_8083_());
      context.m_43725_().m_186460_(context.m_8083_(), BlockRegistry.SOURCE_JAR, 1);
      return (BlockState)this.m_49966_().m_61124_(BlockStateProperties.f_61362_, fluidState.m_76152_() == Fluids.f_76193_);
   }

   public BlockState m_7417_(BlockState stateIn, Direction side, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
      if ((Boolean)stateIn.m_61143_(BlockStateProperties.f_61362_)) {
         worldIn.m_186469_(currentPos, Fluids.f_76193_, Fluids.f_76193_.m_6718_(worldIn));
      }

      return stateIn;
   }

   public void m_213897_(BlockState p_222945_, ServerLevel level, BlockPos pos, RandomSource p_222948_) {
      super.m_213897_(p_222945_, level, pos, p_222948_);
      if (level.m_7702_(pos) instanceof SourceJarTile jarTile) {
         jarTile.updateBlock();
      }
   }

   public boolean m_7278_(BlockState state) {
      return true;
   }

   public int m_6782_(BlockState blockState, Level worldIn, BlockPos pos) {
      SourceJarTile tile = (SourceJarTile)worldIn.m_7702_(pos);
      if (tile != null && tile.getSource() > 0) {
         int step = (tile.getMaxSource() - 1) / 14;
         return (tile.getSource() - 1) / step + 1;
      } else {
         return 0;
      }
   }

   public void m_5871_(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      super.m_5871_(stack, worldIn, tooltip, flagIn);
      if (stack.m_41783_() != null) {
         int mana = stack.m_41783_().m_128469_("BlockEntityTag").m_128451_("source");
         tooltip.add(Component.m_237113_(mana * 100 / 10000 + "% full"));
      }
   }

   public boolean m_7357_(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
      return false;
   }
}
