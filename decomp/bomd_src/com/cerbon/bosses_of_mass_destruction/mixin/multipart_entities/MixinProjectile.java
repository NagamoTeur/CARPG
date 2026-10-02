package com.cerbon.bosses_of_mass_destruction.mixin.multipart_entities;

import com.cerbon.bosses_of_mass_destruction.api.multipart_entities.entity.MultipartAwareEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Projectile.class})
public abstract class MixinProjectile extends Entity {
   public MixinProjectile(EntityType<?> type, Level level) {
      super(type, level);
   }

   @Inject(
      method = {"onHit"},
      at = {@At("HEAD")}
   )
   private void onCollision(HitResult hitResult, CallbackInfo ci) {
      Type type = hitResult.m_6662_();
      if (type == Type.ENTITY
         && hitResult instanceof EntityHitResult
         && ((EntityHitResult)hitResult).m_82443_() instanceof MultipartAwareEntity multipartAwareEntity) {
         String nextDamagedPart = multipartAwareEntity.getBounds().raycast(this.m_20182_(), this.m_20182_().m_82549_(this.m_20184_()));
         multipartAwareEntity.setNextDamagedPart(nextDamagedPart);
      }
   }
}
