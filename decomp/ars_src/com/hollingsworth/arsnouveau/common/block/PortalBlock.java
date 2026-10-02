package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.api.util.FlatPortalAreaHelper;
import com.hollingsworth.arsnouveau.common.block.tile.PortalTile;
import com.hollingsworth.arsnouveau.common.datagen.BlockTagProvider;
import com.hollingsworth.arsnouveau.common.items.DominionWand;
import com.hollingsworth.arsnouveau.common.items.WarpScroll;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.config.ServerConfig;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PortalBlock extends TickableModBlock {
   protected static final VoxelShape SHAPE = Block.m_49796_(0.0, 0.0, 0.0, 14.0, 12.0, 14.0);
   public static final EnumProperty<Axis> AXIS = BlockStateProperties.f_61365_;
   public static final BooleanProperty ALTERNATE = BooleanProperty.m_61465_("alternate");

   public VoxelShape m_5940_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   public PortalBlock() {
      super(Properties.m_60939_(Material.f_76298_).m_60910_().m_60913_(-1.0F, 3600000.0F).m_222994_());
      this.m_49959_((BlockState)((BlockState)this.m_49966_().m_61124_(AXIS, Axis.X)).m_61124_(ALTERNATE, false));
   }

   @OnlyIn(Dist.CLIENT)
   public void m_214162_(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
      for (int i = 0; i < 4; i++) {
         double d0 = (double)pos.m_123341_() + (double)rand.m_188501_();
         double d1 = (double)pos.m_123342_() + (double)rand.m_188501_();
         double d2 = (double)pos.m_123343_() + (double)rand.m_188501_();
         double d3 = ((double)rand.m_188501_() - 0.5) * 0.5;
         double d4 = ((double)rand.m_188501_() - 0.5) * 0.5;
         double d5 = ((double)rand.m_188501_() - 0.5) * 0.5;
         int j = rand.m_188503_(2) * 2 - 1;
         if (worldIn.m_8055_(pos.m_122024_()).m_60734_() != this && worldIn.m_8055_(pos.m_122029_()).m_60734_() != this) {
            d0 = (double)pos.m_123341_() + 0.5 + 0.25 * (double)j;
            d3 = (double)(rand.m_188501_() * 2.0F * (float)j);
         } else {
            d2 = (double)pos.m_123343_() + 0.5 + 0.25 * (double)j;
            d5 = (double)(rand.m_188501_() * 2.0F * (float)j);
         }

         worldIn.m_7106_(ParticleTypes.f_123760_, d0, d1, d2, d3, d4, d5);
      }
   }

   public void setType(Level pLevel, BlockPos pPos, boolean alternate) {
      if ((Boolean)pLevel.m_8055_(pPos).m_61143_(ALTERNATE) != alternate) {
         pLevel.m_46597_(pPos, (BlockState)pLevel.m_8055_(pPos).m_61124_(ALTERNATE, alternate));

         for (BlockPos pos : BlockPos.m_121940_(pPos.m_7918_(-1, -1, -1), pPos.m_7918_(1, 1, 1))) {
            Block var7 = pLevel.m_8055_(pos).m_60734_();
            if (var7 instanceof PortalBlock) {
               PortalBlock portalBlock = (PortalBlock)var7;
               if ((Boolean)pLevel.m_8055_(pos).m_61143_(ALTERNATE) != alternate) {
                  this.setType(pLevel, pos, alternate);
               }
            }
         }
      }
   }

   public InteractionResult m_6227_(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
      if (pLevel.f_46443_ || pHand != InteractionHand.MAIN_HAND) {
         return InteractionResult.SUCCESS;
      } else if (pPlayer.m_21120_(pHand).m_41720_() instanceof DominionWand && pLevel.m_7702_(pPos) instanceof PortalTile) {
         boolean nextVal = !(Boolean)pLevel.m_8055_(pPos).m_61143_(ALTERNATE);
         this.setType(pLevel, pPos, nextVal);
         return InteractionResult.CONSUME;
      } else {
         return super.m_6227_(pState, pLevel, pPos, pPlayer, pHand, pHit);
      }
   }

   public void m_5581_(Level worldIn, BlockState state, BlockHitResult hit, Projectile projectile) {
      if (worldIn.m_7702_(hit.m_82425_()) instanceof PortalTile tile) {
         tile.warp(projectile);
      }
   }

   public BlockEntity m_142194_(BlockPos p_153215_, BlockState p_153216_) {
      return new PortalTile(p_153215_, p_153216_);
   }

   public void m_7892_(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
      if (worldIn.m_7702_(pos) instanceof PortalTile tile) {
         if (entityIn instanceof Player player) {
            tile.entityQueue.add(player);
         } else {
            tile.warp(entityIn);
            entityIn.f_19789_ = 0.0F;
         }
      }
   }

   public boolean trySpawnPortal(Level worldIn, BlockPos stackPos, WarpScroll.WarpScrollData data, String displayName) {
      return !ServerConfig.ENABLE_WARP_PORTALS.get()
         ? false
         : this.trySpawnVerticalPortal(worldIn, stackPos, data, displayName) || this.trySpawnHorizontalPortal(worldIn, stackPos, data, displayName);
   }

   public boolean trySpawnVerticalPortal(LevelAccessor worldIn, BlockPos stackPos, WarpScroll.WarpScrollData data, String displayName) {
      PortalBlock.Size portalblock$size = this.isPortal(worldIn, stackPos);
      if (portalblock$size != null) {
         portalblock$size.placePortalBlocks(data, displayName);
         return true;
      } else {
         return false;
      }
   }

   public boolean trySpawnHorizontalPortal(Level worldIn, BlockPos stackPos, WarpScroll.WarpScrollData data, String displayName) {
      FlatPortalAreaHelper helper = new FlatPortalAreaHelper().init(worldIn, stackPos, null, bs -> bs.m_204336_(BlockTagProvider.DECORATIVE_AN));
      if (helper.isValidFrame()) {
         BlockPos.m_121940_(helper.lowerCorner, helper.lowerCorner.m_5487_(Axis.X, helper.xSize - 1).m_5487_(Axis.Z, helper.zSize - 1)).forEach(blockPos -> {
            worldIn.m_7731_(blockPos, (BlockState)BlockRegistry.PORTAL_BLOCK.m_49966_().m_61124_(AXIS, Axis.Y), 18);
            if (worldIn.m_7702_(blockPos) instanceof PortalTile tile) {
               tile.warpPos = data.getPos();
               tile.dimID = data.getDimension();
               tile.rotationVec = data.getRotation();
               tile.displayName = displayName;
               tile.isHorizontal = true;
               tile.updateBlock();
            }
         });
         return true;
      } else {
         return false;
      }
   }

   public BlockState m_6843_(BlockState state, Rotation rot) {
      return switch (rot) {
         case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> {
            switch ((Axis)state.m_61143_(AXIS)) {
               case Z:
                  yield (BlockState)state.m_61124_(AXIS, Axis.X);
               case X:
                  yield (BlockState)state.m_61124_(AXIS, Axis.Z);
               default:
                  yield state;
            }
         }
         default -> state;
      };
   }

   public BlockState m_7417_(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
      Axis direction$axis = facing.m_122434_();
      Axis direction$axis1 = (Axis)stateIn.m_61143_(AXIS);
      boolean flag = direction$axis1 != direction$axis && direction$axis.m_122479_();
      if (worldIn.m_7702_(currentPos) instanceof PortalTile portal && portal.isHorizontal) {
         FlatPortalAreaHelper frameTester = new FlatPortalAreaHelper();
         frameTester.init((Level)worldIn, currentPos, null, bs -> bs.m_204336_(BlockTagProvider.DECORATIVE_AN));
         if (!frameTester.isValidFrame()) {
            return Blocks.f_50016_.m_49966_();
         }

         return super.m_7417_(stateIn, facing, facingState, worldIn, currentPos, facingPos);
      }

      return !flag && facingState.m_60734_() != this && !new PortalBlock.Size(worldIn, currentPos, direction$axis1).isComplete()
         ? Blocks.f_50016_.m_49966_()
         : super.m_7417_(stateIn, facing, facingState, worldIn, currentPos, facingPos);
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{AXIS}).m_61104_(new Property[]{ALTERNATE});
   }

   @Nullable
   public PortalBlock.Size isPortal(LevelAccessor worldIn, BlockPos pos) {
      PortalBlock.Size portalblock$size = new PortalBlock.Size(worldIn, pos, Axis.X);
      if (portalblock$size.isValid() && portalblock$size.portalBlockCount == 0) {
         return portalblock$size;
      } else {
         PortalBlock.Size portalblock$size1 = new PortalBlock.Size(worldIn, pos, Axis.Z);
         return portalblock$size1.isValid() && portalblock$size1.portalBlockCount == 0 ? portalblock$size1 : null;
      }
   }

   public boolean m_6864_(BlockState state, BlockPlaceContext useContext) {
      return false;
   }

   public static class Size {
      private final LevelAccessor world;
      private final Axis axis;
      private final Direction rightDir;
      private final Direction leftDir;
      private int portalBlockCount;
      @Nullable
      private BlockPos bottomLeft;
      private int height;
      private int width;
      private BlockPos warpPos;
      private int dimId;

      public Size(LevelAccessor worldIn, BlockPos pos, Axis axisIn) {
         this.world = worldIn;
         this.axis = axisIn;
         if (axisIn == Axis.X) {
            this.leftDir = Direction.EAST;
            this.rightDir = Direction.WEST;
         } else {
            this.leftDir = Direction.NORTH;
            this.rightDir = Direction.SOUTH;
         }

         BlockPos blockpos = pos;

         while (pos.m_123342_() > blockpos.m_123342_() - 21 && pos.m_123342_() > 0 && this.canReplace(worldIn.m_8055_(pos.m_7495_()))) {
            pos = pos.m_7495_();
         }

         int i = this.getDistanceUntilEdge(pos, this.leftDir) - 1;
         if (i >= 0) {
            this.bottomLeft = pos.m_5484_(this.leftDir, i);
            this.width = this.getDistanceUntilEdge(this.bottomLeft, this.rightDir);
            if (this.width < 1 || this.width > 21) {
               this.bottomLeft = null;
               this.width = 0;
            }
         }

         if (this.bottomLeft != null) {
            this.height = this.calculatePortalHeight();
         }
      }

      protected int getDistanceUntilEdge(BlockPos pos, Direction directionIn) {
         int i;
         for (i = 0; i < 22; i++) {
            BlockPos blockpos = pos.m_5484_(directionIn, i);
            if (!this.canReplace(this.world.m_8055_(blockpos)) || !this.isPortalFrame(this.world, blockpos.m_7495_())) {
               break;
            }
         }

         BlockPos framePos = pos.m_5484_(directionIn, i);
         return this.isPortalFrame(this.world, framePos) ? i : 0;
      }

      public boolean isPortalFrame(LevelAccessor world, BlockPos pos) {
         return world.m_8055_(pos).m_204336_(BlockTagProvider.DECORATIVE_AN);
      }

      public int getHeight() {
         return this.height;
      }

      public int getWidth() {
         return this.width;
      }

      protected int calculatePortalHeight() {
         label57:
         for (this.height = 0; this.height < 21; this.height++) {
            for (int i = 0; i < this.width; i++) {
               BlockPos blockpos = this.bottomLeft.m_5484_(this.rightDir, i).m_6630_(this.height);
               BlockState blockstate = this.world.m_8055_(blockpos);
               if (!this.canReplace(blockstate)) {
                  break label57;
               }

               Block block = blockstate.m_60734_();
               if (block == BlockRegistry.PORTAL_BLOCK) {
                  this.portalBlockCount++;
               }

               if (i == 0) {
                  BlockPos framePos = blockpos.m_121945_(this.leftDir);
                  if (!this.isPortalFrame(this.world, framePos)) {
                     break label57;
                  }
               } else if (i == this.width - 1) {
                  BlockPos framePos = blockpos.m_121945_(this.rightDir);
                  if (!this.isPortalFrame(this.world, framePos)) {
                     break label57;
                  }
               }
            }
         }

         for (int j = 0; j < this.width; j++) {
            BlockPos framePos = this.bottomLeft.m_5484_(this.rightDir, j).m_6630_(this.height);
            if (!this.isPortalFrame(this.world, framePos)) {
               this.height = 0;
               break;
            }
         }

         if (this.height <= 21 && this.height >= 1) {
            return this.height;
         } else {
            this.bottomLeft = null;
            this.width = 0;
            this.height = 0;
            return 0;
         }
      }

      protected boolean canReplace(BlockState pos) {
         Block block = pos.m_60734_();
         return pos.m_60795_() || block == Blocks.f_50083_ || block == BlockRegistry.PORTAL_BLOCK;
      }

      public boolean isValid() {
         return this.bottomLeft != null && this.width >= 1 && this.width <= 21 && this.height >= 1 && this.height <= 21;
      }

      public void placePortalBlocks(WarpScroll.WarpScrollData data, String displayName) {
         for (int i = 0; i < this.width; i++) {
            BlockPos blockpos = this.bottomLeft.m_5484_(this.rightDir, i);

            for (int j = 0; j < this.height; j++) {
               this.world.m_7731_(blockpos.m_6630_(j), (BlockState)BlockRegistry.PORTAL_BLOCK.m_49966_().m_61124_(PortalBlock.AXIS, this.axis), 18);
               if (this.world.m_7702_(blockpos.m_6630_(j)) instanceof PortalTile tile) {
                  tile.warpPos = data.getPos();
                  tile.dimID = data.getDimension();
                  tile.rotationVec = data.getRotation();
                  tile.displayName = displayName;
                  tile.updateBlock();
               }
            }
         }
      }

      private boolean rightSize() {
         return this.portalBlockCount >= this.width * this.height;
      }

      public boolean isComplete() {
         return this.isValid() && this.rightSize();
      }
   }
}
