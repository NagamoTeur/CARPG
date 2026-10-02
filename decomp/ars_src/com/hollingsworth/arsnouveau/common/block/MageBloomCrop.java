package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MageBloomCrop extends CropBlock {
   public static final IntegerProperty AGE_0_4 = IntegerProperty.m_61631_("age", 0, 4);
   private static final VoxelShape[] SHAPES = new VoxelShape[]{
      Block.m_49796_(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
      Block.m_49796_(0.0, 0.0, 0.0, 16.0, 3.0, 16.0),
      Block.m_49796_(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
      Block.m_49796_(0.0, 0.0, 0.0, 16.0, 5.0, 16.0),
      Block.m_49796_(0.0, 0.0, 0.0, 16.0, 5.0, 16.0),
      Block.m_49796_(0.0, 0.0, 0.0, 16.0, 5.0, 16.0),
      Block.m_49796_(0.0, 0.0, 0.0, 16.0, 5.0, 16.0),
      Block.m_49796_(0.0, 0.0, 0.0, 16.0, 5.0, 16.0)
   };

   public MageBloomCrop() {
      super(Properties.m_60939_(Material.f_76300_).m_60977_().m_60978_(0.0F).m_60910_().m_60918_(SoundType.f_56758_).m_60955_());
   }

   public VoxelShape m_5940_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      return SHAPES[state.m_61143_(this.m_7959_())];
   }

   protected ItemLike m_6404_() {
      return BlockRegistry.MAGE_BLOOM_CROP;
   }

   public void m_5871_(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("tooltip.magebloom"));
   }
}
