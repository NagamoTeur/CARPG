package net.thirdlife.iterrpg.block;

import java.util.Collections;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.thirdlife.iterrpg.init.IterRpgModBlocks;

public class ArcaneFlowerBlock extends FlowerBlock {
   public ArcaneFlowerBlock() {
      super(
         MobEffects.f_19619_,
         100,
         Properties.m_60939_(Material.f_76300_).m_60918_(SoundType.f_56740_).m_60966_().m_60953_(s -> 3).m_60910_().m_222979_(OffsetType.NONE)
      );
   }

   public int m_53522_() {
      return 100;
   }

   public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
      return 100;
   }

   public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
      return 60;
   }

   public List<ItemStack> m_7381_(BlockState state, Builder builder) {
      List<ItemStack> dropsOriginal = super.m_7381_(state, builder);
      return !dropsOriginal.isEmpty() ? dropsOriginal : Collections.singletonList(new ItemStack(this));
   }

   public boolean m_6266_(BlockState groundState, BlockGetter worldIn, BlockPos pos) {
      return groundState.m_60713_(Blocks.f_152490_)
         || groundState.m_60713_((Block)IterRpgModBlocks.AMETRINE_BLOCK.get())
         || groundState.m_60713_(Blocks.f_152491_)
         || groundState.m_60713_(Blocks.f_50440_)
         || groundState.m_60713_(Blocks.f_50493_)
         || groundState.m_60713_(Blocks.f_152549_)
         || groundState.m_60713_(Blocks.f_50195_);
   }

   public boolean m_7898_(BlockState blockstate, LevelReader worldIn, BlockPos pos) {
      BlockPos blockpos = pos.m_7495_();
      BlockState groundState = worldIn.m_8055_(blockpos);
      return this.m_6266_(groundState, worldIn, blockpos);
   }
}
