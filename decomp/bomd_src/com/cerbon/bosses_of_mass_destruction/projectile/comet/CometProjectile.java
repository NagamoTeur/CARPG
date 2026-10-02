package com.cerbon.bosses_of_mass_destruction.projectile.comet;

import com.cerbon.bosses_of_mass_destruction.entity.BMDEntities;
import com.cerbon.bosses_of_mass_destruction.projectile.BaseThrownItemProjectile;
import com.cerbon.bosses_of_mass_destruction.projectile.util.ExemptEntities;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class CometProjectile extends BaseThrownItemProjectile implements IAnimatable, IAnimationTickable {
   private Consumer<Vec3> impactAction;
   private boolean impacted = false;

   public CometProjectile(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
      super(entityType, level);
   }

   public CometProjectile(LivingEntity livingEntity, Level level, Consumer<Vec3> impactAction, List<EntityType<?>> exemptEntities) {
      super((EntityType<? extends ThrowableItemProjectile>)BMDEntities.COMET.get(), livingEntity, level, new ExemptEntities(exemptEntities));
      this.impactAction = impactAction;
   }

   @Override
   public void entityHit(EntityHitResult entityHitResult) {
      this.onImpact();
   }

   protected void m_8060_(@NotNull BlockHitResult result) {
      this.onImpact();
      super.m_8060_(result);
   }

   public boolean m_6469_(@NotNull DamageSource source, float amount) {
      this.onImpact();
      return super.m_6469_(source, amount);
   }

   private void onImpact() {
      if (!this.impacted) {
         this.impacted = true;
         Entity owner = this.m_37282_();
         if (owner instanceof LivingEntity) {
            if (this.impactAction != null) {
               this.impactAction.accept(this.m_20182_());
            }

            this.m_146870_();
         }
      }
   }

   public boolean m_7337_(@NotNull Entity entity) {
      return true;
   }

   public float m_146909_() {
      return (float)this.f_19797_ * 5.0F;
   }

   public void registerControllers(AnimationData animationData) {
   }

   public AnimationFactory getFactory() {
      return new AnimationFactory(this);
   }

   public int tickTimer() {
      return 0;
   }
}
