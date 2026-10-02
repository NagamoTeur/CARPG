package com.github.L_Ender.cataclysm.blocks;

import com.github.L_Ender.cataclysm.blockentities.Mechanical_fusion_Anvil_Block_Entity;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import com.github.L_Ender.cataclysm.inventory.WeaponfusionMenu;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Mechanical_fusion_Anvil extends BaseEntityBlock {
   private static final Component CONTAINER_TITLE = Component.m_237115_("cataclysm.container.weapon_fusion");
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.f_54117_;
   private static final VoxelShape BASE = Block.m_49796_(2.0, 0.0, 2.0, 14.0, 4.0, 14.0);
   private static final VoxelShape X_LEG1 = Block.m_49796_(3.0, 4.0, 4.0, 13.0, 5.0, 12.0);
   private static final VoxelShape X_LEG2 = Block.m_49796_(4.0, 5.0, 6.0, 12.0, 10.0, 10.0);
   private static final VoxelShape X_TOP = Block.m_49796_(0.0, 10.0, 3.0, 16.0, 16.0, 13.0);
   private static final VoxelShape Z_LEG1 = Block.m_49796_(4.0, 4.0, 3.0, 12.0, 5.0, 13.0);
   private static final VoxelShape Z_LEG2 = Block.m_49796_(6.0, 5.0, 4.0, 10.0, 10.0, 12.0);
   private static final VoxelShape Z_TOP = Block.m_49796_(3.0, 10.0, 0.0, 13.0, 16.0, 16.0);
   private static final VoxelShape X_AXIS_AABB = Shapes.m_83124_(BASE, new VoxelShape[]{X_LEG1, X_LEG2, X_TOP});
   private static final VoxelShape Z_AXIS_AABB = Shapes.m_83124_(BASE, new VoxelShape[]{Z_LEG1, Z_LEG2, Z_TOP});

   public Mechanical_fusion_Anvil() {
      super(Properties.m_60939_(Material.f_76281_).m_155949_(MaterialColor.f_76404_).m_60913_(50.0F, 1200.0F).m_60999_().m_60918_(SoundType.f_56749_));
      this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(FACING, Direction.NORTH));
   }

   public BlockState m_5573_(BlockPlaceContext p_48781_) {
      return (BlockState)this.m_49966_().m_61124_(FACING, p_48781_.m_8125_().m_122427_());
   }

   public InteractionResult m_6227_(BlockState p_56428_, Level p_56429_, BlockPos p_56430_, Player p_56431_, InteractionHand p_56432_, BlockHitResult p_56433_) {
      if (p_56429_.f_46443_) {
         return InteractionResult.SUCCESS;
      } else {
         p_56431_.m_5893_(p_56428_.m_60750_(p_56429_, p_56430_));
         p_56431_.m_36220_(Stats.f_12954_);
         return InteractionResult.CONSUME;
      }
   }

   public MenuProvider m_7246_(BlockState state, Level level, BlockPos pos) {
      return new SimpleMenuProvider((i, inv, player) -> new WeaponfusionMenu(i, inv, ContainerLevelAccess.m_39289_(level, pos)), CONTAINER_TITLE);
   }

   public VoxelShape m_5940_(BlockState p_48816_, BlockGetter p_48817_, BlockPos p_48818_, CollisionContext p_48819_) {
      Direction direction = (Direction)p_48816_.m_61143_(FACING);
      return direction.m_122434_() == Axis.X ? X_AXIS_AABB : Z_AXIS_AABB;
   }

   public BlockState m_6843_(BlockState p_48811_, Rotation p_48812_) {
      return (BlockState)p_48811_.m_61124_(FACING, p_48812_.m_55954_((Direction)p_48811_.m_61143_(FACING)));
   }

   protected void m_7926_(Builder<Block, BlockState> p_48814_) {
      p_48814_.m_61104_(new Property[]{FACING});
   }

   public boolean m_7357_(BlockState p_48799_, BlockGetter p_48800_, BlockPos p_48801_, PathComputationType p_48802_) {
      return false;
   }

   @Nullable
   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new Mechanical_fusion_Anvil_Block_Entity(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_152180_, BlockState p_152181_, BlockEntityType<T> p_152182_) {
      return m_152132_(p_152182_, (BlockEntityType)ModTileentites.MECHANICAL_FUSION_ANVIL.get(), Mechanical_fusion_Anvil_Block_Entity::commonTick);
   }
}
