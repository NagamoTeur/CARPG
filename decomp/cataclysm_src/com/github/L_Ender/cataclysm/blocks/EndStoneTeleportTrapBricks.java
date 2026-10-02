package com.github.L_Ender.cataclysm.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class EndStoneTeleportTrapBricks extends TrapBlock {
   public EndStoneTeleportTrapBricks(Properties properties) {
      super(properties);
   }

   @Override
   public void m_141947_(Level worldIn, BlockPos pos, BlockState state, Entity entityIn) {
      activate(worldIn.m_8055_(pos), worldIn, pos, entityIn);
      super.m_141947_(worldIn, pos, state, entityIn);
   }

   private static void activate(BlockState state, Level world, BlockPos pos, Entity entity) {
      if (!(Boolean)state.m_61143_(LIT) && shouldTrigger(entity)) {
         double d0 = entity.m_20185_() + (entity.f_19853_.f_46441_.m_188500_() - 0.5) * 16.0;
         double d1 = entity.m_20186_();
         double d2 = entity.m_20189_() + (entity.f_19853_.f_46441_.m_188500_() - 0.5) * 16.0;
         ((LivingEntity)entity).m_20984_(d0, d1, d2, false);
         ((LivingEntity)entity).m_7292_(new MobEffectInstance(MobEffects.f_19610_, 25));
         world.m_7731_(pos, (BlockState)state.m_61124_(LIT, true), 3);
         world.m_5594_(null, pos, SoundEvents.f_11852_, SoundSource.BLOCKS, 1.0F, 1.0F);
      }
   }
}
