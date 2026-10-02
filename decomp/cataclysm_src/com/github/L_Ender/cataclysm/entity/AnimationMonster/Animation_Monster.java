package com.github.L_Ender.cataclysm.entity.AnimationMonster;

import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.IAnimatedEntity;
import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class Animation_Monster extends Monster implements IAnimatedEntity {
   public int animationTick;
   public Animation currentAnimation;

   public Animation_Monster(EntityType entity, Level world) {
      super(entity, world);
   }

   public List<LivingEntity> getEntityLivingBaseNearby(double distanceX, double distanceY, double distanceZ, double radius) {
      return this.getEntitiesNearby(LivingEntity.class, distanceX, distanceY, distanceZ, radius);
   }

   public <T extends Entity> List<T> getEntitiesNearby(Class<T> entityClass, double dX, double dY, double dZ, double r) {
      return this.f_19853_
         .m_6443_(
            entityClass,
            this.m_20191_().m_82377_(dX, dY, dZ),
            e -> e != this && (double)this.m_20270_(e) <= r + (double)(e.m_20205_() / 2.0F) && e.m_20186_() <= this.m_20186_() + dY
         );
   }

   protected void onAnimationFinish(Animation animation) {
   }

   public Animation[] getAnimations() {
      return new Animation[]{NO_ANIMATION};
   }

   public int getAnimationTick() {
      return this.animationTick;
   }

   public void setAnimationTick(int tick) {
      this.animationTick = tick;
   }

   public Animation getAnimation() {
      return this.currentAnimation;
   }

   public void setAnimation(Animation animation) {
      if (animation == NO_ANIMATION) {
         this.onAnimationFinish(this.currentAnimation);
      }

      this.currentAnimation = animation;
      this.setAnimationTick(0);
   }
}
