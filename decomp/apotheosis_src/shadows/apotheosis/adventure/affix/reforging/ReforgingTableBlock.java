package shadows.apotheosis.adventure.affix.reforging;

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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.placebo.block_entity.TickingEntityBlock;
import shadows.placebo.container.ContainerUtil;
import shadows.placebo.container.SimplerMenuProvider;

public class ReforgingTableBlock extends Block implements TickingEntityBlock {
   public static final Component TITLE = Component.m_237115_("container.apotheosis.reforge");
   public static final VoxelShape SHAPE = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);
   protected final LootRarity maxRarity;

   public ReforgingTableBlock(Properties properties, LootRarity maxRarity) {
      super(properties);
      this.maxRarity = maxRarity;
   }

   public LootRarity getMaxRarity() {
      return this.maxRarity;
   }

   public boolean m_7923_(BlockState pState) {
      return true;
   }

   public VoxelShape m_5940_(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
      return SHAPE;
   }

   public InteractionResult m_6227_(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
      return ContainerUtil.openGui(player, pos, ReforgingMenu::new);
   }

   public MenuProvider m_7246_(BlockState state, Level world, BlockPos pos) {
      return new SimplerMenuProvider(world, pos, ReforgingMenu::new);
   }

   public void m_5871_(ItemStack pStack, BlockGetter pLevel, List<Component> list, TooltipFlag pFlag) {
      list.add(Component.m_237115_(((ReforgingTableBlock)Apoth.Blocks.REFORGING_TABLE.get()).m_7705_() + ".desc").m_130940_(ChatFormatting.GRAY));
      if (this.maxRarity != LootRarity.ANCIENT) {
         list.add(
            Component.m_237110_(((ReforgingTableBlock)Apoth.Blocks.REFORGING_TABLE.get()).m_7705_() + ".desc2", new Object[]{this.getMaxRarity().toComponent()})
               .m_130940_(ChatFormatting.GRAY)
         );
      }
   }

   public BlockEntity m_142194_(BlockPos pPos, BlockState pState) {
      return new ReforgingTableTile(pPos, pState);
   }

   @Deprecated
   public void m_6810_(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
      if (state.m_60734_() != this || newState.m_60734_() != this) {
         if (world.m_7702_(pos) instanceof ReforgingTableTile ref) {
            for (int i = 0; i < ref.inv.getSlots(); i++) {
               m_49840_(world, pos, ref.inv.getStackInSlot(i));
            }
         }

         super.m_6810_(state, world, pos, newState, isMoving);
      }
   }
}
