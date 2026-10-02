package com.bobmowzie.mowziesmobs.server.entity.frostmaw;

import com.bobmowzie.mowziesmobs.client.particle.ParticleCloud;
import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.ParticleRing;
import com.bobmowzie.mowziesmobs.client.particle.ParticleSnowFlake;
import com.bobmowzie.mowziesmobs.server.advancement.AdvancementHandler;
import com.bobmowzie.mowziesmobs.server.ai.MMEntityMoveHelper;
import com.bobmowzie.mowziesmobs.server.ai.MMPathNavigateGround;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationActivateAI;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationAreaAttackAI;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationDeactivateAI;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationDieAI;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationTakeDamage;
import com.bobmowzie.mowziesmobs.server.ai.animation.SimpleAnimationAI;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.LegSolverQuadruped;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.bobmowzie.mowziesmobs.server.entity.MowzieLLibraryEntity;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityCameraShake;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityIceBall;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityIceBreath;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import com.bobmowzie.mowziesmobs.server.loot.LootTableHandler;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.google.common.base.Supplier;
import com.ilexiconn.llibrary.server.animation.Animation;
import com.ilexiconn.llibrary.server.animation.AnimationHandler;
import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemHandlerHelper;

public class EntityFrostmaw extends MowzieLLibraryEntity implements Enemy {
   public static final Animation DIE_ANIMATION = Animation.create(94);
   public static final Animation HURT_ANIMATION = Animation.create(0);
   public static final Animation ROAR_ANIMATION = Animation.create(76);
   public static final Animation SWIPE_ANIMATION = Animation.create(28);
   public static final Animation SWIPE_TWICE_ANIMATION = Animation.create(57);
   public static final Animation ICE_BREATH_ANIMATION = Animation.create(92);
   public static final Animation ICE_BALL_ANIMATION = Animation.create(50);
   public static final Animation ACTIVATE_ANIMATION = Animation.create(118);
   public static final Animation ACTIVATE_NO_CRYSTAL_ANIMATION = Animation.create(100);
   public static final Animation DEACTIVATE_ANIMATION = Animation.create(25);
   public static final Animation DODGE_ANIMATION = Animation.create(15);
   public static final Animation LAND_ANIMATION = Animation.create(14);
   public static final Animation SLAM_ANIMATION = Animation.create(113);
   private static final EntityDataAccessor<Boolean> ACTIVE = SynchedEntityData.m_135353_(EntityFrostmaw.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Boolean> HAS_CRYSTAL = SynchedEntityData.m_135353_(EntityFrostmaw.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Boolean> ALWAYS_ACTIVE = SynchedEntityData.m_135353_(EntityFrostmaw.class, EntityDataSerializers.f_135035_);
   public static final int ICE_BREATH_COOLDOWN = 260;
   public static final int ICE_BALL_COOLDOWN = 200;
   public static final int SLAM_COOLDOWN = 500;
   public static final int DODGE_COOLDOWN = 200;
   public EntityIceBreath iceBreath;
   public boolean swingWhichArm = false;
   private Vec3 prevRightHandPos = new Vec3(0.0, 0.0, 0.0);
   private Vec3 prevLeftHandPos = new Vec3(0.0, 0.0, 0.0);
   private int iceBreathCooldown = 0;
   private int iceBallCooldown = 0;
   private int slamCooldown = 0;
   private int timeWithoutTarget;
   private int shouldDodgeMeasure = 0;
   private int dodgeCooldown = 0;
   private boolean shouldDodge;
   private float dodgeYaw = 0.0F;
   private Vec3 prevTargetPos = new Vec3(0.0, 0.0, 0.0);
   private boolean shouldPlayLandAnimation = false;
   public LegSolverQuadruped legSolver;

   public EntityFrostmaw(EntityType<? extends EntityFrostmaw> type, Level world) {
      super(type, world);
      this.f_19793_ = 1.0F;
      this.frame = this.frame + this.f_19796_.m_188503_(50);
      this.legSolver = new LegSolverQuadruped(1.0F, 2.0F, -1.0F, 1.5F);
      if (world.f_46443_) {
         this.socketPosArray = new Vec3[]{new Vec3(0.0, 0.0, 0.0), new Vec3(0.0, 0.0, 0.0), new Vec3(0.0, 0.0, 0.0)};
      }

      this.active = false;
      this.playsHurtAnimation = false;
      this.m_146922_(this.f_20883_ = this.f_19796_.m_188501_() * 360.0F);
      this.f_21364_ = 60;
      this.f_21342_ = new MMEntityMoveHelper(this, 7.0F);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(5, new MoveTowardsRestrictionGoal(this, 1.0));
      this.f_21345_.m_25352_(7, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.f_21345_.m_25352_(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(2, new AnimationAreaAttackAI<EntityFrostmaw>(this, SWIPE_ANIMATION, null, null, 2.0F, 6.5F, 6.0F, 135.0F, 1.0F, 9) {
         @Override
         public void m_8056_() {
            super.m_8056_();
         }
      });
      this.f_21345_.m_25352_(2, new AnimationAreaAttackAI<EntityFrostmaw>(this, SWIPE_TWICE_ANIMATION, null, null, 1.0F, 6.5F, 6.0F, 135.0F, 1.0F, 9) {
         @Override
         public void m_8056_() {
            super.m_8056_();
         }

         @Override
         public void m_8037_() {
            super.m_8037_();
            if (EntityFrostmaw.this.getAnimationTick() == 21) {
               this.hitEntities();
            }

            if (EntityFrostmaw.this.getAnimationTick() == 16) {
               EntityFrostmaw.this.m_5496_((SoundEvent)MMSounds.ENTITY_FROSTMAW_WHOOSH.get(), 2.0F, 0.7F);
            }

            if (EntityFrostmaw.this.getAnimationTick() == 6) {
               EntityFrostmaw.this.m_5496_((SoundEvent)MMSounds.ENTITY_FROSTMAW_WHOOSH.get(), 2.0F, 0.8F);
            }

            if (EntityFrostmaw.this.m_5448_() != null) {
               EntityFrostmaw.this.f_21365_.m_24960_(EntityFrostmaw.this.m_5448_(), 30.0F, 30.0F);
            }
         }

         @Override
         protected void onAttack(LivingEntity entityTarget, float damageMultiplier, float applyKnockbackMultiplier) {
            super.onAttack(entityTarget, damageMultiplier, applyKnockbackMultiplier);
            if (EntityFrostmaw.this.getAnimationTick() == 21 && entityTarget instanceof Player player && player.m_21254_()) {
               player.m_36384_(true);
            }
         }
      });
      this.f_21345_.m_25352_(2, new SimpleAnimationAI<>(this, ICE_BREATH_ANIMATION, true));
      this.f_21345_.m_25352_(2, new SimpleAnimationAI<EntityFrostmaw>(this, ICE_BALL_ANIMATION, true) {
         @Override
         public void m_8056_() {
            super.m_8056_();
            EntityFrostmaw.this.m_5496_((SoundEvent)MMSounds.ENTITY_FROSTMAW_ICEBALL_CHARGE.get(), 2.0F, 0.9F);
         }
      });
      this.f_21345_.m_25352_(2, new SimpleAnimationAI<>(this, ROAR_ANIMATION, false));
      this.f_21345_.m_25352_(2, new AnimationActivateAI<EntityFrostmaw>(this, ACTIVATE_ANIMATION) {
         @Override
         public void m_8056_() {
            super.m_8056_();
            EntityFrostmaw.this.m_5496_((SoundEvent)MMSounds.ENTITY_FROSTMAW_WAKEUP.get(), 1.0F, 1.0F);
         }
      });
      this.f_21345_.m_25352_(2, new AnimationActivateAI<EntityFrostmaw>(this, ACTIVATE_NO_CRYSTAL_ANIMATION) {
         @Override
         public void m_8056_() {
            super.m_8056_();
            EntityFrostmaw.this.m_5496_((SoundEvent)MMSounds.ENTITY_FROSTMAW_WAKEUP.get(), 1.0F, 1.0F);
         }
      });
      this.f_21345_.m_25352_(2, new AnimationDeactivateAI<>(this, DEACTIVATE_ANIMATION));
      this.f_21345_.m_25352_(2, new SimpleAnimationAI<>(this, LAND_ANIMATION, false));
      this.f_21345_.m_25352_(2, new SimpleAnimationAI<>(this, SLAM_ANIMATION, EnumSet.of(Flag.LOOK)));
      this.f_21345_.m_25352_(2, new SimpleAnimationAI<>(this, DODGE_ANIMATION, EnumSet.of(Flag.MOVE, Flag.JUMP)));
      this.f_21345_.m_25352_(3, new AnimationTakeDamage<>(this));
      this.f_21345_.m_25352_(1, new AnimationDieAI<>(this));
      this.f_21346_.m_25352_(1, new NearestAttackableTargetGoal(this, Player.class, 0, true, false, null));
   }

   protected PathNavigation m_6037_(Level world) {
      return new MMPathNavigateGround(this, world);
   }

   public static Builder createAttributes() {
      return MowzieEntity.createAttributes()
         .m_22268_(Attributes.f_22281_, 10.0)
         .m_22268_(Attributes.f_22276_, 250.0)
         .m_22268_(Attributes.f_22278_, 1.0)
         .m_22268_(Attributes.f_22277_, 50.0)
         .m_22268_(Attributes.f_22279_, 0.3);
   }

   protected float m_6108_() {
      return 0.98F;
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.m_20088_().m_135372_(ACTIVE, false);
      this.m_20088_().m_135372_(HAS_CRYSTAL, true);
      this.m_20088_().m_135372_(ALWAYS_ACTIVE, false);
   }

   public void m_8032_() {
      if (this.active) {
         int i = this.f_19796_.m_188503_(4);
         super.m_8032_();
         if (i == 0 && this.getAnimation() == NO_ANIMATION) {
            AnimationHandler.INSTANCE.sendAnimationMessage(this, ROAR_ANIMATION);
         } else {
            if (i < MMSounds.ENTITY_FROSTMAW_LIVING.size()) {
               this.m_5496_((SoundEvent)((Supplier)MMSounds.ENTITY_FROSTMAW_LIVING.get(i)).get(), 2.0F, 0.8F + this.f_19796_.m_188501_() * 0.3F);
            }
         }
      }
   }

   @Nullable
   protected SoundEvent m_7515_() {
      return super.m_7515_();
   }

   @Override
   public boolean canBePushedByEntity(Entity entity) {
      return false;
   }

   public boolean m_6063_() {
      return false;
   }

   @Override
   protected void repelEntities(float x, float y, float z, float radius) {
      for (Entity entity : this.getPlayersNearby((double)x, (double)y, (double)z, (double)radius)) {
         double angle = (this.getAngleBetweenEntities(this, entity) + 90.0) * Math.PI / 180.0;
         entity.m_20334_(-0.1 * Math.cos(angle), entity.m_20184_().f_82480_, -0.1 * Math.sin(angle));
      }
   }

   @Override
   public void m_8119_() {
      this.m_146922_(this.f_20883_);
      super.m_8119_();
      this.repelEntities(3.8F, 3.8F, 3.8F, 3.8F);
      if (this.m_5448_() != null && (!this.m_5448_().m_6084_() || this.m_5448_().m_21223_() <= 0.0F)) {
         this.m_6710_(null);
      }

      if (this.isAlwaysActive()) {
         this.setActive(true);
      }

      if (this.getActive() && this.getAnimation() != ACTIVATE_ANIMATION && this.getAnimation() != ACTIVATE_NO_CRYSTAL_ANIMATION) {
         this.legSolver.update(this);
         if ((this.getAnimation() == SWIPE_ANIMATION || this.getAnimation() == SWIPE_TWICE_ANIMATION) && this.getAnimationTick() == 3) {
            int i = Mth.m_216271_(this.f_19796_, 0, MMSounds.ENTITY_FROSTMAW_ATTACK.size());
            if (i < MMSounds.ENTITY_FROSTMAW_ATTACK.size()) {
               this.m_5496_((SoundEvent)((Supplier)MMSounds.ENTITY_FROSTMAW_ATTACK.get(i)).get(), 2.0F, 0.9F + this.f_19796_.m_188501_() * 0.2F);
            }
         }

         if (this.getAnimation() == SWIPE_ANIMATION) {
            if (this.getAnimationTick() == 6) {
               this.m_5496_((SoundEvent)MMSounds.ENTITY_FROSTMAW_WHOOSH.get(), 2.0F, 0.8F);
            }

            if (this.m_5448_() != null) {
               this.f_21365_.m_24960_(this.m_5448_(), 30.0F, 30.0F);
            }
         }

         if (this.getAnimation() == ROAR_ANIMATION) {
            if (this.getAnimationTick() == 10) {
               this.m_5496_((SoundEvent)MMSounds.ENTITY_FROSTMAW_ROAR.get(), 4.0F, 1.0F);
               EntityCameraShake.cameraShake(this.f_19853_, this.m_20182_(), 45.0F, 0.03F, 60, 20);
            }

            if (this.getAnimationTick() >= 8 && this.getAnimationTick() < 65) {
               this.doRoarEffects();
            }
         }

         if (this.getAnimation() == LAND_ANIMATION && this.getAnimationTick() == 3) {
            this.m_5496_((SoundEvent)MMSounds.ENTITY_FROSTMAW_LAND.get(), 3.0F, 0.9F);
         }

         if (this.getAnimation() == SLAM_ANIMATION) {
            if (this.getAnimationTick() == 82) {
               this.m_5496_((SoundEvent)MMSounds.ENTITY_FROSTMAW_LIVING_1.get(), 2.0F, 1.0F);
            }

            if (this.m_5448_() != null) {
               this.f_21365_.m_24960_(this.m_5448_(), 30.0F, 30.0F);
            }

            if (this.getAnimationTick() == 82) {
               int i = Mth.m_216271_(this.f_19796_, 0, MMSounds.ENTITY_FROSTMAW_ATTACK.size() - 1);
               if (i < MMSounds.ENTITY_FROSTMAW_ATTACK.size()) {
                  this.m_5496_((SoundEvent)((Supplier)MMSounds.ENTITY_FROSTMAW_ATTACK.get(i)).get(), 2.0F, 0.9F + this.f_19796_.m_188501_() * 0.2F);
               }

               this.m_5496_((SoundEvent)MMSounds.ENTITY_FROSTMAW_WHOOSH.get(), 2.0F, 0.7F);
            }

            if (this.getAnimationTick() == 87) {
               this.m_5496_((SoundEvent)MMSounds.ENTITY_FROSTMAW_LAND.get(), 3.0F, 1.0F);
               float radius = 4.0F;
               float slamPosX = (float)(this.m_20185_() + (double)radius * Math.cos(Math.toRadians((double)(this.m_146908_() + 90.0F))));
               float slamPosZ = (float)(this.m_20189_() + (double)radius * Math.sin(Math.toRadians((double)(this.m_146908_() + 90.0F))));
               if (this.f_19853_.f_46443_) {
                  this.f_19853_
                     .m_7106_(
                        new ParticleRing.RingData(0.0F, (float) (Math.PI / 2), 17, 1.0F, 1.0F, 1.0F, 1.0F, 60.0F, false, ParticleRing.EnumRingBehavior.GROW),
                        (double)slamPosX,
                        this.m_20186_() + 0.2F,
                        (double)slamPosZ,
                        0.0,
                        0.0,
                        0.0
                     );
               }

               AABB hitBox = new AABB(new BlockPos((double)(slamPosX - 0.5F), this.m_20186_(), (double)(slamPosZ - 0.5F))).m_82377_(3.0, 3.0, 3.0);

               for (LivingEntity entity : this.f_19853_.m_45976_(LivingEntity.class, hitBox)) {
                  if (entity != this) {
                     this.doHurtTarget(entity, 4.0F, 1.0F);
                     if (entity.m_21254_()) {
                        entity.m_21211_().m_41622_(400, entity, p -> p.m_21190_(entity.m_7655_()));
                     }
                  }
               }

               EntityCameraShake.cameraShake(this.f_19853_, new Vec3((double)slamPosX, this.m_20186_(), (double)slamPosZ), 30.0F, 0.1F, 0, 20);
            }
         }

         if (this.getAnimation() == DODGE_ANIMATION && !this.f_19853_.f_46443_) {
            this.m_21573_().m_26573_();
            if (this.getAnimationTick() == 2) {
               this.dodgeYaw = (float)Math.toRadians((double)(this.targetAngle + 90.0F + this.f_19796_.m_188501_() * 150.0F - 75.0F));
            }

            if (this.getAnimationTick() == 6 && (this.f_19861_ || this.m_20077_() || this.m_20069_())) {
               float speed = 1.7F;
               Vec3 m = this.m_20184_().m_82520_((double)speed * Math.cos((double)this.dodgeYaw), 0.0, (double)speed * Math.sin((double)this.dodgeYaw));
               this.m_20334_(m.f_82479_, 0.6, m.f_82481_);
            }

            if (this.m_5448_() != null) {
               this.f_21365_.m_24960_(this.m_5448_(), 30.0F, 30.0F);
            }
         }

         if (this.getAnimation() == ICE_BREATH_ANIMATION) {
            if (this.m_5448_() != null) {
               this.f_21365_.m_24960_(this.m_5448_(), 30.0F, 30.0F);
               this.m_21391_(this.m_5448_(), 30.0F, 30.0F);
            }

            Vec3 mouthPos = new Vec3(2.3, 2.65, 0.0);
            mouthPos = mouthPos.m_82524_((float)Math.toRadians((double)(-this.m_146908_() - 90.0F)));
            mouthPos = mouthPos.m_82549_(this.m_20182_());
            mouthPos = mouthPos.m_82549_(
               new Vec3(0.0, 0.0, 1.0).m_82496_((float)Math.toRadians((double)(-this.m_146909_()))).m_82524_((float)Math.toRadians((double)(-this.f_20885_)))
            );
            if (this.getAnimationTick() == 13) {
               this.iceBreath = new EntityIceBreath((EntityType<? extends EntityIceBreath>)EntityHandler.ICE_BREATH.get(), this.f_19853_, this);
               this.iceBreath.m_19890_(mouthPos.f_82479_, mouthPos.f_82480_, mouthPos.f_82481_, this.f_20885_, this.m_146909_() + 10.0F);
               if (!this.f_19853_.f_46443_) {
                  this.f_19853_.m_7967_(this.iceBreath);
               }
            }

            if (this.iceBreath != null) {
               this.iceBreath.m_19890_(mouthPos.f_82479_, mouthPos.f_82480_, mouthPos.f_82481_, this.f_20885_, this.m_146909_() + 10.0F);
            }
         }

         if (this.getAnimation() == ICE_BALL_ANIMATION) {
            if (this.m_5448_() != null) {
               this.f_21365_.m_24960_(this.m_5448_(), 15.0F, 15.0F);
            }

            Vec3 projectilePos = new Vec3(2.0, 1.9, 0.0);
            projectilePos = projectilePos.m_82524_((float)Math.toRadians((double)(-this.m_146908_() - 90.0F)));
            projectilePos = projectilePos.m_82549_(this.m_20182_());
            projectilePos = projectilePos.m_82549_(
               new Vec3(0.0, 0.0, 1.0).m_82496_((float)Math.toRadians((double)(-this.m_146909_()))).m_82524_((float)Math.toRadians((double)(-this.f_20885_)))
            );
            if (this.f_19853_.f_46443_) {
               Vec3 mouthPosx = this.socketPosArray[2];
               if (this.getAnimationTick() < 12) {
                  for (int i = 0; i < 6; i++) {
                     Vec3 particlePos = new Vec3(3.5, 0.0, 0.0);
                     particlePos = particlePos.m_82524_((float)((double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI));
                     particlePos = particlePos.m_82496_((float)((double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI));
                     float value = this.f_19796_.m_188501_() * 0.15F;
                     this.f_19853_
                        .m_7106_(
                           new ParticleCloud.CloudData(
                              (ParticleType<ParticleCloud.CloudData>)ParticleHandler.CLOUD.get(),
                              0.75F + value,
                              0.75F + value,
                              1.0F,
                              5.0F + this.f_19796_.m_188501_() * 15.0F,
                              30,
                              ParticleCloud.EnumCloudBehavior.CONSTANT,
                              1.0F
                           ),
                           mouthPosx.f_82479_ + particlePos.f_82479_,
                           mouthPosx.f_82480_ + particlePos.f_82480_,
                           mouthPosx.f_82481_ + particlePos.f_82481_,
                           -0.1 * particlePos.f_82479_,
                           -0.1 * particlePos.f_82480_,
                           -0.1 * particlePos.f_82481_
                        );
                  }

                  for (int i = 0; i < 8; i++) {
                     Vec3 particlePos = new Vec3(3.5, 0.0, 0.0);
                     particlePos = particlePos.m_82524_((float)((double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI));
                     particlePos = particlePos.m_82496_((float)((double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI));
                     this.f_19853_
                        .m_7106_(
                           new ParticleSnowFlake.SnowflakeData(40.0F, false),
                           mouthPosx.f_82479_ + particlePos.f_82479_,
                           mouthPosx.f_82480_ + particlePos.f_82480_,
                           mouthPosx.f_82481_ + particlePos.f_82481_,
                           -0.07 * particlePos.f_82479_,
                           -0.07 * particlePos.f_82480_,
                           -0.07 * particlePos.f_82481_
                        );
                  }
               }
            }

            if (this.getAnimationTick() == 32 && this.m_5448_() != null) {
               this.prevTargetPos = this.m_5448_().m_20182_().m_82549_(new Vec3(0.0, (double)this.m_5448_().m_20206_() / 2.0, 0.0));
            }

            if (this.getAnimationTick() == 33) {
               this.m_5496_((SoundEvent)MMSounds.ENTITY_FROSTMAW_ICEBALL_SHOOT.get(), 2.0F, 0.7F);
               EntityIceBall iceBall = new EntityIceBall((EntityType<? extends EntityIceBall>)EntityHandler.ICE_BALL.get(), this.f_19853_, this);
               iceBall.m_19890_(projectilePos.f_82479_, projectilePos.f_82480_, projectilePos.f_82481_, this.f_20885_, this.m_146909_() + 10.0F);
               float projSpeed = 1.6F;
               if (this.m_5448_() != null) {
                  float ticksUntilHit = this.targetDistance / projSpeed;
                  Vec3 targetPos = this.m_5448_().m_20182_().m_82549_(new Vec3(0.0, (double)this.m_5448_().m_20206_() / 2.0, 0.0));
                  Vec3 targetMovement = targetPos.m_82546_(this.prevTargetPos).m_82490_((double)ticksUntilHit * 0.95);
                  targetMovement = targetMovement.m_82492_(0.0, targetMovement.f_82480_, 0.0);
                  Vec3 futureTargetPos = targetPos.m_82549_(targetMovement);
                  Vec3 projectileMid = projectilePos.m_82549_(new Vec3(0.0, (double)iceBall.m_20206_() / 2.0, 0.0));
                  Vec3 shootVec = futureTargetPos.m_82546_(projectileMid).m_82541_();
                  iceBall.shoot(shootVec.f_82479_, shootVec.f_82480_, shootVec.f_82481_, projSpeed, 0.0F);
               } else {
                  iceBall.shoot(this.m_20154_().f_82479_, this.m_20154_().f_82480_, this.m_20154_().f_82481_, projSpeed, 0.0F);
               }

               if (!this.f_19853_.f_46443_) {
                  this.f_19853_.m_7967_(iceBall);
               }
            }
         }

         this.spawnSwipeParticles();
         if ((double)this.f_19789_ > 0.2 && !this.f_19861_ && this.m_21225_() != DamageSource.f_19308_) {
            this.shouldPlayLandAnimation = true;
         }

         if (this.f_19861_ && this.shouldPlayLandAnimation && this.getAnimation() != DODGE_ANIMATION) {
            if (!this.f_19853_.f_46443_ && this.getAnimation() == NO_ANIMATION) {
               AnimationHandler.INSTANCE.sendAnimationMessage(this, LAND_ANIMATION);
            }

            this.shouldPlayLandAnimation = false;
         }

         if (this.m_5448_() != null) {
            this.timeWithoutTarget = 0;
            float entityHitAngle = (float)(
               (Math.atan2(this.m_5448_().m_20189_() - this.m_20189_(), this.m_5448_().m_20185_() - this.m_20185_()) * (180.0 / Math.PI) - 90.0) % 360.0
            );
            float entityAttackingAngle = this.f_20883_ % 360.0F;
            if (entityHitAngle < 0.0F) {
               entityHitAngle += 360.0F;
            }

            if (entityAttackingAngle < 0.0F) {
               entityAttackingAngle += 360.0F;
            }

            float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
            if (this.m_21573_().m_26571_()
               && (!(entityRelativeAngle <= 15.0F) || !(entityRelativeAngle >= -15.0F))
               && !(entityRelativeAngle >= 345.0F)
               && !(entityRelativeAngle <= -345.0F)) {
               this.m_21573_().m_5624_(this.m_5448_(), 0.85);
            }

            if (this.shouldDodgeMeasure >= 12) {
               this.shouldDodge = true;
            }

            if (this.targetDistance < 4.0F && this.shouldDodge && this.getAnimation() == NO_ANIMATION) {
               this.shouldDodge = false;
               this.dodgeCooldown = 200;
               this.shouldDodgeMeasure = 0;
               AnimationHandler.INSTANCE.sendAnimationMessage(this, DODGE_ANIMATION);
            }

            if ((double)this.targetDistance > 5.5 && (this.getAnimation() != ICE_BREATH_ANIMATION || !((double)this.targetDistance < 7.5))) {
               if (this.getAnimation() != SLAM_ANIMATION) {
                  this.m_21573_().m_5624_(this.m_5448_(), 1.0);
               } else {
                  this.m_21573_().m_5624_(this.m_5448_(), 0.95);
               }
            } else {
               this.m_21573_().m_26573_();
            }

            if ((double)this.targetDistance <= 8.5
               && this.getAnimation() == NO_ANIMATION
               && this.slamCooldown <= 0
               && this.f_19796_.m_188503_(4) == 0
               && (double)this.getHealthRatio() < 0.6) {
               AnimationHandler.INSTANCE.sendAnimationMessage(this, SLAM_ANIMATION);
               this.slamCooldown = 500;
            }

            if ((double)this.targetDistance <= 6.5 && this.getAnimation() == NO_ANIMATION) {
               if (this.f_19796_.m_188503_(4) == 0) {
                  AnimationHandler.INSTANCE.sendAnimationMessage(this, SWIPE_TWICE_ANIMATION);
               } else {
                  AnimationHandler.INSTANCE.sendAnimationMessage(this, SWIPE_ANIMATION);
               }
            }

            if ((double)this.targetDistance <= 13.5
               && this.getAnimation() == NO_ANIMATION
               && this.iceBreathCooldown <= 0
               && this.getHasCrystal()
               && (this.f_19861_ || this.f_19798_)) {
               AnimationHandler.INSTANCE.sendAnimationMessage(this, ICE_BREATH_ANIMATION);
               this.iceBreathCooldown = 260;
            }

            if ((double)this.targetDistance >= 14.5
               && this.getAnimation() == NO_ANIMATION
               && this.iceBallCooldown <= 0
               && this.getHasCrystal()
               && (this.f_19861_ || this.f_19798_)) {
               AnimationHandler.INSTANCE.sendAnimationMessage(this, ICE_BALL_ANIMATION);
               this.iceBallCooldown = 200;
            }
         } else if (!this.f_19853_.f_46443_ && !this.isAlwaysActive()) {
            this.timeWithoutTarget++;
            if (this.timeWithoutTarget > 1200 || this.f_19853_.m_46791_() == Difficulty.PEACEFUL) {
               this.timeWithoutTarget = 0;
               if (this.getAnimation() == NO_ANIMATION) {
                  AnimationHandler.INSTANCE.sendAnimationMessage(this, DEACTIVATE_ANIMATION);
                  this.setActive(false);
               }
            }
         }
      } else {
         this.m_21573_().m_26573_();
         this.m_20334_(0.0, this.m_20184_().f_82480_, 0.0);
         this.f_20883_ = this.f_20884_;
         if (!this.f_19853_.f_46443_ && this.getAnimation() != ACTIVATE_ANIMATION && (Boolean)ConfigHandler.COMMON.MOBS.FROSTMAW.healsOutOfBattle.get()) {
            this.m_5634_(0.3F);
         }

         if (this.m_5448_() != null && this.m_5448_().m_21023_(MobEffects.f_19609_)) {
            this.m_6710_(null);
         }

         if (!this.getAttackableEntityLivingBaseNearby(8.0, 8.0, 8.0, 8.0).isEmpty()
            && this.m_5448_() != null
            && this.getAnimation() == NO_ANIMATION
            && this.f_19853_.m_46791_() != Difficulty.PEACEFUL) {
            if (this.getHasCrystal()) {
               AnimationHandler.INSTANCE.sendAnimationMessage(this, ACTIVATE_ANIMATION);
            } else {
               AnimationHandler.INSTANCE.sendAnimationMessage(this, ACTIVATE_NO_CRYSTAL_ANIMATION);
            }

            this.setActive(true);
         }

         if ((Boolean)ConfigHandler.COMMON.MOBS.FROSTMAW.stealableIceCrystal.get()
            && this.getHasCrystal()
            && this.f_19797_ > 20
            && this.getAnimation() == NO_ANIMATION) {
            Vec3 crystalPos = new Vec3(1.6, 0.4, 1.8);
            crystalPos = crystalPos.m_82524_((float)Math.toRadians((double)(-this.m_146908_() - 90.0F)));
            crystalPos = crystalPos.m_82549_(this.m_20182_());

            for (Player player : this.getPlayersNearby(8.0, 8.0, 8.0, 8.0)) {
               if (player.m_20182_().m_82554_(crystalPos) <= 1.8 && (player.m_7500_() || player.m_20145_()) && !isInventoryFull(player.m_150109_())) {
                  ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(ItemHandler.ICE_CRYSTAL));
                  this.setHasCrystal(false);
                  if (this.f_19853_.m_46791_() != Difficulty.PEACEFUL) {
                     AnimationHandler.INSTANCE.sendAnimationMessage(this, ACTIVATE_NO_CRYSTAL_ANIMATION);
                     this.setActive(true);
                  }

                  if (player instanceof ServerPlayer) {
                     AdvancementHandler.STEAL_ICE_CRYSTAL_TRIGGER.trigger((ServerPlayer)player);
                  }
                  break;
               }
            }
         }
      }

      if (this.getAnimation() == ACTIVATE_ANIMATION || this.getAnimation() == ACTIVATE_NO_CRYSTAL_ANIMATION) {
         if (this.getAnimation() == ACTIVATE_ANIMATION && this.getAnimationTick() == 18) {
            this.m_5496_((SoundEvent)((Supplier)MMSounds.ENTITY_FROSTMAW_ATTACK.get(0)).get(), 1.5F, 1.0F);
         }

         if (this.getAnimation() == ACTIVATE_ANIMATION && this.getAnimationTick() == 52
            || this.getAnimation() == ACTIVATE_NO_CRYSTAL_ANIMATION && this.getAnimationTick() == 34) {
            this.m_5496_((SoundEvent)MMSounds.ENTITY_FROSTMAW_ROAR.get(), 4.0F, 1.0F);
            EntityCameraShake.cameraShake(this.f_19853_, this.m_20182_(), 45.0F, 0.03F, 60, 20);
         }

         if (this.getAnimation() == ACTIVATE_ANIMATION && this.getAnimationTick() >= 51 && this.getAnimationTick() < 108
            || this.getAnimation() == ACTIVATE_NO_CRYSTAL_ANIMATION && this.getAnimationTick() >= 33 && this.getAnimationTick() < 90) {
            this.doRoarEffects();
         }
      }

      if (this.f_19853_.f_46443_ && (this.getAnimation() == SWIPE_ANIMATION || this.getAnimation() == SWIPE_TWICE_ANIMATION) && this.getAnimationTick() == 1) {
         this.swingWhichArm = this.f_19796_.m_188499_();
      }

      float moveX = (float)(this.m_20185_() - this.f_19854_);
      float moveZ = (float)(this.m_20189_() - this.f_19856_);
      float speed = Mth.m_14116_(moveX * moveX + moveZ * moveZ);
      if (this.frame % 16 == 5 && (double)speed > 0.05 && this.active) {
         this.m_5496_((SoundEvent)MMSounds.ENTITY_FROSTMAW_STEP.get(), 3.0F, 0.8F + this.f_19796_.m_188501_() * 0.2F);
         EntityCameraShake.cameraShake(this.f_19853_, this.m_20182_(), 20.0F, 0.03F, 0, 10);
      }

      if (this.frame % 118 == 1 && !this.active) {
         int i = Mth.m_216271_(this.f_19796_, 0, 1);
         this.m_5496_((SoundEvent)((Supplier)MMSounds.ENTITY_FROSTMAW_BREATH.get(i)).get(), 1.5F, 1.1F + this.f_19796_.m_188501_() * 0.1F);
      }

      if (this.iceBreathCooldown > 0) {
         this.iceBreathCooldown--;
      }

      if (this.iceBallCooldown > 0) {
         this.iceBallCooldown--;
      }

      if (this.slamCooldown > 0) {
         this.slamCooldown--;
      }

      if (this.shouldDodgeMeasure > 0 && this.f_19797_ % 7 == 0) {
         this.shouldDodgeMeasure--;
      }

      if (this.dodgeCooldown > 0) {
         this.dodgeCooldown--;
      }

      this.f_19859_ = this.m_146908_();
   }

   private void doRoarEffects() {
      if (this.getHasCrystal()) {
         for (LivingEntity entity : this.getEntityLivingBaseNearby(10.0, 3.0, 10.0, 10.0)) {
            if (entity != this) {
               double angle = (this.getAngleBetweenEntities(this, entity) + 90.0) * Math.PI / 180.0;
               double distance = (double)(this.m_20270_(entity) - 4.0F);
               entity.m_20256_(
                  entity.m_20184_()
                     .m_82520_(
                        Math.min(1.0 / (distance * distance), 1.0) * -1.0 * Math.cos(angle),
                        0.0,
                        Math.min(1.0 / (distance * distance), 1.0) * -1.0 * Math.sin(angle)
                     )
               );
            }
         }

         if (this.getAnimationTick() % 12 == 0 && this.f_19853_.f_46443_) {
            int particleCount = 15;

            for (int i = 1; i <= particleCount; i++) {
               double yaw = (double)((float)i * 360.0F / (float)particleCount);
               double speed = 0.9;
               double xSpeed = speed * Math.cos(Math.toRadians(yaw));
               double zSpeed = speed * Math.sin(Math.toRadians(yaw));
               this.f_19853_
                  .m_7106_(
                     new ParticleCloud.CloudData(
                        (ParticleType<ParticleCloud.CloudData>)ParticleHandler.CLOUD.get(),
                        0.75F,
                        0.75F,
                        1.0F,
                        40.0F,
                        22,
                        ParticleCloud.EnumCloudBehavior.GROW,
                        1.0F
                     ),
                     this.m_20185_(),
                     this.m_20186_() + 1.0,
                     this.m_20189_(),
                     xSpeed,
                     0.0,
                     zSpeed
                  );
            }

            for (int i = 1; i <= particleCount; i++) {
               double yaw = (double)((float)i * 360.0F / (float)particleCount);
               double speed = 0.65;
               double xSpeed = speed * Math.cos(Math.toRadians(yaw));
               double zSpeed = speed * Math.sin(Math.toRadians(yaw));
               this.f_19853_
                  .m_7106_(
                     new ParticleCloud.CloudData(
                        (ParticleType<ParticleCloud.CloudData>)ParticleHandler.CLOUD.get(),
                        0.75F,
                        0.75F,
                        1.0F,
                        35.0F,
                        22,
                        ParticleCloud.EnumCloudBehavior.GROW,
                        1.0F
                     ),
                     this.m_20185_(),
                     this.m_20186_() + 1.0,
                     this.m_20189_(),
                     xSpeed,
                     0.0,
                     zSpeed
                  );
            }
         }
      }
   }

   private static boolean isInventoryFull(Inventory inventory) {
      for (ItemStack itemstack : inventory.f_35974_) {
         if (itemstack.m_41619_()) {
            return false;
         }
      }

      return true;
   }

   public boolean m_5545_(LevelAccessor world, MobSpawnType reason) {
      for (LivingEntity nearbyEntity : this.getEntityLivingBaseNearby(100.0, 100.0, 100.0, 100.0)) {
         if (nearbyEntity instanceof EntityFrostmaw || nearbyEntity instanceof Villager) {
            return false;
         }
      }

      return super.m_5545_(world, reason);
   }

   public int m_5792_() {
      return 1;
   }

   @Nullable
   @Override
   public SpawnGroupData m_6518_(
      ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingData, @Nullable CompoundTag compound
   ) {
      this.setHasCrystal(true);
      return super.m_6518_(world, difficulty, reason, livingData, compound);
   }

   private void spawnSwipeParticles() {
      if (this.f_19853_.f_46443_ && this.getHasCrystal()) {
         double motionX = this.m_20184_().m_7096_();
         double motionY = this.m_20184_().m_7098_();
         double motionZ = this.m_20184_().m_7094_();
         int snowflakeDensity = 4;
         float snowflakeRandomness = 0.5F;
         int cloudDensity = 2;
         float cloudRandomness = 0.5F;
         if (this.getAnimation() == SWIPE_ANIMATION || this.getAnimation() == SWIPE_TWICE_ANIMATION) {
            Vec3 rightHandPos = this.socketPosArray[0];
            Vec3 leftHandPos = this.socketPosArray[1];
            if (this.getAnimation() == SWIPE_ANIMATION) {
               if (this.getAnimationTick() > 8 && this.getAnimationTick() < 14) {
                  if (this.swingWhichArm) {
                     double length = this.prevRightHandPos.m_82546_(rightHandPos).m_82553_();
                     int numClouds = (int)Math.floor(2.0 * length);

                     for (int i = 0; i < numClouds; i++) {
                        double x = this.prevRightHandPos.f_82479_ + (double)i * (rightHandPos.f_82479_ - this.prevRightHandPos.f_82479_) / (double)numClouds;
                        double y = this.prevRightHandPos.f_82480_ + (double)i * (rightHandPos.f_82480_ - this.prevRightHandPos.f_82480_) / (double)numClouds;
                        double z = this.prevRightHandPos.f_82481_ + (double)i * (rightHandPos.f_82481_ - this.prevRightHandPos.f_82481_) / (double)numClouds;

                        for (int j = 0; j < snowflakeDensity; j++) {
                           float xOffset = snowflakeRandomness * (2.0F * this.f_19796_.m_188501_() - 1.0F);
                           float yOffset = snowflakeRandomness * (2.0F * this.f_19796_.m_188501_() - 1.0F);
                           float zOffset = snowflakeRandomness * (2.0F * this.f_19796_.m_188501_() - 1.0F);
                           this.f_19853_
                              .m_7106_(
                                 new ParticleSnowFlake.SnowflakeData(40.0F, false),
                                 x + (double)xOffset,
                                 y + (double)yOffset,
                                 z + (double)zOffset,
                                 motionX,
                                 motionY - 0.01F,
                                 motionZ
                              );
                        }

                        for (int j = 0; j < cloudDensity; j++) {
                           float xOffset = cloudRandomness * (2.0F * this.f_19796_.m_188501_() - 1.0F);
                           float yOffset = cloudRandomness * (2.0F * this.f_19796_.m_188501_() - 1.0F);
                           float zOffset = cloudRandomness * (2.0F * this.f_19796_.m_188501_() - 1.0F);
                           float value = this.f_19796_.m_188501_() * 0.1F;
                           this.f_19853_
                              .m_7106_(
                                 new ParticleCloud.CloudData(
                                    (ParticleType<ParticleCloud.CloudData>)ParticleHandler.CLOUD.get(),
                                    0.8F + value,
                                    0.8F + value,
                                    1.0F,
                                    (float)(10.0 + this.f_19796_.m_188500_() * 10.0),
                                    40,
                                    ParticleCloud.EnumCloudBehavior.SHRINK,
                                    1.0F
                                 ),
                                 x + (double)xOffset,
                                 y + (double)yOffset,
                                 z + (double)zOffset,
                                 motionX,
                                 motionY,
                                 motionZ
                              );
                        }
                     }
                  } else {
                     double length = this.prevLeftHandPos.m_82546_(leftHandPos).m_82553_();
                     int numClouds = (int)Math.floor(2.5 * length);

                     for (int i = 0; i < numClouds; i++) {
                        double x = this.prevLeftHandPos.f_82479_ + (double)i * (leftHandPos.f_82479_ - this.prevLeftHandPos.f_82479_) / (double)numClouds;
                        double y = this.prevLeftHandPos.f_82480_ + (double)i * (leftHandPos.f_82480_ - this.prevLeftHandPos.f_82480_) / (double)numClouds;
                        double z = this.prevLeftHandPos.f_82481_ + (double)i * (leftHandPos.f_82481_ - this.prevLeftHandPos.f_82481_) / (double)numClouds;

                        for (int j = 0; j < snowflakeDensity; j++) {
                           float xOffset = snowflakeRandomness * (2.0F * this.f_19796_.m_188501_() - 1.0F);
                           float yOffset = snowflakeRandomness * (2.0F * this.f_19796_.m_188501_() - 1.0F);
                           float zOffset = snowflakeRandomness * (2.0F * this.f_19796_.m_188501_() - 1.0F);
                           this.f_19853_
                              .m_7106_(
                                 new ParticleSnowFlake.SnowflakeData(40.0F, false),
                                 x + (double)xOffset,
                                 y + (double)yOffset,
                                 z + (double)zOffset,
                                 motionX,
                                 motionY - 0.01F,
                                 motionZ
                              );
                        }

                        for (int j = 0; j < cloudDensity; j++) {
                           float xOffset = cloudRandomness * (2.0F * this.f_19796_.m_188501_() - 1.0F);
                           float yOffset = cloudRandomness * (2.0F * this.f_19796_.m_188501_() - 1.0F);
                           float zOffset = cloudRandomness * (2.0F * this.f_19796_.m_188501_() - 1.0F);
                           float value = this.f_19796_.m_188501_() * 0.1F;
                           this.f_19853_
                              .m_7106_(
                                 new ParticleCloud.CloudData(
                                    (ParticleType<ParticleCloud.CloudData>)ParticleHandler.CLOUD.get(),
                                    0.8F + value,
                                    0.8F + value,
                                    1.0F,
                                    (float)(10.0 + this.f_19796_.m_188500_() * 10.0),
                                    40,
                                    ParticleCloud.EnumCloudBehavior.SHRINK,
                                    1.0F
                                 ),
                                 x + (double)xOffset,
                                 y + (double)yOffset,
                                 z + (double)zOffset,
                                 motionX,
                                 motionY,
                                 motionZ
                              );
                        }
                     }
                  }
               }
            } else if ((!this.swingWhichArm || this.getAnimationTick() <= 8 || this.getAnimationTick() >= 14)
               && (this.swingWhichArm || this.getAnimationTick() <= 19 || this.getAnimationTick() >= 25)) {
               if (!this.swingWhichArm && this.getAnimationTick() > 8 && this.getAnimationTick() < 14
                  || this.swingWhichArm && this.getAnimationTick() > 19 && this.getAnimationTick() < 25) {
                  double length = this.prevLeftHandPos.m_82546_(leftHandPos).m_82553_();
                  int numClouds = (int)Math.floor(2.5 * length);

                  for (int i = 0; i < numClouds; i++) {
                     double x = this.prevLeftHandPos.f_82479_ + (double)i * (leftHandPos.f_82479_ - this.prevLeftHandPos.f_82479_) / (double)numClouds;
                     double y = this.prevLeftHandPos.f_82480_ + (double)i * (leftHandPos.f_82480_ - this.prevLeftHandPos.f_82480_) / (double)numClouds;
                     double z = this.prevLeftHandPos.f_82481_ + (double)i * (leftHandPos.f_82481_ - this.prevLeftHandPos.f_82481_) / (double)numClouds;

                     for (int j = 0; j < snowflakeDensity; j++) {
                        float xOffset = snowflakeRandomness * (2.0F * this.f_19796_.m_188501_() - 1.0F);
                        float yOffset = snowflakeRandomness * (2.0F * this.f_19796_.m_188501_() - 1.0F);
                        float zOffset = snowflakeRandomness * (2.0F * this.f_19796_.m_188501_() - 1.0F);
                        this.f_19853_
                           .m_7106_(
                              new ParticleSnowFlake.SnowflakeData(40.0F, false),
                              x + (double)xOffset,
                              y + (double)yOffset,
                              z + (double)zOffset,
                              motionX,
                              motionY - 0.01F,
                              motionZ
                           );
                     }

                     for (int j = 0; j < cloudDensity; j++) {
                        float xOffset = cloudRandomness * (2.0F * this.f_19796_.m_188501_() - 1.0F);
                        float yOffset = cloudRandomness * (2.0F * this.f_19796_.m_188501_() - 1.0F);
                        float zOffset = cloudRandomness * (2.0F * this.f_19796_.m_188501_() - 1.0F);
                        float value = this.f_19796_.m_188501_() * 0.1F;
                        this.f_19853_
                           .m_7106_(
                              new ParticleCloud.CloudData(
                                 (ParticleType<ParticleCloud.CloudData>)ParticleHandler.CLOUD.get(),
                                 0.8F + value,
                                 0.8F + value,
                                 1.0F,
                                 (float)(10.0 + this.f_19796_.m_188500_() * 10.0),
                                 40,
                                 ParticleCloud.EnumCloudBehavior.SHRINK,
                                 1.0F
                              ),
                              x + (double)xOffset,
                              y + (double)yOffset,
                              z + (double)zOffset,
                              motionX,
                              motionY,
                              motionZ
                           );
                     }
                  }
               }
            } else {
               double length = this.prevRightHandPos.m_82546_(rightHandPos).m_82553_();
               int numClouds = (int)Math.floor(2.0 * length);

               for (int i = 0; i < numClouds; i++) {
                  double x = this.prevRightHandPos.f_82479_ + (double)i * (rightHandPos.f_82479_ - this.prevRightHandPos.f_82479_) / (double)numClouds;
                  double y = this.prevRightHandPos.f_82480_ + (double)i * (rightHandPos.f_82480_ - this.prevRightHandPos.f_82480_) / (double)numClouds;
                  double z = this.prevRightHandPos.f_82481_ + (double)i * (rightHandPos.f_82481_ - this.prevRightHandPos.f_82481_) / (double)numClouds;

                  for (int j = 0; j < snowflakeDensity; j++) {
                     float xOffset = snowflakeRandomness * (2.0F * this.f_19796_.m_188501_() - 1.0F);
                     float yOffset = snowflakeRandomness * (2.0F * this.f_19796_.m_188501_() - 1.0F);
                     float zOffset = snowflakeRandomness * (2.0F * this.f_19796_.m_188501_() - 1.0F);
                     this.f_19853_
                        .m_7106_(
                           new ParticleSnowFlake.SnowflakeData(40.0F, false),
                           x + (double)xOffset,
                           y + (double)yOffset,
                           z + (double)zOffset,
                           motionX,
                           motionY - 0.01F,
                           motionZ
                        );
                  }

                  for (int j = 0; j < cloudDensity; j++) {
                     float xOffset = cloudRandomness * (2.0F * this.f_19796_.m_188501_() - 1.0F);
                     float yOffset = cloudRandomness * (2.0F * this.f_19796_.m_188501_() - 1.0F);
                     float zOffset = cloudRandomness * (2.0F * this.f_19796_.m_188501_() - 1.0F);
                     float value = this.f_19796_.m_188501_() * 0.1F;
                     this.f_19853_
                        .m_7106_(
                           new ParticleCloud.CloudData(
                              (ParticleType<ParticleCloud.CloudData>)ParticleHandler.CLOUD.get(),
                              0.8F + value,
                              0.8F + value,
                              1.0F,
                              (float)(10.0 + this.f_19796_.m_188500_() * 10.0),
                              40,
                              ParticleCloud.EnumCloudBehavior.SHRINK,
                              1.0F
                           ),
                           x + (double)xOffset,
                           y + (double)yOffset,
                           z + (double)zOffset,
                           motionX,
                           motionY,
                           motionZ
                        );
                  }
               }
            }

            this.prevLeftHandPos = leftHandPos;
            this.prevRightHandPos = rightHandPos;
         }
      }
   }

   @Override
   public boolean m_6469_(DamageSource source, float damage) {
      if (source == DamageSource.f_19315_) {
         return false;
      } else {
         if (source == DamageSource.f_19308_ && this.getAnimation() == NO_ANIMATION) {
            AnimationHandler.INSTANCE.sendAnimationMessage(this, DODGE_ANIMATION);
         }

         if (source.m_19384_()) {
            damage = (float)((double)damage * 1.25);
         }

         if (source.m_7640_() instanceof AbstractArrow) {
            this.m_5496_((SoundEvent)MMSounds.ENTITY_WROUGHT_UNDAMAGED.get(), 0.4F, 2.0F);
            Entity entity = source.m_7639_();
            if (entity != null
               && entity instanceof LivingEntity
               && (!(entity instanceof Player) || !((Player)entity).m_7500_())
               && this.m_5448_() == null
               && !(entity instanceof EntityFrostmaw)) {
               this.m_6710_((LivingEntity)entity);
            }

            if (!this.getActive()) {
               if (this.getAnimation() != DIE_ANIMATION && this.f_19853_.m_46791_() != Difficulty.PEACEFUL) {
                  if (this.getHasCrystal()) {
                     AnimationHandler.INSTANCE.sendAnimationMessage(this, ACTIVATE_ANIMATION);
                  } else {
                     AnimationHandler.INSTANCE.sendAnimationMessage(this, ACTIVATE_NO_CRYSTAL_ANIMATION);
                  }
               }

               if (this.f_19853_.m_46791_() != Difficulty.PEACEFUL) {
                  this.setActive(true);
               }
            }

            return false;
         } else {
            boolean attack = super.m_6469_(source, damage);
            if (attack) {
               this.shouldDodgeMeasure = (int)((float)this.shouldDodgeMeasure + damage);
               Entity entityx = source.m_7639_();
               if (entityx != null
                  && entityx instanceof LivingEntity
                  && (!(entityx instanceof Player) || !((Player)entityx).m_7500_())
                  && this.m_5448_() == null
                  && !(entityx instanceof EntityFrostmaw)) {
                  this.m_6710_((LivingEntity)entityx);
               }

               if (!this.getActive()) {
                  if (this.getAnimation() != DIE_ANIMATION && this.f_19853_.m_46791_() != Difficulty.PEACEFUL) {
                     if (this.getHasCrystal()) {
                        AnimationHandler.INSTANCE.sendAnimationMessage(this, ACTIVATE_ANIMATION);
                     } else {
                        AnimationHandler.INSTANCE.sendAnimationMessage(this, ACTIVATE_NO_CRYSTAL_ANIMATION);
                     }
                  }

                  if (this.f_19853_.m_46791_() != Difficulty.PEACEFUL) {
                     this.setActive(true);
                  }
               }
            }

            return attack;
         }
      }
   }

   @Override
   protected void m_6153_() {
      super.m_6153_();
      if (this.getAnimationTick() == 5) {
         this.m_5496_((SoundEvent)MMSounds.ENTITY_FROSTMAW_DIE.get(), 2.5F, 1.0F);
      } else if (this.getAnimationTick() == 53) {
         this.m_5496_((SoundEvent)MMSounds.ENTITY_FROSTMAW_LAND.get(), 2.5F, 1.0F);
      }
   }

   @Override
   public Animation getDeathAnimation() {
      return DIE_ANIMATION;
   }

   @Override
   public Animation getHurtAnimation() {
      return HURT_ANIMATION;
   }

   public void setActive(boolean active) {
      this.m_20088_().m_135381_(ACTIVE, active);
   }

   public boolean getActive() {
      this.active = (Boolean)this.m_20088_().m_135370_(ACTIVE);
      return this.active;
   }

   public void setHasCrystal(boolean hasCrystal) {
      this.m_20088_().m_135381_(HAS_CRYSTAL, hasCrystal);
   }

   public boolean getHasCrystal() {
      return (Boolean)this.m_20088_().m_135370_(HAS_CRYSTAL);
   }

   public boolean isAlwaysActive() {
      return (Boolean)this.m_20088_().m_135370_(ALWAYS_ACTIVE);
   }

   public void setAlwaysActive(boolean isAlwaysActive) {
      this.m_20088_().m_135381_(ALWAYS_ACTIVE, isAlwaysActive);
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[]{
         DIE_ANIMATION,
         HURT_ANIMATION,
         ROAR_ANIMATION,
         SWIPE_ANIMATION,
         SWIPE_TWICE_ANIMATION,
         ICE_BREATH_ANIMATION,
         ICE_BALL_ANIMATION,
         ACTIVATE_ANIMATION,
         ACTIVATE_NO_CRYSTAL_ANIMATION,
         DEACTIVATE_ANIMATION,
         SLAM_ANIMATION,
         LAND_ANIMATION,
         DODGE_ANIMATION
      };
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      if (compound.m_128441_("has_crystal")) {
         this.setHasCrystal(compound.m_128471_("has_crystal"));
      }

      this.setActive(compound.m_128471_("active"));
      this.setAlwaysActive(compound.m_128471_("alwaysActive"));
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128379_("has_crystal", this.getHasCrystal());
      compound.m_128379_("active", this.getActive());
      compound.m_128379_("alwaysActive", this.isAlwaysActive());
   }

   public boolean m_8023_() {
      return this.getHasCrystal();
   }

   @Override
   public boolean hasBossBar() {
      return (Boolean)ConfigHandler.COMMON.MOBS.FROSTMAW.hasBossBar.get();
   }

   @Override
   protected BossBarColor bossBarColor() {
      return BossBarColor.WHITE;
   }

   protected ResourceLocation m_7582_() {
      return LootTableHandler.FROSTMAW;
   }

   @Override
   protected ConfigHandler.CombatConfig getCombatConfig() {
      return ConfigHandler.COMMON.MOBS.FROSTMAW.combatConfig;
   }

   public Vec3 m_20184_() {
      return !this.getActive() ? super.m_20184_().m_82542_(0.0, 1.0, 0.0) : super.m_20184_();
   }

   @Override
   public SoundEvent getBossMusic() {
      return (SoundEvent)MMSounds.MUSIC_FROSTMAW_THEME.get();
   }

   @Override
   protected boolean canPlayMusic() {
      return super.canPlayMusic() && (this.active || this.getAnimation() == ACTIVATE_ANIMATION);
   }

   @Override
   public boolean resetHealthOnPlayerRespawn() {
      return (Boolean)ConfigHandler.COMMON.MOBS.FROSTMAW.resetHealthWhenRespawn.get();
   }
}
