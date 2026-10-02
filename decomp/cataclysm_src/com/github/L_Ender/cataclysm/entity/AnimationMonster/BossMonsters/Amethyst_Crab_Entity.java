package com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters;

import com.github.L_Ender.cataclysm.client.particle.RingParticle;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.SimpleAnimationGoal;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.etc.CMEntityMoveHelper;
import com.github.L_Ender.cataclysm.entity.etc.SmartBodyHelper2;
import com.github.L_Ender.cataclysm.entity.etc.path.CMPathNavigateGround;
import com.github.L_Ender.cataclysm.entity.projectile.Amethyst_Cluster_Projectile_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.EarthQuake_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.IAnimatedEntity;
import java.util.EnumSet;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Path;
import org.jetbrains.annotations.Nullable;

public class Amethyst_Crab_Entity extends LLibrary_Boss_Monster implements NeutralMob {
   public static final Animation CRAB_SMASH = Animation.create(53);
   public static final Animation CRAB_SMASH_THREE = Animation.create(77);
   public static final Animation CRAB_DEATH = Animation.create(114);
   public static final Animation CRAB_BURROW = Animation.create(65);
   public static final Animation CRAB_BITE = Animation.create(48);
   private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.m_145020_(20, 39);
   private int remainingPersistentAngerTime;
   private int despawnTime = 4000;
   public static final int BURROW_ATTACK_COOLDOWN = 240;
   private int burrow_cooldown = 0;
   @Nullable
   private UUID persistentAngerTarget;

   public Amethyst_Crab_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.f_21364_ = 50;
      this.f_21342_ = new CMEntityMoveHelper(this, 45.0F);
      this.m_21441_(BlockPathTypes.UNPASSABLE_RAIL, 0.0F);
      this.m_21441_(BlockPathTypes.WATER, -1.0F);
      setConfigattribute(this, CMConfig.AmethystCrabHealthMultiplier, CMConfig.AmethystCrabDamageMultiplier);
   }

   public float getStepHeight() {
      return 1.5F;
   }

   protected int m_5639_(float p_21237_, float p_21238_) {
      return 0;
   }

   public boolean m_6673_(DamageSource p_20122_) {
      return super.m_6673_(p_20122_) || p_20122_.m_146707_();
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[]{NO_ANIMATION, CRAB_SMASH, CRAB_SMASH_THREE, CRAB_DEATH, CRAB_BURROW, CRAB_BITE};
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(2, new Amethyst_Crab_Entity.CrabMoveGoal(this, false, 1.0));
      this.f_21345_.m_25352_(0, new Amethyst_Crab_Entity.CrabSmashGoal(this, CRAB_SMASH));
      this.f_21345_.m_25352_(0, new Amethyst_Crab_Entity.CrabAttack(this, CRAB_SMASH_THREE, 10));
      this.f_21345_.m_25352_(0, new Amethyst_Crab_Entity.CrabBurrow(this, CRAB_BURROW));
      this.f_21345_.m_25352_(0, new Amethyst_Crab_Entity.CrabAttack(this, CRAB_BITE, 17));
      this.f_21345_.m_25352_(5, new RandomStrollGoal(this, 1.0, 80));
      this.f_21345_.m_25352_(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
   }

   public static Builder amethyst_crab() {
      return Monster.m_33035_()
         .m_22268_(Attributes.f_22277_, 20.0)
         .m_22268_(Attributes.f_22279_, 0.28F)
         .m_22268_(Attributes.f_22281_, 13.0)
         .m_22268_(Attributes.f_22276_, 200.0)
         .m_22268_(Attributes.f_22284_, 10.0)
         .m_22268_(Attributes.f_22278_, 1.0);
   }

   protected int m_7302_(int air) {
      return air;
   }

   public MobType m_6336_() {
      return MobType.f_21642_;
   }

   public boolean m_142535_(float p_148711_, float p_148712_, DamageSource p_148713_) {
      return false;
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
   }

   public boolean isKrusty() {
      String s = ChatFormatting.m_126649_(this.m_7755_().getString());
      return s != null && (s.toLowerCase().contains("eugene harold krabs") || s.toLowerCase().contains("mr.krabs"));
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      this.m_21678_(compound);
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
   }

   @Override
   public boolean m_6469_(DamageSource source, float damage) {
      if (this.getAnimation() == CRAB_BURROW && this.getAnimationTick() > 9 && this.getAnimationTick() < 52 && !source.m_19378_()) {
         this.m_5496_(SoundEvents.f_11668_, 0.4F, 2.0F);
         return false;
      } else {
         return super.m_6469_(source, damage);
      }
   }

   public boolean m_5545_(LevelAccessor worldIn, MobSpawnType spawnReasonIn) {
      return ModEntities.rollSpawn(CMConfig.AmethystCrabSpawnRolls, this.m_217043_(), spawnReasonIn) && super.m_5545_(worldIn, spawnReasonIn);
   }

   public static boolean canCrabSpawnSpawnRules(
      EntityType<? extends Amethyst_Crab_Entity> p_219020_, LevelAccessor p_219021_, MobSpawnType p_219022_, BlockPos p_219023_, RandomSource p_219024_
   ) {
      return m_219019_(p_219020_, p_219021_, p_219022_, p_219023_, p_219024_);
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      this.repelEntities(1.7F, 3.7F, 1.7F, 1.7F);
      if (this.burrow_cooldown > 0) {
         this.burrow_cooldown--;
      }

      if (this.getdespawnTimee() > 0) {
         this.setdespawnTime(this.getdespawnTimee() - 1);
      }
   }

   public void m_8107_() {
      super.m_8107_();
      if (this.getAnimation() == CRAB_SMASH && this.getAnimationTick() == 22) {
         this.AreaAttack(4.0F, 4.0F, 70.0F, 1.25F, 120);
         this.m_5496_(SoundEvents.f_11913_, 1.0F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
         this.Attackparticle(2.4F, -0.4F);
         ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 15.0F, 0.1F, 0, 20);
      }

      if (this.getAnimation() == CRAB_SMASH_THREE) {
         if (this.getAnimationTick() == 16) {
            this.Attackparticle(2.2F, -0.2F);
            this.EarthQuakeSummon(2.2F, -0.2F);
            this.m_5496_(SoundEvents.f_11913_, 1.0F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 15.0F, 0.1F, 0, 20);
         }

         if (this.getAnimationTick() == 36) {
            this.Attackparticle(1.8F, -1.5F);
            this.EarthQuakeSummon(1.8F, -1.5F);
            this.m_5496_(SoundEvents.f_11913_, 1.0F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 15.0F, 0.1F, 0, 20);
         }

         if (this.getAnimationTick() == 56) {
            this.Attackparticle(1.7F, 1.3F);
            this.EarthQuakeSummon(1.7F, 1.3F);
            this.m_5496_(SoundEvents.f_11913_, 1.0F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 15.0F, 0.1F, 0, 20);
         }
      }

      if (this.getAnimation() == CRAB_BURROW) {
         for (int l = 1; l <= 10; l += 3) {
            if (this.getAnimationTick() == l) {
               this.BurrowSound();
               this.BurrowParticle(0.6F, 0.0F, 2.0F);
            }
         }

         for (int lx = 39; lx <= 48; lx += 3) {
            if (this.getAnimationTick() == lx) {
               this.BurrowParticle(0.6F, 0.0F, 2.0F);
               this.BurrowSound();
            }
         }
      }

      if (this.getAnimation() == CRAB_BITE) {
         if (this.getAnimationTick() == 14) {
            this.m_5496_((SoundEvent)ModSounds.CRAB_BITE.get(), 1.0F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
         }

         if (this.getAnimationTick() == 17) {
            this.AreaAttack(4.5F, 4.5F, 110.0F, 1.25F, 120);
         }
      }
   }

   private void AreaAttack(float range, float height, float arc, float damage, int shieldbreakticks) {
      for (LivingEntity entityHit : this.getEntityLivingBaseNearby((double)range, (double)height, (double)range, (double)range)) {
         float entityHitAngle = (float)(
            (Math.atan2(entityHit.m_20189_() - this.m_20189_(), entityHit.m_20185_() - this.m_20185_()) * (180.0 / Math.PI) - 90.0) % 360.0
         );
         float entityAttackingAngle = this.f_20883_ % 360.0F;
         if (entityHitAngle < 0.0F) {
            entityHitAngle += 360.0F;
         }

         if (entityAttackingAngle < 0.0F) {
            entityAttackingAngle += 360.0F;
         }

         float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
         float entityHitDistance = (float)Math.sqrt(
            (entityHit.m_20189_() - this.m_20189_()) * (entityHit.m_20189_() - this.m_20189_())
               + (entityHit.m_20185_() - this.m_20185_()) * (entityHit.m_20185_() - this.m_20185_())
         );
         if ((
               entityHitDistance <= range && entityRelativeAngle <= arc / 2.0F && entityRelativeAngle >= -arc / 2.0F
                  || entityRelativeAngle >= 360.0F - arc / 2.0F
                  || entityRelativeAngle <= -360.0F + arc / 2.0F
            )
            && !(entityHit instanceof Amethyst_Crab_Entity)) {
            DamageSource damagesource = DamageSource.m_19370_(this);
            entityHit.m_6469_(damagesource, (float)this.m_21133_(Attributes.f_22281_) * damage);
            if (entityHit.m_21275_(damagesource) && entityHit instanceof Player player && shieldbreakticks > 0) {
               this.disableShield(player, shieldbreakticks);
            }
         }
      }
   }

   private void Attackparticle(float vec, float math) {
      if (this.f_19853_.f_46443_) {
         float f = Mth.m_14089_(this.f_20883_ * (float) (Math.PI / 180.0));
         float f1 = Mth.m_14031_(this.f_20883_ * (float) (Math.PI / 180.0));
         double theta = (double)this.f_20883_ * (Math.PI / 180.0);
         double vecX = Math.cos(++theta);
         double vecZ = Math.sin(theta);

         for (int i1 = 0; i1 < 80 + this.f_19796_.m_188503_(12); i1++) {
            double DeltaMovementX = this.m_217043_().m_188583_() * 0.07;
            double DeltaMovementY = this.m_217043_().m_188583_() * 0.07;
            double DeltaMovementZ = this.m_217043_().m_188583_() * 0.07;
            float angle = (float) (Math.PI / 180.0) * this.f_20883_ + (float)i1;
            double extraX = 1.0 * (double)Mth.m_14031_((float)(Math.PI + (double)angle));
            double extraY = 0.3F;
            double extraZ = 1.0 * (double)Mth.m_14089_(angle);
            int hitX = Mth.m_14107_(this.m_20185_() + (double)vec * vecX + extraX);
            int hitY = Mth.m_14107_(this.m_20186_());
            int hitZ = Mth.m_14107_(this.m_20189_() + (double)vec * vecZ + extraZ);
            BlockPos hit = new BlockPos(hitX, hitY, hitZ);
            BlockState block = this.f_19853_.m_8055_(hit.m_7495_());
            if (block.m_60799_() != RenderShape.INVISIBLE) {
               this.f_19853_
                  .m_7106_(
                     new BlockParticleOption(ParticleTypes.f_123794_, block),
                     this.m_20185_() + (double)vec * vecX + extraX + (double)(f * math),
                     this.m_20186_() + extraY,
                     this.m_20189_() + (double)vec * vecZ + extraZ + (double)(f1 * math),
                     DeltaMovementX,
                     DeltaMovementY,
                     DeltaMovementZ
                  );
            }
         }

         this.f_19853_
            .m_7106_(
               new RingParticle.RingData(0.0F, (float) (Math.PI / 2), 25, 1.0F, 1.0F, 1.0F, 1.0F, 25.0F, false, RingParticle.EnumRingBehavior.GROW_THEN_SHRINK),
               this.m_20185_() + (double)vec * vecX + (double)(f * math),
               this.m_20186_() + 0.3F,
               this.m_20189_() + (double)vec * vecZ + (double)(f1 * math),
               0.0,
               0.0,
               0.0
            );
      }
   }

   private void BurrowParticle(float vec, float math, float size) {
      if (this.f_19853_.f_46443_) {
         for (int i1 = 0; i1 < 80 + this.f_19796_.m_188503_(12); i1++) {
            double DeltaMovementX = this.m_217043_().m_188583_() * 0.07;
            double DeltaMovementY = this.m_217043_().m_188583_() * 0.1;
            double DeltaMovementZ = this.m_217043_().m_188583_() * 0.07;
            float angle = (float) (Math.PI / 180.0) * this.f_20883_ + (float)i1;
            float f = Mth.m_14089_(this.f_20883_ * (float) (Math.PI / 180.0));
            float f1 = Mth.m_14031_(this.f_20883_ * (float) (Math.PI / 180.0));
            double extraX = (double)(size * Mth.m_14031_((float)(Math.PI + (double)angle)));
            double extraY = 0.3F;
            double extraZ = (double)(size * Mth.m_14089_(angle));
            double theta = (double)this.f_20883_ * (Math.PI / 180.0);
            double vecX = Math.cos(++theta);
            double vecZ = Math.sin(theta);
            int hitX = Mth.m_14107_(this.m_20185_() + (double)vec * vecX + extraX);
            int hitY = Mth.m_14107_(this.m_20186_());
            int hitZ = Mth.m_14107_(this.m_20189_() + (double)vec * vecZ + extraZ);
            BlockPos hit = new BlockPos(hitX, hitY, hitZ);
            BlockState block = this.f_19853_.m_8055_(hit.m_7495_());
            if (block.m_60799_() != RenderShape.INVISIBLE) {
               this.f_19853_
                  .m_7106_(
                     new BlockParticleOption(ParticleTypes.f_123794_, block),
                     this.m_20185_() + (double)vec * vecX + extraX + (double)(f * math),
                     this.m_20186_() + extraY,
                     this.m_20189_() + (double)vec * vecZ + extraZ + (double)(f1 * math),
                     DeltaMovementX,
                     DeltaMovementY,
                     DeltaMovementZ
                  );
            }
         }
      }
   }

   private void BurrowSound() {
      float angle = (float) (Math.PI / 180.0) * this.f_20883_;
      double extraX = 1.0 * (double)Mth.m_14031_((float)(Math.PI + (double)angle));
      double extraZ = 1.0 * (double)Mth.m_14089_(angle);
      int hitX = Mth.m_14107_(this.m_20185_() + extraX);
      int hitY = Mth.m_14107_(this.m_20186_());
      int hitZ = Mth.m_14107_(this.m_20189_() + extraZ);
      BlockPos hit = new BlockPos(hitX, hitY, hitZ);
      BlockState block = this.f_19853_.m_8055_(hit.m_7495_());
      SoundType soundtype = block.getSoundType(this.f_19853_, hit, this);
      this.f_19853_.m_6269_((Player)null, this, soundtype.m_56775_(), SoundSource.HOSTILE, 3.0F, 0.8F + this.m_217043_().m_188501_() * 0.1F);
   }

   private void EarthQuakeSummon(float vec, float math) {
      float f = Mth.m_14089_(this.f_20883_ * (float) (Math.PI / 180.0));
      float f1 = Mth.m_14031_(this.f_20883_ * (float) (Math.PI / 180.0));
      double theta = (double)this.f_20883_ * (Math.PI / 180.0);
      double vecX = Math.cos(++theta);
      double vecZ = Math.sin(theta);
      int quakeCount = 16;
      float angle = 22.5F;

      for (int i = 0; i < 16; i++) {
         EarthQuake_Entity peq = new EarthQuake_Entity(this.f_19853_, this);
         peq.setDamage((float)CMConfig.AmethystCrabEarthQuakeDamage);
         peq.m_37251_(this, 0.0F, angle * (float)i, 0.0F, 0.25F, 0.0F);
         peq.m_6034_(this.m_20185_() + (double)vec * vecX + (double)(f * math), this.m_20186_(), this.m_20189_() + (double)vec * vecZ + (double)(f1 * math));
         this.f_19853_.m_7967_(peq);
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

   @Nullable
   @Override
   public Animation getDeathAnimation() {
      return CRAB_DEATH;
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.CRAB_HURT.get();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)ModSounds.CRAB_DEATH.get();
   }

   protected BodyRotationControl m_7560_() {
      return new SmartBodyHelper2(this);
   }

   protected PathNavigation m_6037_(Level worldIn) {
      return new CMPathNavigateGround(this, worldIn);
   }

   public void m_7870_(int p_32515_) {
      this.remainingPersistentAngerTime = p_32515_;
   }

   public int m_6784_() {
      return this.remainingPersistentAngerTime;
   }

   public void setdespawnTime(int p_32515_) {
      this.despawnTime = p_32515_;
   }

   public int getdespawnTimee() {
      return this.despawnTime;
   }

   @Override
   public boolean m_6785_(double p_21542_) {
      return this.despawnTime >= 0;
   }

   @Nullable
   public UUID m_6120_() {
      return this.persistentAngerTarget;
   }

   public void m_6925_(@javax.annotation.Nullable UUID p_32509_) {
      this.persistentAngerTarget = p_32509_;
   }

   public void m_6825_() {
      this.m_7870_(PERSISTENT_ANGER_TIME.m_214085_(this.f_19796_));
   }

   static class CrabAttack extends SimpleAnimationGoal<Amethyst_Crab_Entity> {
      private final int look;

      public CrabAttack(Amethyst_Crab_Entity entity, Animation animation, int look) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
         this.look = look;
      }

      public void m_8056_() {
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }

         super.m_8056_();
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (this.entity.getAnimationTick() < this.look && target != null) {
            this.entity.m_21391_(target, 30.0F, 30.0F);
            this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
         } else {
            this.entity.m_146922_(this.entity.f_19859_);
         }
      }
   }

   static class CrabBurrow extends SimpleAnimationGoal<Amethyst_Crab_Entity> {
      public CrabBurrow(Amethyst_Crab_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void m_8056_() {
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }

         this.entity.burrow_cooldown = 240;
         super.m_8056_();
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (this.entity.getAnimationTick() < 48 && target != null) {
            this.entity.m_21391_(target, 30.0F, 30.0F);
            this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
         } else {
            this.entity.m_146922_(this.entity.f_19859_);
         }

         if (this.entity.getAnimationTick() == 50) {
            for (int i = 0; i < 32; i++) {
               float throwAngle = (float)i * (float) Math.PI / 16.0F;
               double sx = this.entity.m_20185_() + (double)(Mth.m_14089_(throwAngle) * 1.0F);
               double sy = this.entity.m_20186_() + (double)this.entity.m_20206_() * 0.2;
               double sz = this.entity.m_20189_() + (double)(Mth.m_14031_(throwAngle) * 1.0F);
               double vx = (double)Mth.m_14089_(throwAngle);
               double vy = (double)(0.0F + this.entity.f_19796_.m_188501_() * 0.3F);
               double vz = (double)Mth.m_14031_(throwAngle);
               double v3 = (double)Mth.m_14116_((float)(vx * vx + vz * vz));
               Amethyst_Cluster_Projectile_Entity projectile = new Amethyst_Cluster_Projectile_Entity(
                  (EntityType<Amethyst_Cluster_Projectile_Entity>)ModEntities.AMETHYST_CLUSTER_PROJECTILE.get(),
                  this.entity.f_19853_,
                  this.entity,
                  (float)CMConfig.AmethystClusterdamage
               );
               projectile.m_7678_(sx, sy, sz, (float)i * 11.25F, this.entity.m_146909_());
               float speed = 0.8F;
               projectile.m_6686_(vx, vy + v3 * 0.2F, vz, speed, 1.0F);
               this.entity.f_19853_.m_7967_(projectile);
            }
         }
      }
   }

   static class CrabMoveGoal extends Goal {
      private final Amethyst_Crab_Entity crab;
      private final boolean followingTargetEvenIfNotSeen;
      private Path path;
      private int delayCounter;
      protected final double moveSpeed;

      public CrabMoveGoal(Amethyst_Crab_Entity boss, boolean followingTargetEvenIfNotSeen, double moveSpeed) {
         this.crab = boss;
         this.followingTargetEvenIfNotSeen = followingTargetEvenIfNotSeen;
         this.moveSpeed = moveSpeed;
         this.m_7021_(EnumSet.of(Flag.LOOK, Flag.MOVE));
      }

      public boolean m_8036_() {
         LivingEntity target = this.crab.m_5448_();
         return target != null && target.m_6084_() && this.crab.getAnimation() == IAnimatedEntity.NO_ANIMATION;
      }

      public void m_8041_() {
         this.crab.m_21573_().m_26573_();
         LivingEntity livingentity = this.crab.m_5448_();
         if (!EntitySelector.f_20406_.test(livingentity)) {
            this.crab.m_6710_((LivingEntity)null);
         }

         this.crab.m_21561_(false);
         this.crab.m_21573_().m_26573_();
      }

      public boolean m_8045_() {
         LivingEntity target = this.crab.m_5448_();
         if (target == null) {
            return false;
         } else if (!target.m_6084_()) {
            return false;
         } else if (!this.followingTargetEvenIfNotSeen) {
            return !this.crab.m_21573_().m_26571_();
         } else {
            return !this.crab.m_21444_(target.m_20183_()) ? false : !(target instanceof Player) || !target.m_5833_() && !((Player)target).m_7500_();
         }
      }

      public void m_8056_() {
         this.crab.m_21573_().m_26536_(this.path, this.moveSpeed);
         this.crab.m_21561_(true);
      }

      public boolean m_183429_() {
         return true;
      }

      public void m_8037_() {
         LivingEntity target = this.crab.m_5448_();
         if (target != null) {
            this.crab.m_21563_().m_24960_(target, 30.0F, 30.0F);
            double distSq = this.crab.m_20275_(target.m_20185_(), target.m_20191_().f_82289_, target.m_20189_());
            if (--this.delayCounter <= 0) {
               this.delayCounter = 4 + this.crab.m_217043_().m_188503_(7);
               if (distSq > Math.pow(this.crab.m_21051_(Attributes.f_22277_).m_22135_(), 2.0)) {
                  if (!this.crab.m_21691_() && !this.crab.m_21573_().m_5624_(target, 1.0)) {
                     this.delayCounter += 5;
                  }
               } else {
                  this.crab.m_21573_().m_5624_(target, this.moveSpeed);
               }
            }

            if (target.m_6084_() && this.crab.getAnimation() == IAnimatedEntity.NO_ANIMATION) {
               if (this.crab.burrow_cooldown <= 0 && this.crab.m_217043_().m_188501_() * 100.0F < 6.0F && (double)this.crab.m_20270_(target) <= 8.0) {
                  this.crab.setAnimation(Amethyst_Crab_Entity.CRAB_BURROW);
               } else if (this.crab.m_217043_().m_188501_() * 100.0F < 24.0F && (double)this.crab.m_20270_(target) <= 3.75) {
                  if (this.crab.f_19796_.m_188503_(2) == 0) {
                     this.crab.setAnimation(Amethyst_Crab_Entity.CRAB_BITE);
                  } else {
                     this.crab.setAnimation(Amethyst_Crab_Entity.CRAB_SMASH);
                  }
               } else if (this.crab.m_217043_().m_188501_() * 100.0F < 16.0F && (double)this.crab.m_20270_(target) <= 3.75 && target.m_20096_()) {
                  this.crab.setAnimation(Amethyst_Crab_Entity.CRAB_SMASH_THREE);
               }
            }
         }
      }
   }

   static class CrabSmashGoal extends SimpleAnimationGoal<Amethyst_Crab_Entity> {
      public CrabSmashGoal(Amethyst_Crab_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void m_8056_() {
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 90.0F);
         }

         super.m_8056_();
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (this.entity.getAnimationTick() < 19 && target != null) {
            this.entity.m_21391_(target, 30.0F, 30.0F);
            this.entity.m_21573_().m_5624_(target, 1.0);
            this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
         } else {
            this.entity.m_146922_(this.entity.f_19859_);
         }

         if (this.entity.getAnimationTick() == 19) {
            this.entity.m_21573_().m_26573_();
         }
      }
   }
}
