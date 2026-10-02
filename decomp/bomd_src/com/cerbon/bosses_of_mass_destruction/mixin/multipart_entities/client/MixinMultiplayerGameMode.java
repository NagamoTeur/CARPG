package com.cerbon.bosses_of_mass_destruction.mixin.multipart_entities.client;

import com.cerbon.bosses_of_mass_destruction.api.multipart_entities.client.PlayerInteractMultipartEntity;
import com.cerbon.bosses_of_mass_destruction.api.multipart_entities.entity.MultipartAwareEntity;
import com.cerbon.bosses_of_mass_destruction.packet.BMDPacketHandler;
import com.cerbon.bosses_of_mass_destruction.packet.custom.multipart_entities.MultipartEntityInteractionC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({MultiPlayerGameMode.class})
public abstract class MixinMultiplayerGameMode {
   @Shadow
   private GameType f_105197_;

   @Shadow
   protected abstract void m_105297_();

   @Inject(
      method = {"attack"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void attackHook(Player player, Entity target, CallbackInfo ci) {
      if (target instanceof MultipartAwareEntity) {
         this.m_105297_();
         Minecraft client = Minecraft.m_91087_();
         Vec3 pos = client.f_91075_.m_20299_(client.m_91296_());
         Vec3 dir = client.f_91075_.m_20252_(client.m_91296_());
         double reach = (double)client.f_91072_.m_105286_();
         String part = ((MultipartAwareEntity)target).getBounds().raycast(pos, pos.m_82549_(dir.m_82490_(reach)));
         if (part == null) {
            return;
         }

         BMDPacketHandler.sendToServer(
            new MultipartEntityInteractionC2SPacket(
               target.m_19879_(), part, InteractionHand.MAIN_HAND, client.f_91075_.m_6144_(), PlayerInteractMultipartEntity.InteractionType.ATTACK
            )
         );
         if (this.f_105197_ != GameType.SPECTATOR) {
            ((MultipartAwareEntity)target).setNextDamagedPart(part);
            player.m_5706_(target);
            player.m_36334_();
         }

         ci.cancel();
      }
   }

   @Inject(
      method = {"interact"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void interactHook(Player player, Entity entity, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
      if (entity instanceof MultipartAwareEntity) {
         this.m_105297_();
         Minecraft client = Minecraft.m_91087_();
         Vec3 pos = client.f_91075_.m_20299_(client.m_91296_());
         Vec3 dir = client.f_91075_.m_20252_(client.m_91296_());
         double reach = (double)client.f_91072_.m_105286_();
         String part = ((MultipartAwareEntity)entity).getBounds().raycast(pos, pos.m_82549_(dir.m_82490_(reach)));
         if (part == null) {
            return;
         }

         BMDPacketHandler.sendToServer(
            new MultipartEntityInteractionC2SPacket(
               entity.m_19879_(), part, hand, client.f_91075_.m_6144_(), PlayerInteractMultipartEntity.InteractionType.INTERACT
            )
         );
         if (this.f_105197_ != GameType.SPECTATOR) {
            cir.setReturnValue(((MultipartAwareEntity)entity).interact(player, hand, part));
         }

         cir.setReturnValue(InteractionResult.PASS);
      }
   }
}
