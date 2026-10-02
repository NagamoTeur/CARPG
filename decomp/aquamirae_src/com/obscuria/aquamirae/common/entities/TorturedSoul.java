package com.obscuria.aquamirae.common.entities;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.obscureapi.api.hekate.Animation;
import com.obscuria.obscureapi.api.hekate.AnimationHelper;
import com.obscuria.obscureapi.api.hekate.IAnimated;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.SpawnPlacements.SpawnPredicate;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import org.jetbrains.annotations.NotNull;

@ShipGraveyardEntity
public class TorturedSoul extends Monster implements IAnimated {
   public final Animation ATTACK = new Animation(1);

   public TorturedSoul(SpawnEntity packet, Level world) {
      this((EntityType<TorturedSoul>)AquamiraeEntities.TORTURED_SOUL.get(), world);
   }

   public TorturedSoul(EntityType<TorturedSoul> type, Level world) {
      super(type, world);
      this.f_21364_ = 0;
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(1, new MeleeAttackGoal(this, 1.2, false) {
         protected double m_6639_(@NotNull LivingEntity entity) {
            return 4.0 + (double)(entity.m_20205_() * entity.m_20205_());
         }
      });
      this.f_21346_.m_25352_(2, new HurtByTargetGoal(this, new Class[0]));
      this.f_21345_.m_25352_(3, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.f_21345_.m_25352_(4, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(5, new LookAtPlayerGoal(this, Monster.class, 6.0F));
      this.f_21345_.m_25352_(6, new FloatGoal(this));
      this.f_21346_.m_25352_(7, new NearestAttackableTargetGoal(this, Player.class, false, false));
      this.f_21346_.m_25352_(8, new NearestAttackableTargetGoal(this, AbstractIllager.class, false, false));
      this.f_21346_.m_25352_(9, new NearestAttackableTargetGoal(this, AbstractVillager.class, false, false));
   }

   public Optional<Animation> getAnimation(byte id) {
      return id == 1 ? Optional.of(this.ATTACK) : Optional.empty();
   }

   @NotNull
   public MobType m_6336_() {
      return MobType.f_21643_;
   }

   public SoundEvent m_7515_() {
      return SoundEvents.f_12048_;
   }

   public SoundEvent m_7975_(@NotNull DamageSource source) {
      return SoundEvents.f_12051_;
   }

   public SoundEvent m_5592_() {
      return SoundEvents.f_12050_;
   }

   public boolean m_6469_(DamageSource source, float amount) {
      if (source.m_7640_() instanceof ThrownPotion || source.m_7640_() instanceof AreaEffectCloud) {
         return false;
      } else {
         return source == DamageSource.f_19312_ ? false : super.m_6469_(source, amount);
      }
   }

   public boolean m_7327_(@NotNull Entity entity) {
      this.ATTACK.play(this, 20);
      return super.m_7327_(entity);
   }

   public SpawnGroupData m_6518_(
      @NotNull ServerLevelAccessor world,
      @NotNull DifficultyInstance difficulty,
      @NotNull MobSpawnType spawnType,
      @Nullable SpawnGroupData spawnGroupData,
      @Nullable CompoundTag tag
   ) {
      Aquamirae.loadFromConfig(this, (Attribute)ForgeMod.SWIM_SPEED.get(), (Double)AquamiraeConfig.Common.soulSwimSpeed.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22279_, (Double)AquamiraeConfig.Common.soulSpeed.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22276_, (Double)AquamiraeConfig.Common.soulMaxHealth.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22284_, (Double)AquamiraeConfig.Common.soulArmor.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22281_, (Double)AquamiraeConfig.Common.soulAttackDamage.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22277_, (Double)AquamiraeConfig.Common.soulFollowRange.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22282_, (Double)AquamiraeConfig.Common.soulAttackKnockback.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22278_, (Double)AquamiraeConfig.Common.soulKnockbackResistance.get());
      return super.m_6518_(world, difficulty, spawnType, spawnGroupData, tag);
   }

   public void m_6075_() {
      AnimationHelper.handle(new Animation[]{this.ATTACK});
      if (this.m_5448_() != null && !this.m_21023_(MobEffects.f_19609_)) {
         this.getPersistentData().m_128347_("charge", this.getPersistentData().m_128459_("charge") + 1.0);
         if (this.getPersistentData().m_128459_("charge") > 200.0) {
            this.getPersistentData().m_128347_("charge", 0.0);
            this.m_7292_(new MobEffectInstance(MobEffects.f_19596_, 200, 2, true, false));
            this.m_7292_(new MobEffectInstance(MobEffects.f_19609_, 200, 0, true, false));
            if (this.f_19853_ instanceof ServerLevel server) {
               server.m_8767_(ParticleTypes.f_123755_, this.m_20185_(), this.m_20186_() + 1.0, this.m_20189_(), 10, 0.4, 0.8, 0.4, 0.0);
            }

            this.m_20256_(new Vec3(this.m_20184_().m_7096_(), 0.2, this.m_20184_().m_7094_()));
         }
      }

      if (this.m_21023_(MobEffects.f_19609_)) {
         this.getPersistentData().m_128347_("flame", this.getPersistentData().m_128459_("flame") + 1.0);
         this.getPersistentData().m_128347_("smoke", this.getPersistentData().m_128459_("smoke") + 1.0);
         if (this.getPersistentData().m_128459_("smoke") > 1.0) {
            this.getPersistentData().m_128347_("smoke", 0.0);
            if (this.f_19853_ instanceof ServerLevel server) {
               server.m_8767_(ParticleTypes.f_123755_, this.m_20185_(), this.m_20186_() + 1.3, this.m_20189_(), 1, 0.14, 0.2, 0.14, 0.0);
            }
         }

         if (this.getPersistentData().m_128459_("flame") > 6.0) {
            this.getPersistentData().m_128347_("flame", 0.0);
            if (this.f_19853_ instanceof ServerLevel server) {
               server.m_8767_(ParticleTypes.f_123745_, this.m_20185_(), this.m_20186_() + 1.3, this.m_20189_(), 1, 0.1, 0.15, 0.1, 0.0);
            }
         }
      }

      super.m_6075_();
   }

   public static SpawnPredicate<TorturedSoul> getSpawnRules() {
      return Monster::m_219013_;
   }

   @NotNull
   public static Builder createAttributes() {
      return Mob.m_21552_()
         .m_22268_((Attribute)ForgeMod.SWIM_SPEED.get(), 3.0)
         .m_22268_(Attributes.f_22279_, 0.2)
         .m_22268_(Attributes.f_22276_, 30.0)
         .m_22268_(Attributes.f_22284_, 4.0)
         .m_22268_(Attributes.f_22281_, 7.0)
         .m_22268_(Attributes.f_22277_, 24.0)
         .m_22268_(Attributes.f_22282_, 0.7)
         .m_22268_(Attributes.f_22278_, 0.0);
   }
}
