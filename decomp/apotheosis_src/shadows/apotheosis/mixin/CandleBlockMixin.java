package shadows.apotheosis.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.spongepowered.asm.mixin.Mixin;
import shadows.apotheosis.ench.api.IEnchantingBlock;

@Mixin({CandleBlock.class})
public abstract class CandleBlockMixin extends AbstractCandleBlock implements IEnchantingBlock {
   protected CandleBlockMixin(Properties pProperties) {
      super(pProperties);
   }

   @Override
   public float getArcanaBonus(BlockState state, LevelReader world, BlockPos pos) {
      return 1.25F * (float)((Integer)state.m_61143_(CandleBlock.f_152790_)).intValue();
   }
}
