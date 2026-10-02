package net.thirdlife.iterrpg.block;

import java.util.Collections;
import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext.Builder;

public class PackedSnowBricksStairsBlock extends StairBlock {
   public PackedSnowBricksStairsBlock() {
      super(() -> Blocks.f_50016_.m_49966_(), Properties.m_60939_(Material.f_76280_).m_60918_(SoundType.f_56747_).m_60978_(0.3F).m_60988_());
   }

   public float m_7325_() {
      return 0.3F;
   }

   public boolean m_6724_(BlockState state) {
      return false;
   }

   public List<ItemStack> m_7381_(BlockState state, Builder builder) {
      List<ItemStack> dropsOriginal = super.m_7381_(state, builder);
      return !dropsOriginal.isEmpty() ? dropsOriginal : Collections.singletonList(new ItemStack(this, 1));
   }
}
