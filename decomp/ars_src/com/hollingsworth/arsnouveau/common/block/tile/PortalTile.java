package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.client.ITooltipProvider;
import com.hollingsworth.arsnouveau.api.util.NBTUtil;
import com.hollingsworth.arsnouveau.common.block.ITickable;
import com.hollingsworth.arsnouveau.common.block.PortalBlock;
import com.hollingsworth.arsnouveau.common.entity.EntityFollowProjectile;
import com.hollingsworth.arsnouveau.common.items.WarpScroll;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketWarpPosition;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundMoveVehiclePacket;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.ITeleporter;

public class PortalTile extends ModdedTile implements ITickable, ITooltipProvider {
   public BlockPos warpPos;
   public String dimID;
   public Vec2 rotationVec;
   public String displayName;
   public boolean isHorizontal;
   public Set<Entity> entityQueue = new HashSet<>();

   public PortalTile(BlockPos pos, BlockState state) {
      super(BlockRegistry.PORTAL_TILE_TYPE, pos, state);
   }

   public void warp(Entity e) {
      if (this.f_58857_ instanceof ServerLevel serverLevel
         && this.warpPos != null
         && this.dimID != null
         && teleportEntityTo(e, getServerLevel(this.dimID, serverLevel), this.warpPos, this.rotationVec) != null) {
         ServerLevel serverWorld = getServerLevel(this.dimID, serverLevel);
         if (serverWorld == null) {
            return;
         }

         Networking.sendToNearby(
            serverWorld, e, new PacketWarpPosition(e.m_19879_(), e.m_20185_() + 0.5, e.m_20186_(), e.m_20189_() + 0.5, e.m_146909_(), e.m_146908_())
         );
         serverLevel.m_8767_(
            ParticleTypes.f_123760_,
            (double)this.warpPos.m_123341_(),
            (double)(this.warpPos.m_123342_() + 1),
            (double)this.warpPos.m_123343_(),
            4,
            (serverWorld.f_46441_.m_188500_() - 0.5) * 2.0,
            -serverWorld.f_46441_.m_188500_(),
            (serverWorld.f_46441_.m_188500_() - 0.5) * 2.0,
            0.1F
         );
      }
   }

   public void setFromScroll(WarpScroll.WarpScrollData scrollData) {
      this.warpPos = scrollData.getPos();
      this.dimID = scrollData.getDimension();
      this.rotationVec = scrollData.getRotation();
   }

   public void m_142466_(CompoundTag compound) {
      super.m_142466_(compound);
      this.dimID = compound.m_128461_("dim");
      this.warpPos = NBTUtil.getBlockPos(compound, "warp");
      this.rotationVec = new Vec2(compound.m_128457_("xRot"), compound.m_128457_("yRot"));
      this.displayName = compound.m_128461_("display");
      this.isHorizontal = compound.m_128471_("horizontal");
   }

   @Override
   public void m_183515_(CompoundTag compound) {
      if (this.warpPos != null) {
         NBTUtil.storeBlockPos(compound, "warp", this.warpPos);
      }

      if (this.dimID != null) {
         compound.m_128359_("dim", this.dimID);
      }

      if (this.rotationVec != null) {
         compound.m_128350_("xRot", this.rotationVec.f_82470_);
         compound.m_128350_("yRot", this.rotationVec.f_82471_);
      }

      if (this.displayName != null) {
         compound.m_128359_("display", this.displayName);
      }

      compound.m_128379_("horizontal", this.isHorizontal);
   }

   @Override
   public void tick() {
      if (this.f_58857_ != null && this.f_58857_ instanceof ServerLevel serverLevel && this.warpPos != null) {
         Set<Entity> entities = this.entityQueue;
         if (!entities.isEmpty()) {
            for (Entity e : entities) {
               if (!(e instanceof EntityFollowProjectile)
                  && this.dimID != null
                  && teleportEntityTo(e, getServerLevel(this.dimID, serverLevel), this.warpPos, this.rotationVec) != null) {
                  this.f_58857_.m_5594_(null, this.warpPos, SoundEvents.f_12052_, SoundSource.NEUTRAL, 1.0F, 1.0F);
                  serverLevel.m_8767_(
                     ParticleTypes.f_123760_,
                     (double)this.warpPos.m_123341_(),
                     (double)(this.warpPos.m_123342_() + 1),
                     (double)this.warpPos.m_123343_(),
                     4,
                     (this.f_58857_.f_46441_.m_188500_() - 0.5) * 2.0,
                     -this.f_58857_.f_46441_.m_188500_(),
                     (this.f_58857_.f_46441_.m_188500_() - 0.5) * 2.0,
                     0.1F
                  );
               }
            }

            this.entityQueue.clear();
         }
      }
   }

   @Nullable
   public static ServerLevel getServerLevel(String dimID, ServerLevel level) {
      if (dimID != null && level != null) {
         ResourceKey<Level> resourcekey = ResourceKey.m_135785_(Registry.f_122819_, new ResourceLocation(dimID));
         return level.m_7654_().m_129880_(resourcekey);
      } else {
         return null;
      }
   }

   @Nullable
   public static Entity teleportEntityTo(Entity entity, @Nullable Level targetWorld, BlockPos target, final Vec2 rotationVec) {
      if (targetWorld == null) {
         return entity;
      } else if (entity.m_20193_().m_46472_() == targetWorld.m_46472_()) {
         if (targetWorld.m_8055_(target).m_60734_() instanceof PortalBlock) {
            return entity;
         } else {
            entity.m_6021_((double)target.m_123341_() + 0.5, (double)target.m_123342_(), (double)target.m_123343_() + 0.5);
            entity.m_146926_(rotationVec != null ? rotationVec.f_82470_ : entity.m_146909_());
            entity.m_146922_(rotationVec != null ? rotationVec.f_82471_ : entity.m_146908_());
            if (!entity.m_20197_().isEmpty()) {
               ((ServerChunkCache)entity.m_20193_().m_7726_()).m_8445_(entity, new ClientboundSetPassengersPacket(entity));
               Entity controller = entity.m_6688_();
               if (controller != entity && controller instanceof ServerPlayer player && !(controller instanceof FakePlayer) && player.f_8906_ != null) {
                  player.f_8906_.m_9829_(new ClientboundMoveVehiclePacket(entity));
               }
            }

            return entity;
         }
      } else {
         final Vec3 destination = new Vec3((double)target.m_123341_() + 0.5, (double)target.m_123342_(), (double)target.m_123343_() + 0.5);
         final List<Entity> passengers = entity.m_20197_();
         return entity.changeDimension((ServerLevel)targetWorld, new ITeleporter() {
            public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
               Entity repositionedEntity = repositionEntity.apply(false);
               if (repositionedEntity != null) {
                  for (Entity passenger : passengers) {
                     PortalTile.teleportPassenger(destWorld, destination, repositionedEntity, passenger);
                  }
               }

               return repositionedEntity;
            }

            public PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
               return new PortalInfo(destination, entity.m_20184_(), rotationVec.f_82471_, rotationVec.f_82470_);
            }

            public boolean playTeleportSound(ServerPlayer player, ServerLevel sourceWorld, ServerLevel destWorld) {
               return false;
            }
         });
      }
   }

   private static void teleportPassenger(ServerLevel destWorld, final Vec3 destination, final Entity repositionedEntity, Entity passenger) {
      if (passenger.m_6072_()) {
         final List<Entity> passengers = passenger.m_20197_();
         passenger.changeDimension(destWorld, new ITeleporter() {
            public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
               boolean invulnerable = entity.m_20147_();
               entity.m_20331_(true);
               Entity repositionedPassenger = repositionEntity.apply(false);
               if (repositionedPassenger != null) {
                  repositionedPassenger.m_7998_(repositionedEntity, true);

                  for (Entity passenger : passengers) {
                     PortalTile.teleportPassenger(destWorld, destination, repositionedPassenger, passenger);
                  }

                  repositionedPassenger.m_20331_(invulnerable);
               }

               entity.m_20331_(invulnerable);
               return repositionedPassenger;
            }

            public PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
               return new PortalInfo(destination, entity.m_20184_(), entity.m_146908_(), entity.m_146909_());
            }

            public boolean playTeleportSound(ServerPlayer player, ServerLevel sourceWorld, ServerLevel destWorld) {
               return false;
            }
         });
      }
   }

   @Override
   public void getTooltip(List<Component> tooltip) {
      if (this.displayName != null) {
         tooltip.add(Component.m_237113_(this.displayName));
      }
   }
}
