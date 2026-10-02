package shadows.apotheosis.village.fletching;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FletchingTableBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import shadows.placebo.util.IReplacementBlock;

public class ApothFletchingBlock extends FletchingTableBlock implements IReplacementBlock {
   public static final Component NAME = Component.m_237115_("apotheosis.recipes.fletching");
   protected StateDefinition<Block, BlockState> container;

   public ApothFletchingBlock() {
      super(Properties.m_60939_(Material.f_76320_).m_60978_(2.5F).m_60918_(SoundType.f_56736_));
   }

   public InteractionResult m_6227_(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
      if (worldIn.f_46443_) {
         return InteractionResult.SUCCESS;
      } else {
         player.m_5893_(this.m_7246_(state, worldIn, pos));
         return InteractionResult.CONSUME;
      }
   }

   public MenuProvider m_7246_(BlockState state, Level world, BlockPos pos) {
      return ApothFletchingBlock.What.getMenuProvider(state, world, pos);
   }

   public void _setDefaultState(BlockState state) {
      this.m_49959_(state);
   }

   public void setStateContainer(StateDefinition<Block, BlockState> container) {
      this.container = container;
   }

   public StateDefinition<Block, BlockState> m_49965_() {
      return this.container == null ? super.m_49965_() : this.container;
   }

   private static class What {
      static MenuProvider getMenuProvider(BlockState state, Level world, BlockPos pos) {
         return new SimpleMenuProvider((id, inv, player) -> new FletchingContainer(id, inv, world, pos), ApothFletchingBlock.NAME);
      }
   }
}
