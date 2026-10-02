package com.github.L_Ender.cataclysm.blocks;

import com.github.L_Ender.cataclysm.blockentities.AltarOfFire_Block_Entity;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;

public class Altar_Of_Fire_Block extends BaseEntityBlock {
   public Altar_Of_Fire_Block() {
      super(
         Properties.m_60939_(Material.f_76281_)
            .m_60955_()
            .m_60953_(block -> 7)
            .m_60991_((block, world, pos) -> true)
            .m_60913_(-1.0F, 3600000.0F)
            .m_222994_()
            .m_60918_(SoundType.f_56743_)
      );
   }

   public void m_214162_(BlockState p_220918_, Level p_220919_, BlockPos p_220920_, RandomSource p_220921_) {
      if (p_220921_.m_188503_(5) == 0) {
         for (int i = 0; i < p_220921_.m_188503_(1) + 1; i++) {
            p_220919_.m_7106_(
               ParticleTypes.f_123756_,
               (double)p_220920_.m_123341_() + 0.5,
               (double)p_220920_.m_123342_() + 1.5,
               (double)p_220920_.m_123343_() + 0.5,
               (double)(p_220921_.m_188501_() / 2.0F),
               5.0E-5,
               (double)(p_220921_.m_188501_() / 2.0F)
            );
         }
      }
   }

   public void m_7892_(BlockState p_51269_, Level p_51270_, BlockPos p_51271_, Entity p_51272_) {
      if (p_51272_ instanceof LivingEntity && !EnchantmentHelper.m_44938_((LivingEntity)p_51272_)) {
         p_51272_.m_6469_(DamageSource.f_19305_, 3.0F);
      }

      super.m_7892_(p_51269_, p_51270_, p_51271_, p_51272_);
   }

   public InteractionResult m_6227_(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
      ItemStack heldItem = player.m_21120_(handIn);
      if (worldIn.m_7702_(pos) instanceof AltarOfFire_Block_Entity && !player.m_6144_() && heldItem.m_41720_() != this.m_5456_()) {
         AltarOfFire_Block_Entity aof = (AltarOfFire_Block_Entity)worldIn.m_7702_(pos);
         ItemStack copy = heldItem.m_41777_();
         copy.m_41764_(1);
         if (aof.m_8020_(0).m_41619_()) {
            aof.m_6836_(0, copy);
            if (!player.m_7500_()) {
               heldItem.m_41774_(1);
            }
         } else {
            m_49840_(worldIn, pos, aof.m_8020_(0).m_41777_());
            aof.m_6836_(0, ItemStack.f_41583_);
         }

         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }

   @Nullable
   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new AltarOfFire_Block_Entity(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_152180_, BlockState p_152181_, BlockEntityType<T> p_152182_) {
      return m_152132_(p_152182_, (BlockEntityType)ModTileentites.ALTAR_OF_FIRE.get(), AltarOfFire_Block_Entity::commonTick);
   }
}
