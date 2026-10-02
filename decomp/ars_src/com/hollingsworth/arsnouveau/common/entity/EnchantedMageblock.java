package com.hollingsworth.arsnouveau.common.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class EnchantedMageblock extends EnchantedFallingBlock {
   public EnchantedMageblock(EntityType<? extends ColoredProjectile> p_31950_, Level p_31951_) {
      super(p_31950_, p_31951_);
   }

   public EnchantedMageblock(Level level, double v, int y, double v1, BlockState blockState) {
      super(level, v, (double)y, v1, blockState);
   }

   @Override
   public EntityType<?> m_6095_() {
      return (EntityType<?>)ModEntities.ENCHANTED_MAGE_BLOCK.get();
   }
}
