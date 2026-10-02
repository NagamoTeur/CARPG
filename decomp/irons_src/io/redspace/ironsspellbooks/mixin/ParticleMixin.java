package io.redspace.ironsspellbooks.mixin;

import io.redspace.ironsspellbooks.entity.spells.AbstractShieldEntity;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin({Particle.class})
public class ParticleMixin {
   @Shadow
   protected ClientLevel f_107208_;
   @Shadow
   private AABB f_107207_;

   @ModifyArg(
      method = {"move"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/entity/Entity;collideBoundingBox(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/AABB;Lnet/minecraft/world/level/Level;Ljava/util/List;)Lnet/minecraft/world/phys/Vec3;"
      ),
      index = 4
   )
   private List<VoxelShape> mixin(List<VoxelShape> in) {
      List<VoxelShape> shieldCollisions = new ArrayList<>();
      this.f_107208_.m_45976_(AbstractShieldEntity.class, this.f_107207_.m_82400_(0.25)).stream().forEach(s -> shieldCollisions.addAll(s.getVoxels()));
      return shieldCollisions;
   }
}
