package dev.latvian.mods.kubejs.entity;

import dev.latvian.mods.kubejs.level.BlockContainerJS;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.Nullable;

public class RayTraceResultJS {
   public final Entity fromEntity;
   public final Type type;
   public final double distance;
   public Vec3 hit = null;
   public BlockContainerJS block = null;
   public Direction facing = null;
   public Entity entity = null;

   public RayTraceResultJS(Entity from, @Nullable HitResult result, double d) {
      this.fromEntity = from;
      this.distance = d;
      this.type = result == null ? Type.MISS : result.m_6662_();
      if (result instanceof BlockHitResult b && result.m_6662_() == Type.BLOCK) {
         this.hit = result.m_82450_();
         this.block = new BlockContainerJS(from.f_19853_, b.m_82425_());
         this.facing = b.m_82434_();
         return;
      }

      if (result instanceof EntityHitResult e && result.m_6662_() == Type.ENTITY) {
         this.hit = result.m_82450_();
         this.entity = e.m_82443_();
      }
   }

   public double getHitX() {
      return this.hit == null ? Double.NaN : this.hit.f_82479_;
   }

   public double getHitY() {
      return this.hit == null ? Double.NaN : this.hit.f_82480_;
   }

   public double getHitZ() {
      return this.hit == null ? Double.NaN : this.hit.f_82481_;
   }
}
