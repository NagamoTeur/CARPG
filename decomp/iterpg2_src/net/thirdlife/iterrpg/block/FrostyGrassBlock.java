package net.thirdlife.iterrpg.block;

import java.util.Collections;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FrostyGrassBlock extends FlowerBlock {
   public FrostyGrassBlock() {
      super(MobEffects.f_19607_, 100, Properties.m_60939_(Material.f_76300_).m_60918_(SoundType.f_56740_).m_60966_().m_60910_());
   }

   public VoxelShape m_5940_(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
      Vec3 offset = state.m_60824_(world, pos);
      return m_49796_(2.0, 0.0, 2.0, 14.0, 12.0, 14.0).m_83216_(offset.f_82479_, offset.f_82480_, offset.f_82481_);
   }

   public int m_53522_() {
      return 100;
   }

   public boolean m_6864_(BlockState state, BlockPlaceContext useContext) {
      return useContext.m_43722_().m_41720_() != this.m_5456_();
   }

   public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
      return 25;
   }

   public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
      return 60;
   }

   public List<ItemStack> m_7381_(BlockState state, Builder builder) {
      List<ItemStack> dropsOriginal = super.m_7381_(state, builder);
      return !dropsOriginal.isEmpty() ? dropsOriginal : Collections.singletonList(new ItemStack(this));
   }

   public boolean m_6266_(BlockState groundState, BlockGetter worldIn, BlockPos pos) {
      return groundState.m_60713_(Blocks.f_50440_)
         || groundState.m_60713_(Blocks.f_50493_)
         || groundState.m_60713_(Blocks.f_50127_)
         || groundState.m_60713_(Blocks.f_50126_)
         || groundState.m_60713_(Blocks.f_50354_)
         || groundState.m_60713_(Blocks.f_50449_)
         || groundState.m_60713_(Blocks.f_152499_)
         || groundState.m_60713_(Blocks.f_50546_)
         || groundState.m_60713_(Blocks.f_152549_);
   }

   public boolean m_7898_(BlockState blockstate, LevelReader worldIn, BlockPos pos) {
      BlockPos blockpos = pos.m_7495_();
      BlockState groundState = worldIn.m_8055_(blockpos);
      return this.m_6266_(groundState, worldIn, blockpos);
   }
}
