package com.obscuria.aquamirae.common.entities;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.obscureapi.api.common.FlatVFX.Builder;
import com.obscuria.obscureapi.api.hekate.Animation;
import com.obscuria.obscureapi.api.hekate.AnimationHelper;
import com.obscuria.obscureapi.api.hekate.IAnimated;
import com.obscuria.obscureapi.util.EntityUtils;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import org.jetbrains.annotations.NotNull;

@ShipGraveyardEntity
public class Eel extends Monster implements IAnimated {
   private static final EntityDataAccessor<Integer> MOVE_COOLDOWN = SynchedEntityData.m_135353_(Eel.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Float> POS_X = SynchedEntityData.m_135353_(Eel.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Float> POS_Y = SynchedEntityData.m_135353_(Eel.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Float> POS_Z = SynchedEntityData.m_135353_(Eel.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Integer> HIT_SERIES = SynchedEntityData.m_135353_(Eel.class, EntityDataSerializers.f_135028_);
   public final Animation RARE_IDLE = new Animation(1);
   public final Animation ATTACK = new Animation(2);
   public final Animation ROAR = new Animation(3);
   public final Animation DEATH = new Animation(4);
   public final Animation MOVE = new Animation(5);
   private int rareIdle;

   public Eel(SpawnEntity packet, Level world) {
      this((EntityType<Eel>)AquamiraeEntities.EEL.get(), world);
   }

   public Eel(EntityType<Eel> type, Level world) {
      super(type, world);
      this.f_21364_ = 120;
      this.m_21530_();
   }

   public AABB m_6921_() {
      return super.m_6921_().m_82400_(2.0);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, false, false));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, AbstractIllager.class, false, false));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, AbstractVillager.class, false, false));
   }

   protected void m_8097_() {
      super.m_8097_();
      this.m_20088_().m_135372_(MOVE_COOLDOWN, 200);
      this.m_20088_().m_135372_(POS_X, (float)this.m_20185_());
      this.m_20088_().m_135372_(POS_Y, (float)this.m_20186_());
      this.m_20088_().m_135372_(POS_Z, (float)this.m_20189_());
      this.m_20088_().m_135372_(HIT_SERIES, 0);
   }

   public void m_7380_(@NotNull CompoundTag tag) {
      super.m_7380_(tag);
      CompoundTag data = new CompoundTag();
      data.m_128405_("MoveCooldown", (Integer)this.m_20088_().m_135370_(MOVE_COOLDOWN));
      data.m_128405_("Hits", (Integer)this.m_20088_().m_135370_(HIT_SERIES));
      data.m_128350_("x", (Float)this.m_20088_().m_135370_(POS_X));
      data.m_128350_("y", (Float)this.m_20088_().m_135370_(POS_Y));
      data.m_128350_("z", (Float)this.m_20088_().m_135370_(POS_Z));
      tag.m_128365_("EelData", data);
   }

   public void m_7378_(@NotNull CompoundTag tag) {
      super.m_7378_(tag);
      CompoundTag data = (CompoundTag)tag.m_128423_("EelData");
      if (data != null) {
         this.m_20088_().m_135381_(MOVE_COOLDOWN, data.m_128451_("MoveCooldown"));
         this.m_20088_().m_135381_(HIT_SERIES, data.m_128451_("Hits"));
         this.m_20088_().m_135381_(POS_X, data.m_128457_("x"));
         this.m_20088_().m_135381_(POS_Y, data.m_128457_("y"));
         this.m_20088_().m_135381_(POS_Z, data.m_128457_("z"));
      }
   }

   public Optional<Animation> getAnimation(byte id) {
      return switch (id) {
         case 1 -> Optional.of(this.RARE_IDLE);
         case 2 -> Optional.of(this.ATTACK);
         case 3 -> Optional.of(this.ROAR);
         case 4 -> Optional.of(this.DEATH);
         case 5 -> Optional.of(this.MOVE);
         default -> Optional.empty();
      };
   }

   public void m_6075_() {
      AnimationHelper.handleDeath(this, this.DEATH, 60);
      AnimationHelper.handle(new Animation[]{this.RARE_IDLE, this.ATTACK, this.ROAR, this.DEATH, this.MOVE});
      this.ATTACK.sound(this, 1, AquamiraeSounds.ENTITY_EEL_BITE, SoundSource.HOSTILE, 2.0F, 1.0F);
      this.ROAR.sound(this, 1, AquamiraeSounds.ENTITY_EEL_ROAR, SoundSource.HOSTILE, 2.0F, 0.8F + this.f_19796_.m_188501_() * 0.2F);
      this.rareIdle--;
      int moveCooldown = (Integer)this.m_20088_().m_135370_(MOVE_COOLDOWN);
      int hitSeries = (Integer)this.m_20088_().m_135370_(HIT_SERIES);
      this.m_20088_().m_135381_(MOVE_COOLDOWN, moveCooldown - 1);
      if (this.rareIdle <= 0 && !this.RARE_IDLE.isPlaying() && this.f_19796_.m_188503_(200) == 1) {
         this.RARE_IDLE.play(this, this.f_19796_.m_216339_(150, 300));
         this.rareIdle = 400;
      }

      if (this.MOVE.isPlaying()) {
         if (this.MOVE.getTick() == 25) {
            this.m_6021_(
               (double)((Float)this.m_20088_().m_135370_(POS_X)).floatValue(),
               (double)((Float)this.m_20088_().m_135370_(POS_Y)).floatValue(),
               (double)((Float)this.m_20088_().m_135370_(POS_Z)).floatValue()
            );
         }

         for (int i = 0; i < 5; i++) {
            this.spawnBlockParticles();
         }
      } else {
         if (this.m_5448_() != null) {
            LivingEntity target = this.m_5448_();
            double distance = this.m_20280_(target);
            this.f_21365_.m_148051_(target);
            if ((Integer)this.m_20088_().m_135370_(HIT_SERIES) <= 0 && distance <= 30.0 && this.f_19796_.m_188503_(60) == 1) {
               this.m_20088_().m_135381_(HIT_SERIES, this.f_19796_.m_216339_(1, 3));
            } else if ((Integer)this.m_20088_().m_135370_(HIT_SERIES) > 0 && !this.ATTACK.isPlaying() && !this.ROAR.isPlaying()) {
               this.m_20088_().m_135381_(HIT_SERIES, hitSeries - 1);
               this.ATTACK.play(this, 30);
            }

            if (!this.ATTACK.isPlaying() && !this.ROAR.isPlaying() && this.f_19796_.m_188503_(100) == 1) {
               this.ROAR.play(this, 100);
            }

            if (distance <= 24.0 && this.ATTACK.getTick() == 14) {
               float hp = target.m_21223_();
               this.m_7327_(target);
               if (target.m_21223_() < hp) {
                  this.m_5634_((hp - target.m_21223_()) * 2.0F);
               }
            }

            if (!target.m_9236_().m_5776_() && this.ROAR.isPlaying() && this.ROAR.getTick() > 12 && this.ROAR.getTick() < 52) {
               if (this.f_19797_ % 5 == 0) {
                  Vec3 pos = EntityUtils.getRelativePos(this, 4.0F, 0.0F, 0.0F);
                  Builder.create(20)
                     .owner(this)
                     .texture("aquamirae", "roar")
                     .pos(pos.m_82520_(0.0, this.m_20188_() - pos.f_82480_ - 0.2, 0.0))
                     .relativeRot(this, true, true)
                     .xRot(90.0F, 0.0F, 0.0F)
                     .zRot((float)(360.0 * Math.random()), 0.0F, 0.0F)
                     .moveForward(0.0F, 3.0F, 0.03F)
                     .scale(0.4F, 0.1F, 0.01F)
                     .alpha(0.25F, -0.0025F, -0.0025F)
                     .build(this.f_19853_);
               }

               if (distance < 150.0) {
                  Vec3 thisVec = new Vec3(this.m_20185_(), this.m_20186_(), this.m_20189_());
                  Vec3 targetVec = new Vec3(target.m_20185_(), target.m_20186_(), target.m_20189_());
                  Vec3 targetDelta = target.m_20184_();
                  Vec3 roarVec = thisVec.m_82505_(targetVec).m_82490_(0.07).m_82490_(Math.max(0.0, 1.0 - distance / 150.0));
                  target.m_20334_(targetDelta.f_82479_ + roarVec.f_82479_, targetDelta.f_82480_, targetDelta.f_82481_ + roarVec.f_82481_);
                  if (target instanceof Player player) {
                     player.f_19864_ = true;
                  }
               }

               if (target instanceof Mob mob) {
                  mob.m_6710_(this);
               }
            }
         }

         if (!this.ATTACK.isPlaying() && !this.ROAR.isPlaying() && moveCooldown < 0) {
            Vec3 pos = this.m_5448_() != null && this.m_20280_(this.m_5448_()) > 40.0 ? this.targetEelMove() : this.randomEelMove();
            if (pos.m_7098_() == 0.0) {
               this.m_20088_().m_135381_(MOVE_COOLDOWN, 2);
            } else {
               this.m_20088_().m_135381_(POS_X, (float)pos.m_7096_());
               this.m_20088_().m_135381_(POS_Y, (float)pos.m_7098_());
               this.m_20088_().m_135381_(POS_Z, (float)pos.m_7094_());
               this.m_20088_().m_135381_(MOVE_COOLDOWN, 400);
               this.MOVE.play(this, 100);
            }
         }
      }

      super.m_6075_();
   }

   public void spawnBlockParticles() {
      BlockState state = this.f_19853_.m_8055_(this.m_20183_().m_7495_());
      if (state.m_60799_() != RenderShape.INVISIBLE) {
         this.f_19853_
            .m_7106_(
               new BlockParticleOption(ParticleTypes.f_123794_, state).setPos(this.m_20183_().m_7495_()),
               this.m_20182_().f_82479_ + (this.f_19796_.m_188500_() - 0.5) * 1.6,
               this.m_20182_().f_82480_ + 0.03,
               this.m_20182_().f_82481_ + (this.f_19796_.m_188500_() - 0.5) * 1.6,
               0.0,
               1.5,
               0.0
            );
      }
   }

   public Vec3 randomEelMove() {
      RandomSource random = RandomSource.m_216327_();
      int x = this.m_146903_() + random.m_216339_(5, 16) * (Math.random() > 0.5 ? -1 : 1);
      int z = this.m_146907_() + random.m_216339_(5, 16) * (Math.random() > 0.5 ? -1 : 1);

      for (int i = -8; i < 8; i++) {
         if (this.checkGround(x, this.m_146904_() - i, z)
            && this.checkSpace(x, this.m_146904_() - i + 1, z)
            && this.checkSpace(x, this.m_146904_() - i + 2, z)
            && this.checkSpace(x, this.m_146904_() - i + 3, z)
            && this.checkSpace(x, this.m_146904_() - i + 4, z)) {
            return new Vec3((double)x + 0.5, (double)(this.m_146904_() - i) + 1.0, (double)z + 0.5);
         }
      }

      return Vec3.f_82478_;
   }

   public Vec3 targetEelMove() {
      if (this.m_5448_() == null) {
         return Vec3.f_82478_;
      } else {
         RandomSource random = RandomSource.m_216327_();
         int x = this.m_5448_().m_146903_() + random.m_216339_(-3, 3);
         int z = this.m_5448_().m_146907_() + random.m_216339_(-3, 3);

         for (int i = -4; i < 4; i++) {
            if (this.checkGround(x, this.m_146904_() - i, z)
               && this.checkSpace(x, this.m_146904_() - i + 1, z)
               && this.checkSpace(x, this.m_146904_() - i + 2, z)
               && this.checkSpace(x, this.m_146904_() - i + 3, z)
               && this.checkSpace(x, this.m_146904_() - i + 4, z)) {
               return new Vec3((double)x + 0.5, (double)(this.m_146904_() - i) + 1.0, (double)z + 0.5);
            }
         }

         return Vec3.f_82478_;
      }
   }

   public boolean checkGround(int x, int y, int z) {
      return this.m_9236_().m_8055_(new BlockPos(x, y, z)).m_204336_(Aquamirae.EEL_MOVE)
         && this.m_9236_().m_8055_(new BlockPos(x + 1, y, z)).m_204336_(Aquamirae.EEL_MOVE)
         && this.m_9236_().m_8055_(new BlockPos(x - 1, y, z)).m_204336_(Aquamirae.EEL_MOVE)
         && this.m_9236_().m_8055_(new BlockPos(x, y, z + 1)).m_204336_(Aquamirae.EEL_MOVE)
         && this.m_9236_().m_8055_(new BlockPos(x, y, z - 1)).m_204336_(Aquamirae.EEL_MOVE);
   }

   public boolean checkSpace(int x, int y, int z) {
      return this.m_9236_().m_46859_(new BlockPos(x, y, z))
         && this.m_9236_().m_46859_(new BlockPos(x + 1, y, z))
         && this.m_9236_().m_46859_(new BlockPos(x - 1, y, z))
         && this.m_9236_().m_46859_(new BlockPos(x, y, z + 1))
         && this.m_9236_().m_46859_(new BlockPos(x, y, z - 1))
         && this.m_9236_().m_46859_(new BlockPos(x + 1, y, z + 1))
         && this.m_9236_().m_46859_(new BlockPos(x - 1, y, z - 1))
         && this.m_9236_().m_46859_(new BlockPos(x + 1, y, z - 1))
         && this.m_9236_().m_46859_(new BlockPos(x - 1, y, z + 1));
   }

   public SpawnGroupData m_6518_(
      ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag
   ) {
      Aquamirae.loadFromConfig(this, Attributes.f_22276_, (Double)AquamiraeConfig.Common.eelMaxHealth.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22284_, (Double)AquamiraeConfig.Common.eelArmor.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22281_, (Double)AquamiraeConfig.Common.eelAttackDamage.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22277_, (Double)AquamiraeConfig.Common.eelFollowRange.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22282_, (Double)AquamiraeConfig.Common.eelAttackKnockback.get());
      return super.m_6518_(world, difficulty, reason, livingdata, tag);
   }

   public boolean isPushedByFluid(FluidType type) {
      return false;
   }

   public void m_5997_(double d1, double d2, double d3) {
   }

   public MobType m_6336_() {
      return MobType.f_21640_;
   }

   public boolean m_6785_(double distanceToClosestPlayer) {
      return false;
   }

   public SoundEvent m_7515_() {
      return (SoundEvent)AquamiraeSounds.ENTITY_DEEP_AMBIENT.get();
   }

   public SoundEvent m_7975_(DamageSource source) {
      return (SoundEvent)AquamiraeSounds.ENTITY_DEEP_HURT.get();
   }

   public SoundEvent m_5592_() {
      return (SoundEvent)AquamiraeSounds.ENTITY_DEEP_DEATH.get();
   }

   public boolean m_6469_(DamageSource source, float amount) {
      if (this.MOVE.isPlaying()) {
         return false;
      } else if (source.m_7640_() instanceof AbstractArrow) {
         return false;
      } else {
         return source != DamageSource.f_19315_ && source != DamageSource.f_19314_ && source != DamageSource.f_19312_ ? super.m_6469_(source, amount) : false;
      }
   }

   public boolean m_6072_() {
      return false;
   }

   @NotNull
   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Mob.m_21552_()
         .m_22268_(Attributes.f_22279_, 0.0)
         .m_22268_(Attributes.f_22276_, 180.0)
         .m_22268_(Attributes.f_22284_, 20.0)
         .m_22268_(Attributes.f_22281_, 8.0)
         .m_22268_(Attributes.f_22277_, 32.0)
         .m_22268_(Attributes.f_22282_, 2.0)
         .m_22268_(Attributes.f_22278_, 100.0);
   }
}
