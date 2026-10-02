package com.hollingsworth.arsnouveau.common.entity;

import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.api.util.NBTUtil;
import com.hollingsworth.arsnouveau.client.particle.GlowParticleData;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ParticleStatus;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import software.bernie.ars_nouveau.geckolib3.core.easing.EasingManager;
import software.bernie.ars_nouveau.geckolib3.core.easing.EasingType;

public class EntityFlyingItem extends ColoredProjectile {
   public static final EntityDataAccessor<Vec3> to = SynchedEntityData.m_135353_(EntityFlyingItem.class, (EntityDataSerializer)DataSerializers.VEC3.get());
   public static final EntityDataAccessor<Vec3> from = SynchedEntityData.m_135353_(EntityFlyingItem.class, (EntityDataSerializer)DataSerializers.VEC3.get());
   public static final EntityDataAccessor<Boolean> SPAWN_TOUCH = SynchedEntityData.m_135353_(EntityFlyingItem.class, EntityDataSerializers.f_135035_);
   public int age;
   int maxAge;
   public static final EntityDataAccessor<ItemStack> HELD_ITEM = SynchedEntityData.m_135353_(EntityFlyingItem.class, EntityDataSerializers.f_135033_);
   public static final EntityDataAccessor<Float> OFFSET = SynchedEntityData.m_135353_(EntityFlyingItem.class, EntityDataSerializers.f_135029_);
   public static final EntityDataAccessor<Boolean> DIDOFFSET = SynchedEntityData.m_135353_(EntityFlyingItem.class, EntityDataSerializers.f_135035_);
   boolean wentUp;

   public EntityFlyingItem(Level worldIn, Vec3 from, Vec3 to) {
      this(worldIn, from, to, 255, 25, 180);
   }

   public EntityFlyingItem(Level worldIn, Vec3 from, Vec3 to, int r, int g, int b) {
      this((EntityType<EntityFlyingItem>)ModEntities.ENTITY_FLYING_ITEM.get(), worldIn);
      this.f_19804_.m_135381_(EntityFlyingItem.to, to);
      this.f_19804_.m_135381_(EntityFlyingItem.from, from);
      this.maxAge = (int)Math.floor(from.m_82546_(to).m_82553_() * 5.0);
      this.f_19804_.m_135381_(RED, r);
      this.f_19804_.m_135381_(GREEN, g);
      this.f_19804_.m_135381_(BLUE, b);
      this.m_146884_(from);
   }

   public EntityFlyingItem(Level worldIn, BlockPos from, BlockPos to) {
      this(
         worldIn,
         new Vec3((double)from.m_123341_() + 0.5, (double)from.m_123342_(), (double)from.m_123343_() + 0.5),
         new Vec3((double)to.m_123341_() + 0.5, (double)to.m_123342_(), (double)to.m_123343_() + 0.5),
         255,
         25,
         180
      );
   }

   public EntityFlyingItem(Level worldIn, BlockPos from, BlockPos to, int r, int g, int b) {
      this(
         worldIn,
         new Vec3((double)from.m_123341_() + 0.5, (double)from.m_123342_(), (double)from.m_123343_() + 0.5),
         new Vec3((double)to.m_123341_() + 0.5, (double)to.m_123342_(), (double)to.m_123343_() + 0.5),
         r,
         g,
         b
      );
   }

   public EntityFlyingItem(EntityType<EntityFlyingItem> entityAOEProjectileEntityType, Level world) {
      super(entityAOEProjectileEntityType, world);
   }

   public EntityFlyingItem setStack(ItemStack stack) {
      this.f_19804_.m_135381_(HELD_ITEM, stack.m_41777_());
      return this;
   }

   public static float lerp(double percentCompleted, double startValue, double endValue, EasingType type) {
      if (percentCompleted >= 1.0) {
         return (float)endValue;
      } else {
         percentCompleted = EasingManager.ease(percentCompleted, type, null);
         return (float)lerpInternal(percentCompleted, startValue, endValue);
      }
   }

   public static double lerpInternal(double pct, double start, double end) {
      return start + pct * (end - start);
   }

   public double normalize(double value, double min, double max) {
      return 1.0 - (value - min) / (max - min);
   }

   public void m_8119_() {
      super.m_8119_();
      this.age++;
      if (this.age > 400) {
         this.m_142687_(RemovalReason.DISCARDED);
      }

      Vec3 start = (Vec3)this.f_19804_.m_135370_(from);
      Vec3 end = (Vec3)this.f_19804_.m_135370_(to);
      if (!(BlockUtil.distanceFrom(end, this.m_20182_()) <= 1.0) && this.age <= 1000 && !(BlockUtil.distanceFrom(end, this.m_20182_()) > 16.0)) {
         double posX = this.m_20185_();
         double posY = this.m_20186_();
         double posZ = this.m_20189_();
         double time = 1.0 - this.normalize((double)this.age, 0.0, 80.0);
         EasingType type = EasingType.NONE;
         double startY = start.m_7098_();
         double endY = end.m_7098_() + this.getDistanceAdjustment(start, end);
         double lerpX = (double)lerp(time, start.m_7096_(), end.m_7096_(), type);
         double lerpY = (double)lerp(time, (double)lerp(time, startY, endY, type), (double)lerp(time, endY, startY, type), type);
         double lerpZ = (double)lerp(time, start.m_7094_(), end.m_7094_(), type);
         Vec3 adjustedPos = new Vec3(posX, end.m_7098_(), posZ);
         if (BlockUtil.distanceFrom(end, adjustedPos) <= 0.5) {
            posY = this.m_20186_() - 0.05;
            this.m_6034_(lerpX, posY, lerpZ);
         } else {
            this.m_6034_(lerpX, lerpY, lerpZ);
         }

         if (this.f_19853_.f_46443_ && this.age > 1) {
            double deltaX = this.m_20185_() - this.f_19790_;
            double deltaY = this.m_20186_() - this.f_19791_;
            double deltaZ = this.m_20189_() - this.f_19792_;
            double dist = Math.ceil(Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) * 20.0);
            int counter = 0;

            for (double i = 0.0; i < dist; i++) {
               double coeff = i / dist;
               counter += this.f_19853_.f_46441_.m_188503_(3);
               if (counter
                     % (
                        ((ParticleStatus)Minecraft.m_91087_().f_91066_.m_231929_().m_231551_()).m_35965_() == 0
                           ? 1
                           : 2 * ((ParticleStatus)Minecraft.m_91087_().f_91066_.m_231929_().m_231551_()).m_35965_()
                     )
                  == 0) {
                  this.f_19853_
                     .m_7106_(
                        GlowParticleData.createData(
                           new ParticleColor(
                              (Integer)this.f_19804_.m_135370_(RED), (Integer)this.f_19804_.m_135370_(GREEN), (Integer)this.f_19804_.m_135370_(BLUE)
                           )
                        ),
                        (double)((float)(this.f_19854_ + deltaX * coeff)),
                        (double)((float)(this.f_19855_ + deltaY * coeff)),
                        (double)((float)(this.f_19856_ + deltaZ * coeff)),
                        (double)(0.0125F * (this.f_19796_.m_188501_() - 0.5F)),
                        (double)(0.0125F * (this.f_19796_.m_188501_() - 0.5F)),
                        (double)(0.0125F * (this.f_19796_.m_188501_() - 0.5F))
                     );
               }
            }
         }
      } else {
         this.m_142687_(RemovalReason.DISCARDED);
         if (this.f_19853_.f_46443_ && (Boolean)this.f_19804_.m_135370_(SPAWN_TOUCH)) {
            ParticleUtil.spawnTouch(
               (ClientLevel)this.f_19853_,
               new BlockPos(end.f_82479_, end.f_82480_, end.f_82481_),
               new ParticleColor((Integer)this.f_19804_.m_135370_(RED), (Integer)this.f_19804_.m_135370_(GREEN), (Integer)this.f_19804_.m_135370_(BLUE))
            );
         }
      }
   }

   public EntityFlyingItem withNoTouch() {
      this.f_19804_.m_135381_(SPAWN_TOUCH, false);
      return this;
   }

   public void setDistanceAdjust(float offset) {
      this.f_19804_.m_135381_(OFFSET, offset);
      this.f_19804_.m_135381_(DIDOFFSET, true);
   }

   private double getDistanceAdjustment(Vec3 start, Vec3 end) {
      if ((Boolean)this.f_19804_.m_135370_(DIDOFFSET)) {
         return (double)((Float)this.f_19804_.m_135370_(OFFSET)).floatValue();
      } else {
         double distance = BlockUtil.distanceFrom(start, end);
         return distance <= 1.5 ? 2.5 : 3.0;
      }
   }

   @Override
   public void m_20258_(CompoundTag compound) {
      super.m_20258_(compound);
      if (compound.m_128441_("item")) {
         this.f_19804_.m_135381_(HELD_ITEM, ItemStack.m_41712_(compound.m_128469_("item")));
      }

      this.age = compound.m_128451_("age");
      this.f_19804_.m_135381_(DIDOFFSET, compound.m_128471_("didoffset"));
      this.f_19804_.m_135381_(OFFSET, compound.m_128457_("offset"));
      this.f_19804_.m_135381_(from, NBTUtil.getVec(compound, "from"));
      this.f_19804_.m_135381_(to, NBTUtil.getVec(compound, "to"));
   }

   @Override
   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      if (this.getStack() != null) {
         CompoundTag tag = new CompoundTag();
         this.getStack().m_41739_(tag);
         compound.m_128365_("item", tag);
      }

      compound.m_128405_("age", this.age);
      compound.m_128379_("didoffset", (Boolean)this.f_19804_.m_135370_(DIDOFFSET));
      compound.m_128350_("offset", (Float)this.f_19804_.m_135370_(OFFSET));
      if (from != null) {
         NBTUtil.storeVec(compound, "from", (Vec3)this.f_19804_.m_135370_(from));
      }

      if (to != null) {
         NBTUtil.storeVec(compound, "to", (Vec3)this.f_19804_.m_135370_(to));
      }
   }

   public ItemStack getStack() {
      return (ItemStack)this.f_19804_.m_135370_(HELD_ITEM);
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(HELD_ITEM, ItemStack.f_41583_);
      this.f_19804_.m_135372_(OFFSET, 0.0F);
      this.f_19804_.m_135372_(DIDOFFSET, false);
      this.f_19804_.m_135372_(to, new Vec3(0.0, 0.0, 0.0));
      this.f_19804_.m_135372_(from, new Vec3(0.0, 0.0, 0.0));
      this.f_19804_.m_135372_(SPAWN_TOUCH, true);
   }

   public EntityType<?> m_6095_() {
      return (EntityType<?>)ModEntities.ENTITY_FLYING_ITEM.get();
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public EntityFlyingItem(SpawnEntity packet, Level world) {
      super((EntityType<? extends ColoredProjectile>)ModEntities.ENTITY_FLYING_ITEM.get(), world);
   }
}
