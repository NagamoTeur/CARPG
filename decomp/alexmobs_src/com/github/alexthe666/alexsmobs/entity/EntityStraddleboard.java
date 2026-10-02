package com.github.alexthe666.alexsmobs.entity;

import com.github.alexthe666.alexsmobs.enchantment.AMEnchantmentRegistry;
import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import javax.annotation.Nullable;
import net.minecraft.BlockUtil.FoundRectangle;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;

public class EntityStraddleboard extends Entity implements PlayerRideableJumping {
   private static final EntityDataAccessor<ItemStack> ITEMSTACK = SynchedEntityData.m_135353_(EntityStraddleboard.class, EntityDataSerializers.f_135033_);
   private static final EntityDataAccessor<Integer> TIME_SINCE_HIT = SynchedEntityData.m_135353_(EntityStraddleboard.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Float> DAMAGE_TAKEN = SynchedEntityData.m_135353_(EntityStraddleboard.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Integer> ROCKING_TICKS = SynchedEntityData.m_135353_(EntityStraddleboard.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.m_135353_(EntityStraddleboard.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Boolean> DEFAULT_COLOR = SynchedEntityData.m_135353_(EntityStraddleboard.class, EntityDataSerializers.f_135035_);
   public float boardRot = 0.0F;
   public float prevBoardRot = 0.0F;
   private double lastYd;
   private boolean rocking;
   private boolean downwards;
   private float rockingIntensity;
   private float rockingAngle;
   private float prevRockingAngle;
   private boolean jumpOutOfLava = false;
   private float outOfControlTicks;
   private net.minecraft.world.entity.vehicle.Boat.Status status;
   private net.minecraft.world.entity.vehicle.Boat.Status previousStatus;
   private float momentum;
   private double waterLevel;
   private float boatGlide;
   private int extinguishTimer = 0;

   public EntityStraddleboard(EntityType<?> p_i48580_1_, Level p_i48580_2_) {
      super(p_i48580_1_, p_i48580_2_);
      this.f_19850_ = true;
   }

   public EntityStraddleboard(SpawnEntity spawnEntity, Level world) {
      this((EntityType<?>)AMEntityRegistry.STRADDLEBOARD.get(), world);
   }

   public EntityStraddleboard(Level worldIn, double x, double y, double z) {
      this((EntityType<?>)AMEntityRegistry.STRADDLEBOARD.get(), worldIn);
      this.m_6034_(x, y, z);
      this.m_20256_(Vec3.f_82478_);
      this.f_19854_ = x;
      this.f_19855_ = y;
      this.f_19856_ = z;
   }

   public static boolean canVehicleCollide(Entity p_242378_0_, Entity entity) {
      return (entity.m_5829_() || entity.m_6094_()) && !p_242378_0_.m_20365_(entity);
   }

   protected float m_6380_(Pose poseIn, EntityDimensions sizeIn) {
      return sizeIn.f_20378_;
   }

   protected boolean isMovementNoisy() {
      return false;
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(TIME_SINCE_HIT, 0);
      this.f_19804_.m_135372_(ITEMSTACK, new ItemStack((ItemLike)AMItemRegistry.STRADDLEBOARD.get()));
      this.f_19804_.m_135372_(ROCKING_TICKS, 0);
      this.f_19804_.m_135372_(DEFAULT_COLOR, true);
      this.f_19804_.m_135372_(COLOR, 0);
      this.f_19804_.m_135372_(DAMAGE_TAKEN, 0.0F);
   }

   public boolean shouldRiderSit() {
      return false;
   }

   public boolean m_7337_(Entity entity) {
      return canVehicleCollide(this, entity);
   }

   public boolean m_5829_() {
      return true;
   }

   public boolean m_6094_() {
      return true;
   }

   protected Vec3 m_7643_(Axis axis, FoundRectangle result) {
      return LivingEntity.m_21289_(super.m_7643_(axis, result));
   }

   public double m_6048_() {
      return 0.9;
   }

   public boolean m_6469_(DamageSource source, float amount) {
      if (this.m_6673_(source)) {
         return false;
      } else if (!this.f_19853_.f_46443_ && !this.m_213877_()) {
         this.setTimeSinceHit(10);
         this.setDamageTaken(this.getDamageTaken() + amount * 10.0F);
         this.m_5834_();
         this.setRockingTicks(25);
         boolean flag = source.m_7639_() instanceof Player && ((Player)source.m_7639_()).m_150110_().f_35937_;
         if (flag || this.getDamageTaken() > 40.0F) {
            if (!flag) {
               Player p = null;
               if (source.m_7639_() instanceof Player) {
                  p = (Player)source.m_7639_();
               }

               if (this.m_6688_() != null && this.m_6688_() instanceof Player) {
                  p = (Player)this.m_6688_();
               }

               if (!this.f_19853_.f_46443_ && !this.m_213877_()) {
                  boolean drop = false;
                  if (this.getEnchant((Enchantment)AMEnchantmentRegistry.STRADDLE_BOARDRETURN.get()) > 0) {
                     drop = p != null && !p.m_36356_(this.getItemBoard());
                  }

                  if (drop) {
                     this.m_19983_(this.getItemBoard());
                  }

                  this.m_142687_(RemovalReason.DISCARDED);
               }
            }

            this.m_142687_(RemovalReason.DISCARDED);
         }

         return true;
      } else {
         return true;
      }
   }

   private ItemStack getItemBoard() {
      return this.getItemStack();
   }

   public void m_7334_(Entity entityIn) {
      if (entityIn instanceof EntityStraddleboard) {
         if (entityIn.m_20191_().f_82289_ < this.m_20191_().f_82292_) {
            super.m_7334_(entityIn);
         }
      } else if (entityIn.m_20191_().f_82289_ <= this.m_20191_().f_82289_) {
         super.m_7334_(entityIn);
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void m_6053_() {
      this.setTimeSinceHit(10);
      this.setDamageTaken(this.getDamageTaken() * 11.0F);
   }

   public boolean m_6087_() {
      return !this.m_213877_();
   }

   private net.minecraft.world.entity.vehicle.Boat.Status getBoatStatus() {
      net.minecraft.world.entity.vehicle.Boat.Status boatentity$status = this.getUnderwaterStatus();
      if (boatentity$status != null) {
         this.waterLevel = this.m_20191_().f_82292_;
         return boatentity$status;
      } else if (this.checkInWater()) {
         return net.minecraft.world.entity.vehicle.Boat.Status.IN_WATER;
      } else {
         float f = this.getBoatGlide();
         if (f > 0.0F) {
            this.boatGlide = f;
            return net.minecraft.world.entity.vehicle.Boat.Status.ON_LAND;
         } else {
            return net.minecraft.world.entity.vehicle.Boat.Status.IN_AIR;
         }
      }
   }

   public float getBoatGlide() {
      AABB axisalignedbb = this.m_20191_();
      AABB axisalignedbb1 = new AABB(
         axisalignedbb.f_82288_, axisalignedbb.f_82289_ - 0.001, axisalignedbb.f_82290_, axisalignedbb.f_82291_, axisalignedbb.f_82289_, axisalignedbb.f_82293_
      );
      int i = Mth.m_14107_(axisalignedbb1.f_82288_) - 1;
      int j = Mth.m_14165_(axisalignedbb1.f_82291_) + 1;
      int k = Mth.m_14107_(axisalignedbb1.f_82289_) - 1;
      int l = Mth.m_14165_(axisalignedbb1.f_82292_) + 1;
      int i1 = Mth.m_14107_(axisalignedbb1.f_82290_) - 1;
      int j1 = Mth.m_14165_(axisalignedbb1.f_82293_) + 1;
      VoxelShape voxelshape = Shapes.m_83064_(axisalignedbb1);
      float f = 0.0F;
      int k1 = 0;
      MutableBlockPos blockpos$mutable = new MutableBlockPos();

      for (int l1 = i; l1 < j; l1++) {
         for (int i2 = i1; i2 < j1; i2++) {
            int j2 = (l1 != i && l1 != j - 1 ? 0 : 1) + (i2 != i1 && i2 != j1 - 1 ? 0 : 1);
            if (j2 != 2) {
               for (int k2 = k; k2 < l; k2++) {
                  if (j2 <= 0 || k2 != k && k2 != l - 1) {
                     blockpos$mutable.m_122178_(l1, k2, i2);
                     BlockState blockstate = this.f_19853_.m_8055_(blockpos$mutable);
                     if (!(blockstate.m_60734_() instanceof WaterlilyBlock)
                        && Shapes.m_83157_(
                           blockstate.m_60816_(this.f_19853_, blockpos$mutable).m_83216_((double)l1, (double)k2, (double)i2), voxelshape, BooleanOp.f_82689_
                        )) {
                        f += blockstate.getFriction(this.f_19853_, blockpos$mutable, this);
                        k1++;
                     }
                  }
               }
            }
         }
      }

      return f / (float)k1;
   }

   private boolean checkInWater() {
      AABB axisalignedbb = this.m_20191_();
      int i = Mth.m_14107_(axisalignedbb.f_82288_);
      int j = Mth.m_14165_(axisalignedbb.f_82291_);
      int k = Mth.m_14107_(axisalignedbb.f_82289_);
      int l = Mth.m_14165_(axisalignedbb.f_82289_ - 0.001);
      int i1 = Mth.m_14107_(axisalignedbb.f_82290_);
      int j1 = Mth.m_14165_(axisalignedbb.f_82293_);
      boolean flag = false;
      this.waterLevel = Double.MIN_VALUE;
      MutableBlockPos blockpos$mutable = new MutableBlockPos();

      for (int k1 = i; k1 < j; k1++) {
         for (int l1 = k; l1 < l; l1++) {
            for (int i2 = i1; i2 < j1; i2++) {
               blockpos$mutable.m_122178_(k1, l1, i2);
               FluidState fluidstate = this.f_19853_.m_6425_(blockpos$mutable);
               if (fluidstate.m_205070_(FluidTags.f_13131_) || fluidstate.m_205070_(FluidTags.f_13132_)) {
                  float f = (float)l1 + fluidstate.m_76155_(this.f_19853_, blockpos$mutable);
                  this.waterLevel = Math.max((double)f, this.waterLevel);
                  flag |= axisalignedbb.f_82289_ < (double)f;
               }
            }
         }
      }

      return flag;
   }

   private void updateMotion() {
      double d0 = -0.04F;
      double d1 = this.m_20068_() ? 0.0 : -0.04F;
      double d2 = 0.0;
      this.momentum = 0.05F;
      if (this.previousStatus == net.minecraft.world.entity.vehicle.Boat.Status.IN_AIR
         && this.status != net.minecraft.world.entity.vehicle.Boat.Status.IN_AIR
         && this.status != net.minecraft.world.entity.vehicle.Boat.Status.ON_LAND) {
         this.waterLevel = this.m_20227_(1.0);
         this.m_6034_(this.m_20185_(), (double)(this.getWaterLevelAbove() - this.m_20206_()) + 0.25, this.m_20189_());
         this.m_20256_(this.m_20184_().m_82542_(1.0, 1.0, 1.0));
         this.lastYd = 0.0;
         this.status = net.minecraft.world.entity.vehicle.Boat.Status.IN_WATER;
      } else {
         if (this.status == net.minecraft.world.entity.vehicle.Boat.Status.IN_WATER) {
            d2 = (this.waterLevel - this.m_20186_()) / (double)this.m_20206_();
            this.momentum = 0.9F;
         } else if (this.status == net.minecraft.world.entity.vehicle.Boat.Status.UNDER_FLOWING_WATER) {
            d1 = -7.0E-4;
            this.momentum = 0.9F;
         } else if (this.status == net.minecraft.world.entity.vehicle.Boat.Status.UNDER_WATER) {
            d2 = 0.01F;
            this.momentum = 0.45F;
         } else if (this.status == net.minecraft.world.entity.vehicle.Boat.Status.IN_AIR) {
            this.momentum = 0.9F;
         } else if (this.status == net.minecraft.world.entity.vehicle.Boat.Status.ON_LAND) {
            this.momentum = this.boatGlide;
            if (this.m_6688_() instanceof Player) {
               this.boatGlide /= 2.0F;
            }
         }

         Vec3 vector3d = this.m_20184_();
         this.m_20334_(vector3d.f_82479_ * (double)this.momentum, vector3d.f_82480_ + d1, vector3d.f_82481_ * (double)this.momentum);
         if (d2 > 0.0) {
            Vec3 vector3d1 = this.m_20184_();
            this.m_20334_(vector3d1.f_82479_, (vector3d1.f_82480_ + d2 * 0.06153846016296973) * 0.75, vector3d1.f_82481_);
         }
      }
   }

   public boolean isDefaultColor() {
      return (Boolean)this.f_19804_.m_135370_(DEFAULT_COLOR);
   }

   public void setDefaultColor(boolean bar) {
      this.f_19804_.m_135381_(DEFAULT_COLOR, bar);
   }

   public int getColor() {
      return this.isDefaultColor() ? 11387863 : (Integer)this.f_19804_.m_135370_(COLOR);
   }

   public void setColor(int index) {
      this.f_19804_.m_135381_(COLOR, index);
   }

   @Nullable
   private net.minecraft.world.entity.vehicle.Boat.Status getUnderwaterStatus() {
      AABB axisalignedbb = this.m_20191_();
      double d0 = axisalignedbb.f_82292_ + 0.001;
      int i = Mth.m_14107_(axisalignedbb.f_82288_);
      int j = Mth.m_14165_(axisalignedbb.f_82291_);
      int k = Mth.m_14107_(axisalignedbb.f_82292_);
      int l = Mth.m_14165_(d0);
      int i1 = Mth.m_14107_(axisalignedbb.f_82290_);
      int j1 = Mth.m_14165_(axisalignedbb.f_82293_);
      boolean flag = false;
      MutableBlockPos blockpos$mutable = new MutableBlockPos();

      for (int k1 = i; k1 < j; k1++) {
         for (int l1 = k; l1 < l; l1++) {
            for (int i2 = i1; i2 < j1; i2++) {
               blockpos$mutable.m_122178_(k1, l1, i2);
               FluidState fluidstate = this.f_19853_.m_6425_(blockpos$mutable);
               if (fluidstate.m_205070_(FluidTags.f_13131_)
                  || fluidstate.m_205070_(FluidTags.f_13132_)
                     && d0 < (double)((float)blockpos$mutable.m_123342_() + fluidstate.m_76155_(this.f_19853_, blockpos$mutable))) {
                  if (!fluidstate.m_76170_()) {
                     return net.minecraft.world.entity.vehicle.Boat.Status.UNDER_FLOWING_WATER;
                  }

                  flag = true;
               }
            }
         }
      }

      return flag ? net.minecraft.world.entity.vehicle.Boat.Status.UNDER_WATER : null;
   }

   public void m_8119_() {
      this.prevBoardRot = this.boardRot;
      super.m_8119_();
      this.previousStatus = this.status;
      this.status = this.getBoatStatus();
      this.floatStrider();
      this.m_20101_();
      if (this.m_5830_()) {
         this.m_20314_(this.m_20185_(), (this.m_20191_().f_82289_ + this.m_20191_().f_82292_) / 2.0, this.m_20189_());
      }

      if (this.getTimeSinceHit() > 0) {
         this.setTimeSinceHit(this.getTimeSinceHit() - 1);
      }

      if (this.getDamageTaken() > 0.0F) {
         this.setDamageTaken(this.getDamageTaken() - 1.0F);
      }

      if (this.m_20077_()) {
         this.m_20242_(true);
         if (this.f_19799_.getDouble(FluidTags.f_13132_) >= (double)this.m_20206_()) {
            this.m_20334_(0.0, 0.1, 0.0);
         }
      } else {
         this.m_20242_(false);
      }

      float f2 = (float)(-((double)((float)this.m_20184_().f_82480_ * 0.5F) * 180.0F / (float)Math.PI));
      this.m_146926_(f2);
      if (this.extinguishTimer > 0) {
         this.extinguishTimer--;
      }

      this.updateRocking();
      if (this.m_6688_() instanceof Player player) {
         if (this.f_19797_ % 50 == 0 && this.getEnchant((Enchantment)AMEnchantmentRegistry.STRADDLE_LAVAWAX.get()) > 0) {
            player.m_7292_(new MobEffectInstance(MobEffects.f_19607_, 100, 0, true, false));
         }

         if (player.m_20094_() > 0 && this.extinguishTimer == 0) {
            player.m_20095_();
         }

         this.m_146922_(player.f_19859_);
         Vec3 vector3d = this.m_20184_();
         if (vector3d.f_82480_ > -0.5) {
            this.f_19789_ = 1.0F;
         }

         Vec3 vector3d1 = player.m_20154_();
         float f = player.m_146909_() * (float) (Math.PI / 180.0);
         double d1 = Math.sqrt(vector3d1.f_82479_ * vector3d1.f_82479_ + vector3d1.f_82481_ * vector3d1.f_82481_);
         double d3 = Math.sqrt((double)((float)vector3d.m_165925_()));
         double d4 = vector3d1.m_82553_();
         float f1 = Mth.m_14089_(f);
         f1 = (float)((double)f1 * (double)f1 * Math.min(1.0, d4 / 0.4));
         double d5 = vector3d.f_82480_ * -0.1 * (double)f1;
         float slow = player.f_20902_ < 0.0F ? 0.0F : player.f_20902_ * 0.115F;
         float threshold = 0.05F;
         if (this.f_19859_ - this.m_146908_() > threshold) {
            this.boardRot += 2.0F;
            slow *= 0.0F;
         } else if (this.f_19859_ - this.m_146908_() < -threshold) {
            this.boardRot -= 2.0F;
            slow *= 0.0F;
         } else if (this.boardRot > 0.0F) {
            this.boardRot = Math.max(this.boardRot - 10.0F, 0.0F);
         } else if (this.boardRot < 0.0F) {
            this.boardRot = Math.min(this.boardRot + 10.0F, 0.0F);
         }

         this.boardRot = Mth.m_14036_(this.boardRot, -25.0F, 25.0F);
         vector3d = vector3d.m_82520_(vector3d1.f_82479_ * (double)slow / d1, 0.0, vector3d1.f_82481_ * (double)slow / d1);
         if (d1 > 0.0) {
            vector3d = vector3d.m_82520_(
               (vector3d1.f_82479_ / d1 * d3 - vector3d.f_82479_) * 0.1, 0.0, (vector3d1.f_82481_ / d1 * d3 - vector3d.f_82481_) * 0.1
            );
         }

         this.m_20256_(vector3d.m_82542_(0.99F, 1.0, 0.99F));
         if (player.m_5830_()) {
            player.m_6038_();
            this.m_6469_(DamageSource.f_19318_, 100.0F);
         }
      }

      this.updateMotion();
      this.m_6478_(MoverType.SELF, this.m_20184_());
   }

   public double m_20188_() {
      return this.m_20186_() + 0.3F;
   }

   protected void m_20351_(Entity passenger) {
      super.m_20351_(passenger);
      if (!this.f_19853_.f_46443_) {
         EntityStraddleboard copy = (EntityStraddleboard)((EntityType)AMEntityRegistry.STRADDLEBOARD.get()).m_20615_(this.f_19853_);
         CompoundTag tag = new CompoundTag();
         this.m_7380_(tag);
         copy.m_7378_(tag);
         copy.m_20359_(passenger);
         this.f_19853_.m_7967_(copy);
      }

      this.m_142687_(RemovalReason.DISCARDED);
   }

   private void floatStrider() {
   }

   @Nullable
   public Entity m_6688_() {
      for (Entity passenger : this.m_20197_()) {
         if (passenger instanceof Player) {
            return (Player)passenger;
         }
      }

      return null;
   }

   private void updateRocking() {
      if (this.f_19853_.f_46443_) {
         int i = this.getRockingTicks();
         if (i > 0) {
            this.rockingIntensity++;
         } else {
            this.rockingIntensity -= 0.1F;
         }

         this.rockingIntensity = Mth.m_14036_(this.rockingIntensity, 0.0F, 1.0F);
         this.prevRockingAngle = this.rockingAngle;
         this.rockingAngle = 10.0F * (float)Math.sin((double)(0.5F * (float)this.f_19853_.m_46467_())) * this.rockingIntensity;
      } else {
         if (!this.rocking) {
            this.setRockingTicks(0);
         }

         int k = this.getRockingTicks();
         if (k > 0) {
            this.setRockingTicks(--k);
            int j = 60 - k - 1;
            if (j > 0 && k == 0) {
               this.setRockingTicks(0);
               Vec3 vector3d = this.m_20184_();
               if (this.downwards) {
                  this.m_20256_(vector3d.m_82520_(0.0, -0.7, 0.0));
                  this.m_20153_();
               } else {
                  this.m_20334_(vector3d.f_82479_, this.m_146862_(p_150274_ -> p_150274_ instanceof Player) ? 2.7 : 0.6, vector3d.f_82481_);
               }
            }

            this.rocking = false;
         }
      }
   }

   public float getWaterLevelAbove() {
      AABB axisalignedbb = this.m_20191_();
      int i = Mth.m_14107_(axisalignedbb.f_82288_);
      int j = Mth.m_14165_(axisalignedbb.f_82291_);
      int k = Mth.m_14107_(axisalignedbb.f_82292_);
      int l = Mth.m_14165_(axisalignedbb.f_82292_ - this.lastYd);
      int i1 = Mth.m_14107_(axisalignedbb.f_82290_);
      int j1 = Mth.m_14165_(axisalignedbb.f_82293_);
      MutableBlockPos blockpos$mutable = new MutableBlockPos();

      label44:
      for (int k1 = k; k1 < l; k1++) {
         float f = 0.0F;

         for (int l1 = i; l1 < j; l1++) {
            for (int i2 = i1; i2 < j1; i2++) {
               blockpos$mutable.m_122178_(l1, k1, i2);
               FluidState fluidstate = this.f_19853_.m_6425_(blockpos$mutable);
               if (fluidstate.m_205070_(FluidTags.f_13131_) || fluidstate.m_205070_(FluidTags.f_13132_)) {
                  f = Math.max(f, fluidstate.m_76155_(this.f_19853_, blockpos$mutable));
               }

               if (f >= 1.0F) {
                  continue label44;
               }
            }
         }

         if (f < 1.0F) {
            return (float)blockpos$mutable.m_123342_() + f;
         }
      }

      return (float)(l + 1);
   }

   public InteractionResult m_6096_(Player player, InteractionHand hand) {
      if (player.m_36341_()) {
         return InteractionResult.PASS;
      } else if (!this.f_19853_.f_46443_) {
         return player.m_20329_(this) ? InteractionResult.CONSUME : InteractionResult.PASS;
      } else {
         return InteractionResult.SUCCESS;
      }
   }

   public float getDamageTaken() {
      return (Float)this.f_19804_.m_135370_(DAMAGE_TAKEN);
   }

   public void setDamageTaken(float damageTaken) {
      this.f_19804_.m_135381_(DAMAGE_TAKEN, damageTaken);
   }

   public int getTimeSinceHit() {
      return (Integer)this.f_19804_.m_135370_(TIME_SINCE_HIT);
   }

   public void setTimeSinceHit(int timeSinceHit) {
      this.f_19804_.m_135381_(TIME_SINCE_HIT, timeSinceHit);
   }

   private int getRockingTicks() {
      return (Integer)this.f_19804_.m_135370_(ROCKING_TICKS);
   }

   private void setRockingTicks(int ticks) {
      this.f_19804_.m_135381_(ROCKING_TICKS, ticks);
   }

   @OnlyIn(Dist.CLIENT)
   public float getRockingAngle(float partialTicks) {
      return Mth.m_14179_(partialTicks, this.prevRockingAngle, this.rockingAngle);
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   protected void m_7378_(CompoundTag compound) {
      this.setDefaultColor(compound.m_128471_("IsDefColor"));
      if (compound.m_128441_("BoardStack")) {
         this.setItemStack(ItemStack.m_41712_(compound.m_128469_("BoardStack")));
      }

      this.setColor(compound.m_128451_("Color"));
   }

   protected void m_7380_(CompoundTag compound) {
      compound.m_128379_("IsDefColor", this.isDefaultColor());
      compound.m_128405_("Color", this.getColor());
      if (!this.getItemStack().m_41619_()) {
         CompoundTag stackTag = new CompoundTag();
         this.getItemStack().m_41739_(stackTag);
         compound.m_128365_("BoardStack", stackTag);
      }
   }

   public void m_7888_(int i) {
   }

   public boolean m_7132_() {
      return this.f_19853_.m_8055_(this.m_20099_()).m_60819_().m_205070_(FluidTags.f_13132_);
   }

   public void m_7199_(int i) {
      this.jumpOutOfLava = true;
      this.f_19812_ = true;
      float scaled = (float)i * 0.01F + 0.1F * (float)this.getEnchant((Enchantment)AMEnchantmentRegistry.STRADDLE_JUMP.get());
      this.m_20256_(this.m_20184_().m_82520_(0.0, (double)(scaled * 1.5F), 0.0));
   }

   private int getEnchant(Enchantment enchantment) {
      return EnchantmentHelper.m_44843_(enchantment, this.getItemBoard());
   }

   public boolean shouldSerpentFriend() {
      return this.getEnchant((Enchantment)AMEnchantmentRegistry.STRADDLE_SERPENTFRIEND.get()) > 0;
   }

   public void m_8012_() {
   }

   public ItemStack getItemStack() {
      return (ItemStack)this.f_19804_.m_135370_(ITEMSTACK);
   }

   public void setItemStack(ItemStack item) {
      this.f_19804_.m_135381_(ITEMSTACK, item);
   }

   public static enum Status {
      IN_WATER,
      UNDER_WATER,
      UNDER_FLOWING_WATER,
      ON_LAND,
      IN_AIR;
   }
}
