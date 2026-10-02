package io.redspace.ironsspellbooks.entity.spells.portal;

import com.mojang.math.Vector3f;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.capabilities.magic.PortalManager;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import io.redspace.ironsspellbooks.util.ModTags;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PortalEntity extends Entity implements AntiMagicSusceptible {
   private static final EntityDataAccessor<Optional<UUID>> DATA_ID_OWNER_UUID = SynchedEntityData.m_135353_(PortalEntity.class, EntityDataSerializers.f_135041_);
   private static final EntityDataAccessor<Boolean> DATA_PORTAL_CONNECTED = SynchedEntityData.m_135353_(PortalEntity.class, EntityDataSerializers.f_135035_);
   private long ticksToLive = 0L;
   private boolean isPortalConnected = false;

   public PortalEntity(Level level, PortalData portalData) {
      this((EntityType<PortalEntity>)EntityRegistry.PORTAL.get(), level);
      PortalManager.INSTANCE.addPortalData(this.f_19820_, portalData);
      this.ticksToLive = (long)portalData.ticksToLive;
   }

   public PortalEntity(EntityType<PortalEntity> portalEntityEntityType, Level level) {
      super(portalEntityEntityType, level);
   }

   @Override
   public void onAntiMagic(MagicData magicData) {
      if (!this.f_19853_.f_46443_) {
         this.m_146870_();
      }
   }

   public void onRemovedFromWorld() {
      if (!this.f_19853_.f_46443_) {
         RemovalReason removalReason = this.m_146911_();
         if (removalReason != null && removalReason.m_146965_()) {
            PortalManager.INSTANCE.killPortal(this.f_19820_, this.getOwnerUUID());
         }

         MagicManager.spawnParticles(
            this.f_19853_,
            new DustParticleOptions(new Vector3f(0.5F, 0.05F, 0.6F), 1.5F),
            this.m_20185_(),
            this.m_20186_(),
            this.m_20189_(),
            25,
            0.4,
            0.8,
            0.4,
            0.03,
            false
         );
         super.onRemovedFromWorld();
      }
   }

   public void checkForEntitiesToTeleport() {
      if (!this.f_19853_.f_46443_) {
         this.f_19853_
            .m_6249_(
               (Entity)null,
               this.m_20191_(),
               entity -> !entity.m_6095_().m_204039_(ModTags.CANT_USE_PORTAL)
                     && (entity.m_6087_() || entity instanceof Projectile)
                     && !entity.m_20160_()
                     && !entity.m_5833_()
            )
            .forEach(
               entity -> {
                  PortalManager.INSTANCE.processDelayCooldown(this.f_19820_, entity.m_20148_(), 1);
                  if (PortalManager.INSTANCE.canUsePortal(this, entity)) {
                     PortalManager.INSTANCE.addPortalCooldown(entity, this.f_19820_);
                     PortalData portalData = PortalManager.INSTANCE.getPortalData(this);
                     portalData.getConnectedPortalPos(this.f_19820_)
                        .ifPresent(
                           portalPos -> {
                              Vec3 destination = portalPos.pos().m_82520_(0.0, entity.m_20186_() - this.m_20186_(), 0.0);
                              entity.m_146922_(portalPos.rotation());
                              this.f_19853_.m_5594_(null, this.m_20183_(), SoundEvents.f_11852_, SoundSource.NEUTRAL, 1.0F, 1.0F);
                              if (this.f_19853_.m_46472_().equals(portalPos.dimension())) {
                                 entity.m_6021_(destination.f_82479_, destination.f_82480_ + 0.1, destination.f_82481_);
                                 Vec3 delta = entity.m_20184_();
                                 float hspeed = (float)Math.sqrt(delta.f_82479_ * delta.f_82479_ + delta.f_82481_ * delta.f_82481_);
                                 float f = portalPos.rotation() * (float) (Math.PI / 180.0);
                                 entity.m_20334_((double)(-Mth.m_14031_(f) * hspeed), delta.f_82480_, (double)(Mth.m_14089_(f) * hspeed));
                              } else {
                                 MinecraftServer server = this.f_19853_.m_7654_();
                                 if (server != null) {
                                    ServerLevel dim = server.m_129880_(portalPos.dimension());
                                    if (dim != null) {
                                       entity.changeDimension(dim, new PortalTeleporter(destination));
                                    }
                                 }
                              }

                              this.f_19853_
                                 .m_6263_(
                                    null,
                                    destination.f_82479_,
                                    destination.f_82480_,
                                    destination.f_82481_,
                                    SoundEvents.f_11852_,
                                    SoundSource.NEUTRAL,
                                    1.0F,
                                    1.0F
                                 );
                           }
                        );
                  }
               }
            );
      }
   }

   private Vec3 getDestinationPosition(PortalPos globalPos, Entity entity) {
      Vec3 offset = new Vec3(this.m_20185_() - entity.m_20185_(), this.m_20186_() - entity.m_20186_(), this.m_20189_() - entity.m_20189_());
      return new Vec3(globalPos.pos().f_82479_ - offset.f_82479_, globalPos.pos().f_82480_ - offset.f_82480_, globalPos.pos().f_82481_ - offset.f_82481_);
   }

   public void setTicksToLive(int ticksToLive) {
      this.ticksToLive = (long)ticksToLive;
   }

   public void m_8119_() {
      if (!this.f_19853_.f_46443_) {
         PortalManager.INSTANCE.processCooldownTick(this.f_19820_, -1);
         this.checkForEntitiesToTeleport();
         if (--this.ticksToLive <= 0L) {
            this.m_146870_();
         }
      } else {
         Vec3 center = this.m_20191_().m_82399_();

         for (int i = 0; i < 2; i++) {
            this.f_19853_.m_7106_(ParticleHelper.PORTAL_FRAME, center.f_82479_, center.f_82480_, center.f_82481_, 1.0, 2.1F, (double)this.m_146908_());
         }
      }
   }

   public void spawnParticles(float radius, Vector3f color) {
      int particles = 40;
      float step = 6.28F / (float)particles;
      Vec3 center = this.m_20191_().m_82399_();

      for (int i = 0; i < particles; i++) {
         float x = Mth.m_14089_((float)i * step) * radius;
         float y = Mth.m_14031_((float)i * step) * radius * 2.0F;
         Vec3 offset = new Vec3((double)x, (double)y, 0.0).m_82524_(-this.m_146908_() * (float) (Math.PI / 180.0));
         this.f_19853_
            .m_6493_(
               ParticleHelper.UNSTABLE_ENDER,
               true,
               center.f_82479_ + offset.f_82479_,
               center.f_82480_ + offset.f_82480_,
               center.f_82481_ + offset.f_82481_,
               0.0,
               0.0,
               0.0
            );
      }
   }

   public void setOwnerUUID(@Nullable UUID uuid) {
      this.f_19804_.m_135381_(DATA_ID_OWNER_UUID, Optional.ofNullable(uuid));
   }

   public UUID getOwnerUUID() {
      return ((Optional)this.f_19804_.m_135370_(DATA_ID_OWNER_UUID))
         .orElseGet(() -> (UUID)((Optional)this.f_19804_.m_135370_(DATA_ID_OWNER_UUID)).orElse(null));
   }

   public void setPortalConnected() {
      this.f_19804_.m_135381_(DATA_PORTAL_CONNECTED, true);
   }

   public boolean getPortalConnected() {
      return (Boolean)this.f_19804_.m_135370_(DATA_PORTAL_CONNECTED);
   }

   @NotNull
   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(DATA_ID_OWNER_UUID, Optional.empty());
      this.f_19804_.m_135372_(DATA_PORTAL_CONNECTED, false);
   }

   public void m_7350_(EntityDataAccessor<?> pKey) {
      super.m_7350_(pKey);
      if (this.f_19853_.f_46443_) {
         if (pKey.m_135015_() == DATA_PORTAL_CONNECTED.m_135015_()) {
            this.isPortalConnected = this.getPortalConnected();
         }
      }
   }

   protected void m_7378_(CompoundTag compoundTag) {
      if (compoundTag.m_128441_("ownerUUID")) {
         this.setOwnerUUID(compoundTag.m_128342_("ownerUUID"));
      }

      if (compoundTag.m_128441_("ticksToLive")) {
         this.ticksToLive = compoundTag.m_128454_("ticksToLive");
      }

      PortalData portalData = PortalManager.INSTANCE.getPortalData(this);
      if (portalData == null) {
         this.ticksToLive = 0L;
      } else if (portalData.portalEntityId1 != null && portalData.portalEntityId2 != null) {
         this.setPortalConnected();
      }
   }

   protected void m_7380_(CompoundTag compoundTag) {
      compoundTag.m_128356_("ticksToLive", this.ticksToLive);
      compoundTag.m_128362_("ownerUUID", this.getOwnerUUID());
   }
}
