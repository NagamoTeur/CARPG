package com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters;

import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.IAnimatedEntity;
import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.monster.AbstractIllager.IllagerArmPose;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Nameless_Sorcerer_Entity extends AbstractIllager implements IAnimatedEntity {
   private static final EntityDataAccessor<Byte> SPELL = SynchedEntityData.m_135353_(Nameless_Sorcerer_Entity.class, EntityDataSerializers.f_135027_);
   protected int spellTicks;
   private Nameless_Sorcerer_Entity.SpellType activeSpell = Nameless_Sorcerer_Entity.SpellType.NONE;
   private static final EntityDataAccessor<Boolean> IS_ILLUSION = SynchedEntityData.m_135353_(Nameless_Sorcerer_Entity.class, EntityDataSerializers.f_135035_);
   private int animationTick;
   private Animation currentAnimation;

   public Nameless_Sorcerer_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.f_21364_ = 300;
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(1, new Nameless_Sorcerer_Entity.CastingSpellGoal());
      this.f_21345_.m_25352_(2, new AvoidEntityGoal(this, Player.class, 8.0F, 0.6, 1.0));
      this.f_21345_.m_25352_(5, new Nameless_Sorcerer_Entity.AttackSpellGoal());
      this.f_21345_.m_25352_(6, new Nameless_Sorcerer_Entity.TeleportSpellGoal());
      this.f_21345_.m_25352_(7, new Nameless_Sorcerer_Entity.IllusionSpellGoal());
      this.f_21345_.m_25352_(8, new RandomStrollGoal(this, 0.6));
      this.f_21345_.m_25352_(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
      this.f_21345_.m_25352_(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[]{Raider.class}).m_26044_(new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, true).m_26146_(300));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, AbstractVillager.class, false).m_26146_(300));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, IronGolem.class, false));
   }

   public Animation[] getAnimations() {
      return new Animation[]{NO_ANIMATION};
   }

   public int getAnimationTick() {
      return this.animationTick;
   }

   public void setAnimationTick(int tick) {
      this.animationTick = tick;
   }

   public Animation getAnimation() {
      return this.currentAnimation;
   }

   public void setAnimation(Animation animation) {
      this.currentAnimation = animation;
   }

   public static Builder nameless_sorcerer() {
      return Monster.m_33035_().m_22268_(Attributes.f_22279_, 0.5).m_22268_(Attributes.f_22277_, 24.0).m_22268_(Attributes.f_22276_, 50.0);
   }

   protected boolean m_7341_(Entity p_31508_) {
      return false;
   }

   public boolean m_7490_() {
      return false;
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(SPELL, (byte)0);
      this.f_19804_.m_135372_(IS_ILLUSION, false);
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.spellTicks = compound.m_128451_("SpellTicks");
      this.setIsIllusion(compound.m_128471_("is_Illusion"));
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128405_("SpellTicks", this.spellTicks);
      compound.m_128379_("is_Illusion", this.getIsIllusion());
   }

   public boolean m_7307_(Entity p_32665_) {
      if (p_32665_ == null) {
         return false;
      } else if (p_32665_ == this) {
         return true;
      } else if (super.m_7307_(p_32665_)) {
         return true;
      } else if (p_32665_ instanceof Vex) {
         return this.m_7307_(((Vex)p_32665_).m_34026_());
      } else {
         return p_32665_ instanceof LivingEntity && ((LivingEntity)p_32665_).m_6336_() == MobType.f_21643_
            ? this.m_5647_() == null && p_32665_.m_5647_() == null
            : false;
      }
   }

   public void setIsIllusion(boolean isIllusion) {
      this.f_19804_.m_135381_(IS_ILLUSION, isIllusion);
   }

   public boolean getIsIllusion() {
      return (Boolean)this.f_19804_.m_135370_(IS_ILLUSION);
   }

   @OnlyIn(Dist.CLIENT)
   public IllagerArmPose m_6768_() {
      if (this.isSpellcasting()) {
         return IllagerArmPose.SPELLCASTING;
      } else {
         return this.m_37888_() ? IllagerArmPose.CELEBRATING : IllagerArmPose.CROSSED;
      }
   }

   public boolean m_6469_(DamageSource source, float damage) {
      if (!this.getIsIllusion()) {
         return super.m_6469_(source, damage);
      } else {
         this.m_5496_(SoundEvents.f_12049_, 1.0F, 0.9F);

         for (int i = 0; i < 20; i++) {
            double d0 = this.f_19796_.m_188583_() * 0.02;
            double d1 = this.f_19796_.m_188583_() * 0.02;
            double d2 = this.f_19796_.m_188583_() * 0.02;
            this.f_19853_.m_7106_(ParticleTypes.f_123759_, this.m_20208_(1.0), this.m_20187_(), this.m_20262_(1.0), d0, d1, d2);
         }

         this.m_142687_(RemovalReason.KILLED);
         return false;
      }
   }

   public boolean isSpellcasting() {
      return this.f_19853_.f_46443_ ? (Byte)this.f_19804_.m_135370_(SPELL) > 0 : this.spellTicks > 0;
   }

   public void setSpellType(Nameless_Sorcerer_Entity.SpellType spellType) {
      this.activeSpell = spellType;
      this.f_19804_.m_135381_(SPELL, (byte)spellType.id);
   }

   protected Nameless_Sorcerer_Entity.SpellType getSpellType() {
      return !this.f_19853_.f_46443_ ? this.activeSpell : Nameless_Sorcerer_Entity.SpellType.getFromId((Byte)this.f_19804_.m_135370_(SPELL));
   }

   protected void m_8024_() {
      super.m_8024_();
      if (this.spellTicks > 0) {
         this.spellTicks--;
      }
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_11861_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_11864_;
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return SoundEvents.f_11866_;
   }

   public void m_7895_(int p_32632_, boolean p_32633_) {
   }

   public SoundEvent m_7930_() {
      return SoundEvents.f_11863_;
   }

   protected SoundEvent getSpellSound() {
      return SoundEvents.f_11862_;
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.f_19853_.f_46443_ && this.isSpellcasting()) {
         Nameless_Sorcerer_Entity.SpellType Nameless_Sorcerer_Entity$spelltype = this.getSpellType();
         double d0 = this.m_217043_().m_188583_() * 0.07;
         double d1 = this.m_217043_().m_188583_() * 0.07;
         double d2 = this.m_217043_().m_188583_() * 0.07;
         float f = this.f_20883_ * (float) (Math.PI / 180.0) + Mth.m_14089_((float)this.f_19797_ * 0.6662F) * 0.25F;
         float f1 = Mth.m_14089_(f);
         float f2 = Mth.m_14031_(f);
         if (Nameless_Sorcerer_Entity$spelltype == Nameless_Sorcerer_Entity.SpellType.TELEPORTSPELL) {
            this.f_19853_
               .m_7106_(ParticleTypes.f_123760_, this.m_20185_() + (double)f1 * 0.6, this.m_20186_() + 1.8, this.m_20189_() + (double)f2 * 0.6, d0, d1, d2);
            this.f_19853_
               .m_7106_(ParticleTypes.f_123760_, this.m_20185_() - (double)f1 * 0.6, this.m_20186_() + 1.8, this.m_20189_() - (double)f2 * 0.6, d0, d1, d2);
         }

         if (Nameless_Sorcerer_Entity$spelltype == Nameless_Sorcerer_Entity.SpellType.FANGS) {
            this.f_19853_
               .m_7106_(ParticleTypes.f_123797_, this.m_20185_() + (double)f1 * 0.6, this.m_20186_() + 1.8, this.m_20189_() + (double)f2 * 0.6, d0, d1, d2);
            this.f_19853_
               .m_7106_(ParticleTypes.f_123797_, this.m_20185_() - (double)f1 * 0.6, this.m_20186_() + 1.8, this.m_20189_() - (double)f2 * 0.6, d0, d1, d2);
         }

         if (Nameless_Sorcerer_Entity$spelltype == Nameless_Sorcerer_Entity.SpellType.ILLUSION) {
            this.f_19853_
               .m_7106_(ParticleTypes.f_123762_, this.m_20185_() + (double)f1 * 0.6, this.m_20186_() + 1.8, this.m_20189_() + (double)f2 * 0.6, d0, d1, d2);
            this.f_19853_
               .m_7106_(ParticleTypes.f_123762_, this.m_20185_() - (double)f1 * 0.6, this.m_20186_() + 1.8, this.m_20189_() - (double)f2 * 0.6, d0, d1, d2);
         }
      }
   }

   protected int getSpellTicks() {
      return this.spellTicks;
   }

   class AttackSpellGoal extends Nameless_Sorcerer_Entity.UseSpellGoal {
      private AttackSpellGoal() {
      }

      @Override
      protected int getCastingTime() {
         return 20;
      }

      @Override
      protected int getCastingInterval() {
         return 45;
      }

      @Override
      protected void castSpell() {
         LivingEntity target = Nameless_Sorcerer_Entity.this.m_5448_();
         double d0 = Math.min(target.m_20186_(), Nameless_Sorcerer_Entity.this.m_20186_());
         double d1 = Math.max(target.m_20186_(), Nameless_Sorcerer_Entity.this.m_20186_()) + 1.0;
         float f = (float)Mth.m_14136_(
            target.m_20189_() - Nameless_Sorcerer_Entity.this.m_20189_(), target.m_20185_() - Nameless_Sorcerer_Entity.this.m_20185_()
         );
         if (Nameless_Sorcerer_Entity.this.m_20280_(target) < 12.0) {
            for (int i = 0; i < 5; i++) {
               float f1 = f + (float)i * (float) Math.PI * 0.4F;
               this.spawnFangs(
                  Nameless_Sorcerer_Entity.this.m_20185_() + (double)Mth.m_14089_(f1) * 1.5,
                  Nameless_Sorcerer_Entity.this.m_20189_() + (double)Mth.m_14031_(f1) * 1.5,
                  d0,
                  d1,
                  f1,
                  0
               );
               this.spawnFangs(
                  Nameless_Sorcerer_Entity.this.m_20185_() + (double)Mth.m_14089_(f1) * 1.5,
                  Nameless_Sorcerer_Entity.this.m_20189_() + (double)Mth.m_14031_(f1) * 1.5,
                  d0,
                  d1,
                  f1,
                  40
               );
            }

            for (int k = 0; k < 8; k++) {
               float f2 = f + (float)k * (float) Math.PI * 2.0F / 8.0F + (float) (Math.PI * 2.0 / 5.0);
               this.spawnFangs(
                  Nameless_Sorcerer_Entity.this.m_20185_() + (double)Mth.m_14089_(f2) * 2.5,
                  Nameless_Sorcerer_Entity.this.m_20189_() + (double)Mth.m_14031_(f2) * 2.5,
                  d0,
                  d1,
                  f2,
                  3
               );
               this.spawnFangs(
                  Nameless_Sorcerer_Entity.this.m_20185_() + (double)Mth.m_14089_(f2) * 2.5,
                  Nameless_Sorcerer_Entity.this.m_20189_() + (double)Mth.m_14031_(f2) * 2.5,
                  d0,
                  d1,
                  f2,
                  37
               );
            }

            for (int k = 0; k < 13; k++) {
               float f3 = f + (float)k * (float) Math.PI * 2.0F / 13.0F + (float) (Math.PI / 5);
               this.spawnFangs(
                  Nameless_Sorcerer_Entity.this.m_20185_() + (double)Mth.m_14089_(f3) * 3.5,
                  Nameless_Sorcerer_Entity.this.m_20189_() + (double)Mth.m_14031_(f3) * 3.5,
                  d0,
                  d1,
                  f3,
                  10
               );
               this.spawnFangs(
                  Nameless_Sorcerer_Entity.this.m_20185_() + (double)Mth.m_14089_(f3) * 3.5,
                  Nameless_Sorcerer_Entity.this.m_20189_() + (double)Mth.m_14031_(f3) * 3.5,
                  d0,
                  d1,
                  f3,
                  30
               );
            }

            for (int k = 0; k < 16; k++) {
               float f4 = f + (float)k * (float) Math.PI * 2.0F / 16.0F + (float) (Math.PI / 10);
               this.spawnFangs(
                  Nameless_Sorcerer_Entity.this.m_20185_() + (double)Mth.m_14089_(f4) * 4.5,
                  Nameless_Sorcerer_Entity.this.m_20189_() + (double)Mth.m_14031_(f4) * 4.5,
                  d0,
                  d1,
                  f4,
                  15
               );
               this.spawnFangs(
                  Nameless_Sorcerer_Entity.this.m_20185_() + (double)Mth.m_14089_(f4) * 4.5,
                  Nameless_Sorcerer_Entity.this.m_20189_() + (double)Mth.m_14031_(f4) * 4.5,
                  d0,
                  d1,
                  f4,
                  25
               );
            }

            for (int k = 0; k < 19; k++) {
               float f5 = f + (float)k * (float) Math.PI * 2.0F / 19.0F + (float) (Math.PI / 20);
               this.spawnFangs(
                  Nameless_Sorcerer_Entity.this.m_20185_() + (double)Mth.m_14089_(f5) * 5.5,
                  Nameless_Sorcerer_Entity.this.m_20189_() + (double)Mth.m_14031_(f5) * 5.5,
                  d0,
                  d1,
                  f5,
                  20
               );
            }
         } else {
            for (int l = 0; l < 16; l++) {
               double d2 = 1.25 * (double)(l + 1);
               int j = 1 * l;
               this.spawnFangs(
                  Nameless_Sorcerer_Entity.this.m_20185_() + (double)Mth.m_14089_(f) * d2,
                  Nameless_Sorcerer_Entity.this.m_20189_() + (double)Mth.m_14031_(f) * d2,
                  d0,
                  d1,
                  f,
                  j
               );
            }
         }
      }

      private void spawnFangs(double p_190876_1_, double p_190876_3_, double p_190876_5_, double p_190876_7_, float p_190876_9_, int p_190876_10_) {
         BlockPos blockpos = new BlockPos(p_190876_1_, p_190876_7_, p_190876_3_);
         boolean flag = false;
         double d0 = 0.0;

         do {
            BlockPos blockpos1 = blockpos.m_7495_();
            BlockState blockstate = Nameless_Sorcerer_Entity.this.f_19853_.m_8055_(blockpos1);
            if (blockstate.m_60783_(Nameless_Sorcerer_Entity.this.f_19853_, blockpos1, Direction.UP)) {
               if (!Nameless_Sorcerer_Entity.this.f_19853_.m_46859_(blockpos)) {
                  BlockState blockstate1 = Nameless_Sorcerer_Entity.this.f_19853_.m_8055_(blockpos);
                  VoxelShape voxelshape = blockstate1.m_60812_(Nameless_Sorcerer_Entity.this.f_19853_, blockpos);
                  if (!voxelshape.m_83281_()) {
                     d0 = voxelshape.m_83297_(Axis.Y);
                  }
               }

               flag = true;
               break;
            }

            blockpos = blockpos.m_7495_();
         } while (blockpos.m_123342_() >= Mth.m_14107_(p_190876_5_) - 1);

         if (flag) {
            Nameless_Sorcerer_Entity.this.f_19853_
               .m_7967_(
                  new EvokerFangs(
                     Nameless_Sorcerer_Entity.this.f_19853_,
                     p_190876_1_,
                     (double)blockpos.m_123342_() + d0,
                     p_190876_3_,
                     p_190876_9_,
                     p_190876_10_,
                     Nameless_Sorcerer_Entity.this
                  )
               );
         }
      }

      @Override
      protected SoundEvent getSpellPrepareSound() {
         return SoundEvents.f_11867_;
      }

      @Override
      protected Nameless_Sorcerer_Entity.SpellType getSpellType() {
         return Nameless_Sorcerer_Entity.SpellType.FANGS;
      }
   }

   public class CastingASpellGoal extends Goal {
      public CastingASpellGoal() {
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean m_8036_() {
         return Nameless_Sorcerer_Entity.this.getSpellTicks() > 0;
      }

      public void m_8056_() {
         super.m_8056_();
         Nameless_Sorcerer_Entity.this.f_21344_.m_26573_();
      }

      public void m_8041_() {
         super.m_8041_();
         Nameless_Sorcerer_Entity.this.setSpellType(Nameless_Sorcerer_Entity.SpellType.NONE);
      }

      public void m_8037_() {
         if (Nameless_Sorcerer_Entity.this.m_5448_() != null) {
            Nameless_Sorcerer_Entity.this.m_21563_()
               .m_24960_(
                  Nameless_Sorcerer_Entity.this.m_5448_(), (float)Nameless_Sorcerer_Entity.this.m_8085_(), (float)Nameless_Sorcerer_Entity.this.m_8132_()
               );
         }
      }
   }

   class CastingSpellGoal extends Nameless_Sorcerer_Entity.CastingASpellGoal {
      private CastingSpellGoal() {
      }

      @Override
      public void m_8037_() {
         if (Nameless_Sorcerer_Entity.this.m_5448_() != null) {
            Nameless_Sorcerer_Entity.this.m_21563_()
               .m_24960_(
                  Nameless_Sorcerer_Entity.this.m_5448_(), (float)Nameless_Sorcerer_Entity.this.m_8085_(), (float)Nameless_Sorcerer_Entity.this.m_8132_()
               );
         }
      }
   }

   class IllusionSpellGoal extends Nameless_Sorcerer_Entity.UseSpellGoal {
      private IllusionSpellGoal() {
      }

      @Override
      public boolean m_8036_() {
         LivingEntity livingentity = Nameless_Sorcerer_Entity.this.m_5448_();
         if (livingentity == null || !livingentity.m_6084_()) {
            return false;
         } else if (Nameless_Sorcerer_Entity.this.getIsIllusion()) {
            return false;
         } else {
            return Nameless_Sorcerer_Entity.this.isSpellcasting() ? false : Nameless_Sorcerer_Entity.this.f_19797_ >= this.spellCooldown;
         }
      }

      @Override
      protected int getCastingTime() {
         return 80;
      }

      @Override
      protected int getCastingInterval() {
         return 300;
      }

      @Override
      protected void castSpell() {
         ServerLevel serverLevel = (ServerLevel)Nameless_Sorcerer_Entity.this.f_19853_;

         for (int i = 0; i < 2; i++) {
            LivingEntity target = Nameless_Sorcerer_Entity.this.m_5448_();
            BlockPos blockpos = Nameless_Sorcerer_Entity.this.m_20183_()
               .m_7918_(-2 + Nameless_Sorcerer_Entity.this.f_19796_.m_188503_(5), 0, -2 + Nameless_Sorcerer_Entity.this.f_19796_.m_188503_(5));
            Nameless_Sorcerer_Entity illusion = (Nameless_Sorcerer_Entity)((EntityType)ModEntities.NAMELESS_SORCERER.get())
               .m_20615_(Nameless_Sorcerer_Entity.this.f_19853_);
            illusion.m_20035_(blockpos, 0.0F, 0.0F);
            if (target != null) {
               illusion.m_6710_(target);
            }

            illusion.m_6518_(
               serverLevel, Nameless_Sorcerer_Entity.this.f_19853_.m_6436_(blockpos), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null
            );
            illusion.setIsIllusion(true);
            serverLevel.m_47205_(illusion);
         }
      }

      @Override
      protected SoundEvent getSpellPrepareSound() {
         return SoundEvents.f_11869_;
      }

      @Override
      protected Nameless_Sorcerer_Entity.SpellType getSpellType() {
         return Nameless_Sorcerer_Entity.SpellType.ILLUSION;
      }
   }

   public static enum SpellType {
      NONE(0),
      TELEPORTSPELL(1),
      FANGS(2),
      WOLOLO(3),
      ILLUSION(4);

      private final int id;

      private SpellType(int idIn) {
         this.id = idIn;
      }

      public static Nameless_Sorcerer_Entity.SpellType getFromId(int idIn) {
         for (Nameless_Sorcerer_Entity.SpellType Nameless_Sorcerer_Entity$spelltype : values()) {
            if (idIn == Nameless_Sorcerer_Entity$spelltype.id) {
               return Nameless_Sorcerer_Entity$spelltype;
            }
         }

         return NONE;
      }
   }

   class TeleportSpellGoal extends Nameless_Sorcerer_Entity.UseSpellGoal {
      private TeleportSpellGoal() {
      }

      @Override
      public boolean m_8036_() {
         LivingEntity livingentity = Nameless_Sorcerer_Entity.this.m_5448_();
         if (livingentity == null || !livingentity.m_6084_()) {
            return false;
         } else if (Nameless_Sorcerer_Entity.this.getIsIllusion()) {
            return false;
         } else if (Nameless_Sorcerer_Entity.this.m_20270_(livingentity) < 6.0F) {
            return false;
         } else {
            return Nameless_Sorcerer_Entity.this.isSpellcasting() ? false : Nameless_Sorcerer_Entity.this.f_19797_ >= this.spellCooldown;
         }
      }

      @Override
      protected int getCastingTime() {
         return 60;
      }

      @Override
      protected int getCastingInterval() {
         return 300;
      }

      @Override
      protected void castSpell() {
         LivingEntity target = Nameless_Sorcerer_Entity.this.m_5448_();
         this.teleportEntity(target);
      }

      public void teleportEntity(LivingEntity target) {
         if (target.m_20159_()) {
            target.m_8127_();
         }

         double d0 = target.m_20185_();
         double d1 = target.m_20186_();
         double d2 = target.m_20189_();
         double d3 = Nameless_Sorcerer_Entity.this.m_20185_();
         double d4 = Nameless_Sorcerer_Entity.this.m_20186_();
         double d5 = Nameless_Sorcerer_Entity.this.m_20189_();
         target.m_6021_(d3, d4, d5);
         target.m_5496_(SoundEvents.f_11757_, 1.0F, 1.0F);
         target.f_19859_ = Nameless_Sorcerer_Entity.this.m_146908_();
         target.f_19860_ = Nameless_Sorcerer_Entity.this.m_146909_();
         Nameless_Sorcerer_Entity.this.m_6021_(d0, d1, d2);
         Nameless_Sorcerer_Entity.this.m_5496_(SoundEvents.f_11852_, 1.0F, 1.0F);
         Nameless_Sorcerer_Entity.this.f_19859_ = target.m_146908_();
         Nameless_Sorcerer_Entity.this.f_19860_ = target.m_146909_();
      }

      @Override
      protected SoundEvent getSpellPrepareSound() {
         return SoundEvents.f_12054_;
      }

      @Override
      protected Nameless_Sorcerer_Entity.SpellType getSpellType() {
         return Nameless_Sorcerer_Entity.SpellType.TELEPORTSPELL;
      }
   }

   public abstract class UseSpellGoal extends Goal {
      protected int spellWarmup;
      protected int spellCooldown;

      protected UseSpellGoal() {
      }

      public boolean m_8036_() {
         LivingEntity livingentity = Nameless_Sorcerer_Entity.this.m_5448_();
         if (livingentity == null || !livingentity.m_6084_()) {
            return false;
         } else {
            return Nameless_Sorcerer_Entity.this.isSpellcasting() ? false : Nameless_Sorcerer_Entity.this.f_19797_ >= this.spellCooldown;
         }
      }

      public boolean m_8045_() {
         LivingEntity livingentity = Nameless_Sorcerer_Entity.this.m_5448_();
         return livingentity != null && livingentity.m_6084_() && this.spellWarmup > 0;
      }

      public void m_8056_() {
         this.spellWarmup = this.getCastWarmupTime();
         Nameless_Sorcerer_Entity.this.spellTicks = this.getCastingTime();
         this.spellCooldown = Nameless_Sorcerer_Entity.this.f_19797_ + this.getCastingInterval();
         SoundEvent soundevent = this.getSpellPrepareSound();
         if (soundevent != null) {
            Nameless_Sorcerer_Entity.this.m_5496_(soundevent, 1.0F, 1.0F);
         }

         Nameless_Sorcerer_Entity.this.setSpellType(this.getSpellType());
      }

      public void m_8037_() {
         this.spellWarmup--;
         if (this.spellWarmup == 0) {
            this.castSpell();
            Nameless_Sorcerer_Entity.this.m_5496_(Nameless_Sorcerer_Entity.this.getSpellSound(), 1.0F, 1.0F);
         }
      }

      protected abstract void castSpell();

      protected int getCastWarmupTime() {
         return 20;
      }

      protected abstract int getCastingTime();

      protected abstract int getCastingInterval();

      @Nullable
      protected abstract SoundEvent getSpellPrepareSound();

      protected abstract Nameless_Sorcerer_Entity.SpellType getSpellType();
   }
}
