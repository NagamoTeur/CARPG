package com.bobmowzie.mowziesmobs.server.potion;

import com.bobmowzie.mowziesmobs.server.block.BlockHandler;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class EffectGeomancy extends MowzieEffect {
   public EffectGeomancy() {
      super(MobEffectCategory.BENEFICIAL, 13500280);
   }

   public static boolean isBlockDiggable(BlockState blockState) {
      Material mat = blockState.m_60767_();
      return mat != Material.f_76315_ && mat != Material.f_76314_ && mat != Material.f_76278_ && mat != Material.f_76313_ && mat != Material.f_76317_
         ? false
         : blockState.m_60734_() != Blocks.f_50335_
            && blockState.m_60734_() != Blocks.f_50451_
            && !(blockState.m_60734_() instanceof FenceBlock)
            && blockState.m_60734_() != Blocks.f_50085_
            && blockState.m_60734_() != Blocks.f_50453_
            && blockState.m_60734_() != Blocks.f_50201_
            && blockState.m_60734_() != Blocks.f_50258_
            && blockState.m_60734_() != Blocks.f_50265_
            && blockState.m_60734_() != Blocks.f_50374_
            && blockState.m_60734_() != Blocks.f_50332_
            && blockState.m_60734_() != BlockHandler.THATCH.get()
            && !blockState.m_155947_();
   }

   public static boolean canUse(LivingEntity entity) {
      return entity.m_21205_().m_41619_() && entity.m_21023_((MobEffect)EffectHandler.GEOMANCY.get());
   }
}
