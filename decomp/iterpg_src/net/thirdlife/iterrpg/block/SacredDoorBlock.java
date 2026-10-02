package net.thirdlife.iterrpg.block;

import java.util.Collections;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext.Builder;

public class SacredDoorBlock extends DoorBlock {
   public SacredDoorBlock() {
      super(Properties.m_60939_(Material.f_76320_).m_60918_(SoundType.f_56736_).m_60978_(2.0F).m_60988_());
   }

   public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
      return 5;
   }

   public List<ItemStack> m_7381_(BlockState state, Builder builder) {
      if (state.m_61143_(BlockStateProperties.f_61401_) != DoubleBlockHalf.LOWER) {
         return Collections.emptyList();
      } else {
         List<ItemStack> dropsOriginal = super.m_7381_(state, builder);
         return !dropsOriginal.isEmpty() ? dropsOriginal : Collections.singletonList(new ItemStack(this, 1));
      }
   }
}
