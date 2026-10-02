package com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MobUtils;
import com.cerbon.bosses_of_mass_destruction.api.multipart_entities.entity.EntityBounds;
import com.cerbon.bosses_of_mass_destruction.api.multipart_entities.entity.EntityPart;
import com.cerbon.bosses_of_mass_destruction.api.multipart_entities.entity.MutableBox;
import com.cerbon.bosses_of_mass_destruction.entity.damage.IDamageHandler;
import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityStats;
import com.cerbon.bosses_of_mass_destruction.packet.BMDPacketHandler;
import com.cerbon.bosses_of_mass_destruction.packet.custom.ChangeHitboxS2CPacket;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;

public class GauntletHitboxes implements IDamageHandler {
   private final GauntletEntity entity;
   private final AABB collisionHitbox = new AABB(Vec3.f_82478_, new Vec3(2.0, 4.0, 2.0));
   private final AABB clampedCollisionHitbox = new AABB(Vec3.f_82478_, new Vec3(2.0, 2.0, 2.0));
   private final String rootBoxPitch = "rootPitch";
   private final String rootBoxYaw = "rootYaw";
   private String nextDamagedPart;
   private final String eyeBox = "eye";
   private final String fingersBox = "fingers";
   private final String thumbBox = "thumb";
   private final String pinkyBox = "pinky";
   private final EntityBounds hitboxes = EntityBounds.builder()
      .add("rootYaw")
      .setBounds(0.0, 0.0, 0.0)
      .build()
      .add("rootPitch")
      .setBounds(2.0, 2.6, 0.6)
      .setOffset(0.0, 1.3, 0.0)
      .setPivot(0.0, -0.2, 0.0)
      .setParent("rootYaw")
      .build()
      .add("eye")
      .setBounds(1.1, 1.2, 0.2)
      .setOffset(-0.025, 0.35, 0.4)
      .setParent("rootPitch")
      .build()
      .add("fingers")
      .setBounds(1.5, 2.0, 0.5)
      .setOffset(0.0, 1.8, 0.5)
      .setParent("rootPitch")
      .build()
      .add("thumb")
      .setBounds(0.3, 1.6, 0.3)
      .setOffset(1.0, 0.6, 0.7)
      .setParent("rootPitch")
      .build()
      .add("pinky")
      .setBounds(0.25, 1.0, 0.25)
      .setOffset(-0.9, 1.7, 0.5)
      .setParent("rootPitch")
      .build()
      .overrideCollisionBox(this.collisionHitbox)
      .getFactory()
      .create();
   private final String rootFistBox = "rootFist";
   private final String rootFistBoxYaw = "rootFistYaw";
   private final EntityBounds clampedHitboxes = EntityBounds.builder()
      .add("rootFistYaw")
      .setBounds(0.0, 0.0, 0.0)
      .build()
      .add("rootFist")
      .setBounds(2.0, 1.5, 2.0)
      .setOffset(0.0, 1.0, 0.0)
      .setParent("rootFistYaw")
      .build()
      .overrideCollisionBox(this.clampedCollisionHitbox)
      .getFactory()
      .create();
   private EntityBounds currentHitbox = this.hitboxes;
   private final boolean disableHitboxesForCompatibility = ModList.get().isLoaded("bettercombat") || ModList.get().isLoaded("epicfight");

   public GauntletHitboxes(GauntletEntity entity) {
      this.entity = entity;
      this.hitboxes.getPart("fingers").setRotation(35.0, 0.0, 0.0, true);
      this.hitboxes.getPart("thumb").setRotation(30.0, 0.0, 0.0, true);
      this.hitboxes.getPart("pinky").setRotation(35.0, 0.0, 0.0, true);
   }

   public void setOpenHandHitbox() {
      if (!this.entity.f_19853_.m_5776_() && this.currentHitbox != this.hitboxes) {
         BMDPacketHandler.sendToAllPlayersTrackingChunk(
            new ChangeHitboxS2CPacket(this.entity.m_19879_(), true), (ServerLevel)this.entity.f_19853_, this.entity.m_20182_()
         );
      }

      this.currentHitbox = this.hitboxes;
   }

   public void setClosedFistHitbox() {
      if (!this.entity.f_19853_.m_5776_() && this.currentHitbox != this.clampedHitboxes) {
         BMDPacketHandler.sendToAllPlayersTrackingChunk(
            new ChangeHitboxS2CPacket(this.entity.m_19879_(), false), (ServerLevel)this.entity.f_19853_, this.entity.m_20182_()
         );
      }

      this.currentHitbox = this.clampedHitboxes;
   }

   public EntityBounds getHitbox() {
      return this.currentHitbox;
   }

   public void updatePosition() {
      EntityPart rootPitch = this.hitboxes.getPart("rootPitch");
      EntityPart rootYaw = this.hitboxes.getPart("rootYaw");
      rootYaw.setRotation(0.0, (double)(-this.entity.m_146908_()), 0.0, true);
      rootPitch.setRotation((double)this.entity.m_146909_(), 0.0, 0.0, true);
      rootYaw.setX(this.entity.m_20185_());
      rootYaw.setY(this.entity.m_20186_());
      rootYaw.setZ(this.entity.m_20189_());
      EntityPart fistYaw = this.clampedHitboxes.getPart("rootFistYaw");
      EntityPart fist = this.clampedHitboxes.getPart("rootFist");
      fistYaw.setRotation(0.0, (double)(-this.entity.m_146908_()), 0.0, true);
      fist.setRotation((double)this.entity.m_146909_(), 0.0, 0.0, true);
      fistYaw.setX(this.entity.m_20185_());
      fistYaw.setY(this.entity.m_20186_());
      fistYaw.setZ(this.entity.m_20189_());
      MutableBox overrideBox = this.hitboxes.getOverrideBox();
      if (overrideBox != null) {
         overrideBox.setBox(this.collisionHitbox.m_82383_(this.entity.m_20182_()).m_82386_(-1.0, 0.0, -1.0));
      }

      MutableBox overrideClampedHitbox = this.clampedHitboxes.getOverrideBox();
      if (overrideClampedHitbox != null) {
         overrideClampedHitbox.setBox(this.clampedCollisionHitbox.m_82383_(this.entity.m_20182_()).m_82386_(-1.0, 0.0, -1.0));
      }
   }

   public void setNextDamagedPart(String part) {
      this.nextDamagedPart = part;
   }

   @Override
   public void beforeDamage(IEntityStats stats, DamageSource damageSource, float amount) {
   }

   @Override
   public void afterDamage(IEntityStats stats, DamageSource damageSource, float amount, boolean result) {
   }

   @Override
   public boolean shouldDamage(LivingEntity actor, DamageSource damageSource, float amount) {
      String part = this.nextDamagedPart;
      this.nextDamagedPart = null;
      if (this.disableHitboxesForCompatibility) {
         return true;
      } else if ((part == null || !part.equals("eye")) && !damageSource.m_19378_()) {
         if (damageSource.m_19372_()) {
            Vec3 pos = damageSource.m_7270_();
            if (pos != null) {
               Vec3 explosionDirection = MathUtils.unNormedDirection(pos, MobUtils.eyePos(this.entity));
               if (!MathUtils.facingSameDirection(explosionDirection, this.entity.m_20154_())) {
                  return true;
               }
            }
         }

         if (!damageSource.m_19360_() && damageSource.m_7639_() instanceof LivingEntity livingEntity) {
            livingEntity.m_147240_(0.5, actor.m_20185_() - livingEntity.m_20185_(), actor.m_20189_() - livingEntity.m_20189_());
         }

         if (!damageSource.m_19384_()) {
            this.entity.m_5496_((SoundEvent)BMDSounds.GAUNTLET_CLINK.get(), 1.0F, BMDUtils.randomPitch(actor.m_217043_()));
         }

         return false;
      } else {
         return true;
      }
   }
}
