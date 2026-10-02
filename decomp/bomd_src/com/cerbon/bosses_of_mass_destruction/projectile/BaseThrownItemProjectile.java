package com.cerbon.bosses_of_mass_destruction.projectile;

import com.google.errorprone.annotations.ForOverride;
import java.util.function.Predicate;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public abstract class BaseThrownItemProjectile extends ThrowableItemProjectile {
   protected final Predicate<EntityHitResult> entityCollisionPredicate;
   protected Predicate<HitResult> collisionPredicate = hitResult -> !this.f_19853_.m_5776_();

   public BaseThrownItemProjectile(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
      super(entityType, level);
      this.entityCollisionPredicate = entityHitResult -> true;
   }

   public BaseThrownItemProjectile(
      EntityType<? extends ThrowableItemProjectile> entityType, LivingEntity livingEntity, Level level, Predicate<EntityHitResult> collisionPredicate
   ) {
      super(entityType, livingEntity, level);
      this.entityCollisionPredicate = collisionPredicate;
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.f_19853_.f_46443_) {
         this.clientTick();
      }
   }

   @NotNull
   public Packet<?> m_5654_() {
      return super.m_5654_();
   }

   protected void m_6532_(@NotNull HitResult hitResult) {
      if (this.collisionPredicate.test(hitResult)) {
         super.m_6532_(hitResult);
      }
   }

   protected void m_5790_(@NotNull EntityHitResult entityHitResult) {
      if (this.entityCollisionPredicate.test(entityHitResult)) {
         this.entityHit(entityHitResult);
      }
   }

   protected abstract void entityHit(EntityHitResult var1);

   @ForOverride
   public void clientTick() {
   }

   @NotNull
   protected Item m_7881_() {
      return Items.f_42452_;
   }
}
