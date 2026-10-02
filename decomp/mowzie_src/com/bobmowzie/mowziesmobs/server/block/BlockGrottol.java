package com.bobmowzie.mowziesmobs.server.block;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.stream.Collector;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockGrottol extends HorizontalDirectionalBlock {
   public static final EnumProperty<BlockGrottol.Variant> VARIANT = EnumProperty.m_61587_("variant", BlockGrottol.Variant.class);
   private static final VoxelShape BOUNDS = Shapes.m_83048_(0.0625, 0.0, 0.0625, 0.9375, 0.9375, 0.9375);

   public BlockGrottol(Properties properties) {
      super(properties);
      this.m_49959_(
         (BlockState)((BlockState)((BlockState)this.m_49965_().m_61090_()).m_61124_(f_54117_, Direction.NORTH)).m_61124_(VARIANT, BlockGrottol.Variant.DIAMOND)
      );
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      super.m_7926_(builder);
      builder.m_61104_(new Property[]{f_54117_});
      builder.m_61104_(new Property[]{VARIANT});
   }

   @Deprecated
   public VoxelShape m_5940_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      return BOUNDS;
   }

   @Deprecated
   public boolean m_7898_(BlockState state, LevelReader world, BlockPos pos) {
      return super.m_7898_(state, world, pos) && hasSupport(world, pos);
   }

   public void m_6861_(BlockState state, Level world, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
      if (hasSupport(world, pos)) {
         world.m_7471_(pos, false);
      }
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext context) {
      return (BlockState)this.m_49966_().m_61124_(f_54117_, context.m_8125_().m_122424_());
   }

   private static boolean hasSupport(BlockGetter world, BlockPos pos) {
      return world.m_8055_(pos.m_7495_()).m_60767_().m_76333_();
   }

   public static enum Variant implements StringRepresentable {
      DIAMOND(0, "diamond"),
      BLACK_PINK(1, "black_pink");

      private static final Int2ObjectMap<BlockGrottol.Variant> LOOKUP = Stream.of(values())
         .collect(Collector.of(Int2ObjectOpenHashMap::new, (map, variant) -> map.put(variant.getIndex(), variant), (left, right) -> {
            throw new IllegalStateException();
         }, map -> {
            map.defaultReturnValue(DIAMOND);
            return Int2ObjectMaps.unmodifiable(map);
         }));
      private final int index;
      private final String name;

      private Variant(int index, String name) {
         this.index = index;
         this.name = name;
      }

      public final int getIndex() {
         return this.index;
      }

      public final String m_7912_() {
         return this.name;
      }

      public static BlockGrottol.Variant valueOf(int index) {
         return (BlockGrottol.Variant)LOOKUP.get(index);
      }
   }
}
