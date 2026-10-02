package com.obscuria.aquamirae.common.entities.projectiles;

import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.obscureapi.api.common.DynamicProjectile;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages.SpawnEntity;

public class MazeRose extends DynamicProjectile {
   public MazeRose(SpawnEntity packet, Level world) {
      this((EntityType<MazeRose>)AquamiraeEntities.MAZE_ROSE.get(), world);
   }

   public MazeRose(EntityType<MazeRose> type, Level world) {
      super(type, world);
   }

   public void updateMotion() {
      Vec3 center = this.OWNER.m_20182_().m_82520_(0.0, (double)this.OWNER.m_20206_() * 0.33, 0.0);
      float radius = this.getRadius();
      float speed = this.getSpinSpeed();
      float offset = this.getSpinOffset();
      Vec3 orbit = new Vec3(
         center.f_82479_ + Math.cos((double)(speed + offset)) * (double)radius + Math.sin((double)(speed * 6.0F + offset)) * (double)(radius * 0.5F),
         center.f_82480_,
         center.f_82481_ + Math.sin((double)(speed + offset)) * (double)radius + Math.cos((double)(speed * 6.0F + offset)) * (double)(radius * 0.5F)
      );
      this.m_20219_(orbit);
   }

   public float getAttackRange() {
      return 1.3F;
   }

   protected float getDefaultRadius() {
      return 6.0F;
   }

   protected float getDefaultSpinSpeed() {
      return 0.03F;
   }
}
