package net.thirdlife.iterrpg.block;

import java.util.Collections;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraftforge.common.PlantType;
import net.thirdlife.iterrpg.init.IterRpgModBlocks;
import net.thirdlife.iterrpg.procedures.CattailBonemealProcedure;

public class CattailBlock extends DoublePlantBlock implements BonemealableBlock {
   public CattailBlock() {
      super(Properties.m_60939_(Material.f_76300_).m_60918_(SoundType.f_56741_).m_60966_().m_60910_());
   }

   public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
      return 100;
   }

   public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
      return 60;
   }

   public List<ItemStack> m_7381_(BlockState state, Builder builder) {
      if (state.m_61143_(f_52858_) != DoubleBlockHalf.LOWER) {
         return Collections.emptyList();
      } else {
         List<ItemStack> dropsOriginal = super.m_7381_(state, builder);
         return !dropsOriginal.isEmpty() ? dropsOriginal : Collections.singletonList(new ItemStack(this));
      }
   }

   public boolean m_6266_(BlockState groundState, BlockGetter worldIn, BlockPos pos) {
      return groundState.m_60713_((Block)IterRpgModBlocks.WITCHMUD.get())
         || groundState.m_60713_(Blocks.f_50493_)
         || groundState.m_60713_(Blocks.f_50440_)
         || groundState.m_60713_(Blocks.f_50195_)
         || groundState.m_60713_(Blocks.f_152549_)
         || groundState.m_60713_(Blocks.f_50546_)
         || groundState.m_60713_(Blocks.f_50599_);
   }

   public boolean m_7898_(BlockState blockstate, LevelReader worldIn, BlockPos pos) {
      BlockPos blockpos = pos.m_7495_();
      BlockState groundState = worldIn.m_8055_(blockpos);
      return blockstate.m_61143_(f_52858_) != DoubleBlockHalf.UPPER
         ? this.m_6266_(groundState, worldIn, blockpos)
         : groundState.m_60713_(this) && groundState.m_61143_(f_52858_) == DoubleBlockHalf.LOWER;
   }

   public PlantType getPlantType(BlockGetter world, BlockPos pos) {
      return PlantType.PLAINS;
   }

   public boolean m_7370_(BlockGetter worldIn, BlockPos pos, BlockState blockstate, boolean clientSide) {
      return true;
   }

   public boolean m_214167_(Level world, RandomSource random, BlockPos pos, BlockState blockstate) {
      return true;
   }

   public void m_214148_(ServerLevel world, RandomSource random, BlockPos pos, BlockState blockstate) {
      CattailBonemealProcedure.execute(world, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
   }
}
