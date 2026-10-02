package shadows.apotheosis.adventure.affix.salvaging;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.BlockHitResult;
import shadows.placebo.container.ContainerUtil;
import shadows.placebo.container.SimplerMenuProvider;

public class SalvagingTableBlock extends Block implements EntityBlock {
   public SalvagingTableBlock(Properties properties) {
      super(properties);
   }

   public MenuProvider m_7246_(BlockState pState, Level pLevel, BlockPos pPos) {
      return new SimplerMenuProvider(pLevel, pPos, SalvagingMenu::new);
   }

   public InteractionResult m_6227_(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
      return ContainerUtil.openGui(player, pos, SalvagingMenu::new);
   }

   public void m_5871_(ItemStack pStack, BlockGetter pLevel, List<Component> list, TooltipFlag pFlag) {
      list.add(Component.m_237115_(this.m_7705_() + ".desc").m_130940_(ChatFormatting.GRAY));
   }

   public BlockEntity m_142194_(BlockPos pPos, BlockState pState) {
      return new SalvagingTableTile(pPos, pState);
   }

   @Deprecated
   public void m_6810_(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
      if (state.m_60734_() != this || newState.m_60734_() != this) {
         if (world.m_7702_(pos) instanceof SalvagingTableTile salvTile) {
            for (int i = 0; i < salvTile.output.getSlots(); i++) {
               m_49840_(world, pos, salvTile.output.getStackInSlot(i));
            }
         }

         super.m_6810_(state, world, pos, newState, isMoving);
      }
   }
}
