package com.cerbon.bosses_of_mass_destruction.item.custom;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.Event;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.RandomUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.capability.util.BMDCapabilities;
import com.cerbon.bosses_of_mass_destruction.entity.BMDEntities;
import com.cerbon.bosses_of_mass_destruction.entity.custom.lich.LichUtils;
import com.cerbon.bosses_of_mass_destruction.item.BMDItems;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.particle.ClientParticleBuilder;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class SoulStarEntity extends Entity implements ItemSupplier {
   private static final EntityDataAccessor<ItemStack> ITEM = SynchedEntityData.m_135353_(SoulStarEntity.class, EntityDataSerializers.f_135033_);
   private final ClientParticleBuilder particleBuilder = new ClientParticleBuilder((ParticleOptions)BMDParticles.SPARKLES.get())
      .color(LichUtils.blueColorFade)
      .age(RandomUtils.range(80, 100))
      .colorVariation(0.3)
      .scale(f -> 0.05F - f * 0.025F)
      .brightness(15728880);
   private double targetX = 0.0;
   private double targetY = 0.0;
   private double targetZ = 0.0;

   public SoulStarEntity(EntityType<?> entityType, Level level) {
      super(entityType, level);
   }

   public SoulStarEntity(Level level, double x, double y, double z) {
      this((EntityType<?>)BMDEntities.SOUL_STAR.get(), level);
      this.m_20248_(x, y, z);
   }

   public void setItem(ItemStack stack) {
      if (stack.m_41720_() != Items.f_42545_ || stack.m_41782_()) {
         this.m_20088_().m_135381_(ITEM, (ItemStack)Util.m_137469_(stack.m_41777_(), stack1 -> stack1.m_41764_(1)));
      }
   }

   private ItemStack getTrackedItem() {
      return (ItemStack)this.m_20088_().m_135370_(ITEM);
   }

   @NotNull
   public ItemStack m_7846_() {
      ItemStack itemStack = this.getTrackedItem();
      return itemStack.m_41619_() ? new ItemStack((ItemLike)BMDItems.SOUL_STAR.get()) : itemStack;
   }

   protected void m_8097_() {
      this.m_20088_().m_135372_(ITEM, ItemStack.f_41583_);
   }

   @OnlyIn(Dist.CLIENT)
   public boolean m_6783_(double distance) {
      double d = this.m_20191_().m_82309_() * 4.0;
      if (Double.isNaN(d)) {
         d = 4.0;
      }

      d *= 64.0;
      return distance < d * d;
   }

   public void initTargetPos(BlockPos pos) {
      double d = (double)pos.m_123341_();
      double e = (double)pos.m_123343_();
      double f = d - this.m_20185_();
      double g = e - this.m_20189_();
      double h = Math.sqrt(f * f + g * g);
      this.targetX = this.m_20185_() + f / h * 12.0;
      this.targetZ = this.m_20189_() + g / h * 12.0;
      this.targetY = this.m_20186_() + 8.0;
   }

   @OnlyIn(Dist.CLIENT)
   public void m_6001_(double x, double y, double z) {
      this.m_20334_(x, y, z);
      if (this.f_19860_ == 0.0F && this.f_19859_ == 0.0F) {
         double f = Math.sqrt(x * x + z * z);
         this.m_146922_((float)(Mth.m_14136_(x, z) * 180.0F / (float)Math.PI));
         this.m_146926_((float)(Mth.m_14136_(y, f) * 180.0F / (float)Math.PI));
         this.f_19859_ = this.m_146908_();
         this.f_19860_ = this.m_146909_();
      }
   }

   public void m_8119_() {
      if (this.f_19853_.m_5776_() && this.f_19797_ == 1) {
         double rotationOffset = this.f_19796_.m_188500_() * 360.0;
         BMDCapabilities.getLevelEventScheduler(this.f_19853_).addEvent(new Event(() -> true, () -> this.spawnTrailParticles(rotationOffset), this::m_213877_));
      }

      super.m_8119_();
      Vec3 vec3 = this.m_20184_();
      double d = this.m_20185_() + vec3.f_82479_;
      double e = this.m_20186_() + vec3.f_82480_;
      double f = this.m_20189_() + vec3.f_82481_;
      double g = vec3.m_165924_();
      this.m_146926_(this.updateRotation(this.f_19860_, (float)(Mth.m_14136_(vec3.f_82480_, g) * 180.0F / (float)Math.PI)));
      this.m_146922_(this.updateRotation(this.f_19859_, (float)(Mth.m_14136_(vec3.f_82479_, vec3.f_82481_) * 180.0F / (float)Math.PI)));
      if (!this.f_19853_.m_5776_()) {
         double xd = this.targetX - d;
         double zd = this.targetZ - f;
         float distance = (float)Math.sqrt(xd * xd + zd * zd);
         float k = (float)Mth.m_14136_(zd, xd);
         double l = Mth.m_14139_(0.0025, g, (double)distance);
         double m = vec3.f_82480_;
         if (distance < 1.0F) {
            l *= 0.8;
            m *= 0.8;
            this.m_5496_((SoundEvent)BMDSounds.SOUL_STAR.get(), 1.0F, 1.0F);
            this.m_146870_();
            this.f_19853_.m_7967_(new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_7846_()));
         }

         int n = this.m_20186_() < this.targetY ? 1 : -1;
         vec3 = new Vec3(Math.cos((double)k) * l, m + ((double)n - m) * 0.015F, Math.sin((double)k) * l);
         this.m_20256_(vec3);
         this.m_20248_(d, e, f);
      } else {
         this.m_20343_(d, e, f);
      }

      this.spawnParticles(d, vec3, e, f);
   }

   private void spawnTrailParticles(double rotationOffset) {
      Vec3 look = this.m_20184_();
      Vec3 cross = look.m_82537_(VecUtils.yAxis).m_82541_();
      Vec3 rotatedOffset = VecUtils.rotateVector(cross, look, rotationOffset + (double)this.f_19797_ * 30.0).m_82490_(0.25);
      Vec3 particlePos = this.m_20182_().m_82549_(rotatedOffset);
      this.particleBuilder.build(particlePos, this.m_20184_().m_82490_(0.1));
   }

   private void spawnParticles(double d, Vec3 vec3, double e, double f) {
      if (this.m_20069_()) {
         for (int p = 0; p <= 3; p++) {
            this.f_19853_
               .m_7106_(
                  ParticleTypes.f_123795_,
                  d - vec3.f_82479_ * 0.25,
                  e - vec3.f_82480_ * 0.25,
                  f - vec3.f_82481_ * 0.25,
                  vec3.f_82479_,
                  vec3.f_82480_,
                  vec3.f_82481_
               );
         }
      }
   }

   protected void m_7380_(@NotNull CompoundTag compound) {
      ItemStack itemStack = this.getTrackedItem();
      if (!itemStack.m_41619_()) {
         compound.m_128365_("Item", itemStack.m_41739_(new CompoundTag()));
      }
   }

   protected void m_7378_(@NotNull CompoundTag compound) {
      ItemStack itemStack = ItemStack.m_41712_(compound.m_128469_("Item"));
      this.setItem(itemStack);
   }

   public float m_213856_() {
      return 1.0F;
   }

   public boolean m_6097_() {
      return false;
   }

   public Packet<?> m_5654_() {
      return new ClientboundAddEntityPacket(this);
   }

   private float updateRotation(float f, float g) {
      float f1 = f;

      while (g - f1 < -180.0F) {
         f1 -= 360.0F;
      }

      while (g - f1 >= 180.0F) {
         f1 += 360.0F;
      }

      return Mth.m_14179_(0.2F, f1, g);
   }
}
