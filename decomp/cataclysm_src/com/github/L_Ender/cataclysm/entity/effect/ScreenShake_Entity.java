package com.github.L_Ender.cataclysm.entity.effect;

import com.github.L_Ender.cataclysm.init.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

public class ScreenShake_Entity extends Entity {
   private static final EntityDataAccessor<Float> RADIUS = SynchedEntityData.m_135353_(ScreenShake_Entity.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Float> MAGNITUDE = SynchedEntityData.m_135353_(ScreenShake_Entity.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.m_135353_(ScreenShake_Entity.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> FADE_DURATION = SynchedEntityData.m_135353_(ScreenShake_Entity.class, EntityDataSerializers.f_135028_);

   public ScreenShake_Entity(EntityType<?> type, Level world) {
      super(type, world);
   }

   public ScreenShake_Entity(Level world, Vec3 position, float radius, float magnitude, int duration, int fadeDuration) {
      super((EntityType)ModEntities.SCREEN_SHAKE.get(), world);
      this.setRadius(radius);
      this.setMagnitude(magnitude);
      this.setDuration(duration);
      this.setFadeDuration(fadeDuration);
      this.m_6034_(position.f_82479_, position.f_82480_, position.f_82481_);
   }

   @OnlyIn(Dist.CLIENT)
   public float getShakeAmount(Player player, float delta) {
      float ticksDelta = (float)this.f_19797_ + delta;
      float timeFrac = 1.0F - (ticksDelta - (float)this.getDuration()) / ((float)this.getFadeDuration() + 1.0F);
      float baseAmount = ticksDelta < (float)this.getDuration() ? this.getMagnitude() : timeFrac * timeFrac * this.getMagnitude();
      Vec3 playerPos = player.m_20299_(delta);
      float distFrac = (float)(1.0 - Mth.m_14008_(this.m_20182_().m_82554_(playerPos) / (double)this.getRadius(), 0.0, 1.0));
      return baseAmount * distFrac * distFrac;
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.f_19797_ > this.getDuration() + this.getFadeDuration()) {
         this.m_146870_();
      }
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(RADIUS, 10.0F);
      this.f_19804_.m_135372_(MAGNITUDE, 1.0F);
      this.f_19804_.m_135372_(DURATION, 0);
      this.f_19804_.m_135372_(FADE_DURATION, 5);
   }

   public float getRadius() {
      return (Float)this.f_19804_.m_135370_(RADIUS);
   }

   public void setRadius(float radius) {
      this.f_19804_.m_135381_(RADIUS, radius);
   }

   public float getMagnitude() {
      return (Float)this.f_19804_.m_135370_(MAGNITUDE);
   }

   public void setMagnitude(float magnitude) {
      this.f_19804_.m_135381_(MAGNITUDE, magnitude);
   }

   public int getDuration() {
      return (Integer)this.f_19804_.m_135370_(DURATION);
   }

   public void setDuration(int duration) {
      this.f_19804_.m_135381_(DURATION, duration);
   }

   public int getFadeDuration() {
      return (Integer)this.f_19804_.m_135370_(FADE_DURATION);
   }

   public void setFadeDuration(int fadeDuration) {
      this.f_19804_.m_135381_(FADE_DURATION, fadeDuration);
   }

   protected void m_7378_(CompoundTag compound) {
      this.setRadius(compound.m_128457_("radius"));
      this.setMagnitude(compound.m_128457_("magnitude"));
      this.setDuration(compound.m_128451_("duration"));
      this.setFadeDuration(compound.m_128451_("fade_duration"));
      this.f_19797_ = compound.m_128451_("ticks_existed");
   }

   protected void m_7380_(CompoundTag compound) {
      compound.m_128350_("radius", this.getRadius());
      compound.m_128350_("magnitude", this.getMagnitude());
      compound.m_128405_("duration", this.getDuration());
      compound.m_128405_("fade_duration", this.getFadeDuration());
      compound.m_128405_("ticks_existed", this.f_19797_);
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public static void ScreenShake(Level world, Vec3 position, float radius, float magnitude, int duration, int fadeDuration) {
      if (!world.f_46443_) {
         ScreenShake_Entity ScreenShake = new ScreenShake_Entity(world, position, radius, magnitude, duration, fadeDuration);
         world.m_7967_(ScreenShake);
      }
   }
}
