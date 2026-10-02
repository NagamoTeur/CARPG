package com.aqutheseal.celestisynth.common.block;

import com.aqutheseal.celestisynth.client.gui.celestialcrafting.CelestialCraftingMenu;
import com.aqutheseal.celestisynth.common.registry.CSBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class CelestialCraftingTable extends Block implements EntityBlock {
   private static final Component CONTAINER_TITLE = Component.m_237115_("gui.celestisynth.celestial_crafting");

   public CelestialCraftingTable(Properties pProperties) {
      super(pProperties);
   }

   public InteractionResult m_6227_(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
      if (pLevel.f_46443_) {
         return InteractionResult.SUCCESS;
      } else {
         NetworkHooks.openScreen((ServerPlayer)pPlayer, pState.m_60750_(pLevel, pPos), pPos);
         pPlayer.m_5893_(pState.m_60750_(pLevel, pPos));
         pPlayer.m_36220_(Stats.f_12967_);
         return InteractionResult.CONSUME;
      }
   }

   public MenuProvider m_7246_(BlockState pState, Level pLevel, BlockPos pPos) {
      return new SimpleMenuProvider(
         (containerId, targetPlayerInv, ownerPlayer) -> new CelestialCraftingMenu(containerId, targetPlayerInv, ContainerLevelAccess.m_39289_(pLevel, pPos)),
         CONTAINER_TITLE
      );
   }

   public RenderShape m_7514_(BlockState state) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   public void m_214162_(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
      if (pRandom.m_188503_(24) == 0) {
         pLevel.m_7785_(
            (double)pPos.m_123341_() + 0.5,
            (double)pPos.m_123342_() + 0.5,
            (double)pPos.m_123343_() + 0.5,
            SoundEvents.f_11936_,
            SoundSource.BLOCKS,
            1.0F + pRandom.m_188501_(),
            pRandom.m_188501_() * 0.7F + 0.3F,
            false
         );
      }

      if (pRandom.m_188503_(24) == 0) {
         pLevel.m_7785_(
            (double)pPos.m_123341_() + 0.5,
            (double)pPos.m_123342_() + 0.5,
            (double)pPos.m_123343_() + 0.5,
            SoundEvents.f_12325_,
            SoundSource.BLOCKS,
            1.0F + pRandom.m_188501_(),
            pRandom.m_188501_() * 0.7F + 0.3F,
            false
         );
      }

      for (int i = 0; i < 2; i++) {
         double rX = -0.2 + pRandom.m_188500_() * 0.4;
         double rY = pRandom.m_188500_() * 0.2;
         double rZ = -0.2 + pRandom.m_188500_() * 0.4;
         pLevel.m_7106_(ParticleTypes.f_123745_, (double)pPos.m_123341_() + 0.5, (double)pPos.m_123342_() + 0.75, (double)pPos.m_123343_() + 0.5, rX, rY, rZ);
         pLevel.m_7106_(ParticleTypes.f_123746_, (double)pPos.m_123341_() + 0.5, (double)pPos.m_123342_() + 0.75, (double)pPos.m_123343_() + 0.5, rZ, rY, rX);
      }
   }

   @Nullable
   public BlockEntity m_142194_(BlockPos pPos, BlockState pState) {
      return ((BlockEntityType)CSBlockEntityTypes.CELESTIAL_CRAFTING_TABLE_TILE.get()).m_155264_(pPos, pState);
   }
}
