package com.bobmowzie.mowziesmobs.server.entity.lantern;

import com.bobmowzie.mowziesmobs.client.particle.ParticleCloud;
import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.ParticleOrb;
import com.bobmowzie.mowziesmobs.client.particle.ParticleVanillaCloudExtended;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleData;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationDieAI;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationTakeDamage;
import com.bobmowzie.mowziesmobs.server.ai.animation.SimpleAnimationAI;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.bobmowzie.mowziesmobs.server.entity.MowzieLLibraryEntity;
import com.bobmowzie.mowziesmobs.server.loot.LootTableHandler;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.ilexiconn.llibrary.server.animation.Animation;
import com.ilexiconn.llibrary.server.animation.AnimationHandler;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EntityLantern extends MowzieLLibraryEntity {
   public static final Animation DIE_ANIMATION = Animation.create(25);
   public static final Animation HURT_ANIMATION = Animation.create(10);
   public static final Animation PUFF_ANIMATION = Animation.create(28);
   private static final Animation[] ANIMATIONS = new Animation[]{DIE_ANIMATION, HURT_ANIMATION, PUFF_ANIMATION};
   public Vec3 dir;
   private int groundDist = 1;
   @OnlyIn(Dist.CLIENT)
   private Vec3[] pos;

   public EntityLantern(EntityType<? extends EntityLantern> type, Level world) {
      super(type, world);
      this.dir = null;
      if (world.f_46443_) {
         this.pos = new Vec3[1];
      }

      this.f_21342_ = new EntityLantern.MoveHelperController(this);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(2, new SimpleAnimationAI<>(this, PUFF_ANIMATION, false));
      this.f_21345_.m_25352_(3, new AnimationTakeDamage<>(this));
      this.f_21345_.m_25352_(1, new AnimationDieAI<>(this));
      this.f_21345_.m_25352_(5, new EntityLantern.RandomFlyGoal(this));
   }

   public static Builder createAttributes() {
      return MowzieEntity.createAttributes().m_22268_(Attributes.f_22276_, 4.0).m_22268_(Attributes.f_22280_, 0.3).m_22268_(Attributes.f_22279_, 0.2);
   }

   public float m_213856_() {
      return 1.572888E7F;
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      if (this.getAnimation() == PUFF_ANIMATION && this.getAnimationTick() == 7) {
         if (this.groundDist == 0) {
            this.groundDist = 1;
         }

         this.m_20256_(this.m_20184_().m_82520_(0.0, 0.2 + 0.2 / (double)this.groundDist, 0.0));
         if (!this.f_19853_.f_46443_) {
            if (this.getMoveHelperController().isMovingTo()) {
               Vec3 lvt_1_1_ = new Vec3(
                  this.m_21566_().m_25000_() - this.m_20185_(), this.m_21566_().m_25001_() - this.m_20186_(), this.m_21566_().m_25002_() - this.m_20189_()
               );
               double lvt_2_1_ = lvt_1_1_.m_82553_();
               lvt_1_1_ = lvt_1_1_.m_82541_();
               if (this.getMoveHelperController().canReach(lvt_1_1_, Mth.m_14165_(lvt_2_1_))) {
                  this.m_20256_(this.m_20184_().m_82549_(lvt_1_1_.m_82490_(0.2)));
               }
            }
         } else {
            for (int i = 0; i < 5; i++) {
               ParticleVanillaCloudExtended.spawnVanillaCloud(
                  this.f_19853_,
                  this.m_20185_(),
                  this.m_20186_() + 0.3,
                  this.m_20189_(),
                  -this.m_20184_().m_7096_() * 0.2 + 0.1 * ((double)this.f_19796_.m_188501_() - 0.5),
                  -this.m_20184_().m_7098_() * 0.2 + 0.1 * ((double)this.f_19796_.m_188501_() - 0.5),
                  -this.m_20184_().m_7094_() * 0.2 + 0.1 * ((double)this.f_19796_.m_188501_() - 0.5),
                  0.8 + this.f_19796_.m_188500_() * 1.0,
                  0.63671875,
                  0.96484375,
                  0.2890625,
                  0.95,
                  30.0
               );
            }

            for (int i = 0; i < 8; i++) {
               AdvancedParticleBase.spawnParticle(
                  this.f_19853_,
                  (ParticleType<AdvancedParticleData>)ParticleHandler.PIXEL.get(),
                  this.m_20185_(),
                  this.m_20186_() + 0.3,
                  this.m_20189_(),
                  -this.m_20184_().m_7096_() * 0.2 + 0.2 * ((double)this.f_19796_.m_188501_() - 0.5),
                  -this.m_20184_().m_7098_() * 0.2 + 0.1 * ((double)this.f_19796_.m_188501_() - 0.5),
                  -this.m_20184_().m_7094_() * 0.2 + 0.2 * ((double)this.f_19796_.m_188501_() - 0.5),
                  true,
                  0.0,
                  0.0,
                  0.0,
                  0.0,
                  4.0,
                  0.63671875,
                  0.96484375,
                  0.2890625,
                  1.0,
                  0.9,
                  (double)(17.0F + this.f_19796_.m_188501_() * 10.0F),
                  true,
                  true,
                  new ParticleComponent[]{
                     new ParticleComponent.PropertyControl(
                        ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                        new ParticleComponent.KeyTrack(new float[]{4.0F, 0.0F}, new float[]{0.8F, 1.0F}),
                        false
                     )
                  }
               );
            }
         }

         this.m_5496_((SoundEvent)MMSounds.ENTITY_LANTERN_PUFF.get(), 0.6F, 1.0F + this.f_19796_.m_188501_() * 0.2F);
      }

      if (!this.f_19853_.f_46443_ && this.getAnimation() == NO_ANIMATION && (this.groundDist < 5 || this.f_19796_.m_188503_(13) == 0 && this.groundDist < 16)) {
         AnimationHandler.INSTANCE.sendAnimationMessage(this, PUFF_ANIMATION);
      }

      if (this.groundDist >= 2) {
         this.m_20256_(this.m_20184_().m_82520_(0.0, -0.0055, 0.0));
      }

      if (this.f_19797_ % 5 == 0) {
         BlockPos checkPos = this.m_20183_();

         int i;
         for (i = 0; i < 16 && this.f_19853_.m_8055_(checkPos).m_60734_() == Blocks.f_50016_; i++) {
            checkPos = checkPos.m_7495_();
         }

         this.groundDist = i;
      }

      if (this.f_19853_.f_46443_ && (Boolean)ConfigHandler.CLIENT.glowEffect.get()) {
         this.pos[0] = this.m_20182_().m_82520_(0.0, (double)this.m_20206_() * 0.8, 0.0);
         if (this.f_19797_ % 70 == 0) {
            AdvancedParticleBase.spawnParticle(
               this.f_19853_,
               (ParticleType<AdvancedParticleData>)ParticleHandler.GLOW.get(),
               this.pos[0].f_82479_,
               this.pos[0].f_82480_,
               this.pos[0].f_82481_,
               0.0,
               0.0,
               0.0,
               true,
               0.0,
               0.0,
               0.0,
               0.0,
               20.0,
               0.8,
               0.95,
               0.35,
               1.0,
               1.0,
               70.0,
               true,
               true,
               new ParticleComponent[]{
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA,
                     new ParticleComponent.KeyTrack(new float[]{0.0F, 0.8F, 0.0F}, new float[]{0.0F, 0.5F, 1.0F}),
                     false
                  ),
                  new ParticleComponent.PinLocation(this.pos)
               }
            );
         }
      }
   }

   @Override
   protected void m_6153_() {
      super.m_6153_();
      if (this.getAnimationTick() == 1 && this.f_19853_.f_46443_) {
         for (int i = 0; i < 8; i++) {
            this.f_19853_
               .m_7106_(
                  ParticleTypes.f_123753_,
                  this.m_20185_(),
                  this.m_20186_(),
                  this.m_20189_(),
                  0.2 * ((double)this.f_19796_.m_188501_() - 0.5),
                  0.2 * ((double)this.f_19796_.m_188501_() - 0.5),
                  0.2 * ((double)this.f_19796_.m_188501_() - 0.5)
               );
            this.f_19853_
               .m_7106_(
                  new ParticleCloud.CloudData(
                     (ParticleType<ParticleCloud.CloudData>)ParticleHandler.CLOUD.get(),
                     0.63671875F,
                     0.96484375F,
                     0.2890625F,
                     10.0F + this.f_19796_.m_188501_() * 20.0F,
                     30,
                     ParticleCloud.EnumCloudBehavior.GROW,
                     0.9F
                  ),
                  this.m_20185_(),
                  this.m_20186_() + 0.3,
                  this.m_20189_(),
                  0.25 * ((double)this.f_19796_.m_188501_() - 0.5),
                  0.25 * ((double)this.f_19796_.m_188501_() - 0.5),
                  0.25 * ((double)this.f_19796_.m_188501_() - 0.5)
               );
            this.f_19853_
               .m_7106_(
                  new ParticleOrb.OrbData(0.63671875F, 0.96484375F, 0.2890625F, 1.5F, 25),
                  this.m_20185_(),
                  this.m_20186_() + 0.3,
                  this.m_20189_(),
                  (double)(0.2F * (this.f_19796_.m_188501_() - 0.5F)),
                  (double)(0.2F * (this.f_19796_.m_188501_() - 0.5F)),
                  (double)(0.2F * (this.f_19796_.m_188501_() - 0.5F))
               );
         }
      }

      if (this.getAnimationTick() == 2) {
         this.m_5496_((SoundEvent)MMSounds.ENTITY_LANTERN_POP.get(), 1.0F, 0.8F + this.f_19796_.m_188501_() * 0.4F);
      }
   }

   protected void m_7840_(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
   }

   public void m_7023_(Vec3 movement) {
      if (this.m_20069_()) {
         this.m_19920_(0.02F, movement);
         this.m_6478_(MoverType.SELF, this.m_20184_());
         this.m_20256_(this.m_20184_().m_82490_(0.8F));
      } else if (this.m_20077_()) {
         this.m_19920_(0.02F, movement);
         this.m_6478_(MoverType.SELF, this.m_20184_());
         this.m_20256_(this.m_20184_().m_82490_(0.5));
      } else {
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

         this.m_19920_(this.m_20096_() ? 0.1F * f1 : 0.02F, movement);
         this.m_6478_(MoverType.SELF, this.m_20184_());
         this.m_20256_(this.m_20184_().m_82490_((double)f));
      }

      this.f_20923_ = this.f_20924_;
      double d1 = this.m_20185_() - this.f_19854_;
      double d0 = this.m_20189_() - this.f_19856_;
      float f2 = Mth.m_14116_((float)(d1 * d1 + d0 * d0)) * 4.0F;
      if (f2 > 1.0F) {
         f2 = 1.0F;
      }

      this.f_20924_ = this.f_20924_ + (f2 - this.f_20924_) * 0.4F;
      this.f_20925_ = this.f_20925_ + this.f_20924_;
   }

   @Override
   protected ConfigHandler.SpawnConfig getSpawnConfig() {
      return ConfigHandler.COMMON.MOBS.LANTERN.spawnConfig;
   }

   public boolean m_6147_() {
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

   @Override
   public Animation[] getAnimations() {
      return ANIMATIONS;
   }

   protected ResourceLocation m_7582_() {
      return LootTableHandler.LANTERN;
   }

   @Override
   protected ConfigHandler.CombatConfig getCombatConfig() {
      return ConfigHandler.COMMON.MOBS.LANTERN.combatConfig;
   }

   public EntityLantern.MoveHelperController getMoveHelperController() {
      return this.m_21566_() instanceof EntityLantern.MoveHelperController ? (EntityLantern.MoveHelperController)super.m_21566_() : null;
   }

   static class MoveHelperController extends MoveControl {
      private final EntityLantern parentEntity;
      protected int courseChangeCooldown;

      public MoveHelperController(EntityLantern p_i45838_1_) {
         super(p_i45838_1_);
         this.parentEntity = p_i45838_1_;
      }

      public void m_8126_() {
         if (this.f_24981_ == Operation.MOVE_TO && this.courseChangeCooldown-- <= 0) {
            this.courseChangeCooldown = this.courseChangeCooldown + this.parentEntity.m_217043_().m_188503_(5) + 2;
            Vec3 lvt_1_1_ = new Vec3(
               this.f_24975_ - this.parentEntity.m_20185_(), this.f_24976_ - this.parentEntity.m_20186_(), this.f_24977_ - this.parentEntity.m_20189_()
            );
            double lvt_2_1_ = lvt_1_1_.m_82553_();
            lvt_1_1_ = lvt_1_1_.m_82541_();
            if (!this.canReach(lvt_1_1_, Mth.m_14165_(lvt_2_1_))) {
               this.f_24981_ = Operation.WAIT;
            }
         }
      }

      public boolean canReach(Vec3 p_220673_1_, int p_220673_2_) {
         AABB lvt_3_1_ = this.parentEntity.m_20191_();

         for (int lvt_4_1_ = 1; lvt_4_1_ < p_220673_2_; lvt_4_1_++) {
            lvt_3_1_ = lvt_3_1_.m_82383_(p_220673_1_);
            if (!this.parentEntity.f_19853_.m_45756_(this.parentEntity, lvt_3_1_)) {
               return false;
            }
         }

         return true;
      }

      public boolean isMovingTo() {
         return this.f_24981_ == Operation.MOVE_TO;
      }
   }

   static class RandomFlyGoal extends Goal {
      private final EntityLantern parentEntity;

      public RandomFlyGoal(EntityLantern p_i45836_1_) {
         this.parentEntity = p_i45836_1_;
         this.m_7021_(EnumSet.of(Flag.MOVE));
      }

      public boolean m_8036_() {
         MoveControl lvt_1_1_ = this.parentEntity.m_21566_();
         if (!lvt_1_1_.m_24995_()) {
            return true;
         } else {
            double lvt_2_1_ = lvt_1_1_.m_25000_() - this.parentEntity.m_20185_();
            double lvt_4_1_ = lvt_1_1_.m_25001_() - this.parentEntity.m_20186_();
            double lvt_6_1_ = lvt_1_1_.m_25002_() - this.parentEntity.m_20189_();
            double lvt_8_1_ = lvt_2_1_ * lvt_2_1_ + lvt_4_1_ * lvt_4_1_ + lvt_6_1_ * lvt_6_1_;
            return lvt_8_1_ < 1.0 || lvt_8_1_ > 3600.0;
         }
      }

      public boolean m_8045_() {
         return false;
      }

      public void m_8056_() {
         RandomSource lvt_1_1_ = this.parentEntity.m_217043_();
         double lvt_2_1_ = this.parentEntity.m_20185_() + (double)((lvt_1_1_.m_188501_() * 2.0F - 1.0F) * 16.0F);
         double lvt_4_1_ = this.parentEntity.m_20186_() + (double)((lvt_1_1_.m_188501_() * 2.0F - 1.0F) * 16.0F);
         double lvt_6_1_ = this.parentEntity.m_20189_() + (double)((lvt_1_1_.m_188501_() * 2.0F - 1.0F) * 16.0F);
         this.parentEntity.m_21566_().m_6849_(lvt_2_1_, lvt_4_1_, lvt_6_1_, 1.0);
      }
   }
}
