package net.thirdlife.iterrpg.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.PlantType;
import net.thirdlife.iterrpg.procedures.TwiffleBonemealProcedure;

public class TwiffleBlock extends FlowerBlock implements BonemealableBlock {
   public TwiffleBlock() {
      super(MobEffects.f_19598_, 100, Properties.m_60939_(Material.f_76300_).m_60918_(SoundType.f_56711_).m_60966_().m_60910_().m_222979_(OffsetType.NONE));
   }

   public VoxelShape m_5940_(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
      Vec3 offset = state.m_60824_(world, pos);
      return m_49796_(4.0, 0.0, 4.0, 12.0, 12.0, 12.0).m_83216_(offset.f_82479_, offset.f_82480_, offset.f_82481_);
   }

   public int m_53522_() {
      return 100;
   }

   public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
      return 4;
   }

   public BlockPathTypes getBlockPathType(BlockState state, BlockGetter world, BlockPos pos, Mob entity) {
      return BlockPathTypes.OPEN;
   }

   public PlantType getPlantType(BlockGetter world, BlockPos pos) {
      return PlantType.CAVE;
   }

   public InteractionResult m_6227_(BlockState blockstate, Level world, BlockPos pos, Player entity, InteractionHand hand, BlockHitResult hit) {
      super.m_6227_(blockstate, world, pos, entity, hand, hit);
      TwiffleBonemealProcedure.execute(world, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
      return InteractionResult.SUCCESS;
   }

   public boolean m_7370_(BlockGetter worldIn, BlockPos pos, BlockState blockstate, boolean clientSide) {
      return true;
   }

   public boolean m_214167_(Level world, RandomSource random, BlockPos pos, BlockState blockstate) {
      return true;
   }

   public void m_214148_(ServerLevel world, RandomSource random, BlockPos pos, BlockState blockstate) {
      TwiffleBonemealProcedure.execute(world, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
   }
}
