package com.bobmowzie.mowziesmobs.server.entity.naga;

import com.bobmowzie.mowziesmobs.MowziesMobs;
import com.bobmowzie.mowziesmobs.client.model.tools.ControlledAnimation;
import com.bobmowzie.mowziesmobs.client.model.tools.dynamics.DynamicChain;
import com.bobmowzie.mowziesmobs.client.particle.ParticleVanillaCloudExtended;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationProjectileAttackAI;
import com.bobmowzie.mowziesmobs.server.ai.animation.SimpleAnimationAI;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.bobmowzie.mowziesmobs.server.entity.MowzieLLibraryEntity;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityPoisonBall;
import com.bobmowzie.mowziesmobs.server.loot.LootTableHandler;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.bobmowzie.mowziesmobs.server.util.MowzieMathUtil;
import com.google.common.base.Supplier;
import com.ilexiconn.llibrary.server.animation.Animation;
import com.ilexiconn.llibrary.server.animation.AnimationHandler;
import com.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import java.util.EnumSet;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;

public class EntityNaga extends MowzieLLibraryEntity implements RangedAttackMob, Enemy, FlyingAnimal {
   @OnlyIn(Dist.CLIENT)
   public DynamicChain dc;
   @OnlyIn(Dist.CLIENT)
   public Vec3[] mouthPos;
   public static final Animation FLAP_ANIMATION = Animation.create(25);
   public static final Animation DODGE_ANIMATION = Animation.create(10);
   public static final Animation SPIT_ANIMATION = Animation.create(50);
   public static final Animation SWOOP_ANIMATION = Animation.create(54);
   public static final Animation HURT_TO_FALL_ANIMATION = Animation.create(20);
   public static final Animation LAND_ANIMATION = Animation.create(8);
   public static final Animation GET_UP_ANIMATION = Animation.create(33);
   public static final Animation TAIL_DEMO_ANIMATION = Animation.create(80);
   public static final Animation DIE_AIR_ANIMATION = Animation.create(70);
   public static final Animation DIE_GROUND_ANIMATION = Animation.create(70);
   private static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.m_135353_(EntityNaga.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Float> BANKING = SynchedEntityData.m_135353_(EntityNaga.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Float> PREV_BANKING = SynchedEntityData.m_135353_(EntityNaga.class, EntityDataSerializers.f_135029_);
   public static int MAX_DIST_FROM_HOME = 50;
   private final ControlledAnimation hoverAnim = new ControlledAnimation(10);
   private final ControlledAnimation flapAnim = new ControlledAnimation(10);
   public float hoverAnimFrac;
   public float prevHoverAnimFrac;
   public float flapAnimFrac;
   public float prevFlapAnimFrac;
   private boolean hasFlapSoundPlayed = false;
   @OnlyIn(Dist.CLIENT)
   public float shoulderRot;
   @OnlyIn(Dist.CLIENT)
   public float banking;
   @OnlyIn(Dist.CLIENT)
   public float prevBanking;
   public static final int ROAR_DURATION = 30;
   public int roarAnimation = 0;
   public EntityNaga.EnumNagaMovement movement = EntityNaga.EnumNagaMovement.GLIDING;
   public double prevMotionX;
   public double prevMotionY;
   public double prevMotionZ;
   public static int SPIT_COOLDOWN_MAX = 120;
   public int spitCooldown = 0;
   public static int SWOOP_COOLDOWN_MAX = 90;
   public int swoopCooldown = 0;
   public float swoopTargetCorrectY;
   public float swoopTargetCorrectX;
   public static int GROUND_TIMER_MAX = 60;
   public int onGroundTimer = 0;
   public boolean interrupted = false;

   public EntityNaga(EntityType<? extends EntityNaga> type, Level world) {
      super(type, world);
      if (world.f_46443_) {
         this.dc = new DynamicChain(this);
         this.mouthPos = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
      }

      this.f_21364_ = 10;
      this.f_21342_ = new EntityNaga.NagaMoveHelper(this);
      this.f_21365_ = new EntityNaga.NagaLookController(this);
      this.m_21441_(BlockPathTypes.DANGER_FIRE, -1.0F);
      this.m_21441_(BlockPathTypes.WATER, -1.0F);
      this.m_21441_(BlockPathTypes.WATER_BORDER, -1.0F);
      this.m_21441_(BlockPathTypes.FENCE, -1.0F);
      this.m_21441_(BlockPathTypes.WALKABLE, -1.0F);
   }

   public float m_5610_(BlockPos pos, LevelReader worldIn) {
      return worldIn.m_8055_(pos).m_60795_() ? 10.0F : 0.0F;
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(0, new EntityNaga.FlyOutOfWaterGoal(this));
      this.f_21345_.m_25352_(5, new EntityNaga.WanderGoal());
      this.f_21345_.m_25352_(4, new EntityNaga.AIFlyAroundTarget(this));
      this.f_21345_.m_25352_(3, new EntityNaga.AIFlyTowardsTarget(this));
      this.f_21345_.m_25352_(6, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_
         .m_25352_(
            2,
            new NearestAttackableTargetGoal<Player>(
               this, Player.class, 0, true, true, target -> target.m_20183_().m_123314_(this.m_21534_(), (double)this.m_21535_())
            ) {
               public boolean m_8045_() {
                  return super.m_8045_()
                     && EntityNaga.this.m_5448_() != null
                     && EntityNaga.this.m_5448_().m_20183_().m_123314_(EntityNaga.this.m_21534_(), (double)EntityNaga.this.m_21535_())
                     && EntityNaga.this.getAnimation() == IAnimatedEntity.NO_ANIMATION;
               }
            }
         );
      this.f_21345_.m_25352_(2, new SimpleAnimationAI<EntityNaga>(this, FLAP_ANIMATION, false) {
         @Override
         public void m_8056_() {
            super.m_8056_();
            EntityNaga.this.m_5496_((SoundEvent)MMSounds.ENTITY_NAGA_FLAP_1.get(), 2.0F, (float)(0.85 + (double)EntityNaga.this.f_19796_.m_188501_() * 0.2));
         }

         public void m_8037_() {
            super.m_8037_();
            if (EntityNaga.this.getAnimationTick() >= 4 && EntityNaga.this.getAnimationTick() <= 9) {
               EntityNaga.this.m_20256_(EntityNaga.this.m_20184_().m_82520_(0.0, 0.1, 0.0));
            }
         }
      });
      this.f_21345_.m_25352_(2, new SimpleAnimationAI<>(this, DODGE_ANIMATION, false));
      this.f_21345_.m_25352_(2, new AnimationProjectileAttackAI<EntityNaga>(this, SPIT_ANIMATION, 30, null) {
         @Override
         public void m_8056_() {
            super.m_8056_();
            EntityNaga.this.m_5496_((SoundEvent)MMSounds.ENTITY_NAGA_ACID_CHARGE.get(), 2.0F, 1.0F);
         }

         @Override
         public void m_8037_() {
            super.m_8037_();
            if (!EntityNaga.this.interrupted) {
               if (EntityNaga.this.getAnimationTick() < 9) {
                  EntityNaga.this.m_20256_(EntityNaga.this.m_20184_().m_82520_(0.0, 0.015, 0.0));
               }
            }
         }
      });
      this.f_21345_
         .m_25352_(
            2,
            new SimpleAnimationAI<EntityNaga>(this, SWOOP_ANIMATION, true) {
               @Override
               public void m_8056_() {
                  super.m_8056_();
                  EntityNaga.this.m_5496_((SoundEvent)MMSounds.ENTITY_NAGA_FLAP_1.get(), 2.0F, 0.7F);
               }

               public void m_8037_() {
                  super.m_8037_();
                  if (!EntityNaga.this.interrupted) {
                     Vec3 v = new Vec3(0.0, 0.0, 0.0);
                     int phase1Length = 15;
                     int phase2Length = 21;
                     LivingEntity target = EntityNaga.this.m_5448_();
                     if (EntityNaga.this.getAnimationTick() < phase1Length && target != null) {
                        this.entity.m_21391_(target, 100.0F, 100.0F);
                        this.entity.f_21365_.m_24960_(target, 30.0F, 30.0F);
                     }

                     if (EntityNaga.this.getAnimationTick() < 23 + phase2Length) {
                        if (EntityNaga.this.getAnimationTick() >= 1 && EntityNaga.this.getAnimationTick() < 1 + phase1Length) {
                           float frame = (float)(EntityNaga.this.getAnimationTick() - 1) / (float)phase1Length;
                           v = v.m_82549_(
                              new Vec3(
                                 1.9F * Math.sin((double)frame * Math.PI * 2.0) * (double)frame,
                                 -1.9F * Math.sin((double)frame * Math.PI * 2.0) * (double)frame,
                                 0.8F * Math.sin((double)frame * Math.PI * 2.0)
                              )
                           );
                        } else if (EntityNaga.this.getAnimationTick() >= 16) {
                           if (EntityNaga.this.getAnimationTick() == 23) {
                              if (target != null) {
                                 EntityNaga.this.swoopTargetCorrectY = 0.09F * (float)Math.abs(EntityNaga.this.m_20186_() - target.m_20186_());
                                 EntityNaga.this.swoopTargetCorrectX = 0.1F
                                    * (float)Math.sqrt(
                                       (EntityNaga.this.m_20185_() - target.m_20185_()) * (EntityNaga.this.m_20185_() - target.m_20185_())
                                          + (EntityNaga.this.m_20189_() - target.m_20189_()) * (EntityNaga.this.m_20189_() - target.m_20189_())
                                    );
                                 if (EntityNaga.this.swoopTargetCorrectX > 1.8F) {
                                    EntityNaga.this.swoopTargetCorrectX = 1.8F;
                                 }

                                 if (EntityNaga.this.swoopTargetCorrectY > 2.0F) {
                                    EntityNaga.this.swoopTargetCorrectY = 2.0F;
                                 }
                              } else {
                                 EntityNaga.this.swoopTargetCorrectX = EntityNaga.this.swoopTargetCorrectY = 1.0F;
                              }
                           }

                           if (EntityNaga.this.getAnimationTick() >= 23 && EntityNaga.this.getAnimationTick() < 23 + phase2Length) {
                              float frame = (float)(EntityNaga.this.getAnimationTick() - 23) / (float)phase2Length;
                              v = v.m_82549_(
                                 new Vec3(
                                    (double)EntityNaga.this.swoopTargetCorrectX * 1.4 * (1.0 - Math.exp((double)(2.0F * (frame - 1.0F)))),
                                    (double)EntityNaga.this.swoopTargetCorrectY
                                       * -1.5
                                       * Math.cos((double)frame * Math.PI)
                                       * (1.0 - Math.exp((double)(7.0F * (frame - 1.0F)))),
                                    0.0
                                 )
                              );

                              for (LivingEntity entityHit : EntityNaga.this.getEntityLivingBaseNearby(4.0, 4.0, 4.0, 4.0)) {
                                 if (!(entityHit instanceof EntityNaga)) {
                                    EntityNaga.this.m_7327_(entityHit);
                                 }
                              }
                           }
                        }

                        v = v.m_82524_((float)Math.toRadians((double)(-EntityNaga.this.m_146908_() - 90.0F)));
                        EntityNaga.this.m_20334_(v.f_82479_, v.f_82480_, v.f_82481_);
                     }

                     if (EntityNaga.this.getAnimationTick() == 22) {
                        MowziesMobs.PROXY.playNagaSwoopSound(this.entity);
                     }

                     if (EntityNaga.this.getAnimationTick() == 7) {
                        EntityNaga.this.m_5496_((SoundEvent)MMSounds.ENTITY_NAGA_GRUNT_3.get(), 2.0F, 1.0F);
                     }

                     if (EntityNaga.this.getAnimationTick() == 22) {
                        EntityNaga.this.m_5496_((SoundEvent)MMSounds.ENTITY_NAGA_ROAR_1.get(), 3.0F, 1.0F);
                     }
                  }
               }
            }
         );
      this.f_21345_.m_25352_(2, new SimpleAnimationAI<EntityNaga>(this, HURT_TO_FALL_ANIMATION, true) {
         public void m_8037_() {
         }
      });
      this.f_21345_.m_25352_(2, new SimpleAnimationAI<EntityNaga>(this, LAND_ANIMATION, true) {
         @Override
         public void m_8056_() {
            super.m_8056_();
            EntityNaga.this.m_5496_((SoundEvent)MMSounds.MISC_GROUNDHIT_2.get(), 1.5F, 1.0F);
         }
      });
      this.f_21345_.m_25352_(1, new SimpleAnimationAI<EntityNaga>(this, GET_UP_ANIMATION, true) {
         public void m_8037_() {
            super.m_8037_();
            if (EntityNaga.this.getAnimationTick() == 13) {
               EntityNaga.this.m_5496_((SoundEvent)MMSounds.ENTITY_NAGA_FLAP_1.get(), 2.0F, 1.0F);
            }

            if (EntityNaga.this.getAnimationTick() == 15) {
               EntityNaga.this.m_20256_(EntityNaga.this.m_20184_().m_82520_(0.0, 1.6, 0.0));
            }
         }
      });
      this.f_21345_.m_25352_(2, new SimpleAnimationAI<>(this, TAIL_DEMO_ANIMATION, false));
   }

   protected PathNavigation m_6037_(Level worldIn) {
      FlyingPathNavigation flyingpathnavigator = new FlyingPathNavigation(this, worldIn) {
         public boolean m_6342_(BlockPos pos) {
            return !this.f_26495_.m_8055_(pos.m_7495_()).m_60795_();
         }
      };
      flyingpathnavigator.m_26440_(false);
      flyingpathnavigator.m_7008_(false);
      flyingpathnavigator.m_26443_(false);
      return flyingpathnavigator;
   }

   public boolean m_29443_() {
      return !this.f_19861_;
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.m_20088_().m_135372_(ATTACKING, false);
      this.m_20088_().m_135372_(BANKING, 0.0F);
      this.m_20088_().m_135372_(PREV_BANKING, 0.0F);
   }

   public boolean m_6783_(double distance) {
      return distance < 16600.0;
   }

   @OnlyIn(Dist.CLIENT)
   public AABB m_6921_() {
      return super.m_6921_().m_82400_(12.0);
   }

   public static Builder createAttributes() {
      return MowzieEntity.createAttributes()
         .m_22268_(Attributes.f_22276_, 30.0)
         .m_22268_(Attributes.f_22279_, 12.0)
         .m_22268_(Attributes.f_22277_, 40.0)
         .m_22268_(Attributes.f_22281_, 4.0);
   }

   @Nullable
   protected SoundEvent m_7515_() {
      if (this.getAnimation() != NO_ANIMATION) {
         return null;
      } else {
         int r = this.f_19796_.m_188503_(4);
         if (r == 0) {
            this.m_5496_((SoundEvent)((Supplier)MMSounds.ENTITY_NAGA_ROAR.get(this.f_19796_.m_188503_(4))).get(), 5.0F, 1.0F);
            this.roarAnimation = 0;
         } else if (r <= 2) {
            this.m_5496_((SoundEvent)((Supplier)MMSounds.ENTITY_NAGA_GROWL.get(this.f_19796_.m_188503_(3))).get(), 4.0F, 1.0F);
         }

         return null;
      }
   }

   @Nullable
   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      this.m_5496_((SoundEvent)((Supplier)MMSounds.ENTITY_NAGA_GRUNT.get(this.f_19796_.m_188503_(3))).get(), 2.0F, 1.0F);
      return null;
   }

   @Nullable
   protected SoundEvent m_5592_() {
      this.m_5496_((SoundEvent)((Supplier)MMSounds.ENTITY_NAGA_ROAR.get(this.f_19796_.m_188503_(4))).get(), 3.0F, 1.0F);
      return null;
   }

   @Override
   public void m_8119_() {
      this.prevMotionX = this.m_20184_().f_82479_;
      this.prevMotionY = this.m_20184_().f_82480_;
      this.prevMotionZ = this.m_20184_().f_82481_;
      this.prevHoverAnimFrac = this.hoverAnimFrac;
      this.prevFlapAnimFrac = this.flapAnimFrac;
      super.m_8119_();
      this.f_20883_ = this.m_146908_();
      if (this.f_19853_.m_5776_()) {
         this.banking = this.getBanking();
         this.prevBanking = this.getPrevBanking();
      }

      if (this.spitCooldown > 0) {
         this.spitCooldown--;
      }

      if (this.swoopCooldown > 0) {
         this.swoopCooldown--;
      }

      if (this.onGroundTimer > 0) {
         this.onGroundTimer--;
      }

      if (this.roarAnimation < 30) {
         this.roarAnimation++;
      }

      if (this.getAnimation() == null) {
         AnimationHandler.INSTANCE.sendAnimationMessage(this, NO_ANIMATION);
      }

      if (this.m_21023_(MobEffects.f_19614_)) {
         this.m_6234_(MobEffects.f_19614_);
      }

      if (!this.f_19853_.f_46443_) {
         if (this.m_5448_() != null
            && (double)this.targetDistance < 29.5
            && this.movement != EntityNaga.EnumNagaMovement.FALLEN
            && this.movement != EntityNaga.EnumNagaMovement.FALLING) {
            this.setAttacking(true);
            if (this.getAnimation() == NO_ANIMATION
               && this.swoopCooldown == 0
               && this.f_19796_.m_188503_(80) == 0
               && this.m_20186_() - this.m_5448_().m_20186_() > 0.0) {
               this.interrupted = false;
               AnimationHandler.INSTANCE.sendAnimationMessage(this, SWOOP_ANIMATION);
               this.swoopCooldown = SWOOP_COOLDOWN_MAX;
            } else if (this.getAnimation() == NO_ANIMATION && this.spitCooldown == 0 && this.f_19796_.m_188503_(80) == 0) {
               this.interrupted = false;
               AnimationHandler.INSTANCE.sendAnimationMessage(this, SPIT_ANIMATION);
               this.spitCooldown = SPIT_COOLDOWN_MAX;
            }
         } else {
            this.setAttacking(false);
         }
      }

      if (this.movement != EntityNaga.EnumNagaMovement.FALLING && this.movement != EntityNaga.EnumNagaMovement.FALLEN) {
         if (this.getAttacking()) {
            this.movement = EntityNaga.EnumNagaMovement.HOVERING;
            this.hoverAnim.increaseTimer();
            if (this.getAnimation() == NO_ANIMATION && !this.f_19853_.f_46443_) {
               for (Projectile a : this.getEntitiesNearby(Projectile.class, 30.0)) {
                  Vec3 aActualMotion = new Vec3(a.m_20185_() - a.f_19854_, a.m_20186_() - a.f_19855_, a.m_20189_() - a.f_19856_);
                  if (!(aActualMotion.m_82553_() < 0.1) && a.f_19797_ > 1) {
                     float dot = (float)a.m_20184_().m_82541_().m_82526_(this.m_20182_().m_82546_(a.m_20182_()).m_82541_());
                     if ((double)dot > 0.96) {
                        Vec3 dodgeVec = a.m_20184_().m_82537_(new Vec3(0.0, 1.0, 0.0)).m_82541_().m_82490_(1.2);
                        Vec3 newPosLeft = this.m_20182_().m_82549_(dodgeVec.m_82490_(2.0));
                        Vec3 newPosRight = this.m_20182_().m_82549_(dodgeVec.m_82490_(-2.0));
                        Vec3 diffLeft = newPosLeft.m_82546_(a.m_20182_());
                        Vec3 diffRight = newPosRight.m_82546_(a.m_20182_());
                        if (diffRight.m_82526_(a.m_20184_()) > diffLeft.m_82526_(a.m_20184_())) {
                           dodgeVec = dodgeVec.m_82490_(-1.0);
                        }

                        this.m_20256_(this.m_20184_().m_82549_(dodgeVec));
                        AnimationHandler.INSTANCE.sendAnimationMessage(this, DODGE_ANIMATION);
                     }
                  }
               }
            }
         } else {
            this.movement = EntityNaga.EnumNagaMovement.GLIDING;
            this.hoverAnim.decreaseTimer();
            this.flapAnim.decreaseTimer();
         }
      } else if (this.movement == EntityNaga.EnumNagaMovement.FALLING && (this.f_19861_ || this.m_20077_() || this.m_20069_())) {
         AnimationHandler.INSTANCE.sendAnimationMessage(this, LAND_ANIMATION);
         this.movement = EntityNaga.EnumNagaMovement.FALLEN;
         this.onGroundTimer = GROUND_TIMER_MAX;
         this.m_20334_(0.0, this.m_20184_().f_82480_, 0.0);
         this.m_21573_().m_26573_();
      }

      if (this.movement == EntityNaga.EnumNagaMovement.FALLEN && this.onGroundTimer <= 0 && this.getAnimation() == NO_ANIMATION) {
         this.setAnimationTick(0);
         AnimationHandler.INSTANCE.sendAnimationMessage(this, GET_UP_ANIMATION);
      }

      if (this.getAnimation() == SWOOP_ANIMATION && this.getAnimationTick() < 43) {
         this.hoverAnim.increaseTimer();
         this.flapAnim.decreaseTimer();
      } else if (this.getAnimation() == HURT_TO_FALL_ANIMATION) {
         this.flapAnim.decreaseTimer();
         this.hoverAnim.increaseTimer();
      } else if (this.getAnimation() == LAND_ANIMATION) {
         this.flapAnim.decreaseTimer();
         this.hoverAnim.increaseTimer();
      } else if (this.getAnimation() == GET_UP_ANIMATION && this.getAnimationTick() < 26) {
         this.flapAnim.decreaseTimer();
         this.hoverAnim.increaseTimer();
      } else if (this.movement == EntityNaga.EnumNagaMovement.FALLEN) {
         this.flapAnim.decreaseTimer();
         this.hoverAnim.increaseTimer();
         this.m_146922_(this.f_19859_);
         this.f_20885_ = this.f_20886_;
         this.m_146926_(this.f_19860_);
      } else if (this.movement == EntityNaga.EnumNagaMovement.FALLING) {
         this.flapAnim.decreaseTimer();
         this.hoverAnim.increaseTimer();
      } else {
         this.flapAnim.increaseTimer();
      }

      if (this.getAnimation() == SPIT_ANIMATION && this.f_19853_.f_46443_ && this.mouthPos != null && !this.interrupted) {
         if (this.getAnimationTick() == 33) {
            float explodeSpeed = 2.4F;

            for (int i = 0; i < 25; i++) {
               Vec3 particlePos = new Vec3(0.25, 0.0, 0.0);
               particlePos = particlePos.m_82524_((float)((double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI));
               particlePos = particlePos.m_82496_((float)((double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI));
               double value = (double)(this.f_19796_.m_188501_() * 0.1F);
               double life = (double)(this.f_19796_.m_188501_() * 10.0F + 20.0F);
               ParticleVanillaCloudExtended.spawnVanillaCloud(
                  this.f_19853_,
                  particlePos.f_82479_ + this.mouthPos[0].f_82479_,
                  particlePos.f_82480_ + this.mouthPos[0].f_82480_,
                  particlePos.f_82481_ + this.mouthPos[0].f_82481_,
                  particlePos.f_82479_ * (double)explodeSpeed,
                  particlePos.f_82480_ * (double)explodeSpeed,
                  particlePos.f_82481_ * (double)explodeSpeed,
                  1.0,
                  0.25 + value,
                  0.75 + value,
                  0.25 + value,
                  0.6,
                  life
               );
            }
         }

         if (this.getAnimationTick() <= 15 && this.mouthPos != null && !this.interrupted) {
            int howMany = 4;

            for (int i = 0; i < howMany; i++) {
               Vec3 particlePos = new Vec3(3.0, 0.0, 0.0);
               particlePos = particlePos.m_82524_((float)((double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI));
               particlePos = particlePos.m_82496_((float)((double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI));
               double value = (double)(this.f_19796_.m_188501_() * 0.15F);
               ParticleVanillaCloudExtended.spawnVanillaCloudDestination(
                  this.f_19853_,
                  particlePos.f_82479_ + this.mouthPos[0].f_82479_,
                  particlePos.f_82480_ + this.mouthPos[0].f_82480_,
                  particlePos.f_82481_ + this.mouthPos[0].f_82481_,
                  0.0,
                  0.0,
                  0.0,
                  1.0,
                  0.25 + value,
                  0.75 + value,
                  0.25 + value,
                  0.9,
                  15.0,
                  this.mouthPos
               );
            }
         }
      }

      if (this.getAnimation() == HURT_TO_FALL_ANIMATION && this.getAnimationTick() == 17) {
         this.movement = EntityNaga.EnumNagaMovement.FALLING;
      }

      if (this.getAnimation() == GET_UP_ANIMATION && this.getAnimationTick() == 26) {
         this.movement = EntityNaga.EnumNagaMovement.HOVERING;
      }

      if (this.f_19853_.f_46443_ && this.movement == EntityNaga.EnumNagaMovement.HOVERING && (double)this.flapAnim.getAnimationFraction() >= 0.5) {
         if ((double)this.shoulderRot > 0.9) {
            this.hasFlapSoundPlayed = false;
         }

         if ((double)this.shoulderRot <= 0.7 && !this.hasFlapSoundPlayed) {
            this.f_19853_
               .m_7785_(
                  this.m_20185_(),
                  this.m_20186_(),
                  this.m_20189_(),
                  (SoundEvent)MMSounds.ENTITY_NAGA_FLAP_1.get(),
                  SoundSource.HOSTILE,
                  2.0F,
                  (float)(0.85 + (double)this.f_19796_.m_188501_() * 0.2),
                  false
               );
            this.hasFlapSoundPlayed = true;
         }
      }

      this.hoverAnimFrac = this.hoverAnim.getAnimationProgressSinSqrt();
      this.flapAnimFrac = this.flapAnim.getAnimationProgressSinSqrt();
      if (!this.f_19853_.f_46443_ && this.f_19853_.m_46791_() == Difficulty.PEACEFUL) {
         this.m_146870_();
      }
   }

   @Override
   public SpawnGroupData m_6518_(
      ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, SpawnGroupData spawnDataIn, CompoundTag dataTag
   ) {
      this.m_21446_(this.m_20183_(), MAX_DIST_FROM_HOME);
      return super.m_6518_(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
   }

   @Override
   protected ConfigHandler.SpawnConfig getSpawnConfig() {
      return ConfigHandler.COMMON.MOBS.NAGA.spawnConfig;
   }

   @Override
   protected ConfigHandler.CombatConfig getCombatConfig() {
      return ConfigHandler.COMMON.MOBS.NAGA.combatConfig;
   }

   public boolean m_5545_(LevelAccessor world, MobSpawnType reason) {
      boolean flag = super.m_5545_(world, reason);
      this.m_6034_(this.m_20185_(), this.m_20186_() + 5.0, this.m_20189_());
      return flag && world.m_46791_() != Difficulty.PEACEFUL;
   }

   public boolean m_6914_(LevelReader worldIn) {
      boolean liquid = !this.f_19853_.m_46855_(this.m_20191_());
      boolean worldCollision = this.f_19853_.m_45756_(this, this.m_20191_());
      boolean mobCollision = this.f_19853_.m_45784_(this);
      return liquid && worldCollision && mobCollision;
   }

   @Override
   protected void m_6153_() {
      super.m_6153_();
      if (this.f_20919_ == 15 && this.movement != EntityNaga.EnumNagaMovement.FALLEN) {
         this.movement = EntityNaga.EnumNagaMovement.FALLING;
      }
   }

   @Override
   public boolean m_6469_(DamageSource source, float damage) {
      boolean flag = super.m_6469_(source, damage);
      boolean isSpitting = this.getAnimation() == SPIT_ANIMATION && this.getAnimationTick() < 30;
      boolean isSwooping = this.getAnimation() == SWOOP_ANIMATION && this.getAnimationTick() < 25;
      if (flag && this.movement != EntityNaga.EnumNagaMovement.FALLING && (isSpitting || isSwooping) && damage > 0.0F) {
         AnimationHandler.INSTANCE.sendAnimationMessage(this, HURT_TO_FALL_ANIMATION);
         this.interrupted = true;
      }

      return flag;
   }

   public void m_6504_(LivingEntity target, float distanceFactor) {
      if (!this.interrupted) {
         Vec3 projectilePos = new Vec3(1.0, -0.7, 0.0);
         projectilePos = projectilePos.m_82524_((float)Math.toRadians((double)(-this.m_146908_() - 90.0F)));
         projectilePos = projectilePos.m_82549_(this.m_20182_());
         projectilePos = projectilePos.m_82549_(
            new Vec3(0.0, 0.0, 1.0).m_82496_((float)Math.toRadians((double)(-this.m_146909_()))).m_82524_((float)Math.toRadians((double)(-this.f_20885_)))
         );
         projectilePos = projectilePos.m_82549_(new Vec3(0.0, 0.0, 0.0));
         EntityPoisonBall poisonBall = new EntityPoisonBall((EntityType<? extends EntityPoisonBall>)EntityHandler.POISON_BALL.get(), this.f_19853_, this);
         poisonBall.m_6034_(projectilePos.f_82479_, projectilePos.f_82480_, projectilePos.f_82481_);
         Vec3 look = this.m_20154_();
         Vec3 dir = new Vec3(look.f_82479_, 0.0, look.f_82481_).m_82541_();
         if (target != null) {
            float dy = (float)(projectilePos.f_82480_ - target.m_20186_());
            float dx = (float)(projectilePos.f_82479_ - target.m_20185_());
            float dz = (float)(projectilePos.f_82481_ - target.m_20189_());
            float dist = (float)Math.sqrt((double)(dx * dx + dz * dz));
            float timeGuess = (float)Math.sqrt((double)(2.0F * dy / EntityPoisonBall.GRAVITY));
            float speed = Math.min(dist / timeGuess, 0.9F);
            poisonBall.shoot(dir.f_82479_ * (double)speed, 0.1, dir.f_82481_ * (double)speed, 1.0F, 0.0F);
         }

         this.f_19853_.m_7967_(poisonBall);
         this.m_5496_((SoundEvent)MMSounds.ENTITY_NAGA_ACID_SPIT.get(), 2.0F, 1.0F);
         this.m_5496_((SoundEvent)MMSounds.ENTITY_NAGA_ACID_SPIT_HISS.get(), 2.0F, 1.0F);
      }
   }

   @Override
   public Animation getDeathAnimation() {
      return this.movement == EntityNaga.EnumNagaMovement.FALLEN ? DIE_GROUND_ANIMATION : DIE_AIR_ANIMATION;
   }

   @Override
   public Animation getHurtAnimation() {
      return null;
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[]{
         FLAP_ANIMATION,
         DODGE_ANIMATION,
         SWOOP_ANIMATION,
         SPIT_ANIMATION,
         HURT_TO_FALL_ANIMATION,
         LAND_ANIMATION,
         GET_UP_ANIMATION,
         DIE_AIR_ANIMATION,
         DIE_GROUND_ANIMATION,
         TAIL_DEMO_ANIMATION
      };
   }

   public boolean m_142535_(float distance, float damageMultiplier, DamageSource source) {
      return this.movement == EntityNaga.EnumNagaMovement.FALLING ? super.m_142535_(distance, damageMultiplier, source) : false;
   }

   protected void m_7840_(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
      if (this.movement == EntityNaga.EnumNagaMovement.FALLING) {
         super.m_7840_(y, onGroundIn, state, pos);
      }
   }

   public void m_7023_(Vec3 motion) {
      double d0 = 0.08;
      AttributeInstance gravity = this.m_21051_((Attribute)ForgeMod.ENTITY_GRAVITY.get());
      boolean flag = this.m_20184_().f_82480_ <= 0.0;
      d0 = gravity.m_22135_();
      FluidState fluidstate = this.f_19853_.m_6425_(this.m_20183_());
      if (this.m_20069_() && this.m_6129_() && !this.m_203441_(fluidstate)) {
         double d8 = this.m_20186_();
         float f5 = this.m_20142_() ? 0.9F : this.m_6108_();
         float f6 = 0.02F;
         float f7 = (float)EnchantmentHelper.m_44922_(this);
         if (f7 > 3.0F) {
            f7 = 3.0F;
         }

         if (!this.f_19861_) {
            f7 *= 0.5F;
         }

         if (f7 > 0.0F) {
            f5 += (0.54600006F - f5) * f7 / 3.0F;
            f6 += (this.m_6113_() - f6) * f7 / 3.0F;
         }

         if (this.m_21023_(MobEffects.f_19593_)) {
            f5 = 0.96F;
         }

         f6 *= (float)this.m_21051_((Attribute)ForgeMod.SWIM_SPEED.get()).m_22135_();
         this.m_19920_(f6, motion);
         this.m_6478_(MoverType.SELF, this.m_20184_());
         Vec3 vector3d6 = this.m_20184_();
         if (this.f_19862_ && this.m_6147_()) {
            new Vec3(vector3d6.f_82479_, 0.2, vector3d6.f_82481_);
         }

         Vec3 vector3d2 = this.m_20994_(d0, flag, this.m_20184_());
         this.m_20256_(vector3d2);
         if (this.f_19862_ && this.m_20229_(vector3d2.f_82479_, vector3d2.f_82480_ + 0.6F - this.m_20186_() + d8, vector3d2.f_82481_)) {
            this.m_20334_(vector3d2.f_82479_, 0.3F, vector3d2.f_82481_);
         }
      } else if (this.m_20077_() && this.m_6129_() && !this.m_203441_(fluidstate)) {
         double d7 = this.m_20186_();
         this.m_19920_(0.02F, motion);
         this.m_6478_(MoverType.SELF, this.m_20184_());
         if (this.m_204036_(FluidTags.f_13132_) <= this.m_20204_()) {
            this.m_20256_(this.m_20184_().m_82542_(0.5, 0.8F, 0.5));
            Vec3 vector3d3 = this.m_20994_(d0, flag, this.m_20184_());
            this.m_20256_(vector3d3);
         } else {
            this.m_20256_(this.m_20184_().m_82490_(0.5));
         }

         if (!this.m_20068_()) {
            this.m_20256_(this.m_20184_().m_82520_(0.0, -d0 / 4.0, 0.0));
         }

         Vec3 vector3d4 = this.m_20184_();
         if (this.f_19862_ && this.m_20229_(vector3d4.f_82479_, vector3d4.f_82480_ + 0.6F - this.m_20186_() + d7, vector3d4.f_82481_)) {
            this.m_20334_(vector3d4.f_82479_, 0.3F, vector3d4.f_82481_);
         }
      } else if (this.movement == EntityNaga.EnumNagaMovement.HOVERING) {
         BlockPos ground = new BlockPos(this.m_20185_(), this.m_20191_().f_82289_ - 1.0, this.m_20189_());
         float f = 0.91F;
         if (this.m_20096_()) {
            f = this.f_19853_.m_8055_(ground).getFriction(this.f_19853_, ground, this) * 0.91F;
         }

         float f1 = 0.16277137F / (f * f * f);
         f = 0.91F;
         if (this.m_20096_()) {
            f = this.f_19853_.m_8055_(ground).getFriction(this.f_19853_, ground, this) * 0.91F;
         }

         this.m_19920_(this.m_20096_() ? 0.1F * f1 : 0.02F, motion);
         this.m_6478_(MoverType.SELF, this.m_20184_());
         this.m_20256_(this.m_20184_().m_82490_((double)f));
         BlockPos destination = this.m_21573_().m_26567_();
         if (destination != null) {
            double dx = (double)destination.m_123341_() - this.m_20185_();
            double dy = (double)destination.m_123342_() - this.m_20186_();
            double dz = (double)destination.m_123343_() - this.m_20189_();
            double distanceToDest = Math.sqrt(dx * dx + dy * dy + dz * dz);
            if (distanceToDest < 0.1 && this.getAnimation() == NO_ANIMATION) {
               this.m_20334_(0.0, 0.0, 0.0);
            }
         }
      } else if (this.movement == EntityNaga.EnumNagaMovement.GLIDING) {
         Vec3 vec3 = this.m_20184_();
         if (vec3.f_82480_ > -0.5) {
            this.f_19789_ = 1.0F;
         }

         Vec3 moveDirection = this.m_20154_();
         moveDirection = moveDirection.m_82541_();
         float f6x = this.m_146909_() * (float) (Math.PI / 180.0);
         double d9 = Math.sqrt(moveDirection.f_82479_ * moveDirection.f_82479_ + moveDirection.f_82481_ * moveDirection.f_82481_);
         double d11 = Math.sqrt(vec3.m_165925_());
         double d12 = moveDirection.m_82553_();
         float f3 = Mth.m_14089_(f6x);
         f3 = (float)((double)f3 * (double)f3 * Math.min(1.0, d12 / 0.4));
         vec3 = this.m_20184_().m_82520_(0.0, d0 * (-1.0 + (double)f3 * 0.75), 0.0);
         if (vec3.f_82480_ < 0.0 && d9 > 0.0) {
            double d3 = vec3.f_82480_ * -0.1 * (double)f3;
            vec3 = vec3.m_82520_(moveDirection.f_82479_ * d3 / d9, d3, moveDirection.f_82481_ * d3 / d9);
         }

         if (f6x < 0.0F && d9 > 0.0) {
            double d13 = d11 * (double)(-Mth.m_14031_(f6x)) * 0.04;
            vec3 = vec3.m_82520_(-moveDirection.f_82479_ * d13 / d9, d13 * 3.2, -moveDirection.f_82481_ * d13 / d9);
         }

         if (d9 > 0.0) {
            vec3 = vec3.m_82520_((moveDirection.f_82479_ / d9 * d11 - vec3.f_82479_) * 0.1, 0.0, (moveDirection.f_82481_ / d9 * d11 - vec3.f_82481_) * 0.1);
         }

         this.m_20256_(vec3.m_82542_(0.99F, 0.98F, 0.99F));
         this.m_6478_(MoverType.SELF, this.m_20184_());
         if (moveDirection.m_7098_() < 0.0 && this.getAnimation() == NO_ANIMATION) {
            AnimationHandler.INSTANCE.sendAnimationMessage(this, FLAP_ANIMATION);
         }
      } else if (this.movement == EntityNaga.EnumNagaMovement.FALLING || this.movement == EntityNaga.EnumNagaMovement.FALLEN || this.m_21525_()) {
         BlockPos blockpos = new BlockPos(this.m_20185_(), this.m_20191_().f_82289_ - 1.0, this.m_20189_());
         float f5x = this.f_19853_.m_8055_(blockpos).getFriction(this.f_19853_, blockpos, this);
         float f7x = this.m_20096_() ? f5x * 0.91F : 0.91F;
         this.m_6478_(MoverType.SELF, this.m_20184_());
         Vec3 Vector3d5 = this.m_20184_();
         if ((this.f_19862_ || this.f_20899_) && this.m_6147_()) {
            Vector3d5 = new Vec3(Vector3d5.f_82479_, 0.2, Vector3d5.f_82481_);
         }

         double d10 = Vector3d5.f_82480_;
         if (this.m_21023_(MobEffects.f_19620_)) {
            d10 += (0.05 * (double)(this.m_21124_(MobEffects.f_19620_).m_19564_() + 1) - Vector3d5.f_82480_) * 0.2;
            this.f_19789_ = 0.0F;
         } else if (this.f_19853_.f_46443_ && !this.f_19853_.m_46805_(blockpos)) {
            if (this.m_20186_() > 0.0) {
               d10 = -0.1;
            } else {
               d10 = 0.0;
            }
         } else if (!this.m_20068_()) {
            d10 -= d0;
         }

         this.m_20334_(Vector3d5.f_82479_ * (double)f7x, d10 * 0.98F, Vector3d5.f_82481_ * (double)f7x);
      }

      this.f_20923_ = this.f_20924_;
      double d5 = this.m_20185_() - this.f_19854_;
      double d6 = this.m_20189_() - this.f_19856_;
      double d8x = this.m_20186_() - this.f_19855_;
      float f8 = Mth.m_14116_((float)(d5 * d5 + d8x * d8x + d6 * d6)) * 4.0F;
      if (f8 > 1.0F) {
         f8 = 1.0F;
      }

      this.f_20924_ = this.f_20924_ + (f8 - this.f_20924_) * 0.4F;
      this.f_20925_ = this.f_20925_ + this.f_20924_;
   }

   public boolean m_6147_() {
      return false;
   }

   public boolean getAttacking() {
      return (Boolean)this.m_20088_().m_135370_(ATTACKING);
   }

   public void setAttacking(boolean attacking) {
      this.m_20088_().m_135381_(ATTACKING, attacking);
   }

   public float getBanking() {
      return (Float)this.m_20088_().m_135370_(BANKING);
   }

   public void setBanking(float banking) {
      this.m_20088_().m_135381_(BANKING, banking);
   }

   public float getPrevBanking() {
      return (Float)this.m_20088_().m_135370_(PREV_BANKING);
   }

   public void setPrevBanking(float prevBanking) {
      this.m_20088_().m_135381_(PREV_BANKING, prevBanking);
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128405_("HomePosX", this.m_21534_().m_123341_());
      compound.m_128405_("HomePosY", this.m_21534_().m_123342_());
      compound.m_128405_("HomePosZ", this.m_21534_().m_123343_());
      compound.m_128405_("HomeDist", (int)this.m_21535_());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      int i = compound.m_128451_("HomePosX");
      int j = compound.m_128451_("HomePosY");
      int k = compound.m_128451_("HomePosZ");
      int dist = compound.m_128451_("HomeDist");
      this.m_21446_(new BlockPos(i, j, k), dist);
   }

   protected ResourceLocation m_7582_() {
      return LootTableHandler.NAGA;
   }

   class AIFlyAroundTarget extends Goal {
      private final EntityNaga parentEntity;

      public AIFlyAroundTarget(EntityNaga naga) {
         this.parentEntity = naga;
         this.m_7021_(EnumSet.of(Flag.MOVE));
      }

      public boolean m_8036_() {
         return this.parentEntity.m_5448_() != null && this.parentEntity.m_21573_().m_26571_() ? this.parentEntity.f_19796_.m_188503_(60) == 0 : false;
      }

      public boolean m_8045_() {
         if (EntityNaga.this.m_5448_() == null) {
            return false;
         } else {
            BlockPos navigatorPos = this.parentEntity.m_21573_().m_26567_();
            if (navigatorPos == null) {
               return false;
            } else {
               double dx = (double)navigatorPos.m_123341_() - this.parentEntity.m_20185_();
               double dy = (double)navigatorPos.m_123342_() - this.parentEntity.m_20186_();
               double dz = (double)navigatorPos.m_123343_() - this.parentEntity.m_20189_();
               double distanceToDest = Math.sqrt(dx * dx + dy * dy + dz * dz);
               if (distanceToDest > 60.0) {
                  return false;
               } else {
                  LivingEntity target = this.parentEntity.m_5448_();
                  double dx2 = (double)navigatorPos.m_123341_() - target.m_20185_();
                  double dy2 = (double)navigatorPos.m_123342_() - target.m_20186_();
                  double dz2 = (double)navigatorPos.m_123343_() - target.m_20189_();
                  double distanceDestToTarget = Math.sqrt(dx2 * dx2 + dy2 * dy2 + dz2 * dz2);
                  return !(distanceDestToTarget > 20.0) && !(distanceDestToTarget < 5.0)
                     ? EntityNaga.this.f_21344_.m_26572_() && !EntityNaga.this.f_21344_.m_26567_().m_123314_(EntityNaga.this.m_20183_(), 1.0)
                     : false;
               }
            }
         }
      }

      public void m_8056_() {
         RandomSource random = this.parentEntity.m_217043_();
         LivingEntity target = this.parentEntity.m_5448_();
         float yaw = (float)((double)random.m_188501_() * Math.PI * 2.0);
         float radius = 16.0F;
         double d0 = target.m_20185_() + Math.cos((double)yaw) * (double)radius;
         double d1 = target.m_20186_() + 8.0 + (double)(random.m_188501_() * 5.0F);
         double d2 = target.m_20189_() + Math.sin((double)yaw) * (double)radius;
         double speed = this.parentEntity.m_21051_(Attributes.f_22279_).m_22135_();
         if (!this.parentEntity.f_19853_.m_46801_(new BlockPos(d0, d1, d2))) {
            this.parentEntity.m_21573_().m_26519_(d0, d1, d2, speed);
         }
      }

      public void m_8041_() {
         super.m_8041_();
         EntityNaga.this.m_21573_().m_26573_();
      }
   }

   class AIFlyTowardsTarget extends Goal {
      private final EntityNaga parentEntity;

      public AIFlyTowardsTarget(EntityNaga naga) {
         this.parentEntity = naga;
         this.m_7021_(EnumSet.of(Flag.MOVE));
      }

      public boolean m_8036_() {
         return this.parentEntity.m_5448_() != null && EntityNaga.this.m_20280_(this.parentEntity.m_5448_()) >= 870.25;
      }

      public boolean m_8045_() {
         if (EntityNaga.this.m_5448_() == null) {
            return false;
         } else if (EntityNaga.this.m_5448_().m_20280_(this.parentEntity) <= 500.0) {
            return false;
         } else {
            BlockPos navigatorPos = this.parentEntity.m_21573_().m_26567_();
            if (navigatorPos == null) {
               return false;
            } else {
               double dx = (double)navigatorPos.m_123341_() - this.parentEntity.m_20185_();
               double dy = (double)navigatorPos.m_123342_() - this.parentEntity.m_20186_();
               double dz = (double)navigatorPos.m_123343_() - this.parentEntity.m_20189_();
               double distanceToDest = Math.sqrt(dx * dx + dy * dy + dz * dz);
               return distanceToDest > 60.0 ? false : EntityNaga.this.f_21344_.m_26572_();
            }
         }
      }

      public void m_8037_() {
         super.m_8037_();
         LivingEntity target = this.parentEntity.m_5448_();
         double speed = this.parentEntity.m_21051_(Attributes.f_22279_).m_22135_();
         BlockPos targetPos = target.m_20183_().m_6630_(8);
         if (!this.parentEntity.f_19853_.m_46801_(targetPos)) {
            this.parentEntity.m_21573_().m_26519_((double)targetPos.m_123341_(), (double)targetPos.m_123342_(), (double)targetPos.m_123343_(), speed);
         }
      }

      public void m_8041_() {
         super.m_8041_();
         EntityNaga.this.m_21573_().m_26573_();
      }
   }

   static class AILookAround extends Goal {
      private final EntityNaga parentEntity;

      public AILookAround(EntityNaga naga) {
         this.parentEntity = naga;
         this.m_7021_(EnumSet.of(Flag.LOOK));
      }

      public boolean m_8036_() {
         return true;
      }

      public void m_8037_() {
         if (this.parentEntity.m_5448_() == null) {
            Vec3 motion = this.parentEntity.m_20184_();
            this.parentEntity.m_146922_(-((float)Mth.m_14136_(motion.f_82479_, motion.f_82481_)) * (180.0F / (float)Math.PI));
            this.parentEntity.f_20883_ = this.parentEntity.m_146908_();
         } else {
            LivingEntity entitylivingbase = this.parentEntity.m_5448_();
            if (entitylivingbase.m_20280_(this.parentEntity) < 1600.0) {
               double d1 = entitylivingbase.m_20185_() - this.parentEntity.m_20185_();
               double d2 = entitylivingbase.m_20189_() - this.parentEntity.m_20189_();
               this.parentEntity.m_146922_(-((float)Mth.m_14136_(d1, d2)) * (180.0F / (float)Math.PI));
               this.parentEntity.f_20883_ = this.parentEntity.m_146908_();
            }
         }
      }
   }

   public static enum EnumNagaMovement {
      GLIDING,
      HOVERING,
      SWIMMING,
      FALLING,
      FALLEN;
   }

   public class FlyOutOfWaterGoal extends Goal {
      private final EntityNaga entity;

      public FlyOutOfWaterGoal(EntityNaga entityIn) {
         this.entity = entityIn;
         this.m_7021_(EnumSet.of(Flag.MOVE));
      }

      public boolean m_8036_() {
         return this.entity.m_20069_() && this.entity.m_204036_(FluidTags.f_13131_) > this.entity.m_20204_() || this.entity.m_20077_();
      }

      public void m_8056_() {
         if (this.entity.getAnimation() == IAnimatedEntity.NO_ANIMATION) {
            AnimationHandler.INSTANCE.sendAnimationMessage(this.entity, EntityNaga.FLAP_ANIMATION);
         }
      }

      public boolean m_8045_() {
         return false;
      }
   }

   class NagaLookController extends LookControl {
      public NagaLookController(Mob entity) {
         super(entity);
      }

      public void m_8128_() {
         if (this.f_186068_ > 0 && this.m_180896_().isPresent()) {
            this.f_186068_--;
            this.f_24937_.f_20885_ = this.m_24956_(this.f_24937_.f_20885_, (Float)this.m_180896_().get(), this.f_24938_);
         } else {
            this.f_24937_.f_20885_ = this.m_24956_(this.f_24937_.f_20885_, this.f_24937_.f_20883_, 10.0F);
         }

         if (!this.f_24937_.m_21573_().m_26571_()) {
            this.f_24937_.f_20885_ = Mth.m_14094_(this.f_24937_.f_20885_, this.f_24937_.f_20883_, (float)this.f_24937_.m_8085_());
         }
      }
   }

   class NagaMoveHelper extends MoveControl {
      private final EntityNaga parentEntity;
      private int courseChangeCooldown;
      private float speedFactor = 0.1F;

      public NagaMoveHelper(EntityNaga naga) {
         super(naga);
         this.parentEntity = naga;
      }

      public void m_8126_() {
         this.speedFactor = 1.0F;
         if (EntityNaga.this.movement == EntityNaga.EnumNagaMovement.GLIDING) {
            if (this.f_24981_ == Operation.MOVE_TO) {
               if (EntityNaga.this.f_19862_) {
                  EntityNaga naga = EntityNaga.this;
                  naga.m_146922_(naga.m_146908_() + 180.0F);
                  this.speedFactor = 0.1F;
                  EntityNaga.this.m_21573_().m_26573_();
               }

               float orbitOffsetDiffX = (float)((double)EntityNaga.this.m_21573_().m_26567_().m_123341_() - EntityNaga.this.m_20185_());
               float orbitOffsetDiffY = (float)((double)EntityNaga.this.m_21573_().m_26567_().m_123342_() - EntityNaga.this.m_20186_());
               float orbitOffsetDiffZ = (float)((double)EntityNaga.this.m_21573_().m_26567_().m_123343_() - EntityNaga.this.m_20189_());
               double horizontalDistToOrbitOffset = (double)Mth.m_14116_(orbitOffsetDiffX * orbitOffsetDiffX + orbitOffsetDiffZ * orbitOffsetDiffZ);
               double yFractionReduction = 1.0 - (double)Mth.m_14154_(orbitOffsetDiffY * 0.7F) / horizontalDistToOrbitOffset;
               orbitOffsetDiffX = (float)((double)orbitOffsetDiffX * yFractionReduction);
               orbitOffsetDiffZ = (float)((double)orbitOffsetDiffZ * yFractionReduction);
               horizontalDistToOrbitOffset = (double)Mth.m_14116_(orbitOffsetDiffX * orbitOffsetDiffX + orbitOffsetDiffZ * orbitOffsetDiffZ);
               double distToOrbitOffset = (double)Mth.m_14116_(
                  orbitOffsetDiffX * orbitOffsetDiffX + orbitOffsetDiffZ * orbitOffsetDiffZ + orbitOffsetDiffY * orbitOffsetDiffY
               );
               float rotationYaw = EntityNaga.this.m_146908_();
               float desiredRotationYaw = (float)Mth.m_14136_((double)orbitOffsetDiffZ, (double)orbitOffsetDiffX);
               float rotationYawWrapped = Mth.m_14177_(EntityNaga.this.m_146908_() + 90.0F);
               float desiredRotationYawWrapped = Mth.m_14177_(desiredRotationYaw * (180.0F / (float)Math.PI));
               EntityNaga.this.m_146922_(Mth.m_14148_(rotationYawWrapped, desiredRotationYawWrapped, 4.0F) - 90.0F);
               float newBanking = MowzieMathUtil.approachDegreesSmooth(
                  EntityNaga.this.getBanking(), EntityNaga.this.getPrevBanking(), EntityNaga.this.m_146908_() - rotationYaw, 0.5F, 0.1F
               );
               EntityNaga.this.setPrevBanking(EntityNaga.this.getBanking());
               EntityNaga.this.setBanking(newBanking);
               EntityNaga.this.f_20883_ = EntityNaga.this.m_146908_();
               float desiredPitch = (float)(-(Mth.m_14136_((double)(-orbitOffsetDiffY), horizontalDistToOrbitOffset) * 180.0F / (float)Math.PI));
               EntityNaga.this.m_146926_(Mth.m_14148_(EntityNaga.this.m_146909_(), desiredPitch, 8.0F));
               float rotationYaw1 = EntityNaga.this.m_146908_() + 90.0F;
               double xMotion = (double)(this.speedFactor * Mth.m_14089_(rotationYaw1 * (float) (Math.PI / 180.0)))
                  * Math.abs((double)orbitOffsetDiffX / distToOrbitOffset);
               double yMotion = (double)(this.speedFactor * Mth.m_14031_(rotationYaw1 * (float) (Math.PI / 180.0)))
                  * Math.abs((double)orbitOffsetDiffZ / distToOrbitOffset);
               double zMotion = (double)(this.speedFactor * Mth.m_14031_(desiredPitch * (float) (Math.PI / 180.0)))
                  * Math.abs((double)orbitOffsetDiffY / distToOrbitOffset);
               Vec3 motion = EntityNaga.this.m_20184_();
               EntityNaga.this.m_20256_(motion.m_82549_(new Vec3(xMotion, zMotion, yMotion).m_82546_(motion).m_82490_(0.1)));
            }
         } else if (EntityNaga.this.movement == EntityNaga.EnumNagaMovement.HOVERING
            && (EntityNaga.this.getAnimation() == IAnimatedEntity.NO_ANIMATION || EntityNaga.this.getAnimation() == EntityNaga.SPIT_ANIMATION)) {
            LivingEntity target = EntityNaga.this.m_5448_();
            if (target != null && EntityNaga.this.m_20280_(this.parentEntity) < 1600.0) {
               EntityNaga.this.m_21391_(target, 100.0F, 100.0F);
            }

            if (this.f_24981_ == Operation.MOVE_TO && this.courseChangeCooldown-- <= 0) {
               this.courseChangeCooldown = this.courseChangeCooldown + this.parentEntity.m_217043_().m_188503_(5) + 2;
               Vec3 Vector3d = new Vec3(
                  this.f_24975_ - this.parentEntity.m_20185_(), this.f_24976_ - this.parentEntity.m_20186_(), this.f_24977_ - this.parentEntity.m_20189_()
               );
               double d0 = Vector3d.m_82553_();
               Vector3d = Vector3d.m_82541_();
               if (this.checkCollisions(Vector3d, Mth.m_14165_(d0))) {
                  this.parentEntity.m_20256_(this.parentEntity.m_20184_().m_82549_(Vector3d.m_82490_(0.1)));
               } else {
                  this.f_24981_ = Operation.WAIT;
               }
            }
         }
      }

      public boolean checkCollisions(Vec3 p_220673_1_, int p_220673_2_) {
         AABB axisalignedbb = this.parentEntity.m_20191_();

         for (int i = 1; i < p_220673_2_; i++) {
            axisalignedbb = axisalignedbb.m_82383_(p_220673_1_);
            if (!this.parentEntity.f_19853_.m_45756_(this.parentEntity, axisalignedbb)) {
               return false;
            }
         }

         return true;
      }
   }

   class WanderGoal extends Goal {
      private boolean seesGround;

      WanderGoal() {
         this.m_7021_(EnumSet.of(Flag.MOVE));
      }

      public boolean m_8036_() {
         return EntityNaga.this.f_21344_.m_26571_() && EntityNaga.this.m_5448_() == null;
      }

      public boolean m_8045_() {
         return EntityNaga.this.f_21344_.m_26572_()
            && !EntityNaga.this.f_21344_.m_26567_().m_123314_(EntityNaga.this.m_20183_(), 4.0)
            && EntityNaga.this.m_5448_() == null;
      }

      public void m_8056_() {
         Vec3 vector3d = this.getRandomLocation();
         if (vector3d != null) {
            EntityNaga.this.f_21344_.m_26529_(0.5F);
            EntityNaga.this.f_21344_.m_26536_(EntityNaga.this.f_21344_.m_7864_(new BlockPos(vector3d), 1), 1.0);
         }
      }

      public void m_8037_() {
         super.m_8037_();
         if (!this.seesGround) {
            Vec3 newLocation = this.getRandomLocation();
            if (newLocation != null && this.seesGround) {
               EntityNaga.this.f_21344_.m_26536_(EntityNaga.this.f_21344_.m_7864_(new BlockPos(newLocation), 1), 1.0);
            }
         }
      }

      public void m_8041_() {
         super.m_8041_();
         EntityNaga.this.m_21573_().m_26573_();
      }

      @Nullable
      private Vec3 getRandomLocation() {
         Vec3 vector3d = EntityNaga.this.m_20252_(0.0F);
         Vec3 position = HoverRandomPos.m_148465_(EntityNaga.this, 24, 24, vector3d.f_82479_, vector3d.f_82481_, (float) (Math.PI / 2), 18, 8);
         if (position == null) {
            Vec3 sumPos = EntityNaga.this.m_20182_().m_82549_(vector3d);
            position = AirAndWaterRandomPos.m_148357_(EntityNaga.this, 24, 8, -8, sumPos.f_82479_, sumPos.f_82481_, (float) (Math.PI / 2));
            this.seesGround = false;
         } else {
            this.seesGround = true;
         }

         if (position != null && EntityNaga.this.f_19853_.m_46859_(new BlockPos(position).m_7495_())) {
            Vec3 offset = position.m_82546_(EntityNaga.this.m_20182_());
            AABB newBB = EntityNaga.this.m_20191_().m_82383_(offset);
            return EntityNaga.this.f_19853_.m_45772_(newBB)
                  && EntityNaga.this.f_19853_
                        .m_45547_(new ClipContext(EntityNaga.this.m_20182_(), position, Block.COLLIDER, Fluid.NONE, EntityNaga.this))
                        .m_6662_()
                     != Type.BLOCK
               ? position
               : null;
         } else {
            return null;
         }
      }
   }
}
