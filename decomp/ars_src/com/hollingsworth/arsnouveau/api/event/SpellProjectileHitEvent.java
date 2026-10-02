package com.hollingsworth.arsnouveau.api.event;

import com.hollingsworth.arsnouveau.common.entity.EntityProjectileSpell;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.EntityEvent;

public class SpellProjectileHitEvent extends EntityEvent {
   public HitResult hit;
   public EntityProjectileSpell projectile;

   public SpellProjectileHitEvent(EntityProjectileSpell entity, HitResult result) {
      super(entity);
      this.projectile = entity;
      this.hit = result;
   }

   public EntityProjectileSpell getProjectile() {
      return this.projectile;
   }

   public HitResult getHitResult() {
      return this.hit;
   }

   public boolean isCancelable() {
      return true;
   }
}
