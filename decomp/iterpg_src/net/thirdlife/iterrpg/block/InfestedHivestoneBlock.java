package net.thirdlife.iterrpg.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.thirdlife.iterrpg.procedures.InfestedHivestoneBreakProcedure;
import net.thirdlife.iterrpg.procedures.InfestedHivestoneExplodeProcedure;
import net.thirdlife.iterrpg.procedures.PeeperPopoutProcedure;

public class InfestedHivestoneBlock extends Block {
   public InfestedHivestoneBlock() {
      super(Properties.m_60939_(Material.f_76278_).m_60918_(SoundType.f_56718_).m_60913_(1.0F, 6.0F).m_60999_().m_60977_());
   }

   public int m_7753_(BlockState state, BlockGetter worldIn, BlockPos pos) {
      return 15;
   }

   public boolean canHarvestBlock(BlockState state, BlockGetter world, BlockPos pos, Player player) {
      return player.m_150109_().m_36056_().m_41720_() instanceof PickaxeItem tieredItem ? tieredItem.m_43314_().m_6604_() >= 0 : false;
   }

   public void m_213897_(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
      super.m_213897_(blockstate, world, pos, random);
      int x = pos.m_123341_();
      int y = pos.m_123342_();
      int z = pos.m_123343_();
      PeeperPopoutProcedure.execute(world, (double)x, (double)y, (double)z);
   }

   public boolean onDestroyedByPlayer(BlockState blockstate, Level world, BlockPos pos, Player entity, boolean willHarvest, FluidState fluid) {
      boolean retval = super.onDestroyedByPlayer(blockstate, world, pos, entity, willHarvest, fluid);
      InfestedHivestoneBreakProcedure.execute(world, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), entity);
      return retval;
   }

   public void m_7592_(Level world, BlockPos pos, Explosion e) {
      super.m_7592_(world, pos, e);
      InfestedHivestoneExplodeProcedure.execute(world, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
   }
}
