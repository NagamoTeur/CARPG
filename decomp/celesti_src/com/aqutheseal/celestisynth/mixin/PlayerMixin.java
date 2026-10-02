package com.aqutheseal.celestisynth.mixin;

import com.aqutheseal.celestisynth.api.item.CSWeapon;
import com.aqutheseal.celestisynth.api.mixin.PlayerMixinSupport;
import com.aqutheseal.celestisynth.common.network.util.SetPersistentIntPacket;
import com.aqutheseal.celestisynth.manager.CSNetworkManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Player.class})
public abstract class PlayerMixin extends LivingEntity implements PlayerMixinSupport {
   private static final String CAMERA_ANGLE_ORDINAL = "cs.cameraOrdinal";
   private static final String SCREENSHAKE_DURATION = "cs.screenShakeDuration";
   private static final String SCREENSHAKE_FADEOUTBEGIN = "cs.screenShakeFadeoutStart";
   private static final String SCREENSHAKE_INTENSITY = "cs.screenShakeIntensity";

   private PlayerMixin(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
   }

   @Shadow
   public abstract Inventory m_150109_();

   @Inject(
      method = {"attack"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void celestisynth$attack(Entity pTarget, CallbackInfo ci) {
      if (this.cancelCI(this.m_21205_()) || this.cancelCI(this.m_21206_())) {
         ci.cancel();
      }
   }

   @Inject(
      method = {"tick"},
      at = {@At("TAIL")}
   )
   public void celestisynth$tick(CallbackInfo ci) {
      if (this.f_19853_.m_5776_() && this.getScreenShakeDuration() > 0) {
         if (this.getScreenShakeIntensity() > 1.0F) {
            this.setScreenShakeIntensity(1.0F);
         }

         this.setScreenShakeDuration(this.getScreenShakeDuration() - 1);
         if (this.getScreenShakeDuration() < this.getScreenShakeFadeoutBegin()) {
            this.setScreenShakeIntensity(Math.max(0.0F, this.getScreenShakeIntensity() - 0.001F));
         }
      }

      if (this.m_21205_().m_41720_() instanceof CSWeapon cs) {
         cs.forceTick(this.m_21205_(), this.f_19853_, this, this.m_150109_().f_35977_, this.m_150109_().m_36056_() == this.m_21205_());
      }

      if (this.m_21206_().m_41720_() instanceof CSWeapon cs) {
         cs.forceTick(this.m_21206_(), this.f_19853_, this, 40, this.m_150109_().m_36056_() == this.m_21206_());
      }
   }

   @Override
   public int getScreenShakeDuration() {
      return this.getPersistentData().m_128451_("cs.screenShakeDuration");
   }

   @Override
   public void setScreenShakeDuration(int duration) {
      this.getPersistentData().m_128405_("cs.screenShakeDuration", duration);
   }

   @Override
   public int getScreenShakeFadeoutBegin() {
      return this.getPersistentData().m_128451_("cs.screenShakeFadeoutStart");
   }

   @Override
   public void setScreenShakeFadeoutBegin(int beginByValue) {
      this.getPersistentData().m_128405_("cs.screenShakeFadeoutStart", beginByValue);
   }

   @Override
   public float getScreenShakeIntensity() {
      return this.getPersistentData().m_128457_("cs.screenShakeIntensity");
   }

   @Override
   public void setScreenShakeIntensity(float intensity) {
      this.getPersistentData().m_128350_("cs.screenShakeIntensity", intensity);
   }

   @Override
   public int getCameraAngleOrdinal() {
      return this.getPersistentData().m_128451_("cs.cameraOrdinal");
   }

   @Override
   public void setCameraAngleOrdinal(int ordinal) {
      if (this.f_19853_.m_5776_()) {
         this.getPersistentData().m_128405_("cs.cameraOrdinal", ordinal);
         CSNetworkManager.sendToServer(new SetPersistentIntPacket("cs.cameraOrdinal", ordinal));
      }
   }

   private boolean cancelCI(ItemStack stack) {
      if (stack.m_41720_() instanceof CSWeapon) {
         CompoundTag controllerTag = stack.m_41737_("csController");
         if (controllerTag != null) {
            return controllerTag.m_128471_("cs.hasAnimationBegun");
         }
      }

      return false;
   }
}
