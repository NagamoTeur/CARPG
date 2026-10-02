package com.github.L_Ender.cataclysm.blocks;

import com.github.L_Ender.cataclysm.blockentities.AltarOfAbyss_Block_Entity;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Altar_Of_Abyss_Block extends BaseEntityBlock implements SimpleWaterloggedBlock {
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.f_61362_;
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.f_54117_;
   private static final VoxelShape BASE = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 3.0, 16.0);
   private static final VoxelShape MID = Block.m_49796_(2.0, 3.0, 2.0, 14.0, 8.0, 14.0);
   private static final VoxelShape TOP = Block.m_49796_(0.0, 8.0, 0.0, 16.0, 11.0, 16.0);
   private static final VoxelShape AXIS_AABB = Shapes.m_83124_(BASE, new VoxelShape[]{MID, TOP});

   public Altar_Of_Abyss_Block() {
      super(
         Properties.m_60939_(Material.f_76278_)
            .m_60955_()
            .m_60953_(block -> 7)
            .m_60991_((block, world, pos) -> true)
            .m_60913_(-1.0F, 3600000.0F)
            .m_222994_()
            .m_60918_(SoundType.f_56742_)
      );
      this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(FACING, Direction.NORTH)).m_61124_(WATERLOGGED, Boolean.FALSE));
   }

   public BlockState m_5573_(BlockPlaceContext p_48781_) {
      FluidState fluidstate = p_48781_.m_43725_().m_6425_(p_48781_.m_8083_());
      return (BlockState)((BlockState)this.m_49966_().m_61124_(FACING, p_48781_.m_8125_().m_122427_()))
         .m_61124_(WATERLOGGED, fluidstate.m_76152_() == Fluids.f_76193_);
   }

   public FluidState m_5888_(BlockState p_51581_) {
      return p_51581_.m_61143_(WATERLOGGED) ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(p_51581_);
   }

   public InteractionResult m_6227_(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
      ItemStack heldItem = player.m_21120_(handIn);
      if (worldIn.m_7702_(pos) instanceof AltarOfAbyss_Block_Entity && !player.m_6144_() && heldItem.m_41720_() != this.m_5456_()) {
         AltarOfAbyss_Block_Entity aof = (AltarOfAbyss_Block_Entity)worldIn.m_7702_(pos);
         ItemStack copy = heldItem.m_41777_();
         copy.m_41764_(1);
         if (aof.m_8020_(0).m_41619_()) {
            aof.m_6836_(0, copy);
            if (!player.m_7500_()) {
               heldItem.m_41774_(1);
            }
         } else {
            m_49840_(worldIn, pos, aof.m_8020_(0).m_41777_());
            aof.m_6836_(0, ItemStack.f_41583_);
         }

         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }

   public BlockState m_6843_(BlockState p_48811_, Rotation p_48812_) {
      return (BlockState)p_48811_.m_61124_(FACING, p_48812_.m_55954_((Direction)p_48811_.m_61143_(FACING)));
   }

   protected void m_7926_(Builder<Block, BlockState> p_48814_) {
      p_48814_.m_61104_(new Property[]{FACING, WATERLOGGED});
   }

   public VoxelShape m_5940_(BlockState p_48816_, BlockGetter p_48817_, BlockPos p_48818_, CollisionContext p_48819_) {
      return AXIS_AABB;
   }

   public BlockState m_7417_(BlockState p_51555_, Direction p_51556_, BlockState p_51557_, LevelAccessor p_51558_, BlockPos p_51559_, BlockPos p_51560_) {
      if ((Boolean)p_51555_.m_61143_(WATERLOGGED)) {
         p_51558_.m_186469_(p_51559_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_51558_));
      }

      return super.m_7417_(p_51555_, p_51556_, p_51557_, p_51558_, p_51559_, p_51560_);
   }

   @Nullable
   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new AltarOfAbyss_Block_Entity(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_152180_, BlockState p_152181_, BlockEntityType<T> p_152182_) {
      return m_152132_(p_152182_, (BlockEntityType)ModTileentites.ALTAR_OF_ABYSS.get(), AltarOfAbyss_Block_Entity::commonTick);
   }
}
