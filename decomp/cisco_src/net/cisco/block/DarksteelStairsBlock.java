package net.cisco.block;

import java.util.Collections;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext.Builder;

public class DarksteelStairsBlock extends StairBlock {
   public DarksteelStairsBlock() {
      super(() -> Blocks.f_50016_.m_49966_(), Properties.m_60939_(Material.f_76279_).m_60918_(SoundType.f_56725_).m_60913_(6.0F, 10.0F).m_60988_());
   }

   public float m_7325_() {
      return 10.0F;
   }

   public boolean m_6724_(BlockState state) {
      return false;
   }

   public int m_7753_(BlockState state, BlockGetter worldIn, BlockPos pos) {
      return 0;
   }

   public List<ItemStack> m_7381_(BlockState state, Builder builder) {
      List<ItemStack> dropsOriginal = super.m_7381_(state, builder);
      return !dropsOriginal.isEmpty() ? dropsOriginal : Collections.singletonList(new ItemStack(this, 1));
   }
}
