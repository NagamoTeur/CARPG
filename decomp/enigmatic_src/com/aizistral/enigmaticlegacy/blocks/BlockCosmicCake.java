package com.aizistral.enigmaticlegacy.blocks;

import com.aizistral.enigmaticlegacy.registries.EnigmaticSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class BlockCosmicCake extends CakeBlock {
   private static final FoodProperties AS_TASTY_AS = Foods.f_38831_;

   public BlockCosmicCake() {
      super(Properties.m_60926_(Blocks.f_50145_));
   }

   public InteractionResult m_6227_(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
      if ((Integer)pState.m_61143_(f_51180_) > 5) {
         return InteractionResult.PASS;
      } else {
         ItemStack itemstack = pPlayer.m_21120_(pHand);
         if (pLevel.f_46443_) {
            if (eat(pLevel, pPos, pState, pPlayer).m_19077_()) {
               return InteractionResult.SUCCESS;
            }

            if (itemstack.m_41619_()) {
               return InteractionResult.CONSUME;
            }
         }

         return eat(pLevel, pPos, pState, pPlayer);
      }
   }

   protected static InteractionResult eat(LevelAccessor pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
      if (!pPlayer.m_36391_(false)) {
         return InteractionResult.PASS;
      } else {
         pPlayer.m_36220_(Stats.f_12942_);
         pPlayer.m_36324_().m_38707_(AS_TASTY_AS.m_38744_(), AS_TASTY_AS.m_38745_());
         int i = (Integer)pState.m_61143_(f_51180_);
         pLevel.m_142346_(pPlayer, GameEvent.f_157806_, pPos);
         pLevel.m_46796_(2001, pPos, Block.m_49956_(pState));
         if (i < 6) {
            pLevel.m_7731_(pPos, (BlockState)pState.m_61124_(f_51180_, i + 1), 3);
         } else {
            pLevel.m_7471_(pPos, false);
            pLevel.m_142346_(pPlayer, GameEvent.f_157794_, pPos);
         }

         pLevel.m_5594_(null, pPlayer.m_20183_(), SoundEvents.f_11912_, SoundSource.BLOCKS, 1.0F, 0.5F + (float)Math.random() * 0.5F);
         return InteractionResult.SUCCESS;
      }
   }

   public void m_213898_(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      int bites = 0;
      if ((bites = (Integer)state.m_61143_(f_51180_)) > 0) {
         level.m_7731_(pos, (BlockState)state.m_61124_(f_51180_, bites - 1), 3);
         level.m_46796_(2001, pos, Block.m_49956_(state));
         level.m_5594_(null, pos, EnigmaticSounds.EAT_REVERSE, SoundSource.BLOCKS, 1.0F, 0.5F + (float)Math.random() * 0.5F);
      }
   }

   public boolean m_6724_(BlockState pState) {
      return (Integer)pState.m_61143_(f_51180_) > 0;
   }
}
