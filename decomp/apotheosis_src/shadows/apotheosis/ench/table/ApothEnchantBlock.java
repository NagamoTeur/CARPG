package shadows.apotheosis.ench.table;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import shadows.apotheosis.ench.api.IEnchantingBlock;
import shadows.placebo.util.IReplacementBlock;

public class ApothEnchantBlock extends EnchantmentTableBlock implements IReplacementBlock {
   protected StateDefinition<Block, BlockState> container;

   public ApothEnchantBlock() {
      super(Properties.m_60944_(Material.f_76278_, MaterialColor.f_76364_).m_60913_(5.0F, 1200.0F));
   }

   @Nullable
   public MenuProvider m_7246_(BlockState state, Level world, BlockPos pos) {
      BlockEntity tileentity = world.m_7702_(pos);
      if (tileentity instanceof ApothEnchantTile) {
         Component itextcomponent = ((Nameable)tileentity).m_5446_();
         return new SimpleMenuProvider(
            (id, inventory, player) -> new ApothEnchantmentMenu(id, inventory, ContainerLevelAccess.m_39289_(world, pos), (ApothEnchantTile)tileentity),
            itextcomponent
         );
      } else {
         return null;
      }
   }

   public BlockEntity m_142194_(BlockPos pPos, BlockState pState) {
      return new ApothEnchantTile(pPos, pState);
   }

   public void m_6810_(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
      if (state.m_60734_() != newState.m_60734_()) {
         BlockEntity tileentity = world.m_7702_(pos);
         if (tileentity instanceof ApothEnchantTile) {
            Block.m_49840_(world, pos, ((ApothEnchantTile)tileentity).inv.getStackInSlot(0));
            world.m_46747_(pos);
         }
      }
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

   @OnlyIn(Dist.CLIENT)
   public void m_214162_(BlockState state, Level level, BlockPos pos, RandomSource rand) {
      for (BlockPos offset : f_207902_) {
         BlockState shelfState = level.m_8055_(pos.m_121955_(offset));
         ((IEnchantingBlock)shelfState.m_60734_()).spawnTableParticle(shelfState, level, rand, pos, offset);
      }
   }
}
