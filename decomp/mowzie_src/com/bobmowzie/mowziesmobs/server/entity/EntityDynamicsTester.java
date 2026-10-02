package com.bobmowzie.mowziesmobs.server.entity;

import com.bobmowzie.mowziesmobs.client.model.tools.dynamics.DynamicChain;
import com.ilexiconn.llibrary.server.animation.Animation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EntityDynamicsTester extends MowzieLLibraryEntity {
   @OnlyIn(Dist.CLIENT)
   public DynamicChain dc;

   public EntityDynamicsTester(Level world) {
      super((EntityType<? extends MowzieEntity>)EntityHandler.NAGA.get(), world);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(4, new RandomStrollGoal(this, 0.3));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
   }

   @Override
   public Animation getDeathAnimation() {
      return null;
   }

   @Override
   public Animation getHurtAnimation() {
      return null;
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[0];
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      if (this.f_19853_.f_46443_) {
         if (this.f_19797_ == 1) {
            this.dc = new DynamicChain(this);
         }

         this.dc.updateSpringConstraint(0.1F, 0.3F, 0.6F, 1.0F, true, 0.5F, 1);
         this.f_20883_ = this.m_146908_();
      }
   }
}
