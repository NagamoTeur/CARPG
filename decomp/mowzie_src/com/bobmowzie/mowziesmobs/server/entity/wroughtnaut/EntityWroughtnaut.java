package com.bobmowzie.mowziesmobs.server.entity.wroughtnaut;

import com.bobmowzie.mowziesmobs.client.model.tools.ControlledAnimation;
import com.bobmowzie.mowziesmobs.server.ai.MMPathNavigateGround;
import com.bobmowzie.mowziesmobs.server.ai.WroughtnautAttackAI;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationActivateAI;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationDeactivateAI;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationDieAI;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationFWNAttackAI;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationFWNStompAttackAI;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationFWNVerticalAttackAI;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationTakeDamage;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.bobmowzie.mowziesmobs.server.entity.MowzieLLibraryEntity;
import com.bobmowzie.mowziesmobs.server.entity.SmartBodyHelper;
import com.bobmowzie.mowziesmobs.server.loot.LootTableHandler;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.ilexiconn.llibrary.server.animation.Animation;
import com.ilexiconn.llibrary.server.animation.AnimationHandler;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class EntityWroughtnaut extends MowzieLLibraryEntity implements Enemy {
   public static final Animation DIE_ANIMATION = Animation.create(130);
   public static final Animation HURT_ANIMATION = Animation.create(15);
   public static final Animation ATTACK_ANIMATION = Animation.create(50);
   public static final Animation ATTACK_TWICE_ANIMATION = Animation.create(36);
   public static final Animation ATTACK_THRICE_ANIMATION = Animation.create(59);
   public static final Animation VERTICAL_ATTACK_ANIMATION = Animation.create(105);
   public static final Animation STOMP_ATTACK_ANIMATION = Animation.create(40);
   public static final Animation ACTIVATE_ANIMATION = Animation.create(45);
   public static final Animation DEACTIVATE_ANIMATION = Animation.create(15);
   private static final Animation[] ANIMATIONS = new Animation[]{
      DIE_ANIMATION,
      HURT_ANIMATION,
      ATTACK_ANIMATION,
      ATTACK_TWICE_ANIMATION,
      ATTACK_THRICE_ANIMATION,
      VERTICAL_ATTACK_ANIMATION,
      STOMP_ATTACK_ANIMATION,
      ACTIVATE_ANIMATION,
      DEACTIVATE_ANIMATION
   };
   private static final float[][] VERTICAL_ATTACK_BLOCK_OFFSETS = new float[][]{{-0.1F, -0.1F}, {-0.1F, 0.1F}, {0.1F, 0.1F}, {0.1F, -0.1F}};
   private static final EntityDataAccessor<Optional<BlockPos>> REST_POSITION = SynchedEntityData.m_135353_(
      EntityWroughtnaut.class, EntityDataSerializers.f_135039_
   );
   private static final EntityDataAccessor<Boolean> ACTIVE = SynchedEntityData.m_135353_(EntityWroughtnaut.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Boolean> ALWAYS_ACTIVE = SynchedEntityData.m_135353_(EntityWroughtnaut.class, EntityDataSerializers.f_135035_);
   public ControlledAnimation walkAnim = new ControlledAnimation(10);
   public boolean swingDirection;
   public boolean vulnerable;
   private EntityWroughtnaut.CeilingDisturbance disturbance;
   public Vec3 leftEyePos;
   public Vec3 rightEyePos;
   public Vec3 leftEyeRot;
   public Vec3 rightEyeRot;

   public EntityWroughtnaut(EntityType<? extends EntityWroughtnaut> type, Level world) {
      super(type, world);
      this.f_21364_ = 30;
      this.active = false;
      this.f_19793_ = 1.0F;
      this.dropAfterDeathAnim = true;
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(1, new AnimationFWNAttackAI(this, 4.0F, 5.0F, 100.0F));
      this.f_21345_
         .m_25352_(1, new AnimationFWNVerticalAttackAI(this, VERTICAL_ATTACK_ANIMATION, (SoundEvent)MMSounds.ENTITY_WROUGHT_WHOOSH.get(), 1.0F, 5.0F, 40.0F));
      this.f_21345_.m_25352_(1, new AnimationFWNStompAttackAI(this, STOMP_ATTACK_ANIMATION));
      this.f_21345_.m_25352_(1, new AnimationTakeDamage<>(this));
      this.f_21345_.m_25352_(1, new AnimationDieAI<>(this));
      this.f_21345_.m_25352_(1, new AnimationActivateAI<>(this, ACTIVATE_ANIMATION));
      this.f_21345_.m_25352_(1, new AnimationDeactivateAI<>(this, DEACTIVATE_ANIMATION));
      this.f_21345_.m_25352_(2, new WroughtnautAttackAI(this));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, 0, true, false, null));
   }

   protected float m_6431_(Pose poseIn, EntityDimensions sizeIn) {
      return sizeIn.f_20378_ * 0.98F;
   }

   protected PathNavigation m_6037_(Level world) {
      return new MMPathNavigateGround(this, world);
   }

   protected BodyRotationControl m_7560_() {
      return new SmartBodyHelper(this);
   }

   protected SoundEvent m_7975_(DamageSource source) {
      return (SoundEvent)MMSounds.ENTITY_WROUGHT_HURT_1.get();
   }

   public SoundEvent m_5592_() {
      this.m_5496_((SoundEvent)MMSounds.ENTITY_WROUGHT_SCREAM.get(), 1.0F, 1.0F);
      return null;
   }

   protected SoundEvent m_7515_() {
      return this.getAnimation() == NO_ANIMATION && this.isActive() ? (SoundEvent)MMSounds.ENTITY_WROUGHT_AMBIENT.get() : null;
   }

   public static Builder createAttributes() {
      return MowzieEntity.createAttributes().m_22268_(Attributes.f_22281_, 30.0).m_22268_(Attributes.f_22276_, 40.0).m_22268_(Attributes.f_22278_, 1.0);
   }

   @Override
   public boolean m_6469_(DamageSource source, float amount) {
      Entity entitySource = source.m_7639_();
      if (entitySource != null) {
         if ((!this.active || this.m_5448_() == null)
            && entitySource instanceof LivingEntity
            && (!(entitySource instanceof Player) || !((Player)entitySource).m_7500_())
            && !(entitySource instanceof EntityWroughtnaut)) {
            this.m_6710_((LivingEntity)entitySource);
         }

         if (this.vulnerable) {
            int arc = 220;
            float entityHitAngle = (float)(
               (Math.atan2(entitySource.m_20189_() - this.m_20189_(), entitySource.m_20185_() - this.m_20185_()) * (180.0 / Math.PI) - 90.0) % 360.0
            );
            float entityAttackingAngle = this.f_20883_ % 360.0F;
            if (entityHitAngle < 0.0F) {
               entityHitAngle += 360.0F;
            }

            if (entityAttackingAngle < 0.0F) {
               entityAttackingAngle += 360.0F;
            }

            float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
            if ((!(entityRelativeAngle <= (float)arc / 2.0F) || !(entityRelativeAngle >= (float)(-arc) / 2.0F))
               && !(entityRelativeAngle >= 360.0F - (float)arc / 2.0F)
               && !(entityRelativeAngle <= (float)(-arc) + 45.0F)) {
               this.setAnimation(NO_ANIMATION);
               return super.m_6469_(source, amount);
            }

            this.m_5496_((SoundEvent)MMSounds.ENTITY_WROUGHT_UNDAMAGED.get(), 0.4F, 2.0F);
            return false;
         }

         this.m_5496_((SoundEvent)MMSounds.ENTITY_WROUGHT_UNDAMAGED.get(), 0.4F, 2.0F);
      } else if (source.m_19378_()) {
         return super.m_6469_(source, amount);
      }

      return false;
   }

   @Override
   public Animation getDeathAnimation() {
      return DIE_ANIMATION;
   }

   @Override
   public Animation getHurtAnimation() {
      return HURT_ANIMATION;
   }

   public boolean m_6051_() {
      return false;
   }

   public boolean m_6063_() {
      return false;
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      this.walkAnim.updatePrevTimer();
      if (this.m_5448_() != null && (!this.m_5448_().m_6084_() || this.m_5448_().m_21223_() <= 0.0F)) {
         this.m_6710_(null);
      }

      if (!this.f_19853_.f_46443_) {
         if (this.isAlwaysActive()) {
            this.setActive(true);
            this.active = true;
         } else if (this.getAnimation() == NO_ANIMATION && !this.m_21525_()) {
            if (this.isActive()) {
               if (this.m_5448_() == null && this.f_20902_ == 0.0F && this.isAtRestPos()) {
                  AnimationHandler.INSTANCE.sendAnimationMessage(this, DEACTIVATE_ANIMATION);
                  this.setActive(false);
               }
            } else if (this.m_5448_() != null && (double)this.targetDistance <= 4.5) {
               AnimationHandler.INSTANCE.sendAnimationMessage(this, ACTIVATE_ANIMATION);
               this.setActive(true);
            }
         }
      }

      if (!this.isActive()) {
         this.m_20334_(0.0, this.m_20184_().f_82480_, 0.0);
         this.m_146922_(this.f_19859_);
      }

      if (this.getAnimation() != NO_ANIMATION || !this.isActive()) {
         this.f_20885_ = this.f_20883_ = this.m_146908_();
      }

      if (!this.isAlwaysActive() && this.m_5448_() == null && this.m_21573_().m_26571_() && !this.isAtRestPos() && this.isActive()) {
         this.updateRestPos();
      }

      if (this.getAnimation() == ATTACK_ANIMATION && this.getAnimationTick() == 1) {
         this.swingDirection = this.f_19796_.m_188499_();
      } else if (this.getAnimation() == ACTIVATE_ANIMATION) {
         int tick = this.getAnimationTick();
         if (tick == 1) {
            this.m_5496_((SoundEvent)MMSounds.ENTITY_WROUGHT_GRUNT_2.get(), 1.0F, 1.0F);
         } else if (tick == 27 || tick == 44) {
            this.m_5496_((SoundEvent)MMSounds.ENTITY_WROUGHT_STEP.get(), 0.5F, 0.5F);
         }
      } else if (this.getAnimation() == VERTICAL_ATTACK_ANIMATION && this.getAnimationTick() == 29) {
         this.doVerticalAttackHitFX();
      } else if (this.getAnimation() == STOMP_ATTACK_ANIMATION) {
         this.doStompFX();
      }

      float moveX = (float)(this.m_20185_() - this.f_19854_);
      float moveZ = (float)(this.m_20189_() - this.f_19856_);
      float speed = Mth.m_14116_(moveX * moveX + moveZ * moveZ);
      if ((double)speed > 0.01) {
         if (this.getAnimation() == NO_ANIMATION) {
            this.walkAnim.increaseTimer();
         }
      } else {
         this.walkAnim.decreaseTimer();
      }

      if (this.getAnimation() != NO_ANIMATION) {
         this.walkAnim.decreaseTimer(2);
      }

      if (this.f_19853_.f_46443_ && this.frame % 20 == 1 && (double)speed > 0.03 && this.getAnimation() == NO_ANIMATION && this.isActive()) {
         this.f_19853_
            .m_7785_(this.m_20185_(), this.m_20186_(), this.m_20189_(), (SoundEvent)MMSounds.ENTITY_WROUGHT_STEP.get(), this.m_5720_(), 0.5F, 0.5F, false);
      }

      this.repelEntities(1.7F, 4.0F, 1.7F, 1.7F);
      if (!this.active
         && !this.f_19853_.f_46443_
         && this.getAnimation() != ACTIVATE_ANIMATION
         && (Boolean)ConfigHandler.COMMON.MOBS.FERROUS_WROUGHTNAUT.healsOutOfBattle.get()) {
         this.m_5634_(0.3F);
      }

      if (this.disturbance != null && this.disturbance.update()) {
         this.disturbance = null;
      }
   }

   @Override
   protected void repelEntities(float x, float y, float z, float radius) {
      super.repelEntities(x, y, z, radius);
   }

   @Override
   public boolean canBePushedByEntity(Entity entity) {
      return false;
   }

   private boolean isAtRestPos() {
      Optional<BlockPos> restPos = this.getRestPos();
      return restPos.isPresent() ? restPos.get().m_123331_(this.m_20183_()) < 36.0 : false;
   }

   private void updateRestPos() {
      boolean reassign = true;
      if (this.getRestPos().isPresent()) {
         BlockPos pos = this.getRestPos().get();
         if (this.m_21573_().m_26519_((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), 0.2)) {
            reassign = false;
         }
      }

      if (reassign) {
         this.setRestPos(this.m_20183_());
      }
   }

   private void doVerticalAttackHitFX() {
      double theta = (double)(this.f_20883_ - 4.0F) * (Math.PI / 180.0);
      double perpX = Math.cos(theta);
      double perpZ = Math.sin(theta);
      double vecX = Math.cos(++theta);
      double vecZ = Math.sin(theta);
      double x = this.m_20185_() + 4.2 * vecX;
      double y = this.m_20191_().f_82289_ + 0.1;
      double z = this.m_20189_() + 4.2 * vecZ;
      int hitY = Mth.m_14107_(this.m_20186_() - 0.2);

      for (int t = 0; t < VERTICAL_ATTACK_BLOCK_OFFSETS.length; t++) {
         float ox = VERTICAL_ATTACK_BLOCK_OFFSETS[t][0];
         float oy = VERTICAL_ATTACK_BLOCK_OFFSETS[t][1];
         int hitX = Mth.m_14107_(x + (double)ox);
         int hitZ = Mth.m_14107_(z + (double)oy);
         BlockPos hit = new BlockPos(hitX, hitY, hitZ);
         BlockState block = this.f_19853_.m_8055_(hit);
         if (block.m_60799_() != RenderShape.INVISIBLE) {
            for (int n = 0; n < 6; n++) {
               double pa = this.f_19796_.m_188500_() * 2.0 * Math.PI;
               double pd = this.f_19796_.m_188500_() * 0.6 + 0.1;
               double px = x + Math.cos(pa) * pd;
               double pz = z + Math.sin(pa) * pd;
               double magnitude = this.f_19796_.m_188500_() * 4.0 + 5.0;
               double velX = perpX * magnitude;
               double velY = this.f_19796_.m_188500_() * 3.0 + 6.0;
               double velZ = perpZ * magnitude;
               if (vecX * (pz - this.m_20189_()) - vecZ * (px - this.m_20185_()) > 0.0) {
                  velX = -velX;
                  velZ = -velZ;
               }

               this.f_19853_.m_7106_(new BlockParticleOption(ParticleTypes.f_123794_, block), px, y, pz, velX, velY, velZ);
            }
         }
      }

      int hitX = Mth.m_14107_(x);
      int ceilY = Mth.m_14107_(this.m_20191_().f_82292_);
      int hitZ = Mth.m_14107_(z);
      int maxHeight = 5;
      int height = 5;

      for (MutableBlockPos pos = new MutableBlockPos(); height-- > 0; ceilY++) {
         pos.m_122178_(hitX, ceilY, hitZ);
         if (this.f_19853_.m_8055_(pos).m_60767_().m_76334_()) {
            break;
         }
      }

      float strength = (float)height / 5.0F;
      if (strength >= 0.0F) {
         int radius = Mth.m_14167_(Mth.m_14116_(1.0F - strength * strength) * 5.0F);
         this.disturbance = new EntityWroughtnaut.CeilingDisturbance(
            hitX, ceilY, hitZ, radius, this.f_19796_.m_188503_(5) + 3, this.f_19796_.m_188503_(60) + 20
         );
      }
   }

   private void doStompFX() {
      double perpFacing = (double)this.f_20883_ * (Math.PI / 180.0);
      double facingAngle = perpFacing + (Math.PI / 2);
      int tick = this.getAnimationTick();
      int maxDistance = 6;
      if (tick > 9 && tick < 17) {
         if (tick == 12) {
            double infront = 1.47;
            double side = -0.21;
            double vx = Math.cos(facingAngle) * 1.47;
            double vz = Math.sin(facingAngle) * 1.47;
            double perpX = Math.cos(perpFacing);
            double perpZ = Math.sin(perpFacing);
            double fx = this.m_20185_() + vx + perpX * -0.21;
            double fy = this.m_20191_().f_82289_ + 0.1;
            double fz = this.m_20189_() + vz + perpZ * -0.21;
            int amount = 16 + this.f_19853_.f_46441_.m_188503_(8);

            while (amount-- > 0) {
               double theta = this.f_19853_.f_46441_.m_188500_() * Math.PI * 2.0;
               double dist = this.f_19853_.f_46441_.m_188500_() * 0.1 + 0.25;
               double sx = Math.cos(theta);
               double sz = Math.sin(theta);
               double px = fx + sx * dist;
               double py = fy + this.f_19853_.f_46441_.m_188500_() * 0.1;
               double pz = fz + sz * dist;
               this.f_19853_.m_7106_(ParticleTypes.f_123762_, px, py, pz, sx * 0.065, 0.0, sz * 0.065);
            }
         }

         if (tick % 2 == 0) {
            int distance = tick / 2 - 2;
            double spread = Math.PI * 2;
            int arcLen = Mth.m_14165_((double)distance * spread);

            for (int i = 0; i < arcLen; i++) {
               double theta = ((double)i / ((double)arcLen - 1.0) - 0.5) * spread + facingAngle;
               double vx = Math.cos(theta);
               double vz = Math.sin(theta);
               double px = this.m_20185_() + vx * (double)distance;
               double pz = this.m_20189_() + vz * (double)distance;
               float factor = 1.0F - (float)distance / 6.0F;
               if (this.f_19853_.f_46441_.m_188499_()) {
                  int amount = this.f_19853_.f_46441_.m_188503_(5);

                  while (amount-- > 0) {
                     double velX = vx * 0.075;
                     double velY = (double)factor * 0.3 + 0.025;
                     double velZ = vz * 0.075;
                     this.f_19853_
                        .m_7106_(
                           ParticleTypes.f_123796_,
                           px + (double)(this.f_19853_.f_46441_.m_188501_() * 2.0F) - 1.0,
                           this.m_20191_().f_82289_ + 0.1 + (double)this.f_19853_.f_46441_.m_188501_() * 1.5,
                           pz + (double)(this.f_19853_.f_46441_.m_188501_() * 2.0F) - 1.0,
                           velX,
                           velY,
                           velZ
                        );
                  }
               }
            }
         }
      }
   }

   @Nullable
   @Override
   public SpawnGroupData m_6518_(
      ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingData, @Nullable CompoundTag compound
   ) {
      this.setRestPos(this.m_20183_());
      return super.m_6518_(world, difficulty, reason, livingData, compound);
   }

   public boolean m_8023_() {
      return true;
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.m_20088_().m_135372_(REST_POSITION, Optional.empty());
      this.m_20088_().m_135372_(ACTIVE, false);
      this.m_20088_().m_135372_(ALWAYS_ACTIVE, false);
   }

   public Optional<BlockPos> getRestPos() {
      return (Optional<BlockPos>)this.m_20088_().m_135370_(REST_POSITION);
   }

   public void setRestPos(BlockPos pos) {
      this.m_20088_().m_135381_(REST_POSITION, Optional.of(pos));
   }

   public boolean isActive() {
      return (Boolean)this.m_20088_().m_135370_(ACTIVE);
   }

   public void setActive(boolean isActive) {
      this.m_20088_().m_135381_(ACTIVE, isActive);
   }

   public boolean isAlwaysActive() {
      return (Boolean)this.m_20088_().m_135370_(ALWAYS_ACTIVE);
   }

   public void setAlwaysActive(boolean isAlwaysActive) {
      this.m_20088_().m_135381_(ALWAYS_ACTIVE, isAlwaysActive);
      if (isAlwaysActive) {
         this.f_21345_.m_25352_(7, new WaterAvoidingRandomStrollGoal(this, 0.2));
         this.f_21345_.m_25352_(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
         this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      }
   }

   @Override
   public void writeSpawnData(FriendlyByteBuf buf) {
      super.writeSpawnData(buf);
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      Optional<BlockPos> restPos = this.getRestPos();
      if (restPos.isPresent()) {
         compound.m_128365_("restPos", NbtUtils.m_129224_(this.getRestPos().get()));
      }

      compound.m_128379_("active", this.isActive());
      compound.m_128379_("alwaysActive", this.isAlwaysActive());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      if (compound.m_128441_("restPos")) {
         this.setRestPos(NbtUtils.m_129239_(compound.m_128469_("restPos")));
      }

      this.setActive(compound.m_128471_("active"));
      this.active = this.isActive();
      this.setAlwaysActive(compound.m_128471_("alwaysActive"));
   }

   @Override
   protected ConfigHandler.CombatConfig getCombatConfig() {
      return ConfigHandler.COMMON.MOBS.FERROUS_WROUGHTNAUT.combatConfig;
   }

   protected void m_7355_(BlockPos pos, BlockState state) {
   }

   @Override
   public Animation[] getAnimations() {
      return ANIMATIONS;
   }

   @Nullable
   protected ResourceLocation m_7582_() {
      return LootTableHandler.FERROUS_WROUGHTNAUT;
   }

   @Override
   public boolean hasBossBar() {
      return (Boolean)ConfigHandler.COMMON.MOBS.FERROUS_WROUGHTNAUT.hasBossBar.get();
   }

   @Override
   protected BossBarColor bossBarColor() {
      return BossBarColor.RED;
   }

   @Override
   public SoundEvent getBossMusic() {
      return (SoundEvent)MMSounds.MUSIC_FERROUS_WROUGHTNAUT_THEME.get();
   }

   @Override
   protected boolean canPlayMusic() {
      return super.canPlayMusic() && (this.active || this.getAnimation() == ACTIVATE_ANIMATION);
   }

   @Override
   public boolean resetHealthOnPlayerRespawn() {
      return (Boolean)ConfigHandler.COMMON.MOBS.FERROUS_WROUGHTNAUT.resetHealthWhenRespawn.get();
   }

   private class CeilingDisturbance {
      private final int ceilX;
      private final int ceilY;
      private final int ceilZ;
      private final int radius;
      private int delay;
      private int remainingTicks;
      private final int duration;

      public CeilingDisturbance(int x, int y, int z, int radius, int delay, int remainingTicks) {
         this.ceilX = x;
         this.ceilY = y;
         this.ceilZ = z;
         this.radius = radius;
         this.delay = delay;
         this.remainingTicks = remainingTicks;
         this.duration = remainingTicks;
      }

      public boolean update() {
         if (--this.delay > 0) {
            return false;
         } else {
            float t = (float)this.remainingTicks / (float)this.duration;
            int amount = Mth.m_14167_((1.0F - Mth.m_14116_(1.0F - t * t)) * (float)this.radius * (float)this.radius * 0.15F);
            boolean playSound = true;
            MutableBlockPos pos = new MutableBlockPos();

            while (amount-- > 0) {
               double theta = EntityWroughtnaut.this.f_19796_.m_188500_() * Math.PI * 2.0;
               double dist = EntityWroughtnaut.this.f_19796_.m_188500_() * (double)this.radius;
               double x = (double)this.ceilX + Math.cos(theta) * dist;
               double y = (double)this.ceilY - 0.1 - EntityWroughtnaut.this.f_19796_.m_188500_() * 0.3;
               double z = (double)this.ceilZ + Math.sin(theta) * dist;
               int blockX = Mth.m_14107_(x);
               int blockZ = Mth.m_14107_(z);
               pos.m_122178_(blockX, this.ceilY, blockZ);
               BlockState block = EntityWroughtnaut.this.f_19853_.m_8055_(pos);
               if (block.m_60799_() != RenderShape.INVISIBLE) {
                  EntityWroughtnaut.this.f_19853_.m_7106_(new BlockParticleOption(ParticleTypes.f_123814_, block), x, y, z, 0.0, 0.0, 0.0);
                  if (playSound && EntityWroughtnaut.this.f_19796_.m_188501_() < 0.075F) {
                     SoundType sound = block.m_60734_().getSoundType(block, EntityWroughtnaut.this.f_19853_, pos, null);
                     EntityWroughtnaut.this.f_19853_
                        .m_7785_(
                           EntityWroughtnaut.this.m_20185_(),
                           EntityWroughtnaut.this.m_20186_(),
                           EntityWroughtnaut.this.m_20189_(),
                           sound.m_56775_(),
                           SoundSource.BLOCKS,
                           sound.m_56773_() * 2.0F,
                           sound.m_56774_() * 0.6F,
                           false
                        );
                     playSound = false;
                  }
               }
            }

            return --this.remainingTicks <= 0;
         }
      }
   }
}
