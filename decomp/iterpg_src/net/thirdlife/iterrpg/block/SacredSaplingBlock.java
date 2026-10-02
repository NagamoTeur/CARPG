package net.thirdlife.iterrpg.block;

import java.util.Collections;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.thirdlife.iterrpg.block.entity.SacredSaplingBlockEntity;
import net.thirdlife.iterrpg.procedures.SacredSaplingGrowProcedure;
import net.thirdlife.iterrpg.procedures.SacredSaplingRandomTickProcedure;

public class SacredSaplingBlock extends FlowerBlock implements EntityBlock, BonemealableBlock {
   public SacredSaplingBlock() {
      super(
         MobEffects.f_19596_,
         100,
         Properties.m_60939_(Material.f_76300_).m_60977_().m_60918_(SoundType.f_56740_).m_60966_().m_60910_().m_222979_(OffsetType.NONE)
      );
   }

   public VoxelShape m_5940_(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
      Vec3 offset = state.m_60824_(world, pos);
      return m_49796_(0.0, 0.0, 0.0, 16.0, 16.0, 16.0).m_83216_(offset.f_82479_, offset.f_82480_, offset.f_82481_);
   }

   public int m_53522_() {
      return 100;
   }

   public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
      return 100;
   }

   public BlockPathTypes getBlockPathType(BlockState state, BlockGetter world, BlockPos pos, Mob entity) {
      return BlockPathTypes.OPEN;
   }

   public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
      return 60;
   }

   public List<ItemStack> m_7381_(BlockState state, Builder builder) {
      List<ItemStack> dropsOriginal = super.m_7381_(state, builder);
      return !dropsOriginal.isEmpty() ? dropsOriginal : Collections.singletonList(new ItemStack(this));
   }

   public void m_213897_(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
      super.m_213897_(blockstate, world, pos, random);
      SacredSaplingRandomTickProcedure.execute(world, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
   }

   public boolean m_7370_(BlockGetter worldIn, BlockPos pos, BlockState blockstate, boolean clientSide) {
      return true;
   }

   public boolean m_214167_(Level world, RandomSource random, BlockPos pos, BlockState blockstate) {
      return true;
   }

   public void m_214148_(ServerLevel world, RandomSource random, BlockPos pos, BlockState blockstate) {
      SacredSaplingGrowProcedure.execute(world, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
   }

   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new SacredSaplingBlockEntity(pos, state);
   }

   public boolean m_8133_(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
      super.m_8133_(state, world, pos, eventID, eventParam);
      BlockEntity blockEntity = world.m_7702_(pos);
      return blockEntity == null ? false : blockEntity.m_7531_(eventID, eventParam);
   }
}
