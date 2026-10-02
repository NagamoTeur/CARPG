package com.cerbon.bosses_of_mass_destruction.item.custom;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.RandomUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.entity.BMDEntities;
import com.cerbon.bosses_of_mass_destruction.item.BMDItems;
import com.cerbon.bosses_of_mass_destruction.packet.BMDPacketHandler;
import com.cerbon.bosses_of_mass_destruction.packet.custom.ChargedEnderPearlS2CPacket;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.particle.ClientParticleBuilder;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.util.BMDColors;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChargedEnderPearlEntity extends ThrowableItemProjectile {
   private static final double radius = 3.0;
   private static final double impactHeight = 3.0;
   private static final ClientParticleBuilder verticalRodParticle = new ClientParticleBuilder((ParticleOptions)BMDParticles.ROD.get())
      .brightness(15728880)
      .color((Function<Float, Vec3>)(age -> MathUtils.lerpVec(age, BMDColors.LIGHT_ENDER_PEARL, BMDColors.DARK_ENDER_PEARL)))
      .colorVariation(0.25);
   private static final ClientParticleBuilder enderParticle = new ClientParticleBuilder((ParticleOptions)BMDParticles.FLUFF.get())
      .brightness(15728880)
      .color(BMDColors.ENDER_PURPLE)
      .colorVariation(0.25);

   public ChargedEnderPearlEntity(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
      super(entityType, level);
   }

   protected ChargedEnderPearlEntity(Level level, LivingEntity owner) {
      super((EntityType)BMDEntities.CHARGED_ENDER_PEARL.get(), owner, level);
   }

   @NotNull
   protected Item m_7881_() {
      return (Item)BMDItems.CHARGED_ENDER_PEARL.get();
   }

   protected void m_5790_(@NotNull EntityHitResult result) {
      super.m_5790_(result);
      result.m_82443_().m_6469_(DamageSource.m_19361_(this, this.m_37282_()), 0.0F);
   }

   protected void m_6532_(@NotNull HitResult result) {
      super.m_6532_(result);
      if (!this.f_19853_.m_5776_() && !this.m_213877_()) {
         this.serverCollision();
      }
   }

   private void serverCollision() {
      this.teleportEntity(this.m_37282_());
      this.applyMobEffects(this.m_37282_());
      BMDPacketHandler.sendToAllPlayersTrackingChunk(new ChargedEnderPearlS2CPacket(this.m_20182_()), (ServerLevel)this.f_19853_, this.m_20182_());
      this.m_5496_((SoundEvent)BMDSounds.CHARGED_ENDER_PEARL.get(), 1.0F, BMDUtils.randomPitch(this.f_19796_) * 0.8F);
      this.m_146870_();
   }

   private void teleportEntity(Entity entity) {
      if (entity instanceof ServerPlayer player) {
         if (player.f_8906_.m_6198_().m_129536_() && player.f_19853_ == this.f_19853_ && !player.m_5803_()) {
            if (player.m_20159_()) {
               player.m_142098_(this.m_20185_(), this.m_20186_(), this.m_20189_());
            } else {
               player.m_6021_(this.m_20185_(), this.m_20186_(), this.m_20189_());
            }

            player.m_183634_();
         }
      } else if (entity != null) {
         entity.m_6021_(this.m_20185_(), this.m_20186_(), this.m_20189_());
         entity.m_183634_();
      }
   }

   private void applyMobEffects(Entity entity) {
      if (entity instanceof LivingEntity livingEntity) {
         livingEntity.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 120, 1));
         livingEntity.m_7292_(new MobEffectInstance(MobEffects.f_19591_, 20, 0));
      }

      for (LivingEntity entity1 : this.f_19853_
         .m_6443_(
            LivingEntity.class,
            new AABB(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_20185_(), this.m_20186_(), this.m_20189_()).m_82377_(6.0, 6.0, 6.0),
            this::isInXzAndYDistance
         )) {
         Vec3 direction = this.m_20182_().m_82546_(entity1.m_20182_());
         entity1.m_147240_(0.4F, direction.m_7096_(), direction.m_7094_());
      }
   }

   private boolean isInXzAndYDistance(LivingEntity entity) {
      return this.isInXzDistance(entity) && this.isInYDistance(entity);
   }

   private boolean isInXzDistance(LivingEntity entity) {
      Vec3 xzLineToEntity = VecUtils.planeProject(entity.m_20182_(), VecUtils.yAxis).m_82546_(VecUtils.planeProject(this.m_20182_(), VecUtils.yAxis));
      double sqXzDistanceTowardEntity = xzLineToEntity.m_82556_();
      boolean entityCenterInRadius = sqXzDistanceTowardEntity < 9.0;
      if (entityCenterInRadius) {
         return true;
      } else {
         Vec3 xzDirectionTowardEntity = xzLineToEntity.m_82541_();
         Vec3 xzPointTowardEntity = xzDirectionTowardEntity.m_82490_(3.0).m_82549_(this.m_20182_());
         return entity.m_20191_().m_82377_(0.0, 10.0, 0.0).m_82390_(xzPointTowardEntity);
      }
   }

   private boolean isInYDistance(LivingEntity entity) {
      double yBottom = this.m_20182_().m_7098_() - 1.0;
      double yTop = this.m_20182_().m_7098_() + 3.0;
      return entity.m_20191_().f_82292_ > yBottom || entity.m_20191_().f_82289_ < yTop;
   }

   public void m_8119_() {
      if (this.m_37282_() instanceof Player player && !player.m_6084_()) {
         this.m_146870_();
         return;
      }

      super.m_8119_();
   }

   @Nullable
   public Entity m_5489_(@NotNull ServerLevel destination) {
      Entity entity = this.m_37282_();
      if (entity != null && entity.f_19853_.m_46472_() != destination.m_46472_()) {
         this.m_5602_(null);
      }

      return super.m_5489_(destination);
   }

   public Packet<?> m_5654_() {
      return super.m_5654_();
   }

   public static void handlePearlImpact(Vec3 pos) {
      spawnTeleportParticles(pos);
      spawnVerticalRods(pos);
   }

   private static void spawnTeleportParticles(Vec3 pos) {
      for (int i = 0; i <= 10; i++) {
         int startingRotation = new Random().nextInt(360);
         double randomRadius = RandomUtils.range(2.0, 3.0);
         double rotationSpeed = RandomUtils.range(3.0, 4.0);
         int finalI = i;
         enderParticle.continuousPosition(particle -> rotateAroundPos(pos, finalI, particle.getAge(), startingRotation, randomRadius, rotationSpeed))
            .build(rotateAroundPos(pos, i, 0, startingRotation, randomRadius, rotationSpeed), Vec3.f_82478_);
      }
   }

   private static Vec3 rotateAroundPos(Vec3 pos, int i, int age, int startingRotation, double radius, double rotationSpeed) {
      Vec3 yOffset = VecUtils.yAxis.m_82490_((double)i / 15.0);
      Vec3 xzOffset = VecUtils.xAxis.m_82524_((float)Math.toRadians((double)age * rotationSpeed + (double)startingRotation));
      return pos.m_82549_(yOffset).m_82549_(xzOffset.m_82490_(radius));
   }

   private static void spawnVerticalRods(Vec3 pos) {
      for (int y = 0; y <= 3; y++) {
         spawnVerticalRodRing(y, pos);
      }
   }

   private static void spawnVerticalRodRing(int y, Vec3 pos) {
      MathUtils.circleCallback(
         3.0,
         30 - y * 3,
         VecUtils.yAxis,
         vec3 -> verticalRodParticle.build(
               pos.m_82549_(vec3).m_82549_(VecUtils.yAxis.m_82490_((double)y + RandomUtils.range(0.0, 1.0))), VecUtils.yAxis.m_82490_(0.1)
            )
      );
   }
}
