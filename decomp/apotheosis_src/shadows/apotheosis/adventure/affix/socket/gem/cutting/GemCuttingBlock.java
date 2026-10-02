package shadows.apotheosis.adventure.affix.socket.gem.cutting;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GemCuttingBlock extends HorizontalDirectionalBlock {
   public static final Component NAME = Component.m_237115_("menu.apotheosis.gem_cutting");
   public static final VoxelShape SHAPE = Shapes.m_83124_(
      m_49796_(0.0, 12.0, 0.0, 16.0, 16.0, 16.0),
      new VoxelShape[]{
         m_49796_(0.0, 0.0, 0.0, 2.0, 16.0, 2.0),
         m_49796_(0.0, 0.0, 14.0, 2.0, 16.0, 16.0),
         m_49796_(14.0, 0.0, 0.0, 16.0, 16.0, 2.0),
         m_49796_(14.0, 0.0, 14.0, 16.0, 16.0, 16.0)
      }
   );

   public GemCuttingBlock(Properties props) {
      super(props);
      this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_54117_, Direction.NORTH));
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{f_54117_});
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
      return new SimpleMenuProvider((id, pInv, player) -> new GemCuttingMenu(id, pInv, ContainerLevelAccess.m_39289_(world, pos)), NAME);
   }

   public VoxelShape m_5940_(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
      return SHAPE;
   }

   public void m_5871_(ItemStack pStack, BlockGetter pLevel, List<Component> list, TooltipFlag pFlag) {
      list.add(Component.m_237115_(this.m_7705_() + ".desc").m_130940_(ChatFormatting.GRAY));
   }

   public BlockState m_5573_(BlockPlaceContext pContext) {
      return (BlockState)this.m_49966_().m_61124_(f_54117_, pContext.m_8125_().m_122424_());
   }
}
