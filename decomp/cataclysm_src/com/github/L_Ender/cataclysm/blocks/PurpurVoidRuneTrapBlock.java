package com.github.L_Ender.cataclysm.blocks;

import com.github.L_Ender.cataclysm.entity.projectile.Void_Rune_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class PurpurVoidRuneTrapBlock extends TrapBlock {
   public PurpurVoidRuneTrapBlock(Properties properties) {
      super(properties);
   }

   @Override
   public void m_141947_(Level worldIn, BlockPos pos, BlockState state, Entity entityIn) {
      activate(worldIn.m_8055_(pos), worldIn, pos, entityIn);
      super.m_141947_(worldIn, pos, state, entityIn);
   }

   private static void activate(BlockState state, Level world, BlockPos pos, Entity entity) {
      if (!(Boolean)state.m_61143_(LIT) && shouldTrigger(entity)) {
         Void_Rune_Entity voidrune = (Void_Rune_Entity)((EntityType)ModEntities.VOID_RUNE.get()).m_20615_(world);
         if (voidrune != null) {
            voidrune.m_7678_((double)pos.m_123341_() + 0.5, (double)(pos.m_123342_() + 1), (double)pos.m_123343_() + 0.5, 0.0F, 0.0F);
            world.m_7967_(voidrune);
         }

         ((LivingEntity)entity).m_7292_(new MobEffectInstance(MobEffects.f_19597_, 50, 3));
         world.m_7731_(pos, (BlockState)state.m_61124_(LIT, true), 3);
      }
   }
}
