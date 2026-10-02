package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.api.mob_jar.JarBehaviorRegistry;
import com.hollingsworth.arsnouveau.common.block.tile.MobJarTile;
import com.hollingsworth.arsnouveau.common.datagen.ItemTagProvider;
import com.hollingsworth.arsnouveau.common.items.MobJarItem;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PlayerRideable;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.ContainerEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MobJar extends TickableModBlock implements EntityBlock, SimpleWaterloggedBlock {
   public static final DirectionProperty FACING = DirectionalBlock.f_52588_;
   public static final Property<Integer> LIGHT_LEVEL = IntegerProperty.m_61631_("level", 0, 15);
   public static final Property<Boolean> POWERED = BlockStateProperties.f_61448_;
   private static Properties props = defaultProperties().m_60955_().m_60953_(state -> (Integer)state.m_61143_(LIGHT_LEVEL));
   public static final VoxelShape shape = Stream.of(
         Block.m_49796_(3.0, 14.0, 3.0, 13.0, 16.0, 13.0), Block.m_49796_(1.0, 0.0, 1.0, 15.0, 13.0, 15.0), Block.m_49796_(4.0, 13.0, 4.0, 12.0, 14.0, 12.0)
      )
      .reduce((v1, v2) -> Shapes.m_83113_(v1, v2, BooleanOp.f_82695_))
      .get();

   public MobJar() {
      super(props);
      this.m_49959_(
         (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(BlockStateProperties.f_61362_, false))
                  .m_61124_(FACING, Direction.NORTH))
               .m_61124_(LIGHT_LEVEL, 0))
            .m_61124_(POWERED, false)
      );
   }

   public InteractionResult m_6227_(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
      MobJarTile tile = (MobJarTile)pLevel.m_7702_(pPos);
      if (tile == null) {
         return InteractionResult.PASS;
      } else {
         if (!pLevel.f_46443_) {
            ItemStack held = pPlayer.m_21120_(pHand);
            if (held.m_204117_(ItemTagProvider.JAR_ITEM_BLACKLIST)) {
               return InteractionResult.PASS;
            }
         }

         if (tile.getEntity() == null && !pLevel.f_46443_) {
            ItemStack stack = pPlayer.m_21120_(pHand);
            if (stack.m_41720_() instanceof SpawnEggItem spawnEggItem) {
               EntityType<?> type = spawnEggItem.m_43228_(null);
               Entity entity = type.m_20615_(pLevel);
               if (entity != null) {
                  tile.setEntityData(entity);
                  stack.m_41774_(1);
                  return InteractionResult.CONSUME;
               }
            } else if (!stack.m_41619_() && !(stack.m_41720_() instanceof MobJarItem)) {
               ItemEntity entity = new ItemEntity(EntityType.f_20461_, pLevel);
               entity.m_32045_(stack.m_41777_());
               tile.setEntityData(entity);
               stack.m_41764_(0);
               return InteractionResult.CONSUME;
            }
         }

         if (tile.getEntity() != null
            && !(tile.getEntity() instanceof PlayerRideable)
            && !JarBehaviorRegistry.containsEntity(tile.getEntity())
            && !(tile.getEntity() instanceof ContainerEntity)) {
            Entity tileEntity = tile.getEntity();
            pPlayer.m_36157_(tileEntity, pHand);
            if (!tileEntity.m_6084_() || tileEntity.m_213877_()) {
               tile.removeEntity();
            }
         }

         tile.dispatchBehavior(behavior -> behavior.use(pState, pLevel, pPos, pPlayer, pHand, pHit, tile));
         tile.updateBlock();
         return InteractionResult.SUCCESS;
      }
   }

   public FluidState m_5888_(BlockState state) {
      return state.m_61143_(BlockStateProperties.f_61362_) ? Fluids.f_76193_.m_76068_(false) : Fluids.f_76191_.m_76145_();
   }

   @NotNull
   public BlockState m_5573_(BlockPlaceContext context) {
      FluidState fluidState = context.m_43725_().m_6425_(context.m_8083_());
      context.m_43725_().m_186460_(context.m_8083_(), this, 1);
      return (BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_(FACING, context.m_7820_().m_122424_()))
            .m_61124_(BlockStateProperties.f_61362_, fluidState.m_76152_() == Fluids.f_76193_))
         .m_61124_(POWERED, context.m_43725_().m_46753_(context.m_8083_()));
   }

   public BlockState m_7417_(BlockState stateIn, Direction side, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
      if ((Boolean)stateIn.m_61143_(BlockStateProperties.f_61362_)) {
         worldIn.m_186469_(currentPos, Fluids.f_76193_, Fluids.f_76193_.m_6718_(worldIn));
      }

      return stateIn;
   }

   public void m_213897_(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
      if (!pLevel.f_46443_) {
         if (pLevel.m_7702_(pPos) instanceof MobJarTile tile) {
            int light = tile.calculateLight();
            if ((Integer)pState.m_61143_(LIGHT_LEVEL) != light) {
               pLevel.m_7731_(pPos, (BlockState)pState.m_61124_(LIGHT_LEVEL, light), 2);
            }
         }
      }
   }

   public void m_6861_(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
      if (!pLevel.f_46443_) {
         boolean flag = (Boolean)pState.m_61143_(POWERED);
         if (flag != pLevel.m_46753_(pPos)) {
            if (!flag) {
               MobJarTile tile = (MobJarTile)pLevel.m_7702_(pPos);
               tile.dispatchBehavior(behavior -> behavior.onRedstonePower(tile));
            }

            pLevel.m_7731_(pPos, (BlockState)pState.m_61122_(POWERED), 2);
         }
      }
   }

   public boolean m_7278_(BlockState state) {
      return true;
   }

   public int m_6782_(BlockState blockState, Level worldIn, BlockPos pos) {
      MobJarTile tile = (MobJarTile)worldIn.m_7702_(pos);
      AtomicInteger power = new AtomicInteger();
      tile.dispatchBehavior(behavior -> power.set(Math.max(power.get(), behavior.getAnalogPower(tile))));
      return Math.min(power.get(), 15);
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{FACING, BlockStateProperties.f_61362_, LIGHT_LEVEL, POWERED});
   }

   public BlockState m_6843_(BlockState state, Rotation rot) {
      return (BlockState)state.m_61124_(FACING, rot.m_55954_((Direction)state.m_61143_(FACING)));
   }

   public BlockState m_6943_(BlockState state, Mirror mirrorIn) {
      return state.m_60717_(mirrorIn.m_54846_((Direction)state.m_61143_(FACING)));
   }

   public VoxelShape m_5940_(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
      return shape;
   }

   @Nullable
   public BlockEntity m_142194_(BlockPos pPos, BlockState pState) {
      return new MobJarTile(pPos, pState);
   }

   public int m_6378_(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
      if (pBlockAccess.m_7702_(pPos) instanceof MobJarTile jarTile) {
         AtomicInteger power = new AtomicInteger();
         jarTile.dispatchBehavior(behavior -> power.set(Math.max(power.get(), behavior.getSignalPower(jarTile))));
         return Math.min(power.get(), 15);
      } else {
         return 0;
      }
   }

   public boolean m_7357_(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
      return false;
   }
}
