package net.thirdlife.iterrpg.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;

public class NetherrackGeoditeBlock extends Block {
   public NetherrackGeoditeBlock() {
      super(Properties.m_60939_(Material.f_76278_).m_60918_(SoundType.f_56720_).m_60913_(4.0F, 8.0F).m_60999_());
   }

   public int m_7753_(BlockState state, BlockGetter worldIn, BlockPos pos) {
      return 15;
   }

   public boolean canHarvestBlock(BlockState state, BlockGetter world, BlockPos pos, Player player) {
      return player.m_150109_().m_36056_().m_41720_() instanceof PickaxeItem tieredItem ? tieredItem.m_43314_().m_6604_() >= 0 : false;
   }
}
