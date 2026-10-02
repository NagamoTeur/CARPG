package com.aizistral.enigmaticlegacy.entities;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.handlers.SoulArchive;
import com.aizistral.enigmaticlegacy.helpers.ItemNBTHelper;
import com.aizistral.enigmaticlegacy.items.SoulCrystal;
import com.aizistral.enigmaticlegacy.items.StorageCrystal;
import com.aizistral.enigmaticlegacy.packets.clients.PacketHandleItemPickup;
import com.aizistral.enigmaticlegacy.packets.clients.PacketRecallParticles;
import com.aizistral.enigmaticlegacy.registries.EnigmaticEntities;
import com.aizistral.enigmaticlegacy.registries.EnigmaticItems;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity.MovementEmission;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PacketDistributor.TargetPoint;

public class PermanentItemEntity extends Entity {
   private static final EntityDataAccessor<ItemStack> ITEM = SynchedEntityData.m_135353_(PermanentItemEntity.class, EntityDataSerializers.f_135033_);
   private int age;
   private int pickupDelay;
   private int health = 5;
   private UUID thrower;
   private UUID owner;
   private Vec3 position;
   public float hoverStart = (float)(Math.random() * Math.PI * 2.0);

   public PermanentItemEntity(EntityType<PermanentItemEntity> type, Level world) {
      super(type, world);
   }

   public PermanentItemEntity(Level world, double x, double y, double z) {
      this(EnigmaticEntities.PERMANENT_ITEM_ENTITY, world);
      y = y <= (double)world.m_141937_() ? (double)(world.m_141937_() + 8) : y;
      this.m_6034_(x, y, z);
      this.m_146922_(this.f_19796_.m_188501_() * 360.0F);
      this.m_20331_(true);
      this.m_20242_(true);
      this.position = new Vec3(x, y, z);
   }

   public PermanentItemEntity(Level worldIn, double x, double y, double z, ItemStack stack) {
      this(worldIn, x, y, z);
      this.setItem(stack);
   }

   @OnlyIn(Dist.CLIENT)
   private PermanentItemEntity(PermanentItemEntity p_i231561_1_) {
      super(p_i231561_1_.m_6095_(), p_i231561_1_.f_19853_);
      this.setItem(p_i231561_1_.getItem().m_41777_());
      this.m_20359_(p_i231561_1_);
      this.age = p_i231561_1_.age;
      this.hoverStart = p_i231561_1_.hoverStart;
   }

   @OnlyIn(Dist.CLIENT)
   public PermanentItemEntity copy() {
      return new PermanentItemEntity(this);
   }

   public boolean m_213854_() {
      return this.getItem().m_204117_(ItemTags.f_215865_);
   }

   protected MovementEmission m_142319_() {
      return MovementEmission.NONE;
   }

   protected void m_8097_() {
      this.m_20088_().m_135372_(ITEM, ItemStack.f_41583_);
   }

   public void m_8119_() {
      if (this.getItem().m_41619_()) {
         this.m_146870_();
      } else {
         if (!this.f_19853_.f_46443_ && this.position != null && !this.m_20182_().equals(this.position)) {
            this.m_6021_(this.position.f_82479_, this.position.f_82480_, this.position.f_82481_);
         }

         super.m_8119_();
         if (this.pickupDelay > 0 && this.pickupDelay != 32767) {
            this.pickupDelay--;
         }

         this.f_19854_ = this.m_20185_();
         this.f_19855_ = this.m_20186_();
         this.f_19856_ = this.m_20189_();
         Vec3 vec3d = this.m_20184_();
         if (!this.m_20068_()) {
            this.m_20256_(this.m_20184_().m_82520_(0.0, -0.04, 0.0));
         }

         if (this.f_19853_.f_46443_) {
            this.f_19794_ = false;
            this.f_19853_
               .m_7106_(
                  ParticleTypes.f_123760_,
                  this.m_20185_(),
                  this.m_20186_() + (double)(this.m_20206_() / 2.0F),
                  this.m_20189_(),
                  (Math.random() - 0.5) * 2.0,
                  (Math.random() - 0.5) * 2.0,
                  (Math.random() - 0.5) * 2.0
               );
         }

         this.age++;
         if (!this.f_19853_.f_46443_) {
            double d0 = this.m_20184_().m_82546_(vec3d).m_82556_();
            if (d0 > 0.01) {
               this.f_19812_ = true;
            }
         }

         ItemStack item = this.getItem();
         if (item.m_41619_()) {
            this.m_146870_();
         }

         this.m_20091_();
      }
   }

   public int m_6045_() {
      return 32767;
   }

   public Entity changeDimension(ServerLevel server, ITeleporter teleporter) {
      return null;
   }

   public boolean m_6072_() {
      return false;
   }

   public boolean m_6469_(DamageSource source, float amount) {
      if (this.f_19853_.f_46443_ || !this.m_6084_()) {
         return false;
      } else if (source.m_19379_()) {
         EnigmaticLegacy.LOGGER.warn("[WARN] Attacked permanent item entity with absolute DamageSource: " + source);
         this.m_6074_();
         return true;
      } else {
         return false;
      }
   }

   public void m_142687_(RemovalReason reason) {
      if (reason == RemovalReason.DISCARDED || reason == RemovalReason.KILLED) {
         EnigmaticLegacy.LOGGER.warn("[WARN] Removing Permanent Item Entity: " + this);
         if (!this.f_19853_.f_46443_) {
            SoulArchive.getInstance().removeItem(this);
         }
      }

      super.m_142687_(reason);
   }

   public void m_7380_(CompoundTag compound) {
      compound.m_128376_("Health", (short)this.health);
      compound.m_128376_("Age", (short)this.age);
      compound.m_128376_("PickupDelay", (short)this.pickupDelay);
      if (this.getThrowerId() != null) {
         compound.m_128362_("Thrower", this.getThrowerId());
      }

      if (this.getOwnerId() != null) {
         compound.m_128362_("Owner", this.getOwnerId());
      }

      if (!this.getItem().m_41619_()) {
         compound.m_128365_("Item", this.getItem().m_41739_(new CompoundTag()));
      }

      if (this.position != null) {
         compound.m_128347_("BoundX", this.position.f_82479_);
         compound.m_128347_("BoundY", this.position.f_82480_);
         compound.m_128347_("BoundZ", this.position.f_82481_);
      }
   }

   public void m_7378_(CompoundTag compound) {
      this.health = compound.m_128448_("Health");
      this.age = compound.m_128448_("Age");
      if (compound.m_128441_("PickupDelay")) {
         this.pickupDelay = compound.m_128448_("PickupDelay");
      }

      if (compound.m_128441_("Owner")) {
         this.owner = compound.m_128342_("Owner");
      }

      if (compound.m_128441_("Thrower")) {
         this.thrower = compound.m_128342_("Thrower");
      }

      if (compound.m_128441_("BoundX") && compound.m_128441_("BoundY") && compound.m_128441_("BoundZ")) {
         double x = compound.m_128459_("BoundX");
         double y = compound.m_128459_("BoundY");
         double z = compound.m_128459_("BoundZ");
         this.position = new Vec3(x, y, z);
      }

      CompoundTag compoundnbt = compound.m_128469_("Item");
      this.setItem(ItemStack.m_41712_(compoundnbt));
      if (this.getItem().m_41619_()) {
         this.m_146870_();
      }
   }

   public void m_6123_(Player player) {
      if (!this.f_19853_.f_46443_) {
         if (this.pickupDelay > 0) {
            return;
         }

         ItemStack itemstack = this.getItem();
         Item item = itemstack.m_41720_();
         int i = itemstack.m_41613_();
         ItemStack copy = itemstack.m_41777_();
         boolean isPlayerOwner = player.m_20148_().equals(this.getOwnerId());
         boolean allowPickUp = false;
         if (!(item instanceof StorageCrystal) || !isPlayerOwner && EnigmaticItems.ENIGMATIC_AMULET.isVesselOwnerOnly()) {
            if (item instanceof SoulCrystal && isPlayerOwner) {
               allowPickUp = true;
            }
         } else {
            allowPickUp = true;
         }

         if (allowPickUp) {
            if (item instanceof StorageCrystal) {
               CompoundTag crystalNBT = ItemNBTHelper.getNBT(itemstack);
               ItemStack embeddedSoul = crystalNBT.m_128441_("embeddedSoul") ? ItemStack.m_41712_(crystalNBT.m_128469_("embeddedSoul")) : null;
               if (!isPlayerOwner && embeddedSoul != null) {
                  return;
               }

               EnigmaticItems.STORAGE_CRYSTAL.retrieveDropsFromCrystal(itemstack, player, embeddedSoul);
               SoulArchive.getInstance().removeItem(this);
            } else if (item instanceof SoulCrystal) {
               if (!EnigmaticItems.SOUL_CRYSTAL.retrieveSoulFromCrystal(player, itemstack)) {
                  return;
               }

               SoulArchive.getInstance().removeItem(this);
            }

            EnigmaticLegacy.packetInstance
               .send(
                  PacketDistributor.NEAR
                     .with(
                        () -> new TargetPoint(
                              this.m_20185_(), this.m_20186_() + (double)(this.m_20206_() / 2.0F), this.m_20189_(), 64.0, player.f_19853_.m_46472_()
                           )
                     ),
                  new PacketRecallParticles(this.m_20185_(), this.m_20186_() + (double)(this.m_20206_() / 2.0F), this.m_20189_(), 48, false)
               );
            player.m_7938_(this, i);
            EnigmaticLegacy.packetInstance
               .send(
                  PacketDistributor.NEAR.with(() -> new TargetPoint(this.m_20185_(), this.m_20186_(), this.m_20189_(), 64.0, this.f_19853_.m_46472_())),
                  new PacketHandleItemPickup(player.m_19879_(), this.m_19879_())
               );
            EnigmaticLegacy.LOGGER.info("Player " + player.m_36316_().getName() + " picking up: " + this);
            this.m_146870_();
            itemstack.m_41764_(0);
         } else if (this.pickupDelay == 0 && (this.owner == null || this.owner.equals(player.m_20148_())) && (i <= 0 || player.m_150109_().m_36054_(itemstack))
            )
          {
            copy.m_41764_(copy.m_41613_() - this.getItem().m_41613_());
            if (itemstack.m_41619_()) {
               player.m_7938_(this, i);
               EnigmaticLegacy.packetInstance
                  .send(
                     PacketDistributor.NEAR.with(() -> new TargetPoint(this.m_20185_(), this.m_20186_(), this.m_20189_(), 64.0, this.f_19853_.m_46472_())),
                     new PacketHandleItemPickup(player.m_19879_(), this.m_19879_())
                  );
               EnigmaticLegacy.LOGGER.info("Player " + player.m_36316_().getName() + " picking up: " + this);
               this.m_146870_();
               itemstack.m_41764_(i);
            }

            player.m_6278_(Stats.f_12984_.m_12902_(item), i);
         }
      }
   }

   public boolean containsSoul() {
      if (this.getItem().m_150930_(EnigmaticItems.SOUL_CRYSTAL)) {
         return true;
      } else {
         return this.getItem().m_150930_(EnigmaticItems.STORAGE_CRYSTAL) ? ItemNBTHelper.getNBT(this.getItem()).m_128441_("embeddedSoul") : false;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public float getItemHover(float partialTicks) {
      return ((float)this.getAge() + partialTicks) / 20.0F + this.hoverStart;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean m_6783_(double distance) {
      double d0 = this.m_20191_().m_82309_() * 4.0;
      if (Double.isNaN(d0)) {
         d0 = 4.0;
      }

      d0 *= 64.0;
      return distance < d0 * d0;
   }

   public Component m_7755_() {
      Component itextcomponent = this.m_7770_();
      return (Component)(itextcomponent != null ? itextcomponent : Component.m_237115_(this.getItem().m_41778_()));
   }

   public boolean m_6097_() {
      return false;
   }

   public ItemStack getItem() {
      return (ItemStack)this.m_20088_().m_135370_(ITEM);
   }

   public void setItem(ItemStack stack) {
      this.m_20088_().m_135381_(ITEM, stack);
   }

   @Nullable
   public UUID getOwnerId() {
      return this.owner;
   }

   public void setOwnerId(@Nullable UUID ownerId) {
      this.owner = ownerId;
   }

   @Nullable
   public UUID getThrowerId() {
      return this.thrower;
   }

   public void setThrowerId(@Nullable UUID throwerId) {
      this.thrower = throwerId;
   }

   @OnlyIn(Dist.CLIENT)
   public int getAge() {
      return this.age;
   }

   public void setDefaultPickupDelay() {
      this.pickupDelay = 10;
   }

   public void setNoPickupDelay() {
      this.pickupDelay = 0;
   }

   public void setInfinitePickupDelay() {
      this.pickupDelay = 32767;
   }

   public void setPickupDelay(int ticks) {
      this.pickupDelay = ticks;
   }

   public boolean cannotPickup() {
      return this.pickupDelay > 0;
   }

   public void makeFakeItem() {
      this.setInfinitePickupDelay();
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}
