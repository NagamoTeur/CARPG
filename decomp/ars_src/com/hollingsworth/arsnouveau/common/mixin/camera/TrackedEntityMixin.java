package com.hollingsworth.arsnouveau.common.mixin.camera;

import com.hollingsworth.arsnouveau.common.entity.ScryerCamera;
import com.hollingsworth.arsnouveau.common.util.CameraUtil;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ChunkMap.TrackedEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(
   value = {TrackedEntity.class},
   priority = 1100
)
public abstract class TrackedEntityMixin {
   @Shadow
   @Final
   ServerEntity f_140471_;
   @Shadow
   @Final
   Entity f_140472_;
   @Unique
   private boolean shouldBeSent = false;

   @Inject(
      method = {"updatePlayer"},
      at = {@At(
         value = "FIELD",
         target = "Lnet/minecraft/world/phys/Vec3;x:D",
         ordinal = 0
      )},
      locals = LocalCapture.CAPTURE_FAILSOFT
   )
   private void onUpdatePlayer(ServerPlayer player, CallbackInfo callback, Vec3 unused, double viewDistance) {
      if (CameraUtil.isPlayerMountedOnCamera(player)) {
         Vec3 relativePosToCamera = player.m_8954_().m_20182_().m_82546_(this.f_140472_.m_20182_());
         if (relativePosToCamera.f_82479_ >= -viewDistance
            && relativePosToCamera.f_82479_ <= viewDistance
            && relativePosToCamera.f_82481_ >= -viewDistance
            && relativePosToCamera.f_82481_ <= viewDistance) {
            this.shouldBeSent = true;
         }
      }
   }

   @ModifyVariable(
      method = {"updatePlayer"},
      name = {"flag"},
      at = @At(
         value = "JUMP",
         opcode = 153,
         shift = Shift.BEFORE,
         ordinal = 1
      )
   )
   public boolean modifyFlag(boolean original) {
      boolean shouldBeSent = this.shouldBeSent;
      this.shouldBeSent = false;
      return this.f_140472_ instanceof ScryerCamera || original || shouldBeSent;
   }
}
