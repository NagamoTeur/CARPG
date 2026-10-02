package dev.latvian.mods.kubejs.block.custom;

import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.client.ModelGenerator;
import dev.latvian.mods.kubejs.client.VariantBlockStateGenerator;
import dev.latvian.mods.kubejs.generator.AssetJsonGenerator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HorizontalDirectionalBlockBuilder extends BlockBuilder {
   public HorizontalDirectionalBlockBuilder(ResourceLocation i) {
      super(i);
   }

   @Override
   protected void generateBlockStateJson(VariantBlockStateGenerator bs) {
      String modelLocation = this.model.isEmpty() ? this.newID("block/", "").toString() : this.model;
      bs.variant("facing=north", v -> v.model(modelLocation));
      bs.variant("facing=east", v -> v.model(modelLocation).y(90));
      bs.variant("facing=south", v -> v.model(modelLocation).y(180));
      bs.variant("facing=west", v -> v.model(modelLocation).y(270));
   }

   @Override
   protected void generateBlockModelJsons(AssetJsonGenerator gen) {
      gen.blockModel(this.id, mg -> {
         String side = this.getTextureOrDefault("side", this.newID("block/", "").toString());
         mg.texture("side", side);
         mg.texture("front", this.getTextureOrDefault("front", this.newID("block/", "_front").toString()));
         mg.texture("particle", this.getTextureOrDefault("particle", side));
         mg.texture("top", this.getTextureOrDefault("top", side));
         if (this.textures.has("bottom")) {
            mg.parent("block/orientable_with_bottom");
            mg.texture("bottom", this.textures.get("bottom").getAsString());
         } else {
            mg.parent("minecraft:block/orientable");
         }
      });
   }

   @Override
   protected void generateItemModelJson(ModelGenerator m) {
      m.parent(this.model.isEmpty() ? this.newID("block/", "").toString() : this.model);
   }

   public HorizontalDirectionalBlockBuilder textureAll(String tex) {
      super.textureAll(tex);
      this.texture("side", tex);
      return this;
   }

   private String getTextureOrDefault(String name, String defaultTexture) {
      return this.textures.has(name) ? this.textures.get(name).getAsString() : defaultTexture;
   }

   public Block createObject() {
      return (Block)(this.blockEntityInfo != null
         ? new HorizontalDirectionalBlockBuilder.WithEntity(this)
         : new HorizontalDirectionalBlockBuilder.HorizontalDirectionalBlockJS(this));
   }

   public static class HorizontalDirectionalBlockJS extends BasicBlockJS {
      public static final DirectionProperty FACING = BlockStateProperties.f_61374_;
      public final Map<Direction, VoxelShape> shapes = new HashMap<>();

      public HorizontalDirectionalBlockJS(BlockBuilder p) {
         super(p);
         if (this.hasCustomShape()) {
            Plane.HORIZONTAL.forEach(direction -> this.shapes.put(direction, rotateShape(this.shape, direction)));
         }
      }

      private static VoxelShape rotateShape(VoxelShape shape, Direction direction) {
         List<AABB> newShapes = new ArrayList<>();
         switch (direction) {
            case NORTH:
               return shape;
            case SOUTH:
               shape.m_83286_((x1, y1, z1, x2, y2, z2) -> newShapes.add(new AABB(1.0 - x2, y1, 1.0 - z2, 1.0 - x1, y2, 1.0 - z1)));
               break;
            case WEST:
               shape.m_83286_((x1, y1, z1, x2, y2, z2) -> newShapes.add(new AABB(z1, y1, 1.0 - x2, z2, y2, 1.0 - x1)));
               break;
            case EAST:
               shape.m_83286_((x1, y1, z1, x2, y2, z2) -> newShapes.add(new AABB(1.0 - z2, y1, x1, 1.0 - z1, y2, x2)));
               break;
            default:
               throw new IllegalArgumentException("Cannot rotate around direction " + direction.m_122433_());
         }

         return BlockBuilder.createShape(newShapes);
      }

      @Override
      protected void m_7926_(@NotNull net.minecraft.world.level.block.state.StateDefinition.Builder<Block, BlockState> builder) {
         builder.m_61104_(new Property[]{FACING});
         super.m_7926_(builder);
      }

      @Override
      public BlockState m_5573_(@NotNull BlockPlaceContext context) {
         BlockState state = (BlockState)this.m_49966_().m_61124_(FACING, context.m_8125_().m_122424_());
         if (this.blockBuilder.canBeWaterlogged()) {
            state = (BlockState)state.m_61124_(BlockStateProperties.f_61362_, context.m_43725_().m_6425_(context.m_8083_()).m_76152_() == Fluids.f_76193_);
         }

         return state;
      }

      private boolean hasCustomShape() {
         return this.shape != Shapes.m_83144_();
      }

      @Deprecated
      @Override
      public VoxelShape m_5940_(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
         return this.hasCustomShape() ? this.shapes.get(state.m_61143_(FACING)) : this.shape;
      }
   }

   public static class WithEntity extends HorizontalDirectionalBlockBuilder.HorizontalDirectionalBlockJS implements EntityBlock {
      public WithEntity(BlockBuilder p) {
         super(p);
      }

      @Nullable
      public BlockEntity m_142194_(BlockPos blockPos, BlockState blockState) {
         return this.blockBuilder.blockEntityInfo.createBlockEntity(blockPos, blockState);
      }

      @Nullable
      public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
         return this.blockBuilder.blockEntityInfo.getTicker(level);
      }
   }
}
