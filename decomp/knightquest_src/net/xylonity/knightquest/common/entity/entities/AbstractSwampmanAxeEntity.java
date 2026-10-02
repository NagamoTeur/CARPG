package net.xylonity.knightquest.common.entity.entities;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Entity.MovementEmission;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;

public class AbstractSwampmanAxeEntity extends Projectile {
   private static final EntityDataAccessor<Byte> ID_FLAGS = SynchedEntityData.m_135353_(AbstractSwampmanAxeEntity.class, EntityDataSerializers.f_135027_);
   private static final EntityDataAccessor<Byte> PIERCE_LEVEL = SynchedEntityData.m_135353_(AbstractSwampmanAxeEntity.class, EntityDataSerializers.f_135027_);
   @Nullable
   private BlockState lastState;
   protected boolean inGround;
   protected int inGroundTime;
   public Pickup pickup = Pickup.DISALLOWED;
   public int shakeTime;
   private int life;
   private double baseDamage = 5.0;
   private int knockback;
   private SoundEvent soundEvent = this.getDefaultHitGroundSoundEvent();
   @Nullable
   private IntOpenHashSet piercingIgnoreEntityIds;
   @Nullable
   private List<Entity> piercedAndKilledEntities;
   private final IntOpenHashSet ignoredEntities = new IntOpenHashSet();

   protected AbstractSwampmanAxeEntity(EntityType<? extends AbstractSwampmanAxeEntity> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
   }

   protected AbstractSwampmanAxeEntity(EntityType<? extends AbstractSwampmanAxeEntity> pEntityType, double pX, double pY, double pZ, Level pLevel) {
      this(pEntityType, pLevel);
      this.m_6034_(pX, pY, pZ);
   }

   protected AbstractSwampmanAxeEntity(EntityType<? extends AbstractSwampmanAxeEntity> pEntityType, LivingEntity pShooter, Level pLevel) {
      this(pEntityType, pShooter.m_20185_(), pShooter.m_20188_() - 0.1F, pShooter.m_20189_(), pLevel);
      this.m_5602_(pShooter);
   }

   public void setSoundEvent(SoundEvent pSoundEvent) {
      this.soundEvent = pSoundEvent;
   }

   public boolean m_6783_(double pDistance) {
      double d0 = this.m_20191_().m_82309_() * 10.0;
      if (Double.isNaN(d0)) {
         d0 = 1.0;
      }

      d0 *= 64.0 * m_20150_();
      return pDistance < d0 * d0;
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(ID_FLAGS, (byte)0);
      this.f_19804_.m_135372_(PIERCE_LEVEL, (byte)0);
   }

   public void m_6686_(double pX, double pY, double pZ, float pVelocity, float pInaccuracy) {
      super.m_6686_(pX, pY, pZ, pVelocity, pInaccuracy);
      this.life = 0;
   }

   public void m_6453_(double pX, double pY, double pZ, float pYaw, float pPitch, int pPosRotationIncrements, boolean pTeleport) {
      this.m_6034_(pX, pY, pZ);
      this.m_19915_(pYaw, pPitch);
   }

   public void m_6001_(double pX, double pY, double pZ) {
      super.m_6001_(pX, pY, pZ);
      this.life = 0;
   }

   public void m_8119_() {
      super.m_8119_();
      boolean flag = this.isNoPhysics();
      Vec3 vec3 = this.m_20184_();
      if (this.f_19860_ == 0.0F && this.f_19859_ == 0.0F) {
         double d0 = vec3.m_165924_();
         this.m_146922_((float)(Mth.m_14136_(vec3.f_82479_, vec3.f_82481_) * 180.0F / (float)Math.PI));
         this.m_146926_((float)(Mth.m_14136_(vec3.f_82480_, d0) * 180.0F / (float)Math.PI));
         this.f_19859_ = this.m_146908_();
         this.f_19860_ = this.m_146909_();
      }

      if (!this.inGround) {
         this.f_19860_ = this.m_146909_() + 60.0F;
      }

      BlockPos blockpos = this.m_20183_();
      BlockState blockstate = this.f_19853_.m_8055_(blockpos);
      if (!blockstate.m_60795_() && !flag) {
         VoxelShape voxelshape = blockstate.m_60812_(this.f_19853_, blockpos);
         if (!voxelshape.m_83281_()) {
            Vec3 vec31 = this.m_20182_();

            for (AABB aabb : voxelshape.m_83299_()) {
               if (aabb.m_82338_(blockpos).m_82390_(vec31)) {
                  this.inGround = true;
                  break;
               }
            }
         }
      }

      if (this.shakeTime > 0) {
         this.shakeTime--;
      }

      if (this.m_20070_() || blockstate.m_60713_(Blocks.f_152499_) || this.isInFluidType((fluidType, height) -> this.canFluidExtinguish(fluidType))) {
         this.m_20095_();
      }

      if (this.inGround && !flag) {
         if (this.lastState != blockstate && this.shouldFall()) {
            this.startFalling();
         } else if (!this.f_19853_.f_46443_) {
            this.tickDespawn();
         }

         this.inGroundTime++;
      } else {
         this.inGroundTime = 0;
         Vec3 vec32 = this.m_20182_();
         Vec3 vec33 = vec32.m_82549_(vec3);
         HitResult hitresult = this.f_19853_.m_45547_(new ClipContext(vec32, vec33, Block.COLLIDER, Fluid.NONE, this));
         if (hitresult.m_6662_() != Type.MISS) {
            vec33 = hitresult.m_82450_();
         }

         while (!this.m_213877_()) {
            EntityHitResult entityhitresult = this.findHitEntity(vec32, vec33);
            if (entityhitresult != null) {
               hitresult = entityhitresult;
            }

            if (hitresult != null && hitresult.m_6662_() == Type.ENTITY) {
               assert hitresult instanceof EntityHitResult;

               Entity entity = ((EntityHitResult)hitresult).m_82443_();
               Entity entity1 = this.m_37282_();
               if (entity instanceof Player && entity1 instanceof Player && !((Player)entity1).m_7099_((Player)entity)) {
                  hitresult = null;
                  entityhitresult = null;
               }
            }

            if (hitresult != null && hitresult.m_6662_() != Type.MISS && !flag && !ForgeEventFactory.onProjectileImpact(this, hitresult)) {
               this.m_6532_(hitresult);
               this.f_19812_ = true;
            }

            if (entityhitresult == null || this.getPierceLevel() <= 0) {
               break;
            }

            hitresult = null;
         }

         vec3 = this.m_20184_();
         double d5 = vec3.f_82479_;
         double d6 = vec3.f_82480_;
         double d1 = vec3.f_82481_;
         if (this.isCritArrow()) {
            for (int i = 0; i < 4; i++) {
               this.f_19853_
                  .m_7106_(
                     ParticleTypes.f_123797_,
                     this.m_20185_() + d5 * (double)i / 4.0,
                     this.m_20186_() + d6 * (double)i / 4.0,
                     this.m_20189_() + d1 * (double)i / 4.0,
                     -d5,
                     -d6 + 0.2,
                     -d1
                  );
            }
         }

         double d7 = this.m_20185_() + d5;
         double d2 = this.m_20186_() + d6;
         double d3 = this.m_20189_() + d1;
         double d4 = vec3.m_165924_();
         if (flag) {
            this.m_146922_((float)(Mth.m_14136_(-d5, -d1) * 180.0F / (float)Math.PI));
         } else {
            this.m_146922_((float)(Mth.m_14136_(d5, d1) * 180.0F / (float)Math.PI));
         }

         this.m_146926_((float)(Mth.m_14136_(d6, d4) * 180.0F / (float)Math.PI));
         this.m_146926_(m_37273_(this.f_19860_, this.m_146909_()));
         this.m_146922_(m_37273_(this.f_19859_, this.m_146908_()));
         float f = 0.99F;
         if (this.m_20069_()) {
            for (int j = 0; j < 4; j++) {
               this.f_19853_.m_7106_(ParticleTypes.f_123795_, d7 - d5 * 0.25, d2 - d6 * 0.25, d3 - d1 * 0.25, d5, d6, d1);
            }

            f = this.getWaterInertia();
         }

         this.m_20256_(vec3.m_82490_((double)f));
         if (!this.m_20068_() && !flag) {
            Vec3 vec34 = this.m_20184_();
            this.m_20334_(vec34.f_82479_, vec34.f_82480_ - 0.05F, vec34.f_82481_);
         }

         this.m_6034_(d7, d2, d3);
         this.m_20101_();
      }
   }

   private boolean shouldFall() {
      return this.inGround && this.f_19853_.m_45772_(new AABB(this.m_20182_(), this.m_20182_()).m_82400_(0.06));
   }

   private void startFalling() {
      this.inGround = false;
      Vec3 vec3 = this.m_20184_();
      this.m_20256_(
         vec3.m_82542_((double)(this.f_19796_.m_188501_() * 0.2F), (double)(this.f_19796_.m_188501_() * 0.2F), (double)(this.f_19796_.m_188501_() * 0.2F))
      );
      this.life = 0;
   }

   public void m_6478_(@NotNull MoverType pType, @NotNull Vec3 pPos) {
      super.m_6478_(pType, pPos);
      if (pType != MoverType.SELF && this.shouldFall()) {
         this.startFalling();
      }
   }

   protected void tickDespawn() {
      this.life++;
      if (this.life >= 1200) {
         this.m_146870_();
      }
   }

   private void resetPiercedEntities() {
      if (this.piercedAndKilledEntities != null) {
         this.piercedAndKilledEntities.clear();
      }

      if (this.piercingIgnoreEntityIds != null) {
         this.piercingIgnoreEntityIds.clear();
      }
   }

   public byte getPierceLevel() {
      return (Byte)this.f_19804_.m_135370_(PIERCE_LEVEL);
   }

   protected void m_5790_(@NotNull EntityHitResult pResult) {
      super.m_5790_(pResult);
      Entity entity = pResult.m_82443_();
      float f = (float)this.m_20184_().m_82553_();
      int i = Mth.m_14165_(Mth.m_14008_((double)f * this.baseDamage, 0.0, 2.147483647E9));
      if (this.getPierceLevel() > 0) {
         if (this.piercingIgnoreEntityIds == null) {
            this.piercingIgnoreEntityIds = new IntOpenHashSet(5);
         }

         if (this.piercedAndKilledEntities == null) {
            this.piercedAndKilledEntities = Lists.newArrayListWithCapacity(5);
         }

         if (this.piercingIgnoreEntityIds.size() >= this.getPierceLevel() + 1) {
            this.m_146870_();
            return;
         }

         this.piercingIgnoreEntityIds.add(entity.m_19879_());
      }

      if (this.isCritArrow()) {
         long j = (long)this.f_19796_.m_188503_(i / 2 + 2);
         i = (int)Math.min(j + (long)i, 2147483647L);
      }

      Entity entity1 = this.m_37282_();
      DamageSource damagesource;
      if (entity1 == null) {
         damagesource = DamageSource.f_19318_;
      } else {
         damagesource = DamageSource.f_19318_;
         if (entity1 instanceof LivingEntity) {
            ((LivingEntity)entity1).m_21335_(entity);
         }
      }

      boolean flag = entity.m_6095_() == EntityType.f_20566_;
      int k = entity.m_20094_();
      if (this.m_6060_() && !flag) {
         entity.m_20254_(5);
      }

      if (entity.m_6469_(damagesource, (float)i)) {
         if (flag) {
            return;
         }

         if (entity instanceof LivingEntity livingentity) {
            if (this.knockback > 0) {
               double d0 = Math.max(0.0, 1.0 - livingentity.m_21133_(Attributes.f_22278_));
               Vec3 vec3 = this.m_20184_().m_82542_(1.0, 0.0, 1.0).m_82541_().m_82490_((double)this.knockback * 0.6 * d0);
               if (vec3.m_82556_() > 0.0) {
                  livingentity.m_5997_(vec3.f_82479_, 0.1, vec3.f_82481_);
               }
            }

            if (!this.f_19853_.f_46443_ && entity1 instanceof LivingEntity) {
               EnchantmentHelper.m_44823_(livingentity, entity1);
               EnchantmentHelper.m_44896_((LivingEntity)entity1, livingentity);
            }

            this.doPostHurtEffects(livingentity);
            if (entity1 != null && livingentity != entity1 && livingentity instanceof Player && entity1 instanceof ServerPlayer && !this.m_20067_()) {
               ((ServerPlayer)entity1).f_8906_.m_9829_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132159_, 0.0F));
            }

            if (!entity.m_6084_() && this.piercedAndKilledEntities != null) {
               this.piercedAndKilledEntities.add(livingentity);
            }

            if (!this.f_19853_.f_46443_ && entity1 instanceof ServerPlayer serverplayer) {
               if (this.piercedAndKilledEntities != null && this.shotFromCrossbow()) {
                  CriteriaTriggers.f_10556_.m_46871_(serverplayer, this.piercedAndKilledEntities);
               } else if (!entity.m_6084_() && this.shotFromCrossbow()) {
                  CriteriaTriggers.f_10556_.m_46871_(serverplayer, Arrays.asList(entity));
               }
            }
         }

         this.m_5496_(this.soundEvent, 1.0F, 1.2F / (this.f_19796_.m_188501_() * 0.2F + 0.9F));
      } else {
         entity.m_7311_(k);
         this.m_20256_(this.m_20184_().m_82490_(-0.1));
         this.m_146922_(this.m_146908_() + 180.0F);
         this.f_19859_ += 180.0F;
         if (!this.f_19853_.f_46443_ && this.m_20184_().m_82556_() < 1.0E-7) {
            this.m_146870_();
         }
      }
   }

   protected void m_8060_(BlockHitResult pResult) {
      this.lastState = this.f_19853_.m_8055_(pResult.m_82425_());
      super.m_8060_(pResult);
      Vec3 vec3 = pResult.m_82450_().m_82492_(this.m_20185_(), this.m_20186_(), this.m_20189_());
      this.m_20256_(vec3);
      Vec3 vec31 = vec3.m_82541_().m_82490_(0.05F);
      this.m_20343_(this.m_20185_() - vec31.f_82479_, this.m_20186_() - vec31.f_82480_, this.m_20189_() - vec31.f_82481_);
      this.m_5496_(this.getHitGroundSoundEvent(), 1.0F, 1.2F / (this.f_19796_.m_188501_() * 0.2F + 0.9F));
      this.inGround = true;
      this.shakeTime = 7;
      this.setCritArrow(false);
      this.setPierceLevel((byte)0);
      this.setSoundEvent(SoundEvents.f_11685_);
      this.setShotFromCrossbow(false);
      this.resetPiercedEntities();
   }

   protected SoundEvent getDefaultHitGroundSoundEvent() {
      return SoundEvents.f_11685_;
   }

   protected final SoundEvent getHitGroundSoundEvent() {
      return this.soundEvent;
   }

   protected void doPostHurtEffects(LivingEntity pTarget) {
   }

   @Nullable
   protected EntityHitResult findHitEntity(Vec3 pStartVec, Vec3 pEndVec) {
      return ProjectileUtil.m_37304_(this.f_19853_, this, pStartVec, pEndVec, this.m_20191_().m_82369_(this.m_20184_()).m_82400_(1.0), this::m_5603_);
   }

   protected boolean m_5603_(@NotNull Entity pEntity) {
      return super.m_5603_(pEntity)
         && (this.piercingIgnoreEntityIds == null || !this.piercingIgnoreEntityIds.contains(pEntity.m_19879_()))
         && !this.ignoredEntities.contains(pEntity.m_19879_());
   }

   public void m_7380_(@NotNull CompoundTag pCompound) {
      super.m_7380_(pCompound);
      pCompound.m_128376_("life", (short)this.life);
      if (this.lastState != null) {
         pCompound.m_128365_("inBlockState", NbtUtils.m_129202_(this.lastState));
      }

      pCompound.m_128344_("shake", (byte)this.shakeTime);
      pCompound.m_128379_("inGround", this.inGround);
      pCompound.m_128344_("pickup", (byte)this.pickup.ordinal());
      pCompound.m_128347_("damage", this.baseDamage);
      pCompound.m_128379_("crit", this.isCritArrow());
      pCompound.m_128379_("ShotFromCrossbow", this.shotFromCrossbow());
   }

   public void m_7378_(@NotNull CompoundTag pCompound) {
      super.m_7378_(pCompound);
      this.life = pCompound.m_128448_("life");
      if (pCompound.m_128425_("inBlockState", 10)) {
         this.lastState = NbtUtils.m_129241_(pCompound.m_128469_("inBlockState"));
      }

      this.shakeTime = pCompound.m_128445_("shake") & 255;
      this.inGround = pCompound.m_128471_("inGround");
      if (pCompound.m_128425_("damage", 99)) {
         this.baseDamage = pCompound.m_128459_("damage");
      }

      this.pickup = Pickup.m_36808_(pCompound.m_128445_("pickup"));
      this.setCritArrow(pCompound.m_128471_("crit"));
      this.setPierceLevel(pCompound.m_128445_("PierceLevel"));
      if (pCompound.m_128425_("SoundEvent", 8)) {
         this.soundEvent = Registry.f_122821_.m_6612_(new ResourceLocation(pCompound.m_128461_("SoundEvent"))).orElse(this.getDefaultHitGroundSoundEvent());
      }

      this.setShotFromCrossbow(pCompound.m_128471_("ShotFromCrossbow"));
   }

   public void m_5602_(@Nullable Entity pEntity) {
      super.m_5602_(pEntity);
      if (pEntity instanceof Player) {
         this.pickup = ((Player)pEntity).m_150110_().f_35937_ ? Pickup.CREATIVE_ONLY : Pickup.ALLOWED;
      }
   }

   public void m_6123_(@NotNull Player pEntity) {
      if (!this.f_19853_.f_46443_ && (this.inGround || this.isNoPhysics()) && this.shakeTime <= 0 && this.tryPickup(pEntity)) {
         pEntity.m_7938_(this, 1);
         this.m_146870_();
      }
   }

   protected boolean tryPickup(Player pPlayer) {
      return Objects.requireNonNull(this.pickup) == Pickup.CREATIVE_ONLY ? pPlayer.m_150110_().f_35937_ : false;
   }

   @NotNull
   protected MovementEmission m_142319_() {
      return MovementEmission.NONE;
   }

   public boolean m_6097_() {
      return false;
   }

   protected float m_6380_(@NotNull Pose pPose, @NotNull EntityDimensions pSize) {
      return 0.13F;
   }

   public void setCritArrow(boolean pCritArrow) {
      this.setFlag(1, pCritArrow);
   }

   public void setPierceLevel(byte pPierceLevel) {
      this.f_19804_.m_135381_(PIERCE_LEVEL, pPierceLevel);
   }

   private void setFlag(int pId, boolean pValue) {
      byte b0 = (Byte)this.f_19804_.m_135370_(ID_FLAGS);
      if (pValue) {
         this.f_19804_.m_135381_(ID_FLAGS, (byte)(b0 | pId));
      } else {
         this.f_19804_.m_135381_(ID_FLAGS, (byte)(b0 & ~pId));
      }
   }

   public boolean isCritArrow() {
      byte b0 = (Byte)this.f_19804_.m_135370_(ID_FLAGS);
      return (b0 & 1) != 0;
   }

   public boolean shotFromCrossbow() {
      byte b0 = (Byte)this.f_19804_.m_135370_(ID_FLAGS);
      return (b0 & 4) != 0;
   }

   protected float getWaterInertia() {
      return 0.6F;
   }

   public boolean isNoPhysics() {
      return !this.f_19853_.f_46443_ ? this.f_19794_ : ((Byte)this.f_19804_.m_135370_(ID_FLAGS) & 2) != 0;
   }

   public void setShotFromCrossbow(boolean pShotFromCrossbow) {
      this.setFlag(4, pShotFromCrossbow);
   }
}
