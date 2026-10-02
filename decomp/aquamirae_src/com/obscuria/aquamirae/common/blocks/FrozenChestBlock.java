package com.obscuria.aquamirae.common.blocks;

import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeParticleTypes;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class FrozenChestBlock extends Block implements SimpleWaterloggedBlock {
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.f_54117_;
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.f_61362_;

   public FrozenChestBlock() {
      super(Properties.m_60939_(Material.f_76320_).m_60918_(SoundType.f_56736_).m_60913_(-1.0F, 3600000.0F).m_60955_().m_60924_((bs, br, bp) -> false));
      this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(FACING, Direction.NORTH)).m_61124_(WATERLOGGED, false));
   }

   public boolean m_7420_(BlockState state, @NotNull BlockGetter reader, @NotNull BlockPos pos) {
      return state.m_60819_().m_76178_();
   }

   public int m_7753_(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos) {
      return 0;
   }

   @NotNull
   public VoxelShape m_5940_(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
      return m_49796_(0.9, 0.0, 0.9, 15.1, 14.1, 15.1);
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{FACING, WATERLOGGED});
   }

   @NotNull
   public BlockState m_6843_(BlockState state, Rotation rot) {
      return (BlockState)state.m_61124_(FACING, rot.m_55954_((Direction)state.m_61143_(FACING)));
   }

   @NotNull
   public BlockState m_6943_(BlockState state, Mirror mirrorIn) {
      return state.m_60717_(mirrorIn.m_54846_((Direction)state.m_61143_(FACING)));
   }

   public BlockState m_5573_(BlockPlaceContext context) {
      boolean flag = context.m_43725_().m_6425_(context.m_8083_()).m_76152_() == Fluids.f_76193_;
      return (BlockState)((BlockState)this.m_49966_().m_61124_(FACING, context.m_8125_().m_122424_())).m_61124_(WATERLOGGED, flag);
   }

   @NotNull
   public FluidState m_5888_(BlockState state) {
      return state.m_61143_(WATERLOGGED) ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(state);
   }

   @NotNull
   public BlockState m_7417_(
      BlockState state,
      @NotNull Direction facing,
      @NotNull BlockState facingState,
      @NotNull LevelAccessor world,
      @NotNull BlockPos currentPos,
      @NotNull BlockPos facingPos
   ) {
      if ((Boolean)state.m_61143_(WATERLOGGED)) {
         world.m_186469_(currentPos, Fluids.f_76193_, Fluids.f_76193_.m_6718_(world));
      }

      return super.m_7417_(state, facing, facingState, world, currentPos, facingPos);
   }

   @NotNull
   public PushReaction m_5537_(@NotNull BlockState state) {
      return PushReaction.BLOCK;
   }

   @NotNull
   public List<ItemStack> m_7381_(@NotNull BlockState state, @NotNull net.minecraft.world.level.storage.loot.LootContext.Builder builder) {
      List<ItemStack> dropsOriginal = super.m_7381_(state, builder);
      return !dropsOriginal.isEmpty() ? dropsOriginal : Collections.singletonList(new ItemStack(this, 1));
   }

   public boolean canHarvestBlock(BlockState state, BlockGetter level, BlockPos pos, Player player) {
      return false;
   }

   public boolean canEntityDestroy(BlockState state, BlockGetter level, BlockPos pos, Entity entity) {
      return false;
   }

   @NotNull
   public InteractionResult m_6227_(
      @NotNull BlockState state,
      @NotNull Level world,
      @NotNull BlockPos pos,
      @NotNull Player player,
      @NotNull InteractionHand hand,
      @NotNull BlockHitResult hit
   ) {
      ItemStack stack = player.m_21120_(hand);
      if (stack.m_41720_() != AquamiraeItems.FROZEN_KEY.get()) {
         return InteractionResult.FAIL;
      } else {
         stack.m_41774_(1);
         BlockState chest = (BlockState)Blocks.f_50087_.m_49966_().m_61124_(FACING, (Direction)state.m_61143_(FACING));
         world.m_7731_(pos, chest, 3);
         RandomizableContainerBlockEntity.m_222766_(world, player.m_217043_(), pos, new ResourceLocation("aquamirae", "chests/frozen_chest"));
         world.m_5594_(player, pos, (SoundEvent)AquamiraeSounds.BLOCK_FROZEN_CHEST_UNLOCK.get(), SoundSource.BLOCKS, 1.0F, 1.0F);

         for (int i = 0; i < 12; i++) {
            double d0 = (double)pos.m_123341_() - 0.1 + 1.2 * player.m_217043_().m_188500_();
            double d1 = (double)pos.m_123342_() - 0.1 + 1.2 * player.m_217043_().m_188500_();
            double d2 = (double)pos.m_123343_() - 0.1 + 1.2 * player.m_217043_().m_188500_();
            world.m_7106_(ParticleTypes.f_123796_, d0, d1, d2, 0.0, 0.01, 0.0);
         }

         for (int i = 0; i < 12; i++) {
            double d0 = (double)pos.m_123341_() - 0.2 + 1.4 * player.m_217043_().m_188500_();
            double d1 = (double)pos.m_123342_() - 0.2 + 1.4 * player.m_217043_().m_188500_();
            double d2 = (double)pos.m_123343_() - 0.2 + 1.4 * player.m_217043_().m_188500_();
            world.m_7106_((ParticleOptions)AquamiraeParticleTypes.SHINE.get(), d0, d1, d2, 0.0, 0.05, 0.0);
         }

         return InteractionResult.SUCCESS;
      }
   }
}
