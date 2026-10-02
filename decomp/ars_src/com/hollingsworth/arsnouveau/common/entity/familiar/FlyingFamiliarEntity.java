package com.hollingsworth.arsnouveau.common.entity.familiar;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;

public class FlyingFamiliarEntity extends FamiliarEntity {
   public FlyingFamiliarEntity(EntityType<? extends PathfinderMob> p_i48575_1_, Level p_i48575_2_) {
      super(p_i48575_1_, p_i48575_2_);
      this.f_21342_ = new FlyingMoveControl(this, 10, true);
   }

   protected PathNavigation m_6037_(Level world) {
      FlyingPathNavigation flyingpathnavigator = new FlyingPathNavigation(this, world);
      flyingpathnavigator.m_26440_(false);
      flyingpathnavigator.m_7008_(true);
      flyingpathnavigator.m_26443_(true);
      return flyingpathnavigator;
   }

   @Override
   public boolean canTeleport() {
      return true;
   }

   protected int m_5639_(float p_225508_1_, float p_225508_2_) {
      return 0;
   }

   public boolean m_142535_(float p_147187_, float p_147188_, DamageSource p_147189_) {
      return false;
   }
}
